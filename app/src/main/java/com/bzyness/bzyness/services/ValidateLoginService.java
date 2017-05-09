package com.bzyness.bzyness.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.activity.HomeActivity;
import com.bzyness.bzyness.activity.LoginActivity;
import com.bzyness.bzyness.activity.NewBusinessDetailsActivity;
import com.bzyness.bzyness.models.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Pervacio on 4/25/2017.
 */

public class ValidateLoginService extends AsyncTask<String,Void,String> {

    OkHttpClient client;
    int responseCode=0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Activity activity;
    String accessToken=null;
    Long expiresIn=0L;
    private static final String TAG=ValidateLoginService.class.getSimpleName();
    ProgressDialog pd;


    public ValidateLoginService(Activity activity){
        Log.i(TAG,"Login service Initiated");
        this.activity=activity;
        pd=new ProgressDialog(activity);
        this.pref=activity.getSharedPreferences(Constants.LOGIN_PREF_NAME, Context.MODE_PRIVATE);
        this.editor=pref.edit();
    }

    String doRequest(String url, String accessToken) throws IOException {
        Log.i(TAG,"Login service Post Request");
        Request request=new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+ accessToken)
                .build();
        Log.i(TAG,request.headers().toString());
        Response response=client.newCall(request).execute();
        responseCode=response.code();
        return response.body().string();
    }


    @Override
    protected String doInBackground(String... params) {
        client=new OkHttpClient();
        accessToken=params[0];
        expiresIn=Long.parseLong(params[1]);
        String url= Constants.VALIDATE_LOGIN_URL;
        String jsonResponse=null;
        try {
            jsonResponse=doRequest(url,accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG,"JSON"+jsonResponse);
        return jsonResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(result!=null){
            Log.i(TAG,"Result:" +result);
            UserDetails trueUser=new UserDetails();
            ObjectMapper objectMapper=new ObjectMapper();
            try {
                trueUser=objectMapper.readValue(result,UserDetails.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!trueUser.getError()){
                String userName=trueUser.getUser().getFullName().split(" ")[0];
                BaseActivity.session.createSession(accessToken,expiresIn);
                Log.i(TAG,"Validate Login service Success AccessToken:"+accessToken+"ExpiresIn:"+expiresIn);
                Toast.makeText(activity, "Welcome, "+userName , Toast.LENGTH_SHORT).show();
                BaseActivity.session.saveUser(trueUser.getUser().getEmail(),userName,trueUser.getUser().getRole(),trueUser.getUser().getTypeOfUser(),trueUser.getUser().getMobile(),trueUser.getUser().getUserId());
                activity.startActivity(new Intent(activity, NewBusinessDetailsActivity.class));
            }else{
                Toast.makeText(activity, "Validation Failed. Try Again!!", Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent(activity, LoginActivity.class));
            }
        }
    }
}
