package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.entity.DrillTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.ViewHoler> {

    private List<DrillTime> values;
    private final PublishSubject<DrillTime> onClickSubject = PublishSubject.create();
    private final PublishSubject<DrillTime> onTipSubject = PublishSubject.create();
    private String type;
    public LessonListAdapter(List<DrillTime> myDataset , String type) {
        values = myDataset == null ? new ArrayList<>() : myDataset;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.lesson_list_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final DrillTime obj = values.get(position);

        viewHoler.curriculumName.setText(obj.getCurriculumName());
        viewHoler.coachName.setText(obj.getCoachName());


        viewHoler.classTime.setText(
                String.format("上课时间：%s" ,obj.getClassTime()));

        viewHoler.collectionNum.setText(
                String.format("%s人" ,obj.getCollectionNum())
        );

        if(!TextUtils.isEmpty(obj.getImg())){
            Glide.with(viewHoler.layout.getContext()).load(obj.getImg()).into(viewHoler.img);
        }
        viewHoler.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(obj);
            }
        });

        if(Objects.equals(type , "1")){
            viewHoler.lessonBtn.setText("提醒");
        }else {
            boolean isCollection = Objects.equals(
                    obj.getIsCollection(),
                    "1"
            );
            viewHoler.lessonBtn.setText(isCollection ? "已关注":"关注");
        }

        viewHoler.lessonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTipSubject.onNext(obj);
            }
        });
    }

    public void addAll(List<DrillTime> list){
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

    public class ViewHoler extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView curriculumName;
        public TextView coachName;
        public TextView classTime;
        public TextView collectionNum;
        public MaterialButton lessonBtn;


        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            img = (ImageView)itemView.findViewById(R.id.img);

            curriculumName = (TextView)itemView.findViewById(R.id.curriculumName);
            coachName = (TextView)itemView.findViewById(R.id.coachName);
            classTime = (TextView)itemView.findViewById(R.id.classTime);
            collectionNum = (TextView)itemView.findViewById(R.id.collectionNum);
            lessonBtn = (MaterialButton)itemView.findViewById(R.id.lessonBtn);
        }
    }

    public Observable<DrillTime> getPositionClicks(){
        return onClickSubject.hide();
    }
    public Observable<DrillTime> getTipClicks(){
        return onTipSubject.hide();
    }
}
