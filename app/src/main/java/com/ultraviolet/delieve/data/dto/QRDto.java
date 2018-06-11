package com.ultraviolet.delieve.data.dto;

public class QRDto {
    public int id;
    public String hashValue;
    public String status;

    public QRDto(int id, String hashValue, String status) {
        this.id = id;
        this.hashValue = hashValue;
        this.status = status;
    }
}
