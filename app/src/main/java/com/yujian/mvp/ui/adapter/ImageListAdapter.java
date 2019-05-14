package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.app.utils.zoompreview.PreviewImageInfo;
import com.yujian.entity.GymPicture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHoler> {
    private List<GymPicture> values;
    private final PublishSubject<Integer> onClickSubject = PublishSubject.create();

    public List<PreviewImageInfo> getPreviewImage(){

        List<PreviewImageInfo> urls = new ArrayList<>();

        for(GymPicture p : values){
            PreviewImageInfo p1 = new PreviewImageInfo(p.getUrl());
            urls.add(p1);
        }
        return urls;
//        return Observable.fromIterable(values).map(new Function<GymPicture, String>() {
//            @Override
//            public String apply(GymPicture gymPicture) throws Exception {
//                return gymPicture.getUrl();
//            }
//        }).toList().to;
    }
    public ImageListAdapter(List<GymPicture> myDataset) {
        values = myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.three_column_grid_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final GymPicture name = values.get(position);
        Glide.with(viewHoler.layout.getContext()).load(name.getUrl()).into(viewHoler.imageView);
        viewHoler.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(Integer.valueOf(position));
            }
        });


    }

    public void clear() {
        values.clear();
        notifyDataSetChanged();
    }

    public void add(int position, GymPicture item) {
        values.add(position, item);
//        notifyItemInserted(position);
        notifyDataSetChanged();
    }


    public void addAll(List<GymPicture> list) {
        int count = values.size();
        values.addAll(list);
//        notifyItemRangeChanged(headerData == null ? count : count + 1, list.size());
        //不能使用 notifyItemRangeChanged ： error :
        /*
         * Called attach on a child which is not detached: ViewHolder{2e1b324d position=0 id=-1, oldPos=-1, pLpos:-1} com.jcodecraeer.xrecyclerview.XRecyclerView{25ac0888 VFED.... ......ID 0,397-1080,1699 #7f09006f app:id/GymPicture_list}, adapter:com.jcodecraeer.xrecyclerview.XRecyclerView$WrapAdapter@16094221, layout:android.support.v7.widget.LinearLayoutManager@1f761102, context:com.yujian.mvp.ui.activity.MainActivity@53d4d58
         * */
        notifyDataSetChanged();
    }



    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public View layout;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public Observable<Integer> getPositionClicks() {
        return onClickSubject.hide();
    }

}
