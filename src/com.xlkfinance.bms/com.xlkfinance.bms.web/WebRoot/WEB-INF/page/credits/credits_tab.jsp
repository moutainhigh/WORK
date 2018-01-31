<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
<title>额度提款申请</title>
</head>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/credits/credits_add_investigation.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/applyCommon.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/project_mortgage.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/credits/creditsFlowPath.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/investigation.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/credits/credits_investigation.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/project_contract.js"></script>
<script type="text/javascript">
//归档类型
var Iam = "CREDITEX";
/**************工作流字段 begin******************/ 
var workflowTaskDefKey = "task_CreditWithdrawalRequest";
var WORKFLOW_ID = "creditWithdrawalRequestProcess"; 
/* workflowTaskDefKey = "task_CreditWithdrawalRequest"; */
/**************工作流字段 end********************/
var projectId = ${proId};// 项目ID
var loanId = -1;// 贷款ID
var creditId = -1;// 授信ID
var biaoZhi = -1;// 流程设置为1,编辑设置为2,查看设置为3
var acct_id=0; // 账户id 
var com_id=0; // 企业id
var newProjectId = -1; //新的项目ID
var surPid = -1;//尽职调查报告

$(document).ready(function(){
	/******************工作流字段代码 begin *********************/
 
	//获取路径
	var url = decodeURI(window.location.href);
	var arr = url.split("=");
	nextRoleCode = "BIZ_DIRECTOR";//
	//将参数转换为Json对象
	var params;
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
						newProjectId = params.refId;
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
						biaoZhi = 1;
					}
				}else{
					// 设置新，旧项目ID
					projectId = parseInt(arr[1]);
					newProjectId = parseInt(arr[2]);
					// 判断新项目ID是否为空
					if(newProjectId != -1){
						// 禁用按钮
						$("#personalSave").linkbutton("disable");
						$("#enterpriseSave").linkbutton("disable");
					}
					// 根据项目ID查询授信ID的URL
					var urlCredit = "<%=basePath%>secondBeforeLoanController/getCreditIdByProjectId.action?projectId="+projectId;
					$.post(urlCredit,
						function(ret){
							creditId = ret.header["msg"];
						}, "json");
					// 获取标志号为传过来的数
					biaoZhi = arr[3];
				}
			}
		}
	}
	
	// 加载额度提取头部内容
	loadCreditDiv(${proId});
	
	/******************流程初始化客户基础信息 begin *********************/
	if(projectId!=-1){
		var url="";
		$("input[name='cusType']").attr('disabled','true');
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
	        		$("#personalForm input[name='tempcusType']").val(1);
	        		$("input[name='cusType']:eq(1)").attr("checked",'checked');
	        		$('#personalAbbreviation').val(row.chinaName);
	        		// 获取 共同借款人信息 
	        		var url = "<%=basePath%>customerController/getNoSpouseList.action?acctId="+row.acctId;
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
	        		$("#enterpriseForm input[name='tempcusType']").val(2);
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
		
		
	}
	if(newProjectId!=-1){

		var url = "<%=basePath%>beforeLoanController/getLoanProjectByProjectId.action?projectId="+newProjectId;
		if(biaoZhi == -1){
			url = "<%=basePath%>beforeLoanController/getProjectNameOrNumber.action?projectId="+newProjectId;
		}
		// 获取贷款基本信息表的Id
		$.ajax({
			url:url,
			dataType:"json",
		  	success:function(data){
		  		if(biaoZhi == -1){
		  			
		  		}else{ 
		  			loanId = data.loanId;
		  		}
			}
		})
		
		var urlSr = "<%=basePath%>secondBeforeLoanController/getSurveyReportByProjectId.action?projectId="+newProjectId;
		// 获取尽职调查报告的Id
		$.ajax({
			url:urlSr,
			type:"post",
			dataType:"json",
		  	success:function(data){
		  		if(biaoZhi == -1){
		  		}else{ 
		  			surPid = data.pid;
		  		}
			}
		})
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
	  			if(newProjectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>creditsController/navigationInvestigation.action');
	  		}
	  		// 2.合同
	  		else if(title == "合同"){
	  			if(newProjectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>creditsContractController/navigationContract.action?projectId='+newProjectId+'&contractCatelog=CREDIT_CONTRACT');
	  		}
	  		// 3.借据及还款计划表
	  		else if(title == "借据及还款计划表"){
	  			if(newProjectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>dataUploadController/receiptRepaymentList.action?loanId='+loanId+'&projectId='+projectId+'&contractCatelog=RECEIPT_CONTRACT');
	  		}
	  		// 4.资料上传
	  		else if(title == "资料上传"){ 
	  			if(newProjectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>dataUploadController/navigationDatum.action');
	  		} 
		}
	});
	setTimeout("changeEvent()",1);
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
		if(!$('#personal').hasClass('active')){
		// 清空个人模版数据
		$('#personalForm').form("reset");
		}
		//$('#personal_public').datagrid('loadData', { total: 0, rows: [] });
	}
}

// 打开企业客户信息窗口
function selectEnterprise(){
	$('#enterprise_grid').datagrid('loadData',[]);
	//ajaxSearch('#enterprise_grid','#searchFromEnterprise'); 	
  	$('#dlg_enterprise').dialog('open');
  	$("#cpyNamemm").textbox('setValue',"");
  	$("#ecoTrademm").combobox('setValue',"-1");
// 	$("#searchFromEnterprise input[name='ecoTrade']").combobox('select', '0');
}

// 打开个人客户信息窗口 
function selectPersonal(){
	$('#personal_grid').datagrid('loadData',[]);
 	//ajaxSearch('#personal_grid','#searchFromPersonal');   
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
				projectId = ret.header["pid"];//原项目Id  
				newProjectId=ret.header["msg"];//把新的项目ID赋值  
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
				//选中尽职调查
				$("#tabs").tabs("select", "尽职调查");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}

//贷款试算
function loancalcInfo(){
	if(loanId==-1){
		$.messager.alert('操作提示','请先保存贷款信息','warning');
	}else{
		operRepaymentList('${basePath}beforeLoanController/loanCalc.action?loanId='+loanId);
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
<!-- 授信项目信息 -->
		<div class="easyui-panel" title="授信项目信息" data-options="collapsible:true">	
			<div id="receiptContractBiao" class="dueInfoDiv ">
					<div class="search_result_info cus_table" style="border:none;width:auto;margin:0;padding: 10px 15px 15px 15px;">					
							<%@ include file="add_client.jsp"%>
					</div>
			</div>
		</div>

<div class="easyui-tabs" id="tabs" style="width:auto;height:auto">
	<input type="hidden" id="pid" name="pid" value="-1" />
	<div title="客户信息" id="tab1">
		<input type="hidden" name="pid" id="project_id">
		<input type="hidden" name="loanId" >
		
		
		
		<div class="easyui-panel" style="width:auto;padding-top:10px;">
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
			<form action="<%=basePath%>creditsCustomerInfoController/addNewProject.action" id="enterpriseForm" name="enterpriseForm" method="post">
			<input type="hidden" id="abbreviation" name="abbreviation" />
			<input type="hidden" name="tempcusType" />
			<input type="hidden" name="acctId" />
			<input type="hidden" name="creditId" value="${proId}"><!-- 替代原项目ID -->
			<table class="creditsTable">
				<tr>
					<td class="label_right">企业名称：</td>
					<td>
					<a id='cpyNamema'  onclick='editComBases()' href='#'  style="color:blue ">${cpyName}</a>
					 <input name="cpyName" type="text"  hidden="true" class="no_frame text_style" disabled="true" >
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
			
			<div class="fitem">
				<label style="width: auto;">【股东(投资人)信息】：</label>
			</div>
			<%-- 企业股东信息列表 --%>
			<div class="fitem" style="margin-left: 50px;">
				<table id="share_grid" class="easyui-datagrid" fitColumns="true" style="width: 600px;height: auto;"
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
			</form>
			<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" id="enterpriseSave" class="easyui-linkbutton" style="margin-left: 249px;" iconCls="icon-save" onclick="save('#enterpriseForm')">保存</a>
			</div>
		</div>
		<%-- end 企业模版 --%>
		
		<%-- begin 个人模版 --%>
		<div id="personal" class="none">
			<form action="<%=basePath%>creditsCustomerInfoController/addNewProject.action" id="personalForm" name="personalForm" method="post" >
			<input type="hidden" id="personalAbbreviation" name="abbreviation" />
			<input type="hidden" name="tempcusType" />
			<input type="hidden" name="acctId" />
			<input type="hidden" name="userPids" />
			<input type="hidden"  name="creditId" value="${proId}"><!-- 替代原项目ID -->
			<table class="creditsTable">
				<tr>
					<td class="label_right">客户姓名：</td>
					<td>
					<a id='chinaNamema'  onclick='editPerBase()' href='#'  style="color:blue ">${cpyName}</a>
					 <input name="chinaName" type="text"  hidden="true" class="no_frame text_style"  disabled="true">
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
					<td><input name="cusMail" type="text" class="no_frame text_style" disabled="true"></td>
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
					<label style="width: auto;">共同借款人信息（共同借款人来自个人客户的配偶关系人和非配偶关系人）：</label>
				</div>
				<table id="personal_public" class="easyui-datagrid" fitColumns="true" style="width: 800px;height: auto;"
				     data-options="
				     	url: '',
				     	method: 'post',
				     	rownumbers: true,
			    		fitColumns:true,
				    	singleSelect: false,
					    pagination: false,
					    idField: 'pid'">
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
			<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" id="personalSave" class="easyui-linkbutton" style="margin-left: 249px;" iconCls="icon-save" onclick="save('#personalForm')">保存</a>
			</div>
		</div>
		<%-- end 个人模版 --%>
	
		<div id="dlg-buttons_enterprise">
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveEnterprise()">选择</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_enterprise').dialog('close')">关闭</a>
		</div>
		<%-- 企业客户选取 --%>
		<div id="dlg_enterprise" class="easyui-dialog" fitColumns="true"  title="企业客户信息查询"
		     style="width:850px;top:100px; 0px 0px" buttons="#dlg-buttons_enterprise"  closed="true" >
		    <table id="enterprise_grid" class="easyui-datagrid"  		    	
			 	data-options="
			    url: '<%=basePath%>customerController/getEnterpriseList.action',
			    method: 'post',
			    height:320,
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
						<th data-options="field:'cpyAbbrName'" >企业简介</th>
						<th data-options="field:'busLicCert'" >组织机构代码</th>
						<th data-options="field:'orgCode'" >营业执照号</th>
						<th data-options="field:'orgCode'" >所有制性质</th>
						<th data-options="field:'chinaName'" >法人</th>
						<th data-options="field:'cusRegMoney'" >注册资金（万元）</th>
						<th data-options="field:'cusTelephone'" >联系电话</th>
						<th data-options="field:'cusFoundDate'" >企业成立日期</th>
						<th data-options="field:'cusStatus'" >企业状态</th>	
						<th data-options="field:'realName'" >客户经理</th>	
					</tr>
				</thead>
		   	</table>
			<div id="toolbar_enterprise">
		        <form method="post" action="<%=basePath%>customerController/getEnterpriseList.action"  id="searchFromEnterprise" name="searchFromEnterprise"  style="padding: 0px 0px;">
		     		<div style="margin:5px">
				        <input name="page" id="page" type="hidden"  />
					 	<input name="rows" id="rows" type="hidden"  />
					 	<table class="creditsTable">
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
		    	style="height: 400px;width: 836px;"
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
					 	<table class="creditsTable">
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

 <div id="include_approve_scrutiny" class="pt10">
	<%@ include file="procedures/approve_scrutiny.jsp" %>
</div>
<div id="include_loanApprovalPaper" class="pt10">
	<%@ include file="loanApprovalPaper.jsp" %>
</div>


<div id="include_approve_dizhiya" class="pt10">
	<%@ include file="procedures/credits_dizhiya_add.jsp" %>  
</div>
<!-- 贷款合同制作  -->
<div id="include_make_sign" class="pt10">
	<%@ include file="procedures/contract_make_sign.jsp" %>
</div>
<!-- //财务放款 -->
<div id="include_loan_output"  class="pt10">	
	<%@ include file="loan_ouput.jsp" %> 	
</div>

<div id="include_projectArchive" class="pt10">
	<%@ include file="projectArchive.jsp" %>  
</div> 

<div class="pt10" id="loanworkflowWrapDivPanel" style="padding: 10px 0px 0 0px;">
	<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
		<div class="loanworkflowWrapDiv" style="padding:5px">
			<%@ include file="../common/loanWorkflow.jsp"%> 
			<%@ include file="../common/task_hi_list.jsp"%>
		</div>
	</div> 
</div>
</div>
</body>
</html>
<script>
$(document).ready(function() {
	if(workflowTaskDefKey!='task_CreditWithdrawalRequest'||newProjectId!=-1){
		$("#enterpriseSave").linkbutton("disable");
		$("#personalSave").linkbutton("disable");
		newProjectId = newProjectId;
		projectId =projectId;
	}  
	// 详情链接点击进来,表单禁用
	applyCommon.detailDisable(biaoZhi,3);
});


</script>