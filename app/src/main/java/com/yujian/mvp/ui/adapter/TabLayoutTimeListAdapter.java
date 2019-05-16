package com.yujian.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.widget.TimerPickerWithTabLayoutMonth;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class TabLayoutTimeListAdapter extends RecyclerView.Adapter<TabLayoutTimeListAdapter.ViewHoler> {

    private List<TimerPickerWithTabLayoutMonth.WeekDay> values;
    RecyclerView.SmoothScroller smoothScroller;
    LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private int selectedDay = 0;
    private final PublishSubject<Integer> onClickSubject = PublishSubject.create();
    private TextView preTextView = null;
    public TabLayoutTimeListAdapter(List<TimerPickerWithTabLayoutMonth.WeekDay> myDataset,
                                    RecyclerView.SmoothScroller smoothScroller ,
                                    LinearLayoutManager linearLayoutManager , RecyclerView recyclerView) {
        values = myDataset;
        this.smoothScroller = smoothScroller;
        this.linearLayoutManager = linearLayoutManager;
        this.recyclerView = recyclerView;

    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.tablayout_month_layout_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final TimerPickerWithTabLayoutMonth.WeekDay name = values.get(position);
        viewHoler.week.setText(name.getWeek());
        viewHoler.day.setText(Integer.toString(name.getDay()));
        Context context =  this.recyclerView.getContext();
        viewHoler.day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(position + 1);


                if(preTextView != null){
                    preTextView.setBackgroundColor(ContextCompat.getColor(context , R.color.white));
                    preTextView.setTextColor(ContextCompat.getColor(context , R.color.text_black));
                }

                preTextView = viewHoler.day;


                viewHoler.day.setBackground(ContextCompat.getDrawable(viewHoler.layout.getContext(), R.drawable.day_selected_bg));
//            viewHoler.day.setBackgroundColor(android.R.color.transparent);
                viewHoler.day.setTextColor(ContextCompat.getColor(context , R.color.white));

                preTextView = viewHoler.day;
            }
        });


        if(name.getDay() == selectedDay){

            viewHoler.day.setBackground(ContextCompat.getDrawable(viewHoler.layout.getContext(), R.drawable.day_selected_bg));
//            viewHoler.day.setBackgroundColor(android.R.color.transparent);
            viewHoler.day.setTextColor(ContextCompat.getColor(context , R.color.white));

            preTextView = viewHoler.day;

        }else{
//            viewHoler.day.setBackground(null);
//            viewHoler.day.setBackgroundColor(R.color.white);
            viewHoler.day.setBackgroundColor(ContextCompat.getColor(context , R.color.white));
            viewHoler.day.setTextColor(ContextCompat.getColor(context , R.color.text_black));
//            viewHoler.day.setTextColor(R.color.text_black);
        }


//        viewHoler.day.setBackground(ContextCompat.getDrawable(viewHoler.layout.getContext(), R.drawable.day_selected_bg));

    }

    public void setSelectedDay(int day){
        selectedDay = day;
        int count = values.size();
        int curPos = day - 1;
        int scrPos = curPos;
        int vleft = linearLayoutManager.findLastVisibleItemPosition();
        int vright = linearLayoutManager.findFirstVisibleItemPosition();

        if(vright - curPos > curPos - vleft){
            //curpos 靠左边
            if(curPos - 2 > 0){
                scrPos = curPos - 2;
            }
        }else{
            if(curPos + 2 < count){
                scrPos = curPos + 2;
            }
        }


//
//        if(curPos + 2 < count){
//            scrPos = curPos + 2;
//        }
//
//        if(curPos - 2 > 0){
//            scrPos = curPos - 2;
//        }
        smoothScroller.setTargetPosition(scrPos);
        linearLayoutManager.startSmoothScroll(smoothScroller);
    }
    public void clear() {
        values.clear();
        notifyDataSetChanged();
    }


    public void addAll(List<TimerPickerWithTabLayoutMonth.WeekDay> list) {
        int count = values.size();
        values.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        public TextView week;
        public TextView day;
        public View layout;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            week = (TextView) itemView.findViewById(R.id.week);
            day = (TextView) itemView.findViewById(R.id.day);
        }
    }

    public Observable<Integer> getPositionClicks() {
        return onClickSubject.hide();
    }
}
