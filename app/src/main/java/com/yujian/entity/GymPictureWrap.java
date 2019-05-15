package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymPictureWrap implements Serializable {
    private GymPicture gymPicture;
    private boolean isSelected = false;
    public GymPictureWrap(GymPicture gymPicture){
        this.gymPicture = gymPicture;
    }


}
