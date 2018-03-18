package com.epicodus.example.myclimbingapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutesActivity extends AppCompatActivity {
    @BindView(R.id.appMyRoutesTitle)
    TextView mAppMyRoutesTitle;
    @BindView(R.id.addRoutesButton)
    Button mAddRoutesButton;
    @BindView(R.id.routeNameEditText)
    EditText mRouteNameEditText;
    @BindView(R.id.routeLocationEditText)
    EditText mRouteLocationEditText;
    @BindView(R.id.routeGradeEditText)
    EditText mRouteGradeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        ButterKnife.bind(this);
        Typeface baseFont = Typeface.createFromAsset(getAssets(), "fonts/Base02.ttf");
        mAppMyRoutesTitle.setTypeface(baseFont);
    }

    @Override
    public void onClick(View v){
        if(v == mAddRoutesButton){
            String name = mRouteNameEditText.getText().toString();
            String location = mRouteLocationEditText.getText().toString();
            String grade = mRouteGradeEditText.getText().toString();

            Intent intent = new Intent(RoutesActivity.this, RestaurantsActivity.class);
            intent.putExtra("location", location);
            startActivity(intent);
        }
    }
}
