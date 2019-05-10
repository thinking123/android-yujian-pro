package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yujian.R;
import com.yujian.entity.FitnessRoom;
import com.yujian.utils.Common;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class FitnessRoomListAdapter extends RecyclerView.Adapter<FitnessRoomListAdapter.ViewHolder> {
    private List<FitnessRoom> values;

    private final PublishSubject<FitnessRoom> onClickSubject = PublishSubject.create();
    private final PublishSubject<FitnessRoom> onClickSubjectCardView = PublishSubject.create();

    public Observable<FitnessRoom> getIconClicks() {
        return onClickSubject.debounce(1000 , TimeUnit.MILLISECONDS).hide();
    }
    public Observable<FitnessRoom> getCardViewClicks() {
        return onClickSubjectCardView.hide();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView address;
        public TextView time;
        public TextView type;
        public TextView distance;
        public ImageView bg;
        public ImageView typeIcon;
        public CircleImageView attention;
        public View layout;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            title = (TextView) itemView.findViewById(R.id.card_title);
            address = (TextView) itemView.findViewById(R.id.card_address);
            time = (TextView) itemView.findViewById(R.id.card_time);
            type = (TextView) itemView.findViewById(R.id.card_type);
            distance = (TextView) itemView.findViewById(R.id.card_distance);

            bg = (ImageView) itemView.findViewById(R.id.bg);
            typeIcon = (ImageView) itemView.findViewById(R.id.card_type_icon);
            attention = (CircleImageView) itemView.findViewById(R.id.card_attention);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

    public FitnessRoomListAdapter(List<FitnessRoom> myDataset) {
        values = myDataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v = inflater.inflate(R.layout.fitness_room_list_row, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        FitnessRoom fitnessRoom = values.get(position);
        viewHolder.title.setText(fitnessRoom.getGymName());
        viewHolder.address.setText(Common.formatAddress(fitnessRoom.getGymAddressDetails()));
        viewHolder.time.setText(Common.formatTimeRange(fitnessRoom.getOpenTime()));
        viewHolder.distance.setText(fitnessRoom.getDistance());
//        viewHolder.type.setText(fitnessRoom.getGymName());
        if (Objects.equals(fitnessRoom.getIsHasCard(), "1")) {
            viewHolder.type.setVisibility(View.VISIBLE);
            viewHolder.typeIcon.setVisibility(View.VISIBLE);
        } else {
            viewHolder.type.setVisibility(View.INVISIBLE);
            viewHolder.typeIcon.setVisibility(View.INVISIBLE);
        }


        viewHolder.attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(fitnessRoom);
            }
        });

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubjectCardView.onNext(fitnessRoom);
            }
        });
    }

    public void clear() {
        values.clear();
        notifyDataSetChanged();
    }


    public void addAll(List<FitnessRoom> list) {
        int count = values.size();
        values.addAll(list);
//        notifyItemRangeChanged(headerData == null ? count : count + 1, list.size());
        //不能使用 notifyItemRangeChanged ： error :
        /*
         * Called attach on a child which is not detached: ViewHolder{2e1b324d position=0 id=-1, oldPos=-1, pLpos:-1} com.jcodecraeer.xrecyclerview.XRecyclerView{25ac0888 VFED.... ......ID 0,397-1080,1699 #7f09006f app:id/friend_list}, adapter:com.jcodecraeer.xrecyclerview.XRecyclerView$WrapAdapter@16094221, layout:android.support.v7.widget.LinearLayoutManager@1f761102, context:com.yujian.mvp.ui.activity.MainActivity@53d4d58
         * */
        notifyDataSetChanged();
//        notifyItemRangeInserted(0 , list.size());
    }


    @Override
    public int getItemCount() {
        return values.size();
    }


}
