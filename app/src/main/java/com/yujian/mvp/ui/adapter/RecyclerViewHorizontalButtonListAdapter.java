package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yujian.R;
import com.yujian.app.BaseApp;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class RecyclerViewHorizontalButtonListAdapter extends RecyclerView.Adapter<RecyclerViewHorizontalButtonListAdapter.ViewHoler> {

    private List<String> values;
    private MaterialButton preSelectedBtn;
    private final PublishSubject<String> onClickSubject = PublishSubject.create();
    public RecyclerViewHorizontalButtonListAdapter(List<String> myDataset) {
        values = myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.recycler_hor_button_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final String name = values.get(position);
        viewHoler.button.setText(name);
        viewHoler.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Timber.i("click item");
//                viewHoler.button.setActivated(true);
                if(preSelectedBtn != null){
                    preSelectedBtn.setBackgroundTintList(ContextCompat.getColorStateList(BaseApp.getContext(), R.color.white1));;
                    preSelectedBtn.setTextColor(ContextCompat.getColor(BaseApp.getContext() , R.color.text_gray));
                }
                preSelectedBtn = viewHoler.button;
                viewHoler.button.setBackgroundTintList(ContextCompat.getColorStateList(BaseApp.getContext(), R.color.btn_wx_hint_bg));;
                viewHoler.button.setTextColor(ContextCompat.getColor(BaseApp.getContext() , R.color.white));
                onClickSubject.onNext(name);
            }
        });

        if(position == 0 && preSelectedBtn == null) {
            preSelectedBtn = viewHoler.button;
            viewHoler.button.setBackgroundTintList(ContextCompat.getColorStateList(BaseApp.getContext(), R.color.btn_wx_hint_bg));;
            viewHoler.button.setTextColor(ContextCompat.getColor(BaseApp.getContext() , R.color.white));
//            viewHoler.button.setTextSize(R.dimen.page_title_size);
        }
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

        public MaterialButton button;
        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            button = (MaterialButton)itemView.findViewById(R.id.btn);
        }
    }

    public Observable<String> getPositionClicks(){
        return onClickSubject.hide();
    }
}
