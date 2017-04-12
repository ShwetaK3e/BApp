package com.bzyness.bzyness.AppUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.icu.util.Calendar;
import android.util.Log;

import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.activity.LoginActivity;
import com.bzyness.bzyness.models.UserDetails;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.bzyness.bzyness.AppUtils.Constants.pref_Login_Date;
import static com.bzyness.bzyness.AppUtils.Constants.pref_accessToken;
import static com.bzyness.bzyness.AppUtils.Constants.pref_expiresIn;
import static com.bzyness.bzyness.AppUtils.Constants.pref_isFIRST_INSTALLED;
import static com.bzyness.bzyness.AppUtils.Constants.pref_isLoggedIN;
import static com.bzyness.bzyness.AppUtils.Constants.pref_pass;
import static com.bzyness.bzyness.AppUtils.Constants.pref_uname;

/**
 * This class maintains data across the app using the SharedPreferences.
 * We store a boolean flag isLoggedIn in shared preferences to check the login status.
 */

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    UserDetails existingUser;
    private final static String TAG=SessionManager.class.getSimpleName();



    public SessionManager(Context context) {
        Log.i(TAG,"SessionManager started");
        this.context=context;
        pref=context.getSharedPreferences(Constants.LOGIN_PREF_NAME,Context.MODE_PRIVATE);
        editor=pref.edit();
    }

    public void saveUser(String uName, String pass){
        Log.i(TAG,"SessionManager saved User"+ uName+" "+pass);
        editor.putString(pref_uname,uName);
        editor.putString(pref_pass,pass);
        editor.commit();
    }

    public void createSession(String accessToken, long expiresIn ){
        Log.i(TAG,"Session created, accessToken:"+ accessToken+"expiresIn:"+expiresIn);
        editor.putString(pref_accessToken,accessToken);
        editor.putLong(pref_expiresIn,expiresIn);
        editor.putBoolean(pref_isFIRST_INSTALLED,false);
        editor.putBoolean(pref_isLoggedIN,true);
        editor.putLong(pref_Login_Date,new Date().getTime());
        editor.commit();
    }

    public void updateExpiresIn(){
        Log.i(TAG,"SessionManager updated expiresIN");
        Long sessionStartedAt=getLoginDate();
        Long currentDateMS=new Date().getTime();
        Long expiresIn=getExpiresIn();
        editor.putLong(pref_expiresIn,expiresIn-(currentDateMS-sessionStartedAt));
        editor.commit();
        Log.i(TAG,"SessionManager updated expiresIN:"+getExpiresIn());
    }

    public UserDetails getOldSession(){
        existingUser=new UserDetails();
        existingUser.setUserName(getUserName());
        existingUser.setPassword(getPassword());
        Log.i(TAG,"SessionManager started Old Session:"+existingUser.getUserName()+existingUser.getPassword());
        return existingUser;
    }

    public boolean isLoggedIn(){
        updateExpiresIn();
        if(getExpiresIn()<=0)
            editor.putBoolean(pref_isLoggedIN,false);
        Log.i(TAG,"SessionManager isLoggedIn:"+pref.getBoolean(pref_isLoggedIN,false));
        return pref.getBoolean(pref_isLoggedIN,false);
    }

    public boolean isFirstInstalled(){
        Log.i(TAG,"SessionManager isFirstInstalled"+pref.getBoolean(pref_isFIRST_INSTALLED,false));
        return pref.getBoolean(pref_isFIRST_INSTALLED,true);
    }
    public String getUserName(){
        Log.i(TAG,"SessionManager userName:"+pref.getString(pref_uname,null));
        return pref.getString(pref_uname,null);
    }
    public String getPassword(){
        Log.i(TAG,"SessionManager passWord:"+pref.getString(pref_pass,null));
        return pref.getString(pref_pass,null);
    }
    public String getAccessToken(){
            Log.i(TAG,"SessionManager accessstoken:"+pref.getString(pref_accessToken,null));
        return pref.getString(pref_accessToken,null);
    }
    public Long getExpiresIn(){
        Log.i(TAG,"SessionManager expiresIn:"+pref.getLong(pref_expiresIn,0));
        return pref.getLong(pref_expiresIn,0);
    }

    public Long getLoginDate(){
        Log.i(TAG,"SessionManager login date:"+pref.getLong(pref_Login_Date,0));
        return pref.getLong(pref_Login_Date,0);
    }

    public void Logout() {

        editor.remove(pref_accessToken);
        editor.remove(pref_expiresIn);
        editor.putBoolean(pref_isLoggedIN,false);
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}