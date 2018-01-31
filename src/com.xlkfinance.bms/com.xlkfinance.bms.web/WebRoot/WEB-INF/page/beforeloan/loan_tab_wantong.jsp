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
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/project_contract.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/applyCommon.js"></script>
<title>赎楼贷款申请办理(万通)</title>
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
function selectfirst() {$(this).combobox('select', $(this).combobox('getData')[0].pid);}
//归档类型
var Iam = "BEFORE";
/**************工作流字段 begin******************/
var WORKFLOW_ID = "foreLoanRequestProcess";//默认是万通审批工作流ID
nextRoleCode = "INSPECTOR,DEPARTMENT_MANAGER";//下一处理角色
workflowTaskDefKey = "task_LoanRequest";//当前处理节点ID
/**************工作流字段 end********************/
var projectId = -1;// 项目ID
var loanId = -1;// 贷款ID
var creditId = -1;// 授信ID
var bianzhi = -1;// 流程设置为1,编辑设置为2,查看为3
var acct_id=0; // 账户id 
var com_id=0; // 企业id
var surPid = -1;//尽职调查报告
var biaoZhi;
var productType = -1;//产品类型 1：信用2：赎楼 
isEdit = "${editType}";
var foreStatus = '${project.foreclosureStatus}';
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
		acct_id = arr[2];// 获取传过来的客户ID
		var url="";
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
	        		$("#personalForm input[name='commCode']").val(row.commCode);
	        		$("#personalForm input[name='certNumber']").val(row.certNumber);
	        		$("#personalForm input[name='perTelephone']").val(row.perTelephone);
	        		$("#personalForm input[name='mail']").val(row.mail);
	        		$("#personalForm input[name='liveAddr']").val(row.liveAddr);

	        		// 设置客户类别和客户id
	        		$("#personalForm input[name='acctId']").val(row.acctId);
	        		$('#personalAbbreviation').val(row.chinaName);
	        		
	        		// 设置客户类别和客户id
	        		$("#personalForm input[name='acctId']").val(acct_id);
	        		$('#personalAbbreviation').val(row.chinaName);
	        		// 获取 共同借款人信息 
	        		var url = "<%=basePath%>customerController/getNoSpouseList.action?acctId="+acct_id;
	        		$('#personal_public').datagrid('options').url = url;
	        		$('#personal_public').datagrid('reload');
	        		$('#personal').addClass('active');
	        		$('#personal').removeClass('none');
	        		//$("#personalSave").removeClass("l-btn-disabled");
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
							// 根据项目ID查询贷款ID的URL
							var urlSr = "<%=basePath%>secondBeforeLoanController/getLoanIdByProjectId.action?projectId="+projectId;
							$.post(urlSr,
								function(ret){
									loanId = ret.header["msg"];
								}, "json");
							// 标志为是流程
								bianzhi = 1;
						}
					}else if(url.indexOf("projectId") > 0){
						// 设置项目ID
						projectId = arr[2];
						// 标志为是编辑
						bianzhi = 2;
					}
				}
			}
		}
	}
	
	setCombobox("businessCategory","BUSINESS_CATEGORY","${project.businessCategory}");
	$("#businessCategory").combobox({  
        onChange:function(){  
        	var businessCategory = $("#businessCategory").combobox('getValue');
        		if(businessCategory == 13756){//非交易
        			$("#fundsMoney").numberbox("disable");
        			$("#fundsMoney").numberbox('setValue', 0);
        		
        			$("#superviseDepartment").combobox("disable");
        			$("#superviseDepartment").combobox('setValue', -1);
        			$("#buyerName").textbox("disable");
        			$("#buyerName").textbox('setValue', "");
        			$("#buyerCardNo").textbox("disable");
        			$("#buyerCardNo").textbox('setValue', "");
        			$("#buyerPhone").textbox("disable");
        			$("#buyerPhone").textbox('setValue', "");
        			$("#buyerAddress").textbox("disable");
        			$("#buyerAddress").textbox('setValue', "");
        			$("#superviseAccount").textbox("disable");
        			$("#superviseAccount").textbox('setValue', "");
        			$("#evaluationPrice_lable").text("评估价：");
        		}
        		if(businessCategory == 13755){
        			$("#fundsMoney").numberbox("enable");
        			$("#superviseDepartment").combobox("enable");
        			$("#buyerName").textbox("enable");
        			$("#buyerCardNo").textbox("enable");
        			$("#buyerPhone").textbox("enable");
        			$("#buyerAddress").textbox("enable");
        			$("#superviseAccount").textbox("enable");
        			$("#evaluationPrice_lable").text("成交价：");
        		}
        }
	});
	var productId = "${project.productId}";
	
	$("#productId").combobox({ 
        onChange:function(){  
        	productId = $("#productId").combobox("getValue");
        	if(productId >0){
        		$.ajax({
            		type: "POST",
                    data:{"productId":productId},
                	url : '${basePath}productController/getProductType.action',
            		dataType: "json",
            	    success: function(data){
            	    		$("#productType").val(data.productType);
            		    	productType = data.productType;
            		    	$("#personalForm input[name='productType']").val(productType);
            		}
            	});	
        	}
        }
    });
	
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
	setCombobox3("innerOrOut","INNER_OR_OUT","${project.innerOrOut}");
	setCombobox("businessSource","BUSINESS_SOURCE","${project.businessSource}");
		$("#cert_Number").textbox({
 		    "onChange":function(){
 		    	getAge();
 		    }
 		  });
	//业务来源下拉框初始化
	setCombobox("bank_source_no","BANK_NAME","-1");
	setCombobox("business_source_no","INTERMEDIARY","-1");
	setCombobox("partner_source_no","PARTNER_NAME_WT","-1");
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
			setCombobox("partner_source_no","PARTNER_NAME_WT","${project.businessSourceNo}");
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
	
	//业务来源选择Q房后，内外单自动定位为内单
	$("#business_source_no").combobox({ 
		onSelect:function(){
        	var intermediary = $("#business_source_no").combobox("getText");
        		if(intermediary == "Q房网"){
        			$("#innerOrOut").combobox("setValue",1);
        		}else{
        			$("#innerOrOut").combobox("setValue",2);
        		}
        	}
        }); 
	
	
	
	setCombobox("address","ADDRESS_VALUE","${project.address}");
	//贷款金额和赎楼成数的计算
	$("#loanMoney").numberbox({
		    "onChange":function(){
		    	changForeRate();
		    }
		  });
	
	$("#evaluationPrice").numberbox({
		    "onChange":function(){
		    	changForeRate();
		    }
		  });
	
	//手续费
	$("#poundage").numberbox({
	    "onChange":function(){
	    	changChargesSubsidized();
	    }
	  });
	//内外单手续费补贴计算
	$("#innerOrOut").combobox({ 
		onSelect:function(){
			changChargesSubsidized();
        	}
        }); 

	$('#sellerName').textbox({
		  onChange: function(value){
			  checkNameOrAddress('sellerName');
		  }
		});
	$('#sellerCardNo').textbox({
		  onChange: function(value){
			  checkCardOrPhone('sellerCardNo');
			  //checkCardNo('sellerCardNo');
		  }
		});
	$('#sellerPhone').textbox({
		  onChange: function(value){
			  checkCardOrPhone('sellerPhone');
		  }
		});
	$('#sellerAddress').textbox({
		  onChange: function(value){
			  checkNameOrAddress('sellerAddress');
		  }
		});
	$('#buyerName').textbox({
		  onChange: function(value){
			  checkNameOrAddress('buyerName');
		  }
		});
	$('#buyerCardNo').textbox({
		  onChange: function(value){
			  checkCardOrPhone('buyerCardNo');
			  //checkCardNo('buyerCardNo');
		  }
		});
	$('#buyerPhone').textbox({
		  onChange: function(value){
			  checkCardOrPhone('buyerPhone');
		  }
		});
	$('#buyerAddress').textbox({
		  onChange: function(value){
			  checkNameOrAddress('buyerAddress');
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
	$("#personalSave").linkbutton("enable");//设置保存按钮可用
	if(projectId!=-1){
		var url="";
		$("#readtype1").linkbutton("disable");
		$("#readtype2").linkbutton("disable");
		$("#text_gtjkr").text("已勾选关系人信息（关系人来自个人客户的配偶关系人和非配偶关系人）:");
		//$("#personalSave").linkbutton("disable");
		$.ajax({
			type: "POST",
	        url: "<%=basePath%>customerController/getCusPersonKeyPid.action",
	        data:{"pid":projectId},
	        dataType: "json",
	        success: function(row){
	        		// 重置并填充表单
	        		acct_id=row.acctId;
	        		$('#chinaNamema').html(row.chinaName);
					/*$('#personalForm').form('reset');
	        		$('#personalForm').form('load', row); */
	        		
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
	        		$("#personalForm input[name='liveAddr']").val(row.liveAddr);

	        		// 设置客户类别和客户id
	        		$("#personalForm input[name='acctId']").val(row.acctId);
	        		$('#personalAbbreviation').val(row.chinaName);
	        		// 获取 共同借款人信息 
	        		var url = "<%=basePath%>customerController/getNoSpouseLists.action?projectId="+projectId;
	        		$('#personal_public').datagrid('options').url = url;
	        		$('#personal_public').datagrid('reload');
	        		$('#personal').addClass('active');
	        		$('#personal').removeClass('none');
	        		// 关闭个人客户窗口
	        		$('#dlg_personal').dialog('close');
	        }
		}); 
		
		// 获取贷款基本信息表的Id
		$.ajax({
			url:"<%=basePath%>beforeLoanController/getProjectNameOrNumber.action?projectId="+projectId,
			dataType:"json",
		  	success:function(data){
		  			$("#projectName").val(data.projectName);
		  			$("#projectNumber").val(data.projectNumber);
				}
			});
		
		var urlSr = "<%=basePath%>secondBeforeLoanController/getSurveyReportByProjectId.action?projectId="+projectId;
		// 获取尽职调查报告的Id
		$.ajax({
			url:urlSr,
			type:"post",
			dataType:"json",
		  	success:function(data){
		  		if(data.pid){
			  		if(bianzhi != -1){
						$("#specialDesc").textbox('setValue',data.specialDesc);
			  			surPid = data.pid;
			  		}
		  		}
			}
		});
	}else{
		//$("#gtjkrAdd").linkbutton("disable");
		//$("#gtjkrRemove").linkbutton("disable");
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
	  			p.panel('refresh','<%=basePath%>beforeLoanController/navigationDatum.action?foreStatus='+foreStatus);
	  		} 
	  		// 5.赎楼资料清单
	  		else if(title == "赎楼清单"){ 
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>beforeLoanController/navigationForeInformation.action?projectId='+projectId);
	  		}
	  		// 6.客户经理申请办理
	  		else if(title == "申请办理"){ 
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>beforeLoanController/navigationApplyInfo.action?projectId='+projectId);
	  		}
		}
	});
	//setTimeout("changeEvent()",1);
	$('.loanworkflowWrapDiv').css('width',$('#tab1 .easyui-panel').width());
	$('.loanworkflowWrapDiv .cus_table').css('width',$('#tab1 .easyui-panel').width());
	
	
	// 判断是什么操作
	var url = "<%=basePath%>beforeLoanController/getLoanProjectByProjectId.action?projectId="+projectId;
	if(projectId > 0){
		$.post(url,
			  	function(data){
				
					loanId = data.loanId;
 					//$('#productId').combobox('setValue',data.productId);
 					/*$('#businessCategory').val(data.businessCategory);
					$('#businessSource').val(data.businessSource); */
					$('#projectInfoForm').form('reset');
					$('#projectInfoForm').form('load', data);
			  	}, "json");
	}
	if(workflowTaskDefKey != "task_LoanRequest"){
		$("#specialDesc").attr("readonly",true);
	}
});



<%-- // 删除共同借款人
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
	$.messager.confirm("操作提示","确定删除选择的此批关系人吗?",
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
	
} --%>



function editPerBase() { 
	parent.openNewTab('${basePath}customerController/editPerBase.action?acctId='
	 		+ acct_id+'&currUserPid='+${currUser.pid}+'&flag='+2+'&isLook='+3,"个人客户详细信息",true);
}	

/**
 * 保存项目信息
 */
function saveProjectInfo(){
	acct_id = $("#personalForm input[name='acctId']").val();
	if(acct_id >0){
		
	}else{
		$.messager.alert("操作提示","请选择借款人!","error");
		return;
	}
	
	var productId = $("#productId").combobox("getValue");
	if(productId >0){
		
	}else{
		$.messager.alert("操作提示","请选择产品!","error");
		return;
	}
	
	var loanMoney = $("#loanMoney").numberbox('getValue');
	
	if(loanMoney <=0){
		$.messager.alert('操作提示',"借款金额应大于0!",'error');	
		return false;
	}
	var newLoanMoney = $("#newLoanMoney").numberbox("getValue");
	var fundsMoney = $("#fundsMoney").numberbox('getValue');//资金监管金额
	if(newLoanMoney<0 || (loanMoney - newLoanMoney-fundsMoney)>0){
		$.messager.alert('操作提示',"新贷款金额加监管金额必须大于等于借款金额！",'error');	
		return;
	}
	
	var rows = $('#personal_public').datagrid('getRows');
	// 共同借款人
	var userPids = "";
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		userPids += row.pid+",";
	  	}
		$("#personalForm input[name='userPids']").val(userPids);
	}
	
	var businessSource = $("#businessSource").combobox("getText");
	if(businessSource == "银行"){
		var bankSource = $("#bank_source_no").combobox("getValue");
		$("#businessSourceNo").val(bankSource);
	}
	
	if(businessSource == "中介"){
		var businessSourceNo = $("#business_source_no").combobox("getValue");
		$("#businessSourceNo").val(businessSourceNo);
	}
	
	if(businessSource == "合作机构"){
		var partnerSourceNo = $("#partner_source_no").combobox("getValue");
		$("#businessSourceNo").val(partnerSourceNo);
	}
	
	$("#personalForm").form('submit',{
		onSubmit : function() {},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				
				// 设置项目ID
				projectId = ret.header["msg"];
				$("#personalForm input[name='pid']").val(projectId);
				
				$("#personalForm input[name='projectForeclosure.projectId']").val(projectId);
				$("#personalForm input[name='projectGuarantee.projectId']").val(projectId);
				$("#personalForm input[name='projectProperty.projectId']").val(projectId);
				
				$("#projectName").val(ret.header["projectName"]);
				$("#projectNumber").val(ret.header["projectNumber"]);
			
				$("#foreclosureId").val(ret.header["foreclosureId"]);
				$("#propertyId").val(ret.header["propertyId"]);
				$("#guaranteeId").val(ret.header["guaranteeId"]);
				$("#personalForm input[name='projectGuarantee.pid']").val(ret.header["guaranteeId"]);
				$("#personalForm input[name='projectForeclosure.pid']").val(ret.header["foreclosureId"]);
				$("#personalForm input[name='projectProperty.pid']").val(ret.header["propertyId"]);
			
				// 提示语句
				$.messager.alert('操作提示','保存信息成功','info');
				$("input[name='cusType']").attr('disabled','true');
				//$("#personalSave").linkbutton("disable");
				$("#readtype1").linkbutton("disable");
				$("#readtype2").linkbutton("disable");
				$("#text_gtjkr").text("已勾选关系人信息（关系人来自个人客户的配偶关系人和非配偶关系人）:");
				var url = "<%=basePath%>customerController/getNoSpouseLists.action?projectId="+projectId;
        		$('#personal_public').datagrid('options').url = url;
        		$('#personal_public').datagrid('reload');
        		initFlow();
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
	
}

/**
 * 校验赎楼信息
 */
function checkProjectInfo(){
	if(projectId == -1){
		$.messager.alert("操作提示","请选择保存贷款信息,才能提交审批!","error");
		return false;
	}
	
	var businessSource = $("#businessSource").combobox("getText");
	if(businessSource == "银行"){
		var bankSource = $("#bank_source_no").combobox("getValue");
		if(bankSource == -1){
			$.messager.alert("操作提示","请选择具体的银行!","error");
			return false;
		}
		$("#businessSourceNo").val(bankSource);
	}
	
	if(businessSource == "中介"){
		var businessSourceNo = $("#business_source_no").combobox("getValue");
		if(businessSourceNo == -1){
			$.messager.alert("操作提示","请选择具体的中介!","error");
			return false;
		}
		$("#businessSourceNo").val(businessSourceNo);
	}
	
	if(businessSource == "合作机构"){
		var partnerSourceNo = $("#partner_source_no").combobox("getValue");
		if(partnerSourceNo == -1){
			$.messager.alert("操作提示","请选择具体的合作机构!","error");
			return false;
		}
		$("#businessSourceNo").val(partnerSourceNo);
	}
	
	// 判断项目名称
	if($("#projectName").val()==null || $("#projectName").val()==""){
		$.messager.alert('操作提示',"项目名称不允许为空!",'error');	
		return false;
	}
	
	var loanMoney = $("#loanMoney").numberbox('getValue');
	
	if(loanMoney <=0){
		$.messager.alert('操作提示',"借款金额应大于0!",'error');	
		return false;
	}
	var newLoanMoney = $("#newLoanMoney").numberbox("getValue");
	var fundsMoney = $("#fundsMoney").numberbox('getValue');//资金监管金额
	if(newLoanMoney<0 || (loanMoney - newLoanMoney-fundsMoney)>0){
		$.messager.alert('操作提示',"新贷款金额加监管金额必须大于等于借款金额！",'error');	
		return false;
	}
	
	var businessCategory = $("#businessCategory").combobox('getValue');
	if(businessCategory == 13755){//交易
		if($("#fundsMoney").numberbox('getValue') <=0.0){
			$.messager.alert('操作提示',"交易类赎楼，资金监管金额不允许为空!",'error');	
			return false;
		}
		
		if($("#superviseAccount").textbox('getValue') ==''){
			$.messager.alert('操作提示',"交易类赎楼，资金监管账号不允许为空!",'error');	
			return false;
		}
		
		if($("#superviseDepartment").combobox('getValue') == -1){
			$.messager.alert('操作提示',"交易类赎楼，监管银行不允许为空!",'error');	
			return false;
		}
		if($("#superviseDepartmentBranch").combobox('getValue') == -1){
			$.messager.alert('操作提示',"交易类赎楼，监管银行支行不允许为空!",'error');	
			return false;
		}
		
		if($("#buyerName").textbox('getValue') ==''){
			$.messager.alert('操作提示',"交易类赎楼，买方姓名不允许为空!",'error');	
			return false;
		}
		
		if($("#buyerCardNo").textbox('getValue')==''){
			$.messager.alert('操作提示',"交易类赎楼，买方身份证号不允许为空!",'error');	
			return false;
		}
		
		if($("#buyerPhone").textbox('getValue')==''){
			$.messager.alert('操作提示',"交易类赎楼，买方联系电话不允许为空!",'error');	
			return false;
		}
		
		if($("#buyerAddress").textbox('getValue')==''){
			$.messager.alert('操作提示',"交易类赎楼，买方地址不允许为空!",'error');	
			return false;
		}
		if($("#evaluationPrice").numberbox("getValue")<=0){

			$.messager.alert('操作提示',"交易类赎楼，成交价必须大于0!",'error');	
			return false;
		}
		
		}
	var flag = $("#personalForm").form('validate');
	if(!flag){
		$.messager.alert('操作提示',"请先保存数据!",'error');
		return false;
	}
	$("#personalForm").form('submit',{
		onSubmit : function() {},
        success:function(result){
        }
	});
	return true;
}
//重新初始化流程
function initFlow(){
	if(isEdit == 'isEdit'){
		return;
	}
	//重置流程提交
	var roleCode = null;
	var roleName = null;
	var loanMoney = findProjectLoanMoney(projectId);
	var specialState = checkSpecialDesc(projectId);
	var htmlStr = '<tr id="next_node_tr">'
		+ '<td class="label_right" >业务提交至：</td>'
		+ '<td id="next_node_td">';
	//贷款申请下一节点，大于300万需要部门经理审批，否者审查员审批
	if("foreLoanRequestProcess" == WORKFLOW_ID &&"task_LoanRequest" == workflowTaskDefKey ){
		var url = BASE_PATH + 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
		$.ajax({
			url : url,
			async : false,
			success : function(data) {
				var datas = eval(data);
				var len = datas.length;
				for(var i = 0; i < len; i++){
					var value = datas[i].roleCode;
					if (loanMoney > 3000000 || specialState == 1) {
						if ("DEPARTMENT_MANAGER" == value) {
							roleCode = value;
							roleName = datas[i].roleName;
							break;
						}
					} else {
						if ("INSPECTOR" == value) {
							roleCode = value;
							roleName = datas[i].roleName;
							break;
						}
					}
				}
			}
		});
		
		htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
			+ roleCode
			+ '" class="verifier" value="'
			+ roleCode
			+ '" taskNodeName="'
			+ roleName
			+ '"/>'
			+ '<span id="transfer_down">'
			+ roleName
			+ '</span>&nbsp;&nbsp;&nbsp;';
		htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
			+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
	$("#next_node_tr").replaceWith(htmlStr);
	$('#userIds').val(null);
	$('#userIds').combobox({
		valueField : 'pid',
		textField : 'realName',
		multiple : true,
		editable : false,
		disabled : true
	});
	$('body').delegate('.verifier', 'change',
			findUsersByRoleCode);
	}else if("foreLoanRequestProcess" == WORKFLOW_ID && "task_ExaminerCheck" == workflowTaskDefKey){
		var url = BASE_PATH+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
					var datas = eval(data);
					var len = datas.length;
					for(var i = 0; i < len; i++){
						var value = datas[i].roleCode;
						if (loanMoney > 3000000 || specialState == 1) {
							if ("REVIEW_DEPARTMENT_SUPERVISOR" == value) {
									roleCode = value;
									roleName = datas[i].roleName;
									break;
								}
						} else {
							if ("RISK_DIRECTOR" == value) {
								roleCode = value;
								roleName = datas[i].roleName;
								break;
							}
						}
					}
				}
			});
		
		htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
			+ roleCode
			+ '" class="verifier" value="'
			+ roleCode
			+ '" taskNodeName="'
			+ roleName
			+ '"/>'
			+ '<span id="transfer_down">'
			+ roleName
			+ '</span>&nbsp;&nbsp;&nbsp;';
		htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
			+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
	$("#next_node_tr").replaceWith(htmlStr);
	$('#userIds').val(null);
	$('#userIds').combobox({
		valueField : 'pid',
		textField : 'realName',
		multiple : true,
		editable : false,
		disabled : true
	});
	$('body').delegate('.verifier', 'change',
			findUsersByRoleCode);
	}
}

//打开新增借款人
function createPersonal(){
	//绑定下拉框的值
 	setCombobox("cert_type","CERT_TYPE",13088);
 	setCombobox("add_sex","SEX",-1);
 	$('#personInfo').form('reset');
	$('#add_person').dialog('open').dialog('setTitle', "新增借款人");
}
//新增借款人
function addPersonal(){
	var tempFlag=1;
	var certNumber = $("#cert_Number").val();
	$("#sex_text").val($("#add_sex").combobox("getValue"));
	var personId = $("#personInfo input[name='cusPerson.pid']").val();
	var type=false;
	$.ajax({
		url : '<%=basePath%>customerController/validateCeryNumber.action?certNumber='
			+ certNumber+ '&personId='+personId,
		type : 'post',
		cache: false,
		success : function(result) {
			var ret = eval("("+result+")");
			var flags=ret.header["flags"]; 
			if(flags>0){
				tempFlag=3;
			}
			if(tempFlag==3){
				$.messager.alert('操作提示',"系统已存在此证件号码的用户！",'error');	
				$("#cert_Number").focus();
			}
			else{
				$('#personInfo').form('submit', {
					onSubmit : function() {
							return $(this).form('validate'); 
					},
					success : function(result) {
						var ret = eval("("+result+")");
						acct_id=ret.header["acctId"];
						if(acct_id >0){
							// 关闭个人客户窗口
				        	$('#add_person').dialog('close');
							// 根据客户ID查询用户数据
							$.ajax({
								type: "POST",
						        url: "<%=basePath%>customerController/getCusPersonByAcctId.action",
						        data:{"acctId":acct_id},
						        dataType: "json",
						        success: function(row){
						        		// 重置并填充表单
						        		$('#chinaNamema').html(row.chinaName);
										$("#personalForm input[name='userPids']").val(row.userPids);
										$("#personalForm input[name='pid']").val(projectId);
										$("#personalForm input[name='chinaName']").val(row.chinaName);
										$("#personalForm input[name='sexName']").val(row.sexName);
										$("#personalForm input[name='marrName']").val(row.marrName);
										$("#personalForm input[name='certTypeName']").val(row.certTypeName);
										$("#personalForm input[name='commCode']").val(row.commCode);
										$("#personalForm input[name='certNumber']").val(row.certNumber);
										$("#personalForm input[name='perTelephone']").val(row.perTelephone);
										$("#personalForm input[name='mail']").val(row.mail);
										$("#personalForm input[name='liveAddr']").val(row.liveAddr);
						        		// 设置客户类别和客户id
						        		$("#personalForm input[name='acctId']").val(acct_id);
						        		$('#personalAbbreviation').val(row.chinaName);
						        		// 获取 共同借款人信息 
						        		var url = "<%=basePath%>customerController/getNoSpouseList.action?acctId="+acct_id;
						        		$('#personal_public').datagrid('options').url = url;
						        		$('#personal_public').datagrid('reload');
						        		$('#personal').addClass('active');
						        		$('#personal').removeClass('none');
						        		//$("#personalSave").removeClass("l-btn-disabled");
						        		$("#personalSave").linkbutton("enable");
									 /* $("#gtjkrAdd").linkbutton("enable");
						        		$("#gtjkrRemove").linkbutton("enable"); */
						        }
							});
						}else{
							$.messager.alert('操作提示',"保存失败",'error');	
						}
					},error : function(result){
						$.messager.alert('操作提示',"保存失败",'error');	
					}
				}); 
			}
		}
	});
}

/**
 * 检验身份证号码以及计算年龄与性别
 */
function getAge(){
	var certType = $("#cert_type").combobox('getText');
	var certNumber = $("#cert_Number").val();
	if(certType=='身份证' && certNumber.length>0 &&(!IdCardValidate(certNumber)))
      {
		$.messager.alert('操作提示',"输入的身份证号长度不对，或者身份证号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。",'error');	
        $("#cert_Number").textbox('setValue',"");
        return false;
      }
	if(certType=='身份证' && certNumber.length>='15'){
		if(certNumber.substring(12,14)> 31){
			$.messager.alert('操作提示',"请输入正确的身份证号！",'error');
			$("#cert_Number").textbox('setValue',"");
			return false;
		}
		else{
			
			var year=certNumber.substring(6,10);
			var month=certNumber.substring(10,12);
			var date=certNumber.substring(12,14);
			$("#birth_Date").val(year+"-"+month+"-"+date);
			
			//判断性别
			if (parseInt(certNumber.substr(16, 1)) % 2 == 1) {
				//男
				$("#add_sex").combobox("setValue","13098");
				} else {
				//女
				$("#add_sex").combobox("setValue","13099");
				}
			//获取年龄
			var myDate = new Date();
			var mon = myDate.getMonth() + 1;
			var day = myDate.getDate();
			
			var age = myDate.getFullYear() - certNumber.substring(6, 10) - 1;
			if (certNumber.substring(10, 12) < mon || certNumber.substring(10, 12) == mon && certNumber.substring(12, 14) <= day) {
			age++;
			}
			$("#age").val(age);
		}
	}
	validateCertNumber();
}
//中文名失去焦点验证合法性
function checkChineseName(id){
	var chinaName = $(id).val();
	if(chinaName==''){
		$.messager.alert('操作提示',"客户姓名不能为空！",'error');
		$(id).val("");
		return false;
	}
	if(chinaName.length >20){
		$.messager.alert('操作提示',"客户姓名长度不能超过20个字符！",'error');
		$(id).val("");
		return false;
	}
}

/**
 * 校验手机号码
 */
function checkPhone(phone){
	var phoneNum = $(phone).val();
	if(phoneNum==''){
		$.messager.alert('操作提示',"手机号码不能为空！",'error');
		$(phone).val("");
		return false;
	}
	var reg = /^(\d{3,4})?-?\d{7,8}$/; 
	var reg2 = /^1[3|4|5|8|9]\d{9}$/;
	if(!reg.test(phoneNum) && !reg2.test(phoneNum)){
		$.messager.alert('操作提示',"手机号码格式错误！",'error');
		$(phone).val("");
		return false;
	}
}
/**
 * 校验身份证号码
 */
function checkCertNumber(certId,certTypeId){
	
	var certType = $(certTypeId).combobox('getText');
	var certNumber = $(certId).val();
	if(certType=='身份证' &&certNumber.length>0 &&!IdCardValidate(certNumber))
    {
		$.messager.alert('操作提示',"输入的身份证号长度不对，或者身份证号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。",'error');	
        $(certId).val('');
        return false;
    }
	if(certType=='身份证' && certNumber.length>='15'){
		if(certNumber.substring(12,14)> 31){
			$.messager.alert('操作提示',"请输入正确的身份证号！",'error');
			$(certId).val('');
			 return false;
		}
	}
}

/**
 * 校验产权值
 */
function checkNumber(numId){
	var rate = $(numId).val();
	if(rate<0 || rate >100){
		$.messager.alert('操作提示',"请输入0到100的值！",'error');
		$(numId).val('');
		return false;
	}
}

//赎楼成数
function changForeRate(){
	var loanMoney = $("#loanMoney").numberbox("getValue");
	//评估价
	var evaluationPrice = $("#evaluationPrice").numberbox("getValue");
	
	if(evaluationPrice > 0){
		var foreRate = loanMoney/evaluationPrice;
		$("#foreRate").numberbox("setValue",foreRate*100);
	}
}
//手续费补贴
function changChargesSubsidized(){
	//内外单
	var innerOrOut = $("#innerOrOut").combobox("getValue");
	//手续费
	var poundage = $("#poundage").numberbox("getValue");
	var chargesSubsidized = 0;
	if(poundage >= 0){
		if(innerOrOut == 1){
			chargesSubsidized = poundage*0.5;
		}else if(innerOrOut == 2){
			chargesSubsidized = poundage;
		}
	}
	$("#chargesSubsidized").numberbox("setValue",chargesSubsidized);

}
//保存审查员意见
function saveAuditorOpinion(){
	// 提交表单
	$('#auditorOpinionInfo').form('submit', {
		onSubmit : function() {return $(this).form('validate');},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$.messager.alert('操作提示',"保存成功!",'info');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
		}
	});
}
//是否买卖方
function changeEvent(){
	// 获取 radio 的值
	var str = $("input[name='isSeller']:checked").val();
	var businessCategory = $("#businessCategory").combobox('getText');
	
	var customerName = $("#personalForm input[name='chinaName']").val();
	var customerCard = $("#personalForm input[name='certNumber']").val();
	var customerPhone = $("#personalForm input[name='perTelephone']").val();
	var sutomerAddress = $("#personalForm input[name='liveAddr']").val();
	if(str == 1 && businessCategory == "交易"){//买方
		$("#buyerName").textbox('setValue',customerName.trim());
		$("#buyerCardNo").textbox('setValue',customerCard.trim());
		$("#buyerPhone").textbox('setValue',customerPhone.trim());
		$("#buyerAddress").textbox('setValue',sutomerAddress.trim());
		
	}
	if(str == 2){//卖方
		$("#sellerName").textbox('setValue',customerName.trim());
		$("#sellerCardNo").textbox('setValue',customerCard.trim());
		$("#sellerPhone").textbox('setValue',customerPhone.trim());
		$("#sellerAddress").textbox('setValue',sutomerAddress.trim());
	}
	
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
	acct_id=row.acctId;
	$('#chinaNamema').html(row.chinaName);
	$("#personalSave").linkbutton("enable");
	// 重置并填充表单
	$("#personalForm input[name='userPids']").val(row.userPids);
	$("#personalForm input[name='pid']").val(projectId);
	$("#personalForm input[name='chinaName']").val(row.chinaName);
	$("#personalForm input[name='sexName']").val(row.sexName);
	$("#personalForm input[name='marrName']").val(row.marrName);
	$("#personalForm input[name='certTypeName']").val(row.certTypeName);
	$("#personalForm input[name='commCode']").val(row.commCode);
	$("#personalForm input[name='certNumber']").val(row.certNumber);
	$("#personalForm input[name='perTelephone']").val(row.perTelephone);
	$("#personalForm input[name='mail']").val(row.mail);
	$("#personalForm input[name='liveAddr']").val(row.liveAddr);
	// 设置个人客户的简称
	$('#personalAbbreviation').val(row.chinaName);
	// 设置客户类别和客户id
	$("#personalForm input[name='acctId']").val(row.acctId);
	$("#personalForm input[name='cusType']").val(1);
	
/* 	$("#gtjkrAdd").linkbutton("enable");
	$("#gtjkrRemove").linkbutton("enable"); */
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

//打开新增共同借款人窗口
function openPersonalPublic(){
	if(acct_id <= 0){
		$.messager.alert("操作提示","请先选择借款人!","error");
		return;
	}
	
	var rows = $('#personal_public').datagrid('getRows');
	// 共同借款人
	var userPids = "";
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		userPids += row.pid+",";
	  	}
	}
	
	// 初始化新增共同借款人datagrid
	var url = "<%=basePath%>customerController/getNoSpouseList.action?acctId="+acct_id;
	$("#customer_public tr").eq(0).nextAll().remove();
	$.ajax({
		url:url,
		type:"post",
		dataType:"json",
	  	success:function(data){
	  		if(data.length == 0){
	  		    var str='<tr id="customer_tr'+0+'" name="'+0+'" style="border:1px solid #999;">'+
	  		            '<td>'+
	  		          	'<input type="checkbox" name="customerList['+0+'].pid" value="0" class="isChecked"/>'+
	  		          	'<input type="hidden" name="customerList['+0+'].status" value="1"/>'+
	  	          		'<input type="hidden" name="customerList['+0+'].cusAcct.pid" value="'+acct_id+'"/>'+
	  		            '</td>'+
	  			          '<td align="center">'+'<input type="text" onblur="checkChineseName(this)" name="customerList['+0+'].chinaName" required="true" data-options="required:true,validType:\'length[1,20]\'" class="text_style easyui-textbox" style="width:80px;"/>'+'</td>'+
	  			 		  '<td align="center">'+
	  			 		     '<select name="customerList['+0+'].relationType" id="customer_relation_'+0+'" data-options="validType:\'selrequired\'" class="easyui-combobox"  style="width:100px;" />'+
	  			 		  '</td>	'+
	  			 		   '<td align="center">'+
	  			 		  '<select name="customerList['+0+'].certType" id="customer_certType_'+0+'" data-options="validType:\'selrequired\'" class="easyui-combobox"  style="width:100px;" />'+
	  			 		  '</td>'+
	  			 		  '<td align="center">'+
	  			 		     '<input type="text" name="customerList['+0+'].certNumber" onblur="checkCertNumber(this,customer_certType_0)" required="true" data-options="required:true" class="text_style easyui-textbox" style="width:140px;"/>'+
	  			 		  '</td>'+
	  				 		 '<td align="center">'+
	  			 		     '<input type="text" name="customerList['+0+'].telephone" onblur="checkPhone(this)" required="true" data-options="required:true,validType:\'telephone\'" class="text_style easyui-textbox" style="width:120px;"/>'+
	  			 		  '</td>'+
	  			 		  '<td align="center">'+
	  					     '<input type="text" name="customerList['+0+'].proportionProperty" onblur="checkNumber(this)" value="0" required="true" data-options="required:true,validType:\'number\'" class="text_style easyui-textbox" style="width:40px;"/>'+
	  				 		  '</td>'+
	  			     	'</tr>';
	  			$("#customer_public").append(str);
	  			setRelationCombobox("customer_relation_"+0,"RELATION",-1);
	  			setCombobox("customer_certType_"+0,"CERT_TYPE","13088");
	  		}else if(data.length > 0){
	  			for(var i=0;i<data.length;i++){
	  				//判断是否需要选中
	  				var checkStr = '<input type="checkbox" name="customerList['+i+'].pid"  class="isChecked" value="'+data[i].pid+'"/>';
	  				if(userPids != "" && userPids.indexOf(data[i].pid) != -1){
	  					checkStr = '<input type="checkbox" name="customerList['+i+'].pid" checked class="isChecked" value="'+data[i].pid+'"/>';
	  				}
	  				 var str='<tr id="customer_tr'+i+'" name="'+i+'" style="border:1px solid #999;">'+
	  		            '<td>'+ checkStr +
	  		          	'<input type="hidden" name="customerList['+i+'].status" value="1"/>'+
	  	          		'<input type="hidden" name="customerList['+i+'].cusAcct.pid" value="'+acct_id+'"/>'+
	  		            '</td>'+
	  			          '<td align="center">'+'<input type="text" onblur="checkChineseName(this)" name="customerList['+i+'].chinaName" required="true" data-options="required:true,validType:\'length[1,20]\'" class="text_style easyui-textbox" value="'+data[i].chinaName+'" style="width:80px;"/>'+'</td>'+
	  			 		  '<td align="center">'+
	  			 		     '<select name="customerList['+i+'].relationType" id="customer_relation_'+i+'" data-options="validType:\'selrequired\'" class="easyui-combobox"  style="width:100px;" />'+
	  			 		  '</td>	'+
	  			 		   '<td align="center">'+
	  			 		  '<select name="customerList['+i+'].certType" id="customer_certType_'+i+'" data-options="validType:\'selrequired\'" class="easyui-combobox"  style="width:100px;" />'+
	  			 		  '</td>'+
	  			 		  '<td align="center">'+
	  			 		     '<input type="text" name="customerList['+i+'].certNumber" onblur="checkCertNumber(this,customer_certType_'+i+')" required="true" data-options="required:true" value="'+data[i].certNumber+'" class="text_style easyui-textbox" style="width:140px;"/>'+
	  			 		  '</td>'+
	  				 		 '<td align="center">'+
	  			 		     '<input type="text" name="customerList['+i+'].telephone" onblur="checkPhone(this)" required="true" data-options="required:true,validType:\'telephone\'" value="'+data[i].perTelephone+'" class="text_style easyui-textbox" style="width:120px;"/>'+
	  			 		  '</td>'+
	  			 		  '<td align="center">'+
	  					     '<input type="text" name="customerList['+i+'].proportionProperty" onblur="checkNumber(this)" required="true" data-options="required:true,validType:\'number\'" value="'+data[i].proportionProperty+'" class="text_style easyui-textbox" style="width:40px;"/>'+
	  				 		  '</td>'+
	  			     	'</tr>';
	  			$("#customer_public").append(str);
	  			setRelationCombobox("customer_relation_"+i,"RELATION",data[i].relationVal);
	  			setCombobox("customer_certType_"+i,"CERT_TYPE",data[i].certType);
	  			}
	  		}
		}
	});
	
	// 打开窗口
	$('#dlg_publicMan').dialog('open').dialog('setTitle', "新增关系人");
}

// 新增共同借款人
function addPersonalPublic(){
	var validate = true;
	//校验数据
	$.each($("#customer_public").find("tr:gt(0)"), function(k, v) {
		var flag = false;
		$.each($(v).find(":checkbox:checked"),function(m, n) {
				flag = true;
			});
		if(flag){
			$.each($(v).find("input:text"),function(m, n) {
				var value = $(n).val();
				if(value == ""){
					$.messager.alert("操作提示","请填写正确的数据!","error");
					validate = false;
					return false; 
				}
			});
		}
	});
	if(validate){
		var flag = false;
		//判断是否选中
		$.each($("#customer_public").find("tr:gt(0)"), function(k, v) {
			$.each($(v).find(":checkbox:checked"),function(m, n) {
					flag = true;
				});
		});
		if(!flag){
			$.messager.confirm("操作提示","确认不选择任何的关系人?",
					function(r) {
			 			if(r){
			 				savePublicPerson();
			 			}
					});
		}else{
			savePublicPerson();
		}
	}
}

function savePublicPerson(){
	//将未勾选的数据name属性去掉
		$.each($("#customer_public").find("tr:gt(0)"), function(k, v) {
			var flag = false;
			$.each($(v).find(":checkbox:checked"),function(m, n) {
					flag = true;
				});
			if(!flag){
				$.each($(v).find("input"),function(m, n) {
					$(n).removeAttr("name");
				});
			}
		});
		// 赋值
		$("#publicManForm input[name='pid']").val(acct_id);// 客户ID
		
		$("#publicManForm").form('submit', {
				onSubmit : function() {return $(this).form('validate');},
				success : function(result) {
					var ret = eval("("+result+")");
					if(ret && ret.header["success"]){
						// 操作提示
						$.messager.alert('操作提示',"保存成功！");
						// 关闭窗口
						$('#dlg_publicMan').dialog('close');
						var pids = ret.header["result"];
						// 重新加载共同借款人
						var url = "<%=basePath%>customerController/getNoSpouseListByPid.action?userIds="+pids;
						$('#personal_public').datagrid('options').url = url;
						$('#personal_public').datagrid('reload');
					}else{
						$.messager.alert('操作提示',ret.header["msg"],'error');
					}
				}
			}); 
}

//增加关系人列表
function addPublicPerson(){
	var trIndex=$("#customer_public tr").length -1;
    var str='<tr id="customer_tr'+trIndex+'" name="'+trIndex+'" style="border:1px solid #999;">'+
            '<td>'+
          	'<input type="checkbox" name="customerList['+trIndex+'].pid"  class="isChecked" value="-1"/>'+
          	'<input type="hidden" name="customerList['+trIndex+'].status" value="1"/>'+
          	'<input type="hidden" name="customerList['+trIndex+'].cusAcct.pid" value="'+acct_id+'"/>'+
            '</td>'+
	          '<td align="center">'+'<input name="customerList['+trIndex+'].chinaName" onblur="checkChineseName(this)" required="true" data-options="required:true,validType:\'length[1,20]\'" class="text_style easyui-textbox"  panelHeight="auto" style="width:80px;"/>'+'</td>'+
	 		  '<td align="center">'+
	 		     '<select name="customerList['+trIndex+'].relationType" id="customer_relation_'+trIndex+'" data-options="validType:\'selrequired\'" class="easyui-combobox"  style="width:100px;" />'+
	 		  '</td>	'+
	 		   '<td align="center">'+
	 		  '<select name="customerList['+trIndex+'].certType" id="customer_certType_'+trIndex+'" data-options="validType:\'selrequired\'" class="easyui-combobox"  style="width:100px;" />'+
	 		  '</td>'+
	 		  '<td align="center">'+
	 		     '<input type="text" name="customerList['+trIndex+'].certNumber" onblur="checkCertNumber(this,customer_certType_'+trIndex+')" required="true" data-options="required:true" class="text_style" style="width:140px;"/>'+
	 		  '</td>'+
		 		 '<td align="center">'+
	 		     '<input type="text" name="customerList['+trIndex+'].telephone" onblur="checkPhone(this)" required="true" data-options="required:true,validType:\'telephone\'" class="text_style easyui-textbox" style="width:120px;"/>'+
	 		  '</td>'+
	 		  '<td align="center">'+
			     '<input type="text" name="customerList['+trIndex+'].proportionProperty" onblur="checkNumber(this)" value="0" required="true" data-options="required:true,validType:\'number\'" class="text_style easyui-textbox" style="width:40px;"/>'+
		 		  '</td>'+
	     	'</tr>';
	$("#customer_public").append(str);
	setRelationCombobox("customer_relation_"+trIndex,"RELATION",-1);
	setCombobox("customer_certType_"+trIndex,"CERT_TYPE","13088");
}

function removePublicPerson(){
	var flag=false;
	$.each($("#customer_public").find("tr:gt(0)"), function(k, v) {
		$.each($(v).find(":checkbox:checked"),function(m, n) {
				$(v).remove();
				flag = true;
			});
	});
	
	if(flag==false){
		$.messager.alert('操作提示',"请选择一条数据删除！",'info');
	} 
}

//校验证件类型和证件号码是否存在
function validateCertNumber(){
	var certNumber = $("#cert_Number").val();
	var personId = 0;
	var type=false;
	$.ajax({
		url : '${basePath}customerController/validateCeryNumber.action?certNumber='
			+ certNumber
			+ '&personId='
			+ personId,
		type : 'post',
		cache: false,
		success : function(result) {
			var ret = eval("("+result+")");
			var flags=ret.header["flags"];
			var cusPerBaseDTO = ret.header["cusPerBaseDTO"];
			var blackFlags=ret.header["blackFlags"];
			if(blackFlags >0){
				$.messager.alert('操作提示',"此客户已加入系统黑名单，不允许申请订单！",'error');
				$("#cert_Number").textbox('setValue',"");
				return;
			}
			if(flags>0){
				$("#personInfo input[name='pid']").val(cusPerBaseDTO.pid);
				$("#personInfo input[name='cusAcct.pmUserId']").val(cusPerBaseDTO.cusAcct.pmUserId);
				$("#personInfo input[name='cusAcct.pid']").val(cusPerBaseDTO.cusAcct.pid);
				
				$("#personInfo input[name='cusPerBase.pid']").val(cusPerBaseDTO.cusPerBase.pid);
				$("#personInfo input[name='cusPerBase.cusAcct.pid']").val(cusPerBaseDTO.cusPerBase.cusAcct.pid);
				$("#personInfo input[name='cusPerson.pid']").val(cusPerBaseDTO.cusPerson.pid);
				$("#personInfo input[name='cusPerson.cusAcct.pid']").val(cusPerBaseDTO.cusPerson.cusAcct.pid);
				$("#personInfo input[name='cusPerSocSec.pid']").val(cusPerBaseDTO.cusPerSocSec.pid);
				$("#personInfo input[name='cusPerSocSec.cusPerBase.pid']").val(cusPerBaseDTO.cusPerSocSec.cusPerBase.pid);
				
				$("#personInfo input[name='cusAcct.cusStatus']").val(cusPerBaseDTO.cusAcct.cusStatus);
				$("#personInfo input[name='cusPerSocSec.cusAcct.status']").val(cusPerBaseDTO.cusAcct.status);
				
				$("#chineseName").textbox('setValue',cusPerBaseDTO.cusPerson.chinaName);
				
				$("#cusPersonTelephone").textbox('setValue',cusPerBaseDTO.cusPerson.telephone);
				$("#cusPersonLiveAddr").textbox('setValue',cusPerBaseDTO.cusPerson.liveAddr);
			}else{
				$("#personInfo input[name='pid']").val(0);
				$("#personInfo input[name='cusAcct.pmUserId']").val(${currUser.pid});
				$("#personInfo input[name='cusAcct.pid']").val(0);
				
				$("#personInfo input[name='cusPerBase.pid']").val(0);
				$("#personInfo input[name='cusPerBase.cusAcct.pid']").val(0);
				$("#personInfo input[name='cusPerson.pid']").val(0);
				$("#personInfo input[name='cusPerson.cusAcct.pid']").val(0);
				$("#personInfo input[name='cusPerSocSec.pid']").val(0);
				$("#personInfo input[name='cusPerSocSec.cusPerBase.pid']").val(0);
				$("#personInfo input[name='cusAcct.cusStatus']").val(1);
				$("#personInfo input[name='cusPerSocSec.cusAcct.status']").val(1);
			}
		}
	});
}
//打印
function printAuditorOpinion(){

	var url = "${basePath}beforeLoanController/printAutitorOpinion.action?projectId="+projectId;
	parent.openNewTab(url,"审批意见表打印",true);
}
/**
 * 校验卖方名称与地址
 */
function checkNameOrAddress(id){
	var val = $("#"+id).val();
	if(val != ''){
	if(!val.match("^(?!,)(?!.*?,$)[\u4e00-\u9fa5,a-zA-Z0-9]+$")){
		$.messager.alert('操作提示',"两个以上请用英文逗号隔开！",'error');
		$("#"+id).textbox('setValue',"");
		return false;
	}
	}
}
/**
 * 校验卖方身份证与电话号码
 */
function checkCardOrPhone(id){
	var val = $("#"+id).val();
	if(val != ''){
		if(!val.match("^(?!,)(?!.*?,$)[a-zA-Z0-9,]+$")){
			$.messager.alert('操作提示',"两个以上请用英文逗号隔开！",'error');
			$("#"+id).textbox('setValue',"");
			return false;
		}
	}
}


//中文名失去焦点验证合法性
function checkChinaName(){
	var chinaName = $("#chineseName").val();
	chinaName=chinaName.replace(/[^\u4E00-\u9FA5]/g,'');
	$("#chineseName").val(chinaName);
}
/**
 * 校验买卖方身份证号码
 */
function checkCardNo(id){
	var cardNos = $("#"+id).val();
	if(cardNos.length>0){
		var cardArray = cardNos.split(",");
		for(var i=0;i<cardArray.length;i++){
			var cardNo = cardArray[i]
			if(!IdCardValidate(cardNo)){
				$.messager.alert('操作提示','号码：'+cardNo+'有误，请重新填写！','error');
				$("#"+id).textbox('setValue',"");
				return false;
			}
		}
	}
}
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
	<input type="hidden" id="pid" name="pid" value="-1" />
	<input name="cusType" type="hidden" value="1"/>
	<div title="业务信息" id="tab1">
		<form action="<%=basePath%>beforeLoanController/saveProjectInfo.action" id="personalForm" name="personalForm" method="post">
		<div style="padding: 30px 10px 0 10px;">
		<div class="easyui-panel" title="借款人信息及基础信息填写" data-options="collapsible:true" width="1039px;">
		<%-- begin 个人模版 --%>
		<div id="personal" style="padding:10px 0 15px 0;">
			<input type="hidden" id="personalAbbreviation" name="abbreviation" />
			<input type="hidden" name="cusType" />
			<input type="hidden" name="acctId" />
			<input type="hidden" name="projectSource" value="1"/>
			<input type="hidden" name="userPids" />
			<input type="hidden" name="projectType" value="2"/>
			<input type="hidden" name="pmUserId" value="${ project.pmUserId>0?project.pmUserId:login.pid}"/>
			<input type="hidden" id="productType" name="productType"/>
			<input type="hidden" name="pid" value="${project.pid}" />
			<input type="hidden" name="businessSourceNo" value="${project.businessSourceNo}" id="businessSourceNo"/>
			<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
				<tr>
					<td align="right" width="80" height="28">项目名称：</td>
					<td><input id="projectName" value="${project.projectName}" readonly="readonly" name="projectName" type="text" style="width: 200px;color: red;" class="text_style"> </td>
					<td align="right" width="80" height="28">项目编号：</td>
					<td ><input id="projectNumber" value="${project.projectNumber}" name="projectNumber" type="text" style="width: 200px;color: red;" class="text_style" readonly="readonly" > </td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>产品名称：</td>
					<td>
						<input name="productId" id="productId" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
					</td>
					<td class="label_right"><font color="red">*</font>业务类型：</td>
					<td>
						<input name="businessCategory" id="businessCategory" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
					</td>
					<td class="label_right"><font color="red">*</font>业务来源：</td>
					<td>
						<input name="businessSource" id="businessSource" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
					</td>
					<td colspan="2">
						<div id="bank_source" class="none">
							<select id="bank_source_no" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="partner_source1" class="none">
							<select id="partner_source_no1" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="partner_source" class="none">
							<select id="partner_source_no" class="easyui-combobox" editable="false" style="width:235px;" />
						</div>
						<div id="partner_source2" class="none">
							<select id="partner_source_no2" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="business_source" class="none">
							<select id="business_source_no" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="business_source1" class="none">
							<select id="business_source_no1" class="easyui-combobox" editable="false" style="width:129px;" />
						</div>
						<div id="other" class="none">
							<input class="easyui-textbox" name="myMainText" value="${project.myMainText}" style="width:129px;" panelHeight="auto" data-options="validType:'length[0,50]'"/>
						</div>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>经办人：</td>
					<td>
						<input  class="easyui-textbox" readonly="readonly" data-options="required:true,validType:'length[1,20]'" required="true" name="managers"  value="${project.managers==null?login.realName:project.managers } " />
					</td>
					<td class="label_right"><font color="red">*</font>经办人电话：</td>
					<td>
						<input  class="easyui-textbox" readonly="readonly" data-options="required:true,validType:'telephone'" required="true" name="managersPhone" value="${project.managersPhone==null?login.phone:project.managersPhone }" />
					</td>
					<td class="label_right"><font color="red">*</font>内外单：</td>
					<td class="nwd">
						<select id="innerOrOut" name="innerOrOut" class="easyui-combobox"  data-options="validType:'selrequired'" editable="false" style="width:129px;" />
					</td>
					<td class="label_right"><font color="red">*</font>报单员：</td>
					<td>
						 <input name="declaration" value="${project.declaration}" data-options="required:true,validType:'length[1,20]'" id="declaration"  class="easyui-textbox" panelHeight="auto"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">业务联系人：</td>
					<td>
						 <input name="businessContacts" value="${project.businessContacts}" data-options="validType:'length[0,20]'" id="businessContacts"  class="easyui-textbox" panelHeight="auto"/>
					</td>
					<td class="label_right">联系电话：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'telephone'" name="contactsPhone" value="${project.contactsPhone}" />
					</td>
				</tr>
				
				<%-- <tr>
					<td class="label_right"><font color="red">*</font>计划放款日期：</td>
					<td>
						 <input name="projectForeclosure.receDate" required="true" value="${project.projectForeclosure.receDate }" class="easyui-datebox" editable="false" style="width:129px;"/>
					</td>
				</tr> --%>
			</table>
			<table class="beforeloanTable" width="800">
				<tr>
					<td class="label_right">客户姓名：</td>
					<td>
					<a id='chinaNamema'  onclick='editPerBase()' href='#'  style="color:blue;float: left;width: 145px;padding-left: 5px; ">${cpyName}</a>
					 <input name="chinaName" type="hidden"  class="no_frame text_style"  disabled="true">
					 <a class="easyui-linkbutton personalbtn"  id="readtype1" onclick="selectPersonal()">选择借款人</a>
						<a class="easyui-linkbutton personalbtn"  id="readtype2"  onclick="createPersonal()">创建借款人</a>
					</td>
					<td width="120" align="center"><label>是否为买卖方：</label></td>
					<td width="60"><input name="isSeller" type="radio" <c:if test="${project.isSeller ==1 }">checked</c:if> value="1" onchange="changeEvent()"> 买方</td>
					<td width="60"><input name="isSeller" type="radio" <c:if test="${project.isSeller ==2 }">checked</c:if> value="2" onchange="changeEvent()"> 卖方</td>
				</tr>
				<tr>
				<td class="label_right">客户性别：</td>
					<td><input name="sexName" type="text" class="no_frame text_style" disabled="true"></td>
					<td class="label_right">证件类型：</td>
					<td><input name="certTypeName" type="text" class="no_frame text_style" disabled="true"></td>
				</tr>
				<tr>
					<td class="label_right">证件号码：</td>
					<td><input name="certNumber" type="text" class="no_frame text_style" disabled="true"></td>
					<td class="label_right">手机号码：</td>
					<td><input name="perTelephone" type="text" class="no_frame text_style" disabled="true"></td>
				</tr>
				<tr>
					<td class="label_right">通讯地址：</td>
					<td colspan="3"><input name="liveAddr" type="text" style="width: 400px;" class="no_frame text_style" disabled="true"></td>
				</tr>
			</table>
			
			<div class="fitem" style="margin-left: 40px;">				
				<%-- 共同借款人信息--%>
				<div style="padding-bottom:10px;">
					<label style="width: auto;" id="text_gtjkr">请勾选共同借款人信息（共同借款人来自个人客户的配偶关系人和非配偶关系人）：</label>
				</div>
				<div id="toolbar_gtjkr"  style="border-bottom: 0;">
					<a href="javascript:void(0)" id="gtjkrAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openPersonalPublic()">新增共同借款人</a>
					<!-- <a href="javascript:void(0)" id="gtjkrRemove" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deletePersonalPublic()">删除</a> -->
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
							<th data-options="field:'pid',hidden:true" ></th>
							<th data-options="field:'projectPublicManId',hidden:true" ></th>
							<th data-options="field:'chinaName',width:30" >姓名</th>
							<th data-options="field:'certTypeName',width:30" >证件类型</th>
							<th data-options="field:'certNumber',width:30" >证件号号码</th>
							<th data-options="field:'relationText',width:30" >与客户关系</th>
							<th data-options="field:'perTelephone',width:30" >手机号码</th>
							<th data-options="field:'proportionProperty',width:30" >产权占比</th>
						</tr>
					</thead>
			   	</table>
			</div>
			</div>
	</div>
	
		
	<div  style="padding:10px 0 15px 0;">
		<div class=" easyui-panel" title="物业及买卖双方信息" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<input type="hidden" name="projectProperty.pid" id="propertyId" value="${project.projectProperty.pid }">
			<input type="hidden" name="projectProperty.projectId" value="${project.projectProperty.projectId }">
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>物业名称：</td>
					<td>
						<input name="projectProperty.houseName" data-options="required:true,validType:'length[1,50]'" value="${project.projectProperty.houseName }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>区域：</td>
					<td>
						<select id="address" class="easyui-combobox"  data-options="validType:'selrequired'" name="address" editable="false" style="width:129px;" />
					</td>
					<td class="label_right1"><font color="red">*</font>面积：</td>
					<td>
						<input name="projectProperty.area" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" style="width:119px;" class="easyui-numberbox" min="0" precision="2" maxLength='8' groupSeparator="," value="${project.projectProperty.area }"/>㎡
					</td>
					<td class="label_right"><font color="red">*</font>房产证号：</td>
					<td>
						<input name="projectProperty.housePropertyCard" data-options="required:true,validType:'length[1,50]'" value="${project.projectProperty.housePropertyCard }"  class="easyui-textbox"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>用途：</td>
					<td>
						<input name="projectProperty.purpose" data-options="required:true,validType:'length[1,50]'" value="${project.projectProperty.purpose }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>原价：</td>
					<td>
						<input class="easyui-numberbox" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" name="projectProperty.costMoney" value="${project.projectProperty.costMoney }"/>
					</td>
					<td class="label_right1" id="evaluationPrice_lable">成交价：</td>
					<td>
						 <input name="projectProperty.tranasctionMoney" id="evaluationPrice" value="${project.projectProperty.tranasctionMoney }" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
					</td>
					<td class="label_right">赎楼成数：</td>
					<td>
						 <input name="projectProperty.foreRate" id="foreRate" readonly="readonly" value="${project.projectProperty.foreRate }" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" style="width:119px;"  class="easyui-numberbox"/>%
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>业主姓名：</td>
					<td>
						 <input name="projectProperty.sellerName" id="sellerName" data-options="prompt:'两个以上请用英文逗号隔开！',required:true,validType:'length[1,20]'" value="${project.projectProperty.sellerName }"  class="easyui-textbox"/>
					</td>
					<td class="label_right1"><font color="red">*</font>身份证号：</td>
					<td>
						<input name="projectProperty.sellerCardNo" id="sellerCardNo" data-options="prompt:'两个以上请用英文逗号隔开！',required:true,validType:'length[1,100]'" value="${project.projectProperty.sellerCardNo }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>联系电话：</td>
					<td>
						<input name="projectProperty.sellerPhone" id="sellerPhone" data-options="prompt:'两个以上请用英文逗号隔开！',required:true,validType:'length[1,100]'" value="${project.projectProperty.sellerPhone }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>家庭住址：</td>
					<td>
						 <input name="projectProperty.sellerAddress" id="sellerAddress" data-options="prompt:'两个以上请用英文逗号隔开！',required:true,validType:'length[1,100]'" value="${project.projectProperty.sellerAddress }"  class="easyui-textbox" />
					</td>
				</tr>

				<tr>
					<td class="label_right"><font color="red">*</font>买方名称：</td>
					<td>
						 <input name="projectProperty.buyerName" id="buyerName" data-options="prompt:'两个以上请用英文逗号隔开！',validType:'length[0,20]'" value="${project.projectProperty.buyerName }"  class="easyui-textbox"/>
					</td>
					<td class="label_right1"><font color="red">*</font>身份证号：</td>
					<td>
						<input name="projectProperty.buyerCardNo" id="buyerCardNo" data-options="prompt:'两个以上请用英文逗号隔开！',validType:'length[0,50]'" value="${project.projectProperty.buyerCardNo }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>联系电话：</td>
					<td>
						<input name="projectProperty.buyerPhone" id="buyerPhone" data-options="prompt:'两个以上请用英文逗号隔开！',validType:'length[1,100]'" value="${project.projectProperty.buyerPhone }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>家庭地址：</td>
					<td>
						 <input name="projectProperty.buyerAddress" id="buyerAddress" data-options="prompt:'两个以上请用英文逗号隔开！',validType:'length[0,100]'" value="${project.projectProperty.buyerAddress }"  class="easyui-textbox"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
		</div>
	
	<!-- 赎楼信息开始 -->
	<div  id="foreclosureInfo">
	<div style="padding:10px 0 15px 0;">
		<div class=" easyui-panel" title="赎楼" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<input type="hidden" name="projectForeclosure.pid" id="foreclosureId" value="${project.projectForeclosure.pid }">
			<input type="hidden" name="projectForeclosure.projectId" value="${project.projectForeclosure.projectId }">
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>原贷款银行：</td>
					<td>
						<select id="oldLoanBank" class="easyui-combobox"  data-options="validType:'selrequired'" name="projectForeclosure.oldLoanBank" editable="false" style="width:129px;" />
					</td>
					<td colspan="2">
						<select id="oldLoanBankBranch" class="easyui-combobox"  data-options="validType:'selrequired'" name="projectForeclosure.oldLoanBankBranch" editable="false" style="width:200px;" />
					</td>
					<td class="label_right1"><font color="red">*</font>原贷款金额：</td>
					<td>
						<input class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" value="${project.projectForeclosure.oldLoanMoney }" name="projectForeclosure.oldLoanMoney" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>原贷款欠款金额：</td>
					<td>
						 <input name="projectForeclosure.oldOwedAmount" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" value="${project.projectForeclosure.oldOwedAmount }" style="width:129px;"/>
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
						 <input name="projectForeclosure.oldLoanPhone" data-options="validType:'telephone'" value="${project.projectForeclosure.oldLoanPhone }"  class="easyui-textbox" style="width:129px;"/>
					</td>
				</tr>
				
				<tr style="padding-top:10px;">
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
						 <input name="projectForeclosure.thirdBorrowerPhone" data-options="validType:'telephone'" value="${project.projectForeclosure.thirdBorrowerPhone }"  class="easyui-textbox" style="width:129px;"/>
					</td>
					<td class="label_right">地址：</td>
					<td>
						<input class="easyui-textbox" name="projectForeclosure.thirdBorrowerAddress" data-options="validType:'length[0,100]'" value="${project.projectForeclosure.thirdBorrowerAddress }" style="width:129px;"/>
					</td>
				</tr>
				
				<tr style="padding-top:10px;">
					<td class="label_right"><font color="red">*</font>新贷款银行：</td>
					<td>
						<select id="newLoanBank" class="easyui-combobox"  data-options="validType:'selrequired'" name="projectForeclosure.newLoanBank" editable="false" style="width:129px;" />
					</td>
					<td colspan="2">
						<select id="newLoanBankBranch" class="easyui-combobox"  data-options="validType:'selrequired'" name="projectForeclosure.newLoanBankBranch" editable="false" style="width:200px;" />
					</td>
					<td class="label_right"><font color="red">*</font>新贷款金额：</td>
					<td>
						<input class="easyui-numberbox" id="newLoanMoney" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.newLoanMoney" value="${project.projectForeclosure.newLoanMoney }" style="width:129px;"/>
					</td>
					<td class="label_right1">银行联系人：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[0,20]'" name="projectForeclosure.newLoanPerson" value="${project.projectForeclosure.newLoanPerson }" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">联系电话：</td>
					<td>
						 <input name="projectForeclosure.newLoanPhone" data-options="validType:'telephone'" value="${project.projectForeclosure.newLoanPhone }"  class="easyui-textbox" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>回款户名：</td>
					<td>
						<input  class="easyui-textbox" data-options="required:true,validType:'length[1,20]'" name="projectForeclosure.paymentName" value="${project.projectForeclosure.paymentName }" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>回款账号：</td>
					<td colspan="3">
						<input  class="easyui-textbox" data-options="required:true,validType:'length[1,50]'" name="projectForeclosure.paymentAccount" value="${project.projectForeclosure.paymentAccount }" style="width:300px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>监管银行：</td>
					<td>
						<select id="superviseDepartment" class="easyui-combobox" name="projectForeclosure.superviseDepartment" editable="false" style="width:129px;" />
					</td>
					<td colspan="2">
						<select id="superviseDepartmentBranch" class="easyui-combobox" name="projectForeclosure.superviseDepartmentBranch" editable="false" style="width:200px;" />
					</td>
					<td class="label_right"><font color="red">*</font>资金监管金额：</td>
					<td>
						<input class="easyui-numberbox" id="fundsMoney" data-options="prompt:'非交易类型不用填！',min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.fundsMoney" value="${project.projectForeclosure.fundsMoney }" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>监管账号：</td>
					<td colspan="3">
						<input  class="easyui-textbox" data-options="prompt:'非交易类型不用填！',validType:'length[0,50]'" id="superviseAccount" name="projectForeclosure.superviseAccount" value="${project.projectForeclosure.superviseAccount }" style="width:300px;"/>
					</td>
					<td class="label_right">公积金银行：</td>
					<td>
						<select id="accumulationFundBank" class="easyui-combobox" name="projectForeclosure.accumulationFundBank" editable="false" style="width:129px;" />
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
						<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="projectForeclosure.paymentType" style="width:129px;">
							<option value="-1">--请选择--</option>
							<option value="1" <c:if test="${project.projectForeclosure.paymentType==1 }">selected </c:if>>按揭</option>
							<option value="2" <c:if test="${project.projectForeclosure.paymentType==2 }">selected </c:if>>组合贷</option>
							<option value="3" <c:if test="${project.projectForeclosure.paymentType==3 }">selected </c:if>>公积金贷</option>
							<option value="4" <c:if test="${project.projectForeclosure.paymentType==4 }">selected </c:if>>一次性付款</option>
						</select>
					</td>
					<td class="label_right"><font color="red">*</font>委托公证日期：</td>
					<td>
						 <input name="projectForeclosure.notarizationDate" required="true" value="${project.projectForeclosure.notarizationDate }" class="easyui-datebox" editable="false" style="width:129px;"/>
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
					<td class="label_right"><font color="red">*</font>借款金额：</td>
					<td>
						<input name="projectGuarantee.loanMoney" required="true" id="loanMoney" value="${project.projectGuarantee.loanMoney }" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>借款天数：</td>
					<td>
						 <input name="projectForeclosure.loanDays" required="true" data-options="min:1,max:9999,required:true,validType:'integer'" value="${project.projectForeclosure.loanDays }" class="easyui-numberbox" style="width:129px;"/>
					</td>
					<td class="label_right1"><font color="red">*</font>费率：</td>
					<td>
						<input name="projectGuarantee.feeRate" style="width:129px;" required="true" value="${project.projectGuarantee.feeRate }" class="easyui-numberbox" data-options="required:true,min:0,max:100,precision:2,groupSeparator:','"/>%
					</td> 
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>咨询费：</td>
					<td>
						<input name="projectGuarantee.guaranteeFee" style="width:129px;" required="true" value="${project.projectGuarantee.guaranteeFee }" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td> 
					<td class="label_right1"><font color="red">*</font>手续费：</td>
					<td>
						<input name="projectGuarantee.poundage" style="width:129px;" id="poundage" value="${project.projectGuarantee.poundage }" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
					<td class="label_right">手续费补贴：</td>
					<td>
						<input name="projectGuarantee.chargesSubsidized" style="width:129px;" id="chargesSubsidized" value="${project.projectGuarantee.chargesSubsidized }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
	</div>
	</div>
	<!-- 赎楼信息结束 -->
	<!-- 特殊情况说明开始 -->
	<div id="foreclosure">
		<div class="control_disbase" style="padding:0 0 15px 0;width:1039px">
		<div class=" easyui-panel" title="尽职报告调查" data-options="collapsible:true">	
			<div style="padding-left: 10px;padding-top:10px;height: auto;line-height: 35px;" >
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><span>特殊情况说明：</span></label>
             	<input name="specialDesc" id="specialDesc" class="easyui-textbox" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,1000]'">
			</div>
			</div>
		</div>
		<div style="padding-top: 10px;">
			<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveProjectInfo()">保存</a>
		</div>
		</div>
		</div>
	<!-- 特殊情况说明结束 -->
	</form>
	<!-- 审查员意见开始 -->
	<div>
		<div class="control_disbase" style="padding:0 0 15px 0;width:1039px">
		<div class=" easyui-panel" title="审查员审批意见" data-options="collapsible:true">	
			<form action="<%=basePath%>secondBeforeLoanController/updateAuditorOpinion.action" id="auditorOpinionInfo" name="auditorOpinionInfo" method="post" >
			<div style="padding-left: 10px;padding-top:10px;height: auto;line-height: 35px;" >
			<input type="hidden" name="pid" value="${project.pid}" />
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><span>意见与说明：</span></label>
             	<input name="auditorOpinion" id="auditorOpinion" value="${project.auditorOpinion}" class="easyui-textbox" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,1000]'">
			</div>
			
			</div>
			</form>
		</div>
		<div style="padding-top: 10px;">
		<shiro:hasAnyRoles name="ADD_UPDATE_AUDITOR,ALL_BUSINESS">
			<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveAuditorOpinion()">保存</a>
			<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" id="printAuditorOpinion" iconCls="icon-print" onclick="printAuditorOpinion()">打印</a>
		</shiro:hasAnyRoles>
		</div>
		</div>
		</div>
	<!-- 审查员意见结束 -->
	</div>
	<!-- begin 新增共同借款人 -->
		<div id="dlg-buttons_publicMan">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addPublicPerson()">增加关系人</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removePublicPerson()">删除关系人</a>		
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addPersonalPublic()">选择</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_publicMan').dialog('close')">关闭</a>
		</div>
		<div id="dlg_publicMan" class="easyui-dialog" fitColumns="true"  title="关系人"
     		style="width:696px;" data-options="modal:true" buttons="#dlg-buttons_publicMan" closed="true" >
			<form method="post" action="<%=basePath%>customerController/saveSpouseList.action" id="publicManForm" name="publicManForm"  >
				<table class="cus_table" id="customer_public">
		       	  <tr style="border:1px solid #999;">
		            <td width="40px" align="center">选择</td>
		            <td align="center"><font color="red">*</font>客户姓名</td>
		            <td align="center"><font color="red">*</font>关系</td>
		            <td align="center"><font color="red">*</font>证件类型</td>
		            <td align="center"><font color="red">*</font>证件号码</td>
		            <td align="center"><font color="red">*</font>手机号码</td>
		            <td align="center">产权占比%</td>
		      	 </tr>
		      	 </table>
			<input type="hidden" name="pid" >
		</form>
		</div>
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
		<!-- 个人客户选取结束 -->
		<!-- 新增借款人开始 -->
		<div id="add_person_info">		
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addPersonal()">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#add_person').dialog('close')">关闭</a>
		</div>
		<div id="add_person" class="easyui-dialog" fitColumns="true"  title="新增借款人"
		     style="width:850px;top:100px;padding:10px;" buttons="#add_person_info" closed="true" >
			<form id="personInfo" action="<%=basePath%>customerController/saveCusPer.action" method="POST">
				<table class="beforeloanTable">
					<input type="hidden" name="pid"/>
					<input type="hidden" name="cusAcct.cusStatus" value="1"/>
					<input type="hidden" name="cusAcct.status" value="1"/>
					<input type="hidden" name="cusAcct.cusType" value="1"/>
					<input type="hidden" name="cusAcct.pmUserId" value="${currUser.pid}"/>
					<input type="hidden"  name="cusPerBase.status" value="1"/>
					<input type="hidden"  name="cusPerson.status" value="1"/>
					<input type="hidden"  name="cusPerson.relationType" value="1"/>
					<input type="hidden"  name="cusPerSocSec.status" value="1"/>
					<input name="cusPerson.sex" type="hidden" id="sex_text"/>
					<input name="cusPerson.age" type="hidden" id ="age"/>
					<input name="cusPerson.birthDate" type="hidden" id ="birth_Date"/>

					<input type="hidden" name="cusAcct.pid" />
					<input type="hidden" name="cusPerBase.pid"/>
        			<input type="hidden" name="cusPerBase.cusAcct.pid"/>
					<input type="hidden" name="cusPerson.pid"/>
      				<input type="hidden" name="cusPerson.cusAcct.pid"/>
					<input type="hidden" name="cusPerSocSec.pid"/>
       				<input type="hidden" name="cusPerSocSec.cusPerBase.pid"/>
					
					<tr>
					 <td class="label_right1"><span class="cus_red">*</span>客户名称：</td>
					 <td><input type="text" class="text_style easyui-textbox" data-options="required:true,validType:'length[1,20]'"  onblur="checkChinaName();" id="chineseName" name="cusPerson.chinaName"/></td>
					<td class="label_right1"><span class="cus_red">*</span>性别：</td>
				    <td>
					 <select id="add_sex" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" style="width:150px;" />
				   </td>
				  </tr>
				  <tr>
				  	<td class="label_right1"><span class="cus_red">*</span>证件类型：</td>
					 <td>
						<select id="cert_type" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" name="cusPerson.certType"  style="width:150px;"   />
					 </td>
				  	<td class="label_right1"><span class="cus_red">*</span>证件号码：</td>
					 <td><input type="text" id="cert_Number" class="text_style easyui-textbox" name="cusPerson.certNumber" data-options="required:true,validType:'length[1,18]'" onblur="getAge();"/></td>
				  </tr>
				  <tr>
				  	<td class="label_right1" ><span class="cus_red">*</span>手机号码：</td>
				     <td><input type="text" class="text_style easyui-textbox" data-options="required:true,validType:'telephone'" id="cusPersonTelephone" name="cusPerson.telephone"/></td>
				   <td class="label_right1"><span class="cus_red">*</span>居住地址：</td>
					<td colspan="2"><input type="text" style="width: 425px;" class="text_style easyui-textbox" id="cusPersonLiveAddr" name="cusPerson.liveAddr" data-options="required:true,validType:'length[1,50]'"/>
				  </tr>
				 </table>
			</form>	  
		</div>
		<!-- 新增借款人结束 -->
	</div>
	<div title="赎楼清单" id="tab6">
		
	</div>
	<div title="申请办理" id="tab7">
		
	</div>
	<div title="合同" id="tab3">
		
	</div>

	<div title="借据及还款计划表" id="tab4">
		
	</div>

	<div title="资料上传" id="tab5">
		
	</div>
</div>
<div class="panel-body1 pt10" id="loanworkflowWrapDivPanel" style="padding: 10px 10px 0 10px;width: 1039px;">
	<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
		<div style="padding:5px">
			<%@ include file="../common/loanWorkflow.jsp"%> 
			<%@ include file="../common/task_hi_list.jsp"%>
		</div>
	</div>
</div>
</div>
</div>
</body>
</html>