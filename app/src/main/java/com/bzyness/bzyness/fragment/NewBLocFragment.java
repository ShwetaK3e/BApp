package com.bzyness.bzyness.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.BzynessDetails;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class NewBLocFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MSG = "ARG_MSG";

    // TODO: Rename and change types of parameters
    private int page;


    SupportMapFragment supportMapFragment;
    LinearLayout locEdit;
    LatLng latLng;
    GoogleMap googleMap;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    LinearLayout mapScheme;
    LinearLayout snap_your_location;


    private boolean DONT_PAUSE = false, REMOVED = false;

    private static final String TAG=NewBLocFragment.class.getSimpleName();
    BzynessDetails bzynessDetails;


    public static NewBLocFragment newInstance(int page, BzynessDetails bzynessDetails) {
        NewBLocFragment fragment = new NewBLocFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MSG, page);
        fragment.setArguments(args);
        fragment.bzynessDetails = bzynessDetails;
        Log.i(TAG,"NewBLocFragment newInstance ");
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_MSG);
        }
        Log.i(TAG,"NewBLocFragment onCreate ");
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        Log.i(TAG,"NewBLocFragment onCreateView ");


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_bloc, container, false);


        /*mapScheme=(LinearLayout)view.findViewById(R.id.map_scheme);

        supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.loc_map);
        supportMapFragment.getMapAsync(this);
        latLng=new LatLng(14.0f, 72.0f);

        snap_your_location = (LinearLayout) view.findViewById(R.id.add_loc_from_google);
        snap_your_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snap_your_location.setVisibility(INVISIBLE);
                mapScheme.setVisibility(INVISIBLE);
                locEdit.setVisibility(VISIBLE);
                setLocation();
            }
        });

        locEdit = (LinearLayout) view.findViewById(R.id.loc_edit);
        locEdit.setVisibility(View.INVISIBLE);
        locEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snap_your_location.setVisibility(VISIBLE);
                mapScheme.setVisibility(View.VISIBLE);
                locEdit.setVisibility(View.INVISIBLE);
                googleMap.addMarker(null);

            }
        });

*/
        return view;
    }


    @Override
    public void onPause() {
        Log.i(TAG,"NewBLocFragment onPause ");
        if (supportMapFragment != null && !DONT_PAUSE) {
            getChildFragmentManager().beginTransaction().remove(supportMapFragment).commit();
            REMOVED = true;

        }
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.i(TAG,"NewBLocFragment onResume");
        super.onResume();
        /*if (supportMapFragment != null && REMOVED) {
           // getChildFragmentManager().beginTransaction().add(R.id.loc_map, supportMapFragment).commit();
        }

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }*/
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG,"NewBLocFragment onMapReady ");
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setAllGesturesEnabled(false);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
    }


    public void setLocation() {
        Log.i(TAG,"NewBLocFragment setLocation ");
        if (mLastLocation == null) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "here");
                return;
            }
            Log.i(TAG, "Not here");
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }
        Double latitude = mLastLocation.getLatitude();
        Double longitude = mLastLocation.getLongitude();
        latLng = new LatLng(latitude, longitude);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
        this.googleMap.addMarker(new MarkerOptions().position(latLng).title(bzynessDetails.getBzyness_name() == null ? "Bzyness Loc" : bzynessDetails.getBzyness_name()));
        bzynessDetails.setLatitude(String.valueOf(latitude));
        bzynessDetails.setLongitude(String.valueOf(longitude));
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.i(TAG,"NewBLocFragment onConnected");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, " here");
            return;
        }
        Log.i(TAG, "Not here");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Log.i(TAG,"Last Location"+mLastLocation.toString());

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG,"NewBLocFragment onConeection suspended ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG,"NewBLocFragment onConnection Failed ");
    }
}
