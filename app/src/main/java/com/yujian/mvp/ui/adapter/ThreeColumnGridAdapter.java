package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.app.BaseApp;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ThreeColumnGridAdapter extends RecyclerView.Adapter<ThreeColumnGridAdapter.ViewHoler> {

    private List<String> values;
    private final PublishSubject<String> onClickSubject = PublishSubject.create();
    public ThreeColumnGridAdapter(List<String> myDataset) {
        values = myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.three_column_grid_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final String name = values.get(position);
        Glide.with(viewHoler.layout.getContext()).load(name).into(viewHoler.imageView);
        viewHoler.imageView.setOnClickListener(new View.OnClickListener() {
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

        public ImageView imageView;
        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            imageView = (ImageView)itemView.findViewById(R.id.image);
        }
    }

    public Observable<String> getPositionClicks(){
        return onClickSubject.hide();
    }
}
