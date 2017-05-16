package com.sttech.supervisor.dto;

public class ImageDto extends AbstractMobileDto{

	private static final long serialVersionUID = -383685267409902197L;
	
	String id;
	String path;
	
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
	public ImageDto(String id, String path) {
		super();
		this.id = id;
		this.path = path;
	}
	public ImageDto() {
		super();
	}
}
