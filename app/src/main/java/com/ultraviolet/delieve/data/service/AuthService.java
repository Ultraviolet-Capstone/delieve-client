package com.ultraviolet.delieve.data.service;


import com.ultraviolet.delieve.data.dto.AuthDto;
import com.ultraviolet.delieve.data.dto.UserDto;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface AuthService {

    @GET("/auth/admin/token")
    Observable<AuthDto> getToken(@Query("userName") String username,
                                 @Query("password") String password,
                                 @Query("grantType") String grantType);

    @GET("/auth/admin/")
    Observable<AuthDto> login(@Query("token") String token);


    @POST("/auth/user/register")
    Observable<String> register(@Body UserDto userDto);

}

