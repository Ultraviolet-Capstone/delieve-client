package com.ultraviolet.delieve.data.repository.impl;

import android.util.Log;

import com.ultraviolet.delieve.data.dto.LoginDto;
import com.ultraviolet.delieve.data.dto.UserDto;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.model.User;

import javax.inject.Singleton;

@Singleton
public class UserRepositoryImpl implements UserRepository {

    @Singleton
    private User user;

    @Override
    public boolean isUserSignedIn() {
        return user != null;
    }

    @Override
    public void userSignIn(LoginDto loginDto){
        user = new User(loginDto.accessToken,
                loginDto.userInfo.Delivable,
                loginDto.userInfo.name,
                loginDto.userInfo.phone,
                loginDto.userInfo.email,
                loginDto.userInfo.birthDay
                );
        Log.i("delieve", "user signed in as " + user.getName());
    }

    @Override
    public int getUserType() {
        return user.getDelivable();
    }
}
