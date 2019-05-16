package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.entity.Friend;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RelateCoachAdapter extends RecyclerView.Adapter<RelateCoachAdapter.ViewHoler> {

    private List<Friend> values;
    private final PublishSubject<Friend> onClickSubject = PublishSubject.create();


    public RelateCoachAdapter(List<Friend> myDataset) {
        values = myDataset == null ? new ArrayList<>() : myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.relate_coach_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final Friend obj = values.get(position);

        if(!TextUtils.isEmpty(obj.getLogo())){
            Glide.with(viewHoler.layout.getContext()).load(obj.getLogo()).into(viewHoler.logo);
        }

        viewHoler.tag.setText(obj.getName());
        String f = "工作%s年";
        String content = String.format(f , obj.getContent());
        viewHoler.content.setText(content);


        viewHoler.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(obj);
            }
        });

    }

    public void addAll(List<Friend> list){
        values.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        values.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        public ImageView logo;
        public TextView tag;
        public TextView content;
        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;


            logo = (ImageView)itemView.findViewById(R.id.logo);

            tag = (TextView)itemView.findViewById(R.id.tag);
            content = (TextView)itemView.findViewById(R.id.content);

        }


    }

    public Observable<Friend> getPositionClicks(){
        return onClickSubject.hide();
    }

}
