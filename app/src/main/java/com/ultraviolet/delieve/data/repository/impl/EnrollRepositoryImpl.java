package com.ultraviolet.delieve.data.repository.impl;

import com.ultraviolet.delieve.data.repository.EnrollRepository;
import com.ultraviolet.delieve.data.service.EnrollService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class EnrollRepositoryImpl implements EnrollRepository  {

    private EnrollService service;

    @Inject
    public EnrollRepositoryImpl(EnrollService service) {
        this.service = service;
    }

    @Override
    public Observable<Response<Void>> requestEnroll(MultipartBody.Part selfie, MultipartBody.Part idCard, MultipartBody.Part id) {


        Observable<Response<Void>> res = service
                .requestEnroll(selfie, idCard, id);
        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
