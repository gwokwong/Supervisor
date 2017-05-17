package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.ProjectListDto;
import com.sttech.supervisor.ui.adapter.TabPagerAdapter;
import com.sttech.supervisor.ui.fragment.EndFragment;
import com.sttech.supervisor.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * function : 已结束项目
 * Created by 韦国旺 on 2017/5/3.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class EndProjectActivity extends TActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, EndProjectActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_end_project);
        initView();
        initData();
    }

    private List<ProjectListDto> projectList1, projectList2;
    private static int position = 0;

    private void initData() {
        projectList2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            projectList2.add(new ProjectListDto("高档办公室装修" + (++position), "办公室", "陈先生", "广东省深圳市宝安区坂田大道", 15, "抢单结束2"));
        }
        mHandler.sendEmptyMessageDelayed(1, 500);

        projectList1 = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            projectList1.add(new ProjectListDto("写字楼装潢" + (++position), "写字楼", "韦先生", "广东省深圳市宝安区南海大道", 23, "竣工"));
        }
        mHandler.sendEmptyMessageDelayed(0, 500);


    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    fragment1.setProjectList(projectList1);
                    break;
                case 1:
                    fragment2.setProjectList(projectList2);
                    break;
            }

        }
    };

    private String[] mTitles = null;
    private ViewPager vp;
    private TabLayout tl;
    private List<Fragment> fragmentList;
    private AppBarLayout appBarLayout;
    private EndFragment fragment1, fragment2;

    private void initView() {
        mTitles = getResources().getStringArray(R.array.end_project_tab);
        initNavigation(getString(R.string.the_end_project));
        vp = (ViewPager) findViewById(R.id.end_project_vp);
        tl = (TabLayout) findViewById(R.id.end_project_tablayout);

        fragmentList = new ArrayList<>();
        fragment1 = new EndFragment();
        fragment2 = new EndFragment();

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);

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


}
