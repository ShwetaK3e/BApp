package com.bzyness.bzyness.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.AppUtils.UserFormValidity;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.activity.HomeActivity;
import com.bzyness.bzyness.activity.LoginActivity;
import com.bzyness.bzyness.activity.NewBusinessDetailsActivity;
import com.bzyness.bzyness.activity.RegisterActivity;
import com.bzyness.bzyness.models.ServerResponse;
import com.bzyness.bzyness.models.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.Deserializers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.bzyness.bzyness.BaseActivity.objectMapper;
import static com.bzyness.bzyness.BaseActivity.session;

/**
 *
 * Created by Pervacio on 2/7/2017.
 */

public class LoginService extends AsyncTask<String,Void,String> {

    public static final MediaType URL_ENCODED=MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    OkHttpClient client;
    Activity activity;
    int responseCode=0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private final static String TAG=LoginService.class.getSimpleName();

    ProgressDialog pd;


    public LoginService(Activity activity){
        Log.i(TAG,"Login service Initiated");
        this.activity=activity;
        pd=new ProgressDialog(activity);
        this.pref=activity.getSharedPreferences(Constants.LOGIN_PREF_NAME, Context.MODE_PRIVATE);
        this.editor=pref.edit();
    }

    String doPostRequest(String url, String url_encoded) throws IOException {
        Log.i(TAG,"Login service Post Request");
        RequestBody body= RequestBody.create(URL_ENCODED,url_encoded);
        Request request=new Request.Builder()
                .url(url)
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .post(body)
                .build();
        Response response=client.newCall(request).execute();
        responseCode=response.code();
        return response.body().string();
    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG,"Login service preExecute");
        super.onPreExecute();
        pd.setCancelable(false);
        pd.show();
    }


    @Override
    protected String doInBackground(String... params) {
        Log.i(TAG,"Login service doInBackground");
        client = new OkHttpClient();
        String url=Constants.LOGIN_URL;
        String login_params=params[0];
        Log.i(TAG,"Login service params: "+ login_params);
        String JsonResponse=null;
        try {
            JsonResponse = doPostRequest(url,login_params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i(TAG,"Login service post Execute");
        super.onPostExecute(result);
        pd.dismiss();

        if(responseCode==0){
            LinearLayout layout=null;
            if(activity.getClass().getCanonicalName().equalsIgnoreCase(LoginActivity.class.getCanonicalName())) {
                layout = (LinearLayout) activity.findViewById(R.id.log_layout);
            }else if(activity.getClass().getCanonicalName().equalsIgnoreCase(RegisterActivity.class.getCanonicalName())){
                layout = (LinearLayout) activity.findViewById(R.id.reg_layout);
            }
            Snackbar snackbar = Snackbar.make(layout, Constants.NO_NETWORK, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if(result!=null) {
            ServerResponse serverResponse = new ServerResponse();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                serverResponse = objectMapper.readValue(result, ServerResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (serverResponse != null) {
                if (!serverResponse.getError()) {
                    Log.i(TAG, serverResponse.getAccessToken());
                    Toast.makeText(activity, "Log in Successful", Toast.LENGTH_SHORT).show();
                    new ValidateLoginService(activity).execute(serverResponse.getAccessToken(),Long.toString(serverResponse.getExpiresIn()));
                } else {
                    TextInputEditText userName = null, passWord = null;
                    userName = (TextInputEditText) activity.findViewById(R.id.rEditEmail);
                    passWord = (TextInputEditText) activity.findViewById(R.id.rEditPassword);
                    Log.i(TAG, "Login service , email error ");
                    UserFormValidity.invalidLoginCredentials(userName,passWord);
                    Toast.makeText(activity, "Sorry,Invalid Details. ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.i(TAG, "serverResponse is null");
            }
        }

        Log.i(TAG, "Login Service response, Response Code:"+ responseCode);
    }



}
