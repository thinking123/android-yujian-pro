package com.yujian.mvp.ui.fragment.userProfile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.jess.arms.base.BaseFragment;
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
import com.yujian.mvp.ui.EventBus.EventBusTags;
import com.yujian.mvp.ui.EventBus.PictureSetEvent;
import com.yujian.mvp.ui.EventBus.UserProfileEvent;
import com.yujian.mvp.ui.adapter.CardListAdapter;
import com.yujian.mvp.ui.adapter.CertificateListAdapter;
import com.yujian.mvp.ui.adapter.PictureSetsAdapter;
import com.yujian.utils.Common;
import com.yujian.widget.GridSpacesItemDecoration;
import com.yujian.widget.HorizontalScrollTagList;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/11/2019 14:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserProfileFragment extends BaseSupportFragment<UserProfilePresenter> implements UserProfileContract.View {
    private UserProfile userProfile;

    @BindView(R.id.tags)
    HorizontalScrollTagList tagList;


    @BindView(R.id.introduce)
    TextView introduce;
    @BindView(R.id.addressInfo)
    TextView addressInfo;

    @BindView(R.id.certificateList)
    RecyclerView certificateList;

    @BindView(R.id.matchList)
    RecyclerView matchList;

    @BindView(R.id.cardLists)
    RecyclerView cardLists;

    @BindView(R.id.pictureSets)
    RecyclerView pictureSets;


    @BindView(R.id.goto_introduce)
    TextView getIntroduce;
    MapView bdMap;
    BaiduMap mBaiduMap;

    private BDLocation bdLocation;
    private boolean isFirstLocation = true;

    public static UserProfileFragment newInstance(UserProfile userProfile) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userProfile", userProfile);
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick({R.id.goto_introduce,
    R.id.goto_certificateList,
    R.id.goto_matchList,
    R.id.goto_cardLists,
    R.id.goto_pictureSets,
    })
    public void onClick(View view){
        switch (view.getId()){
            case R.id.goto_introduce:
                EventBus.getDefault().post(new UserProfileEvent(userProfile ,
                        EventBusTags.UserProfile.GOTOINTRODUCE,
                        null));
                break;
            case R.id.goto_certificateList:
                EventBus.getDefault().post(new UserProfileEvent(userProfile ,
                        EventBusTags.UserProfile.CERTIFICATE,
                        null));
                break;
            case R.id.goto_matchList:
                EventBus.getDefault().post(new UserProfileEvent(userProfile ,
                        EventBusTags.UserProfile.MATCH,
                        null));
                break;
            case R.id.goto_pictureSets:
                EventBus.getDefault().post(new UserProfileEvent(userProfile ,
                        EventBusTags.UserProfile.GOTOPICTURESETS,
                        null));
                break;
        }
    }
    private void initMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) (getChildFragmentManager()
                .findFragmentById(R.id.addressMap));

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
                ContextCompat.getColor(getActivity(), R.color.bd_location_border),
                ContextCompat.getColor(getActivity(), R.color.bd_location_border)
        );

        mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);


        BaseApp.getInstance().myListener.getBDLocation().take(1).subscribe(new Consumer<BDLocation>() {
            @Override
            public void accept(BDLocation location) throws Exception {
                if (location != null && mBaiduMap != null && isFirstLocation) {
                    isFirstLocation = false;

                    UserProfileFragment.this.bdLocation = location;

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

    private void updataLocation(BDLocation location) {
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

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerUserProfileComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    private void initCertificateMatchList() {


        int gridSpace = getResources().getDimensionPixelSize(R.dimen.grid_space);
        GridSpacesItemDecoration decoration = new GridSpacesItemDecoration(gridSpace);
        certificateList.addItemDecoration(decoration);
        certificateList.setLayoutManager(new GridLayoutManager(
                getActivity(),
                2
        ));


        CertificateListAdapter adapter = new CertificateListAdapter(userProfile.getCertificateList());

        certificateList.setAdapter(adapter);

        adapter.getPositionClicks().subscribe(new Consumer<UserProfileMatchCertificatePersonalStory>() {
            @Override
            public void accept(UserProfileMatchCertificatePersonalStory userProfileMatchCertificatePersonalStory) throws Exception {
                EventBus.getDefault().post(new UserProfileEvent(userProfile, EventBusTags.UserProfile.CERTIFICATE, null));
            }
        });

        matchList.addItemDecoration(decoration);
        matchList.setLayoutManager(new GridLayoutManager(
                getActivity(),
                2
        ));


        CertificateListAdapter adapter1 = new CertificateListAdapter(userProfile.getMatchList());

        matchList.setAdapter(adapter1);
        adapter1.getPositionClicks().subscribe(new Consumer<UserProfileMatchCertificatePersonalStory>() {
            @Override
            public void accept(UserProfileMatchCertificatePersonalStory userProfileMatchCertificatePersonalStory) throws Exception {
                EventBus.getDefault().post(new UserProfileEvent(userProfile, EventBusTags.UserProfile.MATCH, null));
            }
        });

        pictureSets.addItemDecoration(decoration);
        pictureSets.setLayoutManager(new GridLayoutManager(
                getActivity(),
                2
        ));

        PictureSetsAdapter adapter2 = new PictureSetsAdapter(userProfile.getPictureSets());

        pictureSets.setAdapter(adapter2);

        adapter2.getPositionClicks().subscribe(new Consumer<PictureSet>() {
            @Override
            public void accept(PictureSet pictureSet) throws Exception {
                EventBus.getDefault().post(new UserProfileEvent(userProfile, EventBusTags.UserProfile.PICTURESET, pictureSet));
            }
        });

    }


    private void initCardLists() {


        cardLists.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        CardListAdapter adapter = new CardListAdapter(userProfile.getCardLists());

        cardLists.setAdapter(adapter);


    }


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        userProfile = (UserProfile) savedInstanceState.getSerializable("userProfile");


        userProfile = (UserProfile) this.getArguments().getSerializable("userProfile");

        tagList.setTags(Common.splitStringToList(userProfile.getLabelList(), ""));

        introduce.setText(userProfile.getIntroduce());


        addressInfo.setText(userProfile.getAddress());
        initCertificateMatchList();
        initCardLists();

        initMap();
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
}
