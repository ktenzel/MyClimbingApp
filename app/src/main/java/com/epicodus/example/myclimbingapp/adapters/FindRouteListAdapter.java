package com.epicodus.example.myclimbingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.ui.FindRouteDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindRouteListAdapter extends RecyclerView.Adapter<FindRouteListAdapter.FindRouteViewHolder> {
    private ArrayList<Route> mRoutes = new ArrayList<>();
    private Context mContext;

    public FindRouteListAdapter(Context context, ArrayList<Route> routes) {
        mContext = context;
        mRoutes = routes;
    }

    @Override
    public FindRouteListAdapter.FindRouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_route_list_item, parent, false);
        FindRouteViewHolder viewHolder = new FindRouteViewHolder(view);
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
        @BindView(R.id.routeImageView)
        ImageView mRouteImageView;
        @BindView(R.id.routeNameTextView)
        TextView mNameTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;

        public FindRouteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindRoute(Route route) {
            Picasso.with(mContext)
                    .load(route.getImgMedium())
                    .into(mRouteImageView);
            mNameTextView.setText(route.getName());
            mRatingTextView.setText("Rating: " + route.getRating());
        }

        @Override
        public void onClick(View v) {
            Log.d("click listener", "working!");
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, FindRouteDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("routes", Parcels.wrap(mRoutes));
            mContext.startActivity(intent);
        }
    }
}
