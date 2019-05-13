package com.yujian.mvp.ui.EventBus;

import com.yujian.entity.UserProfile;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;

public class UserProfileEvent {
    public UserProfile userProfile;
    public String tag;
    public UserProfileEvent(UserProfile userProfile , String tag){
        this.userProfile = userProfile;
        this.tag = tag;
    }

    public UserProfile getMessage(){
        return userProfile;
    }

    public String getTag(){
        return tag;
    }
}
