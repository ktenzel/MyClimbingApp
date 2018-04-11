package com.epicodus.example.myclimbingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.adapters.MessageListAdapter;
import com.epicodus.example.myclimbingapp.models.Messages;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendMessageActivity extends AppCompatActivity implements View.OnClickListener{
    private Query query;
    private MessageListAdapter mAdapter;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    public ArrayList<Messages> messages = new ArrayList<>();
    private String chatId;
    @BindView(R.id.messageRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.messageEditText)
    EditText mMessageEditText;
    @BindView(R.id.messageSendButton)
    Button mMessageSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        ButterKnife.bind(this);
        chatId = getIntent().getStringExtra("chatId");
        query = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages");

        mMessageSendButton.setOnClickListener(this);


    }

    public void setAdapter() {
        mAdapter = new MessageListAdapter(getApplicationContext(), messages);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SendMessageActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

    }


    @Override
    public void onClick(View view) {
        if (view == mMessageSendButton) {
            String messageContent = mMessageEditText.getText().toString();
            if (messageContent != "") {
                String senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String senderName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String text = messageContent;
                Messages newMessage = new Messages(senderId, senderName, text);
                DatabaseReference mChatRef = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages").push();
                newMessage.setPushId(mChatRef.getKey());
                mChatRef.setValue(newMessage);
            } else {
                Toast.makeText(this, "please enter a message", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}