package com.ultraviolet.delieve.data.repository.impl;

import com.ultraviolet.delieve.data.service.AuthService;
import com.ultraviolet.delieve.data.dto.AuthDto;
import com.ultraviolet.delieve.data.repository.AuthRepository;


import javax.inject.Inject;
import javax.inject.Singleton;

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
    public Observable<AuthDto> getToken(String username, String password, String grantType) {
        Observable<AuthDto> authDto = service
                .getToken(username,
                        password,
                        grantType);
        return authDto.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
