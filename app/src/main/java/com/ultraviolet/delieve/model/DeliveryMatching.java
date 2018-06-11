package com.ultraviolet.delieve.model;

import com.ultraviolet.delieve.data.dto.DeliveryMatchingDto;
import com.ultraviolet.delieve.data.dto.DeliveryMatchingForDelieverDto;

import java.io.Serializable;

public class DeliveryMatching implements Serializable {
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
    public String delieverSelfiURL;
    public String delieverName;
    public String delieverPhone;
    public String delieverEmail;
    public String delieverBirthday;
    public String recieverPhone;
    public String matchingStatus;

    public String senderSelfiURL;
    public String senderName;
    public String senderPhone;
    public String senderEmail;
    public String senderBirthday;

    public int price;



    public DeliveryMatching(DeliveryMatchingDto dto) {
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
        delieverSelfiURL = dto.delivererSelfiURL;
        delieverBirthday = dto.delivererBirthday;
        delieverEmail = dto.delivererEmail;
        delieverName = dto.delivererName;
        delieverPhone = dto.delivererPhone;
        recieverPhone = dto.recieverPhone;
        matchingStatus = dto.matchingStatus;

        senderSelfiURL = dto.senderSelfiURL;
        senderBirthday = dto.senderBirthday;
        senderEmail = dto.senderEmail;
        senderName = dto.senderName;
        senderPhone = dto.senderPhone;

        price = dto.price;
    }

    public DeliveryMatching(DeliveryMatchingForDeliever data){
        beginAddress = data.beginAddress;
        beginLatitude = data.beginLatitude;
        beginLongitude = data.beginLongitude;
        finishAddress = data.finishAddress;
        finishLatitude = data.finishLatitude;
        finishLongitude = data.finishLongitude;
        stuffName = data.stuffName;
        stuffSize = data.stuffSize;
        stuffWeight = data.stuffWeight;
        distance = data.distance;
        beginTime = data.beginTime;
        type = data.type;
        beginTime = data.beginTime;
        finishTime = data.finishTime;
        recieverPhone = data.recieverPhone;
        senderSelfiURL = data.senderSelfiURL;
        senderBirthday = data.senderBirthday;
        senderEmail = data.senderEmail;
        senderName = data.senderName;
        senderPhone = data.senderPhone;

        price = data.price;
    }

}
