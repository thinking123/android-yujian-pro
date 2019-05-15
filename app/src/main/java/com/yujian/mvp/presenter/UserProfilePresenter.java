package com.yujian.mvp.presenter;

import android.app.Application;
import android.net.Uri;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.yujian.app.utils.RxUtils;
import com.yujian.entity.BaseResponse;
import com.yujian.entity.DrillTime;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.UserProfile;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.mvp.contract.UserProfileContract;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;
import com.yujian.mvp.model.entity.GymPictureBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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

    public void getSetPictureById(String longitude,
                                  String latitude,
                                  String pageNum,
                                  String setId) {
        mModel.getSetPictureById(longitude, latitude, pageNum, setId)
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

    /*
    * /api/coach/AddCoachCredentials
新增/编辑 1 认证证书 2参加赛事 3个人事迹 --编号 003

证书/赛事表/个人事迹 {
id (integer, optional): id 编辑必穿 ,
introduce (string, optional): 介绍 ,
name (string, optional): 名称 ,
times (string, optional): 时间 ,
type (integer, optional): 1 认证证书 2参加赛事 3个人事迹 ,
url (string, optional): 图片 逗号分割
}
    * */
    public void addCoachCredentials(String id,
                                    String introduce,
                                    String name,
                                    String times,
                                    String type,
                                    String url
    ) {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("introduce", introduce);
        map.put("name", name);
        map.put("times", times);
        map.put("type", type);
        map.put("url", url);


        mModel.addCoachCredentials(map)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.addCoachCredentialsResult(response.getData());

                        } else {
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }

    /*
    *
/api/coach/DelCoachCredentials
删除 1 认证证书 2参加赛事 3个人事迹
id (string, optional): id 多个id请用逗号分割
    * */
    public void delCoachCredentials(String id
    ) {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);


        mModel.delCoachCredentials(map)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.delCoachCredentialsResult(response.getData());

                        } else {
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }

    public void getMsgByIdToEdit(String longitude, String latitude, String id) {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);


        mModel.getMsgByIdToEdit(longitude, latitude, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserProfileMatchCertificatePersonalStory>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserProfileMatchCertificatePersonalStory> response) {
                        if (response.isSuccess()) {
                            mRootView.getMsgByIdToEditResult(response.getData());

                        } else {
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }

    public void getMsgByType(String longitude, String latitude, String id, String type) {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);


        mModel.getMsgByType(longitude, latitude, id, type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<UserProfileMatchCertificatePersonalStory>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<UserProfileMatchCertificatePersonalStory>> response) {
                        if (response.isSuccess()) {
                            mRootView.getMsgByTypeResult(response.getData());

                        } else {
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });
    }

    public void upLoadImage(String file) {
        MultipartBody.Part face = MultipartBody.Part.createFormData("file", "upload_file.png", RequestBody.create(MediaType.parse("multipart/form-data"), new File(file)));
        mModel.upLoadImage(face)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.uploadImageResult(response.getData());

                        } else {
                            mRootView.showMessage(response.getMsg());
                        }

                    }
                });


    }

    public void upLoadImages(List<String> files) {

        List<File> fs = new ArrayList<>();
        for (String file : files) {
            Uri uri = Uri.parse(file);
            fs.add(new File(file));
        }
        List<MultipartBody.Part> parts = new ArrayList<>();

        for (File file : fs) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
            parts.add(part);
        }


        mModel.uploadImages(parts)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<String>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<String>> response) {
                        if (response.isSuccess()) {
                            mRootView.uploadImagesResult(response.getData());

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
