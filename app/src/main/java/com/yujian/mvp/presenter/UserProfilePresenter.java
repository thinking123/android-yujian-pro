package com.yujian.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.yujian.app.utils.RxUtils;
import com.yujian.entity.BaseResponse;
import com.yujian.entity.DrillTime;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.UserProfile;
import com.yujian.mvp.contract.UserProfileContract;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;
import com.yujian.mvp.model.entity.GymPictureBean;

import java.util.List;


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
public class UserProfilePresenter extends BasePresenter<UserProfileContract.Model, UserProfileContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserProfilePresenter(UserProfileContract.Model model, UserProfileContract.View rootView) {
        super(model, rootView);
    }

    public void getUserProfile(String id) {
        mModel.getUserProfile(id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserProfile>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserProfile> response) {
                        if (response.isSuccess()) {
                            mRootView.getUserProfileResult(response.getData());

                        } else {
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }

    public void getCoachOrUserRelevant(String id) {
        mModel.getCoachOrUserRelevant(id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<GetCoachOrUserRelevantBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<GetCoachOrUserRelevantBean> response) {
                        if (response.isSuccess()) {
                            mRootView.getCoachOrUserRelevantResult(response.getData());

                        } else {
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }

    public void getGymdetailsCoach(String id) {
        mModel.getGymdetailsCoach(id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Personaltainer>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Personaltainer>> response) {
                        if (response.isSuccess()) {
                            mRootView.getGymdetailsCoachResult(response.getData());

                        } else {
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }

    public void getCurriculumByTime(String id, String week, String time) {
        mModel.getCurriculumByTime(id, week, time)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<DrillTime>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<DrillTime>> response) {
                        if (response.isSuccess()) {
                            mRootView.getCurriculumByTimeResult(response.getData());

                        } else {
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }

    public void getSetPictureById( String longitude,
                                   String latitude,
                                   String pageNum,
                                   String setId) {
        mModel.getSetPictureById(longitude,  latitude,  pageNum,  setId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<GymPictureBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<GymPictureBean> response) {
                        if (response.isSuccess()) {
                            mRootView.getSetPictureByIdResult(response.getData());

                        } else {
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
