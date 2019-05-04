package com.yujian.utils;

import android.content.Context;

import com.jess.arms.base.BaseApplication;
import com.yujian.app.BaseApp;
import com.yujian.entity.User;

import timber.log.Timber;

public class Common {
    public static Context getContext(){
        return BaseApp.getContext();
    }
    public static boolean isLogin(){
        User user = User.getInstance();

        return !user.getToken().isEmpty();
    }

    public static int dpToPx(float dp){
        Context context = getContext();
        final float scale = context.getResources().getDisplayMetrics().density;
        int px = (int)(dp * scale + 0.5f);

        Timber.i("convert dp : %s to px : %s " , dp , px);

        return px;
    }

}
