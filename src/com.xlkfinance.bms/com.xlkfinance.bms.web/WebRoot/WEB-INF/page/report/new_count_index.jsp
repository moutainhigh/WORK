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
<script type="text/javascript">
 		var rePaymentMonthId = '${rePaymentMonthId}';
		var includeWt = '${includeWt}';
		var mothOrWekNum = '${mothOrWekNum}';
	//重置按钮
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset')
	}
	// 项目名称format
	function formatProjectName(value, row, index) {
		var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
		return va;
	}
	function cxportFinancialStatistics() {
		
		$.ajax({url : BASE_PATH
							+ 'templateFileController/checkFileUrl.action',
					data:{templateName : 'XZXQMB'},
					dataType:'json',
					success : function(data) {
						if (data == 1) {
							var projectName =$("#projectName").textbox('getValue');
							var projectNumber =$("#projectNumber").textbox('getValue');
							var pmUserName =$("#pmUserName").textbox('getValue');
							
							window.location.href ="${basePath}reportController/newCountExportList.action?chooseMonthOrWeek="+mothOrWekNum+"&includeWt="+includeWt+"&rePaymentMonthId="+rePaymentMonthId+
									"&pmUserName="+pmUserName+"&projectNumber="+projectNumber+"&projectName="+projectName+"&page=-1";
						} else {
							alert('新增详情的导出模板不存在....，请上传模板后重试');
						}
					}
				})
	}
	$(document).ready(function(){
	});

</script>
<title>新增列表</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/newCountList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
	       <td width="80" align="right">项目名称：</td>
	       <td>
	        <input class="easyui-textbox" name="projectName" id="projectName"/>
	       </td>
	        <td width="80" align="right">项目编号：</td>
	        <td>
	        <input class="easyui-textbox" name="projectNumber" id="projectNumber"/>
	       </td>
	       <td width="80" align="right">业务经理：</td>
	        <td>
	        <input class="easyui-textbox" name="pmUserName" id="pmUserName"/>
	       </td>
       </tr>
       
       <tr>
        <td colspan="10"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>

      </table>
     </div>
    </form>
    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
    <shiro:hasAnyRoles name="EXPORT_FORECLOSURE_NEW,ALL_BUSINESS">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportFinancialStatistics()">导出</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="新增详情列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>reportController/newCountList.action?includeWt='+${includeWt}+'&mothOrWekNum='+${mothOrWekNum}+'&rePaymentMonthId='+rePaymentMonthId,
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: false,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'projectId',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
	      <th data-options="field:'projectId',checkbox:true"></th>
	      <th data-options="field:'projectName',formatter:formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
	      <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th>
	      <th data-options="field:'businessSourceStr',sortable:true" align="center" halign="center">机构名称</th>
	      <th data-options="field:'loanMoney',sortable:true,formatter:formatMoney" align="center" halign="center">贷款金额</th>
	      <th data-options="field:'applyLoanDate',sortable:true,formatter:convertDate" align="center" halign="center">贷款申请时间</th>
	      <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">业务经理</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
