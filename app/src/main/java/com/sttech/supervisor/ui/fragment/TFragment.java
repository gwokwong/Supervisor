package com.sttech.supervisor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sttech.supervisor.R;
import com.sttech.supervisor.utils.CommonUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * function :
 * Created by 韦国旺 on 2017/5/2.
 * Copyright (c) 2017 All Rights Reserved.
 */


public abstract class TFragment extends Fragment {


    View rootView;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (rootView == null) {
//            rootView = inflater.inflate(provideContentViewId(), container, false);
//            initView(rootView);
//        }
//        return rootView;
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(provideContentViewId(), container, false);
            initView(rootView);
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public abstract int provideContentViewId();


    public void init() {

    }

    public abstract void initView(View rootView);

    public void initListener() {

    }

    public void initData() {

    }


    /**
     * 设置标题
     *
     * @param view
     * @param title
     */
    public void initNavigation(View view, String title) {
        TextView textView = (TextView) view.findViewById(R.id.title);
        if (textView != null && !TextUtils.isEmpty(title)) {
            textView.setText(title);
        }
    }

    /**
     * toast info
     *
     * @param msg
     */
    public void toastInfo(String msg) {
        Toasty.info(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param dpValue
     */
    public int dip2px(float dpValue) {
        return CommonUtils.dip2px(getActivity(), dpValue);
    }


}
