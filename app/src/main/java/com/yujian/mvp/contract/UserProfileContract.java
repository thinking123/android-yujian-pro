package com.yujian.mvp.contract;

import com.yujian.entity.GymPicture;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.yujian.entity.BaseResponse;
import com.yujian.entity.DrillTime;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.PictureSet;
import com.yujian.entity.UserProfile;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;
import com.yujian.mvp.model.entity.GymPictureBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/11/2019 13:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface UserProfileContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getUserProfileResult(UserProfile userProfile);

        void getCoachOrUserRelevantResult(GetCoachOrUserRelevantBean getCoachOrUserRelevantBean);

        void getGymdetailsCoachResult(List<Personaltainer> list);

        void getCurriculumByTimeResult(List<DrillTime> list);

        void getSetPictureByIdResult(GymPictureBean list);


        void uploadImageResult(String url);

        void uploadImagesResult(List<String> urls);

        void addCoachCredentialsResult(String res);

        void delCoachCredentialsResult(String res);

        void getMsgByIdToEditResult(UserProfileMatchCertificatePersonalStory res);

        void getMsgByTypeResult(List<UserProfileMatchCertificatePersonalStory> res);


        void addSetResult(
                PictureSet requestBody
        );


        void addSetPictureResult(
                List<GymPicture> requestBody
        );


        void delSetPictureResult(
                String requestBody
        );


        void editBackGroundResult(
                String requestBody
        );


        void setAllResult(
                List<PictureSet> list
        );
      

        void sortSetPictureResult(
                String requestBody
        );


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<UserProfile>> getUserProfile(
                String id
        );

        Observable<BaseResponse<GetCoachOrUserRelevantBean>> getCoachOrUserRelevant(
                String id
        );

        //GET /api/gym/GymdetailsCoach
        //作用:健身房详情--课程-私教 列表 --编号
        Observable<BaseResponse<List<Personaltainer>>> getGymdetailsCoach(
                String id
        );

        Observable<BaseResponse<List<DrillTime>>> getCurriculumByTime(
                String id,
                String week,
                String time
        );

        Observable<BaseResponse<GymPictureBean>> getSetPictureById(
                String longitude,
                String latitude,
                String pageNum,
                String setId
        );

        Observable<BaseResponse<String>> addCoachCredentials(
                HashMap<String, String> requestBody
        );

        Observable<BaseResponse<String>> delCoachCredentials(
                HashMap<String, String> requestBody
        );

        Observable<BaseResponse<UserProfileMatchCertificatePersonalStory>> getMsgByIdToEdit(
                String longitude,
                String latitude,
                String id
        );

        Observable<BaseResponse<List<UserProfileMatchCertificatePersonalStory>>> getMsgByType(
                String longitude,
                String latitude,
                String id,
                String type
        );

        Observable<BaseResponse<String>> upLoadImage(MultipartBody.Part upload_file);

        Observable<BaseResponse<List<String>>> uploadImages(List<MultipartBody.Part> upload_file);




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

        Observable<BaseResponse<PictureSet>> addSet(
                PictureSet requestBody
        );


    /*/api/gym/AddSetPicture
新增/ 图片集 图片

图片集 图片 {
gymPictureSetId (integer, optional): 图片集id ,
url (string, optional): url 逗号分割
}
    *
    * */


        Observable<BaseResponse<List<GymPicture>>> addSetPicture(
                HashMap<String, String> requestBody,
                String longitude,
                String latitude
        );

        /*/api/gym/DelSetPicture
    删除/ 图片集 图片
    
    
    id (string, optional): id 多个id请用逗号分割
        *
        * */

        Observable<BaseResponse<String>> delSetPicture(
                HashMap<String, String> requestBody
        );


        /*/api/gym/EditBackGround
    设置/ 图片集 背景
        *设置图片集背景 {
    id (string, optional): 图片集id ,
    url (string, optional): 背景图url
    }
        * */

        Observable<BaseResponse<String>> editBackGround(
                HashMap<String, String> requestBody
        );


        /*/api/gym/SetAll
    获取 所有的图片集
        *
        * */

        Observable<BaseResponse<List<PictureSet>>> setAll(
                String longitude,
                String latitude,
                String id
        );
        /*/api/gym/SortSetPicture
        *排序图片 {
    gymPictureSetId (string, optional): 图片集id ,
    id (string, optional): 图片集 图片id ,
    newSrot (string, optional): 新的排序位置
    }
        * */

        Observable<BaseResponse<String>> sortSetPicture(
                HashMap<String, String> requestBody
        );
    }
}
