package com.ultraviolet.delieve.data.dto;

import com.google.gson.annotations.SerializedName;

public class AuthDto {

    @SerializedName("accessToken")
    public String accessToken;

    public AuthDto(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
