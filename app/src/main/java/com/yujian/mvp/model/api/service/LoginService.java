package com.yujian.mvp.model.api.service;

import com.yujian.entity.BaseResponse;
import com.yujian.mvp.model.entity.LoginBean;
import com.yujian.mvp.model.entity.LoginRequestBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {
    @GET("/vcode/get")
    Observable<BaseResponse<String>> getPhoneCode(
            @Query("phone") String phone
    );

    @GET("/api/login/checkPhone")
    Observable<BaseResponse<String>> checkPhone(
            @Query("phone") String phone
    );

    @FormUrlEncoded
    @POST("/api/login/loginByPhone")
    Observable<BaseResponse<LoginBean>> loginByPhone(
            @Field("deviceId") String deviceId ,
            @Field("openId") String openId,
            @Field("passWord") String passWord,
            @Field("phone") String phone
    );

    @POST("/api/login/loginByPhone")
    Observable<BaseResponse<LoginBean>> loginByPhone(
            @Body LoginRequestBean requestBody
    );


}
