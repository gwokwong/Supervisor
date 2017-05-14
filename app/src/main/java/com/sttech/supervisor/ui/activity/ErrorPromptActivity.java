package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sttech.supervisor.R;

/**
 * function : 发送资料失败
 * Created by 韦国旺 on 2017/5/4.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class ErrorPromptActivity extends TActivity {

    public static final String KEY_ERROR_CODE = "error_code";
    public static final String KEY_ERROR_MSG = "error_msg";

    public static void start(Context context) {
        Intent intent = new Intent(context, ErrorPromptActivity.class);
        context.startActivity(intent);
    }


    public static void start(Context context, String errorCode, String errorMsg) {
        Intent intent = new Intent(context, ErrorPromptActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ERROR_CODE, errorCode);
        bundle.putString(KEY_ERROR_MSG, errorMsg);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void onCreateBinding() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_error_prompt);
        initNavigation(getString(R.string.send_data_fail));
    }

    public void onClick(View view) {
        SendMsgFailActivity.start(this);
    }

}
