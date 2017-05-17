package com.sttech.supervisor.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.orhanobut.logger.Logger;
import com.sttech.supervisor.MyApp;
import com.sttech.supervisor.db.LocationInfo;
import com.sttech.supervisor.event.MeEvent;
import com.sttech.supervisor.map.LocationService;

import de.greenrobot.event.EventBus;

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
//        Logger.d("执行LocationJobonRun");
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
//                    Logger.d("LocationJob定位成功" + sb);

                    LocationInfo locationInfo = new LocationInfo();
                    locationInfo.latitude = location.getLatitude();
                    locationInfo.lontitude = location.getLongitude();
                    locationInfo.time = System.currentTimeMillis();
                    locationInfo.save();


                    //TODO 发送之前的位置记录
//                    List<LocationInfo> locationInfos = new Select().from(LocationInfo.class).where(LocationInfo_Table.tryTimes.lessThan(100)).queryList();

                    EventBus.getDefault().post(new MeEvent(MeEvent.TYPE_MY_MSG, 15));
//
//                    if (locationInfos.size() > 0) {
//                        Logger.d("locationInfos.size() > 0)->" + locationInfos.size());
//                        final LocationInfo locationInfo = locationInfos.get(0);
//                        HttpSubscriber httpSubscriber = new HttpSubscriber(new OnResultCallBack<Daily>() {
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
//
//                        HttpManager.getInstance().getDailyWithCache(httpSubscriber, false);
//                    } else {
//                        Logger.d("locationInfos.size()为0");
//
//                    }
                    locationService.unregisterListener(mListener);
                    locationService.destroy();
                } else {
                    //连续几次定位都失败，尝试最大次数后记录到数据库
                    last_location_time = System.currentTimeMillis();
                    Logger.d("LocationJob定位失败" + TRY_TIMES);
                    if (++TRY_TIMES >= MAX_TRY_TIME) {
                        locationService.unregisterListener(mListener);
                        locationService.destroy();
                        //TODO  定位失败记录在数据库 最后一次尝试的时候记录
                        if (System.currentTimeMillis() - last_location_time > 5 * 60 * 1000) {
                            LocationInfo locationInfo = new LocationInfo();
                            locationInfo.name = "机主";
                            locationInfo.tryTimes = 0;
                            locationInfo.time = System.currentTimeMillis();
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
        Logger.d("LocationJob->shouldReRunOnThrowable");
        return RetryConstraint.RETRY;
    }
}
