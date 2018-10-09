package com.example.oscar.project1;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements SensorEventListener{

    private ListView listView;
    private ListAdapter adapter;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private List<Sensor> mSensorList;
    private ArrayList<String> sensorNames = new ArrayList<String>();

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView)view.findViewById(R.id.lvList);
        mSensorManager = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        mSensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);


        for(int i=0; i<mSensorList.size(); i++){
            String sensor = mSensorList.get(i).getName();
            sensorNames.add(sensor);
        }



        //adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arr);
        adapter = new SensorAdapter(getActivity(),sensorNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new itemListener());
        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private class itemListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String test = String.valueOf(parent.getItemAtPosition(position));

            Toast.makeText(getContext(),test, Toast.LENGTH_LONG).show();
        }
    }
}
