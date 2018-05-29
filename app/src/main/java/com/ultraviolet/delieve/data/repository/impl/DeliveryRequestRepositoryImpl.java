package com.ultraviolet.delieve.data.repository.impl;

import com.ultraviolet.delieve.data.dto.DelieveryRequestDto;

import com.ultraviolet.delieve.data.repository.DeliveryRequestRepository;
import com.ultraviolet.delieve.data.service.DeliveryRequestService;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class DeliveryRequestRepositoryImpl implements DeliveryRequestRepository {

    private DeliveryRequestService service;

    @Inject
    public DeliveryRequestRepositoryImpl(DeliveryRequestService service){
        this.service = service;
    }
    @Override
    public Observable<Response<Void>> postRequest(DelieveryRequestDto dto) {
        Observable<Response<Void>> res = service
                .postRequest(dto);
        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
