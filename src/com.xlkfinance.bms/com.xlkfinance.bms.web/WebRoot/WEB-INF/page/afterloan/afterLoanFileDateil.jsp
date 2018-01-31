<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/system/cneter.js"></script>
<script type="text/javascript">
var Iam = "AFTER";
var url = "";
var projectId="${projectId}";
var pName = "${project.projectName}";
var mflag = "${mflag}";
// 提交
function editCommit(){
	if($('#pid').val()=="" ||　$('#pid').val()==undefined){
		$('#enterpriseForm').attr('action','<%=basePath%>afterLoanFileController/addProjectStatus.action');
	}else{
		$('#enterpriseForm').attr('action','<%=basePath%>afterLoanFileController/updateProjectStatus.action');
	}
	if($('#afterloanStatus').combobox('getValue')==1){
		var rows = $('#projectFileArchive').datagrid('getData');
		for(var i=0;i<rows.total;i++){
			if(rows.rows[i]){
				if(rows.rows[i].isArchiveValue=='否'){
					$.messager.alert('操作提示','请确认归档全部的归档资料','error');
					return false;
				}
			}
		}
	}
	mCommit();
}

function mCommit(){
	$("#enterpriseForm").form('submit',{
		success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$.messager.alert('操作提示', '归档办理成功!', 'success',function(){
					var myObj = parent.$('#layout_center_tabs').tabs('getTab','归档项目查询');
					if(myObj){
					//获取iframe的URL
					var url = myObj[0].innerHTML;
					//打开数据
					parent.layout_center_addTabFun({
						title : '归档项目查询', //tab的名称
						closable : true, //是否显示关闭按钮
						content : url,//加载链接
						falg:true
					});
					}
					parent.$('#layout_center_tabs').tabs('close','办理项目归档');
				})
			}else{
				$.messager.alert('操作提示','提交失败','error');	
			}
			
		}
	});
}

 function mhref(){
	 window.location.href="<%=basePath%>afterLoanFileController/getAfterLoadFileRecord.action"; 
 }
 

$(function() {
	Iam = "AFTER";
	projectId="${projectId}";
	mflag = "${mflag}";
	if(mflag==1){
		$("#mbutt").hide();
		$('#projectFileArchive').datagrid({
			onLoadSuccess:function(){
		    	if(mflag!=undefined && mflag==1){
		    		$('.archive_dis .easyui-linkbutton ,.archive_dis input,.archive_dis textarea,.archive_dis a').attr('disabled','disabled');
		    		$('.archive_dis .easyui-linkbutton ,.archive_dis input,.archive_dis textarea,.archive_dis a').attr('readOnly','readOnly');
		    		$('.archive_dis .easyui-linkbutton,.archive_dis a,.archive_dis a').removeAttr('onClick');
		    		$('.archive_dis a font').css('color','gray');
		    		$('.archive_dis .datagrid-toolbar a, .archive_dis .easyui-linkbutton ,.archive_dis  input,.archive_dis  textarea').addClass('l-btn-disabled');
		    	}
	    	}
		})
	}else{
		$("#mbuttdate").hide();
	}
	
	$('#ttt').datagrid({
		title : '',
		iconCls : 'icon-ok',
		singleSelect : true,
		checkOnSelect : false,
		selectOnCheck : true,
		rownumbers : true,
		idField : 'pId',
		method : 'POST',
		url : '<%=basePath%>rePaymentController/advapplyrepaymenturl.action?projectId=${projectNumber}',
		pagination : true,
		frozenColumns : [ [ , ] ],
		columns : [ [ {
			field : 'planRepayDt',
			title : '还款日期',
			rowspan : 2,
			width : 80

		}, {
			field : 'planCycleNum',
			title : '还款期数',
			rowspan : 2,
			align : 'center',
			width : 60,
			formatter : function(val, rec) {
				if (val == -2) {
					return "挪用罚金";
				} else if (val == -1) {
					return "坏账";
				} else if (val == -3) {
					return "违约罚金";
				} else {
					return val;
				}
			}
		}, {
			title : '本期应还款明细',
			colspan : 5
		},

		{
			field : 'principalBalance',
			title : '本金余额',
			width : 100,
			align : 'right',
			rowspan : 2,
			formatter :formatMoney
		}, {
			field : 'overdueLoanmt',
			title : '应收预期利息',
			width : 80,
			align : 'right',
			rowspan : 2,
			formatter : formatMoney
		}, {
			field : 'overdueFineAmt',
			title : '应收预期罚息',
			width : 100,
			align : 'right',
			rowspan : 2,
			formatter :formatMoney
		}, {
			field : 'shouldTotailAmt',
			title : '应收合计',
			width : 100,
			align : 'right',
			rowspan : 2
		}, {
			field : 'hasReceiveAmt',
			title : '已收合计',
			width : 100,
			align : 'right',
			rowspan : 2,
			formatter : formatMoney
		}, {
			field : 'noReceiveAmt',
			title : '未收合计',
			width : 100,
			align : 'right',
			rowspan : 2,
			formatter :formatMoney
		} ], [ {
			field : 'shouldPrincipal',
			title : '本期应还本金',
			width : 100,
			align : 'right',
			formatter :formatMoney
		}, {
			field : 'shouldMangCost',
			width : 100,
			align : 'right',
			title : '本期应付管理费',
			formatter : formatMoney
		}, {
			field : 'shouldOtherCost',
			width : 100,
			align : 'right',
			title : '其他费用',
			formatter : formatMoney
		}, {
			field : 'shouldInterest',
			width : 100,
			align : 'right',
			title : '本期应付利息',
			formatter : formatMoney
		}, {
			field : 'total',
			width : 100,
			align : 'right',
			title : '合计',
			formatter : formatMoney
		} ] ]
	});
	$('#ttt').datagrid({
		rowStyler : function(index, row) {
			if (row.planCycleNum == -2) {
				return 'background-color:red;';
			}
			if (row.planCycleNum == -3) {
				return 'background-color:blue;';
			}
			if (row.planCycleNum == -1) {
				return 'background-color:pink;';
			}
			if (row.isReconciliation == 1) {
				return 'background-color:gray;';
			}
		}
	});
	if(projectId!=""){
		$.ajax({
			url:'<%=basePath%>afterLoanFileController/getAfterLoanArchive.action',
			type:'post',
			data:{projectId:projectId},
			dataType:'json',
			success:function(response){
				$('#afterloanStatus').combobox('setValue',response.afterloanStatus);
				$('#afterloanComments').textbox('setValue',response.afterloanComments);
				$('#pid').val(response.pid);
			}
		})
	}
	
});

function resetForm(){
	mhref();
}
 
//查看
function perItem() { 
	parent.openNewTab("${basePath}beforeLoanController/addNavigation.action?projectId="+projectId+"&param='refId':'"+projectId+"','projectName':'"+pName+"'","项目详情",true);
}

$(document).ready(function() {
	
	
	$('#addProjectFileArchive').attr('disabled','disabled');
	$('#addProjectFileArchive').attr('readOnly','readOnly');
	$('#addProjectFileArchive').addClass('l-btn-disabled');
	$('#addProjectFileArchive').removeAttr('onclick');

	$('#remove_projectFileArchive_data').attr('disabled','disabled');
	$('#remove_projectFileArchive_data').attr('readOnly','readOnly');
	$('#remove_projectFileArchive_data').addClass('l-btn-disabled');
	$('#remove_projectFileArchive_data').removeAttr('onclick');

	$('#refresh_data').attr('disabled','disabled');
	$('#refresh_data').attr('readOnly','readOnly');
	$('#refresh_data').addClass('l-btn-disabled');
	$('#refresh_data').removeAttr('onclick');
});
</script>
<title>贷后管理</title>
</head>
<body  class="easyui-layout">
<div data-options="region:'center',border:false">
	<div class="easyui-panel" title="办理归档 当前处理人：${currUser.realName}" data-options="collapsible:true">
		<!--图标按钮 -->
			<div id="cus_content">
				<table class="cus_table"
					style="min-width: 700px; border: none; width: 700px;">
					<tr>
						<td class="align_right">项目名称：</td>
						<td>
							 <a id='aid'  onclick="formatterProjectName.disposeClick(${projectId},'${project.projectNumber}')"  href='#'> 
	            			 <span style='color:blue;'>${project.projectName}</span></a>
	            			</td>
						<td class="align_right">项目编号：</td>
						<td>${project.projectNumber}</td>
					</tr>
					<tr>
						<td class="align_right">业务类别：</td>
						<td>${project.businessCatelogText}</td>
						<td class="align_right">业务品种：</td>
						<td>${project.businessTypeText }</td>
					</tr>
					<tr>
						<td class="align_right">流程类别：</td>
						<td>${project.flowCatelogText}</td>
						<td class="align_right">项目经理：</td>
						<td>${project.realName}</td>
					</tr>
				</table>
		</div>
	</div>
	
	<!-- 放款收息表 -->
	<div class="pt10"></div>
	
	<div class="easyui-panel" title="放款收息表" data-options="collapsible:true">
	<div id="tablegrid">
	<%@ include file="../repayment/list_loanCalculate.jsp"%>
	</div>
    </div>
	
	<!-- 项目资料归档 -->
	<div class="pt10"></div>
	<div id="include_projectArchive">
		<%@ include file="/WEB-INF/page/beforeloan/projectArchive.jsp"%>
	</div>
	
	<!-- 项目归档说明 -->
	<div class="pt10"></div>
	<div class="easyui-panel" title="项目归档说明" data-options="collapsible:true">

	 <div style="padding-bottom: 5px; text-align: center;" id="duidang">
	 <form action="" id="enterpriseForm" name="enterpriseForm" method="post">
		<input type="hidden" name="projectId"  value="${projectId}"/>
		<input type="hidden" name="pid" id="pid"/>
		<table class="beforeloanTable" style="width: 100%">
			<tr id="next_node_tr" style="height: auto;">
			</tr>
			<tr id="reject_select_tr" style="height: auto;">
			</tr>
			<tr class="pview">
				<td class="label_right" width="100"><font color="red">*</font>归档状态：</td>
				<td colspan="3">
					<select class="easyui-combobox" id="afterloanStatus"  name="afterloanStatus" style="width:115px;" required="true" panelHeight="auto" editable="false" >
								<option value="0">未归档</option>
								<option value="1">已归档</option>
							</select> 
				</td>
			</tr>
			<tr>
				<td class="label_right"><font color="red">*</font>备注与说明：</td>
				<td colspan="3">
					<div style="width: 100%">
						<input class="easyui-textbox" id="afterloanComments" name="afterloanComments" data-options="multiline:true" required="true" style="width:99%;height:60px">
 
					</div>
				</td>
			</tr>
			<tr>
				<td class="align_center" style="line-height: 50px;" height="50" colspan="4" align="center">
				<div id="mbutt">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save"
					onclick="editCommit()">提交</a>
					<a
					href="#" class="easyui-linkbutton" iconCls="icon-clear"
					onclick="resetForm()">取消</a>
				 </div>
				 <div id="mbuttdate">
					 <a
						href="#" class="easyui-linkbutton" iconCls="icon-clear"
						onclick="resetForm()">返回</a>
 				</div>
 				</td>
			</tr>
		</table></form>
	</div>
				
	</div>

</div>
</body>
</html>
