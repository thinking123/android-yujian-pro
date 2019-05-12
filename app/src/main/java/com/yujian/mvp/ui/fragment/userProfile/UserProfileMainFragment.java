package com.yujian.mvp.ui.fragment.userProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yujian.R;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerUserProfileComponent;
import com.yujian.entity.DrillTime;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.UserProfile;
import com.yujian.mvp.contract.UserProfileContract;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;
import com.yujian.mvp.presenter.UserProfilePresenter;
import com.yujian.mvp.ui.adapter.UserProfileMainViewPagerAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

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

    @BindView(R.id.nested_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.user_profile_main_container)
    CoordinatorLayout constraintLayout;
    private String userId;
    @BindView(R.id.viewpager_tablayout)
    public TabLayout tabLayout;
    @BindView(R.id.viewpager)
    public ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.visitNum)
    TextView visitNum;

    @BindView(R.id.fansNum)
    TextView fansNum;

    @BindView(R.id.collectionNum)
    TextView collectionNum;


    @BindView(R.id.header_title)
    TextView headerTitle;

    @BindView(R.id.header_time)
    TextView headerTime;

    @BindView(R.id.logo)
    CircleImageView logo;

    @BindView(R.id.header_bg)
    ImageView headerBg;

//    @BindView(R.id.tags)
//    TagCloudView tags;
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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_user_profile_main, container, false);
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


        userId = this.getArguments().getString("id");
        getUserProfile();
//        userId = savedInstanceState.getString("id");
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_profile_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.heart_icon:

                break;
            case R.id.relay_icon:

                break;
            case R.id.edit_icon:

                break;
        }


        return true;
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void getUserProfile(){
        if(mPresenter != null){
            mPresenter.getUserProfile(userId);
        }
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
    public void getUserProfileResult(UserProfile p) {
        String[] tabs = getResources().getStringArray(R.array.user_profile_tab);
        UserProfileMainViewPagerAdapter viewPagerAdapter = new UserProfileMainViewPagerAdapter(getChildFragmentManager() , Arrays.asList(tabs) , p);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setOffscreenPageLimit(2);
        visitNum.setText(p.getVisitNum());
        fansNum.setText(p.getFansNum());
        collectionNum.setText(p.getCollectionNum());
        headerTitle.setText(p.getName());
        headerTime.setText(p.getOpenTime());

        Glide.with(getActivity()).load(p.getHead()).into(logo);
        Glide.with(getActivity()).load(p.getLogo()).into(headerBg);

        constraintLayout.invalidate();

//
//        List<String> labelList = Arrays.asList(p.getLabelList().split(","));
//
//        tags.setTags(labelList);
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
}
