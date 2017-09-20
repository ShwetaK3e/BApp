package com.bzyness.bzyness.fragment;

import android.app.Dialog;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.UtilityFunc;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.CustomWidgets.BAppTextViewBold;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.activity.NewBusinessDetailsActivity;
import com.bzyness.bzyness.adapters.BzynessTypeAdapter;
import com.bzyness.bzyness.adapters.ProductCategoryAdapter;
import com.bzyness.bzyness.models.ProductCatList;
import com.bzyness.bzyness.models.ProductCategory;
import com.bzyness.bzyness.models.ProductList;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;
import static com.bzyness.bzyness.BaseActivity.bzynessClient;
import static com.bzyness.bzyness.BaseActivity.getPath;


public class AddProductCategoryFragment extends Fragment {




    BAppTextViewBold guideLine;
    ImageButton back_btn;
    RecyclerView product_list;
    BzynessTypeAdapter prdct_adapter;
    List<ProductCategory> productCategoryList=new LinkedList<>();
    int bzynessId=0;

    Dialog add_prodct_cat_dialog;


    public static AddProductCategoryFragment newInstance() {
        AddProductCategoryFragment fragment = new AddProductCategoryFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_products, container, false);

        bzynessId= Integer.parseInt(UtilityFunc.getStringFromSharedPreference(getContext(), Constants.BZYNESSID));
        guideLine=view.findViewById(R.id.prdct_guideline);
        back_btn=view.findViewById(R.id.back_btn);
        product_list=view.findViewById(R.id.product_cat_list);
        product_list.setLayoutManager(new GridLayoutManager(getActivity(),3));
        populateProductCategory(bzynessId);
        return  view;
    }


    final AddDialog holder=new AddDialog(add_prodct_cat_dialog);

    void showAddDialog(int mode){
        add_prodct_cat_dialog=new Dialog(getActivity(),R.style.MyDialogTheme);
        add_prodct_cat_dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        add_prodct_cat_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        add_prodct_cat_dialog.setContentView(R.layout.add_product_category_dialog);

        if(mode==0){
            holder.cat_name.setHint("Product Name");
            holder.dialog_header.setText("New Product");

        }else{
            holder.cat_name.setHint("Product Category Name");
            holder.dialog_header.setText("New Product Category");
        }
        holder.save.setOnClickListener(v -> {
            String name=holder.cat_name.getText().toString().trim();
            if(name.length()!=0 ){

                if(holder.add_image.getVisibility()!=View.GONE){

                }else{
                    Toast.makeText(getActivity(), "Upload a Category Image !!!", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(getActivity(), "Name your Category !!!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.cancel.setOnClickListener(v -> {
            add_prodct_cat_dialog.dismiss();
            add_prodct_cat_dialog=null;
        });

        holder.add_image.setOnClickListener(click->{
            BaseActivity.imageChooser(mode==0?Constants.PICK_PRODUCT_IMG_REQUEST:Constants.PICK_PRODUCT_CAT_IMG_REQUEST, "Select Image",getActivity());
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
        BAppTextViewBold dialog_header;

        AddDialog(Dialog dialog){
            cat_img=dialog.findViewById(R.id.prdct_cat_img);
            add_image=dialog.findViewById(R.id.add_img);
            cat_name=dialog.findViewById(R.id.cat_name);
            save=dialog.findViewById(R.id.save_cat);
            cancel=dialog.findViewById(R.id.close_dialog);
            dialog_header=dialog.findViewById(R.id.dialog_header);
        }
    }



    void populateProductCategory(int bzynessId) {
        if(bzynessClient!=null){
            bzynessClient.getBzynessProductCat(bzynessId).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ProductCatList>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ProductCatList
                                                   productCatList) {

                            if(!productCatList.getError()){
                                final Map<String,String> mapID=new HashMap<String, String>();
                                final Map<String, String> images=new HashMap<>();
                                final Map<String, String> names=new HashMap<String, String>();
                                int i = 1;

                                names.put(String.valueOf(i),"");
                                images.put(String.valueOf(i),"drawable://"+R.drawable.ic_plus);
                                mapID.put(String.valueOf(i++), "");

                                for (ProductCatList.BzynessCategory productCat : productCatList.getBzynessCategories()) {
                                    names.put(String.valueOf(i), productCat.getCategoryName());
                                    images.put(String.valueOf(i), productCat.getCategoryThumbnail());
                                    mapID.put(String.valueOf(i++), String.valueOf(productCat.getId()));
                                }
                                prdct_adapter = new BzynessTypeAdapter(getActivity(), names,images, new BzynessTypeAdapter.OnMyItemClickListener() {
                                    @Override
                                    public void onClick(String position) {


                                        String CATEGORY_ID = mapID.get(position);
                                        if(!CATEGORY_ID.isEmpty()) {
                                            guideLine.setText(names.get(position).toUpperCase());
                                            populateCategoryProduct(Integer.parseInt(CATEGORY_ID));
                                        }else{
                                            showAddDialog(1);
                                        }

                                    }

                                });
                                product_list.setAdapter(prdct_adapter);

                            }
                        }
                    });
        }

    }

    void populateCategoryProduct(int categoryId) {
        if(bzynessClient!=null){
            bzynessClient.getBzynessCatProduct(categoryId).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ProductList>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ProductList productList) {

                            if(!productList.getError()){

                                final Map<String,String> mapID=new HashMap<String, String>();
                                final Map<String, String> images=new HashMap<>();
                                final Map<String, String> names=new HashMap<String, String>();
                                int i = 1;

                                names.put(String.valueOf(i),"");
                                images.put(String.valueOf(i),"drawable://"+R.drawable.ic_plus);
                                mapID.put(String.valueOf(i++), "");
                                for (ProductList.BzynessPhoto product : productList.getBzynessPhotos()) {
                                    names.put(String.valueOf(i), product.getProductName());
                                    images.put(String.valueOf(i), product.getImageUrl());
                                    mapID.put(String.valueOf(i++), String.valueOf(product.getId()));
                                }
                                prdct_adapter = new BzynessTypeAdapter(getActivity(), names,images, new BzynessTypeAdapter.OnMyItemClickListener() {
                                    @Override
                                    public void onClick(String position) {
                                        if(mapID.get(position).isEmpty()) {
                                        showAddDialog(0);
                                        }

                                    }
                                });
                                product_list.setAdapter(prdct_adapter);
                            }
                        }
                    });
        }

    }

    void addCategory(int bzynessId, String categoryName) {
        if(bzynessClient!=null){

            RequestBody requestFile=RequestBody.create(MediaType.parse(getContext().getContentResolver().getType(imagePath)),pic);
            MultipartBody.Part body=MultipartBody.Part.createFormData("categoryThumbnail",pic.getName(),requestFile);

            bzynessClient.addBzynessProductCat(UtilityFunc.getStringFromSharedPreference(getContext(),Constants.pref_accessToken),bzynessId,categoryName,null,body).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Void>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Void productList) {


                        }
                    });
        }

    }


    void addProduct(String bzynessId,String categoryId, String productName) {
        if(bzynessClient!=null){

            RequestBody requestFile=RequestBody.create(MediaType.parse(getContext().getContentResolver().getType(imagePath)),pic);
            MultipartBody.Part body=MultipartBody.Part.createFormData("photo",pic.getName(),requestFile);

            bzynessClient.addBzynessProduct(UtilityFunc.getStringFromSharedPreference(getContext(),Constants.pref_accessToken),productName,bzynessId,categoryId,null,body).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Void>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Void productList) {


                        }
                    });
        }

    }

    File pic;
    Uri imagePath;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                imagePath = data.getData();
                pic= new File(getPath(getActivity(),imagePath));
                Glide.with(getActivity()).load(imagePath).into(holder.cat_img);
                holder.add_image.setVisibility(View.INVISIBLE);

                Toast.makeText(getActivity(), "Added", LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "No  Pic Added", LENGTH_LONG).show();
            }


    }
}
