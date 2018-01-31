<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeTotleAcct.js" charset="utf-8"></script>
<script type="text/javascript">
	// 查询
	function searchData (){
		ajaxSearchPage("#acctTotalGrid","#searchFrom");
	}
	
	// 锁定状态格式话
	function formatStatus(value, row, index){
		if(row.projectName == '合计'){
			return "";
		}
		if(value == 1){
			return "已锁定";
		}else if(value == 0){
			return "";
		}else{
			return "";
		}
	}
	
	// 备注格式话
	function formatMark(value, row, index){
		if(row.projectName == '合计'){
			return "";
		}
		if(value == undefined){
			return "";
		}

		return "<font title="+value+">"+value+"</font>";
	}
	
	// 重新计算
	function reCalculation(){
		var month = $.trim($('#month').textbox('getValue'));
		if(month == '' || month == null){
			$.messager.alert('操作提示','请输入你要计算的月份!','warning');
			return ;
		}
		$.ajax({
			url:BASE_PATH+'financeController/addFinanceMonthlyReport.action',
			data:{month:month},
			dataType:'json',
			success:function(data){
				if(data == "success"){
					$.messager.alert('操作提示',"计算任务正在执行中,请稍后刷新页面查看!",'warning');
				}else{
					$.messager.alert('操作提示',"调用计算任务异常!",'warning');
				}
			}
		});
	}
	
	// 下载
	function exportExcel(){
		// 验证模版是否存在
 		$.ajax({
			url:BASE_PATH+'templateFileController/checkFileUrl.action',
			data:{templateName:'FINACE_TOTAL'},
			dataType:'json',
			success:function(data){
				if(data==1){
					var projectName = $.trim($('#projectName').textbox('getValue'));
					var projectNo = $.trim($('#projectNo').textbox('getValue'));
					var month = $.trim($('#month').textbox('getValue'));
					var assWay = $.trim($('#assWay').combobox("getValue"));
					window.location.href =BASE_PATH+"financeController/exportExcel.action?projectName="+projectName+"&projectNo="+projectNo+"&month="+month+"&assWay="+assWay;
				}else{
					$.messager.alert('操作提示','财务总账的导出模板不存在，请上传模板后重试','warning');
				}
			}
		});
	}
	
	// 锁定解锁
	function updateStatus(status){
		var rows = $('#acctTotalGrid').datagrid('getSelections');
	 	if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}
	 	var pId = "";
		for(var i=0;i<rows.length;i++){
	  		if(i==0){
	  			pId+=rows[i].pid;
	  		}else{
	  			pId+=","+rows[i].pid;
	  		}
	  	}
		$.ajax({
			url:BASE_PATH+'financeController/updateStatus.action',
			data:{status:status,pids:pId},
			dataType:'json',
			success:function(data){
				$.messager.alert('操作提示',data,'warning');
				$("#acctTotalGrid").datagrid('reload');
			}
		});
	}
	
	// 删除数据
	function deleteMonthlyReportRecords(){
		var rows = $('#acctTotalGrid').datagrid('getSelections');
	 	if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}
	 	var pId = "";
		for(var i=0;i<rows.length;i++){
			if(rows[i].status == 1 ){
				$.messager.alert("操作提示","已锁定的数据不能删除,请检查!","error");
				return;
			}
	  		if(i==0){
	  			pId+=rows[i].pid;
	  		}else{
	  			pId+=","+rows[i].pid;
	  		}
	  	}
		
		$.messager.confirm("操作提示","请确认是否删除选中数据?",
				function(r) {
		 			if(r){
		 				$.ajax({
		 					url:BASE_PATH+'financeController/deleteMonthlyReportRecordsById.action',
		 					data:{pids:pId},
		 					dataType:'json',
		 					success:function(data){
		 						$.messager.alert('操作提示',data,'warning');
		 						$("#acctTotalGrid").datagrid('reload');
		 					}
		 				});
		 			}
				}
		 	);
	}
	
// 修正数据
function updateReportRecord()
{
	$("#updateMonthlyReportRecord").dialog(
			{
				href : BASE_PATH+'financeController/updateMonthlyReportRecord.action',
				width : 500,
				height : 350,
				modal : true,
				title : '修正报表数据',
				buttons : [
						{
							text : '保存',
							iconCls : 'icon-save',
							handler : function() {
								doSave();
							}
						},
						{
							text : '取消 ',
							iconCls : 'icon-no',
							handler : function() {
								$("#updateMonthlyReportRecord").dialog('close');
							}
						}],
		onLoad : function() {
		}
	});
	
}
	
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="listFinanceAcctTotalUrl.action" method="post"
					id="searchFrom" name="searchFrom">
					<!-- 查询条件 -->
					<div style="padding: 5px">
						<table>
							<tr>
								<td class="label_right">项目名称：</td>
								<td><input class="easyui-textbox" name="projectName" style="width: 180px;" id="projectName" />
								</td>
								<td class="label_right">项目编号：</td>
								<td><input class="easyui-textbox" name="projectNo" style="width: 180px;" id="projectNo" /></td>
								
								<td class="label_right">项目经理：</td>
								<td><input class="easyui-textbox" name="projectManage" style="width: 100px;" id="projectManage" /></td>
							</tr>
							<tr>
								<td class="label_right">月份：</td>
								<td><input class="easyui-textbox" name="month" style="width: 80px;" id="month" /><font color="red">&nbsp;&nbsp;月份格式 如:201501</font></td>
								<td class="label_right">担保方式：</td>
								<td>
						            <input id="assWay" name="assWay" class="easyui-combobox" editable="false" panelHeight="auto"  style="width: 180px;" 
						            data-options="valueField:'pid',textField:'lookupDesc',multiple:true,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=ASS_WAY'" />
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="6" align="center"> 
									<input type="hidden" id="rows" name="rows">
									<input type="hidden" id="page" name="page" value='1'>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData()">查询</a>
								 	<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
								 	<a href="#" onclick="javascript:reCalculation();" iconCls="icon-clear" class="easyui-linkbutton">重新计算</a>
								</td>
							</tr>
						</table>
					</div>
					<div style="padding-bottom:5px">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true"  onclick="exportExcel()">导出Excel</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="updateStatus(1)">锁定</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="updateStatus(0)">解锁</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="deleteMonthlyReportRecords()">删除</a>
						
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="updateReportRecord()">修正数据</a>
					</div>
				</form>
			</div>
			<div style="height: 100%">
				<table id="acctTotalGrid" title="财务总账查询" class="easyui-datagrid"
					style="height: 100%; width: auto;"
					data-options="
					    url: 'listMonthlyReportRecords.action',
					    rownumbers: true,
					    pagination: true,
					    toolbar: '#toolbar',
					    idField: 'pid',
					    fitColumns:true,
					    pageSize:50,
					    singleOnCheck: true,
						selectOnCheck: true,
						singleSelect: false,
						checkOnSelect: true,
						onLoadSuccess:function(data){
								clearSelectRows('#acctTotalGrid');
						}">
					<!-- 表头 -->
					<thead>
						<tr>
							<th data-options="field:'pid',checkbox:true"></th> 
							<th data-options="field:'mark',width:100,align:'center',formatter:formatMark">备注</th>
							<th data-options="field:'projectManage',width:80">项目经理</th>
							<th data-options="field:'projectName',width:120">项目名称</th>
							<th data-options="field:'projectNo',width:100">项目编号</th>
							<th data-options="field:'loanDate',width:80">放款日期</th>
							<th data-options="field:'month',width:50">月份</th>
							<th data-options="field:'loanAmt',width:100,align:'right',formatter:formatMoney">放款金额</th>
							<th data-options="field:'interest',width:100,align:'right',formatter:formatMoney">贷款利息</th>
							<th data-options="field:'mangCost',width:100,align:'right',formatter:formatMoney">贷款管理费</th>
							<th data-options="field:'otherCost',width:100,align:'right',formatter:formatMoney">其他费用</th>
							<th data-options="field:'theRestCost',width:100,align:'right',formatter:formatMoney">其他收入</th>
							<th data-options="field:'totalCost',width:100,align:'right',formatter:formatMoney">回收合计</th>
							<th data-options="field:'status',width:100,align:'right',formatter:formatStatus">锁定状态</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		
		<!-- 调整记录页面 -->
		<div id="updateMonthlyReportRecord" />
</body>