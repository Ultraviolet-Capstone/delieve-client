package com.ultraviolet.delieve.data.repository.impl;

import com.ultraviolet.delieve.data.dto.LoginDto;
import com.ultraviolet.delieve.data.dto.TokenDto;
import com.ultraviolet.delieve.data.dto.UserDto;
import com.ultraviolet.delieve.data.service.AuthService;
import com.ultraviolet.delieve.data.repository.AuthRepository;


import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class AuthRepositoryImpl implements AuthRepository {

    private AuthService service;

    @Inject
    public AuthRepositoryImpl(AuthService service) {
        this.service = service;
    }

    @Override
    public Observable<TokenDto> getToken(String username, String password, String grantType) {
        Observable<TokenDto> tokenDto = service
                .getToken(username,
                        password,
                        grantType);
        return tokenDto.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<LoginDto>> login(String token) {
        Observable<Response<LoginDto>> loginDto = service
                .login(token);
        return loginDto.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Void>> register(UserDto userDto) {
        Observable<Response<Void>> res = service
                .register(userDto);
        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
