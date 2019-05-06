package com.yujian.mvp.model.api.service;

import com.yujian.entity.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface LoginService {
    @GET("/vcode/get")
    Observable<BaseResponse<String>> getPhoneCode(
            @Query("phone") String phone
    );

    @GET("/login/checkPhone")
    Observable<BaseResponse<String>> checkPhone(
            @Query("phone") String phone
    );

}
