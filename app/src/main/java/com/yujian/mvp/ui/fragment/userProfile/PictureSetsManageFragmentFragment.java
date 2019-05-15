package com.yujian.mvp.ui.fragment.userProfile;

import android.content.Intent;
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

import com.baidu.location.BDLocation;
import com.google.gson.JsonElement;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
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
import com.yujian.mvp.ui.adapter.PictureSetsManageAdapter;
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
 * Created by MVPArmsTemplate on 05/15/2019 13:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PictureSetsManageFragmentFragment extends BaseSupportFragment<UserProfilePresenter> implements UserProfileContract.View {

    @BindView(R.id.imageList)
    private XRecyclerViewEx imageList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    PictureSetsManageAdapter adapter;
    private BDLocation bdLocation;
    private String userId;
    public static PictureSetsManageFragmentFragment newInstance(String userId) {
        PictureSetsManageFragmentFragment fragment = new PictureSetsManageFragmentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", userId);
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
        return inflater.inflate(R.layout.fragment_picture_sets_manage, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolbarForActionbar(toolbar);

        userId = this.getArguments().getString("id");
        adapter = new PictureSetsManageAdapter(new ArrayList<>());
        imageList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        imageList.setRefreshProgressStyle(ProgressStyle.BallZigZag); //设定下拉刷新样式
        imageList.setLoadingMoreProgressStyle(ProgressStyle.BallZigZag);//设定上拉加载样式
        imageList.setPullRefreshEnabled(true);
        imageList.setLoadingMoreEnabled(false);
        imageList.setAdapter(adapter);
        int gridSpaceRight = getResources().getDimensionPixelSize(R.dimen.grid_space_right_14);
        int gridSpaceBottom = getResources().getDimensionPixelSize(R.dimen.grid_space_bottom_10);
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
                if (rePos % 2 == 0) {
                    outRect.right = gridSpaceRight;
                    outRect.bottom = gridSpaceBottom;
                }else{
                    outRect.bottom = gridSpaceBottom;
                }

            }
        });
        imageList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initPictureSetsManageData();
            }

            @Override
            public void onLoadMore() {

            }
        });



        BaseApp.getInstance().myListener.getBDLocation().take(1).subscribe(new Consumer<BDLocation>() {
            @Override
            public void accept(BDLocation location) throws Exception {
                if (location != null) {

                    PictureSetsManageFragmentFragment.this.bdLocation = location;
                    initPictureSetsManageData();
                }
            }
        });
    }

    private void initPictureSetsManageData() {
        if (mPresenter != null && bdLocation != null) {
            adapter.clear();
            mPresenter.setAll(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    userId
            );
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                _mActivity.onBackPressed();
                break;
            case R.menu.add_menu:

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
        if(imageList != null){
            imageList.refreshComplete();
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
    public void addSetPictureResult(JsonElement requestBody) {

    }

    @Override
    public void delSetPictureResult(String requestBody) {

    }

    @Override
    public void editBackGroundResult(String requestBody) {

    }

    @Override
    public void setAllResult(List<PictureSet> list) {
        if(imageList != null){
            imageList.refreshComplete();
        }
        adapter.addAll(list);
    }

    @Override
    public void sortSetPictureResult(String requestBody) {

    }
}
