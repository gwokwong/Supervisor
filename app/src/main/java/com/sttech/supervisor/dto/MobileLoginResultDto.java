package com.sttech.supervisor.dto;

public class MobileLoginResultDto extends AbstractMobileDto{

	private static final long serialVersionUID = 2627125330959605475L;
	
	String userId;			// 用户主键
	String accountText;		// 用户账号
	String userName;		// 用户名称, 可能为空
	String cellPhone;		// 手机
	String avatarId;		// 头像id
	
	String avatarPath;			// 头像网络地址
	String provinceName;		// 省份
	String cityName;			// 城市
	String districtName;		// 区域
	int roleCode;			// 权限代码
	
	String loginTime;			// 字符串, yyyyMMddhhmmss 格式. 
	String provinceCode;
	String cityCode;
	String districtCode;	
	
	int loginTry = 0;			// 尝试登录次数. 保留字段, 在未来增强安全性时应用. 

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountText() {
		return accountText;
	}

	public void setAccountText(String accountText) {
		this.accountText = accountText;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
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

	public int getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(int roleCode) {
		this.roleCode = roleCode;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public int getLoginTry() {
		return loginTry;
	}

	public void setLoginTry(int loginTry) {
		this.loginTry = loginTry;
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

}
