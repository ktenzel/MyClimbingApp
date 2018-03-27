//package com.epicodus.example.myclimbingapp.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.epicodus.example.myclimbingapp.R;
//import com.epicodus.example.myclimbingapp.models.Route;
//
//import org.parceler.Parcels;
//
//import java.util.ArrayList;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class FindRouteListAdapter extends RecyclerView.Adapter<FindRouteListAdapter.RestaurantViewHolder> {
//    private ArrayList<Route> mRoutes = new ArrayList<>();
//    private Context mContext;
//
//    public FindRouteListAdapter(Context context, ArrayList<Route> restaurants) {
//        mContext = context;
//        mRoutes = routes;
//    }
//
//    @Override
//    public FindRouteListAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
//        RestaurantViewHolder viewHolder = new RestaurantViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(FindRouteListAdapter.RestaurantViewHolder holder, int position) {
//        holder.bindRestaurant(mRoutes.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return mRoutes.size();
//    }
//
//
//    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        @BindView(R.id.restaurantImageView)
//        ImageView mRestaurantImageView;
//        @BindView(R.id.restaurantNameTextView)
//        TextView mNameTextView;
//        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
//        @BindView(R.id.ratingTextView) TextView mRatingTextView;
//
//        private Context mContext;
//
//        public RestaurantViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//
//            mContext = itemView.getContext();
//            itemView.setOnClickListener(this);
//        }
//
//        public void bindRestaurant(Route route) {
//            Picasso.with(mContext).load(route.getImageUrl()).into(mRestaurantImageView);
//            mNameTextView.setText(route.getName());
//            mCategoryTextView.setText(route.getCategories().get(0));
//            mRatingTextView.setText("Rating: " + route.getRating() + "/5");
//        }
//
//        @Override
//        public void onClick(View v) {
//            Log.d("click listener", "working!");
//            int itemPosition = getLayoutPosition();
//            Intent intent = new Intent(mContext, FindRoutesDetailActivity.class);
//            intent.putExtra("position", itemPosition + "");
//            intent.putExtra("routes", Parcels.wrap(mRoutes));
//            mContext.startActivity(intent);
//        }
//    }
//}
