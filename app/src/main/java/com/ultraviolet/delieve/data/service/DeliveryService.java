package com.ultraviolet.delieve.data.service;

import com.ultraviolet.delieve.data.dto.DelieverAcceptDto;
import com.ultraviolet.delieve.data.dto.DelieverAcceptResDto;
import com.ultraviolet.delieve.data.dto.DeliveryMatchingForDelieverDto;
import com.ultraviolet.delieve.data.dto.DeliveryMatchingForSenderDto;
import com.ultraviolet.delieve.data.dto.DeliveryRequestDto;
import com.ultraviolet.delieve.data.dto.DeliveryRequestResDto;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface DeliveryService {

    @POST("/api/request")
    Observable<Response<DeliveryRequestResDto>> postRequest(@Body DeliveryRequestDto dto);

    @GET("http://13.125.124.127/matching")
    Observable<Response<DeliveryMatchingForDelieverDto>> getDeliveryMatchingForDeliever(@Query("lat") double lat,
                                                                                        @Query("long") double lng ,
                                                                                        @Query("userId") String id);

    @GET("/api/matching")
    Observable<Response<DeliveryMatchingForSenderDto>> getDeliveryMatchingForSender(@Query("requestId") String id);

    @POST("/api/matching")
    Observable<Response<DelieverAcceptResDto>> delieverAccept(@Body DelieverAcceptDto dto);

    @GET("http://13.125.124.127/matching/flush")
    Observable<Response<Void>> delieverFlush(@Query("delivererId") int delieverId);

    @GET("/api/matching/user/{userId}")
    Observable<Response<List<DeliveryMatchingForSenderDto>>> getDeliveryMatchingList(@Path("userId")int id);

}
