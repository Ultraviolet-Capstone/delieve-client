package com.ultraviolet.delieve.model;

import com.ultraviolet.delieve.data.dto.DeliveryMatchingDto;

import java.io.Serializable;

public class DeliveryMatching implements Serializable {
    public int requestId;
    public String beginAddress;
    public double beginLatitude;
    public double beginLongitude;
    public String finishAddress;
    public double finishLatitude;
    public double finishLongitude;
    public String stuffName;
    public String stuffSize;
    public double stuffWeight;
    public double distance;
    public String beginTime;
    public int type;
    public String finishTime;
    public String senderSelfiURL;
    public String senderName;
    public String senderPhone;
    public String senderEmail;
    public String senderBirthday;
    public String recieverPhone;

    public DeliveryMatching(DeliveryMatchingDto dto){
        requestId = dto.matchingId;
        beginAddress = dto.beginAddress;
        beginLatitude = dto.beginLatitude;
        beginLongitude = dto.beginLongitude;
        finishAddress = dto.finishAddress;
        finishLatitude = dto.finishLatitude;
        finishLongitude = dto.finishLongitude;
        stuffName = dto.stuffName;
        stuffSize = dto.stuffSize;
        stuffWeight = dto.stuffWeight;
        distance = dto.distance;
        beginTime = dto.beginTime;
        type = dto.type;
        beginTime = dto.beginTime;
        finishTime = dto.finishTime;
        senderSelfiURL = dto.senderSelfiURL;
        senderBirthday = dto.senderBirthday;
        senderEmail = dto.senderEmail;
        senderName = dto.senderName;
        senderPhone = dto.senderPhone;
        recieverPhone = dto.recieverPhone;

    }

}
