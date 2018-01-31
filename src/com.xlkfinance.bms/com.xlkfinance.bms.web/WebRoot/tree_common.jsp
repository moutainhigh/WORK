<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="/config.jsp"%>
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/icon.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/mis.css"></link>

<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/index.css" />
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.easyui.patch.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/datagrid-detailview.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/project_contract.js"></script>

<script type="text/javascript" src="${ctx}/p/xlkfinance/module/system/mootools-1.4.5.js"></script>

<!-- 自己定义的样式和JS扩展 -->
<link rel="stylesheet" href="${ctx}/p/xlkfinance/css/syCss.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/system/cneter.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/syUtil.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/common.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/tool.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/accounting.js" charset="utf-8"></script>
<!-- 一些弹出页面的js add yql -->
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeCommon.js" charset="utf-8"></script>

<!-- 自定义样式 -->
<link rel="stylesheet" href="${ctx}/p/xlkfinance/css/customer/css/style.css" type="text/css"></link>

<!-- easyui portal插件 -->
<link rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-portal/portal.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>

<!-- 表单验证   jquery.validate-->
<script type="text/javascript" src="${ctx}/l/jqueryValidation/dist/jquery.validate.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/l/jqueryValidation/dist/localization/messages_zh.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/validater.js" charset="utf-8"></script>

<!-- zTree树形菜单 -->
<link rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/zTree/js/jquery.ztree.core-3.5.js"></script> 
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/zTree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/zTree/js/jquery.ztree.exedit-3.5.js"></script>

<!-- My97日历控件 --> 
<link rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/My97DatePicker/skin/whyGreen/datepicker.css" type="text/css"> 
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/My97DatePicker/WdatePicker.js"></script>

<!-- 省市区级联 -->
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/sitedata.bas.js" charset="utf-8"></script>


<script type="text/javascript">
//声明basePath
var BASE_PATH = "${basePath}";

 			
</script>

 
