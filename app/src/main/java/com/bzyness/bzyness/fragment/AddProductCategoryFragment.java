package com.bzyness.bzyness.fragment;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.ProductCategoryAdapter;
import com.bzyness.bzyness.models.BzynessDetails;
import com.bzyness.bzyness.models.ProductCategory;

import java.util.LinkedList;
import java.util.List;


public class AddProductCategoryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    RecyclerView product_list;
    ProductCategoryAdapter adapter;
    List<ProductCategory> productCategoryList=new LinkedList<>();

    Dialog add_prodct_cat_dialog;


    public static AddProductCategoryFragment newInstance() {
        AddProductCategoryFragment fragment = new AddProductCategoryFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_product_category, container, false);
        product_list=(RecyclerView)view.findViewById(R.id.product_cat_list);
        product_list.setLayoutManager(new GridLayoutManager(getActivity(),3));
        productCategoryList=getProductCategories();
        adapter=new ProductCategoryAdapter(getActivity(), productCategoryList, new ProductCategoryAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(int position) {
               if(position==0 && add_prodct_cat_dialog==null) {
                   showAddDialog();
               }

            }
        });
        product_list.setAdapter(adapter);

        return  view;
    }

   /* // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

  /*  @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    List<ProductCategory> getProductCategories(){
        return new LinkedList<>();
    }

    void showAddDialog(){
        add_prodct_cat_dialog=new Dialog(getActivity(),R.style.MyDialogTheme);
        add_prodct_cat_dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        add_prodct_cat_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        add_prodct_cat_dialog.setContentView(R.layout.add_product_category_dialog);

        final AddDialog holder=new AddDialog(add_prodct_cat_dialog);
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cat_name=holder.cat_name.getText().toString().trim();
                if(cat_name.length()!=0 ){

                    if(holder.add_image.getVisibility()!=View.GONE){

                    }else{
                        Toast.makeText(getActivity(), "Upload a Category Image !!!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getActivity(), "Name your Category !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_prodct_cat_dialog.dismiss();
                add_prodct_cat_dialog=null;
            }
        });


        add_prodct_cat_dialog.setCanceledOnTouchOutside(true);
        add_prodct_cat_dialog.show();
    }

    class AddDialog{
        ImageView cat_img;
        ImageButton add_image;
        EditText cat_name;
        Button save;
        ImageButton cancel;

        AddDialog(Dialog dialog){
            cat_img=(ImageView)dialog.findViewById(R.id.prdct_cat_img);
            add_image=(ImageButton)dialog.findViewById(R.id.add_img);
            cat_name=(EditText)dialog.findViewById(R.id.cat_name);
            save=(Button)dialog.findViewById(R.id.save_cat);
            cancel=(ImageButton)dialog.findViewById(R.id.close_dialog);
        }
    }
}
