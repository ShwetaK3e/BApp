package com.bzyness.bzyness.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.SessionManager;
import com.bzyness.bzyness.AppUtils.UserFormValidity;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.ChatUser;
import com.bzyness.bzyness.models.UserDetails;
import com.bzyness.bzyness.services.RegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.Firebase;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText firstNameEdit, lastNameEdit, emailEdit, phoneEdit,userNameEdit, passwordEdit;
    TextInputLayout   firstName, lastName, email, phone,userName, password;
    UserDetails newUser;
    final String TAG= getClass().getSimpleName();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressDialog pd;
    CheckBox showPasswordButton;
    Button btnLinkToLoginScreen;
    SessionManager session;
    TextView joinUs;
    private boolean dontShowPassword=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        pd=new ProgressDialog(this);
        session=new SessionManager(this);
        /*if(session.isFirstInstalled()){
            this.startActivity(new Intent(this, UploadImageActivity.class));
        }*/

        newUser=new UserDetails();
        pref=getSharedPreferences("USERDETAILS",MODE_PRIVATE);
        editor=pref.edit();


        firstName=(TextInputLayout)findViewById(R.id.rTextFName);
        firstNameEdit = (TextInputEditText) findViewById(R.id.rEditFName);
        firstNameEdit.addTextChangedListener(new NewUserTextWatcher(firstNameEdit));


        lastName=(TextInputLayout)findViewById(R.id.rTextLName);
        lastNameEdit = (TextInputEditText) findViewById(R.id.rEditLName);
        lastNameEdit.addTextChangedListener(new NewUserTextWatcher(lastNameEdit));


        email=(TextInputLayout)findViewById(R.id.rTextEmail);
        emailEdit=(TextInputEditText) findViewById(R.id.rEditEmail);
        emailEdit.addTextChangedListener(new NewUserTextWatcher(emailEdit));


        phone=(TextInputLayout)findViewById(R.id.rTextPhone);
        phoneEdit=(TextInputEditText) findViewById(R.id.rEditPhone);
        phoneEdit.addTextChangedListener(new NewUserTextWatcher(phoneEdit));


        userName=(TextInputLayout)findViewById(R.id.rTextUName);
        userNameEdit=(TextInputEditText) findViewById(R.id.rEditUName);
        userNameEdit.addTextChangedListener(new NewUserTextWatcher(userNameEdit));


        password=(TextInputLayout)findViewById(R.id.rTextPassword);
        passwordEdit=(TextInputEditText) findViewById(R.id.rEditPassword);
        passwordEdit.addTextChangedListener(new NewUserTextWatcher(passwordEdit));


        showPasswordButton=(CheckBox)findViewById(R.id.show_password_btn);
        showPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dontShowPassword){
                    showPasswordButton.setText("Show Password");
                    passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordEdit.requestFocus();
                    passwordEdit.setSelection(passwordEdit.getText().toString().length());
                    dontShowPassword=false;
                }else{
                    showPasswordButton.setText("Hide Password");
                    passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordEdit.requestFocus();
                    passwordEdit.setSelection(passwordEdit.getText().toString().length());
                    dontShowPassword=true;
                }
            }
        });

        btnLinkToLoginScreen=(Button)findViewById(R.id.btnLinkToLoginScreen);
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/face_your_fears.ttf");
        btnLinkToLoginScreen.setTypeface(tf);

        joinUs=(TextView)findViewById(R.id.reg_direction);
        tf= Typeface.createFromAsset(getAssets(),"fonts/allura_regular.ttf");
        joinUs.setTypeface(tf);

    }


    public void register(View v){

        if(validateForm()) {
            try {
                newUser.setFirstName(firstNameEdit.getText().toString().trim());
                newUser.setLastName(lastNameEdit.getText().toString().trim());
                newUser.setUserName(userNameEdit.getText().toString().trim());
                newUser.setEmail(emailEdit.getText().toString().trim());
                newUser.setPhoneNumber(phoneEdit.getText().toString().trim());
                newUser.setPassword(passwordEdit.getText().toString().trim());
                newUser.setConfirmPassword(newUser.getPassword());
                ObjectMapper objectMapper=new ObjectMapper();
                String jsonNewUserDetStr=objectMapper.writeValueAsString(newUser);
                new RegistrationService(this).execute(Constants.REGISTRATION_URL,jsonNewUserDetStr,newUser.getFirstName(),TAG);
               // addChatUserT(newUser.getUserName(),newUser.getFirstName()+" "+newUser.getLastName(),newUser.getPhoneNumber());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    boolean validateForm(){
        if(!UserFormValidity.isName(firstNameEdit, UserFormValidity.REQUIRED)) {
            firstNameEdit.requestFocus();
            firstNameEdit.setSelection(firstNameEdit.getText().toString().length());
            return false;
        }
        if(!UserFormValidity.isName(lastNameEdit, UserFormValidity.REQUIRED)){
            lastNameEdit.requestFocus();
            lastNameEdit.setSelection(lastNameEdit.getText().toString().length());
            return false;
        }
        if(!UserFormValidity.isName(userNameEdit, UserFormValidity.REQUIRED)){
            userNameEdit.requestFocus();
            userNameEdit.setSelection(userNameEdit.getText().toString().length());
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
                    UserFormValidity.isName(firstNameEdit, UserFormValidity.REQUIRED);
                    break;
                case R.id.rEditLName:
                    UserFormValidity.isName(lastNameEdit, UserFormValidity.REQUIRED);
                    break;
                case R.id.rEditEmail:
                    UserFormValidity.isEmailAddress(emailEdit, UserFormValidity.REQUIRED);
                    break;
                case R.id.rEditUName:
                    UserFormValidity.isName(userNameEdit, UserFormValidity.REQUIRED);
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


