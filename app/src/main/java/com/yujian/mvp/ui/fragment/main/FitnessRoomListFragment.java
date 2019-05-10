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

import com.baidu.location.BDLocation;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yujian.R;
import com.yujian.app.BaseApp;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerFitnessRoomComponent;
import com.yujian.entity.FitnessRoom;
import com.yujian.mvp.contract.FitnessRoomContract;
import com.yujian.mvp.model.entity.FitnessRoomBean;
import com.yujian.mvp.presenter.FitnessRoomPresenter;
import com.yujian.mvp.ui.adapter.FitnessRoomListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/10/2019 14:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FitnessRoomListFragment extends BaseSupportFragment<FitnessRoomPresenter> implements FitnessRoomContract.View {

    @BindView(R.id.fitness_room_list)
    XRecyclerView fitnessRoomList;

    private BDLocation bdLocation;
    private String nearBy = "30000000";
    private boolean isRefreshing = false;
    FitnessRoomListAdapter fitnessRoomListAdapter = null;

    public static FitnessRoomListFragment newInstance() {
        FitnessRoomListFragment fragment = new FitnessRoomListFragment();
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        BaseApp.getInstance().myListener.getBDLocation().take(1).subscribe(new Consumer<BDLocation>() {
            @Override
            public void accept(BDLocation location) throws Exception {
                if (location != null) {
                    FitnessRoomListFragment.this.bdLocation = location;
                    initFitnessRoomList();
                }
            }
        });
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFitnessRoomComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fitness_room_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        fitnessRoomListAdapter = new FitnessRoomListAdapter(new ArrayList<>());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        fitnessRoomList.setLayoutManager(layoutManager);

        fitnessRoomList.setRefreshProgressStyle(ProgressStyle.BallZigZag); //设定下拉刷新样式
        fitnessRoomList.setLoadingMoreProgressStyle(ProgressStyle.BallZigZag);//设定上拉加载样式
        fitnessRoomList.setPullRefreshEnabled(true);

        fitnessRoomList.setLoadingMoreEnabled(false);

        fitnessRoomList.setAdapter(fitnessRoomListAdapter);
        fitnessRoomList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefreshing = true;
                initFitnessRoomList();
            }

            @Override
            public void onLoadMore() {}
        });

        initFitnessRoomList();
    }

    private void initFitnessRoomList() {
        if (mPresenter != null && bdLocation != null) {
            fitnessRoomListAdapter.clear();
            mPresenter.getNearbyFitnessRoom(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    nearBy
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
    public void getNearbyFitnessRoomResult(FitnessRoomBean fitnessRoomBean) {
        if(isRefreshing && fitnessRoomList != null){
            isRefreshing = false;
            fitnessRoomList.refreshComplete();
        }

//        List<FitnessRoom> list = new ArrayList<>();
//        for(int i = 0 ; i < 10 ; i++){
//            list.addAll(fitnessRoomBean.getList());
//        }
        fitnessRoomListAdapter.addAll(fitnessRoomBean.getList());
//        fitnessRoomListAdapter.addAll(list);
    }
}
