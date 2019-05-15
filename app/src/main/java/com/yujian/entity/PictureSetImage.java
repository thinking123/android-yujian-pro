package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureSetImage implements Serializable {
  private String gymPictureSetId ;//integer, optional): 健身房图片集id ,
  private String id ;//integer, optional): 主键 ,
  private String sort ;//integer, optional): 排序 ,
  private String url ;//string, optional): 图片路径
}
