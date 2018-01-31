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
 		var reMonth = '${reMonth}';
		var mothOrWekNum = '${mothOrWekNum}';
		var orgName = '${orgName}';
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
	function cxportCapSquareCountList() {
		$.ajax({url : BASE_PATH
			+ 'templateFileController/checkFileUrl.action',
	data:{templateName : 'XZXQMB'},
	dataType:'json',
	success : function(data) {
		if (data == 1) {
			var projectName =$("#projectName").textbox('getValue');
			var projectNumber =$("#projectNumber").textbox('getValue');
			var pmUserName =$("#pmUserName").textbox('getValue');
			
			window.location.href ="${basePath}otherReportController/capIngCountExportList.action?chooseMonthOrWeek="+mothOrWekNum+"&reMonth="+reMonth+
			"&pmUserName="+pmUserName+"&projectNumber="+projectNumber+"&projectName="+projectName+"&orgName="+orgName+"&page=-1";
		} else {
			alert('赎楼贷资金方未还款的导出模板不存在....，请上传模板后重试');
		}
	}
})
	}
	
	//审批状态:审核中=1,通过=2,未通过=3
	function formatterApprovalStatus(val, row) {
		if (val == 1) {
			return "审核中";
		} else if (val == 2) {
			return "通过";
		}else{
			return "未通过";
		}
	}
		
	//确认要款状态:1：未发送  2：审核中 3：审核通过 4 审核不通过
	function formatterConfirmLoanStatus(val, row) {
		if (val == 1) {
			return "未发送";
		} else if (val == 2) {
			return "审核中";
		}else if(val == 3){
			return "审核通过";
		}else{
			return "审核不通过";
		}
	}
	//放款状态:1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败
	function formatterLoanStatus(val, row) {
		if (val == 1) {
			return "未申请";
		} else if (val == 2) {
			return "申请中";
		}else if(val == 3){
			return "放款中";
		}else if(val == 4){
			return "放款成功";
		}else{
			return "放款失败";
		}
	}
</script>
<title>赎楼贷资金方结清列表</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>otherReportController/capIngCountList.action" method="post" id="searchFrom" name="searchFrom">
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
	       <td width="80" align="right">经办人：</td>
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
    <shiro:hasAnyRoles name="EXPORT_FORECLOSURE_CAPITAL,ALL_BUSINESS">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportCapSquareCountList()">导出</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="赎楼贷资金方未还款详情列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>otherReportController/capIngCountList.action?mothOrWekNum='+${mothOrWekNum}+'&reMonth='+reMonth+'&orgName='+orgName,
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
	      
	      <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">经办人</th>
	      <th data-options="field:'chinaName',sortable:true" align="center" halign="center">借款人</th>
	      <th data-options="field:'businessCategoryStr',sortable:true" align="center" halign="center">业务类型</th>
	      <th data-options="field:'orgName',sortable:true" align="center" halign="center">资方机构</th>
	      
	      <th data-options="field:'loanAmount',sortable:true,formatter:formatMoney" align="center" halign="center">借款金额</th>
	      <th data-options="field:'approveMoney',sortable:true,formatter:formatMoney" align="center" halign="center">审批金额</th>
	      <th data-options="field:'approvalStatus',sortable:true,formatter:formatterApprovalStatus" align="center" halign="center">审核状态</th>
	      <th data-options="field:'confirmLoanStatus',sortable:true,formatter:formatterConfirmLoanStatus" align="center" halign="center">确认要款状态</th>
	      
	      <th data-options="field:'loanStatus',sortable:true,formatter:formatterLoanStatus" align="center" halign="center">放款状态</th>
	      <th data-options="field:'approvalComment',sortable:true" align="center" halign="center">审批意见</th>
	      <th data-options="field:'approvalTime',sortable:true" align="center" halign="center">审批时间</th>
	      <th data-options="field:'loanTime',sortable:true" align="center" halign="center">请求放款时间</th>
	      <th data-options="field:'applyDate',sortable:true" align="center" halign="center">借款期限</th>
	     
	      <th data-options="field:'payInterest',sortable:true" align="center" halign="center">付资方费用</th>
	      <th data-options="field:'loanResultTime',sortable:true" align="center" halign="center">放款结束时间</th>
	      <th data-options="field:'partnerLoanDate',sortable:true" align="center" halign="center">资方放款时间</th>
	      <th data-options="field:'recLoanDate',sortable:true" align="center" halign="center">回款时间</th>
	      <th data-options="field:'refundDate',sortable:true" align="center" halign="center">我司还资方时间</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
