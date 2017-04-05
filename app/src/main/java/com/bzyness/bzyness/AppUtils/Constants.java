package com.bzyness.bzyness.AppUtils;

/**
 * Created by Pervacio on 2/14/2017.
 */

public class Constants {
    public static final String REGISTRATION_URL="http://coreenginex.azurewebsites.net/api/account/register";
    public static final String LOGIN_URL="http://coreenginex.azurewebsites.net/Token";
    public static final String UPLOAD_IMAGE_URL="http://coreenginex.azurewebsites.net/api/Account/AddProfilePicture";
    public static final String CREATE_BUSINESS_URL="http://coreenginex.azurewebsites.net/api/BListing/Create";

    // LOG/ INTENT TAGS
    public static final String TYPE="type";

    //FireBase urls
    public static final String REGISTER_FIREBASE_URL="https://chatappfirebase-e48ea.firebaseio.com/users.json";

    //Intent Request constants
    public static final int PICK_PROFILE_IMAGE_REQUEST=100;
    public static final int PICK_LOGO_IMAGE_REQUEST=101;
    public static final int PLACE_PICKER_REQUEST = 1001;
    public static final int PICK_IMAGE1_REQUEST = 1;
    public static final int PICK_IMAGE2_REQUEST = 2;
    public static final int PICK_IMAGE3_REQUEST = 3;
    public static final int PICK_IMAGE4_REQUEST = 4;
    public static final int PICK_IMAGE5_REQUEST = 5;

    //chat Urls
    public static final String CHAT_USERS_TABLE = "https://bzyness-84772.firebaseio.com/users";
    public static final String CHAT_USERS_TABLE_CREATE_URL = "https://bzyness-84772.firebaseio.com/users.json";
    public static final String CHAT_MESSAGE_TABLE_CREATE_URL = "https://bzyness-84772.firebaseio.com/messages/";


    //SharedPreferences
    public static final String USERDETAILS="USERDETAILS";
    public static final String USERNAME="USERNAME";
    public static final String BUSINESSNAME="BUSINESSNAME";


}
