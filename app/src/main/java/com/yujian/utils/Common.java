package com.yujian.utils;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.jess.arms.base.BaseApplication;
import com.yujian.app.BaseApp;
import com.yujian.entity.User;
import com.yujian.mvp.ui.EventBus.EventBusTags;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import timber.log.Timber;

public class Common {

    public static class Filter{
        public static String userProfileMatchCertificatePersonalStoryTypeFilter(String eventBusType){
            String type = "0";
            switch (eventBusType){
                case EventBusTags.UserProfile.CERTIFICATE:
                case EventBusTags.UserProfile.ADDCERTIFICATE:
                    type = "1";
                    break;
                case EventBusTags.UserProfile.MATCH:
                case EventBusTags.UserProfile.ADDMATCH:
                    type = "2";
                    break;
                case EventBusTags.UserProfile.PERSONALSTORY:
                case EventBusTags.UserProfile.ADDPERSONALSTORY:
                    type = "3";
                    break;
            }

            return type;
        }
    }
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

    public static Calendar strToCalendar(String str){
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constant.Common.DAYDATEPATTERN);
            cal.setTime(sdf.parse(str));// all done
        }catch (ParseException e){
            Timber.e(e.getMessage());
        }

        return  cal;

    }

    public static boolean isUIEmpty(CharSequence input){
        return input == null || TextUtils.isEmpty(input.toString().trim());
    }

    public static String joinList(List<String> list , String join){
        if(list == null){
            return "";
        }

        if(TextUtils.isEmpty(join)){
            join = ",";
        }
//        String res = "";
//        for(String l : list){
//            res =
//        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {

            sb.append(list.get(i));

            // if not the last item
            if (i != list.size() - 1) {
                sb.append(join);
            }

        }

        Timber.i("joined string : %s" , sb.toString());
        return sb.toString();
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
