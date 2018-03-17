package com.epicodus.example.myclimbingapp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {
    @BindView(R.id.appAboutTextView)
    TextView mAppAboutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Typeface baseFont = Typeface.createFromAsset(getAssets(), "fonts/Base02.ttf");
        mAppAboutTextView.setTypeface(baseFont);

    }
}