package com.bzyness.bzyness.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "error",
        "user",
        "message"
})
public class UserDetails {

    @JsonProperty("error")
    private Boolean error;
    @JsonProperty("user")
    private User user;
    @JsonProperty("message")
    private String message= null;

    @JsonProperty("error")
    public Boolean getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(Boolean error) {
        this.error = error;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonPropertyOrder({
            "userId",
            "fullName",
            "email",
            "mobile",
            "role",
            "typeOfUser"
    })
    public class User {

        @JsonProperty("userId")
        private Long userId;
        @JsonProperty("fullName")
        private String fullName;
        @JsonProperty("email")
        private String email;
        @JsonProperty("mobile")
        private String mobile;
        @JsonProperty("role")
        private String role;
        @JsonProperty("typeOfUser")
        private String typeOfUser;


        @JsonProperty("userId")
        public Long getUserId() {
            return userId;
        }

        @JsonProperty("userId")
        public void setUserId(Long userId) {
            this.userId = userId;
        }

        @JsonProperty("fullName")
        public String getFullName() {
            return fullName;
        }

        @JsonProperty("fullName")
        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        @JsonProperty("email")
        public String getEmail() {
            return email;
        }

        @JsonProperty("email")
        public void setEmail(String email) {
            this.email = email;
        }

        @JsonProperty("mobile")
        public String getMobile() {
            return mobile;
        }

        @JsonProperty("mobile")
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        @JsonProperty("role")
        public String getRole() {
            return role;
        }

        @JsonProperty("role")
        public void setRole(String role) {
            this.role = role;
        }

        @JsonProperty("typeOfUser")
        public String getTypeOfUser() {
            return typeOfUser;
        }

        @JsonProperty("typeOfUser")
        public void setTypeOfUser(String typeOfUser) {
            this.typeOfUser = typeOfUser;
        }

    }

}
