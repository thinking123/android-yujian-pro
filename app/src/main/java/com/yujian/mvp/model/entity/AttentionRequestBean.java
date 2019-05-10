package com.yujian.mvp.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttentionRequestBean {
    private String collectType;//: 收藏类型 1 健身房 2 教练 3用户 ,
    private String collectUserId ;
    public AttentionRequestBean(String collectType,String collectUserId){
        this.collectType = collectType;
        this.collectUserId = collectUserId;
    }

}
