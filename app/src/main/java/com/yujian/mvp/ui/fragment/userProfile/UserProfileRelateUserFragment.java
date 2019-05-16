package com.yujian.mvp.ui.fragment.userProfile;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.previewlibrary.GPreviewBuilder;
import com.yujian.R;
import com.yujian.app.BaseApp;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerUserProfileComponent;
import com.yujian.entity.AttationCurriculum;
import com.yujian.entity.DrillTime;
import com.yujian.entity.FeedbackInfo;
import com.yujian.entity.FollowUser;
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
import com.yujian.mvp.ui.EventBus.EventBusTags;
import com.yujian.mvp.ui.adapter.RelateUserListAdapter;
import com.yujian.widget.XRecyclerViewEx;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/16/2019 22:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserProfileRelateUserFragment extends BaseSupportFragment<UserProfilePresenter> implements UserProfileContract.View {
    private UserProfile userProfile;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pageTitle)
    TextView pageTitle;
    @BindView(R.id.relateUserList)
    XRecyclerViewEx relateUserList;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    private RelateUserListAdapter adapter;
    private String type;
    private BDLocation bdLocation;
    int pageNum = 1;
    int pages = 0;
    int total = 0;
    private String selectedType = "1";
    public static UserProfileRelateUserFragment newInstance(UserProfile userProfile , String type) {
        UserProfileRelateUserFragment fragment = new UserProfileRelateUserFragment();
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
        return inflater.inflate(R.layout.fragment_user_profile_relate_user, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolbarForActionbar(toolbar);
        bdLocation = BaseApp.getInstance().bdLocation;
        userProfile = (UserProfile) this.getArguments().getSerializable("userProfile");
        type = this.getArguments().getString("type");
        if(type == EventBusTags.UserProfile.GOTOFANS){
            pageTitle.setText("关注TA的");
        }else {
            pageTitle.setText("TA关注的");
        }
        initList();

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //1 健身房 2 教练 3 普通用户
                int pos = tab.getPosition() + 1;
                selectedType = Integer.toString(pos);

                initImageSetData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    
    private void initList(){
        adapter = new RelateUserListAdapter(new ArrayList<FollowUser>());

        adapter.getPositionClicks().subscribe(new Consumer<FollowUser>() {
            @Override
            public void accept(FollowUser followUser) throws Exception {

            }
        });


        relateUserList.setLimitNumberToCallLoadMore(2);

        relateUserList.setLayoutManager(new LinearLayoutManager(getActivity()));

        relateUserList.setRefreshProgressStyle(ProgressStyle.BallZigZag); //设定下拉刷新样式
        relateUserList.setLoadingMoreProgressStyle(ProgressStyle.BallZigZag);//设定上拉加载样式
//        friendList.setArrowImageView(R.drawable.qwe);

        relateUserList.setPullRefreshEnabled(true);

        relateUserList.setLoadingMoreEnabled(true);

        relateUserList.setAdapter(adapter);


        relateUserList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initImageSetData();
            }

            @Override
            public void onLoadMore() {

                if (pageNum < pages) {
                    getMoreData(pageNum + 1);
                } else {
                    showMessage("没有更多了");
                    relateUserList.loadMoreComplete();
                }
            }
        });


    }
    private void getMoreData(int pageNum) {
        if (mPresenter != null) {
            mPresenter.followAllList(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    Integer.toString(pageNum),
                    selectedType
            );
        }
    }
    private void initImageSetData() {
        if (mPresenter != null && bdLocation != null) {
            adapter.clear();
            mPresenter.followAllList(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    Integer.toString(pageNum),
                    selectedType
            );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                setFragmentResult(RESULT_OK , new Bundle());
                _mActivity.onBackPressed();
                break;
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
        pageNum = Integer.parseInt(res.getPageNum());
        pages = Integer.parseInt(res.getPages());
        total = Integer.parseInt(res.getTotal());
        adapter.addAll(res.getList());
    }

    @Override
    public void getCoachOrUserRelevantResult(GetCoachOrUserRelevantBean res) {

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
    public void getTopicListByUserIdResult(List<Topic> res) {

    }
}
