package com.bzyness.bzyness.models;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Pervacio on 2/23/2017.
 */

public class PlacePickerDetails {

    private String name;
    private String id;
    private String address;
    private LatLng latLng;
    private String phoneNumber;
    private List<Integer> types;
    private float rating;
    private int priceLevel;
    private String website;
    private Place place;



    public PlacePickerDetails(Place place) {
        this.place = place;
        setName(place.getName().toString());
        setAddress(place.getAddress().toString());
        setLatLng(place.getLatLng());
        setPhoneNumber(place.getPhoneNumber().toString());
        setId(place.getId());
        setPriceLevel(place.getPriceLevel());
        setRating(place.getRating());
        setTypes(place.getPlaceTypes());
        setWebsite(place.getWebsiteUri().toString());
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Integer> getTypes() {
        return types;
    }

    public float getRating() {
        return rating;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public String getWebsite() {
        return website;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


}
