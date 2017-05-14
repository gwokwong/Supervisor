package com.sttech.supervisor.event;

/**
 * function :
 * Created by 韦国旺 on 2017/5/14.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class MeEvent {

    //我的消息
    public static final int TYPE_MY_MSG = 0;

    //未发送成功
    public static final int TYPE_SEND_FAIL = 1;


    public int type;
    public int number;

    public MeEvent(int type, int number) {
        this.type = type;
        this.number = number;
    }
}
