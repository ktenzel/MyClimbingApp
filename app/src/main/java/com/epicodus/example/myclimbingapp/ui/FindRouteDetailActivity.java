package com.epicodus.example.myclimbingapp.ui;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.adapters.FindRoutePagerAdapter;
import com.epicodus.example.myclimbingapp.models.Route;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindRouteDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager) ViewPager mViewPager;
    private FindRoutePagerAdapter adapterViewPager;
    private String mSource;
    ArrayList<Route> mRoutes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_route_detail);
        ButterKnife.bind(this);

        mRoutes = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_ROUTES));
        int startingPosition = getIntent().getIntExtra(Constants.EXTRA_KEY_POSITION, 0);
        mSource = getIntent().getStringExtra(Constants.KEY_SOURCE);
        adapterViewPager = new FindRoutePagerAdapter(getSupportFragmentManager(), mRoutes, mSource);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}