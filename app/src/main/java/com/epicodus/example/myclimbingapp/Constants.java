package com.epicodus.example.myclimbingapp;



public class Constants {
    public static final String GOOGLE_TOKEN = BuildConfig.GOOGLE_TOKEN;
    public static final String GOOGLE_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    public static final String MOUNTAIN_END = "&maxDistance=10&minDiff=5.6&maxDiff=5.10";

    public static final String MOUNTAIN_TOKEN = BuildConfig.MOUNTAIN_TOKEN;
    public static final String MOUNTAIN_BASE_URL = "https://www.mountainproject.com/data/get-routes-for-lat-lon?";

    //issue could be here\/
    public static final String FIREBASE_CHILD_ROUTES = "routes";

    public static final String PREFERENCES_LOCATION_KEY = "location";


}
