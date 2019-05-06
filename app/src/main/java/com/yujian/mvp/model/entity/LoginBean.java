package com.yujian.mvp.model.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginBean implements Serializable {
    private String id;
    private String msg;
    private String state;
    private String token;
    private String userType;
}
