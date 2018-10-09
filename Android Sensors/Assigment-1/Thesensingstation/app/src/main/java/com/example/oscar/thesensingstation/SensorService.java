package com.example.oscar.thesensingstation;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Oscar on 2017-01-23.
 */

/**
 * Man använder en Service klass för att inte avbryta UI tråden när man läser
 * kostar mycket tid
 */
public class SensorService extends Service implements SensorEventListener {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
