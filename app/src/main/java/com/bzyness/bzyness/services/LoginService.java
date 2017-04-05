package com.bzyness.bzyness.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.activity.NewBusinessDetailsActivity;
import com.bzyness.bzyness.models.UserDetails;

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

    public static final MediaType JSON=MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    OkHttpClient client;
    String userName=null;
    Activity activity;
    int responseCode=0;
    SessionManager session;


    public LoginService(Activity activity, SessionManager session){
        this.activity=activity;
        this.session=session;
    }

    String doPostRequest(String url, String json) throws IOException {
        RequestBody body= RequestBody.create(JSON,json);
        Request request=new Request.Builder().url(url).post(body).build();
        Response response=client.newCall(request).execute();
        responseCode=response.code();
        return response.body().string();
    }


    @Override
    protected String doInBackground(String... params) {
        client = new OkHttpClient();
        String url=params[0];
        String JsonDATA=params[1];
        String JsonResponse=null;
        try {
            JsonResponse = doPostRequest(url,JsonDATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResponse;

}

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if( responseCode==200){
            Toast.makeText(activity, "Welcome, "+userName, Toast.LENGTH_SHORT).show();
            UserDetails logDetails=new UserDetails();
            try {
                logDetails=objectMapper.readValue(result, UserDetails.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            session.createSession(logDetails.getAccessToken(),logDetails.getExpiresIn());
            activity.startActivity(new Intent(activity, NewBusinessDetailsActivity.class));
       }else{
            Log.i("TAG", "Login failed "+ responseCode);
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
