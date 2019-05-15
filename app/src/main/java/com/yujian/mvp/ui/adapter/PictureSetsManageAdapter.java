package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.entity.PictureSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class PictureSetsManageAdapter extends RecyclerView.Adapter<PictureSetsManageAdapter.ViewHoler> {

    private List<PictureSet> values;
    private final PublishSubject<PictureSet> onClickSubject = PublishSubject.create();
    private final PublishSubject<PictureSet> onMenuSubject = PublishSubject.create();
    private boolean isEdit = false;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            Log.e("", "Longpress detected");
        }
    });
    public PictureSetsManageAdapter(List<PictureSet> myDataset) {
        values = myDataset == null ? new ArrayList<>() : myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.picturesets_manage_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final PictureSet obj = values.get(position);

        if(!TextUtils.isEmpty(obj.getBackground())){
            Glide.with(viewHoler.layout.getContext()).load(obj.getBackground()).into(viewHoler.background);
        }

        viewHoler.gymPictureSetName.setText(obj.getGymPictureSetName());
        viewHoler.gymPictureSetSize.setText(obj.getGymPictureSetSize());
        viewHoler.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(obj);
                if(isEdit){
//                    if(viewHoler.selectedIcon.getVisibility() == View.VISIBLE){
//                        viewHoler.selectedIcon.setVisibility(View.INVISIBLE);
//                    }else{
//                        viewHoler.selectedIcon.setVisibility(View.VISIBLE);
//                    }
                }
            }
        });

        viewHoler.background.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(viewHoler.layout.getContext(), viewHoler.background);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu_rename);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.rename_menu:
                                //handle menu1 click
                                onMenuSubject.onNext(obj);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

                return false;
            }
        });
    }

    public void add(int position, PictureSet item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }
    public void addAll(List<PictureSet> list){
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

    public class ViewHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.background)
        public ImageView background;
        public ImageView selectedIcon;
        @BindView(R.id.gymPictureSetName)
        public TextView gymPictureSetName;
        @BindView(R.id.gymPictureSetSize)
        public TextView gymPictureSetSize;
        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;


            background = (ImageView)itemView.findViewById(R.id.background);
            selectedIcon = (ImageView)itemView.findViewById(R.id.selectedIcon);
            gymPictureSetName = (TextView)itemView.findViewById(R.id.gymPictureSetName);
            gymPictureSetSize = (TextView)itemView.findViewById(R.id.gymPictureSetSize);

        }


    }

    public Observable<PictureSet> getPositionClicks(){
        return onClickSubject.hide();
    }

    public Observable<PictureSet> getMenuClicks(){
        return onMenuSubject.hide();
    }
}
