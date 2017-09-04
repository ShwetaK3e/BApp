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
public class ServerResponse implements Parcelable {

    //Common
    @JsonProperty("error")
    private boolean error;

    //Register , Login error
    @JsonProperty("message")
    private String message;

    //Login
    @JsonProperty("token")
    private String accessToken;
    @JsonProperty("expires")
    private Long expiresIn;

    //startBzynessFirstcall
    @JsonProperty("bzynessId")
    private Integer bzynessId;

    public ServerResponse(){}

    protected ServerResponse(Parcel in) {
        error = in.readByte() != 0;
        message = in.readString();
        accessToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (error ? 1 : 0));
        dest.writeString(message);
        dest.writeString(accessToken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ServerResponse> CREATOR = new Creator<ServerResponse>() {
        @Override
        public ServerResponse createFromParcel(Parcel in) {
            return new ServerResponse(in);
        }

        @Override
        public ServerResponse[] newArray(int size) {
            return new ServerResponse[size];
        }
    };

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

    @JsonProperty("token")
    public String getAccessToken() {
        return accessToken;
    }

    @JsonProperty("token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @JsonProperty("expires")
    public Long getExpiresIn() {
        return expiresIn;
    }

    @JsonProperty("expires")
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

}