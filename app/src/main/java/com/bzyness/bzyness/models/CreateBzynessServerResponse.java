package com.bzyness.bzyness.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Pervacio on 2/15/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "error",
        "message",
        "expires"

})
public class CreateBzynessServerResponse {

    @JsonProperty("error")
    private boolean error;

    //onSuccess
    @JsonProperty("bzynessId")
    private Integer bzynessId;


    //onError
    @JsonProperty("message")
    private String message;




    public CreateBzynessServerResponse(){}


    @JsonProperty("bzynessId")
    public Integer getBzynessId() {
        return bzynessId;
    }

    @JsonProperty("bzynessId")
    public void setBzynessId(Integer bzynessId) {
        this.bzynessId = bzynessId;
    }

    @JsonProperty("error")
    public boolean getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(boolean error) {
        this.error = error;
    }


    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }


}