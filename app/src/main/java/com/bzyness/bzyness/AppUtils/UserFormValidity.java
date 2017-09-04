package com.bzyness.bzyness.AppUtils;

import android.support.design.widget.TextInputEditText;

import java.util.regex.Pattern;

/**
 * Created by Pervacio on 2/13/2017.
 */

public class UserFormValidity {

    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{10}";
    private static final String NAME_REGEX="^[a-zA-Z\\s]+$";
    private static final String PASSWORD_REGEX="^(?=(.*\\d){1})(?=.*[A-Z])(?=.*[!@#$%])[0-9a-zA-Z!@#$%]{8,}";

    //Required value
    public static final boolean REQUIRED=true;
    public static final boolean NOT_REQUIRED=false;

    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PHONE_MSG = "##########";
    private static final String NAME_MSG = "invalid name";
    private static final String PASSWORD_MSG = "1 special char| 1 Caps | 1 Numeric (8)";


    //Error Messages from Server-side
    private static final String SAME_PHONE_NO_MSG = "This Phone No. is already Registered.";
    private static final String SAME_EMAIL_MSG = "This Email is already Registered.";
    private static final String SAME_EMAIL_ISSUE = "EMAIL";
    private static final String SAME_PHONE_ISSUE = "PHONE";



    // Error Login Messages
    private static final String INVALID_UNAME_MSG = "This username doesn't exist.";
    private static final String INVALID_PASSWORD_MSG = "Password doesn't match";




    // call this method when you need to check email validation
    public static boolean isEmailAddress(TextInputEditText editText, boolean required) {
        return isValid(editText,EMAIL_REGEX, EMAIL_MSG, required);
    }

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(TextInputEditText editText, boolean required) {
        return isValid(editText,PHONE_REGEX, PHONE_MSG, required);
    }

    // call this method when you need to check user name validation
    public static boolean isName(TextInputEditText editText, boolean required) {
        return isValid(editText, NAME_REGEX, NAME_MSG, required);
    }


    // call this method when you need to check password validation
    public static boolean isPassword(TextInputEditText editText, boolean required) {
        return isValid(editText, PASSWORD_REGEX,PASSWORD_MSG, required);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(TextInputEditText editText,String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other value
        editText.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }

        return true;
    }



    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(TextInputEditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }


    public static void userExists(TextInputEditText editText, String duplicateIssue){
        editText.setText("");
        if(SAME_EMAIL_ISSUE.equalsIgnoreCase(duplicateIssue)){
            editText.setError(SAME_EMAIL_MSG);
        }else if(SAME_PHONE_ISSUE.equalsIgnoreCase(duplicateIssue)){
            editText.setError(SAME_PHONE_NO_MSG);
        }
        editText.requestFocus();

    }



    public static void invalidLoginCredentials(TextInputEditText userName, TextInputEditText password){
        userName.setText("");
        password.setText("");
        userName.setError(INVALID_UNAME_MSG);
        password.setError(INVALID_PASSWORD_MSG);
        userName.requestFocus();
    }

}
