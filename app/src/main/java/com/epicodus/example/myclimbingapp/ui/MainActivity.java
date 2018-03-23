package com.epicodus.example.myclimbingapp.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.example.myclimbingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
  @BindView(R.id.routesButton)
  Button mRoutesButton;
  @BindView(R.id.aboutButton)
  Button mAboutButton;
  @BindView(R.id.contactButton)
  Button mContactButton;
  @BindView(R.id.findRoutesButton)
  Button mFindRoutesButton;
  @BindView(R.id.appNameTextView)
  TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Typeface baseFont = Typeface.createFromAsset(getAssets(), "fonts/Base02.ttf");
        mAppNameTextView.setTypeface(baseFont);

        mRoutesButton.setOnClickListener(this);
        mAboutButton.setOnClickListener(this);
        mContactButton.setOnClickListener(this);
        mFindRoutesButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mRoutesButton) {
            Intent intent = new Intent(MainActivity.this, RoutesActivity.class);
            startActivity(intent);
        }
        if (v == mAboutButton) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        if (v == mContactButton) {
            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(intent);
        }
        if (v == mFindRoutesButton){
            Intent intent = new Intent(MainActivity.this, FindRouteActivity.class);
            startActivity(intent);
        }
    }
};