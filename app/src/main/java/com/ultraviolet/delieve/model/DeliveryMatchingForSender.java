package com.ultraviolet.delieve.model;

import com.ultraviolet.delieve.data.dto.DeliveryMatchingForDelieverDto;
import com.ultraviolet.delieve.data.dto.DeliveryMatchingForSenderDto;

import java.io.Serializable;

public class DeliveryMatchingForSender implements Serializable {
    public int matchingId;
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

    public DeliveryMatchingForSender(DeliveryMatchingForSenderDto dto){
        matchingId = dto.matchingId;
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
