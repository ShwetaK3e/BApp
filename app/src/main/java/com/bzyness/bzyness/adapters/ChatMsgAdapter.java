package com.bzyness.bzyness.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pervacio on 3/14/2017.
 */

public class ChatMsgAdapter extends RecyclerView.Adapter<ChatMsgAdapter.Holder> {

    private Context context;
    private List<Chat> chatList=new ArrayList<>();
    SharedPreferences pref;
    String businessName, userName;


    public ChatMsgAdapter(Context context, List<Chat> chatList) {
        this.context=context;
        this.chatList = chatList;
        this.pref=context.getSharedPreferences("USERDETAILS",Context.MODE_PRIVATE);
        businessName=pref.getString("BUSINESSNAME",null);
        userName=pref.getString("USERNAME",null);
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_grid,parent,false);
        final Holder holder= new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
          if(chatList.get(position).getSender().equals(businessName+"OF"+userName)){
              holder.bubbleLayout.setBackgroundResource(R.drawable.my_bubble);
              holder.parentBubbleLayout.setGravity(Gravity.RIGHT);
          }else{
              holder.bubbleLayout.setBackgroundResource(R.drawable.friend_bubble);
              holder.parentBubbleLayout.setGravity(Gravity.LEFT);
          }
          holder.name.setText(chatList.get(position).getMsg());
    }

    @Override
    public int getItemCount() {
        Log.i("TAGabcd",chatList.size()+"");
        return chatList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

       TextView name;
       LinearLayout bubbleLayout,parentBubbleLayout;
        public Holder(View itemView) {
            super(itemView);
            parentBubbleLayout=(LinearLayout)itemView.findViewById(R.id.bubble_layout_parent);
            bubbleLayout=(LinearLayout)itemView.findViewById(R.id.bubble_layout);
            name=(TextView)itemView.findViewById(R.id.message_text);
        }
    }


}
