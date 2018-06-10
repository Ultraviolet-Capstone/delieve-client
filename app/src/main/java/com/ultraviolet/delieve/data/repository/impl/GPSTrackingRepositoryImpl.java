package com.ultraviolet.delieve.data.repository.impl;

import com.ultraviolet.delieve.data.dto.GPSReceiverDto;
import com.ultraviolet.delieve.data.dto.GPSSenderDto;
import com.ultraviolet.delieve.data.repository.GPSTrackingRepository;
import com.ultraviolet.delieve.data.service.GPSTrackingService;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class GPSTrackingRepositoryImpl implements GPSTrackingRepository{

    private GPSTrackingService service;

    @Inject
    public GPSTrackingRepositoryImpl(GPSTrackingService service) {
        this.service = service;
    }

    @Override
    public Observable<Response<GPSSenderDto>> postGPS(GPSSenderDto dto) {
        Observable<Response<GPSSenderDto>> res = service
                .postGPS(dto);
        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<GPSReceiverDto>> getGPS(int matchingId) {
        Observable<Response<GPSReceiverDto>> res = service
                .getGPS(matchingId);
        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
