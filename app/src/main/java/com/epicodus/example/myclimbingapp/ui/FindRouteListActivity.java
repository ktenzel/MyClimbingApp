package com.epicodus.example.myclimbingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.services.GoogleService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindRouteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_route_list);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        getLatLon(location);
    }

    private void getLatLon(String location){
        final GoogleService googleService = new GoogleService();
        googleService.findLatLon(location, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                lonlat = googleService.processResults(response);
                FindRouteListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new FindRouteListAdapter(getApplicationContext(), lonlat);
                        mRecyclerView.LayoutMnanager layoutMnanager = new LinearLayoutManager(RestaurantsListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                    }
                });
            }
        });
    }
}
