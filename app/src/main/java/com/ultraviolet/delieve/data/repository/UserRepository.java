package com.ultraviolet.delieve.data.repository;

import com.ultraviolet.delieve.data.dto.LoginDto;

public interface UserRepository {

    boolean isUserSignedIn();

    void userSignIn(LoginDto loginDto);

    int getUserType();

    int getUserId();

}
