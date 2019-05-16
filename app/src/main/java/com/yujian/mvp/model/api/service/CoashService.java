package com.yujian.mvp.model.api.service;

import com.yujian.entity.BaseResponse;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.mvp.model.entity.FollowUserBean;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CoashService {
    /*
    * /api/coach/AddCoachCredentials
新增/编辑 1 认证证书 2参加赛事 3个人事迹 --编号 003

证书/赛事表/个人事迹 {
id (integer, optional): id 编辑必穿 ,
introduce (string, optional): 介绍 ,
name (string, optional): 名称 ,
times (string, optional): 时间 ,
type (integer, optional): 1 认证证书 2参加赛事 3个人事迹 ,
url (string, optional): 图片 逗号分割
}
    * */
    @POST("/api/coach/AddCoachCredentials")
    Observable<BaseResponse<String>> addCoachCredentials(
            @Body HashMap<String, String> requestBody
    );

    /*
    *
/api/coach/DelCoachCredentials
删除 1 认证证书 2参加赛事 3个人事迹
id (string, optional): id 多个id请用逗号分割
    * */

    @POST("/api/coach/DelCoachCredentials")
    Observable<BaseResponse<String>> delCoachCredentials(
            @Body HashMap<String, String> requestBody
    );


    /*
    * /api/coach/GetMsgByIdToEdit
获取通过id (用于编辑 返回数据) 1 认证证书 2参加赛事 3个人事迹
    * */
    @GET("/api/coach/GetMsgByIdToEdit")
    Observable<BaseResponse<UserProfileMatchCertificatePersonalStory>> getMsgByIdToEdit(
            @Header("longitude") String longitude,
            @Header("latitude") String latitude,
            @Query("id") String id
    );



        /*
        api/coach/GetMsgByType
    获取 1 认证证书 2参加赛事 3个人事迹
         */
    @GET("/api/coach/GetMsgByType")
    Observable<BaseResponse<List<UserProfileMatchCertificatePersonalStory>>> getMsgByType(
            @Header("longitude") String longitude,
            @Header("latitude") String latitude,
            @Query("id") String id,
            @Query("type") String type
    );


    /*
/api/gym/FollowAllList
作用:关注列表
 */
    @GET("/api/gym/FollowAllList")
    Observable<BaseResponse<FollowUserBean>> followAllList(
            @Header("longitude") String longitude,
            @Header("latitude") String latitude,
            @Query("pageNum") String pageNum,
            @Query("type") String type
    );


    /*
/api/gym/GetCoachOrUserRelevant
作用: 教练/用户 详情--与他相关教练/健身房 列表
   */
    @GET("/api/gym/GetCoachOrUserRelevant")
    Observable<BaseResponse<GetCoachOrUserRelevantBean>> getCoachOrUserRelevant(
            @Header("longitude") String longitude,
            @Header("latitude") String latitude,
            @Query("id") String id
    );


    /*
/api/gym/GymdetailsCoach
作用:健身房详情--课程-私教 列表
*/
    @GET("/api/gym/GymdetailsCoach")
    Observable<BaseResponse<List<Personaltainer>>> gymdetailsCoach(
            @Header("longitude") String longitude,
            @Header("latitude") String latitude,
            @Query("id") String id
    );
}
