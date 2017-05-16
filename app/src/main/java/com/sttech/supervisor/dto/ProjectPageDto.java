package com.sttech.supervisor.dto;


/**
 * 项目详情. 包括两部分: 上半部分的项目信息, 下半部分的上传资料列表
 * 
 * @author cys
 *
 */
public class ProjectPageDto {
	ProjectDetailDto detailDto;
	PageDto<ProjectAttachDto> attachList;
	public ProjectDetailDto getDetailDto() {
		return detailDto;
	}
	public void setDetailDto(ProjectDetailDto detailDto) {
		this.detailDto = detailDto;
	}
	public PageDto<ProjectAttachDto> getAttachList() {
		return attachList;
	}
	public void setAttachList(PageDto<ProjectAttachDto> attachList) {
		this.attachList = attachList;
	}
	
}
