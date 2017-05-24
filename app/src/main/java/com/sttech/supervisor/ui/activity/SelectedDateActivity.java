package com.sttech.supervisor.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.sttech.supervisor.R;
import com.sttech.supervisor.ui.widget.materialcalendarview.CalendarDay;
import com.sttech.supervisor.ui.widget.materialcalendarview.MaterialCalendarView;
import com.sttech.supervisor.ui.widget.materialcalendarview.OnDateSelectedListener;
import com.sttech.supervisor.ui.widget.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * function:
 * Created by 韦国旺 on 2017/5/24 0024.
 * Copyright (c) 2017 北京联龙博通 All Rights Reserved.
 */
public class SelectedDateActivity extends TActivity implements OnDateSelectedListener, OnMonthChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_selected_date);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
        //Setup initial text
        textView.setText(getSelectedDatesString());


    }


    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        textView.setText(getSelectedDatesString());
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        toast(FORMATTER.format(date.getDate()));
    }

    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }
}
