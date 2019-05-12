package com.yujian.utils;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.jess.arms.base.BaseApplication;
import com.yujian.app.BaseApp;
import com.yujian.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import timber.log.Timber;

public class Common {
    public interface Callback {
        public void callback();
    }

    public static Context getContext() {
        return BaseApp.getContext();
    }

    public static boolean isLogin() {
        User user = User.getInstance();

        return !user.getToken().isEmpty();
    }

    public static String formatDistanceToKm(String d) {
        final String f = "%sKM";
        if (TextUtils.isEmpty(d)) {
            return String.format(f, 0);
        } else {
            float fd = Float.parseFloat(d);
            return String.format("$.2fKM", fd);
        }
    }

    public static String formatAddress(String d) {
        final String f = "地址：%s";
        if (TextUtils.isEmpty(d)) {
            return String.format(f, "-");
        } else {
            return String.format(f, d);
        }
    }

    public static String formatTimeRange(String d) {
        final String f = "营业时间：%s";
        if (TextUtils.isEmpty(d)) {
            return String.format(f, "-");
        } else {
            return String.format(f, d);
        }
    }

    public static int dpToPx(float dp) {
        Context context = getContext();
        final float scale = context.getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale + 0.5f);

        Timber.i("convert dp : %s to px : %s ", dp, px);

        return px;
    }

    public static void showMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public interface IPredicate<T> {
        boolean apply(T type);
    }

    public static <T> Collection<T> filter(Collection<T> target, IPredicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element : target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

    public static List<String> splitStringToList(String str, String split) {
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        if (TextUtils.isEmpty(split)) {
            split = ",";
        }

        return Arrays.asList(str.split(split));
    }

    public static boolean isPhone(String phone) {

        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return phone.matches(Constant.Regex.phone);
        }

    }

    public static boolean isValidPw(String pw) {
        if (TextUtils.isEmpty(pw)) {
            return false;
        } else {
            return pw.matches(Constant.Regex.pw);
        }
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
//    public static String recourceTo
}
