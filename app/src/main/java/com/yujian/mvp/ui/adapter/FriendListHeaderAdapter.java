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
import com.yujian.entity.Friend;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class FriendListHeaderAdapter extends RecyclerView.Adapter<FriendListHeaderAdapter.ViewHoler> {

    private List<Friend> values;
    private final PublishSubject<Friend> onClickSubject = PublishSubject.create();
    public FriendListHeaderAdapter(List<Friend> myDataset) {
        values = myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.friend_list_row_header_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final Friend name = values.get(position);

        Glide.with(viewHoler.layout.getContext()).load(name.getLogo()).into(viewHoler.imageView);
        viewHoler.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(name);
            }
        });
    }

    public void add(int position, Friend item) {
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
            imageView = (ImageView)itemView.findViewById(R.id.logo);
        }
    }

    public Observable<Friend> getPositionClicks(){
        return onClickSubject.hide();
    }
}
