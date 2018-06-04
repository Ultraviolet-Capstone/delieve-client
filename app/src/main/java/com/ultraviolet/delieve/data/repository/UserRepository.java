package com.ultraviolet.delieve.data.repository;

import com.ultraviolet.delieve.data.dto.DelieverDelivableDto;
import com.ultraviolet.delieve.data.dto.LoginDto;


import retrofit2.Response;
import rx.Observable;

public interface UserRepository {

    boolean isUserSignedIn();

    void userSignIn(LoginDto loginDto);

    int getUserType();
    void setUserType(int i);

    int getUserId();

}
