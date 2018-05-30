package com.ultraviolet.delieve.data.repository.impl;

import com.ultraviolet.delieve.data.repository.EnrollRepository;
import com.ultraviolet.delieve.data.service.EnrollService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

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
    public Observable<Response<Void>> requestEnroll(RequestBody selfie, RequestBody idCard, RequestBody id) {

        Map<String, RequestBody> map = new HashMap<>();

        map.put("userId", id);
        map.put("selfi", selfie);
        map.put("idCard", idCard);

        Observable<Response<Void>> res = service
                .requestEnroll(map);
        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
