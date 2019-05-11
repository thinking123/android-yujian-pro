package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymCard implements Serializable {
    private String cardDescribe;//string, optional): 卡片描述/规则 ,
    private String cardName;//string, optional): 卡片名称 ,
    private String cardNum;//string, optional): 卡片次数 ,
    private String cardPrice;//number, optional): 卡片金额 ,
    private String cardState;//integer, optional): 卡片状态 0 正常 1 下架 ,
    private String cardType;//integer, optional): 卡片类型 0次卡 1其他 ,
    private String id;//integer, optional): 卡片id 详情回显
}
