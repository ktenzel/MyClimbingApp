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

import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.models.Route;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindRouteDetailFragement extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    @BindView(R.id.routeImageView)ImageView mImageLabel;
    @BindView(R.id.routeNameTextView) TextView mNameLabel;
    @BindView(R.id.ratingTextView) TextView mRatingLabel;
    @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
    @BindView(R.id.locationTextView) TextView mLocationLabel;
    @BindView(R.id.saveRouteButton) TextView mSaveRestaurantButton;

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
        mRatingLabel.setText(mRoute.getRating());
        mLocationLabel.setText("Lat: " + Double.toString(mRoute.getLatitude()) + " Lon: " + Double.toString(mRoute.getLongitude()));

        mWebsiteLabel.setOnClickListener(this);


        mSaveRestaurantButton.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mRestaurant.getWebsite()));
            startActivity(webIntent);
        }
        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mRestaurant.getPhone()));
            startActivity(phoneIntent);
        }
        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mRestaurant.getLatitude()
                            + "," + mRestaurant.getLongitude()
                            + "?q=(" + mRestaurant.getName() + ")"));
            startActivity(mapIntent);
        }
        if (v == mSaveRestaurantButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                    .child(uid);

            DatabaseReference pushRef = restaurantRef.push();
            String pushId = pushRef.getKey();
            mRestaurant.setPushId(pushId);
            pushRef.setValue(mRestaurant);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
