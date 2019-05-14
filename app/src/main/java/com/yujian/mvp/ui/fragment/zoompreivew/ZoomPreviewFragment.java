package com.yujian.mvp.ui.fragment.zoompreivew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.previewlibrary.view.BasePhotoFragment;
import com.yujian.R;
import com.yujian.app.utils.zoompreview.PreviewImageInfo;

/**
 * author  yangc
 * date 2017/11/22
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */

public class ZoomPreviewFragment extends BasePhotoFragment {
    /****用户具体数据模型***/
    private PreviewImageInfo b;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        b = (PreviewImageInfo) getBeanViewInfo();
//        imageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Log.d("SmoothImageView","onLongClick");
//                Toast.makeText(getContext(), "长按事件:" + b.getUser(), Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zoom_preview, container, false);
    }
}
