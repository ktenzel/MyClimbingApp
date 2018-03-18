package com.epicodus.example.myclimbingapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteListActivity extends AppCompatActivity {
    @BindView(R.id.newRouteTextView)
    TextView mNewRouteTextView;
    @BindView(R.id.routeListTitle)
    TextView mRouteListTitleTextView;
    @BindView(R.id.listView)
    ListView mListView;

    private String[] name = new String[] {"The Peanut", "Rope de Dope", "Morning Glory", "Road Kill"};
    private String[] location = new String[] {"Smith Rock", "Smith Rock", "Smith Rock", "French Dome"};
    private String[] grade = new String[] {"5.8", "5.8", "5.8", "5.12a"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);
        ButterKnife.bind(this);

        mListView = (ListView) findViewById(R.id.listView);
        mNewRouteTextView = (TextView) findViewById(R.id.newRouteTextView);

        MyRoutesArrayAdapter adapter = new MyRoutesArrayAdapter(this, android.R.layout.simple_list_item_1, name, location, grade); //must match constructor!


        Typeface baseFont = Typeface.createFromAsset(getAssets(), "fonts/Base02.ttf");
        mRouteListTitleTextView.setTypeface(baseFont);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String location = intent.getStringExtra("location");
        String grade = intent.getStringExtra("grade");
        mNewRouteTextView.setText(name + " at " + location + " Grade: " + grade);
    }
}
