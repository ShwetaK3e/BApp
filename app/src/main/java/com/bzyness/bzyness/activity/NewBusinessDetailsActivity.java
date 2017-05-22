package com.bzyness.bzyness.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.LinkAddress;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.DetectNetworkConnectivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.NewBDetailsAdapter;
import com.bzyness.bzyness.fragment.NewBLocFragment;
import com.bzyness.bzyness.fragment.NewBPhotosFragment;
import com.bzyness.bzyness.models.BzynessDetails;
import com.bzyness.bzyness.models.ServerResponse;
import com.bzyness.bzyness.services.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    Button save_details;
    private final String NONE="NONE";
    private final String BZYNESS_CREATED="BZYNESS_CREATED";
    private final String BZYNESS_TAG_SAVED="BZYNESS_TAG_SAVED";
    private final String BZYNESS_LOC_SAVED="BZYNESS_LOC_SAVED";
    private final String BZYNESS_PHOTO_SAVED="BZYNESS_PHOTO_SAVED";
    private final String BZYNESS_COVER_PIC_SAVED="BZYNESS_COVER_PIC_SAVED";
    private final String BZYNESS_LOGO_SAVED="BZYNESS_LOGO_SAVED";
    private String what_is_saved=NONE;

    BzynessDetails bzynessDetails;

    private final String TAG=NewBusinessDetailsActivity.class.getSimpleName();


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


        save_details=(Button)findViewById(R.id.save_new_bzyness);
        save_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (what_is_saved){
                    case NONE:
                          String bzyness_name = businessName.getText().toString().trim();
                          Log.i(TAG, bzyness_name + "------");
                          if (bzyness_name.length() == 0) {
                              businessName.requestFocus();
                              lManager.showSoftInput(businessName, 0);
                              Toast.makeText(NewBusinessDetailsActivity.this, "Bzyness Name Missing", Toast.LENGTH_SHORT).show();
                          } else {
                              bzynessDetails.setBzyness_name(bzyness_name);
                              String alias_name = aliasName.getText().toString().trim();
                              Log.i(TAG, alias_name + "------");
                              if (alias_name.length() == 0) {
                                  aliasName.requestFocus();
                                  lManager.showSoftInput(aliasName, 0);
                                  Toast.makeText(NewBusinessDetailsActivity.this, "Alias Name Missing", Toast.LENGTH_SHORT).show();
                              } else {
                                  bzynessDetails.setAlias_name(alias_name);
                                  if (bzynessDetails.getBzyness_type_id() == null) {
                                      Toast.makeText(NewBusinessDetailsActivity.this, "Select Bzyness Type", Toast.LENGTH_SHORT).show();
                                  } else if (bzynessDetails.getBzyness_category_id() == null) {
                                      Toast.makeText(NewBusinessDetailsActivity.this, "Select Bzyness Category", Toast.LENGTH_SHORT).show();
                                  } else {
                                      new SaveNewBzynessService().execute();
                                  }
                              }
                          }
                          break;
                    case BZYNESS_CREATED:
                        }
        }
        });

        bzynessDetails=new BzynessDetails();
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new NewBDetailsAdapter(getSupportFragmentManager(), this,bzynessDetails));

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


    class SaveNewBzynessService extends AsyncTask<Void, Void, String> {

        OkHttpClient client;
        private int responseCode;
        ProgressDialog pd;
        String email, password;
        private final String TAG = SaveNewBzynessService.class.getSimpleName();


        public SaveNewBzynessService() {
            Log.i(TAG, "New Bzyness Add Ctor");
            pd=new ProgressDialog(NewBusinessDetailsActivity.this);

        }

        String doPostRequest(String url) throws IOException {
            Log.i(TAG, "Add new Bzyness service post Request"+ bzynessDetails.getBzyness_name()+ " "+ bzynessDetails.getAlias_name()+" "+bzynessDetails.getBzyness_type_id()+" "+bzynessDetails.getBzyness_category_id());
            FormBody.Builder builder = new FormBody.Builder();
                   builder.add("name",bzynessDetails.getBzyness_name());
                   builder.add("aliasName",bzynessDetails.getAlias_name());
                   builder.add("typeId",bzynessDetails.getBzyness_type_id());
                   builder.add("categoryId",bzynessDetails.getBzyness_category_id());
            RequestBody body = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization","Bearer "+ BaseActivity.getStringFromPref(NewBusinessDetailsActivity.this,Constants.pref_accessToken))
                    .post(body).build();
            Response response = client.newCall(request).execute();
            responseCode = response.code();
            return response.body().string();
        }

        @Override
        protected String doInBackground(Void... params) {

            client = new OkHttpClient();
            String url = Constants.CREATE_BUSINESS_URL;
            String JsonResponse = null;
            try {
                JsonResponse = doPostRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Add new Bzyness service postExecute"+JsonResponse);
            return JsonResponse;
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Add new Bzyness service preExecute");
            super.onPreExecute();
            pd.setCancelable(false);
            pd.show();
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "Add new Bzyness service postExecute");
            super.onPostExecute(result);
            pd.dismiss();
            if (responseCode == 0) {
                LinearLayout layout = null;
                layout = (LinearLayout) NewBusinessDetailsActivity.this.findViewById(R.id.new_bzyness_layout);
                Snackbar snackbar = Snackbar.make(layout, Constants.NO_NETWORK, Snackbar.LENGTH_LONG);
                snackbar.show();
            } else if (result != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                ServerResponse serverResponse = new ServerResponse();
                try {
                    serverResponse = objectMapper.readValue(result, ServerResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (serverResponse.getError()) {
                    Toast.makeText(NewBusinessDetailsActivity.this, "Try Again !!", Toast.LENGTH_SHORT).show();
                } else {
                    what_is_saved=BZYNESS_CREATED;
                    bzynessDetails.setBzyness_id(serverResponse.getBzynessId());
                    if(bzynessDetails.getBzyness_tags()!=null){
                        new SaveNewBzynessTagService().execute();
                    }
                    Toast.makeText(NewBusinessDetailsActivity.this, "New Bzyness added Successfully !!!", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Add new Bzyness service , success");
                }
                Log.i(TAG, "Add new Bzyness service , responseCode:" + responseCode);
            } else {
                Log.i(TAG, "Add new Bzyness service , null result");
            }
        }
    }



    class SaveNewBzynessTagService extends AsyncTask<Void, Void, String> {

        OkHttpClient client;
        private int responseCode;
        ProgressDialog pd;

        private final String TAG = SaveNewBzynessTagService.class.getSimpleName();


        public SaveNewBzynessTagService() {
            Log.i(TAG, "New Bzyness Tag Add Ctor");
            pd=new ProgressDialog(NewBusinessDetailsActivity.this);

        }

        String doPostRequest(String url, String tag) throws IOException {
            Log.i(TAG, "Add new Bzyness Tag service post Request"+ bzynessDetails.getBzyness_name()+ " "+ bzynessDetails.getAlias_name()+" "+bzynessDetails.getBzyness_type_id()+" "+bzynessDetails.getBzyness_category_id());
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("bzynessId",String.valueOf(bzynessDetails.getBzyness_id()));
            builder.add("tag",tag);
            RequestBody body = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization","Bearer "+ BaseActivity.getStringFromPref(NewBusinessDetailsActivity.this,Constants.pref_accessToken))
                    .post(body).build();
            Response response = client.newCall(request).execute();
            responseCode = response.code();
            return response.body().string();
        }

        @Override
        protected String doInBackground(Void... params) {

            client = new OkHttpClient();
            String url = Constants.ADD_BUSINESS_TAG_URL;
            String JsonResponse = null;
            List<String> tags=bzynessDetails.getBzyness_tags();
            for(String Tag:tags) {
                try {
                    JsonResponse = doPostRequest(url,Tag);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.i(TAG, "Add new Bzyness Tag service postExecute"+JsonResponse);
            return JsonResponse;
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Add new Bzyness Tag service preExecute");
            super.onPreExecute();
            pd.setCancelable(false);
            pd.show();
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "Add new Bzyness Tag service postExecute");
            super.onPostExecute(result);
            pd.dismiss();
            if (responseCode == 0) {
                LinearLayout layout = null;
                layout = (LinearLayout) NewBusinessDetailsActivity.this.findViewById(R.id.new_bzyness_layout);
                Snackbar snackbar = Snackbar.make(layout, Constants.NO_NETWORK, Snackbar.LENGTH_LONG);
                snackbar.show();
            } else if (result != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                ServerResponse serverResponse = new ServerResponse();
                try {
                    serverResponse = objectMapper.readValue(result, ServerResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (serverResponse.getError()) {
                    Toast.makeText(NewBusinessDetailsActivity.this, "Try Again !!", Toast.LENGTH_SHORT).show();
                } else {
                    what_is_saved=BZYNESS_TAG_SAVED;
                    Toast.makeText(NewBusinessDetailsActivity.this, "New Bzyness added Successfully !!!", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Add new Bzyness Tag service , success");
                }
                Log.i(TAG, "Add new Bzyness Tag service , responseCode:" + responseCode);
            } else {
                Log.i(TAG, "Add new Bzyness Tag service , null result");
            }
        }
    }


}
