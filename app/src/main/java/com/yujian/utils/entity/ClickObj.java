package com.yujian.utils.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClickObj implements Serializable {
    private String id;
    private String type;
    private Object target;
    public ClickObj(String id , String type){
        this.id = id;
        this.type = type;
    }
}
