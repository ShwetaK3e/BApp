package com.bzyness.bzyness;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.activity.ChatUsersActivity;
import com.bzyness.bzyness.activity.NewBusinessDetailsActivity;
import com.bzyness.bzyness.activity.RegisterActivity;
import com.bzyness.bzyness.activity.reg;


public class SplashActivity extends AppCompatActivity{

    //Splash Screen Timer
    private static int SPLASH_TIME_OUT = 3000;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        session=new SessionManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                //Start your app main activity
                Intent i;
                if(session.isLoggedIn()){
                    i= new Intent(SplashActivity.this, RegisterActivity.class);
                }else{
                    i= new Intent(SplashActivity.this, RegisterActivity.class);
                }
                SplashActivity.this.startActivity(i);

                // Close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
