package com.bzyness.bzyness.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bzyness.bzyness.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pervacio on 3/14/2017.
 */

public class BusinessTypeAdapter extends RecyclerView.Adapter<BusinessTypeAdapter.Holder> {

    private Map<String,String> names=new HashMap<>();
    private Map<String,String> imagesURI=new HashMap<>();
    private Context context;
    private OnMyItemClickListener onMyItemClickListener;

    private static String TAG=BusinessTypeAdapter.class.getSimpleName();


    public BusinessTypeAdapter(Context context, Map<String,String > names, Map<String, String> imagesURI, OnMyItemClickListener onMyItemClickListener) {
        Log.i(TAG,"Adapter Constructor");
        this.context=context;
        this.names = names;
        this.imagesURI = imagesURI;
        this.onMyItemClickListener=onMyItemClickListener;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG,"adapter onCreateViewHolder");
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_text_grid_view,parent,false);
        final Holder holder= new Holder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyItemClickListener.onClick(holder.name.getText().toString());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Log.i(TAG,"adapter onBindViewHolder");
        String key= String.valueOf(position+1);
          holder.name.setText(names.get(key).toUpperCase());
          Glide.with(context).load(imagesURI.get(key)).into(holder.img);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG,names.size()+" ");
        return names.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

       TextView name;
       ImageView img;
        public Holder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            img=(ImageView)itemView.findViewById(R.id.image);
        }
    }

    public interface OnMyItemClickListener{
        public void onClick(String type_id);
    }

}
