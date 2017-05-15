package com.sttech.supervisor.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.sttech.supervisor.Constant;
import com.sttech.supervisor.MyApp;
import com.sttech.supervisor.R;
import com.sttech.supervisor.utils.SpUtils;

/**
 * function : 启动界面
 * Created by 韦国旺 on 2017/5/7.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class StartActivity extends TActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_start);
        initData();
    }

    private void initData() {
        boolean isFirst = (boolean) SpUtils.get(Constant.SP_KEY_IS_FIRST, true);
        boolean isLogin = (boolean) SpUtils.get(Constant.SP_KEY_IS_LOGIN, false);
        Logger.d("isLogin->"+isLogin);
        if (isFirst &&!isLogin) {
            SignInActivity.start(this);
            finish();
        } else {
            MainActivity.start(this);
            finish();
        }
    }

    @Override
    public void onCreateBinding() {


    }
}
