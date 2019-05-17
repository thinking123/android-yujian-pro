package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.app.utils.zoompreview.PreviewImageInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class DynamicImagesListAdapter extends RecyclerView.Adapter<DynamicImagesListAdapter.ViewHoler> {

    private List<String> values;
    private final PublishSubject<Integer> onClickSubject = PublishSubject.create();
    public DynamicImagesListAdapter(List<String> myDataset) {
        values = myDataset == null ? new ArrayList<>() : myDataset;
    }


    public List<PreviewImageInfo> getPreviewImage() {

        List<PreviewImageInfo> urls = new ArrayList<>();

        for (String p : values) {
            PreviewImageInfo p1 = new PreviewImageInfo(p);
            urls.add(p1);
        }
        return urls;
    }
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.dynamic_images_list_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final String obj = values.get(position);

        viewHoler.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(position);
            }
        });



        if(!TextUtils.isEmpty(obj)){
            Glide.with(viewHoler.layout.getContext()).load(obj).into(viewHoler.imageView);
        }
    }


    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;


            imageView = (ImageView)itemView.findViewById(R.id.imageView);
        }
    }

    public Observable<Integer> getPositionClicks(){
        return onClickSubject.hide();
    }
}
