package com.ultraviolet.delieve.data.repository;

import com.ultraviolet.delieve.data.dto.DelieverDelivableDto;

import retrofit2.Response;
import rx.Observable;

public interface UserInfoRepository {

    Observable<Response<DelieverDelivableDto>> fetchUserDelivable(int id);

}
