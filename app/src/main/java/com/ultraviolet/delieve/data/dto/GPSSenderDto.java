package com.ultraviolet.delieve.data.dto;

public class GPSSenderDto {
    double latitude;
    double longitude;
    String sensedTime;
    int matchingId;

    public GPSSenderDto(double latitude, double longitude, String sensedTime, int matchingId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.sensedTime = sensedTime;
        this.matchingId = matchingId;
    }
}
