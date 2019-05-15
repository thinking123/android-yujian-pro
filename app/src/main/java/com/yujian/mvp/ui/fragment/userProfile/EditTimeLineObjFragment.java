package com.yujian.mvp.ui.fragment.userProfile;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.previewlibrary.GPreviewBuilder;
import com.yujian.R;
import com.yujian.app.BaseApp;
import com.yujian.app.BaseSupportFragment;
import com.yujian.app.utils.matisse.MyGlideEngine;
import com.yujian.di.component.DaggerUserProfileComponent;
import com.yujian.entity.DrillTime;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.UserProfile;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.mvp.contract.UserProfileContract;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;
import com.yujian.mvp.model.entity.GymPictureBean;
import com.yujian.mvp.presenter.UserProfilePresenter;
import com.yujian.mvp.ui.EventBus.EventBusTags;
import com.yujian.mvp.ui.adapter.ImageListSelectAdapter;
import com.yujian.utils.Constant;
import com.yujian.widget.PrimaryRadiusBtn;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/14/2019 17:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class EditTimeLineObjFragment extends BaseSupportFragment<UserProfilePresenter> implements UserProfileContract.View {
    public final int REQUEST_CODE_CHOOSE = 1;
    private String id;
    private String type;
    private BDLocation bdLocation;
    private boolean isEdit = false;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetitle)
    TextView pagetitle;
    @BindView(R.id.twoLineTip)
    TextView twoLineTip;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.imageList)
    RecyclerView imageList;
    @BindView(R.id.submit)
    PrimaryRadiusBtn submit;
    ImageListSelectAdapter adapter;
    TimePickerView pvTime;

    private PublishSubject<Void> submitSubject = PublishSubject.create();
    public static EditTimeLineObjFragment newInstance(String id,String type) {
        EditTimeLineObjFragment fragment = new EditTimeLineObjFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("type", type);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                _mActivity.onBackPressed();
                break;
        }


        return true;
    }
    @OnClick({R.id.timepicker , R.id.submit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.timepicker:
                pvTime.show();
                break;
            case R.id.submit:
                submitSubject.debounce(500 , TimeUnit.MILLISECONDS);
                break;
        }
    }

    private void submit(){

    }
    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_edit_time_line_obj, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK){
            List<String> uris = Matisse.obtainPathResult(data);
//            List<String> urls = new ArrayList<>();
//            for(Uri uri : uris){
//                urls.add(uris.toString());
//            }

            if(mPresenter != null){
                mPresenter.upLoadImages(uris);
            }

//            adapter.addAll(urls);
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolbarForActionbar(toolbar);

        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                Constant.Common.DAYDATEPATTERN;
                DateFormat df = new SimpleDateFormat(Constant.Common.DAYDATEPATTERN);

                time.setText(df.format(date));

                time.setTextColor(ContextCompat.getColor(getActivity() , R.color.text_black));
            }
        }).build();

        id = this.getArguments().getString("id");
        type = this.getArguments().getString("type");

        String titleStr = "" , _twoLineTip = "";
        switch (type){
            case EventBusTags.UserProfile.ADDMATCH:
                titleStr = getResources().getString(R.string.main_timeline_title2);
                break;
            case EventBusTags.UserProfile.ADDCERTIFICATE:
                titleStr = getResources().getString(R.string.main_timeline_title1);
                break;
            case EventBusTags.UserProfile.ADDPERSONALSTORY:
                break;
        }

        _twoLineTip = String.format("%s时间" , titleStr);
        twoLineTip.setText(_twoLineTip);
        pagetitle.setText(titleStr);

        adapter = new ImageListSelectAdapter(new ArrayList<>());
        adapter.getAddClicks().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Matisse.from(EditTimeLineObjFragment.this)
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

        adapter.getPositionClicks().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
//                int p = pos;


                GPreviewBuilder.from(getActivity())//activity实例必须
//                        .to(CustomActivity.class)//自定义Activity 使用默认的预览不需要
                        .setData(adapter.getPreviewImage())//集合
//                        .setUserFragment(ZoomPreviewFragment.class)//自定义Fragment 使用默认的预览不需要
                        .setCurrentIndex(integer)
                        .setSingleFling(false)//是否在黑屏区域点击返回
                        .setDrag(false)//是否禁用图片拖拽返回
                        .setType(GPreviewBuilder.IndicatorType.Number)//指示器类型
                        .start();//启动
            }
        });

        adapter.getDelClicks().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                adapter.remove(integer);
            }
        });
        imageList.setAdapter(adapter);
        imageList.setLayoutManager(new GridLayoutManager(getActivity() , 3));


        int gridSpace = getResources().getDimensionPixelSize(R.dimen.grid_space);

        imageList.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int pos = parent.getChildLayoutPosition(view);
//                if(pos < 3){
//                    outRect.top =
//
//                }
                if(pos % 3 == 0){
                    outRect.right = gridSpace;
                    outRect.bottom = gridSpace;
                }else if (pos % 3 == 1) {
                    outRect.right = gridSpace;
                    outRect.bottom = gridSpace;
                    outRect.left = gridSpace;
                }else{
                    outRect.bottom = gridSpace;
                    outRect.left = gridSpace;
                }

            }
        });

        isEdit = !TextUtils.isEmpty(id);
        if(isEdit){
            BaseApp.getInstance().myListener.getBDLocation().take(1).subscribe(new Consumer<BDLocation>() {
                @Override
                public void accept(BDLocation location) throws Exception {
                    if(location != null ){

                        EditTimeLineObjFragment.this.bdLocation = location;
                        initTimelineObj();
                    }
                }
            });
        }else{

        }



    }

    private void initTimelineObj(){
        if (mPresenter != null && bdLocation != null) {
            mPresenter.getMsgByIdToEdit(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    id
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
        adapter.addAll(urls);
    }

    @Override
    public void addCoachCredentialsResult(String res) {

    }

    @Override
    public void delCoachCredentialsResult(String res) {

    }

    @Override
    public void getMsgByIdToEditResult(UserProfileMatchCertificatePersonalStory res) {
        Timber.i("UserProfileMatchCertificatePersonalStory");
    }

    @Override
    public void getMsgByTypeResult(List<UserProfileMatchCertificatePersonalStory> res) {

    }
}
