package com.ultraviolet.delieve.data.repository;

import com.ultraviolet.delieve.data.dto.DelieverDelivableDto;
import com.ultraviolet.delieve.data.dto.LoginDto;


import retrofit2.Response;
import rx.Observable;

public interface UserRepository {

    boolean isUserSignedIn();

    void userSignIn(LoginDto loginDto);

    boolean isUserMatching();
    int getUserMatchingId();
    void setUserMatching(int matchingId);


    int getUserType();

    void setUserType(int i);

    String getUserProfilePicURL();
    void setUserProfilePicURL(String url);

    int getUserId();

    String getUsername();
}
