package com.bzyness.bzyness.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.Toast;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.AppUtils.UserFormValidity;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.activity.LoginActivity;
import com.bzyness.bzyness.activity.UploadImageActivity;
import com.bzyness.bzyness.models.ServerError;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.bzyness.bzyness.BaseActivity.objectMapper;

/**
 * Created by Pervacio on 2/7/2017.
 */

public class RegistrationService extends AsyncTask<String,Void,String> {

    public static MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client;
    Activity activity;
    private int responseCode;
    private String userName,password;
    ProgressDialog pd;
    private final static String TAG= RegistrationService.class.getSimpleName();

    public RegistrationService(Activity activity){
        Log.i(TAG,"Registration service Initiated");
        this.activity=activity;
        this.pd = new ProgressDialog(activity);
    }

    String doPostRequest(String url, String json) throws IOException {
        Log.i(TAG,"Registration service post Request");
        RequestBody body= RequestBody.create(JSON,json);
        Request request=new Request.Builder().url(url).post(body).build();
        Response response=client.newCall(request).execute();
        responseCode=response.code();
        return response.body().string();
    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG,"Registration service preExecute");
        super.onPreExecute();
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        Log.i(TAG,"Registration service doInBackGround");
        client = new OkHttpClient();
        String url=params[0];
        String JsonDATA=params[1];
        userName=params[2];
        password=params[3];
        String JsonResponse=null;
        try {
             JsonResponse = doPostRequest(url, JsonDATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResponse;
}

    @Override
    protected void onPostExecute(String result) {
        Log.i(TAG,"Registration service postExecute");
        super.onPostExecute(result);
        if (responseCode==200) {
            Log.i(TAG,"Registration service Success");
            String login_params= BaseActivity.getLoginParams(userName,password);
            new LoginService(activity).execute(Constants.LOGIN_URL,login_params,userName);
            pd.dismiss();
        }else{
            pd.dismiss();
            List<ServerError> errorDetails = new ArrayList<ServerError>();
            if (result != null) {
                /*try {
                    errorDetails = objectMapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, ServerError.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TextInputEditText txt;
                TextInputLayout txt_layout;
                for (int i = 0; i < errorDetails.size(); i++) {
                    if("DuplicateUserName".equalsIgnoreCase(errorDetails.get(i).getErrorDescription())){
                        txt=(TextInputEditText)activity.findViewById(R.id.rEditUName);
                        txt_layout=(TextInputLayout)activity.findViewById(R.id.rTextUName);
                        UserFormValidity.userNameExists(txt,txt_layout);
                    }
                }*/

            }
            Log.i(TAG,"Registration service Error , responseCode:" + responseCode);

        }
    }
}
