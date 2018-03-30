package com.epicodus.example.myclimbingapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.ui.FindRouteDetailActivity;
import com.epicodus.example.myclimbingapp.ui.FindRouteDetailFragement;

import java.util.ArrayList;

public class FindRoutePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Route> mRoutes;

    public FindRoutePagerAdapter(FragmentManager fm, ArrayList<Route> routes) {
        super(fm);
        mRoutes = routes;
    }

    @Override
    public Fragment getItem(int position) {
        return FindRouteDetailFragement.newInstance(mRoutes.get(position));
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
