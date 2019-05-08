package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.app.BaseApp;
import com.yujian.entity.Friend;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Friend> values;
    private MaterialButton preSelectedBtn;
//    private final PublishSubject<Friend> onClickSubject = PublishSubject.create();
    private List<Friend> headerData;
    public FriendListAdapter(List<Friend> myDataset) {
        values = myDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(i == TYPE_HEADER){
            LayoutInflater inflater = LayoutInflater.from(
                    viewGroup.getContext());
            View v = inflater.inflate(R.layout.friend_list_row_header, viewGroup, false);

            ViewHolerHeader viewHoler = new ViewHolerHeader(v);

            return viewHoler;
        }else if(i == TYPE_ITEM){
            LayoutInflater inflater = LayoutInflater.from(
                    viewGroup.getContext());
            View v = inflater.inflate(R.layout.friend_list_row_body, viewGroup, false);

            ViewHoler viewHoler = new ViewHoler(v);

            return viewHoler;
        }
        throw new RuntimeException("there is no type that matches the type ");
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position) && headerData != null)
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder  viewHoler, int position) {
        if(viewHoler instanceof ViewHoler){
            position = headerData == null ? position : position - 1;
            Friend friend = values.get(position );
            ViewHoler holer = (ViewHoler)viewHoler;
//            holer.button.setText("");
//            holer.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onClickSubject.onNext(friend);
//                }
//            });
            Glide.with(holer.layout.getContext()).load(friend.getLogo()).into(holer.logo);

            holer.name.setText(friend.getName());
            holer.addressDetails.setText(friend.getAddressDetails());
            double distance = 0;
            if(!TextUtils.isEmpty(friend.getDistance())){
                distance = Double.parseDouble(friend.getDistance());
            }
            holer.distance.setText(String.format("%.2fKM" , distance));


            String icons = friend.getIcon();
            icons = "金牌,水电费了可视对讲弗兰克,sddsflkj,是的范德萨开发,水电费了款式大方";
            List<String> list = new ArrayList<String>();
            if(!TextUtils.isEmpty(icons)){
                list = Arrays.asList(icons.split(","));
            }
            FriendListTagAdapter friendListTagAdapter = new FriendListTagAdapter(list);
            RecyclerView.LayoutManager layoutManagerBtnList = new LinearLayoutManager(holer.layout.getContext() , LinearLayoutManager.HORIZONTAL , false);

            holer.icons.setAdapter(friendListTagAdapter);
            holer.icons.setLayoutManager(layoutManagerBtnList);

        }else if(viewHoler instanceof ViewHolerHeader){
            ViewHolerHeader holer = (ViewHolerHeader)viewHoler;
            FriendListHeaderAdapter friendListHeaderAdapter = new FriendListHeaderAdapter(headerData);
            holer.header.setAdapter(friendListHeaderAdapter);


            RecyclerView.LayoutManager layoutManagerBtnList = new LinearLayoutManager(holer.layout.getContext() , LinearLayoutManager.HORIZONTAL , false);
            holer.header.setLayoutManager(layoutManagerBtnList);
        }


    }

    public void addHeaderData(List<Friend> friends){
        headerData = friends;
//        notifyItemInserted(0);
        notifyDataSetChanged();
    }
    public void add(int position, Friend item) {
        position = headerData == null ? position : position + 1;
                values.add(position, item);
        notifyItemInserted(position);
    }

    public void addAll(int position , List<Friend> list){
        int count = values.size();
        values.addAll(position, list);
        notifyItemRangeChanged(headerData == null ? count : count + 1, list.size());
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return headerData == null ? values.size() : values.size() + 1;
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        public ImageView logo;
        public TextView name;
        public TextView addressDetails;
        public TextView distance;
        public RecyclerView icons;
        public View layout;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            logo = (ImageView) itemView.findViewById(R.id.logo);
            name = (TextView) itemView.findViewById(R.id.name);
            addressDetails = (TextView) itemView.findViewById(R.id.addressDetails);
            distance = (TextView) itemView.findViewById(R.id.distance);
            icons = (RecyclerView) itemView.findViewById(R.id.icons);
        }
    }
    public class ViewHolerHeader extends RecyclerView.ViewHolder {

        public RecyclerView header;
        public View layout;

        public ViewHolerHeader(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            header = (RecyclerView) itemView.findViewById(R.id.friend_list_header);
        }
    }

//    public Observable<Friend> getPositionClicks() {
//        return onClickSubject.hide();
//    }

}
