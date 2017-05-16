package com.sttech.supervisor.dto;

import java.io.Serializable;

/**
 * 项目详情dto. 
 * 
 * @author cys
 *
 */
public class ProjectDetailDto implements Serializable{

	private static final long serialVersionUID = 1351392800596539489L;
	
	// ----------- 简介信息 
	String title;
	String createTime;				// 时间, yyyy-MM-dd hh:mm:ss 格式
	String customerManagerName;		// 客户经理名称

	// ----------- 装修信息(在tab显示)
	String projectFullAddress;				// 项目地址-全部拼接起来
	String decorationCategoryLabel;			// 装修类型
	String decorationLevelLabel;			// 装修档次
	String officeBuildingLevelLabel;		// 楼盘属性
	String constructionSpaceLabel;			// 施工面积
	
	String projectRequirementLabel;			// 项目需求
	String budgetForProjectLabel;			// 装修预算
	String projectPeriodLabel;				// 项目周期
	String qualityPriceRatioLabel;			// 性价比
	String decorationStyleLabel;			// 风格要求
	
	String designRequirementLabel;			// 设计要求
	String environmentProtectionLabel;		// 环保要求

	// ----------- 客户信息(在tab显示)
	String companyName;					// 公司名称
	int companyStartupFund;				// 注册资金(单位万人民币)
	String companyOwner;				// 公司法人
	String runTimeLabel;				// 经营期限从xx到xx
	String stockStructure;				// 股东结构
	
	String companyScopeLabel;			// 经营范围
	String companyStageLabel;			// 企业阶段
	String companyManCountLabel;		// 企业员工

	// ----------- 客户分析(在tab显示)
	String decorationCountLabel;		// 装修次数
	String aboutDesignLabel;			// (关于)设计方面 
	String aboutMaterialLabel;			// (关于)材料方面 
	String aboutConstructionLabel;		// (关于)施工方面
	String aboutProcessLabel;			// (关于)流程方面
	
	String aboutGongzhuangLabel;		// (关于)公装网
	String aboutDecorationCompanyLabel;	// 关于装修公司
	String buyForLabel;					// 采购标准
	String belongToIndustry;			// 行业说明(所属行业)
	
	
	// ----------- 其他属性	
	String categoryLabel;		// 类别名称
	String projectManagerName;	// 项目经理名称
	String fullProjectAddress;	// 全部项目地址
	String provinceName;		// 省份名称
	String cityName;			// 城市名称
	
	String districtName;		// 区域名称
	String provinceCode;
	String cityCode;
	String districtCode;	
	int workNoteCount;			// 工作记录数量

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategoryLabel() {
		return categoryLabel;
	}

	public void setCategoryLabel(String categoryLabel) {
		this.categoryLabel = categoryLabel;
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

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public int getWorkNoteCount() {
		return workNoteCount;
	}

	public void setWorkNoteCount(int workNoteCount) {
		this.workNoteCount = workNoteCount;
	}
	
}
