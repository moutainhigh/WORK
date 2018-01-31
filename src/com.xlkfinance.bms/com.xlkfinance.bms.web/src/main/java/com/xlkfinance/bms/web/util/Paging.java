package com.xlkfinance.bms.web.util;

public class Paging<T> {
	private int rows;
	private int page;
	private T vo;
	private String pid;

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public T getVo() {
		return vo;
	}

	public void setVo(T vo) {
		this.vo = vo;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
