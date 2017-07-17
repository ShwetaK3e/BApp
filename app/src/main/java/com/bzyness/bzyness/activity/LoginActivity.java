package com.bzyness.bzyness.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.AppUtils.UserFormValidity;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.DetectNetworkConnectivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.LoginServerResponse;
import com.bzyness.bzyness.services.LoginService;
import com.bzyness.bzyness.services.ServiceGenerator;
import com.bzyness.bzyness.services.ValidateUserService;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bzyness.bzyness.BaseActivity.authenticatedBzynessClient;
import static com.bzyness.bzyness.BaseActivity.bzynessClient;

public class LoginActivity extends AppCompatActivity {

    LinearLayout log_layout;
    TextInputEditText emailEdit, passwordEdit;
    TextInputLayout email, password;
    CheckBox showPasswordButton;
    Button btnLinkToRegScreen;

    private static final String TAG = LoginActivity.class.getSimpleName();
    private TextView signIn;
    BroadcastReceiver nonetwork;

    private static final String EMAIL_PARAM_KEY="email";
    private static final String PASSWORD_PARAM_KEY="password";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BaseActivity.session=new SessionManager(this);
        log_layout=(LinearLayout)findViewById(R.id.log_layout);

        nonetwork=new DetectNetworkConnectivity() {
            @Override
            protected void onNetworkChange() {
                Snackbar snackbar=Snackbar.make(log_layout,Constants.NO_NETWORK,Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        };
        IntentFilter filter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(nonetwork,filter);

        email=(TextInputLayout)findViewById(R.id.rTextEmail);
        emailEdit=(TextInputEditText) findViewById(R.id.rEditEmail);
        emailEdit.addTextChangedListener(new LoginFormTextWatcher(emailEdit));

        password=(TextInputLayout)findViewById(R.id.rTextPassword);
        passwordEdit=(TextInputEditText) findViewById(R.id.rEditPassword);
        passwordEdit.addTextChangedListener(new LoginFormTextWatcher(passwordEdit));


       if(!BaseActivity.session.isFirstInstalled()){
           emailEdit.setText(BaseActivity.session.getEmail());
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
            String login_params=BaseActivity.getLoginParams(emailEdit.getText().toString().trim(),passwordEdit.getText().toString().trim());
            Map<String, String> user= new HashMap<>();
            user.put(EMAIL_PARAM_KEY,emailEdit.getText().toString().trim());
            user.put(PASSWORD_PARAM_KEY,passwordEdit.getText().toString().trim());
            if(bzynessClient!=null){
                bzynessClient.loginClient(user)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<LoginServerResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG,e.getMessage());
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(LoginServerResponse loginServerResponse) {


                                if(!loginServerResponse.getError()){
                                    authenticatedBzynessClient=ServiceGenerator.createService(ServiceGenerator.BzynessClient.class,loginServerResponse.getAccessToken());
                                    Log.i(TAG,loginServerResponse.getAccessToken());
                                    Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(LoginActivity.this, ValidateUserService.class);
                                    i.putExtra("serverResponse",loginServerResponse);
                                    startService(i);
                                }else {
                                    Toast.makeText(LoginActivity.this, "Try Again !!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

           // new LoginService(this).execute(login_params);
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
               case R.id.rEditEmail:
                   UserFormValidity.isEmailAddress(emailEdit, UserFormValidity.REQUIRED);
                   break;
               case R.id.rEditPassword:
                   UserFormValidity.isPassword(passwordEdit, UserFormValidity.REQUIRED);
                   break;
           }
        }
    }

}
