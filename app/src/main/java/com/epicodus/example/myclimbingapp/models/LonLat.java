package com.epicodus.example.myclimbingapp.models;

import org.parceler.Parcel;

@Parcel
public class LonLat {
    double lat;
    double lon;

    public LonLat() {}

    public LatLon(double lat, double lon){

        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {return lat;}
    public double getLon() {return lon;}
}
