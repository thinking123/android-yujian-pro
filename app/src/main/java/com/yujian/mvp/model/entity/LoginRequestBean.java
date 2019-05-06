package com.yujian.mvp.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestBean {
    String deviceId;
    String openId;
    String passWord;
    String phone;

    public LoginRequestBean(String deviceId,
                            String openId,
                            String passWord,
                            String phone){
        this.deviceId = deviceId;
        this.openId = openId;
        this.passWord = passWord;
        this.phone = phone;
    }
}
