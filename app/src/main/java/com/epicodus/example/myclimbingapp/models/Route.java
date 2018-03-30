package com.epicodus.example.myclimbingapp.models;

import org.parceler.Parcel;

@Parcel
public class Route {
    String name;
    String type;
    String imgMedium;
    String rating;
    String url;
    double latitude;
    double longitude;
    private String pushId;


    public Route() {}

    public Route(String name, String type, String imgMedium, String rating, String url, double latitude, double longitude){
        this.name = name;
        this.type = type;
        this.imgMedium = imgMedium;
        this.rating = rating;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getImgMedium() { return imgMedium; }
    public String getRating() { return rating; }
    public String getUrl() { return url; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
