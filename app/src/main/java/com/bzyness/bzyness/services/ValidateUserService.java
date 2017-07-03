package com.bzyness.bzyness.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.activity.NewBusinessDetailsActivity;
import com.bzyness.bzyness.models.LoginServerResponse;
import com.bzyness.bzyness.models.ServerResponse;
import com.bzyness.bzyness.models.UserDetails;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ValidateUserService extends Service {

    private static final String TAG=ValidateUserService.class.getSimpleName();


    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        final LoginServerResponse loginServerResponse=intent.getExtras().getParcelable("serverResponse");
        Log.i(TAG,"accesstoken"+loginServerResponse.getAccessToken());
        if(BaseActivity.authenticatedBzynessClient!=null) {
            Log.i(TAG,"expiresIN"+loginServerResponse.getExpiresIn()+" ");
            BaseActivity.authenticatedBzynessClient.validateClient().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UserDetails>() {
                        @Override
                        public void onCompleted() {
                            Log.i(TAG,"validation completed");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG,"Error "+ e.getMessage());
                        }

                        @Override
                        public void onNext(UserDetails userDetails) {
                            Log.i(TAG,"on Next() ");
                            if(!userDetails.getError()) {
                                BaseActivity.session.createSession(loginServerResponse.getAccessToken(),loginServerResponse.getExpiresIn());
                                BaseActivity.session.saveUser(userDetails.getUser().getEmail(), userDetails.getUser().getFullName(), userDetails.getUser().getRole(), userDetails.getUser().getTypeOfUser(), userDetails.getUser().getMobile(), userDetails.getUser().getUserId());
                                Log.i(TAG,userDetails.getMessage());
                                Intent i = new Intent(ValidateUserService.this, NewBusinessDetailsActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                            }else{
                                Log.i(TAG,userDetails.getMessage());
                            }
                        }
                    });
        }

        return START_NOT_STICKY;
    }


}
