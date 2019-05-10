package com.yujian.mvp.model.entity;

import com.yujian.entity.FitnessRoom;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FitnessRoomBean {
    private List<FitnessRoom> list;
    String pageNum;
    String pages;
    String total;
}
