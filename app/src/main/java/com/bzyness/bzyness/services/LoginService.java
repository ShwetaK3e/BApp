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
import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.activity.HomeActivity;
import com.bzyness.bzyness.activity.NewBusinessDetailsActivity;
import com.bzyness.bzyness.models.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.Deserializers;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.bzyness.bzyness.BaseActivity.objectMapper;

/**
 *
 * Created by Pervacio on 2/7/2017.
 */

public class LoginService extends AsyncTask<String,Void,String> {

    public static final MediaType URL_ENCODED=MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    OkHttpClient client;
    String userName=null;
    Activity activity;
    int responseCode=0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private final static String TAG=LoginService.class.getSimpleName();


    public LoginService(Activity activity){
        Log.i(TAG,"Login service Initiated");
        this.activity=activity;
        this.pref=activity.getSharedPreferences(Constants.LOGIN_PREF_NAME, Context.MODE_PRIVATE);
        this.editor=pref.edit();
    }

    String doPostRequest(String url, String url_encoded) throws IOException {
        Log.i(TAG,"Login service Post Request");
        RequestBody body= RequestBody.create(URL_ENCODED,url_encoded);
        Request request=new Request.Builder().url(url).post(body).build();
        Response response=client.newCall(request).execute();
        responseCode=response.code();
        return response.body().string();
    }


    @Override
    protected String doInBackground(String... params) {
        Log.i(TAG,"Login service doInBackground");
        client = new OkHttpClient();
        String url=params[0];
        String login_params=params[1];
        userName=params[2];
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
        if( responseCode==200){
            Log.i(TAG,"Login service Success");
            Toast.makeText(activity, "Welcome, "+userName, Toast.LENGTH_SHORT).show();
            editor.putString(Constants.pref_uname,userName);
            editor.commit();
            UserDetails logDetails=new UserDetails();
            ObjectMapper objectMapper=new ObjectMapper();
            try {
                logDetails=objectMapper.readValue(result, UserDetails.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BaseActivity.session.createSession(logDetails.getAccessToken(),logDetails.getExpiresIn());
            Log.i(TAG,"Login service Success AccessToken:"+logDetails.getAccessToken()+"ExpiresIn:"+logDetails.getExpiresIn());
            activity.startActivity(new Intent(activity, HomeActivity.class));
       }else{
            Log.i(TAG, "Login Service error, Response Code:"+ responseCode);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Login...");
        pd.show();
    }
}
