package com.ultraviolet.delieve.data.repository.impl;

import android.util.Log;

import com.ultraviolet.delieve.data.dto.LoginDto;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.data.service.UserInfoService;
import com.ultraviolet.delieve.model.User;

import javax.inject.Inject;
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
    public String getUserProfilePicURL() {
        return user.getProfilePicURL();
    }

    @Override
    public void setUserProfilePicURL(String url) {
        user.setProfilePicURL(url);
    }

    @Override
    public int getUserType() {
        return user.getDelivable();
    }

    @Override
    public void setUserType(int i) {
        user.setDelivable(i);
    }

    @Override
    public int getUserId() {
        return user.getUserId();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isUserMatching() {
        if (user.getMatchingIdForDeliever() == -1){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public int getUserMatchingId() {
        return user.getMatchingIdForDeliever();
    }

    @Override
    public void setUserMatching(int matchingId) {
        if (matchingId != -1 ){
            Log.i("credt", "user has matching id " + matchingId);
        }
        user.setMatchingIdForDeliever(matchingId);
    }
}
