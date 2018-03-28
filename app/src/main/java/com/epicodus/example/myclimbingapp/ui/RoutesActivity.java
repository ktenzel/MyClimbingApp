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

public class RoutesActivity extends AppCompatActivity implements View.OnClickListener{
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
        mAddRoutesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mAddRoutesButton){
            String name = mRouteNameEditText.getText().toString();
            String location = mRouteLocationEditText.getText().toString();
            String grade = mRouteGradeEditText.getText().toString();
            if(name.length() < 1 || location.length() < 1 || grade.length() < 1){
                Toast.makeText(RoutesActivity.this, "Please fill out all fields", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(RoutesActivity.this, RouteListActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("location", location);
                intent.putExtra("grade", grade);
                startActivity(intent);
            }
        }
    }
}
