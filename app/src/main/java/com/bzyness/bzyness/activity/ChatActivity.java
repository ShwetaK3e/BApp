package com.bzyness.bzyness.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.ChatMsgAdapter;
import com.bzyness.bzyness.models.Chat;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    ImageView sendButton;
    EditText messageArea;
    SharedPreferences pref;
    String senderName,senderB,receiver;
    ChatMsgAdapter chatMsgAdapter;
    List<Chat> chatList=new ArrayList<>();
    List<String> chatKeys=new ArrayList<>();
    RecyclerView chatListView;
    RecyclerView.LayoutManager layoutManager;
    SimpleDateFormat lastMsgOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        lastMsgOn=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        pref = getSharedPreferences("USERDETAILS", MODE_PRIVATE);
        senderName = pref.getString("USERNAME", null);
        senderB = pref.getString("BUSINESSNAME", null);
        receiver = getIntent().getStringExtra("CHATWITH");

        layoutManager = new GridLayoutManager(this, 1);
        chatListView = (RecyclerView) findViewById(R.id.chats);
        chatListView.setLayoutManager(layoutManager);

        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);

        FirebaseDatabase database = BaseActivity.getDatabase();
        final DatabaseReference senderRef = database.getReference("messages/" + senderB+"OF"+senderName + "_" + receiver);
        final DatabaseReference receiverRef = database.getReference("messages/" + receiver + "_" + senderB+"OF"+senderName);
        chatMsgAdapter = new ChatMsgAdapter(ChatActivity.this, chatList);
        chatListView.setAdapter(chatMsgAdapter);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    Chat chat = new Chat(senderB+"OF"+senderName, messageText);
                    senderRef.push().setValue(chat);
                    receiverRef.push().setValue(chat);
                }
            }
        });

        senderRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                String key = dataSnapshot.getKey();
                if (s == null) {
                    chatList.add(0, chat);
                    chatKeys.add(0, key);
                } else {
                    int prev_index = chatKeys.indexOf(s);
                    int nxt_index = prev_index + 1;
                    if (nxt_index == chatList.size()) {
                        chatList.add(chat);
                        chatKeys.add(key);
                    } else {
                        chatList.add(nxt_index, chat);
                        chatKeys.add(nxt_index, key);
                    }
                }
                chatMsgAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
