/**
 * @Title: SysConfig.java
 * @Package com.xlkfinance.bms.web.util
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月10日 下午6:11:37
 * @version V1.0
 */
package com.xlkfinance.bms.web.util;

public class WebSysConfig {
	// //////////////WEB应用配置////////////
	// 文件根目录
	private String uploadFileRoot;
	private int maxFileSize;
	private int maxRequestSize;
	// /////////////Thrift配置/////////////////
	private String thriftServerIp;
	private int thriftServerPort;
	private String rpcPackage;
	private int thriftTimeout;
	private String fileType;
	private String aomFilePath;
	
	private String aomPackage;
	
	public String[] getFileType() {
		return fileType.split(",");
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(int maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public int getMaxRequestSize() {
		return maxRequestSize;
	}

	public void setMaxRequestSize(int maxRequestSize) {
		this.maxRequestSize = maxRequestSize;
	}

	public String getUploadFileRoot() {
		return uploadFileRoot;
	}

	public void setUploadFileRoot(String uploadFileRoot) {
		this.uploadFileRoot = uploadFileRoot;
	}

	public String getThriftServerIp() {
		return thriftServerIp;
	}

	public void setThriftServerIp(String thriftServerIp) {
		this.thriftServerIp = thriftServerIp;
	}

	public int getThriftServerPort() {
		return thriftServerPort;
	}

	public void setThriftServerPort(int thriftServerPort) {
		this.thriftServerPort = thriftServerPort;
	}

	public String getRpcPackage() {
		return rpcPackage;
	}

	public void setRpcPackage(String rpcPackage) {
		this.rpcPackage = rpcPackage;
	}

	public int getThriftTimeout() {
		return thriftTimeout;
	}

	public void setThriftTimeout(int thriftTimeout) {
		this.thriftTimeout = thriftTimeout;
	}

	public String getAomPackage() {
		return aomPackage;
	}

	public void setAomPackage(String aomPackage) {
		this.aomPackage = aomPackage;
	}

	public String getAomFilePath() {
		return aomFilePath;
	}

	public void setAomFilePath(String aomFilePath) {
		this.aomFilePath = aomFilePath;
	}
	
}
