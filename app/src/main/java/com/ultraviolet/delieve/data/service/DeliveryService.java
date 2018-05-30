package com.ultraviolet.delieve.data.service;

import com.ultraviolet.delieve.data.dto.DeliveryMatchingDto;
import com.ultraviolet.delieve.data.dto.DeliveryRequestDto;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface DeliveryService {

    @POST("/api/request")
    Observable<Response<Void>> postRequest(@Body DeliveryRequestDto dto);

    @GET("http://13.125.124.127/matching")
    Observable<Response<DeliveryMatchingDto>> getDeliveryMatching(@Query("lat") double lat,
                                                                  @Query("long") double lng ,
                                                                  @Query("userId") String id);

    @GET("/matching")
    Observable<Response<DeliveryMatchingDto>> getDeliveryMatchingForSender(@Query("lat") double lat,
                                                                  @Query("long") double lng ,
                                                                  @Query("userId") String id);

}
