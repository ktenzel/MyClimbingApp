package com.epicodus.example.myclimbingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.example.myclimbingapp.models.LatLng;
import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.services.GoogleService;
import com.epicodus.example.myclimbingapp.services.MountainService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindRouteListActivity extends AppCompatActivity {
    //    @BindView(R.id.recyclerView)
//    RecyclerView mRecyclerView;
    String stringLatLng;
    public ArrayList<Route> routes = new ArrayList<>();
//    public ArrayList<LatLng> latslngs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        getLatLon(location);
    }

    private void getLatLon(String location){
        final GoogleService googleService = new GoogleService();
        final MountainService mountainService = new MountainService();
        googleService.findLatLng(location, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {e.printStackTrace();}

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                latslngs = googleService.processResults(response);

                stringLatLng = googleService.processResults(response);
                routes = mountainService.processResults(stringLatLng);
//                FindRouteListActivity.this.runOnUiThread(new Runnable() {

//                    @Override
//                    public void run() {
//                        mAdapter = new FindRouteListAdapter(getApplicationContext(), lonlat);
//                        mRecyclerView.LayoutMnanager layoutMnanager = new LinearLayoutManager(RestaurantsListActivity.this);
//                        mRecyclerView.setLayoutManager(layoutManager);
//                        mRecyclerView.setHasFixedSize(true);

//                    }
//                });
            }
        });
    }
}