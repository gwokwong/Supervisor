package com.sttech.supervisor.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * function : 位置信息记录表
 * Created by 韦国旺 on 2017/5/7.
 * Copyright (c) 2017 All Rights Reserved.
 */
@ModelContainer
@Table(database = AppDatabase.class)
public class LocationInfo extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public String name; //人员名称
    @Column
    public double latitude; // 纬度
    @Column
    public double lontitude;  // 经度
    @Column
    public long time;  //时间
    @Column
    public int tryTimes; //尝试次数

}
