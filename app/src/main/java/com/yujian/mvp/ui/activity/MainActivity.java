package com.yujian.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yujian.app.BaseSupportActivity;
import com.yujian.di.component.DaggerMainComponent;
import com.yujian.mvp.contract.MainContract;
import com.yujian.mvp.presenter.MainPresenter;

import com.yujian.R;
import com.yujian.mvp.ui.fragment.main.DynamicFragment;
import com.yujian.mvp.ui.fragment.main.FitnessRoomFragment;
import com.yujian.mvp.ui.fragment.main.FriendFragment;
import com.yujian.mvp.ui.fragment.main.HomeFragment;
import com.yujian.mvp.ui.fragment.main.MyFragment;
import com.yujian.utils.GPSUtils;


import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.yokeyword.fragmentation.ISupportFragment;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 19:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseSupportActivity<MainPresenter> implements MainContract.View {


    @BindView(R.id.home_nav)
    BottomNavigationView navigationView;

    private ISupportFragment[] mFragments = new ISupportFragment[5];

//    private final LocationListener mLocationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(final Location location) {
//            //your code here
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//    };
//
//    private  LocationManager mLocationManager ;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        requestPermissions();
        initNav();
    }

    private void initFragment() {
        ISupportFragment home = findFragment(HomeFragment.class);
        if (home == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = FriendFragment.newInstance();
            mFragments[2] = FitnessRoomFragment.newInstance();
            mFragments[3] = DynamicFragment.newInstance();
            mFragments[4] = MyFragment.newInstance();
            loadMultipleRootFragment(R.id.home_container, 0, mFragments);
        } else {
            mFragments[0] = findFragment(HomeFragment.class);
            mFragments[1] = findFragment(FriendFragment.class);
            mFragments[2] = findFragment(FitnessRoomFragment.class);
            mFragments[3] = findFragment(DynamicFragment.class);
            mFragments[4] = findFragment(MyFragment.class);
        }
    }

    private void initNav() {
        initFragment();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main_nav_home:
                        showHideFragment(mFragments[0]);
                        return true;
                    case R.id.main_nav_friend:
                        showHideFragment(mFragments[1]);
                        return true;
                    case R.id.main_nav_fitnessroom:
                        showHideFragment(mFragments[2]);
                        return true;
                    case R.id.main_nav_dynamic:
                        showHideFragment(mFragments[3]);
                        return true;
                    case R.id.main_nav_my:
                        showHideFragment(mFragments[4]);
                        return true;
                }
                return false;
            }
        });
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .request(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
//                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(granted -> {
                    if (granted) {
                        Timber.i("grant loaton");
                        GPSUtils.getInstance(this).getLngAndLat(new GPSUtils.OnLocationResultListener() {
                            @Override
                            public void onLocationResult(Location location) {
                                String str = String.format("l : %f , r : %f" , location.getLatitude() , location.getLongitude());

                                showMessage(str);
                            }

                            @Override
                            public void OnLocationChange(Location location) {
                                String str = String.format("l : %f , r : %f" , location.getLatitude() , location.getLongitude());

                                showMessage(str);
                            }
                        });
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Timber.e("%s is denied.", "location");
                    }
                } , error -> {
                    Timber.e(error.getMessage());
                });


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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void post(Runnable runnable) {

    }
}
