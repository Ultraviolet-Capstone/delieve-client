package com.ultraviolet.delieve.data.repository;

import com.ultraviolet.delieve.data.dto.QRDto;

import retrofit2.Response;
import rx.Observable;

public interface QRApiRepository {

    Observable<Response<QRDto>> getQRHash(int matchingId, String status);

    Observable<Response<QRDto>> postQRHash(QRDto qrDto);

}
