package com.sttech.supervisor.dto;

import java.io.Serializable;

/**
 * 用于项目列表的dto
 *
 * @author cys
 */
public class ProjectListDto implements Serializable {

    private static final long serialVersionUID = 1351392800596539489L;

    String title;
    String decorationCategoryLabel;        // 装修类型名称
    String projectManagerName;    // 项目经理名称
    String fullProjectAddress;    // 全部项目地址
    int workNoteCount;            // 工作记录数量

    String statusLabel;            // 状态. 如竣工.

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecorationCategoryLabel() {
        return decorationCategoryLabel;
    }

    public void setDecorationCategoryLabel(String decorationCategoryLabel) {
        this.decorationCategoryLabel = decorationCategoryLabel;
    }

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public String getFullProjectAddress() {
        return fullProjectAddress;
    }

    public void setFullProjectAddress(String fullProjectAddress) {
        this.fullProjectAddress = fullProjectAddress;
    }

    public int getWorkNoteCount() {
        return workNoteCount;
    }

    public void setWorkNoteCount(int workNoteCount) {
        this.workNoteCount = workNoteCount;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public ProjectListDto(String title, String decorationCategoryLabel, String projectManagerName, String fullProjectAddress, int workNoteCount, String statusLabel) {
        this.title = title;
        this.decorationCategoryLabel = decorationCategoryLabel;
        this.projectManagerName = projectManagerName;
        this.fullProjectAddress = fullProjectAddress;
        this.workNoteCount = workNoteCount;
        this.statusLabel = statusLabel;
    }
}
