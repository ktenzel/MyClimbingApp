package com.epicodus.example.myclimbingapp.services;

import com.epicodus.example.myclimbingapp.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class GoogleService {
    public static void findLatLon(String location, Callback callback) {
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
}
