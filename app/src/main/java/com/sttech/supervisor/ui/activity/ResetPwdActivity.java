package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.sttech.supervisor.R;
import com.sttech.supervisor.db.MobileLoginResult;
import com.sttech.supervisor.http.HttpManager;
import com.sttech.supervisor.http.callback.OnResultCallBack;
import com.sttech.supervisor.http.subscriber.HttpSubscriber;
import com.sttech.supervisor.ui.fragment.dialog.DialogFragmentHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @BindView(R.id.mobile_phone_number)
    TextView mobilePhone;

    @BindView(R.id.passwordWrapper)
    TextInputLayout passwordLayout;

    @BindView(R.id.passwordAgainWrapper)
    TextInputLayout passwordAgainLayout;


    @BindView(R.id.sign_in_btn)
    Button signInBtn;

    private void initView() {
        initNavigation(getString(R.string.reset_pwd_title));

        signInBtn.setEnabled(false);
        passwordLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwdAgain = passwordAgainLayout.getEditText().getText().toString();
                signInBtn.setEnabled(pwdAgain.length() > 5 && s.length() > 5);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordAgainLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = passwordLayout.getEditText().getText().toString();
                signInBtn.setEnabled(pwd.length() > 5 && s.length() > 5);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    String mobilePhoneNumber;

    private void initData() {
        mobilePhoneNumber = getIntent().getStringExtra(KEY_MOBILE_PHONE);
        mobilePhone.setText(TextUtils.isEmpty(mobilePhoneNumber) ? "" : mobilePhoneNumber);
    }


    public void resetOnClick(View view) {
        if (view.getId() == R.id.sign_in_btn) {
            doSignIn();
        } else if (view.getId() == R.id.back_btn) {
            finish();
        }
    }


    private void doSignIn() {
        String password = passwordLayout.getEditText().getText().toString();
        String passwordAgain = passwordAgainLayout.getEditText().getText().toString();
        if (!password.equals(passwordAgain)) {
            toaste("两次密码输入不一致");
            return;
        }

        HttpSubscriber httpSubscriber = new HttpSubscriber(new OnResultCallBack<String>() {

            @Override
            public void onStart() {
                mDialogFragment = DialogFragmentHelper.showProgress(ResetPwdActivity.this.getSupportFragmentManager(), "loading...");
            }

            @Override
            public void onSuccess(String data) {
                toast(data);
                //TODO 做和登录一样的操作
                Delete.table(MobileLoginResult.class);
                MobileLoginResult result = new MobileLoginResult();
                result.setUserName("韦国旺");
                result.setUserId("10010");
                result.setCellPhone("18681529205");
                result.save();

                Intent intent = new Intent(ResetPwdActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(int code, String errorMsg) {
//                toaste(errorMsg);
                onSuccess(""); //TODO test

            }

            @Override
            public void onCompleted() {
                mDialogFragment.dismiss();
                onSuccess("模拟登录成功");
            }
        });

        HttpManager.getInstance().resetPwd(httpSubscriber, mobilePhoneNumber, password);


    }
}
