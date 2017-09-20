package com.bzyness.bzyness.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bzyness.bzyness.CustomWidgets.BAppEditTextNormal;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.BzynessTypeAdapter;
import com.bzyness.bzyness.models.BzynessDetails;
import com.bzyness.bzyness.models.BzynessTypeDetails;
import com.bzyness.bzyness.models.ProductCatList;
import com.bzyness.bzyness.models.ProductList;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bzyness.bzyness.BaseActivity.bzynessClient;


public class NewBPrdctFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private static final String TAG=NewBPrdctFragment.class.getSimpleName();

    BAppEditTextNormal guideLine;
    RecyclerView prdctList;
    ImageButton back_btn;
    BzynessTypeAdapter prdct_adapter;




    public static NewBPrdctFragment newInstance() {
        NewBPrdctFragment fragment = new NewBPrdctFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"NewBLocFragment onCreate ");
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        Log.i(TAG,"NewBLocFragment onCreateView ");

        View view=inflater.inflate(R.layout.fragment_add_products, container, false);
        guideLine=view.findViewById(R.id.prdct_guideline);
        back_btn=view.findViewById(R.id.back_btn);
        prdctList=view.findViewById(R.id.product_list);
        prdctList.setLayoutManager(new GridLayoutManager(getActivity(),3));

        return view;
    }

















}
