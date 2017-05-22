package com.bzyness.bzyness.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.BzynessDetails;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class NewBPhotosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MSG = "ARG_MSG";

    // TODO: Rename and change types of parameters
    private int page;


    private static final String PICK_FEATURED_PHOTO="Pick featured Photos";

    ImageView featuredPhoto1, featuredPhoto2, featuredPhoto3, featuredPhoto4, featuredPhoto5;
    ImageButton addFeaturedPhoto1, addFeaturedPhoto2, addFeaturedPhoto3, addFeaturedPhoto4,addFeaturedPhoto5;
    LinearLayout editFeaturedPhoto1, editFeaturedPhoto2, editFeaturedPhoto3, editFeaturedPhoto4,editFeaturedPhoto5;
    LinearLayout featuredPhotoScheme1, featuredPhotoScheme2, featuredPhotoScheme3, featuredPhotoScheme4,featuredPhotoScheme5;

    BzynessDetails bzynessDetails;

    public NewBPhotosFragment() {
        // Required empty public constructor
    }
    public static NewBPhotosFragment newInstance(int page, BzynessDetails bzynessDetails) {
        NewBPhotosFragment fragment = new NewBPhotosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MSG, page);
        fragment.setArguments(args);
        fragment.bzynessDetails=bzynessDetails;
        return fragment;
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

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_bphotos, container, false);

       /* next=(Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewBPhotosActivity.this,NewBLocActivity.class));
            }
        });*/


        featuredPhoto1 = (ImageView) view.findViewById(R.id.other_pic_1);
        featuredPhoto2 = (ImageView) view.findViewById(R.id.other_pic_2);
        featuredPhoto3 = (ImageView) view.findViewById(R.id.other_pic_3);
        featuredPhoto4 = (ImageView) view.findViewById(R.id.other_pic_4);
        featuredPhoto5 = (ImageView) view.findViewById(R.id.other_pic_5);

        featuredPhotoScheme1 = (LinearLayout) view.findViewById(R.id.other_pic_1_scheme);
        featuredPhotoScheme2 = (LinearLayout) view.findViewById(R.id.other_pic_2_scheme);
        featuredPhotoScheme3 = (LinearLayout) view.findViewById(R.id.other_pic_3_scheme);
        featuredPhotoScheme4 = (LinearLayout) view.findViewById(R.id.other_pic_4_scheme);
        featuredPhotoScheme5= (LinearLayout) view.findViewById(R.id.other_pic_5_scheme);


        addFeaturedPhoto1 = (ImageButton) view.findViewById(R.id.add_other_pic_1);
        addFeaturedPhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_IMAGE1_REQUEST,PICK_FEATURED_PHOTO,getActivity());
            }
        });
        addFeaturedPhoto2 = (ImageButton) view.findViewById(R.id.add_other_pic_2);
        addFeaturedPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_IMAGE2_REQUEST,PICK_FEATURED_PHOTO,getActivity());
            }
        });
        addFeaturedPhoto3 = (ImageButton) view.findViewById(R.id.add_other_pic_3);
        addFeaturedPhoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_IMAGE3_REQUEST,PICK_FEATURED_PHOTO,getActivity());
            }
        });
        addFeaturedPhoto4 = (ImageButton) view.findViewById(R.id.add_other_pic_4);
        addFeaturedPhoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_IMAGE4_REQUEST,PICK_FEATURED_PHOTO,getActivity());
            }
        });
        addFeaturedPhoto5 = (ImageButton) view.findViewById(R.id.add_other_pic_5);
        addFeaturedPhoto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_IMAGE5_REQUEST,PICK_FEATURED_PHOTO,getActivity());
            }
        });

        editFeaturedPhoto1 = (LinearLayout) view.findViewById(R.id.edit_pic1);
        editFeaturedPhoto1.setVisibility(View.INVISIBLE);
        editFeaturedPhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_IMAGE1_REQUEST,PICK_FEATURED_PHOTO,getActivity());
            }
        });
        editFeaturedPhoto2 = (LinearLayout) view.findViewById(R.id.edit_pic2);
        editFeaturedPhoto2.setVisibility(View.INVISIBLE);
        editFeaturedPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_IMAGE2_REQUEST,PICK_FEATURED_PHOTO,getActivity());
            }
        });
        editFeaturedPhoto3 = (LinearLayout) view.findViewById(R.id.edit_pic3);
        editFeaturedPhoto3.setVisibility(View.INVISIBLE);
        editFeaturedPhoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_IMAGE3_REQUEST,PICK_FEATURED_PHOTO,getActivity());
            }
        });
        editFeaturedPhoto4 = (LinearLayout) view.findViewById(R.id.edit_pic4);
        editFeaturedPhoto4.setVisibility(View.INVISIBLE);
        editFeaturedPhoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_IMAGE4_REQUEST,PICK_FEATURED_PHOTO,getActivity());
            }
        });
        editFeaturedPhoto5 = (LinearLayout) view.findViewById(R.id.edit_pic5);
        editFeaturedPhoto5.setVisibility(View.INVISIBLE);
        editFeaturedPhoto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.imageChooser(Constants.PICK_IMAGE5_REQUEST,PICK_FEATURED_PHOTO,getActivity());
            }
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imagePath = data.getData();
            switch (requestCode){
                case Constants.PICK_IMAGE1_REQUEST:
                    Glide.with(getActivity()).load(imagePath).into(featuredPhoto1);
                    addFeaturedPhoto1.setVisibility(View.INVISIBLE);
                    editFeaturedPhoto1.setVisibility(View.VISIBLE);
                    featuredPhotoScheme1.setVisibility(View.INVISIBLE);
                    break;
                case Constants.PICK_IMAGE2_REQUEST:
                    Glide.with(getActivity()).load(imagePath).into(featuredPhoto2);
                    addFeaturedPhoto2.setVisibility(View.INVISIBLE);
                    editFeaturedPhoto2.setVisibility(View.VISIBLE);
                    featuredPhotoScheme2.setVisibility(View.INVISIBLE);
                    break;
                case Constants.PICK_IMAGE3_REQUEST:
                    Glide.with(getActivity()).load(imagePath).into(featuredPhoto3);
                    addFeaturedPhoto3.setVisibility(View.INVISIBLE);
                    editFeaturedPhoto3.setVisibility(View.VISIBLE);
                    featuredPhotoScheme3.setVisibility(View.INVISIBLE);
                    break;
                case Constants.PICK_IMAGE4_REQUEST:
                    Glide.with(getActivity()).load(imagePath).into(featuredPhoto4);
                    addFeaturedPhoto4.setVisibility(View.INVISIBLE);
                    editFeaturedPhoto4.setVisibility(View.VISIBLE);
                    featuredPhotoScheme4.setVisibility(View.INVISIBLE);
                    break;
                case Constants.PICK_IMAGE5_REQUEST:
                    Glide.with(getActivity()).load(imagePath).into(featuredPhoto5);
                    addFeaturedPhoto5.setVisibility(View.INVISIBLE);
                    editFeaturedPhoto5.setVisibility(View.VISIBLE);
                    featuredPhotoScheme5.setVisibility(View.INVISIBLE);
                    break;
                default:
            }

            Toast.makeText(getActivity(), "New Profile Pic Added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "No Image Added", Toast.LENGTH_LONG).show();
        }
    }





}
