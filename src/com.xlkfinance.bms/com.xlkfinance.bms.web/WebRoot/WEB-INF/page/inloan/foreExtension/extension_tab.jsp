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
var WORKFLOW_ID = "creditExtensionRequestProcess"; 
workflowTaskDefKey = "task_LoanRequest";
nextRoleCode = "RISK_ONE";//下一处理角色
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
			searchPartnerSource('${project.businessSourceNo}');
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
	
/* 	var productId = ${project.productId};
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
	}); */
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
		var partnerSourceNo = $("#partner_source_no  option:selected").val();
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

/**
 *  合作机构初始化
 */
function searchPartnerSource(value){
	$.ajax({
		type: "POST",
    	url : '${basePath}beforeLoanController/getOrgAssetsList.action',
		dataType: "json",
	    success: function(result){
	    	if(result != null && result.length>0){
	        	var optionStr = "<option value='-1'>请选择</option>";
	    		for(var i = 0; i<result.length ; i ++ ){
	    			var orgInfo = result[i];
	       			optionStr = optionStr + "<option value='" + orgInfo.orgId+"'"
	       								+ " rate='"+ orgInfo.rate+"' ";
					optionStr += ">" + orgInfo.orgName + "</option>";
	    		}
	        	$("#partner_source_no").empty();
	        	$("#partner_source_no").append(optionStr);	//追加
	          }
	    	if(value >0 || value != undefined){
	    		$("#partner_source_no").val(value);
	    	}
		}
	});	
}

</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<!-- 基本项目信息 -->

	<div style="padding:10px 20px">
		<table>
			<tr>
				<td height="35" align="left" style="padding-right: 30px;">项目名称: <a href="#" onclick="formatterProjectOverview.disposeClick(${projectId})"><font color='blue'> ${projectName }</font></a></td>
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
			<input type="hidden" name="productId" value="${project.productId }"/>
			<input type="hidden" name="userPids" />
			<input type="hidden"  name="creditId" value="${projectId}"><!-- 替代原项目ID -->
			<input type="hidden" id="productType" name="productType" value="2"/>
			<input type="hidden" id="projectSource" name="projectSource" value="${project.projectSource }"/>
			<input type="hidden" name="businessSourceNo" value="${project.businessSourceNo}" id="businessSourceNo"/>
			<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
				<tr>
					<td class="label_right1"><font color="red">*</font>产品名称：</td>
					<td>
						<input  class="easyui-textbox" value="${project.businessTypeText}" data-options="validType:'length[0,50]'" readonly="readonly"/>
					</td>
					<td class="label_right">区域：</td>
					<td>
						<input class="easyui-textbox" name="address"  disabled="disabled" value="${project.address}" data-option="validType:'length[0,50]'"/>
					</td>
					<td class="label_right"><font color="red">*</font>业务来源：</td>
					<td>
						<input name="businessSource" id="businessSource" data-options="validType:'selrequired'" disabled="disabled" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
					</td>
					<td colspan="2">
						<div id="bank_source" class="none">
							<select id="bank_source_no" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="partner_source1" class="none">
							<select id="partner_source_no1" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="partner_source" class="none">
							<select id="partner_source_no" disabled="disabled"
		 				 	  class="select_style" data-options="validType:'selrequired'" style="width: 151px;height: 26px;border-radius: 5px;border-color: #95B8E7;">
		 						<option value="-1">请选择</option>
							</select>
						</div>
						<div id="business_source" class="none">
							<select id="business_source_no" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="business_source1" class="none">
							<select id="business_source_no1" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="other" class="none">
							<input class="easyui-textbox" name="myMain" value="${project.myMain}" class="easyui-textbox" style="width:129px;" panelHeight="auto" data-options="validType:'length[0,50]'"/>
						</div>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>业务联系人：</td>
					<td>
						 <input name="businessContacts" disabled="disabled" value="${project.businessContacts}" data-options="required:true,validType:'length[1,50]'" id="businessContacts"  class="easyui-textbox" panelHeight="auto"  required="true"/>
					</td>
					<td class="label_right"><font color="red">*</font>联系电话：</td>
					<td>
						<input  class="easyui-textbox" disabled="disabled" data-options="required:true,validType:'phone'" required="true" name="contactsPhone" value="${project.contactsPhone}" />
					</td>
					<td class="label_right"><font color="red">*</font>业务类型：</td>
					<td>
						<input name="businessCategory" disabled="disabled" id="businessCategory" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
					</td>
					<td class="label_right"><font color="red">*</font>内外单：</td>
					<td>
						<select id="innerOrOut" name="innerOrOut" class="easyui-combobox" disabled="disabled" data-options="validType:'selrequired'" editable="false" style="width:129px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>经办人：</td>
					<td>
						<input  class="easyui-textbox" disabled="disabled" data-options="required:true,validType:'length[1,50]'" required="true" name="managers" value="${project.managers }" />
					</td>
					<td class="label_right"><font color="red">*</font>经办人电话：</td>
					<td>
						<input  class="easyui-textbox" disabled="disabled" data-options="required:true,validType:'phone'" required="true" name="managersPhone" value="${project.managersPhone }" />
					</td>
					<td class="label_right1"><font color="red">*</font>借款金额：</td>
					<td>
						<input disabled="disabled" required="true" id="loanMoney" value="${project.projectGuarantee.loanMoney }" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
					<td class="label_right"><font color="red">*</font>贷款天数：</td>
					<td>
						 <input disabled="disabled" required="true" data-options="min:1,max:9999,required:true,validType:'integer'" value="${project.projectForeclosure.loanDays }" class="easyui-numberbox" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>放款日期：</td>
					<td>
						 <input disabled="disabled" required="true" value="${project.projectForeclosure.receDate }" class="easyui-datebox" editable="false" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>回款日期：</td>
					<td>
						 <input disabled="disabled" required="true" value="${project.projectForeclosure.paymentDate }" class="easyui-datebox" editable="false" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>回款户名：</td>
					<td>
						<input  class="easyui-textbox" disabled="disabled" data-options="required:true,validType:'length[1,50]'" value="${project.projectForeclosure.paymentName }" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>回款账号：</td>
					<td>
						<input  class="easyui-textbox" disabled="disabled" data-options="required:true,validType:'length[0,50]'" value="${project.projectForeclosure.paymentAccount }" style="width:129px;"/>
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
							<th data-options="field:'proportionProperty'" >产权占比</th>
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
});


</script>