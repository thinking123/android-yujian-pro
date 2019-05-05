package com.yujian.widget;

import android.content.Context;
import android.support.design.button.MaterialButton;
import android.util.AttributeSet;

import timber.log.Timber;

public class PrimaryRadiusBtn extends MaterialButton {
    public PrimaryRadiusBtn(Context context) {
        this(context, null, 0);
    }

    public PrimaryRadiusBtn(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PrimaryRadiusBtn(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        try {
            init();
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }

    }

    private void init(){

    }
}
