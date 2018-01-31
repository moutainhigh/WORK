<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<title>500页面</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->


</head>
<body class="gray-bg">
 <div class="middle-box text-center animated fadeInDown">
  <h1>500</h1>
  <h3 class="font-bold" style="color: red;">${errorMsg }</h3>

  <div class="error-desc">
    <c:if test="${loginUser eq null }">
   您可以返回主页看看 <br />
    <a href="${ctx}index.action" target="" class="btn btn-primary m-t">主页</a>
   </c:if>
  </div>
 </div>
</body>
</html>