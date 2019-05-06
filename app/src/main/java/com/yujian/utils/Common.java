package com.yujian.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.jess.arms.base.BaseApplication;
import com.yujian.app.BaseApp;
import com.yujian.entity.User;

import timber.log.Timber;

public class Common {
    public interface Callback{
        public void callback();
    }
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

    public static void showMsg(String msg){
        Toast.makeText(getContext() , msg , Toast.LENGTH_SHORT).show();
    }

    public static boolean isPhone(String phone){

        if (TextUtils.isEmpty(phone)){
            return false;
        }else{
            return phone.matches(Constant.Regex.phone);
        }

    }

    public static boolean isValidPw(String pw){
        if (TextUtils.isEmpty(pw)){
            return false;
        }else{
            return pw.matches(Constant.Regex.pw);
        }
    }

//    public static String recourceTo
}
