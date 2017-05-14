package com.sttech.supervisor.ui.fragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sttech.supervisor.R;
import com.sttech.supervisor.utils.CommonUtils;

import es.dmoral.toasty.Toasty;

/**
 * function :
 * Created by 韦国旺 on 2017/5/2.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class TFragment extends Fragment {


    /**
     * Fragment当前状态是否可见
     */

    protected boolean isVisible;


    @Override

    public void setUserVisibleHint(boolean isVisibleToUser) {

// TODO Auto-generated method stub

        super.setUserVisibleHint(isVisibleToUser);
        Logger.d("isVisibleToUser");


        if (isVisibleToUser) {

            Logger.d("isVisibleToUser");
            isVisible = true;

            onVisible();

        } else {

            isVisible = false;

            onInvisible();

        }

    }


    /**
     * 可见
     */

    protected void onVisible() {

        lazyLoad();

    }


    /**
     * 不可见
     */

    protected void onInvisible() {


    }


    /**
     * 延迟加载    子类必须重写此方法
     */

    protected void lazyLoad() {

    }


    /**
     * 设置标题
     *
     * @param view
     * @param title
     */
    public void initNatigation(View view, String title) {
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
