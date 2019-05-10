package com.yujian.mvp.model.api.service;

import com.yujian.entity.BaseResponse;
import com.yujian.mvp.model.entity.FitnessRoomBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FitnessRoomService {
    @GET("/api/gym/GymAllList")
    Observable<BaseResponse<FitnessRoomBean>> getNearbyFitnessRoom(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("Kilometres") String Kilometres
            );
}
