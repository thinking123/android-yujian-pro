package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackInfo implements Serializable {
    private String creatTime;//string, optional): 列表返回 反馈时间 ,
    private String gymId;//integer, optional): 新增（必填） 被反馈的健身房id ,
    private String id;//integer, optional): id 新增/列表 返回 ,
    private String msg;//string, optional): 新增（必填） 反馈信息
}
