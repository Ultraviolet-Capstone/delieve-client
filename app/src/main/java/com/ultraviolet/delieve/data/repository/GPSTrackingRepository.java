package com.ultraviolet.delieve.data.repository;

import com.ultraviolet.delieve.data.dto.GPSReceiverDto;
import com.ultraviolet.delieve.data.dto.GPSSenderDto;

import retrofit2.Response;
import retrofit2.http.Body;
import rx.Observable;

public interface GPSTrackingRepository {
    Observable<Response<GPSSenderDto>> postGPS( GPSSenderDto dto);
    Observable<Response<GPSReceiverDto>> getGPS(int matchingId);

}
