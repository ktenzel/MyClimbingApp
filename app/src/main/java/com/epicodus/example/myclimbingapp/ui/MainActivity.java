package com.epicodus.example.myclimbingapp.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.example.myclimbingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.routesButton) Button mRoutesButton;
//    @BindView(R.id.aboutButton) Button mAboutButton;
//    @BindView(R.id.contactButton) Button mContactButton;
    @BindView(R.id.findRoutesButton) Button mFindRoutesButton;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface baseFont = Typeface.createFromAsset(getAssets(), "fonts/Base02.ttf");
        mAppNameTextView.setTypeface(baseFont);

        mRoutesButton.setOnClickListener(this);
//        mAboutButton.setOnClickListener(this);
//        mContactButton.setOnClickListener(this);
        mFindRoutesButton.setOnClickListener(this);

        //verify authentication
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        } if (id == R.id.about_menu_Button){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        } if (id == R.id.contact_menu_Button){
            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == mRoutesButton) {
            Intent intent = new Intent(MainActivity.this, SavedRoutesListActivity.class);
            startActivity(intent);
        }
//        if (v == mAboutButton) {
//            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
//            startActivity(intent);
//        }
//        if (v == mContactButton) {
//            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
//            startActivity(intent);
//        }
        if (v == mFindRoutesButton){
            Intent intent = new Intent(MainActivity.this, FindRouteListActivity.class);
            startActivity(intent);
        }
    }
}