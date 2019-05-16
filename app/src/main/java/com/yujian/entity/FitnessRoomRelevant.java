package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FitnessRoomRelevant implements Serializable {
    private String background;//string, optional): 背景图 ,
    private String gymAddressDetails;//string, optional): 健身房地址 ,
    private String gymName;//string, optional): 健身房名称 ,
    private String hoursEnd;//string, optional): 结束营业时间 ,
    private String hoursStart;//string, optional): 开始营业时间 ,
    private String id;//integer, optional): id ,
    private String isHasCard;//string, optional): 是否有健身卡 1 是 0否 ,
    private String openTime;//string, optional): 营业时间
}
