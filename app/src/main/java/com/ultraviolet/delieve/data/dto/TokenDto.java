package com.ultraviolet.delieve.data.dto;

import com.google.gson.annotations.SerializedName;

public class TokenDto {

    @SerializedName("accessToken")
    public String accessToken;

    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getAccessToken() {
        return accessToken;
    }

}
