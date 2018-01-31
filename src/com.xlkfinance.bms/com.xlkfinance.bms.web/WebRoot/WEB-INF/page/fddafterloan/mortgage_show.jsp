<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<style type="text/css">
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
	});
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
        <table id="storageinfo_grid"  title="入库资料清单" class="easyui-datagrid" style="height: 270px; width: auto;"
	     data-options="
	            url: '<%=basePath%>bizProjectMortgageController/queryStorageInfo.action?projectId=${ projectMortgage.projectId}',
			    method: 'POST',
			    rownumbers: true,
			    pagination: true,
			    sortOrder:'asc',
			    remoteSort:false,
			    idField: 'pid',
			    fitColumns:true"
	    >
	     <!-- 表头 -->
	     <thead>
	      <tr>
	       <th data-options="field:'pid',checkbox:true" />
	       <th data-options="field:'fileName'" align="center" halign="center">资料名称</th>
	       <th data-options="field:'registerTime',formatter:convertDate" align="center" halign="center">入库时间</th>
	       <th data-options="field:'fileDesc'" align="center" halign="center">说明</th>
	       <th data-options="field:'createName'" align="center" halign="center">操作人</th>
	      </tr>
	     </thead>
	    </table>
     <div class="easyui-panel" title="出库申请" data-options="collapsible:true" >
       <div style="height: auto; line-height: 35px;">
        <table style="padding: 10px;" cellpadding="0" cellspacing="0">
         <tr>
          <td class="label_right">签收人：</td>
          <td class="label_center"><input class="easyui-textbox" value="${projectMortgage.issueUserName }" editable="false"/></td>
          <td class="label_right">签收材料：</td>
          <td class="label_center"><input class="easyui-textbox" value="${projectMortgage.issueMaterial }"  editable="false"/></td>
          <td class="label_right">签收日期：</td>
          <td class="label_center"><input class="easyui-datebox"  editable="false" value="${projectMortgage.issueTime }"/></td>
         </tr>
        </table>
       </div>
      </div>
     <div class="easyui-panel" title="出具注销材料" data-options="collapsible:true" >
       <div style="height: auto; line-height: 35px;">
        <table style="padding: 10px;" cellpadding="0" cellspacing="0">
         <tr>
          <td class="label_right">签收人：</td>
          <td class="label_center"><input class="easyui-textbox" value="${projectMortgage.cancelUserName }" editable="false"/></td>
          <td class="label_right">签收材料：</td>
          <td class="label_center"><input class="easyui-textbox" value="${projectMortgage.cancelMaterial }"  editable="false"/></td>
          <td class="label_right">签收日期：</td>
          <td class="label_center"><input class="easyui-datebox"  editable="false" value="${projectMortgage.cancelTime }"/></td>
         </tr>
        </table>
       </div>
      </div>
     <div class="easyui-panel" title="退证登记" data-options="collapsible:true" >
       <div style="height: auto; line-height: 35px;">
        <table style="padding: 10px;" cellpadding="0" cellspacing="0">
         <tr>
          <td class="label_right">签收人：</td>
          <td class="label_center"><input class="easyui-textbox" value="${projectMortgage.returnUserName }" editable="false"/></td>
          <td class="label_right">签收材料：</td>
          <td class="label_center"><input class="easyui-textbox" value="${projectMortgage.returnMaterial }"  editable="false"/></td>
          <td class="label_right">签收日期：</td>
          <td class="label_center"><input class="easyui-datebox"  editable="false" value="${projectMortgage.returnTime }"/></td>
         </tr>
        </table>
       </div>
      </div>
 </div>
</body>
</html>
