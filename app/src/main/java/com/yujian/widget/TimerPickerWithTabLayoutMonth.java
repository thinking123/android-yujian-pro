package com.yujian.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yujian.R;
import com.yujian.mvp.ui.adapter.TabLayoutTimeListAdapter;
import com.yujian.utils.Common;
import com.yujian.utils.Constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class TimerPickerWithTabLayoutMonth extends LinearLayout {
    private ConstraintLayout container;
    private LinearLayout timepickerLayout;
    private TextView day;
    private TextView week;
    private TextView date;
    private RecyclerView timeList;

    private final PublishSubject<Date> onClickSubject = PublishSubject.create();
    private final PublishSubject<Date> onSelectedSubject = PublishSubject.create();


    private Date curDate;
    private Calendar curCalendar;
    private TabLayoutTimeListAdapter tabLayoutTimeListAdapter;
//    public Date getDate() {
//        return curDate;
//    }
//
//    public void setDate(Date curDate) {
//        this.curDate = curDate;
//    }

    List<WeekDay> weekDayList = new ArrayList<>();

    public class WeekDay {

        public WeekDay(int week, int day) {
            this.day = day;
            this.week = Common.CalendarWeekToString(week);
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        private String week;
        private int day;
    }

    public TimerPickerWithTabLayoutMonth(Context context) {
        this(context, null, 0);
    }

    public TimerPickerWithTabLayoutMonth(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerPickerWithTabLayoutMonth(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TimerPickerWithTabLayoutMonth(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        container = (ConstraintLayout) LayoutInflater.from(getContext()).inflate(R.layout.timerpicker_with_tablayout_month_layout, null);

//        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 0, 0, 0);
//        this.setLayoutParams(lp);
//        this.setPadding(0, 0, 0, 0);


        addView(container, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        day = (TextView) findViewById(R.id.day);
        week = (TextView) findViewById(R.id.week);
        date = (TextView) findViewById(R.id.date);
        timeList = (RecyclerView) findViewById(R.id.timeList);
        timepickerLayout = (LinearLayout) findViewById(R.id.timepickerLayout);
        timepickerLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(curDate);
            }
        });

        RecyclerView.SmoothScroller smoothScroller = new
                LinearSmoothScroller(getContext()) {
                    @Override
                    protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false);

        tabLayoutTimeListAdapter = new TabLayoutTimeListAdapter(new ArrayList<>(),smoothScroller , linearLayoutManager , timeList);



        timeList.setAdapter(tabLayoutTimeListAdapter);
        timeList.setLayoutManager(linearLayoutManager);
//        timeList.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//            }
//        });
        tabLayoutTimeListAdapter.getPositionClicks().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer day) throws Exception {
                curCalendar.set(Calendar.DAY_OF_MONTH, day);
                curDate = curCalendar.getTime();
                setUiDate();
                onSelectedSubject.onNext(curDate);

                tabLayoutTimeListAdapter.setSelectedDay(curCalendar.get(Calendar.DAY_OF_MONTH));
            }
        });
        initDate();
        setUiDate();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setDate(curDate);
            }
        } , 100);

    }

    public Date getCurDate() {
        return curDate;
    }

    private void initDate() {
        if (curDate == null) {
            curDate = new Date();
        }
        curCalendar = Calendar.getInstance();
        curCalendar.setTime(curDate);
    }

    public void setDate(Date date) {
        curDate = date;
        curCalendar.setTime(curDate);
        setUiDate();
        getWeekendDays();
        tabLayoutTimeListAdapter.clear();
        tabLayoutTimeListAdapter.addAll(weekDayList);
        tabLayoutTimeListAdapter.setSelectedDay(curCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private List<WeekDay> getWeekendDays() {
        weekDayList = new ArrayList<>();
        int year = curCalendar.get(Calendar.YEAR);
        int month = curCalendar.get(Calendar.MONTH);
        int curDay = 1;
        Calendar cal = new GregorianCalendar(year, month, curDay);
        do {

            // get the day of the week for the current day
            int day = cal.get(Calendar.DAY_OF_WEEK);
            // check if it is a Saturday or Sunday
            if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
                // print the day - but you could add them to a list or whatever
                System.out.println(cal.get(Calendar.DAY_OF_MONTH));
            }
            // advance to the next day
//            WeekDay weekDay = new WeekDay();
            weekDayList.add(new WeekDay(day, curDay));
            cal.add(Calendar.DAY_OF_YEAR, 1);
            curDay++;
        } while (cal.get(Calendar.MONTH) == month);


        return weekDayList;
    }

    private void setUiDate() {
        day.setText(Integer.toString(curCalendar.get(Calendar.DAY_OF_MONTH)));
        week.setText(String.format("星期：%s" , Common.CalendarWeekToString(curCalendar.get(Calendar.DAY_OF_WEEK))));


        DateFormat df = new SimpleDateFormat("yyyy-MM");

        date.setText(df.format(curDate));
    }

    public Observable<Date> getPositionClicks() {
        return onClickSubject.hide();
    }

    public Observable<Date> getSelectedClicks() {
        return onSelectedSubject.hide();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
