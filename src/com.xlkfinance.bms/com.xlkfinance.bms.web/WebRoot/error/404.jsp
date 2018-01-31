<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>404</title>
</head>
<body>
    <h1>404 错误的访问路径！</h1>

    <dl class="texts">
        <dt>提示：您所查找的页面不存在,可能已被删除或您输错了网址!</dt>
    </dl>
</body>
</html>
