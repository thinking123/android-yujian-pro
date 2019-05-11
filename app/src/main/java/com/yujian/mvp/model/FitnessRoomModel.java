package com.yujian.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.yujian.entity.BaseResponse;
import com.yujian.mvp.contract.FitnessRoomContract;
import com.yujian.mvp.model.api.service.FitnessRoomService;
import com.yujian.mvp.model.entity.AttentionRequestBean;
import com.yujian.mvp.model.entity.FitnessRoomBean;

import java.util.HashMap;

import io.reactivex.Observable;


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
public class FitnessRoomModel extends BaseModel implements FitnessRoomContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FitnessRoomModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<FitnessRoomBean>> getNearbyFitnessRoom(
            String longitude,
            String latitude,
            String Kilometres
    ) {
        return mRepositoryManager.obtainRetrofitService(FitnessRoomService.class).getNearbyFitnessRoom(
                longitude,
                latitude,
                Kilometres
        );
    }

    @Override
    public Observable<BaseResponse<String>> attention(String collectType,String collectUserId) {
        return mRepositoryManager.obtainRetrofitService(FitnessRoomService.class).attention(
             new AttentionRequestBean(collectType , collectUserId));
    }

    @Override
    public Observable<BaseResponse<String>> unfollow(HashMap<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(FitnessRoomService.class).unfollow(
                map
        );
    }
}