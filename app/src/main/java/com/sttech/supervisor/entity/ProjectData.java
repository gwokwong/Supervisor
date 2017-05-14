package com.sttech.supervisor.entity;

import java.util.List;

/**
 * function :
 * Created by 韦国旺 on 2017/5/5.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class ProjectData {

    private String headImgUrl;
    private String name;
    private String type;
    private String date;
    private String time;
    private String body;


    private List<LocalMedia> projectMedias;

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public List<LocalMedia> getProjectMedias() {
        return projectMedias;
    }

    public void setProjectMedias(List<LocalMedia> projectMedias) {
        this.projectMedias = projectMedias;
    }

    public ProjectData(String headImgUrl, String name, String type, String date, String time, String body, List<LocalMedia> projectMedias) {
        this.headImgUrl = headImgUrl;
        this.name = name;
        this.type = type;
        this.date = date;
        this.time = time;
        this.body = body;
        this.projectMedias = projectMedias;
    }


    private int sendFailTime; //发送失败次数

    public int getSendFailTime() {
        return sendFailTime;
    }

    public void setSendFailTime(int sendFailTime) {
        this.sendFailTime = sendFailTime;
    }

    public ProjectData(String headImgUrl, String name, String type, String date, String time, String body, List<LocalMedia> projectMedias, int sendFailTime) {
        this.headImgUrl = headImgUrl;
        this.name = name;
        this.type = type;
        this.date = date;
        this.time = time;
        this.body = body;
        this.projectMedias = projectMedias;
        this.sendFailTime = sendFailTime;
    }
}
