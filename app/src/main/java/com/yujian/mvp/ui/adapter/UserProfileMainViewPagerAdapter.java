package com.yujian.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yujian.mvp.presenter.UserProfilePresenter;
import com.yujian.mvp.ui.fragment.userProfile.CoachLessonFragment;
import com.yujian.mvp.ui.fragment.userProfile.UserDynamicFragment;
import com.yujian.mvp.ui.fragment.userProfile.UserProfileFragment;
import com.yujian.mvp.ui.fragment.userProfile.UserProfileMainFragment;

import java.util.List;

public class UserProfileMainViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> tabs;

    public UserProfileMainViewPagerAdapter(FragmentManager fm, List<String> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                UserProfileFragment userProfileFragment = UserProfileFragment.newInstance();
                return userProfileFragment;
            case 1:
                CoachLessonFragment coachLessonFragment = CoachLessonFragment.newInstance();
                return coachLessonFragment;
            case 2:
                UserDynamicFragment userDynamicFragment = UserDynamicFragment.newInstance();
                return userDynamicFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position < tabs.size() - 1){
            return tabs.get(position);
        }else{
            return "";
        }
    }
}
