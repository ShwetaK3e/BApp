package com.bzyness.bzyness.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.UserFormValidity;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.DetectNetworkConnectivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.LoginServerResponse;
import com.bzyness.bzyness.models.RegistrationServerResponse;
import com.bzyness.bzyness.services.ServiceGenerator;
import com.bzyness.bzyness.services.ValidateUserService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bzyness.bzyness.BaseActivity.authenticatedBzynessClient;
import static com.bzyness.bzyness.BaseActivity.bzynessClient;

public class RegisterActivity extends AppCompatActivity {
    LinearLayout reg_layout;
    TextInputEditText fullNameEdit,  emailEdit, phoneEdit,passwordEdit;
    TextInputLayout   fullName, email, phone, password;
    Button btnRegister;
    private  static final String TAG= RegisterActivity.class.getSimpleName();

    CheckBox showPasswordButton;
    TextView joinUs;

    private static final String FULL_NAME_PARAM_KEY="fullName";
    private static final String EMAIL_PARAM_KEY="email";
    private static final String PASSWORD_PARAM_KEY="password";
    private static final String PHONE_PARAM_KEY="mobile";

    BroadcastReceiver nonetwork;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_layout=(LinearLayout)findViewById(R.id.reg_layout);

        nonetwork=new DetectNetworkConnectivity() {
            @Override
            protected void onNetworkChange() {
                Snackbar snackbar=Snackbar.make(reg_layout,Constants.NO_NETWORK,Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        };
        IntentFilter filter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(nonetwork,filter);


        fullName=(TextInputLayout)findViewById(R.id.rTextFName);
        fullNameEdit = (TextInputEditText) findViewById(R.id.rEditFName);
        fullNameEdit.addTextChangedListener(new NewUserTextWatcher(fullNameEdit));


        email=(TextInputLayout)findViewById(R.id.rTextEmail);
        emailEdit=(TextInputEditText) findViewById(R.id.rEditEmail);
        emailEdit.addTextChangedListener(new NewUserTextWatcher(emailEdit));


        phone=(TextInputLayout)findViewById(R.id.rTextPhone);
        phoneEdit=(TextInputEditText) findViewById(R.id.rEditPhone);
        phoneEdit.addTextChangedListener(new NewUserTextWatcher(phoneEdit));


        password=(TextInputLayout)findViewById(R.id.rTextPassword);
        passwordEdit=(TextInputEditText) findViewById(R.id.rEditPassword);
        passwordEdit.addTextChangedListener(new NewUserTextWatcher(passwordEdit));


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


        btnRegister=(Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               btnRegister.setEnabled(false);
               register();
            }
        });

        joinUs=(TextView)findViewById(R.id.reg_direction);
        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/allura_regular.ttf");
        joinUs.setTypeface(tf);

        Log.i(TAG,"onCreate");
    }


    void register(){

        if(validateForm()) {
            Log.i(TAG,"register validated");

                final Map<String, String> newUserDetails=new HashMap<>();
                newUserDetails.put(FULL_NAME_PARAM_KEY,fullNameEdit.getText().toString().trim());
                newUserDetails.put(EMAIL_PARAM_KEY,emailEdit.getText().toString().trim());
                newUserDetails.put(PHONE_PARAM_KEY,phoneEdit.getText().toString().trim());
                newUserDetails.put(PASSWORD_PARAM_KEY,passwordEdit.getText().toString().trim());
                if(bzynessClient!=null) {
                    bzynessClient.registerClient(newUserDetails)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<RegistrationServerResponse>() {
                                @Override
                                public void onCompleted() {
                                    Log.i(TAG, "registration completed");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.i(TAG, "error");
                                }

                                @Override
                                public void onNext(RegistrationServerResponse registrationServerResponse) {
                                    if(!registrationServerResponse.getError()){
                                        Map<String,String> user=new HashMap<>();
                                        user.put(EMAIL_PARAM_KEY,newUserDetails.get(EMAIL_PARAM_KEY));
                                        user.put(PASSWORD_PARAM_KEY,newUserDetails.get(PASSWORD_PARAM_KEY));
                                        Log.i(TAG,"registered successfully");
                                        Toast.makeText(RegisterActivity.this,"Successfully Registered",Toast.LENGTH_SHORT).show();
                                        login(user);
                                    }else{
                                        Toast.makeText(RegisterActivity.this,registrationServerResponse.getMessage(),Toast.LENGTH_SHORT).show();
                                        Log.i(TAG,registrationServerResponse.getMessage());
                                    }
                                }
                            });
                }
                //new RegistrationService(this).execute(newUserDetails);
               // addChatUserT(newUser.getUserName(),newUser.getFirstName()+" "+newUser.getLastName(),newUser.getPhoneNumber());

        }else{
            Log.i(TAG,"registration invalidated");
        }

    }

    void login(Map<String,String> user){
        bzynessClient.loginClient(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginServerResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG,"Login Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,e.getMessage());
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(LoginServerResponse loginServerResponse) {
                        if(!loginServerResponse.getError()){
                            authenticatedBzynessClient=ServiceGenerator.createService(ServiceGenerator.BzynessClient.class,loginServerResponse.getAccessToken());
                            Toast.makeText(RegisterActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(RegisterActivity.this, ValidateUserService.class);
                            i.putExtra("serverResponse",loginServerResponse);
                            startService(i);
                            Log.i(TAG,"Login Successfully");
                        }else {
                            Log.i(TAG,loginServerResponse.getMessage());
                            Toast.makeText(RegisterActivity.this, "Try Logging In Again !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        btnRegister.setEnabled(true);
    }



    boolean validateForm(){

        Log.i(TAG,"validating Form");

        if(!UserFormValidity.isName(fullNameEdit, UserFormValidity.REQUIRED)) {
            fullNameEdit.requestFocus();
            fullNameEdit.setSelection(fullNameEdit.getText().toString().length());
            return false;
        }

        if(!UserFormValidity.isEmailAddress(emailEdit, UserFormValidity.REQUIRED)){
            emailEdit.requestFocus();
            emailEdit.setSelection(emailEdit.getText().toString().length());
            return false;
        }
        if(!UserFormValidity.isPhoneNumber(phoneEdit, UserFormValidity.REQUIRED)){
            phoneEdit.requestFocus();
            phoneEdit.setSelection(phoneEdit.getText().toString().length());
            return false;
        }
        if(!UserFormValidity.isPassword(passwordEdit, UserFormValidity.REQUIRED)){
            passwordEdit.requestFocus();
            passwordEdit.setSelection(passwordEdit.getText().toString().length());
            return false;
        }
        return true;
    }

    public void gotoLogin(View v){
        Log.i(TAG,"going to login Page");
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }

    private void addChatUserT(final String userName, final String businessName, final String phoneNo){

        final FirebaseDatabase database=BaseActivity.getDatabase();
        final DatabaseReference reference = database.getReference("Users");
        reference.keepSynced(true);
        reference.child(userName).child(businessName).child("phoneno").setValue(phoneNo);
        reference.child(userName).child(businessName).child("onLine").setValue(true);

        final DatabaseReference presenceRef=database.getReference("Users/"+userName+"/"+businessName+"/onLine");
        presenceRef.onDisconnect().setValue(false);
        final DatabaseReference lastOnlineRef=database.getReference("Users/"+userName+"/"+businessName+"/lastOnline");
        lastOnlineRef.onDisconnect().setValue(ServerValue.TIMESTAMP);

        Toast.makeText(RegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(RegisterActivity.this,ChatUsersActivity.class));
    }

  class NewUserTextWatcher implements TextWatcher {

        private View view;

        private NewUserTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.rEditFName:
                    UserFormValidity.isName(fullNameEdit, UserFormValidity.REQUIRED);
                    break;
                case R.id.rEditEmail:
                    UserFormValidity.isEmailAddress(emailEdit, UserFormValidity.REQUIRED);
                    break;
                case R.id.rEditPhone:
                    UserFormValidity.isPhoneNumber(phoneEdit, UserFormValidity.REQUIRED);
                    break;
                case R.id.rEditPassword:
                    UserFormValidity.isPassword(passwordEdit, UserFormValidity.REQUIRED);
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }
}


