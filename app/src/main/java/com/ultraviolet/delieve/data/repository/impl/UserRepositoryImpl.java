package com.ultraviolet.delieve.data.repository.impl;

import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.model.User;

import javax.inject.Singleton;

@Singleton
public class UserRepositoryImpl implements UserRepository {

    private User user;

    @Override
    public boolean isUserSignedIn() {
        return user != null;
    }

    @Override
    public void userSignIn(String token) {
        user  = new User();
    }

    @Override
    public int getUserType() {
        return user.getType();
    }
}
