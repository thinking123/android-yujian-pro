package com.yujian.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FitnessRoom {
    private String advertisement;
    private String distance;
    private String gymAddressDetails;
    private String gymIsOpen;
    private String gymName;
    private String id;
    private String isCollect;
    private String isHasCard;
    private String latitude;
    private String longitude;
    private String nowTimeIsOpen;
    private String openTime;

















    //从GET /api/gym/GetCoachOrUserRelevant接口返回的数据
    private String background;// (string, optional): 背景图 ,

    private String  hoursEnd ;//(string, optional): 结束营业时间 ,
    private String hoursStart ;//(string, optional): 开始营业时间 ,

    /*
    * advertisement (string, optional): 广告 ,
distance (string, optional): 距离 ,
gymAddressDetails (string, optional): 健身房地址 ,
gymIsOpen (string, optional): 是否营业 1营业 0暂不营业 ,
gymName (string, optional): 健身房名称 ,
id (integer, optional): id ,
isCollect (string, optional): 是否关注 1 是 0否 ,
isHasCard (string, optional): 是否有健身卡 1 是 0否 ,
latitude (string, optional): 健身房纬度 ,
longitude (string, optional): 健身房经度 ,
nowTimeIsOpen (string, optional): 是否在营业时间内 0否 1是 ,
openTime (string, optional): 营业时间
    * */
}
