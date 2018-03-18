package com.epicodus.example.myclimbingapp;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by Kyle on 3/17/2018.
 */

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
        return String.format("%s \n %s \n %s", name, location, grades);
    }

    @Override
    public int getCount() {
        return mNames.length;
    }
}