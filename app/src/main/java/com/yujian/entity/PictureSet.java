package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureSet implements Serializable {
    private String background;//string, optional): 背景图 ,
    private String createTime;//string, optional): 创建时间 ,
    private String gymId;//integer, optional): 健身房id ,
    private String gymPictureSetName;//string, optional): 健身房图片集名称 ,
    private String gymPictureSetSize;//integer, optional): 健身房图片数量 0 代表没有图片 ,
    private String id;//integer, optional): id 编辑必传
}
