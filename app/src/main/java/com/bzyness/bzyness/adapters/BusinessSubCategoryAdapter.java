package com.bzyness.bzyness.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bzyness.bzyness.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pervacio on 3/14/2017.
 */

public class BusinessSubCategoryAdapter extends RecyclerView.Adapter<BusinessSubCategoryAdapter.Holder> {

    private List<String> names=new ArrayList<>();
    private Context context;
    private OnMyItemClickListener onMyItemClickListener;


    public BusinessSubCategoryAdapter(Context context, List<String> names, OnMyItemClickListener onMyItemClickListener) {
        this.context=context;
        this.names = names;
        this.onMyItemClickListener=onMyItemClickListener;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_list_grid,parent,false);
        final Holder holder= new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
          holder.subcategory_tag.setText(names.get(position).toUpperCase());
          holder.remove_sub_category.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onMyItemClickListener.onClick(holder.subcategory_tag.getText().toString());
              }
          });

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

       TextView subcategory_tag;
       ImageButton remove_sub_category;
        public Holder(View itemView) {
            super(itemView);
            subcategory_tag=(TextView) itemView.findViewById(R.id.sub_category_tag);
            remove_sub_category=(ImageButton)itemView.findViewById(R.id.remove_sub_category);

        }
    }

    public interface OnMyItemClickListener{
        void onClick(String name);
    }

}
