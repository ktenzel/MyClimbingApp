package com.epicodus.example.myclimbingapp.adapters;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class FirebaseFindRouteViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    private static final int MAX_WIDTH = 800;
    private static final int MAX_HEIGHT = 600;

    View mView;
    Context mContext;
    public ImageView mRouteImageView;

    public FirebaseFindRouteViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        }

    public void bindRoute (Route route) {
        mRouteImageView = (ImageView) mView.findViewById(R.id.routeImageView);
        ImageView routeImageView = (ImageView) mView.findViewById(R.id.routeImageView);
        TextView routeNameTextView = (TextView) mView.findViewById(R.id.routeNameTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);
        if(!route.getImgMedium().contains("http")){
            try{
                Bitmap imageBitmap = decodeFromFirebaseBase64(route.getImgMedium());
                mRouteImageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{

        if (route.getImgMedium() != null) {
            Picasso.with(mContext)
                    .load(R.drawable.hero)
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(routeImageView);
        } else {
            Picasso.with(mContext)
                    .load(route.getImgMedium())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(routeImageView);
            }
            routeNameTextView.setText(route.getName());
            ratingTextView.setText("Rating: " + route.getRating());
        }

        routeNameTextView.setText(route.getName());
        ratingTextView.setText("Rating: " + route.getRating());
    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    public void onItemSelected() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
    }

    @Override
    public void onItemClear() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
    }
}
