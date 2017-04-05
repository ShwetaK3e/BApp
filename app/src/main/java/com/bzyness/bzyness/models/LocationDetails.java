package com.bzyness.bzyness.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.sym.Name;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pervacio on 2/14/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "locationID",
        "longitude",
        "latitude"
})
public class LocationDetails {

    @JsonProperty("locationID")
    private Integer locationID;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("latitude")
    private Double latitude;

    private Float ratings;
    private String name;



    private String _ID;
    private Float loved;

    public LocationDetails(Float loved, LatLng latLng, Float ratings, String name) {
        this.loved = loved;
        this.longitude = latLng.longitude;
        this.latitude = latLng.latitude;
        this.ratings = ratings;
        this.name = name;
    }


    @JsonProperty("locationID")
    public Integer getLocationID() {
        return locationID;
    }

    @JsonProperty("locationID")
    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public Float getRatings() {
        return ratings;
    }

    public void setRatings(Float ratings) {
        this.ratings = ratings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLoved() {
        return loved;
    }

    public void setLoved(Float loved) {
        this.loved = loved;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }
}