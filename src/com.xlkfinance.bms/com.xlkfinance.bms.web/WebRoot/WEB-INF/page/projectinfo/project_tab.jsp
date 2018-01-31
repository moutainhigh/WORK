<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/investigation.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/project_investigation.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/project_mortgage.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/project_contract.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/applyCommon.js"></script>
<title>赎楼贷款申请办理</title>
<style type="text/css">
#tabs .tabs-header .tabs{
  height: 26px;
  position: fixed;
  top:0px;
  background: #fff;
  z-index: 9999999;
  padding-top;2px;
}
.{padding-top:7px;}
</style>
</head>
<script type="text/javascript">
/**************工作流字段 begin******************/
var WORKFLOW_ID = "mortgageLoanForeAppRequestProcess"; //默认是房抵贷审批工作流ID
workflowTaskDefKey = "task_MortgageLoanRequest";//当前处理节点ID
nextRoleCode = "MORTGAGE_ASSESSMENT";//下一处理角色

var projectType = '${project.projectType}';
/**************工作流字段 end********************/

var projectId = -1;// 项目ID
isEdit = "${editType}";
var foreStatus = '${project.foreclosureStatus}';
// 页面初始化开始
$(document).ready(function() {
	//收单人下拉框
	var pmUserId = "${project.pmUserId}";
	//获取路径
	var url = decodeURI(window.location.href);
	var arr = url.split("=");
	/***********************个人申请贷款办理************************/
	// 判断当前URL是否存在acctId,如果存在,就表示是从客户管理过来的,如果不存在,不进入
	if(url.indexOf("acctId") > 0) {
		acct_id = arr[2];// 获取传过来的客户ID
		var url = "";
		// 根据客户ID查询用户数据
		$.ajax({
			type: "POST",
	        url: "<%=basePath%>customerController/getCusPersonByAcctId.action",
	        data:{"acctId":acct_id},
	        dataType: "json",
	        success: function(row){
	        		// 重置并填充表单
	        		$('#chinaNamema').html(row.chinaName);
	        		
	        		$("#personalForm input[name='cusType']").val(row.cusType);
	        		$("#personalForm input[name='userPids']").val(row.userPids);
	        		$("#personalForm input[name='chinaName']").val(row.chinaName);
	        		$("#personalForm input[name='sexName']").val(row.sexName);
	        		$("#personalForm input[name='marrName']").val(row.marrName);
	        		$("#personalForm input[name='certTypeName']").val(row.certTypeName);
	        		$("#personalForm input[name='censusAddr']").val(row.censusAddr);
	        		$("#personalForm input[name='certNumber']").val(row.certNumber);
	        		$("#personalForm input[name='perTelephone']").val(row.perTelephone);
	        		$("#personalForm input[name='mail']").val(row.mail);
	        		$("#personalForm input[name='liveAddr']").val(row.liveAddr);
	        		// 设置客户类别和客户id
	        		$("#personalForm input[name='acctId']").val(row.acctId);
	        		$('#personalAbbreviation').val(row.chinaName);
	        		checkPerson(row.certNumber);
	        		// 设置客户类别和客户id
	        		$("#personalForm input[name='acctId']").val(acct_id);
	        		$('#personalAbbreviation').val(row.chinaName);
	        		// 获取 共同借款人信息 
	        		var url = "<%=basePath%>customerController/getNoSpouseList.action?acctId="+acct_id;
	        		$('#personal_public').datagrid('options').url = url;
	        		$('#personal_public').datagrid('reload');
	        		$('#personal').addClass('active');
	        		$('#personal').removeClass('none');
	        }
		});
	} else {
		if(arr){
			if(arr.length > 1){
				if(arr[arr.length-1]){
					//获取参数
					var param = "{"+arr[arr.length-1]+"}";
					if(param.indexOf(":")>0){
						//将参数转换为Json对象
						params = eval('('+param+')');
						taskVo = params;
						if(params){
							nextRoleCode = params.nextRoleCode;
							taskId = params.workflowTaskId;
							allowTurnDown = params.allowTurnDown;
							workflowTaskDefKey = params.workflowTaskDefKey;
							workflowInstanceId = params.workflowInstanceId;
							projectId = params.projectId;
							// 标志为是流程
							bianzhi = 1;
						}
					}else if(url.indexOf("projectId") > 0) {
						var str = url.substring(url.indexOf("projectId") + 10);
						// 设置项目ID
						projectId = str.substring(0, str.indexOf("&") > 0 ? str.indexOf("&") : str.length)
						// 标志为是编辑
						bianzhi = 2;
					}
				}
			}
		}
	}
	
	$('#pmUserId').combobox({
		url:'${basePath}beforeLoanController/getAcctManagerByOrg.action',	
		valueField:'pid',	
		textField:'realName',
		filter: function(q, row){//模糊匹配
			var opts = $('#pmUserId').combobox('options');
			//只要输入的内容，即可匹配
			return row[opts.textField].indexOf(q) >-1;
		},
		onLoadSuccess: function(rec){
			$('#pmUserId').combobox('setValue',pmUserId);
			setCity(pmUserId);
		},
		onSelect: function(rec){
			pmUserId = $('#pmUserId').combobox('getValue');
			setCity(pmUserId);
		}
	});
	
	//初始化合作机构下拉框
	searchPartnerSource('${project.businessSourceNo}');
	$("#personalForm input:radio[name='cusCredentials.isOverdue']").change(function(){
		if (this.value == "1") {
			$(this).parent().next().show().next().show();
		} else if (this.value == "2") {
			$(this).parent().next().hide().next().hide();
		}
	});
	// 回写选中
	$("#personalForm input:radio[name='cusCredentials.isOverdue']:checked").change();
	
	/******************流程初始化客户基础信息 begin *********************/
	if(projectId != -1) {
		var url = "";
		$("#readtype1").linkbutton("disable");
		$("#text_gtjkr").text("共同借款人信息（共同借款人来自个人客户的配偶关系人和非配偶关系人）:");
		$.ajax({
			type: "POST",
			url: "<%=basePath%>customerController/getCusPersonKeyPid.action",
			data:{"pid":projectId},
			dataType: "json",
			success: function(row) {
				fillCustomerInfo(row)
			}
		}); 
		
	}else{
		$("#gtjkrAdd").linkbutton("disable");
		$("#gtjkrRemove").linkbutton("disable");
	}
	
	
	/***************/
	// 根据不同的title选项卡，加载不同的页面
	
	$('#tabs').tabs({
		width: $("#tabs").parents().find('body').width()-18,  
		onSelect:function(title){
	 		var p = $(this).tabs('getTab', title);
	 		// 判断属于那个title
	 		// 1.下户调查
	 		if(title == "下户调查"){//
	 			if(projectId == -1){
	 				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	 				// 返回当前第一个选项卡
	 				$("#tabs").tabs("select", 0);
	 				return false;
	 			}
	 			// 贷前申请流程状态，下户调查必须在评估后才可以操作
	 			var foreclosureStatus = $("#personalForm input[name='foreclosureStatus']").val();
	 			if(foreclosureStatus < 3) {
	 				$.messager.alert("操作提示","请评估节点完成后,才可以下户调查操作!","error");
	 				// 返回当前第一个选项卡
	 				$("#tabs").tabs("select", 0);
	 				return false;
	 			}	
	 			p.panel('refresh','<%=basePath%>projectInfoController/toSpotInfoPage.action?projectId='+projectId +'&foreclosureStatus=' + foreStatus);
	 		}
	 		else if(title == "签约公证抵押"){
	 			if(projectId == -1){
	 				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	 				// 返回当前第一个选项卡
	 				$("#tabs").tabs("select", 0);
	 				return false;
	 			}
	 			p.panel('refresh','<%=basePath%>projectInfoController/toInterviewPage.action?projectId='+projectId);
	 		}
	 		// 3.财务明细
	 		else if(title == "财务明细") {
	 			if(projectId == -1){
	 				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	 				// 返回当前第一个选项卡
	 				$("#tabs").tabs("select", 0);
	 				return false;
	 			}
	 			p.panel('refresh','<%=basePath%>projectInfoController/toFinanceDetail.action?projectId='+projectId);
	 		}
	 		// 4.资料上传
	 		else if(title == "资料上传"){ 
	 			if(projectId == -1){
	 				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	 				// 返回当前第一个选项卡
	 				$("#tabs").tabs("select", 0);
	 				return false;
	 			}
	 			p.panel('refresh','<%=basePath%>projectInfoController/navigationDatum.action?foreStatus='+foreStatus);
	 		}
		}
	});
	$('.loanworkflowWrapDiv').css('width',$('#tab1 .easyui-panel').width());
	$('.loanworkflowWrapDiv .cus_table').css('width',$('#tab1 .easyui-panel').width());
	
});

/**
 * 将客户信息填充
 */
function fillCustomerInfo(row) {
	// 重置并填充表单
		acct_id=row.acctId;
		$('#chinaNamema').html(row.chinaName);
		$("#personalForm input[name='cusType']").val(row.cusType);
		$("#personalForm input[name='userPids']").val(row.userPids);
		$("#personalForm input[name='chinaName']").val(row.chinaName);
		$("#personalForm input[name='sexName']").val(row.sexName);
		$("#personalForm input[name='marrName']").val(row.marrName);
		$("#personalForm input[name='certTypeName']").val(row.certTypeName);
		$("#personalForm input[name='censusAddr']").val(row.censusAddr);
		$("#personalForm input[name='certNumber']").val(row.certNumber);
		$("#personalForm input[name='perTelephone']").val(row.perTelephone);
		$("#personalForm input[name='mail']").val(row.mail);
		$("#personalForm input[name='liveAddr']").val(row.liveAddr);
		checkPerson(row.certNumber);
		// 设置客户类别和客户id
		$("#personalForm input[name='acctId']").val(row.acctId);
		$('#personalAbbreviation').val(row.chinaName);
		// 获取 共同借款人信息 
		var url = "<%=basePath%>customerController/getNoSpouseLists.action?projectId=" + projectId;
		$('#personal_public').datagrid('options').url = url;
		$('#personal_public').datagrid('reload');
		$('#personal').addClass('active');
		$('#personal').removeClass('none');
		// 关闭个人客户窗口
		$('#dlg_personal').dialog('close');
}

/**
 *  合作机构初始化
 */
function searchPartnerSource(value){
	$.ajax({
		type: "POST",
		url : '${basePath}beforeLoanController/getOrgAssetsList.action',
		dataType: "json",
		success: function(result) {
			if(result != null && result.length > 0) {
				var optionStr = "<option selected='selected'>--请选择--</option>";
				for(var i = 0; i<result.length ; i ++ ){
					var orgInfo = result[i];
					optionStr = optionStr + "<option value='" + orgInfo.orgId+"'"
										+ " rate='"+ orgInfo.rate+"' ";
					optionStr += ">" + orgInfo.orgName + "</option>";
				}
				$("#partner_source_no").empty();
				$("#partner_source_no").append(optionStr); // 追加
			}
			if(!!value && value > 0) {
				$("#partner_source_no").addClass('easyui-combobox easyui-validatebox').combobox();
				$("#partner_source_no").combobox("setValue", value);
			}
		}
	});	
}

//页面初始化结束

//根据录单员填充城市	
function setCity(userId) {
	
	$.ajax({
		url : "${basePath}sysAreaInfoController/getSysAreaInfoByUserId.action",
		type : "POST",
		data : {"userId":userId},
		async : false,
		success : function(result) {
			var ret = eval("(" + result + ")");
			$("#cityName").textbox('setValue',ret.areaName);
			$("#areaCode").val(ret.areaCode);
		}
	});
}
//打开个人客户信息窗口 
function selectPersonal(){
	$('#personal_grid').datagrid('loadData',[]);
  	$("#searchFromPersonal #chinaName").textbox('setValue',"");
  	$("#searchFromPersonal #certType").combobox('setValue',"-1");
  	$("#searchFromPersonal #sex").combobox('setValue',"-1");
  	$("#searchFromPersonal #certNumber").textbox('setValue',"");
	ajaxSearchPage('#personal_grid','#searchFromPersonal');
  	$('#dlg_personal').dialog('open');

}

// 选取个人客户
function savePersonal(){
	// 获取选中的行
	var row = $('#personal_grid').datagrid('getSelected');	
	// 保证必须选取客户数据
	if(!row){
		$.messager.alert("操作提示","请选择客户信息!","error");
		return false;
	}
	acct_id = row.acctId;
	$('#chinaNamema').html(row.chinaName);
	// 重置并填充表单
	$("#personalForm input[name='userPids']").val(row.userPids);
	$("#personalForm input[name='pid']").val(projectId);
	$("#personalForm input[name='chinaName']").val(row.chinaName);
	$("#personalForm input[name='sexName']").val(row.sexName);
	$("#personalForm input[name='marrName']").val(row.marrName);
	$("#personalForm input[name='certTypeName']").val(row.certTypeName);
	$("#personalForm input[name='censusAddr']").val(row.censusAddr);
	$("#personalForm input[name='certNumber']").val(row.certNumber);
	$("#personalForm input[name='perTelephone']").val(row.perTelephone);
	$("#personalForm input[name='mail']").val(row.mail);
	$("#personalForm input[name='liveAddr']").val(row.liveAddr);
	// 设置个人客户的简称
	$('#personalAbbreviation').val(row.chinaName);
	// 设置客户类别和客户id
	$("#personalForm input[name='acctId']").val(row.acctId);
	$("#personalForm input[name='cusType']").val(1);
	
	// 获取 共同借款人信息 
	var url = "<%=basePath%>customerController/getNoSpouseList.action?acctId="+row.acctId;
	$('#personal_public').datagrid('options').url = url;
	$('#personal_public').datagrid('reload');
	$('#enterprise').addClass('none');
	$('#personal').addClass('active');
	$('#personal').removeClass('none');
	// 关闭个人客户窗口
	$('#dlg_personal').dialog('close');
}


// 删除共同借款人
function deletePersonalPublic(){
	var $personalRow = $('#personal_public'), rows = $personalRow.datagrid('getSelections');
	// 共同借款人
	var userPids = "";
	// 判断是否选择数据
	if(rows.length == 0){
 		$.messager.alert("操作提示","请选择数据!","error");
		return;
	}
	// 循环获取当前共同借款人的ID
	for(var i = 0; i < rows.length; i++) {
		var row = rows[i];
		userPids += row.projectPublicManId+",";
	}
	$.messager.confirm("操作提示","确定删除选择的此批共同借款人吗?",
		function(r) {
			if(r){
				$.post(BASE_PATH+"secondBeforeLoanController/batchDeleteProjectPublicMan.action",{userPids : userPids}, 
					function(ret) {
						//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
						if(ret && ret.header["success"]){
							$.messager.alert('操作提示',ret.header["msg"]);
							var url = "<%=basePath%>customerController/getNoSpouseLists.action?projectId="+projectId;
							$('#personal_public').datagrid('options').url = url;
							$('#personal_public').datagrid('reload');
							// 清空 datagrid 选择行
							clearSelectRows("#personal_public");
						}else{
							$.messager.alert('操作提示',ret.header["msg"],'error');	
						}
					},'json');
 			}
		});
}

// 打开共同借款人窗口
function openPersonalPublic() {
	// 已选择共同借款人信息列表
	var $personalRow = $('#publicMan_grid');
	// 初始化新增共同借款人datagrid
	var url = "<%=basePath%>customerController/getNoSpouseList.action?acctId="+acct_id;
	$personalRow.datagrid('options').url = url;
	$personalRow.datagrid('reload');
	// 打开窗口
	$('#dlg_publicMan').dialog('open').dialog('setTitle', "选择共同借款人");
}

// 新增共同借款人
function addPersonalPublic(){
	// 获取数据
	var $personalRow = $('#publicMan_grid'), rows = $personalRow.datagrid('getSelections');
	// 共同借款人ID
	var userPids = "";
	// 判断是否选择数据
	if(rows.length == 0){
 		$.messager.alert("操作提示","请选择共同借款人!","error");
		return;
	}
	// 循环获取当前共同借款人的ID
	for(var i=0;i<rows.length;i++) {
		var row = rows[i];
		userPids += row.pid+",";
	}
	// 赋值
	$("#publicManForm input[name='userPids']").val(userPids);// 共同借款人ID
	$("#publicManForm input[name='projectId']").val(projectId);// 项目ID
	$("#publicManForm").form('submit', {
		onSubmit : function() {return $(this).form('validate');},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);
				// 关闭窗口
				$('#dlg_publicMan').dialog('close');
				// 重新加载共同借款人
				var url = "<%=basePath%>customerController/getNoSpouseLists.action?projectId="+projectId;
				$('#personal_public').datagrid('options').url = url;
				$('#personal_public').datagrid('reload');
				// 清除datagrid的函数
				clearSelectRows("#publicMan_grid");
				clearSelectRows("#personal_public");
				// 重置form数据
				$("#publicManForm").form('reset');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		}
	});
}

// 项目信息校验
function checkProjectInfo() {
	// 校验业务来源不能为空
	var businessSource = $("#personalForm input[name='businessSource']").val();
	if(!businessSource) {
		$.messager.alert("操作提示","业务来源不能为空!","error");
		return false;
	} else {
		var $businessSourceNo = $("#personalForm :input[name='businessSourceNo']");
		if (businessSource == 2) {
			if (!$businessSourceNo.val() || $businessSourceNo.val() == '--请选择--') {
				$.messager.alert("操作提示","业务来源机构信息不能为空!","error");
				return false;
			}
		} else {
			$businessSourceNo.val(-1);
		}
	}
	// 校验收单人信息不能为空
	var pmUserId = $("#pmUserId").combobox("getValue");
	if(!pmUserId || pmUserId <= 0) {
		$.messager.alert("操作提示","收单人不能为空!","error");
		return false;
	}
	// 校验借款人信息不能为空
	var acct_id = $("#personalForm input[name='acctId']").val();
	if(acct_id <= 0) {
		$.messager.alert("操作提示","借款人不能为空!","error");
		return false;
	}
	// 校验雇佣情况信息不能为空
	var chargeType = $("#personalForm :input[name='cusEnterpriseInfo.employSituation']").val();
	if(chargeType <= 0) {
		$.messager.alert("操作提示","请选择雇佣情况!","error");
		return false;
	}
	// 校验五级分类信息不能为空
	var fiveClassify = $("#personalForm :input[name='cusCredentials.fiveClassify']").val();
	if(!fiveClassify) {
		$.messager.alert("操作提示","请选择五级分类!","error");
		return false;
	}
	// 校验逾期金额信息不能为空
	var isOverdue = $("#personalForm input:radio[name='cusCredentials.isOverdue']:checked").val();
	if (isOverdue == "1") {
		var overdueMoney = $("#personalForm :input[name='cusCredentials.overdueMoney']").val();
		if(overdueMoney <= 0) {
			$.messager.alert("操作提示","逾期金额必须大于0!","error");
			return false;
		}
	}
	
	// 校验借款金额必须大于0
	var loanMoney = $("#personalForm input[name='projectGuarantee.loanMoney']").val();
	if(loanMoney <= 0) {
		$.messager.alert('操作提示',"借款金额必须大于0!",'error');
		return false;
	}
	// 校验借款期限必须大于0
	var loanDays = $("#personalForm input[name='projectGuarantee.loanTerm']").val();
	if(loanDays <= 0) {
		$.messager.alert('操作提示',"借款期限必须大于0!",'error');
		return false;
	}
	// 校验还款方式不能为空
	var repaymentType = $("#personalForm :input[name='projectGuarantee.repaymentType']").val();
	if(!repaymentType) {
		$.messager.alert("操作提示","还款方式不能为空!","error");
		return false;
	}
	// 校验借款用途不能为空
	var loanUsage = $("#personalForm :input[name='projectGuarantee.loanUsage']").val();
	if(!loanUsage) {
		$.messager.alert("操作提示","借款用途不能为空!","error");
		return false;
	}
	// 校验还款来源不能为空
	var paymentSource = $("#personalForm :input[name='projectGuarantee.paymentSource']").val();
	if(!paymentSource){
		$.messager.alert("操作提示","还款来源不能为空!","error");
		return false;
	}
	// 月利率
	var feeRate = $("#personalForm input[name='projectGuarantee.feeRate']").val();
	// 抵押率
	var mortgageRate = $("#personalForm input[name='projectGuarantee.mortgageRate']").val();
	// 月返佣率
	var monthMaidRate = $("#personalForm input[name='projectGuarantee.monthMaidRate']").val();
	// 逾期罚息率
	var overdueRate = $("#personalForm input[name='projectGuarantee.overdueRate']").val();
	// 提前还款率
	var prepaymentRate = $("#personalForm input[name='projectGuarantee.prepaymentRate']").val();
	// 月利率必须大于零
	if(feeRate <= 0) {
		$.messager.alert('操作提示',"月利率必须大于零！",'error');
		return false;
	}
	// 抵押率必须大于零
	if(mortgageRate <= 0) {
		$.messager.alert('操作提示',"抵押率必须大于零！",'error');
		return false;
	}
	// 月返佣费率必须大于零
	if(monthMaidRate <= 0) {
		$.messager.alert('操作提示',"月返佣费率必须大于零！",'error');
		return false;
	}
	// 逾期日罚息率必须大于零
	if(overdueRate <= 0) {
		$.messager.alert('操作提示',"逾期日罚息率必须大于零！",'error');
		return false;
	}
	// 提前还款费率必须大于零
	if(prepaymentRate <= 0) {
		$.messager.alert('操作提示',"提前还款费率必须大于零！",'error');
		return false;
	}
	
	// 月返佣率必须小于等于月利率
	if(parseFloat(monthMaidRate) > parseFloat(feeRate)){
		$.messager.alert('操作提示',"月返佣率不能大于月利率！",'error');
		return false;
	}
	// 收费节点信息在评估时必填
	var $chargesType = $("#personalForm input[name='projectGuarantee.chargesType']");
	if ($chargesType.length > 0) {
		if (!$chargesType.val()) {
			$.messager.alert('操作提示',"收费节点信息不能为空！",'error');
			return false;
		}
		// 收费金额必须大于零
		var poundage = $("#personalForm input[name='projectGuarantee.poundage']").val();
		if (poundage <= 0) {
			$.messager.alert("操作提示","收费金额必须大于零!","error");
			return false;
		}
	}
	// 抵押物不能为空
	var pledgeRows = $('#pledge_grid').datagrid('getRows');
	if (pledgeRows.length <= 0) {
		$.messager.alert('操作提示',"抵押物信息不能为空！",'error');
		return false;
	}
	
	if(!$("#personalForm").form('validate')) {
		$.messager.alert('操作提示',"请先保存必填数据!",'error');
		return false;
	}
	// 校验下拉框值只能为数字
	var selectors = $("#personalForm input.easyui-combobox");
	for (var i = 0; i < selectors.length; i++) {
		var value = $(selectors.get(i)).combobox("getValue");
		if (value && !/\d+/.test(value)) {
			var label = $(selectors.get(i)).parent().prev().text();
			$.messager.alert('操作提示',label+ "下拉值必须为数字,请正确配置数据字典!",'error');
			return false;
		}
	}
	
	// 设置共同借款人信息
	var rows = $('#personal_public').datagrid('getRows');
	if(rows.length > 0) {
		var userArr = $.map(rows, function(row) {
			return row.pid;
		});
		$("#personalForm input[name='userPids']").val(userArr.join(","));
	}
	// 设置抵押物信息
	var rows = $('#pledge_grid').datagrid('getRows');
	if(rows.length > 0) {
		var houseIdArr = $.map(rows, function(row) {
			return row.houseId;
		});
		$("#personalForm input[name='houseIds']").val(houseIdArr.join(","));
	}
	//经办人姓名
	var managers = $("#pmUserId").combobox('getText');
	$("#personalForm input[name='managers']").val(managers);
	return true;
}

/**
 * 保存抵押贷申请信息
 */
function saveProjectInfo() {
	$("#personalForm").form('submit',{
		onSubmit : function() {
			return checkProjectInfo();
		},
		success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$("#gtjkrAdd").linkbutton("enable");
				$("#gtjkrRemove").linkbutton("enable");
				// 设置项目ID
				projectId = ret.header["projectId"];
				$("#personalForm input[name='pid']").val(projectId);
				// 设置授信ID
				creditId = ret.header["creditId"];
				$("#projectName").val(ret.header["projectName"]);
				$("#projectNumber").val(ret.header["projectNumber"]);
				
				$("#personalForm input[name='cusEnterpriseInfo.pid']").val(ret.header["enterpriseId"]);
				$("#personalForm input[name='cusEnterpriseInfo.projectId']").val(projectId);
				
				$("#personalForm input[name='cusCredentials.pid']").val(ret.header["credentialsId"]);
				$("#personalForm input[name='cusCredentials.projectId']").val(projectId);
				
				$("#personalForm input[name='cusCardInfo.pid']").val(ret.header["cusCardId"]);
				$("#personalForm input[name='cusCardInfo.projectId']").val(projectId);
				
				$("#personalForm input[name='projectGuarantee.pid']").val(ret.header["guaranteeId"]);
				$("#personalForm input[name='projectGuarantee.projectId']").val(projectId);
				// 提示语句
				$.messager.alert('操作提示','保存信息成功','info');
				$("#readtype1").linkbutton("disable");
				$("input[name='cusType']").attr('disabled','true');
				var url = "<%=basePath%>customerController/getNoSpouseLists.action?projectId="+projectId;
				$('#personal_public').datagrid('options').url = url;
				$('#personal_public').datagrid('reload');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
		}
	});
}

/**
 * 贷款申请信息历史
 */
function LoanInfoHistory() {
	$('#dlg_loanInfoHistory').dialog('open');
	var url = '<%=basePath%>projectInfoController/getLoanInfoAppHistory.action?projectId='+${project.pid};
	$('#loanInfoHistory_grid').datagrid('options').url = url;
	$('#loanInfoHistory_grid').datagrid('reload');
}

/**
 * 客户银行信息历史
 */
function bankInfoHistory() {
	$('#dlg_cardInfoHistory').dialog('open');
	var url = '<%=basePath%>projectInfoController/getCardInfoHistory.action?projectId='+${project.pid};
	$('#cardInfoHistory_grid').datagrid('options').url = url;
	$('#cardInfoHistory_grid').datagrid('reload');
}

function editPerBase() { 
	parent.openNewTab('${basePath}customerController/editPerBase.action?acctId='
			+ acct_id+'&currUserPid=${currUser.pid}&flag='+2+'&isLook='+3,"个人客户详细信息",true);
}

/**
 * 校验客户黑名单
 */
function checkPerson(certNumber){
	$.ajax({
		url : '${basePath}customerController/validateCeryNumber.action?certNumber='
			+ certNumber+ '&personId=0',
		type : 'post',
		cache: false,
		success : function(result) {
			var ret = eval("("+result+")");
			var blackFlags=ret.header["blackFlags"];
			if(blackFlags >0){
				$.messager.alert('操作提示',"此客户已加入系统黑名单，请谨慎操作！",'error');
			}
		}
	});
}

function formatterTimestamp(val, row) {
	var value = (val = val || "").trim().substring(0, val.indexOf(".")), date = new Date(value);
	if (date.toDateString() == "Invalid Date") {
		return value;
	}
	return date.format("yyyy-MM-dd hh:mm:ss");
}

function formatterRepaymentType(val, row) {
	return val == "1" ? "先息后本" : "";
}

function formatterLoanUsage(val, row) {
	return {"1": "消费", "2": "经营", "3": "资金周转", "4": "转贷款"}[val];
}

function formatterPayment(val, row) {
	return {"1": "经营收入", "2": "资产变卖", "3": "转贷款"}[val];
}

// 文件上传节点描述信息
function formatMortgageCreateNode(val,row){
	row.projectType = 8;
	return formatterProjectStatus(val, row);
}
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
	<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
		<div title="业务信息" id="tab1">
			<form action="<%=basePath%>projectInfoController/saveProjectInfo.action" id="personalForm" name="personalForm" method="post">
			<div style="padding: 30px 10px 0 10px;">
				<div class="easyui-panel" title="借款人信息及基础信息填写" data-options="collapsible:true" width="1039px;">
					<%-- begin 个人模版 --%>
					<div id="personal" style="padding:10px 0 15px 0;">
						<input type="hidden" id="personalAbbreviation" name="abbreviation" />
						<input type="hidden" name="cusType" />
						<input type="hidden" name="acctId" />
						<input type="hidden" name="projectSource" value="${project.projectSource}"/>
						<input type="hidden" name="projectType" value="${project.projectType}"/>
						<input type="hidden" name="foreclosureStatus" value="${project.foreclosureStatus}"/>
						<input type="hidden" name="userPids" />
						<input type="hidden" name="houseIds" />
						<input type="hidden" id="productType" name="productType" value="${mortgageLoanProduct.pid}"/>
						<input type="hidden" name="pid" value="${project.pid}" />
						
						<input type="hidden" name="cusEnterpriseInfo.pid" value="${project.cusEnterpriseInfo.pid}"/>
						<input type="hidden" name="cusEnterpriseInfo.projectId" value="${project.cusEnterpriseInfo.projectId}"/>
						
						<input type="hidden" name="cusCredentials.pid" value="${project.cusCredentials.pid}"/>
						<input type="hidden" name="cusCredentials.projectId" value="${project.cusCredentials.projectId}"/>
						
						<input type="hidden" name="cusCardInfo.pid" value="${project.cusCardInfo.pid}"/>
						<input type="hidden" name="cusCardInfo.projectId" value="${project.cusCardInfo.projectId}"/>
						
						<input type="hidden" name="projectGuarantee.pid" value="${project.projectGuarantee.pid}"/>
						<input type="hidden" name="projectGuarantee.projectId" value="${project.projectGuarantee.projectId}"/>
						<input type="hidden" name="projectGuarantee.guaranteeMoney" value="${project.projectGuarantee.guaranteeMoney}"/>
						<c:if  test="${project == null ||project.projectType==2}">
							<input type="hidden" name="areaCode" value="${project.areaCode}" id="areaCode"/>
						</c:if>
						<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
							<tr>
								<td align="right" width="80" height="28">项目名称：</td>
								<td><input id="projectName" value="${project.projectName}" name="projectName" type="text" class="text_style" style="width: 145px;color: red;" readonly="readonly" > </td>
								<td align="right" width="80" height="28">项目编号：</td>
								<td ><input id="projectNumber" value="${project.projectNumber}" name="projectNumber" type="text" class="text_style" style="width: 145px;color: red;" readonly="readonly" > </td>
								<td class="label_right">录单员：</td>
								<td>
									<input type="hidden" name="recordClerkId" value="${project.recordClerkId }">
									<input  class="easyui-textbox" readonly="readonly" data-options="validType:'length[1,20]'" name="declaration"  value="${project.declaration } " style="width:150px;" />
								</td>
							</tr>
							<tr>
								<td class="label_right1">产品名称：</td>
								<td>
									<select <c:if test="${project.foreclosureStatus > 1}">readonly="readonly"</c:if> class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" name="productId" style="width:150px;">
										<option value="${mortgageLoanProduct.pid}" selected>${mortgageLoanProduct.productName}</option>
									</select>
								</td>
								<td class="label_right">城市：</td>
								<td>
									<input id="cityName" class="easyui-textbox" readonly="readonly"  style="width:150px;"/>
								</td>
								<td class="label_right"><font color="red">*</font>业务来源：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 1}">readonly="readonly"</c:if> name="businessSource" class="easyui-combobox easyui-validatebox" editable="false" panelHeight="auto" <c:if test="${project.businessSource != 0}">value="${project.businessSource}"</c:if>
										data-options="validType:'selrequired',valueField:'lookupVal',textField:'lookupDesc',
											url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=MORTGAGE_LOAN_BUSINESS_SOURCE',
											onSelect:function(rec) {
												if (rec.lookupVal == 2) {
													$('#partner_source').show().find('select').addClass('easyui-combobox easyui-validatebox').combobox().combobox({validType:'selrequired'});
												} else {
													$('#partner_source').hide().find('select').removeClass('easyui-combobox easyui-validatebox').combobox().combobox({validType:''});
												}
											},
											onLoadSuccess:function() {
												var value = $(this).combobox('getValue');
												if (value == 2) {
													$('#partner_source').show();
												} else {
													$('#partner_source').hide();
												}
											}"  style="width:150px;"/>
								</td>
								<td colspan="2">
									<div id="partner_source" class="none">
										<select <c:if test="${project.foreclosureStatus > 1}">disabled="disabled"</c:if> name="businessSourceNo" id="partner_source_no"
										 class="textbox combo" editable="false" style="width:235px;height: 26px;border-radius: 5px;border-color: #95B8E7;">
											<option selected="selected">--请选择--</option>
										</select>
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label_right"><font color="red">*</font>机构联系人：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 1}">readonly="readonly"</c:if> name="businessContacts" value="${project.businessContacts}" data-options="required:true,validType:'length[1,20]'" id="businessContacts"  class="easyui-textbox" panelHeight="auto"  style="width:150px;"/>
								</td>
								<td class="label_right"><font color="red">*</font>联系电话：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 1}">readonly="readonly"</c:if> class="easyui-textbox" data-options="required:true,validType:'telephone'" name="contactsPhone" value="${project.contactsPhone}" style="width:150px;"/>
								</td>
								<td class="label_right"><font color="red">*</font>收单人：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 1}">readonly="readonly"</c:if> name="pmUserId" id="pmUserId" style="width: 149px;" editable="false" data-options="validType:'selrequired'" class="easyui-combobox" style="width:150px;"/>
									<input type="hidden" name="managers" value="${project.managers}" id="managers">
								</td>
							</tr>
						</table>
						<table class="beforeloanTable" width="800">
							<tr>
								<td class="label_right">借款人：</td>
								<td>
									<a id='chinaNamema'  onclick='editPerBase()' href='#'  style="color:blue;float: left;width: 145px;padding-left: 5px; ">${cpyName}</a>
									<input name="chinaName" type="hidden"  class="no_frame text_style"  disabled="true">
									<c:if test="${project.foreclosureStatus < 1}">
										<a class="easyui-linkbutton personalbtn"  id="readtype1" onclick="selectPersonal()">选择借款人</a>
									</c:if>
								</td>
								<td class="label_right">客户性别：</td>
								<td><input name="sexName" type="text" class="no_frame text_style" disabled="true"></td>
							</tr>
							<tr>
								<td class="label_right">婚姻情况：</td>
								<td><input name="marrName" type="text" class="no_frame text_style" disabled="true"></td>
								<td class="label_right">证件类型：</td>
								<td><input name="certTypeName" type="text" class="no_frame text_style" disabled="true"></td>
							</tr>
							<tr>
								<td class="label_right">户籍地址：</td>
								<td><input name="censusAddr" type="text" class="no_frame text_style" disabled="true"></td>
								<td class="label_right">证件号码：</td>
								<td><input name="certNumber" type="text" class="no_frame text_style" disabled="true"></td>
							</tr>
							<tr>
								<td class="label_right">手机号码：</td>
								<td><input name="perTelephone" type="text" class="no_frame text_style" disabled="true"></td>
								<td class="label_right">电子邮箱：</td>
								<td><input name="mail" type="text" class="no_frame text_style" disabled="true"></td>
							</tr>
							<tr>
								<td class="label_right">现居住地址：</td>
								<td colspan="3"><input name="liveAddr" type="text" style="width: 400px;" class="no_frame text_style" disabled="true"></td>
							</tr>
						</table>
			
						<div class="fitem" style="margin-left: 40px;">				
							<%-- 共同借款人信息--%>
							<div style="padding-bottom:10px;">
								<label style="width: auto;" id="text_gtjkr">共同借款人信息（共同借款人来自个人客户的配偶关系人和非配偶关系人）：</label>
							</div>
							<c:if test="${project.foreclosureStatus <= 1}">
							<div id="toolbar_gtjkr"  style="border-bottom: 0;">
								<a href="javascript:void(0)" id="gtjkrAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openPersonalPublic()">选择共同借款人</a>
								<a href="javascript:void(0)" id="gtjkrRemove" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deletePersonalPublic()">删除</a>
							</div>
							</c:if>
							<table id="personal_public" class="easyui-datagrid" fitColumns="true" style="width: 800px;height: auto;"
								data-options="
									url: '',
									method: 'post',
									rownumbers: true,
									fitColumns:true,
									singleSelect: false,
									toolbar: '#toolbar_gtjkr',
									pagination: false">
								<thead>
									<tr>
										<th data-options="field:'caozuo',checkbox:true" ></th>
										<th data-options="field:'projectPublicManId',hidden:true,width:30" ></th>
										<th data-options="field:'chinaName',width:30" >姓名</th>
										<th data-options="field:'sexName',width:30" >性别</th>
										<th data-options="field:'certTypeName',width:30" >证件类型</th>
										<th data-options="field:'certNumber',width:30" >证件号号码</th>
										<th data-options="field:'relationText',width:30" >与客户关系</th>
										<th data-options="field:'perTelephone',width:30" >联系电话</th>
										<th data-options="field:'proportionProperty',width:30" >产权占比</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 企业信息 -->
			<div  style="padding: 10px 10px 0 10px;">
				<div class=" easyui-panel" title="企业信息" data-options="collapsible:true" width="1039px;">
					<div style="padding: 10px 5px 10px 0;height: auto;line-height: 35px;" >
						<table class="beforeloanTable">
							<tr>
								<td class="label_right"><font color="red">*</font>雇佣情况：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusEnterpriseInfo.employSituation" class="easyui-combobox" editable="false" panelHeight="auto" value="${project.cusEnterpriseInfo.employSituation}"
										data-options="validType:'selrequired',valueField:'lookupVal',textField:'lookupDesc',
											url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=MORTGAGE_LOAN_EMPLOY_SITUATION'" style="width:135px;" />
								</td>
								<td class="label_right">企业类型：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusEnterpriseInfo.enterpriseType" class="easyui-combobox" editable="false" panelHeight="auto" <c:if test="${project.cusEnterpriseInfo.enterpriseType != 0}">value="${project.cusEnterpriseInfo.enterpriseType}"</c:if>
										data-options="valueField:'lookupVal',textField:'lookupDesc',
											url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=MORTGAGE_LOAN_ENTERPRISE_TYPE'" style="width:135px;" />
								</td> 
								<td class="label_right">企业名称：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusEnterpriseInfo.enterpriseName" value="${project.cusEnterpriseInfo.enterpriseName}" class="easyui-textbox" data-options="validType:'length[1,50]'" style="width:135px;"/>
								</td>
								<td class="label_right">法人代表：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusEnterpriseInfo.legalRepresentative" value="${project.cusEnterpriseInfo.legalRepresentative}" class="easyui-textbox" data-options="validType:'length[1,20]'" style="width:135px;"/>
								</td>
							</tr>
							<tr>
								<td class="label_right">成立时间：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusEnterpriseInfo.foundDate" value="${project.cusEnterpriseInfo.foundDate}" class="easyui-datebox" editable="false" />
								</td>
								<td class="label_right">注册资金：</td>
								<td style="width: 162px">
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusEnterpriseInfo.regMoney" id="regMoney" value="${project.cusEnterpriseInfo.regMoney}" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" style="width:135px;"/>元
								</td>
								<td class="label_right">股东类型：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusEnterpriseInfo.stockholderType" value="${project.cusEnterpriseInfo.stockholderType}" class="easyui-textbox" data-options="validType:'length[1,20]'"  style="width:135px;"/>
								</td>
								<td class="label_right">员工人数：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusEnterpriseInfo.staffNum" value="${project.cusEnterpriseInfo.staffNum}" class="easyui-numberbox" data-options="min:0,max:999999999,groupSeparator:','" style="width:135px;"/>
								</td>
							</tr>
							<tr>
								<td class="label_right">经营范围：</td>
								<td colspan="7">
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusEnterpriseInfo.operationScope" value="${project.cusEnterpriseInfo.operationScope}" class="easyui-textbox" data-options="validType:'length[1,500]',multiline:true" style="width:100%;height:40px">
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			
			<!-- 客户资质 -->
			<div  style="padding: 10px 10px 0 10px;">
				<div class=" easyui-panel" title="客户资质" data-options="collapsible:true" width="1039px;">
					<div style="padding: 10px 0 10px 10px;height: auto;line-height: 35px;" >
						<table class="beforeloanTable">
							<tr>
								<td class="label_right1" style="width: 180px;"><font color="red">*</font>五级分类：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.fiveClassify" class="easyui-combobox" editable="false" panelHeight="auto" value="${project.cusCredentials.fiveClassify}"
										data-options="validType:'selrequired',valueField:'lookupVal',textField:'lookupDesc',
											url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=MORTGAGE_LOAN_FIVE_CLASSIFY'" />
								</td>
								<td class="label_right1" style="width: 180px;"><font color="red">*</font>最近三年累计逾期次数：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.overdueNumsThree" id="guaranteeFee" value="${project.cusCredentials.overdueNumsThree}" class="easyui-numberbox" data-options="required:true,min:0,max:999,groupSeparator:','"/>次
								</td> 
								<td class="label_right1" style="width: 160px;"><font color="red">*</font>最近两年累计逾期次数：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.overdueNumsTwo" value="${project.cusCredentials.overdueNumsTwo}" class="easyui-numberbox" data-options="required:true,min:0,max:999,groupSeparator:','"/>次
								</td>
							</tr>
							<tr>
								<td class="label_right1"><font color="red">*</font>总贷款笔数：</td>
								<td>
									 <input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.loanTotal" value="${project.cusCredentials.loanTotal}" class="easyui-numberbox" data-options="required:true,min:0,max:999,groupSeparator:','"/>笔
								</td>
								<td class="label_right1"><font color="red">*</font>当前是否有逾期：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> type="radio" name="cusCredentials.isOverdue" value="1"  <c:if test="${project.cusCredentials.isOverdue==1}">checked </c:if>/>是
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> type="radio" name="cusCredentials.isOverdue" value="2" <c:if test="${project.cusCredentials.isOverdue==2  || empty project.cusCredentials.isOverdue}">checked </c:if>/>否
								</td>
								<td class="label_right1 none"><font color="red">*</font>当前逾期金额：</td>
								<td class="none">
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.overdueMoney" style="width:150px;" value="${project.cusCredentials.overdueMoney}" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>元
								</td>
							</tr>
							<tr>
								<td class="label_right1"><font color="red">*</font>历史逾期总金额：</td>
								<td>
									 <input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.overdueTotalMoney" value="${project.cusCredentials.overdueTotalMoney}" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>元
								</td>
								<td class="label_right1"><font color="red">*</font>历史逾期最长期数：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.overduePeriods" value="${project.cusCredentials.overduePeriods}" class="easyui-numberbox" data-options="required:true,min:0,max:999,groupSeparator:','"/>月
								</td>
							</tr>
							<tr>
								<td class="label_right1"><font color="red">*</font>最近1个月内贷款审批次数：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.loanApprovalsNums" value="${project.cusCredentials.loanApprovalsNums}" class="easyui-numberbox" data-options="required:true,min:0,max:999,groupSeparator:','"/>次
								</td>
								<td class="label_right1"><font color="red">*</font>最近3个月内贷款审批次数：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.loanApprovalsNumsTwo" value="${project.cusCredentials.loanApprovalsNumsTwo}" class="easyui-numberbox" data-options="required:true,min:0,max:999,groupSeparator:','"/>次
								</td>
							</tr>
							<tr>
								<td class="label_right1"><font color="red">*</font>最近1个月内信用卡审批次数：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.creditApprovalsNums" value="${project.cusCredentials.creditApprovalsNums }" class="easyui-numberbox" data-options="required:true,min:0,max:999,groupSeparator:','"/>次
								</td>
								<td class="label_right1"><font color="red">*</font>最近3个月内信用卡审批次数：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.creditApprovalsNumsThree" value="${project.cusCredentials.creditApprovalsNumsThree }" class="easyui-numberbox" data-options="required:true,min:0,max:999,groupSeparator:','"/>次
								</td>
							</tr>
							<tr>
								<td class="label_right1"><font color="red">*</font>最近2月征信查询次数：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCredentials.inquiriesNums" value="${project.cusCredentials.inquiriesNums }" class="easyui-numberbox" data-options="required:true,min:0,max:999,groupSeparator:','"/>次
								</td>
								<td class="label_right1"><font color="red">*</font>社保情况是否正常：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> type="radio" name="cusCredentials.socialSecurity" value="1"  <c:if test="${project.cusCredentials.socialSecurity==1}">checked </c:if>/>是
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> type="radio" name="cusCredentials.socialSecurity" value="2" <c:if test="${project.cusCredentials.socialSecurity==2  || empty project.cusCredentials.socialSecurity}">checked </c:if>/>否
								</td>
								<td class="label_right1"><font color="red">*</font>公积金情况是否正常：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> type="radio" name="cusCredentials.accumulationFund" value="1"  <c:if test="${project.cusCredentials.accumulationFund==1}">checked </c:if>/>是
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> type="radio" name="cusCredentials.accumulationFund" value="2" <c:if test="${project.cusCredentials.accumulationFund==2  || empty project.cusCredentials.accumulationFund}">checked </c:if>/>否
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			
			<!-- 抵押物信息 -->
			<div  style="padding: 10px 10px 0 10px;">
				<div class=" easyui-panel" title="抵押物信息" data-options="collapsible:true" width="1039px;">
					<div style="padding: 10px 0 10px 10px;line-height: 35px;" >
						<%@ include file="./pledge.jsp" %>
					</div>
				</div>
			</div>
			
			<!-- 贷款申请信息 -->
			<div  style="padding: 10px 10px 0 10px;">
				<div class=" easyui-panel" title="贷款申请信息" data-options="collapsible:true" width="1039px;">
					<div style="padding: 10px 5px 10px 0;height: auto;line-height: 35px;" >
						<table class="beforeloanTable">
							<tr>
								<td class="label_right1"><font color="red">*</font>借款金额：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 7}">readonly="readonly"</c:if> name="projectGuarantee.loanMoney" value="${project.projectGuarantee.loanMoney }" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>
								</td>
								<td class="label_right1"><font color="red">*</font>期限：</td>
								<td style="width: 165px">
									<input <c:if test="${project.foreclosureStatus > 7}">readonly="readonly"</c:if> name="projectGuarantee.loanTerm" value="${project.projectGuarantee.loanTerm }" class="easyui-numberbox" data-options="required:true,min:0,max:99"/>月
								</td> 
								<td class="label_right1"><font color="red">*</font>还款方式：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 7}">readonly="readonly"</c:if> name="projectGuarantee.repaymentType" class="easyui-combobox" editable="false" panelHeight="auto" value="${project.projectGuarantee.repaymentType}"
										data-options="validType:'selrequired',valueField:'lookupVal',textField:'lookupDesc',
											url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=MORTGAGE_LOAN_REPAYMENT_TYPE'" />
								</td>
								<td class="label_right"><font color="red">*</font>借款用途：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 7}">readonly="readonly"</c:if> name="projectGuarantee.loanUsage" class="easyui-combobox" editable="false" panelHeight="auto" value="${project.projectGuarantee.loanUsage}"
										data-options="validType:'selrequired',valueField:'lookupVal',textField:'lookupDesc',
											url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=MORTGAGE_LOAN_USAGE'" />
								</td>
							</tr>
							<tr>
								<td class="label_right1"><font color="red">*</font>月利率：</td>
								<td>
									 <input <c:if test="${project.foreclosureStatus > 7}">readonly="readonly"</c:if> name="projectGuarantee.feeRate" value="${project.projectGuarantee.feeRate }" class="easyui-numberbox" data-options="required:true,min:0,max:100,precision:2,groupSeparator:','"/>%
								</td>
								<td class="label_right1"><font color="red">*</font>抵押率（LTV)：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 7}">readonly="readonly"</c:if> name="projectGuarantee.mortgageRate" value="${project.projectGuarantee.mortgageRate }" class="easyui-numberbox" data-options="required:true,min:0,max:100,precision:2,groupSeparator:','"/>%
								</td>
								<td class="label_right1"><font color="red">*</font>还款来源：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 7}">readonly="readonly"</c:if> name="projectGuarantee.paymentSource" class="easyui-combobox" editable="false" panelHeight="auto" value="${project.projectGuarantee.paymentSource}"
										data-options="validType:'selrequired',valueField:'lookupVal',textField:'lookupDesc',
											url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=MORTGAGE_LOAN_PAYMENT_SOURCE'" />
								</td>
							</tr>
							<tr>
								<td class="label_right1"><font color="red">*</font>月返佣费率：</td>
								<td style="width: 162px">
									 <input <c:if test="${project.foreclosureStatus > 7}">readonly="readonly"</c:if> name="projectGuarantee.monthMaidRate" value="${project.projectGuarantee.monthMaidRate }" class="easyui-numberbox" data-options="required:true,min:0,max:100,precision:2,groupSeparator:','"/>%
								</td>
								<td class="label_right1"><font color="red">*</font>逾期日罚息率：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 7}">readonly="readonly"</c:if> name="projectGuarantee.overdueRate" value="${project.projectGuarantee.overdueRate }" class="easyui-numberbox" data-options="required:true,min:0,max:100,precision:2,groupSeparator:','"/>%
								</td>
								<td class="label_right1"><font color="red">*</font>提前还款费率：</td>
								<td style="width: 162px">
									<input <c:if test="${project.foreclosureStatus > 7}">readonly="readonly"</c:if> name="projectGuarantee.prepaymentRate" value="${project.projectGuarantee.prepaymentRate }" class="easyui-numberbox" data-options="required:true,min:0,max:100,precision:2,groupSeparator:','"/>%
								</td>
							</tr>
						</table>
						<a id="loanApplyHistory" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" onclick="LoanInfoHistory()" style="margin-left: 40px;">历史记录</a>
					</div>
				</div>
			</div>
			
			<c:if test="${project.foreclosureStatus > 1}">
			<c:if test="${project.foreclosureStatus > 2}">
			<!-- 银行卡信息 -->
			<div  style="padding: 10px 10px 0 10px;">
				<div class=" easyui-panel" title="银行卡信息" data-options="collapsible:true" width="1039px;">
					<div style="padding: 10px 0 10px 10px;height: auto;line-height: 35px;" >
						<table class="beforeloanTable">
							<tr>
								<td class="label_right1"><font color="red">*</font>收款银行卡户名：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCardInfo.receBankCardName" value="${project.cusCardInfo.receBankCardName }" class="easyui-textbox" data-options="required:true,validType:'length[1,50]'"/>
								</td>
								<td class="label_right1"><font color="red">*</font>收款银行卡账号：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCardInfo.receBankCardCode" value="${project.cusCardInfo.receBankCardCode }" class="easyui-textbox" data-options="required:true,validType:'length[1,50]'"/>
								</td> 
								<td class="label_right1"><font color="red">*</font>收款银行卡开户行：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCardInfo.receBankName" value="${project.cusCardInfo.receBankName }" class="easyui-textbox" data-options="required:true,validType:'length[1,50]'"/>
								</td>
							</tr>
							<tr>
								<td class="label_right1"><font color="red">*</font>还款银行卡户名：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCardInfo.repaymentBankCardName" value="${project.cusCardInfo.repaymentBankCardName }" class="easyui-textbox" data-options="required:true,validType:'length[1,50]'"/>
								</td>
								<td class="label_right1"><font color="red">*</font>还款银行卡账号：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCardInfo.repaymentBankCardCode" value="${project.cusCardInfo.repaymentBankCardCode }" class="easyui-textbox" data-options="required:true,validType:'length[1,50]'"/>
								</td>
								<td class="label_right1"><font color="red">*</font>还款银行卡开户行：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 3}">readonly="readonly"</c:if> name="cusCardInfo.repaymentBankName" value="${project.cusCardInfo.repaymentBankName}" class="easyui-textbox" data-options="required:true,validType:'length[1,50]'"/>
								</td>
							</tr>
						</table>
						<a id="bankHistory" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" onclick="bankInfoHistory()">历史记录</a>
					</div>
				</div>
			</div>
			</c:if>
			<!-- 收费情况 -->
			<div  style="padding: 10px 10px 0 10px;">
				<div class=" easyui-panel" title="收费情况" data-options="collapsible:true" width="1039px;">
					<div style="padding: 10px 0 10px 10px;height: auto;line-height: 35px;" >
						<table class="beforeloanTable">
							<tr>
								<td class="label_right1"><font color="red">*</font>收费节点：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 2}">readonly="readonly"</c:if> name="projectGuarantee.chargesType" class="easyui-combobox" editable="false" panelHeight="auto" <c:if test="${project.projectGuarantee.chargesType != 0}">value="${project.projectGuarantee.chargesType}"</c:if>
										data-options="validType:'selrequired',valueField:'lookupVal',textField:'lookupDesc',
											url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=MORTGAGE_LOAN_CHARGES_TYPE'" />
								</td>
								<td class="label_right1"><font color="red">*</font>收费金额：</td>
								<td>
									<input <c:if test="${project.foreclosureStatus > 2}">readonly="readonly"</c:if> name="projectGuarantee.poundage" value="${project.projectGuarantee.poundage }" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>
								</td> 
							</tr>
						</table>
					</div>
				</div>
			</div>
			</c:if>
			<!-- 保存项目信息 -->
			<div id="foreclosureInfo">
				<div style="padding: 10px 10px 0 10px;">
					<div style="padding-bottom: 20px;padding-top: 10px;">
						<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" id="fore_info_click" onclick="saveProjectInfo()">保存项目信息</a>
					</div>
				</div>
			</div>
			</form>
			<!-- begin 新增共同借款人 -->
			<div id="dlg-buttons_publicMan">		
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addPersonalPublic()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_publicMan').dialog('close')">关闭</a>
			</div>
			<div id="dlg_publicMan" class="easyui-dialog" fitColumns="true"  title="共同借款人"
				style="width:666px;" data-options="modal:true" buttons="#dlg-buttons_publicMan" closed="true" >
				<table id="publicMan_grid"   class="easyui-datagrid"
					style="height: 300px;width: auto;"
					data-options="
					url: '',
					method: 'post',
					rownumbers: true,
					fitColumns:true,
					singleSelect: false,
					pagination: false,
					idField: 'pid'
					">
					<thead>
						<tr>
							<th data-options="field:'pid',checkbox:true" ></th>
							<th data-options="field:'chinaName'" >姓名</th>
							<th data-options="field:'sexName'" >性别</th>
							<th data-options="field:'certTypeName'" >证件类型</th>
							<th data-options="field:'certNumber'" >证件号号码</th>
							<th data-options="field:'relationText'" >与客户关系</th>
							<th data-options="field:'perTelephone'" >手机号码</th>
							<th data-options="field:'proportionProperty'" >产权占比</th>
						</tr>
					</thead>
				</table>
			</div>
			<form method="post" action="<%=basePath%>secondBeforeLoanController/addProjectPublicMan.action" id="publicManForm" name="publicManForm"  >
				<input type="hidden" name="projectId" >
				<input type="hidden" name="userPids" >
			</form>
			<!-- end 新增共同借款人 -->
			
			<%-- 个人客户选取 --%>
			<div id="dlg-buttons_personal">		
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="savePersonal()">选择</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_personal').dialog('close')">关闭</a>
			</div>
			<div id="dlg_personal" class="easyui-dialog" fitColumns="true"  title="个人客户信息查询"
				style="width:850px;top:100px; 0px 0px" buttons="#dlg-buttons_personal" closed="true" >
				<table id="personal_grid" class="easyui-datagrid"  
					style="height: 300px;width: 836px;"
					data-options="
					url: '<%=basePath%>customerController/getPersonalListTwo.action',
					method: 'post',
					rownumbers: true,
					singleSelect: true,
					pagination: true,
					toolbar: '#toolbar_personal',
					idField: 'acctId'
					">
					<thead>
						<tr>
							<th data-options="field:'acctId',checkbox:true" ></th>
							<th data-options="field:'chinaName'" >姓名</th>
							<th data-options="field:'sexName'" >性别</th>
							<th data-options="field:'certTypeName'" >证件类型</th>
							<th data-options="field:'certNumber'" >证件号号码</th>
							<th data-options="field:'liveAddr'" >居住地址</th>
							<th data-options="field:'cusStatusVal'" >客户状态</th>
							<th data-options="field:'realName'" >客户经理</th>
						</tr>
					</thead>
				</table>
				<div id="toolbar_personal">
					<form method="post" action="<%=basePath%>customerController/getPersonalListTwo.action"  id="searchFromPersonal" name="searchFromPersonal"  style="padding: 0px 0px;">
						<div style="margin:5px">
							<input name="pid" id="pid" type="hidden"  value="${currUser.pid}"  />
							<table class="beforeloanTable">
								<tr>
									<td class="label_right">姓名：</td>
									<td><input name="chinaName" id="chinaName" class="easyui-textbox" /></td>
									<td class="label_right">证件类型：</td>
									<td colspan="2"><input name="certType" id="certType" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CERT_TYPE'" /></td>
								</tr>
								<tr>
									<td class="label_right">性别：</td>
									<td><input name="sex" id="sex" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=SEX'" /></td>
									<td class="label_right">证件号码：</td>
									<td><input name="certNumber" id="certNumber" class="easyui-textbox" /></td>
									<td>
										<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#personal_grid','#searchFromPersonal')">查询</a>
										<a href="#" onclick="javascript:$('#searchFromPersonal').form('reset');" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
									</td>
								</tr>
							</table>						
						</div> 
					</form>
				</div>
			</div>
			
			<!-- 贷款申请 -->
			<div id="dlg-buttons_loanInfoHistory">		
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_loanInfoHistory').dialog('close')">关闭</a>
			</div>
			<div id="dlg_loanInfoHistory" class="easyui-dialog" fitColumns="true"  title="贷款申请信息历史查询"
				style="width:850px;top:100px; 0px 0px" buttons="#dlg-buttons_loanInfoHistory" closed="true" >
				<table id="loanInfoHistory_grid" class="easyui-datagrid"  
					style="height: 300px;width: 836px;"
					data-options="
					url: '',
					method: 'post',
					rownumbers: true,
					singleSelect: true,
					pagination: false
					">
					<thead>
						<tr>
							<th data-options="field:'createUserName'">操作人员</th>
							<th data-options="field:'createNode'" formatter="formatMortgageCreateNode">处理节点</th>
							<th data-options="field:'loanMoney'" >借款金额（元）</th>
							<th data-options="field:'loanTerm'" >期限（月）</th>
							<th data-options="field:'repaymentType'" formatter="formatterRepaymentType" >还款方式</th>
							<th data-options="field:'loanUsage'" formatter="formatterLoanUsage">借款用途</th>
							<th data-options="field:'feeRate'" >月利率（%）</th>
							<th data-options="field:'mortgageRate'" >抵押率（%）</th>
							<th data-options="field:'payment'" formatter="formatterPayment">还款来源</th>
							<th data-options="field:'monthMaidRate'" >月返佣费率（%）</th>
							<th data-options="field:'overdueRate'" >逾期日罚息率（%）</th>
							<th data-options="field:'prepaymentRate'" >提前还款费率（%）</th>
							<th data-options="field:'createDate'" formatter="formatterTimestamp">操作时间</th>
						</tr>
					</thead>
				</table>
			</div>
			
			<!-- 客户银行信息历史记录 -->
			<div id="dlg-buttons_cardInfoHistory">		
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_cardInfoHistory').dialog('close')">关闭</a>
			</div>
			<div id="dlg_cardInfoHistory" class="easyui-dialog" fitColumns="true"  title="客户银行信息历史查询"
				style="width:850px;top:100px; 0px 0px" buttons="#dlg-buttons_cardInfoHistory" closed="true" >
				<table id="cardInfoHistory_grid" class="easyui-datagrid"  
					style="height: 300px;width: 836px;"
					data-options="
					url: '',
					method: 'post',
					rownumbers: true,
					singleSelect: true,
					pagination: false
					">
					<thead>
						<tr>
							<th data-options="field:'createUserName'">操作人员</th>
							<th data-options="field:'createNode'" formatter="formatMortgageCreateNode">处理节点</th>
							<th data-options="field:'receBankCardName'" >收款银行卡户名</th>
							<th data-options="field:'receBankCardCode'" >收款银行卡账号</th>
							<th data-options="field:'receBankName'" >收款银行卡开户行</th>
							<th data-options="field:'repaymentBankCardName'" >还款银行卡户名</th>
							<th data-options="field:'repaymentBankCardCode'" >还款银行卡账号</th>
							<th data-options="field:'repaymentBankName'" >还款银行卡开户行</th>
							<th data-options="field:'createDate'" formatter="formatterTimestamp">操作时间</th>
						</tr>
					</thead>
				</table>
			</div>
			<!-- 个人客户选取结束 -->
		</div>
		
		<div title="下户调查" id="tab2" >
		</div>
		
		<div title="签约公证抵押" id="tab3" >
		</div>
	
		<div title="财务明细" id="tab4">
		
		</div>
	
		<div title="资料上传" id="tab5">
		</div>
	</div>
	
	
	<!-- 弹窗窗口 避免form冲突 -->
	<%@ include file="./projectPopDialog.jsp" %>
	
	<div class="panel-body1 pt10" id="loanworkflowWrapDivPanel" style="padding: 10px 10px 0 10px;width: 1039px;">
		<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
			<div style="padding:5px">
				<%@ include file="../common/loanWorkflow.jsp"%> 
				<%@ include file="../common/task_hi_list.jsp"%>
			</div>
		</div>
	</div>
</div>
</body>
</html>