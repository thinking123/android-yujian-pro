package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Personaltainer implements Serializable {
    private String head;//string, optional): 教练头像 ,
    private String id;//string, optional): 教练id ,
    private String label;//string, optional): 标签 ,
    private String name;//string, optional): 教练名称 ,
    private String workingLife;//string, optional): 工作年限
}
