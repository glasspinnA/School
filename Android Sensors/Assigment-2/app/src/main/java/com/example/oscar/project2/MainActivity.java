package com.example.oscar.project2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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


public class MainActivity extends Activity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Boolean isSensorPresent = false;
    private TextView tvPressure, tvTemp, tvHumidity, tvTimestamp;
    private final int sensorDelay = 9000000;
    private Button btnASyncTask, btnVolley;
    private String tempError = "No temp sensor available";
    private String humidityError = "No humidity sensor available";
    private float valueFromSensor;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        initComps();
        initSensors();
    }

    /**
     * Method that initialize all the UI components for the mainActivity,
     * And sends the sensors reading to the other activity classes
     */
    public void initComps() {
        tvTimestamp = (TextView)findViewById(R.id.tvTimeStamp);
        tvTemp = (TextView) findViewById(R.id.tvPressure);
        tvHumidity = (TextView) findViewById(R.id.tvHumidity);
        tvPressure = (TextView) findViewById(R.id.tvTemp);
        btnASyncTask = (Button)findViewById(R.id.btnAsyncTask);
        btnVolley = (Button)findViewById(R.id.btnVolley);
        btnASyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ASyncTaskOption.class);
                i.putExtra("PRESSURE-SENSOR",String.valueOf(valueFromSensor));
                i.putExtra("TEMP-SENSOR", tempError);
                i.putExtra("HUMIDITY-SENSOR",humidityError);
                startActivity(i);
            }
        });
        btnVolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,VolleyOption.class);
                i.putExtra("PRESSURE-SENSOR",String.valueOf(valueFromSensor));
                i.putExtra("TEMP-SENSOR", tempError);
                i.putExtra("HUMIDITY-SENSOR",humidityError);
                startActivity(i);
            }
        });
    }

    /**
     * Method that initialize the sensors that are used for the app, and the method checks
     * if the sensors exits on the device or not
     */
    public void initSensors() {
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isSensorPresent = true;
        } else {
            Toast.makeText(getApplicationContext(),"No temperature sensor available ",Toast.LENGTH_SHORT).show();
            tvTemp.setText(tempError);
            isSensorPresent = false;
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            isSensorPresent = true;
        } else {
            Toast.makeText(getApplicationContext(),"No humidity sensor available",Toast.LENGTH_SHORT).show();
            tvHumidity.setText(humidityError);
            isSensorPresent = false;
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            isSensorPresent = true;
        } else {
            Toast.makeText(getApplicationContext(), "No valueFromSensor sensor available", Toast.LENGTH_LONG).show();
            isSensorPresent = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isSensorPresent){
            mSensorManager.registerListener(this, mSensor, sensorDelay);
            Toast.makeText(getApplicationContext(), "Pressure Sensor is regristred", Toast.LENGTH_SHORT);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
        mSensorManager = null;
        mSensor = null;
    }

    /**
     * Method that reads the valueFromSensor from the sensor
     * and passes the timestamp to a textview
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        valueFromSensor = event.values[0];
        switch (event.sensor.getType()) {
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                tvTemp.setText(valueFromSensor + "C");
                break;
            case Sensor.TYPE_PRESSURE:
                tvPressure.setText("Pressure is: " + valueFromSensor + " mbar");
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                tvHumidity.setText(valueFromSensor + "%");
                break;
        }
        tvTimestamp.setText("Timestamp:" + dateFormat.format(new Date(event.timestamp / 1000000)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
