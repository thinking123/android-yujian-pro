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

            gpsLocation.setLongitude(117.4658203125);
            gpsLocation.setLatitude(28.6809497286);
        }

        return gpsLocation;
    }
}
