package com.epicodus.example.myclimbingapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.ui.FindRouteDetailActivity;
import com.epicodus.example.myclimbingapp.ui.FindRouteDetailFragment;
import com.epicodus.example.myclimbingapp.util.ItemTouchHelperAdapter;
import com.epicodus.example.myclimbingapp.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseFindRouteListAdapter extends FirebaseRecyclerAdapter<Route, FirebaseFindRouteViewHolder> implements ItemTouchHelperAdapter {
    private ChildEventListener mChildEventListener;
    private ArrayList<Route> mRoutes = new ArrayList<>();
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private int mOrientation;

    public FirebaseFindRouteListAdapter(Class<Route> modelClass, int modelLayout, Class<FirebaseFindRouteViewHolder> viewHolderClass, Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mRoutes.add(dataSnapshot.getValue(Route.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseFindRouteViewHolder viewHolder, Route model, int position) {
        viewHolder.bindRoute(model);
        mOrientation = viewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }

        viewHolder.mRouteImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }

        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = viewHolder.getAdapterPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, FindRouteDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                    intent.putExtra(Constants.EXTRA_KEY_ROUTES, Parcels.wrap(mRoutes));
                    intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_SAVED);
                    mContext.startActivity(intent);
                }
            }
        });
    }
    private void createDetailFragment(int position) {
        FindRouteDetailFragment detailFragment = FindRouteDetailFragment.newInstance(mRoutes, position, Constants.SOURCE_SAVED);
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.routeDetailContainer, detailFragment);
        ft.commit();
    }
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mRoutes, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }
    @Override
    public void onItemDismiss(int position) {
        mRoutes.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Route route : mRoutes) {
            int index = mRoutes.indexOf(route);
            DatabaseReference ref = getRef(index);
            route.setIndex(Integer.toString(index));
            ref.setValue(route);
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }
}
