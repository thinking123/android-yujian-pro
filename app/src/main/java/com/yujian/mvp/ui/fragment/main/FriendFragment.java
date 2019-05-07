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

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerFriendComponent;
import com.yujian.entity.Friend;
import com.yujian.mvp.contract.FriendContract;
import com.yujian.mvp.presenter.FriendPresenter;

import com.yujian.R;
import com.yujian.mvp.ui.adapter.RecyclerViewHorizontalButtonListAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
//import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
//import in.srain.cube.views.ptr.PtrClassicFrameLayout;
//import in.srain.cube.views.ptr.PtrDefaultHandler2;
//import in.srain.cube.views.ptr.PtrFrameLayout;
//import in.srain.cube.views.ptr.header.MaterialHeader;
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

//    @BindView(R.id.friend_list)
//    RecyclerView friendList;

    public static FriendFragment newInstance() {
        FriendFragment fragment = new FriendFragment();
        return fragment;
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


        if(mPresenter != null){
            mPresenter.goodFriendAllListHot();
        }

//        MaterialHeader materialHeader = new MaterialHeader(getActivity());
//        materialHeader.setColorSchemeColors(new int[]{Color.RED, Color.GREEN, Color.BLUE});
//        refreshLayout.setHeaderView(materialHeader);
//        refreshLayout.addPtrUIHandler(materialHeader);
//
//
//        PtrClassicDefaultFooter ptrClassicDefaultFooter = new PtrClassicDefaultFooter(getActivity());
//        refreshLayout.setFooterView(ptrClassicDefaultFooter);
//
//        refreshLayout.addPtrUIHandler(materialHeader);
//        refreshLayout.addPtrUIHandler(ptrClassicDefaultFooter);
//
//        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
//            @Override
//            public void onLoadMoreBegin(PtrFrameLayout frame) {
//                frame.postDelayed(refreshLayout::refreshComplete, 2000);
//            }
//
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                frame.postDelayed(refreshLayout::refreshComplete, 2000);
//            }
//        });
//        refreshLayout.setMode(PtrFrameLayout.Mode.LOAD_MORE);
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
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
    public void goodFriendAllListHotResult(List<Friend> friends) {
        Timber.i("cont : " + friends.size());
    }

    @Override
    public void goodFriendAllListResult(List<Friend> friends) {

    }
}
