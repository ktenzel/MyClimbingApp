package com.epicodus.example.myclimbingapp.ui;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.adapters.FindRouteListAdapter;
import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.services.RoutesService;
import com.epicodus.example.myclimbingapp.util.OnRouteSelectedListener;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindRouteListFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private FindRouteListAdapter mAdapter;
    public ArrayList<Route> mRoutes = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;
    private OnRouteSelectedListener mOnRouteSelectedListener;
    String stringLatLng;

    public FindRouteListFragment(){}

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            mOnRouteSelectedListener = (OnRouteSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    View view = inflater.inflate(R.layout.fragment_route_list, container, false);
    ButterKnife.bind(this, view);
    mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
    if (mRecentAddress != null) {
        getLatLon(mRecentAddress);
    }
        return view;
}
    public void getLatLon(String location){
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
                        mRoutes = routesService.processMountainResults(response);
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mAdapter = new FindRouteListAdapter(getActivity(), mRoutes, mOnRouteSelectedListener);
                                mRecyclerView.setAdapter(mAdapter);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                mRecyclerView.setLayoutManager(layoutManager);
                                mRecyclerView.setHasFixedSize(true);

                            }
                        });

                    }
                });
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getLatLon(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void addToSharedPreferences(String location) {
        Log.d(location, "location ");
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }
}
