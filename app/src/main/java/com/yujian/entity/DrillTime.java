package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrillTime implements Serializable {
    private String classTime;//string, optional): 上课时间 ,
    private String coachId;//string, optional): 教练id ,
    private String coachName;//string, optional): 教练名称 ,
    private String collectionNum;//integer, optional): 关注数 ,
    private String curriculumId;//string, optional): 上课类型id ,
    private String curriculumName;//string, optional): 上课类型名称 ,
    private String curriculumTime;//string, optional),
    private String curriculumType;//integer, optional): 上课 方式 1每周 2本周 3 休息 ,
    private String editId;//integer, optional),
    private String endTime;//string, optional): 下课时间 ,
    private String gymAddress;//string, optional): 健身房地址 ,
    private String gymId;//string, optional): 健身房id ,
    private String gymName;//string, optional): 健身房名称 ,
    private String id;//integer, optional): 课程id ,
    private String img;//string, optional): 课程类型图片 ,
    private String isCollection;//string, optional): 是否关注 0否 1是;//注：)教练查看自己的课程 返回 null 建议 '' null 都判断了 ,
    private String startTime;//string, optional): 上课时间 ,
    private String week;//string, optional): 周几 1-7
}
