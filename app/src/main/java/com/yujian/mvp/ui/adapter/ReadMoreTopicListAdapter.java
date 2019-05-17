package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.entity.Topic;
import com.yujian.mvp.ui.EventBus.EventBusTags;
import com.yujian.utils.entity.ClickObj;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ReadMoreTopicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Topic> values;
    private final PublishSubject<ClickObj> onClickSubject = PublishSubject.create();
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

    public ReadMoreTopicListAdapter(List<Topic> myDataset) {
        values = myDataset == null ? new ArrayList<>() : myDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE_HEADER) {
            LayoutInflater inflater = LayoutInflater.from(
                    viewGroup.getContext());
            View v = inflater.inflate(R.layout.topic_list_read_more_row, viewGroup, false);

            ViewHolerHeader viewHoler = new ViewHolerHeader(v);

            return viewHoler;
        } else if (i == TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(
                    viewGroup.getContext());
            View v = inflater.inflate(R.layout.topic_list_row, viewGroup, false);

            ViewHoler viewHoler = new ViewHoler(v);
            return viewHoler;
        }
        throw new RuntimeException("there is no type that matches the type ");
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 2) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHoler){
            ViewHoler viewHoler = (ViewHoler)holder;
            final Topic obj = values.get(position);

            if (!TextUtils.isEmpty(obj.getTopicImg())) {
                Glide.with(viewHoler.layout.getContext()).load(obj.getTopicImg()).into(viewHoler.topicImg);
            }

            viewHoler.title.setText(obj.getTitle());
            viewHoler.joinNum.setText(
                    String.format("%s参与", obj.getJoinNum()));
            viewHoler.topicImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickObj clickObj = new ClickObj(obj.getId(),
                            EventBusTags.AdapterClickable.ReadMoreTopicListAdapter.ITEM);
                    onClickSubject.onNext(clickObj);
                }
            });
        }else{
            ViewHolerHeader viewHoler = (ViewHolerHeader)holder;
            viewHoler.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickObj clickObj = new ClickObj("",
                            EventBusTags.AdapterClickable.ReadMoreTopicListAdapter.HEADER);
                    onClickSubject.onNext(clickObj);
                }
            });
        }

    }

    public void add(int position, Topic item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(List<Topic> list) {
        values.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        values.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int count = values.size() > 2 ? 3 : values.size();
        return count;
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


            topicImg = (ImageView) itemView.findViewById(R.id.topicImg);
            topicLabel = (ImageView) itemView.findViewById(R.id.topicLabel);
            joinNum = (TextView) itemView.findViewById(R.id.joinNum);
            title = (TextView) itemView.findViewById(R.id.title);

        }


    }

    public class ViewHolerHeader extends RecyclerView.ViewHolder {

        public ImageView topicImg;
        public TextView title;
        public View layout;

        public ViewHolerHeader(@NonNull View itemView) {
            super(itemView);
            layout = itemView;


            topicImg = (ImageView) itemView.findViewById(R.id.topicImg);
            title = (TextView) itemView.findViewById(R.id.title);

        }


    }

    public Observable<ClickObj> getPositionClicks() {
        return onClickSubject.hide();
    }

}
