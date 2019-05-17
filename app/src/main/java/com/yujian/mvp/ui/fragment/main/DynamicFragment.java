package com.yujian.mvp.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.baidu.location.BDLocation;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yujian.app.BaseApp;
import com.yujian.di.component.DaggerDynamicComponent;
import com.yujian.entity.Dynamic;
import com.yujian.mvp.contract.DynamicContract;
import com.yujian.mvp.presenter.DynamicPresenter;

import com.yujian.R;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import com.yujian.app.BaseSupportFragment;
import com.yujian.mvp.ui.EventBus.EventBusTags;
import com.yujian.mvp.ui.adapter.RecyclerViewHorizontalButtonListAdapter;
import com.yujian.mvp.ui.adapter.TopicListAdapter;
import com.yujian.mvp.ui.adapter.UserDynamicListAdapter;
import com.yujian.mvp.ui.adapter.UserTopicDynamicListAdapter;
import com.yujian.utils.Common;
import com.yujian.utils.entity.ClickObj;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/06/2019 14:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DynamicFragment extends BaseSupportFragment<DynamicPresenter> implements DynamicContract.View {
    @BindView(R.id.dynamic_top_btn_list)
    RecyclerView horBtnList;


    @BindView(R.id.dynamicList)
    XRecyclerView dynamicList;
    @BindView(R.id.gotoInfo)
    ImageButton gotoInfo;
    int pageNum = 1;
    int pages = 0;
    int total = 0;
    private String userRole = "0";
    private BDLocation bdLocation;
    private UserTopicDynamicListAdapter userTopicDynamicListAdapter;

    public static DynamicFragment newInstance() {
        DynamicFragment fragment = new DynamicFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDynamicComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic, container, false);
    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initTopBtns();
        BaseApp.getInstance().myListener.getBDLocation().take(1).subscribe(new Consumer<BDLocation>() {
            @Override
            public void accept(BDLocation location) throws Exception {
                if(location != null){

                    DynamicFragment.this.bdLocation = location;
                    init();
                }
            }
        });
    }
    private void initTopBtns(){
        RecyclerView.LayoutManager layoutManagerBtnList = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horBtnList.setLayoutManager(layoutManagerBtnList);
        String[] btns = getResources().getStringArray(R.array.dynamic_btn_list);
        RecyclerViewHorizontalButtonListAdapter recyclerViewHorizontalButtonListAdapter = new RecyclerViewHorizontalButtonListAdapter(Arrays.asList(btns));
        recyclerViewHorizontalButtonListAdapter.getPositionClicks().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                int pos = 0;
                for(String btn : btns){
                    if(Common.equalStr(btn , s)){
                        break;
                    }
                    pos++;
                }


                userRole = Integer.toString(pos);
                init();
            }
        });
        horBtnList.setAdapter(recyclerViewHorizontalButtonListAdapter);
    }
    private void initXRecyclerView() {
        userTopicDynamicListAdapter = new UserTopicDynamicListAdapter(new ArrayList<Dynamic>() ,
                this);

        userTopicDynamicListAdapter.getPositionClicks().subscribe(new Consumer<ClickObj>() {
            @Override
            public void accept(ClickObj clickObj) throws Exception {
                String id = clickObj.getId();
                Dynamic dynamic = (Dynamic)clickObj.getTarget();
                switch (clickObj.getType()){
                    case EventBusTags.AdapterClickable.UserDynamicListAdapter.PRAISECOUNTICON:
                        if(Common.equalStr(dynamic.getIsPraise() , "1")){

                        }else{

                        }
                        break;
                }
            }
        });


        dynamicList.setLayoutManager(new LinearLayoutManager(getActivity()));

        dynamicList.setRefreshProgressStyle(ProgressStyle.BallZigZag); //设定下拉刷新样式
        dynamicList.setLoadingMoreProgressStyle(ProgressStyle.BallZigZag);//设定上拉加载样式
//        friendList.setArrowImageView(R.drawable.qwe);

        dynamicList.setPullRefreshEnabled(true);

        dynamicList.setLoadingMoreEnabled(true);

        dynamicList.setAdapter(userTopicDynamicListAdapter);


        dynamicList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
//                getMood(pageNum + 1);
                if (pageNum < pages) {
//                    getMood(pageNum + 1);
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
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    private void init(){
        pageNum = 1;
        pages = 0;
        total = 0;
        getMood(pageNum);
        getTopicListByUserId();
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
}
