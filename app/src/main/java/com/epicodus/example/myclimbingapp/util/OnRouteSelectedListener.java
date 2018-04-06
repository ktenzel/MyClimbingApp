package com.epicodus.example.myclimbingapp.util;


import com.epicodus.example.myclimbingapp.models.Route;

import java.util.ArrayList;

public interface OnRouteSelectedListener {
    public void onRouteSelected(Integer position, ArrayList<Route> routes, String source);

}
