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

/**
 * This class maintains data across the app using the SharedPreferences.
 * We store a boolean flag isLoggedIn in shared preferences to check the login status.
 */

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public  static final String LOGIN_PREF_NAME="LOGIN_DETAILS";
    public static final String pref_uname="USERNAME";
    public static final String pref_pass="PASSWORD";
    public static final String pref_accessToken="ACCES_TOKEN";
    public static final String pref_expiresIn="EXPIRES_IN";
    public static final String pref_isLoggedIN="IS_LOGGEDIN";
    public static final String pref_isFIRST_INSTALLED="IS_FIRSTINSTALLED";
    public static final String pref_Login_Date="LOGIN_DATE";

    Context context;

    UserDetails existingUser;
    Calendar calender;


    public SessionManager(Context context) {
        context=context;
        pref=context.getSharedPreferences(LOGIN_PREF_NAME,Context.MODE_PRIVATE);
        editor=pref.edit();
       // calender=Calendar.getInstance();
    }

    public void saveUser(String uName, String pass){
        editor.putString(pref_uname,uName);
        editor.putString(pref_pass,pass);
        editor.commit();
    }

    public void createSession(String accessToken, long expiresIn ){
        editor.putString(pref_accessToken,accessToken);
        editor.putLong(pref_expiresIn,expiresIn);
        editor.putBoolean(pref_isLoggedIN,true);
       // editor.putLong(pref_Login_Date, calender.get(Calendar.MILLISECOND));
        editor.commit();
    }

    public void updateExpiresIn(){
       // Long expires_in=1296000-(calender.get(Calendar.MILLISECOND)-pref.getLong(pref_Login_Date,calender.get(Calendar.MILLISECOND)));
       // editor.putLong(pref_expiresIn,expires_in);
        editor.commit();
    }

    public UserDetails getOldSession(){
        existingUser=new UserDetails();
        existingUser.setUserName(pref.getString(pref_uname,null));
        existingUser.setPassword(pref.getString(pref_pass,null));
        return existingUser;
    }

    public boolean isLoggedIn(){
        updateExpiresIn();
        if(getExpiresIn()<=0)
            editor.putBoolean(pref_isLoggedIN,false);
        return pref.getBoolean(pref_isLoggedIN,false);
    }

    public boolean isFirstInstalled(){
        return pref.getBoolean(pref_isFIRST_INSTALLED,true);
    }
    public String getUserName(){
        return pref.getString(pref_uname,null);
    }
    public String getAccessToken(){
        return pref.getString(pref_accessToken,null);
    }
    public int getExpiresIn(){
        return pref.getInt(pref_expiresIn,0);
    }

    public void Logout() {

        editor.remove(pref_accessToken);
        editor.remove(pref_expiresIn);
        editor.putBoolean(pref_isLoggedIN,false);
        editor.putBoolean(pref_isFIRST_INSTALLED,false);
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }


}
