package com.yujian.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile implements Serializable {
    private String address;//string, optional): 健身房定位地址 （非健身房 返回 null 建议 ‘’null 都做判断 ） ,
    private String area;//string, optional): 健身房面积 （非健身房 返回 null 建议 ‘’null 都做判断 ） ,
    private List<GymCard> cardLists;//Array[健身卡], optional): 健身卡列表（非健身房 数字为空） ,
    private List<UserProfileMatchCertificatePersonalStory> certificateList;//Array[证书/赛事表/个人事迹], optional): 证书 ,
    private String collectionNum;//string, optional): 关注数 ,
    private String fansNum;//string, optional): 粉丝量 ,
    private String head;//string, optional): 头像 （健身房 返回 null 建议 ‘’null 都做判断 ） ,
    private String hoursEnd;//string, optional): 结束营业时间 （非健身房 返回 null 建议 ‘’null 都做判断 ） ,
    private String hoursStart;//string, optional): 开始营业时间 （非健身房 返回 null 建议 ‘’null 都做判断 ） ,
    private String id;//string, optional): 健身房/教练/用户 id ,
    private String introduce;//string, optional): 介绍 ,
    private String isCollect;//string, optional): 是否关注 1 是 0否 ,
    private String labelList;//string, optional): 签约的标签 逗号分割 ,
    private String latitude;//string, optional): 纬度 （非健身房 返回 null 建议 ‘’null 都做判断 ） ,
    private String logo;//string, optional): 健身房/教练/用户背景 ,
    private String longitude;//string, optional): 经度 （非健身房 返回 null 建议 ‘’null 都做判断 ） ,
    private List<UserProfileMatchCertificatePersonalStory> matchList;//Array[证书/赛事表/个人事迹], optional): 赛事 ,
    private String name;//string, optional): 健身房名称/教练名称/用户名称 ,
    private String openTime;//string, optional): 营业时间 （非健身房 返回 null 建议 ‘’null 都做判断 ） ,
    private List<PictureSet> pictureSets;//Array[图片集], optional): 图片集 ,
    private String sex;//string, optional): 性别 （健身房 返回 null 建议 ‘’null 都做判断 ） ,
    private String userRole;//string, optional): 用户角色 1 健身房 2 教练 3 普通用户 ,
    private String visitNum;//string, optional): 访问数

//    public List<UserProfileMatchCertificatePersonalStory> getMatchCertificatePersonalStoryList(String type){
//        if(certificateList != null && certificateList.size() > 0){
//            Observable.from(matchList)
//        }else{
//            return new ArrayList<>();
//        }
//    }
}
