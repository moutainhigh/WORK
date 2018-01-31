<%@page import="com.xlkfinance.bms.web.constant.Constants"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="com.xlkfinance.bms.web.shiro.ShiroUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	request.setAttribute("basePath", basePath);
	
	request.setAttribute("currUser", (ShiroUser) SecurityUtils.getSubject().getSession().getAttribute(Constants.SHIRO_USER));
%>