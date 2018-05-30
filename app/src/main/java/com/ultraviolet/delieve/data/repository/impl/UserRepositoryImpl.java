package com.ultraviolet.delieve.data.repository.impl;

import android.util.Log;

import com.ultraviolet.delieve.data.dto.LoginDto;
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
    public void userSignIn(LoginDto loginDto){
        this.user = new User(loginDto.accessToken,
                loginDto.userInfo.delivable,
                loginDto.userInfo.name,
                loginDto.userInfo.phone,
                loginDto.userInfo.email,
                loginDto.userInfo.birthDay,
                loginDto.userInfo.id
                );

        Log.i("delieve", "user signed in as " + user.getName());
        Log.i("delieve", "state " + user.getDelivable());
    }

    @Override
    public int getUserType() {
        return user.getDelivable();
    }

    @Override
    public int getUserId() {
        return user.getUserId();
    }
}
