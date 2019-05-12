package com.yujian.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yujian.R;
import com.yujian.app.BaseApp;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class HorizontalScrollTagList extends RecyclerView {

    private HorizontalScrollTagListAdapter horizontalScrollTagListAdapter;
    public HorizontalScrollTagList(@NonNull Context context) {
        this(context, null, 0);

    }

    public HorizontalScrollTagList(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollTagList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        this.setLayoutManager(layoutManager);
    }

    public void setTags(List<String> list){

        horizontalScrollTagListAdapter = new HorizontalScrollTagListAdapter(list);
        this.setAdapter(horizontalScrollTagListAdapter);
    }



    public class HorizontalScrollTagListAdapter extends RecyclerView.Adapter<HorizontalScrollTagListAdapter.ViewHoler> {

        private List<String> values;
        private final PublishSubject<String> onClickSubject = PublishSubject.create();
        public HorizontalScrollTagListAdapter(List<String> myDataset) {
            values = myDataset;
        }

        @NonNull
        @Override
        public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(
                    viewGroup.getContext());
            View v =
                    inflater.inflate(R.layout.friend_list_row_body_tag_row, viewGroup, false);

            ViewHoler viewHoler = new ViewHoler(v);

            return viewHoler;
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
            final String name = values.get(position);
            viewHoler.textView.setText(name);
            viewHoler.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSubject.onNext(name);
                }
            });

        }

        public void add(int position, String item) {
            values.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(int position) {
            values.remove(position);
            notifyItemRemoved(position);
        }


        @Override
        public int getItemCount() {
            return values.size();
        }

        public class ViewHoler extends RecyclerView.ViewHolder{

            public TextView textView;
            public View layout;
            public ViewHoler(@NonNull View itemView) {
                super(itemView);
                layout = itemView;
                textView = (TextView)itemView.findViewById(R.id.btn);
            }
        }

        public Observable<String> getPositionClicks(){
            return onClickSubject.hide();
        }
    }

}
