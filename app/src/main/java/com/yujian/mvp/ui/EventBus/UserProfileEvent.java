package com.yujian.mvp.ui.EventBus;

import com.yujian.entity.PictureSet;
import com.yujian.entity.UserProfile;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;

public class UserProfileEvent {
    public UserProfile userProfile;
    public PictureSet pictureSet;
    public String tag;
    public UserProfileEvent(UserProfile userProfile , String tag ,PictureSet pictureSet){
        this.userProfile = userProfile;
        this.pictureSet = pictureSet;
        this.tag = tag;
    }

    public UserProfile getMessage(){
        return userProfile;
    }
    public PictureSet getPictureSet(){
        return pictureSet;
    }

    public String getTag(){
        return tag;
    }
}
