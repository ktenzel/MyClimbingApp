package com.epicodus.example.myclimbingapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.ui.FindRouteDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseFindRouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseFindRouteViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }
    public void bindRoute (Route route) {
        ImageView routeImageView = (ImageView) mView.findViewById(R.id.routeImageView);
        TextView routeNameTextView = (TextView) mView.findViewById(R.id.routeNameTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);

//        Picasso.with(mContext)
//                .load(route.getImgMedium())
//                .resize(MAX_WIDTH, MAX_HEIGHT)
//                .centerCrop()
//                .into(routeImageView);

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
    @Override
    public void onClick(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        final ArrayList<Route> routes = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_ROUTES).child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    routes.add(snapshot.getValue(Route.class));
                }
                int itemPosition = getLayoutPosition();


                Intent intent = new Intent(mContext, FindRouteDetailActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("routes", Parcels.wrap(routes));

                mContext.startActivity(intent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
