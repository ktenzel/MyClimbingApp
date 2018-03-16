package com.epicodus.example.myclimbingapp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.appNameTextView)
    TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface ostrichFont = Typeface.createFromAsset(getAssets(), "fonts/Base02.ttf");
        mAppNameTextView.setTypeface(ostrichFont);
    }
}
