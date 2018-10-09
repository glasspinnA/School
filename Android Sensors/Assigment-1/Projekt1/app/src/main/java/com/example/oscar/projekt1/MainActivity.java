package com.example.oscar.projekt1;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private List<Sensor> mSensorsList;
    private ListView sensorListView;
    private ArrayList<String> sensorNames =  new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorListView = (ListView)findViewById(R.id.sensorList);
        sensorListView.setOnItemClickListener(new ItemListener());
        mSensorManager = (SensorManager)getSystemService(this.SENSOR_SERVICE);

        //Gets all the sensors
        mSensorsList = mSensorManager.getSensorList(Sensor.TYPE_ALL);


        for(int i=0; i< mSensorsList.size(); i++) {
            String sensor= mSensorsList.get(i).getName();
            sensorNames.add(sensor);
        }

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sensorNames);
        sensorListView.setAdapter(new SensorAdapter(this,mSensorsList));

    }


    /**
     * A listener class, that listen to which row in the listview that is clicked.
     * Once a certain row in the listview it sends all the information about the clicked sensor
     * to SensorDetailActivity
     */
    private class ItemListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent sensorDetails = new Intent(MainActivity.this, SensorDetailActivity.class);
            sensorDetails.putExtra("SENSOR_NAME", mSensorsList.get(position).getName()); //pass sensor name to detail activity
            sensorDetails.putExtra("SENSOR_VENDOR", mSensorsList.get(position).getVendor());
            sensorDetails.putExtra("SENSOR_VERSION", String.valueOf(mSensorsList.get(position).getVersion()));
            sensorDetails.putExtra("SENSOR_TYPE", mSensorsList.get(position).getType());
            sensorDetails.putExtra("SENSOR_MAXRANGE", String.valueOf(mSensorsList.get(position).getType()));
            sensorDetails.putExtra("SENSOR_RES", String.valueOf(mSensorsList.get(position).getResolution()));
            sensorDetails.putExtra("SENSOR_POWER", String.valueOf(mSensorsList.get(position).getPower()));
            sensorDetails.putExtra("SENSOR_DELAY", String.valueOf(mSensorsList.get(position).getMinDelay()));
            startActivity(sensorDetails);
        }
    }
}
