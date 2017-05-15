package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.orhanobut.logger.Logger;
import com.sttech.supervisor.Constant;
import com.sttech.supervisor.MyApp;
import com.sttech.supervisor.R;
import com.sttech.supervisor.db.LocationInfo;
import com.sttech.supervisor.http.HttpManager;
import com.sttech.supervisor.http.callback.OnResultCallBack;
import com.sttech.supervisor.http.entity.MobileLoginResultDto;
import com.sttech.supervisor.http.subscriber.HttpSubscriber;
import com.sttech.supervisor.ui.fragment.dialog.DialogFragmentHelper;
import com.sttech.supervisor.utils.Base64Utils;
import com.sttech.supervisor.utils.SpUtils;
import com.sttech.supervisor.utils.StrUtils;

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
        initView();
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


    private TextInputLayout usernameWrapper, passwordWrapper;

    private void initView() {
        usernameWrapper = findById(R.id.usernameWrapper);
        passwordWrapper = findById(R.id.passwordWrapper);
//        usernameWrapper.setHint("Username");
//        passwordWrapper.setHint("Password");

    }

    @Override
    public void onCreateBinding() {

    }

    public void signInOnClick(View view) {
        if (view.getId() == R.id.sign_in) {
            doValidate();
        } else if (view.getId() == R.id.forget_pwd) {
            ForgetPwdActivity.start(SignInActivity.this);
        }
    }

    private DialogFragment mDialogFragment;

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }


    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 验证基本信息
     */
    private void doValidate() {
//        hideKeyboard();
//        String username = usernameWrapper.getEditText().getText().toString();
//        String password = passwordWrapper.getEditText().getText().toString();
//        Logger.d("username password" + username + "|" + password);
//        if (TextUtils.isEmpty(username)) {
//            toaste("手机号码不能为空");
//        } else if (!StrUtils.validatePhoneNumber(username)) {
//            Logger.d("!validateUserName");
//            toaste("手机号码输入错误");
//            usernameWrapper.setError("Not a valid email address!");
//        } else if (TextUtils.isEmpty(password)) {
//            toaste("密码不能为空");
//        } else if (!validatePassword(password)) {
//            Logger.d("!validatePassword");
//            toaste("密码输入不正确");
//            passwordWrapper.setError("Not a valid password!");
//        } else {
//            Logger.d("!setErrorEnabled");
//            usernameWrapper.setErrorEnabled(false);
//            passwordWrapper.setErrorEnabled(false);
//            doLogin();
//        }

        doLogin();

    }

    private void doLogin() {
//        mDialogFragment = DialogFragmentHelper.showProgress(getSupportFragmentManager(), "正在加载中");
//        h.sendEmptyMessageDelayed(0, 3000);
        HttpSubscriber httpSubscriber = new HttpSubscriber(new OnResultCallBack<String>() {

            @Override
            public void onStart() {
                Logger.d("onStart");
                mDialogFragment = DialogFragmentHelper.showProgress(getSupportFragmentManager(), "正在登录中...");
            }

            @Override
            public void onSuccess(String data) {
                Logger.d("onSuccess" + data);
                toast(data);

                //设计唯一的记录用户已登录
                SpUtils.put(Constant.SP_KEY_IS_FIRST, false);
//                MyApp.getInstance().setLogin(true);

                SpUtils.put(Constant.SP_KEY_IS_LOGIN, true);




                MainActivity.start(SignInActivity.this);
                finish();
            }

            @Override
            public void onError(int code, String errorMsg) {
                Logger.d("onError" + errorMsg);
                toaste(errorMsg);

            }

            @Override
            public void onCompleted() {
                Logger.d("onCompleted");
                mDialogFragment.dismiss();
            }
        });

        HttpManager.getInstance().login(httpSubscriber, "opt_0215", Base64Utils.encryptBase64("123456"));


//        HttpSubscriber httpSubscriber = new HttpSubscriber(new OnResultCallBack<Daily>() {
//                            @Override
//                            public void onSuccess(Daily daily) {
//                                Logger.d("onSuccess" + daily.toString());
//                                locationInfo.tryTimes = 82;
//                                locationInfo.update();
//                            }
//
//                            @Override
//                            public void onError(int code, String errorMsg) {
//                                Logger.d("onError: code:" + code + "  errorMsg:" + errorMsg);
//                            }
//                        });
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
