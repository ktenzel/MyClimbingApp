package com.epicodus.example.myclimbingapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteListActivity extends AppCompatActivity {
    @BindView(R.id.newRouteTextView)
    TextView mNewRouteTextView;
    @BindView(R.id.routeListTitle)
    TextView mRouteListTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);
        ButterKnife.bind(this);
        Typeface baseFont = Typeface.createFromAsset(getAssets(), "fonts/Base02.ttf");
        mRouteListTitleTextView.setTypeface(baseFont);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String location = intent.getStringExtra("location");
        String grade = intent.getStringExtra("grade");
        mNewRouteTextView.setText(name + " at " + location + " Grade: " + grade);
    }
}
