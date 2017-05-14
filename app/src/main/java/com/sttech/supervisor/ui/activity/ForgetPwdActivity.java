package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sttech.supervisor.R;

/**
 * function : 忘记密码
 * Created by 韦国旺 on 2017/5/2.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class ForgetPwdActivity extends TActivity {


    public static void start(Context context) {
        Intent intent = new Intent(context, ForgetPwdActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreateBinding() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forget_pwd);
        initNavigation(getString(R.string.forget_pwd));
    }

    public void forgetOnClick(View view) {
        switch (view.getId()) {
            case R.id.send_verification_code:
                sendVerificationCode();
                break;
            case R.id.next_btn:
                doNext();
                break;
        }
    }


    private void sendVerificationCode() {

    }

    private void doNext() {
        ResetPwdActivity.start(this, "18681529205");
    }


}
