package com.example.oscar.compass;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class StepActivity extends AppCompatActivity implements SensorEventListener, ChangeListener{
    private ImageView mCompass;
    private int innerCounter = 0;
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor, mMagnetometerSensor;
    public Boolean mBound;
    public MyServiceConnection mConnection;
    public MyService mService;
    private float[] mLastAccelerometer = new float[3];
    private boolean mLastAccelerometerSet;
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastMagnetometerSet;
    private float[] mRotationMatrix = new float[16];
    private float mCurrentDegree;
    private float[] mOrientation = new float[3];
    private long lastUpdateTime;
    private Boolean isFirstValue;
    private float x,y,z, last_x,last_y,last_z;
    private float shakeThreshold = 15;
    private TextView tvAverageSpeed, tvStepcounter;
    private Button btnReset;
    private StepsDBHelper mStepsDBHelper;
    private boolean isFirstStep = true;
    private long startTime;
    private Random random = new Random();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStepcounter = (TextView)findViewById(R.id.tvStepCounter);
        tvAverageSpeed = (TextView)findViewById(R.id.tvAverageSpeed);
        mCompass = (ImageView)findViewById(R.id.imageView2);
        btnReset = (Button)findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new resetButtonListener());

        mStepsDBHelper = new StepsDBHelper(this);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }else{
            Log.d("Acc","Sensor dosen't exist");
        }


        if(mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            mMagnetometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }else{
            Log.d("Mag","Sensor dosen't exist");
        }


        mConnection = new MyServiceConnection(this);
        Intent stepsIntent = new Intent(this, MyService.class);
        bindService(stepsIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometerSensor,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mMagnetometerSensor,SensorManager.SENSOR_DELAY_UI);
        this.isFirstValue = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometerSensor);
        mSensorManager.unregisterListener(this, mMagnetometerSensor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * Method that senses when the devices sensor reads any external values
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            isShakeDetected(event);
        }
        rotateUsingOrientationAPI(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}


    /**
     * Method randomly rotate the compass when the device is shaken
     * @param event
     */
    public void isShakeDetected(SensorEvent event){
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
        if(isFirstValue) {
            float deltaX = Math.abs(last_x - x);
            float deltaY = Math.abs(last_y - y);
            float deltaZ = Math.abs(last_z - z);
            if((deltaX > shakeThreshold && deltaY > shakeThreshold)
                    || (deltaX > shakeThreshold && deltaZ > shakeThreshold)
                    || (deltaY > shakeThreshold && deltaZ > shakeThreshold)) {

                int rotation = random.nextInt(240-180)+160;
                float randomRotation = (float)rotation;
                Log.d("TAG",String.valueOf(randomRotation));
                RotateAnimation mRotateAnimation = new RotateAnimation(mCurrentDegree, randomRotation,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                mRotateAnimation.setDuration(500);
                mRotateAnimation.setFillAfter(true);
                mCompass.startAnimation(mRotateAnimation);
                Toast.makeText(getApplicationContext(),"Rotation detected", Toast.LENGTH_SHORT).show();
            }
        }
        last_x = x;
        last_y = y;
        last_z = z;
        isFirstValue = true;
    }

    /**
     * Method makes the compass to always point to north even when you rotate the device
     * @param event
     */
    public void rotateUsingOrientationAPI(SensorEvent event) {
        if (event.sensor == mAccelerometerSensor) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0,event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometerSensor) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0,event.values.length);
            mLastMagnetometerSet = true;
        }//only 4 times in 1 second

        if (mLastAccelerometerSet && mLastMagnetometerSet && System.currentTimeMillis() -lastUpdateTime > 250) {
            SensorManager.getRotationMatrix(mRotationMatrix, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mRotationMatrix, mOrientation);
            float azimuthInRadians = mOrientation[0];
            float azimuthInDegress = (float) (Math.toDegrees(azimuthInRadians) + 360) % 360;
            RotateAnimation mRotateAnimation = new RotateAnimation(mCurrentDegree, -azimuthInDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            mRotateAnimation.setDuration(250);
            mRotateAnimation.setFillAfter(true);
            mCompass.startAnimation(mRotateAnimation);
            mCurrentDegree = -azimuthInDegress;
            lastUpdateTime = System.currentTimeMillis();
        }
    }

    /**
     * Button listener class that resets the step counter to zero by deleting the table in the database
     * and sets the textviews to zero again
     */
    private class resetButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mStepsDBHelper.deleteAllEntries();
            tvAverageSpeed.setText("Average speed: " + 0);
            tvStepcounter.setText("Number of steps: " + mStepsDBHelper.readStepsEntries());
        }
    }


    /**
     * Method that updates the display with the new values for number of steps and steps per second
     * Steps per second is calculated by starting a timer that has a counter that increases with one everytime the step detector
     * detects a step. After 1 second the timer resters
     */
    public void update(){
        innerCounter++;
        if(isFirstStep) {
            handler.postDelayed(new Runnable(){
                public void run(){
                    tvAverageSpeed.setText("Average speed: " + String.valueOf(innerCounter) + " steps/second");
                    innerCounter = 0;
                    handler.postDelayed(this, 1000);
                }
            }, 1000);
            isFirstStep = false;
        }
        tvStepcounter.setText("Number of steps: " + mStepsDBHelper.readStepsEntries());
    }


}
