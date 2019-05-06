package com.yujian.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Friend {
    private String addressDetails;// 地址 ,
    private String advertisement;// 广告 ,
    private String content;// 性别|工作年限 ,
    private String distance;// 距离 ,
    private String icon;// icon(逗号分割) ,
    private String id;// id ,
    private String isHasCard;// 是否有健身卡 1 是 0否 ,
    private String label;// 标签(逗号分割) ,
    private String logo;// 头像 ,
    private String name;// 名称 ,
    private String sex;// 性别 1男 2女 ,
    private String userRole;// 用户角色 1 健身房 2 教练 3 普通用户
}
