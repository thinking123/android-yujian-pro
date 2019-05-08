package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yujian.R;
import com.yujian.app.BaseApp;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class FriendListTagAdapter extends RecyclerView.Adapter<FriendListTagAdapter.ViewHoler> {

    private List<String> values;
//    private final PublishSubject<String> onClickSubject = PublishSubject.create();
    public FriendListTagAdapter(List<String> myDataset) {
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
        viewHoler.button.setText(name);
//        viewHoler.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickSubject.onNext(name);
//            }
//        });
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

        public TextView button;
        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            button = (TextView)itemView.findViewById(R.id.btn);
        }
    }

//    public Observable<String> getPositionClicks(){
//        return onClickSubject.hide();
//    }
}
