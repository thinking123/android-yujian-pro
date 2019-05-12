package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.entity.GymCard;
import com.yujian.entity.GymCard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHoler> {

    private List<GymCard> values;
    private final PublishSubject<GymCard> onClickSubject = PublishSubject.create();
    public CardListAdapter(List<GymCard> myDataset) {
        values = myDataset == null ? new ArrayList<>() : myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.cardlist_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final GymCard obj = values.get(position);

        viewHoler.cardName.setText(obj.getCardName());
        viewHoler.cardPrice.setText(obj.getCardPrice());
        viewHoler.cardDescribe.setText(obj.getCardDescribe());


        viewHoler.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(obj);
            }
        });
    }

    public void add(int position, GymCard item) {
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

        @BindView(R.id.cardName)
        public TextView cardName;
        @BindView(R.id.cardPrice)
        public TextView cardPrice;
        @BindView(R.id.cardDescribe)
        public TextView cardDescribe;
        @BindView(R.id.cardview)
        public CardView cardview;


        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;

            cardName = (TextView)itemView.findViewById(R.id.cardName);
            cardPrice = (TextView)itemView.findViewById(R.id.cardPrice);
            cardDescribe = (TextView)itemView.findViewById(R.id.cardDescribe);
            cardview = (CardView)itemView.findViewById(R.id.cardview);
        }
    }

    public Observable<GymCard> getPositionClicks(){
        return onClickSubject.hide();
    }
}
