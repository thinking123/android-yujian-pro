package com.yujian.mvp.ui.EventBus;

import com.yujian.entity.PictureSet;
import com.yujian.entity.UserProfile;

public class PictureSetEvent {
    public PictureSet pictureSet;
    public String tag;
    public PictureSetEvent(PictureSet pictureSet , String tag){
        this.pictureSet = pictureSet;
        this.tag = tag;
    }

    public PictureSet getMessage(){
        return pictureSet;
    }

    public String getTag(){
        return tag;
    }
}
