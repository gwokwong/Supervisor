package com.sttech.supervisor.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sttech.supervisor.R;
import com.sttech.supervisor.utils.ManagerActivity;
import com.zhy.autolayout.AutoLayoutActivity;

import es.dmoral.toasty.Toasty;

/**
 * function :
 * Created by 韦国旺 on 2017/5/2.
 * Copyright (c) 2017 All Rights Reserved.
 */


public abstract class TActivity extends AutoLayoutActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateBinding();
    }

    public abstract void onCreateBinding();

    public <T extends View> T findById(int resId) {
        return (T) findViewById(resId);
    }

    public void toast(String msg) {
        Toasty.success(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    /**
     * @param title
     * @param leftBtnTxt
     * @param rightBtnTxt
     * @param leftImgRes
     * @param rightImgRes
     * @param leftOnClickListener
     * @param rightOnClickListener
     */

    //// TODO: 2017/5/3  逻辑未处理完毕
    public void initNavigation(String title, String leftBtnTxt, String rightBtnTxt, int leftImgRes, int rightImgRes, View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener) {
        TextView textView = findById(R.id.title);
        if (textView != null && !TextUtils.isEmpty(title)) {
            textView.setText(title);
        }

        if (leftOnClickListener != null) {
            Button backBtn = findById(R.id.back_btn);
            backBtn.setVisibility(View.VISIBLE);
            backBtn.setOnClickListener(leftOnClickListener);
        }

        if (rightOnClickListener != null) {
            Button rightBtn = findById(R.id.right_btn);
            TextView rightTxt = findById(R.id.right_txt);
            if (!TextUtils.isEmpty(rightBtnTxt) && rightImgRes == 0) {
                rightTxt.setVisibility(View.VISIBLE);
                rightTxt.setText(rightBtnTxt);
                rightTxt.setOnClickListener(rightOnClickListener);
            }

            if (rightImgRes != 0) {
                rightBtn.setVisibility(View.VISIBLE);
                rightBtn.setBackgroundResource(rightImgRes);
                rightBtn.setOnClickListener(rightOnClickListener);
            }
        }

    }


    /**
     * @param title
     */
    public void initNavigation(String title) {
        initNavigation(title, null, null, 0, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }

    /**
     * @param title
     * @param leftOnClickListener
     */
    public void initNavigation(String title, View.OnClickListener leftOnClickListener) {
        initNavigation(title, null, null, 0, 0, leftOnClickListener, null);
    }

    /**
     * @param title
     * @param SingleTitle
     */
    public void initNavigation(String title, boolean SingleTitle) {
        if (!SingleTitle) {
            initNavigation(title, null, null, 0, 0, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            }, null);
        } else {
            initNavigation(title, null, null, 0, 0, null, null);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        ManagerActivity.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ManagerActivity.getInstance().removeActivity(this);
    }

    /**
     * @param viewPager
     * @param appBarLayout
     */
    public void setViewPagerTouchListener(ViewPager viewPager, final AppBarLayout appBarLayout) {
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        appBarLayout.setExpanded(false);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * @param tl
     * @param appBarLayout
     */
    public void setTabLayoutTabSelectedListener(TabLayout tl, final AppBarLayout appBarLayout) {
        tl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()

        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                appBarLayout.setExpanded(false);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
