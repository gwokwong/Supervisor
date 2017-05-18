package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.sttech.supervisor.R;
import com.sttech.supervisor.http.HttpManager;
import com.sttech.supervisor.http.callback.OnResultCallBack;
import com.sttech.supervisor.http.subscriber.HttpSubscriber;
import com.sttech.supervisor.ui.fragment.dialog.DialogFragmentHelper;
import com.sttech.supervisor.utils.CommonUtils;
import com.sttech.supervisor.utils.StrUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * function : 忘记密码
 * Created by 韦国旺 on 2017/5/2.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class ForgetPwdActivity extends TActivity {

    private final static int TIME_TOTAL = 30000;


    public static void start(Context context) {
        Intent intent = new Intent(context, ForgetPwdActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forget_pwd);
        ButterKnife.bind(this);
        initView();
    }


    @BindView(R.id.send_verification_code)
    AppCompatButton sendVerificationBtn;

    @BindView(R.id.next_btn)
    AppCompatButton nextBtn;

    @BindView(R.id.phone_number_wrapper)
    TextInputLayout phoneNumberWrapper;

    @BindView(R.id.verification_code_wrapper)
    TextInputLayout verificationCodeWrapper;


    private void initView() {

        initNavigation(getString(R.string.forget_pwd));
        sendVerificationBtn.setEnabled(false);
        nextBtn.setEnabled(false);

        verificationCodeWrapper.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNumber = phoneNumberWrapper.getEditText().getText().toString();
                nextBtn.setEnabled(s.length() == 6 && phoneNumber.length() == 11);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phoneNumberWrapper.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendVerificationBtn.setEnabled(s.length() == 11);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private CountDownTimer timer = new CountDownTimer(TIME_TOTAL, 1000) {

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
                break;
            case R.id.next_btn:
                doNext();
                break;
        }

    }

    private DialogFragment mDialogFragment;

    private void sendVerificationCode() {
        CommonUtils.hideKeyboard(ForgetPwdActivity.this);
        String phoneNumber = phoneNumberWrapper.getEditText().getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            toaste("手机号码不能为空");
        } else if (!StrUtils.validatePhoneNumber(phoneNumber)) {
            toaste("手机号码输入不正确");
        } else {
            timer.start();
            sendVerificationBtn.setEnabled(false);
            HttpSubscriber httpSubscriber = new HttpSubscriber(new OnResultCallBack<String>() {

                @Override
                public void onStart() {
                    mDialogFragment = DialogFragmentHelper.showProgress(ForgetPwdActivity.this.getSupportFragmentManager(), "loading...");
                }

                @Override
                public void onSuccess(String data) {
                    toast(data);
//                    sendVerificationBtn.setEnabled(false);
                    verificationCodeWrapper.getEditText().setText("123456");
                }

                @Override
                public void onError(int code, String errorMsg) {
                    toaste(errorMsg);

                }

                @Override
                public void onCompleted() {
                    mDialogFragment.dismiss();
                }
            });

            HttpManager.getInstance().sendVerificationCode(httpSubscriber, phoneNumber);


        }
    }

    private void doNext() {
        ResetPwdActivity.start(this, "18681529205");
        //验证验证码是否正确
    }


}
