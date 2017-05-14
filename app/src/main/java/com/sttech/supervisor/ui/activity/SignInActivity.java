package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.sttech.supervisor.Constant;
import com.sttech.supervisor.MyApp;
import com.sttech.supervisor.R;
import com.sttech.supervisor.db.LocationInfo;
import com.sttech.supervisor.ui.fragment.dialog.DialogFragmentHelper;
import com.sttech.supervisor.utils.SpUtils;

import java.util.ArrayList;

/**
 * function :登录界面
 * Created by 韦国旺 on 2017/5/2.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class SignInActivity extends TActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_signin);
        LocationInfo info = new LocationInfo();
        ArrayList<LocationInfo> list = new ArrayList<>();
//        for (int i = 80; i < 119; i++) {
//            info = new LocationInfo();
//            info.name = "李四";
//            info.latitude = 1231.3;
//            info.lontitude = 123.44;
//            info.time = System.currentTimeMillis();
//            info.tryTimes = i;
//            list.add(info);
//        }
////实时保存，马上保存
//        new SaveModelTransaction<>(ProcessModelInfo.withModels(list)).onExecute();

//        TaskManager.getInstance().init(this).setAlarm();

    }

    @Override
    public void onCreateBinding() {

    }

    public void signInOnClick(View view) {
        if (view.getId() == R.id.sign_in) {
            doSignIn();
        } else if (view.getId() == R.id.forget_pwd) {
            ForgetPwdActivity.start(SignInActivity.this);
        }
    }

    private DialogFragment mDialogFragment;

    /**
     * 登录操作
     */
    private void doSignIn() {
        mDialogFragment = DialogFragmentHelper.showProgress(getSupportFragmentManager(), "正在加载中");
        h.sendEmptyMessageDelayed(0, 3000);
    }

    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDialogFragment.dismiss();
            SpUtils.put(Constant.SP_KEY_IS_FIRST, false);
            MyApp.getInstance().setLogin(true);
            MainActivity.start(SignInActivity.this);
            finish();
        }
    };


}
