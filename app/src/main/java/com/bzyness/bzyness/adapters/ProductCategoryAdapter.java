package com.bzyness.bzyness.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.ProductCategory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shwetakumar on 7/27/17.
 */

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {

    Context context;
    List<ProductCategory> productCategories=new LinkedList<>();
    OnMyItemClickListener onMyItemClickListener;

    public ProductCategoryAdapter(Context context, List<ProductCategory> productCategories, OnMyItemClickListener onMyItemClickListener){
        this.context=context;
        this.productCategories=productCategories;
        this.onMyItemClickListener=onMyItemClickListener;
    }

    @Override
    public ProductCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_product_grid,parent,false);
        ViewHolder holder=new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductCategoryAdapter.ViewHolder holder, final int position) {

        if(position==0){
            holder.prodct_cat_img.setVisibility(View.GONE);
            holder.add_product_cat.setVisibility(View.VISIBLE);
        }else{
            holder.add_product_cat.setVisibility(View.GONE);
            Glide.with(context).load(productCategories.get(position).getCategoryThumbnail()).into(holder.prodct_cat_img);
            holder.prodct_cat_img.setVisibility(View.VISIBLE);
        }

        holder.add_product_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyItemClickListener.onClick(position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyItemClickListener.onClick(position);
            }
        });

        holder.prodct_cat_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyItemClickListener.onClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productCategories.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView prodct_cat_img;
        ImageButton add_product_cat;


        public ViewHolder(View itemView) {
            super(itemView);
            prodct_cat_img=(ImageView)itemView.findViewById(R.id.prdct_cat_img);
            add_product_cat=(ImageButton)itemView.findViewById(R.id.add);
        }
    }

    public  interface OnMyItemClickListener{
        void onClick(int position);
    }
}
