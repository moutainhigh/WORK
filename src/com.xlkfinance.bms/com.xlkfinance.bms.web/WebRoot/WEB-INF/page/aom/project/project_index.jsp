<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 项目经理查看自已维护机构订单 -->
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">

	//审批状态
	function formatterApplyStatus(val,row){
		if(val == 1){
			return "待客户经理提交";
		}else if(val == 5){
			return "待风控初审";
		}else if(val == 6){
			return "待风控复审";
		}else if(val == 7){
			return "待风控终审";
		}else if(val == 9){
			return "待总经理审批";
		}else if(val == 10){
			return "已审批";
		}else if(val == 11){
			return "已放款";
		}else if(val == 12){
			return "业务办理已完成";
		}else if(val == 13){
			return "已归档";
		}else if(val == 15){
			return "待合规复审";
		}
	}
	//formatAomMoney
	function formatAomMoney(value,row,index){
		if(value){
			return accounting.formatMoney(value, "", 2, ",", ".");
		}else{
			return "-";
		}
	}
	

	function formatterReject(value,row,index) {
		if (value == 1) {
			return "已驳回";
		} else if (value == 2) {
			return "正常";
		} else if (value == 3) {
			return "补充资料";
		} else if (value == 4) {
			return "已拒单";
		}
	}
	
	$(function() {
		$("#realName").textbox({
		    "onChange":function(){
		    	var realName=$("#realName").textbox("getValue");
				if (realName==null) {
					realName="";
				}
				// 重新加载数据
				$("#grid2").datagrid('load', {"realName":realName});
				// 清空所选择的行数据
				clearSelectRows("#grid2");
		    }
		  });
	});
</script>
<title>机构业务查询</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getOrgProjectList.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">订单名称：</td>
				<td>
					<input name="projectName" class="easyui-textbox" style="width: 200px;" />
				</td>
				<td width="80" align="right" height="25">业务来源：</td>
				<td>
					<input name="orgName" class="easyui-textbox" style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<td width="80" align="right" height="25">客户名称：</td>
				<td><input name="orgCustomerName" class="easyui-textbox" style="width: 200px;" /></td>		
				<td width="100" align="right" >提单日期：</td>
				<td colsapn="2">
					<input name="requestDttm" id="requestDttm" class="easyui-datebox" editable="false"/> <span>~</span> 
					<input name="endRequestDttm" id="endRequestDttm" class="easyui-datebox" editable="false"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 120px;" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
					<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">
	</div>
</div>
<div class="dksqListDiv" style="height:100%;">
<table id="grid" title="机构业务查询" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getOrgProjectList.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    singleSelect: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    width: '100%',
	 	height: '100%',  
	 	 striped: true, 
	 	 loadMsg: '数据加载中请稍后……',  
	    fitColumns:true" >
	    
	<!--
	 表头 -->
	<thead data-options="frozen:true">
		<tr>
		 	<th data-options="field:'projectName'"  align="center" halign="center" >订单名称</th>
			<th data-options="field:'projectNumber'"  align="center" halign="center" >订单编号</th>
		 	<th data-options="field:'orgName'" align="center" halign="center" >业务来源</th>
		 </tr>
	</thead>
	<thead>
		<tr>
			<!-- <th data-options="field:'projectName'"  align="center" halign="center" >订单名称</th>
			<th data-options="field:'projectNumber'"  align="center" halign="center" >订单编号</th>
			<th data-options="field:'orgName'" align="center" halign="center" >业务来源</th> -->
			<th data-options="field:'productName'" align="center" halign="center" >业务类型</th>
			<th data-options="field:'orgCustomerName'" align="center" halign="center" >客户名称</th>
			<th data-options="field:'orgCustomerPhone'" align="center" halign="center" >客户电话</th>
			<th data-options="field:'orgCustomerCard'" align="center" halign="center" >身份证号</th>
			<th data-options="field:'requestDttm'" align="center" halign="center" >提单日期</th>
			<th data-options="field:'planLoanMoney',formatter:formatAomMoney" align="center" halign="center" >借款金额(元)</th>
			<th data-options="field:'foreclosureStatus',formatter:formatterApplyStatus" align="center" halign="center" >审批状态</th>
			<th data-options="field:'isReject',formatter:formatterReject" align="center" halign="center" >驳回状态</th>
		</tr>
	</thead>
</table>
</div>
</div>
</div>
</body>
</html>