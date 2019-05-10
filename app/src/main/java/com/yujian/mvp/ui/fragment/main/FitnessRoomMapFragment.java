package com.yujian.mvp.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yujian.R;
import com.yujian.app.BaseApp;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerFitnessRoomComponent;
import com.yujian.mvp.contract.FitnessRoomContract;
import com.yujian.mvp.model.entity.FitnessRoomBean;
import com.yujian.mvp.presenter.FitnessRoomPresenter;

import butterknife.BindView;
import butterknife.OnClick;
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
public class FitnessRoomMapFragment extends BaseSupportFragment<FitnessRoomPresenter> implements FitnessRoomContract.View {
    //    @BindView(R.id.fitnessroom_bd_map)
    MapView bdMap;
    BaiduMap mBaiduMap;

    @BindView(R.id.location_btn)
    ImageButton locationBtn;
//    @BindView(R.id.fitnessroom_bd_map_fragment)
//    SupportMapFragment supportMapFragment;

    private boolean isFirstLocation = true;
    public static FitnessRoomMapFragment newInstance() {
        FitnessRoomMapFragment fragment = new FitnessRoomMapFragment();
        return fragment;
    }

    @OnClick({R.id.location_btn})
    public void onClick(View view){
        updataLocation(bdLocation);
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
        return inflater.inflate(R.layout.fragment_fitness_room_map, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        SupportMapFragment supportMapFragment = (SupportMapFragment) (getChildFragmentManager()
                .findFragmentById(R.id.fitnessroom_bd_map));
        bdMap = supportMapFragment.getMapView();

        bdMap.showScaleControl(false);
        bdMap.showZoomControls(false);
//        mBaiduMap = bdMap.getMap();
        mBaiduMap = supportMapFragment.getBaiduMap();
        mBaiduMap.setMyLocationEnabled(true);


        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

//        LatLng GEO_SHANGHAI = new LatLng(31.227, 121.481);
//        MapStatusUpdate status1 = MapStatusUpdateFactory.newLatLng(GEO_SHANGHAI);
//        mBaiduMap.setMapStatus(status1);
//
//
//
//        bdMap.setLogoPosition(LogoPosition.logoPostionleftTop);
        MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL,
                false,
                BitmapDescriptorFactory.fromResource(R.drawable.bd_location_dot),
                ContextCompat.getColor(getActivity() , R.color.bd_location_border ),
                ContextCompat.getColor(getActivity() , R.color.bd_location_border )
        );

        mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);


        BaseApp.getInstance().myListener.getBDLocation().subscribe(new Consumer<BDLocation>() {
            @Override
            public void accept(BDLocation location) throws Exception {
                if(location != null && mBaiduMap != null && isFirstLocation){
                    isFirstLocation = false;

                    FitnessRoomMapFragment.this.bdLocation = location;

                    updataLocation(location);
//                    MyLocationData locData = new MyLocationData.Builder()
//                            .accuracy(location.getRadius())
//                            // 此处设置开发者获取到的方向信息，顺时针0-360
//                            .direction(location.getDirection()).latitude(location.getLatitude())
//                            .longitude(location.getLongitude()).build();
//                    mBaiduMap.setMyLocationData(locData);
//
//
//                    LatLng ll = new LatLng(location.getLatitude(),
//                            location.getLongitude());
//
//                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
//
//                    mBaiduMap.animateMapStatus(update);

                }
            }
        });
    }

    private void updataLocation(BDLocation location){
        isFirstLocation = false;

//        FitnessRoomFragment.this.bdLocation = location;

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.getDirection()).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);


        LatLng ll = new LatLng(location.getLatitude(),
                location.getLongitude());

        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);

        mBaiduMap.animateMapStatus(update);
    }
    private BDLocation bdLocation;
    @Override
    public void onResume() {
        bdMap.onResume();
        super.onResume();

    }

    @Override
    public void onPause() {
        bdMap.onPause();
        super.onPause();

    }

    @Override
    public void onDestroy() {

        mBaiduMap.setMyLocationEnabled(false);
        bdMap.onDestroy();

        bdMap = null;
        super.onDestroy();

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

    }

    @Override
    public void attentionResult() {

    }
}
