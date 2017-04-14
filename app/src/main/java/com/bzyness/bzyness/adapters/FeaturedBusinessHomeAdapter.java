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

    private List<Drawable> profileImages=new ArrayList<>();
    private List<Drawable> logoImages=new ArrayList<>();
    List<String> businessNames=new ArrayList<>();
    private Context context;
    private OnMyItemClickListener onMyItemClickListener;


    public FeaturedBusinessHomeAdapter(Context context, List<Drawable> profileImages,List<Drawable> logoImages, List<String> businessNames, OnMyItemClickListener onMyItemClickListener) {
        this.context=context;
        this.profileImages = profileImages;
        this.logoImages=logoImages;
        this.businessNames=businessNames;
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
          holder.profileImg.setImageDrawable(profileImages.get(position));
          holder.logoImg.setImageDrawable(logoImages.get(position));
          holder.businessName.setText(businessNames.get(position));
    }

    @Override
    public int getItemCount() {
        return profileImages.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
       ImageView profileImg;
       ImageView logoImg;
       TextView businessName;
        public Holder(View itemView) {
            super(itemView);
            profileImg=(ImageView)itemView.findViewById(R.id.featured_profile_img);
            logoImg=(ImageView)itemView.findViewById(R.id.featured_logo_img);
            businessName=(TextView)itemView.findViewById(R.id.featured_busi_name);
        }
    }

    public interface OnMyItemClickListener{
        public void onClick(String name, Drawable img);
    }

}
