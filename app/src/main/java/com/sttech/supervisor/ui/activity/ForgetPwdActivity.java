package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;

import com.sttech.supervisor.R;
import com.sttech.supervisor.utils.CommonUtils;
import com.sttech.supervisor.utils.StrUtils;

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
        initView();
    }


    private TextInputLayout phoneNumberWrapper, verificationCodeWrapper;
    private AppCompatButton sendVerificationBtn;

    private void initView() {
        initNavigation(getString(R.string.forget_pwd));
        sendVerificationBtn = findById(R.id.send_verification_code);
        phoneNumberWrapper = findById(R.id.phone_number_wrapper);
        verificationCodeWrapper = findById(R.id.verification_code_wrapper);
    }

    private CountDownTimer timer = new CountDownTimer(15000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            sendVerificationBtn.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            sendVerificationBtn.setEnabled(true);
            sendVerificationBtn.setText("发送验证码");
        }
    };


    public void forgetOnClick(View view) {
        switch (view.getId()) {
            case R.id.send_verification_code:
                sendVerificationCode();
                timer.start();
                sendVerificationBtn.setEnabled(false);
                break;
            case R.id.next_btn:
                doNext();
                break;
        }

    }


    private void sendVerificationCode() {
        CommonUtils.hideKeyboard(ForgetPwdActivity.this);
        String phoneNumber = phoneNumberWrapper.getEditText().getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            toaste("手机号码不能为空");
        } else if (!StrUtils.validatePhoneNumber(phoneNumber)) {
            toaste("手机号码输入不正确");
        } else {
            verificationCodeWrapper.getEditText().setText("123456");
        }
//        String password = verificationCodeWrapper.getEditText().getText().toString();


    }

    private void doNext() {
        ResetPwdActivity.start(this, "18681529205");

    }


}
