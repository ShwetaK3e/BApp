package com.bzyness.bzyness.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.models.BzynessDetails;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class NewBLocFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MSG = "ARG_MSG";

    // TODO: Rename and change types of parameters
    private int page;

    ScrollView scrollView;

    SupportMapFragment supportMapFragment;
    TextView locName, locRating;
    LinearLayout locEdit, cancelEdit;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    LatLng latLng;
    GoogleMap googleMap;
    LinearLayout mapScheme;


    LinearLayout addLocBtns;
    LinearLayout addPlaceFromGoogleButton, addNewPlaceButton;
    LinearLayout locDetailsLayout;
    TextView changePlaceGuideline,addPlaceGuideline;
    Button savePlace,editPlace;
    LinearLayout locForm;
    EditText storeName,zip,locality,street,city,state,country;
    private boolean mapSaved=false;

    private boolean DONT_PAUSE=false, REMOVED=false;
    GoogleMapOptions mapOptions;
    static CameraPosition savedCameraPostion;

    BzynessDetails bzynessDetails;



    public static NewBLocFragment newInstance(int page,BzynessDetails bzynessDetails) {
        NewBLocFragment fragment = new NewBLocFragment();
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
                             final Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_bloc, container, false);

        scrollView=(ScrollView)view.findViewById(R.id.new_bloc);

        addPlaceGuideline = (TextView) view.findViewById(R.id.add_place_guideline);
        changePlaceGuideline = (TextView) view.findViewById(R.id.change_loc_guideline);
        locForm = (LinearLayout) view.findViewById(R.id.loc_form);
        locForm.setVisibility(GONE);
        locForm = (LinearLayout) view.findViewById(R.id.loc_form);
        locForm.setVisibility(GONE);
        storeName=(EditText)view.findViewById(R.id.store_name);
        zip=(EditText)view.findViewById(R.id.zip);
        locality=(EditText)view.findViewById(R.id.locality);
        street=(EditText)view.findViewById(R.id.street);
        city=(EditText)view.findViewById(R.id.city);
        state=(EditText)view.findViewById(R.id.state);
        country=(EditText)view.findViewById(R.id.country);
        savePlace=(Button)view.findViewById(R.id.save_place);
        savePlace.setVisibility(GONE);
        savePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("Save".equalsIgnoreCase(savePlace.getText().toString())) {
                    addingPlace();
                }else{
                    googleMap.getUiSettings().setAllGesturesEnabled(false);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
                    savePlace.setVisibility(GONE);
                    locEdit.setVisibility(VISIBLE);
                    editPlace.setVisibility(GONE);
                }
            }
        });
        editPlace=(Button)view.findViewById(R.id.edit_place);
        editPlace.setVisibility(GONE);
        editPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locForm.setVisibility(VISIBLE);
                editPlace.setVisibility(GONE);
                savePlace.setText("Save");
                locForm.getParent().requestChildFocus(locForm,locForm);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0,street.getBottom());
                    }
                });
            }
        });


        addLocBtns=(LinearLayout) view.findViewById(R.id.add_loc_btn);
        addPlaceFromGoogleButton=(LinearLayout) view.findViewById(R.id.add_loc_from_google);
        addPlaceFromGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation();
                locEdit.setVisibility(INVISIBLE);
                cancelEdit.setVisibility(INVISIBLE);
                locForm.setVisibility(GONE);
                savePlace.setVisibility(GONE);
                editPlace.setVisibility(GONE);
            }
        });
        addNewPlaceButton=(LinearLayout) view.findViewById(R.id.add_new_loc);
        addNewPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locEdit.setVisibility(INVISIBLE);
                cancelEdit.setVisibility(INVISIBLE);
                locForm.setVisibility(View.VISIBLE);
                savePlace.setVisibility(View.VISIBLE);
                savePlace.setText("Save");
                showAddPlaceGuideLine();
                locForm.getParent().requestChildFocus(locForm,locForm);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0,street.getBottom());
                    }
                });

            }
        });


        mapScheme = (LinearLayout) view.findViewById(R.id.map_scheme);

        supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.loc_map);
        supportMapFragment.getMapAsync(this);

        latLng = new LatLng(12.79037479, 77.50854492);

        locDetailsLayout = (LinearLayout) view.findViewById(R.id.loc_details);
        showAddPlaceGuideLine();
        locName = (TextView) view.findViewById(R.id.loc_name);
        locRating = (TextView) view.findViewById(R.id.loc_rating);
        locEdit = (LinearLayout) view.findViewById(R.id.loc_edit);
        locEdit.setVisibility(View.INVISIBLE);
        locEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocBtns.setVisibility(View.VISIBLE);
                mapScheme.setVisibility(View.VISIBLE);
                locEdit.setVisibility(View.INVISIBLE);
                cancelEdit.setVisibility(View.VISIBLE);
                showAddPlaceGuideLine();
            }
        });
        cancelEdit = (LinearLayout) view.findViewById(R.id.cancel_edit);
        cancelEdit.setVisibility(View.INVISIBLE);
        cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocBtns.setVisibility(View.INVISIBLE);
                mapScheme.setVisibility(View.INVISIBLE);
                cancelEdit.setVisibility(View.INVISIBLE);
                locEdit.setVisibility(View.VISIBLE);
                locForm.setVisibility(GONE);
                showlocDetails();
            }
        });

        return view;
    }


    @Override
    public void onPause() {
        if(supportMapFragment!=null && !DONT_PAUSE ) {
            getChildFragmentManager().beginTransaction().remove(supportMapFragment).commit();
            REMOVED=true;
            mapOptions=new GoogleMapOptions();
            savedCameraPostion=googleMap.getCameraPosition();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(supportMapFragment!=null && REMOVED) {
            getChildFragmentManager().beginTransaction().add(R.id.loc_map,supportMapFragment).commit();
            }
        if(mapOptions!=null) {
            if (savedCameraPostion != null) {
                mapOptions.camera(savedCameraPostion);
            } else {
                mapOptions.camera(CameraPosition.fromLatLngZoom(new LatLng(12.79037479, 77.50854492), 4.0f));
            }

            supportMapFragment = SupportMapFragment.newInstance(mapOptions);
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setAllGesturesEnabled(false);
        this.googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                NewBLocFragment.this.latLng=latLng;
                NewBLocFragment.this.googleMap.getUiSettings().setAllGesturesEnabled(true);
                NewBLocFragment.this.googleMap.clear();
                NewBLocFragment.this.googleMap.addMarker(new MarkerOptions().position(latLng).title("Business Marker"));
                NewBLocFragment.this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
            }
        });
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,14.0f));
    }


    private void addingPlace() {
        List<Address> addresses=new ArrayList<>();
        Geocoder geocoder=new Geocoder(getActivity(), Locale.getDefault());
        String address=street.getText().toString().trim()+","+locality.getText().toString().trim()+","+city.getText().toString().trim()+","+state.getText().toString().trim()+","+zip.getText().toString().trim()+","+country.getText().toString().trim();
        try {
            addresses=geocoder.getFromLocationName(address,1);
            if(addresses.isEmpty()){
                Toast.makeText(getActivity(), "Not A Correct Address. Check the entered Details", Toast.LENGTH_LONG).show();
            }else {
                locForm.setVisibility(GONE);
                editPlace.setVisibility(VISIBLE);
                latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                showChangePlaceGuideLine();
                mapScheme.setVisibility(View.INVISIBLE);
                addLocBtns.setVisibility(View.INVISIBLE);
                googleMap.getUiSettings().setAllGesturesEnabled(true);
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Business Marker"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
                savePlace.setText("SET THIS LOCATION");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLocation() {
        try {
            DONT_PAUSE=true;
            getActivity().startActivityForResult(builder.build(getActivity()), Constants.PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        DONT_PAUSE=false;
        if (resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(getActivity(), data);
            cancelEdit.setVisibility(View.INVISIBLE);
            showlocDetails();
            locName.setText(place.getName());
            locRating.setText(place.getRating() > 0 ? Float.toString(place.getRating()) :"NEW");
            locEdit.setVisibility(View.VISIBLE);
            latLng = place.getLatLng();
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Business Marker"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
            addLocBtns.setVisibility(View.INVISIBLE);
            mapScheme.setVisibility(GONE);
            mapSaved=true;
            Toast.makeText(getActivity(), "New Location Added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "No Place Selected.", Toast.LENGTH_LONG).show();
        }

    }

    private void showAddPlaceGuideLine(){
        addPlaceGuideline.setVisibility(VISIBLE);
        changePlaceGuideline.setVisibility(INVISIBLE);
        locDetailsLayout.setVisibility(INVISIBLE);
    }

    private void showChangePlaceGuideLine(){
        addPlaceGuideline.setVisibility(INVISIBLE);
        changePlaceGuideline.setVisibility(VISIBLE);
        locDetailsLayout.setVisibility(INVISIBLE);
    }

    private void showlocDetails(){
        addPlaceGuideline.setVisibility(INVISIBLE);
        changePlaceGuideline.setVisibility(INVISIBLE);
        locDetailsLayout.setVisibility(VISIBLE);
    }

}
