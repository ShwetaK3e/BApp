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

public class ProductAlbumAdapter extends RecyclerView.Adapter<ProductAlbumAdapter.Holder> {

    private List<String> imagesURI=new ArrayList<>();
    private Context context;
    private OnMyItemClickListener onMyItemClickListener;


    public ProductAlbumAdapter(Context context, List<String> imagesURI, OnMyItemClickListener onMyItemClickListener) {
        this.context=context;
        this.imagesURI = imagesURI;
        this.onMyItemClickListener=onMyItemClickListener;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_album_grid,parent,false);
        final Holder holder= new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
         // holder.img.setImageDrawable(images.get(position));
          Glide.with(context).load(imagesURI.get(position)).into(holder.img);
          holder.img.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onMyItemClickListener.onClick(position,holder.img.getDrawable());
              }
          });
    }

    @Override
    public int getItemCount() {
        return imagesURI.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

       ImageView img;
        public Holder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.image);
        }
    }

    public interface OnMyItemClickListener{
        public void onClick(int pos, Drawable img);
    }

}
