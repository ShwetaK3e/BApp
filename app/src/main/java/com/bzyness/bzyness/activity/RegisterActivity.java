package com.bzyness.bzyness.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.AppUtils.UserFormValidity;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.UserDetails;
import com.bzyness.bzyness.services.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText fullNameEdit,  emailEdit, phoneEdit,passwordEdit;
    TextInputLayout   fullName, email, phone, password;
    UserDetails newUser;
    final String TAG= getClass().getSimpleName();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressDialog pd;
    CheckBox showPasswordButton;
    TextView joinUs;

    private static final String FULL_NAME_PARAM_KEY="fullName";
    private static final String EMAIL_PARAM_KEY="email";
    private static final String PASSWORD_PARAM_KEY="password";
    private static final String PHONE_PARAM_KEY="mobile";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pd=new ProgressDialog(this);

        /*if(session.isFirstInstalled()){
            this.startActivity(new Intent(this, UploadImageActivity.class));
        }*/

        newUser=new UserDetails();
        pref=getSharedPreferences(Constants.LOGIN_PREF_NAME,MODE_PRIVATE);
        editor=pref.edit();


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

        joinUs=(TextView)findViewById(R.id.reg_direction);
        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/allura_regular.ttf");
        joinUs.setTypeface(tf);

    }


    public void register(View v){

        if(validateForm()) {
            try {
                newUser.setFullName(fullNameEdit.getText().toString().trim());
                newUser.setEmail(emailEdit.getText().toString().trim());
                newUser.setPhone(phoneEdit.getText().toString().trim());
                newUser.setPassword(passwordEdit.getText().toString().trim());

                Map<String, String> newUserDetails=new HashMap<>();
                newUserDetails.put(FULL_NAME_PARAM_KEY,fullNameEdit.getText().toString().trim());
                newUserDetails.put(EMAIL_PARAM_KEY,emailEdit.getText().toString().trim());
                newUserDetails.put(PHONE_PARAM_KEY,phoneEdit.getText().toString().trim());
                newUserDetails.put(PASSWORD_PARAM_KEY,passwordEdit.getText().toString().trim());
                new RegistrationService(this).execute(newUserDetails);
               // addChatUserT(newUser.getUserName(),newUser.getFirstName()+" "+newUser.getLastName(),newUser.getPhoneNumber());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    boolean validateForm(){
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

        editor.putString("USERNAME",userName);
        editor.putString("BUSINESSNAME",businessName);
        editor.commit();
        Toast.makeText(RegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
        pd.dismiss();
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


