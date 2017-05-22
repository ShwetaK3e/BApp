package com.bzyness.bzyness.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ServerResponse {

    @JsonProperty("error")
    private boolean error;
    @JsonProperty("message")
    private String message;
    @JsonProperty("token")
    private String accessToken;
    @JsonProperty("expires")
    private Long expiresIn;
    @JsonProperty("bzynessId")
    private Integer bzynessId;


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