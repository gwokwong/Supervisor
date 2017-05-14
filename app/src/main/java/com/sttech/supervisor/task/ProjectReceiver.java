package com.sttech.supervisor.task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.sttech.supervisor.MyApp;

import java.util.Date;

/**
 * function : 任务广播
 * Created by 韦国旺 on 2017/5/7.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class ProjectReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        if (Const.isUseService) {
            Intent i = new Intent(context, ProjectSe.class);
            context.startService(i);

        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            long fireTime = sharedPreferences.getLong(Const.PROJECT_UPDATE_TIME_KEY, (new Date()).getTime());
            long fireDelay = (fireTime - (new Date().getTime()) > 0) ? fireTime - (new Date().getTime()) : 0;

            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    TaskManager.getInstance().setProjectAlarm();
                }
            }, fireDelay);

            MyApp.getInstance().getJobManager().addJobInBackground(new ProjectDataJob());  //填写项目资料上传任务

        }
    }

}
