package com.yujian.mvp.model.entity;

import com.yujian.entity.Friend;
import java.util.List;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
//
@Setter
@Getter
public class FriendBean implements Serializable {
    public List<Friend> list;
    String pageNum;
    String pages;
    String total;
}
