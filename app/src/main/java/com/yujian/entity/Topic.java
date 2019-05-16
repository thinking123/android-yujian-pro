package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Topic implements Serializable {
    private String collectNum;//integer, optional): 关注量 ,
    private String content;//string, optional): 内容 ,
    private String createBy;//string, optional),
    private String createTime;//string, optional): 发起时间 ,
    private String id;//integer, optional): id ,
    private String isSystem;//integer, optional): 是否是系统发起 1 是 0 不是 ,
    private String joinNum;//integer, optional): 参与数 ,
    private String moodNum;//integer, optional): 动态量 ,
    private Object params;//object, optional),
    private String remark;//string, optional),
    private String scanNum;//integer, optional): 浏览量 ,
    private String searchValue;//string, optional),
    private String title;//string, optional): 标题 ,
    private String topicImg;//string, optional): logo ,
    private String topicLabel;//integer, optional): 标签 1 热门 2 最新 3 其他 ,
    private String updateBy;//string, optional),
    private String updateTime;//string, optional),
    private String userId;//integer, optional)
}
// {
//         D/-M-ArmsHttpLog-Response: │             "searchValue": null,
//         D/-S-ArmsHttpLog-Response: │             "createBy": null,
//         D/-A-ArmsHttpLog-Response: │             "createTime": null,
//         D/-R-ArmsHttpLog-Response: │             "updateBy": null,
//         D/-M-ArmsHttpLog-Response: │             "updateTime": null,
//         D/-S-ArmsHttpLog-Response: │             "remark": null,
//         D/-A-ArmsHttpLog-Response: │             "params": {},
//         D/-R-ArmsHttpLog-Response: │             "id": 1,
//         D/-M-ArmsHttpLog-Response: │             "title": "#12312#",
//         D/-S-ArmsHttpLog-Response: │             "content": null,
//         D/-A-ArmsHttpLog-Response: │             "topicImg": null,
//         D/-R-ArmsHttpLog-Response: │             "scanNum": 21,
//         D/-M-ArmsHttpLog-Response: │             "userId": 3,
//         D/-S-ArmsHttpLog-Response: │             "isSystem": 1,
//         D/-A-ArmsHttpLog-Response: │             "topicLabel": 1,
//         D/-R-ArmsHttpLog-Response: │             "collectNum": 2,
//         D/-M-ArmsHttpLog-Response: │             "moodNum": 2,
//         D/-S-ArmsHttpLog-Response: │             "joinNum": 25
//         D/-A-ArmsHttpLog-Response: │         }
