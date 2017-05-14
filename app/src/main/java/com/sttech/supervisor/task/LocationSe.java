package com.sttech.supervisor.task;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.sttech.supervisor.MyApp;

/**
 * function :上传位置的service
 * Created by 韦国旺 on 2017/5/7.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class LocationSe extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("启动LocationService");
        MyApp.getInstance().getJobManager().addJobInBackground(new LocationJob());
        TaskManager.getInstance().setLocationAlarm();
        return super.onStartCommand(intent, flags, startId);
    }
}
