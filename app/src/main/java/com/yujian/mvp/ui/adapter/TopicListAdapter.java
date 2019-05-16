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

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.entity.Topic;
import com.yujian.entity.Topic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHoler> {

    private List<Topic> values;
    private final PublishSubject<Topic> onClickSubject = PublishSubject.create();
    private boolean isEdit = false;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            Log.e("", "Longpress detected");
        }
    });
    public TopicListAdapter(List<Topic> myDataset) {
        values = myDataset == null ? new ArrayList<>() : myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.topic_list_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final Topic obj = values.get(position);

        if(!TextUtils.isEmpty(obj.getTopicImg())){
            Glide.with(viewHoler.layout.getContext()).load(obj.getTopicImg()).into(viewHoler.topicImg);
        }

        viewHoler.title.setText(obj.getTitle());
        viewHoler.joinNum.setText(
                String.format("%s参与" , obj.getJoinNum()));
        viewHoler.topicImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(obj);
            }
        });
    }

    public void add(int position, Topic item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }
    public void addAll(List<Topic> list){
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

        public ImageView topicImg;
        public ImageView topicLabel;
        public TextView title;
        public TextView joinNum;
        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;


            topicImg = (ImageView)itemView.findViewById(R.id.topicImg);
            topicLabel = (ImageView)itemView.findViewById(R.id.topicLabel);
            joinNum = (TextView)itemView.findViewById(R.id.joinNum);
            title = (TextView)itemView.findViewById(R.id.title);

        }


    }

    public Observable<Topic> getPositionClicks(){
        return onClickSubject.hide();
    }

}
