package com.ultraviolet.delieve.data.repository.impl;

import com.ultraviolet.delieve.data.dto.DeliveryMatchingDto;
import com.ultraviolet.delieve.data.dto.DeliveryRequestDto;

import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.service.DeliveryService;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private DeliveryService service;

    @Inject
    public DeliveryRepositoryImpl(DeliveryService service){
        this.service = service;
    }

    @Override
    public Observable<Response<Void>> postDeliveryRequest(DeliveryRequestDto dto) {
        Observable<Response<Void>> res = service
                .postRequest(dto);
        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<Response<DeliveryMatchingDto>> getDeliveryMatching(double lat, double lng, String id) {
        Observable<Response<DeliveryMatchingDto>> res = service
                .getDeliveryMatching(lat, lng, id);
        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<DeliveryMatchingDto>> getDeliveryMatchingForSender(double lat, double lng, String id) {
        Observable<Response<DeliveryMatchingDto>> res = service
                .getDeliveryMatchingForSender(lat,lng,id);

        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
