package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.sttech.supervisor.R;
import com.sttech.supervisor.ui.adapter.TabPagerAdapter;
import com.sttech.supervisor.ui.fragment.endproject.EndProjectFragment;
import com.sttech.supervisor.ui.fragment.endproject.GrabSingleEndFragment;
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
    }

    private String[] mTitles = null;
    private ViewPager vp;
    private TabLayout tl;
    private List<Fragment> fragmentList;
    private AppBarLayout appBarLayout;

    private void initView() {
        mTitles = getResources().getStringArray(R.array.end_project_tab);
        initNavigation(getString(R.string.the_end_project));
        vp = (ViewPager) findViewById(R.id.end_project_vp);
        tl = (TabLayout) findViewById(R.id.end_project_tablayout);

        fragmentList = new ArrayList<>();
        fragmentList.add(new EndProjectFragment());
        fragmentList.add(new GrabSingleEndFragment());

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

    @Override
    public void onCreateBinding() {

    }

}
