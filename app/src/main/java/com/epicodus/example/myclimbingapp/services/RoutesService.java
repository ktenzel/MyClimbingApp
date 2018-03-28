package com.epicodus.example.myclimbingapp.services;


import android.util.Log;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.models.Route;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.String.valueOf;

public class RoutesService {

    public String stringLatLng;
    public static void findLatLng(String location, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GOOGLE_BASE_URL + location).newBuilder();
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .header("Authorization", Constants.GOOGLE_TOKEN)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        Log.d(url, "url");
    }

    public static void findRoutes(String stringLatLng, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.MOUNTAIN_BASE_URL + stringLatLng + "&maxDistance=10&key=200235927-f6767fd8062816c821012067218f5bab").newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Constants.MOUNTAIN_TOKEN)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        Log.d(url, "url");
    }

    public String processGoogleResults(Response response){

        try{
            String jsonData = response.body().string();
            JSONObject googleJSON = new JSONObject(jsonData);
            JSONArray locationJSON = googleJSON.getJSONArray("results");
            for (int i = 0; i < locationJSON.length(); i++) {
                JSONObject latlngJSON = locationJSON.getJSONObject(i);
                double latitude = (double) latlngJSON.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                double longitude = (double) latlngJSON.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                String stringLatitude = valueOf(latitude);
                String stringLongitude = valueOf(longitude);

                Log.d(stringLatitude, "latitude");
                Log.d(stringLongitude, "longitude");
                String stringLatLng = "lat=" + stringLatitude + "&lon=" + stringLongitude;
                Log.d(stringLatLng, "stringLatLng: ");


            }

        }catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return stringLatLng;
    }
    public ArrayList<Route> processMountainResults(Response response){
        ArrayList<Route> routes = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            JSONObject mountainJSON = new JSONObject(jsonData);
            JSONArray routesJSON = mountainJSON.getJSONArray("routes");
            for (int i = 0; i < routesJSON.length(); i++) {
                JSONObject listJSON = routesJSON.getJSONObject(i);
                String name = listJSON.getString("name");
                String imgMedium = listJSON.getString("imgMedium");
                String rating = listJSON.getString("rating");
                double latitude = (double) listJSON.getDouble("latitude");
                double longitude = (double) listJSON.getDouble("longitude");
                String stringLatitude = valueOf(latitude);
                String stringLongitude = valueOf(longitude);
                Log.d(stringLatitude, "latitude");

                Log.d(stringLongitude, "longitude");


                Route route = new Route(name, imgMedium, rating, latitude, longitude);
                routes.add(route);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return routes;
    }
}

