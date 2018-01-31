<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
#baseInfo {
	width: 500px;
}

#baseInfo label {
	width: 250px;
}

#baseInfo label.error, #baseInfo input.submit {
	margin-left: 253px;
}

#billDiv .panel {
	margin-bottom: 10px;
}
</style>
<script type="text/javascript">

$(function(){
	$("#currtDttm").datebox('disable');
	// 如果是从旧的财务收款跳转过来的
	if("true" == "${oldfinanceReceivables}")
	{
		$("#paymentDttm").datebox('disable');
		saveFinanceReceivablesSuccess($("#financeReceivablesDTOId").val(),true);
	}
	
	$("#useBalance").numberbox('disable');
});

function setFinanceAcctCombobox(inputId,selVal){
	$('#'+inputId).combobox({    
		url:'${basePath }financeController/searcherFinanceAcctManage.action',
	    valueField:'pid',    
	    textField:'chargeName',
	    onLoadSuccess: function(rec){
	    	if(selVal!=''){
	    		$("#"+inputId).combobox('setValue',selVal);
	    	}
        }
	});  
}

// 修改是否使用客户余额
function changeCustomerBalance()
{
	var checked = $("#customerBalanceCheckbox").is(':checked');
	if(checked)
	{
  　　   $('#useBalance').numberbox('enable');
  　　   $('#useBalance').numberbox('setValue',"${bean.customerBalance}");
	}
	else
	{
	     $("#useBalance").numberbox('clear');
	     $('#useBalance').numberbox('disable');
	} 
}


//检查提交前数据合法性
function checkForm(){
	
	var paymentAmount=Number($("#paymentAmount").numberbox('getValue')); // 收款金额
	// 使用可以余额
	var useBalance=Number($("#useBalance").numberbox('getValue'));
	
	//  如果2个都没有输入金额的话
	if((paymentAmount+useBalance) == 0)
	{
		$.messager.alert("提示","请输入收款金额，或者输入有效的客户余额","info");
		return false;
	}	
	
	// 有使用客户余额
	if(useBalance>0){
		
		var canUseBanlance =  Number(${bean.customerBalance}); // 可以使用的余额
		
		if(Number(useBalance)>canUseBanlance)
		{
			$.messager.alert("提示","客户余额不足","info");
			$("#useBalance").focus();
			return false;
		}	
	}
	
	return true;
}

// 使用余额保存
function useBalanceSave()
{
	// 检测没有通过
	if(!checkForm())
	{
	   return false;
	}
	
	var useBalance=$("#useBalance").numberbox('getValue');
	useBalance = useBalance.length>0?useBalance:0;
    var paramData={'version':$("#financeReceivablesVersion").val(),'pid':$("#financeReceivablesDTOId").val(),'useBalance':useBalance,'projectId':"${bean.projectId}",'acctId':"${bean.acctId}"};
	
	$.ajax({
		type: "POST",
        data:paramData,
		url : "saveUseBalance.action",
		dataType: "json",
	    success: function(data){
	    	if(data.header.success)
	    	{
	    		// 更新数据版本号
	    		$("#financeReceivablesVersion").val(data.header.code);
	    		$("#useBalanceBtn").hide(); // 隐藏按钮
	    		$("#customerBalanceCheckbox").hide();
	    		$("#useBalance").numberbox('disable');
	    		
	    		// 重新设置可对账金额
	    		var waitReconciliationAmount = Number($("#waitReconciliationAmount").numberbox('getValue'));
	    		waitReconciliationAmount = waitReconciliationAmount + Number(useBalance);
	    		$("#waitReconciliationAmount").numberbox('setValue',waitReconciliationAmount);
	    		$("#availableReconciliationAmount").numberbox('setValue',waitReconciliationAmount);   // 未对账金额
	    		$("#hedgingAmount").numberbox('setValue',0.0);  // 平账金额
	    		
	    		showReconciliationOptionTable();// 显示对账选项出来
	    	}
	    	// 保存失败
	    	else
    		{
	    		$.messager.alert("提示",data.header.msg,"error");
    		}
		},error : function(result){
			$.messager.alert("提示","保存收款失败","error");
		}
	}); 
}

// 保存收款
function saveFinanceReceivables()
{
	// 检测没有通过
	if(!checkForm())
	{
	   return false;
	}
	
	var useBalance=$("#useBalance").numberbox('getValue');
	useBalance = useBalance.length>0?useBalance:0;
    var paramData={'loanId':$("#loanId").val(),'paymentAmount':$("#paymentAmount").numberbox('getValue'),'paymentDttm':$("#paymentDttm").datebox('getValue'),'useBalance':useBalance,'projectId':"${bean.projectId}",'acctId':"${bean.acctId}"};
	
	$.ajax({
		type: "POST",
        data:paramData,
		url : "saveFinanceReceivables.action",
		dataType: "json",
	    success: function(data){
	    	if(data.header.success)
	    	{
				saveFinanceReceivablesSuccess(data.header.code,false);
	    	}
	    	// 保存失败
	    	else
    		{
	    		$.messager.alert("提示",data.header.msg,"error");
    		}
		},error : function(result){
			$.messager.alert("提示","保存收款失败","error");
		}
	}); 
}

// 收款成功后
function saveFinanceReceivablesSuccess(pid,oldfinanceReceivables)
{
	$("#paymentAmount").numberbox('disable');
	$("#saveReceivablesBtn").hide();
	$("#customerBalanceCheckbox").hide();
	$("#useBalance").numberbox('disable');
	$("#reconciliationTable").show();
	
	var paymentAmount = $("#paymentAmount").numberbox('getValue'); // 实收金额
	var useBalance =  $("#useBalance").numberbox('getValue');// 使用的余额
	
	var waitReconciliationAmount = 0;
	// 就的shouk
	if(oldfinanceReceivables)
	{
		waitReconciliationAmount = $("#waitReconciliationAmount").numberbox('getValue');
		// 如果是有客户余额的
		if(Number("${bean.customerBalance}")>0)
		{
			$("#customerBalanceCheckbox").show();
			$("#useBalanceBtn").show(); // 把使用余额的按钮显示出来
		}	
	}
	else
	{
		$("#financeReceivablesVersion").val(0);// 收款记录保存后默认是0开始的
		waitReconciliationAmount = (Number(paymentAmount)+ Number(useBalance)); // 对账金额
	}	
	
	$("#waitReconciliationAmount").numberbox('setValue',waitReconciliationAmount);
	$("#availableReconciliationAmount").numberbox('setValue',waitReconciliationAmount);   // 未对账金额
	$("#hedgingAmount").numberbox('setValue',0.0);  // 平账金额
	$("#financeReceivablesDTOId").val(pid); // 记录使用的收款记录
	
	showReconciliationOptionTable();// 显示对账选项出来
}

// 可对账选项的table显示出来
function showReconciliationOptionTable()
{
	$("#reconciliationOptionTable").show();
	// 待对账金额
	var waitReconciliationAmount = $("#waitReconciliationAmount").numberbox('getValue');
	
	$.ajax({
		type: "POST",
        data:{"loanId":$("#loanId").val(),"currentDt":"${currDate}","waitReconciliationAmount":waitReconciliationAmount},
		url : "getReconciliationOptionsList.action",
		dataType: "json",
	    success: function(data){
	    	createReconciliationOptionTable(data);
		},error : function(result){
			alert("获取数据失败");
		}
	}); 
}

// 生成选项
function createReconciliationOptionTable(data)
{
	$("#optionsBody").html(""); // 清除原来的数据
	cleanBillDiv();// 清除已经生成的对账单
	var tr ="<tr>"
	for(var i=0;i<data.length;i++)
	{
		tr+="<td><input hasFeew='"+data[i].hasFeew+"'  refId='"+data[i].refId+"' rtype='"+data[i].type+"'refNum='"+data[i].refNum+"' itmeName='"+data[i].name+"' overdue='"+data[i].overdue+"' repayDt='"+data[i].repayDt+"' type='checkbox'/>"+data[i].name+"</td>";
		if(i>0 && i < data.length-1 && (i+1)%4==0)
		{
			tr+="</tr><tr>";
		}
	}	
	// 需要补齐的td数
	var b = data.length>4?(4-data.length%4):(4-data.length);
	for(var i=0;i<b;i++)
	{
		tr+="<td></td>";
	}
	tr+= "</tr>";
	 
	// 生成选项
	$("#optionsBody").append(tr);
	
	// 提前还款跳转过来的
	if("true" == "${earlyRepayment}")
	{
		 $("#optionsBody").find("input:checkbox").each(function()
		 {
			 
			var type = $(this).attr("rtype"); // 类型： 1：正常还款计划  2：即时还款
			var refNum =$(this).attr("refNum"); // 第几期或者对应的类型
				
			 // 根据需求：如果是提前还款和提前还款罚金，默认选上
			 if(type == 2 && (refNum==7 || refNum ==8))
			 {
				 $(this).attr("checked",true);
			 }
		 });
	}
}

// 清空对账单
function cleanBillDiv()
{
	$("#billDiv").html("");// 移除其他应收里面的数据
	$("#saveButtonDiv").hide(); // 自动对账按钮隐藏
	tableParamArray.length=0;
	waitCreateBill.length=0;
}

//公共
$.extend($.fn.datagrid.methods, {
	editCell : function(jq, param) {
		return jq.each(function() {
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields', true).concat(
					$(this).datagrid('getColumnFields'));
			for (var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field) {
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for (var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

// 结束编辑的行
function endEditingsIt(tableId){
	if (editIndex == undefined){return true}
	
	if ($("#"+tableId).datagrid('validateRow', editIndex)){
	   // 退出当前编辑状态
		$("#"+tableId).datagrid('endEdit', editIndex);
		editIndex = undefined;
		// 编辑完当前表格
		calculateTableTotal(tableId);
		// 统计所有的表格
		calculateAllTableTotal();
		return true;
	} else {
		return false;
	}
}

function onClickCellsIt(index, field){
	// 由于获取tableId的事件在当前事件后，所以需要延迟100毫秒
	setTimeout(function(){
		if(index==$("#"+clickTableId).datagrid('getRows').length-1){return false;}
		// 获取所有的行
		var rows = $("#"+clickTableId).datagrid('getRows');
		// 如果是费用减免列,不要编辑
		if(rows[index].detailType==100)
		{
			return false;
		}	
		
		
		if (endEditingsIt(clickTableId)){
			$("#"+clickTableId).datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
			editIndex = index;
			//编辑时文本框获得焦点
			var editor = $("#"+clickTableId).datagrid('getEditor', {index:index,field:field});
			editor.target[0].nextElementSibling.firstChild.select();
		}
	},100);
}

function formatCss(val,row){
        return '<label style="padding-left:10px;">'+accounting.formatMoney(val, "", 2, ",", ".")+'</label>';
}

// 数据加载完毕
function tableDataLoadSuccess()
{
	var cTableId = waitCreateBill[0].tableId; // 当前加载表的tableId
	var cReconciliationTotal = calculateTableTotal(cTableId);// 当前表格实收合计
	
	// 用户自动对账可以金额， 退还金额不算在内（退还金额是负数，所以这里是-）
	automaticReconciliationAmt = automaticReconciliationAmt - cReconciliationTotal;
	// 加载完当前的表单，移除
	waitCreateBill.shift();
	// 加载下一个对账单
	createOneBill();
}

// 计算出页面的退费总额
function getReturnAmt()
{
	var returnAmt = 0;
	var rows = $('#otherBillTable').datagrid('getRows');
	for(var i=0;i<rows.length-1;i++)
	{
		 reconciliation = Number(rows[i]['reconciliation']); // 实收
		 receivable =  Number(rows[i]['receivable']);  // 应收
		  
		 // 如果是退管理费，退利息，退其他费用 (如果应收小于0，说明是要退的前，否则不处理)
		 if((rows[i].detailType  ==4 || rows[i].detailType  ==5 || rows[i].detailType  == 6) && receivable < 0 )
		 {
			 returnAmt += Number(reconciliation);
		 }
	}
	
	return returnAmt;
}



// 统计到对账信息平账金额和剩余金额
function calculateAllTableTotal()
{
	var  reconciliationTotalAll = 0;  // 实收总合计
	
	// 遍历所有要对账的表格
	$("#billDiv").find("table[id]").each(function(){
		var tableId = $(this).attr("id");// 表格Id
		var reconciliationTotal = calculateTableTotal(tableId);
		reconciliationTotalAll +=reconciliationTotal;
	});
	// 可以对账金额
 	var totalAmt = $("#waitReconciliationAmount").numberbox('getValue');
	var availableReconciliationAmount =  Number(totalAmt) - reconciliationTotalAll ; 
 	$("#hedgingAmount").numberbox('setValue',reconciliationTotalAll);
	$("#availableReconciliationAmount").numberbox('setValue',availableReconciliationAmount);
}

//计算当前表格的合计,返回实收合计
function calculateTableTotal(tableId)
{
	var receivableTotal = 0;  // 应收合计
	var reconciliationTotal = 0;  // 实收合计
	var uncollectedTotal = 0;// 未收合计
	
	// 遍历除了最后一行，最后一行为合计
	var receivable = 0;  // 当前行应收
	var  reconciliation = 0; // 当前行实收
	var uncollected = 0; // 当前行未收
	
	var rows = $('#'+tableId).datagrid('getRows');
	for(var i=0;i<rows.length-1;i++)
	{
		 receivable =  Number(rows[i]['receivable']);
		 reconciliation = Number(rows[i]['reconciliation']);
		 uncollected = Number(rows[i]['uncollected']);
		 
		 // 如果已收已经发生改变
		 if(receivable - reconciliation != uncollected)
		 {
			  // 重新计算未收
			 uncollected = (receivable - reconciliation);
			// 修改合计
			 $('#'+tableId).datagrid('updateRow',{
				 index:i,
				 row:{'uncollected':uncollected}
			 });
		 }
		 
		 receivableTotal += receivable;
		 reconciliationTotal += reconciliation;
		 uncollectedTotal +=  uncollected;
	}
	
	// 修改合计
	 $('#'+tableId).datagrid('updateRow',{
		 index:rows.length-1,
		 row:{'receivable':accounting.formatMoney(receivableTotal, "", 2, ",", "."),'reconciliation':accounting.formatMoney(reconciliationTotal, "", 2, ",", "."),'uncollected':accounting.formatMoney(uncollectedTotal, "", 2, ",", ".")}
	 });
	
	// 单独修改实收合计
	$('#'+tableId).parent().find(' td[field="reconciliation"]').last().find('label').html(accounting.formatMoney(reconciliationTotal, "", 2, ",", "."));
	
	return reconciliationTotal;
}

var tableParamArray = new Array(); // 记录对账单表格参数的数组
// 等待生成对账单数组
var waitCreateBill = new Array();
var automaticReconciliationAmt; // 用户自动对账可以金额
// 生成对账单
function createBill()
{
	cleanBillDiv();
	// 自动还款
	var automaticReconciliation = $("#automaticReconciliation").is(':checked');
	// 用于自动还款的金额
	 automaticReconciliationAmt = automaticReconciliation? $("#waitReconciliationAmount").numberbox('getValue'):0;
	 
	 var iCheck = true;
	 var checkOk = true;
	 $("#optionsBody").find("input:checkbox").each(function()
	 {
		 // 如果上一个期没有选择，但是下一期选中了
		 if(!iCheck && $(this).is(':checked'))
		 {
			checkOk = false;
		    return false;
		 }
		 
		 iCheck = $(this).is(':checked');
	 });
	 
	 if(!checkOk)
	{
		 $.messager.alert("提示","请按照时间循序来对账","info");
		  return false;
	} 
	 
	
	 var other_pid="";// 记录即时发生计划的pid的字符串
	 var maxOverdue =  0;
	 var maxRepayDt="";
	 // 找出即时发生计划的对账单(即时发生的要生成到一个表格中)
	$("#optionsBody").find("input:checkbox:checked").each(function(){
		var type = $(this).attr("rtype"); // 类型： 1：正常还款计划  2：即时还款
		// 剩余即时发生的
		if(type == 2)
		{	
			var pid = $(this).attr("refid"); // 对应的主键
			var refNum =$(this).attr("refNum"); // 第几期或者对应的类型
			var itmeName = $(this).attr("itmeName") // 名称
			var overdue =  $(this).attr("overdue"); // 逾期天数
			var repayDt	=  $(this).attr("repayDt"); // 还款期限
			
			if(overdue>maxOverdue)
			{
				maxOverdue = overdue;
				maxRepayDt = repayDt;
			}
			other_pid +=pid+",";
		}
	});
	
	 // 如果有选中即时发生的项
	 if(other_pid.length>0)
     {
		var tableId = "otherBillTable"; // 其他对账单的表格ID，跟后台约定好
		waitCreateBill[waitCreateBill.length] = {'overdue':maxOverdue,'repayDt':maxRepayDt,'pid':0,'otherPid':other_pid,'type':2,'refNum':0,'tableId':tableId};
     }		
	 
		
	 // 找出正常计划的对账单
	$("#optionsBody").find("input:checkbox:checked").each(function(){
		var pid = $(this).attr("refid"); // 对应的主键
		var type = $(this).attr("rtype"); // 类型： 1：正常还款计划  2：即时还款
		var refNum =$(this).attr("refNum"); // 第几期或者对应的类型
		var itmeName = $(this).attr("itmeName"); // 名称
		var overdue =  $(this).attr("overdue"); // 逾期天数
		var repayDt	=  $(this).attr("repayDt"); // 还款期限
		var hasFeew = $(this).attr("hasFeew"); // 是否有减免
		if(type == 1)
		{	
			var tableId = pid+type; 
			waitCreateBill[waitCreateBill.length] = {'hasFeew':hasFeew ,'overdue':overdue,'repayDt':repayDt,'pid':pid,'type':type,'refNum':refNum,'itmeName':itmeName,'tableId':tableId,'otherPid':''};
		}
	});
	 
	  // 加载下一个对账单
      createOneBill();
}

//  构建一个对账单
function createOneBill()
{
	// 当所有的对账单都已经加载完毕
	if(waitCreateBill.length==0)
	{
		calculateAllTableTotal(); // 统计到对账信息平账金额和剩余金额
		
		// 有退款(20150725 退款的单独对账)
	/* 	var returnAmt = getReturnAmt();
		if(returnAmt != 0)
		{
			 // 未对账金额
			var availableReconciliationAmount = $("#availableReconciliationAmount").numberbox('getValue');
			// 平账金额
			var hedgingAmount =  $("#hedgingAmount").numberbox('getValue');
			hedgingAmount = Number(hedgingAmount)-returnAmt;
			$("#hedgingAmount").numberbox('setValue',hedgingAmount);  
			
			availableReconciliationAmount = Number(availableReconciliationAmount)+ returnAmt;
			if(availableReconciliationAmount<0)
			{
				availableReconciliationAmount = 0;
			}	
			
			$("#availableReconciliationAmount").numberbox('setValue',availableReconciliationAmount);  
		}	 */
		
		return;
	}
	
	$("#saveButtonDiv").show();//  保存按钮对账单按钮显示出来
	var data = waitCreateBill[0];
	var title = "第"+data.refNum+"期";
	var pid = data.pid;
	if(data.type == 2)
	{
		var title = "其他";
	}
	
	var tableId = data.tableId; // 跟后台约好规则
	// 逾期天数
	var overdue = Number(data.overdue);
	var toolbarId="toolBar"+tableId;
	var overDueDivHtml="<div id='"+toolbarId+"'>";
	
	// 逾期计息结束日
	var overdueDay = "${currDate}"
	if("${useRepayDt}" == "true")
	{
		overdueDay = data.repayDt;
	}
	
	// 有逾期
	if(overdue>0)
	{
		overDueDivHtml +="<table> <tr><td class='label_right'>逾期记息开始日：</td><td><input type='text' value='"+data.repayDt+"' class='text_style' readonly /></td><td class='label_right'>  逾期记息结束日：</td><td>  <input id='endDt_"+tableId+"' value='"+overdueDay+"' class='endDateText easyui-datebox' data-options='onChange:onOverdueDateChange'/></td>" 	
		    +"<td>&nbsp;<input type='button' class='text_btn'  value='逾期再计算' style='width:80px;' onclick='overdueRecalculation(\""+tableId+"\")'/>";
		    // 还款计划表中的对账才有费用减免,并且没有有效的减免记录
		    if(data.type == 1 && data.hasFeew=="false")
			{
		    	overDueDivHtml +="&nbsp;<input type='button' class='text_btn'  value='减免设定' style='width:70px;' onclick='reduction(\""+tableId+"\",\""+data.pid+"\")'/>"
			}
		    
		    overDueDivHtml +="</td></tr></table>";
		 //
	}
	overDueDivHtml +="</div>";
	$("#billDiv").append(overDueDivHtml);
	
	if(overdue>0){
		$('.endDateText').datebox();
	}
	$("#billDiv").append("<div class='fincalTableDiv'><table style='width:auto' class='fincalTable' id='"+tableId+"' title="+title+" /></div>");
	var qParams = {"pid":data.pid,"type":data.type,"otherPid":data.otherPid,"currentDt":overdueDay,"automaticReconciliationAmt":automaticReconciliationAmt};
	tableParamArray[''+tableId] = qParams;
	$("#"+tableId).datagrid({
		iconCls:'icon-edit',
		queryParams:qParams,
		url :"getReconciliationItem.action",
		rownumbers:true,
		collapsible: true,
		singleSelect: true,
		toolbar:"#"+toolbarId,
	    columns:[[
	        {field:'typeName',title:'类别',width:120,align:'center'},
	        {field: 'receivable',
	        	title:'应收',
	        	width:140,
	        	formatter:formatCss,
	        	align:'right',
	        	styler:function(){return 'color:#666';}},
	        {field:'reconciliation',title:'实收',width:140,align:'right',formatter:formatCss, editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}},
	        {field:'uncollected',title:'未收',formatter:formatCss,width:140,align:'right'},
	        {field:'remark',title:'备注',width:180,align:'center',editor:{type:'textarea'}},
	        {field:'type',hidden:true},
	        {field:'refId',hidden:true},
	        {field:'detailType',hidden:true},
	        {field:'cycleNum',hidden:true}
	    ]],
		onClickCell: onClickCellsIt,
		onLoadSuccess:tableDataLoadSuccess
	}); 
}

// 逾期再计算
function overdueRecalculation(tableId)
{
	var endDt = $("#endDt_"+tableId).datebox('getValue');	// 获取日期输入框的值
	if(endDt.trim().length==0)
	{
		$.messager.alert("提示","请选择逾期记息结束日","info");
	   return false;
	}
	
	// 自动还款可以金额
	var automaticReconciliationAmt = $('#'+tableId).parent().find(' td[field="reconciliation"]').last().find('label').html();
	
	var params = tableParamArray[''+tableId];
	if(undefined == params)
	{
		return false;
	}	
	
	waitCreateBill[waitCreateBill.length] = {'pid':params.pid,'otherPid':params.otherPid,'type':params.type,'refNum':params.refNum,'tableId':tableId};
	params.currentDt=endDt;
	$("#"+tableId).datagrid("reload",params);
}

// 减免设定
function reduction(tableId,p_pid)
{
	 var rows = $('#'+tableId).datagrid('getRows'); // 所有的行
	var _type = rows[0]['type'];
	var _refId = rows[0]['refId'];
	var _detailType = rows[0]['detailType'];
	var _cycleNum = rows[0]['cycleNum'];
		 
	// 删除旧的费用减免
	var rows = $('#'+tableId).datagrid('getRows'); // 所有的行
	for(var i=0;i<rows.length-1;i++)
	{
	    if(rows[i]['typeName']=='费用减免')
	    {
	    	$('#'+tableId).datagrid('deleteRow',i);
	    }	
	}
	
	reductionDialog(tableId,p_pid,tableId,rows.length-1,_type,_refId,_cycleNum);
}
function closeReductionDialog(){
	$('#reductionDialog').dialog('close');
}
//费用减免
function reductionDialog(tableId,planId,tId,rId,typeValue,refIdValue,cycleNumVlue){
	var url="${basePath}financeReconciliationController/reductionDialogn.action?loanId="+$("#loanId").val()+"&currDate=${currDate}&planId="+planId;
		$('#reductionDialog').dialog({
			href : url,
			width : 800,
			height : 300,
			modal : true,
			title : '费用减免',
			onBeforeClose:function(){
				
				if($('#theReductionAmt').val()>0)
				{	
					$('#'+tId).datagrid('insertRow',{
						index: rId,	// 合计前面加
						row: {
							typeName: '费用减免',
							receivable: -$('#theReductionAmt').val(),
							reconciliation: -$('#theReductionAmt').val(),
							uncollected:0,
							remark:''+$('#remark').val(),
							type:typeValue,
							refId:refIdValue,
							detailType:100,
							cycleNum:cycleNumVlue
						}
					});
				}
				// 重新计算合计
				calculateTableTotal(tableId);  
				// 统计所有的表格
				calculateAllTableTotal();
			}
		});
}


var editIndex = undefined; // 当前编辑的行
var clickTableId = "";  // 选中的table
// 结束上一个单元格的编辑
function endLastEditCell()
{
	if(clickTableId != "")
	{
	   endEditingsIt(clickTableId);
	}
}

function onClickTable(tableId)
{
	endLastEditCell();
	clickTableId = tableId;
}

// 改变了逾期日期，重算逾期罚息和利息
function onOverdueDateChange(newValue,oldValue)
{
	if("" != clickTableId)
	{
	   overdueRecalculation(clickTableId);
	}	
}

$(document).ready(function(){
	$('body').delegate('.fincalTableDiv','click',function(){
		onClickTable($(this).find('.fincalTable').attr('id'));
	});
	
	$('body').delegate('.fincalTableDiv','unClick',function(){
		onClickTable($(this).find('.fincalTable').attr('id'));
	});
	
	//
	//$('#bankNo1').combobox('setValue',-1);
	//$('#bankNo1').combobox('setValue',${bean.loanInterestRecord});	
	//getAccountByPrimaryKeyAccrual(${bean.loanInterestRecord});

});


// 提交保存
function submitReconciliation2()
{ 
	endLastEditCell(); // 结束正在编辑的单元格
	// 统计所有的表格
	calculateAllTableTotal();
	// 未对账金额（退款会是一个负数，所以这里不再判断）
	 var availableReconciliationAmount = Number($("#availableReconciliationAmount").numberbox('getValue'));
	 // 可对账金额 
	 var waitReconciliationAmount = Number($("#waitReconciliationAmount").numberbox('getValue'));
	 
	// 退款对账 
	if(waitReconciliationAmount<0)
	{
		if(availableReconciliationAmount>0)
		{
			$.messager.alert("提示","退款对账中，未对账金额不能大于0,请检查","info");
			return false;
		}	
		
		// 未对账小于可对账
		if(availableReconciliationAmount<waitReconciliationAmount)
		{
			$.messager.alert("提示","退款对账中，实收数据不能大于0,请检查","info");
			return false;
		}	
	}	
	// 正常对账
	else
	{
		if(availableReconciliationAmount>waitReconciliationAmount)
		{
			$.messager.alert("提示","未对账金额大于可对账金额,请检查","info");
			return false;
		} 
	}	
	
	var _typeName =""; 
	var _type="";
	var _refId="";
	var _detailType="";
	var _reconciliationAmt="";
	var _remark="";
	var _cycleNum="";
	var _uncollected="";
	var _currentDt="";
	var checkResult = true;
	var currentDt=""; // 对账日期
	// 遍历所有要对账的表格
	$("#billDiv").find("table[id]").each(function(){
		var tableId = $(this).attr("id");// 表格Id
		var rows = $('#'+tableId).datagrid('getRows'); // 所有的行
		var title = $('#'+tableId).attr("title"); // 对应的title
		
		 // 合计项
		 receivable =  Number(rows[rows.length-1]['receivable']);  //  应收
		 uncollected = Number(rows[rows.length-1]['uncollected']); // 未收
		 reconciliation = Number(rows[rows.length-1]['reconciliation']); // 实收
		    // 默认收款时间
			 try
			 {
				 currentDt =  $("#endDt_"+tableId).datebox('getValue');
				
			 }
			 catch(e)
			 {
				 currentDt = $("#paymentDttm").datebox('getValue');
			 }
		
		  // 合计项有应收和已收为0   (退款是一个负数，所以这里不再判断）
/* 		 if(receivable <0 || uncollected<0|| reconciliation<0)
		 {
			 $.messager.alert("提示","合计项为负数，请检查数据","info");
			 checkResult = false;
			 return false;
		 }	  */
		
		for(var i=0;i<rows.length-1;i++)
		{
			 receivable =   Number(rows[i]['receivable']);  //  应收
			 reconciliation = Number(rows[i]['reconciliation']); //  实收
			 uncollected = Number(rows[i]['uncollected']); // 未收
			 
			 // 非  减免、退管理费、退利息、退其他费用 之外的，不能是负数
			 if(uncollected<0 &&  100 != rows[i]['detailType'] &&  4 != rows[i]['detailType'] &&  5 != rows[i]['detailType'] &&  6 != rows[i]['detailType']){
				 $.messager.alert("提示","第"+(i+1)+"行,未收不能是负数，请检查实收金额","info");
				 checkResult = false;
				 return false;
			 }
			 
			 // 实收超出了应收范围 ,如果应收是正数，实收大于应收，或者 应收是负数，实收小于应收
			 if((receivable>0 && (reconciliation- receivable)>0) || (receivable<0 && (reconciliation- receivable)<0))
			 {
				 $.messager.alert("提示",title+"中，第"+(i+1)+"行,实收金额超出了应收金额，请检查","info");
				 checkResult = false;
				 return false;
			 }	 
			 
			 _typeName += rows[i]['typeName']+",";
			 _type += rows[i]['type']+",";
			 _refId += rows[i]['refId']+",";
			 _detailType += rows[i]['detailType']+",";
			 _reconciliationAmt += reconciliation+",";
			 _remark += rows[i]['remark']+",";
			 _cycleNum += rows[i]['cycleNum']+',';
			 _uncollected += uncollected+',';
			 _currentDt+=currentDt+',';
			//reconciliationDtail[reconciliationDtail.length] = {'typeName':rows[i]['typeName'],'type':rows[i]['type'],'refId':rows[i]['refId'],'detailType':rows[i]['detailType'],'reconciliationAmt':reconciliation,'description':rows[i]['remark']};
		}
		
		 // 如果只有一行，并且是计划表的数据(当前期没有应收了，特殊情况，如:只剩逾期利息和罚息，调整后变为0),提交到后台更改对账状态
		if(rows.length==1 && rows[0]['type']==1)
		{
			 receivable =   Number(rows[0]['receivable']);  //  应收
			 reconciliation = Number(rows[0]['reconciliation']); //  实收
			 uncollected = Number(rows[0]['uncollected']); // 未收
			 
			 _typeName += rows[0]['typeName']+",";
			 _type += rows[0]['type']+",";
			 _refId += rows[0]['refId']+",";
			 _detailType += "-1,";
			 _reconciliationAmt += reconciliation+",";
			 _remark += rows[0]['remark']+",";
			 _cycleNum += rows[0]['cycleNum']+',';
			 _uncollected += uncollected+',';
			 _currentDt+=currentDt+',';
		}	
   });
	
	// 如果检查通不过
	if(!checkResult)
	{
	   return false;
	}
	
	_remark +="end"; // 在后面加上一个str，避免后台spilt的时候丢失
	var postData={"waitReconciliationAmount":waitReconciliationAmount,"availableReconciliationAmount":$("#availableReconciliationAmount").numberbox('getValue'),
			"receivablesVersion":$("#financeReceivablesVersion").val(),
			"receivablesId":$("#financeReceivablesDTOId").val(),
			"currentDt":_currentDt,
			"loanId":$("#loanId").val(),
			"typeName":_typeName,
			"type":_type,
			"refId":_refId,
			"detailType":_detailType,
			"reconciliationAmt":_reconciliationAmt,
			"remark":_remark,
			"cycleNum":_cycleNum,
			"uncollected":_uncollected,
			"loanInterestRecord":$("#loanInterestRecord").val(),
			"loanMgrRecord":$("#loanMgrRecord").val(),
			"loanOtherFee":$("#loanOtherFee").val()
			};
	// 保存提交
	$.ajax({
		type: "POST",
        data:postData,
		url : "saveReconciliation.action",
		dataType: "json",
	    success: function(data){
	    	// 保存成功
	    	if(data.header.success)
	    	{
	    		$.messager.alert("提示","保存成功","info");
	    		//显示转入金额按钮，退款处理按钮 backAmt,moveAcct
	    		showButton();
	    	}
	    	// 保存失败
	    	else
    		{
	    		$.messager.alert("提示",data.header.msg,"error");
    		}
		},error : function(result){
			$.messager.alert("提示","提交失败","error");
		}
	}); 
}

// 关闭当前页面
function closePage()
{
	parent.removePanel();
}


//add by yql


//转入账户余额处理
function moveAcct(){
 var amt=$('#availableReconciliationAmount').numberbox('getValue');
 var receivablesId=$("#financeReceivablesDTOId").val();
 var loanId=$("#loanId").val();
  if(amt>0){
	  $('<div id="saveAcctProjectBalanceDiv"/>').dialog({
			href : '${basePath}financeReconciliationController/getAcctProjectBalance.action?receivablesId='+receivablesId+'&loanId='+loanId,
			width : 650,
			height : 300,
			modal : true,
			title : '多付金额转入客户余额处理',
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					window.save();
				}	
				},{
					text : '取消 ',
					iconCls : 'icon-no',
					handler : function() {
						$("#saveAcctProjectBalanceDiv").dialog('close');
						}
					} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
			}
		});
  }else{
	  $.messager.alert("提示","未对账金额需大于0！","error");
  }
		
	
	
}

//显示转入金额按钮，退款处理按钮 backAmt,moveAcct
function showButton(){
	$("#backAmtId").show();
	$("#moveAcctId").show();
	
   $("#reconciliationOptionTable").hide();
   cleanBillDiv();
}
//退款处理
function backAmt(){
	var receivablesId=$("#financeReceivablesDTOId").val();
	var loanId=$("#loanId").val();
	var amt=$('#availableReconciliationAmount').numberbox('getValue');
	if(amt>0){
		parent.openNewTab("${basePath}financeReconciliationController/getReturnAmtInfoList.action?receivablesId="+receivablesId+"&loanId="+loanId,"多付金额退还处理");
	}else{
		  $.messager.alert("提示","未对账金额需大于0！","error");
	}
}
//设置贷款利息收取单位帐号
function getAccountByPrimaryKeyAccrual(rec){
	// 判断是不是请选择,如果是直接退出
	if(rec.pid == -1){
		return;
	}
	// 设置帐号
	$("#loanInterestRecord").val(rec.pid);
	$("#recAmtForm input[name='loanInterestRecordNo']").val(rec.bankNum);
}

// 设置贷款管理费收取单位帐号
function getAccountByPrimaryKeyFees(rec){
	// 判断是不是请选择,如果是直接退出
	if(rec.pid == -1){
		return;
	}
	// 设置帐号
	$("#loanMgrRecord").val(rec.pid);
	$("#recAmtForm input[name='loanMgrRecordNo']").val(rec.bankNum);
}

// 设置其它费用收取单位帐号
function getAccountByPrimaryKeyOther(rec){
	// 判断是不是请选择,如果是直接退出
	if(rec.pid == -1){
		return;
	}
	// 设置帐号
	$("#loanOtherFee").val(rec.pid);
	$("#recAmtForm input[name='loanOtherFeeNo']").val(rec.bankNum);
}

// add by sql
//验证收入金额的提示
/* function paymentAmountFilter(e){
	if(e.which==45){
		$.messager.alert("提示","收入金额需大于0！","error");
	}
} 

function useBalanceFilter(e){
	if(e.which==45){
		$.messager.alert("提示","使用余额需大于0！","error");
	}
} */

</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="main_body">
		<div id="cus_content" style="width:auto;">
		<div  class="easyui-panel approval_disappr" data-options="collapsible:true" style="padding:10px" title="项目基础信息" style="width:98%">
			<table class="cus_table" style="border:none;">
				<tr>
					<td class="align_right">项目名称：</td>
					<td colspan="3"><h2>
							<a href="javascript:void(0)"
								onclick="formatterProjectName.disposeClick(${bean.projectId},'${bean.projectNumber}')"><font
								color="blue">${bean.projectName}</font></a>
						</h2></td>
				</tr>
				<tr>
					<td class="align_right">项目编号：</td>
					<td colspan="3"><h2>${bean.projectNumber}</h2></td>
				</tr>
				<tr>
					<td class="align_right">业务类别：</td>
					<td style="width: 300px;"><h2>${bean.businessCatelog}</h2></td>
					<td class="align_right">业务品种：</td>
					<td><h2>${bean.businessType}</h2></td>
				</tr>
				<tr>
					<td class="align_right">流程类别：</td>
					<td><h2>${bean.flowCatelog }</h2></td>
					<td class="align_right">项目经理：</td>
					<td><h2 />${bean.realName}</h2></td>
				</tr>

			</table>
		</div>
			<div style="padding: 10px 0 0 0;"></div>
				
			<div  class="easyui-panel approval_disappr" data-options="collapsible:true" style="padding:10px" title="收款信息" style="width:98%">

					<form id="recAmtForm"
						action="{basePath}financeController/saveRecAmt" method="POST">
						<input type="hidden" name="loanId" id="loanId"
							value="${bean.loanId}" /> <input type="hidden"
							id="financeReceivablesDTOId" name="pid"
							value="${bean.financeReceivablesDTO.pid}"> <input
							type="hidden" id="financeReceivablesVersion"
							value="${bean.financeReceivablesDTO.version}"> <input
							type="hidden" id="loanInterestRecord"
							value="${bean.loanInterestRecord}"> <input type="hidden"
							id="loanMgrRecord" value="${bean.loanMgrRecord}"> <input
							type="hidden" id="loanOtherFee" value="${bean.loanOtherFee}">

						<table class="cus_table" style="border:none;">
							<tr>
								<td class="align_right" style="width: 122px;">当前日期：</td>
								<td><input id='currtDttm'
									class='endDateText easyui-datebox' value="${currDate}" /></td>
								<td colspan="2"></td>
							</tr>
							<tr>
								<td class="align_right" style="width: 170px;">小贷利息收取银行账号：</td>
								<td><input id="bankNo1" name="loanInterestRecord"
									class="easyui-combobox" editable="false" panelHeight="auto"
									data-options="valueField:'pid',textField:'chargeName',value:'${bean.loanInterestRecord}',url:'<%=basePath%>secondBeforeLoanController/getFinanceAcctManage.action',onSelect:getAccountByPrimaryKeyAccrual" />
								</td>
								<td colspan="2"><input name="loanInterestRecordNo"
									value="${bean.loanInterestRecordNo}" readonly="readonly"
									type="text" class="text_style" style="width: 234px;" /></td>
							</tr>
							<tr>
								<td class="align_right">小贷管理费收取银行账号：</td>
								<td><input name="loanMgrRecord" class="easyui-combobox"
									editable="false" panelHeight="auto"
									data-options="valueField:'pid',textField:'chargeName',value:'${bean.loanMgrRecord}',url:'<%=basePath%>secondBeforeLoanController/getFinanceAcctManage.action',onSelect:getAccountByPrimaryKeyFees" />
								</td>
								<td colspan="2"><input type="text" readonly="readonly"
									value="${bean.loanMgrRecordNo}" name="loanMgrRecordNo"
									class="text_style easyui-validatebox" style="width: 234px;" /></td>
							</tr>
							<tr>
								<td class="align_right">小贷其他费用收取银行账号：</td>
								<td><input name="loanOtherFee" class="easyui-combobox"
									editable="false" panelHeight="auto"
									data-options="valueField:'pid',textField:'chargeName',value:'${bean.loanOtherFee}',url:'<%=basePath%>secondBeforeLoanController/getFinanceAcctManage.action',onSelect:getAccountByPrimaryKeyOther" />
								</td>
								<td colspan="2"><input type="text" name="loanOtherFeeNo"
									value="${bean.loanOtherFeeNo}" readonly="readonly"
									class="text_style easyui-validatebox" style="width: 234px;" /></td>
							</tr>
							<tr>
								<td class="align_right"><h2>实际收款金额：</h2></td>
								<td><input type="text" class="easyui-numberbox"
									precision="2" groupSeparator="," id="paymentAmount"
									name="paymentAmount" 
									data-options="required:true,validType:'number'"
									value="${bean.financeReceivablesDTO.paymentAmount}" /></td>
								<td><h2>
										收款日期： <input id='paymentDttm'
											class='endDateText easyui-datebox' value="${paymentDttm}"/></td>
								<td></td>
							</tr>
							<tr id="customerBalanceTable">
								<td class="align_right"><font color="#CA8EF">客户余额：</font></td>
								<td><font color="#CA8EF"><div
											id="customerBalanceDiv">${bean.customerBalance}</div></font></td>
								<td><input id="customerBalanceCheckbox" type="checkbox"
									onclick="changeCustomerBalance();" /> &nbsp;使用： <input
									type="text" class="easyui-numberbox" precision="2"
									groupSeparator="," min="0"
									data-options="required:true,validType:'number'"
									value="${bean.financeReceivablesDTO.useBalance}"
									name="useBalance" id="useBalance"
									data-options="validType:'number'" /> <input type="button"
									class="text_btn" id="useBalanceBtn" style="display: none"
									value="使用余额" onclick="useBalanceSave();" /></td>
								<td><input type="button" class="text_btn"
									id="saveReceivablesBtn" value="收款保存"
									onclick="saveFinanceReceivables();" /></td>
							</tr>
						</table>
						</table>
						<table id="reconciliationTable" class="cus_table"
							style="display: none">
							<tr>
								<td class="align_right"
									style="text-align: left; font-weight: bold; padding-left: 8px;"
									colspan="4">对账信息</td>
							</tr>

							<tr>
								<td class="align_right"><font color="#E800E8">可对账金额：</font></td>
								<td><input type="text" disabled="true" style="width:130px;"
									class="easyui-numberbox" precision="2" groupSeparator=","
									id="waitReconciliationAmount" name=""
									value="${bean.waitReconciliationAmount}" /></td>
								<td class="align_right"><font color="#E800E8">平账金额：</font></td>
								<td><input type="text" disabled="true" style="width:130px;"
									class="easyui-numberbox" precision="2" groupSeparator=","
									id="hedgingAmount" name="" value="${bean.hedgingAmount}" /></td>
							</tr>
							<tr>
								<td class="align_right"><font color="#E800E8">未对账金额：</font></td>
								<td><input disabled="true" class="easyui-numberbox" style="width:130px;"
									precision="2" groupSeparator=","
									id="availableReconciliationAmount" name=""
									value="${bean.availableReconciliationAmount}" /></td>
								<td colspan="2" align="center"><input type="button"
									id="backAmtId" style="display: none" class="text_btn"
									value="退还处理" onclick="backAmt();" />&nbsp; <input
									type="button" id="moveAcctId" style="display: none"
									class="text_btn" value="转入账户余额" style="width:100px;"
									onclick="moveAcct();" /></td>
							</tr>
						</table>
						<!-- 对账选择 -->
						<table class="cus_table" style="display: none;"
							id="reconciliationOptionTable">
							<tr style="height: 25px;">
								<th colspan="4">对账选择</th>
							</tr>
							<tbody id="optionsBody"></tbody>
							<tr>
								<td colspan="4" align="right" height="35"><input
									type="checkbox" id="automaticReconciliation" checked="checked">
									自动对账 &nbsp; <input type="button" class="text_btn" value="生成账单"
									onclick="createBill();" />&nbsp;</td>
							</tr>
						</table>
						<!-- 放置对对账单的div -->
						<div id="billDiv"></div>
						<div id="saveButtonDiv" style="display: none; text-align: center;">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-save" onclick="submitReconciliation2();">保存</a> <a
								href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-clear" onclick="closePage();">取消</a>
						</div>
					</form>
			</div>
					<div id="reductionDialog"></div>
				
			</div>
		</div>
</body>


