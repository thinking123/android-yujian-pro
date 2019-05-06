package com.yujian.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.yujian.entity.BaseResponse;
import com.yujian.mvp.contract.loginContract;
import com.yujian.mvp.model.api.service.LoginService;
import com.yujian.mvp.model.entity.LoginBean;
import com.yujian.mvp.model.entity.LoginRequestBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/27/2019 23:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class loginModel extends BaseModel implements loginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public loginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<String>> getPhoneCode(String phone) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class).getPhoneCode(phone);
    }

    @Override
    public Observable<BaseResponse<String>> checkPhone(String phone) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class).checkPhone(phone);
    }


    @Override
    public Observable<BaseResponse<LoginBean>> loginByPhone(
            String deviceId,
            String openId,
            String passWord,
            String phone) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class).loginByPhone(new LoginRequestBean(deviceId , openId , passWord , phone));
    }

}