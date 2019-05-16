package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachRelevant implements Serializable {
    private String addressDetails;//string, optional): 地址 ,
    private String advertisement;//string, optional): 广告 ,
    private String content;//string, optional): 性别|工作年限 ,
    private String distance;//string, optional): 距离 ,
    private String icon;//string, optional): icon;//逗号分割) ,
    private String id;//integer, optional): id ,
    private String isHasCard;//string, optional): 是否有健身卡 1 是 0否 ,
    private String label;//string, optional): 标签;//逗号分割) ,
    private String logo;//string, optional): 头像 ,
    private String name;//string, optional): 名称 ,
    private String sex;//string, optional): 性别 1男 2女 ,
    private String userRole;//integer, optional): 用户角色 1 健身房 2 教练 3 普通用户
}
