package com.epicodus.example.myclimbingapp.models;

import org.parceler.Parcel;

@Parcel
public class LatLng {
    double lat;
    double lon;
    public LatLng() {}

    public LatLng(double lat, double lon){

        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {return lat;}
    public double getLon() {return lon;}

}
