package com.example.oscar.projekt1;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SensorDetailActivity extends Activity implements SensorEventListener{
    private TextView tvName, tvVendor, tvPower, tvRes, tvMaxRange, tvMinDelay, tvVersion, tvType, tvXAxis, tvYAxis,
            tvZAxis, tvXIron, tvYIron, tvZIron, tvTimeStamp, tvAcc;
    private String sensorName;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Button btnRun;
    private final int SensorDelayCustomValue = 600000;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_detail);
        mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        Bundle extras = getIntent().getExtras();
        initializeComps(extras);

        if(mSensorManager.getDefaultSensor(extras.getInt("SENSOR_TYPE"))!= null){
            mSensor = mSensorManager.getDefaultSensor(extras.getInt("SENSOR_TYPE"));
            Toast.makeText(this, mSensor.getName() + " exist", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, mSensor.getName() + " doesn't exist", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Method that initialize all the textviews components for this activity
     * @param extras
     */
    public void initializeComps(Bundle extras){
        tvAcc = (TextView) findViewById(R.id.tvAcc);
        tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        tvName = (TextView) findViewById(R.id.tvName);
        tvVendor = (TextView) findViewById(R.id.tvVendor);
        tvPower = (TextView) findViewById(R.id.tvPower);
        tvMaxRange = (TextView) findViewById(R.id.tvMaxRange);
        tvMinDelay = (TextView) findViewById(R.id.tvMinDelay);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvRes = (TextView) findViewById(R.id.tvRes);
        tvType = (TextView) findViewById(R.id.tvType);
        tvXAxis = (TextView)findViewById(R.id.tvXAxis);
        tvYAxis = (TextView)findViewById(R.id.tvYAxis);
        tvZAxis = (TextView)findViewById(R.id.tvZAxis);

        tvXIron = (TextView)findViewById(R.id.tvXIron);
        tvYIron = (TextView)findViewById(R.id.tvYIron);
        tvZIron = (TextView)findViewById(R.id.tvZIron);


        tvName.setText("Name: " + extras.getString("SENSOR_NAME"));
        tvVendor.setText("Vendor: " + extras.getString("SENSOR_VENDOR"));
        tvPower.setText("Power: " + extras.getString("SENSOR_POWER"));
        tvType.setText("Type: " + String.valueOf(extras.getInt("SENSOR_TYPE")));
        tvMaxRange.setText("Max Range: " + extras.getString("SENSOR_MAXRANGE"));
        tvMinDelay.setText("Min delay: " + extras.getString("SENSOR_DELAY"));
        tvRes.setText("Resolution: " + extras.getString("SENSOR_RES"));
        tvVersion.setText("Version: " + extras.getString("SENSOR_VERSION"));

        btnRun = (Button) findViewById(R.id.btnRun);
        btnRun.setTag(1);
        btnRun.setText("Start sensor");
        btnRun.setOnClickListener(new buttonListener());
    }


    /**
     * Method that listen to which sensor that have been checked
     * once a sensor has been choosen it calls for another method to set the readings from the sensor
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        tvAcc.setText("Accuracy " + event.accuracy);
        tvTimeStamp.setText("Timestamp " + dateFormat.format(new Date(event.timestamp / 1000000)));
        switch (event.sensor.getType()){
            //ENVIRONMENT SENSORS
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                oneValueSensor(event);
                break;
            case Sensor.TYPE_PRESSURE:
                oneValueSensor(event);
                break;
            case Sensor.TYPE_LIGHT:
                oneValueSensor(event);
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                oneValueSensor(event);
                break;
            //POSITION SENSORS
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                threeValuedSensor(event);
                break;
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                threeValuedSensor(event);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                threeValuedSensor(event);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                fiveValuedSensor(event);
                break;
            case Sensor.TYPE_PROXIMITY:
                oneValueSensor(event);
                break;
            //MOTIONS SENSORS
            case Sensor.TYPE_ACCELEROMETER:
                threeValuedSensor(event);
                break;
            case Sensor.TYPE_GRAVITY:
                threeValuedSensor(event);
                break;
            case Sensor.TYPE_GYROSCOPE:
                threeValuedSensor(event);
                break;
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                fiveValuedSensor(event);
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                threeValuedSensor(event);
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                threeValuedSensor(event);
                tvXIron.setText("Scalar: " + event.values[3]);
                break;
            case Sensor.TYPE_STEP_COUNTER:
                oneValueSensor(event);
                break;
            case Sensor.TYPE_STEP_DETECTOR:
                break;
        }
    }

    /**
     * Method that sets the value to the textview to all sensors that only shows ONE value
     * @param event
     */
    public void oneValueSensor(SensorEvent event){
        tvXAxis.setText("Value " + String.valueOf(event.values[0]));

    }
    /**
     * Method that sets the value to the textview to all sensors that only shows TWO value
     * @param event
     */
    public void threeValuedSensor(SensorEvent event){
        tvXAxis.setText("X " + String.valueOf(event.values[0]));
        tvZAxis.setText("Y " + String.valueOf(event.values[1]));
        tvYAxis.setText("Z  " + String.valueOf(event.values[2]));
    }

    /**
     * Method that sets the value to the textview to all sensors that only shows FIVE value
     * @param event
     */
    public void fiveValuedSensor(SensorEvent event){
        tvXAxis.setText("X " + String.valueOf(event.values[0]));
        tvZAxis.setText("Y " + String.valueOf(event.values[1]));
        tvYAxis.setText("Z  " + String.valueOf(event.values[2]));
        tvXIron.setText("Iron Bais X: " + String.valueOf(event.values[3]));
        tvZIron.setText("Iron Bais Z: " + String.valueOf(event.values[4]));
        tvYIron.setText("Iron Bais Y: " + String.valueOf(event.values[5]));
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSensorManager.unregisterListener(this);
        mSensorManager = null;
        mSensor = null;
    }


    /**
     * Button listener class, that listen to which button is clicked.
     * Depending which button is clicked it either register or unregister the sensors
     */
    private class buttonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final int status =(Integer) v.getTag();
            if(status == 1) {
                mSensorManager.registerListener(SensorDetailActivity.this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
                Toast.makeText(getApplicationContext(), mSensor.getName() + " sensor is registered", Toast.LENGTH_SHORT).show();
                btnRun.setText("Stop Sensor");
                v.setTag(0);
            } else {
                mSensorManager.unregisterListener(SensorDetailActivity.this);
                Toast.makeText(getApplicationContext(), mSensor.getName() + " sensor is unregistered", Toast.LENGTH_SHORT).show();
                btnRun.setText("Start Sensor");
                v.setTag(1);
            }

        }
    }
}
