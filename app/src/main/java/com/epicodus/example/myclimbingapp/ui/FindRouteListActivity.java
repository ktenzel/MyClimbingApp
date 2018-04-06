package com.epicodus.example.myclimbingapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.adapters.FindRouteListAdapter;

import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.services.RoutesService;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindRouteListActivity extends AppCompatActivity implements OnRouteSelectedListener {
    private Integer mPosition;
    ArrayList<Route> mRoutes;
    String mSource;
    String stringLatLng;


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
                intent.putExtra(Constants.EXTRA_KEY_POSITION.mPosition);
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
    public void onRestaurantSelected(Integer position, ArrayList<Route> routes, String source) {
        mPosition = position;
        mRoutes = routes;
        mSource = source;
    }
}