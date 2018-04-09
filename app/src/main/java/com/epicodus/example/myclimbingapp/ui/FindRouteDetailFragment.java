package com.epicodus.example.myclimbingapp.ui;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindRouteDetailFragment extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 800;
    private static final int MAX_HEIGHT = 600;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    @BindView(R.id.routeImageView)ImageView mImageLabel;
    @BindView(R.id.routeNameTextView) TextView mNameLabel;
    @BindView(R.id.routeTypeTextView) TextView mTypeLabel;
    @BindView(R.id.routeRatingTextView) TextView mRatingLabel;
    @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
    @BindView(R.id.locationTextView) TextView mLocationLabel;
    @BindView(R.id.saveRouteButton) TextView mSaveRouteButton;

    private Route mRoute;
    private ArrayList<Route> mRoutes;
    private int mPosition;
    private String mSource;


    public static FindRouteDetailFragment newInstance(ArrayList<Route> routes, Integer position, String source) {
        FindRouteDetailFragment findRouteDetailFragment = new FindRouteDetailFragment();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_SOURCE, source);
        args.putParcelable(Constants.EXTRA_KEY_ROUTES, Parcels.wrap(routes));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);

        findRouteDetailFragment.setArguments(args);
        return findRouteDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoutes = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_ROUTES));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mRoute = mRoutes.get(mPosition);
        mSource = getArguments().getString((Constants.KEY_SOURCE));
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_route_detail, container, false);
        ButterKnife.bind(this, view);
            if(!mRoute.getImgMedium().contains("http")){
                try{
                    Bitmap image = decodeFromFirebaseBase64(mRoute.getImgMedium());
                    mImageLabel.setImageBitmap(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (mRoute.getImgMedium().isEmpty()) {
                    Picasso.with(view.getContext())
                            .load(R.drawable.hero)
                            .resize(MAX_WIDTH, MAX_HEIGHT)
                            .centerCrop()
                            .into(mImageLabel);
                } else {
                    Picasso.with(view.getContext())
                            .load(mRoute.getImgMedium())
                            .resize(MAX_WIDTH, MAX_HEIGHT)
                            .centerCrop()
                            .into(mImageLabel);
                }
            }
            setHasOptionsMenu(true);
        mNameLabel.setText(mRoute.getName());
        mTypeLabel.setText(mRoute.getType());
        mRatingLabel.setText(mRoute.getRating());
        mLocationLabel.setText("Lat: " + Double.toString(mRoute.getLatitude()) + " Lon: " + Double.toString(mRoute.getLongitude()));
        mWebsiteLabel.setOnClickListener(this);
        mLocationLabel.setOnClickListener(this);
        mSaveRouteButton.setOnClickListener(this);
        if (mSource.equals(Constants.SOURCE_SAVED)){
            mSaveRouteButton.setVisibility(View.GONE);
        } else {
            mSaveRouteButton.setOnClickListener(this);
        }

        return view;
    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            inflater.inflate(R.menu.menu_photo, menu);
        } else {
            inflater.inflate(R.menu.menu_main, menu);
        }
    }
    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
                onLaunchCamera();
            default:
                break;
        }
        return false;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageLabel.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }
    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_ROUTES)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mRoute.getPushId())
                .child("imageUrl");
        ref.setValue(imageEncoded);
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
            Log.d(mapIntent.toString(), "mapintent");
        }
        if (v == mSaveRouteButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference routeRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_ROUTES)
                    .child(uid);

            DatabaseReference pushRef = routeRef.push();
            String pushId = pushRef.getKey();
            mRoute.setPushId(pushId);
            pushRef.setValue(mRoute);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
