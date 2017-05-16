package com.sttech.supervisor.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目上传资料
 * 
 * @author cys
 *
 */
public class ProjectAttachDto extends AbstractMobileDto {
	
	private static final long serialVersionUID = 8049948800970545865L;
	
	// ---- 上传者信息
	String createUserId;			// 上传者id
	String createUserName;			// 上传者名字
	String createTime;				// 上传时间 yyyy-MM-dd hh:mm:ss 格式
	String createUserAvatarPath;	// 创建者头像路径
	
	// ---- 其他信息
	String categoryLabel;			// 类别, 如工作记录, 量房时间
	String content;					// 上传文字内容
	List<ImageDto> imageList = new ArrayList<ImageDto>();	// 图片列表
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public List<ImageDto> getImageList() {
		return imageList;
	}
	public void setImageList(List<ImageDto> imageList) {
		this.imageList = imageList;
	}
	public void addImage(String id, String path) {
		ImageDto dto = new ImageDto(id, path);
		this.imageList.add(dto);
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserAvatarPath() {
		return createUserAvatarPath;
	}
	public void setCreateUserAvatarPath(String createUserAvatarPath) {
		this.createUserAvatarPath = createUserAvatarPath;
	}
	public String getCategoryLabel() {
		return categoryLabel;
	}
	public void setCategoryLabel(String categoryLabel) {
		this.categoryLabel = categoryLabel;
	}
}
