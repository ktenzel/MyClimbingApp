package com.epicodus.example.myclimbingapp.models;

import org.parceler.Parcel;

@Parcel
public class Route {
    String name;
    String imgMedium;
    String rating;
    double latitude;
    double longitude;

    public Route() {}

    public Route(String name, String imgMedium, String rating, double latitude, double longitude){
        this.name = name;
        this.imgMedium = imgMedium;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() { return name; }
    public String getImgMedium() { return imgMedium; }
    public String getRating() { return rating; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
