package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.app.utils.zoompreview.PreviewImageInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ImageListSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> values;
    private static final int TYPE_BTN = 0;
    private static final int TYPE_ITEM = 1;
    private final PublishSubject<Integer> onClickSubject = PublishSubject.create();
    private final PublishSubject<Integer> onAddSubject = PublishSubject.create();
    private final PublishSubject<Integer> onDelSubject = PublishSubject.create();
    public List<PreviewImageInfo> getPreviewImage() {

        List<PreviewImageInfo> urls = new ArrayList<>();

        for (String p : values) {
            PreviewImageInfo p1 = new PreviewImageInfo(p);
            urls.add(p1);
        }
        return urls;
//        return Observable.fromIterable(values).map(new Function<String, String>() {
//            @Override
//            public String apply(String String) throws Exception {
//                return String.getUrl();
//            }
//        }).toList().to;
    }

    public ImageListSelectAdapter(List<String> myDataset) {
        values = myDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        if (i == TYPE_BTN) {

            View v =
                    inflater.inflate(R.layout.three_column_select_btn_grid_row,
                            viewGroup, false);

            ViewHolerBtn viewHoler = new ViewHolerBtn(v);

            return viewHoler;
        } else {
            View v =
                    inflater.inflate(R.layout.three_column_select_grid_row,
                            viewGroup, false);

            ViewHoler viewHoler = new ViewHoler(v);

            return viewHoler;
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolerBtn) {
            ViewHolerBtn viewHoler = (ViewHolerBtn) holder;

            viewHoler.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddSubject.onNext(Integer.valueOf(position));
                }
            });
        } else {
            ViewHoler viewHoler = (ViewHoler) holder;
            final String name = values.get(position);
            Glide.with(viewHoler.layout.getContext()).load(name).into(viewHoler.imageView);
            viewHoler.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSubject.onNext(Integer.valueOf(position));
                }
            });

            viewHoler.delImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDelSubject.onNext(Integer.valueOf(position));
                }
            });
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position == values.size()) {
            return TYPE_BTN;
        } else {
            return TYPE_ITEM;
        }
    }

    public void clear() {
        values.clear();
        notifyDataSetChanged();
    }

    public void add(int position, String item) {
        values.add(position, item);
//        notifyItemInserted(position);
        notifyDataSetChanged();
    }


    public void addAll(List<String> list) {
        int count = values.size();
        values.addAll(list);
//        notifyItemRangeChanged(headerData == null ? count : count + 1, list.size());
        //不能使用 notifyItemRangeChanged ： error :
        /*
         * Called attach on a child which is not detached: ViewHolder{2e1b324d position=0 id=-1, oldPos=-1, pLpos:-1} com.jcodecraeer.xrecyclerview.XRecyclerView{25ac0888 VFED.... ......ID 0,397-1080,1699 #7f09006f app:id/String_list}, adapter:com.jcodecraeer.xrecyclerview.XRecyclerView$WrapAdapter@16094221, layout:android.support.v7.widget.LinearLayoutManager@1f761102, context:com.yujian.mvp.ui.activity.MainActivity@53d4d58
         * */
        notifyDataSetChanged();
    }


    public void remove(int position) {
        values.remove(position);
//        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return values.size() + 1;
    }

    public List<String> getValues(){
        return values;
    }
    public class ViewHoler extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public ImageView delImageBtn;
        public View layout;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.image);
            delImageBtn = (ImageView) itemView.findViewById(R.id.delImageBtn);
        }
    }

    public class ViewHolerBtn extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public View layout;

        public ViewHolerBtn(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }


    public Observable<Integer> getPositionClicks() {
        return onClickSubject.hide();
    }

    public Observable<Integer> getAddClicks() {
        return onAddSubject.hide();
    }public Observable<Integer> getDelClicks() {
        return onDelSubject.hide();
    }

}
