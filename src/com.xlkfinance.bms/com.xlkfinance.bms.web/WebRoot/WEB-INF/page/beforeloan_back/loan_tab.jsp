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
<title>贷款申请</title>
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
//归档类型
var Iam = "BEFORE";
/**************工作流字段 begin******************/
var WORKFLOW_ID = "loanRequestProcess"; 
nextRoleCode = "BIZ_DIRECTOR";//
workflowTaskDefKey = "task_LoanRequest";
/**************工作流字段 end********************/
var projectId = -1;// 项目ID
var loanId = -1;// 贷款ID
var creditId = -1;// 授信ID
var bianzhi = -1;// 流程设置为1,编辑设置为2,查看为3
var acct_id=0; // 账户id 
var com_id=0; // 企业id
var surPid = -1;//尽职调查报告
var biaoZhi;
$(document).ready(function(){
	/******************工作流字段代码 begin *********************/
	//获取路径
	var url = decodeURI(window.location.href);
	var arr = url.split("=");
	//将参数转换为Json对象
	var params;
	
	/***********************个人申请贷款办理************************/
	// 判断当前URL是否存在acctId,如果存在,就表示是从客户管理过来的,如果不存在,不进入
	if(url.indexOf("acctId") > 0){
		acct_id = arr[1];// 获取传过来的客户ID
		var url="";
		// 隐藏数据
		$("input[name='cusType']").attr('disabled','true');
		// 根据客户ID查询用户数据
		$.ajax({
			type: "POST",
	        url: "<%=basePath%>customerController/getCusPersonByAcctId.action",
	        data:{"acctId":acct_id},
	        dataType: "json",
	        success: function(row){
	        	if(row.cusStatus == 1){
	        		// 重置并填充表单
	        		$('#chinaNamema').html(row.chinaName);
	        		$('#personalForm').form('reset');
	        		$('#personalForm').form('load', row);
	        		// 设置客户类别和客户id
	        		$("#personalForm input[name='acctId']").val(acct_id);
	        		$("input[name='cusType']:eq(1)").attr("checked",'checked');
	        		$('#personalAbbreviation').val(row.chinaName);
	        		// 获取 共同借款人信息 
	        		var url = "<%=basePath%>customerController/getNoSpouseList.action?acctId="+acct_id;
	        		$('#personal_public').datagrid('options').url = url;
	        		$('#personal_public').datagrid('reload');
	        		$('#enterprise').addClass('none');
	        		$('#personal').addClass('active');
	        		$('#personal').removeClass('none');
	        		$("#personalSave").removeClass("l-btn-disabled");
	        	}else{
	        		// 重置并填充表单
					com_id=row.pid;
	        		$('#enterpriseForm').form('reset');
	        		$('#enterpriseForm').form('load', row);
	        		// 设置企业简称和客户类别和企业id
	        		$('#abbreviation').val(row.cpyAbbrName);
	        		$('#cpyNamema').html(row.cpyName);
	        		$("#enterpriseForm input[name='acctId']").val(acct_id);
	        		$("#enterpriseForm input[name='cusType']").val(2);
	        		// 获取企业股东人员信息
	        		var url = "<%=basePath%>customerController/getShareList.action?cusComId="+row.pid;
	        		$('#share_grid').datagrid('options').url = url;
	        		$('#share_grid').datagrid('reload'); 
	        		$('#personal').addClass('none');
	        		$('#enterprise').addClass('active');
	        		$('#enterprise').removeClass('none');
	        		$("#enterpriseSave").removeClass("l-btn-disabled");
	        	}
	        }
		});
	} else{
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
							$("#enterpriseSave").linkbutton("disable");
							// 根据项目ID查询贷款ID的URL
							var urlSr = "<%=basePath%>secondBeforeLoanController/getLoanIdByProjectId.action?projectId="+projectId;
							$.post(urlSr,
								function(ret){
									loanId = ret.header["msg"];
								}, "json");
							// 根据项目ID查询授信ID的URL
							var urlCredit = "<%=basePath%>secondBeforeLoanController/getCreditIdByProjectId.action?projectId="+projectId;
							$.post(urlCredit,
								function(ret){
									creditId = ret.header["msg"];
								}, "json");
							// 标志为是流程
							bianzhi = 1;
						}
					}else{
						// 设置项目ID
						projectId = arr[1];
						// 根据项目ID查询授信ID的URL
						var urlCredit = "<%=basePath%>secondBeforeLoanController/getCreditIdByProjectId.action?projectId="+projectId;
						$.post(urlCredit,
							function(ret){
								creditId = ret.header["msg"];
							}, "json");
						// 标志为是编辑
						bianzhi = 2;
					}
				}
			}
		}
	}
	
	/******************流程初始化客户基础信息 begin *********************/
	
	if(projectId!=-1){
		var url="";
		$("input[name='cusType']").attr('disabled','true');
		$("#text_gtjkr").text("已勾选共同借款人信息（共同借款人来自个人客户的配偶关系人和非配偶关系人）:");
		$("#enterpriseSave").linkbutton("disable");
		$("#personalSave").linkbutton("disable");
		$("#readtype1").linkbutton("disable");
		$("#readtype2").linkbutton("disable");
		$.ajax({
			type: "POST",
	        url: "<%=basePath%>customerController/getCusPersonKeyPid.action",
	        data:{"pid":projectId},
	        dataType: "json",
	        success: function(row){
	        	if(row.cusStatus == 1){
	        		// 重置并填充表单
	        		acct_id=row.acctId;
	        		$('#chinaNamema').html(row.chinaName);
	        		$('#personalForm').form('reset');
	        		$('#personalForm').form('load', row);
	        		// 设置客户类别和客户id
	        		$("#personalForm input[name='acctId']").val(row.acctId);
	        		$("input[name='cusType']:eq(1)").attr("checked",'checked');
	        		$('#personalAbbreviation').val(row.chinaName);
	        		// 获取 共同借款人信息 
	        		var url = "<%=basePath%>customerController/getNoSpouseLists.action?projectId="+projectId;
	        		$('#personal_public').datagrid('options').url = url;
	        		$('#personal_public').datagrid('reload');
	        		$('#enterprise').addClass('none');
	        		$('#personal').addClass('active');
	        		$('#personal').removeClass('none');
	        		// 关闭个人客户窗口
	        		$('#dlg_personal').dialog('close');
	        	}else{
	        		// 重置并填充表单
	        		acct_id=row.acctId;
					com_id=row.pid;
	        		$('#enterpriseForm').form('reset');
	        		$('#enterpriseForm').form('load', row);
	        		// 设置企业简称和客户类别和企业id
	        		$('#abbreviation').val(row.cpyAbbrName);
	        		$('#cpyNamema').html(row.cpyName);
	        		$("#enterpriseForm input[name='acctId']").val(row.acctId);
	        		$("#enterpriseForm input[name='cusType']").val(2);
	        		// 获取企业股东人员信息
	        		var url = "<%=basePath%>customerController/getShareList.action?cusComId="+row.pid;
	        		$('#share_grid').datagrid('options').url = url;
	        		$('#share_grid').datagrid('reload'); 
	        		$('#personal').addClass('none');
	        		$('#enterprise').addClass('active');
	        		$('#enterprise').removeClass('none');
	        		// 关闭企业窗口
	        		$('#dlg_enterprise').dialog('close');
	        	}
	        }
		}); 
		
		var url = "<%=basePath%>beforeLoanController/getLoanProjectByProjectId.action?projectId="+projectId;
		if(bianzhi == -1){
			url = "<%=basePath%>beforeLoanController/getProjectNameOrNumber.action?projectId="+projectId;
		}
		// 获取贷款基本信息表的Id
		$.ajax({
			url:url,
			dataType:"json",
		  	success:function(data){
		  		if(bianzhi == -1){
		  			
		  		}else{ 
		  			loanId = data.loanId;
		  		}
			}
		})
		
		var urlSr = "<%=basePath%>secondBeforeLoanController/getSurveyReportByProjectId.action?projectId="+projectId;
		// 获取尽职调查报告的Id
		$.ajax({
			url:urlSr,
			type:"post",
			dataType:"json",
		  	success:function(data){
		  		if(data.pid){
			  		if(bianzhi != -1){
			  			surPid = data.pid;
			  		}
		  		}
			}
		})
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
	  		// 1.尽职调查
	  		if(title == "尽职调查"){
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>beforeLoanController/navigationInvestigation.action?projectId='+projectId);
	  		}
	  		// 2.合同
	  		else if(title == "合同"){
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>beforeLoanController/navigationContract.action?projectId='+projectId+'&contractCatelog=CREDIT_CONTRACT');
	  		}
	  		// 3.借据及还款计划表
	  		else if(title == "借据及还款计划表"){
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>beforeLoanController/receiptRepaymentList.action?loanId='+loanId+'&projectId='+projectId+'&contractCatelog=JJ');
	  		}
	  		// 4.资料上传
	  		else if(title == "资料上传"){ 
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>beforeLoanController/navigationDatum.action');
	  		} 
		}
	});
	setTimeout("changeEvent()",1);
	$('.loanworkflowWrapDiv').css('width',$('#tab1 .easyui-panel').width());
	$('.loanworkflowWrapDiv .cus_table').css('width',$('#tab1 .easyui-panel').width());
});

//企业 or 个人的改变
function changeEvent(){
	// 获取 radio 的值
	var str = $("input[name='cusType']:checked").val();
	if(str == 1){
		// 1 是 个人   显示个人模版
		//document.getElementById("personal").style.display = "block"; 
		//document.getElementById("enterprise").style.display = "none";
		if($('#personal').hasClass('active')){
			$('#personal').removeClass('none');
		}else{
			$('#personal').addClass('none');
		}
		$('#enterprise').addClass('none');
		$('.enterprisebtn').addClass('none');
		$('.personalbtn').removeClass('none');
		// 清空企业模版数据
		if(!$('#enterprise').hasClass('active')){
			$('#enterpriseForm').form('reset');
		}
		//$('#share_grid').datagrid('loadData', { total: 0, rows: [] });
	}else{
		// 2 是 企业  显示企业模版
		//document.getElementById("personal").style.display = "none"; 
		//document.getElementById("enterprise").style.display = "block";
		if($('#enterprise').hasClass('active')){
			$('#enterprise').removeClass('none');
		}else{
			$('#enterprise').addClass('none');
		}
		$('#personal').addClass('none');
		$('.personalbtn').addClass('none');
		$('.enterprisebtn').removeClass('none');
		// 清空个人模版数据
		if(!$('#personal').hasClass('active')){
			$('#personalForm').form("reset");
		}
		//$('#personal_public').datagrid('loadData', { total: 0, rows: [] });
	}
}

// 打开企业客户信息窗口
function selectEnterprise(){
	$('#enterprise_grid').datagrid('loadData',[]);
	//ajaxSearch('#enterprise_grid','#searchFromEnterprise'); 	
  	ajaxSearchPage('#enterprise_grid','#searchFromPersonal');
  	$('#dlg_enterprise').dialog('open');
  	//ajaxSearch('#enterprise_grid','#searchFromEnterprise');
  	$("#cpyNamemm").textbox('setValue',"");
  	$("#ecoTrademm").combobox('setValue',"-1");
// 	$("#searchFromEnterprise input[name='ecoTrade']").combobox('select', '0');
}

// 打开个人客户信息窗口 
function selectPersonal(){
	$('#personal_grid').datagrid('loadData',[]);
	ajaxSearchPage('#personal_grid','#searchFromPersonal');
  	$('#dlg_personal').dialog('open');
  	$("#searchFromPersonal #chinaName").textbox('setValue',"");
  	$("#searchFromPersonal #certType").combobox('setValue',"-1");
  	$("#searchFromPersonal #sex").combobox('setValue',"-1");
  	$("#searchFromPersonal #certNumber").textbox('setValue',"");
}


// 选取企业客户
function saveEnterprise(){
	// 获取选中的行
	var row = $('#enterprise_grid').datagrid('getSelected');	
	// 保证必须选取客户数据
	if(!row){
		$.messager.alert("操作提示","请选择客户信息!","error");
		return false;
	}
	acct_id=row.acctId;
	com_id=row.pid;
	// 重置并填充表单
	$('#enterpriseForm').form('reset');
	$('#enterpriseForm').form('load', row);
	// 设置企业简称和客户类别和企业id
	$('#abbreviation').val(row.cpyAbbrName);
	$('#cpyNamema').html(row.cpyName);
	$("#enterpriseForm input[name='acctId']").val(row.acctId);
	$("#enterpriseForm input[name='cusType']").val(2);
	// 获取企业股东人员信息
	var url = "<%=basePath%>customerController/getShareList.action?cusComId="+row.pid;
	$('#share_grid').datagrid('options').url = url;
	$('#share_grid').datagrid('reload'); 
	$('#personal').addClass('none');
	$('#enterprise').addClass('active');
	$('#enterprise').removeClass('none');
	// 关闭企业窗口 
	$('#dlg_enterprise').dialog('close');
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
	acct_id=row.acctId;
	$('#chinaNamema').html(row.chinaName);
	// 重置并填充表单
	$('#personalForm').form('reset');
	$('#personalForm').form('load', row);
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

// 单击保存
function save(formId){
	// 判断是什么客户类型
	// 如果是可个人客户,判断是否选取了共同借款人
	if(formId == "#personalForm"){
		var rows = $('#personal_public').datagrid('getSelections');
		// 共同借款人
		var userPids = "";
		if(rows.length > 0){
			for(var i=0;i<rows.length;i++){
		  		var row = rows[i];
		  		userPids += row.pid+",";
		  	}
			$("#personalForm input[name='userPids']").val(userPids);
		}
	}
	$(formId).form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				// 设置项目ID
				projectId = ret.header["msg"];
				// 设置授信ID
				creditId = ret.header["creditId"];
				// 提示语句
				$.messager.alert('操作提示','保存信息成功','info');
				// $('#button').attr('disabled',"true");添加disabled属性
				// $('#button').removeAttr("disabled"); 移除disabled属性
				// 去掉a标签中的onclick事件,去掉href属性.因为不能使用disabled属性
				$("#enterpriseSave").removeAttr('onclick');
				$("#enterpriseSave").removeAttr('href');
				$("#personalSave").removeAttr('onclick');
				$("#personalSave").removeAttr('href');
				$("input[name='cusType']").attr('disabled','true');
				$("#enterpriseSave").linkbutton("disable");
				$("#personalSave").linkbutton("disable");
				$("#readtype1").linkbutton("disable");
				$("#readtype2").linkbutton("disable");
				// 判断是否是选择个人
				if(formId == "#personalForm"){
					$("#text_gtjkr").text("已勾选共同借款人信息（共同借款人来自个人客户的配偶关系人和非配偶关系人）:");
					var url = "<%=basePath%>customerController/getNoSpouseLists.action?projectId="+projectId;
	        		$('#personal_public').datagrid('options').url = url;
	        		$('#personal_public').datagrid('reload');
	        		// 打开新增共有人和删除按钮
	        		//$("#gtjkrAdd").removeClass("l-btn-disabled");
	        		//$("#gtjkrRemove").removeClass("l-btn-disabled");
				}
				//选中尽职调查
				//$("#tabs").tabs("select", "尽职调查");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}

// 删除共同借款人
function deletePersonalPublic(){
	var rows = $('#personal_public').datagrid('getSelections');
	// 共同借款人
	var userPids = "";
	// 判断是否选择数据
	if(rows.length == 0){
 		$.messager.alert("操作提示","请选择数据!","error");
		return;
	}
	// 循环获取当前共同借款人的ID
	for(var i=0;i<rows.length;i++){
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
function openPersonalPublic(){
	// 初始化新增共同借款人datagrid
	var url = "<%=basePath%>customerController/getNoSpouseList.action?acctId="+acct_id;
	$('#publicMan_grid').datagrid('options').url = url;
	$('#publicMan_grid').datagrid('reload');
	// 打开窗口
	$('#dlg_publicMan').dialog('open').dialog('setTitle', "新增共有人");
}

// 新增共同借款人
function addPersonalPublic(){
	// 获取数据
	var rows = $('#publicMan_grid').datagrid('getSelections');
	// 共同借款人ID
	var userPids = "";
	// 判断是否选择数据
	if(rows.length == 0){
 		$.messager.alert("操作提示","请选择共同借款人!","error");
		return;
	}
	// 循环获取当前共同借款人的ID
	for(var i=0;i<rows.length;i++){
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

//贷款试算
function loancalc(){
	if(loanId==-1){
		$.messager.alert('操作提示','请先保存贷款信息','warning');
	}else{
		operRepaymentList('loanCalc.action?loanId='+loanId);
	}
}

function editComBases() { 
	parent.openNewTab('${basePath}customerController/editComBases.action?acctId='+acct_id+"&comId="+com_id+'&currUserPid='+${currUser.pid}+'&flag='+2,"企业客户详细信息",true);
}

function editPerBase() { 
	parent.openNewTab('${basePath}customerController/editPerBase.action?acctId='
	 		+ acct_id+'&currUserPid='+${currUser.pid}+'&flag='+2,"个人客户详细信息",true);
}	

</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
	<input type="hidden" id="pid" name="pid" value="-1" />
	
	<div title="业务信息" id="tab1">
	
		<div class=" easyui-panel" title="基本信息" data-options="collapsible:true">
		<table class="cus_table">
			
		   <tr>
		      <td class="align_right">产品名称：</td> 
		      <td><input type="text" class="text_style" name="loanMoney" value=""/></td>
		      <td class="align_right">业务来源：</td>
		      <td><input type="text" class="text_style" name="loanTerm" value=""/></td>
			  </td>
			  <td class="align_right">区域：</td>
		      <td><input type="text" class="text_style" name="loanTerm" value=""/></td>
			  </td>
		   </tr>
		   
		</table>	
		</div>
		<div class="pt10"></div>
		<div class="easyui-panel" style="width:auto;padding-top:30px;">
		<div class="fitem">
			<table>
				<tr>
					<td width="120" align="center"><label>客户类型：</label></td>
					<td width="60"><input name="cusType" type="radio" value="2" onchange="changeEvent()" checked="checked" > 企业</td>
					<td width="60"><input name="cusType" type="radio" value="1" onchange="changeEvent()"> 个人</td>
					<td>
						<a class="easyui-linkbutton enterprisebtn"  id="readtype1" onclick="selectEnterprise()">选择客户</a>
						<a class="easyui-linkbutton personalbtn none"  id="readtype2"  onclick="selectPersonal()">选择个人</a>
					</td>
				</tr>
			</table>
		</div>
		<%-- begin 企业模版 --%>
		<div id="enterprise" class="none">
			<form action="<%=basePath%>beforeLoanController/addProject.action" id="enterpriseForm" name="enterpriseForm"  method="post">
			<input type="hidden" id="abbreviation" name="abbreviation" />
			<input type="hidden" name="cusType" />
			<input type="hidden" name="acctId" />
			
			<table class="beforeloanTable" width="800">
				<tr>
					<td class="label_right">企业名称：</td>
					<td>
					<a id='cpyNamema'  onclick='editComBases()' href='#'  style="color:blue ">${cpyName}</a>
					 <input name="cpyName" type="hidden"  class="no_frame text_style" disabled="true" >
					 </td>
					<td class="label_right">法定代表人姓名：</td>
					<td> <input name="chinaName" type="text" class="no_frame text_style" disabled="true"></td>
				</tr>
				<tr>
					<td class="label_right">企业简称：</td>
					<td> <input name="cpyAbbrName" type="text" class="no_frame text_style" disabled="true"></td>
					<td class="label_right">法定代表人性别：</td>
					<td><input name="sexName" type="text" class="no_frame text_style" disabled="true"></td>
				</tr>
				<tr>
					<td class="label_right">营业执照号码：</td>
					<td><input name="busLicCert" type="text" class="no_frame text_style" disabled="true"></td>
					<td class="label_right">证件类型：</td>
					<td><input name="certTypeName" type="text" class="no_frame text_style" disabled="true"></td>
				</tr>
				<tr>
					<td class="label_right">组织机构代码：</td>
					<td><input name="orgCode" type="text" class="no_frame text_style" disabled="true"></td>
					<td class="label_right">证件号码：</td>
					<td><input name="certNumber" type="text" class="no_frame text_style" disabled="true"></td>
				</tr>
				<tr>
					<td class="label_right">联系电话：</td>
					<td><input name="cusTelephone" type="text" class="no_frame text_style" disabled="true"></td>
					<td class="label_right">手机号码：</td>
					<td><input name="perTelephone" type="text" class="no_frame text_style" disabled="true"></td>
				</tr>
				<tr>
					<td class="label_right">通讯地址：</td>
					<td><input name="commAddr" type="text" class="no_frame text_style" disabled="true"></td>
					<td class="label_right">邮箱号码：</td>
					<td><input name="cusMail" type="text" class="no_frame text_style" disabled="true"></td>
				</tr>
			
			</table>			
			</form>
			
			<%-- 企业股东信息列表 --%>
			<div class="fitem" style="margin-left: 40px;">
				<div style="padding-bottom:10px">
				<label style="width: auto;">【股东(投资人)信息】：</label>
				</div>
				<table id="share_grid" class="easyui-datagrid" fitColumns="true" style="width: 800px;height: auto;"
				     data-options="
				     	url: '',
				     	method: 'post',
				     	rownumbers: true,
				    	singleSelect: true,
					    pagination: false,			    
					    idField: 'pid'"
						 >
					<thead>
						<tr>
							<th data-options="field:'pid'" hidden="true" >PID</th>
							<th data-options="field:'shareTypeName'" >股东（投资人）类别</th>
							<th data-options="field:'shareName'" >股东（投资人）名称</th>
							<th data-options="field:'invMoney'" >出资额</th>
							<th data-options="field:'invWayName'" >出资方式</th>
							<th data-options="field:'shareRatio'" >持股比例</th>
							<th data-options="field:'remark'" >备注</th>
						</tr>
					</thead>
			   	</table>
			</div>
			<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" id="enterpriseSave" class="easyui-linkbutton" style="margin-left: 249px;" iconCls="icon-save" onclick="save('#enterpriseForm')">保存</a>
			</div>
		</div>
		<%-- end 企业模版 --%>
		
		<%-- begin 个人模版 --%>
		<div id="personal">
			<form action="<%=basePath%>beforeLoanController/addProject.action" id="personalForm" name="personalForm" method="post">
			<input type="hidden" id="personalAbbreviation" name="abbreviation" />
			<input type="hidden" name="cusType" />
			<input type="hidden" name="acctId" />
			<input type="hidden" name="userPids" />
			
			<table class="beforeloanTable" width="800">
				<tr>
					<td class="label_right">客户姓名：</td>
					<td>
					<a id='chinaNamema'  onclick='editPerBase()' href='#'  style="color:blue ">${cpyName}</a>
					 <input name="chinaName" type="hidden"  class="no_frame text_style"  disabled="true">
					</td>
					<td class="label_right">客户性别：</td>
					<td><input name="sexName" type="text" class="no_frame text_style" disabled="true"></td>
				</tr>
				<tr>
					<td class="label_right">婚姻状况：</td>
					<td><input name="marrName" type="text" class="no_frame text_style" disabled="true"></td>
					<td class="label_right">证件类型：</td>
					<td><input name="certTypeName" type="text" class="no_frame text_style" disabled="true"></td>
				</tr>
				<tr>
					<td class="label_right">邮政编码：</td>
					<td><input name="commCode" type="text" class="no_frame text_style" disabled="true"></td>
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
					<td class="label_right">通讯地址：</td>
					<td colspan="3"><input name="commAddr" type="text" style="width: 400px;" class="no_frame text_style" disabled="true"></td>
				</tr>
			</table>
			</form>
			
			<div class="fitem" style="margin-left: 40px;">				
				<%-- 共同借款人信息--%>
				<div style="padding-bottom:10px;">
					<label style="width: auto;" id="text_gtjkr">请勾选共同借款人信息（共同借款人来自个人客户的配偶关系人和非配偶关系人）：</label>
				</div>
				<div id="toolbar_gtjkr"  style="border-bottom: 0;">
					<a href="javascript:void(0)" id="gtjkrAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openPersonalPublic()">新增共同借款人</a>
					<a href="javascript:void(0)" id="gtjkrRemove" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deletePersonalPublic()">删除</a>
				</div>
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
							<th data-options="field:'pid',checkbox:true" ></th>
							<th data-options="field:'projectPublicManId',hidden:true" ></th>
							<th data-options="field:'chinaName'" >姓名</th>
							<th data-options="field:'sexName'" >性别</th>
							<th data-options="field:'certTypeName'" >证件类型</th>
							<th data-options="field:'certNumber'" >证件号号码</th>
							<th data-options="field:'relationText'" >与客户关系</th>
							<th data-options="field:'perTelephone'" >联系电话</th>
							<th data-options="field:'workService'" >职务</th>
							<th data-options="field:'monthIncome'" >月收入(万元)</th>
						</tr>
					</thead>
			   	</table>
			</div>
			<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" id="personalSave" class="easyui-linkbutton" style="margin-left: 249px;" iconCls="icon-save" onclick="save('#personalForm')">保存</a>
			</div>
		</div>
		<%-- end 个人模版 --%>
		
		<!-- begin 新增共同借款人 -->
		<div id="dlg-buttons_publicMan">		
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addPersonalPublic()">选择</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_publicMan').dialog('close')">关闭</a>
		</div>
		<div id="dlg_publicMan" class="easyui-dialog" fitColumns="true"  title="共同借款人"
     		style="width:666px;" data-options="modal:true" buttons="#dlg-buttons_publicMan" closed="true" >
				<table id="publicMan_grid" class="easyui-datagrid"  
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
						<th data-options="field:'perTelephone'" >联系电话</th>
						<th data-options="field:'workService'" >职务</th>
						<th data-options="field:'monthIncome'" >月收入(万元)</th>
					</tr>
				</thead>
		   	</table>
		</div>
		<form method="post" action="<%=basePath%>secondBeforeLoanController/addProjectPublicMan.action" id="publicManForm" name="publicManForm"  >
			<input type="hidden" name="projectId" >
			<input type="hidden" name="userPids" >
		</form>
		<!-- end 新增共同借款人 -->
		
		<div id="dlg-buttons_enterprise">
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveEnterprise()">选择</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_enterprise').dialog('close')">关闭</a>
		</div>
		<%-- 企业客户选取 --%>
		<div id="dlg_enterprise" class="easyui-dialog" fitColumns="true"  title="企业客户信息查询"
		     style="width:850px;top:100px; " buttons="#dlg-buttons_enterprise"  closed="true" >
		    <table id="enterprise_grid" class="easyui-datagrid"  		    	
			 	data-options="
			    url: '<%=basePath%>customerController/getEnterpriseList.action',
			    method: 'post',
			    height:300,
			    top:0,
			    rownumbers: true,
			    singleSelect: true,
		    	pagination: true,
			    toolbar: '#toolbar_enterprise',
			    fitColumns:true,
			    idField: 'acctId'
			    ">
				<thead>
					<tr>
						<th data-options="field:'acctId',checkbox:true" ></th>
						<th data-options="field:'cpyName'" >企业名称</th>
						<th data-options="field:'cpyAbbrName'" >企业简称</th>
						<th data-options="field:'orgCode'" >组织机构代码</th>
						<th data-options="field:'busLicCert'" >营业执照号</th>
						<th data-options="field:'comAllNatureText'" >所有制性质</th>
						<th data-options="field:'chinaName'" >法人代表</th>
						<th data-options="field:'cusRegMoney'" >注册资金（万元）</th>
						<th data-options="field:'cusTelephone'" >联系电话</th>
						<th data-options="field:'cusFoundDate'" >企业成立日期</th>
						<th data-options="field:'cusStatusVal'" >企业状态</th>	
						<th data-options="field:'realName'" >客户经理</th>	
					</tr>
				</thead>
		   	</table>
			<div id="toolbar_enterprise">
		        <form method="post" action="<%=basePath%>customerController/getEnterpriseList.action"  id="searchFromEnterprise" name="searchFromEnterprise"  style="padding: 0px 0px;">
		     		<div style="margin:5px">
					 	<input name="pid" id="pid" type="hidden"  value="${currUser.pid}"  />
					 	<table class="beforeloanTable">
					 		<tr>
					 			<td class="label_right">客户名称：</td>
					 			<td><input name="cpyName" id="cpyNamemm" class="easyui-textbox" /></td>
					 			<td class="label_right">经济行业类别：</td>
					 			<td><input name="ecoTrade" id="ecoTrademm" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /></td>
					 			<td>
					 				<a href="#" class="easyui-linkbutton" iconCls="icon-search"  onclick="ajaxSearchPage('#enterprise_grid','#searchFromEnterprise')">查询</a>
									<a href="#" onclick="javascript:$('#searchFromEnterprise').form('reset');" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
					 			</td>
					 		</tr>
					 	</table>
				 		
	        		</div> 
			 	</form>
		   </div>
		</div>
		
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
						<th data-options="field:'workUnit'" >工作单位</th>
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
		</div>
	</div>
	
	 <div title="尽职调查" id="tab2" >
		
	</div>

	<div title="合同" id="tab3">
		
	</div>

	<div title="借据及还款计划表" id="tab4">
		
	</div>

	<div title="资料上传" id="tab5">
		
	</div>
</div>
<div id="include_approve_scrutiny" style="padding: 10px 10px 0 10px;">
	<%@ include file="procedures/approve_scrutiny.jsp" %>
</div>
<div id="include_offlineCreditMeetingSummary" style="padding: 10px 10px 0 10px;">
	<%@ include file="offlineCreditMeetingSummary.jsp" %>
</div>
<div id="include_make_sign" style="padding: 10px 10px 0 10px;">
	<%@ include file="procedures/contract_make_sign.jsp" %>
</div>
<div id="include_approve_dizhiya" style="padding: 10px 10px 0 10px;">
	<%@ include file="procedures/credits_dizhiya_add.jsp" %>
</div>
<div id="include_loanApprovalPaper" style="padding: 10px 10px 0 10px;">
	<%@ include file="loanApprovalPaper.jsp" %>
</div>
<div id="include_loan_output" style="padding: 10px 10px 0 10px;">	
	<%@ include file="loan_ouput.jsp" %> 	
</div>
<div id="include_projectArchive" style="padding: 10px 10px 0 10px;">
	<%@ include file="projectArchive.jsp" %>  
</div>
<div id="include_organization" style="padding: 10px 10px 0 10px;">
	<%@ include file="procedures/organization_commission.jsp" %>
</div>
<div class="panel-body1 pt10" id="loanworkflowWrapDivPanel" style="padding: 10px 10px 0 10px;">
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