package com.yujian.mvp.ui.fragment.userProfile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yujian.R;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerUserProfileComponent;
import com.yujian.mvp.contract.UserProfileContract;
import com.yujian.mvp.presenter.UserProfilePresenter;
import com.yujian.mvp.ui.adapter.UserProfileMainViewPagerAdapter;

import java.util.Arrays;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/11/2019 14:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserProfileMainFragment extends BaseSupportFragment<UserProfilePresenter> implements UserProfileContract.View {

    @BindView(R.id.viewpager_tablayout)
    public TabLayout tabLayout;
    @BindView(R.id.viewpager)
    public ViewPager viewPager;
    public static UserProfileMainFragment newInstance(String userId) {
        UserProfileMainFragment fragment = new UserProfileMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id" , userId);
        fragment.setArguments(bundle);
        return fragment;
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

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String[] tabs = getResources().getStringArray(R.array.user_profile_tab);
        UserProfileMainViewPagerAdapter viewPagerAdapter = new UserProfileMainViewPagerAdapter(getChildFragmentManager() , Arrays.asList(tabs));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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
