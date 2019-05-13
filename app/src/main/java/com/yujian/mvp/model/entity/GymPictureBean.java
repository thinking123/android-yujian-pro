package com.yujian.mvp.model.entity;

import com.yujian.entity.GymPicture;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymPictureBean {
    String pageNum;
    String pages;
    String total;
    List<GymPicture> list;
}
