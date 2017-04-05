package com.bzyness.bzyness.services;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bzyness.bzyness.R;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Pervacio on 2/7/2017.
 */

public class PhotoService extends AsyncTask<String,Integer,Drawable> {
    @Override
    protected Drawable doInBackground(String... params) {
        return null;
    }

  /*  OkHttpClient client;
    Activity activity;
    private final String TAG= this.getClass().getSimpleName();

    public PhotoService(Activity activity){
        this.activity=activity;
    }


    InputStream doGetRequest(String url) throws IOException {
        Request request=new Request.Builder().url(url).build();
        Response response=client.newCall(request).execute();
        return response.body().byteStream();
    }

    @Override
    protected Drawable doInBackground(String... params) {

        client = new OkHttpClient();
        String url=params[0];
        InputStream JsonPhotoResponse=null;
        Bitmap bitmap=null;
        Drawable photo=null;
        try {
            JsonPhotoResponse = doGetRequest(url);
            bitmap= BitmapFactory.decodeStream(new FlushedInputStream(JsonPhotoResponse));
            photo= new BitmapDrawable(activity.getResources(),bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo;
    }

    @Override
    protected void onPostExecute(Drawable result) {
        super.onPostExecute(result);
        if(isCancelled()){
            result=null;
        }
        if(result!=null){
            Log.i(TAG,"success");
            try {

                ImageView img= (ImageView)activity.findViewById(R.id.selected_category);
                img.setImageDrawable(result);
            } catch (Exception e) {
                Log.i(TAG,"error "+ e);
                e.printStackTrace();
            }
        }

    }

    *//*
   * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
   *//*
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }*/
}
