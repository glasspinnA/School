package com.example.oscar.project1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Oscar on 2017-01-25.
 */

public class SensorAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater = null;


    public SensorAdapter(Context context, ArrayList<String> test) {
        super(context, R.layout.custom_row, test);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if (view == null)
            view = inflater.inflate(R.layout.item_layout, null);
        TextView title = (TextView)vi.findViewById(R.id.tvGroup);
        return view;
    }



}
