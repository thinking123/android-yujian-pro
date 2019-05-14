package com.yujian.utils.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwoLevelEvent implements Serializable {
    private Integer childIndex;
    private Integer parentIndex;
}
