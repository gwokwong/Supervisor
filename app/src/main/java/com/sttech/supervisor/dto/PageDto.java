package com.sttech.supervisor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageDto<T> implements Serializable {

	private static final long serialVersionUID = 4547642217911831397L;
		
	private int currentPageNum;					//如第1页，第2页。。。
	private int pageSize = 10;						//每页有多少条记录
	private long pageCount;						//多少页
	private long totalCount;					//总记录数
	private List<T> dataList = new ArrayList<T>();
	public int getCurrentPageNum() {
		return currentPageNum;
	}
	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum == 0 ? 1 : currentPageNum;
	}	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	public void addData(T t){
		this.dataList.add(t);
	}
	
}
