package com.ultraviolet.delieve.data.repository;

public interface UserRepository {

    boolean isUserSignedIn();

    void userSignIn(String token);

    int getUserType();

}
