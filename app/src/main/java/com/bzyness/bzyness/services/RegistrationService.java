package com.bzyness.bzyness.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.widget.Toast;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.UserFormValidity;
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
    private String userName;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressDialog pd;

    public RegistrationService(Activity activity){
        this.activity=activity;
        this.pref=activity.getSharedPreferences(Constants.USERDETAILS, Context.MODE_PRIVATE);
        this.editor=pref.edit();
        this.pd = new ProgressDialog(activity);
    }

    String doPostRequest(String url, String json) throws IOException {
        RequestBody body= RequestBody.create(JSON,json);
        Request request=new Request.Builder().url(url).post(body).build();
        Response response=client.newCall(request).execute();
        responseCode=response.code();
        return response.body().string();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        client = new OkHttpClient();
        String url=params[0];
        String JsonDATA=params[1];
        userName=params[2];
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
        super.onPostExecute(result);
        if (responseCode==200) {
            editor.putString(Constants.USERNAME,userName);
            editor.commit();
            pd.dismiss();
            Toast.makeText(activity, "Welcome, "+userName, Toast.LENGTH_SHORT).show();
            activity.startActivity(new Intent(activity, LoginActivity.class));
        }else{
            List<ServerError> errorDetails = new ArrayList<ServerError>();
            if (result != null) {
                try {
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
                }

            }
        }
    }

}
