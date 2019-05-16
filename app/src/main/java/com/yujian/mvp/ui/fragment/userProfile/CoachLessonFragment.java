package com.yujian.mvp.ui.fragment.userProfile;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.yujian.app.BaseApp;
import com.yujian.entity.AttationCurriculum;
import com.yujian.entity.FeedbackInfo;
import com.yujian.entity.Friend;
import com.yujian.entity.GymPicture;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yujian.R;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerUserProfileComponent;
import com.yujian.entity.DrillTime;
import com.yujian.entity.Personaltainer;
import com.yujian.entity.PictureSet;
import com.yujian.entity.Topic;
import com.yujian.entity.UserProfile;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.mvp.contract.UserProfileContract;
import com.yujian.mvp.model.entity.DynamicTopicBean;
import com.yujian.mvp.model.entity.FeedbackInfoBean;
import com.yujian.mvp.model.entity.FollowUserBean;
import com.yujian.mvp.model.entity.GetCoachOrUserRelevantBean;
import com.yujian.mvp.model.entity.GymPictureBean;
import com.yujian.mvp.presenter.UserProfilePresenter;
import com.yujian.mvp.ui.activity.UserProfileActivity;
import com.yujian.mvp.ui.adapter.FitnessRoomPersonaltainerAdapter;
import com.yujian.mvp.ui.adapter.LessonListAdapter;
import com.yujian.mvp.ui.adapter.RelateCoachAdapter;
import com.yujian.mvp.ui.adapter.RelateFitnessRoomAdapter;
import com.yujian.utils.Common;
import com.yujian.utils.Constant;
import com.yujian.widget.TimerPickerWithTabLayoutMonth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/11/2019 14:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CoachLessonFragment extends BaseSupportFragment<UserProfilePresenter> implements UserProfileContract.View {

    @BindView(R.id.coachTitle)
    TextView coachTitle;
    @BindView(R.id.coachList)
    RecyclerView coachList;
    @BindView(R.id.userLinerLayout)
    LinearLayout userLinerLayout;
    @BindView(R.id.relateFitnessRoomList)
    RecyclerView relateFitnessRoomList;
    @BindView(R.id.lessonList)
    RecyclerView lessonList;

    private UserProfile userProfile;
    TimePickerView pvTime;
    private RelateCoachAdapter relateCoachAdapter;
    private RelateFitnessRoomAdapter relateFitnessRoomAdapter;
    private FitnessRoomPersonaltainerAdapter fitnessRoomPersonaltainerAdapter;
    private LessonListAdapter lessonListAdapter;

    private BDLocation bdLocation;
    private Date curDate = new Date();
    @BindView(R.id.timeLayout)
    TimerPickerWithTabLayoutMonth timerPickerWithTabLayoutMonth;
    public static CoachLessonFragment newInstance(UserProfile userProfile) {
        CoachLessonFragment fragment = new CoachLessonFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userProfile", userProfile);
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
        return inflater.inflate(R.layout.fragment_coach_lesson, container, false);
    }

    private void goToUserProfile(String userId , String type){
        Intent intent = new Intent(getActivity() , UserProfileActivity.class);
        intent.putExtra(Constant.Bundle.USER_ID , userId);
        intent.putExtra("type" , type);
        startActivity(intent);
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        userProfile = (UserProfile) this.getArguments().getSerializable("userProfile");
        bdLocation = BaseApp.getInstance().bdLocation;

        int gridSpace = getResources().getDimensionPixelSize(R.dimen.grid_space);

        if (Objects.equals(userProfile.getUserRole(), "1")) {
            fitnessRoomPersonaltainerAdapter = new FitnessRoomPersonaltainerAdapter(new ArrayList<>());
            coachList.setAdapter(fitnessRoomPersonaltainerAdapter);
            fitnessRoomPersonaltainerAdapter.getPositionClicks().subscribe(new Consumer<Personaltainer>() {
                @Override
                public void accept(Personaltainer personaltainer) throws Exception {
                    goToUserProfile(personaltainer.getId() , "2");
                }
            });
            getGymdetailsCoach();
        } else {
            coachTitle.setText("与TA相关的教练");
            userLinerLayout.setVisibility(View.VISIBLE);

            relateCoachAdapter = new RelateCoachAdapter(new ArrayList<>());
            coachList.setAdapter(relateCoachAdapter);
            relateCoachAdapter.getPositionClicks().subscribe(new Consumer<Friend>() {
                @Override
                public void accept(Friend friend) throws Exception {
                    goToUserProfile(friend.getId() , friend.getUserRole());
                }
            });

            relateFitnessRoomAdapter = new RelateFitnessRoomAdapter(new ArrayList<>());
            relateFitnessRoomList.setAdapter(relateFitnessRoomAdapter);

            relateFitnessRoomList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            relateFitnessRoomList.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect,
                                           @NonNull View view,
                                           @NonNull RecyclerView parent,
                                           @NonNull RecyclerView.State state) {
                    outRect.left = gridSpace;
                }
            });

            getCoachOrUserRelevant();
        }


        coachList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        coachList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect,
                                       @NonNull View view,
                                       @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                outRect.left = gridSpace;
            }
        });

        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                Constant.Common.DAYDATEPATTERN;
//                DateFormat df = new SimpleDateFormat(Constant.Common.DAYDATEPATTERN);

                curDate = date;
                timerPickerWithTabLayoutMonth.setDate(date);
//                time.setText(df.format(date));
//
//                time.setTextColor(ContextCompat.getColor(getActivity() , R.color.text_black));
            }
        }).build();
        timerPickerWithTabLayoutMonth.getPositionClicks().subscribe(new Consumer<Date>() {
            @Override
            public void accept(Date date) throws Exception {
                pvTime.show();
            }
        });

        timerPickerWithTabLayoutMonth.getSelectedClicks().subscribe(new Consumer<Date>() {
            @Override
            public void accept(Date date) throws Exception {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                curDate = date;
                pvTime.setDate(calendar);
            }
        });


        lessonListAdapter = new LessonListAdapter(new ArrayList<>() , userProfile.getUserRole());
        lessonListAdapter.getPositionClicks().subscribe(new Consumer<DrillTime>() {
            @Override
            public void accept(DrillTime drillTime) throws Exception {
                goToUserProfile(drillTime.getCoachId() , "2");
            }
        });
        lessonListAdapter.getTipClicks().subscribe(new Consumer<DrillTime>() {
            @Override
            public void accept(DrillTime drillTime) throws Exception {
                if(Objects.equals(userProfile.getUserRole() , "1")){
//                    viewHoler.lessonBtn.setText("提醒");
                }else {
                    boolean isCollection = Objects.equals(
                            drillTime.getIsCollection(),
                            "1"
                    );

                    if(isCollection){
                        delCollectCurriculum(drillTime);
                    }else{
                        addCollectCurriculum(drillTime);
                    }
                }
            }
        });


        lessonList.setAdapter(lessonListAdapter);
        lessonList.setLayoutManager(
                new LinearLayoutManager(getActivity())
        );
        getCurriculumByTime();
//        timerPickerWithTabLayoutMonth.setDate(new Date());

    }

    private void addCollectCurriculum(DrillTime drillTime){
        if (mPresenter != null && bdLocation != null) {
            mPresenter.addCollectCurriculum(
                    drillTime.getCoachId(),
                    drillTime.getCurriculumId(),
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude())
            );
        }
    }
    private void delCollectCurriculum(DrillTime drillTime){
        if (mPresenter != null && bdLocation != null) {
            mPresenter.delCollectCurriculum(
                    drillTime.getCoachId(),
                    drillTime.getCurriculumId(),
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude())
            );
        }
    }
    private void getCoachOrUserRelevant() {
        if (mPresenter != null && bdLocation != null) {
            mPresenter.getCoachOrUserRelevant(
                    Double.toString(bdLocation.getLongitude()),
                    Double.toString(bdLocation.getLatitude()),
                    userProfile.getId()
            );
        }
    }

    private void getGymdetailsCoach() {
        if (mPresenter != null && bdLocation != null) {
            mPresenter.getGymdetailsCoach(
                    userProfile.getId()
            );
        }
    }


    private void getCurriculumByTime(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        String week = Common.CalendarWeekToDigitString(calendar.get(Calendar.DAY_OF_WEEK));
        if (mPresenter != null && bdLocation != null) {
            mPresenter.getCurriculumByTime(
                    userProfile.getId(),
                    week,
                    Common.dateToString(curDate)
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
    public void getUserProfileResult(UserProfile userProfile) {

    }

    @Override
    public void getCoachOrUserRelevantResult(GetCoachOrUserRelevantBean getCoachOrUserRelevantBean) {
     relateCoachAdapter.addAll(getCoachOrUserRelevantBean.getCoachList());
     relateFitnessRoomAdapter.addAll(getCoachOrUserRelevantBean.getGymListCollections());
    }

    @Override
    public void gymdetailsCoachResult(List<Personaltainer> res) {

    }

    @Override
    public void addCollectCurriculumResult(AttationCurriculum res) {
        getCurriculumByTime();
    }

    @Override
    public void delCollectCurriculumResult(String res) {
        getCurriculumByTime();
    }

    @Override
    public void getTopicListByUserIdResult(List<Topic> res) {

    }

    @Override
    public void getGymdetailsCoachResult(List<Personaltainer> list) {
            fitnessRoomPersonaltainerAdapter.addAll(list);
    }

    @Override
    public void getCurriculumByTimeResult(List<DrillTime> list) {
        lessonListAdapter.addAll(list);
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

    }

    @Override
    public void unfollowResult(String res) {

    }

    @Override
    public void addFeedbackResult(FeedbackInfo res) {

    }

    @Override
    public void feedbackAllListResult(FeedbackInfoBean res) {

    }

    @Override
    public void addVisitNumResult(String res) {

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
