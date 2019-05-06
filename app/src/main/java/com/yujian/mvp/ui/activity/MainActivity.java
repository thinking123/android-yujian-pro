package com.yujian.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

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


import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

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
        initNav();
    }

    private void initFragment(){
        ISupportFragment home = findFragment(HomeFragment.class);
        if(home == null){
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = FriendFragment.newInstance();
            mFragments[2] = FitnessRoomFragment.newInstance();
            mFragments[3] = DynamicFragment.newInstance();
            mFragments[4] = MyFragment.newInstance();
            loadMultipleRootFragment(R.id.home_container , 0 , mFragments);
        }else{
            mFragments[0] = findFragment(HomeFragment.class);
            mFragments[1] = findFragment(FriendFragment.class);
            mFragments[2] = findFragment(FitnessRoomFragment.class);
            mFragments[3] = findFragment(DynamicFragment.class);
            mFragments[4] = findFragment(MyFragment.class);
        }
    }

    private void initNav(){
        initFragment();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
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
        finish();
    }

    @Override
    public void post(Runnable runnable) {

    }
}
