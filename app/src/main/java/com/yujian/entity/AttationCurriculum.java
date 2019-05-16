package com.yujian.entity;

import java.io.Serializable;

public class AttationCurriculum implements Serializable {
    private String cardId;//integer, optional): 绑定的健身房 ,
    private String curriculumTime;//string, optional): 本周课程 添加 课程日期 格式 2019-05-02 ,
    private String curriculumType;//integer, optional): 上课 方式 1每周 2本周 3 休息 ,
    private String curriculumTypeId;//integer, optional): 上课类型id ,
    private String endTime;//string, optional): 下课时间 格式 13:00 前端请判断 下课时间必须大于上课时间 ,
    private String id;//integer, optional): 新增返回 编辑必传 ,
    private String startTime;//string, optional): 上课时间 格式 12:00 ,
    private String week;//integer, optional): 星期几 1-7
}
