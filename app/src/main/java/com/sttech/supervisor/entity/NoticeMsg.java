package com.sttech.supervisor.entity;

import java.io.Serializable;

/**
 * function :
 * Created by 韦国旺 on 2017/5/5.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class NoticeMsg implements Serializable {
    private String type;
    private String date;
    private String time;
    private String body;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NoticeMsg(String type, String date, String time, String body) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.body = body;
    }
}
