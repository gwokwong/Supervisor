package com.sttech.supervisor.dto;

import java.io.Serializable;

public class MessageDto implements Serializable {

    private static final long serialVersionUID = 4172642858278819507L;

    String title;
    String createTime;                // 时间, yyyy-MM-dd hh:mm:ss 格式
    String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageDto(String title, String createTime, String content) {
        this.title = title;
        this.createTime = createTime;
        this.content = content;
    }
}
