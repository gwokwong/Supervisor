package com.sttech.supervisor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.sttech.supervisor.Constant;
import com.sttech.supervisor.R;
import com.sttech.supervisor.ui.adapter.AreaAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * function :选择时间周期
 * Created by 韦国旺 on 2017/5/6.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class SelectTimePeriodActivity extends TActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_select_period);
        initView();
    }


    private String timePeriods2[] = {"全部", "当天", "三天内", "一周内", "当月", "三个月内"};
    private AreaAdapter periodsAdapter1, periodsAdapter2;
    private TextView okBtn;
    private GridView periodsGridView1, periodsGridView2;
    private int selectPos = 0;

    private void initView() {
        initNavigation("选择时间周期", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.EXTRA_SELECT_POSITION, selectPos);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        okBtn = findById(R.id.select_ok);
        okBtn.setOnClickListener(this);

        selectPos = getIntent().getIntExtra(Constant.EXTRA_SELECT_POSITION, 0);

        periodsGridView1 = findById(R.id.select_gv1);
        periodsAdapter1 = new AreaAdapter(this, new ArrayList<>(Arrays.asList(new String[]{timePeriods2[selectPos]})));
        periodsAdapter1.setCheckItem(-1);
        periodsGridView1.setAdapter(periodsAdapter1);

        periodsGridView2 = findById(R.id.select_gv2);
        periodsAdapter2 = new AreaAdapter(this, new ArrayList<>(Arrays.asList(timePeriods2)));
        periodsAdapter2.setCheckItem(selectPos);
        periodsGridView2.setAdapter(periodsAdapter2);
        periodsGridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                periodsAdapter2.setCheckItem(position);
                selectPos = position;
                periodsAdapter1.updateData(new ArrayList<>(Arrays.asList(new String[]{timePeriods2[selectPos]})));
            }
        });

    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_SELECT_POSITION, selectPos);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
