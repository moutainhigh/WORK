<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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
<title>业务申请</title>
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

var pid = "${project.pid}";
var editType = "${editType}";

/**************工作流字段 begin******************/
//业务申请处理
var WORKFLOW_ID = "businessApplyRequestProcess"; 
var workflowTaskDefKey = "task_BusinessRequest";
var nextRoleCode = "RISK_ONE";//
/**************工作流字段 end********************/
var projectId = '${project.pid}';
var refId = '${project.pid}';
isEdit= '${isEdit}';
$(function() {
	setCombobox3("areaCode","COOPERATION_CITY","${project.areaCode}");
	setCombobox("businessCategory","BUSINESS_CATEGORY","${project.businessCategory}");
	setCombobox("businessSource","BUSINESS_SOURCE","${project.businessSource}");
	searchPartnerSource('${project.businessSourceNo}');
	$("#areaCode").combobox("disable");
	$("#businessCategory").combobox("disable");
	if(editType == '3'){
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('disabled','disabled');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('readOnly','readOnly');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}
	setBankCombobox("oldLoanBank",0,"${project.projectForeclosure.oldLoanBank}");
	setBankCombobox("newLoanBank",0,"${project.projectForeclosure.newLoanBank}");
	setBankCombobox("accumulationFundBank",0,"${project.projectForeclosure.accumulationFundBank}");
	setBankCombobox("superviseDepartment",0,"${project.projectForeclosure.superviseDepartment}");
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
	  			p.panel('refresh','<%=basePath%>bizProjectController/navigationInvestigation.action?projectId='+projectId);
	  		}
	  		// 2.合同
	  		else if(title == "合同"){
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>bizProjectController/navigationContract.action?projectId='+projectId+'&contractCatelog=CREDIT_CONTRACT');
	  		}
	  		// 3.借据及还款计划表
	  		else if(title == "借据及还款计划表"){
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>bizProjectController/receiptRepaymentList.action?projectId='+projectId+'&contractCatelog=JJ');
	  		}
	  		// 4.资料上传
	  		else if(title == "资料上传"){ 
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>bizProjectController/navigationDatum.action');
	  		}// 5.赎楼资料清单
	  		else if(title == "赎楼清单"){ 
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>bizProjectController/navigationForeInformation.action?projectId='+projectId);
	  		}
	  		// 6.客户经理申请办理
	  		else if(title == "申请办理"){ 
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","请先保存当前操作,才能执行下一步操作!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}
	  			p.panel('refresh','<%=basePath%>bizProjectController/navigationApplyInfo.action?projectId='+projectId);
	  		}
		}
	});
	//setTimeout("changeEvent()",1);
	$('.loanworkflowWrapDiv').css('width',$('#tab1 .easyui-panel').width());
	$('.loanworkflowWrapDiv .cus_table').css('width',$('#tab1 .easyui-panel').width());
	
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
        		}
        		if(businessCategory == 13755){
        			$("#fundsMoney").numberbox("enable");
        			$("#superviseDepartment").combobox("enable");
        			$("#buyerName").textbox("enable");
        			$("#buyerCardNo").textbox("enable");
        			$("#buyerPhone").textbox("enable");
        			$("#buyerAddress").textbox("enable");
        			$("#superviseAccount").textbox("enable");
        		}
        }
	});
	$("#loanMoney").numberbox({
	    "onChange":function(){
	    	getGuaranteeFee();
	    }
	});

$("#loanDays").numberbox({
    "onChange":function(){
    	getGuaranteeFee();
    }
  });
  
$("#orgCustomerCard").textbox({
    "onChange":function(){
    	var certNum = $("#orgCustomerCard").textbox("getValue");
    	if( certNum !='' &&(!IdCardValidate(certNum))){
    		$.messager.alert('操作提示',"输入的身份证号长度不对，或者身份证号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。",'error');
    	     $("#orgCustomerCard").textbox('setValue',"");
    	     return false;
    	}
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
		  checkCardNo('sellerCardNo');
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
		  checkCardNo('buyerCardNo');
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
});

function caozuofiter(value,row,index){
	var downloadDom = "<a href='${basePath}beforeLoanController/downloadData.action?path="+row.fileUrl+"&fileName="+row.fileName+"' class='download'><font color='blue'>下载</font></a>";
	return downloadDom;
}

function sizeFileter(value,row,index){
	if(row.fileSize>0){
		return  parseInt(row.fileSize/1024)+" KB";
	}else{
		return '';
	}
}

function saveProjectInfo(){
	var businessCategory = $("#businessCategory").combobox('getValue');
	if(businessCategory == 13755){//交易
		if($("#superviseDepartment").combobox('getValue') == -1){
			$.messager.alert('操作提示',"交易类赎楼，监管银行不允许为空!",'error');	
			return false;
		}
		if($("#fundsMoney").numberbox('getValue') < 0.0){
			$.messager.alert('操作提示',"交易类赎楼，资金监管金额必须大于0!",'error');	
			return false;
		}
		
		if($("#superviseAccount").textbox('getValue') ==''){
			$.messager.alert('操作提示',"交易类赎楼，资金监管收款账号不允许为空!",'error');	
			return false;
		}

		/* if($("#supersionReceBank").textbox('getValue') == ''){
			$.messager.alert('操作提示',"交易类赎楼，资金监管开户行不允许为空!",'error');	
			return false;
		}
		if($("#supersionReceName").textbox('getValue') == ''){
			$.messager.alert('操作提示',"交易类赎楼，资金监管收款户名不允许为空!",'error');	
			return false;
		} */
		
		if($("#buyerName").textbox('getValue') ==''){
			$.messager.alert('操作提示',"交易类赎楼，买方姓名不允许为空!",'error');	
			return false;
		}
		
		if($("#buyerCardNo").textbox('getValue')==''){
			$.messager.alert('操作提示',"交易类赎楼，买方身份证号不允许为空!",'error');	
			return false;
		}
	}
	$("#orgForm").form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$.messager.alert('操作提示','保存信息成功','info');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
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
	    	}else{
	    		$("#partner_source_no").val(-1);
	    	}
	    	
		}
	});	
}
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
 * 校验卖方电话号码
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
	<div title="业务基本信息" id="tab1">
	<form action="<%=basePath%>bizProjectController/saveProjectInfo.action" id="orgForm" name="orgForm" method="post">
		<div style="padding: 30px 10px 0 10px;">
		<div class="easyui-panel" title="业务基本信息" data-options="collapsible:true" width="1039px;">
			<div id="personal" style="padding:10px 0 15px 0;">
				<input type="hidden" name="pid" id="pid" value="${project.pid }">
				<input type="hidden" name="pmUserId" id="pmUserId" value="${project.pmUserId == 0 ?currUser.pid :project.pmUserId }">
        		<input type="hidden" name="projectForeclosure.pid" id="foreclosureId" value="${project.projectForeclosure.pid}">
        		<input type="hidden" name="projectProperty.pid" id="propertyId" value="${project.projectProperty.pid}">
        		<input type="hidden" name="projectGuarantee.pid" id="guaranteeId" value="${project.projectGuarantee.pid}">
				<input type="hidden" name="businessCategory" value="${project.businessCategory}">
				<input type="hidden" name="loanRate" value="${project.loanRate}">
				<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
					<tr>
						<td class="label_right"><font color="red">*</font>客户姓名：</td>
						<td>
							<input class="easyui-textbox" name="orgCustomerName" data-options="required:true,validType:'length[1,20]'" value="${project.orgCustomerName }" style="width:150px;"/>
						</td>
						<td class="label_right"><font color="red">*</font>客户电话：</td>
						<td>
							<input  class="easyui-textbox" value="${project.orgCustomerPhone}"  data-options="required:true,validType:'telephone'" name="orgCustomerPhone" style="width:150px;"/>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>身份证号：</td>
						<td>
							<input  class="easyui-textbox" value="${project.orgCustomerCard}" id="orgCustomerCard" data-options="required:true,validType:'length[1,20]'" name="orgCustomerCard" style="width:150px;"/>
						</td>
						<td class="label_right"><font color="red">*</font>借款金额：</td>
						<td>
							<input class="easyui-numberbox" name="planLoanMoney" id="loanMoney" value="${project.planLoanMoney}" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" style="width:150px;"/>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>借款天数：</td>
						<td>
							<input  class="easyui-numberbox" value="${project.projectForeclosure.loanDays}" id="loanDays" name="projectForeclosure.loanDays"  data-options="required:true,min:1,max:9999,required:true,validType:'integer'"   style="width:150px;"/>
						</td>
						<td class="label_right"><font color="red">*</font>咨询费：</td>
						<td>
							<input  class="easyui-numberbox" value="${project.projectGuarantee.guaranteeFee}" id="guaranteeFee" name="projectGuarantee.guaranteeFee" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"  style="width:150px;"/>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>所属城市：</td>
						<td>
							<input name="areaCode" id="areaCode" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
						</td>
						<td class="label_right"><font color="red">*</font>希望放款日期：</td>
						<td>
							<input value="${project.projectForeclosure.receDate}"  class="easyui-datebox" name="projectForeclosure.receDate" editable="false" style="width:150px;"  />
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>业务类型：</td>
						<td>
							<input id="businessCategory" disabled="disabled" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
						</td>
						<td class="label_right1"><font color="red">*</font>费率：</td>
						<td>
							<input name="projectGuarantee.feeRate" style="width:129px;" id="feeRate" readonly="readonly" value="${project.projectGuarantee.feeRate }" class="easyui-numberbox" data-options="min:0,max:100,precision:2,groupSeparator:','"/>%
						</td> 
					</tr>
					<tr>
					<td class="label_right"><font color="red">*</font>业务来源：</td>
					<td>
						<input id="businessSource" disabled="disabled" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
						<select id="partner_source_no" class="select_style" disabled="disabled" data-options="validType:'selrequired'" style="width: 200px;height: 26px;border-radius: 5px;border-color: #95B8E7;">
		 						<option value="-1">请选择</option>
						</select>
					</td>
					</tr>
					<tr>
					<td class="label_right"><font color="red">*</font>经办人：</td>
					<td>
						<input  class="easyui-textbox" readonly="readonly" data-options="required:true,validType:'length[1,20]'" required="true" name="managers"  value="${empty project.managers?login.realName:project.managers } " />
					</td>
					<td class="label_right"><font color="red">*</font>经办人电话：</td>
					<td>
						<input  class="easyui-textbox" readonly="readonly" data-options="required:true,validType:'telephone'" required="true" name="managersPhone" value="${empty project.managersPhone?login.phone:project.managersPhone }" />
					</td>
				</tr>
					<tr>
						<td class="label_right">备注：</td>
						<td colspan="10">
							<textarea rows="5" style="width:65%;" cols="10" class="form-control" name="surveyResult" id="surveyResult" placeholder="备注"  maxlength="200">${project.surveyResult}</textarea>
						</td>
					</tr>
					
				</table>
			</div>
		</div>
		</div>
		<!-- 赎楼信息开始 -->
		<div style="padding:30px 10px 0 10px;">
		<div class=" easyui-panel" title="赎楼" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>原贷款银行：</td>
					<td>
						<select id="oldLoanBank" class="easyui-combobox"  data-options="validType:'selrequired'" name="projectForeclosure.oldLoanBank" editable="false" style="width:129px;" />
					</td>
					<td class="label_right1"><font color="red">*</font>原贷款金额：</td>
					<td>
						<input class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" value="${project.projectForeclosure.oldLoanMoney }" name="projectForeclosure.oldLoanMoney" style="width:129px;"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>原贷款欠款金额：</td>
					<td>
						 <input name="projectForeclosure.oldOwedAmount" class="easyui-numberbox"  required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" value="${project.projectForeclosure.oldOwedAmount }" style="width:129px;"/>
					</td>
					<td class="label_right">贷款结束时间：</td>
					<td>
						 <input name="projectForeclosure.oldLoanTime"  editable="false"  class="easyui-datebox" value="${project.projectForeclosure.oldLoanTime }" style="width:129px;"/>
					</td>
				</tr>
				<tr>
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
					<td class="label_right"><font color="red">*</font>新贷款银行：</td>
					<td>
						<select id="newLoanBank" class="easyui-combobox"  data-options="validType:'selrequired'" name="projectForeclosure.newLoanBank" editable="false" style="width:129px;" />
					</td>
					<td class="label_right"><font color="red">*</font>新贷款金额：</td>
					<td>
						<input class="easyui-numberbox" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.newLoanMoney" value="${project.projectForeclosure.newLoanMoney }" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1">银行联系人：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[0,20]'" name="projectForeclosure.newLoanPerson" value="${project.projectForeclosure.newLoanPerson }" style="width:129px;"/>
					</td>
					<td class="label_right">联系电话：</td>
					<td>
						 <input name="projectForeclosure.newLoanPhone" data-options="validType:'telephone'" value="${project.projectForeclosure.newLoanPhone }"  class="easyui-textbox" style="width:129px;"/>
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
					<td class="label_right">赎楼账号：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[0,50]'" name="projectForeclosure.foreAccount" value="${project.projectForeclosure.foreAccount }" style="width:150px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">公积金银行：</td>
					<td>
						<select id="accumulationFundBank" class="easyui-combobox" name="projectForeclosure.accumulationFundBank" editable="false" style="width:129px;" />
					</td>
					<td class="label_right">公积金贷款金额：</td>
					<td>
						<input class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.accumulationFundMoney" value="${project.projectForeclosure.accumulationFundMoney }" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">监管银行：</td>
					<td>
						<select id="superviseDepartment" class="easyui-combobox" name="projectForeclosure.superviseDepartment" editable="false" style="width:129px;" />
					</td>
					<td class="label_right">资金监管金额：</td>
					<td>
						<input class="easyui-numberbox" id="fundsMoney" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.fundsMoney" value="${project.projectForeclosure.fundsMoney }" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">监管账号：</td>
					<td>
						<input class="easyui-textbox" data-options="validType:'length[0,50]'" id="superviseAccount" name="projectForeclosure.superviseAccount" value="${project.projectForeclosure.superviseAccount }" style="width:150px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>委托公证日期：</td>
					<td>
						 <input name="projectForeclosure.notarizationDate" required="true" value="${project.projectForeclosure.notarizationDate }" class="easyui-datebox" editable="false" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1">签暑合同日期：</td>
					<td>
						<input class="easyui-datebox" editable="false" name="projectForeclosure.signDate" value="${project.projectForeclosure.signDate }" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>需要保后监管：</td>
					<td>
						<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="isNeedHandle" style="width:129px;">
							<option value="-1">--请选择--</option>
							<option value="1" <c:if test="${project.isNeedHandle==1 }">selected </c:if>>需要</option>
							<option value="2" <c:if test="${project.isNeedHandle==2 }">selected </c:if>>不需要</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		</div>
		</div>
		
		<div  style="padding:30px 10px 0 10px;">
		<div class=" easyui-panel" title="物业及买卖双方信息" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>物业名称：</td>
					<td>
						<input name="projectProperty.houseName" data-options="required:true,validType:'length[1,50]'" value="${project.projectProperty.houseName }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>原价：</td>
					<td>
						<input class="easyui-numberbox" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" name="projectProperty.costMoney" value="${project.projectProperty.costMoney }"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>面积：</td>
					<td>
						<input name="projectProperty.area" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" style="width:119px;" class="easyui-numberbox" value="${project.projectProperty.area }"/>㎡
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
					<td class="label_right1">评估价格：</td>
					<td>
						 <input name="projectProperty.evaluationPrice" id="evaluationPrice" value="${project.projectProperty.evaluationPrice }" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1" id="evaluationPrice_lable">成交价：</td>
					<td>
						 <input name="projectProperty.tranasctionMoney" id="evaluationPrice" value="${project.projectProperty.tranasctionMoney }" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>赎楼成数：</td>
					<td>
						 <input name="projectProperty.foreRate" id="foreRate" readonly="readonly" value="${project.projectProperty.foreRate }" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" style="width:119px;"  class="easyui-numberbox"/>%
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>业主姓名：</td>
					<td>
						 <input name="projectProperty.sellerName" id="sellerName" data-options="prompt:'两个以上请用英文逗号隔开！',required:true,validType:'length[1,20]'" value="${project.projectProperty.sellerName }"  class="easyui-textbox" style="width:129px;"/>
					</td>
					<td class="label_right1"><font color="red">*</font>身份证号：</td>
					<td>
						<input name="projectProperty.sellerCardNo" id="sellerCardNo" data-options="prompt:'两个以上请用英文逗号隔开！',required:true,validType:'length[1,100]'" value="${project.projectProperty.sellerCardNo }"  class="easyui-textbox" style="width:259px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>联系电话：</td>
					<td>
						<input name="projectProperty.sellerPhone" id="sellerPhone" data-options="prompt:'两个以上请用英文逗号隔开！',required:true,validType:'length[1,100]'" value="${project.projectProperty.sellerPhone }"  class="easyui-textbox" style="width:229px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>家庭住址：</td>
					<td>
						 <input name="projectProperty.sellerAddress" id="sellerAddress" data-options="prompt:'两个以上请用英文逗号隔开！',required:true,validType:'length[1,100]'" value="${project.projectProperty.sellerAddress }"  class="easyui-textbox" style="width:259px;"/>
					</td>
				</tr>

				<tr>
					<td class="label_right"><font color="red">*</font>买方名称：</td>
					<td>
						 <input name="projectProperty.buyerName" id="buyerName" data-options="prompt:'两个以上请用英文逗号隔开！',validType:'length[0,20]'" value="${project.projectProperty.buyerName }"  class="easyui-textbox" style="width:129px;"/>
					</td>
					<td class="label_right1"><font color="red">*</font>身份证号：</td>
					<td>
						<input name="projectProperty.buyerCardNo" id="buyerCardNo" data-options="prompt:'两个以上请用英文逗号隔开！',validType:'length[0,50]'" value="${project.projectProperty.buyerCardNo }"  class="easyui-textbox" style="width:259px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">联系电话：</td>
					<td>
						<input name="projectProperty.buyerPhone" id="buyerPhone" data-options="prompt:'两个以上请用英文逗号隔开！',validType:'length[1,100]'" value="${project.projectProperty.buyerPhone }"  class="easyui-textbox" style="width:229px;"/>
					</td>
					<td class="label_right">家庭地址：</td>
					<td>
						 <input name="projectProperty.buyerAddress" id="buyerAddress" data-options="prompt:'两个以上请用英文逗号隔开！',validType:'length[0,100]'" value="${project.projectProperty.buyerAddress }"  class="easyui-textbox" style="width:259px;"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
		</div>
		
		<div style="padding-top: 10px;">
			<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveProjectInfo()">保存</a>
		</div>
		</form>
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
			<%@ include file="../../common/loanWorkflow.jsp"%> 
			<%@ include file="../../common/task_hi_list.jsp"%>
		</div>
	</div>
</div>
</div>
</div>
</div>
</body>
</html>