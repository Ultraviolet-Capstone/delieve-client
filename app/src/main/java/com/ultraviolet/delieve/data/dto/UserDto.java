package com.ultraviolet.delieve.data.dto;

import com.google.gson.annotations.SerializedName;

public class UserDto {

    @SerializedName("name")
    String name;

    @SerializedName("phone")
    String phone;

    @SerializedName("email")
    String email;

    @SerializedName("birthday")
    String birthDay;

    @SerializedName("token")
    String token;

    @SerializedName("tokenProvider")
    String tokenProvider;

    @SerializedName("providerSelfiURL")
    String providerSelfiURL;

    @SerializedName("providerNickname")
    String providerNickname;

    @SerializedName("gender")
    String gender;

    public UserDto(String name,
                   String phone,
                   String email,
                   String birthDay,
                   String token,
                   String tokenProvider,
                   String providerSelfiURL,
                   String providerNickname,
                   String gender) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthDay = birthDay;
        this.token = token;
        this.tokenProvider = tokenProvider;
        this.providerSelfiURL = providerSelfiURL;
        this.providerNickname = providerNickname;
        this.gender = gender;
    }
}
