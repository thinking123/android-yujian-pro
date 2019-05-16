package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.entity.FitnessRoom;
import com.yujian.entity.Personaltainer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RelateFitnessRoomAdapter extends RecyclerView.Adapter<RelateFitnessRoomAdapter.ViewHoler> {

    private List<FitnessRoom> values;
    private final PublishSubject<FitnessRoom> onClickSubject = PublishSubject.create();
    public RelateFitnessRoomAdapter(List<FitnessRoom> myDataset) {
        values = myDataset == null ? new ArrayList<>() : myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.relate_fitnessroom_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final FitnessRoom obj = values.get(position);

        viewHoler.gymName.setText(obj.getGymName());
        viewHoler.gymAddressDetails.setText(
                String.format("地址：%s" ,obj.getGymAddressDetails()));
        viewHoler.workTime.setText(
                String.format("营业时间：%s-%s",obj.getHoursStart()
                        ,obj.getHoursEnd())
        );


        if(!TextUtils.isEmpty(obj.getBackground())){
            Glide.with(viewHoler.layout.getContext()).load(obj.getBackground()).into(viewHoler.background);
        }
        viewHoler.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(obj);
            }
        });
    }

    public void addAll(List<FitnessRoom> list){
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

        public TextView gymName;
        public TextView gymAddressDetails;
        public TextView workTime;
        public ImageView background;


        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;

            gymName = (TextView)itemView.findViewById(R.id.gymName);
            gymAddressDetails = (TextView)itemView.findViewById(R.id.gymAddressDetails);
            workTime = (TextView)itemView.findViewById(R.id.workTime);
            background = (ImageView)itemView.findViewById(R.id.background);
        }
    }

    public Observable<FitnessRoom> getPositionClicks(){
        return onClickSubject.hide();
    }
}
