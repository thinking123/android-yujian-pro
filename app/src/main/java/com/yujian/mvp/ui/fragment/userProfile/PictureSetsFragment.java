package com.yujian.mvp.ui.fragment.userProfile;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.yujian.entity.AttationCurriculum;
import com.yujian.entity.FeedbackInfo;
import com.yujian.entity.GymPicture;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.previewlibrary.GPreviewBuilder;
import com.yujian.R;
import com.yujian.app.BaseApp;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerUserProfileComponent;
import com.yujian.entity.DrillTime;
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
import com.yujian.mvp.ui.adapter.ImageListAdapter;
import com.yujian.mvp.ui.fragment.main.FriendFragment;
import com.yujian.mvp.ui.fragment.zoompreivew.ZoomPreviewFragment;
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
 * Created by MVPArmsTemplate on 05/13/2019 22:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PictureSetsFragment extends BaseSupportFragment<UserProfilePresenter> implements UserProfileContract.View {

    private PictureSet pictureSet;
    private BDLocation bdLocation;
    int pageNum = 1;
    int pages = 0;
    int total = 0;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.gymPictureSetName)
    TextView gymPictureSetName;
    @BindView(R.id.imageList)
    XRecyclerViewEx imageList;
    boolean isLoadingMore = false, isRefreshing = false;
    private ImageListAdapter imageListAdapter;

    public static PictureSetsFragment newInstance(PictureSet pictureSet) {
        PictureSetsFragment fragment = new PictureSetsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("pictureSet", pictureSet);
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

    View header = null;
    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

//         header =  inflater.inflate(R.layout.xrecylerview_header_row, container,false);
        return inflater.inflate(R.layout.fragment_picture_sets, container, false);
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

    private void initImageSetData() {
        if (mPresenter != null && bdLocation != null) {
            imageListAdapter.clear();
            mPresenter.getSetPictureById(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    Integer.toString(pageNum),
                    pictureSet.getId()
            );
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolbarForActionbar(toolbar);
        pictureSet = (PictureSet) this.getArguments().getSerializable("pictureSet");
        gymPictureSetName.setText(pictureSet.getGymPictureSetName());
        initXRecyclerView();
        BaseApp.getInstance().myListener.getBDLocation().take(1).subscribe(new Consumer<BDLocation>() {
            @Override
            public void accept(BDLocation location) throws Exception {
                if (location != null) {

                    PictureSetsFragment.this.bdLocation = location;
                    initImageSetData();
                }
            }
        });

    }

    private void initXRecyclerView() {
        imageListAdapter = new ImageListAdapter(new ArrayList<GymPicture>());

        imageListAdapter.getPositionClicks().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer pos) throws Exception {
                int p = pos;


                GPreviewBuilder.from(getActivity())//activity实例必须
//                        .to(CustomActivity.class)//自定义Activity 使用默认的预览不需要
                        .setData(imageListAdapter.getPreviewImage())//集合
//                        .setUserFragment(ZoomPreviewFragment.class)//自定义Fragment 使用默认的预览不需要
                        .setCurrentIndex(pos)
                        .setSingleFling(false)//是否在黑屏区域点击返回
                        .setDrag(false)//是否禁用图片拖拽返回
                        .setType(GPreviewBuilder.IndicatorType.Number)//指示器类型
                        .start();//启动
            }
        });


//        imageList.addHeaderView(header);
        imageList.setLimitNumberToCallLoadMore(2);
        int gridSpace = getResources().getDimensionPixelSize(R.dimen.grid_space);

        imageList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                //xreceview 有header 和 footer
                final int itemCount = state.getItemCount();
                int pos = parent.getChildAdapterPosition(view);
                if (pos == RecyclerView.NO_POSITION ||
                        pos == 0 ||
                        pos == (itemCount - 1)) {
                    return;
                }
                int rePos = pos - 1;
                if (rePos % 3 != 2) {
                    outRect.right = gridSpace;
                    Timber.i("pos % 3 != 2 current pos : " + rePos);
                }

                if (rePos > 2) {
                    outRect.top = gridSpace;
                    Timber.i("pos > 2 current pos : " + rePos);
                }
            }
        });
        imageList.setLayoutManager(new GridLayoutManager(getActivity(), 3));

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        imageList.setLayoutManager(linearLayoutManager);
//        imageList.setIte
        imageList.setRefreshProgressStyle(ProgressStyle.BallZigZag); //设定下拉刷新样式
        imageList.setLoadingMoreProgressStyle(ProgressStyle.BallZigZag);//设定上拉加载样式
//        friendList.setArrowImageView(R.drawable.qwe);

        imageList.setPullRefreshEnabled(true);

        imageList.setLoadingMoreEnabled(true);

        imageList.setAdapter(imageListAdapter);


        imageList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefreshing = true;
                initImageSetData();
            }

            @Override
            public void onLoadMore() {

                if (pageNum < pages) {
                    isLoadingMore = true;
                    getMoreData(pageNum + 1);
                } else {
                    showMessage("没有更多了");
                    imageList.loadMoreComplete();
                }
            }
        });

//        imageList.refresh();
    }

    private void getMoreData(int pageNum) {
        if (mPresenter != null) {
            mPresenter.getSetPictureById(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    Integer.toString(pageNum),
                    pictureSet.getId()
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

    }

    @Override
    public void getGymdetailsCoachResult(List<Personaltainer> list) {

    }

    @Override
    public void getCurriculumByTimeResult(List<DrillTime> list) {

    }

    @Override
    public void getSetPictureByIdResult(GymPictureBean list) {
        if (isRefreshing && imageList != null) {
            isRefreshing = false;
            imageList.refreshComplete();
        }

        if (isLoadingMore && imageList != null) {
            isLoadingMore = false;
            imageList.loadMoreComplete();
        }

        pageNum = Integer.parseInt(list.getPageNum());
        pages = Integer.parseInt(list.getPages());
        total = Integer.parseInt(list.getTotal());

        imageListAdapter.addAll(list.getList());
//        imageList.refresh();
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

    }
}
