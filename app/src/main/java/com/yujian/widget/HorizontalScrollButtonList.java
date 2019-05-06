package com.yujian.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yujian.R;

import butterknife.BindView;
import timber.log.Timber;

public class HorizontalScrollButtonList extends RecyclerView {

    private Context context;
    private AttributeSet attributeSet;
    public HorizontalScrollButtonList(Context context) {
        this(context, null, 0);
    }

    public HorizontalScrollButtonList(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HorizontalScrollButtonList(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        this.context = context;
        this.attributeSet = attributeSet;
        try {
            init();
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
    }
    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
}
