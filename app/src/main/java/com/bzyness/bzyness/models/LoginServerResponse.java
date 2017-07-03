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
        "token",
        "expires"

})
public class LoginServerResponse implements Parcelable {


    @JsonProperty("error")
    private boolean error=false;

    //onSuccess
    @JsonProperty("token")
    private String token="";
    @JsonProperty("expires")
    private Long expires=0L;

    //onError
    @JsonProperty("message")
    private String message="";


    public LoginServerResponse(){}

    protected LoginServerResponse(Parcel in) {
        error = in.readByte() != 0;
        message = in.readString();
        token = in.readString();
        expires=in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (error ? 1 : 0));
        dest.writeString(message);
        dest.writeString(token);
        dest.writeLong(expires);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginServerResponse> CREATOR = new Creator<LoginServerResponse>() {
        @Override
        public LoginServerResponse createFromParcel(Parcel in) {
            return new LoginServerResponse(in);
        }

        @Override
        public LoginServerResponse[] newArray(int size) {
            return new LoginServerResponse[size];
        }
    };


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

    @JsonProperty("token")
    public String getAccessToken() {
        return token;
    }

    @JsonProperty("token")
    public void setAccessToken(String token) {
        this.token = token;
    }

    @JsonProperty("expires")
    public Long getExpiresIn() {
        return expires;
    }

    @JsonProperty("expires")
    public void setExpiresIn(Long expires) {
        this.expires = expires;
    }

}