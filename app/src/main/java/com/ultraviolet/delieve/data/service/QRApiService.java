package com.ultraviolet.delieve.data.service;

import com.ultraviolet.delieve.data.dto.QRDto;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface QRApiService {

    @GET("/api/qr")
    Observable<Response<QRDto>> getQRHash(@Query("id")int matchingId, @Query("status") String status);

    @POST("/api/qr")
    Observable<Response<QRDto>> postQRHash(@Body QRDto qrDto);
}

