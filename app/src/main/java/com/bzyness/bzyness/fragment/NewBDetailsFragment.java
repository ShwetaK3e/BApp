package com.bzyness.bzyness.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.BusinessCategoryAdapter;
import com.bzyness.bzyness.adapters.BusinessTypeAdapter;
import com.bzyness.bzyness.models.BusinessTypeDetails;
import com.bzyness.bzyness.models.TypesOfBzyness;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    LinearLayout detailsSummaryLayout;
    LinearLayout categoryHeader;
    TextView typeName, categoryName, guideLine;
    Button editDetails;
    LinearLayout sub_category_layout,add_sub_category_layout;
    EditText subCategoryTag;
    ImageButton saveSubCategory,removeSubCategory;



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
                add_sub_category_layout.setVisibility(View.GONE);
                populateTypes();
                detailsSummaryLayout.setVisibility(View.GONE);
                guideLine.setText(getResources().getString(R.string.type_guideline));
                categoryHeader.setBackgroundColor(getResources().getColor(R.color.colorSky));
            }
        });

        categoryHeader=(LinearLayout)view.findViewById(R.id.category_header);
        typeName=(TextView)view.findViewById(R.id.type_name);
        categoryName=(TextView)view.findViewById(R.id.category_name);

        sub_category_layout=(LinearLayout)view.findViewById(R.id.sub_category_layout);
        sub_category_layout.setVisibility(View.GONE);
        add_sub_category_layout=(LinearLayout)view.findViewById(R.id.ech_subcategory_layout);
        subCategoryTag=(EditText)view.findViewById(R.id.sub_category_tag);
        saveSubCategory=(ImageButton)view.findViewById(R.id.save_sub_category);
        saveSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // sub_category_layout.addView(add_sub_category_layout);
                saveSubCategory.setVisibility(View.INVISIBLE);
                removeSubCategory.setVisibility(VISIBLE);
            }
        });
        removeSubCategory=(ImageButton)view.findViewById(R.id.remove_sub_category);
        removeSubCategory.setVisibility(View.INVISIBLE);
        removeSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSubCategory.setVisibility(VISIBLE);
                removeSubCategory.setVisibility(View.INVISIBLE);
            }
        });

        businessDetails=(RecyclerView)view.findViewById(R.id.grid_layout);
        layoutManager= new GridLayoutManager(getActivity(),3);
        businessDetails.setLayoutManager(layoutManager);

        populateTypes();

        return view;
    }


    void populateTypes(){
        new FetchBusinessTypeService().execute();

    }

   void populateCategory(String type_id){
       new FetchBusinessCategoryService().execute(type_id);

    }

    void addSubCategory() {
        businessDetails.setAdapter(null);
        detailsSummaryLayout.setVisibility(VISIBLE);
        sub_category_layout.setVisibility(VISIBLE);
        businessDetails.setAdapter(null);
        subCategoryTag.setText("");
        editDetails.setVisibility(VISIBLE);
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
            case "WHOLESALER":
                guideLine.setText("WHAT DO YOU WHOLE SELL ?");
                break;
            case "PROFESSIONALS":
                guideLine.setText("WHAT IS YOUR PROFESSION ?");
                break;
            case "RENTAL SERVICES":
                guideLine.setText("WHAT DO YOU RENT ?");
                break;
            default:


        }
    }





       class FetchBusinessTypeService extends AsyncTask<Void,Void,String > {
           private OkHttpClient client;
           int responsecode=0;
           private String TAG= FetchBusinessTypeService.class.getSimpleName();
           Map<String,String> names=new HashMap<>();
           Map<String,String> typeImages=new HashMap<>();

           public FetchBusinessTypeService() {
               Log.i(TAG,"Type Constructor");
           }

           String fetchTypes(String url) throws IOException {
               Log.i(TAG,"fetching Types");
               Request request=new Request.Builder().url(url).get().build();
               Response response=client.newCall(request).execute();
               responsecode=response.code();
               return response.body().string();
           }

           @Override
           protected String doInBackground(Void... params) {
               Log.i(TAG,"background");
               client=new OkHttpClient();
               String url= Constants.BUSINESS_TYPE_URL;
               String jsonString=null;
               try {
                   jsonString=fetchTypes(url);
               } catch (IOException e) {
                   Log.i(TAG,e.toString());
                   e.printStackTrace();
               }
               Log.i(TAG,jsonString);
               return jsonString;
           }

           @Override
           protected void onPostExecute(String result) {
               Log.i(TAG,"types onpostExecute");
               super.onPostExecute(result);
               if(result!=null) {
                   Log.i(TAG,result);
                   ObjectMapper objectMapper = new ObjectMapper();
                   BusinessTypeDetails businessTypeDetails=new BusinessTypeDetails();
                   try {
                       businessTypeDetails = objectMapper.readValue(result, BusinessTypeDetails.class);
                   } catch (IOException e) {
                       Log.i(TAG,e.toString());
                       e.printStackTrace();
                   }
                   if(businessTypeDetails!=null){


                       for(TypesOfBzyness type: businessTypeDetails.getTypesOfBzyness()){
                           Log.i(TAG,responsecode+"  "+type.getName());
                           names.put(type.getId(),type.getName());
                           typeImages.put(type.getId(),type.getName());
                       }
                       typeAdapter=new BusinessTypeAdapter(getActivity(), names, typeImages, new BusinessTypeAdapter.OnMyItemClickListener() {
                           @Override
                           public void onClick(String name) {

                                   String id=null;
                                    for(Map.Entry entry:names.entrySet()){
                                      if(name.equalsIgnoreCase(entry.getValue().toString())){
                                          id=entry.getKey().toString();
                                      }
                                    }
                                    setCategoryGuideLine(name.toUpperCase());
                                    typeName.setText(name);
                                    populateCategory(id);
                                    editDetails.setVisibility(VISIBLE);
                                    typeName.setText(name);
                           }
                       });
                       businessDetails.setAdapter(typeAdapter);
                   }else{
                       Log.i(TAG, "Error"+ responsecode);
                   }
               }
           }
         }



       class FetchBusinessCategoryService extends AsyncTask<String,Void,String > {
        private OkHttpClient client;
        int responsecode=0;
        private String TAG= FetchBusinessCategoryService.class.getSimpleName();

        List<String> names=new ArrayList<>();

        public FetchBusinessCategoryService() {
            Log.i(TAG,"CategoryConstructor");
        }

        String fetchCategory(String url, String type_id) throws IOException {
            Log.i(TAG,"fetching Category");
            Request request=new Request.Builder().url(url+type_id).get().build();
            Response response=client.newCall(request).execute();
            responsecode=response.code();
            return response.body().string();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG,"background");
            client=new OkHttpClient();
            String type_id=params[0];
            String url= Constants.BUSINESS_CATEGORY_URL;
            String jsonString=null;
            try {
                jsonString=fetchCategory(url,type_id);
            } catch (IOException e) {
                Log.i(TAG,e.toString());
                e.printStackTrace();
            }
            Log.i(TAG,jsonString);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG,"category onpostExecute");
            super.onPostExecute(result);
            if(result!=null) {
                Log.i(TAG,result);
                ObjectMapper objectMapper = new ObjectMapper();
                BusinessTypeDetails businessTypeDetails=new BusinessTypeDetails();
                try {
                    businessTypeDetails = objectMapper.readValue(result, BusinessTypeDetails.class);
                } catch (IOException e) {
                    Log.i(TAG,e.toString());
                    e.printStackTrace();
                }
                if(businessTypeDetails!=null){

                    Log.i(TAG,businessTypeDetails.getError()+"");
                    for(TypesOfBzyness category: businessTypeDetails.getCategories()){
                        Log.i(TAG,responsecode+category.getId()+"  "+category.getName());
                        names.add(category.getName());
                    }
                    categoryAdapter =new BusinessCategoryAdapter(getActivity(), names, new BusinessCategoryAdapter.OnMyItemClickListener() {
                        @Override
                        public void onClick(String name) {
                            addSubCategory();
                            categoryName.setText(name);
                        }
                    });
                    businessDetails.setAdapter(categoryAdapter);
                }else{
                    Log.i(TAG, "Error"+ responsecode);
                }
            }
        }
    }


}

