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
<title>贷款展期申请</title>
</head>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/applyCommon_fore.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/repayment/fore_extension_add_investigation.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/credits/credits_add_investigation.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/project_mortgage.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/credits/creditsFlowPath.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/credits/creditsCalculationDate.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/project_contract.js"></script>
<script type="text/javascript">
//归档类型
var Iam = "EXTENSION";
/**************工作流字段 begin******************/
var WORKFLOW_ID = "foreExtensionRequestProcess"; 
workflowTaskDefKey = "task_LoanRequest";
nextRoleCode = "DEPARTMENT_MANAGER";//下一处理角色
/**************工作流字段 end********************/
var projectId = ${projectId};// 项目ID
var loanId = -1;// 贷款ID
var creditId = -1;// 授信ID
var biaoZhi = -1;// 流程设置为1,编辑设置为2,查看设置为3
var acct_id=0; // 账户id 
var com_id=0; // 企业id
var receDate = "${project.projectForeclosure.paymentDate}";
var loanMoney = "${project.projectGuarantee.loanMoney}";
var feeRate = "${project.projectGuarantee.feeRate}";
var tempNewProjectId = new Number("${newProjectId}");
var newProjectId = -1;  //新的项目ID
if(tempNewProjectId > 0 ){
	newProjectId =  tempNewProjectId;
}

var productType = -1;//产品类型 1：信用2：赎楼 
$(document).ready(function(){
	/******************工作流字段代码 begin *********************/
	//获取路径
	var url = decodeURI(window.location.href);
	var arr = url.split("=");
	//nextRoleCode = "BIZ_DIRECTOR";//
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
						
						// 标志为是流程
						biaoZhi = 1;
					}
					loadProcessLoggingGrid(taskVo);
				}else{
					// 设置项目ID
					projectId = parseInt(arr[1]);
					// 获取标志号为传过来的数
					biaoZhi = arr[3];
				}
			}
		}
	}
	
	setCombobox3("innerOrOut","INNER_OR_OUT","${project.innerOrOut}");
	setCombobox("businessCategory","BUSINESS_CATEGORY","${project.businessCategory}");
	setCombobox("businessSource","BUSINESS_SOURCE","${project.businessSource}");
	//业务来源下拉框初始化
	setCombobox("bank_source_no","BANK_NAME","-1");
	setCombobox("business_source_no","INTERMEDIARY","-1");
	setCombobox("partner_source_no","PARTNER_NAME","-1");
	var businessSourceStr = '${project.businessSourceStr}';
 	if(businessSourceStr){
		if(businessSourceStr == "银行"){
			$("#bank_source").show();
			$("#business_source").hide();
			$("#partner_source").hide();
			$("#other").hide();
			setCombobox("bank_source_no","BANK_NAME","${project.businessSourceNo}");
		}else if(businessSourceStr == "中介"){
			$("#bank_source").hide();
			$("#business_source").show();
			$("#partner_source").hide();
			$("#other").hide();
			setCombobox("business_source_no","INTERMEDIARY","${project.businessSourceNo}");
		}else if(businessSourceStr == "合作机构"){
			$("#bank_source").hide();
			$("#business_source").hide();
			$("#partner_source").show();
			$("#other").hide();
			setCombobox("partner_source_no","PARTNER_NAME","${project.businessSourceNo}");
		}else if(businessSourceStr == "其他"){
			$("#bank_source").hide();
			$("#business_source").hide();
			$("#partner_source").hide();
			$("#other").show();
		}
	}
	
	$("#businessSource").combobox({ 
		onSelect:function(){
        	var businessSource = $("#businessSource").combobox("getText");
        	if(businessSource == "银行"){
        		$("#bank_source").show();
    			$("#business_source").hide();
    			$("#partner_source").hide();
    			$("#other").hide();
    			$("#innerOrOut").combobox("setValue",2);
    		}else if(businessSource == "中介"){
    			$("#bank_source").hide();
    			$("#business_source").show();
    			$("#partner_source").hide();
    			$("#other").hide();
    		}else if(businessSource == "合作机构"){
    			$("#bank_source").hide();
    			$("#business_source").hide();
    			$("#partner_source").show();
    			$("#other").hide();
    			$("#innerOrOut").combobox("setValue",2);
    		}else if(businessSource == "其他"){
    			$("#bank_source").hide();
    			$("#business_source").hide();
    			$("#partner_source").hide();
    			$("#other").show();
    			$("#innerOrOut").combobox("setValue",2);
    		}else{
    			$("#bank_source").hide();
    			$("#business_source").hide();
    			$("#partner_source").hide();
    			$("#other").hide();
    			$("#innerOrOut").combobox("setValue",2);
    		}
        }
	}); 
	
	var productId = ${project.productId};
	$("#productId").combobox({  
        onChange:function(){  
        	if(productId >0){
        		$.ajax({
            		type: "POST",
                    data:{"productId":productId},
                	url : '${basePath}productController/getProductType.action',
            		dataType: "json",
            	    success: function(data){
            	    	$("#productType").val(data.productType);
            	    	productType = data.productType;
            		}
            	});	
        	}
        }
    });
	setCombobox("address","ADDRESS_VALUE","${project.address}");
	$('#productId').combobox({   
		url:'${basePath}productController/getProductLists.action',    
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
	
	//监管单位初始化
	var superviseVal= "${project.projectForeclosure.superviseDepartment}";
	setBankCombobox("superviseDepartment",0,"${project.projectForeclosure.superviseDepartment}");
	//setBankCombobox("superviseDepartmentBranch",-1,"-1");
	$("#superviseDepartment").combobox({ 
		onChange:function(){
			var superviseDepartment = $("#superviseDepartment").combobox("getValue");
			if(superviseDepartment == superviseVal){
				setBankCombobox("superviseDepartmentBranch",superviseDepartment,"${project.projectForeclosure.superviseDepartmentBranch}");
			}else{
				setBankCombobox("superviseDepartmentBranch",superviseDepartment,"-1");
			}
		}
	});
	
	//新旧按揭银行以及公积金贷款银行
	var oldBank = "${project.projectForeclosure.oldLoanBank}";
	setBankCombobox("oldLoanBank",0,"${project.projectForeclosure.oldLoanBank}");
	$("#oldLoanBank").combobox({ 
		onChange:function(){
			var oldLoanBank = $("#oldLoanBank").combobox("getValue");
			if(oldLoanBank == oldBank){
				setBankCombobox("oldLoanBankBranch",oldLoanBank,"${project.projectForeclosure.oldLoanBankBranch}");
			}else{
				setBankCombobox("oldLoanBankBranch",oldLoanBank,"-1");
			}
		}
	});
	
	var newBank = "${project.projectForeclosure.newLoanBank}";
	setBankCombobox("newLoanBank",0,"${project.projectForeclosure.newLoanBank}");
	$("#newLoanBank").combobox({ 
		onChange:function(){
			var newLoanBank = $("#newLoanBank").combobox("getValue");
			if(newLoanBank == newBank){
				setBankCombobox("newLoanBankBranch",newLoanBank,"${project.projectForeclosure.newLoanBankBranch}");
			}else{
				setBankCombobox("newLoanBankBranch",newLoanBank,"-1");
			}
			
		}
	});
	setBankCombobox("accumulationFundBank",0,"${project.projectForeclosure.accumulationFundBank}");
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
	        		// 重置并填充表单
	        		acct_id=row.acctId;
	        		$('#chinaNamema').html(row.chinaName);
	        		
	        		$("#personalForm input[name='cusType']").val(row.cusType);
	        		$("#personalForm input[name='userPids']").val(row.userPids);
	        		$("#personalForm input[name='chinaName']").val(row.chinaName);
	        		$("#personalForm input[name='sexName']").val(row.sexName);
	        		$("#personalForm input[name='marrName']").val(row.marrName);
	        		$("#personalForm input[name='certTypeName']").val(row.certTypeName);
	        		$("#personalForm input[name='commCode']").val(row.commCode);
	        		$("#personalForm input[name='certNumber']").val(row.certNumber);
	        		$("#personalForm input[name='perTelephone']").val(row.perTelephone);
	        		$("#personalForm input[name='mail']").val(row.mail);
	        		$("#personalForm input[name='commAddr']").val(row.commAddr);

	        		// 设置客户类别和客户id
	        		$("#personalForm input[name='acctId']").val(row.acctId);
	        		$('#personalAbbreviation').val(row.chinaName);
	        		// 获取 共同借款人信息 
	        		var url = "<%=basePath%>customerController/getNoSpouseLists.action?projectId="+projectId;
	        		$('#personal_public').datagrid('options').url = url;
	        		$('#personal_public').datagrid('reload');
	        		$('#personal').addClass('active');
	        		$('#personal').removeClass('none');
	        }
		}); 
	}

	/***************/
	// 根据不同的title选项卡，加载不同的页面
	$('#tabs').tabs({
		onSelect:function(title){
	  		var p = $(this).tabs('getTab', title);
	  		// 判断属于那个title
	  		// 1.尽职调查
	  		if(title == "展期申请"){
	  			if(newProjectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>foreLoanExtensionController/toLoanExtensionInvestigation.action');
	  		}
	  		// 2.合同
	  		else if(title == "合同"){
	  			if(newProjectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>foreLoanExtensionController/navigationContract.action?projectId='+newProjectId+'&contractCatelog=CREDIT_CONTRACT');
	  		}
	  		// 3.借据及还款计划表
	  		else if(title == "借据及还款计划表"){
	  			if(newProjectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			loanId = applyCommon.getLoanId(newProjectId);
	  			p.panel('refresh','<%=basePath%>foreLoanExtensionController/receiptRepaymentList.action?loanId='+loanId+'&projectId='+newProjectId+'&contractCatelog=JJ');
	  		}
	  		// 4.资料上传
	  		else if(title == "资料上传"){ 
	  			if(newProjectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>foreLoanExtensionController/navigationDatum.action?projectId='+newProjectId);
	  		}
		}
	});
	$('.loanworkflowWrapDiv').css('width',$('#tab1 .easyui-panel').width());
	$('.loanworkflowWrapDiv .cus_table').css('width',$('#tab1 .easyui-panel').width());

});

// 单击保存
function save(formId){
	// 根据项目ID判断是否有流程在执行
	if(isWorkflowByStatus(projectId)){
		return;
	}
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
	var businessSource = $("#businessSource").combobox("getText");
	if(businessSource == "银行"){
		var bankSource = $("#bank_source_no").combobox("getValue");
		if(bankSource == -1){
			$.messager.alert("操作提示","请选择具体的银行!","error");
			return;
		}
		$("#businessSourceNo").val(bankSource);
	}
	
	if(businessSource == "中介"){
		var businessSourceNo = $("#business_source_no").combobox("getValue");
		if(businessSourceNo == -1){
			$.messager.alert("操作提示","请选择具体的中介!","error");
			return;
		}
		$("#businessSourceNo").val(businessSourceNo);
	}
	
	if(businessSource == "合作机构"){
		var partnerSourceNo = $("#partner_source_no").combobox("getValue");
		if(partnerSourceNo == -1){
			$.messager.alert("操作提示","请选择具体的合作机构!","error");
			return;
		}
		$("#businessSourceNo").val(partnerSourceNo);
	}
	
	$(formId).form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				projectId = ret.header["pid"];//原项目Id  
				newProjectId=ret.header["msg"];//把新的项目ID赋值  
				$.messager.alert('操作提示','保存信息成功','info');
				
				// 加载展期目标历史项目ID
				extension.loadHisProjectIds(newProjectId);
				// $('#button').attr('disabled',"true");添加disabled属性
				// $('#button').removeAttr("disabled"); 移除disabled属性
				// 去掉a标签中的onclick事件,去掉href属性.因为不能使用disabled属性
				$("#personalSave").removeAttr('onclick');
				$("#personalSave").removeAttr('href');
				$("input[name='cusType']").attr('disabled','true');
				$("#personalSave").linkbutton("disable");
				$("#tabs").tabs("select", "展期申请");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}	

function editPerBase() { 
	parent.openNewTab('${basePath}customerController/editPerBase.action?acctId='
	 		+ acct_id+'&currUserPid='+${currUser.pid}+'&flag='+2+'&isLook='+3,"个人客户详细信息",true);
}		


</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<!-- 基本项目信息 -->

	<div style="padding:10px 20px">
		<table>
			<tr>
				<td height="35" align="left" style="padding-right: 30px;">项目名称: <a href="#" onclick="formatterProjectName.disposeClick(${projectId},'${projectNum}')"><font color='blue'> ${projectName }</font></a></td>
				<td><div class="iniTitle">项目编号:${projectNum}</div></td>
			</tr>
		</table>
	</div>

<%-- <div class="easyui-panel" title="赎楼信息表" data-options="collapsible:true">
	<div id="loanReapymentTable" style="padding-left: 10px;padding-top:10px;height: auto;" >
		<%@ include file="list_loanCalculate.jsp"%>
	</div>
</div>  --%>
<div class = "extension">
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
	<input type="hidden" id="pid" name="pid" value="-1" />
	<div title="业务信息" id="tab1">
		<input type="hidden" name="pid" id="project_id">
		<input type="hidden" name="loanId" >
		<div style="padding: 15px;">
		
		<div class="easyui-panel" title="借款人信息及基础信息填写" data-options="collapsible:true" width="1039px;">
		<%-- begin 个人模版 --%>
		<div id="personal" style="padding:10px 0 15px 0;">
			<form action="<%=basePath%>foreLoanExtensionController/addExtensionProject.action" id="personalForm" name="personalForm" method="post" >
			<input type="hidden" id="personalAbbreviation" name="abbreviation" />
			<input type="hidden" name="tempcusType"/>
			<input type="hidden" name="acctId" />
			<input type="hidden" name="userPids" />
			<input type="hidden"  name="creditId" value="${projectId}"><!-- 替代原项目ID -->
			<input type="hidden" id="productType" name="productType"/>
			<input type="hidden" id="projectSource" name="projectSource" value="${project.projectSource }"/>
			<input type="hidden" name="businessSourceNo" value="${project.businessSourceNo}" id="businessSourceNo"/>
			<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
				<tr>
					<td class="label_right1"><font color="red">*</font>产品名称：</td>
					<td>
						<input name="productId" id="productId" data-options="validType:'selrequired'" disabled="disabled" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
					</td>
					<td class="label_right"><font color="red">*</font>业务类型：</td>
					<td>
						<input name="businessCategory" id="businessCategory" disabled="disabled" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
					</td>
					<td class="label_right"><font color="red">*</font>业务来源：</td>
					<td>
						<input name="businessSource" id="businessSource" disabled="disabled" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
					</td>
					<td colspan="2">
						<div id="bank_source" class="none">
							<select id="bank_source_no" class="easyui-combobox" disabled="disabled" editable="false" style="width:129px;" />
						</div>
						<div id="partner_source1" class="none">
							<select id="partner_source_no1" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="partner_source" class="none">
							<select id="partner_source_no" class="easyui-combobox" disabled="disabled" editable="false" style="width:129px;" />
						</div>
						<div id="partner_source2" class="none">
							<select id="partner_source_no2" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="business_source" class="none">
							<select id="business_source_no" class="easyui-combobox" disabled="disabled" editable="false" style="width:129px;" />
						</div>
						<div id="business_source1" class="none">
							<select id="business_source_no1" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="other" class="none">
							<input class="easyui-textbox" name="myMainText" readonly="readonly" value="${project.myMainText}" style="width:129px;" panelHeight="auto" data-options="validType:'length[0,50]'"/>
						</div>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>经办人：</td>
					<td>
						<input  class="easyui-textbox" disabled="disabled"  data-options="required:true,validType:'length[1,20]'" required="true" name="managers"  value="${empty project.managers?login.realName:project.managers } " />
					</td>
					<td class="label_right"><font color="red">*</font>经办人电话：</td>
					<td>
						<input  class="easyui-textbox" disabled="disabled" data-options="required:true,validType:'phone'" required="true" name="managersPhone" value="${empty project.managersPhone?login.phone:project.managersPhone }" />
					</td>
					<td class="label_right"><font color="red">*</font>内外单：</td>
					<td class="nwd">
						<select id="innerOrOut" name="innerOrOut" class="easyui-combobox" disabled="disabled" data-options="validType:'selrequired'" editable="false" style="width:129px;" />
					</td>
					<td class="label_right"><font color="red">*</font>报单员：</td>
					<td>
						 <input name="declaration" value="${project.declaration}" disabled="disabled" data-options="required:true,validType:'length[1,20]'" id="declaration"  class="easyui-textbox" panelHeight="auto"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">业务联系人：</td>
					<td>
						 <input name="businessContacts" value="${project.businessContacts}" disabled="disabled" data-options="validType:'length[0,20]'" id="businessContacts"  class="easyui-textbox" panelHeight="auto"/>
					</td>
					<td class="label_right">联系电话：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'phone'" disabled="disabled" name="contactsPhone" value="${project.contactsPhone}" />
					</td>
				</tr>
			</table>
			
			<table class="beforeloanTable" width="800">
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
					<td class="label_right">证件类型：</td>
					<td><input name="certTypeName" type="text" class="no_frame text_style" disabled="true"></td>
					<td class="label_right">证件号码：</td>
					<td><input name="certNumber" type="text" class="no_frame text_style" disabled="true"></td>
				</tr>
				<tr>
					<td class="label_right">手机号码：</td>
					<td><input name="perTelephone" type="text" class="no_frame text_style" disabled="true"></td>
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
					    pagination: false">
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
		
		</div>
			
	<!-- 赎楼信息开始 -->
	<div id="foreclosureInfo">
		<div  style="padding:10px 0 15px 0;">
		<div class=" easyui-panel" title="物业及买卖双方信息" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<input type="hidden" name="projectProperty.pid" id="propertyId" value="${project.projectProperty.pid }">
			<input type="hidden" name="projectProperty.projectId" value="${project.projectProperty.projectId }">
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>物业名称：</td>
					<td>
						<input name="projectProperty.houseName" data-options="validType:'length[1,50]'" value="${project.projectProperty.houseName }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>区域：</td>
					<td>
						<select id="address" class="easyui-combobox"  disabled="disabled" data-options="validType:'selrequired'" name="address" editable="false" style="width:129px;" />
					</td>
					<td class="label_right1"><font color="red">*</font>面积：</td>
					<td>
						<input name="projectProperty.area"  data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox" min="0" precision="2" maxLength='8' groupSeparator="," style="width:129px;" value="${project.projectProperty.area }"/>㎡
					</td>
					<td class="label_right"><font color="red">*</font>房产证号：</td>
					<td>
						<input name="projectProperty.housePropertyCard" data-options="validType:'length[1,50]'" value="${project.projectProperty.housePropertyCard }"  class="easyui-textbox"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>用途：</td>
					<td>
						<input name="projectProperty.purpose" data-options="validType:'length[1,50]'" value="${project.projectProperty.purpose }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>原价：</td>
					<td>
						<input class="easyui-numberbox"  data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="projectProperty.costMoney" value="${project.projectProperty.costMoney }"/>
					</td>
					<td class="label_right1" id="evaluationPrice_lable">成交价：</td>
					<td>
						 <input name="projectProperty.tranasctionMoney" id="evaluationPrice" value="${project.projectProperty.tranasctionMoney }" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
					</td>
					<td class="label_right">赎楼成数：</td>
					<td>
						 <input name="projectProperty.foreRate" id="foreRate" readonly="readonly" value="${project.projectProperty.foreRate }" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" style="width:129px;" class="easyui-numberbox"/>%
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>业主姓名：</td>
					<td>
						 <input name="projectProperty.sellerName" id="sellerName" data-options="validType:'length[1,20]'" value="${project.projectProperty.sellerName }"  class="easyui-textbox"/>
					</td>
					<td class="label_right1"><font color="red">*</font>身份证号：</td>
					<td>
						<input name="projectProperty.sellerCardNo" id="sellerCardNo" data-options="validType:'length[1,100]'" value="${project.projectProperty.sellerCardNo }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>联系电话：</td>
					<td>
						<input name="projectProperty.sellerPhone" id="sellerPhone" data-options="validType:'length[1,100]'" value="${project.projectProperty.sellerPhone }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>家庭住址：</td>
					<td>
						 <input name="projectProperty.sellerAddress" id="sellerAddress" data-options="validType:'length[1,100]'" value="${project.projectProperty.sellerAddress }"  class="easyui-textbox" />
					</td>
				</tr>

				<tr>
					<td class="label_right"><font color="red">*</font>买方名称：</td>
					<td>
						 <input name="projectProperty.buyerName" id="buyerName" data-options="validType:'length[0,20]'" value="${project.projectProperty.buyerName }"  class="easyui-textbox"/>
					</td>
					<td class="label_right1"><font color="red">*</font>身份证号：</td>
					<td>
						<input name="projectProperty.buyerCardNo" id="buyerCardNo" data-options="validType:'length[0,50]'" value="${project.projectProperty.buyerCardNo }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>联系电话：</td>
					<td>
						<input name="projectProperty.buyerPhone" id="buyerPhone" data-options="validType:'length[1,100]'" value="${project.projectProperty.buyerPhone }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>家庭地址：</td>
					<td>
						 <input name="projectProperty.buyerAddress" id="buyerAddress" data-options="validType:'length[0,100]'" value="${project.projectProperty.buyerAddress }"  class="easyui-textbox"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
		</div>
	<div style="padding:10px 0 15px 0;">
		<div class=" easyui-panel" title="赎楼" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<input type="hidden" name="projectForeclosure.pid" id="foreclosureId" value="${project.projectForeclosure.pid }">
			<input type="hidden" name="projectForeclosure.projectId" value="${project.projectForeclosure.projectId }">
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>原贷款银行：</td>
					<td>
						<select id="oldLoanBank" class="easyui-combobox"  disabled="disabled" data-options="validType:'selrequired'" name="projectForeclosure.oldLoanBank" editable="false" style="width:129px;" />
					</td>
					<td colspan="2">
						<select id="oldLoanBankBranch" class="easyui-combobox"  disabled="disabled" data-options="validType:'selrequired'" name="projectForeclosure.oldLoanBankBranch" editable="false" style="width:200px;" />
					</td>
					<td class="label_right1"><font color="red">*</font>原贷款金额：</td>
					<td>
						<input class="easyui-numberbox"   data-options="min:0,max:9999999999,precision:2,groupSeparator:','" value="${project.projectForeclosure.oldLoanMoney }" name="projectForeclosure.oldLoanMoney" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>原贷款欠款金额：</td>
					<td>
						 <input name="projectForeclosure.oldOwedAmount" class="easyui-numberbox"   data-options="min:0,max:9999999999,precision:2,groupSeparator:','" value="${project.projectForeclosure.oldOwedAmount }" style="width:129px;"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right">贷款结束时间：</td>
					<td>
						 <input name="projectForeclosure.oldLoanTime"  editable="false"  class="easyui-datebox" value="${project.projectForeclosure.oldLoanTime }" style="width:129px;"/>
					</td>
					<td class="label_right1">银行联系人：</td>
					<td>
						<input name="projectForeclosure.oldLoanPerson" data-options="validType:'length[0,20]'" value="${project.projectForeclosure.oldLoanPerson }" class="easyui-textbox" style="width:129px;"/>
					</td>
					<td class="label_right">联系电话：</td>
					<td>
						 <input name="projectForeclosure.oldLoanPhone" data-options="validType:'phone'" value="${project.projectForeclosure.oldLoanPhone }"  class="easyui-textbox" style="width:129px;"/>
					</td>
				</tr>
				
				<tr style="padding-top:10px;height: 50px;vertical-align: top;">
					<td class="label_right">第三人借款人：</td>
					<td>
						<input class="easyui-textbox" data-options="validType:'length[0,20]'" name="projectForeclosure.thirdBorrower" value="${project.projectForeclosure.thirdBorrower }" style="width:129px;" />
					</td>
					<td class="label_right1">身份证号：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[0,18]'" name="projectForeclosure.thirdBorrowerCard" value="${project.projectForeclosure.thirdBorrowerCard }" style="width:129px;"/>
					</td>
					<td class="label_right">手机号：</td>
					<td>
						 <input name="projectForeclosure.thirdBorrowerPhone" data-options="validType:'phone'" value="${project.projectForeclosure.thirdBorrowerPhone }"  class="easyui-textbox" style="width:129px;"/>
					</td>
					<td class="label_right">地址：</td>
					<td>
						<input class="easyui-textbox" name="projectForeclosure.thirdBorrowerAddress" data-options="validType:'length[0,100]'" value="${project.projectForeclosure.thirdBorrowerAddress }" style="width:129px;"/>
					</td>
				</tr>
				
				<tr style="padding-top:10px;">
					<td class="label_right"><font color="red">*</font>新贷款银行：</td>
					<td>
						<select id="newLoanBank" class="easyui-combobox" disabled="disabled" data-options="validType:'selrequired'" name="projectForeclosure.newLoanBank" editable="false" style="width:129px;" />
					</td>
					<td colspan="2">
						<select id="newLoanBankBranch" class="easyui-combobox" disabled="disabled" data-options="validType:'selrequired'" name="projectForeclosure.newLoanBankBranch" editable="false" style="width:200px;" />
					</td>
					<td class="label_right"><font color="red">*</font>新贷款金额：</td>
					<td>
						<input class="easyui-numberbox"  data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.newLoanMoney" value="${project.projectForeclosure.newLoanMoney }" style="width:129px;"/>
					</td>
					<td class="label_right1">银行联系人：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[0,20]'" name="projectForeclosure.newLoanPerson" value="${project.projectForeclosure.newLoanPerson }" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">联系电话：</td>
					<td>
						 <input name="projectForeclosure.newLoanPhone" data-options="validType:'phone'" value="${project.projectForeclosure.newLoanPhone }"  class="easyui-textbox" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>回款户名：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[1,20]'" name="projectForeclosure.paymentName" value="${project.projectForeclosure.paymentName }" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>回款账号：</td>
					<td colspan="3">
						<input  class="easyui-textbox" data-options="validType:'length[1,50]'" name="projectForeclosure.paymentAccount" value="${project.projectForeclosure.paymentAccount }" style="width:300px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>监管银行：</td>
					<td>
						<select id="superviseDepartment" class="easyui-combobox" disabled="disabled" name="projectForeclosure.superviseDepartment" editable="false" style="width:129px;" />
					</td>
					<td colspan="2">
						<select id="superviseDepartmentBranch" class="easyui-combobox" name="projectForeclosure.superviseDepartmentBranch" editable="false" style="width:200px;" />
					</td>
					<td class="label_right"><font color="red">*</font>资金监管金额：</td>
					<td>
						<input class="easyui-numberbox"  id="fundsMoney" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.fundsMoney" value="${project.projectForeclosure.fundsMoney }" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>监管账号：</td>
					<td colspan="3">
						<input  class="easyui-textbox" data-options="validType:'length[0,50]'" id="superviseAccount" name="projectForeclosure.superviseAccount" value="${project.projectForeclosure.superviseAccount }" style="width:300px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">公积金银行：</td>
					<td>
						<select id="accumulationFundBank" class="easyui-combobox" disabled="disabled" name="projectForeclosure.accumulationFundBank" editable="false" style="width:129px;" />
					</td>
					<!-- <td>
						<select id="accumulationFundBankBranch" class="easyui-combobox" name="projectForeclosure.accumulationFundBankBranch" editable="false" style="width:200px;" />
					</td> -->
					<td class="label_right">公积金贷款金额：</td>
					<td>
						<input class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.accumulationFundMoney" value="${project.projectForeclosure.accumulationFundMoney }" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>借款方式：</td>
					<td>
						<select class="select_style easyui-combobox" disabled="disabled" editable="false" data-options="validType:'selrequired'"  name="projectForeclosure.paymentType" style="width:129px;">
							<option value="-1">--请选择--</option>
							<option value="1" <c:if test="${project.projectForeclosure.paymentType==1 }">selected </c:if>>按揭</option>
							<option value="2" <c:if test="${project.projectForeclosure.paymentType==2 }">selected </c:if>>组合贷</option>
							<option value="3" <c:if test="${project.projectForeclosure.paymentType==3 }">selected </c:if>>公积金贷</option>
							<option value="4" <c:if test="${project.projectForeclosure.paymentType==4 }">selected </c:if>>一次性付款</option>
						</select>
					</td>
					<td class="label_right"><font color="red">*</font>委托公证日期：</td>
					<td>
						 <input name="projectForeclosure.notarizationDate"  value="${project.projectForeclosure.notarizationDate }" class="easyui-datebox" editable="false" style="width:129px;"/>
					</td>
					<td class="label_right">赎楼账号：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[0,50]'" name="projectForeclosure.foreAccount" value="${project.projectForeclosure.foreAccount }" style="width:129px;"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
		</div>
	
		<div  style="padding:0 0 15px 0;">
		<div class=" easyui-panel" title="费用" data-options="collapsible:true" width="1039px;">
		<div style="padding: 10px 0 10px 10px;height: auto;line-height: 35px;" >
			<input type="hidden" name="projectGuarantee.pid" id="guaranteeId" value="${project.projectGuarantee.pid }">
			<input type="hidden" name="projectGuarantee.projectId" value="${project.projectGuarantee.projectId }">
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>借款金额：</td>
					<td>
						<input name="projectGuarantee.loanMoney"  id="loanMoney" value="${project.projectGuarantee.loanMoney }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>借款天数：</td>
					<td>
						 <input name="projectForeclosure.loanDays"  data-options="min:1,max:9999,validType:'integer'" value="${project.projectForeclosure.loanDays }" class="easyui-numberbox" style="width:129px;"/>
					</td>
					<td class="label_right1"><font color="red">*</font>费率：</td>
					<td>
						<input name="projectGuarantee.feeRate" style="width:129px;"  value="${project.projectGuarantee.feeRate }" class="easyui-numberbox" data-options="min:0,max:100,precision:2,groupSeparator:','"/>%
					</td> 
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>咨询费：</td>
					<td>
						<input name="projectGuarantee.guaranteeFee" style="width:129px;"  value="${project.projectGuarantee.guaranteeFee }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td> 
					<td class="label_right1"><font color="red">*</font>手续费：</td>
					<td>
						<input name="projectGuarantee.poundage" style="width:129px;" id="poundage" value="${project.projectGuarantee.poundage }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
					<td class="label_right">手续费补贴：</td>
					<td>
						<input name="projectGuarantee.chargesSubsidized" style="width:129px;" id="chargesSubsidized" readonly="readonly" value="${project.projectGuarantee.chargesSubsidized }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
	</div>
	</div>
	<!-- 赎楼信息结束 -->
	
	</div>
	
	</div>
	<div title="展期申请" id="tab2" >
		
	</div>
<!-- 	<div title="合同" id="tab3">
		
	</div>

	<div title="借据及还款计划表" id="tab4">
		
	</div> -->

	<div title="资料上传" id="tab5">
		
	</div>
</div>

</div>
<div class="panel-body1 pt10" id="loanworkflowWrapDivPanel" style="padding:0 15px 15px 15px;width:1039px">
	<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
		<div style="padding:5px">
			<%@ include file="../../common/loanWorkflow.jsp"%> 
			<%@ include file="../../common/task_hi_list.jsp"%>
		</div>
	</div>
</div>
</div>

</body>
</html>

<script>

$(document).ready(function() {
	
	if("task_LoanRequest" != workflowTaskDefKey){
		$("#personalSave").linkbutton("disable");
		$("#tabs").tabs("select", "展期申请");
	}
	
	// 加载展期目标历史项目ID
	extension.loadHisProjectIds(newProjectId);
	
	// 详情链接点击进来,表单禁用
	applyCommon.detailDisable(biaoZhi,3);
	
	// 已经保存过项目,则把保存人员信息按钮禁用
	if(newProjectId !=-1){

		$("#personalSave").linkbutton("disable");
	}
	
	$('#foreclosureInfo .easyui-linkbutton:not(.download) ,#foreclosureInfo input:not(.download),#tabs textarea').attr('disabled', 'disabled');
	$('#foreclosureInfo .easyui-linkbutton:not(.download) ,#foreclosureInfo input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
	$('#foreclosureInfo .easyui-linkbutton:not(.download) ,#foreclosureInfo input:not(.download),#tabs textarea').addClass('l-btn-disabled');
	$('#foreclosureInfo .easyui-linkbutton:not(.download)').removeAttr('onclick');
});


</script>