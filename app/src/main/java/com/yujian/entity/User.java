package com.yujian.entity;


import com.yujian.utils.SpUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
   private String state;//(string, optional): 用户状态 0 审核成功 1 审核失败 2 待审核 3 封禁 4 信息填写不完整(没填认证信息) 5 未填写信息 ,
    private String   token ;//(string, optional): token ,
    private String  userType ;//(string, optional): 身份类型 1 健身房 2 教练 3 普通用户

    public static final String STATE = "state";
    public static final String TOKEN = "token";
    public static final String USERTYPE = "userType";
    public void init(){

        state = SpUtils.get(STATE);
        token = SpUtils.get(TOKEN);
        userType = SpUtils.get(USERTYPE);
    }

    public static User user;
    public static User getInstance(){
        if(user == null){
            user = new User();
            user.init();
        }

        return user;
    }
}
