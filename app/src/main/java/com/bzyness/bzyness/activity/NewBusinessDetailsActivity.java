package com.bzyness.bzyness.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
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
import android.support.v7.widget.Toolbar;
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
import com.bzyness.bzyness.models.CreateBzynessServerResponse;
import com.bzyness.bzyness.models.ServerResponse;
import com.bzyness.bzyness.services.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bzyness.bzyness.BaseActivity.authenticatedBzynessClient;
import static com.bzyness.bzyness.BaseActivity.bzynessClient;
import static com.bzyness.bzyness.BaseActivity.getPath;

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
    private List<String> done_lists=new ArrayList<>();
    private final String NONE="NONE";
    private final String NEW_BZYNESS="NEW_BZYNESS";
    private final String BZYNESS_TAG="BZYNESS_TAG";
    private final String BZYNESS_LOC="BZYNESS_LOC";
    private final String BZYNESS_COVER_PIC1="BZYNESS_COVER_PIC1";
    private final String BZYNESS_COVER_PIC2="BZYNESS_COVER_PIC2";
    private final String BZYNESS_COVER_PIC3="BZYNESS_COVER_PIC3";
    private final String BZYNESS_COVER_PIC4="BZYNESS_COVER_PIC4";
    private final String BZYNESS_COVER_PIC5="BZYNESS_COVER_PIC5";
    private final String BZYNESS_LOGO="BZYNESS_LOGO";
    private final String BZYNESS_PHONE="BZYNESS_PHONE";
    private final String BZYNESS_WEBSITE="BZYNESS_WEBSITE";
    private final String BZYNESS_APK="BZYNESS_APK";
    private final String BZYNESS_IPA="BZYNESS_IPA";


    BzynessDetails bzynessDetails;

    private final String TAG=NewBusinessDetailsActivity.class.getSimpleName();



    BroadcastReceiver nonetwork;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_business_activity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




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


               List<String> todos=creation_status();

               for( String todo:todos) {
                   switch (todo) {
                       case NEW_BZYNESS:
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



                                       new SaveNewBzynessService(bzynessDetails.getBzyness_name(), bzynessDetails.getAlias_name(), bzynessDetails.getBzyness_type_id(), bzynessDetails.getBzyness_category_id()).execute();
                                   }
                               }
                           }
                           break;
                       case BZYNESS_TAG:
                            new SaveNewBzynessTagService(String.valueOf( bzynessDetails.getBzyness_id()),bzynessDetails.getBzyness_tags()).execute();
                            break;
                       case BZYNESS_LOC:
                            new SaveNewBzyNessLocationService(String.valueOf( bzynessDetails.getBzyness_id()),bzynessDetails.getLatitude(), bzynessDetails.getLongitude()).execute();
                            break;
                       case BZYNESS_LOGO:
                           new SaveNewBzynessPhotoService(String.valueOf( bzynessDetails.getBzyness_id()),"LOGO_"+bzynessDetails.getLogoImage().getName(),bzynessDetails.getLogoImage()).execute();
                           break;
                       case BZYNESS_COVER_PIC1:
                           new SaveNewBzynessPhotoService(String.valueOf( bzynessDetails.getBzyness_id()),"COVER1_"+bzynessDetails.getCoverImage1().getName(),bzynessDetails.getCoverImage1()).execute();
                           break;
                       case BZYNESS_COVER_PIC2:
                           new SaveNewBzynessPhotoService(String.valueOf( bzynessDetails.getBzyness_id()),"COVER2_"+bzynessDetails.getCoverImage2().getName(),bzynessDetails.getCoverImage2()).execute();
                           break;
                       case BZYNESS_COVER_PIC3:
                           new SaveNewBzynessPhotoService(String.valueOf( bzynessDetails.getBzyness_id()),"COVER3_"+bzynessDetails.getCoverImage3().getName(),bzynessDetails.getCoverImage3()).execute();
                           break;
                       case BZYNESS_COVER_PIC4:
                           new SaveNewBzynessPhotoService(String.valueOf( bzynessDetails.getBzyness_id()),"COVER4_"+bzynessDetails.getCoverImage4().getName(),bzynessDetails.getCoverImage4()).execute();
                           break;
                       case BZYNESS_COVER_PIC5:
                           new SaveNewBzynessPhotoService(String.valueOf( bzynessDetails.getBzyness_id()),"COVER5_"+bzynessDetails.getCoverImage5().getName(),bzynessDetails.getCoverImage5()).execute();
                           break;
                       case BZYNESS_WEBSITE:
                           new SaveNewBzynessOtherService(String.valueOf(bzynessDetails.getBzyness_id()),"WEBSITE",bzynessDetails.getPhone_no()).execute();
                           break;
                       case BZYNESS_PHONE:
                           new SaveNewBzynessOtherService(String.valueOf(bzynessDetails.getBzyness_id()),"PHONE",bzynessDetails.getPhone_no()).execute();
                           break;
                       case BZYNESS_APK:
                           new SaveNewBzynessOtherService(String.valueOf(bzynessDetails.getBzyness_id()),"APK",bzynessDetails.getPhone_no()).execute();
                           break;
                       case BZYNESS_IPA:
                           new SaveNewBzynessOtherService(String.valueOf(bzynessDetails.getBzyness_id()),"IPA",bzynessDetails.getPhone_no()).execute();
                           break;

                   }
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
             File coverPic= new File(getPath(this,imagePath));
             bzynessDetails.setCoverImage1(coverPic);
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
             File logoImage=new File(getPath(this,imagePath));
             bzynessDetails.setLogoImage(logoImage);
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
        private final String TAG = SaveNewBzynessService.class.getSimpleName();
        private String bzyness_name, alias_name,type_id,category_id;


        public SaveNewBzynessService(String bzyness_name,String alias_name,String type_id,String category_id) {
            Log.i(TAG, "New Bzyness Add Ctor");
            pd=new ProgressDialog(NewBusinessDetailsActivity.this);
            this.bzyness_name=bzyness_name;
            this.alias_name=alias_name;
            this.type_id=type_id;
            this.category_id=category_id;
        }

        String doPostRequest(String url) throws IOException {
            Log.i(TAG, "Add new Bzyness service post Request"+ bzynessDetails.getBzyness_name()+ " "+ bzynessDetails.getAlias_name()+" "+bzynessDetails.getBzyness_type_id()+" "+bzynessDetails.getBzyness_category_id());
            FormBody.Builder builder = new FormBody.Builder();
                   builder.add("name",bzyness_name);
                   builder.add("aliasName",alias_name);
                   builder.add("typeId",type_id);
                   builder.add("categoryId",category_id);
            RequestBody body = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization","Bearer "+ BaseActivity.session.getAccessToken())
                    .post(body).build();
            Response response = client.newCall(request).execute();
            responseCode = response.code();
            Log.i(TAG, "Reponse"+ responseCode+" "+response.message());
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
            Log.i(TAG, "Add new Bzyness service do In Background"+JsonResponse);
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
                    Toast.makeText(NewBusinessDetailsActivity.this, "Try Again 3 !!", Toast.LENGTH_SHORT).show();
                } else {
                    done_lists.add(NEW_BZYNESS);
                    bzynessDetails.setBzyness_id(serverResponse.getBzynessId());
                    if(bzynessDetails.getBzyness_tags()!=null){
                        new SaveNewBzynessTagService(String.valueOf(bzynessDetails.getBzyness_id()),bzynessDetails.getBzyness_tags()).execute();
                    }
                    if(bzynessDetails.getLatitude() !=null && bzynessDetails.getLongitude()!=null){
                        new SaveNewBzyNessLocationService(String.valueOf(bzynessDetails.getBzyness_id()),bzynessDetails.getLatitude(),bzynessDetails.getLongitude()).execute();
                    }
                    if(bzynessDetails.getLogoImage()!=null){
                        new SaveNewBzynessPhotoService(String.valueOf(bzynessDetails.getBzyness_id()),"LOGO_"+bzynessDetails.getLogoImage().getName(),bzynessDetails.getLogoImage()).execute();
                    }
                    if(bzynessDetails.getCoverImage1()!=null){
                        new SaveNewBzynessPhotoService(String.valueOf(bzynessDetails.getBzyness_id()),"COVER1_"+bzynessDetails.getCoverImage1().getName(),bzynessDetails.getCoverImage1()).execute();
                    }
                    if(bzynessDetails.getCoverImage2()!=null){
                        new SaveNewBzynessPhotoService(String.valueOf(bzynessDetails.getBzyness_id()),"COVER2_"+bzynessDetails.getCoverImage2().getName(),bzynessDetails.getCoverImage2()).execute();
                    }
                    if(bzynessDetails.getCoverImage3()!=null){
                        new SaveNewBzynessPhotoService(String.valueOf(bzynessDetails.getBzyness_id()),"COVER3_"+bzynessDetails.getCoverImage3().getName(),bzynessDetails.getCoverImage3()).execute();
                    }
                    if(bzynessDetails.getCoverImage4()!=null){
                        new SaveNewBzynessPhotoService(String.valueOf(bzynessDetails.getBzyness_id()),"COVER4_"+bzynessDetails.getCoverImage4().getName(),bzynessDetails.getCoverImage4()).execute();
                    }
                    if(bzynessDetails.getCoverImage5()!=null){
                        new SaveNewBzynessPhotoService(String.valueOf(bzynessDetails.getBzyness_id()),"COVER5_"+bzynessDetails.getCoverImage5().getName(),bzynessDetails.getCoverImage5()).execute();
                    }
                    if(bzynessDetails.getPhone_no()!=null){
                        new SaveNewBzynessOtherService(String.valueOf(bzynessDetails.getBzyness_id()),"PHONE",bzynessDetails.getPhone_no()).execute();
                    }
                    if(bzynessDetails.getWebsite_link()!=null){
                        new SaveNewBzynessOtherService(String.valueOf(bzynessDetails.getBzyness_id()),"WEBSITE",bzynessDetails.getPhone_no()).execute();
                    }
                    if(bzynessDetails.getApk_link()!=null){
                        new SaveNewBzynessOtherService(String.valueOf(bzynessDetails.getBzyness_id()),"APK",bzynessDetails.getPhone_no()).execute();
                    }
                    if(bzynessDetails.getIpa_link()!=null){
                        new SaveNewBzynessOtherService(String.valueOf(bzynessDetails.getBzyness_id()),"IPA",bzynessDetails.getPhone_no()).execute();
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

    class SaveNewBzyNessLocationService extends AsyncTask<Void, Void, String> {


        OkHttpClient client;
        private int responseCode;
        ProgressDialog pd;

        private final String TAG = SaveNewBzyNessLocationService.class.getSimpleName();
        String latitude, longitude;
        String bzyness_id;


        public SaveNewBzyNessLocationService(String bzyness_id,String latitude, String longitude) {
            Log.i(TAG, "New Bzyness Loc  Add Ctor");
            pd=new ProgressDialog(NewBusinessDetailsActivity.this);
            this.latitude=latitude;
            this.longitude=longitude;
            this.bzyness_id=bzyness_id;
        }

        String doPostRequest(String url) throws IOException {
            Log.i(TAG, "Add new Bzyness Loc Tag service post Request"+ bzynessDetails.getBzyness_name()+ " "+ bzynessDetails.getAlias_name()+" "+bzynessDetails.getBzyness_type_id()+" "+bzynessDetails.getBzyness_category_id());
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("bzynessId",bzyness_id);
            builder.add("longitude",longitude);
            builder.add("latitude",latitude);
            RequestBody body = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization","Bearer "+ BaseActivity.session.getAccessToken())
                    .post(body).build();
            Response response = client.newCall(request).execute();
            responseCode = response.code();
            return response.body().string();
        }

        @Override
        protected String doInBackground(Void... params) {

            client = new OkHttpClient();
            String url = Constants.ADD_BUSINESS_LOC_URL;
            String JsonResponse = null;
                try {
                    JsonResponse = doPostRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            Log.i(TAG, "Add new Bzyness Loc service doin background"+JsonResponse);
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
                    Toast.makeText(NewBusinessDetailsActivity.this, "Try Again 4 !!", Toast.LENGTH_SHORT).show();
                } else {
                    done_lists.add(BZYNESS_LOC);
                    Toast.makeText(NewBusinessDetailsActivity.this, "New Bzyness Loc added Successfully !!!", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Add new Bzyness Tag service , success");
                }
                Log.i(TAG, "Add new Bzyness Tag service , responseCode:" + responseCode);
            } else {
                Log.i(TAG, "Add new Bzyness Tag service , null result");
            }
        }

    }

    class SaveNewBzynessTagService extends AsyncTask<Void, Void, String> {

        OkHttpClient client;
        private int responseCode;
        ProgressDialog pd;

        private final String TAG = SaveNewBzynessTagService.class.getSimpleName();
        List<String> tags=new ArrayList<>();
        String bzyness_id;


        public SaveNewBzynessTagService( String bzyness_id,List<String> tags) {
            Log.i(TAG, "New Bzyness Tag Add Ctor");
            pd=new ProgressDialog(NewBusinessDetailsActivity.this);
            this.tags=tags;
            this.bzyness_id=bzyness_id;

        }

        String doPostRequest(String url, String tag) throws IOException {
            Log.i(TAG, "Add new Bzyness Tag service post Request"+ bzynessDetails.getBzyness_name()+ " "+ bzynessDetails.getAlias_name()+" "+bzynessDetails.getBzyness_type_id()+" "+bzynessDetails.getBzyness_category_id());
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("bzynessId",bzyness_id);
            builder.add("tag",tag);
            RequestBody body = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization","Bearer "+ BaseActivity.session.getAccessToken())
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
                    Toast.makeText(NewBusinessDetailsActivity.this, "Try Again 1 !!", Toast.LENGTH_SHORT).show();
                } else {
                    done_lists.add(BZYNESS_TAG);
                    Toast.makeText(NewBusinessDetailsActivity.this, "New Bzyness Tag added Successfully !!!", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Add new Bzyness Tag service , success");
                }
                Log.i(TAG, "Add new Bzyness Tag service , responseCode:" + responseCode);
            } else {
                Log.i(TAG, "Add new Bzyness Tag service , null result");
            }
        }
    }

    class SaveNewBzynessPhotoService extends AsyncTask<Void, Void, String> {

        OkHttpClient client;
        private int responseCode;
        ProgressDialog pd;
        String bzyness_id;
        String photo_title;
        File imageFile=null;
        String photo_tag=null;

        private final String TAG = SaveNewBzynessPhotoService.class.getSimpleName();
        MediaType MEDIA_TYPE = null;


        public SaveNewBzynessPhotoService( String bzyness_id, String photo_title , File imageFile) {
            Log.i(TAG, "New Bzyness Photo Add Ctor");
            pd=new ProgressDialog(NewBusinessDetailsActivity.this);
            this.bzyness_id=bzyness_id;
            this.photo_title=photo_title;
            MEDIA_TYPE=imageFile.getName().endsWith(".png")? MediaType.parse("image/png"):MediaType.parse("image/jpeg");
            this.imageFile=imageFile;
        }

        String doPostRequest(String url) throws IOException {
            Log.i(TAG, "Add new Bzyness Photo service post Request"+"----"+url);
            RequestBody requestBody=new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(photo_tag, photo_title, RequestBody.create(MEDIA_TYPE, imageFile))
                    .addFormDataPart("bzynessId", bzyness_id)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .addHeader("Authorization","Bearer "+ BaseActivity.session.getAccessToken())
                    .addHeader("Content-Type","multipart/form-data")
                    .build();

            Response response = client.newCall(request).execute();

            responseCode = response.code();

            return response.body().string();
        }

        @Override
        protected String doInBackground(Void... params) {

            client = new OkHttpClient();
            String url=null;
            if(photo_title.startsWith("LOGO")) {
                url= Constants.ADD_BUSINESS_LOGO_URL;
                photo_tag="logo";
            }else if(photo_title.startsWith("COVER1")){
                url= Constants.ADD_BUSINESS_COVER1_URL;
                photo_tag="coverImage";
            }else if(photo_title.startsWith("COVER2")){
                url= Constants.ADD_BUSINESS_COVER2_URL;
            }else if(photo_title.startsWith("COVER3")){
                url= Constants.ADD_BUSINESS_COVER3_URL;
            }else if(photo_title.startsWith("COVER4")){
                url= Constants.ADD_BUSINESS_COVER4_URL;
            }else if(photo_title.startsWith("COVER5")){
                url= Constants.ADD_BUSINESS_COVER5_URL;
            }
            String JsonResponse = null;
            try {
                JsonResponse = doPostRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "Add new Bzyness Photo service inBackGround"+e.getMessage());
            }


            Log.i(TAG, "Add new Bzyness Photo service postExecute"+JsonResponse);
            return JsonResponse;
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Add new Bzyness Photo service preExecute");
            super.onPreExecute();
            pd.setCancelable(false);
            pd.show();
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "Add new Bzyness Photo service postExecute");
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
                    Log.i(TAG,"Model not matching");
                    e.printStackTrace();
                }

                if (serverResponse.getError()) {
                    Toast.makeText(NewBusinessDetailsActivity.this, "Try Again 2 !!", Toast.LENGTH_SHORT).show();
                } else {
                    if(photo_title.startsWith("LOGO")) {
                        done_lists.add(BZYNESS_LOGO);
                    }else if(photo_title.startsWith("COVER1")) {
                        done_lists.add(BZYNESS_COVER_PIC1);
                    }else if(photo_title.startsWith("COVER2")) {
                        done_lists.add(BZYNESS_COVER_PIC2);
                    }else if(photo_title.startsWith("COVER3")) {
                        done_lists.add(BZYNESS_COVER_PIC3);
                    }else if(photo_title.startsWith("COVER4")) {
                        done_lists.add(BZYNESS_COVER_PIC4);
                    }else if(photo_title.startsWith("COVER5")) {
                        done_lists.add(BZYNESS_COVER_PIC5);
                    }
                    Toast.makeText(NewBusinessDetailsActivity.this, "New Bzyness Photo added Successfully !!!", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Add new Bzyness Photo service , success");
                }
                Log.i(TAG, "Add new Bzyness Photo service , responseCode:" + responseCode);
            } else {
                Log.i(TAG, "Add new Bzyness Photo service , null result");
            }
        }
    }

    class SaveNewBzynessOtherService extends AsyncTask<Void, Void, String> {

        OkHttpClient client;
        private int responseCode;
        ProgressDialog pd;
        String other_type=null;
        String other_value=null;
        String bzyness_id;

        private final String TAG = SaveNewBzynessOtherService.class.getSimpleName();

        public SaveNewBzynessOtherService( String bzyness_id,String other_type,String other_value) {
            Log.i(TAG, "New Bzyness other Add Ctor");
            this.pd=new ProgressDialog(NewBusinessDetailsActivity.this);
            this.other_type=other_type;
            this.other_value=other_value;
            this.bzyness_id=bzyness_id;
        }

        String doPutRequest(String url) throws IOException {
            Log.i(TAG, "Add new Bzyness other service post Request"+ bzynessDetails.getBzyness_name()+ " "+ bzynessDetails.getAlias_name()+" "+bzynessDetails.getBzyness_type_id()+" "+bzynessDetails.getBzyness_category_id());
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("bzynessId",bzyness_id);
            if("PHONE".equalsIgnoreCase(other_type)){
                builder.add("phone",other_value);
            }else if("WEBSITE".equalsIgnoreCase(other_type)){
                builder.add("website",other_value);
            }else if("APK".equalsIgnoreCase(other_type)){
                builder.add("apk",other_value);
            }else if("IPA".equalsIgnoreCase(other_type)){
                builder.add("ipa",other_value);
            }
            RequestBody body = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization","Bearer "+ BaseActivity.session.getAccessToken())
                    .put(body)
                    .build();
            Response response = client.newCall(request).execute();
            responseCode = response.code();
            return response.body().string();
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.i(TAG, "Add new Bzyness other service inbackground");
            client = new OkHttpClient();
            String url=null;
            if("PHONE".equalsIgnoreCase(other_type)) {
                 url= Constants.ADD_BUSINESS_PHONE_URL;
            }else if("WEBSITE".equalsIgnoreCase(other_type)){
                url= Constants.ADD_BUSINESS_WEBSITE_URL;
            }else if("APK".equalsIgnoreCase(other_type)){
                url= Constants.ADD_BUSINESS_APK_URL;
            }else if("IPA".equalsIgnoreCase(other_type)){
                url= Constants.ADD_BUSINESS_IPA_URL;
            }
            String JsonResponse = null;
            try{
                JsonResponse = doPutRequest(url);
            } catch (IOException e) {
                    e.printStackTrace();
                Log.i(TAG, "Add new Bzyness other service inbackground"+e.getMessage());
                }
            Log.i(TAG, "Add new Bzyness other service postExecute"+JsonResponse);
            return JsonResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "Add new Bzyness other service preExecute");
            pd.setCancelable(false);
            pd.show();
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "Add new Bzyness other service postExecute");
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
                    Toast.makeText(NewBusinessDetailsActivity.this, "Try Again 4 !!", Toast.LENGTH_SHORT).show();
                } else {
                    if("PHONE".equalsIgnoreCase(other_type)) {
                        done_lists.add(BZYNESS_PHONE);
                    }else if("WEBSITE".equalsIgnoreCase(other_type)) {
                        done_lists.add(BZYNESS_WEBSITE);
                    }else if("APK".equalsIgnoreCase(other_type)) {
                        done_lists.add(BZYNESS_APK);
                    }else if("IPA".equalsIgnoreCase(other_type)) {
                        done_lists.add(BZYNESS_IPA);
                    }
                    Toast.makeText(NewBusinessDetailsActivity.this, "New Bzyness added Successfully !!!", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Add new Bzyness other service , success");
                }
                Log.i(TAG, "Add new Bzyness other service , responseCode:" + responseCode);
            } else {
                Log.i(TAG, "Add new Bzyness other service , null result");
            }
        }
    }



    private List<String> creation_status(){
        List<String> todos=new ArrayList<>();
        if(!done_lists.contains(NEW_BZYNESS)){
            todos.add(NEW_BZYNESS);
            return todos;
        }
        if(!done_lists.contains(BZYNESS_TAG) && bzynessDetails.getBzyness_tags()!=null){
            todos.add(BZYNESS_TAG);
        }
        if(!done_lists.contains(BZYNESS_LOGO) && bzynessDetails.getLogoImage()!=null ){
            todos.add(BZYNESS_LOGO);
        }
        if(!done_lists.contains(BZYNESS_COVER_PIC1) && bzynessDetails.getCoverImage1()!=null ){
            todos.add(BZYNESS_COVER_PIC1);
        }
        if(!done_lists.contains(BZYNESS_COVER_PIC2) && bzynessDetails.getCoverImage2()!=null ){
            todos.add(BZYNESS_COVER_PIC2);
        }
        if(!done_lists.contains(BZYNESS_COVER_PIC3) && bzynessDetails.getCoverImage3()!=null ){
            todos.add(BZYNESS_COVER_PIC3);
        }
        if(!done_lists.contains(BZYNESS_COVER_PIC4) && bzynessDetails.getCoverImage4()!=null ){
            todos.add(BZYNESS_COVER_PIC4);
        }
        if(!done_lists.contains(BZYNESS_COVER_PIC5) && bzynessDetails.getCoverImage5()!=null ){
            todos.add(BZYNESS_COVER_PIC5);
        }
        if(!done_lists.contains(BZYNESS_LOC) && bzynessDetails.getLatitude()!=null && bzynessDetails.getLongitude()!=null){
            todos.add(BZYNESS_LOC);
        }
        if(!done_lists.contains(BZYNESS_WEBSITE) && bzynessDetails.getWebsite_link()!=null ){
            todos.add(BZYNESS_WEBSITE);
        }
        if(!done_lists.contains(BZYNESS_PHONE) && bzynessDetails.getPhone_no()!=null ){
            todos.add(BZYNESS_PHONE);
        }
        if(!done_lists.contains(BZYNESS_APK) && bzynessDetails.getApk_link()!=null ){
            todos.add(BZYNESS_APK);
        }
        if(!done_lists.contains(BZYNESS_IPA) && bzynessDetails.getIpa_link()!=null ){
            todos.add(BZYNESS_IPA);
        }
        return  todos;
    }

    private void createNewBzyness(String bzynessName, String aliasName, String typeId, String categoryId){

       final Map<String, String> newBzyness=new HashMap<>();
        newBzyness.put("name",bzynessName);
        newBzyness.put("aliasName",aliasName);
        newBzyness.put("typeId",typeId);
        newBzyness.put("categoryId",categoryId);

        if(authenticatedBzynessClient!=null){
           authenticatedBzynessClient.createNewBzyness(newBzyness).subscribeOn(Schedulers.newThread())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Subscriber<CreateBzynessServerResponse>() {
                       @Override
                       public void onCompleted() {

                       }

                       @Override
                       public void onError(Throwable e) {

                       }

                       @Override
                       public void onNext(CreateBzynessServerResponse createBzynessServerResponse) {
                          if(!createBzynessServerResponse.getError()){
                              done_lists.add(NEW_BZYNESS);
                          }else{
                              Toast.makeText(NewBusinessDetailsActivity.this, "Try Again !!", Toast.LENGTH_SHORT).show();
                          }
                       }
                   });
        }

    }

}
