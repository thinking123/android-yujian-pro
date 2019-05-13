package com.yujian.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.yujian.entity.BaseResponse;
import com.yujian.entity.DrillTime;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.UserProfile;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;
import com.yujian.mvp.model.entity.GymPictureBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
    }
}
