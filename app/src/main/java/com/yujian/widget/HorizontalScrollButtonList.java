package com.yujian.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yujian.R;
import com.yujian.mvp.ui.adapter.RecyclerViewHorizontalButtonListAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class HorizontalScrollButtonList extends RecyclerView {

    private Context context;
    private AttributeSet attributeSet;
    public HorizontalScrollButtonList(Context context) {
        this(context, null, 0);
    }

    public HorizontalScrollButtonList(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HorizontalScrollButtonList(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        this.context = context;
        this.attributeSet = attributeSet;
        try {
            init();
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
    }
    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    public void initData(List<String> myDataset){
        HorizontalScrollButtonListAdapter adapter = new HorizontalScrollButtonListAdapter(myDataset);
        setAdapter(adapter);
    }

    public class HorizontalScrollButtonListAdapter extends RecyclerView.Adapter<HorizontalScrollButtonListAdapter.ViewHoler>{

        private List<String> values;
        private final PublishSubject<String> onClickSubject = PublishSubject.create();

        public HorizontalScrollButtonListAdapter(List<String> myDataset) {
            values = myDataset;
        }

        @NonNull
        @Override
        public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(
                    viewGroup.getContext());
            View v =
                    inflater.inflate(R.layout.recycler_hor_button_row1, viewGroup, false);

            HorizontalScrollButtonListAdapter.ViewHoler viewHoler = new HorizontalScrollButtonListAdapter.ViewHoler(v);

            return viewHoler;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
            final String name = values.get(position);
            viewHoler.button.setText(name);
            viewHoler.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSubject.onNext(name);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 0;
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

}
