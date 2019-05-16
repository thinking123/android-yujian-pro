package com.yujian.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.nisrulz.sensey.Sensey;
import com.yujian.R;
import com.yujian.entity.PictureSet;
import com.yujian.entity.UserProfile;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.mvp.ui.EventBus.EventBusTags;
import com.yujian.mvp.ui.EventBus.UserProfileEvent;
import com.yujian.mvp.ui.fragment.userProfile.EditTimeLineObjFragment;
import com.yujian.mvp.ui.fragment.userProfile.IntroduceFragment;
import com.yujian.mvp.ui.fragment.userProfile.PictureSetsFragment;
import com.yujian.mvp.ui.fragment.userProfile.PictureSetsManageFragmentFragment;
import com.yujian.mvp.ui.fragment.userProfile.UserProfileFeedbackFragment;
import com.yujian.mvp.ui.fragment.userProfile.UserProfileMainFragment;
import com.yujian.mvp.ui.fragment.userProfile.UserProfileRelateUserFragment;
import com.yujian.mvp.ui.fragment.userProfile.UserProfileTimeLineFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/11/2019 13:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserProfileActivity extends SupportActivity {
    private String userId;
    private String type;
    ISupportFragment userProfileMainFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        userId = bundle.getString("id");
        type = bundle.getString("type");

        setContentView(R.layout.activity_user_profile);


        userProfileMainFragment = findFragment(UserProfileMainFragment.class);

        if (userProfileMainFragment == null) {
            userProfileMainFragment = UserProfileMainFragment.newInstance(userId , type);
            loadRootFragment(R.id.user_profile_container,
                    userProfileMainFragment);
        }


//        Sensey.getInstance().init(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserProfileEvent e) {
        UserProfile userProfile = e.getMessage();
        PictureSet pictureSet = e.getPictureSet();
        UserProfileMatchCertificatePersonalStory story = e.getUserProfileMatchCertificatePersonalStory();
        String tag = e.getTag();
        ISupportFragment fragment = null;
        switch (tag) {
            case EventBusTags.UserProfile.MATCH:
            case EventBusTags.UserProfile.CERTIFICATE:
            case EventBusTags.UserProfile.PERSONALSTORY:
                fragment = findFragment(UserProfileTimeLineFragment.class);
                if (fragment == null) {
                    fragment = UserProfileTimeLineFragment.newInstance(userProfile, tag);
                }
                start(fragment);
                break;
            case EventBusTags.UserProfile.ADDMATCH:
            case EventBusTags.UserProfile.ADDCERTIFICATE:
            case EventBusTags.UserProfile.ADDPERSONALSTORY:
                fragment = findFragment(EditTimeLineObjFragment.class);
                if (fragment == null) {
                    fragment = EditTimeLineObjFragment.newInstance(story == null ? "" : story.getId(), tag);
                }
                start(fragment);
                break;
            case EventBusTags.UserProfile.PICTURESET:

                fragment = findFragment(PictureSetsFragment.class);
                if (fragment == null) {
                    fragment = PictureSetsFragment.newInstance(pictureSet);
                }
                start(fragment);
                break;

            case EventBusTags.UserProfile.GOTOPICTURESETSMANGEE:

                fragment = findFragment(PictureSetsManageFragmentFragment.class);
                if (fragment == null) {
                    fragment = PictureSetsManageFragmentFragment.newInstance(userProfile.getId());
                }
                start(fragment);
                break;


            case EventBusTags.UserProfile.GOTOINTRODUCE:
                fragment = findFragment(IntroduceFragment.class);
                if (fragment == null) {
                    fragment = IntroduceFragment.newInstance(userProfile);
                }
                start(fragment);
                break;
            case EventBusTags.UserProfile.GOTOADDFEEDBACK:
                fragment = findFragment(UserProfileFeedbackFragment.class);
                if (fragment == null) {
                    fragment = UserProfileFeedbackFragment.newInstance(userProfile.getId());
                }
                start(fragment);
                break;
            case EventBusTags.UserProfile.GOTOFANS:
            case EventBusTags.UserProfile.GOTOVISIT:
                fragment = findFragment(UserProfileRelateUserFragment.class);
                if (fragment == null) {
                    fragment = UserProfileRelateUserFragment.newInstance(userProfile , tag);
                }
                start(fragment);
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Sensey.getInstance().stop();
    }
}
