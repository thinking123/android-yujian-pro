package com.yujian.mvp.model.entity;

import com.yujian.entity.FitnessRoom;
import com.yujian.entity.Friend;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCoachOrUserRelevantBean {
    private List<Friend> coachList;
    private List<FitnessRoom> gymListCollections;
}
