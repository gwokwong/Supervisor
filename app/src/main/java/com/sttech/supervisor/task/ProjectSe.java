package com.sttech.supervisor.task;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.sttech.supervisor.MyApp;

/**
 * function : 上传任务记录的service
 * Created by 韦国旺 on 2017/5/7.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class ProjectSe extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Logger.d("ProjectSe");
        MyApp.getInstance().getJobManager().addJobInBackground(new ProjectDataJob());
        TaskManager.getInstance().setProjectAlarm();
        return super.onStartCommand(intent, flags, startId);
    }
}
