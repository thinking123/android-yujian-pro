package com.yujian.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.yujian.app.utils.RxUtils;
import com.yujian.entity.BaseResponse;
import com.yujian.entity.Friend;
import com.yujian.mvp.contract.FriendContract;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/06/2019 14:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class FriendPresenter extends BasePresenter<FriendContract.Model, FriendContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FriendPresenter(FriendContract.Model model, FriendContract.View rootView) {
        super(model, rootView);
    }


    public void goodFriendAllListHot(){
        mModel.goodFriendAllListHot()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Friend>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Friend>> response) {
                        if (response.isSuccess()) {
                            mRootView.showMessage("验证码发送成功.");

                        } else{
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }


    public void goodFriendAllList(String pageNum, String longitude, String latitude, String memberId, String name, String role, String id){
        mModel.goodFriendAllList(
                pageNum,
                longitude,
                latitude,
                memberId,
                name,
                role,
                id
        )
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Friend>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Friend>> response) {
                        if (response.isSuccess()) {
                            mRootView.showMessage("验证码发送成功.");

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
