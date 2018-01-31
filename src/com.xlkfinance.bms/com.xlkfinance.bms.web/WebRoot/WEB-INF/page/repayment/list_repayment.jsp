<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>
<script type="text/javascript">
//隐藏行业类别
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
function  ecoTradehide(){
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
	//提前还款申请
	function datiloperat(value, row, index) {
		var bt = "<a class='easyui-linkbutton' onclick='advapply("+JSON.stringify(row)+")' href='#'>" +
			"<span style='color:blue; '>"+ "提前还款申请"+"</span></a>";  
	return bt;   
	}
		//查询
		function repayList() {
			$('#grid').datagrid('load',{
				projectName:$.trim($('#projectName').textbox('getValue')),
				projectNum:$.trim($('#projectNum').textbox('getValue')),
				cusName:$.trim($('#cusName').textbox('getValue')),
				cusType:$('#cusType').combobox('getValue'),
				ecoTrade:$('#ecoTrade').combobox('getValue'),
				requestDttm:$('#requestDttm').datebox('getValue'),
				requestDttmLast:$('#requestDttmLast').datebox('getValue'),
				planRepayLoanDt:$('#planRepayLoanDt').datebox('getValue'),
				planRepayLoanDtLast:$('#planRepayLoanDtLast').datebox('getValue')
			});
	}
	
	//项目名称format
	var formatProjectName=function (value, rowData, index) {
		var btn = "<a id='"+rowData.pId+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.pId+",\""+rowData.projectId+"\")' href='#'>" +
			"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
		return btn;   
	}
	
	//提前还款
	function advapply(row) {
		//根据项目ID判断是否有流程在执行
		if(isWorkflowByStatus(row.pId)){
			return;
		}
		
		var result = true;
		// 3 验证项目本金金额是否已还清
		$.ajax({
			type: "POST",
	    	url : '<%=basePath%>loanExtensionController/getExtensionNum.action?projectId='+row.pId,
			async:false, 
		    success: function(data){
		    	if(data.length == 2){
		    		result = false;
		    	}
			}
		});
		if(!result){
			$.messager.alert('操作提示',"此项目本金已全部还清,请检查!",'error');
			return ;
		}
		
		$.post("${basePath}rePaymentController/checkpreRepayByProjectId.action", {projectId : row.pId},
				function(da){
					if(da > 0 ){
						$.messager.alert("操作提示","此项目已经有提前还款申请流程了！","info");
					}else{
						url = "${basePath}rePaymentController/getadvapplyrepaymentlist.action?pId="+ row.pId+"&projectType="+row.projectType;
						parent.openNewTab(url, "提前还款申请", true);
					}
				});
	}
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
	<div class="repaymentListDiv"  style="height:100%;clear:both;">
		<table id="grid" title="还款信息管理" class="easyui-datagrid"
			style="height: 100%; width: auto;"
			data-options="
	    url: 'listrepaymenturl.action',
	    method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: true,
	    singleOnCheck: true,
		selectOnCheck: false,
		checkOnSelect: false
	    ">
			<!-- 表头 -->
			<thead>
				<tr>
					<th data-options="field:'projectId',width:'100'">项目编号</th>
					<th
						data-options="field:'projectName',width:'100',formatter:formatProjectName">项目名称</th>
					<th data-options="field:'cusType',width:'100',formatter:cusType">客户类别</th>
					<th data-options="field:'requestDttm',width:'100'">贷款申请时间</th>
					<th data-options="field:'creditAmt',width:'100',align:'right',formatter:formatMoney">贷款金额</th>
					<th data-options="field:'pmuserName',width:'100'">项目经理</th>
					<th data-options="field:'planOutLoanDt',width:'100'">贷款开始日期</th>
					<th data-options="field:'planRepayLoanDt',width:'100'">贷款结束日期</th>
					<th data-options="field:'yy',width:'200',formatter:datiloperat">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar" style="border-bottom: 0;">
		<!-- 查询条件 -->
		<form action="listrepaymenturl.action" method="post" id="searcFrom"
			name="searcFrom">
			<div style="padding: 5px">
				<table>
					<tr>
						<td class="label_right">项目名称：</td>
						<td><input class="easyui-textbox" name="projectName"
							style="width: 220px;" id="projectName" /></td>
						<td class="label_right">项目编号:</td>
						<td colspan="4"><input class="easyui-textbox"
							name="projectNum" style="width: 220px;" id="projectNum" /></td>
					</tr>
					<tr>
						<td class="label_right">客户名称:</td>
						<td><input class="easyui-textbox" name="cusName"
							style="width: 220px;" id="cusName" /></td>
						<td class="label_right">客户类别:</td>
						<td><select name="cusType" id="cusType"
							class="easyui-combobox" style="width: 220px;" editable="false">
								<option value="">---选择类型---</option>
								<option value="1">个人</option>
								<option value="2">企业</option>
						</select></td>
						<td class="label_right1 ecoTrade">经济行业类别：</td>
						<td class="ecoTrade"><input class="easyui-combobox"
							style="width: 150px;" name="ecoTrade" id="ecoTrade"
							editable="false"
							data-options="
				            valueField:'pid',
				            textField:'lookupVal',
				            method:'get',
				            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
						</td>
					</tr>
					<tr>
						<td class="label_right">贷款申请时间:</td>
						<td><input class="easyui-datebox" name="requestDttm"
							id="requestDttm" editable="false" /> <span>~</span> <input
							class="easyui-datebox" name="requestDttmLast"
							id="requestDttmLast" editable="false" /></td>


						<td class="label_right">计划还款时间:</td>
						<td><input class="easyui-datebox" name="planRepayLoanDt"
							id=planRepayLoanDt editable="false" /> <span>~</span> <input
							editable="false" class="easyui-datebox" name="planRepayLoanDtLast"
							id="planRepayLoanDtLast" /></td>
						<td colspan="2">&nbsp;&nbsp; <a href="javascript:void(0);"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="repayList()">查询</a> <a href="javascript:void(0);"
							onclick="javascript:$('#searcFrom').form('reset');ecoTradehide()"
							iconCls="icon-clear" class="easyui-linkbutton"
							style="width: 60px;">重置</a>
						</td>
					</tr>
				</table>
			</div>
			<div class="clear"></div>
		</form>
	</div>
</div>
</body>


