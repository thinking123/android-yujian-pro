package com.yujian.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dynamic implements Serializable {
    private String commentCount;//integer, optional): 评论数 ,
    private String consumerMoodId;//integer, optional): 发布者id ,
    private String createTime;//string, optional): 发布时间 ,
    private String distance;//number, optional): 距离 ,
    private String head;//string, optional): 头像 ,
    private String id;//integer, optional): 主键id ,
    private String isCollect;//string, optional): 是否关注 1 已关注 0 未关注 ,
    private String isPraise;//string, optional): 是否点赞 1 已点 0 未点 ,
    private String latitude;//string, optional): 纬度 ,
    private String longitude;//string, optional): 经度 ,
    private String moodContent;//string, optional): 内容 ,
    private String moodImg;//string, optional): 动态图片 ,
    private String moodLocation;//string, optional): 地点 ,
    private String name;//string, optional): 名称 ,
    private String praiseCount;//integer, optional): 点赞数 ,
    private String shareNum;//integer, optional): 分享次数 ,
    private String userRole;//string, optional): 用户角色 ,
    private String viewCount;//integer, optional): 浏览次数 ,
    private String visibleComment;//integer, optional): 谁可以评论 1 所有人 2 关注的人和粉丝 3 粉丝 4 仅自己可以 ,
    private String visibleRange;//integer, optional): 可见范围 1所有人 2 粉丝 3 自己
    private List<Topic> topiclist;//Array[话题实体类], optional): 包含的动态列表 ,
}
