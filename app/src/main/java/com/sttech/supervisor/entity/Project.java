package com.sttech.supervisor.entity;

/**
 * function :
 * Created by 韦国旺 on 2017/5/3.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class Project {

    private String title;

    private String workRecordCount;

    private String type;

    private String manager;

    private String address;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWorkRecordCount() {
        return workRecordCount;
    }

    public void setWorkRecordCount(String workRecordCount) {
        this.workRecordCount = workRecordCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Project(String title, String workRecordCount, String type, String manager, String address) {
        this.title = title;
        this.workRecordCount = workRecordCount;
        this.type = type;
        this.manager = manager;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Project{" +
                "title='" + title + '\'' +
                ", workRecordCount='" + workRecordCount + '\'' +
                ", type='" + type + '\'' +
                ", manager='" + manager + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
