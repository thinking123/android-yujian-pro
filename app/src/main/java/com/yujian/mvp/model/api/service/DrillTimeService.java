package com.yujian.mvp.model.api.service;

import com.yujian.entity.BaseResponse;
import com.yujian.entity.DrillTime;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DrillTimeService {
    ///api/curriculum/GetCurriculum
    //获取 操课 根据日期
    @GET("/api/curriculum/GetCurriculum")
    Observable<BaseResponse<List<DrillTime>>> getGymdetailsCoach(
            @Query("id") String id,
            @Query("week") String week,
            @Query("time") String time
    );
}
