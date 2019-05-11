package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileMatchCertificatePersonalStory implements Serializable {

    private String id;//integer, optional): id 编辑必穿 ,
    private String introduce;//string, optional): 介绍 ,
    private String name;//string, optional): 名称 ,
    private String times;//string, optional): 时间 ,
    private String type;//integer, optional): 1 认证证书 2参加赛事 3个人事迹 ,
    private String url;//string, optional): 图片 逗号分割
}
