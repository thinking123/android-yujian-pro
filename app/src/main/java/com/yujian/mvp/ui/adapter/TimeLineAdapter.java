package com.yujian.mvp.ui.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.yujian.R;
import com.yujian.app.BaseApp;
import com.yujian.app.utils.zoompreview.PreviewImageInfo;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.utils.Common;
import com.yujian.utils.entity.TwoLevelEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHoler> {


    private List<UserProfileMatchCertificatePersonalStory> values;
    private MaterialButton preSelectedBtn;
    private final PublishSubject<UserProfileMatchCertificatePersonalStory> onDelSubject = PublishSubject.create();
    private final PublishSubject<UserProfileMatchCertificatePersonalStory> onEditSubject = PublishSubject.create();

    private final PublishSubject<TwoLevelEvent> onGridSubject = PublishSubject.create();

    public TimeLineAdapter(List<UserProfileMatchCertificatePersonalStory> myDataset) {
        values = myDataset;
    }


    public List<PreviewImageInfo> getPreviewImage(int pos){

        UserProfileMatchCertificatePersonalStory u = values.get(pos);
        List<PreviewImageInfo> urls = new ArrayList<>();

        for(String p : Common.splitStringToList(u.getUrl(), "")){
            PreviewImageInfo p1 = new PreviewImageInfo(p);
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
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = View.inflate(viewGroup.getContext(), R.layout.timeline_row, null);
        return new ViewHoler(view, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final UserProfileMatchCertificatePersonalStory p = values.get(position);

        String f = "%s";
        switch (p.getType()) {
            case "1":
                f = "认证时间：%s";
                break;
            case "2":
                f = "参赛时间：%s";
                break;
            case "3":
                f = "个人事迹时间：%s";
                break;
        }


        viewHoler.times.setText(String.format(f, p.getTimes()));
        viewHoler.name.setText(String.format("名称：%s", p.getName()));
        viewHoler.introduce.setText(String.format("介绍：%s", p.getIntroduce()));

        viewHoler.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelSubject.onNext(p);
            }
        });

        viewHoler.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditSubject.onNext(p);
            }
        });


        ThreeColumnGridAdapter adapter = new ThreeColumnGridAdapter(Common.splitStringToList(p.getUrl(), ""));

        adapter.getPositionClicks().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                TwoLevelEvent e = new TwoLevelEvent();
                e.setChildIndex(integer);
                e.setParentIndex(position);
                onGridSubject.onNext(e);
            }
        });
        viewHoler.images.setAdapter(adapter);

        viewHoler.images.setLayoutManager(new GridLayoutManager(viewHoler.layout.getContext(),
                3));
        int gridSpace = viewHoler.layout.getResources().getDimensionPixelSize(R.dimen.grid_space);

        viewHoler.images.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int pos = parent.getChildLayoutPosition(view);
                if (pos % 3 != 2) {
                    outRect.right = gridSpace;
                }

            }
        });

    }

    public void add(int position, UserProfileMatchCertificatePersonalStory item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public void clear(){
        values.clear();

        notifyDataSetChanged();
    }

    public void addAll(List<UserProfileMatchCertificatePersonalStory> list){
        values.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        public TimelineView timelineView;
        public View layout;
        public TextView times;
        public TextView name;
        public TextView introduce;
        public RecyclerView images;
        public MaterialButton deleteBtn;
        public MaterialButton editBtn;

        public ViewHoler(@NonNull View itemView, int viewType) {
            super(itemView);
            layout = itemView;
            timelineView = (TimelineView) itemView.findViewById(R.id.timeline);
            timelineView.initLine(viewType);

            times = (TextView) itemView.findViewById(R.id.times);
            name = (TextView) itemView.findViewById(R.id.name);
            introduce = (TextView) itemView.findViewById(R.id.introduce);
            images = (RecyclerView) itemView.findViewById(R.id.images);
            deleteBtn = (MaterialButton) itemView.findViewById(R.id.deleteBtn);
            editBtn = (MaterialButton) itemView.findViewById(R.id.editBtn);
        }
    }

    public Observable<UserProfileMatchCertificatePersonalStory> getDelClicks() {
        return onDelSubject.hide();
    }

    public Observable<UserProfileMatchCertificatePersonalStory> getEditClicks() {
        return onEditSubject.hide();
    }
    public Observable<TwoLevelEvent> getGridClicks() {
        return onGridSubject.hide();
    }
}
