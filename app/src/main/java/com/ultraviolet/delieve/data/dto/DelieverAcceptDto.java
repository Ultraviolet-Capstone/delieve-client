package com.ultraviolet.delieve.data.dto;

public class DelieverAcceptDto {
    int delivererId;
    int requestId;
    String time;

    public DelieverAcceptDto(int delievererId, int requestId, String time) {
        this.delivererId = delievererId;
        this.requestId = requestId;
        this.time = time;
    }
}
