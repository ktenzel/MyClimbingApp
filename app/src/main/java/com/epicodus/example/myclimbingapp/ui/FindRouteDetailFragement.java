package com.epicodus.example.myclimbingapp.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.models.Route;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindRouteDetailFragement extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    //image
    @BindView(R.id.routeImageView)ImageView mImageLabel;
    //name
    @BindView(R.id.routeNameTextView) TextView mNameLabel;
    //type
    @BindView(R.id.routeTypeTextView) TextView mTypeLabel;
    //rating
    @BindView(R.id.routeRatingTextView) TextView mRatingLabel;
    //url
    @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
    //latitude, longitude
    @BindView(R.id.locationTextView) TextView mLocationLabel;
    @BindView(R.id.saveRouteButton) TextView mSaveRouteButton;

    private Route mRoute;

    public static FindRouteDetailFragement newInstance(Route route) {
        FindRouteDetailFragement findRouteDetailFragement = new FindRouteDetailFragement();
        Bundle args = new Bundle();
        args.putParcelable("route", Parcels.wrap(route));
        findRouteDetailFragement.setArguments(args);
        return findRouteDetailFragement;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoute = Parcels.unwrap(getArguments().getParcelable("route"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_route_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext())
                .load(mRoute.getImgMedium())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageLabel);

        mNameLabel.setText(mRoute.getName());
        mTypeLabel.setText(mRoute.getType());
        mRatingLabel.setText(mRoute.getRating());
        mLocationLabel.setText("Lat: " + Double.toString(mRoute.getLatitude()) + " Lon: " + Double.toString(mRoute.getLongitude()));

        mWebsiteLabel.setOnClickListener(this);


        mSaveRouteButton.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mRoute.getUrl()));
            startActivity(webIntent);
        }
        if (v == mLocationLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mRoute.getLatitude()
                            + "," + mRoute.getLongitude()
                            + "?q=(" + mRoute.getName() + ")"));
            startActivity(mapIntent);
        }
        if (v == mSaveRouteButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_ROUTES)
                    .child(uid);

            DatabaseReference pushRef = restaurantRef.push();
            String pushId = pushRef.getKey();
            mRoute.setPushId(pushId);
            pushRef.setValue(mRoute);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
