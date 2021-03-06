package com.yujian.mvp.ui.fragment.userProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.entity.AttationCurriculum;
import com.yujian.entity.FeedbackInfo;
import com.yujian.entity.GymPicture;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yujian.R;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerUserProfileComponent;
import com.yujian.entity.DrillTime;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.PictureSet;
import com.yujian.entity.Topic;
import com.yujian.entity.User;
import com.yujian.entity.UserProfile;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.mvp.contract.UserProfileContract;
import com.yujian.mvp.model.entity.DynamicTopicBean;
import com.yujian.mvp.model.entity.FeedbackInfoBean;
import com.yujian.mvp.model.entity.FollowUserBean;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;
import com.yujian.mvp.model.entity.GymPictureBean;
import com.yujian.mvp.presenter.UserProfilePresenter;
import com.yujian.mvp.ui.EventBus.EventBusTags;
import com.yujian.mvp.ui.EventBus.UserProfileEvent;
import com.yujian.mvp.ui.adapter.UserProfileMainViewPagerAdapter;
import com.yujian.mvp.ui.fragment.main.DynamicFragment;
import com.yujian.widget.FloatingActionImageView;
import com.yujian.widget.ImagePreviewImage;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.ISupportFragment;

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
//    @BindView(R.id.viewpager)
//    public ViewPager viewPager;


    private UserProfile userProfile;
    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.viewpager_container)
    FrameLayout frameLayout;
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

//    @BindView(R.id.logo)
//    CircleImageView logo;

    @BindView(R.id.logo)
    FloatingActionImageView logo;


    @BindView(R.id.header_bg)
    ImageView headerBg;


    private ISupportFragment[] mFragments = new ISupportFragment[3];

    //    @BindView(R.id.tags)
//    TagCloudView tags;
    private String type;
    public static UserProfileMainFragment newInstance(String userId , String type) {
        UserProfileMainFragment fragment = new UserProfileMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", userId);
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        EventBus.getDefault().unregister(this);
//    }


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
        type = this.getArguments().getString("type");
        getUserProfile();
//        userId = savedInstanceState.getString("id");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(Objects.equals(type , "1")){
            inflater.inflate(R.menu.user_profile_main_fitness_room_menu, menu);
        }else{
            inflater.inflate(R.menu.user_profile_main_menu, menu);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void follow() {
        if (userProfile != null && mPresenter != null) {
            if (Objects.equals(userProfile.getIsCollect(), "1")) {
                mPresenter.unfollow(User.getInstance().getId());
            } else {
                mPresenter.attention(userProfile.getUserRole() , User.getInstance().getId());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.heart_icon:
                follow();
                break;
            case R.id.relay_icon:

                break;
            case R.id.edit_icon:
                EventBus.getDefault().post(new UserProfileEvent( userProfile , EventBusTags.UserProfile.GOTOADDFEEDBACK , null));
                break;
            case android.R.id.home:
                _mActivity.onBackPressed();
                break;
        }


        return true;
    }

    @OnClick({R.id.logo  , R.id.collectionNum, R.id.fansNum})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logo:
                if (userProfile != null &&
                        !TextUtils.isEmpty(userProfile.getHead())) {
                    ImagePreviewImage.previewImage(getActivity(), userProfile.getHead());
                }

                break;
            case R.id.collectionNum:
               EventBus.getDefault().post(new UserProfileEvent(userProfile , EventBusTags.UserProfile.GOTOVISIT , null));
                break;
            case R.id.fansNum:
                EventBus.getDefault().post(new UserProfileEvent(userProfile , EventBusTags.UserProfile.GOTOFANS , null));
                break;
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

//        toolbar.setTitle("");

        initAppbarLayout();

        if (mPresenter != null) {
            mPresenter.addVisitNum(User.getInstance().getId());
        }

    }

    private void initAppbarLayout() {
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Collapsed);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Expanded);

        //This is the most important when you are putting custom textview in CollapsingToolbar
        collapsingToolbar.setTitle(" ");

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //when collapsingToolbar at that time display actionbar title
                    if (userProfile != null) {
                        collapsingToolbar.setTitle(userProfile.getName());
                    } else {
                        collapsingToolbar.setTitle(" ");
                    }

//                    toolbar.setBackgroundColor(ContextCompat.getColor(
//                            getActivity() , R.color.white
//                    ));
//
//                    toolbar.setTitleTextColor(ContextCompat.getColor(
//                            getActivity() , R.color.text_black
//                    ));

                    isShow = true;
                } else if (isShow) {
                    //carefull there must a space between double quote otherwise it dose't work
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
                float range = (float) -appBarLayout.getTotalScrollRange();
                headerBg.setImageAlpha((int) (255 * (1.0f - (float) verticalOffset / range)));


            }
        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void getUserProfile() {
        if (mPresenter != null) {
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

        this.userProfile = p;
        String[] tabs = getResources().getStringArray(R.array.user_profile_tab);
        UserProfileMainViewPagerAdapter viewPagerAdapter = new UserProfileMainViewPagerAdapter(getChildFragmentManager(), Arrays.asList(tabs), p);

//        viewPager.setAdapter(viewPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setOffscreenPageLimit(2);
        visitNum.setText(p.getVisitNum());
        fansNum.setText(p.getFansNum());
        collectionNum.setText(p.getCollectionNum());
        headerTitle.setText(p.getName());
        headerTime.setText(p.getOpenTime());

        Glide.with(getActivity()).load(p.getHead()).into(logo);

//        logo.setImage(p.getHead());

        Glide.with(getActivity()).load(p.getLogo()).into(headerBg);


//        constraintLayout.invalidate();
        ISupportFragment fragment = findChildFragment(UserProfileFragment.class);
        if (fragment == null) {
            fragment = UserProfileFragment.newInstance(p);
        }

        loadRootFragment(R.id.viewpager_container, fragment);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ISupportFragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = findChildFragment(UserProfileFragment.class);
                        if (fragment == null) {
                            fragment = UserProfileFragment.newInstance(p);
                        }

                        loadRootFragment(R.id.viewpager_container, fragment);
                        break;
                    case 1:
                        fragment = findChildFragment(CoachLessonFragment.class);
                        if (fragment == null) {
                            fragment = CoachLessonFragment.newInstance(userProfile);
                        }

                        loadRootFragment(R.id.viewpager_container, fragment);
                        break;
                    case 2:
                        fragment = findChildFragment(UserDynamicFragment.class);
                        if (fragment == null) {
                            fragment = UserDynamicFragment.newInstance(userProfile);
                        }

                        loadRootFragment(R.id.viewpager_container, fragment);
                        break;
                }
//                if(tab.getPosition() == 0)

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//
//        List<String> labelList = Arrays.asList(p.getLabelList().split(","));
//
//        tags.setTags(labelList);
    }

    @Override
    public void getCoachOrUserRelevantResult(GetCoachOrUserRelevantBean getCoachOrUserRelevantBean) {

    }

    @Override
    public void gymdetailsCoachResult(List<Personaltainer> res) {

    }

    @Override
    public void addCollectCurriculumResult(AttationCurriculum res) {

    }

    @Override
    public void delCollectCurriculumResult(String res) {

    }

    @Override
    public void getTopicListByUserIdResult(List<Topic> res) {

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

    @Override
    public void uploadImageResult(String url) {

    }

    @Override
    public void uploadImagesResult(List<String> urls) {

    }

    @Override
    public void addCoachCredentialsResult(String res) {

    }

    @Override
    public void delCoachCredentialsResult(String res) {

    }

    @Override
    public void getMsgByIdToEditResult(UserProfileMatchCertificatePersonalStory res) {

    }

    @Override
    public void getMsgByTypeResult(List<UserProfileMatchCertificatePersonalStory> res) {

    }

    @Override
    public void addSetResult(PictureSet requestBody) {

    }

    @Override
    public void addSetPictureResult(List<GymPicture> requestBody) {

    }

    @Override
    public void delSetPictureResult(String requestBody) {

    }

    @Override
    public void editBackGroundResult(String requestBody) {

    }

    @Override
    public void setAllResult(List<PictureSet> list) {

    }

    @Override
    public void sortSetPictureResult(String requestBody) {

    }

    @Override
    public void attentionResult(String res) {
//        showMessage("attentionResult");
    }

    @Override
    public void unfollowResult(String res) {
//        showMessage("unfollowResult");
    }

    @Override
    public void addFeedbackResult(FeedbackInfo res) {

    }

    @Override
    public void feedbackAllListResult(FeedbackInfoBean res) {

    }

    @Override
    public void addVisitNumResult(String res) {
//        showMessage("addVisitNumResult");
    }

    @Override
    public void cancelCommentPraiseResult(Topic res) {

    }

    @Override
    public void getMoodResult(DynamicTopicBean res) {

    }

    @Override
    public void addScanResult(Topic res) {

    }

    @Override
    public void toCommentPraiseResult(String res) {

    }

    @Override
    public void followAllListResult(FollowUserBean res) {

    }
}
