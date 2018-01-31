<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.cms.commontag/core" prefix="comm"%>
<%
   String path = request.getContextPath();
   String ctx = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
   request.setAttribute("ctx", ctx);
%>
<link href="${ctx }css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="${ctx }css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="${ctx }css/animate.min.css" rel="stylesheet">
<link href="${ctx }css/style.min862f.css?v=4.1.0" rel="stylesheet">
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
 <script type="text/javascript" src="${ctx }js/jquery.min.js?v=2.1.4"></script>
<!--[if lt IE 9]>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <![endif]-->

<!-- jquery-form.js -->
<script type="text/javascript" src="${ctx }js/jquery-form.js"></script>
<script type="text/javascript" src="${ctx }js/accounting.js" ></script>
<script type="text/javascript" src="${ctx }js/common.js?v=201707171" ></script>
<script type="text/javascript" src="${ctx }js/fieldFormat.js" ></script>
<script type="text/javascript" src="${ctx }js/jquery.formatCurrency-1.4.0.js" ></script>
<!-- bootstrap -->
<script src="${ctx }js/bootstrap.min.js?v=3.3.6"></script>
<!-- validate表单校验 -->
<script src="${ctx}js/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx}js/plugins/validate/messages_zh.min.js"></script>
<script src="${ctx}js/plugins/validate/validate-methods.js"></script>
<!-- layer弹层组件 -->
<script src="${ctx}js/plugins/layer/layer.min.js"></script>
<!-- jqgrid-->
<link href="${ctx}css/plugins/jqgrid/ui.jqgridffe4.css?0820" rel="stylesheet">
<!-- 文件工具js，包括文件上传下载等-->
<script type="text/javascript" src="${ctx }js/fileUtil.js" ></script>
<!-- 多文件上传 -->
<link href="${ctx}js/plugins/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" />
<script src="${ctx}js/plugins/uploadify/jquery.uploadify.js" type="text/javascript"></script>
