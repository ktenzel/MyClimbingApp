package com.epicodus.example.myclimbingapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.ui.FindRouteDetailFragment;

import java.util.ArrayList;

public class FindRoutePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Route> mRoutes;
    String mSource;

    public FindRoutePagerAdapter(FragmentManager fm, ArrayList<Route> routes, String source) {
        super(fm);
        mRoutes = routes;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return FindRouteDetailFragment.newInstance(mRoutes,position,mSource);
    }

    @Override
    public int getCount() {
        return mRoutes.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRoutes.get(position).getName();
    }
}
