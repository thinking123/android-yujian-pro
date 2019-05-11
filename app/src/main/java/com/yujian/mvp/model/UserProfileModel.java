package com.yujian.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yujian.entity.BaseResponse;
import com.yujian.entity.DrillTime;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.UserProfile;
import com.yujian.mvp.contract.UserProfileContract;
import com.yujian.mvp.model.api.service.DrillTimeService;
import com.yujian.mvp.model.api.service.UserProfileService;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;

import java.util.List;

import io.reactivex.Observable;


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
@FragmentScope
public class UserProfileModel extends BaseModel implements UserProfileContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserProfileModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<UserProfile>> getUserProfile(String id) {
        return mRepositoryManager.obtainRetrofitService(UserProfileService.class).getUserProfile(id);
    }

    @Override
    public Observable<BaseResponse<GetCoachOrUserRelevantBean>> getCoachOrUserRelevant(String id) {
        return mRepositoryManager.obtainRetrofitService(UserProfileService.class).getCoachOrUserRelevant(id);
    }

    @Override
    public Observable<BaseResponse<List<Personaltainer>>> getGymdetailsCoach(String id) {
        return mRepositoryManager.obtainRetrofitService(UserProfileService.class).getGymdetailsCoach(id);
    }

    @Override
    public Observable<BaseResponse<List<DrillTime>>> getCurriculumByTime(String id, String week, String time) {
        return mRepositoryManager.obtainRetrofitService(DrillTimeService.class).getCurriculumByTime(id , week ,time);
    }
}