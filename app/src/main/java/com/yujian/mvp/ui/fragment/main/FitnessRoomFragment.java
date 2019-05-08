package com.yujian.mvp.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yujian.app.BaseApp;
import com.yujian.di.component.DaggerFitnessRoomComponent;
import com.yujian.mvp.contract.FitnessRoomContract;
import com.yujian.mvp.presenter.FitnessRoomPresenter;

import com.yujian.R;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import com.yujian.app.BaseSupportFragment;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

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
public class FitnessRoomFragment extends BaseSupportFragment<FitnessRoomPresenter> implements FitnessRoomContract.View {

    @BindView(R.id.fitnessroom_bd_map)
    MapView bdMap;
    BaiduMap mBaiduMap;
    public static FitnessRoomFragment newInstance() {
        FitnessRoomFragment fragment = new FitnessRoomFragment();
        return fragment;
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
        return inflater.inflate(R.layout.fragment_fitness_room, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBaiduMap = bdMap.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        BaseApp.getInstance().myListener.getBDLocation().subscribe(new Consumer<BDLocation>() {
            @Override
            public void accept(BDLocation location) throws Exception {
                if(location != null){
                    FitnessRoomFragment.this.bdLocation = location;

                    MyLocationData locData = new MyLocationData.Builder()
                            .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                            .direction(location.getDirection()).latitude(location.getLatitude())
                            .longitude(location.getLongitude()).build();
                    FitnessRoomFragment.this.mBaiduMap.setMyLocationData(locData);

                }
            }
        });
    }

    private BDLocation bdLocation;
    @Override
    public void onResume() {
        super.onResume();
        bdMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        bdMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bdMap.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
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
