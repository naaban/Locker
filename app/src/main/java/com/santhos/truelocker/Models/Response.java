package com.santhos.truelocker.Models;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("response")
    private String response;

    @SerializedName("userid")
    private String userId;

    @SerializedName("key")
    private String key ;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
