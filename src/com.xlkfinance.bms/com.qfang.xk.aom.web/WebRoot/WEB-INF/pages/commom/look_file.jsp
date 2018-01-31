<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>查看文件</title>
<%@ include file="../../../common.jsp"%>
<link rel="shortcut icon" href="favicon.ico">
</head>
<body class="gray-bg">
 <div class="wrapper wrapper-content">
  <div class="row animated fadeInRight">
   <div class="ibox float-e-margins">
    <div class="ibox-title">
     <h5>${fileName }</h5>
    </div>
    <div class="ibox-content">
     <img alt="" src="${ctx }download.action?path=${path}">
    </div>
   </div>
  </div>
 </div>
</body>
</html>