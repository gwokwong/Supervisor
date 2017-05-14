package com.sttech.supervisor.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.orhanobut.logger.Logger;

/**
 * function : 上传资料任务
 * Created by 韦国旺 on 2017/5/7.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class ProjectDataJob extends Job {

    public static final int PRIORITY = 1;

    public ProjectDataJob() {
        super(new Params(PRIORITY).persist().requireNetwork());
    }

    @Override
    public void onAdded() {
        Logger.d("ProjectDataJob->onAdded");
    }


    @Override
    public void onRun() throws Throwable {
        Logger.d("执行ProjectDataJobonRun");
        //查询本地数据库,将所有上传没有成功的项目资料都再次上传，策略，按时间排序先上传最新未上传成功的资料，再寻找以前的资料
        //每次最多启动5个请求。

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        Logger.d("LocationJob->shouldReRunOnThrowable");
        return null;
    }
}
