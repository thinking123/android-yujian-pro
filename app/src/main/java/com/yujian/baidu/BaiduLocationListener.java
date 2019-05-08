package com.yujian.baidu;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MyLocationData;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class BaiduLocationListener extends BDAbstractLocationListener {

    public static BDLocation bdLocation;
    private final PublishSubject<BDLocation> onBDLocationSubject = PublishSubject.create();

    @Override
    public void onReceiveLocation(BDLocation location) {
        //mapView 销毁后不在处理新接收的位置
//        if (location == null || mMapView == null){
//            return;
//        }
//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(location.getRadius())
//                // 此处设置开发者获取到的方向信息，顺时针0-360
//                .direction(location.getDirection()).latitude(location.getLatitude())
//                .longitude(location.getLongitude()).build();
//        mBaiduMap.setMyLocationData(locData);


        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取经纬度相关（常用）的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

        if(location == null){
            return;
        }

        bdLocation = location;
        //获取纬度信息
        double latitude = location.getLatitude();
        //获取经度信息
        double longitude = location.getLongitude();
        //获取定位精度，默认值为0.0f
        float radius = location.getRadius();
        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
        String coorType = location.getCoorType();
        //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        int errorCode = location.getLocType();


        String addr = location.getAddrStr();    //获取详细地址信息
        String country = location.getCountry();    //获取国家
        String province = location.getProvince();    //获取省份
        String city = location.getCity();    //获取城市
        String district = location.getDistrict();    //获取区县
        String street = location.getStreet();    //获取街道信息

        onBDLocationSubject.onNext(location);
    }

    public Observable<BDLocation> getBDLocation() {
        return onBDLocationSubject.hide();
    }
}