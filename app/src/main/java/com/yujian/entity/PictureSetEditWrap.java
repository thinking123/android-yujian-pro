package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureSetEditWrap implements Serializable {
    private boolean isEdit;
    private PictureSet pictureSet;
}
