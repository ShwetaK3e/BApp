package com.bzyness.bzyness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.AppUtils.UserFormValidity;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.UserDetails;
import com.bzyness.bzyness.services.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText userNameEdit, passwordEdit;
    TextInputLayout userName, password;


    SessionManager session;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session=new SessionManager(this);

        userName=(TextInputLayout)findViewById(R.id.rTextUName);
        userNameEdit=(TextInputEditText) findViewById(R.id.rEditUName);
        userNameEdit.addTextChangedListener(new LoginFormTextWatcher(userNameEdit));

        password=(TextInputLayout)findViewById(R.id.rTextPassword);
        passwordEdit=(TextInputEditText) findViewById(R.id.rEditPassword);
        passwordEdit.addTextChangedListener(new LoginFormTextWatcher(passwordEdit));


       if(!session.isFirstInstalled()){
           userNameEdit.setText(session.getOldSession().getUserName());
           passwordEdit.setText(session.getOldSession().getPassword());
        }
    }


    public void login(View v){

        if(validateForm()) {
            Log.i(TAG, "Login");
            String name=userNameEdit.getText().toString().trim();
            String password=passwordEdit.getText().toString().trim();
            session.saveUser(name,password);
                String login_params="";
                try {
                    login_params = URLEncoder.encode("username", "UTF-8")
                            + "=" + URLEncoder.encode(name, "UTF-8");

                    login_params += "&" + URLEncoder.encode("password", "UTF-8") + "="
                            + URLEncoder.encode(password, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                new LoginService(this,this.session).execute(Constants.LOGIN_URL,login_params,name);

        }
    }

    boolean validateForm(){
        if(!UserFormValidity.isName(userNameEdit, UserFormValidity.REQUIRED))return false;
        if(!UserFormValidity.isPassword(passwordEdit, UserFormValidity.REQUIRED))return false;
        return true;
    }


    public void gotoRegister(View v){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }


    private class LoginFormTextWatcher implements TextWatcher{

        View view;

        LoginFormTextWatcher(View view){
          this.view=view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int casestart, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
           switch (view.getId()){
               case R.id.rEditUName:
                   UserFormValidity.isName(userNameEdit, UserFormValidity.REQUIRED);
                   break;
               case R.id.rEditPassword:
                   UserFormValidity.isPassword(passwordEdit, UserFormValidity.REQUIRED);
                   break;
           }
        }
    }
}
