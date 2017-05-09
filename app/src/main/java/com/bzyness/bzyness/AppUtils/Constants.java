package com.bzyness.bzyness.AppUtils;

/**
 * Created by Pervacio on 2/14/2017.
 */

public class Constants {
    public static final String REGISTRATION_URL="http://api.bzyness.com/v1/createUser";
    public static final String LOGIN_URL="http://api.bzyness.com/v1/userLogin";
    public static final String VALIDATE_LOGIN_URL="http://api.bzyness.com/v1/validateLogin";
    public static final String BUSINESS_TYPE_URL="http://api.bzyness.com/v1/typesOfBzyness";
    public static final String BUSINESS_CATEGORY_URL="http://api.bzyness.com/v1/chooseCategory/";
    public static final String UPLOAD_IMAGE_URL="http://coreenginex.azurewebsites.net/api/Account/AddProfilePicture";
    public static final String CREATE_BUSINESS_URL="http://coreenginex.azurewebsites.net/api/BListing/Create";

    // LOG/ INTENT TAGS
    public static final String TYPE="type";

    //FireBase urls
    public static final String REGISTER_FIREBASE_URL="https://chatappfirebase-e48ea.firebaseio.com/users.json";

    //Intent Request constants
    public static final int PICK_PROFILE_IMAGE_REQUEST=100;
    public static final int PICK_LOGO_IMAGE_REQUEST=101;
    public static final int PLACE_PICKER_REQUEST = 102;
    public static final int PICK_IMAGE1_REQUEST = 103;
    public static final int PICK_IMAGE2_REQUEST = 104;
    public static final int PICK_IMAGE3_REQUEST = 105;
    public static final int PICK_IMAGE4_REQUEST = 106;
    public static final int PICK_IMAGE5_REQUEST = 107;
    public static final int PICK_PRODUCT_IMG_REQUEST = 108;

    //chat Urls
    public static final String CHAT_USERS_TABLE = "https://bzyness-84772.firebaseio.com/users";
    public static final String CHAT_USERS_TABLE_CREATE_URL = "https://bzyness-84772.firebaseio.com/users.json";
    public static final String CHAT_MESSAGE_TABLE_CREATE_URL = "https://bzyness-84772.firebaseio.com/messages/";


    //SharedPreferences

    public  static final String LOGIN_PREF_NAME="LOGIN_DETAILS";
    public static final String pref_uname="USERNAME";
    public static final String pref_email="EMAIL";
    public static final String pref_accessToken="ACCES_TOKEN";
    public static final String pref_expiresIn="EXPIRES_IN";
    public static final String pref_role="ROLE";
    public static final String pref_type_of_user="TYPE_OF_USER";
    public static final String pref_uid="UID";
    public static final String pref_mobile="MOBILE";
    public static final String pref_isLoggedIN="IS_LOGGEDIN";
    public static final String pref_isFIRST_INSTALLED="IS_FIRSTINSTALLED";
    public static final String pref_Login_Date="LOGIN_DATE";
    public static final String pref_Business_Name="BUSINESSNAME";


    // Network Connections
    public  static final String NO_NETWORK="Network Connection Missing !!";



}
