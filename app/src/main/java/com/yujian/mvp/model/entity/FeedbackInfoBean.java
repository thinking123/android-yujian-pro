package com.yujian.mvp.model.entity;

import com.yujian.entity.FeedbackInfo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedbackInfoBean {

    String pageNum;
    String pages;
    String total;

    List<FeedbackInfo> list;
}
