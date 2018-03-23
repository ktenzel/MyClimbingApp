package com.epicodus.example.myclimbingapp.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;


public class MyRoutesArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mNames;
    private String[] mLocations;
    private String[] mGrades;

    public MyRoutesArrayAdapter(Context mContext, int resource, String[] mNames, String[] mLocations, String[] mGrades) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mNames = mNames;
        this.mLocations = mLocations;
        this.mGrades = mGrades;
    }

    @Override
    public Object getItem(int position) {
        String name = mNames[position];
        String location = mLocations[position];
        String grades = mGrades[position];
        return String.format("Route name: %s \n Location: %s \n Grade: %s", name, location, grades);
    }

    @Override
    public int getCount() {
        return mNames.length;
    }
}