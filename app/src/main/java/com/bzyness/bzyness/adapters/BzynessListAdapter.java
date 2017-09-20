package com.bzyness.bzyness.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bzyness.bzyness.CustomWidgets.BAppEditTextNormal;
import com.bzyness.bzyness.CustomWidgets.BAppTextViewBold;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.BzynessList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pervacio on 3/14/2017.
 */

public class BzynessListAdapter extends RecyclerView.Adapter<BzynessListAdapter.Holder> {




    private static String TAG=BzynessListAdapter.class.getSimpleName();
    Context context;

    List<BzynessList.Bzyness> bzynesses=new ArrayList<>();


    public BzynessListAdapter(Context context, List<BzynessList.Bzyness> bzynesses, OnMyItemClickListener onMyItemClickListener) {
        Log.i(TAG,"Adapter Constructor");
        this.context=context;
        this.bzynesses=bzynesses;
    }




    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG,"adapter onCreateViewHolder");
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.bzyness_list_grid,parent,false);
        final Holder holder= new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Log.i(TAG,"adapter onBindViewHolder");

        BzynessList.Bzyness bzyness=bzynesses.get(position);
        holder.name.setText(bzyness.getName());
        holder.type.setText(bzyness.getTypeId());
        Glide.with(context).load("drawable://"+R.drawable.ic_logo).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG,bzynesses.size()+" ");
        return bzynesses.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

       BAppTextViewBold name;
       BAppEditTextNormal type;
       ImageView icon;

       //yet to implement
       LinearLayout unread_chat_msg_layout;
       BAppTextViewBold unread_chat_msg_nos;

        public Holder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            type=itemView.findViewById(R.id.type);
            icon=itemView.findViewById(R.id.icon);
        }
    }

    public interface OnMyItemClickListener{
        void onClick(String type_id);
    }

}
