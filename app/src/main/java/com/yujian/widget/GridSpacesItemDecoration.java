package com.yujian.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public GridSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildLayoutPosition(view);
        outRect.top = space;
        if(pos % 2 == 0){

        }else{
            outRect.left = space;
        }
//        outRect.left = space;
//        outRect.right = space;
////        outRect.bottom = space;
//
//        // Add top margin only for the first item to avoid double space between items
//        if (parent.getChildLayoutPosition(view) == 0) {
//            outRect.top = space;
//        } else {
//            outRect.top = 0;
//        }
    }
}
