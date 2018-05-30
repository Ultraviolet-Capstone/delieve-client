package com.ultraviolet.delieve.data.dto;

import com.google.gson.annotations.SerializedName;

public class LoginDto {

    public String accessToken;
    public UserInfo userInfo;

    public class UserInfo{
        public int id;
        public int Delivable;
        public String name;
        public String phone;
        public String email;
        public String birthDay;
    }
}

