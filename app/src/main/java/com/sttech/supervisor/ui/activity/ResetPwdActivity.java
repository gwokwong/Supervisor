package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sttech.supervisor.MyApp;
import com.sttech.supervisor.R;

/**
 * function : 重置密码
 * Created by 韦国旺 on 2017/5/2.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class ResetPwdActivity extends TActivity {

    public static final String KEY_MOBILE_PHONE = "_mobilePhone";

    public static void start(Context context, String mobilePhone) {
        Intent intent = new Intent(context, ResetPwdActivity.class);
        Bundle args = new Bundle();
        args.putString(KEY_MOBILE_PHONE, mobilePhone);
        intent.putExtras(args);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reset_pwd);
        initView();
        initData();
    }


    private TextView mobilePhone;

    private void initView() {
        mobilePhone = findById(R.id.mobile_phone_number);
        initNavigation(getString(R.string.reset_pwd_title));
    }


    private void initData() {
        String mobilePhoneNumber = getIntent().getStringExtra(KEY_MOBILE_PHONE);
        mobilePhone.setText(TextUtils.isEmpty(mobilePhoneNumber) ? "138 8888 8888" : mobilePhoneNumber);

    }

    @Override
    public void onCreateBinding() {


    }

    public void resetOnClick(View view) {
        if (view.getId() == R.id.sign_in_btn) {
            doSignIn();
        } else if (view.getId() == R.id.back_btn) {
            finish();
        }
    }

    private void doSignIn() {
        Intent intent = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
