package com.yujian.mvp.ui.fragment.userProfile;

import android.app.Dialog;
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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.yujian.entity.AttationCurriculum;
import com.yujian.entity.FeedbackInfo;
import com.yujian.entity.GymPicture;
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
import com.yujian.mvp.ui.adapter.PictureSetsManageAdapter;
import com.yujian.utils.Common;
import com.yujian.widget.PrimaryRadiusBtn;
import com.yujian.widget.XRecyclerViewEx;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.ISupportFragment;
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

    public final int RES_CODE_START_EDIT_FRAGMENT = 1;
    @BindView(R.id.imageList)
    XRecyclerViewEx imageList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    PictureSetsManageAdapter adapter;
    private BDLocation bdLocation;
    private String userId;
    private Dialog dialog;
    private PictureSet currentEditPictureSet = null;
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

    private void initDialog() {
        dialog = new Dialog(getActivity() , R.style.PauseDialog);
        dialog.setContentView(R.layout.dialog_edit_name);
        dialog.setTitle("自定义");
        // set the custom dialog components - text, image and button
//        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
        EditText inputName = (EditText) dialog.findViewById(R.id.inputName);

        PrimaryRadiusBtn cancleBtn = (PrimaryRadiusBtn) dialog.findViewById(R.id.cancleBtn);
        PrimaryRadiusBtn submitBtn = (PrimaryRadiusBtn) dialog.findViewById(R.id.submitBtn);
        // if button is clicked, close the custom dialog
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPresenter != null){
                    EditText inputName = (EditText) dialog.findViewById(R.id.inputName);
                    String name = inputName.getText().toString().trim();
                    if(TextUtils.isEmpty(name)){
                        showMessage("请输入名称");
                        return;
                    }


                    if(currentEditPictureSet != null &&
                            Objects.equals(name.trim() , currentEditPictureSet.getGymPictureSetName())){
                        return;
                    }


                    PictureSet editSet = new PictureSet();
                    editSet.setGymPictureSetName(name);
                    if(currentEditPictureSet != null){
                        editSet.setBackground(currentEditPictureSet.getBackground());
                        editSet.setCreateTime(currentEditPictureSet.getCreateTime());
                        editSet.setGymId(currentEditPictureSet.getGymId());
                        editSet.setId(currentEditPictureSet.getId());
                        editSet.setGymPictureSetSize(currentEditPictureSet.getGymPictureSetSize());
                    }else{
                        editSet.setCreateTime(Common.dateToString(new Date()));
                        editSet.setGymId(userId);
                    }
                    dialog.dismiss();
                    mPresenter.addSet(editSet);
                }
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK &&
        requestCode == RES_CODE_START_EDIT_FRAGMENT){
            initPictureSetsManageData();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolbarForActionbar(toolbar);
        initDialog();
        userId = this.getArguments().getString("id");
        adapter = new PictureSetsManageAdapter(new ArrayList<>());
        adapter.getPositionClicks().subscribe(new Consumer<PictureSet>() {
            @Override
            public void accept(PictureSet pictureSet) throws Exception {
                ISupportFragment fragment = PictureSetsEditFragment.newInstance(pictureSet);
                startForResult(fragment , RES_CODE_START_EDIT_FRAGMENT);
            }
        });
        adapter.getMenuClicks().subscribe(new Consumer<PictureSet>() {
            @Override
            public void accept(PictureSet pictureSet) throws Exception {
                currentEditPictureSet = pictureSet;
                EditText inputName = (EditText) dialog.findViewById(R.id.inputName);
                inputName.setText(pictureSet.getGymPictureSetName());
                dialog.show();
            }
        });
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
                } else {
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
            case R.id.add_icon:
                EditText inputName = (EditText) dialog.findViewById(R.id.inputName);
                inputName.setText("");
                dialog.show();
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
        if (imageList != null) {
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
        initPictureSetsManageData();
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
        if (imageList != null) {
            imageList.refreshComplete();
        }
        adapter.addAll(list);
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
