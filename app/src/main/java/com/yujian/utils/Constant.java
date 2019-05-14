package com.yujian.utils;

public class Constant {
    public class Login{
        public static final String TOEKN = "token";
    }

    public class Regex{
        public static final String phone ="^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";

        public static final String pw = "^\\w{8,20}$";
    }

    public class Api {
        public static final   String APP_DOMAIN = "http://117.50.69.235/api/";
        public static final  int RequestSuccess = 0;
        public static final String ResponseSuccess = "200";
    }


    public interface ErrorMsg{
        public static final   String phonePw = "手机或密码不正确";
    }


    public interface Common{
        public static String PRE_HEADER_TOKEN = "Bearer ";
        public static String DAYDATEPATTERN = "yyyy-MM-dd";
    }

}
