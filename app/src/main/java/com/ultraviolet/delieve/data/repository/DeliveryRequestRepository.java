package com.ultraviolet.delieve.data.repository;

import com.ultraviolet.delieve.data.dto.DelieveryRequestDto;

import retrofit2.Response;
import retrofit2.http.Body;
import rx.Observable;

public interface DeliveryRequestRepository {

    Observable<Response<Void>> postRequest(DelieveryRequestDto dto);

}
