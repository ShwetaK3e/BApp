package com.bzyness.bzyness;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Pervacio on 4/26/2017.
 */

public abstract class DetectNetworkConnectivity extends BroadcastReceiver {

        public static final String TAG = DetectNetworkConnectivity.class.getSimpleName();
        Context mContext;

        @Override
        public void onReceive(Context context, Intent intent) {
           mContext=context;
            if(!isOnline()){
                onNetworkChange();
            }
        }

    protected  abstract  void onNetworkChange();

    public boolean isOnline() {
            ConnectivityManager cm = (ConnectivityManager)mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

 }



