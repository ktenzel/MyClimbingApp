package com.epicodus.example.myclimbingapp.services;
import android.util.Log;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.models.Route;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.epicodus.example.myclimbingapp.services.GoogleService;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.epicodus.example.myclimbingapp.Constants.MOUNTAIN_END;
import static java.lang.String.valueOf;

public class MountainService {

    public static void findRoute(String stringLatLng , Callback callback){

//        final GoogleService googleService = new GoogleService();

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
    public ArrayList<Route> processResults(Response response){
        ArrayList<Route> routes = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            JSONObject mountainJSON = new JSONObject(jsonData);
            JSONArray routesJSON = mountainJSON.getJSONArray("routes");
            for (int i = 0; i < routesJSON.length(); i++) {
                JSONObject listJSON = routesJSON.getJSONObject(i);
                String name = listJSON.getString("name");
                String type = listJSON.getString("type");
                String imgMedium = listJSON.getString("imgMedium");
                String url = listJSON.getString("url");
                String rating = listJSON.getString("rating");
                double latitude = (double) listJSON.getDouble("latitude");
                double longitude = (double) listJSON.getDouble("longitude");
                String stringLatitude = valueOf(latitude);
                String stringLongitude = valueOf(longitude);
                Log.d(stringLatitude, "latitude");

                Log.d(stringLongitude, "longitude");


                Route route = new Route(name, type, imgMedium, url, rating, latitude, longitude);
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
