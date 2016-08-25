package com.scxh.java1503.page;

public class PageUtil {
	private int totalRecord; // 总记录数 1
	private int pageSize;// 每页大小  20
	private int pageCount; // 总页数
	private int currentPage; // 当前页

	public PageUtil(int totalRecord, int pageSize, int pageNo) {
		this.totalRecord = totalRecord;
		this.pageSize = pageSize;
		initPageCount();
		this.currentPage = setCurrentPage(pageNo);
	}

	/**
	 * 当前页边界检查：  防止传入pageNo大于总页数
	 * @param pageNo
	 * @return
	 */
	public int setCurrentPage(int pageNo){
		return pageNo > pageCount? pageCount:pageNo;
	}
	
	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public void initPageCount() {
		pageCount = totalRecord / pageSize; // 100/20= 5 101/20=5+1
		int mod = totalRecord % pageSize;
		if (mod != 0) {
			pageCount = pageCount + 1;
		}
	}

	/**
	 * 获取分页开始下标 current=1 0-19 , 2 20-39 , 3 40-59 ,4 60-79, 5 80-99 , 6 100-101
	 * 
	 * @return
	 */
	public int getFormIndex() {
		return (currentPage - 1) * pageSize;
	}

	/**
	 * 获取分页结束下标
	 * 
	 * @return
	 */
	public int getEndIndex() {
		return Math.min(currentPage * pageSize, totalRecord);
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}
