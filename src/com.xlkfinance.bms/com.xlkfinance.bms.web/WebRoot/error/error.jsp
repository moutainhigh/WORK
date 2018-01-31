<!DOCTYPE html>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isErrorPage="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

  <head>   
    <title>Error Page</title>

	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="Server error"/>
	<meta http-equiv="description" content="This is error page."/>
  </head>
  
  <body>
    <H1><font color=red>服务器错误!</font></H1>
    <h6>${exception.message} - 详细请查看后台日志。</h6>
	<%--    <%=exception.getCause() %>--%>
	<%--    <h6>详细:<br/> ${exceptionStack}</h6>--%>
  </body>
</html>
