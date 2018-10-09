package com.example.oscar.project1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private TextView textViewValue, tvAcc, tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE );
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!= null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }else{
            Toast.makeText(this, mSensor.getName() + "dosent exist", Toast.LENGTH_LONG).show();
        }
        textViewValue = (TextView)findViewById(R.id.lbl_text);
        tvAcc = (TextView)findViewById(R.id.lblAcc);
        tvTime = (TextView)findViewById(R.id.lblTime);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Toast.makeText(this, mSensor.getName() + "exist", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager = null;
        mSensor = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textViewValue.setText("Value " + String.valueOf(event.timestamp));
        tvAcc.setText("Acc " + String.valueOf(event.accuracy));
        for(int i=0; i<event.values.length; i++){
            tvTime.setText("Time " + String.valueOf(event.values[i]));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
