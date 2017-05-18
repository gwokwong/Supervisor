package com.sttech.supervisor.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sttech.supervisor.Constant;
import com.sttech.supervisor.MyApp;
import com.sttech.supervisor.R;
import com.sttech.supervisor.ui.fragment.dialog.CommonDialogFragment;
import com.sttech.supervisor.ui.fragment.dialog.DialogFragmentHelper;
import com.sttech.supervisor.ui.fragment.dialog.IDialogResultListener;
import com.sttech.supervisor.utils.SpUtils;

import java.util.ArrayList;

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
        getPermission();
    }

    private void initData() {
        boolean isFirst = (boolean) SpUtils.get(Constant.SP_KEY_IS_FIRST, true);
        boolean isLogin = MyApp.getInstance().isLogin();
        if (isFirst || !isLogin) {
            SignInActivity.start(this);
            finish();
        } else {
            MainActivity.start(this);
            finish();
        }
    }

    private final int SDK_PERMISSION_REQUEST = 127;

    @TargetApi(23)
    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            //将读写权限设为必须权限，如果没有授予，则每次都申请
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            } else {
                //权限都通过直接加载数据
                initData();
            }
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(final int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SDK_PERMISSION_REQUEST) {
            //是否有权限没通过
            boolean isNoPermission = false;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    isNoPermission = true;
                    break;
                }
            }
            if (!isNoPermission) {
                initData();
            } else {
                DialogFragmentHelper.showConfirmDialog(getSupportFragmentManager(), "授权失败，是否重新授权", new IDialogResultListener<Integer>() {
                    @Override
                    public void onDataResult(Integer result) {
                        if (result == DialogInterface.BUTTON_NEGATIVE) {
                            //取消
                            StartActivity.this.finish();
                        } else if (result == DialogInterface.BUTTON_POSITIVE) {
                            //确认
                            getPermission();
                        }

                    }
                }, false, new CommonDialogFragment.OnDialogCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                });
            }

        }

    }
}
