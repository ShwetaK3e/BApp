package com.bzyness.bzyness.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzyness.bzyness.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pervacio on 3/14/2017.
 */

public class FeaturedBusinessHomeAdapter extends RecyclerView.Adapter<FeaturedBusinessHomeAdapter.Holder> {

    private List<Drawable> images=new ArrayList<>();
    private Context context;
    private OnMyItemClickListener onMyItemClickListener;


    public FeaturedBusinessHomeAdapter(Context context, List<Drawable> images, OnMyItemClickListener onMyItemClickListener) {
        this.context=context;
        this.images = images;
        this.onMyItemClickListener=onMyItemClickListener;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_feature_grid,parent,false);
        final Holder holder= new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
          holder.img.setImageDrawable(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

       ImageView img;
        public Holder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.feature_image);
        }
    }

    public interface OnMyItemClickListener{
        public void onClick(String name, Drawable img);
    }

}
