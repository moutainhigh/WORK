<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhml" xml:lang="cn" lang="cn">
<head>
<title>首页</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">

</head>

<body class="easyui-layout">
	<div data-options="region:'north',href:'<%=basePath %>layout/north.jsp'" style="height:76px;background:#0079c3;padding:0px;overflow: hidden;" class="logo"></div>
	
    <div data-options="region:'west',title:'功能导航',href:'<%=basePath %>layout/west.jsp'" style="width: 200px;overflow-y: auto;overflow-x: hidden;"></div> 
	
	<div data-options="region:'center',title:'欢迎使用系统',href:'<%=basePath %>layout/center.jsp'" style="overflow: hidden;"></div>
</body>
</html>
