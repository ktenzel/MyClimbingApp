package com.epicodus.example.myclimbingapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.models.Users;
import com.epicodus.example.myclimbingapp.ui.SendMessageActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private ArrayList<Users> mUsers = new ArrayList<>();
    private Context mContext;

public UserListAdapter(Context context, ArrayList<Users> users) {
        mContext = context;
        mUsers = users;
        }

@Override
public UserListAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
        }
    @Override
    public void onBindViewHolder(UserListAdapter.UserViewHolder holder, int position) {
        holder.bindUser(mUsers.get(position));
    }
    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.userNameTextView)
        TextView mUserNameTextView;
        @BindView(R.id.userImageView)
        ImageView mUserImageTextView;

        private Context mContext;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindUser(Users user) {
            mUserNameTextView.setText(user.getUserName());
            Picasso.with(mContext)
                    .load(user.getProfilePic())
                    .into(mUserImageTextView);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, SendMessageActivity.class);
            intent.putExtra("position", itemPosition + "");
            intent.putExtra("messages", Parcels.wrap(mUsers));
            mContext.startActivity(intent);
        }
    }
}
