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
	//重置按钮
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset')
	}
	function cxportFinancialStatistics() {
		$.ajax({
					url : BASE_PATH
							+ 'templateFileController/checkFileUrl.action',
					data:{templateName : 'CWTJBB'},
					dataType:'json',
					success : function(data) {
						if (data == 1) {
							var projectName =$("#projectName").textbox('getValue');
							var businessSourceStr =$("#businessSourceStr").textbox('getValue');
							var orgName =$("#orgName").combobox('getValue');
							
							var loanDate =$("#loanDate").textbox('getValue');
							var loanEndDate =$("#loanEndDate").textbox('getValue');
							
							var recLoanDate =$("#recLoanDate").textbox('getValue');
							var recLoanEndDate =$("#recLoanEndDate").textbox('getValue');
							
							var planLoanDate =$("#planLoanDate").textbox('getValue');
							var planLoanEndDate =$("#planLoanEndDate").textbox('getValue');
							window.location.href = "${basePath}reportController/financialStatisticsExportList.action?projectName="+projectName+"&businessSourceStr="+businessSourceStr+"&orgName="+orgName+"&loanDate="+loanDate+"&loanEndDate="+loanEndDate+"&recLoanDate="+recLoanDate+"&recLoanEndDate="+recLoanEndDate+"&planLoanDate="+planLoanDate+"&planLoanEndDate="+planLoanEndDate+"&page=-1";
						} else {
							alert('财务统计报表的导出模板不存在，请上传模板后重试');
						}
					}
				})
	}
	var financialStatisticsReportList = {
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
				return va;
			}
		}

</script>
<title>财务统计报表</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/financialStatisticsReportList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
       <td width="80" align="right">项目名称：</td>
       <td>
        <input class="easyui-textbox" name="projectName" id="projectName"/>
       </td>
        <td width="80" align="right">业务来源：</td>
        <td>
        <input name="businessSourceStr" id="businessSourceStr" class="easyui-textbox"/>
       </td>
       <td width="80" align="right">资方来源:</td>
        <td>
        <input name="orgName" id="orgName"  class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'orgCode',textField:'orgName',value:'-1',url:'<%=basePath%>capitalOrgInfoController/getCapitalLists.action'"/>
       </td>
       </tr>
       <tr>
        <td align="right" height="28" width="120">放款日期：</td>
        <td colspan="2">
        	<input class="easyui-datebox" editable="false" name="loanDate"  id ="loanDate"/> 
        	至 <input class="easyui-datebox" editable="false" name="loanEndDate" id ="loanEndDate"/>
        </td>
        <td align="right" height="28" width="120">实际回款日期：</td>
        <td colspan="2">
        	<input class="easyui-datebox" editable="false" name="recLoanDate" id ="recLoanDate"/> 
        	至 <input class="easyui-datebox" editable="false" name="recLoanEndDate" id ="recLoanEndDate"/>
        </td>
       </tr> 
       <tr>     
        <td align="right" height="28" width="120">应回款日期：</td>
        <td colspan="2">
        	<input class="easyui-datebox" editable="false" name="planLoanDate" id="planLoanDate" />
        	 至 <input class="easyui-datebox" editable="false" name="planLoanEndDate" id="planLoanEndDate" />
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
    <shiro:hasAnyRoles name="CXPORT_BUSINESS_SUMMARY_REPORT,ALL_BUSINESS">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportFinancialStatistics()">导出</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="财务统计列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>reportController/financialStatisticsReportList.action',
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
     <thead data-options="frozen:true">
		<tr>
		 	<th data-options="field:'projectId',checkbox:true"></th>
       		<th data-options="field:'projectName',formatter:financialStatisticsReportList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <thead>
      <tr>
  	   <!-- <th data-options="field:'projectId',checkbox:true"></th>
       <th data-options="field:'projectName',formatter:financialStatisticsReportList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th> -->
       <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th>
       <th data-options="field:'businessSourceStr',sortable:true" align="center" halign="center">业务来源</th>
       <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">经办人</th>
	   <th data-options="field:'loanAmt',sortable:true,formatter:formatMoney" align="center" halign="center">贷款金额</th>
       <th data-options="field:'realLoan',sortable:true,formatter:formatMoney" align="center" halign="center">放款金额</th>
	   <th data-options="field:'loanDate',sortable:true,formatter:convertDate" align="center" halign="center">放款日期</th>
	   
	   <th data-options="field:'recMoney',sortable:true,formatter:formatMoney" align="center" halign="center">实际回款金额</th>
	   <th data-options="field:'recLoanDate',sortable:true,formatter:convertDate" align="center" halign="center">实际回款日期</th>
	   <th data-options="field:'needRecMoney',sortable:true,formatter:formatMoney" align="center" halign="center">应回款金额</th>
	   <th data-options="field:'planLoanDate',sortable:true,formatter:convertDate" align="center" halign="center">应回款日期</th>
	   <th data-options="field:'orgName',sortable:true" align="center" halign="center">资方来源</th>
	   <th data-options="field:'orgLoanDate',sortable:true,formatter:convertDate" align="center" halign="center">资方放款时间</th>
	   <th data-options="field:'orgReDate',sortable:true,formatter:convertDate" align="center" halign="center">还资方时间</th>
	   
	   <th data-options="field:'poundage',sortable:true,formatter:formatMoney" align="center" halign="center">手续费</th>
	   <th data-options="field:'rePoundage',sortable:true,formatter:formatMoney" align="center" halign="center">退手续费</th>
	   <th data-options="field:'interest',sortable:true,formatter:formatMoney" align="center" halign="center">咨询费</th>
	   <th data-options="field:'reInterest',sortable:true,formatter:formatMoney" align="center" halign="center">退咨询费</th>
	   <th data-options="field:'payInterest',sortable:true,formatter:formatMoney" align="center" halign="center">付平台息费</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
