package com.ultraviolet.delieve.model;

import java.io.Serializable;

public class User implements Serializable {

    public static final int USER_BEFORE_DELIEVER = 0;
    public static final int USER_WAITNG_FOR_JUDGE = 1;
    public static final int USER_DELIEVER = 2;
    public static final int USER_JUDGE_PENDING = 3;
    public static final int USER_JUDGE_DECLINE = 4;


    private String authToken;

    private int delivable;
    private String name;
    private String phone;
    private String email;
    private String birthDay;
    private int userId;


    private String profilePicURL;


    public User(String authToken, int delivable, String name, String phone, String email, String birthDay, int userId) {
        this.authToken = authToken;
        this.delivable = delivable;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthDay = birthDay;
        this.userId = userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public int getDelivable() {
        return delivable;
    }

    public void setDelivable(int delivable) {
        this.delivable = delivable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

}
