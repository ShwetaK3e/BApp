package com.bzyness.bzyness.services;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.PlaceDetails;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.bzyness.bzyness.BaseActivity.objectMapper;

/**
 * Created by Pervacio on 2/7/2017.
 */

public class LocationService extends AsyncTask<String,Void,String> {

    private OkHttpClient client;
    private Activity activity;
    private final String TAG= this.getClass().getSimpleName();

    public LocationService(){
    }

    String doGetRequest(String url) throws IOException {
        Request request=new Request.Builder().url(url).build();
        Response response=client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    protected String doInBackground(String... params) {
        client = new OkHttpClient();
        String url=params[0];
        String locDetResponse=null;
        try {
            locDetResponse = doGetRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locDetResponse;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(result!=null){
            Log.i(TAG,result);
            try {
                PlaceDetails placeDetails=objectMapper.readValue(result,PlaceDetails.class);
                String url="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+placeDetails.getResult().getPhotos().get(0).getPhotoReference()+"&key="+activity.getResources().getString(R.string.google_maps_key);
                new PhotoService().execute(url);
            } catch (Exception e) {
                Log.i(TAG,"error "+ e);
                e.printStackTrace();
            }
        }

    }
}
