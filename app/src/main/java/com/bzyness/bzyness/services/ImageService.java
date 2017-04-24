package com.bzyness.bzyness.services;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;

import com.bzyness.bzyness.activity.UploadImageActivity;
import com.bzyness.bzyness.models.ServerResponse;
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
 * Created by Pervacio on 2/16/2017.
 */

public class ImageService extends AsyncTask<String,Void,String>  {
    public static final MediaType JSON=MediaType.parse("image/png");
    OkHttpClient client;
    String JsonResponse;
    String JsonDATA;
    String url;
    Activity activity;

    public ImageService(Activity activity){
        this.activity=activity;
    }

    String doPostRequest(String url, String json) throws IOException {
      /*  RequestBody requestBody = new MultipartBody.Builder()
                .type(MultipartBody.FORM)
                .addFormDataPart("member_id", memberId)
                .addFormDataPart("file", "profile.png", RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                .build();*/

        RequestBody body= RequestBody.create(JSON,json);
        Request request=new Request.Builder().url(url).post(body).build();
        Response response=client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    protected String doInBackground(String... params) {
        client = new OkHttpClient();
        url=params[0];
        JsonDATA=params[1];
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
        if ("user created".equalsIgnoreCase(result)) {
            activity.startActivity(new Intent(activity, UploadImageActivity.class));
        } else if("no content".equalsIgnoreCase(result)) {
            Log.i("TAG",result);
        }else{
            List<ServerResponse> errorDetails = new ArrayList<ServerResponse>();
            if (result != null) {
                try {
                    errorDetails = objectMapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, ServerResponse.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TextInputEditText txt;
                TextInputLayout txt_layout;
                for (int i = 0; i < errorDetails.size(); i++) {
                    /*if("DuplicateUserName".equalsIgnoreCase(errorDetails.get(i).getCode())){
                        txt=(TextInputEditText)activity.findViewById(R.id.rEditUName);
                        txt_layout=(TextInputLayout)activity.findViewById(R.id.rTextUName);
                        UserFormValidity.userNameExists(txt,txt_layout);
                    }*/
                }

                Log.e("TAG", result + errorDetails.size()); // this is expecting a response code to be sent from your server upon receiving the POST data
            }
        }
    }
}
