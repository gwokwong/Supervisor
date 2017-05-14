package com.sttech.supervisor.task;

/**
 * function : 后台任务基本信息配置
 * Created by 韦国旺 on 2017/5/7.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class Const {

    /**
     * 延迟启动时间
     */
    public static int WAIT_TIME = 1 * 1000;

    /**
     * 定位上传任务时间间隔
     */
    public static int LOCATION_DELAY_TIME = 5 * 60 * 1000;


    /**
     * 项目资料上传任务时间间隔
     */
    public static int PROJECT_DELAY_TIME = 10 * 60 * 1000;

    /**
     * 定位记录上次更新时间的key
     */
    public static String LOCATION_UPDATE_TIME_KEY = "location_update_time";


    /**
     * 项目资料上次更新时间的key
     */
    public static String PROJECT_UPDATE_TIME_KEY = "project_update_time";

    /**
     * 是否启用Service
     */
    public static boolean isUseService = true;

}
