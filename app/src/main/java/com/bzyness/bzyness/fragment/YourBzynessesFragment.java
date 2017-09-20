package com.bzyness.bzyness.fragment;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.AppUtils.UtilityFunc;
import com.bzyness.bzyness.CustomWidgets.BAppTextViewBold;
import com.bzyness.bzyness.CustomWidgets.BAppTextViewItalic;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.BzynessListAdapter;
import com.bzyness.bzyness.adapters.BzynessTypeAdapter;
import com.bzyness.bzyness.models.BzynessList;
import com.bzyness.bzyness.models.ProductCatList;
import com.bzyness.bzyness.models.ProductCategory;
import com.bzyness.bzyness.models.ProductList;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bzyness.bzyness.BaseActivity.bzynessClient;


public class YourBzynessesFragment extends Fragment {

    private static String TAG=YourBzynessesFragment.class.getSimpleName();

    RecyclerView bzynessListView;
    BzynessListAdapter adapter;

    BAppTextViewItalic no_bzyness_text;

    LinearLayout add_bzyness;


    public static YourBzynessesFragment newInstance() {
        YourBzynessesFragment fragment = new YourBzynessesFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bzyness_list, container, false);
        bzynessListView=view.findViewById(R.id.bzynessList);
        no_bzyness_text=view.findViewById(R.id.no_bzyness_text);
        add_bzyness=view.findViewById(R.id.add_bzyness);
        add_bzyness.setOnClickListener(click->{
            android.support.v4.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
            Fragment f=AddNewBzynessFragment.newInstance();
            ft.replace(R.id.frame,f);
            ft.addToBackStack(null);
            ft.commit();
        });
        populateBzynessList();
        return  view;
    }


    void populateBzynessList(){
        if(bzynessClient!=null){
            bzynessClient.getAllYourBzyness("Bearer "+ UtilityFunc.getStringFromSharedPreference(getContext(),Constants.pref_accessToken),Integer.parseInt(UtilityFunc.getStringFromSharedPreference(getContext(),Constants.pref_uid)))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BzynessList>() {
                        @Override
                        public void onCompleted() {
                            Log.i(TAG,"bzylist fetched");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG,"Error:" +e.getMessage());
                            populateBzynessList();
                        }

                        @Override
                        public void onNext(BzynessList bzynessList) {
                            if(!bzynessList.getError()){

                                List<BzynessList.Bzyness> bzynesses=bzynessList.getBzynesses();
                                if(bzynesses!=null){
                                    no_bzyness_text.setVisibility(View.INVISIBLE);
                                    adapter=new BzynessListAdapter(getContext(),bzynesses, type_id -> {

                                    });

                                    bzynessListView.setAdapter(adapter);
                                }else{
                                    no_bzyness_text.setVisibility(View.VISIBLE);
                                }


                            }


                        }
                    });
        }
    }





}
