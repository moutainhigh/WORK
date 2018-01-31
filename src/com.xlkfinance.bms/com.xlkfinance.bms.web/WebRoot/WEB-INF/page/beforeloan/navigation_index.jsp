<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 小科的页面 -->
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript">
var loginId = "${shiroUser.pid}";
var isWantong = 1;
$(document).ready(function(){
	/* setBankCombobox("oldLoanBank",0,"${project.oldLoanBank}"); */
	var orgId = $("#orgId").combobox('getValue');
	$('#orgId').combobox({
		url:'${basePath}elementLendController/getLoginUserOrgList.action',    
	    valueField:'id',
	    textField:'name',
	    filter: function(q, row){//模糊匹配
			var opts = $('#orgId').combobox('options');
			//只要输入的内容，即可匹配
			return row[opts.textField].indexOf(q) >-1;
		},
	    onLoadSuccess: function(rec){
	    	if(orgId >0){
	    		$('#orgId').combobox('setValue',projectId);
	    	}else{
	    		$('#orgId').combobox('setValue',-1);
	    	}
        } 
	});
	// 格式所有datebox的长度,默认全部设置为100px
	$(document).ready(function(){
		$('.easyui-datebox').datebox({    
			width:150   
		});  

	});
	
	
	
	var pmUserId = $("#pmUserId").combobox('getValue');
	
	$('#pmUserId').combobox({    
		url:'${basePath}beforeLoanController/findAcctManagerList.action',    
		valueField:'pid',    
		textField:'realName',
		filter: function(q, row){//模糊匹配
			var opts = $('#pmUserId').combobox('options');
			//只要输入的内容，即可匹配
			return row[opts.textField].indexOf(q) >-1;
		},
		onLoadSuccess: function(rec){
			if(pmUserId >0){
	    		$('#pmUserId').combobox('setValue',pmUserId);
	    	}else{
	    		$('#pmUserId').combobox('setValue',-1);
	    	}
		}
	});  
	
	
	var url = BASE_PATH+ "beforeLoanController/checkUserOrgInfo.action";
	
	$.ajax({
		url : url,
		async : false,
		success : function(data) {
			isWantong = data;
		}
	});
	
	var productId = $("#productId").combobox('getValue');//产品ID
	$('#productId').combobox({   
		url:'${basePath}productController/getProductLists.action?productType=2',    
	    valueField:'pid',
	    textField:'productName',
	    onLoadSuccess: function(rec){
	    	if(productId >0){
	    		$('#productId').combobox('setValue',productId);
	    	}else{
	    		$('#productId').combobox('setValue',-1);
	    	}
        } 
	});
	
	$('#grid').datagrid({  
		 onDblClickRow: function (rowIndex, rowData){
			 var url="";
			 if(isWantong == 1){//万通用户
		 			url = "${basePath}beforeLoanController/addNavigation.action?type=1&projectId="+rowData.pid+"&param='projectId':'"+rowData.pid+"','projectName':'"+rowData.value23+"','WORKFLOW_ID':'"+rowData.value25+"'";
				}else if(isWantong == 2){//小科用户
					url = "${basePath}beforeLoanController/addNavigation.action?type=2&projectId="+rowData.pid+"&param='projectId':'"+rowData.pid+"','projectName':'"+rowData.value23+"','WORKFLOW_ID':'"+rowData.value25+"'";
				}
				parent.openNewTab(url,"查看赎楼贷款申请",true);
		 }
	 });
	
	//撤单窗口关闭时，清空表单数据
 	$('#chechan_dialog').dialog({
 	    onClose:function(){
			//清空表单数据
 	    	$('#chechanForm').form('reset');
			$("#chechanCause").val("");
 	    }
 	});
	
 	$("#newLoanMoney").numberbox({
	    "onChange":function(){
	    	getGuaranteeFee();
	    	var loanMoney = $("#newLoanMoney").numberbox("getValue");
	    	var cny = numToCny(loanMoney);
	    	$("#newLoanMoneyCny").textbox("setValue",cny);
	    }
});

$("#newLoanDays").numberbox({
    "onChange":function(){
    	getGuaranteeFee();
    }
  });
  
	//变更记录窗口关闭时，清空表单数据
	$('#changeRecord_dialog').dialog({
	    onClose:function(){
			//清空表单数据
	    	$('#changeRecordForm').form('reset');
	    }
	});
	//拒单窗口关闭时，清空表单数据
	$('#refuse_dialog').dialog({
	    onClose:function(){
			//清空表单数据
	    	$('#refuseForm').form('reset');
	    }
	});
	
	$('#audit_dialog').dialog({
	    onClose:function(){
			//清空表单数据
	    	$('#auditForm').form('reset');
	    }
	});
	
	setCombobox3("rejectType","REJECT_TYPE","");
	
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
})

//重置按钮
function majaxReset(){
	$('#searchFrom').form('reset');
	$('#orgId').combobox('setValue',"-1");
	$('#pmUserId').combobox('setValue',"-1");
	$('#productId').combobox('setValue',"-1");
}

// 新增
function addItem(){
	$('#fm').form('reset');
	// 将 url 地址改变
	if(isWantong == 1){//万通用户
		parent.openNewTab("${basePath}beforeLoanController/addNavigation.action?type=1","赎楼贷款申请(万通)");
	}else if(isWantong == 2){//小科用户
		parent.openNewTab("${basePath}beforeLoanController/addNavigation.action?type=2","赎楼贷款申请");
	}
}

// 编辑
function editItem(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		if(loginId != row[0].value27 && loginId != row[0].value32){
 			$.messager.alert("操作提示","贷款申请只有经办人以及录单员才能进行编辑！","error");
 			return;
 		}
 		// 判断选中的状态是否是申请中,只有申请中的状态才可以编辑
 		if(row[0].value1 == 1){
 	 		// 如果是编辑 ,需要让 bianzhi = 1
 	 		var title = "编辑赎楼贷款申请";
 			if(isWantong == 1){//万通用户
 				url = "<%=basePath%>beforeLoanController/addNavigation.action?type=1&projectId="+row[0].pid;
 				title = "编辑赎楼贷款申请(万通)";
 			}else if(isWantong == 2){//小科用户
 				url = "<%=basePath%>beforeLoanController/addNavigation.action?type=2&projectId="+row[0].pid;
 			}
 			 //根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
			  var datas = getTaskVoByWPDefKeyAndRefId(row[0].value25,row[0].pid);
			if(datas){
				url+="&"+datas;
			}   
 			parent.openNewTab(url,title,true);
 		}else{
 			$.messager.alert("操作提示","贷款申请只有未提交审批才能进行编辑！","error");
 		}
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

function lookupItem(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		if(isWantong == 1){//万通用户
 			url = "${basePath}beforeLoanController/addNavigation.action?type=1&projectId="+row[0].pid+"&param='projectId':'"+row[0].pid+"','projectName':'"+row[0].value23+"','WORKFLOW_ID':'"+row[0].value25+"'";
		}else if(isWantong == 2){//小科用户
			url = "${basePath}beforeLoanController/addNavigation.action?type=2&projectId="+row[0].pid+"&param='projectId':'"+row[0].pid+"','projectName':'"+row[0].value23+"','WORKFLOW_ID':'"+row[0].value25+"'";
		}
		parent.openNewTab(url,"查看赎楼贷款申请",true);
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

// 删除
function removeItem(){
	var rows = $('#grid').datagrid('getSelections');
 	if ( rows.length == 0 ) {
 		$.messager.alert("操作提示","请选择数据","error");
		return;
	}
 	var pids = "";
	for (var i = 0; i < rows.length; i++) {
		if(loginId != rows[i].value27 && loginId != row[0].value32){
 			$.messager.alert("操作提示","贷款申请只有经办人以及录单员才能进行删除！","error");
 			return;
 		}
		if(rows[i].value1 == 1){
			if (i == 0) {
				pids += rows[i].pid;
			} else {
				pids += "," + rows[i].pid;
			}
		}else{
			$.messager.alert("操作提示","只有未提交审批的项目才能删除","error");
			return false;
		}
	}
 	$.messager.confirm("操作提示","确认删除选择的数据?",
		function(r) {
 			if(r){
				$.post("<%=basePath%>beforeLoanController/deleteProject.action",{pids : pids}, 
					function(ret) {
						//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
						if(ret && ret.header["code"] == 200){
							$.messager.alert('操作提示',"删除成功!");	
							// 重新加载列表信息
		 					$("#grid").datagrid('load');
		 					// 清空所选择的行数据
		 					clearSelectRows("#grid");
						}else{
							$.messager.alert('操作提示',ret.header["msg"],'error');	
						}
					},'json');
 			}
		});
}

function formatterForeStatus(val,row){
	if(val == 1){
		return "待客户经理提交";
	}else if(val == 2){
		return "待部门经理审批";
	}else if(val == 3){
		return "待业务总监审批";
	}else if(val == 4){
		return "待审查员审批";
	}else if(val == 5){
		return "待风控初审";
	}else if(val == 6){
		return "待风控复审";
	}else if(val == 7){
		return "待风控终审";
	}else if(val == 8){
		return "待风控总监审批";
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
	}else if(val == 14){
		return "待审查主管审查";
	}else if(val == 15){
		return "待合规复审";
	}
}

function formatterDate(val,row){
	if(!val || val == ''){
		return "未公证";
	}else{
		return "已公证";
	}
}
var url = "";

//打开撤单窗口
function openChechanDialog(){
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		if(row[0].value28 == 3){
 			$.messager.alert("操作提示","机构所提业务不能进行撤单操作,请重新选择！","error");
 			return;
 		}
 		
 		if(row[0].value1 == 1){
 	 		$("#chechanProjectId").val(row[0].pid);
 	 		$('#chechan_dialog').dialog('open').dialog('setTitle', row[0].value23+"--撤单");
 	 		url = '${basePath}beforeLoanController/setProjectChechan.action';
 		}else{
 			$.messager.alert("操作提示","只有待客户经理提交状态才能撤单,请重新选择！","error");
 		}
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行撤单,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
/**
 * 运营撤单
 */
function openChechanDialogByBusiness(){
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		if(row[0].value28 == 3){
 			$.messager.alert("操作提示","机构所提业务不能进行撤单操作,请重新选择！","error");
 			return;
 		}
 		if(row[0].value1 == 10){
 	 		$("#chechanProjectId").val(row[0].pid);
 	 		$('#chechan_dialog').dialog('open').dialog('setTitle', row[0].value23+"--撤单");
 	 		url = '${basePath}beforeLoanController/setChechanByBusiness.action';
 		}else{
 			$.messager.alert("操作提示","只有已审批状态才能撤单,请重新选择！","error");
 		}
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行撤单,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

//提交撤单
function suBchechanItem(){
	// 提交表单
	$("#chechanForm").form('submit', {
		url:url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				$.messager.alert('操作提示', ret.header["msg"], 'info');
                //关闭收件窗口
				$('#chechan_dialog').dialog('close');
				// 刷新
				$("#grid").datagrid("clearChecked");
				$("#grid").datagrid('reload');
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}

//要件借出申请
function applyItem() {
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		url = "${basePath}elementLendController/addElementLend.action?status=1&projectId="+row[0].pid;
		parent.openNewTab(url,"要件借出申请",true);
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

function editForeInfo(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		// 判断选中的状态是否是放款前,只有放款前的状态才可以编辑
 		if(row[0].value1 <11 || row[0].value1 == 14 || row[0].value1 == 15){
 	 		// 如果是编辑 ,需要让 bianzhi = 1
 	 		var title = "编辑赎楼贷款申请";
 			if(isWantong == 1){//万通用户
 				url = "${basePath}beforeLoanController/addNavigation.action?type=isEdit&projectId="+row[0].pid+"&param='projectId':'"+row[0].pid+"','projectName':'"+row[0].value23+"','WORKFLOW_ID':'"+row[0].value25+"'";
 				title = "编辑赎楼贷款申请(万通)";
 			}else if(isWantong == 2){//小科用户
 				url = "${basePath}beforeLoanController/addNavigation.action?type=isEdit&projectId="+row[0].pid+"&param='projectId':'"+row[0].pid+"','projectName':'"+row[0].value23+"','WORKFLOW_ID':'"+row[0].value25+"'";
 			}
 			 //根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
			/* var datas = getTaskVoByWPDefKeyAndRefId(row[0].value25,row[0].pid);
			if(datas){
				url+="&"+datas;
			} */   
 			parent.openNewTab(url,title,true);
 		}else{
 			$.messager.alert("操作提示","只有放款前才能进行修改！","error");
 		}
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
/**
 * 打印赎楼清单交接表
 */
function printForeInformation(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		if(row[0].value28 == 3){
 			$.messager.alert("操作提示","机构所提业务不能进行赎楼资料交接单打印,请重新选择！","error");
 			return;
 		}
 		var url = "${basePath}beforeLoanController/printForeInformation.action?projectId="+row[0].pid;
 		parent.openNewTab(url,"赎楼资料交接单打印",true);
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

var foreInformationList = {
		// 项目名称format
		formatProjectName:function(value, row, index){
			var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.pid+")'> <font color='blue'>"+row.value23+"</font></a>";
			return va;
		},
		formatTooltip:function(value, row, index){
			if (value==null) {
				return "";
			}
	        var abValue = value;
            if (value.length>=12) {
               abValue = value.substring(0,10) + "...";
            }
			var va = '<a href="#" title="' + value + '" class="note">' + abValue + '</a>';
            return va;
		}
	}

//打开驳回页面
function openRejectProject(){
	var loginId = "${shiroUser.pid}";
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		if(row[0].value28 != 3){
 			$.messager.alert("操作提示","只有机构端提交的业务才能进行驳回,请重新选择！","error");
 			return;
 		}
 		if(loginId != row[0].value27 && loginId != row[0].value32){
 			$.messager.alert("操作提示","业务申请只有经办人以及录单员才能进行驳回！","error");
 			return;
 		}
 		if(row[0].value30 == 3){
 			$.messager.alert("操作提示","此业务申请已驳回-补充资料,请重新选择！","error");
 			return;
 		}
 		
 		if(row[0].value1 == 1){
 			$("#pid").val(row[0].pid);
	 		$('#audit_dialog').dialog('open').dialog('setTitle', "驳回业务申请");
 		}else{
 			$.messager.alert("操作提示","此业务申请审批中,请重新选择！","error");
 		}
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行操作,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

//驳回业务申请
function rejectProject(){
	// 提交表单
	$("#auditForm").form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				$.messager.alert('操作提示', ret.header["msg"], 'info');
                //关闭窗口
				$('#audit_dialog').dialog('close');
				// 刷新
				$("#grid").datagrid("clearChecked");
				$("#grid").datagrid('reload');
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}

//推送业务给小科
function updateFinancial(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		if(row[0].value1 == 10){
 			var projectId = row[0].pid;
			$.messager.confirm("操作提示","确认推送选择的业务数据给小科?",
					function(r) {
			 			if(r){
							$.post("<%=basePath%>beforeLoanController/updateNeedFinancial.action",{projectId : projectId}, 
								function(result) {
									//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
									if(result && result.header["success"]){
										$.messager.alert('操作提示',"推送成功!");	
										// 重新加载列表信息
					 					$("#grid").datagrid('load');
					 					// 清空所选择的行数据
					 					clearSelectRows("#grid");
									}else{
										$.messager.alert('操作提示',result.header["msg"],'error');	
									}
								},'json');
			 			}
					});
 		}else{
 			$.messager.alert("操作提示","只有已审批的业务才能进行推送！","error");
 		}
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
/**
 * 打开变更记录
 */
function openChangeRecord(){
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		if(row[0].value1 != 10 || row[0].value1 == 15){
 			$.messager.alert("操作提示","只有已审批的业务才可进行操作,请重新选择！","error");
 			return;
 		}
		var projectId = row[0].pid;
		$.ajax({
			url : "${basePath}changeRecordController/getProjectInfo.action",
			cache : true,
			type : "POST",
			data : {'projectId' : projectId},
			async : false,
			success : function(data, status) {
				var result = eval("("+data+")");
				$("#oldLoanMoney").numberbox('setValue',result.oldLoanMoney);
				
				var cny = numToCny(result.oldLoanMoney);
				$("#oldLoanMoneyCny").textbox('setValue',cny);
				
				$("#oldLoanDays").numberbox('setValue',result.oldLoanDays);
				$("#oldFeeRate").numberbox('setValue',result.oldFeeRate);
				$("#newFeeRate").numberbox('setValue',result.oldFeeRate);
				$("#oldGuaranteeFee").numberbox('setValue',result.oldGuaranteeFee);
				$("#relationId").val(result.relationId);
				$('#changeRecord_dialog').dialog('open').dialog('setTitle', "贷款信息变更");
				}
			});
 	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

/**
 * 保存变更记录
 */
function saveChangeRecord(){
	var oldLoanMoney = $("#oldLoanMoney").numberbox('getValue');
	var loanMoney = $("#newLoanMoney").numberbox('getValue');
	if(oldLoanMoney - loanMoney <0){
		$.messager.alert('操作提示', "修改的借款金额不能大于原借款金额！", 'error');
		$("#newLoanMoney").numberbox('setValue','');
		return;
	}
	if(loanMoney <=0){
		$.messager.alert('操作提示', "修改后的借款金额必须大于0！", 'error');
		$("#newLoanMoney").numberbox('setValue','');
		return;
	}
	var loanDays = $("#newLoanDays").numberbox('getValue');
	if(loanDays-5 < 0){
		$.messager.alert('操作提示', "修改的借款天数不能小于5天！", 'error');
		$("#newLoanDays").numberbox('setValue','');
		return;
	}
	// 提交表单
	$("#changeRecordForm").form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				$.messager.alert('操作提示', ret.header["msg"], 'info');
                //关闭窗口
				$('#changeRecord_dialog').dialog('close');
				// 刷新
				$("#grid").datagrid("clearChecked");
				$("#grid").datagrid('reload');
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}
/**
 * 计算咨询费
 */
function getGuaranteeFee(){
	
	var oldLoanMoney = $("#oldLoanMoney").numberbox('getValue');
	var loanMoney = $("#newLoanMoney").numberbox('getValue');
		
	var loanDays = $("#newLoanDays").numberbox('getValue');//借款天数
	var rate = $("#newFeeRate").numberbox('getValue');
	var guaranteeFee = (loanMoney*rate/100*loanDays/30).toFixed(2);
	$("#newGuaranteeFee").numberbox('setValue',guaranteeFee);
}

//打开拒单页面
function openRefuseProject(){
	var loginId = "${shiroUser.pid}";
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		if(row[0].value30 == 3){
 			$.messager.alert("操作提示","此业务申请已驳回-补充资料,请重新选择！","error");
 			return;
 		}
 		if(row[0].value28 != 3){
 			$.messager.alert("操作提示","只有机构端提交的业务才能进行拒单操作,请重新选择！","error");
 			return;
 		}
 		if(row[0].value1 < 11 || row[0].value1 == 15){
 			$("#projectId").val(row[0].pid);
	 		$('#refuse_dialog').dialog('open').dialog('setTitle', "拒单");
 		}else{
 			$.messager.alert("操作提示","只有未放款的业务才能拒单,请重新选择！","error");
 		}
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行操作,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

//提交拒单申请
function refuseProject(){
	// 提交表单
	$("#refuseForm").form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				$.messager.alert('操作提示', ret.header["msg"], 'info');
                //关闭窗口
				$('#refuse_dialog').dialog('close');
				// 刷新
				$("#grid").datagrid("clearChecked");
				$("#grid").datagrid('reload');
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}
function formatReject(val,row){
	if(val == 1){
		return "完全驳回";
	}else if(val == 2){
		return "正常";
	}else if(val == 3){
		return "已驳回-补充资料";
	}else if(val == 4){
		return "已拒单";
	}else{
		return "正常";
	}
}

//打开业务分配页面
function openAssignedProject() {
	var row = $('#grid').datagrid('getSelections');
	if (row.length == 0) {
		$.messager.alert('操作提示', "请选择业务", 'error');
		return;
	}
	if (row.length == 1) {
 		if(row[0].value28 != 3){
 			$.messager.alert("操作提示","只有机构端提交的业务才能进行分配操作,请重新选择！","error");
 			return;
 		}
 		// 重新加载数据
 		$("#grid2").datagrid('load', {});
 		// 清空所选择的行数据
 		clearSelectRows("#grid2");
 		$('#allocation_dialog').dialog('open').dialog('setTitle',"业务分配");
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行操作,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

//分配业务
function assignedProject() {
	var gridRow = $('#grid').datagrid('getSelections');
	var grid2Row = $('#grid2').datagrid('getSelections');
	if (grid2Row.length != 1) {
		$.messager.alert('操作提示', "请选择用户", 'error');
		return;
	}
	
	var pids = "";
	for (var i = 0; i < gridRow.length; i++) {
			if (i == 0) {
				pids += gridRow[i].pid;
			} else {
				pids += "," + gridRow[i].pid;
			}
		}
	
	var userId=grid2Row[0].pid;
	var managers = grid2Row[0].realName;
	var managersPhone = grid2Row[0].phone;
	$.ajax({
		url : "${basePath}bizProjectController/assignedProject.action",
		cache : true,
		type : "POST",
		data : {'pmUserId':userId,'pids':pids,'managers':managers,'managersPhone':managersPhone},
		async : false,
		success : function(data, status) {
			var ret = eval("(" + data + ")");
			if (ret && ret.header["success"]) {
				$.messager.alert("操作提示", ret.header["msg"]);
				$("#grid").datagrid("clearChecked");
				$("#grid").datagrid('reload');
				$('#allocation_dialog').dialog('close');
			}else{
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}

function openUpdateProjectName(){
	var row = $('#grid').datagrid('getSelections');
	if (row.length == 0) {
		$.messager.alert('操作提示', "请选择业务", 'error');
		return;
	}
	if (row.length == 1) {
 		$("#projectId1").val(row[0].pid);
		$("#projectName1").textbox("setValue",row[0].value23);
		$("#customerName1").textbox("setValue",row[0].value13);
 		$('#updateName_dialog').dialog('open').dialog('setTitle',"修改项目名称");
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行操作,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

function updateProjectName(){
	// 提交表单
	$("#updateNameForm").form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				$.messager.alert('操作提示', ret.header["msg"], 'info');
                //关闭窗口
				$('#updateName_dialog').dialog('close');
				// 刷新
				$("#grid").datagrid("clearChecked");
				$("#grid").datagrid('reload');
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}

//撤销推送业务给小科
function cancleNeedFinancial(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
		var projectId = row[0].pid;
		$.messager.confirm("操作提示","确认撤销推送小科?",
				function(r) {
		 			if(r){
						$.post("<%=basePath%>beforeLoanController/cancleNeedFinancial.action",{projectId : projectId}, 
							function(result) {
								//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
								if(result && result.header["success"]){
									$.messager.alert('操作提示',"操作成功!");	
									// 重新加载列表信息
				 					$("#grid").datagrid('load');
				 					// 清空所选择的行数据
				 					clearSelectRows("#grid");
								}else{
									$.messager.alert('操作提示',result.header["msg"],'error');	
								}
							},'json');
		 			}
				});
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
} 
</script>
<title>贷前管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<!--图标按钮 -->
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="<%=basePath%>beforeLoanController/getAllForeProject.action" method="post" id="searchFrom" name="searchFrom" >
			<!-- 查询条件 -->
			<div style="padding:5px">
				<table>
					<tr>
						<td width="80" align="right" >项目名称：</td>
						<td>
							<input name="projectName" id="projectName" class="easyui-textbox" style="width:150px;"/>
						</td>
						<td width="80" align="right" >项目编号：</td>
						<td>
							<input name="projectNumber" id="projectNumber" class="easyui-textbox" style="width:150px;"/>
						</td>
					</tr>
					<tr>
						<td width="80"  align="right" height="28">部门：</td>
						<td ><select id="orgId" class="easyui-combobox" name="orgId" editable="false"  style="width:150px;" /></td>
						<td width="80" align="right" height="28">客户经理：</td>
						<td>
							<select id="pmUserId" class="easyui-combobox" name="pmUserId"  style="width:150px;" />
						</td>
						<td width="100" align="right"  height="28">产品名称：</td>
						<td colsapn="2">
							<input class="easyui-combobox" editable="false"  style="width:150px;" name="productId" id="productId"/>						
						</td>
					</tr>
					<tr>
						<td width="80" align="right" >待办理节点：</td>
						<td>
							<select class="select_style easyui-combobox" editable="false" name="foreclosureStatus"   style="width:150px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${project.foreclosureStatus==1 }">selected </c:if>>待客户经理提交</option>
								<option value="2" <c:if test="${project.foreclosureStatus==2 }">selected </c:if>>待部门经理审批</option>
								<option value="3" <c:if test="${project.foreclosureStatus==3 }">selected </c:if>>待业务总监审批</option>
								<option value="4" <c:if test="${project.foreclosureStatus==4 }">selected </c:if>>待审查员审批</option>
								<option value="5" <c:if test="${project.foreclosureStatus==5 }">selected </c:if>>待风控初审</option>
								<option value="6" <c:if test="${project.foreclosureStatus==6 }">selected </c:if>>待风控复审</option>
								<option value="7" <c:if test="${project.foreclosureStatus==7 }">selected </c:if>>待风控终审</option>
								<option value="14" <c:if test="${project.foreclosureStatus==14 }">selected </c:if>>待审查主管审批</option>
								<option value="8" <c:if test="${project.foreclosureStatus==8 }">selected </c:if>>待风控总监审批</option>
								<option value="9" <c:if test="${project.foreclosureStatus==9 }">selected </c:if>>待总经理审批</option>
								<option value="10" <c:if test="${project.foreclosureStatus==10 }">selected </c:if>>已审批</option>
								<option value="11" <c:if test="${project.foreclosureStatus==11 }">selected </c:if>>已放款</option>
								<option value="12" <c:if test="${project.foreclosureStatus==12 }">selected </c:if>>业务办理已完成</option>
								<option value="13" <c:if test="${project.foreclosureStatus==13 }">selected </c:if>>已归档</option>
								<option value="15" <c:if test="${project.foreclosureStatus==15 }">selected </c:if>>待合规复审</option>
							</select>
						</td>
						<td width="80" align="right" >业务来源：</td>
						<td>
							<input name="businessSourceStr" id="businessSourceStr" class="easyui-textbox"  style="width:150px;"/>
						</td>
						<td width="80" align="right" >单据类型：</td>
						<td>
							<select class="select_style easyui-combobox" editable="false" name="innerOrOut"  style="width:150px;">
							<option value="-1">--请选择--</option>
							<option value="1" <c:if test="${project.innerOrOut==1 }">selected </c:if>>内单    </option>
							<option value="2" <c:if test="${project.innerOrOut==2 }">selected </c:if>>外单    </option>
						</select>
						</td>
					</tr>
					<tr>
						<td width="80" align="right" >原按揭银行：</td>
						<td>
							<input name="oldLoanBank" id="oldLoanBank" class="easyui-textbox"  style="width:150px;"/>
						</td>
						<td width="100" align="right" >申请时间：</td>
						<td colsapn="2">
							<input name="beginRequestDttm" id="beginRequestDttm" class="easyui-datebox" editable="false"/> <span>~</span> 
							<input name="endRequestDttm" id="endRequestDttm" class="easyui-datebox" editable="false"/>
						</td>
						<c:if test="${userSource==2}">
							<td width="80" align="right" >录单员：</td>
						<td>
							<input name="declaration" id="declaration" class="easyui-textbox" style="width:150px;"/>
						</td>
						</c:if>
						<td colspan="2">
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
							<a href="#" onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;">重置</a>
						</td>
					</tr>			
				</table>
			</div>
		</form>
		
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
			<!-- 客户经理可以新增，修改自己的单 -->
			<shiro:hasAnyRoles name="ADD_UPDATE_FORE,ALL_BUSINESS">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
			</shiro:hasAnyRoles>
			<!-- 风控总监、执行岗以及风控终审角色可以修改单 -->
			<shiro:hasAnyRoles name="UPDATE_FORE,ALL_BUSINESS">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editForeInfo()">修改贷款信息</a>
			</shiro:hasAnyRoles>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
 			<%-- <shiro:hasAnyRoles name="DELETE_FORE,DEVELOPMENT,JUNIOR_ACCOUNT_MANAGER">
 				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
 			</shiro:hasAnyRoles> --%>
 			<shiro:hasAnyRoles name="PROJECT_CHECHAN,ALL_BUSINESS">
			   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="openChechanDialog()">撤单</a>
            </shiro:hasAnyRoles>
            <shiro:hasAnyRoles name="ELEMENT_ADD,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="applyItem()">要件借出申请</a>
			</shiro:hasAnyRoles>
			<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-print" onclick="printForeInformation()">打印赎楼资料交接单</a>
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="print()">出账单打印</a> -->
			<shiro:hasAnyRoles name="PROJECT_CHECHAN_BUSINESS,ALL_BUSINESS">
			   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="openChechanDialogByBusiness()">运营撤单</a>
            </shiro:hasAnyRoles>
            
            <shiro:hasAnyRoles name="REJECT_BIZ_PROJECT,ALL_BUSINESS">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-setting" plain="true" onclick="openRejectProject()">驳回</a>
			</shiro:hasAnyRoles>
			
			<shiro:hasAnyRoles name="UPDATE_FINANCIAL,ALL_BUSINESS">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-setting" plain="true" onclick="updateFinancial()">需要小科资金</a>
	  		</shiro:hasAnyRoles>
	  		<shiro:hasAnyRoles name="CHANGE_PROJECT_INFO,ALL_BUSINESS">
	  			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openChangeRecord()">贷款信息变更</a>
	  		</shiro:hasAnyRoles>
	  		
	  		<shiro:hasAnyRoles name="REFUSE_PROJECT,ALL_BUSINESS">
	  			<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-setting" plain="true" onclick="openRefuseProject()">拒单</a>
	  		</shiro:hasAnyRoles>
	  		
	  		<shiro:hasAnyRoles name="ASSIGNED_BIZ_PROJECT,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="openAssignedProject()">分配</a>
			</shiro:hasAnyRoles>
			<shiro:hasAnyRoles name="UPDATE_PROJECT_NAME,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openUpdateProjectName()">修改项目名称</a>
			</shiro:hasAnyRoles>
			<shiro:hasAnyRoles name="CANCLE_FINANCIAL,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="cancleNeedFinancial()">撤销推送</a>
			</shiro:hasAnyRoles>
		</div>
	</div>
	<div class="dksqListDiv" style="height:100%;">
	<table id="grid" title="贷款申请列表"
		style="height:100%;width: auto;"
		data-options="
		    url: '<%=basePath%>beforeLoanController/getAllForeProject.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true, 
		    pagination: true,
		    sortOrder:'desc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid', 
		    fitColumns:true">
		<!-- 表头 -->
		<thead data-options="frozen:true">
           <tr>
            <th data-options="field:'pid',checkbox:true" ></th>
		    <th data-options="field:'value23',formatter:foreInformationList.formatProjectName" align="center" halign="center" >项目名称</th>
           </tr>
        </thead>
		<thead><tr>
		    <th data-options="field:'value29'" align="center" halign="center">业务来源</th>
		    <th data-options="field:'value3'" align="center" halign="center"  >产品名称</th>
		    <th data-options="field:'value10'" align="center" halign="center"  >部门</th>
		    <th data-options="field:'value9'" align="center" halign="center"  >客户经理</th>
		    <c:if test="${userSource==2}">
		    <th data-options="field:'value13'" align="center" halign="center">客户名称</th>
			<th data-options="field:'value14'" align="center" halign="center">客户电话</th>
			<th data-options="field:'value15'" align="center" halign="center">证件号码</th>
			<th data-options="field:'value16'" align="center" halign="center">录单员</th>
			<th data-options="field:'value26'" align="center" halign="center">提单日期</th>
			<th data-options="field:'value30',formatter:formatReject,sortable:true" align="center" halign="center">驳回状态</th>
		    </c:if>
		    <th data-options="field:'value1',formatter:formatterProjectStatus" align="center" halign="center">赎楼状态</th>
		    <th data-options="field:'value7',formatter:formatMoney" align="center" halign="center"  >借款金额</th>
		    <th data-options="field:'value4'" align="center" halign="center"  >房产证号</th>
		    <th data-options="field:'value5'" align="center" halign="center"  >卖方</th>
		    <th data-options="field:'value6'" align="center" halign="center"  >买方</th>
		    <th data-options="field:'value18',formatter:formatMoney" align="center" halign="center"  >实收咨询费</th>
		    <th data-options="field:'value19'" align="center" halign="center"  >收咨询费日期</th>
		    <th data-options="field:'value20'" align="center" halign="center"  >出账日期  </th>
		    <th data-options="field:'value11'" align="center" halign="center"  >应回款日期</th>
		    <th data-options="field:'value17'" align="center" halign="center"  >原按揭银行</th>
		    <th data-options="field:'value24',formatter:formatterDate" align="center" halign="center">公证状态</th>
		</tr></thead>
	</table>
	</div>
	<!-- 撤单操作开始-->
    <div id="chechan_dialog" class="easyui-dialog" buttons="#chechan_dialogDiv" style="width: 353px; height: 150px; padding: 10px;" closed="true">
	   <form id="chechanForm" name="chechanForm" action="" method="post">
	     <table style="width: 100%; height: 50px;">
	       <tr>
	        <td>原因:</td>
	        <td><textarea rows="2" id="chechanCause" name="chechanCause" maxlength="500" style="width: 250px;"></textarea></td>
	       </tr>
	     </table>
	        <input type="hidden" id="chechanProjectId" name="pid"/>
	    </form>
	   </div>
	   <div id="chechan_dialogDiv">
	    <a id="F" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="suBchechanItem()">提交</a> <a href="javascript:void(0)"
	     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#chechan_dialog').dialog('close')"
	    >取消</a>
	   </div>
	</div>
	<!-- 撤单操作结束 -->
	<!-- 驳回操作开始 -->
 <div id="audit_dialog" class="easyui-dialog" buttons="#audit_dialogDiv" style="width: 450px; height: 250px; padding: 10px;" closed="true">
   <form id="auditForm" name="auditForm" action="${basePath}bizProjectController/rejectProject.action" method="post">
     <table style="width: 100%; height: 50px;">
     	<input type="hidden" name="pid" id="pid">
		<tr>
			<td class="label_right1"><font color="red">*</font>驳回类型：</td>
			<td>
			<select class="select_style easyui-combobox" style="width:245px;"  data-options="validType:'selrequired'" editable="false" name="isReject">
				<option value="-1">--请选择--</option>
				<option value="1" >完全驳回</option>
				<option value="3" >补充资料</option>
			</select>
			</td>
		</tr>
		<tr>
        <td class="label_right"><font color="red">*</font>驳回意见:</td>
        <td>
        	<input name="examineOpinion" class="easyui-textbox" style="width:85%;height:120px" data-options="required:true,multiline:true,validType:'length[1,200]'">
        </td>
       </tr>
     </table>
    </form>
   </div>
   <div id="audit_dialogDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="rejectProject()">提交</a> 
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#audit_dialog').dialog('close')">取消</a>
   </div>
<!-- 驳回操作结束 -->
<!-- 已审批业务修改开始 -->
 	<div id="changeRecord_dialog" class="easyui-dialog" buttons="#changeRecord_dialogDiv" style="width: 560px; height: 360px; padding: 10px;" closed="true">
   		<form id="changeRecordForm" name="changeRecordForm" action="${basePath}changeRecordController/saveChangeRecord.action" method="post">
     		<table style="width: 100%; height: 100px;">
     			<input type="hidden" name="relationId" id="relationId">
     			<input type="hidden" name="changeType" value="1">
			  <tr>
				<td class="label_right1">借款金额：</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="oldLoanMoney" id="oldLoanMoney" style="width:129px;"/>
				</td>
				<td class="label_right1"><font color="red">*</font>借款金额：</td>
				<td>
					<input  class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" name="newLoanMoney" id="newLoanMoney" style="width:129px;"/>
				</td>
			  </tr>
			  <tr>
				<td colspan="2" align="right">
					<input readonly="readonly" class="easyui-textbox" id="oldLoanMoneyCny" style="width:200px;"/>
				</td>
				
				<td colspan="2" align="right">
					<input readonly="readonly" class="easyui-textbox" id="newLoanMoneyCny" style="width:200px;"/>
				</td>
			  
			  </tr>
			  <tr>
				<td class="label_right1">借款天数：</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" data-options="required:true,min:1,max:9999,required:true,validType:'integer'" name="oldLoanDays" id="oldLoanDays" style="width:129px;"/>
				</td>
				<td class="label_right1"><font color="red">*</font>借款天数：</td>
				<td>
					<input  class="easyui-numberbox" data-options="prompt:'借款天数不能小于5天！',required:true,min:0,max:9999,required:true,validType:'integer'" name="newLoanDays" id="newLoanDays" style="width:129px;"/>
				</td>
			  </tr>
			  <tr>
				<td class="label_right1">费率：</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" data-options="min:0,max:100,precision:2,groupSeparator:','" name="oldFeeRate" id="oldFeeRate" style="width:125px;"/>%
				</td>
				<td class="label_right1"><font color="red">*</font>费率：</td>
				<td>
					<input class="easyui-numberbox" readonly="readonly" data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" name="newFeeRate" id="newFeeRate" style="width:125px;"/>%
				</td>
			  </tr>
			  <tr>
				<td class="label_right1">咨询费：</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="oldGuaranteeFee" id="oldGuaranteeFee" style="width:129px;"/>
				</td>
				<td class="label_right1"><font color="red">*</font>咨询费：</td>
				<td>
					<input class="easyui-numberbox" readonly="readonly" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" name="newGuaranteeFee" id="newGuaranteeFee" style="width:129px;"/>
				</td>
			  </tr>
			  <tr>
			  	<td class="label_right1">修改人：</td>
				<td>
					<input readonly="readonly" id="modifyUser" value="${shiroUser.realName }" class="easyui-textbox" style="width:129px;"/>
				</td>
			  </tr>
			  <tr>
        		<td class="label_right1"><font color="red">*</font>修改原因:</td>
        		<td colspan="8">
        			<input name="modifyReason" class="easyui-textbox" style="width:85%;height:60px" data-options="required:true,multiline:true,validType:'length[1,200]'">
				</td>
       		  </tr>
     		</table>
    	</form>
   	</div>
   	<div id="changeRecord_dialogDiv">
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveChangeRecord()">提交</a> 
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#changeRecord_dialog').dialog('close')">取消</a>
   	</div>
<!-- 已审批业务修改结束 -->
<!-- 拒单操作页面开始 -->
 <div id="refuse_dialog" class="easyui-dialog" buttons="#refuse_dialogDiv" style="width: 450px; height: 250px; padding: 10px;" closed="true">
   <form id="refuseForm" name="refuseForm" action="${basePath}bizProjectController/refuseProject.action" method="post">
     <table style="width: 100%; height: 50px;">
     	<input type="hidden" name="pid" id="projectId">
     	<input type="hidden" name="isReject" value="4">
		<tr>
			<td class="label_right1"><font color="red">*</font>拒单类型：</td>
			<td>
				<select id="rejectType" name="rejectType" class="easyui-combobox"  data-options="validType:'selrequired'" editable="false" style="width:129px;" />
			</td>
			<td class="label_right1">操作人：</td>
			<td>
				<input readonly="readonly" id="modifyUser" value="${shiroUser.realName }" class="easyui-textbox" style="width:89px;"/>
			</td>
		</tr>
		<tr>
        	<td class="label_right">拒单原因:</td>
        	<td colspan="3"><textarea rows="2" id="examineOpinion" name="examineOpinion" required="required" maxlength="200" style="height: 120px;width: 250px;"></textarea></td>
       </tr>
     </table>
    </form>
   </div>
   <div id="refuse_dialogDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="refuseProject()">提交</a> 
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#refuse_dialog').dialog('close')">取消</a>
   </div>
   <!-- 拒单操作页面结束 -->
   <!-- 分配页面开始 -->
<div id="allocation_dialog" class="easyui-dialog" buttons="#subAllocationDiv" style="width: 400px; height: 410px; padding: 10px;"
    closed="true"
   >
           用户姓名:<input name="realName" id="realName" data-options="prompt:'查询条件'" class="easyui-textbox"/>
    <table id="grid2" title="请选择人员" class="easyui-datagrid" style="height: 90%; width: 100%;"
     data-options="
      url: '<%=basePath%>bizProjectController/orderAllocationUserList.action',
      method: 'POST',
      rownumbers: true,
      singleSelect: true,
      pagination: true,
      sortOrder:'asc',
      remoteSort:false,
      idField: 'pid',
      fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true"></th>
       <th data-options="field:'userName',sortable:true" align="center" halign="center">用户名</th>
       <th data-options="field:'realName',sortable:true" align="center" halign="center">姓名</th>
       <th data-options="field:'memberId',sortable:true" align="center" halign="center">工号</th>
       <th data-options="field:'phone',sortable:true" align="center" halign="center">手机</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="subAllocationDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="assignedProject()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#allocation_dialog').dialog('close')"
    >取消</a>
   </div>
   <!-- 分配页面结束 -->
   <!-- 修改项目名称页面开始 -->
 <div id="updateName_dialog" class="easyui-dialog" buttons="#updateName_dialogDiv" style="width: 480px; height: 150px; padding: 10px;" closed="true">
   <form id="updateNameForm" name="updateNameForm" action="${basePath}secondBeforeLoanController/updateProjectName.action" method="post">
     <table style="width: 100%; height: 50px;">
     	<input type="hidden" name="pid" id="projectId1">
		<tr>
			<td class="label_right"><font color="red">*</font>项目名称：</td>
			<td>
				<input data-options="required:true,validType:'length[1,20]'" required="true" id="projectName1" name="projectName" class="easyui-textbox"/>
			</td>
			<td class="label_right">客户姓名：</td>
			<td>
				<input data-options="required:true,validType:'length[1,20]'" readonly="readonly" id="customerName1" class="easyui-textbox"/>
			</td>
		</tr>
     </table>
    </form>
   </div>
   <div id="updateName_dialogDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateProjectName()">提交</a> 
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#updateName_dialog').dialog('close')">取消</a>
   </div>
   <!-- 修改项目名称页面结束 -->
	</div>
</body>
</html>
