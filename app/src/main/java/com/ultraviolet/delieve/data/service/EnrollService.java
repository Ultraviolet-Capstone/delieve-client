package com.ultraviolet.delieve.data.service;


import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface EnrollService {

    @POST
    Observable<Response<Void>> tmp (@Part MultipartBody.Part selfie, @Part MultipartBody.Part idCard);

}
