package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yujian.R;

import java.util.List;

import timber.log.Timber;

public class RecyclerViewHorizontalButtonListAdapter extends RecyclerView.Adapter<RecyclerViewHorizontalButtonListAdapter.ViewHoler> {

    private List<String> values;

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
                Timber.i("click item");
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

        public MaterialButton button;
        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            button = (MaterialButton)itemView.findViewById(R.id.btn);
        }
    }
}
