package com.yujian.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.previewlibrary.GPreviewBuilder;
import com.yujian.R;
import com.yujian.entity.Dynamic;
import com.yujian.entity.Dynamic;
import com.yujian.mvp.ui.EventBus.EventBusTags;
import com.yujian.utils.Common;
import com.yujian.utils.entity.ClickObj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class UserDynamicListAdapter extends RecyclerView.Adapter<UserDynamicListAdapter.ViewHoler> {

    private List<Dynamic> values;
    private final PublishSubject<ClickObj> onClickSubject = PublishSubject.create();
    private Fragment fragment;
    public UserDynamicListAdapter(List<Dynamic> myDataset , Fragment fragment) {
        values = myDataset;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public UserDynamicListAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v = inflater.inflate(R.layout.user_dynamic_list_row, viewGroup, false);

        ViewHoler viewHoler = new ViewHoler(v);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull UserDynamicListAdapter.ViewHoler  viewHoler, int position) {
        Dynamic obj = values.get(position);

        if(!TextUtils.isEmpty(obj.getHead())){
            Glide.with(viewHoler.layout.getContext()).load(obj.getHead()).into(viewHoler.head);
        }
        viewHoler.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(new ClickObj(obj.getId() , EventBusTags.AdapterClickable.UserDynamicListAdapter.HEAD));
            }
        });


        viewHoler.name.setText(obj.getName());
        viewHoler.viewCount.setText(obj.getViewCount());


        viewHoler.createTime.setText(obj.getCreateTime());

//        viewHoler.moodContent.setText(obj.getMoodContent());

        viewHoler.praiseCount.setText(obj.getPraiseCount());
        viewHoler.commentCount.setText(obj.getCommentCount());
        viewHoler.shareNum.setText(obj.getShareNum());


        setClickSpan(viewHoler.moodContent , obj);


        List<String> moodImg = Common.splitStringToList(obj.getMoodImg() , "");

        DynamicImagesListAdapter dynamicImagesListAdapter = new DynamicImagesListAdapter(moodImg);

        viewHoler.moodImgList.setAdapter(dynamicImagesListAdapter);
        viewHoler.moodImgList.setLayoutManager(new LinearLayoutManager(viewHoler.layout.getContext() ,
                LinearLayoutManager.HORIZONTAL,
                false
                ));
        dynamicImagesListAdapter.getPositionClicks().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                GPreviewBuilder.from(fragment)//activity实例必须
//                        .to(CustomActivity.class)//自定义Activity 使用默认的预览不需要
                        .setData(dynamicImagesListAdapter.getPreviewImage())//集合
//                        .setUserFragment(ZoomPreviewFragment.class)//自定义Fragment 使用默认的预览不需要
                        .setCurrentIndex(integer)
                        .setSingleFling(false)//是否在黑屏区域点击返回
                        .setDrag(false)//是否禁用图片拖拽返回
                        .setType(GPreviewBuilder.IndicatorType.Number)//指示器类型
                        .start();//启动
            }
        });

        viewHoler.praiseCountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickSubject.onNext(new ClickObj(obj.getId() , EventBusTags.AdapterClickable.UserDynamicListAdapter.PRAISECOUNTICON));
            }
        });


        viewHoler.commentCountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickSubject.onNext(new ClickObj(obj.getId() , EventBusTags.AdapterClickable.UserDynamicListAdapter.COMMENTCOUNTICON));
            }
        });

        viewHoler.shareNumIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickSubject.onNext(new ClickObj(obj.getId() , EventBusTags.AdapterClickable.UserDynamicListAdapter.SHARENUMICON));
            }
        });




//        if(Objects.equals(type , "1")){
//            viewHoler.lessonBtn.setText("提醒");
//        }else {
//            boolean isCollection = Objects.equals(
//                    obj.getIsCollection(),
//                    "1"
//            );
//            viewHoler.lessonBtn.setText(isCollection ? "已关注":"关注");
//        }
//
//        viewHoler.lessonBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onTipSubject.onNext(obj);
//            }
//        });



    }
    private void setClickSpan(TextView textView , Dynamic obj){


        textView.setHighlightColor(Color.TRANSPARENT);

        String text = obj.getMoodContent();
        SpannableStringBuilder spannableString = new SpannableStringBuilder(text);

        Pattern topicPattern = Pattern.compile("#[^#]+#");
        Matcher matcher = topicPattern.matcher(text);

        while (matcher.find()){
            int start = matcher.start();
            int end = matcher.end();

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {

                    Timber.i("you click");
//                    view.invalidate();
                }

                @Override
                public void updateDrawState(@androidx.annotation.NonNull TextPaint ds) {
                    ds.setUnderlineText(false);
                }
            };

            spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            spannableString.setSpan(new ForegroundColorSpan(
                            ContextCompat.getColor(fragment.getActivity(), R.color.colorPrimary)),
                    start, end, 0);
        }

//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View view) {
//
//                Timber.i("you click");
////                    view.invalidate();
//            }
//        };
//
//        spannableString.setSpan(clickableSpan, 0,
//                text.length() - 1
//                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
//        spannableString.setSpan(new ForegroundColorSpan(
//                        ContextCompat.getColor(fragment.getActivity(), R.color.colorPrimary)),
//                0,
//                text.length() - 1, 0);


        textView.setText(spannableString);

        textView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public void clear(){
        values.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Dynamic> list){
        values.addAll(list);
        notifyDataSetChanged();
    }




    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        public CircleImageView head;

        public TextView name;
        public TextView viewCount;
        public TextView createTime;
        public TextView moodContent;
        public RecyclerView moodImgList;

        public ImageView praiseCountIcon;
        public TextView praiseCount;
        public ImageView commentCountIcon;
        public TextView commentCount;
        public ImageView shareNumIcon;
        public TextView shareNum;


        public View layout;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout = itemView;

            head = (CircleImageView) itemView.findViewById(R.id.head);
            name = (TextView) itemView.findViewById(R.id.name);
            viewCount = (TextView) itemView.findViewById(R.id.viewCount);
            createTime = (TextView) itemView.findViewById(R.id.createTime);
            moodContent = (TextView) itemView.findViewById(R.id.moodContent);
            moodImgList = (RecyclerView) itemView.findViewById(R.id.moodImgList);


            praiseCountIcon = (ImageView) itemView.findViewById(R.id.praiseCountIcon);
            praiseCount = (TextView) itemView.findViewById(R.id.praiseCount);

            commentCountIcon = (ImageView) itemView.findViewById(R.id.commentCountIcon);
            commentCount = (TextView) itemView.findViewById(R.id.commentCount);

            shareNumIcon = (ImageView) itemView.findViewById(R.id.shareNumIcon);
            shareNum = (TextView) itemView.findViewById(R.id.shareNum);

        }
    }


    public Observable<ClickObj> getPositionClicks() {
        return onClickSubject.hide();
    }

}
