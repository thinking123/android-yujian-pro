package com.yujian.mvp.model.api.service;

import com.yujian.entity.BaseResponse;
import com.yujian.entity.Topic;
import com.yujian.mvp.model.entity.DynamicTopicBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface DynamicService {

    /*
    * /api/mood/cancelCommentPraise
动态评论取消点赞

    * */
    @GET("/api/mood/cancelCommentPraise")
    Observable<BaseResponse<Topic>> cancelCommentPraise(
            @Query("commentId") String commentId
    );


    /*
/api/mood/getMood
获取动态
*/
    @GET("/api/mood/getMood")
    Observable<BaseResponse<DynamicTopicBean>> getMood(
            @Header("longitude") String longitude,
            @Header("latitude") String latitude,

            @Query("type") String type,/*
类型 0 广场（时间排序） 1 健身房 2 教练 3 普通用户 4 推荐 5 附近（距离排序） 6 关注*/
            @Query("longitude") String longitude1,
            @Query("latitude") String latitude1,
            @Query("id") String id,
            @Query("pageNum") String pageNum

    );


    /*
 * /api/mood/addScan
动态增加一次浏览次数

 * */
    @GET("/api/mood/addScan")
    Observable<BaseResponse<Topic>> addScan(
            @Header("longitude") String longitude,
            @Header("latitude") String latitude,
            @Query("moodId") String moodId
    );


    /*
 * /api/mood/toCommentPraise
动态评论点赞

 * */
    @GET("/api/mood/toCommentPraise")
    Observable<BaseResponse<String>> toCommentPraise(
            @Header("longitude") String longitude,
            @Header("latitude") String latitude,
            @Query("id") String id,
            @Query("commentId") String commentId
    );
}
