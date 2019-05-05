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

        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(phone)){
            return false;
        }else{
            return phone.matches(Constant.Regex.phone);
        }

    }
}
