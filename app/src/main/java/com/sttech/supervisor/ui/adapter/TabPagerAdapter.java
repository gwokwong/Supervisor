package com.sttech.supervisor.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * function :TabLayout使用viewpager的适配器
 * Created by 韦国旺 on 2017/5/6.
 * Copyright (c) 2017 All Rights Reserved.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private List<Fragment> fragmentList;

    public TabPagerAdapter(FragmentManager fm, String[] mTitles, List<Fragment> fragmentList) {
        super(fm);
        this.mTitles = mTitles;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int paramInt) {
        return fragmentList.get(paramInt);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
