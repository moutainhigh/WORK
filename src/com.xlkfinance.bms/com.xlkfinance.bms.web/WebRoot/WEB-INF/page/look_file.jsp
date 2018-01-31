<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<%@ include file="/common.jsp"%>
<title>查看文件</title>
<style type="text/css">
</style>
</head>

<body class="easyui-layout">
<h5>${fileName}</h5>
  <img alt="" src="<%=basePath%>orgPartnerInfoController/download.action?path=${path}">
</body>
</html>