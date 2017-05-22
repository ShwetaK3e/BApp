package com.bzyness.bzyness.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.BzynessDetails;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;


public class NewBOthrDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MSG = "ARG_MSG";

    // TODO: Rename and change types of parameters
    private int page;

    LinearLayout phoneHeader, websiteHeader, appHeader, header, details1Layout, details2Layout;
    EditText details1, details2;
    Button saveDetails1, saveDetails2;
    int headerColorDone, headerColorInactive, headerColorActive;
    boolean  phoneSaved=false, websiteSaved=false, androidAppSaved=false,iphoneAppSaved=false;

    BzynessDetails bzynessDetails;

    public NewBOthrDetailsFragment() {
        // Required empty public constructor
    }
    public static NewBOthrDetailsFragment newInstance(int page,BzynessDetails bzynessDetails) {
        NewBOthrDetailsFragment fragment = new NewBOthrDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MSG, page);
        fragment.setArguments(args);
        fragment.bzynessDetails=bzynessDetails;
        return fragment;
    }

    void  init()
    {
        headerColorDone=getResources().getColor(R.color.colorSky);
        headerColorInactive=getResources().getColor(R.color.light_grey);
        headerColorActive=getResources().getColor(R.color.colorActive);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_MSG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        init();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_bothrdet, container, false);

        details1Layout = (LinearLayout) view.findViewById(R.id.detail_1_layout);
        details2Layout = (LinearLayout) view.findViewById(R.id.detail_2_layout);
        details2Layout.setVisibility(View.GONE);
        details1 = (EditText) view.findViewById(R.id.detail_1);
        details2 = (EditText) view.findViewById(R.id.detail_2);
        phoneHeader = (LinearLayout) view.findViewById(R.id.phone_header);
        header = phoneHeader;
        phoneHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                header = phoneHeader;
                phoneHeader.setBackgroundColor(headerColorActive);
                if (!websiteSaved) websiteHeader.setBackgroundColor(headerColorInactive);
                if (!(androidAppSaved||iphoneAppSaved)) appHeader.setBackgroundColor(headerColorInactive);
                details2Layout.setVisibility(View.GONE);
                details1Layout.setVisibility(View.VISIBLE);
                details1.setText("");
                details1.setHint("##########");
            }
        });
        websiteHeader = (LinearLayout) view.findViewById(R.id.website_header);
        websiteHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                header = websiteHeader;
                websiteHeader.setBackgroundColor(headerColorActive);
                if (!phoneSaved) phoneHeader.setBackgroundColor(headerColorInactive);
                if (!(androidAppSaved||iphoneAppSaved)) appHeader.setBackgroundColor(headerColorInactive);
                details2Layout.setVisibility(View.GONE);
                details1Layout.setVisibility(View.VISIBLE);
                details1.setText("");
                details1.setHint("www.bzyness.com");
            }
        });

        appHeader = (LinearLayout) view.findViewById(R.id.mobile_app_header);
        appHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                header = appHeader;
                appHeader.setBackgroundColor(headerColorActive);
                if (!websiteSaved) websiteHeader.setBackgroundColor(headerColorInactive);
                if (!phoneSaved) phoneHeader.setBackgroundColor(headerColorInactive);
                details1Layout.setVisibility(View.VISIBLE);
                details2Layout.setVisibility(View.VISIBLE);
                details1.setText("");
                details1.setText("");
                details1.setHint("Android Application Name");
                details2.setHint("iPhone Application Name");
            }
        });

        saveDetails1 = (Button) view.findViewById(R.id.save_details_1);
        saveDetails1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                header.setBackgroundColor(headerColorDone);
                if (header == websiteHeader) websiteSaved = true;
                else if (header == phoneHeader) phoneSaved = true;
                else androidAppSaved = true;
            }
        });
        saveDetails2 = (Button) view.findViewById(R.id.save_details_2);
        saveDetails2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                header.setBackgroundColor(headerColorDone);
                iphoneAppSaved= true;
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }




    void allsaved() {
        if(websiteSaved && phoneSaved && (androidAppSaved||iphoneAppSaved)) {

        }
    }

}
