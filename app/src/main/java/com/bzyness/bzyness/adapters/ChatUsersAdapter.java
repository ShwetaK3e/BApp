package com.bzyness.bzyness.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.Chat;
import com.bzyness.bzyness.models.ChatUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.INVISIBLE;

/**
 * Created by Pervacio on 3/14/2017.
 */

public class ChatUsersAdapter extends RecyclerView.Adapter<ChatUsersAdapter.Holder> {

    private Context context;
    private OnMyItemClickListener onMyItemClickListener;
    private List<ChatUser> chatUsersList=new ArrayList<>();


    public ChatUsersAdapter(Context context, List<ChatUser> chatUsersList, OnMyItemClickListener onMyItemClickListener) {
        this.context=context;
        this.chatUsersList = chatUsersList;
        this.onMyItemClickListener=onMyItemClickListener;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_users_grid,parent,false);
        final Holder holder= new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
          holder.chat_user_grid.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onMyItemClickListener.onClick(chatUsersList.get(position).getUserName(),chatUsersList.get(position).getBusinessName());
              }
          });
          holder.businessName.setText(chatUsersList.get(position).getBusinessName());
         // holder.lastSeen.setText(chatUsersList.get(position).getName());
         Chat lastMsg=chatUsersList.get(position).getLastChat();
         if(lastMsg!=null){
             holder.lastChatTimeLayout.setVisibility(View.VISIBLE);
             holder.lastChat.setText(lastMsg.getMsg());

             holder.lastMsgTime.setText(setLastMsgTime(lastMsg.getMsgTimeLong()));
         }else{
             holder.lastChat.setText("");
             holder.lastChatTimeLayout.setVisibility(INVISIBLE);
         }
          if(chatUsersList.get(position).isOnLine()){
              holder.online.setBackgroundResource(R.drawable.bg_online);
          }else{
              holder.online.setBackgroundResource(R.drawable.bg_offline);
          }
    }

    @Override
    public int getItemCount() {
        return chatUsersList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

       TextView businessName,lastMsgTime,lastChat;
       ImageView online;
       LinearLayout chat_user_grid,lastChatTimeLayout;
        public Holder(View itemView) {
            super(itemView);
            lastChatTimeLayout=(LinearLayout)itemView.findViewById(R.id.last_msg_time_layout);
            chat_user_grid=(LinearLayout)itemView.findViewById(R.id.chat_user_grid);
            lastMsgTime=(TextView)itemView.findViewById(R.id.last_msg_time);
            businessName=(TextView)itemView.findViewById(R.id.business_name);
            lastChat=(TextView)itemView.findViewById(R.id.last_msg);
            online=(ImageView) itemView.findViewById(R.id.online);
        }
    }

    private String setLastMsgTime(Long lastMsgTime){
        Date date=new Date(lastMsgTime);
        SimpleDateFormat sdfPast=new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfToday=new SimpleDateFormat("hh:mm");
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date current=new Date();
        String dateTimeToday=sdf.format(current);
        String dateToday=sdfPast.format(current);
        String datVal;
        if(dateTimeToday.startsWith(dateToday)){
            datVal=sdfToday.format(date);
        }else{
            datVal=sdfPast.format(date);
        }
        return datVal;
    }

    public interface OnMyItemClickListener{
        void onClick(String userName, String businessName);
    }

}
