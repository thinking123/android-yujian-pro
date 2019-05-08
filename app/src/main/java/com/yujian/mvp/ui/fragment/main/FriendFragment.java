package com.yujian.mvp.ui.fragment.main;

import android.content.Intent;
import android.graphics.Color;
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

import com.baidu.location.BDLocation;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yujian.app.BaseApp;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerFriendComponent;
import com.yujian.entity.Friend;
import com.yujian.entity.GPSLocation;
import com.yujian.entity.User;
import com.yujian.mvp.contract.FriendContract;
import com.yujian.mvp.model.entity.FriendBean;
import com.yujian.mvp.presenter.FriendPresenter;

import com.yujian.R;
import com.yujian.mvp.ui.adapter.FriendListAdapter;
import com.yujian.mvp.ui.adapter.RecyclerViewHorizontalButtonListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/06/2019 14:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FriendFragment extends BaseSupportFragment<FriendPresenter> implements FriendContract.View {

    @BindView(R.id.friend_top_btn_list)
    RecyclerView horBtnList;

    @BindView(R.id.friend_refresh_layout)
    PtrClassicFrameLayout refreshLayout;

    @BindView(R.id.friend_list)
    RecyclerView friendList;

    private int pageNum = 1, pages , total;
    public static FriendFragment newInstance() {
        FriendFragment fragment = new FriendFragment();
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

    private void getFriends(BDLocation location){
        if(mPresenter != null && location != null){
            mPresenter.goodFriendAllListHot();
            mPresenter.goodFriendAllList(
                    Integer.toString(pageNum),

                    Double.toString(location.getLongitude()),
                    Double.toString(location.getLatitude()),

//
//                    Double.toString(GPSLocation.getInstance().getLongitude()),
//                    Double.toString(GPSLocation.getInstance().getLatitude()),
                    "",
                    "",
                    "6",
                    User.getInstance().getId()
            );
        }
    }
    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFriendComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager layoutManagerBtnList = new LinearLayoutManager(getActivity() , LinearLayoutManager.HORIZONTAL , false);
        horBtnList.setLayoutManager(layoutManagerBtnList);
        String [] btns = getResources().getStringArray(R.array.main_friend_btn_list);
        RecyclerViewHorizontalButtonListAdapter recyclerViewHorizontalButtonListAdapter = new RecyclerViewHorizontalButtonListAdapter(Arrays.asList(btns));
        recyclerViewHorizontalButtonListAdapter.getPositionClicks().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Timber.i("you click : " + s);
            }
        });
        horBtnList.setAdapter(recyclerViewHorizontalButtonListAdapter);




//        MaterialHeader materialHeader = new MaterialHeader(getActivity());
//        materialHeader.setColorSchemeColors(new int[]{Color.RED, Color.GREEN, Color.BLUE});
//        refreshLayout.setHeaderView(materialHeader);
//        refreshLayout.addPtrUIHandler(materialHeader);

        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(getActivity());
        storeHouseHeader.setPadding(0,100,0,0);
        storeHouseHeader.setBackgroundColor(Color.BLACK);
        storeHouseHeader.setTextColor(Color.WHITE);
        storeHouseHeader.initWithString("haichenyi");//只可英文，中文不可运行(添加时间)
        refreshLayout.setHeaderView(storeHouseHeader);
        refreshLayout.addPtrUIHandler(storeHouseHeader);


        PtrClassicDefaultFooter ptrClassicDefaultFooter = new PtrClassicDefaultFooter(getActivity());
        refreshLayout.setFooterView(ptrClassicDefaultFooter);

//        refreshLayout.addPtrUIHandler(materialHeader);
        refreshLayout.addPtrUIHandler(ptrClassicDefaultFooter);

        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                frame.postDelayed(refreshLayout::refreshComplete, 2000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(refreshLayout::refreshComplete, 2000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, content, header);
//                return PtrDefaultHandler
            }
        });
        refreshLayout.setMode(PtrFrameLayout.Mode.LOAD_MORE);

        initFriendList();


        BaseApp.getInstance().myListener.getBDLocation().first(null).subscribe(new Consumer<BDLocation>() {
            @Override
            public void accept(BDLocation bdLocation) throws Exception {
                if(bdLocation != null){
                    FriendFragment.this.bdLocation = bdLocation;

                    FriendFragment.this.getFriends(bdLocation);

                }
            }
        });


    }
    private BDLocation bdLocation;
    private FriendListAdapter friendListAdapter;
    private void initFriendList(){
        friendListAdapter = new FriendListAdapter(new ArrayList<Friend>());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        friendList.setLayoutManager(mLayoutManager);

        friendList.setAdapter(friendListAdapter);
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
    public void goodFriendAllListHotResult(FriendBean friends) {
        Timber.i("cont : " + friends.list.size());
//        friendListAdapter.add(0 , friends.list);
        friendListAdapter.addHeaderData(friends.list);

//        if(mPresenter != null){
////            mPresenter.goodFriendAllListHot();
//            mPresenter.goodFriendAllList(
//                    Integer.toString(pageNum),
//                    Double.toString(GPSLocation.getInstance().getLongitude()),
//                    Double.toString(GPSLocation.getInstance().getLatitude()),
//                    "",
//                    "",
//                    "6",
//                    User.getInstance().getId()
//            );
//        }
    }

    @Override
    public void goodFriendAllListResult(FriendBean friends) {
        Timber.i("cont : " + friends.list.size());
        friendListAdapter.addAll(0 , friends.list);
    }
}
