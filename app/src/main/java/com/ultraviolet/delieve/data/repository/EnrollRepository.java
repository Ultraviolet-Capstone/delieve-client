package com.ultraviolet.delieve.data.repository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import rx.Observable;

public interface EnrollRepository {
    Observable<Response<Void>> requestEnroll (MultipartBody.Part selfie, MultipartBody.Part idCard, MultipartBody.Part id);

}
