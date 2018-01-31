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
		var includeWt = '${includeWt}';
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
	function cxportCapNewCountList() {
		
		$.ajax({url : BASE_PATH
							+ 'templateFileController/checkFileUrl.action',
					data:{templateName : 'HZJGYWBSXQ'},
					dataType:'json',
					success : function(data) {
						if (data == 1) {
							var projectName =$("#projectName").textbox('getValue');
							var projectNumber =$("#projectNumber").textbox('getValue');
							var pmUserName =$("#pmUserName").textbox('getValue');
							
							window.location.href ="${basePath}otherReportController/orgNewCountExportList.action?chooseMonthOrWeek="+mothOrWekNum+"&reMonth="+reMonth+
							"&pmUserName="+pmUserName+"&projectNumber="+projectNumber+"&projectName="+projectName+"&orgOrgName="+orgName+"&includeWt="+includeWt+"&page=-1";
						} else {
							alert('合作机构新增详情的导出模板不存在....，请上传模板后重试');
						}
					}
				})
	}
	

</script>
<title>赎楼贷合作机构新增列表</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>otherReportController/orgNewCountList.action" method="post" id="searchFrom" name="searchFrom">
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
    <shiro:hasAnyRoles name="EXPORT_FORECLOSURE_ORGANIZATION,ALL_BUSINESS">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportCapNewCountList()">导出</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="赎楼贷合作机构新增详情列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>otherReportController/orgNewCountList.action?mothOrWekNum='+${mothOrWekNum}+'&reMonth='+reMonth+'&orgOrgName='+orgName+'&includeWt='+includeWt,
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
	      <th data-options="field:'loanDate',sortable:true" align="center" halign="center">放款日期</th>
	      <th data-options="field:'orgOrgName',sortable:true" align="center" halign="center">机构名称</th>
	      
	      <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">经办人</th>
	      <th data-options="field:'chinaName',sortable:true" align="center" halign="center">借款人</th>
	      <th data-options="field:'loanAmount',sortable:true,formatter:formatMoney" align="center" halign="center">借款金额</th>
	      <th data-options="field:'loanDays',sortable:true" align="center" halign="center">借款期限</th>
	      <th data-options="field:'guaranteeFee',sortable:true,formatter:formatMoney" align="center" halign="center">预收咨询费</th>
	      <th data-options="field:'poundage',sortable:true,formatter:formatMoney" align="center" halign="center">预收手续费</th>
	
	      <th data-options="field:'paymentDate',sortable:true" align="center" halign="center">预计回款日期</th>
	      <th data-options="field:'newPepaymentDate',sortable:true" align="center" halign="center">实际回款日期</th>
	      <th data-options="field:'repaymentMoney',sortable:true,formatter:formatMoney" align="center" halign="center">回款金额</th>
	      <th data-options="field:'orgName',sortable:true" align="center" halign="center">资方来源</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
