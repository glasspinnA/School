package com.example.oscar.compass;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service implements SensorEventListener{

    private IBinder myBinder;
    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor, mStepCounterSensor;
    private ChangeListener mListener;
    private StepsDBHelper mStepsDbHelper;
    private int counter = 0;
    public MyService() {}


    @Override
    public void onCreate(){
        super.onCreate();
        myBinder = new MyLocalBinder();
        mSensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null){
            mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            mStepsDbHelper = new StepsDBHelper(this);
        }else{
            Toast.makeText(getApplicationContext(), "Sensor doesn't exist", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(getApplicationContext(),"Service is bound", Toast.LENGTH_SHORT).show();
    }


    @Override
    public IBinder onBind(Intent intent) {
        mSensorManager.registerListener(this,mStepDetectorSensor,SensorManager.SENSOR_DELAY_NORMAL);
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"Service has stopped", Toast.LENGTH_SHORT).show();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mSensorManager.unregisterListener(this);
        return super.onUnbind(intent);
    }


    /**
     * Method that senses if a step has been taken. If it's true then it storage the step in the database and updates the screen
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            mStepsDbHelper.createStepsEntry();
            if(mListener!= null)
                mListener.update();
        }
    }


    public void setListenerActivity(ChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Class that binds the activity to the service class
     */
    public class MyLocalBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }

}

