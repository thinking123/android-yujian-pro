package com.yujian.widget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.yujian.R;

public class ImagePreviewImage {
    public static AlertDialog previewImage(Context context , String url){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context,
                android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View mView = inflater.inflate(R.layout.image_preview_dialog, null);
        PhotoView photoView = mView.findViewById(R.id.previewImage);

        Glide.with(context)
                .load(url).into(photoView);


//        photoView.setImageResource(R.drawable.nature);
        mBuilder.setView(mView);
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        return mDialog;
    }
}
