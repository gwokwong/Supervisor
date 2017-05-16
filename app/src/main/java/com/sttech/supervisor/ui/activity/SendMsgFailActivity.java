package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sttech.supervisor.Constant;
import com.sttech.supervisor.R;
import com.sttech.supervisor.ui.adapter.TabPagerAdapter;
import com.sttech.supervisor.ui.fragment.LocalFragment;
import com.sttech.supervisor.ui.fragment.WorkRecordFragment;
import com.sttech.supervisor.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * function :
 * Created by 韦国旺 on 2017/5/3.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class SendMsgFailActivity extends TActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SendMsgFailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_send_fail);
        initView();
    }

    private String[] mTitles;
    private ViewPager vp;
    private TabLayout tl;
    private List<Fragment> fragmentList;

    private static final int SELECT_TIME_REQ = 101;
    private AppBarLayout appBarLayout;

    private void initView() {
        mTitles = getResources().getStringArray(R.array.send_msg_fail_tab);
        initNavigation(getString(R.string.send_msg_fail), null, getString(R.string.screen), 0, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendMsgFailActivity.this, SelectTimePeriodActivity.class);
                intent.putExtra(Constant.EXTRA_SELECT_POSITION, selectPos);
                startActivityForResult(intent, SELECT_TIME_REQ);
            }
        });

        vp = (ViewPager) findViewById(R.id.send_fail_vp);
        tl = (TabLayout) findViewById(R.id.send_fail_tablayout);

        fragmentList = new ArrayList<>();
        fragmentList.add(new WorkRecordFragment());
        fragmentList.add(new LocalFragment());

        vp.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), mTitles, fragmentList));
        tl.setupWithViewPager(vp);
        tl.post(new Runnable() {
            @Override
            public void run() {
                CommonUtils.setIndicator(tl, 40, 40);
            }
        });

        appBarLayout = findById(R.id.app_bar);
        setViewPagerTouchListener(vp, appBarLayout);
        setTabLayoutTabSelectedListener(tl, appBarLayout);
    }

    private static int selectPos = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_TIME_REQ) {
                int position = data.getIntExtra(Constant.EXTRA_SELECT_POSITION, 0);
                selectPos = position;
//                toast("返回的是" + position);
            }
        }
    }


    @Override
    public void onCreateBinding() {

    }

}
