package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.entity.Personaltainer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class FitnessRoomPersonaltainerAdapter extends RecyclerView.Adapter<FitnessRoomPersonaltainerAdapter.ViewHoler> {

    private List<Personaltainer> values;
    private final PublishSubject<Personaltainer> onClickSubject = PublishSubject.create();


    public FitnessRoomPersonaltainerAdapter(List<Personaltainer> myDataset) {
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
        final Personaltainer obj = values.get(position);

        if(!TextUtils.isEmpty(obj.getHead())){
            Glide.with(viewHoler.layout.getContext()).load(obj.getHead()).into(viewHoler.logo);
        }

        viewHoler.tag.setText(obj.getLabel());

        String f = "工作%s年";
        String content = String.format(f , obj.getWorkingLife());
        viewHoler.content.setText(content);


        viewHoler.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(obj);
            }
        });

    }

    public void addAll(List<Personaltainer> list){
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

    public Observable<Personaltainer> getPositionClicks(){
        return onClickSubject.hide();
    }

}
