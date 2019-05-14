package com.yujian.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class XRecyclerViewEx extends XRecyclerView {
    public XRecyclerViewEx(Context context) {
        super(context);
    }

    public XRecyclerViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecyclerViewEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int getHeaders_includingRefreshCount() {
        int superCount =  super.getHeaders_includingRefreshCount();

        return superCount + 1;
    }
}
