package com.bzyness.bzyness.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.ProductAlbumAdapter;
import com.bzyness.bzyness.adapters.ProductCatAlbumAdapter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    RecyclerView product_img_list;
    RecyclerView.LayoutManager productLayoutManager;
    ProductAlbumAdapter productAlbumAdapter;
    List<String> productImgsURI=new ArrayList<>();
    private static final String ADD_PRODUCT_IMG_PATH="android.resource://com.bzyness.bzyness/drawable/ic_add_product";
    LinearLayout productLayout;
    Button saveProductList;

    RecyclerView product_cat_img_list;
    RecyclerView.LayoutManager productCatLayoutManager;
    ProductCatAlbumAdapter productCatAlbumAdapter;
    List<String> productCatImgsURI=new ArrayList<>();
    LinearLayout productCatLayout;
    static int cat_count=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productLayout=(LinearLayout)findViewById(R.id.product_layout);
        productLayout.setVisibility(View.INVISIBLE);
        productCatLayout=(LinearLayout)findViewById(R.id.product_cat_layout);

        product_img_list=(RecyclerView)findViewById(R.id.product_list);
        productLayoutManager=new GridLayoutManager(this,4);
        product_img_list.setLayoutManager(productLayoutManager);
        productImgsURI.add(ADD_PRODUCT_IMG_PATH);
        productAlbumAdapter=new ProductAlbumAdapter(this, productImgsURI, new ProductAlbumAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(int pos, Drawable img) {
             if(pos==0){
                 BaseActivity.imageChooser(Constants.PICK_PRODUCT_IMG_REQUEST,"PICK_PRODUCT_IMAGE",AddProductActivity.this);
             }else{

             }
            }
        });
        product_img_list.setAdapter(productAlbumAdapter);
        saveProductList=(Button)findViewById(R.id.save_prdct_list);
        saveProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productLayout.setVisibility(View.INVISIBLE);
                productCatLayout.setVisibility(View.VISIBLE);
            }
        });

        product_cat_img_list=(RecyclerView)findViewById(R.id.product_cat_list);
        productCatLayoutManager=new GridLayoutManager(this,3);
        product_cat_img_list.setLayoutManager(productCatLayoutManager);
        productCatImgsURI.add(ADD_PRODUCT_IMG_PATH);
        productCatAlbumAdapter=new ProductCatAlbumAdapter(this, productCatImgsURI, new ProductCatAlbumAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(int pos, Drawable img) {
                if(pos==0){
                   productCatLayout.setVisibility(View.INVISIBLE);
                   productLayout.setVisibility(View.VISIBLE);
                   productImgsURI=new ArrayList<>();
                   cat_count++;
                }else{

                }
            }
        });
        product_cat_img_list.setAdapter(productCatAlbumAdapter);
    }

    private void getProductImages(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constants.PICK_PRODUCT_IMG_REQUEST){
            if(resultCode==RESULT_OK && data!=null){
                Uri imagePath = data.getData();
                productImgsURI.add(imagePath.toString());
                productAlbumAdapter.notifyDataSetChanged();
                productCatImgsURI.add(cat_count,imagePath.toString());
                productCatAlbumAdapter.notifyDataSetChanged();
            }
        }
    }
}
