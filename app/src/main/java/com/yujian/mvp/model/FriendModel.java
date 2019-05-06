package com.yujian.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.yujian.entity.BaseResponse;
import com.yujian.entity.Friend;
import com.yujian.mvp.contract.FriendContract;
import com.yujian.mvp.model.api.service.FriendService;

import java.util.List;

import io.reactivex.Observable;


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
public class FriendModel extends BaseModel implements FriendContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FriendModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<Friend>>> goodFriendAllListHot() {
        return mRepositoryManager.obtainRetrofitService(FriendService.class).goodFriendAllListHot();
    }

    @Override
    public Observable<BaseResponse<List<Friend>>> goodFriendAllList(String pageNum, String longitude, String latitude, String memberId, String name, String role, String id) {
        return mRepositoryManager.obtainRetrofitService(FriendService.class).goodFriendAllList(
                pageNum,
                longitude,
                latitude,
                memberId,
                name,
                role,
                id
        );
    }
}