package com.ultraviolet.delieve.data.dto;


public class DeliveryRequestDto {
    BeginLocation beginLocation;
    FinishLocation finishLocation;
    String beginTime;
    String finishTime;
    int senderId;
    String recieverPhoneNumber;
    Stuff stuff;
    int price;

    public DeliveryRequestDto(String beginAddress,
                              double bax,
                              double bay,
                              String finishAddress,
                              double fax,
                              double fay,
                              String beginTime,
                              String finishTime,
                              int senderId,
                              String receiverPhoneNumber,
                              String name,
                              String size,
                              double weight,
                              int code,
                              int price){
        this.beginLocation = new BeginLocation(beginAddress,
                new GPS(bax, bay));
        this.finishLocation = new FinishLocation(finishAddress,
                new GPS(fax, fay));
        this.beginTime = beginTime;
        this.finishTime = finishTime;
        this.senderId = senderId;
        this.recieverPhoneNumber = receiverPhoneNumber;
        this.stuff = new Stuff(name, size, weight, code);
        this.price = price;
    }

    public BeginLocation getBeginLocation() {
        return beginLocation;
    }

    public void setBeginLocation(BeginLocation beginLocation) {
        this.beginLocation = beginLocation;
    }

    public FinishLocation getFinishLocation() {
        return finishLocation;
    }

    public void setFinishLocation(FinishLocation finishLocation) {
        this.finishLocation = finishLocation;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Stuff getStuff() {
        return stuff;
    }

    public void setStuff(Stuff stuff) {
        this.stuff = stuff;
    }

    class GPS {
        double latitude;
        double longitude;

        public GPS(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    class BeginLocation{

        String address;
        GPS gps;

        public BeginLocation(String address, GPS gps) {
            this.address = address;
            this.gps = gps;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public GPS getGps() {
            return gps;
        }

        public void setGps(GPS gps) {
            this.gps = gps;
        }
    }

    class FinishLocation{
        String address;
        GPS gps;

        public FinishLocation(String address, GPS gps) {
            this.address = address;
            this.gps = gps;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public GPS getGps() {
            return gps;
        }

        public void setGps(GPS gps) {
            this.gps = gps;
        }
    }

    class Stuff {
        String name;
        String size;
        double weight;
        int stuffCode;

        public Stuff(String name, String size, double weight, int stuffCode) {
            this.name = name;
            this.size = size;
            this.weight = weight;
            this.stuffCode = stuffCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public int getStuffCode() {
            return stuffCode;
        }

        public void setStuffCode(int stuffCode) {
            this.stuffCode = stuffCode;
        }
    }

}
