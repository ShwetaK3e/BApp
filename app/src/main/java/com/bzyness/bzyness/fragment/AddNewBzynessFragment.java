package com.bzyness.bzyness.fragment;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.CustomWidgets.BAppEditTextBold;
import com.bzyness.bzyness.CustomWidgets.BAppEditTextNormal;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.BusinessTypeAdapter;
import com.bzyness.bzyness.models.BzynessCategoryDetails;
import com.bzyness.bzyness.models.BzynessDetails;
import com.bzyness.bzyness.models.BzynessTypeDetails;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.View.VISIBLE;
import static com.bzyness.bzyness.BaseActivity.bzynessClient;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewBzynessFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewBzynessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewBzynessFragment extends Fragment implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = AddNewBzynessFragment.class.getSimpleName();
    private BzynessDetails bzynessDetails = new BzynessDetails();


    ViewFlipper bProfViewFlipper;
    Float initialX = 0.0f;
    List<String> imageUri = new LinkedList<>();
    ImageView logo;
    LinearLayout profilePicScheme, logoScheme;
    LinearLayout addProfilePic, addLogo;
    LinearLayout editProfilePic, editLogo;

    EditText businessName, aliasName;

    MapView mapView;
    GoogleMap googleMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location current_location;


    LinearLayout type_layout, loc_layout, othr_layout, prdct_layout;
    FrameLayout all_details;
    LinearLayout type_details_layout, loc_details_layout, othr_details_layout, prdct_details_layout;
    LinearLayout type_header, loc_header, othr_header, prdct_header;
    ImageButton type_back, loc_back, othr_back, prdct_back;
    TextView type_guideline, loc_guideline, othr_guideline, prdct_guideline;


    // Type
    BusinessTypeAdapter typeAdapter;
    RecyclerView bzynessDetailsList;
    boolean type_selected = false;

    //Others
    BAppEditTextNormal contact, website, iphone_app, android_app;

    // Loc
    LinearLayout snap_location;


    private List<String> done_lists = new ArrayList<>();
    private final String NONE = "NONE";
    private final String NEW_BZYNESS = "NEW_BZYNESS";
    private final String BZYNESS_TAG = "BZYNESS_TAG";
    private final String BZYNESS_LOC = "BZYNESS_LOC";
    private final String BZYNESS_COVER_PIC1 = "BZYNESS_COVER_PIC1";
    private final String BZYNESS_COVER_PIC2 = "BZYNESS_COVER_PIC2";
    private final String BZYNESS_COVER_PIC3 = "BZYNESS_COVER_PIC3";
    private final String BZYNESS_COVER_PIC4 = "BZYNESS_COVER_PIC4";
    private final String BZYNESS_COVER_PIC5 = "BZYNESS_COVER_PIC5";
    private final String BZYNESS_LOGO = "BZYNESS_LOGO";
    private final String BZYNESS_PHONE = "BZYNESS_PHONE";
    private final String BZYNESS_WEBSITE = "BZYNESS_WEBSITE";
    private final String BZYNESS_APK = "BZYNESS_APK";
    private final String BZYNESS_IPA = "BZYNESS_IPA";
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;


    public AddNewBzynessFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static AddNewBzynessFragment newInstance() {
        AddNewBzynessFragment fragment = new AddNewBzynessFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_business_activity, container, false);

        formDefaultImageList();
        bProfViewFlipper = view.findViewById(R.id.bprof_viewflipper);
        bProfViewFlipper.setAutoStart(true);
        bProfViewFlipper.setFlipInterval(2000);
        bProfViewFlipper.startFlipping();
        bProfViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_left));
        bProfViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_right));

        profilePicScheme = view.findViewById(R.id.bprof_scheme);

        profilePicScheme.setOnClickListener(viewA -> {
            addCoverPic();
        });
        addProfilePic = view.findViewById(R.id.add_profile_image);
        addProfilePic.setOnClickListener(viewA -> {
            addCoverPic();
        });
        logo = view.findViewById(R.id.b_logo);
        logoScheme = view.findViewById(R.id.logo_scheme);
        logoScheme.setVisibility(View.INVISIBLE);
        logoScheme.setOnClickListener(viewA -> {
            BaseActivity.imageChooser(Constants.PICK_LOGO_IMAGE_REQUEST, "SELECT BUSINESS LOGO", getActivity());
        });
        addLogo = view.findViewById(R.id.add_logo);
        addLogo.setOnClickListener(viewA -> {
            BaseActivity.imageChooser(Constants.PICK_LOGO_IMAGE_REQUEST, "SELECT BUSINESS LOGO", getActivity());
        });


        editProfilePic = view.findViewById(R.id.edit_profile_pic);
        editProfilePic.setOnClickListener(viewA -> {
            BaseActivity.imageChooser(Constants.PICK_PROFILE_IMAGE_REQUEST, "SELECT BUSINESS PROFILE IMAGE", getActivity());
        });

        editProfilePic = view.findViewById(R.id.edit_profile_pic);
        editProfilePic.setVisibility(View.INVISIBLE);
        editProfilePic.setOnClickListener(viewA -> {
            BaseActivity.imageChooser(Constants.PICK_PROFILE_IMAGE_REQUEST, "SELECT BUSINESS PROFILE IMAGE", getActivity());
        });
        editLogo = view.findViewById(R.id.edit_logo);
        editLogo.setVisibility(View.INVISIBLE);
        editLogo.setOnClickListener(viewA -> {
            BaseActivity.imageChooser(Constants.PICK_LOGO_IMAGE_REQUEST, "SELECT BUSINESS LOGO", getActivity());
        });


        final InputMethodManager lManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        businessName = (BAppEditTextBold) view.findViewById(R.id.bName);
        lManager.showSoftInput(businessName, -1);
        aliasName = (BAppEditTextNormal) view.findViewById(R.id.alias_name);
        lManager.showSoftInput(aliasName, -1);


        type_layout = view.findViewById(R.id.type_layout);
        type_layout.setOnClickListener(viewA -> {
            type_layout.setBackgroundResource(R.drawable.bg_circle_second_theme_color_border);
            type_layout.animate().rotationYBy(180).setDuration(500).setListener(new Animator.AnimatorListener() {
                @SuppressLint("NewApi")
                @Override
                public void onAnimationStart(Animator animation) {
                    type_layout.setElevation(0);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            type_details_layout.setVisibility(VISIBLE);
            type_details_layout.setBackgroundResource(R.drawable.bg_dialog);
            othr_details_layout.setVisibility(View.INVISIBLE);
            othr_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            loc_details_layout.setVisibility(View.INVISIBLE);
            loc_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            prdct_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            prdct_details_layout.setVisibility(View.INVISIBLE);
            all_details.animate().alpha(1.0f).translationY(0).setDuration(300).scaleX(1.0f);
            type_guideline.setGravity(Gravity.START);
            type_header.animate().alpha(1.0f).translationY(0).setDuration(300).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    type_header.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            profilePicScheme.setEnabled(false);
            addProfilePic.setEnabled(false);
            logoScheme.setEnabled(false);
            addLogo.setEnabled(false);


        });


        loc_layout = view.findViewById(R.id.location_layout);
        loc_layout.setOnClickListener(viewA -> {

            loc_layout.setBackgroundResource(R.drawable.bg_circle_second_theme_color_border);
            loc_layout.animate().rotationYBy(180).setDuration(500).setListener(new Animator.AnimatorListener() {
                @SuppressLint("NewApi")
                @Override
                public void onAnimationStart(Animator animation) {
                    loc_layout.setElevation(0);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            loc_details_layout.setVisibility(VISIBLE);
            loc_details_layout.setBackgroundResource(R.drawable.bg_dialog);
            othr_details_layout.setVisibility(View.INVISIBLE);
            othr_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            type_details_layout.setVisibility(View.INVISIBLE);
            type_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            prdct_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            prdct_details_layout.setVisibility(View.INVISIBLE);
            all_details.animate().alpha(1.0f).translationY(0).setDuration(300).scaleX(1.0f);
            loc_guideline.setGravity(Gravity.START);
            loc_header.animate().alpha(1.0f).translationY(0).setDuration(300).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    loc_header.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            profilePicScheme.setEnabled(false);
            addProfilePic.setEnabled(false);
            logoScheme.setEnabled(false);
            addLogo.setEnabled(false);


        });

        othr_layout = view.findViewById(R.id.other_layout);
        othr_layout.setOnClickListener(viewA -> {

            othr_layout.setBackgroundResource(R.drawable.bg_circle_second_theme_color_border);
            othr_layout.animate().rotationYBy(180).setDuration(500).setListener(new Animator.AnimatorListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onAnimationStart(Animator animation) {
                    othr_layout.setElevation(0);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            othr_details_layout.setVisibility(VISIBLE);
            othr_details_layout.setBackgroundResource(R.drawable.bg_dialog);
            loc_details_layout.setVisibility(View.INVISIBLE);
            loc_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            type_details_layout.setVisibility(View.INVISIBLE);
            type_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            prdct_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            prdct_details_layout.setVisibility(View.INVISIBLE);
            all_details.animate().alpha(1.0f).translationY(0).setDuration(300).scaleX(1.0f);
            othr_guideline.setGravity(Gravity.START);
            othr_header.animate().alpha(1.0f).translationY(0).setDuration(300).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    othr_header.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            profilePicScheme.setEnabled(false);
            addProfilePic.setEnabled(false);
            logoScheme.setEnabled(false);
            addLogo.setEnabled(false);

        });

        prdct_layout = view.findViewById(R.id.add_product_layout);
        prdct_layout.setOnClickListener(viewA -> {

            prdct_layout.setBackgroundResource(R.drawable.bg_circle_second_theme_color_border);
            prdct_layout.animate().rotationYBy(180).setDuration(500).setListener(new Animator.AnimatorListener() {
                @SuppressLint("NewApi")
                @Override
                public void onAnimationStart(Animator animation) {
                    prdct_layout.setElevation(0);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            prdct_details_layout.setVisibility(VISIBLE);
            prdct_details_layout.setBackgroundResource(R.drawable.bg_dialog);
            loc_details_layout.setVisibility(View.INVISIBLE);
            loc_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            type_details_layout.setVisibility(View.INVISIBLE);
            type_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            othr_layout.setBackgroundResource(R.drawable.bg_circle_theme_color_border);
            othr_details_layout.setVisibility(View.INVISIBLE);
            all_details.animate().alpha(1.0f).translationY(0).setDuration(300).scaleX(1.0f);
            prdct_guideline.setGravity(Gravity.START);
            prdct_header.animate().alpha(1.0f).translationY(0).setDuration(300).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    prdct_header.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            profilePicScheme.setEnabled(false);
            addProfilePic.setEnabled(false);
            logoScheme.setEnabled(false);
            addLogo.setEnabled(false);

        });

        all_details = view.findViewById(R.id.all_details_forms);
        all_details.animate().translationY(all_details.getHeight() + 850).setDuration(300).scaleX(0.95f);
        type_details_layout = view.findViewById(R.id.type_details_layout);
        othr_details_layout = view.findViewById(R.id.other_details_layout);
        othr_details_layout.setVisibility(View.INVISIBLE);
        loc_details_layout = view.findViewById(R.id.location_details_layout);
        loc_details_layout.setVisibility(View.INVISIBLE);
        prdct_details_layout = view.findViewById(R.id.prdct_details_layout);
        prdct_details_layout.setVisibility(View.INVISIBLE);


        type_header = view.findViewById(R.id.type_header);
        type_header.setVisibility(View.GONE);
        type_guideline = view.findViewById(R.id.type_guideline);
        loc_header = view.findViewById(R.id.loc_header);
        loc_header.setVisibility(View.GONE);
        loc_guideline = view.findViewById(R.id.loc_guideline);
        othr_header = view.findViewById(R.id.other_header);
        othr_header.setVisibility(View.GONE);
        othr_guideline = view.findViewById(R.id.other_guideline);
        prdct_header = view.findViewById(R.id.prdct_header);
        prdct_header.setVisibility(View.GONE);
        prdct_guideline = view.findViewById(R.id.prdct_guideline);

        type_back = view.findViewById(R.id.back_det);
        type_back.setOnClickListener(viewA -> {

            if (bzynessDetails.getBzyness_type_id() != null) {
                type_guideline.setText("SELECT YOUR BZYNESS TYPE ");
                populateTypes();
                bzynessDetails.setBzyness_category_id(null);
                bzynessDetails.setBzyness_type_id(null);
            } else {
                type_layout.animate().rotationYBy(180).setDuration(500).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @SuppressLint("NewApi")
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        type_layout.setElevation(10.0f);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                type_details_layout.setBackgroundResource(android.R.color.transparent);
                all_details.animate().translationY(all_details.getHeight() - 140).setDuration(300).scaleX(0.95f);
                type_header.setVisibility(View.GONE);
                type_guideline.setGravity(Gravity.CENTER);
                profilePicScheme.setEnabled(true);
                addProfilePic.setEnabled(true);
                logoScheme.setEnabled(true);
                addLogo.setEnabled(true);
            }


        });
        loc_back = view.findViewById(R.id.back_loc);
        loc_back.setOnClickListener(viewA -> {

            loc_layout.animate().rotationYBy(180).setDuration(500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @SuppressLint("NewApi")
                @Override
                public void onAnimationEnd(Animator animation) {
                    loc_layout.setElevation(5.0f);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            loc_details_layout.setBackgroundResource(android.R.color.transparent);
            all_details.animate().translationY(all_details.getHeight() - 140).setDuration(300).scaleX(0.95f);
            loc_header.setVisibility(View.GONE);
            loc_guideline.setGravity(Gravity.CENTER);
            profilePicScheme.setEnabled(true);
            addProfilePic.setEnabled(true);
            logoScheme.setEnabled(true);
            addLogo.setEnabled(true);

        });
        othr_back = (ImageButton) view.findViewById(R.id.back_other);
        othr_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othr_layout.animate().rotationYBy(180).setDuration(500).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @SuppressLint("NewApi")
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        othr_layout.setElevation(10.0f);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                othr_details_layout.setBackgroundResource(android.R.color.transparent);
                all_details.animate().translationY(all_details.getHeight() - 140).setDuration(300).scaleX(0.95f);
                othr_header.setVisibility(View.GONE);
                othr_guideline.setGravity(Gravity.CENTER);
                profilePicScheme.setEnabled(true);
                addProfilePic.setEnabled(true);
                logoScheme.setEnabled(true);
                addLogo.setEnabled(true);
            }
        });
        prdct_back = view.findViewById(R.id.back_prdct);
        prdct_back.setOnClickListener(viewA -> {

            prdct_layout.animate().rotationYBy(180).setDuration(700).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @SuppressLint("NewApi")
                @Override
                public void onAnimationEnd(Animator animation) {
                    prdct_layout.setElevation(10.0f);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            prdct_details_layout.setBackgroundResource(android.R.color.transparent);
            all_details.animate().translationY(all_details.getHeight() - 140).setDuration(300).scaleX(0.95f);
            prdct_header.setVisibility(View.GONE);
            prdct_guideline.setGravity(Gravity.CENTER);
            profilePicScheme.setEnabled(true);
            addProfilePic.setEnabled(true);
            logoScheme.setEnabled(true);
            addLogo.setEnabled(true);

        });


        mapView = view.findViewById(R.id.overall_map);
        mapView.onCreate(savedInstanceState);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(AddNewBzynessFragment.this)
                .addOnConnectionFailedListener(AddNewBzynessFragment.this)
                .build();

        if (mapView != null) {
            mapView.getMapAsync(map -> {
                Log.i("MAP", "MAP");
                googleMap = map;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));
               // googleMap.getUiSettings().setAllGesturesEnabled(false);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);


            });

        }


        //type
        bzynessDetailsList = view.findViewById(R.id.type_list);
        bzynessDetailsList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        bzynessDetailsList.setOnClickListener(viewA -> {

                    bzynessDetailsList.setSelected(false);
                }
        );
        if (bzynessDetails.getBzyness_type_id() == null) {
            populateTypes();
        } else {
            populateCategory(bzynessDetails.getBzyness_type_id());
        }


        // others
        contact = view.findViewById(R.id.add_bzyness_contact);
        website = view.findViewById(R.id.add_bzyness_website);
        iphone_app = view.findViewById(R.id.add_bzyness_iphone_link);
        android_app = view.findViewById(R.id.add_bzyness_android_link);

        Observable<CharSequence> contact_obv = RxTextView.textChanges(contact);
        Observable<CharSequence> website_obv = RxTextView.textChanges(website);
        Observable<CharSequence> iphone_app_obv = RxTextView.textChanges(iphone_app);
        Observable<CharSequence> android_app_obv = RxTextView.textChanges(android_app);

        contact_obv.debounce(2000, TimeUnit.MILLISECONDS).subscribe(text -> {
            bzynessDetails.setPhone_no(text.toString());
        });

        website_obv.debounce(2000, TimeUnit.MILLISECONDS).subscribe(text -> {
            bzynessDetails.setWebsite_link(text.toString());
        });

        iphone_app_obv.debounce(2000, TimeUnit.MILLISECONDS).subscribe(text -> {
            bzynessDetails.setIpa_link(text.toString());
        });

        android_app_obv.debounce(2000, TimeUnit.MILLISECONDS).subscribe(text -> {
            bzynessDetails.setApk_link(text.toString());
        });


        //loc

        snap_location = view.findViewById(R.id.set_loc);
        snap_location.setOnClickListener(click -> {
            // displayLocation();
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {


        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mapView != null) {
            mapView.onResume();
            mGoogleApiClient.connect();

        }

    }

    @Override
    public void onDetach() {


        if (mapView != null) {
            mapView.onDestroy();
        }

        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (mapView != null) {
            mapView.onLowMemory();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.i("MAP", "MAP1");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        current_location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        googleMap.addMarker(new MarkerOptions()
                .anchor(0.0f, 1.0f)
                .position(new LatLng(current_location.getLatitude(), current_location.getLongitude())));
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_location.getLatitude(), current_location.getLongitude()), 10.0f));


    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("MAP","MAP3");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("MAP","MAP4");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("MAP","MAP2");
        current_location=location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    void formDefaultImageList(){
        imageUri.add(Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.drawable.ic_bcover_pic).toString());
        imageUri.add(Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.drawable.ic_bcover_pic).toString());
        imageUri.add(Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.drawable.ic_bcover_pic).toString());

    }

    Dialog add_bcover_pic;

    void addCoverPic(){
        add_bcover_pic=new Dialog(getActivity(), R.style.MyDialogTheme);
        add_bcover_pic.requestWindowFeature(Window.FEATURE_NO_TITLE);
        add_bcover_pic.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        add_bcover_pic.setContentView(R.layout.add_bprof);
        add_bcover_pic.show();
        add_bcover_pic.setCancelable(true);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("NEW BZYNESS");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);
    }



    void populateTypes() {
        if (bzynessClient != null) {
            bzynessClient.getBzynessTypes("Bearer " + BaseActivity.session.getAccessToken()).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BzynessTypeDetails>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BzynessTypeDetails typesOfBzynesses) {
                            if (!typesOfBzynesses.getError()) {

                                final Map<String, String> names = new HashMap<String, String>();
                                final Map<String, String> typeImages = new HashMap<String, String>();


                                for (BzynessTypeDetails.TypesOfBzyness type : typesOfBzynesses.getTypesOfBzyness()) {
                                    names.put(type.getId(), type.getName());
                                    typeImages.put(type.getId(), type.getName());
                                }

                                typeAdapter = new BusinessTypeAdapter(getActivity(), names, typeImages, new BusinessTypeAdapter.OnMyItemClickListener() {
                                    @Override
                                    public void onClick(String type_id) {
                                        String type_name = names.get(type_id).toUpperCase();
                                        setCategoryGuideLine(type_name);
                                        populateCategory(type_id);
                                        bzynessDetails.setBzyness_type_id(type_id);
                                    }

                                });
                                bzynessDetailsList.setAdapter(typeAdapter);

                            }
                        }
                    });
        }
    }



    void populateCategory(String type_id) {
        if(bzynessClient!=null){
            bzynessClient.getBzynessCategories("Bearer "+BaseActivity.session.getAccessToken(),type_id).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BzynessCategoryDetails>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BzynessCategoryDetails bzynessCategoryDetails) {

                            if(!bzynessCategoryDetails.getError()){

                                final Map<String,String> mapID=new HashMap<String, String>();
                                final Map<String, String> images=new HashMap<>();
                                final Map<String, String> names=new HashMap<String, String>();
                                int i = 1;

                                for (BzynessCategoryDetails.CategoryOfBzyness category : bzynessCategoryDetails.getCategories()) {
                                    names.put(String.valueOf(i), category.getName());
                                    images.put(String.valueOf(i), category.getName());
                                    mapID.put(String.valueOf(i++), category.getId());
                                }
                                typeAdapter = new BusinessTypeAdapter(getActivity(), names,images, new BusinessTypeAdapter.OnMyItemClickListener() {
                                    @Override
                                    public void onClick(String position) {


                                        String CATEGORY_ID = mapID.get(position);
                                        bzynessDetails.setBzyness_category_id(CATEGORY_ID);
                                        type_guideline.setText(names.get(position).toUpperCase());



                                    }
                                });
                                bzynessDetailsList.setAdapter(typeAdapter);
                            }
                        }
                    });
        }

    }



    private void setCategoryGuideLine(String type) {
        switch (type) {
            case "MANUFACTURER":
                type_guideline.setText("WHAT DO YOU MANUFACTURE ?");
                break;
            case "DISTRIBUTOR":
                type_guideline.setText("WHAT DO YOU DISTRIBUTE ?");
                break;
            case "SUPPLIER":
                type_guideline.setText("WHAT DO YOU SUPPLY ?");
                break;
            case "SERVICE PROVIDER":
                type_guideline.setText("WHAT SERVICES YOU PROVIDE ?");
                break;
            case "RETAILER":
                type_guideline.setText("WHAT DO YOU SELL ?");
                break;
            case "WHOLESALER":
                type_guideline.setText("WHAT DO YOU WHOLE SELL ?");
                break;
            case "PROFESSIONALS":
                type_guideline.setText("WHAT IS YOUR PROFESSION ?");
                break;
            case "RENTAL SERVICES":
                type_guideline.setText("WHAT DO YOU RENT ?");
                break;
            default:


        }
    }

}
