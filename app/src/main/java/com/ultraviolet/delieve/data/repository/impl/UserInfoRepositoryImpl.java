package com.ultraviolet.delieve.data.repository.impl;

import com.ultraviolet.delieve.data.dto.DelieverDelivableDto;
import com.ultraviolet.delieve.data.repository.UserInfoRepository;
import com.ultraviolet.delieve.data.service.UserInfoService;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserInfoRepositoryImpl implements UserInfoRepository{

    private UserInfoService service;

    @Inject
    public UserInfoRepositoryImpl(UserInfoService service){
        this.service = service;
    }

    @Override
    public Observable<Response<DelieverDelivableDto>> fetchUserDelivable(int id) {
        Observable<Response<DelieverDelivableDto>> res = service
                .fetchUserDelivable(id);
        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
