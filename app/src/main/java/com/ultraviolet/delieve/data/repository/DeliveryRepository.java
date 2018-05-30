package com.ultraviolet.delieve.data.repository;

import com.ultraviolet.delieve.data.dto.DeliveryMatchingDto;
import com.ultraviolet.delieve.data.dto.DeliveryRequestDto;

import retrofit2.Response;
import retrofit2.http.Query;
import rx.Observable;

public interface DeliveryRepository {

    Observable<Response<Void>> postDeliveryRequest(DeliveryRequestDto dto);

    Observable<Response<DeliveryMatchingDto>> getDeliveryMatching(double lat, double lng , String id);
    Observable<Response<DeliveryMatchingDto>> getDeliveryMatchingForSender( double lat, double lng , String id);

}
