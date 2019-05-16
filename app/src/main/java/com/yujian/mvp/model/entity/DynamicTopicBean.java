package com.yujian.mvp.model.entity;

import com.yujian.entity.Dynamic;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DynamicTopicBean {
    List<Dynamic> list;
    String pageNum;
    String pages;
    String total;
}
