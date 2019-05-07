package com.yujian.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GPSLocation {
    //纬度
    private Double latitude;
    private Double longitude;


    private static GPSLocation gpsLocation;
    public static GPSLocation getInstance(){
        if(gpsLocation == null){
            gpsLocation = new GPSLocation();
        }

        return gpsLocation;
    }
}
