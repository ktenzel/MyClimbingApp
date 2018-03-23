package com.epicodus.example.myclimbingapp.services;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.models.LatLng;

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


public class GoogleService {

    public static void findLatLng(String location, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GOOGLE_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.GOOGLE_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .header("Authorization", Constants.GOOGLE_TOKEN)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<LatLng> processResults(Response response) {
        ArrayList<LatLng> latslngs = new ArrayList<>();

        try{
            String jsonData = response.body().string();
            JSONObject googleJSON = new JSONObject(jsonData);
            JSONArray locationJSON = googleJSON.getJSONArray("results");
            for (int i = 0; i < locationJSON.length(); i++) {
                JSONObject latlngJSON = locationJSON.getJSONObject(i);
                double latitude = (double) latlngJSON.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                double longitude = (double) latlngJSON.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                LatLng latlng = new LatLng(latitude, longitude);
                latslngs.add(latlng);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return latslngs;
    }
}
