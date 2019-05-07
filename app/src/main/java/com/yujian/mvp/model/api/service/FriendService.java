package com.yujian.mvp.model.api.service;

import com.yujian.entity.BaseResponse;
import com.yujian.entity.Friend;
import com.yujian.mvp.model.entity.FriendBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FriendService {
    @GET("/api/gym/GoodFriendAllListHot")
    Observable<BaseResponse<FriendBean>> goodFriendAllListHot();

    @GET("/api/gym/GoodFriendAllList")
    Observable<BaseResponse<FriendBean>> goodFriendAllList(
            @Query("pageNum") String pageNum,
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("memberId") String memberId,
            @Query("name") String name,
            @Query("role") String role,
            @Query("id") String id
    );

}
