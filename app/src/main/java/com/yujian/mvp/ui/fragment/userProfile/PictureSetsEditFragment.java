package com.yujian.mvp.ui.fragment.userProfile;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.jakewharton.rxbinding3.view.RxView;
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
import com.yujian.app.utils.matisse.MyGlideEngine;
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
import com.yujian.mvp.ui.adapter.PictureSetsEditAdapter;
import com.yujian.mvp.ui.adapter.PictureSetsManageAdapter;
import com.yujian.utils.Common;
import com.yujian.widget.PrimaryRadiusBtn;
import com.yujian.widget.XRecyclerViewEx;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import kotlin.Unit;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/15/2019 13:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PictureSetsEditFragment extends BaseSupportFragment<UserProfilePresenter> implements UserProfileContract.View {

    @BindView(R.id.gymPictureSetName)
    TextView gymPictureSetName;
    @BindView(R.id.submit)
    PrimaryRadiusBtn submit;
    @BindView(R.id.imageList)
    XRecyclerViewEx imageList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    PictureSetsEditAdapter adapter;

    private BDLocation bdLocation;
    int pageNum = 1;
    int pages = 0;
    int total = 0;
    boolean isLoadingMore = false, isRefreshing = false;
    private boolean isEdit = false;
    private PictureSet pictureSet;
    public final int REQUEST_CODE_CHOOSE = 1;

    public static PictureSetsEditFragment newInstance(PictureSet pictureSet) {
        PictureSetsEditFragment fragment = new PictureSetsEditFragment();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                setFragmentResult(RESULT_OK , new Bundle());
                _mActivity.onBackPressed();
                break;
            case R.id.edit_icon:
                if (isEdit) {
                    isEdit = false;
                    item.setTitle(getResources().getString(R.string.menu_cancel));
                } else {
                    isEdit = true;
                    item.setTitle(getResources().getString(R.string.menu_edit));
                }

                if(isEdit){
                    submit.setVisibility(View.VISIBLE);
                    adapter.setEdit(true);
                }else{
                    submit.setVisibility(View.INVISIBLE);
                    adapter.setEdit(false);
                }
                break;
        }


        return true;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_picture_sets_edit, container, false);
    }

    private void initImageSetData() {
        if (mPresenter != null && bdLocation != null) {
            adapter.clear();
            adapter.notifyDataSetChanged();
            mPresenter.getSetPictureById(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    Integer.toString(pageNum),
                    pictureSet.getId()
            );
        }
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

    private boolean validSubmit() {
        return adapter.getEditValues().size() > 0;
    }

    private void submitData() {
        if (mPresenter != null && validSubmit()) {
            List<GymPicture> list = adapter.getEditValues();
            List<String> dels = new ArrayList<>();

            for (GymPicture p : list) {
                dels.add(p.getId());
            }

            String ids = Common.joinList(dels, "");
            mPresenter.delSetPicture(ids);
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

                    PictureSetsEditFragment.this.bdLocation = location;
                    initImageSetData();
                }
            }
        });

        RxView.clicks(submit).throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Exception {
                        submitData();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            List<String> uris = Matisse.obtainPathResult(data);
//            List<String> urls = new ArrayList<>();
//            for(Uri uri : uris){
//                urls.add(uris.toString());
//            }

            if (mPresenter != null) {
                mPresenter.upLoadImages(uris);
            }

//            adapter.addAll(urls);
        }
    }

    private void initXRecyclerView() {
        adapter = new PictureSetsEditAdapter(new ArrayList<GymPicture>());

        adapter.getPositionClicks().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer pos) throws Exception {
                if (isEdit) {
                    return;
                }
                int p = pos;


                GPreviewBuilder.from(getActivity())//activity实例必须
//                        .to(CustomActivity.class)//自定义Activity 使用默认的预览不需要
                        .setData(adapter.getPreviewImage())//集合
//                        .setUserFragment(ZoomPreviewFragment.class)//自定义Fragment 使用默认的预览不需要
                        .setCurrentIndex(pos)
                        .setSingleFling(false)//是否在黑屏区域点击返回
                        .setDrag(false)//是否禁用图片拖拽返回
                        .setType(GPreviewBuilder.IndicatorType.Number)//指示器类型
                        .start();//启动
            }
        });


        adapter.getAddClicks().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer pos) throws Exception {
                if (isEdit) {
                    return;
                }
                Matisse.from(PictureSetsEditFragment.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(4)
//                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new MyGlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);

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

        imageList.setAdapter(adapter);


        imageList.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                    imageList.loadMoreComplete();
                }
            }
        });

//        imageList.refresh();
    }

    private void uploadPictureSetImage(List<String> url) {
        if (mPresenter != null && bdLocation != null) {
            mPresenter.addSetPicture(
                    pictureSet.getId(),
                    Common.joinList(url, ""),
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude())
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
        if (imageList != null) {
            imageList.refreshComplete();
            imageList.loadMoreComplete();
        }
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
        if (imageList != null) {
            imageList.refreshComplete();
            imageList.loadMoreComplete();
        }

        pageNum = Integer.parseInt(list.getPageNum());
        pages = Integer.parseInt(list.getPages());
        total = Integer.parseInt(list.getTotal());

        adapter.addAll(list.getList());
    }

    @Override
    public void uploadImageResult(String url) {

    }

    @Override
    public void uploadImagesResult(List<String> urls) {
//        adapter.addAll(urls);
        uploadPictureSetImage(urls);
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
        adapter.addAll(requestBody);
    }

    @Override
    public void delSetPictureResult(String requestBody) {
        initImageSetData();
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
