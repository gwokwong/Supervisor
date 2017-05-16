package com.sttech.supervisor.dto;

public class UploadResponseDto {
	String id;		// 上传附件id
	String path;	// 网络路径
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}	
}
