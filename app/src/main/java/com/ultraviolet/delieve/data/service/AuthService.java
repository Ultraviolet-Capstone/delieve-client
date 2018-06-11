package com.ultraviolet.delieve.data.service;


import com.ultraviolet.delieve.data.dto.LoginDto;
import com.ultraviolet.delieve.data.dto.TokenDto;
import com.ultraviolet.delieve.data.dto.UserDto;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface AuthService {


    @GET("/auth/admin/token")
    Observable<TokenDto> getToken(@Query("userName") String username,
                                  @Query("password") String password,
                                  @Query("grantType") String grantType);


    @GET("/auth/user/login")
    Observable<Response<LoginDto>> login(@Query("token") String kakaoToken,
                                         @Query("pushToken") String pushToken);

    @POST("/auth/user/register")
    Observable<Response<Void>> register(@Body UserDto userDto);

}
