package com.yujian.mvp.model.api.service;

import com.yujian.entity.BaseResponse;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.UserProfile;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserProfileService {
    @GET("/api/gym/Gymdetails")
    Observable<BaseResponse<UserProfile>> getUserProfile(
            @Query("id") String id
    );

    @GET("/api/gym/GetCoachOrUserRelevant")
    Observable<BaseResponse<GetCoachOrUserRelevantBean>> getCoachOrUserRelevant(
            @Query("id") String id
    );

    //GET /api/gym/GymdetailsCoach
    //作用:健身房详情--课程-私教 列表 --编号
    @GET("/api/gym/GymdetailsCoach")
    Observable<BaseResponse<List<Personaltainer>>> getGymdetailsCoach(
            @Query("id") String id
    );


}
