package com.bzyness.bzyness.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.AppUtils.UserFormValidity;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.services.LoginService;
import com.fasterxml.jackson.databind.deser.Deserializers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText emailEdit, passwordEdit;
    TextInputLayout email, password;
    CheckBox showPasswordButton;
    Button btnLinkToRegScreen;

    SessionManager session;
    private final String TAG = getClass().getSimpleName();
    private TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session=new SessionManager(this);

        email=(TextInputLayout)findViewById(R.id.rTextEmail);
        emailEdit=(TextInputEditText) findViewById(R.id.rEditEmail);
        emailEdit.addTextChangedListener(new LoginFormTextWatcher(emailEdit));

        password=(TextInputLayout)findViewById(R.id.rTextPassword);
        passwordEdit=(TextInputEditText) findViewById(R.id.rEditPassword);
        passwordEdit.addTextChangedListener(new LoginFormTextWatcher(passwordEdit));


       if(!session.isFirstInstalled()){
           emailEdit.setText(session.getEmail());
           passwordEdit.setText(session.getPassword());
        }

        showPasswordButton=(CheckBox)findViewById(R.id.show_password_btn);
        showPasswordButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    showPasswordButton.setText("Hide Password");
                    passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordEdit.requestFocus();
                    passwordEdit.setSelection(passwordEdit.getText().toString().length());
                }else{
                    showPasswordButton.setText("Show Password");
                    passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordEdit.requestFocus();
                    passwordEdit.setSelection(passwordEdit.getText().toString().length());
                }
            }
        });

        signIn=(TextView)findViewById(R.id.log_direction);
        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/allura_regular.ttf");
        signIn.setTypeface(tf);

    }


    public void login(View v){

        if(validateForm()) {
            Log.i(TAG, "Login");
            String uName=emailEdit.getText().toString().trim();
            String password=passwordEdit.getText().toString().trim();
            session.saveUser(uName,password);
                String login_params= BaseActivity.getLoginParams(uName,password);
                new LoginService(this).execute(Constants.LOGIN_URL,login_params,uName);
        }
    }

    boolean validateForm(){
        if(!UserFormValidity.isEmailAddress(emailEdit, UserFormValidity.REQUIRED)){
            emailEdit.requestFocus();
            emailEdit.setSelection(emailEdit.getText().toString().length());
            return false;
        }
        if(!UserFormValidity.isPassword(passwordEdit, UserFormValidity.REQUIRED)){
            passwordEdit.requestFocus();
            passwordEdit.setSelection(passwordEdit.getText().toString().length());
            return false;
        }
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
                   UserFormValidity.isEmailAddress(emailEdit, UserFormValidity.REQUIRED);
                   break;
               case R.id.rEditPassword:
                   UserFormValidity.isPassword(passwordEdit, UserFormValidity.REQUIRED);
                   break;
           }
        }
    }
}
