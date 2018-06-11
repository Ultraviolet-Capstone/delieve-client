package com.ultraviolet.delieve.data.repository.impl;

import com.ultraviolet.delieve.data.dto.QRDto;
import com.ultraviolet.delieve.data.repository.QRApiRepository;
import com.ultraviolet.delieve.data.service.QRApiService;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class QRApiRepositoryImpl implements QRApiRepository{

    private QRApiService service;

    @Inject
    public QRApiRepositoryImpl(QRApiService service) {
        this.service = service;
    }

    @Override
    public Observable<Response<QRDto>> getQRHash(int matchingId, String status) {
        Observable<Response<QRDto>> res = service
                .getQRHash(matchingId, status);
        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<QRDto>> postQRHash(QRDto qrDto) {
        Observable<Response<QRDto>> res = service
                .postQRHash(qrDto);
        return res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
