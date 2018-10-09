package com.example.oscar.projekt1;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Oscar on 2017-01-25.
 * Adapter for the listview that populates the listview with which sensors
 * that exits on the device
 */

public class SensorAdapter extends ArrayAdapter<Sensor> {
    private LayoutInflater inflater = null;
    private List<Sensor> items;

    public SensorAdapter(Context context, List<Sensor> items) {
        super(context, R.layout.custom_adapter_row, items);
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.custom_adapter_row, null);
        }
        TextView title = (TextView)view.findViewById(R.id.tvPower);
        title.setText(items.get(position).getName());
        return view;
    }



}
