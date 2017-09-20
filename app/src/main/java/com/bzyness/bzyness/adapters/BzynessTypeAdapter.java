package com.bzyness.bzyness.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bzyness.bzyness.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pervacio on 3/14/2017.
 */

public class BzynessTypeAdapter extends RecyclerView.Adapter<BzynessTypeAdapter.Holder> {

    private Map<String,String> names=new HashMap<>();
    private Map<String,String> imagesURI=new HashMap<>();
    private OnMyItemClickListener onMyItemClickListener;
    private  Holder lastSelectedpostion=null;

    private static String TAG=BzynessTypeAdapter.class.getSimpleName();
    Context context;


    public BzynessTypeAdapter(Context context, Map<String,String > names, Map<String, String> imagesURI, OnMyItemClickListener onMyItemClickListener) {
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
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Log.i(TAG,"adapter onBindViewHolder");
        final String key= String.valueOf(position+1);
        holder.name.setText(names.get(key).toUpperCase());
        Glide.with(context).load(imagesURI.get(key)).into(holder.img);
        holder.itemView.setOnClickListener(view->{
                onMyItemClickListener.onClick(key);
                if(lastSelectedpostion!=null){
                    lastSelectedpostion.itemView.setSelected(false);
                }
                holder.itemView.setSelected(true);
                lastSelectedpostion=holder;
        });
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
            name=itemView.findViewById(R.id.name);
            img=itemView.findViewById(R.id.image);
        }
    }

    public interface OnMyItemClickListener{
        void onClick(String type_id);
    }

}
