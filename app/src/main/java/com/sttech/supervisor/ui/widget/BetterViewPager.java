package com.sttech.supervisor.ui.widget;

/**
 * function:
 * Created by 韦国旺 on 2017/5/24 0024.
 * Copyright (c) 2017 北京联龙博通 All Rights Reserved.
 */
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * {@linkplain #setChildrenDrawingOrderEnabledCompat(boolean)} does some reflection that isn't needed.
 * And was making view creation time rather large. So lets override it and make it better!
 */
public class BetterViewPager extends ViewPager {

    public BetterViewPager(Context context) {
        super(context);
    }

    public BetterViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setChildrenDrawingOrderEnabled(boolean enabled) {
        //super.setChildrenDrawingOrderEnabled(enabled);
        setChildrenDrawingCacheEnabled(enabled);
    }

//    @Override
//    public void setChildrenDrawingOrderEnabledCompat(boolean enable) {
//        setChildrenDrawingOrderEnabled(enable);
//    }
}
