package com.bzyness.bzyness.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.LinkAddress;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.DetectNetworkConnectivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.NewBDetailsAdapter;
import com.bzyness.bzyness.fragment.NewBLocFragment;
import com.bzyness.bzyness.fragment.NewBPhotosFragment;

import java.io.IOException;

/**
 * Created by Pervacio on 3/25/2017.
 */



public class NewBusinessDetailsActivity extends AppCompatActivity {


    LinearLayout new_bzyness_layout;

    ImageView profilePic,logo;
    LinearLayout profilePicScheme,logoScheme;
    LinearLayout addProfilePic, addLogo;
    LinearLayout editProfilePic, editLogo;

    EditText businessName, aliasName;
    ImageButton editBusinessName, editAliasName;

    BroadcastReceiver nonetwork;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_business_activity);

        new_bzyness_layout=(LinearLayout)findViewById(R.id.new_bzyness_layout);
        nonetwork=new DetectNetworkConnectivity() {
            @Override
            protected void onNetworkChange() {
                Snackbar snackbar=Snackbar.make(new_bzyness_layout,Constants.NO_NETWORK,Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        };
        IntentFilter filter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(nonetwork,filter);

        profilePic=(ImageView)findViewById(R.id.bprof_pic);
        profilePicScheme=(LinearLayout)findViewById(R.id.bprof_scheme);
        profilePicScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser(Constants.PICK_PROFILE_IMAGE_REQUEST,"SELECT BUSINESS PROFILE IMAGE");
            }
        });
        addProfilePic=(LinearLayout)findViewById(R.id.add_profile_image);
        addProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser(Constants.PICK_PROFILE_IMAGE_REQUEST,"SELECT BUSINESS PROFILE IMAGE");
            }
        });
        logo=(ImageView)findViewById(R.id.b_logo);
        logoScheme=(LinearLayout)findViewById(R.id.logo_scheme);
        logoScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser(Constants.PICK_LOGO_IMAGE_REQUEST,"SELECT BUSINESS LOGO");
            }
        });
        addLogo=(LinearLayout)findViewById(R.id.add_logo);
        addLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser(Constants.PICK_LOGO_IMAGE_REQUEST,"SELECT BUSINESS LOGO");
            }
        });



        editProfilePic=(LinearLayout)findViewById(R.id.edit_profile_pic);
        editProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser(Constants.PICK_PROFILE_IMAGE_REQUEST,"SELECT BUSINESS PROFILE IMAGE");
            }
        });

        editProfilePic=(LinearLayout)findViewById(R.id.edit_profile_pic);
        editProfilePic.setVisibility(View.INVISIBLE);
        editProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser(Constants.PICK_PROFILE_IMAGE_REQUEST,"SELECT BUSINESS PROFILE IMAGE");
            }
        });
        editLogo=(LinearLayout)findViewById(R.id.edit_logo);
        editLogo.setVisibility(View.INVISIBLE);
        editLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser(Constants.PICK_LOGO_IMAGE_REQUEST,"SELECT BUSINESS LOGO");
            }
        });


        final InputMethodManager lManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        businessName=(EditText)findViewById(R.id.bName);
        lManager.showSoftInput(businessName, -1);
        aliasName=(EditText)findViewById(R.id.alias_name);
        lManager.showSoftInput(aliasName, -1);
        editBusinessName=(ImageButton)findViewById(R.id.edit_bName);
        editBusinessName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                businessName.requestFocus();
                lManager.showSoftInput(businessName, 0);
            }
        });
        editAliasName=(ImageButton)findViewById(R.id.edit_aliasName);
        editAliasName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliasName.requestFocus();
                lManager.showSoftInput(aliasName, 0);
            }
        });


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new NewBDetailsAdapter(getSupportFragmentManager(), this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void imageChooser(int requestCode, String chooserTitle){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,chooserTitle),requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     if(requestCode == Constants.PICK_PROFILE_IMAGE_REQUEST){
         if (resultCode == RESULT_OK && data != null && data.getData() != null) {
             Uri imagePath = data.getData();
             Glide.with(NewBusinessDetailsActivity.this).load(imagePath).into(profilePic);
             addProfilePic.setVisibility(View.INVISIBLE);
             editProfilePic.setVisibility(View.VISIBLE);
             profilePicScheme.setVisibility(View.INVISIBLE);
             Toast.makeText(this, "New Profile Pic Added", Toast.LENGTH_LONG).show();
         } else {
             Toast.makeText(this, "No Profile Pic Added", Toast.LENGTH_LONG).show();
         }
     }else if(requestCode == Constants.PICK_LOGO_IMAGE_REQUEST){
         if (resultCode == RESULT_OK && data != null && data.getData() != null) {
             Uri imagePath = data.getData();
             Glide.with(NewBusinessDetailsActivity.this).load(imagePath).into(logo);
             addLogo.setVisibility(View.GONE);
             editLogo.setVisibility(View.VISIBLE);
             logoScheme.setVisibility(View.INVISIBLE);
             Toast.makeText(this, "New Logo Added", Toast.LENGTH_LONG).show();
         } else {
             Toast.makeText(this, "No Logo Added", Toast.LENGTH_LONG).show();
         }
     }else{
         for (android.support.v4.app.Fragment fragment : getSupportFragmentManager().getFragments()) {
             if (fragment  instanceof NewBLocFragment && requestCode==Constants.PLACE_PICKER_REQUEST ) {
                 fragment.onActivityResult(requestCode, resultCode, data);
             }else if(fragment instanceof NewBPhotosFragment && (requestCode==Constants.PICK_IMAGE1_REQUEST ||requestCode==Constants.PICK_IMAGE2_REQUEST ||requestCode==Constants.PICK_IMAGE3_REQUEST||requestCode==Constants.PICK_IMAGE4_REQUEST||requestCode==Constants.PICK_IMAGE5_REQUEST )){
                 fragment.onActivityResult(requestCode, resultCode, data);
             }
         }
       }
    }
}
