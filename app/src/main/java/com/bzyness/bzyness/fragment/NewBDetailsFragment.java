package com.bzyness.bzyness.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.BusinessCategoryAdapter;
import com.bzyness.bzyness.adapters.BusinessSubCategoryAdapter;
import com.bzyness.bzyness.adapters.BusinessTypeAdapter;
import com.bzyness.bzyness.models.BzynessCategoryDetails;
import com.bzyness.bzyness.models.BzynessDetails;
import com.bzyness.bzyness.models.BzynessTypeDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.View.VISIBLE;
import static com.bzyness.bzyness.BaseActivity.bzynessClient;


public class NewBDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MSG = "ARG_MSG";

    // TODO: Rename and change types of parameters
    private int page;

    RecyclerView bzynessDetailsList;
    RecyclerView.LayoutManager layoutManager;
    BusinessTypeAdapter typeAdapter;
    BusinessCategoryAdapter categoryAdapter;
    BusinessSubCategoryAdapter subCategoryAdapter;

    LinearLayout detailsSummaryLayout;
    LinearLayout categoryHeader;
    TextView typeName, categoryName, guideLine;
    Button editDetails;
    LinearLayout sub_category_layout;
    EditText subCategoryTag;
    ImageButton saveSubCategory;

    List<String> subCategories = new ArrayList<>();

    BzynessDetails bzynessDetails;

    public static final String TAG = NewBDetailsFragment.class.getSimpleName();


    public NewBDetailsFragment() {
        // Required empty public constructor
    }

    public static NewBDetailsFragment newInstance(int page, BzynessDetails bzynessDetails) {
        NewBDetailsFragment fragment = new NewBDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MSG, page);
        fragment.setArguments(args);
        fragment.bzynessDetails = bzynessDetails;
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
        View view = inflater.inflate(R.layout.fragment_new_bdeatils_1, container, false);

        /*guideLine = (TextView) view.findViewById(R.id.guideline);
        detailsSummaryLayout = (LinearLayout) view.findViewById(R.id.details_summary_layout);
        //detailsSummaryLayout.setVisibility(View.GONE);
        editDetails = (Button) view.findViewById(R.id.edit_details);
        editDetails.setVisibility(View.GONE);
        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bzynessDetails.setBzyness_type_id(null);
                bzynessDetails.setBzyness_category_id(null);
                editDetails.setVisibility(View.GONE);
                sub_category_layout.setVisibility(View.GONE);
                populateTypes();
                detailsSummaryLayout.setVisibility(View.GONE);
                guideLine.setText(getResources().getString(R.string.type_guideline));
                categoryHeader.setBackgroundColor(getResources().getColor(R.color.colorSky));
            }
        });


        categoryHeader = (LinearLayout) view.findViewById(R.id.category_header);
        typeName = (TextView) view.findViewById(R.id.type_name);
        categoryName = (TextView) view.findViewById(R.id.category_name);

        sub_category_layout = (LinearLayout) view.findViewById(R.id.sub_category_layout);
        sub_category_layout.setVisibility(View.GONE);

        subCategoryTag = (EditText) view.findViewById(R.id.sub_category_tag);
        saveSubCategory = (ImageButton) view.findViewById(R.id.save_sub_category);
        saveSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subCat_name = subCategoryTag.getText().toString().trim().toUpperCase();
                // sub_category_layout.addView(add_sub_category_layout);
                if (subCat_name != null && subCat_name.length() != 0) {
                    Log.i(TAG, "add Subcategory");
                    if (!subCategories.contains(subCat_name)) {
                        subCategories.add(subCat_name);
                        subCategoryAdapter.notifyDataSetChanged();
                        bzynessDetails.setBzyness_tags(subCategories);
                    }
                    subCategoryTag.setText("");
                }
            }
        });


        bzynessDetailsList = (RecyclerView) view.findViewById(R.id.grid_layout);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        bzynessDetailsList.setLayoutManager(layoutManager);

        //populateTypes();
*/
        return view;
    }


    void populateTypes() {
        if(bzynessClient!=null){
            bzynessClient.getBzynessTypes("Bearer "+ BaseActivity.session.getAccessToken()).subscribeOn(Schedulers.newThread())
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
                              if(!typesOfBzynesses.getError()){

                                  final Map<String, String> names=new HashMap<String, String>();
                                  final Map<String,String> typeImages=new HashMap<String, String>();

                                  for (BzynessTypeDetails.TypesOfBzyness type : typesOfBzynesses.getTypesOfBzyness()) {
                                      names.put(type.getId(), type.getName());
                                      typeImages.put(type.getId(), type.getName());
                                  }

                                  typeAdapter = new BusinessTypeAdapter(getActivity(), names, typeImages, new BusinessTypeAdapter.OnMyItemClickListener() {
                                      @Override
                                      public void onClick(String type_id) {
                                          String type_name = names.get(type_id).toUpperCase();
                                          setCategoryGuideLine(type_name);
                                          typeName.setText(type_name);
                                          populateCategory(type_id);
                                          bzynessDetails.setBzyness_type_id(type_id);
                                          editDetails.setVisibility(VISIBLE);
                                      }
                                  });
                                  bzynessDetailsList.setAdapter(typeAdapter);

                              }
                        }
                    });
        }


        //new FetchBusinessTypeService().execute();

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
                                final List<String> names=new ArrayList<String>();
                                final Map<String,String> mapID=new HashMap<String, String>();
                                int i = 0;

                                for (BzynessCategoryDetails.CategoryOfBzyness category : bzynessCategoryDetails.getCategories()) {
                                    names.add(i, category.getName());
                                    mapID.put(String.valueOf(i++), category.getId());
                                }
                                categoryAdapter = new BusinessCategoryAdapter(getActivity(), names, new BusinessCategoryAdapter.OnMyItemClickListener() {
                                    @Override
                                    public void onClick(String position) {
                                        addSubCategory();
                                        Log.i(TAG, position + "");
                                        String CATEGORY_ID = mapID.get(position);
                                        bzynessDetails.setBzyness_category_id(CATEGORY_ID);
                                        categoryName.setText(names.get(Integer.parseInt(position)).toUpperCase());

                                    }
                                });
                                bzynessDetailsList.setAdapter(categoryAdapter);
                            }
                        }
                    });
        }
    }

    void addSubCategory() {
        subCategoryAdapter = new BusinessSubCategoryAdapter(getActivity(), subCategories, new BusinessSubCategoryAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(String name) {
                Log.i(TAG, "Remove Subcategory B" + subCategories.size());
                subCategories.remove(name);
                bzynessDetails.setBzyness_tags(subCategories);
                Log.i(TAG, "Remove Subcategory A" + subCategories.size());
                subCategoryAdapter.notifyDataSetChanged();
            }
        });
        bzynessDetailsList.setAdapter(subCategoryAdapter);
        detailsSummaryLayout.setVisibility(VISIBLE);
        sub_category_layout.setVisibility(VISIBLE);
        subCategoryTag.setText("");
        editDetails.setVisibility(VISIBLE);
    }

    private void setCategoryGuideLine(String type) {
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


    /*class FetchBusinessTypeService extends AsyncTask<Void, Void, String> {
        private OkHttpClient client;
        int responsecode = 0;
        private String TAG = FetchBusinessTypeService.class.getSimpleName();
        Map<String, String> names = new HashMap<>();
        Map<String, String> typeImages = new HashMap<>();

        public FetchBusinessTypeService() {
            Log.i(TAG, "Type Constructor");
        }

        String fetchTypes(String url) throws IOException {
            Log.i(TAG, "fetching Types");
            Request request = new Request.Builder().url(url).get().build();
            Response response = client.newCall(request).execute();
            responsecode = response.code();
            return response.body().string();
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.i(TAG, "background");
            client = new OkHttpClient();
            String url = Constants.BUSINESS_TYPE_URL;
            String jsonString = null;
            try {
                jsonString = fetchTypes(url);
            } catch (IOException e) {
                Log.i(TAG, e.toString());
                e.printStackTrace();
            }
            Log.i(TAG, "nul"+jsonString);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "types onpostExecute");
            super.onPostExecute(result);
            if (result != null) {
                Log.i(TAG, result);
                ObjectMapper objectMapper = new ObjectMapper();
                BusinessTypeDetails businessTypeDetails = new BusinessTypeDetails();
                try {
                    businessTypeDetails = objectMapper.readValue(result, BusinessTypeDetails.class);
                } catch (IOException e) {
                    Log.i(TAG, e.toString());
                    e.printStackTrace();
                }
                if (businessTypeDetails != null) {


                    for (TypesOfBzyness type : businessTypeDetails.getTypesOfBzyness()) {
                        Log.i(TAG, responsecode + "  " + type.getName());
                        names.put(type.getId(), type.getName());
                        typeImages.put(type.getId(), type.getName());
                    }
                    typeAdapter = new BusinessTypeAdapter(getActivity(), names, typeImages, new BusinessTypeAdapter.OnMyItemClickListener() {
                        @Override
                        public void onClick(String type_id) {
                            String type_name = names.get(type_id).toUpperCase();
                            setCategoryGuideLine(type_name);
                            typeName.setText(type_name);
                            populateCategory(type_id);
                            bzynessDetails.setBzyness_type_id(type_id);
                            editDetails.setVisibility(VISIBLE);
                        }
                    });
                    bzynessDetailsList.setAdapter(typeAdapter);
                } else {
                    Log.i(TAG, "Error" + responsecode);
                }
            }
        }
    }
*/

   /* class FetchBusinessCategoryService extends AsyncTask<String, Void, String> {
        private OkHttpClient client;
        int responsecode = 0;
        private String TAG = FetchBusinessCategoryService.class.getSimpleName();

        List<String> names = new ArrayList<>();
        Map<String, String> mapID = new HashMap<>();

        public FetchBusinessCategoryService() {
            Log.i(TAG, "CategoryConstructor");
        }

        String fetchCategory(String url, String type_id) throws IOException {
            Log.i(TAG, "fetching Category");
            Request request = new Request.Builder().url(url + type_id).get().build();
            Response response = client.newCall(request).execute();
            responsecode = response.code();
            return response.body().string();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG, "background");
            client = new OkHttpClient();
            String type_id = params[0];
            String url = Constants.BUSINESS_CATEGORY_URL;
            String jsonString = null;
            try {
                jsonString = fetchCategory(url, type_id);
            } catch (IOException e) {
                Log.i(TAG, e.toString());
                e.printStackTrace();
            }
            Log.i(TAG, jsonString);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "category onpostExecute");
            super.onPostExecute(result);
            if (result != null) {
                Log.i(TAG, result);
                ObjectMapper objectMapper = new ObjectMapper();
                BusinessTypeDetails businessTypeDetails = new BusinessTypeDetails();
                try {
                    businessTypeDetails = objectMapper.readValue(result, BusinessTypeDetails.class);
                } catch (IOException e) {
                    Log.i(TAG, e.toString());
                    e.printStackTrace();
                }
                if (businessTypeDetails != null) {

                    Log.i(TAG, businessTypeDetails.getError() + "");
                    int i = 0;
                    for (TypesOfBzyness category : businessTypeDetails.getCategories()) {
                        Log.i(TAG, responsecode + category.getId() + "  " + category.getName());
                        names.add(i, category.getName());
                        mapID.put(String.valueOf(i++), category.getId());
                    }
                    categoryAdapter = new BusinessCategoryAdapter(getActivity(), names, new BusinessCategoryAdapter.OnMyItemClickListener() {
                        @Override
                        public void onClick(String position) {
                            addSubCategory();
                            Log.i(TAG, position + "");
                            String CATEGORY_ID = mapID.get(position);
                            bzynessDetails.setBzyness_category_id(CATEGORY_ID);
                            categoryName.setText(names.get(Integer.parseInt(position)).toUpperCase());

                        }
                    });
                    bzynessDetailsList.setAdapter(categoryAdapter);
                } else {
                    Log.i(TAG, "Error" + responsecode);
                }
            }
        }


    }
    */
}

