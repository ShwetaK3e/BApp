package com.bzyness.bzyness.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.BusinessCategoryAdapter;
import com.bzyness.bzyness.adapters.BusinessTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;


public class NewBDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MSG = "ARG_MSG";

    // TODO: Rename and change types of parameters
    private int page;

    RecyclerView businessDetails;
    RecyclerView.LayoutManager layoutManager;
    BusinessTypeAdapter typeAdapter;
    BusinessCategoryAdapter categoryAdapter;
    List<String> names=new ArrayList<>();
    List<Drawable> images=new ArrayList<>();
    LinearLayout detailsSummaryLayout;
    LinearLayout categoryHeader, subCategoryHeader;
    TextView typeName, categoryName,subcategoryName, guideLine;
    Button editDetails;
    LinearLayout otherLayout;
    EditText otherType;
    Button saveOtherType;



    public NewBDetailsFragment() {
        // Required empty public constructor
    }
    public static NewBDetailsFragment newInstance(int page) {
        NewBDetailsFragment fragment = new NewBDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MSG, page);
        fragment.setArguments(args);
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
        View view= inflater.inflate(R.layout.fragment_new_bdeatils, container, false);

        guideLine=(TextView)view.findViewById(R.id.guideline);
        detailsSummaryLayout=(LinearLayout)view.findViewById(R.id.details_summary_layout);
        detailsSummaryLayout.setVisibility(View.GONE);
        editDetails=(Button)view.findViewById(R.id.edit_details);
        editDetails.setVisibility(View.GONE);
        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDetails.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);
                populateTypes();
                detailsSummaryLayout.setVisibility(View.GONE);
                guideLine.setText(getResources().getString(R.string.type_guideline));
                categoryHeader.setBackgroundColor(getResources().getColor(R.color.colorSky));
                subCategoryHeader.setBackgroundColor(getResources().getColor(R.color.colorSky));
            }
        });

        categoryHeader=(LinearLayout)view.findViewById(R.id.category_header);
        subCategoryHeader=(LinearLayout)view.findViewById(R.id.subcategory_header);
        typeName=(TextView)view.findViewById(R.id.type_name);
        categoryName=(TextView)view.findViewById(R.id.category_name);
        subcategoryName=(TextView)view.findViewById(R.id.subcategory_name);
        otherLayout=(LinearLayout)view.findViewById(R.id.other_layout);
        otherLayout.setVisibility(View.GONE);
        otherType=(EditText)view.findViewById(R.id.other_type);
        saveOtherType=(Button)view.findViewById(R.id.save_other);
        saveOtherType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String other=otherType.getText().toString().trim().toUpperCase();
                if(getResources().getString(R.string.other).equalsIgnoreCase(typeName.getText().toString().trim())) {
                    typeName.setText(other);
                }else if(getResources().getString(R.string.other).equalsIgnoreCase(categoryName.getText().toString().trim())){
                    categoryName.setText(other);
                }else{
                    subcategoryName.setText(other);
                }
                otherLayout.setVisibility(View.GONE);
            }
        });

        businessDetails=(RecyclerView)view.findViewById(R.id.grid_layout);
        layoutManager= new GridLayoutManager(getActivity(),3);
        businessDetails.setLayoutManager(layoutManager);

        populateTypes();

        return view;
    }


    void populateTypes(){
        names=new ArrayList<String>();
        images=new ArrayList<Drawable>();
        BaseActivity.listNames(names,images,getActivity());
        typeAdapter=new BusinessTypeAdapter(getActivity(), names, images, new BusinessTypeAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(String name, Drawable img) {
                if(getResources().getString(R.string.other).equals(name)){
                   categoryNotAvilable();
                }else {
                    populateCategory(name);
                }
                editDetails.setVisibility(VISIBLE);
                typeName.setText(name);
            }
        });
        businessDetails.setAdapter(typeAdapter);
    }


    void populateCategory(String name){
        String type=name;
        setCategoryGuideLine(type);
        names=new ArrayList<>();
        images=new ArrayList<>();
        BaseActivity.listCategoryNames(names,getActivity());
        categoryAdapter =new BusinessCategoryAdapter(getActivity(), names, new BusinessCategoryAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(String name) {
                if(getResources().getString(R.string.other).equals(name)) {
                    subCategoryNotAvilable();
                }else {
                    populateSubCategory(name);
                }
                categoryName.setText(name);
            }
        });
        businessDetails.setAdapter(categoryAdapter);
    }

    void populateSubCategory(String name) {
        names=new ArrayList<>();
        images=new ArrayList<>();
        BaseActivity.listSubCategoryNames(names,getActivity());
        if (names.isEmpty()) {
            subCategoryNotAvilable();
            //next.setEnabled(true);
        } else {
            categoryAdapter = new BusinessCategoryAdapter(getActivity(), names, new BusinessCategoryAdapter.OnMyItemClickListener() {
                @Override
                public void onClick(String name) {

                    if(getResources().getString(R.string.other).equals(name)) {
                        otherLayout.setVisibility(VISIBLE);
                        otherType.setText("");
                    }
                    subcategoryName.setText(name);
                    names = new ArrayList<>();
                    images = new ArrayList<>();
                    businessDetails.setAdapter(null);
                    detailsSummaryLayout.setVisibility(VISIBLE);
                    editDetails.setVisibility(VISIBLE);
                   // next.setEnabled(true);
                }
            });
            businessDetails.setAdapter(categoryAdapter);
        }
    }

    private void setCategoryGuideLine(String type){
        switch (type) {
            case "MANUFACTURER":
                guideLine.setText("WHAT DO YOU MANUFACTURE ?");
                break;
            case "DISTRIBUTOR":
                guideLine.setText("WHAT DO YOU DISTRIBUTE ?");
                break;
            case "SUPPLIER":
                guideLine.setText("WHAT DO YOU SUPPLY ?");
                break;
            case "SERVICE PROVIDER":
                guideLine.setText("WHAT SERVICES YOU PROVIDE ?");
                break;
            case "RETAILER":
                guideLine.setText("WHAT DO YOU SELL ?");
                break;
            case "WHOLE SELLER":
                guideLine.setText("WHAT DO YOU WHOLE SELL ?");
                break;
            case "PROFESSIONAL":
                guideLine.setText("WHAT IS YOUR PROFESSION ?");
                break;
            case "RENTALS":
                guideLine.setText("WHAT DO YOU RENT ?");
                break;
            default:
                images.add(getResources().getDrawable(R.drawable.ic_loading));

        }
    }

    public void subCategoryNotAvilable(){
        subcategoryName.setText("NOT AVAILABLE");
        businessDetails.setAdapter(null);
        detailsSummaryLayout.setVisibility(VISIBLE);
        otherLayout.setVisibility(VISIBLE);
        otherType.setText("");
        editDetails.setVisibility(VISIBLE);
        subCategoryHeader.setBackgroundColor(getResources().getColor(R.color.light_grey));
    }


    public void categoryNotAvilable(){
        detailsSummaryLayout.setVisibility(VISIBLE);
        otherLayout.setVisibility(VISIBLE);
        businessDetails.setAdapter(null);
        otherType.setText("");
        categoryName.setText("NOT AVAILABLE");
        subcategoryName.setText("NOT AVAILABLE");
        categoryHeader.setBackgroundColor(getResources().getColor(R.color.light_grey));
        subCategoryHeader.setBackgroundColor(getResources().getColor(R.color.light_grey));
    }

}
