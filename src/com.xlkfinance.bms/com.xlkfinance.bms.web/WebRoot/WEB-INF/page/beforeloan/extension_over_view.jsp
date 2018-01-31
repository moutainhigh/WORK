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

.table_css th{ background: linear-gradient(to bottom,#EFF5FF 0,#E0ECFF 100%);
background-repeat: repeat-x;padding: 7px 5px;font-size: 12px;border-bottom: 1px #ddd solid;border-right: 1px #ddd solid;}
.table_css tr{ background:#fff;}
.table_css tr:nth-child(even){ background:#fff;}
.table_css tr:nth-child(odd){ background:#f9f9f9;}
.table_css td {padding: 7px 5px;font-size: 12px;border-bottom: 1px #ddd solid;border-right: 1px #ddd solid;
}
.hidden_css{
display: none;
}
</style>
</head>
<script type="text/javascript">
var projectId = ${project.pid};// 项目ID
var bianzhi = -1;// 流程设置为1,编辑设置为2,查看为3
var acct_id=0; // 账户id 
$(document).ready(function(){
	$('#tabs').tabs({
		 width: $("#tabs").parents().find('body').width()-18,  
		onSelect:function(title){
	  		var p = $(this).tabs('getTab', title);
	  		// 判断属于那个title
	  		// 1.业务实时流程图
	  		if(title == "业务实时流程图"){
	  			if(projectId == -1){
	  				$.messager.alert("操作提示","系统错误，请联系后台管理员!","error");
	  				// 返回当前第一个选项卡
	  				$("#tabs").tabs("select", 0);
	  				return false;
	  			}	
	  			p.panel('refresh','<%=basePath%>secondBeforeLoanController/toProjectFlow.action?projectId='+projectId);
	  		}
		}
	});
	
	setCombobox("businessCategory","BUSINESS_CATEGORY","${project.businessCategory}");
	var productId = "${project.productId}";
	$('#productId').combobox({   
		url:'${basePath}productController/getAllProductList.action',    
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

	setCombobox("address","ADDRESS_VALUE","${project.address}");
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
	        }
		}); 
	}
	/***************/
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
		var urlSr = "<%=basePath%>secondBeforeLoanController/getSurveyReportByProjectId.action?projectId="+projectId;
		// 获取尽职调查报告的Id
		$.ajax({
			url:urlSr,
			type:"post",
			dataType:"json",
		  	success:function(data){
		  		if(data.pid){
		  			$("#specialDesc").textbox('setValue',data.specialDesc);
		  			surPid = data.pid;
		  		}
			}
		});
	}
	
	$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('disabled', 'disabled');
	$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
	$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').addClass('l-btn-disabled');
	$('#tabs .easyui-linkbutton:not(.download)').removeAttr('onclick');

});

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
	       			if(value == orgInfo.orgId){
	       				optionStr +=" checked ";
	       			}
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
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
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
			<input type="hidden" id="productType" name="productType"/>
			<input type="hidden" name="pid" value="${project.pid}" />
			<input type="hidden" name="businessSourceNo" value="${project.businessSourceNo}" id="businessSourceNo"/>
			<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
				<tr>
					<td align="right" width="80" height="28">项目名称：</td>
					<td><input id="projectName" value="${project.projectName}" name="projectName" type="text" style="width: 200px;color: red;" class="text_style" readonly="readonly"> </td>
					<td align="right" width="80" height="28">项目编号：</td>
					<td ><input id="projectNumber" value="${project.projectNumber}" name="projectNumber" type="text" style="width: 200px;color: red;" class="text_style" readonly="readonly" > </td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>产品名称：</td>
					<td>
						<input name="productId" id="productId" disabled="disabled" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" />
					</td>
					<td class="label_right"><font color="red">*</font>业务类型：</td>
					<td>
						<input name="businessCategory" id="businessCategory" disabled="disabled" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" />
					</td>
					<td class="label_right"><font color="red">*</font>业务来源：</td>
					<td>
						<input name="businessSource" id="businessSource" disabled="disabled" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" />
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
					<td class="label_right"><font color="red">*</font>经办人：</td>
					<td>
						<input  class="easyui-textbox" readonly="readonly" data-options="validType:'length[1,20]'"  name="managers"  value="${empty project.managers?login.realName:project.managers } " />
					</td>
					<td class="label_right"><font color="red">*</font>经办人电话：</td>
					<td>
						<input  class="easyui-textbox" readonly="readonly" data-options="validType:'phone'"  name="managersPhone" value="${empty project.managersPhone?login.phone:project.managersPhone }" />
					</td>
					<td class="label_right"><font color="red">*</font>内外单：</td>
					<td class="nwd">
						<select id="innerOrOut" name="innerOrOut" disabled="disabled" class="easyui-combobox"  data-options="validType:'selrequired'" editable="false" style="width:129px;" />
					</td>
					<td class="label_right"><font color="red">*</font>报单员：</td>
					<td>
						 <input name="declaration" value="${project.declaration}" data-options="validType:'length[1,20]'" id="declaration"  class="easyui-textbox" panelHeight="auto"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">业务联系人：</td>
					<td>
						 <input name="businessContacts" value="${project.businessContacts}" data-options="validType:'length[0,20]'" id="businessContacts"  class="easyui-textbox" panelHeight="auto"/>
					</td>
					<td class="label_right">联系电话：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'phone'" name="contactsPhone" value="${project.contactsPhone}" />
					</td>
				</tr>
				
				<%-- <tr>
					<td class="label_right"><font color="red">*</font>计划放款日期：</td>
					<td>
						 <input name="projectForeclosure.receDate"  value="${project.projectForeclosure.receDate }" class="easyui-datebox" editable="false" style="width:129px;"/>
					</td>
				</tr> --%>
			</table>
			<table class="beforeloanTable" width="800">
				<tr>
					<td class="label_right">客户姓名：</td>
					<td width="60">
					<a id='chinaNamema'  onclick='editPerBase()' href='#'  style="color:blue;float: left;width: 45px;padding-left: 5px; ">${cpyName}</a>
					 <input name="chinaName" type="hidden"  class="no_frame text_style"  disabled="true">
					 <!-- <a class="easyui-linkbutton personalbtn"  id="readtype1" onclick="selectPersonal()">选择借款人</a>
						<a class="easyui-linkbutton personalbtn"  id="readtype2"  onclick="createPersonal()">创建借款人</a> -->
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
					<!-- <a href="javascript:void(0)" id="gtjkrAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openPersonalPublic()">新增共同借款人</a> -->
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
							<th data-options="field:'chinaName'" >姓名</th>
							<th data-options="field:'certTypeName'" >证件类型</th>
							<th data-options="field:'certNumber'" >证件号号码</th>
							<th data-options="field:'relationText'" >与客户关系</th>
							<th data-options="field:'perTelephone'" >手机号码</th>
							<th data-options="field:'proportionProperty'" >产权占比</th>
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
					<td class="label_right"><font color="red">*</font>新贷款金额：</td>
					<td>
						<input class="easyui-numberbox"  data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.newLoanMoney" value="${project.projectForeclosure.newLoanMoney }" style="width:129px;"/>
					</td>
					<td class="label_right1">银行联系人：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[0,20]'" name="projectForeclosure.newLoanPerson" value="${project.projectForeclosure.newLoanPerson }" style="width:129px;"/>
					</td>
					<td class="label_right">联系电话：</td>
					<td>
						 <input name="projectForeclosure.newLoanPhone" data-options="validType:'phone'" value="${project.projectForeclosure.newLoanPhone }"  class="easyui-textbox" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>新贷款开户行：</td>
					<td>
						<input  class="easyui-textbox" data-options="required:true,validType:'length[1,20]'" name="projectForeclosure.newReceBank" value="${project.projectForeclosure.newReceBank }" style="width:129px;"/>
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
					<td class="label_right"><font color="red">*</font>资金监管金额：</td>
					<td>
						<input class="easyui-numberbox"  id="fundsMoney" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.fundsMoney" value="${project.projectForeclosure.fundsMoney }" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>资金监管开户行：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[0,30]'" id="supersionReceBank" name="projectForeclosure.supersionReceBank" value="${project.projectForeclosure.supersionReceBank }" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>资金监管收款户名：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[0,30]'" id="supersionReceName" name="projectForeclosure.supersionReceName" value="${project.projectForeclosure.supersionReceName }" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>资金监管收款账号：</td>
					<td colspan="3">
						<input  class="easyui-textbox" data-options="validType:'length[0,50]'" id="superviseAccount" name="projectForeclosure.superviseAccount" value="${project.projectForeclosure.superviseAccount }" style="width:370px;"/>
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
							<option value="5" <c:if test="${project.projectForeclosure.paymentType==5 }">selected </c:if>>经营贷</option>
							<option value="6" <c:if test="${project.projectForeclosure.paymentType==6 }">selected </c:if>>消费贷</option>
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
		<div class=" easyui-panel" title="展期信息" data-options="collapsible:true" width="1039px;">
		<div style="padding: 10px 0 10px 10px;height: auto;line-height: 35px;" >
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>展期金额：</td>
					<td>
						<input id="loanMoney" value="${extension.extensionAmt }" class="easyui-numberbox" style="width:129px;" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
					<td class="label_right"><font color="red">*</font>展期天数：</td>
					<td>
						 <input value="${extension.extensionDays }" class="easyui-numberbox" style="width:129px;" data-options="min:0,max:9999,required:true,validType:'integer'"/>
					</td>
					<td class="label_right1"><font color="red">*</font>费率：</td>
					<td>
						<input style="width:129px;"  value="${extension.extensionRate }" class="easyui-numberbox"  data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>%
					</td> 
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>展期开始时间：</td>
					<td>
						<input style="width:129px;"  value="${project.projectForeclosure.paymentDate}" class="easyui-datebox" readonly="readonly" editable="false"/>
					</td> 
					<td class="label_right1"><font color="red">*</font>展期结束时间：</td>
					<td>
						<input style="width:129px;" id="extensionDate" value="${extension.extensionDate }" class="easyui-datebox" readonly="readonly" editable="false"/>
					</td>
					<td class="label_right"><font color="red">*</font>展期费：</td>
					<td>
						<input style="width:129px;" id="chargesSubsidized" readonly="readonly" value="${extension.extensionFee }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1">展期原因：</td>
					<td colspan="9">
						<input name="specialDesc" id="specialDesc" readonly="readonly" class="easyui-textbox" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,800]'"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
	</div>
	</div>
	</form>
	
	<!-- 项目总览 -->
	<%@ include file="../common/project_handle_view.jsp"%>    
   </div>
	</div>
	<c:if test="${project.projectSource != 1 }">
		<div title="业务实时流程图" id="tab2" >
		
		</div>
	</c:if>
	</div>
</div>

</body>
</html>