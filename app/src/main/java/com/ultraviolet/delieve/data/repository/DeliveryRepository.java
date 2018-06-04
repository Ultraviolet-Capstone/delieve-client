package com.ultraviolet.delieve.data.repository;

import com.ultraviolet.delieve.data.dto.DelieverAcceptDto;
import com.ultraviolet.delieve.data.dto.DelieverAcceptResDto;
import com.ultraviolet.delieve.data.dto.DeliveryMatchingForDelieverDto;
import com.ultraviolet.delieve.data.dto.DeliveryMatchingForSenderDto;
import com.ultraviolet.delieve.data.dto.DeliveryRequestDto;
import com.ultraviolet.delieve.data.dto.DeliveryRequestResDto;

import retrofit2.Response;
import rx.Observable;

public interface DeliveryRepository {

    Observable<Response<DeliveryRequestResDto>> postDeliveryRequest(DeliveryRequestDto dto);

    Observable<Response<DeliveryMatchingForDelieverDto>> getDeliveryMatchingForDeliever(double lat, double lng , String id);
    Observable<Response<DeliveryMatchingForSenderDto>> getDeliveryMatchingForSender(String id);


    Observable<Response<DelieverAcceptResDto>> delieverAccept(DelieverAcceptDto dto);
    Observable<Response<Void>> delieverFlush( int delieverId);

}
