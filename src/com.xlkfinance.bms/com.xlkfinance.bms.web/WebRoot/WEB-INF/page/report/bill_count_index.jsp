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

	var dateId = '${dateId}';
	var mothOrWekNum = '${mothOrWekNum}';
	var approvalPerson = '${approvalPerson}';
	var approvalStep = '${approvalStep}';
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
		$.ajax({	url : BASE_PATH
							+ 'templateFileController/checkFileUrl.action',
					data:{templateName : 'YWSPTZMXTJ'},
					dataType:'json',
					success : function(data) {
						if (data == 1) {
							var projectName =$("#projectName").textbox('getValue');												   
							window.location.href = "${basePath}reportController/billCountDetailExportList.action?dateId="+dateId+"&chooseMonthOrWeek="+mothOrWekNum+"&approvalPerson="+approvalPerson+"&approvalStep="+approvalStep+"&projectName="+projectName+"&page=-1";
						} else {
							alert('业务审批台账明细导出模板不存在....，请上传模板后重试');
						}
					}
				}) 
	}
	
	var businessApprovalDetailList = {
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
				return va;
			}
		}

</script>
<title>业务审批台账业务笔数详情</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/billCountList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
      <tr>
	     <td width="80" align="right">项目名称：</td>
	      <td><input class="easyui-textbox"   name="projectName" id="projectName"/></td>
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
    <shiro:hasAnyRoles name="EXPORT_BUSINESS_BILL_COUNT,ALL_BUSINESS">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportFinancialStatistics()">导出</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="业务审批台账--业务笔数列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
     
     	url: '<%=basePath%>reportController/billCountList.action?mothOrWekNum=${mothOrWekNum}&dateId=${dateId}&approvalPerson=${approvalPerson}&approvalStep=${approvalStep}',
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
       <th data-options="field:'projectName',formatter:businessApprovalDetailList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
       <th data-options="field:'businessSourceStr',sortable:true" align="center" halign="center">业务来源</th>
       
       <th data-options="field:'customerName',sortable:true" align="center" halign="center">客户名称</th>
       <th data-options="field:'realLoan',sortable:true,formatter:formatMoney" align="center" halign="center">放款金额</th>
       <th data-options="field:'businessCatelog',sortable:true" align="center" halign="center">业务类型</th>
       
       <th data-options="field:'approvalStep',sortable:true" align="center" halign="center">操作步骤</th>
       <th data-options="field:'department',sortable:true" align="center" halign="center">操作人部门</th>
       <th data-options="field:'approvalPerson',sortable:true" align="center" halign="center">操作人</th>
       <th data-options="field:'approvalDate',sortable:true" align="center" halign="center">操作时间</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
