package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.entity.PictureSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class PictureSetsManageAdapter extends RecyclerView.Adapter<PictureSetsManageAdapter.ViewHoler> {

    private List<PictureSet> values;
    private final PublishSubject<PictureSet> onClickSubject = PublishSubject.create();
    public PictureSetsManageAdapter(List<PictureSet> myDataset) {
        values = myDataset == null ? new ArrayList<>() : myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.picturesets_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final PictureSet obj = values.get(position);

        Glide.with(viewHoler.layout.getContext()).load(obj.getBackground()).into(viewHoler.background);
        viewHoler.gymPictureSetName.setText(obj.getGymPictureSetName());
        viewHoler.gymPictureSetSize.setText(obj.getGymPictureSetSize());
        viewHoler.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(obj);
            }
        });
    }

    public void add(int position, PictureSet item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }
    public void addAll(List<PictureSet> list){
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

        @BindView(R.id.background)
        public ImageView background;
        @BindView(R.id.gymPictureSetName)
        public TextView gymPictureSetName;
        @BindView(R.id.gymPictureSetSize)
        public TextView gymPictureSetSize;
        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;


            background = (ImageView)itemView.findViewById(R.id.background);
            gymPictureSetName = (TextView)itemView.findViewById(R.id.gymPictureSetName);
            gymPictureSetSize = (TextView)itemView.findViewById(R.id.gymPictureSetSize);
        }
    }

    public Observable<PictureSet> getPositionClicks(){
        return onClickSubject.hide();
    }
}
