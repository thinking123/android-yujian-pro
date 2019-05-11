package com.yujian.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.yujian.app.utils.RxUtils;
import com.yujian.entity.BaseResponse;
import com.yujian.mvp.contract.FitnessRoomContract;
import com.yujian.mvp.model.entity.FitnessRoomBean;

import java.util.HashMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/06/2019 14:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class FitnessRoomPresenter extends BasePresenter<FitnessRoomContract.Model, FitnessRoomContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FitnessRoomPresenter(FitnessRoomContract.Model model, FitnessRoomContract.View rootView) {
        super(model, rootView);
    }

    public void getNearbyFitnessRoom(
            String longitude,
            String latitude,
            String Kilometres
    ){
        mModel.getNearbyFitnessRoom(
                longitude,
                latitude,
                Kilometres)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<FitnessRoomBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<FitnessRoomBean> response) {
                        if (response.isSuccess()) {
                            mRootView.getNearbyFitnessRoomResult(response.getData());

                        } else{
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }

    public void attention(String collectType,String collectUserId){
        mModel.attention(
                collectType,
                collectUserId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.attentionResult();

                        } else{
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }

    public void unfollow(String id){
        HashMap<String , String> hashMap = new HashMap<String , String>();
        hashMap.put("id" , id);
        mModel.unfollow(
                hashMap)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.unfollowResult();
                        } else{
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
