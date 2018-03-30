package com.epicodus.example.myclimbingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.support.v7.widget.LinearLayoutManager;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.adapters.FindRouteListAdapter;
import com.epicodus.example.myclimbingapp.models.LatLng;
import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.services.GoogleService;
import com.epicodus.example.myclimbingapp.services.MountainService;
import com.epicodus.example.myclimbingapp.services.RoutesService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindRouteListActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    String stringLatLng;
    public ArrayList<Route> routes = new ArrayList<>();
    private FindRouteListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        getLatLon(location);
    }

    private void getLatLon(String location){
        final RoutesService routesService = new RoutesService();
        routesService.findLatLng(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {e.printStackTrace();}

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                stringLatLng = routesService.processGoogleResults(response);

                routesService.findRoutes(stringLatLng, new Callback(){

                    @Override
                    public void onFailure(Call call, IOException e) {e.printStackTrace();}

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        routes = routesService.processMountainResults(response);
                    }
                });
                FindRouteListActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new FindRouteListAdapter(getApplicationContext(), routes);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.LayoutManager layoutMnanager = new LinearLayoutManager(FindRouteListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                    }
                });
            }
        });
    }
    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }
}