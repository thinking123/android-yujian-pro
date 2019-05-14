package com.yujian.mvp.ui.fragment.userProfile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.previewlibrary.GPreviewBuilder;
import com.yujian.R;
import com.yujian.app.BaseApp;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerUserProfileComponent;
import com.yujian.entity.DrillTime;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.PictureSet;
import com.yujian.entity.UserProfile;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.mvp.contract.UserProfileContract;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;
import com.yujian.mvp.model.entity.GymPictureBean;
import com.yujian.mvp.presenter.UserProfilePresenter;
import com.yujian.mvp.ui.EventBus.EventBusTags;
import com.yujian.mvp.ui.EventBus.UserProfileEvent;
import com.yujian.mvp.ui.adapter.TimeLineAdapter;
import com.yujian.utils.entity.TwoLevelEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/13/2019 19:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserProfileTimeLineFragment extends BaseSupportFragment<UserProfilePresenter> implements UserProfileContract.View {

    private UserProfile userProfile;
    private String type;
    /*1 认证证书 2参加赛事 3个人事迹*/
    private String innerType = "1";
    @BindView(R.id.timelineList)
    RecyclerView timelineList;
    private TimeLineAdapter adapter;
    private BDLocation bdLocation;
    public static UserProfileTimeLineFragment newInstance(UserProfile userProfile, String type) {
        UserProfileTimeLineFragment fragment = new UserProfileTimeLineFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userProfile", userProfile);
        bundle.putSerializable("type", type);
        fragment.setArguments(bundle);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_user_profile_time_line, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_icon:
                String et = String.format("add%s" ,type).toUpperCase();
                EventBus.getDefault().post(new UserProfileEvent(et));
                break;
            case android.R.id.home:
                _mActivity.onBackPressed();
                break;
        }


        return true;
    }
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.nav_btn_back);


        userProfile = (UserProfile) this.getArguments().getSerializable("userProfile");
        type = this.getArguments().getString("type");




        List<UserProfileMatchCertificatePersonalStory> list = new ArrayList<>();
        switch (type){
            case EventBusTags.UserProfile.CERTIFICATE:
                list = userProfile.getCertificateList();
                innerType = "1";
                break;
            case EventBusTags.UserProfile.MATCH:
                list = userProfile.getMatchList();
                innerType = "2";
                break;
            case EventBusTags.UserProfile.PERSONALSTORY:
                innerType = "3";
                break;
        }

        adapter = new TimeLineAdapter(list);
        adapter.getGridClicks().subscribe(new Consumer<TwoLevelEvent>() {
            @Override
            public void accept(TwoLevelEvent integer) throws Exception {
                GPreviewBuilder.from(getActivity())//activity实例必须
//                        .to(CustomActivity.class)//自定义Activity 使用默认的预览不需要
                        .setData(adapter.getPreviewImage(integer.getParentIndex()))//集合
//                        .setUserFragment(ZoomPreviewFragment.class)//自定义Fragment 使用默认的预览不需要
                        .setCurrentIndex(integer.getChildIndex())
                        .setSingleFling(false)//是否在黑屏区域点击返回
                        .setDrag(false)//是否禁用图片拖拽返回
                        .setType(GPreviewBuilder.IndicatorType.Number)//指示器类型
                        .start();//启动
            }
        });
        timelineList.setAdapter(adapter);

        timelineList.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.getDelClicks().subscribe(new Consumer<UserProfileMatchCertificatePersonalStory>() {
            @Override
            public void accept(UserProfileMatchCertificatePersonalStory userProfileMatchCertificatePersonalStory) throws Exception {
                if(mPresenter != null){
                    mPresenter.delCoachCredentials(userProfileMatchCertificatePersonalStory.getId());
                }
            }
        });

        adapter.getEditClicks().subscribe(new Consumer<UserProfileMatchCertificatePersonalStory>() {
            @Override
            public void accept(UserProfileMatchCertificatePersonalStory userProfileMatchCertificatePersonalStory) throws Exception {
                String et = String.format("add%s" ,type).toUpperCase();
                UserProfileEvent event = new UserProfileEvent(et);
                event.setUserProfileMatchCertificatePersonalStory(userProfileMatchCertificatePersonalStory);
                EventBus.getDefault().post(event);
            }
        });

        BaseApp.getInstance().myListener.getBDLocation().take(1).subscribe(new Consumer<BDLocation>() {
            @Override
            public void accept(BDLocation location) throws Exception {
                if(location != null ){

                    UserProfileTimeLineFragment.this.bdLocation = location;
                    initTimelineData();
                }
            }
        });
    }

    private void initTimelineData(){
        if (mPresenter != null && bdLocation != null) {
            adapter.clear();
            mPresenter.getMsgByType(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    userProfile.getId(),
                    innerType
            );
        }
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
        initTimelineData();
    }

    @Override
    public void getMsgByIdToEditResult(UserProfileMatchCertificatePersonalStory res) {

    }

    @Override
    public void getMsgByTypeResult(List<UserProfileMatchCertificatePersonalStory> res) {
        adapter.addAll(res);
    }
}
