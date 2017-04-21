package com.bzyness.bzyness.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bzyness.bzyness.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pervacio on 3/14/2017.
 */

public class BusinessTypeHomeAdapter extends RecyclerView.Adapter<BusinessTypeHomeAdapter.Holder> {

    private List<String> names=new ArrayList<>();
    private List<String> imagesURI=new ArrayList<>();
    private Context context;
    private OnMyItemClickListener onMyItemClickListener;


    public BusinessTypeHomeAdapter(Context context, List<String> names, List<String> imagesURI, OnMyItemClickListener onMyItemClickListener) {
        this.context=context;
        this.names = names;
        this.imagesURI = imagesURI;
        this.onMyItemClickListener=onMyItemClickListener;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_type_grid,parent,false);
        final Holder holder= new Holder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyItemClickListener.onClick(holder.name.getText().toString().trim(),holder.img.getDrawable());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
          holder.name.setText(names.get(position));
          Glide.with(context).load(imagesURI.get(position)).into(holder.img);
    }

    @Override
    public int getItemCount() {
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
        public void onClick(String name, Drawable img);
    }

}
