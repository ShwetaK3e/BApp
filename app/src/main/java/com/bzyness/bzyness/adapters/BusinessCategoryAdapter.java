package com.bzyness.bzyness.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bzyness.bzyness.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pervacio on 3/14/2017.
 */

public class BusinessCategoryAdapter extends RecyclerView.Adapter<BusinessCategoryAdapter.Holder> {

    private List<String> names=new ArrayList<>();
    private Context context;
    private OnMyItemClickListener onMyItemClickListener;


    public BusinessCategoryAdapter(Context context, List<String> names, OnMyItemClickListener onMyItemClickListener) {
        this.context=context;
        this.names = names;
        this.onMyItemClickListener=onMyItemClickListener;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.text_grid,parent,false);
        final Holder holder= new Holder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyItemClickListener.onClick(holder.name.getText().toString().trim());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
          holder.name.setText(names.get(position));

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

       TextView name;
        public Holder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
        }
    }

    public interface OnMyItemClickListener{
        public void onClick(String name);
    }

}
