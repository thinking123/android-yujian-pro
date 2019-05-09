package com.yujian.mvp.ui.fragment.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

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

//    @BindView(R.id.friend_refresh_layout)
//    PtrClassicFrameLayout refreshLayout;

    @BindView(R.id.friend_list)
    XRecyclerView friendList;
    int pageNum = 1;
    int pages = 0;
    int total = 0;
    boolean isLoadingMore = false, isRefreshing = false;

    public static FriendFragment newInstance() {
        FriendFragment fragment = new FriendFragment();
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initFriendListData();
    }

    public void initFriendListData() {
        if (mPresenter != null) {
            friendListAdapter.clear();
            pageNum = 1;
            pages = 0;
            total = 0;
            mPresenter.goodFriendAllListHot();
            mPresenter.goodFriendAllList(
                    Integer.toString(pageNum),
                    Double.toString(GPSLocation.getInstance().getLongitude()),
                    Double.toString(GPSLocation.getInstance().getLatitude()),
                    "",
                    "",
                    "6",
                    User.getInstance().getId()
            );
        }
    }


    public void getFriendListData(int pageNum,
                                  String memberId,
                                  String name,
                                  String role) {
//        role = "6";
        if (mPresenter != null) {
            mPresenter.goodFriendAllList(
                    Integer.toString(pageNum),
                    Double.toString(GPSLocation.getInstance().getLongitude()),
                    Double.toString(GPSLocation.getInstance().getLatitude()),
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
        RecyclerView.LayoutManager layoutManagerBtnList = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horBtnList.setLayoutManager(layoutManagerBtnList);
        String[] btns = getResources().getStringArray(R.array.main_friend_btn_list);
        RecyclerViewHorizontalButtonListAdapter recyclerViewHorizontalButtonListAdapter = new RecyclerViewHorizontalButtonListAdapter(Arrays.asList(btns));
        recyclerViewHorizontalButtonListAdapter.getPositionClicks().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Timber.i("you click : " + s);
            }
        });
        horBtnList.setAdapter(recyclerViewHorizontalButtonListAdapter);


        initFriendList();
    }

    private FriendListAdapter friendListAdapter;

    private void initFriendList() {
        friendListAdapter = new FriendListAdapter(new ArrayList<Friend>());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        friendList.setLayoutManager(mLayoutManager);

        friendList.setRefreshProgressStyle(ProgressStyle.BallZigZag); //设定下拉刷新样式
        friendList.setLoadingMoreProgressStyle(ProgressStyle.BallZigZag);//设定上拉加载样式
//        friendList.setArrowImageView(R.drawable.qwe);

        friendList.setPullRefreshEnabled(true);

        friendList.setLoadingMoreEnabled(true);

        friendList.setAdapter(friendListAdapter);


        friendList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefreshing = true;
                initFriendListData();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        friendList.refreshComplete();
//                    }
//                } , 5000);

            }

            @Override
            public void onLoadMore() {

                if (pageNum < pages) {
                    isLoadingMore = true;
                    getFriendListData(pageNum + 1, "", "", "");
                } else {
                    showMessage("没有更多了");
                    friendList.loadMoreComplete();
                }

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getContext() , "loadmore" , Toast.LENGTH_LONG).show();
//                        friendList.loadMoreComplete();
//                    }
//                } , 5000);
            }
        });
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
        if (isRefreshing && friendList != null) {
            isRefreshing = false;
            friendList.refreshComplete();
        }
        friendListAdapter.addHeaderData(friends.list);
    }


    @Override
    public void goodFriendAllListResult(FriendBean friends) {

        if (isLoadingMore && friendList != null) {
            isLoadingMore = false;
            friendList.loadMoreComplete();
        }


        Timber.i("isLoadingMore : " + friends.list.size());

        List<Friend> list = friends.list;

        pageNum = Integer.parseInt(friends.getPageNum());
        pages = Integer.parseInt(friends.getPages());
        total = Integer.parseInt(friends.getTotal());


//        for(int i = 0 ; i < 10 ; i++){
//            list.add(list.get(0));
//        }

        friendListAdapter.addAll(list);

    }
}
