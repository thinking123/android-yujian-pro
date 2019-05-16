package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowUser implements Serializable {
  private String address ;//string, optional): 地址 ,
  private String id ;//integer, optional): id ,
  private String label ;//string, optional): 标签 ,
  private String logo ;//string, optional): 头像 ,
  private String name ;//string, optional): 名称
}
