package com.yujian.mvp.model.api.service;

import com.yujian.entity.BaseResponse;
import com.yujian.mvp.model.entity.AttentionRequestBean;
import com.yujian.mvp.model.entity.FitnessRoomBean;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FitnessRoomService {
    @GET("/api/gym/GymAllList")
    Observable<BaseResponse<FitnessRoomBean>> getNearbyFitnessRoom(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("Kilometres") String Kilometres
            );

    @POST("/api/gym/AddCollect")
    Observable<BaseResponse<String>> attention(
            @Body AttentionRequestBean requestBody
    );

    @POST("/api/gym/DelCollect")
    Observable<BaseResponse<String>> unfollow(
            @Body HashMap<String, String> requestBody
    );
}
