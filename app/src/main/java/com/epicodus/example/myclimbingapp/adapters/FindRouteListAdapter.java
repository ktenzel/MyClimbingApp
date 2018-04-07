package com.epicodus.example.myclimbingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.ui.FindRouteDetailActivity;
import com.epicodus.example.myclimbingapp.ui.FindRouteDetailFragment;

import com.epicodus.example.myclimbingapp.util.OnRouteSelectedListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindRouteListAdapter extends RecyclerView.Adapter<FindRouteListAdapter.FindRouteViewHolder> {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Route> mRoutes = new ArrayList<>();
    private Context mContext;
    private int mOrientation;
    private OnRouteSelectedListener mOnRouteSelectedListener;

    public FindRouteListAdapter(Context context, ArrayList<Route> routes, OnRouteSelectedListener routeSelectedListener) {
        mContext = context;
        mRoutes = routes;
        mOnRouteSelectedListener = routeSelectedListener;
    }

    @Override
    public FindRouteListAdapter.FindRouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_route_list_item, parent, false);
        FindRouteViewHolder viewHolder = new FindRouteViewHolder(view, mRoutes, mOnRouteSelectedListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FindRouteListAdapter.FindRouteViewHolder holder, int position) {
        holder.bindRoute(mRoutes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRoutes.size();
    }


    public class FindRouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.routeImageView)ImageView mRouteImageView;
        @BindView(R.id.routeNameTextView)TextView mNameTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;
        private int mOrientation;
        private ArrayList<Route> mRoutes = new ArrayList<>();
        private OnRouteSelectedListener mRouteSelectedListener;

        public FindRouteViewHolder(View itemView, ArrayList<Route> routes, OnRouteSelectedListener routeSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
            mOrientation = itemView.getResources().getConfiguration().orientation;
            mRoutes = routes;
            mRouteSelectedListener = routeSelectedListener;

            if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(0);
            }
            itemView.setOnClickListener(this);
        }


        public void bindRoute(Route route) {
            if (route.getImgMedium().isEmpty()) {
                mRouteImageView.setImageResource(R.drawable.hero);
            } else {
                Picasso.with(mContext)
                        .load(route.getImgMedium())
                        .into(mRouteImageView);
            }
            mNameTextView.setText(route.getName());
            mRatingTextView.setText("Rating: " + route.getRating());
        }
        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            mRouteSelectedListener.onRouteSelected(itemPosition, mRoutes, Constants.SOURCE_FIND);
            if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(itemPosition);
            } else {
            Intent intent = new Intent(mContext, FindRouteDetailActivity.class);
            intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
            intent.putExtra(Constants.EXTRA_KEY_ROUTES, Parcels.wrap(mRoutes));
            intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);
            mContext.startActivity(intent);
        }
    }
    private void createDetailFragment(int position) {
        FindRouteDetailFragment detailFragment = FindRouteDetailFragment.newInstance(mRoutes, position, Constants.SOURCE_FIND);
        android.support.v4.app.FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.routeDetailContainer, detailFragment);
        ft.commit();
        }
    }
}
