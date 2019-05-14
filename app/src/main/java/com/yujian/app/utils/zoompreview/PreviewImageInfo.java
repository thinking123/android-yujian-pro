package com.yujian.app.utils.zoompreview;

import android.graphics.Rect;
import android.os.Parcel;
import android.support.annotation.Nullable;

import com.previewlibrary.enitity.IThumbViewInfo;

public class PreviewImageInfo implements IThumbViewInfo {
    private String url;  //图片地址
    private Rect mBounds; // 记录坐标
    private String user;//
    private String videoUrl;//视频链接 //不为空是视频

    public PreviewImageInfo(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {//将你的图片地址字段返回
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public Rect getBounds() {//将你的图片显示坐标字段返回
        return mBounds;
    }

    @Nullable
    @Override
    public String getVideoUrl() {
        return null;
    }

    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.mBounds, flags);
    }
    protected PreviewImageInfo(Parcel in) {
        this.url = in.readString();
        this.mBounds = in.readParcelable(Rect.class.getClassLoader());
    }

    public static final Creator<PreviewImageInfo> CREATOR = new Creator<PreviewImageInfo>() {
        @Override
        public PreviewImageInfo createFromParcel(Parcel source) {
            return new PreviewImageInfo(source);
        }

        @Override
        public PreviewImageInfo[] newArray(int size) {
            return new PreviewImageInfo[size];
        }
    };
}
