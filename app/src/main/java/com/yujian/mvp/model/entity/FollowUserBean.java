package com.yujian.mvp.model.entity;

import com.yujian.entity.FollowUser;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FollowUserBean {
    private List<FollowUser> list;
    String pageNum;
    String pages;
    String total;
}
