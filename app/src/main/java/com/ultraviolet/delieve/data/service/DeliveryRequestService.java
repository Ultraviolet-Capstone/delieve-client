package com.ultraviolet.delieve.data.service;

import com.ultraviolet.delieve.data.dto.DelieveryRequestDto;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface DeliveryRequestService {

    @POST("/api/request")
    Observable<Response<Void>> postRequest(@Body DelieveryRequestDto dto);


}
