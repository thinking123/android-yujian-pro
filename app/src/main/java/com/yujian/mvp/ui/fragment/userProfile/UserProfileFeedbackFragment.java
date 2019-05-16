package com.yujian.mvp.ui.fragment.userProfile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jakewharton.rxbinding3.view.RxView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yujian.R;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerUserProfileComponent;
import com.yujian.entity.AttationCurriculum;
import com.yujian.entity.DrillTime;
import com.yujian.entity.FeedbackInfo;
import com.yujian.entity.GymPicture;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.PictureSet;
import com.yujian.entity.Topic;
import com.yujian.entity.UserProfile;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.mvp.contract.UserProfileContract;
import com.yujian.mvp.model.entity.DynamicTopicBean;
import com.yujian.mvp.model.entity.FeedbackInfoBean;
import com.yujian.mvp.model.entity.FollowUserBean;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;
import com.yujian.mvp.model.entity.GymPictureBean;
import com.yujian.mvp.presenter.UserProfilePresenter;
import com.yujian.utils.Common;
import com.yujian.widget.PrimaryRadiusBtn;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import kotlin.Unit;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/15/2019 21:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserProfileFeedbackFragment extends BaseSupportFragment<UserProfilePresenter> implements UserProfileContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.submit)
    PrimaryRadiusBtn submit;

    private String id;
    public static UserProfileFeedbackFragment newInstance(String id) {
        UserProfileFeedbackFragment fragment = new UserProfileFeedbackFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                _mActivity.onBackPressed();
                break;
        }


        return true;
    }
    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerUserProfileComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_user_profile_feedback, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolbarForActionbar(toolbar);
        id = this.getArguments().getString("id");

        RxView.clicks(submit).throttleFirst(500 , TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Exception {
                        submitData();
                    }
                });
    }
    private void submitData(){
        if(mPresenter != null && validSubmit()){
            FeedbackInfo info = new FeedbackInfo();
            info.setGymId(id);
            info.setMsg(editText.getText().toString());
            mPresenter.addFeedback(info);
//            mPresenter.addCoachCredentials(
//                    "",
//                    introduce.getText().toString(),
//                    editText.getText().toString(),
//                    time.getText().toString(),
//                    Common.Filter.userProfileMatchCertificatePersonalStoryTypeFilter(type),
//                    Common.joinList(adapter.getValues() , "")
//            );
        }
    }
    private boolean validSubmit(){
        boolean isOk = false;

        if(Common.isUIEmpty(editText.getText())){
            showMessage("请输入内容");
            return isOk;
        }

        return true;
    }
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void getUserProfileResult(UserProfile userProfile) {

    }

    @Override
    public void getCoachOrUserRelevantResult(GetCoachOrUserRelevantBean getCoachOrUserRelevantBean) {

    }

    @Override
    public void gymdetailsCoachResult(List<Personaltainer> res) {

    }

    @Override
    public void addCollectCurriculumResult(AttationCurriculum res) {

    }

    @Override
    public void delCollectCurriculumResult(String res) {

    }

    @Override
    public void getGymdetailsCoachResult(List<Personaltainer> list) {

    }

    @Override
    public void getCurriculumByTimeResult(List<DrillTime> list) {

    }

    @Override
    public void getSetPictureByIdResult(GymPictureBean list) {

    }

    @Override
    public void uploadImageResult(String url) {

    }

    @Override
    public void uploadImagesResult(List<String> urls) {

    }

    @Override
    public void addCoachCredentialsResult(String res) {

    }

    @Override
    public void delCoachCredentialsResult(String res) {

    }

    @Override
    public void getMsgByIdToEditResult(UserProfileMatchCertificatePersonalStory res) {

    }

    @Override
    public void getMsgByTypeResult(List<UserProfileMatchCertificatePersonalStory> res) {

    }

    @Override
    public void addSetResult(PictureSet requestBody) {

    }

    @Override
    public void addSetPictureResult(List<GymPicture> requestBody) {

    }

    @Override
    public void delSetPictureResult(String requestBody) {

    }

    @Override
    public void editBackGroundResult(String requestBody) {

    }

    @Override
    public void setAllResult(List<PictureSet> list) {

    }

    @Override
    public void sortSetPictureResult(String requestBody) {

    }

    @Override
    public void attentionResult(String res) {

    }

    @Override
    public void unfollowResult(String res) {

    }

    @Override
    public void addFeedbackResult(FeedbackInfo res) {
        _mActivity.onBackPressed();
    }

    @Override
    public void feedbackAllListResult(FeedbackInfoBean res) {

    }

    @Override
    public void addVisitNumResult(String res) {

    }

    @Override
    public void cancelCommentPraiseResult(Topic res) {

    }

    @Override
    public void getMoodResult(DynamicTopicBean res) {

    }

    @Override
    public void addScanResult(Topic res) {

    }

    @Override
    public void toCommentPraiseResult(String res) {

    }

    @Override
    public void followAllListResult(FollowUserBean res) {

    }
}
