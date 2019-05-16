package com.yujian.mvp.model.api.service;

import com.yujian.entity.AttationCurriculum;
import com.yujian.entity.BaseResponse;
import com.yujian.entity.DrillTime;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/*
* 操课 service
* */
public interface DrillTimeService {
    ///api/curriculum/GetCurriculum
    //获取 操课 根据日期

    @GET("/api/curriculum/GetCurriculum")
    Observable<BaseResponse<List<DrillTime>>> getCurriculumByTime(
            @Query("id") String id,
            @Query("week") String week,//周几 1-7
            @Query("time") String time  //时间-日期 格式：2019-05-02
    );


    /*
    * /api/curriculum/AddCollectCurriculum
关注操课
操课关注 {
coachId (integer, optional): 教练id ,
curriculumId (integer, optional): 操课id
}
    * */
    @POST("/api/curriculum/AddCollectCurriculum")
    Observable<BaseResponse<AttationCurriculum>> addCollectCurriculum(
            @Body HashMap<String, String> requestBody,
            @Header("longitude") String longitude,
            @Header("latitude") String latitude
    );



    /*
    * /api/curriculum/DelCollectCurriculum
操课 取消关注 传入操课id
操课关注 {
coachId (integer, optional): 教练id ,
curriculumId (integer, optional): 操课id
}
    * */
    @POST("/api/curriculum/DelCollectCurriculum")
    Observable<BaseResponse<String>> delCollectCurriculum(
            @Body HashMap<String, String> requestBody,
            @Header("longitude") String longitude,
            @Header("latitude") String latitude
    );

}
