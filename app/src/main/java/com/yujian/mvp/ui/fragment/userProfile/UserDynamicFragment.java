package com.yujian.mvp.ui.fragment.userProfile;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yujian.R;
import com.yujian.app.BaseApp;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerUserProfileComponent;
import com.yujian.entity.AttationCurriculum;
import com.yujian.entity.DrillTime;
import com.yujian.entity.Dynamic;
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
import com.yujian.mvp.ui.EventBus.EventBusTags;
import com.yujian.mvp.ui.adapter.TopicListAdapter;
import com.yujian.mvp.ui.adapter.UserDynamicListAdapter;
import com.yujian.utils.entity.ClickObj;
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
 * Created by MVPArmsTemplate on 05/11/2019 14:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserDynamicFragment extends BaseSupportFragment<UserProfilePresenter> implements UserProfileContract.View {

    @BindView(R.id.topicList)
    RecyclerView topicList;
    @BindView(R.id.dynamicList)
    XRecyclerViewEx dynamicList;
    private TopicListAdapter topicListAdapter;
    private UserDynamicListAdapter userDynamicListAdapter;
    private UserProfile userProfile;
    private BDLocation bdLocation;
    int pageNum = 1;
    int pages = 0;
    int total = 0;
    public static UserDynamicFragment newInstance(UserProfile userProfile) {
        UserDynamicFragment fragment = new UserDynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userProfile", userProfile);
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
        return inflater.inflate(R.layout.fragment_user_dynamic, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        userProfile = (UserProfile) this.getArguments().getSerializable("userProfile");
        bdLocation = BaseApp.getInstance().bdLocation;
        int gridSpace = getResources().getDimensionPixelSize(R.dimen.grid_space);

        topicListAdapter = new TopicListAdapter(new ArrayList<>());
        topicList.setAdapter(topicListAdapter);
        topicListAdapter.getPositionClicks().subscribe(new Consumer<Topic>() {
            @Override
            public void accept(Topic friend) throws Exception {

            }
        });

        topicList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        topicList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect,
                                       @NonNull View view,
                                       @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                outRect.left = gridSpace;
            }
        });

        getTopicListByUserId();

        initXRecyclerView();
        
        getMood(pageNum);
    }

    private void initXRecyclerView() {
        userDynamicListAdapter = new UserDynamicListAdapter(new ArrayList<Dynamic>() ,
                this);

        userDynamicListAdapter.getPositionClicks().subscribe(new Consumer<ClickObj>() {
            @Override
            public void accept(ClickObj clickObj) throws Exception {
                String id = clickObj.getId();
                switch (clickObj.getType()){
                    case EventBusTags.AdapterClickable.UserDynamicListAdapter.COMMENTCOUNTICON:
                        break;
                }
            }
        });


//        dynamicList.setLimitNumberToCallLoadMore(2);

        dynamicList.setLayoutManager(new LinearLayoutManager(getActivity()));

        dynamicList.setRefreshProgressStyle(ProgressStyle.BallZigZag); //设定下拉刷新样式
        dynamicList.setLoadingMoreProgressStyle(ProgressStyle.BallZigZag);//设定上拉加载样式
//        friendList.setArrowImageView(R.drawable.qwe);

        dynamicList.setPullRefreshEnabled(false);

        dynamicList.setLoadingMoreEnabled(true);

        dynamicList.setAdapter(userDynamicListAdapter);


        dynamicList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
//                getMood(pageNum + 1);
                if (pageNum < pages) {
                    getMood(pageNum + 1);
                } else {
                    showMessage("没有更多了");
                    dynamicList.loadMoreComplete();
                }
            }
        });

    }


    private void getMood(int pageNum){

        if (mPresenter != null && bdLocation != null) {
            mPresenter.getMood(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    userProfile.getUserRole(),
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    userProfile.getId(),
                    Integer.toString(pageNum)
            );
        }
    }
    private void getTopicListByUserId(){

        if (mPresenter != null && bdLocation != null) {
            mPresenter.getTopicListByUserId(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    userProfile.getId()
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
        topicListAdapter.addAll(res);
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
        pageNum = Integer.parseInt(res.getPageNum());
        pages = Integer.parseInt(res.getPages());
        total = Integer.parseInt(res.getTotal());

        userDynamicListAdapter.addAll(res.getList());


//        topicListAdapter.addAll(res.getList());
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
