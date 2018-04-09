package com.epicodus.example.myclimbingapp.ui;

import android.content.Intent;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.R;


import com.epicodus.example.myclimbingapp.models.Route;

import com.epicodus.example.myclimbingapp.util.OnRouteSelectedListener;

import org.parceler.Parcels;


import java.util.ArrayList;

public class FindRouteListActivity extends AppCompatActivity implements OnRouteSelectedListener {
    private Integer mPosition;
    ArrayList<Route> mRoutes;
    String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_route);

    if(savedInstanceState != null){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
            mRoutes = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_ROUTES));
            mSource = savedInstanceState.getString(Constants.KEY_SOURCE);

            if(mPosition != null && mRoutes != null) {
                Intent intent = new Intent(this, FindRouteDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                intent.putExtra(Constants.EXTRA_KEY_ROUTES, Parcels.wrap(mRoutes));
                intent.putExtra(Constants.KEY_SOURCE, mSource);
                startActivity(intent);
                }
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mPosition != null && mRoutes != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_ROUTES, Parcels.wrap(mRoutes));
            outState.putString(Constants.KEY_SOURCE, mSource);
        }
    }
    @Override
    public void onRouteSelected(Integer position, ArrayList<Route> routes, String source) {
        mPosition = position;
        mRoutes = routes;
        mSource = source;
    }
}