package com.yujian.utils;

import android.content.Context;

import com.jess.arms.base.BaseApplication;
import com.yujian.app.BaseApp;
import com.yujian.entity.User;

public class Common {
    public static Context getContext(){
        return BaseApp.getContext();
    }
    public static boolean isLogin(){
        User user = User.getInstance();

        return !user.getToken().isEmpty();
    }


}
