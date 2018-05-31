package com.ultraviolet.delieve.data.service;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

public interface EnrollService {

    /*
    @Multipart
    @POST("http://192.168.0.21:3000/api/evaluate/deliver")
    Observable<Response<Void>> requestEnroll (@PartMap Map<String, RequestBody> params);
    */
    @Multipart
    @POST("/api/evaluate/deliver")
    Observable<Response<Void>> requestEnroll(@Part MultipartBody.Part selfi,
                                             @Part MultipartBody.Part idcard,
                                             @Part MultipartBody.Part userId);



}
