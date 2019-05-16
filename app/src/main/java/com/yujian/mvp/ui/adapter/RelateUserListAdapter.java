package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.entity.FollowUser;
import com.yujian.entity.FollowUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RelateUserListAdapter extends RecyclerView.Adapter<RelateUserListAdapter.ViewHoler> {

    private List<FollowUser> values;
    private final PublishSubject<FollowUser> onClickSubject = PublishSubject.create();
    public RelateUserListAdapter(List<FollowUser> myDataset ) {
        values = myDataset == null ? new ArrayList<>() : myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.relate_user_list_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final FollowUser obj = values.get(position);

        viewHoler.name.setText(obj.getName());
        viewHoler.address.setText(obj.getAddress());


        if(!TextUtils.isEmpty(obj.getLogo())){
            Glide.with(viewHoler.layout.getContext()).load(obj.getLogo()).into(viewHoler.logo);
        }
        viewHoler.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(obj);
            }
        });

    }

    public void addAll(List<FollowUser> list){
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

        public ImageView logo;
        public ImageView label;
        public TextView name;
        public TextView address;


        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            logo = (ImageView)itemView.findViewById(R.id.logo);
            label = (ImageView)itemView.findViewById(R.id.label);

            name = (TextView)itemView.findViewById(R.id.name);
            address = (TextView)itemView.findViewById(R.id.address);
        }
    }

    public Observable<FollowUser> getPositionClicks(){
        return onClickSubject.hide();
    }
}
