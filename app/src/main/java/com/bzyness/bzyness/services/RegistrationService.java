package com.bzyness.bzyness.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.util.Log;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.ServerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Pervacio on 2/7/2017.
 */

public class RegistrationService extends AsyncTask<Map<String,String>,Void,String> {

    OkHttpClient client;
    Activity activity;
    private int responseCode;
    ProgressDialog pd;
    String email, password;
    private final static String TAG = RegistrationService.class.getSimpleName();
    private final static String EMAIL_ERROR_MSG = "Sorry, this user already exists";
    private final static String PHONE_ERROR_MSG = "Oops! An error occurred while registering";


    public RegistrationService(Activity activity) {
        Log.i(TAG, "Registration service Initiated");
        this.activity = activity;
        this.pd = new ProgressDialog(activity);
    }

    String doPostRequest(String url, Map<String, String> params) throws IOException {
        Log.i(TAG, "Registration service post Request");
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
            if ("email".equalsIgnoreCase(entry.getKey())) {
                email = entry.getValue();
            } else if ("password".equalsIgnoreCase(entry.getKey())) {
                password = entry.getValue();
            }
            Log.i(TAG, "Registration params: " + entry.getKey() + "  " + entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        responseCode = response.code();
        return response.body().string();
    }

    @Override
    protected String doInBackground(Map<String, String>... params) {

        client = new OkHttpClient();
        String url = Constants.REGISTRATION_URL;
        String JsonResponse = null;
        try {
            JsonResponse = doPostRequest(url, params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResponse;
    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG, "Registration service preExecute");
        super.onPreExecute();
        pd.setCancelable(false);
        pd.show();
    }


    @Override
    protected void onPostExecute(String result) {
        Log.i(TAG, "Registration service postExecute");
        super.onPostExecute(result);
        pd.dismiss();
        if (result != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            ServerResponse serverResponse = new ServerResponse();
            try {
                serverResponse = objectMapper.readValue(result, ServerResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            TextInputEditText txtInputEditTxt = null;
            if (serverResponse.getError()) {
                if (EMAIL_ERROR_MSG.equalsIgnoreCase(serverResponse.getMessage())) {
                    txtInputEditTxt = (TextInputEditText) activity.findViewById(R.id.rEditEmail);
                    Log.i(TAG, "Registration service , email error ");
                } else if (PHONE_ERROR_MSG.equalsIgnoreCase(serverResponse.getMessage())) {
                    txtInputEditTxt = (TextInputEditText) activity.findViewById(R.id.rEditPhone);
                    Log.i(TAG, "Registration service , phone error ");
                }
                txtInputEditTxt.setText("");
                txtInputEditTxt.requestFocus();
            } else {
                String login_params= BaseActivity.getLoginParams(email,password);
                new LoginService(activity).execute(login_params,email);
                Log.i(TAG, "Registration service , success");
            }
            Log.i(TAG, "Registration service , responseCode:" + responseCode);
        }else {
            Log.i(TAG, "Registration service , null result");
        }
    }
}
