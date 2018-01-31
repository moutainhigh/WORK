<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="/config.jsp"%>
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/default/easyui.css?v=${v}" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/icon.css?v=${v}"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/mis.css?v=${v}"></link>

<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/index.css?v=${v}" />
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.min.js?v=${v}"></script> 
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.easyui.min.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.easyui.patch.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/datagrid-detailview.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/project_contract.js?v=${v}"></script>

<script type="text/javascript" src="${ctx}/p/xlkfinance/module/system/mootools-1.4.5.js?v=${v}"></script>

<!-- 自己定义的样式和JS扩展 -->
<link rel="stylesheet" href="${ctx}/p/xlkfinance/css/syCss.css?v=${v}" type="text/css"></link>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/system/cneter.js?v=${v}" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js?v=${v}?version=20161031"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/syUtil.js?v=${v}" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/common.js?v=${v}?version=20161123" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/tool.js?v=${v}" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/accounting.js?v=${v}" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/jquery.PrintArea.js?v=${v}" charset="utf-8"></script>
<!-- 一些弹出页面的js add yql -->
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeCommon.js?v=${v}" charset="utf-8"></script>

<!-- 自定义样式 -->
<link rel="stylesheet" href="${ctx}/p/xlkfinance/css/customer/css/style.css?v=${v}" type="text/css"></link>

<!-- easyui portal插件 -->
<link rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-portal/portal.css?v=${v}" type="text/css"></link>
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-portal/jquery.portal.js?v=${v}" charset="utf-8"></script>

<!-- 表单验证   jquery.validate-->
<script type="text/javascript" src="${ctx}/l/jqueryValidation/dist/jquery.validate.js?v=${v}" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/l/jqueryValidation/dist/localization/messages_zh.js?v=${v}" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/validater.js?v=${v}" charset="utf-8"></script>

<!-- zTree树形菜单 -->
<link rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/zTree/css/zTreeStyle/zTreeStyle.css?v=${v}" type="text/css">
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/zTree/js/jquery.ztree.core-3.5.js?v=${v}"></script> 


<!-- My97日历控件 --> 
<link rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/My97DatePicker/skin/whyGreen/datepicker.css?v=${v}" type="text/css"> 
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/My97DatePicker/WdatePicker.js?v=${v}"></script>

<!-- 省市区级联 -->
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/sitedata.bas.js?v=${v}" charset="utf-8"></script>

<!-- zhengxiu 重写样式 -->
<link rel="stylesheet" href="${ctx}/p/xlkfinance/css/rewritecss.css?v=${v}" type="text/css"></link>

<!-- 多文件上传 -->
<link href="${ctx}/p/xlkfinance/plug-in/uploadify/css/uploadify.css?v=${v}" rel="stylesheet" type="text/css" />
<script src="${ctx}/p/xlkfinance/plug-in/uploadify/jquery.uploadify.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
//声明basePath
var BASE_PATH = "${basePath}";

 			
</script>

 
