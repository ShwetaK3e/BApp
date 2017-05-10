package com.bzyness.bzyness.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.activity.HomeActivity;
import com.bzyness.bzyness.activity.NewBusinessDetailsActivity;
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
        if(result!=null) {
            ServerResponse serverResponse = new ServerResponse();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                serverResponse = objectMapper.readValue(result, ServerResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (serverResponse != null) {
                if (!serverResponse.getError()) {
                    pd.dismiss();
                    Log.i(TAG, serverResponse.getAccessToken());
                    //requestWithSomeHttpHeaders(serverResponse.getAccessToken());
                    new ValidateLoginService(activity).execute(serverResponse.getAccessToken(),Long.toString(serverResponse.getExpiresIn()));
                } else {
                    pd.dismiss();
                    TextInputEditText txtInputEditTxt1 = null, txtInputEditTxt2 = null;
                    txtInputEditTxt1 = (TextInputEditText) activity.findViewById(R.id.rEditEmail);
                    txtInputEditTxt2 = (TextInputEditText) activity.findViewById(R.id.rEditPassword);
                    Log.i(TAG, "Login service , email error ");
                    txtInputEditTxt1.setText("");
                    txtInputEditTxt2.setText("");
                    txtInputEditTxt1.requestFocus();
                    Toast.makeText(activity, "Sorry,Invalid Details. ", Toast.LENGTH_SHORT).show();
                }
            } else {
                pd.dismiss();
                Log.i(TAG, "serverResponse is null");
            }
        }

        Log.i(TAG, "Login Service response, Response Code:"+ responseCode);
        }

    public void requestWithSomeHttpHeaders(final String accessToken) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = Constants.VALIDATE_LOGIN_URL;
        final StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d(TAG+"Success Response", response);

                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(TAG+"ERROR","error => "+error.toString()+" Code:"+error.networkResponse.statusCode);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                Log.i(TAG,"headers");
                params.put("Authorization", "Bearer "+ accessToken);
                Log.i(TAG,"headers1");
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        /*try {
            for( Map.Entry entry: postRequest.getHeaders().entrySet()) {
                Log.i(TAG, entry.getKey()+ " : "+entry.getValue());
            }
        } catch (AuthFailureError authFailureError) {
            Log.i(TAG,authFailureError.getMessage());
            authFailureError.printStackTrace();
        }*/
        queue.add(postRequest);

    }

}
