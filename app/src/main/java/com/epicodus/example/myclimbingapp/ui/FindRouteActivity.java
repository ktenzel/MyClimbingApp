package com.epicodus.example.myclimbingapp.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.example.myclimbingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindRouteActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.findRoutesButton)
    Button mFindRouteButton;

    @BindView(R.id.locationEditText)
    EditText mLocationEditText;

//    @BindView(R.id.longitudeEditText)
//    EditText mLongitudeEditText;
//
//    @BindView(R.id.latitudeEditText)
//    EditText mLatitudeEditText;

    @BindView(R.id.appFindRoutesTitle)
    TextView mAppFindRoutesTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_route);
        ButterKnife.bind(this);
        Typeface baseFont = Typeface.createFromAsset(getAssets(), "fonts/Base02.ttf");
        mAppFindRoutesTitle.setTypeface(baseFont);

        mFindRouteButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if (v == mFindRouteButton) {
//            String longitude = mLongitudeEditText.getText().toString();
//            String latitude = mLatitudeEditText.getText().toString();
            String location = mLocationEditText.getText().toString();

//            if(longitude.length() <= 1 || latitude.length() <= 1) {
                if(location.length() <= 1){
                Toast.makeText(FindRouteActivity.this, "Please fill out all fields", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(FindRouteActivity.this, FindRouteListActivity.class);
//                intent.putExtra("longitude", longitude);
//                intent.putExtra("latitude", latitude);
                    intent.putExtra("location", location);
                startActivity(intent);
            }
        }
    }
}