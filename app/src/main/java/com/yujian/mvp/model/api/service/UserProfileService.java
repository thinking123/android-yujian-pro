package com.yujian.mvp.model.api.service;

import com.yujian.entity.BaseResponse;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.UserProfile;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;
import com.yujian.mvp.model.entity.GymPictureBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
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




    @GET("/api/gym/GymdetailsCoach")
    Observable<BaseResponse<List<Personaltainer>>> getGymdetailsCoach(
            @Query("id") String id
    );


    ///api/gym/GetSetPictureById
    //作用:图片集图片 --编号 011
    @GET("/api/gym/GetSetPictureById")
    Observable<BaseResponse<GymPictureBean>> getSetPictureById(
            @Header("longitude") String longitude,
            @Header("latitude") String latitude,
            @Query("pageNum") String pageNum,
            @Query("setId") String setId
            );
}
