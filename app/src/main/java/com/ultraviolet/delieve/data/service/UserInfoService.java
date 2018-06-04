package com.ultraviolet.delieve.data.service;

import com.ultraviolet.delieve.data.dto.DelieverDelivableDto;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface UserInfoService {
    @GET("/api/user/{userId}/delivable")
    Observable<Response<DelieverDelivableDto>> fetchUserDelivable(@Path("userId")int id);

}
