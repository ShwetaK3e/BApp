package com.bzyness.bzyness;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.models.ChatUser;
import com.bzyness.bzyness.models.ServerError;
import com.bzyness.bzyness.models.UserDetails;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private static Activity activity;

    public static Firebase reference;

    public static ServerError error=new ServerError();




    private static Map<String, ChatUser> chatuserList=new HashMap<>();

    public static Map<String, ChatUser> getChatuserList(Context context) {
        getChatUserList(context);
        return chatuserList;
    }



    public static void listNames(List<String> names,List<Drawable> images,Context context){
        BaseActivity.context=context;
        names.add(context.getResources().getString(R.string.manufacturer));
        listImages(names.get(names.size()-1),images);
        names.add(context.getResources().getString(R.string.distributor));
        listImages(names.get(names.size()-1),images);
        names.add(context.getResources().getString(R.string.supplier));
        listImages(names.get(names.size()-1),images);
        names.add(context.getResources().getString(R.string.service_provider));
        listImages(names.get(names.size()-1),images);
        names.add(context.getResources().getString(R.string.retailer));
        listImages(names.get(names.size()-1),images);
        names.add(context.getResources().getString(R.string.wholeseller));
        listImages(names.get(names.size()-1),images);
        names.add(context.getResources().getString(R.string.professionals));
        listImages(names.get(names.size()-1),images);
        names.add(context.getResources().getString(R.string.rentals));
        listImages(names.get(names.size()-1),images);
        names.add(context.getResources().getString(R.string.other));
        listImages(names.get(names.size()-1),images);
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

    private static void listImages(String name,List<Drawable> images){

        switch (name) {
            case "MANUFACTURER":
                images.add(context.getResources().getDrawable(R.drawable.ic_manufacture_type));
                break;
            case "DISTRIBUTOR":
                images.add(context.getResources().getDrawable(R.drawable.ic_distributor_type));
                break;
            case "SUPPLIER":
                images.add(context.getResources().getDrawable(R.drawable.ic_supplier_type));
                break;
            case "SERVICE PROVIDER":
                images.add(context.getResources().getDrawable(R.drawable.ic_service_provider_type));
                break;
            case "RETAILER":
                images.add(context.getResources().getDrawable(R.drawable.ic_retailer_type));
                break;
            case "WHOLE SELLER":
                images.add(context.getResources().getDrawable(R.drawable.ic_wholeseller_type));
                break;
            case "PROFESSIONAL":
                images.add(context.getResources().getDrawable(R.drawable.ic_professional_type));
                break;
            case "RENTALS":
                images.add(context.getResources().getDrawable(R.drawable.ic_rental_type));
                break;
            case "OTHER":
                images.add(context.getResources().getDrawable(R.drawable.ic_other));
                break;
            default:
                images.add(context.getResources().getDrawable(R.drawable.ic_loading));
        }
    }


    private static  void getChatUserList(Context context){

        StringRequest request = new StringRequest(Request.Method.GET, Constants.CHAT_USERS_TABLE_CREATE_URL, new Response.Listener<String>(){
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
        rQueue.add(request);
    }

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }








    public static void imageChooser(int requestCode, String chooserTitle, Activity activity){
        BaseActivity.activity=activity;
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent,chooserTitle),requestCode);
    }



}

