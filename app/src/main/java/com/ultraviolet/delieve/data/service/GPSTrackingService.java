package com.ultraviolet.delieve.data.service;

import com.ultraviolet.delieve.data.dto.GPSReceiverDto;
import com.ultraviolet.delieve.data.dto.GPSSenderDto;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface GPSTrackingService {

    @POST("/api/gps/tracking")
    Observable<Response<GPSSenderDto>> postGPS(@Body GPSSenderDto dto);

    @GET("/api/gps/tracking/{id}")
    Observable<Response<GPSReceiverDto>> getGPS(@Path("id") int matchingId);


}
