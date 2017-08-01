package com.bzyness.bzyness.fragment;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewBzynessFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewBzynessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewBzynessFragment extends Fragment {



    private OnFragmentInteractionListener mListener;

    ViewFlipper bProfViewFlipper;
    Float initialX=0.0f;
    List<String> imageUri=new LinkedList<>();
    ImageView logo;
    LinearLayout profilePicScheme,logoScheme;
    LinearLayout addProfilePic, addLogo;
    LinearLayout editProfilePic, editLogo;

    EditText businessName, aliasName;



    LinearLayout type_layout,loc_layout,othr_layout,prdct_layout;
    FrameLayout all_details;
    LinearLayout type_details_layout,loc_details_layout,othr_details_layout, prdct_details_layout;
    LinearLayout type_header,loc_header,othr_header,prdct_header;
    ImageButton type_back,loc_back,othr_back,prdct_back;
    TextView type_guideline,loc_guideline,othr_guideline,prdct_guideline;



    private List<String> done_lists=new ArrayList<>();
    private final String NONE="NONE";
    private final String NEW_BZYNESS="NEW_BZYNESS";
    private final String BZYNESS_TAG="BZYNESS_TAG";
    private final String BZYNESS_LOC="BZYNESS_LOC";
    private final String BZYNESS_COVER_PIC1="BZYNESS_COVER_PIC1";
    private final String BZYNESS_COVER_PIC2="BZYNESS_COVER_PIC2";
    private final String BZYNESS_COVER_PIC3="BZYNESS_COVER_PIC3";
    private final String BZYNESS_COVER_PIC4="BZYNESS_COVER_PIC4";
    private final String BZYNESS_COVER_PIC5="BZYNESS_COVER_PIC5";
    private final String BZYNESS_LOGO="BZYNESS_LOGO";
    private final String BZYNESS_PHONE="BZYNESS_PHONE";
    private final String BZYNESS_WEBSITE="BZYNESS_WEBSITE";
    private final String BZYNESS_APK="BZYNESS_APK";
    private final String BZYNESS_IPA="BZYNESS_IPA";

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
        View view= inflater.inflate(R.layout.fragment_new_business_activity, container, false);

        formDefaultImageList();
        bProfViewFlipper=(ViewFlipper) view.findViewById(R.id.bprof_viewflipper);
        bProfViewFlipper.setAutoStart(true);
        bProfViewFlipper.setFlipInterval(2000);
        bProfViewFlipper.startFlipping();
        bProfViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_left));
        bProfViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_right));

        profilePicScheme=(LinearLayout)view.findViewById(R.id.bprof_scheme);

        profilePicScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCoverPic();
                //BaseActivity.imageChooser(Constants.PICK_PROFILE_IMAGE_REQUEST,"SELECT BUSINESS PROFILE IMAGE",getActivity());
            }
        });
        addProfilePic=(LinearLayout)view.findViewById(R.id.add_profile_image);
        addProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCoverPic();
                //BaseActivity.imageChooser(Constants.PICK_PROFILE_IMAGE_REQUEST,"SELECT BUSINESS PROFILE IMAGE",getActivity());
            }
        });
        logo=(ImageView)view.findViewById(R.id.b_logo);
        logoScheme=(LinearLayout)view.findViewById(R.id.logo_scheme);
        logoScheme.setVisibility(View.INVISIBLE);
        logoScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_LOGO_IMAGE_REQUEST,"SELECT BUSINESS LOGO",getActivity());
            }
        });
        addLogo=(LinearLayout)view.findViewById(R.id.add_logo);
        addLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_LOGO_IMAGE_REQUEST,"SELECT BUSINESS LOGO",getActivity());
            }
        });



        editProfilePic=(LinearLayout)view.findViewById(R.id.edit_profile_pic);
        editProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_PROFILE_IMAGE_REQUEST,"SELECT BUSINESS PROFILE IMAGE",getActivity());
            }
        });

        editProfilePic=(LinearLayout)view.findViewById(R.id.edit_profile_pic);
        editProfilePic.setVisibility(View.INVISIBLE);
        editProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_PROFILE_IMAGE_REQUEST,"SELECT BUSINESS PROFILE IMAGE",getActivity());
            }
        });
        editLogo=(LinearLayout)view.findViewById(R.id.edit_logo);
        editLogo.setVisibility(View.INVISIBLE);
        editLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_LOGO_IMAGE_REQUEST,"SELECT BUSINESS LOGO",getActivity());
            }
        });


        final InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        businessName=(BAppEditTextBold)view.findViewById(R.id.bName);
        lManager.showSoftInput(businessName, -1);
        aliasName=(BAppEditTextNormal)view.findViewById(R.id.alias_name);
        lManager.showSoftInput(aliasName, -1);


        type_layout=(LinearLayout)view.findViewById(R.id.type_layout);
        type_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });


        loc_layout=(LinearLayout)view.findViewById(R.id.location_layout);
        loc_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        othr_layout=(LinearLayout)view.findViewById(R.id.other_layout);
        othr_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        prdct_layout=(LinearLayout)view.findViewById(R.id.add_product_layout);
        prdct_layout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                prdct_layout.setBackgroundResource(R.drawable.bg_circle_second_theme_color_border);
                prdct_layout.animate().rotationYBy(180).setDuration(500).setListener(new Animator.AnimatorListener() {
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
            }
        });

        all_details=(FrameLayout)view.findViewById(R.id.all_details_forms);
        all_details.animate().translationY(all_details.getHeight()+850).setDuration(300).scaleX(0.95f);
        type_details_layout=(LinearLayout)view.findViewById(R.id.type_details_layout);
        othr_details_layout=(LinearLayout)view.findViewById(R.id.other_details_layout);
        othr_details_layout.setVisibility(View.INVISIBLE);
        loc_details_layout=(LinearLayout)view.findViewById(R.id.location_details_layout);
        loc_details_layout.setVisibility(View.INVISIBLE);
        prdct_details_layout=(LinearLayout)view.findViewById(R.id.prdct_details_layout);
        prdct_details_layout.setVisibility(View.INVISIBLE);


        type_header=(LinearLayout)view.findViewById(R.id.type_header);
        type_header.setVisibility(View.GONE);
        type_guideline=(TextView)view.findViewById(R.id.type_guideline);
        loc_header=(LinearLayout)view.findViewById(R.id.loc_header);
        loc_header.setVisibility(View.GONE);
        loc_guideline=(TextView)view.findViewById(R.id.loc_guideline);
        othr_header=(LinearLayout)view.findViewById(R.id.other_header);
        othr_header.setVisibility(View.GONE);
        othr_guideline=(TextView)view.findViewById(R.id.other_guideline);
        prdct_header=(LinearLayout)view.findViewById(R.id.prdct_header);
        prdct_header.setVisibility(View.GONE);
        prdct_guideline=(TextView)view.findViewById(R.id.prdct_guideline);

        type_back=(ImageButton)view.findViewById(R.id.back_det);
        type_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                all_details.animate().translationY(all_details.getHeight()-140).setDuration(300).scaleX(0.95f);
                type_header.setVisibility(View.GONE);
                type_guideline.setGravity(Gravity.CENTER);
                profilePicScheme.setEnabled(true);
                addProfilePic.setEnabled(true);
                logoScheme.setEnabled(true);
                addLogo.setEnabled(true);

            }
        });
        loc_back=(ImageButton)view.findViewById(R.id.back_loc);
        loc_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                all_details.animate().translationY(all_details.getHeight()-140).setDuration(300).scaleX(0.95f);
                loc_header.setVisibility(View.GONE);
                loc_guideline.setGravity(Gravity.CENTER);
                profilePicScheme.setEnabled(true);
                addProfilePic.setEnabled(true);
                logoScheme.setEnabled(true);
                addLogo.setEnabled(true);
            }
        });
        othr_back=(ImageButton)view.findViewById(R.id.back_other);
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
                        othr_layout.setElevation(5.0f);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                othr_details_layout.setBackgroundResource(android.R.color.transparent);
                all_details.animate().translationY(all_details.getHeight()-140).setDuration(300).scaleX(0.95f);
                othr_header.setVisibility(View.GONE);
                othr_guideline.setGravity(Gravity.CENTER);
                profilePicScheme.setEnabled(true);
                addProfilePic.setEnabled(true);
                logoScheme.setEnabled(true);
                addLogo.setEnabled(true);
            }
        });
        prdct_back=(ImageButton)view.findViewById(R.id.back_prdct);
        prdct_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prdct_layout.animate().rotationYBy(180).setDuration(700).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @SuppressLint("NewApi")
                    @Override
                    public void onAnimationEnd(Animator animation) {
                       prdct_layout.setElevation(5.0f);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                prdct_details_layout.setBackgroundResource(android.R.color.transparent);
                all_details.animate().translationY(all_details.getHeight()-140).setDuration(300).scaleX(0.95f);
                prdct_header.setVisibility(View.GONE);
                prdct_guideline.setGravity(Gravity.CENTER);
                profilePicScheme.setEnabled(true);
                addProfilePic.setEnabled(true);
                logoScheme.setEnabled(true);
                addLogo.setEnabled(true);
            }
        });

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
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
}
