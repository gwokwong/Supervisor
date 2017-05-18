package com.sttech.supervisor.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.orhanobut.logger.Logger;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sttech.supervisor.MyApp;
import com.sttech.supervisor.db.LocationInfo;
import com.sttech.supervisor.db.LocationInfo_Table;
import com.sttech.supervisor.map.LocationService;

import java.util.List;

import static com.sttech.supervisor.task.Const.LOCATION_DELAY_TIME;

/**
 * function : 定位任务
 * Created by 韦国旺 on 2017/5/7.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class LocationJob extends Job {

    public static final int PRIORITY = 1;
    private LocationService locationService;

    public LocationJob() {
        super(new Params(PRIORITY).persist().requireNetwork());
    }

    @Override
    public void onAdded() {
//        Logger.d("LocationJob->onAdded");
    }

    private static int TRY_TIMES = 0;
    private static int MAX_TRY_TIME = 5;

    BDLocationListener mListener = null;

    private long last_location_time = 0;


    @Override
    public void onRun() throws Throwable {
        Logger.d("执行LocationJobonRun");
        locationService = ((MyApp) MyApp.getmContext()).locationService;
        mListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(final BDLocation location) {
//                Logger.d("onReceiveLocation22");
                if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                    location.getCity();
                    location.getLatitude();
                    StringBuffer sb = new StringBuffer(256);
                    sb.append("\nlatitude : ");// 纬度
                    sb.append(location.getLatitude());
                    sb.append("\nlontitude : ");// 经度
                    sb.append(location.getLongitude());
                    sb.append("\ncity : ");// 城市
                    sb.append(location.getCity());
                    Logger.d("LocationJob定位成功" + sb);
                    Logger.t("logo").d("定位success");
                    locationService.unregisterListener(mListener);
                    locationService.destroy();

                    //TODO 判断和上次发送成功相差是否超过5分钟，如果超过就可以再次发送
                    List<LocationInfo> locationInfos = SQLite.select().from(LocationInfo.class).where().orderBy(LocationInfo_Table.time, false).queryList();  //false 为降序
                    long now = System.currentTimeMillis();
                    if (locationInfos.size() > 0) {
                        long time = now - locationInfos.get(0).time;
                        if (time > LOCATION_DELAY_TIME - 1100) {
                            LocationInfo locationInfo = new LocationInfo();
                            locationInfo.name = "机主";
                            locationInfo.tryTimes = 0;
                            locationInfo.latitude = location.getLatitude();
                            locationInfo.lontitude = location.getLongitude();
                            locationInfo.time = now;
                            locationInfo.save();
                        }
                    } else {
                        LocationInfo locationInfo = new LocationInfo();
                        locationInfo.name = "机主";
                        locationInfo.tryTimes = 0;
                        locationInfo.time = now;
                        locationInfo.latitude = location.getLatitude();
                        locationInfo.lontitude = location.getLongitude();
                        locationInfo.save();
                    }

                } else {
                    Logger.d("LocationJob定位失败" + TRY_TIMES);
                    if (++TRY_TIMES >= MAX_TRY_TIME) {
                        locationService.unregisterListener(mListener);
                        locationService.destroy();
                        //TODO  定位失败记录在数据库 最后一次尝试的时候记录
                        List<LocationInfo> locationInfos = SQLite.select().from(LocationInfo.class).where().orderBy(LocationInfo_Table.time, false).queryList();  //false 为降序
                        long now = System.currentTimeMillis();
                        long time = now - locationInfos.get(0).time;
                        if (time > LOCATION_DELAY_TIME - 1100) {
                            LocationInfo locationInfo = new LocationInfo();
                            locationInfo.name = "机主";
                            locationInfo.tryTimes = 0;
                            locationInfo.time = now;
                            locationInfo.lontitude = 0;
                            locationInfo.latitude = 0;
                            locationInfo.save();
                        }
                    }
                }
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        };
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return RetryConstraint.RETRY;
    }
}
