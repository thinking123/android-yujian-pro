package com.yujian.mvp.model.api.service;

import com.yujian.entity.FeedbackInfo;
import com.yujian.entity.GymPicture;
import com.yujian.entity.BaseResponse;
import com.yujian.entity.PictureSet;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.mvp.model.entity.AttentionRequestBean;
import com.yujian.mvp.model.entity.FeedbackInfoBean;
import com.yujian.mvp.model.entity.FitnessRoomBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    /*
    * /api/gym/AddSet
新增/编辑 图片集

图片集 {
background (string, optional): 背景图 ,
createTime (string, optional): 创建时间 ,
gymId (integer, optional): 健身房id ,
gymPictureSetName (string, optional): 健身房图片集名称 ,
gymPictureSetSize (integer, optional): 健身房图片数量 0 代表没有图片 ,
id (integer, optional): id 编辑必传
}
    * */
    @POST("/api/gym/AddSet")
    Observable<BaseResponse<PictureSet>> addSet(
            @Body PictureSet requestBody
    );


    /*/api/gym/AddSetPicture
新增/ 图片集 图片

图片集 图片 {
gymPictureSetId (integer, optional): 图片集id ,
url (string, optional): url 逗号分割
}
    *
    * */

    @POST("/api/gym/AddSetPicture")
    Observable<BaseResponse<List<GymPicture>>> addSetPicture(
            @Body HashMap<String, String> requestBody,
            @Header("longitude") String longitude,
            @Header("latitude") String latitude
    );

    /*/api/gym/DelSetPicture
删除/ 图片集 图片


id (string, optional): id 多个id请用逗号分割
    *
    * */
    @POST("/api/gym/DelSetPicture")
    Observable<BaseResponse<String>> delSetPicture(
            @Body HashMap<String, String> requestBody
    );


    /*/api/gym/EditBackGround
设置/ 图片集 背景
    *设置图片集背景 {
id (string, optional): 图片集id ,
url (string, optional): 背景图url
}
    * */
    @POST("/api/gym/EditBackGround")
    Observable<BaseResponse<String>> editBackGround(
            @Body HashMap<String, String> requestBody
    );


    /*/api/gym/SetAll
获取 所有的图片集
    *
    * */
    @GET("/api/gym/SetAll")
    Observable<BaseResponse<List<PictureSet>>> setAll(
            @Header("longitude") String longitude,
            @Header("latitude") String latitude,
            @Query("id") String id
    );
    /*/api/gym/SortSetPicture
    *排序图片 {
gymPictureSetId (string, optional): 图片集id ,
id (string, optional): 图片集 图片id ,
newSrot (string, optional): 新的排序位置
}
    * */
    @POST("/api/gym/SortSetPicture")
    Observable<BaseResponse<String>> sortSetPicture(
            @Body HashMap<String, String> requestBody
    );

    /*/api/gym/AddFeedback
新增/反馈信息
creatTime (string, optional): 列表返回 反馈时间 ,
gymId (integer, optional): 新增（必填） 被反馈的健身房id ,
id (integer, optional): id 新增/列表 返回 ,
msg (string, optional): 新增（必填） 反馈信息
 * */
    @POST("/api/gym/AddFeedback")
    Observable<BaseResponse<FeedbackInfo>> addFeedback(
            @Body FeedbackInfo requestBody
    );

    /*/api/gym/FeedbackAllList
作用:反馈信息 列表
 *
 * */
    @GET("/api/gym/FeedbackAllList")
    Observable<BaseResponse<FeedbackInfoBean>> feedbackAllList(
            @Header("longitude") String longitude,
            @Header("latitude") String latitude,
            @Query("pageNum") String pageNum
    );


    /*/api/gym/AddVisitNum
新增访问量
id (integer, optional): 健身房id/教练id
    *
    * */
    @POST("/api/gym/AddVisitNum")
    Observable<BaseResponse<String>> addVisitNum(
            @Body HashMap<String, String> requestBody
    );
}
