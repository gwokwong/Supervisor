package com.sttech.supervisor.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * function : 数据库配置
 * Created by 韦国旺 on 2017/5/7.
 * Copyright (c) 2017 All Rights Reserved.
 */


@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    //数据库名称
    public static final String NAME = "Supervisor";
    //数据库版本号
    public static final int VERSION = 1;
}
