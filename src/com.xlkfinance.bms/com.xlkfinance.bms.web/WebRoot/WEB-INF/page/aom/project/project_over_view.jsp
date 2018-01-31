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
<title>业务申请详情</title>
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
	
	setCombobox3("areaCode","COOPERATION_CITY","${project.areaCode}");
	setCombobox("businessCategory","BUSINESS_CATEGORY","${project.businessCategory}");

	setBankCombobox("superviseDepartment",0,"${project.projectForeclosure.superviseDepartment}");
	
	setBankCombobox("oldLoanBank",0,"${project.projectForeclosure.oldLoanBank}");

	setBankCombobox("newLoanBank",0,"${project.projectForeclosure.newLoanBank}");

	setBankCombobox("accumulationFundBank",0,"${project.projectForeclosure.accumulationFundBank}");
	setCombobox("businessSource","BUSINESS_SOURCE","${project.businessSource}");
	searchPartnerSource('${project.businessSourceNo}');
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
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
	<div title="业务信息" id="tab1">
		<div style="padding: 30px 10px 0 10px;">
		<div class="easyui-panel" title="借款人信息及基础信息填写" data-options="collapsible:true" width="1039px;">
		<%-- begin 个人模版 --%>
		<div id="personal" style="padding:10px 0 15px 0;">
			<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
					<tr>
						<td class="label_right">客户姓名：</td>
						<td>
							<input class="easyui-textbox" name="orgCustomerName" value="${project.orgCustomerName }" style="width:150px;"  readonly="true"/>
						</td>
						<td class="label_right">客户电话：</td>
						<td>
							<input  class="easyui-textbox" value="${project.orgCustomerPhone}" name="orgCustomerPhone" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">身份证号：</td>
						<td>
							<input  class="easyui-textbox" value="${project.orgCustomerCard}" name="orgCustomerCard" style="width:150px;" readonly="true"/>
						</td>
						<td class="label_right">借款金额：</td>
						<td>
							<input  class="easyui-numberbox" name="planLoanMoney" value="${project.planLoanMoney}" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" readonly="true" style="width:150px;"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">所属城市：</td>
						<td>
							<input name="areaCode" id="areaCode" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
						</td>
						<td class="label_right">希望放款日期：</td>
						<td>
							<input value="${project.planLoanDate}"  class="easyui-datebox" name="planLoanDate" editable="false" style="width:150px;"  />
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>业务类型：</td>
						<td>
							<input name="businessCategory" id="businessCategory" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
						</td>
						<td class="label_right"><font color="red">*</font>需要保后监管：</td>
						<td>
							<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="isNeedHandle" style="width:135px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${project.isNeedHandle==1 }">selected </c:if>>需要</option>
								<option value="2" <c:if test="${project.isNeedHandle==2 }">selected </c:if>>不需要</option>
							</select>
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
							<input  class="easyui-textbox" readonly="readonly" data-options="required:true,validType:'length[1,20]'" required="true" value="${empty project.managers?login.realName:project.managers } " />
						</td>
						<td class="label_right"><font color="red">*</font>经办人电话：</td>
						<td>
							<input  class="easyui-textbox" readonly="readonly" data-options="required:true,validType:'telephone'" required="true" value="${empty project.managersPhone?login.phone:project.managersPhone }" />
						</td>
					</tr>
					<tr>
						<td class="label_right">备注：</td>
						<td colspan="4">
							<textarea rows="5" style="width:65%;" cols="10" readonly="readonly" class="form-control" id="surveyResult" placeholder="备注"  maxlength="200">${project.surveyResult}</textarea>
						</td>
					</tr>
				</table>
			</div>
		</div>
	
		
	<div  style="padding:10px 0 15px 0;">
		<div class=" easyui-panel" title="赎楼信息" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>原贷款银行：</td>
					<td>
						<select id="oldLoanBank" class="easyui-combobox"  data-options="validType:'selrequired'" name="projectForeclosure.oldLoanBank" editable="false" style="width:135px;" />
					</td>
					<td class="label_right1"><font color="red">*</font>原贷款金额：</td>
					<td>
						<input class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" value="${project.projectForeclosure.oldLoanMoney }" name="projectForeclosure.oldLoanMoney" style="width:135px;"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>原贷款欠款金额：</td>
					<td>
						 <input name="projectForeclosure.oldOwedAmount" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" value="${project.projectForeclosure.oldOwedAmount }" style="width:135px;"/>
					</td>
					<td class="label_right">贷款结束时间：</td>
					<td>
						 <input name="projectForeclosure.oldLoanTime"  editable="false"  class="easyui-datebox" value="${project.projectForeclosure.oldLoanTime }" style="width:135px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1">银行联系人：</td>
					<td>
						<input name="projectForeclosure.oldLoanPerson" data-options="validType:'length[0,20]'" value="${project.projectForeclosure.oldLoanPerson }" class="easyui-textbox" style="width:135px;"/>
					</td>
					<td class="label_right">联系电话：</td>
					<td>
						 <input name="projectForeclosure.oldLoanPhone" data-options="validType:'phone'" value="${project.projectForeclosure.oldLoanPhone }"  class="easyui-textbox" style="width:135px;"/>
					</td>
				</tr>
				
				<tr style="padding-top:10px;">
					<td class="label_right"><font color="red">*</font>新贷款银行：</td>
					<td>
						<select id="newLoanBank" class="easyui-combobox"  data-options="validType:'selrequired'" name="projectForeclosure.newLoanBank" editable="false" style="width:135px;" />
					</td>
					<td class="label_right"><font color="red">*</font>新贷款金额：</td>
					<td>
						<input class="easyui-numberbox" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.newLoanMoney" value="${project.projectForeclosure.newLoanMoney }" style="width:135px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1">银行联系人：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[0,20]'" name="projectForeclosure.newLoanPerson" value="${project.projectForeclosure.newLoanPerson }" style="width:135px;"/>
					</td>
					<td class="label_right">联系电话：</td>
					<td>
						 <input name="projectForeclosure.newLoanPhone" data-options="validType:'phone'" value="${project.projectForeclosure.newLoanPhone }"  class="easyui-textbox" style="width:135px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>借款方式：</td>
					<td>
						<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="projectForeclosure.paymentType" style="width:135px;">
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
						<select id="accumulationFundBank" class="easyui-combobox" name="projectForeclosure.accumulationFundBank" editable="false" style="width:135px;" />
					</td>
					<td class="label_right">公积金贷款金额：</td>
					<td>
						<input class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.accumulationFundMoney" value="${project.projectForeclosure.accumulationFundMoney }" style="width:135px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>监管银行：</td>
					<td>
						<select id="superviseDepartment" class="easyui-combobox" name="projectForeclosure.superviseDepartment" editable="false" style="width:135px;" />
					</td>
					<td class="label_right"><font color="red">*</font>资金监管金额：</td>
					<td>
						<input class="easyui-numberbox" required="true" id="fundsMoney" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="projectForeclosure.fundsMoney" value="${project.projectForeclosure.fundsMoney }" style="width:135px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>监管账号：</td>
					<td>
						<input class="easyui-textbox" data-options="validType:'length[0,50]'" id="superviseAccount" name="projectForeclosure.superviseAccount" value="${project.projectForeclosure.superviseAccount }" style="width:150px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>委托公证日期：</td>
					<td>
						 <input name="projectForeclosure.notarizationDate" required="true" value="${project.projectForeclosure.notarizationDate }" class="easyui-datebox" editable="false" style="width:135px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1">签暑合同日期：</td>
					<td>
						<input class="easyui-datebox" editable="false" name="projectForeclosure.signDate" value="${project.projectForeclosure.signDate }" style="width:135px;"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
		</div>
	
	<!-- 赎楼信息开始 -->
	<div  id="foreclosureInfo">
	<div style="padding:10px 0 15px 0;">
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
				</tr>
				<tr>
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
				</tr>
				<tr>
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
						<input name="projectGuarantee.loanMoney"  id="loanMoney" value="${project.projectGuarantee.loanMoney }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" style="width:135px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>借款天数：</td>
					<td>
						 <input name="projectForeclosure.loanDays"  data-options="min:1,max:9999,validType:'integer'" value="${project.projectForeclosure.loanDays }" class="easyui-numberbox" style="width:135px;"/>
					</td>
					<td class="label_right1"><font color="red">*</font>费率：</td>
					<td>
						<input name="projectGuarantee.feeRate" style="width:135px;"  value="${project.projectGuarantee.feeRate }" class="easyui-numberbox" data-options="min:0,max:100,precision:2,groupSeparator:','"/>%
					</td> 
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>咨询费：</td>
					<td>
						<input name="projectGuarantee.guaranteeFee" style="width:135px;"  value="${project.projectGuarantee.guaranteeFee }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td> 
					<td class="label_right1"><font color="red">*</font>手续费：</td>
					<td>
						<input name="projectGuarantee.poundage" style="width:135px;" id="poundage" value="${project.projectGuarantee.poundage }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
	</div>
	</div>
	<!-- 赎楼信息结束 -->
	
	<!-- 项目总览 -->
	<%@ include file="../../common/project_handle_view.jsp"%>
	</div>
	<div title="业务实时流程图" id="tab2" >
		
	</div>
	</div>
</div>

</body>
</html>