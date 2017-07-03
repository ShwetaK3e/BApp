package com.bzyness.bzyness;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;


import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.models.ChatUser;
import com.bzyness.bzyness.models.ServerResponse;
import com.bzyness.bzyness.services.ServiceGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pervacio on 2/15/2017.
 */

public class BaseActivity {

    public  static ObjectMapper objectMapper;
    public  static SessionManager session;
    public  static GoogleApiClient client;
    private static Context context;
    private static FirebaseDatabase mDatabase;


    public static ServerResponse error=new ServerResponse();

    public static ServiceGenerator.BzynessClient bzynessClient= ServiceGenerator.createService(ServiceGenerator.BzynessClient.class);
    public static ServiceGenerator.BzynessClient authenticatedBzynessClient;


    private static Map<String, ChatUser> chatuserList=new HashMap<>();

    public static Map<String, ChatUser> getChatuserList(Context context) {
        getChatUserList(context);
        return chatuserList;
    }



    public static void listTypeNames(Map<String,String> names,Map<String,String> images,Activity activity){
        //businessTypeDetails=new BusinessTypeDetails();
       // new FetchBusinessTypeService(activity).execute();
     /*   for(BusinessTypeDetails.TypesOfBzyness type: businessTypeDetails.getTypesOfBzyness()){
            names.put(type.getId(),type.getName());
            images.put(type.getId(),(String)type.getIcon());
        }*/
    }

    public static void listCategoryNames(List<String> names,Context context){
        BaseActivity.context=context;
        names.add(context.getResources().getString(R.string.manufacturer));
        names.add(context.getResources().getString(R.string.distributor));
        names.add(context.getResources().getString(R.string.supplier));
        names.add(context.getResources().getString(R.string.service_provider));
        names.add(context.getResources().getString(R.string.retailer));
        names.add(context.getResources().getString(R.string.wholeseller));
        names.add(context.getResources().getString(R.string.professionals));
        names.add(context.getResources().getString(R.string.rentals));
        names.add(context.getResources().getString(R.string.other));
    }

    public static void listSubCategoryNames(List<String> names, Context context){
        BaseActivity.context=context;
        names.add(context.getResources().getString(R.string.manufacturer));
        names.add(context.getResources().getString(R.string.distributor));
        names.add(context.getResources().getString(R.string.supplier));
        names.add(context.getResources().getString(R.string.service_provider));
        names.add(context.getResources().getString(R.string.retailer));
        names.add(context.getResources().getString(R.string.wholeseller));
        names.add(context.getResources().getString(R.string.professionals));
        names.add(context.getResources().getString(R.string.rentals));
        names.add(context.getResources().getString(R.string.other));
    }

    private static void listImages(String name,List<String> images){

        switch (name) {
            case "Manufacturer":
                images.add("android.resource://com.bzyness.bzyness/drawable/ic_manufacture_type");
                break;
            case "Distributor":
                images.add("android.resource://com.bzyness.bzyness/drawable/ic_distributor_type");
                break;
            case "Supplier":
                images.add("android.resource://com.bzyness.bzyness/drawable/ic_supplier_type");
                break;
            case "Service Provider":
                images.add("android.resource://com.bzyness.bzyness/drawable/ic_service_provider_type");
                break;
            case "Retailer":
                images.add("android.resource://com.bzyness.bzyness/drawable/ic_retailer_type");
                break;
            case "WHOLE SELLER":
                images.add("android.resource://com.bzyness.bzyness/drawable/ic_wholeseller_type");
                break;
            case "PROFESSIONAL":
                images.add("android.resource://com.bzyness.bzyness/drawable/ic_professional_type");
                break;
            case "RENTALS":
                images.add("android.resource://com.bzyness.bzyness/drawable/ic_rental_type");
                break;
            case "OTHER":
                images.add("android.resource://com.bzyness.bzyness/drawable/ic_other");
                break;
            default:
                images.add("android.resource://com.bzyness.bzyness/drawable/ic_loading");
        }
    }

    public static void listFeaturedBusiness(List<Drawable> profileImgs,List<Drawable> logoImgs, List<String> businessNames){
        profileImgs.add(context.getResources().getDrawable(R.drawable.pro));
        profileImgs.add(context.getResources().getDrawable(R.drawable.pro));
        profileImgs.add(context.getResources().getDrawable(R.drawable.pro));
        profileImgs.add(context.getResources().getDrawable(R.drawable.pro));
        logoImgs.add(context.getResources().getDrawable(R.drawable.bg_circle_first_color));
        logoImgs.add(context.getResources().getDrawable(R.drawable.bg_circle_first_color));
        logoImgs.add(context.getResources().getDrawable(R.drawable.bg_circle_first_color));
        logoImgs.add(context.getResources().getDrawable(R.drawable.bg_circle_first_color));
        businessNames.add("Tantra");
        businessNames.add("Tantra");
        businessNames.add("Tantra");
        businessNames.add("Tantra");
    }


    private static  void getChatUserList(Context context){

        /*StringRequest request = new StringRequest(Request.Method.GET, Constants.CHAT_USERS_TABLE_CREATE_URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                ObjectMapper objectMapper=new ObjectMapper();
                try {
                    chatuserList=objectMapper.readValue(s, new TypeReference<Map<String, ChatUser>>(){});
                    if("a"=="a"){
                        Log.i("TAG123","Exception");
                    }
                } catch (IOException e) {
                    Log.i("TAG123","Exception....");
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);*/
    }



    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }


    public static void imageChooser(int requestCode, String chooserTitle, Activity activity){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent,chooserTitle),requestCode);
    }

    public static String getPath(Context context,Uri uri) {

        String result;

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static String getLoginParams(String name,String password){
        String login_params="";
        try {
            login_params = URLEncoder.encode("email", "UTF-8")
                + "=" + URLEncoder.encode(name, "UTF-8");
            login_params += "&" + URLEncoder.encode("password", "UTF-8") + "="
                    + URLEncoder.encode(password, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return login_params;
    }

    public static void getProductImages(Context context,String BusinessName, String Category, List<Drawable> imgList){
        BaseActivity.context=context;
        imgList.add(context.getResources().getDrawable(R.drawable.ic_add_product));
    }



}

