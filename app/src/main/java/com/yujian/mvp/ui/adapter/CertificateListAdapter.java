package com.yujian.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yujian.R;
import com.yujian.app.BaseApp;
import com.yujian.entity.UserProfileMatchCertificatePersonalStory;
import com.yujian.utils.Common;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class CertificateListAdapter extends RecyclerView.Adapter<CertificateListAdapter.ViewHoler> {

    private List<UserProfileMatchCertificatePersonalStory> values;
    private final PublishSubject<UserProfileMatchCertificatePersonalStory> onClickSubject = PublishSubject.create();
    public CertificateListAdapter(List<UserProfileMatchCertificatePersonalStory> myDataset) {
        values = myDataset == null ? new ArrayList<>() : myDataset;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.certificatelist_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int position) {
        final UserProfileMatchCertificatePersonalStory obj = values.get(position);

        List<String> urls = Common.splitStringToList(obj.getUrl() , "");
        if(urls.size() > 0){
            Glide.with(viewHoler.layout.getContext()).load(urls.get(0)).into(viewHoler.imageView);
        }
        viewHoler.name.setText(obj.getName());
        viewHoler.introduce.setText(obj.getIntroduce());
        viewHoler.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(obj);
            }
        });
    }

    public void add(int position, UserProfileMatchCertificatePersonalStory item) {
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

        @BindView(R.id.imageView)
        public ImageView imageView;
        @BindView(R.id.name)
        public TextView name;
        @BindView(R.id.introduce)
        public TextView introduce;
        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;


            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            name = (TextView)itemView.findViewById(R.id.name);
            introduce = (TextView)itemView.findViewById(R.id.introduce);
        }
    }

    public Observable<UserProfileMatchCertificatePersonalStory> getPositionClicks(){
        return onClickSubject.hide();
    }
}
