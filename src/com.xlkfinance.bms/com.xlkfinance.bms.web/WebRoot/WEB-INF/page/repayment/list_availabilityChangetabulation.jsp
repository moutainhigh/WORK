<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript">
function dateSplit(value, row, index){
	if(null!=row.requestLoanDttm&&""!=row.requestLoanDttm){
		return row.requestLoanDttm.split(" ")[0];
	}
	return row.requestLoanDttm;
}
function datePlanSplit(value, row, index){
	if(null!=row.planRepayLoanDt&&""!=row.planRepayLoanDt){
		return row.planRepayLoanDt.split(" ")[0];
	}
	return row.planRepayLoanDt;
}
function datecompelteSplit(value, row, index){
	if(null!=row.compelteAdvDttm&&""!=row.compelteAdvDttm){
		return row.compelteAdvDttm.split(" ")[0];
	}
	return row.compelteAdvDttm;
}
function daterequestSplit(value, row, index){
	if(null!=row.requestAdvDttm&&""!=row.requestAdvDttm){
		return row.requestAdvDttm.split(" ")[0];
	}
	return row.requestAdvDttm;
}

	function advApplyRepay(value, rowData, index) {
		var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='disposeClick("+JSON.stringify(rowData)+")' href='#'>" +
 			"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
		return btn;   
	}

	function disposeClick(row) {
		url = "<%=basePath%>repaymanageloan/repaydatilapplybyProcess.action?interestChgId="
			+ row.interestChgId;
		parent.openNewTab(url, "利率变更查看", true);
	}
	function datiladvoperat(value, row, index) {
		if(row.status==1){
			var bt = "<a class='easyui-linkbutton' onclick='advappl("+JSON.stringify(row)+")' href='#'>" +
			"<span style='color:blue; '>"+ "编辑"+"</span></a>";  
			var projectDom = "|<a href='javascript:void(0)' onclick='reprojectbyid("+JSON.stringify(row)+")'><font color='blue'> 删除</font></a>";
			return bt+ projectDom;
			}else{
				var bt = "<a class='easyui-linkbutton' onclick='advapply("+JSON.stringify(row)+")' href='#'>" +
				"<span style='color:blue; '>"+ "查看"+"</span></a>";
				return bt+"| <font color='grey'>删除</font>";
			}
	}
	
	function advappl(row) {
		
		url = "<%=basePath%>repaymanageloan/repaydatilapplybyProcess.action?interestChgId="
				+ row.interestChgId;
		var datas = getTaskVoByWPDefKeyAndRefId("interestChangeRequestProcess",row.interestChgId);
		if(datas){
			url+="&"+datas;
		}

		parent.openNewTab(url, "利率变更编辑", true);
	}
	function advapply(row) {
	url = "<%=basePath%>repaymanageloan/repaydatilInfo.action?interestChgId="
				+ row.interestChgId;
		parent.openNewTab(url, "利率变更查看", true); 
	}
	function reprojectbyid(row){
		var projectIds =row.pId;;
		var interestChgIds =row.interestChgId;
	 	 $.messager.confirm("操作提示","确定删除吗?",
			function(r) {
	 			if(r){
					$.post("<%=basePath%>repaymanageloan/deleteProjectbyinterestChgId.action?interestChgIds="+interestChgIds +"&projectIds="+ projectIds, 
						function(ret) {
								$("#grid").datagrid('reload');
								$.messager.alert("操作提示","删除成功！","success");
						},'json');
	 			}
			}); 
	}  
	
	//删除
	function removeItem(){
		  var rows = $('#grid').datagrid('getSelections');
		 	if ( rows.length == 0 ) {
		 		$.messager.alert("操作提示","请选择数据","error");
				return;
			}// 获取选中的系统用户名 
			var stat=1;
			for(var i=0;i<rows.length;i++){
				if(rows[i].status!=1){
					stat=2;
					break;
				}
				
			}
			if(stat==2){
				$.messager.alert("操作提示","请选择申请中的项目做删除！","error");
				return false;
				
			}
		 	$.messager.confirm("操作提示","确定删除吗?",
					function(r) {
			 			if(r){
			 				var projectIds ="";
			 				var interestChgIds ="";
			 				for(var i=0;i<rows.length;i++){
			 					projectIds+=rows[i].pId+",";
			 					interestChgIds+=rows[i].interestChgId+",";
			 				}
			 				$.get("${basePath}repaymanageloan/deleteProjectbyinterestChgId.action?interestChgIds="+interestChgIds +"&projectIds="+ projectIds,
									function(ret) {
											$("#grid").datagrid('reload');
											$.messager.alert("操作提示","删除成功！","success");
									},'json');
			 				
			 			}
					});
		  	
	}   
	
	//重置
	function resetss(){
		$(".ecoTrade").hide();	
	}
	function cusType(value, row, index){
		if (1==row.cusType) {
			return "个人";
		}
		if (2==row.cusType) {
			return "企业";
		}else{
			return "";
		}
		
	}
	//(申请中=0，办理中=1，审核=2)
	function requestStatus(value, row, index){
		if (1==row.status) {
			return "申请中";
		}else if (2==row.status) {
			return "审核中";
		}else if (3==row.status) {
			return "已否决";
		}else if (4==row.status){
			return "处理结束"; 
		}else {
			return " "; 
		}
	}
	
	$(document).ready(function(){
		$('#cusType').combobox({
			onSelect: function(row){
				if(row.value==2){
					$(".ecoTrade").show();
				}else{
					$(".ecoTrade").hide();
				}
			}
		});
		$(".ecoTrade").hide();
	});
	
	
	//查询
	function ajaxSearch(){
		if(!compareDate('requestAdvDttm','requestAdvDttmLast')){
			return false;
		}
		ajaxSearchPage('#grid','#searcm');
	};
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<div>
			<!-- 查询条件 -->
			<form action="availabilityChangeTabulation.action" method="post" id="searcm"
				name="searcm">
				<div style="padding: 5px">
					<table class="beforeloanTable" width="90%">
						<tr>
							<td class="label_right">项目名称：</td>
							<td><input class="easyui-textbox" style="width:220px" name="projectName" id="projectName" /></td>
							<td class="label_right">项目编号: </td>
							<td>
								<input class="easyui-textbox"  name="projectId" id="projectId" />
							</td>
						</tr>
			<tr>
							<td class="label_right">客户名称:</td>
							<td><input class="easyui-textbox" style="width:220px" name="cusName" id="cusName" /></td>
							<td class="label_right">客户类别: </td>
							<td>
								<select name="cusType" id="cusType" class="easyui-combobox"  style="width:150px;" >
								<option value="">--请选择-- </option>
								<option value="1">个人 </option>
								<option value="2">企业 </option>
								
								</select>
							</td>
						</tr>
						<tr>
							<td class="label_right1">处理状态：</td>
							<td>
								<select name="status"  id="status"  class="easyui-combobox"  style="width:150px;"  editable="false">
								<option value="">--请选择-- </option>
								<option value="1">申请中 </option>
								<option value="2">审核中 </option>
								<option value="3">已否决 </option>
								<option value="4">处理结束 </option>
								</select>
							</td>
							<td class="label_right1 ecoTrade">经济行业类别：</td>
							<td class="ecoTrade">
								<input class="easyui-combobox" name="ecoTrade"
									id="ecoTrade" editable="false"
									data-options="
					            valueField:'pid',
					            textField:'lookupVal',
					            method:'get',
					            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
							</td>
						</tr>
						<tr>
							<td class="label_right">利率变更申请时间: </td>
							<td colspan="3">
								<input class="easyui-datebox"
									name="requestAdvDttm" id="requestAdvDttm" editable="false" /> <span>~</span>
								<input class="easyui-datebox" name="requestAdvDttmLast"
									id="requestAdvDttmLast" editable="false" />
							
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="ajaxSearch()">查询</a>
								<a href="javascript:void(0);" iconCls="icon-clear" onclick="javascript:$('#searcm').form('reset');resetss()" class="easyui-linkbutton" style="width: 60px;">重置</a>
							</td>							
						</tr>
					</table>	
						<div >
	</div>		
	
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="removeItem()">删除</a>
				</div>
			</form>
	
	
	</div>
	
		<!-- 操作按钮 -->
		
	</div>

	<div class="advrepaymentListDiv" style="height:100%">
	
	
	
	
	
	
	<table id="grid" title="利率变更列表" class="easyui-datagrid"
		style="height: 100%; width: auto;"
		data-options="
	     url:'availabilityChangeTabulation.action',
	     method: 'POST',
	     rownumbers: true,
	     pagination: true,
	     toolbar: '#toolbar',
	     idField: 'interestChgId',
	     fitColumns:true
	    ">
		<!-- 表头 -->
		<thead>
			<tr>
			  <th data-options="field:'interestChgId',checkbox:true"></th> 
				<th	data-options="field:'projectId',width:150">项目编号</th>
				<th data-options="field:'projectName',width:150,formatter:advApplyRepay">项目名称</th>
				<th data-options="field:'status',width:150,formatter:requestStatus">处理状态</th>
				<th data-options="field:'requestAdvDttm',width:150">申请时间</th>
				<th data-options="field:'compelteAdvDttm',width:150,formatter:datecompelteSplit">办理结束时间</th>
				<th data-options="field:'cusType',width:150,formatter:cusType">客户类别</th>
				<th data-options="field:'creditAmt',width:150,align:'right',formatter:formatMoney">贷款金额</th>
				<th data-options="field:'pmuserName',width:150">项目经理</th>
				<th data-options="field:'planOutLoanDt',width:150">贷款开始时间</th>
				<th data-options="field:'planRepayLoanDt',width:150">贷款结束时间</th>
				<th data-options="field:'yy',width:150,formatter:datiladvoperat">操作</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>
</body>
