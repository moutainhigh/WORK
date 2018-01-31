<%@page import="com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/exportRepayment.js"></script>
<%
	String isEdit=request.getParameter("isEdit");
	RepaymentLoanInfo loanInfo=(RepaymentLoanInfo)request.getAttribute("repayment");
	String dateEdit="";
	String numberEdit="";
	if(loanInfo!=null){
		if("9".equals(loanInfo.getRepayFunVal()) && "1".equals(isEdit)){
			dateEdit=",editor:{type:'datebox',options:{precision:2}}";
			numberEdit= ",editor:{type:'numberbox',options:{precision:2}}";
		}
	}
	
	if("2".equals(isEdit)){
		dateEdit=",editor:{type:'datebox',options:{precision:2}}";
	}
	request.setAttribute("dateEdit", dateEdit);
	request.setAttribute("numberEdit", numberEdit);
	request.setAttribute("isEdit", isEdit);
	
%>
<style type="text/css">

</style>

<script type="text/javascript">

var editIndex = undefined;
function endEditingsIt(){
	if (editIndex == undefined){return true}
	if ($('#repaymentPlanGrid${isEdit}').datagrid('validateRow', editIndex)){
		$('#repaymentPlanGrid${isEdit}').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickCellsIt(index, field){
	if (endEditingsIt()){
		$('#repaymentPlanGrid${isEdit}').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		editIndex = index;
	}
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

$(function(){
	//$('.creditAmtFormatter').html(new Number(accounting.formatMoney($('.creditAmtFormatter').text()), "", 2, ",", "."));
	
	//结束编辑,计算合计
	$('#repaymentPlanGrid${isEdit}').datagrid({    
		onClickRow:function(rowIndex){
			var rows = $("#repaymentPlanGrid${isEdit}").datagrid('getRows'); 
			var rowNum=rows.length-1;
			
			var totals=0;
			var shouldPrincipalTotal=0;
		    var shouldMangCostTotal=0;
		    var shouldOtherCostTotal=0;
		    var shouldInterestTotal=0;
		    
			
			for(var i=0;i<rows.length;i++){
				
				 //即时发生表数据不可编辑
				if(rows[i].dataType !=2 && rows[i].dataType !=4){
					$('#repaymentPlanGrid${isEdit}').datagrid('endEdit',i);
				} 
				 if(i<rowNum){
					
					totals+=parseFloat(convertZero(rows[i].total));
					shouldPrincipalTotal+=parseFloat(convertZero(rows[i].shouldPrincipal));
					shouldMangCostTotal+=parseFloat(convertZero(rows[i].shouldMangCost));
					shouldOtherCostTotal+=parseFloat(convertZero(rows[i].shouldOtherCost));
					shouldInterestTotal+=parseFloat(convertZero(rows[i].shouldInterest));
				} 
				
				
			}
			
			//修改合计的值
			$('#repaymentPlanGrid${isEdit}').datagrid('updateRow',{index:rowNum,row:{
				total:Digit.round(totals, 2),
				shouldPrincipal:Digit.round(shouldPrincipalTotal, 2),
				shouldMangCost:Digit.round(shouldMangCostTotal, 2),
				shouldOtherCost:Digit.round(shouldOtherCostTotal, 2),
				shouldInterest:Digit.round(shouldInterestTotal, 2)}
			});
			
	    }    
	});  
});

//格式化
function convertZero(content){
	if(isNaN(content) || typeof(content) == "undefined" || content=="" || content==null){
		return "0";
	}else{
		return content;
	}
}

//数值四舍五入
var Digit = {};
Digit.round = function(digit, length) {
    length = length ? parseInt(length) : 0;
    if (length <= 0) return Math.round(digit);
    digit = Math.round(digit * Math.pow(10, length)) / Math.pow(10, length);
    return digit;
};

//计算合计
function calRepayTotals(j){
	
}

//数字格式化
function thsou(value,row){
	value = formatMoney(value,1);
	return value;
}

function formatMoney(s, type) {
	if (s == null || s == "" || s == 0)
		return "0";
	s = s.toString().replace(/^(\d*)$/, "$1.");
	s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
	s = s.replace(".", ",");
	var re = /(\d)(\d{3},)/;
	while (re.test(s))
		s = s.replace(re, "$1,$2");
	s = s.replace(/,(\d\d)$/, ".$1");
	if (type == 0) {// 不带小数位(默认是有小数位)  
		var a = s.split(".");
		if (a[1] == "00") {
			s = a[0];
		}
	}
	return s;
}


//保存利润表
function saveRepaymentPlan(){
	//grid去除编辑状态
	endEditingsIt();
	var contents = $("#repaymentPlanGrid${isEdit}").datagrid('getRows');//获得datagrid表的所有行的值
	var shouldOtherCostName = $("#shouldOtherCostName").val();
	var repayFun='${repayment.repayFunVal}';
	var loanId='${loanId}';
	// 其它还款方式分批次保存数据，避免请求数据过多页面报错
	var content = '';
	var count = 1 ;
	var result = false;
	for(var i=0 ; i<contents.length;i++){
		// 数据拼装
		if(count == 1 ){
			content = '[';
		}
		// 最后一条数据为合计，不用添加
		if( i != (contents.length -1)){
			content+=JSON.stringify(contents[i]);
		}
		
		if(count!=6 &&  i!=(contents.length -2) ){
			content+=",";
		}else{
			content+="]";
			count = 0 ;
			// 数据保存
			$.ajax({
				type: "POST",
		        data:{"shouldOtherCostName":shouldOtherCostName,"repayFun":repayFun,"loanInfoId":loanId,"content":content},
		        async:false, 
		    	url : BASE_PATH+'beforeLoanController/saveRepaymentPlan.action',
				dataType: "json",
			    success: function(data){
			    	var data = data.trim();
					if(data=="saveSucc"){
						result = true;
					}else{
						result = false;
					}
				}
			});  
		}
		count++;
	}
	if(result){
		$.messager.alert('保存还款计划表','保存成功!','info');
		window.$("#repaymentDiv").dialog('close');
	}else{
		$.messager.alert('保存还款计划表','保存失败!','error');
	}
}
</script>
	<!--图标按钮 -->
<div id="toolbar" class="easyui-panel" border="false" style="border-bottom: 0;">
	<div id="main_body">
	<div id="cus_content">
			<table style="border:none;width:700px;">
				<tr>
					<td class="label_right">贷款人：</td>
					<td colspan="3" width="240">${repayment.acctName}</td>
					<td class="label_right" style="width:120px;">借款合同编号：</td>
					<td colspan="3" width="240">${repayment.contractNo }</td>
				</tr>
				<tr>
					<td class="label_right">放款日期：</td>
					<td colspan="3">${repayment.loanOutDt}</td>
					<td class="label_right">最后还款日：</td>
					<td colspan="3">${repayment.loanRepayDt }</td>
				</tr>
				<tr>
					<td class="label_right">借款期限：</td>
					<td colspan="3">${repayment.repayCycle}</td>
					<td class="label_right">借款金额：</td>
					<td colspan="3" >${repayment.formatAmt }<!-- <div class="creditAmtFormatter">${repayment.creditAmt }</div> --></td>
				</tr>
				<tr>
					<td class="label_right">月利率（%）：</td>
					<td>${repayment.loanInterest}</td>
					<td  class="label_right">管理费率（%）：</td>
					<td>${repayment.loanMgr }</td>
					<td class="label_right">其它费用（%）：</td>
					<td>${repayment.loanOtherFee}</td>
					<td class="label_right">手续费（%）：</td>
					<td>${repayment.feesProportion }</td>
				</tr>
			</table>
		</div>
	</div>
	
</div>
<form id="repaymentPlanForm" method="post">
	<div id="exportDiv" style="border-bottom: 0;">
		<div id="#toolbar_export" class="easyui-panel datagrid-toolbar"  style="border-bottom: 0;">
			<a href="javascript:void(0)" class="easyui-linkbutton download" 
			         iconCls="icon-export" plain="true" onclick="exportRepay.exprotRepayment('REPAYMENT_BEFORELOAN',${loanId },newProjectId,'${isEdit}')">导出Excel</a>
		</div>			
	</div>	
	<table id="repaymentPlanGrid${isEdit}"  class="easyui-datagrid" 
		style="height:auto;width: auto;"
		data-options="
			iconCls: 'icon-edit',
			singleSelect: true,
			toolbar: '#toolbar_export',
			border:false,
			url: '${basePath}beforeLoanController/repaymentListUrl.action?loanId=${loanId }',
			method:'post',
			onClickCell: onClickCellsIt
		    ">
		<!-- 表头 -->
		<thead>
			<tr>
				<th align="center" hidden="true" rowspan="2" data-options="field:'pId'">
			    <th align="center" data-options="field:'planRepayDt',width:90,align:'center'${dateEdit }" rowspan="2">还款日期</th>
			    <th align="center" data-options="field:'planCycleName',width:60,align:'center'" rowspan="2">期数</th>
			    <th align="center" data-options="" colspan="4" >本期应还明细</th>
			    <th data-options="field:'total',width:80,formatter:thsou,align:'right'${numberEdit }" rowspan="2">合计</th>
			    <th data-options="field:'principalBalance',width:80,formatter:thsou,align:'right'${numberEdit }" rowspan="2">本金余额</th>
			</tr>
			<tr>
			    <th data-options="field:'shouldPrincipal',width:100,formatter:thsou,align:'right'${numberEdit }">本期应还本金</th>
			    <th data-options="field:'shouldMangCost',width:100,formatter:thsou,align:'right'${numberEdit }">本期应付管理费</th>
			    <th data-options="field:'shouldOtherCost',width:90,formatter:thsou,align:'right'${numberEdit }">
			    	<c:if test="${isEdit=='1' }">
			    		<input id="shouldOtherCostName" class="easyui-validatebox"  value="${repayment.otherCostName }"  style="width:100%;border:none;background: #f1f1f1;color:#72acf3;" />
			    	</c:if>
			    	<c:if test="${isEdit=='2' }">
			    		${repayment.otherCostName }
			    	</c:if>
		    	</th>
			    <th data-options="field:'shouldInterest',width:100,formatter:thsou,align:'right'${numberEdit }">本期应付利息</th>
			</tr>
		</thead>
	</table>
</form>
