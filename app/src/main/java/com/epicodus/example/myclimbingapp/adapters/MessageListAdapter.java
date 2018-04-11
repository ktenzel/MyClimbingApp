package com.epicodus.example.myclimbingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.models.Messages;
import com.epicodus.example.myclimbingapp.models.Users;
import com.epicodus.example.myclimbingapp.ui.SendMessageActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {
    private ArrayList<Messages> mMessages = new ArrayList<>();
    private Context mContext;


    public MessageListAdapter(Context context, ArrayList<Messages> messages) {
        mContext = context;
        mMessages = messages;
    }

    @Override
    public MessageListAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list_item, parent, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageListAdapter.MessageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.userNameTextView)
        TextView mUserNameTextView;

        private Context mContext;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindUser(Users user) {
            mUserNameTextView.setText(user.getUserName());
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, SendMessageActivity.class);
            intent.putExtra("position", itemPosition + "");
            intent.putExtra("messages", Parcels.wrap(mMessages));
            mContext.startActivity(intent);
        }
    }
}
