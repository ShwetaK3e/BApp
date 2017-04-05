package com.bzyness.bzyness.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.ChatUsersAdapter;
import com.bzyness.bzyness.models.Chat;
import com.bzyness.bzyness.models.ChatUser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChatUsersActivity extends AppCompatActivity {


    RecyclerView chatUsersListView;
    RecyclerView.LayoutManager layoutManager;
    ChatUsersAdapter chatUsersAdapter;
    Map<String,ChatUser> chatUsersMap=new HashMap<>();
    SharedPreferences pref;
    String businessName,userName;
    List<ChatUser> chatUsersList=new ArrayList<>();
    List<String> chatUserKeys=new ArrayList<>();
    ProgressDialog pd;
    DatabaseReference reference;
    DatabaseReference presenceRef;
    DatabaseReference lastOnlineRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_users);
        layoutManager=new GridLayoutManager(this,1);
        chatUsersListView=(RecyclerView)findViewById(R.id.chat_users_list);
        chatUsersListView.setLayoutManager(layoutManager);
        pref=getSharedPreferences("USERDETAILS",MODE_PRIVATE);
        businessName=pref.getString("BUSINESSNAME",null);
        userName=pref.getString("USERNAME",null);
        pd=new ProgressDialog(this);
        chatUsersAdapter=new ChatUsersAdapter(ChatUsersActivity.this, chatUsersList, new ChatUsersAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(String userName,String businessName) {
                Intent intent = new Intent(ChatUsersActivity.this, ChatActivity.class);

                intent.putExtra("CHATWITH", businessName+"OF"+userName);
                startActivity(intent);
            }
        });
        chatUsersListView.setAdapter(chatUsersAdapter);

        final FirebaseDatabase firebaseDatabase=BaseActivity.getDatabase();
        reference=firebaseDatabase.getReference("Users");
        reference.keepSynced(true);
        reference.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                final String key=dataSnapshot.getKey();
                if(!key.equals(userName)){
                    DatabaseReference businessRef=firebaseDatabase.getReference("Users/"+key);
                    businessRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
                        @Override
                        public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                            final String childKey=dataSnapshot.getKey();
                            final ChatUser chatUser=dataSnapshot.getValue(ChatUser.class);

                            DatabaseReference msgRef=firebaseDatabase.getReference("messages/"+businessName+"OF"+userName+"_"+childKey+"OF"+key);
                            Query query=msgRef.limitToLast(1);
                            query.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
                                @Override
                                public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                                    Chat lastChat=dataSnapshot.getValue(Chat.class);
                                    chatUser.setLastChat(lastChat);
                                    chatUsersAdapter.notifyDataSetChanged();
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

                            chatUser.setBusinessName(dataSnapshot.getKey());
                            chatUser.setUserName(key);
                            if (s == null) {
                                if (pd.isShowing()) pd.hide();
                                chatUsersList.add(0, chatUser);
                                chatUserKeys.add(0,childKey);
                            } else {
                                if (pd.isShowing()) pd.hide();
                                int prev_index = chatUserKeys.indexOf(s);
                                int nxt_index = prev_index + 1;
                                if (nxt_index == chatUserKeys.size()) {
                                    chatUsersList.add(chatUser);
                                    chatUserKeys.add(childKey);
                                } else {
                                    chatUsersList.add(nxt_index, chatUser);
                                    chatUserKeys.add(nxt_index, childKey);
                                }
                            }
                            chatUsersAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                            String childKey=dataSnapshot.getKey();
                            ChatUser chatUser=dataSnapshot.getValue(ChatUser.class);
                            chatUser.setUserName(key);
                            chatUser.setBusinessName(childKey);
                            int indexOfChildChanged=chatUserKeys.indexOf(childKey);
                            chatUsersList.remove(indexOfChildChanged);
                            chatUsersList.add(indexOfChildChanged,chatUser);
                            chatUsersAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            String childKey=dataSnapshot.getKey();
                            int indexOfRemovedItem=chatUserKeys.indexOf(childKey);
                            chatUsersList.remove(indexOfRemovedItem);
                            chatUserKeys.remove(indexOfRemovedItem);
                        }

                        @Override
                        public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                            ChatUser chatUser=dataSnapshot.getValue(ChatUser.class);
                            String childKey=dataSnapshot.getKey();
                            chatUser.setBusinessName(childKey);
                            chatUser.setUserName(key);
                            int indexOfChildMoved=chatUserKeys.indexOf(childKey);
                            chatUsersList.remove(indexOfChildMoved);
                            chatUserKeys.remove(indexOfChildMoved);
                            if (s == null) {
                                if (pd.isShowing()) pd.hide();
                                chatUsersList.add(0, chatUser);
                                chatUserKeys.add(0, dataSnapshot.getKey());
                            } else {
                                if (pd.isShowing()) pd.hide();
                                int prev_index = chatUserKeys.indexOf(s);
                                int nxt_index = prev_index + 1;
                                if (nxt_index == chatUserKeys.size()) {
                                    chatUsersList.add(chatUser);
                                    chatUserKeys.add(dataSnapshot.getKey());
                                } else {
                                    chatUsersList.add(nxt_index, chatUser);
                                    chatUserKeys.add(nxt_index, dataSnapshot.getKey());
                                }
                            }
                            chatUsersAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
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

    /*void getChatUsersList()
    {
        StringRequest request = new StringRequest(Request.Method.GET, Constants.CHAT_USERS_TABLE_CREATE_URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                ObjectMapper objectMapper=new ObjectMapper();
                try {
                    chatUsersMap=objectMapper.readValue(s, new TypeReference<Map<String,ChatUser>>(){});
                    ChatUser chatuser;
                    for(Map.Entry<String,ChatUser> entry : chatUsersMap.entrySet()){
                        chatuser=entry.getValue();
                        if(!chatuser.getBusinessName().equals(businessName)) {
                            chatUsersList.add(chatuser);
                        }
                    }
                    chatUsersAdapter=new ChatUsersAdapter(ChatUsersActivity.this, chatUsersList, new ChatUsersAdapter.OnMyItemClickListener() {
                        @Override
                        public void onClick(String name) {
                            Intent intent = new Intent(ChatUsersActivity.this, ChatActivity.class);
                            intent.putExtra("CHATWITH", name);
                            startActivity(intent);
                        }
                    });
                    chatUsersListView.setAdapter(chatUsersAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("TAG123a" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(this);
        rQueue.add(request);
    }*/
}
