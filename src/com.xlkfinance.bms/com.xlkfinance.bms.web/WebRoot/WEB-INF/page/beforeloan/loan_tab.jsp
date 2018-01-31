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
function selectfirst() {$(this).combobox('select', $(this).combobox('getData')[0].pid);}
//归档类型
var Iam = "BEFORE";
/**************工作流字段 begin******************/

var WORKFLOW_ID = "creditLoanRequestProcess";//默认是深圳审批工作流ID
workflowTaskDefKey = "task_LoanRequest";//当前处理节点ID
nextRoleCode = "RISK_ONE";//下一处理角色

var projectType = '${project.projectType}';
if(projectType == 6){
	WORKFLOW_ID = "businessApplyRequestProcess";
	workflowTaskDefKey = "task_BusinessRequest";//当前处理节点ID
}

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
// 页面初始化开始
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
							<%-- // 根据项目ID查询授信ID的URL
							var urlCredit = "<%=basePath%>secondBeforeLoanController/getCreditIdByProjectId.action?projectId="+projectId;
							$.post(urlCredit,
								function(ret){
									creditId = ret.header["msg"];
								}, "json"); --%>
							// 标志为是流程
								bianzhi = 1;
						}
					}else if(url.indexOf("projectId") > 0){
						// 设置项目ID
						projectId = arr[2];
						<%-- // 根据项目ID查询授信ID的URL
						var urlCredit = "<%=basePath%>secondBeforeLoanController/getCreditIdByProjectId.action?projectId="+projectId;
						$.post(urlCredit,
							function(ret){
								creditId = ret.header["msg"];
							}, "json"); --%>
						// 标志为是编辑
						bianzhi = 2;
					}
				}
			}
		}
	}
	
	//初始化合作机构下拉框
	searchPartnerSource('${project.businessSourceNo}');
	setCombobox("businessCategory","BUSINESS_CATEGORY","${project.businessCategory}");
	$("#businessCategory").combobox({  
        onChange:function(){  
        	var businessCategory = $("#businessCategory").combobox('getValue');
        		if(businessCategory == 13756){//非交易
        			$("#fundsMoney").numberbox("disable");
        			$("#fundsMoney").numberbox('setValue', 0);
        		
        			$("#superviseDepartment").combobox("disable");
        			$("#superviseDepartment").combobox('setValue', "");
        			
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
        			
        			$("#supersionReceBank").textbox("disable");
        			$("#supersionReceBank").textbox('setValue', "");
        			$("#supersionReceName").textbox("disable");
        			$("#supersionReceName").textbox('setValue', "");
        			
        		}
        		if(businessCategory == 13755){
        			$("#fundsMoney").numberbox("enable");
        			$("#superviseDepartment").combobox("enable");
        			$("#buyerName").textbox("enable");
        			$("#buyerCardNo").textbox("enable");
        			$("#buyerPhone").textbox("enable");
        			
        			$("#buyerAddress").textbox("enable");
        			$("#superviseAccount").textbox("enable");
        			$("#supersionReceBank").textbox("enable");
        			$("#supersionReceName").textbox("enable");
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
            			    var productNumber = data.productNumber;
            	    		$("#productType").val(data.productType);
            		    	productType = data.productType;
            		    	$("#personalForm input[name='productType']").val(productType);

        	        		$('#foreclosureInfo').addClass('active');
        	        		$('#projectTypeEdsq').prop('disabled', true); 
        	        		$('#projectTypeDksq').attr("checked",true);
        	        		$('#productNumber').combobox('setValue',productNumber);
        	        		//交易有赎楼提放贷和非交易有赎楼提放贷提放贷显示
        	        		if(productNumber == 'JYYSLTFD' || productNumber == 'FJYYSLTFD'){
							//alert("成功******"); foreclosure_Money1 turnover_Money
							    $("#oldFitem").show();
        	        			$("#oldLoanMoneyInfo").show();
        	        			$("#oldLoanMoneyInfo1").show();
        	        			$("#foreclosure_Money1").show();
        	        			$("#foreclosure_Money").show();
        	        			$("#turnover_Money").show();
        	        			$("#turnover_Money1").show();
        	        			$("#turnoverCapitalBank1").show();
        	        			$("#turnoverCapitalBank").show();
        	        			$("#turnoverCapitalName1").show();
        	        			$("#turnoverCapitalName").show();
        	        			$("#turnoverCapitalAccount1").show();
        	        			$("#turnoverCapitalAccount").show();
								$("#loanMoney").numberbox("disable");
								if(productId <=0){
									$("#loanMoney").numberbox('setValue','0.00')
									$("#turnoverMoney").numberbox('setValue','0.00')
									$("#foreclosureMoney").numberbox('setValue','0.00')
								}
								//交易无赎楼提放贷和非交易无赎楼提放贷提放贷时屏蔽原贷款信息
        	        		}else if(productNumber == 'JYWSLTFD' || productNumber == 'FJYWSLTFD'){
        	        			$("#oldFitem").hide();
        	        			$("#oldLoanMoneyInfo").hide();
        	        			$("#oldLoanMoneyInfo1").hide();
        	        			$("#foreclosure_Money1").hide();
        	        			$("#foreclosure_Money").hide();
        	        			$("#turnover_Money").hide();
        	        			$("#turnover_Money1").hide();
        	        			$("#turnoverCapitalBank1").hide();
        	        			$("#turnoverCapitalBank").hide();
        	        			$("#turnoverCapitalName1").hide();
        	        			$("#turnoverCapitalName").hide();
        	        			$("#turnoverCapitalAccount1").hide();
        	        			$("#turnoverCapitalAccount").hide();
        	        			$("#loanMoney").numberbox("enable");
        	        		}else{
        	        			$("#foreclosure_Money1").hide();
        	        			$("#foreclosure_Money").hide();
        	        			$("#turnover_Money").hide();
        	        			$("#turnover_Money1").hide();
        	        			$("#turnoverCapitalBank1").hide();
        	        			$("#turnoverCapitalBank").hide();
        	        			$("#turnoverCapitalName1").hide();
        	        			$("#turnoverCapitalName").hide();
        	        			$("#turnoverCapitalAccount1").hide();
        	        			$("#turnoverCapitalAccount").hide();
        	        			$("#loanMoney").numberbox("enable");
								$("#turnoverMoney").numberbox('setValue','0.00')
								$("#foreclosureMoney").numberbox('setValue','0.00')
								
        	        			$("#oldFitem").show();
        	        			$("#oldLoanMoneyInfo").show();
        	        			$("#oldLoanMoneyInfo1").show();
        	        		}
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
	    		var productNumber = "${productNumber}";
	    		$('#productId').combobox('setValue',productId);
	    		$('#productNumber').combobox('setValue',productNumber);
	    	}else{
	    		$('#productId').combobox('setValue','--请选择--');
	    	}
        } 
	});
	setCombobox3("innerOrOut","INNER_OR_OUT","${project.innerOrOut}");
	
	//监管单位初始化
	var superviseVal= "${project.projectForeclosure.superviseDepartment}";
	setBankCombobox("superviseDepartment",0,"${project.projectForeclosure.superviseDepartment}");
	
	//新旧按揭银行以及公积金贷款银行
	var newBank = "${project.projectForeclosure.newLoanBank}";
	setBankCombobox("newLoanBank",0,"${project.projectForeclosure.newLoanBank}");
	
	setBankCombobox("accumulationFundBank",0,"${project.projectForeclosure.accumulationFundBank}");
	
	setCombobox("businessSource","BUSINESS_SOURCE","${project.businessSource}");
		$("#cert_Number").textbox({
 		    "onChange":function(){
 		    	getAge();
 		    }
 		  });
		
		//业务来源下拉框初始化
		setCombobox("bank_source_no","BANK_NAME","-1");
		setCombobox("business_source_no","INTERMEDIARY","-1");
		var businessSourceStr = '${project.businessSourceStr}';
	 	if(businessSourceStr){
			if(businessSourceStr == "银行"){
				$("#bank_source").show();
				$("#business_source").hide();
				$("#partner_source").hide();
    			$("#city_source").hide();
				$("#other").hide();
				setCombobox("bank_source_no","BANK_NAME","${project.businessSourceNo}");
			}else if(businessSourceStr == "中介"){
				$("#bank_source").hide();
				$("#business_source").show();
				$("#partner_source").hide();
    			$("#city_source").hide();
				$("#other").hide();
				setCombobox("business_source_no","INTERMEDIARY","${project.businessSourceNo}");
			}else if(businessSourceStr == "合作机构"){
				$("#bank_source").hide();
				$("#business_source").hide();
				$("#partner_source").show();
				$("#city_source").show();
				$("#other").hide();
				var orgId = "${project.businessSourceNo}";
				if(orgId != null && orgId >0){
					setApplyCityCombobox("city_source_no",orgId,"${project.areaCode}");
				}
			}else if(businessSourceStr == "其他"){
				$("#bank_source").hide();
				$("#business_source").hide();
				$("#partner_source").hide();
    			$("#city_source").hide();
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
	    			$("#city_source").hide();
	    			$("#other").hide();
	    			$("#innerOrOut").combobox("setValue",2);
	    		}else if(businessSource == "中介"){
	    			$("#bank_source").hide();
	    			$("#business_source").show();
	    			$("#partner_source").hide();
	    			$("#city_source").hide();
	    			$("#other").hide();
	    		}else if(businessSource == "合作机构"){
	    			$("#bank_source").hide();
	    			$("#business_source").hide();
	    			$("#partner_source").show();
	    			$("#city_source").show();
	    			$("#other").hide();
	    			$("#innerOrOut").combobox("setValue",2);
	    		}else if(businessSource == "其他"){
	    			$("#bank_source").hide();
	    			$("#business_source").hide();
	    			$("#partner_source").hide();
	    			$("#city_source").hide();
	    			$("#other").show();
	    			$("#innerOrOut").combobox("setValue",2);
	    		}else{
	    			$("#bank_source").hide();
	    			$("#business_source").hide();
	    			$("#partner_source").hide();
	    			$("#city_source").hide();
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
	
	$("#loanMoney").numberbox({
		    "onChange":function(){
		    	getGuaranteeFee();
		    	changForeRate();
		    }
	});
	
	$("#loanDays").numberbox({
	    "onChange":function(){
	    	getGuaranteeFee();
	    }
	  });
	
	$("#feeRate").numberbox({
	    "onChange":function(){
	    	getGuaranteeFee();
	    }
	  });
	
	$("#evaluationPrice").numberbox({
		    "onChange":function(){
		    	changForeRate();
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
	
	$('#turnoverMoney').numberbox({
		onChange: function(value){
			addMoney();
		  }
		});
	$('#foreclosureMoney').numberbox({
		onChange: function(value){
			addMoney();
		  }
		});
	
	
	//物业信息加载成功后获取总评估价以及总成交价
	$("#house_info").datagrid({
		onLoadSuccess : function(data){
			var evaluationPrice = 0.0;//评估价
			var tranasctionMoney = 0.0;//成交价
			var evaluationNet = 0.0;//评估净值
			var rows = $('#house_info').datagrid('getRows');
			// 物业信息
			if(rows.length > 0){
				for(var i=0;i<rows.length;i++){
			  		var row = rows[i];
			  		evaluationPrice += row.evaluationPrice;
			  		tranasctionMoney += row.tranasctionMoney;
			  		evaluationNet += row.evaluationNet;
			  	}
			}
			$("#tranasctionMoney").numberbox('setValue',tranasctionMoney);//成交价
			$("#evaluationPrice").numberbox('setValue',evaluationPrice);// 评估价
			$("#evaluationNet").numberbox('setValue',evaluationNet);//评估净值
		}
	});
	
	//原贷款信息加载完毕后执行
	$("#originalLoan_info").datagrid({
		onLoadSuccess : function(data){
			var oldLoanMoney = 0.0;//原贷款借款金额
			var oldOwedAmount = 0.0;//原贷款欠款金额
			var rows = $('#originalLoan_info').datagrid('getRows');
			// 物业信息
			if(rows.length > 0){
				for(var i=0;i<rows.length;i++){
			  		var row = rows[i];
			  		oldLoanMoney += row.oldLoanMoney;
			  		oldOwedAmount += row.oldOwedAmount;
			  	}
			}
			$("#oldLoanMoney1").numberbox('setValue',oldLoanMoney);//原贷款借款总金额
			$("#oldOwedAmount1").numberbox('setValue',oldOwedAmount);// 原贷款欠款总金额
		}
	});
	
	//经办人下拉框
	var pmUserId = "${project.pmUserId}";
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
			//ERP后台录入的订单所属城市根据经办人来进行判断
			if(projectType == 2){
		        setCity(pmUserId);
			}
		},
		onSelect: function(rec){
			pmUserId = $('#pmUserId').combobox('getValue');
			//ERP后台录入的订单所属城市根据经办人来进行判断
			if(projectType == 2){
		        setCity(pmUserId);
			}
		}
	});  
	
	/******************流程初始化客户基础信息 begin *********************/
	$("#personalSave").linkbutton("enable");//设置保存按钮可用
	if(projectId!=-1){
		var url="";
		$("#readtype1").linkbutton("disable");
		$("#readtype2").linkbutton("disable");
		$("#text_gtjkr").text("已勾选共同借款人信息（共同借款人来自个人客户的配偶关系人和非配偶关系人）:");
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
	        		var url = "<%=basePath%>customerController/getNoSpouseLists.action?projectId="+projectId;
	        		$('#personal_public').datagrid('options').url = url;
	        		$('#personal_public').datagrid('reload');
	        		$('#personal').addClass('active');
	        		$('#personal').removeClass('none');
	        		// 关闭个人客户窗口
	        		$('#dlg_personal').dialog('close');
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
			  			surPid = data.pid;
			  		}
		  		}
			}
		});
		
		//获取物业信息
		var url = "<%=basePath%>beforeLoanController/getHouseByProjectId.action?projectId="+projectId;
		$('#house_info').datagrid('options').url = url;
		$('#house_info').datagrid('reload');
		//获取原贷款信息
		var url = "<%=basePath%>beforeLoanController/getOriginalLoanByProjectId.action?projectId="+projectId;
		$('#originalLoan_info').datagrid('options').url = url;
		$('#originalLoan_info').datagrid('reload');
		
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
	  			p.panel('refresh','<%=basePath%>beforeLoanController/navigationDatum.action?foreStatus='+foreStatus);
	  		}// 5.赎楼资料清单
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
					$('#projectInfoForm').form('reset');
					$('#projectInfoForm').form('load', data);
			  	}, "json");
	}
	
	//初始化绑定房产估价自动填充
	initHousePriceAutocomp();
	
});
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
	
 	/* $("#gtjkrAdd").linkbutton("enable");
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

/**
 * 保存项目信息
 */
function saveProjectInfo(){
	
	var pmUserId = $("#pmUserId").combobox("getValue");
	if(pmUserId == undefined ||pmUserId == "" || pmUserId<=0){
		$.messager.alert("操作提示","经办人不能为空!","error");
		return;
	}
	
	acct_id = $("#personalForm input[name='acctId']").val();
	if(acct_id <=0){
		$.messager.alert("操作提示","借款人不能为空!","error");
		return;
	}
	
	var productId = $("#productId").combobox("getValue");
	if(productId <=0){
		$.messager.alert("操作提示","请选择产品!","error");
		return;
	}
	
	var chargeType = $("#charge_type").combobox("getValue");
	if(chargeType <=0){
		$.messager.alert("操作提示","请选择收费类型!","error");
		return;
	}
	
	var loanMoney = $("#loanMoney").numberbox('getValue');
	if(loanMoney <=0){
		$.messager.alert('操作提示',"借款金额必须大于0!",'error');	
		return;
	}
	
	var loanDays = $("#loanDays").numberbox('getValue');
	if(loanDays <=0){
		$.messager.alert('操作提示',"借款天数必须大于0!",'error');	
		return;
	}
	var businessCategory = $("#businessCategory").combobox('getValue');
	if(businessCategory == -1){
		$.messager.alert("操作提示","业务类型不能为空!","error");
		return;
	}
	
	var newLoanMoney = $("#newLoanMoney").numberbox("getValue");
	var fundsMoney = $("#fundsMoney").numberbox('getValue');//资金监管金额
	var downPayment = $("#downPayment").numberbox('getValue');//首付款金额
	if(newLoanMoney<0 || (loanMoney - newLoanMoney-fundsMoney-downPayment)>0){
		$.messager.alert('操作提示',"新贷款金额+监管金额+首付款金额≥借款金额！",'error');	
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
	
	var rows = $('#house_info').datagrid('getRows');
	// 物业信息
	var houseIds = "";
	var houseName ="";
	var housePropertyCard = "";//房产证号
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		houseIds += row.houseId+",";
	  		houseName += row.houseName+",";
	  		housePropertyCard += row.housePropertyCard+",";
	  	}
		houseIds = houseIds.substring(0, houseIds.length-1);
		houseName = houseName.substring(0, houseName.length-1);
		housePropertyCard = housePropertyCard.substring(0, housePropertyCard.length-1);
		$("#personalForm input[name='houseIds']").val(houseIds);
		$("#personalForm input[name='projectProperty.houseName']").val(houseName);
		$("#personalForm input[name='projectProperty.housePropertyCard']").val(housePropertyCard);
	}
	
	var rows = $('#originalLoan_info').datagrid('getRows');
	// 原贷款信息
	var originalLoanIds = "";
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		originalLoanIds += row.originalLoanId+",";
	  	}
		originalLoanIds = originalLoanIds.substring(0, originalLoanIds.length-1);
		$("#personalForm input[name='originalLoanIds']").val(originalLoanIds);
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
		if(partnerSourceNo == null || partnerSourceNo == '' || partnerSourceNo == -1){
			$.messager.alert("操作提示","请选择具体的合作机构!","error");
			return;
		}
		$("#businessSourceNo").val(partnerSourceNo);
	}
	
	if(businessSource != "合作机构" && businessSource != "银行" && businessSource != "中介"){
		$("#businessSourceNo").val(0);
	}
	//经办人姓名
	var managers = $("#pmUserId").combobox('getText');
	$("#personalForm input[name='managers']").val(managers);
	//$("#personalForm input[name='pid']").val(projectId);
	$("#personalForm").form('submit',{
		onSubmit : function() {},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$("#gtjkrAdd").linkbutton("enable");
				$("#gtjkrRemove").linkbutton("enable");
				// 设置项目ID
				projectId = ret.header["msg"];
				$("#personalForm input[name='pid']").val(projectId);
				// 设置授信ID
				creditId = ret.header["creditId"];
				
				$("#personalForm input[name='projectForeclosure.projectId']").val(projectId);
				$("#personalForm input[name='projectGuarantee.projectId']").val(projectId);
				$("#personalForm input[name='projectProperty.projectId']").val(projectId);
				
				$("#projectName").val(ret.header["projectName"]);
				$("#projectNumber").val(ret.header["projectNumber"]);

				$("#personalForm input[name='projectGuarantee.pid']").val(ret.header["guaranteeId"]);
				$("#personalForm input[name='projectForeclosure.pid']").val(ret.header["foreclosureId"]);
				$("#personalForm input[name='projectProperty.pid']").val(ret.header["propertyId"]);
				// 提示语句
				$.messager.alert('操作提示','保存信息成功','info');
				$("input[name='cusType']").attr('disabled','true');
				$("#readtype1").linkbutton("disable");
				$("#readtype2").linkbutton("disable");
				$("#text_gtjkr").text("已勾选共同借款人信息（共同借款人来自个人客户的配偶关系人和非配偶关系人）:");
				var url = "<%=basePath%>customerController/getNoSpouseLists.action?projectId="+projectId;
        		$('#personal_public').datagrid('options').url = url;
        		$('#personal_public').datagrid('reload');
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
	$('#dlg_publicMan').dialog('open').dialog('setTitle', "选择共同借款人");
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

function editPerBase() { 
	parent.openNewTab('${basePath}customerController/editPerBase.action?acctId='
	 		+ acct_id+'&currUserPid='+${currUser.pid}+'&flag='+2+'&isLook='+3,"个人客户详细信息",true);
}

/**
 * 校验项目信息
 */
function checkProjectInfo(){
	var pmUserId = $("#pmUserId").combobox("getValue");
	if(pmUserId == undefined ||pmUserId == "" || pmUserId<=0){
		$.messager.alert("操作提示","经办人不能为空!","error");
		return;
	}
	
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
		var partnerSourceNo =  $("#partner_source_no  option:selected").val();
		if(partnerSourceNo == null || partnerSourceNo == '' || partnerSourceNo == -1){
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
	var downPayment = $("#downPayment").numberbox('getValue');//首付款金额
	if(newLoanMoney<0 || (loanMoney - newLoanMoney-fundsMoney-downPayment)>0){
		$.messager.alert('操作提示',"新贷款金额+监管金额+首付款金额≥借款金额！",'error');	
		return;
	}
	
	var businessCategory = $("#businessCategory").combobox('getValue');
	if(businessCategory == 13755){//交易
		
		if($("#superviseAccount").textbox('getValue') ==''){
			$.messager.alert('操作提示',"交易类赎楼，资金监管收款账号不允许为空!",'error');	
			return false;
		}
		
		if($("#superviseDepartment").combobox('getValue') == -1){
			$.messager.alert('操作提示',"交易类赎楼，监管银行不允许为空!",'error');	
			return false;
		}
		if($("#supersionReceBank").textbox('getValue') == ''){
			$.messager.alert('操作提示',"交易类赎楼，资金监管开户行不允许为空!",'error');	
			return false;
		}
		if($("#supersionReceName").textbox('getValue') == ''){
			$.messager.alert('操作提示',"交易类赎楼，资金监管收款户名不允许为空!",'error');	
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
	}
	//校验产品名称 交易/非交易有赎楼提放贷 周转资金等字段不能为空
	var productNumber = $("#productNumber").combobox('getValue');
// 	alert(productNumber);
	if(productNumber == 'JYYSLTFD' || productNumber == 'FJYYSLTFD'){
		if($("#turnoverCapitalBank2").textbox('getValue')==''){
			$.messager.alert('操作提示',"交易/非交易有赎楼提放贷，周转资金开户行不允许为空!",'error');	
			return false;
		}
		if($("#turnoverCapitalName2").textbox('getValue')==''){
			$.messager.alert('操作提示',"交易/非交易有赎楼提放贷，周转资金户名不允许为空!",'error');	
			return false;
		}
		if($("#turnoverCapitalAccount2").textbox('getValue')==''){
			$.messager.alert('操作提示',"交易/非交易有赎楼提放贷，周转资金账号不允许为空!",'error');	
			return false;
		}
	}
	var rows = $('#house_info').datagrid('getRows');
	// 物业信息
	if(rows.length <= 0){
		$.messager.alert('操作提示',"物业信息不允许为空!",'error');	
		return false;
	}
	
	var rows = $('#originalLoan_info').datagrid('getRows');
	// 原贷款信息
	if((productNumber != 'FJYWSLTFD' && productNumber != 'JYWSLTFD')){
		if(rows.length <= 0){
			$.messager.alert('操作提示',"原贷款信息不允许为空!",'error');	
			return false;
		}
	}
	var flag = $("#personalForm").form('validate');
	if(!flag){
		$.messager.alert('操作提示',"请先保存数据!",'error');
		return false;
	}
	//经办人姓名
	var managers = $("#pmUserId").combobox('getText');
	$("#personalForm input[name='managers']").val(managers);
	$("#personalForm").form('submit',{
		onSubmit : function() {},
        success:function(result){
        }
	});
	return true;
}
	

//打开新增借款人
function createPersonal(){
	//绑定下拉框的值
 	setCombobox("cert_type","CERT_TYPE",13088);
 	$('#personInfo').form('reset');
	$('#add_person').dialog('open').dialog('setTitle', "新增借款人");
}
//新增借款人
function addPersonal(){
	var tempFlag=1;
	var certNumber = $("#cert_Number").val();
	var type=false;
	var personId = $("#personInfo input[name='cusPerson.pid']").val();
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
							//保存成功，加载客户信息
							onloadPerson(acct_id);
						}else{
							$.messager.alert('操作提示',"保存失败！",'error');
						}
					},error : function(result){
						$.messager.alert('操作提示',"保存失败！",'error');
					}
				}); 
			}
		}
	});
}
/**
 * 保存成功加载个人客户信息
 */
function onloadPerson(acct_id){
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
				$("#personalForm input[name='censusAddr']").val(row.censusAddr);
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
}

/**
 * 校驗省份證號碼以及獲取性別以及年齡
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
				//判断性别
			if (parseInt(certNumber.substr(16, 1)) % 2 == 1) {
				//男
				$("#cusSex").val(13098);
				} else {
				//女
				$("#cusSex").val(13099);
				}  
			var year=certNumber.substring(6,10);
			var month=certNumber.substring(10,12);
			var date=certNumber.substring(12,14);
			$("#birth_Date").val(year+"-"+month+"-"+date);
			
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

//中文名失去焦点验证合法性
function checkChineseName(){
	var chinaName = $("#chineseName").val();
	chinaName=chinaName.replace(/[^\u4E00-\u9FA5]/g,'');
	$("#chineseName").val(chinaName);
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

/**
* 借款金额=赎楼金额+周转资金
*/
function addMoney(){
	// 	alert("成功****");
	var sum = 0;
	var turnoverMoney = $("#turnoverMoney").numberbox("getValue");
	var foreclosureMoney = $("#foreclosureMoney").numberbox("getValue");
	if(turnoverMoney !=null && turnoverMoney != ''){
		//判断赎楼金额是不是为空
		if(foreclosureMoney == null || foreclosureMoney ==''){
			turnoverMoney = parseFloat(turnoverMoney);
			sum = parseFloat(turnoverMoney);
		}else{
			turnoverMoney = parseFloat(turnoverMoney);
			foreclosureMoney = parseFloat(foreclosureMoney);
			sum = parseFloat(turnoverMoney) + parseFloat(foreclosureMoney);
		}
	}else{
		alert("周转金额不能为空！");
		return;
	}
	$("#loanMoney").numberbox("setValue",sum);
}




/*=======================================房产估价begin===========================================*/

//打开房产估价
function createSearchHousePrice(){
	
  	$('#search_house_price').dialog({
	    title: 'Q房估价',
	    width: 600,
	    height: 430,
	    closed: false,
	    cache: false,
	    modal: false
	});  
  	
	//清空数据
	$("#gardenId").val("");
	$("#buildingId").val("");
	$("#roomNumber").val("");
	
	$("input[name='searchHouseType']").get(0).checked=true; 
	changeSearchHouseType("1");
	$("#gardenName").textbox("setValue","");
	$("#buildingName").val("");
	$("#roomName").val("");
	
	$("#buildArea").textbox("setValue","");
	$("#directionEnum").val("");
	
	$("#floor").textbox("setValue","");
	$("#maxFloor").textbox("setValue","");
	
	$("#bedRoom").val("");
	$("#livingRoom").val("");
	
	$("#qfangPrice").textbox("setValue","");
	$("#totalQfangPrice").textbox("setValue","");
}

//修改房价查询方式
function changeSearchHouseType(searchHouseType){
	
	if(searchHouseType == "1"){
		//快速查询
		$(".tr_buildingName,.tr_roomName").hide();
		$("#span_buildArea").show();
		
	}else{
		//精确查询
		$(".tr_buildingName,.tr_roomName").show();
		$("#span_buildArea").hide();
	}
}


//初始化自动填充
var indexSelData = null;		//当前选 中的加载数据
var tempSearchP = "";			//临时查询关键字
function initHousePriceAutocomp(){
	
  	//加载城市 
  	var cityCode = $('#qfangCity').combobox('getValue');
  	if(cityCode == ""){
	  	$('#qfangCity').combobox({   
			url:'${basePath}qfangApi/getCityList.action',    
		    valueField:'cityCode',
		    textField:'cityName',
		    onLoadSuccess: function(rec){
		    	//默认加载深圳
		   		$('#qfangCity').combobox('setValue',"SHENZHEN");
	        } 
		});
  	}
	
	
	
	var cache = {};
       $('#gardenName').combobox({
       	prompt:'请输入小区名或楼盘名',
       	required:true,
       	mode:'remote',
       	url:'<%=basePath%>qfangApi/searchGarden.action',
           valueField:'id',
           textField:'name',
       	editable:true,
       	hasDownArrow:false,
        	onBeforeLoad: function(param){
      			//console.log("onBeforeLoad q:"+param.q +" ,tempSearchP :"+tempSearchP);
      			//防止空格重复查询
      			if(param == null || param.q == null || param.q.replace(/ /g, '') == ''){
      				tempSearchP = "";
	     			$('#gardenName').combobox("loadData",[]);
      				return false; 
      			}else{
 	       			if($.trim(param.q) == tempSearchP){
	       				return false;
	       			} 
	     			$('#gardenName').combobox("loadData",[]);
      				param.q =$.trim(param.q);
      				tempSearchP = param.q;
      				param.qfangCity=$('#qfangCity').combobox('getValue');
      				//console.log(param);
      				return true;
      			}
       	}, 
       	onLoadSuccess: function(result){
       		if(result.items != null  &&  result.items !='undefined'){
        		$("#gardenName").combobox("loadData",result.items);
       		}
       	},
		onSelect:function(result){  
			if(result != null && result != "undefined" && result.id != null){
				//console.log(">>>>>>onSelect:function set result:"+JSON.stringify(result));
				indexSelData = result;
				searchBuilding();
			}
			//console.log(">>>>>>onSelect:function indexSelData:"+JSON.stringify(indexSelData));
       	},
       	onChange:function(result){
       		var value = $(this).combobox('getValue');
       		var textVal = $(this).combobox('getText');
       		
       		if( value != null && value != "" && result != null && textVal != result && textVal != 0){
	       		$('#gardenId').val(value);
       		}else{
	       		$('#gardenId').val("");
	       		indexSelData = null;
	       		
	       		//清空级联下拉框
	       		clearSelOption("buildingName");
	       		clearSelOption("roomName");
	       		$("#buildingId").val("");
	       		$("#roomNumber").val("");
       		}
       		console.log("onChange:function : value :"+value +",textVal:"+textVal+",result:"+result+",indexSelData"+indexSelData);
       	}
       });        
}

 
//设置选中
function setSelectId(selectObjId,setValueId){
	var indexSelectObj = $("#"+selectObjId+" option:selected"); 
	$("#"+setValueId).val(indexSelectObj.val());
	if(selectObjId == "buildingName"){
		searchRoom();
	}else if(selectObjId == "roomName"){
		//设置朝向，户型，面积，楼层
		if(indexSelectObj.attr("buildArea") != null && indexSelectObj.attr("buildArea") !="null"){
			$("#buildArea").textbox("setValue",indexSelectObj.attr("buildArea"));
		}
		if(indexSelectObj.attr("floorNum") != null && indexSelectObj.attr("floorNum") !="null"){
			$("#floor").textbox("setValue",indexSelectObj.attr("floorNum"));
		}
		if(indexSelectObj.attr("maxFloor") != null && indexSelectObj.attr("maxFloor") !="null"){
			$("#maxFloor").textbox("setValue",indexSelectObj.attr("maxFloor"));
		}
		
		$("#directionEnum").val(indexSelectObj.attr("direction"));
		$("#bedRoom").val(indexSelectObj.attr("bedRoom"));
		$("#livingRoom").val(indexSelectObj.attr("livingRoom"));
	}
}


//查询楼栋
function searchBuilding(){
	var gardenId = $("#gardenId").val();
	console.log(">>>>>>searchBuilding gardenId:"+gardenId);
	
	if(gardenId == null || gardenId == "" ){
		//重新查询，加载未成功，先中旧选项时，清空
		$("#gardenName").combobox("setValue","");
		return;
	}
	
	var url = "<%=basePath%>qfangApi/searchBuilding.action";
	$.ajax( {
          url: url,
          dataType: "json",
          data: {gardenId: gardenId,qfangCity:$('#qfangCity').combobox('getValue')},
          success: function( result ) {
        	//console.log("searchBuilding result:"+JSON.stringify(result));
        	if(result != null && result.items !=null ){
	        	$("#buildingName").empty();
	        	var optionStr = "<option value=''>请选择</option>";
 				//加载其它项选项
 				var buildItems=result.items; 
           		for(var i = 0 ; i < buildItems.length; i++ ){
           			var build = buildItems[i];
           			if(build != null){
	           			var unitItems = build.units;
						if(unitItems != null && unitItems !='undefined'){
		           			for(var j = 0; j<unitItems.length ; j ++ ){
		           				var unit = unitItems[j];
		           				if(unit != null){
				           			optionStr = optionStr + "<option value='" + unit.id 
													 		 + "' buildSeq='"+ build.seq
													 		 + "' unitSeq='"+ unit.seq+"' ";
				           			optionStr += ">" + build.name+""+unit.unitName + "</option>";
		           				}
		           			}
						}else{
							
							optionStr = optionStr + "<option value='" + build.id 
					 		 + "' buildSeq='"+ build.seq
					 		 + "' unitSeq='' ";
							optionStr += ">" + build.name+ "</option>";
						}	           			
           			}
           		}
           		$("#buildingName").append(optionStr);	//追加
	          }
          }
    });
}

//查询房号
function searchRoom(){
	console.log("searchRoom");
	var buildingId = $("#buildingId").val();
	var url = "<%=basePath%>qfangApi/searchRoom.action";
	$.ajax( {
          url: url,
          dataType: "json",
          data: {buildingId: buildingId,qfangCity:$('#qfangCity').combobox('getValue')},
          success: function( result ) {
			if(result != null && result.items != null){
	        	var roomItems=result.items; 
	        	var optionStr = "<option value=''>请选择</option>";
	    		for(var i = 0; i<roomItems.length ; i ++ ){
	    			var room = roomItems[i];
	    			//console.log("searchRoom room"+JSON.stringify(room));
	       			optionStr = optionStr + "<option value='" + room.roomNumber+"' "
	       								+ "' bedRoom='"+ room.bedRoom +"' "
	       								+ "' livingRoom='"+ room.livingRoom +"' "
	       								+ "' kitchen='"+ room.kitchen +"' "
	       								+ "' balcony='"+ room.balcony +"' "
	       								+ "' direction='"+ room.direction +"' "
	       								+ "' maxFloor='"+ room.maxFloor +"' "
	       								+ "' floorNum='"+ room.floor.floorNum +"' "
	       								+ "' buildArea='"+ room.buildArea +"' "
	       								+ "' roomId='"+ room.id +"' "
	       								//+ "' unitId='"+ room.unit.id +"' "
	       								;
					optionStr += ">" + room.roomNumber + "</option>";
	    		}
	        	$("#roomName").empty();
	        	$("#roomName").append(optionStr);	//追加
	          }
			}
    });
}

//清空下拉框选项
function clearSelOption(selectObjId){
	$("#"+selectObjId).empty();
	var optionStr = "<option value=''>请选择</option>";
	$("#"+selectObjId).append(optionStr);	//追加
}

//查询房产价格
function searchHousePrice(){
	
	//查询类型
	var searchHouseType = $("input[name=searchHouseType]:checked").val();
	
	var gardenId = $("#gardenId").val();		//小区id
	var buildingId = $("#buildingId").val();	//楼栋id
	var roomNumber = $("#roomNumber").val();	//房号id
	var buildArea = $("#buildArea").val();		//面积
	
	var directionEnum = $("#directionEnum option:selected").val(); 	//朝向
	var floor = $("#floor").val();				//楼层
	var maxFloor = $("#maxFloor").val();		//最高层
	var bedRoom = $("#bedRoom").val();			//室
	var livingRoom = $("#livingRoom").val();	//厅
	var qfangCity= $('#qfangCity').combobox('getValue');	//城市
	
	
	var gardenName= $('#gardenName').combobox('getValue');	//小区名称
	
	if(gardenName!="" && gardenId == ""){
		$.messager.alert('操作提示',"请从提示列表选择小区",'error');
		return false;
	}
	if(gardenId == ""){
		$.messager.alert('操作提示',"请输入小区",'error');
		return false;
	}
	 
	if(searchHouseType == "1"){
		if(buildArea == ""){
			$.messager.alert('操作提示',"请输入面积",'error');
			return false;
		 }
	}else{
		if(buildingId == ""){
			$.messager.alert('操作提示',"请选择楼栋",'error');
			return false;
		}else if(roomNumber == ""){
			$.messager.alert('操作提示',"请选择房号",'error');
			return false;
		}
	 }
 
	 var url = "<%=basePath%>qfangApi/searchGuidingPrice.action";
	 $.ajax( {
          url: url,
          dataType: "json",
          data: {
        	  qfangCity:qfangCity,
        	  searchHouseType:searchHouseType,
        	  gardenId: gardenId,
        	  buildingId: buildingId,
        	  roomNumber: roomNumber,
        	  buildArea: buildArea,
        	  directionEnum: directionEnum,
        	  floor: floor,
        	  maxFloor: maxFloor,
        	  bedRoom: bedRoom,
        	  livingRoom: livingRoom
          },
          success: function( result ) {
        	 //var resultStr =  JSON.stringify(result);  
        	 // console.log("resultStr:"+resultStr);
        	 // resultStr =  resultStr.replace(/,/g,",</br>")
        	  if(result.evaluationHousePriceGuiding != null){
        		$("#qfangPrice").textbox('setValue', result.evaluationHousePriceGuiding.qfangPrice+"");
        		$("#totalQfangPrice").textbox('setValue', result.evaluationHousePriceGuiding.totalQfangPrice+"");
        		$.messager.alert('操作提示',"操作成功");
        	  }
          }
      });
}

//确认房产评估价格，赋值到评估价格文本模框
function confirmHousePrice(){
	
	var totalQfangPrice = $("#totalQfangPrice").textbox("getValue");
	
	if(totalQfangPrice == "" || totalQfangPrice <= 0 ){
		$.messager.alert('操作提示',"房产评估价格不能小于0",'error');
		return false;
	}
	$("#evaluationPrice1").textbox("setValue",totalQfangPrice);
	
	//关闭
	$('#search_house_price').dialog('close')
	
}


/*=======================================房产估价end===========================================*/
/**
 * 计算咨询费
 */
function getGuaranteeFee(){
	var loanMoney = $("#loanMoney").numberbox('getValue');
	var loanDays = $("#loanDays").numberbox('getValue');//借款天数
	var rate = $("#feeRate").numberbox('getValue');
	var guaranteeFee = (loanMoney*rate/100*loanDays/30).toFixed(2);
	$("#guaranteeFee").numberbox('setValue',guaranteeFee);
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
	        	var optionStr = "<option value=''>请选择</option>";
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
	    	}else{
	    		$("#partner_source_no").val(-1);
	    	}
	    	
		}
	});	
}

/**
 * 业务来源变更方法
 */
function setSelectSource(){
	var indexSelectObj = $("#partner_source_no option:selected"); //获取选中项
	var rate = indexSelectObj.attr("rate");
	$("#feeRate").numberbox('setValue',rate);
	var orgId = indexSelectObj.attr("value");
	setApplyCityCombobox("city_source_no",orgId,"");
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
/**
 * 打开物业信息信息框
 */
function openHouseInfo(){
	var rows = $('#house_info').datagrid('getRows');
	if(rows.length>=5){
		$.messager.alert('操作提示',"已达到物业保存上限，请去除一个再添加！",'error');
		return;
	}
	$('#houseInfo').form('reset');
	$("#houseInfo input[name='houseId']").val(0);// 物业ID
	setAreaCombobox("live_province_code","1","","");
	setAreaCombobox("live_city_code","2","","");
	setAreaCombobox("live_district_code","3","","");

	//居住地址省市区
  	$("#live_province_code").combobox({ 
		onSelect:function(){
        	var provinceCode = $("#live_province_code").combobox("getValue");
        	console.log("provinceCode:"+provinceCode);
        	if(provinceCode != ""){
        		setAreaCombobox("live_city_code","2",provinceCode,"");
        		setAreaCombobox("live_district_code","3","0","");
    		}
        }
	}); 
	$("#live_city_code").combobox({ 
		onSelect:function(){
        	var cityCode = $("#live_city_code").combobox("getValue");
        	if(cityCode != ""){
        		setAreaCombobox("live_district_code","3",cityCode,"");
    		}
        }
	});   
	$('#add_house').dialog('open').dialog('setTitle', "新增物业信息");
}
/**
 * 保存物业信息
 */
function addHouse(){
	$("#houseInfo input[name='projectId']").val(projectId);// 项目ID
	$('#houseInfo').form('submit', {
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);
				// 关闭窗口
				$('#add_house').dialog('close');
				var houseId = ret.header["houseId"];//返回的物业ID
				//重新加载物业信息
				loadHouseInfo(houseId);
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	}); 
}
/**
 * 物业列表操作按钮
 */
function caozuofiter(value,row,index){
	if(workflowTaskDefKey != "task_LoanRequest" && isEdit != 'isEdit' && workflowTaskDefKey != "task_BusinessRequest"){
		var dataDelDom = "<a href='javascript:void(0)' ><font color='gray'>删除</font></a>";
	}else{
		var dataDelDom = "<a href='javascript:void(0)' class='download'  onclick='delHouse("+row.houseId+")'><font color='blue'>删除</font></a>";	
	}
	return dataDelDom;
}
/**
 * 打开编辑页面
 */
function openEditHouse(){
	
	var row = $('#house_info').datagrid('getSelections');
 	if (row.length == 1) {
 		$('#houseInfo').form('reset');
 		$("#houseInfo input[name='houseId']").val(0);// 物业ID
 		var houseProvinceCode = row[0].houseProvinceCode;//省编码
 		var houseCityCode = row[0].houseCityCode;//市编码
 		var houseDistrictCode = row[0].houseDistrictCode;//区编码
 		if(houseProvinceCode == null ){
 			houseProvinceCode = "";
 		}
 		if(houseCityCode == null ){
 			houseCityCode = "";
 		}
 		if(houseDistrictCode == null ){
 			houseDistrictCode = "";
 		}
 		setAreaCombobox("live_province_code","1","",houseProvinceCode);
 		setAreaCombobox("live_city_code","2",houseProvinceCode,houseCityCode);
 		setAreaCombobox("live_district_code","3",houseCityCode,houseDistrictCode);

 		//居住地址省市区
 	  	$("#live_province_code").combobox({ 
 			onSelect:function(){
 	        	var provinceCode = $("#live_province_code").combobox("getValue");
 	        	console.log("provinceCode:"+provinceCode);
 	        	if(provinceCode != ""){
 	        		setAreaCombobox("live_city_code","2",provinceCode,"");
 	        		setAreaCombobox("live_district_code","3","0","");
 	    		}
 	        }
 		}); 
 		$("#live_city_code").combobox({ 
 			onSelect:function(){
 	        	var cityCode = $("#live_city_code").combobox("getValue");
 	        	if(cityCode != ""){
 	        		setAreaCombobox("live_district_code","3",cityCode,"");
 	    		}
 	        }
 		});   
 		$("#houseInfo input[name='houseId']").val(row[0].houseId);// 物业ID
 		$("#houseInfo input[name='projectId']").val(row[0].projectId);// 项目ID
 		$("#houseName").textbox('setValue',row[0].houseName);// 物业名称
 		$("#costMoney").numberbox('setValue',row[0].costMoney);// 登记价
 		$("#area").numberbox('setValue',row[0].area);// 面积
 		$("#housePropertyCard").textbox('setValue',row[0].housePropertyCard);// 房产证号
 		$("#purpose").textbox('setValue',row[0].purpose);// 用途
 		$("#tranasctionMoney1").numberbox('setValue',row[0].tranasctionMoney);//成交价
 		$("#evaluationPrice1").numberbox('setValue',row[0].evaluationPrice);// 评估价
 		$("#evaluationNet1").numberbox('setValue',row[0].evaluationNet);// 评估净值
 		$('#add_house').dialog('open').dialog('setTitle', "编辑物业信息");
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
	
}
/**
 * 删除物业信息
 */
function delHouse(houseId){
	if (confirm("是否确认删除?")) {
		$.ajax({
			type: "POST",
	        url: "${basePath}beforeLoanController/delHouse.action",
	        data:{"houseIds":houseId},
	        dataType: "json",
	        success: function(ret){
	        	//var ret = eval("("+data+")");
	        	if(ret && ret.header["success"]){
					// 操作提示
					$.messager.alert('操作提示',ret.header["msg"]);
					//重新加载
					loadHouseInfo("");
	        	}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');
				}
	        }
		});
	}
}
/**
 * 重新加载物业信息
 */
function loadHouseInfo(houseId){
	
	var rows = $('#house_info').datagrid('getRows');
	// 物业信息
	var houseIds = "";
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		houseIds += row.houseId+",";
	  	}
	}
	houseIds+=houseId;
	// 重新加载物业信息
	var url = "<%=basePath%>beforeLoanController/getHouseList.action?houseIds="+houseIds;
	$('#house_info').datagrid('options').url = url;
	$('#house_info').datagrid('reload');
}

/**
 * jiaoyan客户黑名单
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


/**
 * 打开原贷款信息信息框
 */
function openOriginalLoanInfo(){
	var rows = $('#originalLoan_info').datagrid('getRows');
	if(rows.length>=5){
		$.messager.alert('操作提示',"已达到原贷款保存上限，请去除一个再添加！",'error');
		return;
	}
	
	$('#originalLoanInfo').form('reset');
	$("#originalLoanInfo input[name='originalLoanId']").val(0);// 原贷款ID
 	//原贷款银行
	setBankCombobox("oldLoanBank",0,"-1");
	//贷款类型下拉框
	setCombobox("loanType","LOAN_TYPE","-1");
	setEstateCombox(0);
	$('#add_originalLoan').dialog('open').dialog('setTitle', "新增原贷款信息");
}
/**
 * 保存原贷款信息
 */
function addOriginalLoan(){
	$("#originalLoanInfo input[name='projectId']").val(projectId);// 项目ID
	$('#originalLoanInfo').form('submit', {
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);
				// 关闭窗口
				$('#add_originalLoan').dialog('close');
				var originalLoanId = ret.header["originalLoanId"];//返回的原贷款ID
				//重新加载原贷款信息
				loadOriginalLoanInfo(originalLoanId);
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	}); 
}

/**
 * 原贷款信息列表操作按钮
 */
function originalLoanCaozuo(value,row,index){
	if(workflowTaskDefKey != "task_LoanRequest" && isEdit != 'isEdit' && workflowTaskDefKey != "task_BusinessRequest"){
		var dataDelDom = "<a href='javascript:void(0)' ><font color='gray'>删除</font></a>";
	}else{
		var dataDelDom = "<a href='javascript:void(0)' class='download'  onclick='delOriginalLoan("+row.originalLoanId+")'><font color='blue'>删除</font></a>";	
	}
	return dataDelDom;
}
/**
 * 打开编辑页面
 */
function openEditOriginalLoan(){
	
	var row = $('#originalLoan_info').datagrid('getSelections');
 	if (row.length == 1) {
 		$('#originalLoanInfo').form('reset');
 		
 		//原贷款银行
 		setBankCombobox("oldLoanBank",0,row[0].oldLoanBank);
 		//贷款类型下拉框
 		setCombobox("loanType","LOAN_TYPE",row[0].loanType);
 		setEstateCombox(row[0].estateId);
 		$("#originalLoanInfo input[name='originalLoanId']").val(row[0].originalLoanId);// 物业ID
 		$("#originalLoanInfo input[name='projectId']").val(row[0].projectId);// 项目ID
 		$("#oldLoanMoney").numberbox('setValue',row[0].oldLoanMoney);// 原贷款借款金额
 		$("#oldOwedAmount").numberbox('setValue',row[0].oldOwedAmount);// 原贷款欠款金额
 		$("#oldLoanTime").datebox('setValue',row[0].oldLoanTime);
 		$("#oldLoanPerson").textbox('setValue',row[0].oldLoanPerson);// 原贷款联系人
 		$("#oldLoanPhone").textbox('setValue',row[0].oldLoanPhone);// 原贷款联系电话
 		$("#oldLoanAccount").textbox('setValue',row[0].oldLoanAccount);// 原贷款供楼账号
 		
 		$('#add_originalLoan').dialog('open').dialog('setTitle', "编辑原贷款信息");
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
	
}
/**
 * 删除原贷款信息
 */
function delOriginalLoan(originalLoanId){
	if (confirm("是否确认删除?")) {
		$.ajax({
			type: "POST",
	        url: "${basePath}beforeLoanController/delOriginalLoan.action",
	        data:{"originalLoanIds":originalLoanId},
	        dataType: "json",
	        success: function(ret){
	        	//var ret = eval("("+data+")");
	        	if(ret && ret.header["success"]){
					// 操作提示
					$.messager.alert('操作提示',ret.header["msg"]);
					//重新加载
					loadOriginalLoanInfo("");
	        	}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');
				}
	        }
		});
	}
}
/**
 * 重新加载原贷款信息
 */
function loadOriginalLoanInfo(originalLoanId){
	
	var rows = $('#originalLoan_info').datagrid('getRows');
	// 原贷款信息ID
	var originalLoanIds = "";
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		originalLoanIds += row.originalLoanId+",";
	  	}
	}
	originalLoanIds+=originalLoanId;
	// 重新加载原贷款信息
	var url = "<%=basePath%>beforeLoanController/getOriginalLoanList.action?originalLoanIds="+originalLoanIds;
	$('#originalLoan_info').datagrid('options').url = url;
	$('#originalLoan_info').datagrid('reload');
}

/**
 * 原贷款页面物业下拉框
 **/
function setEstateCombox(estateId){
	
	var rows = $('#house_info').datagrid('getRows');
	// 物业信息
	var houseIds = "";
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		houseIds += row.houseId+",";
	  	}
	}
	houseIds = houseIds.substring(0, houseIds.length-1);
	$('#estateId').combobox({
		url:'${basePath}beforeLoanController/getEstateList.action?houseIds='+houseIds,    
	    valueField:'houseId',
	    textField:'houseName',
	    value:'-1',
	    onLoadSuccess: function(rec){
	    	if(estateId >0){
	    		$('#estateId').combobox('setValue',estateId);
	    	}else{
	    		$('#estateId').combobox('setValue',-1);
	    	}
        } 
	});  
}

</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<c:if test="${retreatFlag ==1}">
	<div style="padding:30px 5px 0px 0px">
		<table>
			<tr>
				<td height="35" align="left" style="padding-left: 30px;">回撤项目名称: <a href="#" onclick="formatterProjectOverview.disposeClick(${oldProject.pid})"><font color='blue'> ${oldProject.projectName }</font></a></td>
				<td><div class="iniTitle">回撤项目编号:${oldProject.projectNumber}</div></td>
			</tr>
		</table>
	</div>
</c:if>
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
			<input type="hidden" name="projectSource" value="${project.projectSource}"/>
			<input type="hidden" name="projectType" value="${project.projectType}"/>
			<input type="hidden" name="userPids" />
			<input type="hidden" name="houseIds" />
			<input type="hidden" name="originalLoanIds" />
			<input type="hidden" name="projectProperty.houseName" />
			<input type="hidden" name="projectProperty.housePropertyCard" />
			<input type="hidden" id="productType" name="productType"/>
			<input type="hidden" name="projectForeclosure.pid" value="${project.projectForeclosure.pid }">
			<input type="hidden" name="projectForeclosure.projectId" value="${project.projectForeclosure.projectId }">
			<input type="hidden" name="projectGuarantee.pid" value="${project.projectGuarantee.pid }">
			<input type="hidden" name="projectGuarantee.projectId" value="${project.projectGuarantee.projectId }">
			<input type="hidden" name="projectProperty.pid" value="${project.projectProperty.pid }">
			<input type="hidden" name="projectProperty.projectId" value="${project.projectProperty.projectId }">
			<input type="hidden" name="pid" value="${project.pid}" />
			<input type="hidden" name="businessSourceNo" value="${project.businessSourceNo}" id="businessSourceNo"/>
			<c:if  test="${project == null ||project.projectType==2}">
				<input type="hidden" name="areaCode" value="${project.areaCode}" id="areaCode"/>
			</c:if>
			<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
				<tr>
					<td align="right" width="80" height="28">项目名称：</td>
					<td><input id="projectName" value="${project.projectName}" name="projectName" type="text" style="width: 149px;color: red;" class="text_style" readonly="readonly" > </td>
					<td align="right" width="80" height="28">项目编号：</td>
					<td ><input id="projectNumber" value="${project.projectNumber}" name="projectNumber" type="text" style="width: 149px;color: red;" class="text_style" readonly="readonly" > </td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>产品名称：</td>
					<td>
						<input name="productId" id="productId" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true" style="width: 135px;"/>
					</td>
					<!--隐藏产品编号 -->
					<td  class="label_right" style="display:none;" >产品编号：
						<input name="productNumber" id="productNumber" class="easyui-combobox"  style="width: 135px;"/>
					</td>
					<td class="label_right">区域：</td>
					<td>
						<input  class="easyui-textbox" name="address" value="${project.address}" data-options="validType:'length[0,50]'" style="width: 135px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>业务来源：</td>
					<td>
						<input name="businessSource" id="businessSource" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true" style="width: 135px;"/>
					</td>
					<td colspan="2">
						<div id="bank_source" class="none">
							<select id="bank_source_no" class="easyui-combobox" editable="false" style="width:135px;" />
						</div>
						<div id="partner_source1" class="none">
							<select id="partner_source_no1" class="easyui-combobox" editable="false" style="width:135px;" />
						</div>
						<div id="partner_source" class="none">
							<select id="partner_source_no" onchange="setSelectSource()"
		 				 	  class="textbox combo" editable="false" data-options="validType:'selrequired'" style="width: 236px;height: 24px;border-radius: 5px;border-color: #95B8E7;">
		 						<option value="-1">--请选择--</option>
							</select>
						</div>
						
						<div id="business_source" class="none">
							<select id="business_source_no" class="easyui-combobox" editable="false" style="width:135px;" />
						</div>
						<div id="business_source1" class="none">
							<select id="business_source_no1" class="easyui-combobox" editable="false" style="width:135px;" />
						</div>
						<div id="other" class="none">
							<input class="easyui-textbox" name="myMainText" value="${project.myMainText}" class="easyui-textbox" style="width:135px;" panelHeight="auto" data-options="validType:'length[0,50]'"/>
						</div>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>业务联系人：</td>
					<td>
						 <input name="businessContacts" value="${project.businessContacts}" data-options="required:true,validType:'length[1,20]'" id="businessContacts"  class="easyui-textbox" panelHeight="auto"  required="true" style="width: 135px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>联系电话：</td>
					<td>
						<input  class="easyui-textbox" data-options="required:true,validType:'telephone'" required="true" name="contactsPhone" value="${project.contactsPhone}" style="width: 135px;" />
					</td>
					<td class="label_right"><font color="red">*</font>业务类型：</td>
					<td>
						<input name="businessCategory" id="businessCategory" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true" style="width: 135px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>内外单：</td>
					<td class="nwd">
						<select id="innerOrOut" name="innerOrOut" class="easyui-combobox"  data-options="validType:'selrequired'" editable="false" style="width:135px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>经办人：</td>
					<td>
						<input name="pmUserId" id="pmUserId" style="width: 135px;" data-options="validType:'selrequired'" class="easyui-combobox" required="true"/>
						<input type="hidden" name="managers" value="${project.managers}" id="managers">
					</td>
					<!-- ERP后台录入数据，默认录单员为首次登陆用户 -->
					<c:if  test="${project == null || project.projectType==2}">
					<td class="label_right"><font color="red">*</font>录单员：</td>
					<td>
						<input type="hidden" name="recordClerkId" value="${project.recordClerkId }">
						<input  class="easyui-textbox" readonly="readonly" data-options="required:true,validType:'length[1,20]'" required="true" name="declaration"  value="${project.declaration } " style="width: 135px;" />
					</td>
					</c:if>
					<!-- AOM 端提交数据，默认录单员为空 -->
					<c:if  test="${project.projectType==6}">
					<td class="label_right"><font color="red">*</font>录单员：</td>
					<td>
						<input type="hidden" name="recordClerkId" value="0">
						<input  class="easyui-textbox" readonly="readonly" name="declaration"  value="" style="width: 135px;" />
					</td>
					</c:if>
					<td class="label_right1"><font color="red">*</font>借款金额：</td>
					<td>
						<input name="projectGuarantee.loanMoney" required="true" <c:if test="${project.projectType==6 }"> readonly="readonly" </c:if> id="loanMoney" value="${project.projectGuarantee.loanMoney }" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" style="width: 135px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>贷款天数：</td>
					<td>
						 <input name="projectForeclosure.loanDays" required="true" id="loanDays" data-options="min:1,max:9999,required:true,validType:'integer'" value="${project.projectForeclosure.loanDays }" class="easyui-numberbox" style="width:135px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>计划放款日期：</td>
					<td>
						 <input name="projectForeclosure.receDate" required="true" value="${project.projectForeclosure.receDate }" data-options="required:true" class="easyui-datebox" editable="false" style="width:135px;"/>
					</td>
					
					<td  id = "turnover_Money1"  class="label_right"  style="display:none;"><font color="red">*</font>周转金额：</td>
					<td  id = "turnover_Money"  style="display:none;">
						<input name="projectGuarantee.turnoverMoney" id="turnoverMoney" value="${project.projectGuarantee.turnoverMoney }" <c:if test="${project.projectType==6 }"> readonly="readonly" </c:if> class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" style="width:135px;"/>
					</td>
					<td  id = "foreclosure_Money1" class="label_right"  style="display:none;"><font color="red">*</font>赎楼金额：</td>
					<td  id = "foreclosure_Money" style="display:none;">
						<input name="projectGuarantee.foreclosureMoney" id="foreclosureMoney" <c:if test="${project.projectType==6 }"> readonly="readonly" </c:if> value="${project.projectGuarantee.foreclosureMoney }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" style="width:135px;"/>
					</td>
					<td class="label_right" ><font color="red">*</font>收费类型：</td>
					<td>
					   <select name="projectGuarantee.chargeType" id="charge_type" editable="false" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" 
		 				 	  style="width: 135px;height: 26px;border-radius: 5px;border-color: #95B8E7;">
		 				 	<option value=-1 <c:if test="${project.projectGuarantee.chargeType==-1 }">selected="selected"</c:if>>--请选择--</option>
		 				 	<option value=1 <c:if test="${project.projectGuarantee.chargeType==1 }">selected="selected"</c:if>>标准业务</option>
           					<option value=2 <c:if test="${project.projectGuarantee.chargeType==2 }">selected="selected"</c:if>>非标准业务</option>
					   </select>
					</td>
			    </tr>
				<tr>
				<!-- 小科的单 -->
				<c:if  test="${ project == null || project.projectType==2}">
 					<td class="label_right">城市：</td>
					<td>
						<input  class="easyui-textbox" readonly="readonly" required="true" id = "cityName" style="width: 135px;">
					</td>
				</c:if>
				<!-- 机构的单或者新订单-->
				<c:if  test="${project.projectType==6}">
					<td class="label_right">城市：</td>
					<td>
						<input  class="easyui-textbox" readonly="readonly" value="${project.areaCode }">
					</td>
				</c:if>
				</tr>
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
					<label style="width: auto;" id="text_gtjkr">请勾选共同借款人信息（共同借款人来自个人客户的配偶关系人和非配偶关系人）：</label>
				</div>
				<div id="toolbar_gtjkr"  style="border-bottom: 0;">
					<a href="javascript:void(0)" id="gtjkrAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openPersonalPublic()">选择共同借款人</a>
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
			
	<!-- 物业及买卖双方信息开始 -->
	<div  style="padding: 10px 10px 0 10px;">
		<div class=" easyui-panel" title="物业及买卖双方信息" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<!-- 物业信息 -->
			<div class="fitem" style="margin-left: 40px;">				
				<div id="toolbar_house"  style="border-bottom: 0;">
					<a href="javascript:void(0)" id="houseAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openHouseInfo()">新增物业</a>
					<a href="javascript:void(0)" id="houseAdd" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="openEditHouse()">修改物业</a>
				</div>
				<table id="house_info" class="easyui-datagrid" fitColumns="true" style="width: 960px;height: auto;"
				     data-options="
				     	url: '',
				     	method: 'post',
				     	rownumbers: true,
			    		fitColumns:true,
				    	singleSelect: false,
				    	toolbar: '#toolbar_house',
					    pagination: false">
					<thead>
						<tr>
							<th data-options="field:'houseId',checkbox:true" ></th>
							<th data-options="field:'houseName'"  align="center" halign="center" >物业名称</th>
							<th data-options="field:'area'"  align="center" halign="center" >面积(㎡)</th>
							<th data-options="field:'housePropertyCard'"  align="center" halign="center" >房产证号</th>
							<th data-options="field:'costMoney',formatter:formatMoney"  align="center" halign="center" >登记价(元)</th>
							<th data-options="field:'tranasctionMoney',formatter:formatMoney"  align="center" halign="center" >成交价(元)</th>
							<th data-options="field:'evaluationPrice',formatter:formatMoney"  align="center" halign="center" >评估价(元)</th>
							<th data-options="field:'evaluationNet',formatter:formatMoney"  align="center" halign="center" >评估净值(元)</th>
							<th data-options="field:'purpose'"  align="center" halign="center" >用途</th>
							<th data-options="field:'houseAddress'"  align="center" halign="center" >地址</th>
							<th data-options="field:'caozuo',formatter:caozuofiter"  align="center" halign="center" >操作</th>
						</tr>
					</thead>
			   	</table>
			</div>
			<table class="beforeloanTable">
				<%-- <tr>
					<td class="label_right1"><font color="red">*</font>物业名称：</td>
					<td>
						<input name="projectProperty.houseName" data-options="required:true,validType:'length[1,50]'" value="${project.projectProperty.houseName }"  class="easyui-textbox"/>
					</td>
					<td class="label_right1"><font color="red">*</font>面积：</td>
					<td>
						<input name="projectProperty.area" required="true" style="width:119px;" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox" min="0" precision="2" maxLength='8' groupSeparator="," value="${project.projectProperty.area }"/>㎡
					</td>
					<td class="label_right"><font color="red">*</font>原价：</td>
					<td>
						<input class="easyui-numberbox" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" name="projectProperty.costMoney" value="${project.projectProperty.costMoney }"/>
					</td>
					<td class="label_right"><font color="red">*</font>房产证号：</td>
					<td>
						<input name="projectProperty.housePropertyCard" data-options="required:true,validType:'length[1,50]'" value="${project.projectProperty.housePropertyCard }"  class="easyui-textbox"/>
					</td>
				</tr> --%>
				
				<tr>
					<td class="label_right1"><font color="red">*</font>总成交价：</td>
					<td>
						 <input name="projectProperty.tranasctionMoney" readonly="readonly" id="tranasctionMoney" required="true" value="${project.projectProperty.tranasctionMoney }" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
					</td>
					<td class="label_right1"><font color="red">*</font>总评估价格：</td>
					<td>
						 <input name="projectProperty.evaluationPrice" readonly="readonly" id="evaluationPrice" value="${project.projectProperty.evaluationPrice }" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
					</td>
					<td class="label_right1">评估净值总价：</td>
					<td>
						 <input name="projectProperty.evaluationNet" readonly="readonly" id="evaluationNet" value="${project.projectProperty.evaluationNet }" data-options="prompt:'推南粤银行为必填项！',min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
					</td>
					<!-- 估价按钮
					<td class="label_right1">评估价格：</td>
					<td>
						<a class="easyui-linkbutton personalbtn"  id="searchHousePriceBtn"  onclick="createSearchHousePrice()">评估房产</a>
					</td> -->
					
					<td class="label_right">赎楼成数：</td>
					<td colspan="3">
						 <input name="projectProperty.foreRate" style="width:119px;" id="foreRate" readonly="readonly" value="${project.projectProperty.foreRate }" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>%
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
						<input name="projectProperty.buyerCardNo" id="buyerCardNo" data-options="prompt:'两个以上请用英文逗号隔开！',validType:'length[0,100]'" value="${project.projectProperty.buyerCardNo }"  class="easyui-textbox"/>
					</td>
					<td class="label_right">联系电话：</td>
					<td>
						<input name="projectProperty.buyerPhone" id="buyerPhone" data-options="prompt:'两个以上请用英文逗号隔开！',validType:'length[0,100]'" value="${project.projectProperty.buyerPhone }"  class="easyui-textbox"/>
					</td>
					<td class="label_right">家庭地址：</td>
					<td>
						 <input name="projectProperty.buyerAddress" id="buyerAddress" data-options="prompt:'两个以上请用英文逗号隔开！',validType:'length[0,100]'" value="${project.projectProperty.buyerAddress }"  class="easyui-textbox"/>
					</td>
				</tr>
			</table>
		</div>
		
		</div>
		</div>
		
		<!-- 物业及买卖双方信息结束 -->

	<!-- 赎楼信息开始 -->
	<div  id="foreclosureInfo">
	<div style="padding: 10px 10px 0 10px;">
		<div class=" easyui-panel" title="附加" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<!-- 原贷款银行信息开始 -->
			<div class="fitem" style="margin-left: 40px;" id = "oldFitem">				
				<div id="toolbar_originalLoan"  style="border-bottom: 0;">
					<a href="javascript:void(0)" id="originalLoanAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openOriginalLoanInfo()">新增原贷款</a>
					<a href="javascript:void(0)" id="originalLoanEdit" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="openEditOriginalLoan()">修改原贷款</a>
				</div>
				<table id="originalLoan_info" fitColumns="true" style="width: 960px;height: auto;"
				     data-options="
				     	url: '',
				     	method: 'post',
				     	rownumbers: true,
			    		fitColumns:true,
				    	singleSelect: false,
				    	toolbar: '#toolbar_originalLoan',
					    pagination: false">
					<thead>
						<tr>
							<th data-options="field:'originalLoanId',checkbox:true" ></th>
							<th data-options="field:'loanTypeStr'"  align="center" halign="center" >原贷款类型</th>
							<th data-options="field:'oldLoanBankStr'"  align="center" halign="center" >原贷款银行</th>
							<th data-options="field:'oldLoanMoney',formatter:formatMoney"  align="center" halign="center" >原贷款金额(元)</th>
							<th data-options="field:'oldOwedAmount',formatter:formatMoney"  align="center" halign="center" >原欠款金额(元)</th>
							<th data-options="field:'oldLoanTime'"  align="center" halign="center" >原贷款结束时间</th>
							<th data-options="field:'estateName'"  align="center" halign="center" >原贷款物业</th>
							<th data-options="field:'oldLoanAccount'"  align="center" halign="center" >供楼账号</th>
							<th data-options="field:'oldLoanPerson'"  align="center" halign="center" >原贷款联系人</th>
							<th data-options="field:'oldLoanPhone'"  align="center" halign="center" >原贷款联系电话</th>
							<th data-options="field:'caozuo',formatter:originalLoanCaozuo"  align="center" halign="center" >操作</th>
						</tr>
					</thead>
			   	</table>
			</div>
			<!-- 原贷款银行信息结束 -->
			<table class="beforeloanTable">
				<tr id = "oldLoanMoneyInfo">
					<td class="label_right1"><font color="red">*</font>原贷款总金额：</td>
					<td>
						<input class="easyui-numberbox" id="oldLoanMoney1" readonly="readonly"  data-options="min:0,max:9999999999,precision:2,groupSeparator:','" value="${project.projectForeclosure.oldLoanMoney }" name="projectForeclosure.oldLoanMoney" style="width:135px;"/>
					</td>
					<td class="label_right1"><font color="red">*</font>原贷款欠款总金额：</td>
					<td>
						 <input name="projectForeclosure.oldOwedAmount" readonly="readonly" id="oldOwedAmount1" class="easyui-numberbox"  data-options="min:0,max:9999999999,precision:2,groupSeparator:','" value="${project.projectForeclosure.oldOwedAmount }" style="width:135px;"/>
					</td>
				</tr>
				
				<tr style="padding-top:10px;">
					<td class="label_right"><font color="red">*</font>新贷款银行：</td>
					<td>
						<select id="newLoanBank" class="easyui-combobox"  data-options="validType:'selrequired'" name="projectForeclosure.newLoanBank" editable="false" style="width:135px;" />
					</td>
					<td class="label_right"><font color="red">*</font>新贷款金额：</td>
					<td>
						<input class="easyui-numberbox" id="newLoanMoney" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.newLoanMoney" value="${project.projectForeclosure.newLoanMoney }" style="width:135px;"/>
					</td>

					<td class="label_right1">银行联系人：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[0,20]'" name="projectForeclosure.newLoanPerson" value="${project.projectForeclosure.newLoanPerson }" style="width:135px;"/>
					</td>
					<td class="label_right">联系电话：</td>
					<td>
						 <input name="projectForeclosure.newLoanPhone" data-options="validType:'telephone'" value="${project.projectForeclosure.newLoanPhone }"  class="easyui-textbox" style="width:135px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>回款账号开户行：</td>
					<td>
						<input  class="easyui-textbox" data-options="required:true,validType:'length[1,20]'" required="true" name="projectForeclosure.newReceBank" value="${project.projectForeclosure.newReceBank }" style="width:135px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>回款账号户名：</td>
					<td>
						<input  class="easyui-textbox" data-options="required:true,validType:'length[1,20]'" name="projectForeclosure.paymentName" value="${project.projectForeclosure.paymentName }" style="width:135px;"/>
					</td>
					<td class="label_right">回款人证件号：</td>
					<td colspan="3">
						<input  class="easyui-textbox" id = "idCardNumber" data-options="validType:'length[0,20]'" name="projectForeclosure.idCardNumber" value="${project.projectForeclosure.idCardNumber }" style="width:200px;"/>
					</td>
				</tr>
				<tr>
				<td class="label_right"><font color="red">*</font>回款账号：</td>
					<td colspan="3">
						<input  class="easyui-textbox" data-options="required:true,validType:'length[1,50]'" name="projectForeclosure.paymentAccount" value="${project.projectForeclosure.paymentAccount }" style="width:380px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>贷款方式：</td>
					<td>
						<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="projectForeclosure.paymentType" style="width:135px;">
							<option value="-1">--请选择--</option>
							<option value="5" <c:if test="${project.projectForeclosure.paymentType==5 }">selected </c:if>>经营贷</option>
							<option value="6" <c:if test="${project.projectForeclosure.paymentType==6 }">selected </c:if>>消费贷</option>
							<option value="1" <c:if test="${project.projectForeclosure.paymentType==1 }">selected </c:if>>按揭</option>
							<option value="2" <c:if test="${project.projectForeclosure.paymentType==2 }">selected </c:if>>组合贷</option>
							<option value="3" <c:if test="${project.projectForeclosure.paymentType==3 }">selected </c:if>>公积金贷</option>
							<option value="4" <c:if test="${project.projectForeclosure.paymentType==4 }">selected </c:if>>一次性付款</option>
						</select>
					</td>
					<td class="label_right"><font color="red">*</font>出款账号：</td>
					<td>
						<input  class="easyui-textbox" data-options="required:true,validType:'length[1,50]'" name="projectForeclosure.foreAccount" value="${project.projectForeclosure.foreAccount }" style="width:135px;"/>
					</td>
					<td class="label_right">公积金银行：</td>
					<td>
						<select id="accumulationFundBank" class="easyui-combobox" name="projectForeclosure.accumulationFundBank" editable="false" style="width:135px;" />
					</td>
					<td class="label_right">公积金贷款金额：</td>
					<td>
						<input class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.accumulationFundMoney" value="${project.projectForeclosure.accumulationFundMoney }" style="width:135px;"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right">首付款金额：</td>
					<td>
						<input class="easyui-numberbox" id=downPayment data-options="prompt:'非交易类型不用填！',min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.downPayment" value="${project.projectForeclosure.downPayment }" style="width:135px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>监管银行：</td>
					<td>
						<select id="superviseDepartment" class="easyui-combobox"  data-options="prompt:'非交易类型不用填！',validType:'selrequired'" name="projectForeclosure.superviseDepartment" editable="false" style="width:120px;" />
					</td>
					<td class="label_right"><font color="red">*</font>资金监管金额：</td>
					<td>
						<input class="easyui-numberbox" id="fundsMoney" data-options="prompt:'非交易类型不用填！',min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.fundsMoney" value="${project.projectForeclosure.fundsMoney }" style="width:135px;"/>
					</td>
					
				</tr>
				<tr>
				<td class="label_right"><font color="red">*</font>资金监管开户行：</td>
					<td>
						<input  class="easyui-textbox" data-options="prompt:'非交易类型不用填！',validType:'length[0,30]'" id="supersionReceBank" name="projectForeclosure.supersionReceBank" value="${project.projectForeclosure.supersionReceBank }" style="width:135px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>资金监管收款户名：</td>
					<td>
						<input  class="easyui-textbox" data-options="prompt:'非交易类型不用填！',validType:'length[0,30]'" id="supersionReceName" name="projectForeclosure.supersionReceName" value="${project.projectForeclosure.supersionReceName }" style="width:135px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>资金监管收款账号：</td>
					<td colspan="3">
						<input  class="easyui-textbox" data-options="prompt:'非交易类型不用填！',validType:'length[0,50]'" id="superviseAccount" name="projectForeclosure.superviseAccount" value="${project.projectForeclosure.superviseAccount }" style="width:280px;"/>
					</td>
				</tr>
				<tr> 
				<td class="label_right" id = "turnoverCapitalBank1" style="display:none;"><font color="red">*</font>周转资金开户行：</td>
					<td id = "turnoverCapitalBank" style="display:none;">
						<input  class="easyui-textbox" data-options="validType:'length[0,30]'" id="turnoverCapitalBank2" name="projectForeclosure.turnoverCapitalBank" value="${project.projectForeclosure.turnoverCapitalBank }" style="width:135px;"/>
					</td>
					<td class="label_right" id = "turnoverCapitalName1" style="display:none;"><font color="red">*</font>周转资金户名：</td>
					<td id = "turnoverCapitalName" style="display:none;">
						<input  class="easyui-textbox" data-options="validType:'length[0,30]'" id="turnoverCapitalName2" name="projectForeclosure.turnoverCapitalName" value="${project.projectForeclosure.turnoverCapitalName }" style="width:135px;"/>
					</td>
					<td class="label_right" id = "turnoverCapitalAccount1" style="display:none;"><font color="red">*</font>周转资金账号：</td>
					<td colspan="3" id = "turnoverCapitalAccount" style="display:none;">
						<input  class="easyui-textbox" data-options="validType:'length[0,50]'" id="turnoverCapitalAccount2" name="projectForeclosure.turnoverCapitalAccount" value="${project.projectForeclosure.turnoverCapitalAccount }" style="width:280px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">委托公证日期：</td>
					<td>
						 <input name="projectForeclosure.notarizationDate" value="${project.projectForeclosure.notarizationDate }" class="easyui-datebox" editable="false" style="width:135px;"/>
					</td>
					<td class="label_right1">签暑合同日期：</td>
					<td>
						<input class="easyui-datebox" editable="false" name="projectForeclosure.signDate" value="${project.projectForeclosure.signDate }" style="width:135px;"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
		</div>
	
		<div  style="padding: 10px 10px 0 10px;">
		<div class=" easyui-panel" title="费用" data-options="collapsible:true" width="1039px;">
		<div style="padding: 10px 0 10px 10px;height: auto;line-height: 35px;" >
			<table class="beforeloanTable">
				<tr>
					<td class="label_right"><font color="red">*</font>费率：</td>
					<td>
						<input name="projectGuarantee.feeRate" style="width:135px;" id="feeRate" required="true" value="${project.projectGuarantee.feeRate }" class="easyui-numberbox" data-options="required:true,min:0,max:100,precision:2,groupSeparator:','"/>%
					</td>
					<td class="label_right1"><font color="red">*</font>咨询费：</td>
					<td>
						<input name="projectGuarantee.guaranteeFee" required="true" id="guaranteeFee" value="${project.projectGuarantee.guaranteeFee }" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td> 
					<td class="label_right1">手续费：</td>
					<td>
						<input name="projectGuarantee.poundage" value="${project.projectGuarantee.poundage }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
					<td class="label_right">手续费补贴：</td>
					<td>
						<input name="projectGuarantee.chargesSubsidized" value="${project.projectGuarantee.chargesSubsidized }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">应付佣金：</td>
					<td>
						 <input name="projectGuarantee.receMoney" value="${project.projectGuarantee.receMoney }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
					<td class="label_right1"><font color="red">*</font>收费方式：</td>
					<td>
						<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="projectGuarantee.chargesType" style="width:135px;">
							<option value="1" <c:if test="${project.projectGuarantee.chargesType==1 }">selected </c:if>>贷前收费</option>
							<option value="2" <c:if test="${project.projectGuarantee.chargesType==2 }">selected </c:if>>贷后收费</option>
						</select>
					</td>
					<td class="label_right1">待收费用：</td>
					<td>
						<input name="projectGuarantee.deptMoney" value="${project.projectGuarantee.deptMoney }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
		<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" id="fore_info_click" onclick="saveProjectInfo()">保存项目信息</a>
			</div>
	</form>
	</div>
	</div>
	<!-- 赎楼信息结束 -->
	
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
					<input name="cusPerson.sex" type="hidden" id="cusSex"/>
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
					 <td><input type="text" class="text_style easyui-textbox" data-options="required:true"  onblur="checkChineseName();" id="chineseName" name="cusPerson.chinaName"/></td>
					<td class="label_right1"><span class="cus_red">*</span>证件类型：</td>
					 <td>
						<select id="cert_type" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" name="cusPerson.certType"  style="width:150px;"   />
					 </td>
				  </tr>
				  <tr>
				  	<td class="label_right1" ><span class="cus_red">*</span>手机号码：</td>
				     <td><input type="text" class="text_style easyui-textbox" id="cusPersonTelephone" data-options="required:true,validType:'telephone'" name="cusPerson.telephone"/></td>
				  	<td class="label_right1"><span class="cus_red">*</span>证件号码：</td>
					 <td><input type="text" id="cert_Number" class="text_style easyui-textbox" name="cusPerson.certNumber" data-options="required:true" onblur="getAge();"/></td>
				  </tr>
				  <tr>
				   <td class="label_right1"><span class="cus_red">*</span>居住地址：</td>
					<td colspan="3"><input type="text" style="width: 425px;" class="text_style easyui-textbox" id="cusPersonLiveAddr" name="cusPerson.liveAddr" data-options="required:true"/>
				  </tr>
				 </table>
			</form>	  
		</div>
		<!-- 新增借款人结束 -->
		
		<!-- 房产估价开始 =======================================================-->
		<div id="search_house_price_info">		
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="searchHousePrice()">立即评估</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="confirmHousePrice()">确认</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#search_house_price').dialog('close')">关闭</a>
		</div>
 		<div id="search_house_price" class="easyui-dialog" fitColumns="true"  title="房产估价"
		     style="width:850px;top:100px;padding:10px;" buttons="#search_house_price_info" closed="true" >
		     
		     <!-- 小区id -->
		     <input type="hidden" id="gardenId" style="width: 250px;" >
		     <!-- 楼栋id -->
	         <input type="hidden" id="buildingId" style="width: 250px;">
		     <!-- 房号id -->
	         <input type="hidden" id="roomNumber" style="width: 250px;">
	         
	         
			<table class="beforeloanTable">
			
				<tr class="tr_directionEnum">
					<td class="label_right1">城市：</td>
				 	<td>
		 				<select  name="qfangCity" id="qfangCity" class="easyui-combobox"  editable="false"  style="width: 151px;height: 26px;border-radius: 5px;border-color: #95B8E7;">
						</select>
				 	</td>
			  	</tr>
			
				<tr>
					<td class="label_right1"><span class="cus_red">*</span>查询方式：</td>
				 	<td>
				 		<input type="radio"  name="searchHouseType" value="1" onclick="changeSearchHouseType('1')" checked="checked"/>快速查询
				 		<input type="radio"  name="searchHouseType" value="2" onclick="changeSearchHouseType('2')"  />精准查询
				 	</td>
			  	</tr>
				<tr>
					<td class="label_right1"><span class="cus_red">*</span>小区：</td>
				 	<td>
				 		<input type="text" id="gardenName" name="gardenName" class="text_style" />
				 		<span class="cus_red">请从提示列表选择</span>
				 	</td>
			  	</tr>
				<tr class="tr_buildingName" style="display: none;">
					<td class="label_right1"><span class="cus_red">*</span>楼栋：</td>
				 	<td>
		 				<select  name="buildingName" id="buildingName" onchange="setSelectId('buildingName','buildingId')"
		 				 	  class="select_style" data-options="validType:'selrequired'" style="width: 151px;height: 26px;border-radius: 5px;border-color: #95B8E7;">
		 					<option value="">请选择</option>
						</select>
				 	</td>
			  	</tr>
				<tr class="tr_roomName" style="display: none;">
					<td class="label_right1"><span class="cus_red">*</span>房号：</td>
				 	<td>
		 				<select  name="roomName" id="roomName" onchange="setSelectId('roomName','roomNumber')"
		 				 	  class="select_style" data-options="validType:'selrequired'" style="width: 151px;height: 26px;border-radius: 5px;border-color: #95B8E7;">
		 					<option value="">请选择</option>
						</select>
				 	</td>
			  	</tr>
				<tr class="tr_buildArea">
					<td class="label_right1"><span class="cus_red"  id="span_buildArea">*</span>面积：</td>
				 	<td><input type="text" id="buildArea" name="buildArea" class="text_style easyui-numberbox"  data-options="min:0,max:500" />㎡</td>
			  	</tr>
			  	<!-- 朝向 -->
				<tr class="tr_directionEnum">
					<td class="label_right1">朝向：</td>
				 	<td>
		 				<select  name="directionEnum" id="directionEnum" class="select_style" style="width: 151px;height: 26px;border-radius: 5px;border-color: #95B8E7;">
		 					<option value="">请选择</option>
		 					<option value="EAST">东</option>
		 					<option value="SOUTH">南</option>
		 					<option value="WEST">西</option>
		 					<option value="NORTH">北</option>
		 					<option value="NORTHEAST">东北</option>
		 					<option value="SOUTHEAST">东南</option>
		 					<option value="WESTEAST">东西</option>
		 					<option value="NORTHSOUTH">南北</option>
		 					<option value="NORTHWEST">西北</option>
		 					<option value="SOUTHWEST">东南</option>
						</select>
				 	</td>
			  	</tr>
			  	<!-- 楼层 -->
				<tr class="tr_floor">
					<td class="label_right1">楼层：</td>
				 	<td>
				 		<input type="text" name="floor" id="floor" class="text_style easyui-numberbox"  data-options="min:0,max:70" >共
						<input type="text" name="maxFloor" id="maxFloor" class="text_style easyui-numberbox" data-options="min:0,max:70">层
				 	</td>
			  	</tr>
			  	<!-- 户型-->
				<tr class="tr_roomType">
					<td class="label_right1">户型：</td>
				 	<td>
		 				<select  name="bedRoom" id="bedRoom" class="select_style" style="width: 151px;height: 26px;border-radius: 5px;border-color: #95B8E7;">
		 					<option value="">请选择</option>
		 					<option value="1">1</option>
		 					<option value="2">2</option>
		 					<option value="3">3</option>
		 					<option value="4">4</option>
		 					<option value="5">5</option>
		 					<option value="6">6</option>
		 					<option value="7">7</option>
						</select>
						室
						<select  name="livingRoom" id="livingRoom" class="select_style" style="width: 151px;height: 26px;border-radius: 5px;border-color: #95B8E7;">
		 					<option value="">请选择</option>
		 					<option value="1">1</option>
		 					<option value="2">2</option>
		 					<option value="3">3</option>
		 					<option value="4">4</option>
		 					<option value="5">5</option>
		 					<option value="6">6</option>
		 					<option value="7">7</option>
						</select>
						厅
				 	</td>
			  	</tr>
				<tr>
					<td class="label_right1"><span class="cus_red">*</span>Q房估价单价：</td>
				 	<td><input type="text" id="qfangPrice" name="qfangPrice" readonly="readonly" class="text_style easyui-textbox" /></td>
			  	</tr>
				<tr>
					<td class="label_right1"><span class="cus_red">*</span>Q房估价总价：</td>
				 	<td><input type="text" id="totalQfangPrice" name="totalQfangPrice" readonly="readonly" class="text_style easyui-textbox" /></td>
			  	</tr>
			 </table>
		</div> 
		<!-- 房产估价结束========================================================== -->
		
		<!-- 新增物业信息开始 -->
		<div id="add_house_info">		
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addHouse()">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#add_house').dialog('close')">关闭</a>
		</div>
		<div id="add_house" class="easyui-dialog" fitColumns="true"  title="新增物业信息"
		     style="width:850px;top:100px;padding:10px;" buttons="#add_house_info" closed="true" >
			<form id="houseInfo" action="<%=basePath%>beforeLoanController/saveHouseInfo.action" method="POST">
				<table class="beforeloanTable">
					<input type="hidden" name="houseId"/>
					<input type="hidden" name="projectId"/>
					<tr>
					<td class="label_right1"><font color="red">*</font>物业名称：</td>
					<td>
						<input name="houseName" id="houseName" data-options="required:true,validType:'length[1,50]'" class="easyui-textbox"/>
					</td> 
					<td class="label_right1"><font color="red">*</font>面积：</td>
					<td>
						<input name="area" id="area" required="true" style="width:119px;" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>㎡
					</td>
					<td class="label_right"><font color="red">*</font>房产证号：</td>
					<td>
						<input name="housePropertyCard" id="housePropertyCard" data-options="required:true,validType:'length[1,50]'"  class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
				   <td class="label_right"><span class="cus_red">*</span>物业所在地：</td>
				   <td >
				   	<select id="live_province_code" name="houseProvinceCode" class="easyui-combobox" data-options="validType:'selrequired'" editable="false" style="width:135px;" />
				   	</td>
				   <td >
				   	<select id="live_city_code" name="houseCityCode" class="easyui-combobox" data-options="validType:'selrequired'" editable="false" style="width:135px;" />
				   	</td>
				   <td >
				   	<select id="live_district_code" name="houseDistrictCode" class="easyui-combobox" data-options="validType:'selrequired'" editable="false" style="width:135px;" />
				   	</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>登记价：</td>
					<td>
						<input name="costMoney" id="costMoney" class="easyui-numberbox" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
					<td class="label_right1"><font color="red">*</font>成交价：</td>
					<td>
						 <input name="tranasctionMoney" id="tranasctionMoney1" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>用途：</td>
					<td>
						<input name="purpose" id="purpose" data-options="required:true,validType:'length[1,50]'" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1">评估净值：</td>
					<td>
						 <input name="evaluationNet" id="evaluationNet1" data-options="prompt:'推南粤银行为必填项！',min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
					</td>
					<td class="label_right1"><font color="red">*</font>评估价格：</td>
					<td>
						 <input name="evaluationPrice" id="evaluationPrice1" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
					</td>
					<!-- 估价按钮 -->
					<td class="label_right1">评估价格：</td>
					<td>
						<a class="easyui-linkbutton personalbtn"  id="searchHousePriceBtn"  onclick="createSearchHousePrice()">评估房产</a>
					</td>
				</tr>
				 </table>
			</form>	  
		</div>
		<!-- 新增物业信息结束 -->
		<!-- 新增原贷款信息开始 -->
		<div id="add_originalLoan_info">		
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addOriginalLoan()">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#add_originalLoan').dialog('close')">关闭</a>
		</div>
		<div id=add_originalLoan class="easyui-dialog" fitColumns="true"  title="新增原贷款信息"
		     style="width:850px;top:100px;padding:10px;" buttons="#add_originalLoan_info" closed="true" >
			<form id="originalLoanInfo" action="<%=basePath%>beforeLoanController/saveOriginalLoanInfo.action" method="POST">
				<table class="beforeloanTable">
					<input type="hidden" name="originalLoanId"/>
					<input type="hidden" name="projectId"/>
					<tr>
						<td class="label_right"><font color="red">*</font>原贷款类型：</td>
						<td>
							 <select id="loanType" class="easyui-combobox"  data-options="validType:'selrequired'" name="loanType" editable="false" style="width:135px;" />
						</td>
						<td class="label_right1"><font color="red">*</font>原贷款银行：</td>
						<td>
							<select id="oldLoanBank" class="easyui-combobox"  data-options="validType:'selrequired'" name="oldLoanBank" editable="false" style="width:135px;" />
						</td>
						<td class="label_right1">供楼账号：</td>
						<td>
							<input name="oldLoanAccount" id="oldLoanAccount" data-options="prompt:'推南粤银行为必填项！',validType:'length[0,50]'" class="easyui-textbox" style="width:135px;"/>
						</td>
					</tr>
					<tr>
						<td class="label_right1"><font color="red">*</font>关联物业：</td>
						<td>
							<select id="estateId" class="easyui-combobox"  data-options="validType:'selrequired'" name="estateId" editable="false" style="width:135px;" />
						</td>
						<td class="label_right1"><font color="red">*</font>原贷款金额：</td>
						<td>
							<input class="easyui-numberbox" name="oldLoanMoney" id="oldLoanMoney" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" style="width:135px;"/>
						</td>
						<td class="label_right"><font color="red">*</font>原贷款欠款金额：</td>
						<td>
							 <input name="oldOwedAmount" id="oldOwedAmount" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" style="width:135px;"/>
						</td>
					</tr>
					
					<tr>
						<td class="label_right"><font color="red">*</font>贷款结束时间：</td>
						<td>
							 <input name="oldLoanTime" required="true" id="oldLoanTime" editable="false" data-options="required:true" class="easyui-datebox" style="width:135px;"/>
						</td>
						<td class="label_right1">银行联系人：</td>
						<td>
							<input name="oldLoanPerson" id="oldLoanPerson" data-options="validType:'length[0,20]'" class="easyui-textbox" style="width:135px;"/>
						</td>
						<td class="label_right">联系电话：</td>
						<td>
							 <input name="oldLoanPhone" id="oldLoanPhone" data-options="validType:'telephone'"  class="easyui-textbox" style="width:135px;"/>
						</td>
					</tr>
				 </table>
			</form>	  
		</div>
		<!-- 新增原贷款信息结束 -->
	</div>
	 <div title="尽职调查" id="tab2" >
		
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