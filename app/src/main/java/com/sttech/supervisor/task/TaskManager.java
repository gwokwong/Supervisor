package com.sttech.supervisor.task;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Date;

/**
 * function : 后台任务管理类
 * Created by 韦国旺 on 2017/5/7.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class TaskManager {

    private static Context mContext;
    private static TaskManager instance;

    private TaskManager() {

    }


    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
//        mContext = context;
        return instance;
    }

    public TaskManager init(Context context) {
        mContext = context;
        return instance;
    }


    public void setLocationAlarm() {
//        Logger.d("setAlarm");
        long delay = (new Date().getTime()) + Const.LOCATION_DELAY_TIME;
        long fireDelay = delay - Const.WAIT_TIME;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences.edit().putLong(Const.LOCATION_UPDATE_TIME_KEY, delay).apply();
        Intent startIntent = new Intent(mContext, LocationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 1, startIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        int ALARM_TYPE = AlarmManager.RTC_WAKEUP;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            alarmManager.setExact(ALARM_TYPE, fireDelay, pendingIntent);
//        } else {
//            alarmManager.set(ALARM_TYPE, fireDelay, pendingIntent);
//        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(ALARM_TYPE, fireDelay, pendingIntent);
            } else {
                alarmManager.set(ALARM_TYPE, fireDelay, pendingIntent);
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(ALARM_TYPE, fireDelay, pendingIntent);
        }
    }

    /**
     * 
     */
    public void setProjectAlarm() {
//        Logger.d("setAlarm");
        long delay = (new Date().getTime()) + Const.PROJECT_DELAY_TIME;
        long fireDelay = delay - Const.WAIT_TIME;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences.edit().putLong(Const.PROJECT_UPDATE_TIME_KEY, delay).apply();
        Intent startIntent = new Intent(mContext, ProjectReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 1, startIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        int ALARM_TYPE = AlarmManager.RTC_WAKEUP;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            alarmManager.setExact(ALARM_TYPE, fireDelay, pendingIntent);
//        } else {
//            alarmManager.set(ALARM_TYPE, fireDelay, pendingIntent);
//        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(ALARM_TYPE, fireDelay, pendingIntent);
            } else {
                alarmManager.set(ALARM_TYPE, fireDelay, pendingIntent);
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(ALARM_TYPE, fireDelay, pendingIntent);
        }
    }
}
