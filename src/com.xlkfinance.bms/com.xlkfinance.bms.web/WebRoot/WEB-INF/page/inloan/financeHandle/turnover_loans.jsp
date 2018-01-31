<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
<style type="text/css">
.table_css{ border:1px #95B8E7 solid;}
.table_css td{ padding:4px 10px; font-size:12px;border-bottom:1px #ddd solid;  border-right:1px #ddd solid;}
.table_css td:last-child{  border-right:0;}
.bb td b{ float:left; width:95px; text-align: right; padding-right: 10px;font-weight: normal;line-height: 22px;}
.bt{ background-color: #E0ECFF;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#EFF5FF,endColorstr=#E0ECFF,GradientType=0);
font-size: 12px;
font-weight: bold;
color: #0E2D5F;}
.bt td{padding:4px 10px;border-bottom:1px #95B8E7 solid;}
.srk1{ height:60px;width:70%;}
.table_css th {
	background: linear-gradient(to bottom, #EFF5FF 0, #E0ECFF 100%);
	background-repeat: repeat-x;
	padding: 7px 5px;
	font-size: 12px;
	border-bottom: 1px #ddd solid;
	border-right: 1px #ddd solid;
}

.table_css tr {
	background: #fff;
}

.table_css tr:nth-child(even) {
	background: #fff;
}

.table_css tr:nth-child(odd) {
	background: #f9f9f9;
}

.table_css td {
	padding: 7px 5px;
	font-size: 12px;
	border-bottom: 1px #ddd solid;
	border-right: 1px #ddd solid;
}

.hidden_css {
	display: none;
}
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript">
/**************工作流字段 begin******************/
//退佣金处理申请
var WORKFLOW_ID = "makeLoansProcess"; 
var workflowTaskDefKey = "task_Request";
nextRoleCode = "FUND_MANAGER";//
/**************工作流字段 end********************/
var projectId = ${financeIndexDTO.projectId};
var refId = ${financeIndexDTO.makeApplyFinanceIdTwo};
var makeLoansId = ${financeIndexDTO.makeApplyFinanceIdTwo == null ? -1 : financeIndexDTO.makeApplyFinanceIdTwo};
var makeApplyStatusTwo = ${financeIndexDTO.makeApplyStatusTwo};
	//初始化放款数据
   function initMakeLoans(){
		   var houseClerkId=${financeIndexDTO.houseClerkId};//赎楼员id
		   var financeHandleId=${financeIndexDTO.pid};//财务办理id
		   var projectId=${financeIndexDTO.projectId};//项目id
		   initHouseClerk(houseClerkId,projectId);//初始化实例员列表
	       //初始化是否完结单选按钮 start
		   var status=${financeIndexDTO.status};//放款状态
		   $('input:radio[name=isLoanFinish]')[0].checked = true;
		   $('input:radio[name=isLoanFinish]')[1].checked = false;
		   if(status==3){
		    	$('input:radio[name=isLoanFinish]')[0].checked = true;
		       $("input[type=radio][name=isLoanFinish][value=4]").removeAttr("checked");  
		    }else if(status==4){
		       $("input[type=radio][name=isLoanFinish][value=3]").removeAttr("checked");  
		       $('input:radio[name=isLoanFinish]')[1].checked = true;
		   }
	       //初始化是否完结单选按钮 end
		   $.ajax({
				url : "getApplyFinanceHandle.action",
				cache : true,
				type : "POST",
				data : {'projectId' : projectId,'financeHandleId' : financeHandleId,'recPros' : "3,5,6,9"},
				async : false,
				success : function(data, status) {
					var resultMap = eval("("+data+")");
					var resultList = resultMap.list;
					 for (var i = 0; i < 4; i++) {
					   var recPro=resultList[i].recPro;
					   var recMoney=resultList[i].recMoney;
					   var recAccount=resultList[i].recAccount;
					   var recMoney1 =resultList[1].recMoney;
					   var recDate=resultList[i].recDate;
					   var resource=resultList[i].resource;
					   var resourceMoney=resultList[i].resourceMoney;
					   var resource2=resultList[i].resource2;
					   var resourceMoney2=resultList[i].resourceMoney2;
					   var operator=resultList[i].operator;
					   var turnoverMoney = resultList[i].operator;
					   var remark=resultList[i].remark;
					   if (recPro==9) {//如果没有保存过放款金额，则把第一次放款金额初始化为借款金额
						   if(recMoney9 > 0 ){
							   $("#recMoney3").numberbox('setValue',recMoney9);   
						   }else{
							   $("#recMoney3").numberbox('setValue',${projectGuarantee.foreclosureMoney});
						   }
   					   	   $("#recMoney9").numberbox('setValue',${projectGuarantee.turnoverMoney});
	   					   	 if(resourceMoney > 0){
	   					   	    $("#resourceMoney9").numberbox('setValue',resourceMoney);
	   					   	 }else{
	   					   	   $("#resourceMoney9").numberbox('setValue',${projectGuarantee.turnoverMoney});
	   					   	 }
	   					    if(resourceMoney2 > 0){
	  					   	   $("#resourceMoney25").numberbox('setValue',resourceMoney2);
	  					   	 }
	   					    if(recMoney > 0){
		  					   	 $("#recMoney9").numberbox('setValue',recMoney);
		  					}
					   } else {
   					      $("#recMoney3").numberbox('setValue',recMoney);
					   } 
   					   //有保存过，则选择保存的值
   					   var projectSource=${financeIndexDTO.projectSource};
   					   if(projectSource==1&&${userSource==1}){//万通
		                   if(recAccount==null||recAccount==""){
	                           //还没有保存过，默认第一个
	                           setCombobox3("recAccount"+recPro,"WT_MAKE_LOANS_BANK",'-1');
	                       }else{
		                	   setCombobox3("recAccount"+recPro,"WT_MAKE_LOANS_BANK",recAccount);
	                       }
	                   }else{//小科
		                   if(recAccount==null||recAccount==""){
	                           //还没有保存过，默认第一个
	                           setCombobox3("recAccount"+recPro,"MAKE_LOANS_BANK",'4100627824511118');
	                       }else{
		                	   setCombobox3("recAccount"+recPro,"MAKE_LOANS_BANK",recAccount);
	                       }
	                   }
   					   if(recDate==null||recDate==""){
   					      $("#recDate"+recPro).datebox('setValue',formatterDate(new Date()));
   					   }else{
   					      $("#recDate"+recPro).datebox('setValue',recDate);
   					   }
   					   if(resource==null||resource==""){
   						  setCapitalOrgInfo("resource"+recPro,'ziyouzijin')
   					   }else{
   						  setCapitalOrgInfo("resource"+recPro,resource)
   					   }
   					   if(resourceMoney>0){
 					      $("#resourceMoney3").numberbox('setValue',resourceMoney);
 					   }
   					   //
   					   if(resource2==null||resource2==""){
   						  setCapitalOrgInfo("resource2"+recPro,'ziyouzijin')
   					   }else{
   						   setCapitalOrgInfo("resource2"+recPro,resource2)
   					   }
   					   if (projectSource==1&&${userSource==1}) {//万通的数据并且是万通的人在操作，款项来源只有自有资金
   						 $("#resource2"+recPro).combobox({ readonly: true });
   						 $("#resource"+recPro).combobox({ readonly: true });
					   }
   					   if(resourceMoney2>0){
					      $("#resourceMoney2"+recPro).numberbox('setValue',resourceMoney2);
					   }
   					   //操作人为空，取当前登录用户
   					   if(operator!=null&&operator.length>0){
   					      $("#operator"+recPro).textbox('setValue',operator);
   					   }else{
   					      $("#operator"+recPro).textbox('setValue','${currUser.realName}');
   					   }
   					   $("#financeHandleId"+recPro).val(resultList[i].financeHandleId);
   					   $("#pid"+recPro).val(resultList[i].pid);
   					   $("#remark9").val(remark);
   					   if (recPro==9) {
   						$("#displayRemark").val(remark);//备注
					   }
					}
					var recMoney3=0;
		    		if ($('#recMoney3').numberbox('getValue').length>0) {
		    			recMoney3=parseFloat($('#recMoney3').numberbox('getValue'));//第二次放款
					}
				}
			});
	}
   //提交放款
   function subForm(formId) {
			var formName = "makeLoansForm";
			var formObj = document.getElementById(formName);
				var houseClerkId=$("#houseClerks").combobox('getValue');
				var isLoanFinish=$('input[name="isLoanFinish"]:checked ').val();
				
				var resourceMoney= 0.00;	
				var resourceMoney2= 0.00;
				
				if($('#resourceMoney9').numberbox('getValue') == null || $('#resourceMoney9').numberbox('getValue') == ''){
					resourceMoney = $('#resourceMoney9').numberbox('setValue',resourceMoney);//款项来源1
					}
				if($('#resourceMoney25').numberbox('getValue') == null || $('#resourceMoney25').numberbox('getValue') == ''){
					resourceMoney2 = $('#resourceMoney25').numberbox('setValue',resourceMoney2);//款项来源2
				}
				
				resourceMoney = parseFloat($('#resourceMoney9').numberbox('getValue'));//款项来源2
				resourceMoney2 = parseFloat($('#resourceMoney25').numberbox('getValue'));//款项来源2
				  $("#resourceMoney9").numberbox('setValue',resourceMoney);
				  $("#resourceMoney25").numberbox('setValue',resourceMoney2);
				  //$("#recMoney9").numberbox('setValue',(resourceMoney+resourceMoney2));
				$.ajax({
							url : "${basePath}financeHandleController/makeLoans.action?houseClerkId="+houseClerkId+"&isLoanFinish="+isLoanFinish,
							cache : true,
							type : "POST",
							data : $("#" + formName).serialize(),
							async : false,
							success : function(data, status) {
								var ret = eval("(" + data + ")");
								if (ret && ret.header["success"]) {
									$("#subMsg").attr("style","color: green").html("（保存成功）").show(300).delay(3000).hide(1300);
								    // 必须重置 datagr 选中的行 
									$("#grid").datagrid("clearChecked");
									$("#grid").datagrid('reload'); 
									//$("#makeLoansForm").form('reset');
								} else {
									$("#subMsg").attr("style","color: red").html(" ("+ret.header["msg"]+")").show(300).delay(3000).hide(1300);
								}
								
							}
						});
// 			}
	}

	function changeRemark(formId, valStr) {
			document.getElementById("remark9").value = valStr;
	}
	/**
	 * 初始化实例员列表
	 */
	function initHouseClerk(houseClerkId,projectId) {
		$('#houseClerks').val(null);
		$('#houseClerks')
				.combobox(
						{
							url : BASE_PATH
									+ 'systemRoleController/findUsersByRoleCodeAndProjectId.action?roleCode=HOUSE_CLERK&projectId='+projectId,
							valueField : 'pid',
							textField : 'realName',
							multiple : false,
							editable : false,
							onLoadSuccess:function(){
								//已有赎楼员，默认选择
  		                       if(houseClerkId!=null&&houseClerkId>0){
  		                    	  $("#houseClerks").combobox('select',houseClerkId);
  	                          	}else{
  	                          	//还没有选择赎楼员，默认第一个
  								  var data = $('#houseClerks').combobox('getData');
  								  $("#houseClerks").combobox('select',data[0].pid);
  	                          	}
						    }
						});
	}
	
	$(document).ready(function() {
		initMakeLoans();
			 $('#recMoney3').numberbox({  
		        onChange:function(){  
		        	var makeLoansForm_3=document.getElementById("makeLoansForm_3");
		    		var recMoney3=0;//第二次次放款金额
		    		if (makeLoansForm_3.recMoney.value.length>0) {
		    			recMoney3=parseFloat(makeLoansForm_3.recMoney.value);
					}
		        }  
		    }); 
	 $('#resourceMoney9,#resourceMoney25').numberbox({
		        onChange:function(){  
		    		//第二次放款
		        	var makeLoansForm_5=document.getElementById("makeLoansForm");
		    		var resourceMoney5=0;//款项来源1金额
		    		if (makeLoansForm_5.resourceMoney.value.length>0) {
		    			resourceMoney5=parseFloat(makeLoansForm_5.resourceMoney.value);
					}
		    		var resourceMoney25=0;//款项来源2金额
		    		if (makeLoansForm_5.resourceMoney25.value.length>0) {
		    			resourceMoney25=parseFloat(makeLoansForm_5.resourceMoney2.value);
					}
		    		$("#recMoney9").numberbox('setValue',resourceMoney5+resourceMoney25);//第二次放款
		        }  
		    }); 
		 //只有驳回和申请中才能修改数据
		   $("input :text textarea select :radio :checkbox").attr( "disabled",true);
	});
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
   
   <div id="edit_dialog" style="width: 100%;padding:10px;" closed="true">
                选择赎楼员：<select class="easyui-combobox" id="houseClerks" name="houseClerks" style="width:100px">
       </select>
       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                 是否已完结：<input type="radio" name="isLoanFinish" checked="checked" value="1">是
             <input type="radio" name="isLoanFinish" value="-1">否
   <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="table_css" style="border-bottom: none;margin-top:5px;">
     <tr class="bt">
      <td width="150px">放款项目</td>
      <td>放款金额</td>
      <td>放款帐号</td>
      <td>放款日期</td>
      <td>款项来源1</td>
      <td>金额</td>
      <td>款项来源2</td>
      <td>金额</td>
      <td>操作员</td>
     </tr>
     <tr>
      <td>第一次放款（赎楼金额）<span class="subMsg1" > </span></td>
      <td><input name="recMoney" id="recMoney3" disabled="disabled"  class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width: 100px;"></td>
      <td>
      <input name="recAccount" id="recAccount3" disabled="disabled" data-options="validType:'selrequired'" class="easyui-combobox" editable="false"  required="true" style="width: 250px;"/>
      </td>
      <td><input name="recDate" id="recDate3" disabled="disabled" class="easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
      <input name="resource" id="resource3" disabled="disabled" style="width: 80px;" data-options="validType:'selrequired'" class="easyui-combobox" editable="false"  required="true"/>
      </td>
      <td>
      <input name="resourceMoney" id="resourceMoney3" disabled="disabled" style="width: 80px;" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" />
      </td>
      <td>
      <input name="resource2" id="resource23" disabled="disabled" style="width: 80px;" data-options="validType:'selrequired'" class="easyui-combobox" editable="false"  required="true"/>
      </td>
      <td>
      <input name="resourceMoney2" id="resourceMoney23"disabled="disabled"  style="width: 80px;" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" />
      </td>
      <td style="border-right:0;">
      <input name="operator" id="operator3" disabled="disabled" disabled="disabled" class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:60px"></td>
     </tr>
     </table>
     
     <form action="#" id="makeLoansForm" name="makeLoansForm"  method="post">
     <table width="100%;" border="0" cellpadding="0" cellspacing="0" class="table_css" style="border-bottom: none;border-top: none;">
     <tr>
      <td width="150px">第二次放款（周转金额）<span id="subMsg"></span></td>
      <td><input name="recMoney" id="recMoney9" class="easyui-numberbox"data-options="min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入逾期贷款利率!" precision="2" groupSeparator="," onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width: 100px;"></td>
      <td>
      <input name="recAccount" id="recAccount9" data-options="validType:'selrequired'" class="easyui-combobox" editable="false"  required="true" style="width: 250px;"/>
      </td>
      <td><input name="recDate" id="recDate9" class="easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" id="resource9" style="width: 80px;" data-options="validType:'selrequired'" class="easyui-combobox" editable="false"  required="true"/></td>
      <td><input name="resourceMoney" id="resourceMoney9" style="width: 80px;" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" /></td>
      <td><input name="resource2" id="resource25" style="width: 80px;" data-options="validType:'selrequired'" class="easyui-combobox" editable="false"  required="true"/></td>
      <td><input name="resourceMoney2" id="resourceMoney25" style="width: 80px;" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" /></td>
      <td style="border-right:0;"><input name="operator" id="operator5" disabled="disabled" class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:60px"></td>
      <input type="hidden" name="remark" id="remark9">
      <input type="hidden" name="recPro" value="9" >
      <input type="hidden" name="financeHandleId" id="financeHandleId9">
      <input type="hidden" name="pid" id="pid9">
     </tr>
     </table>
     </form>
      <td style="border-bottom: 0;" ><span>备注:</span>
	      <textarea id="displayRemark" onchange="changeRemark('5',this.value)" maxlength="500"  class="srk1 text_style" onfocus="this.style.color='#000';"
	        onBlur="this.style.color='#8d8d8d';">
	      </textarea>
       </td>
   </div>
    <a id="saveFile" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subForm('9')">保存</a>
	<div class="panel-body1 pt10"  style="padding: 10px;">
     <div class="easyui-panel" title="费用" data-options="collapsible:true" style="width:99%;">
      <table class="beforeloanTable">
				<tr>
					<td class="label_right1">借款金额：</td>
					<td>
						<input name="projectGuarantee.loanMoney" disabled="disabled" id="loanMoney" value="${projectGuarantee.loanMoney }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" style="width:129px;"/>
					</td>
					<td class="label_right">借款天数：</td>
					<td>
						 <input name="projectForeclosure.loanDays"  disabled="disabled" data-options="min:1,max:9999,validType:'integer'" value="${projectForeclosure.loanDays }" class="easyui-numberbox" style="width:129px;"/>
					</td>
					<td class="label_right1">费率：</td>
					<td>
						<input name="projectGuarantee.feeRate" disabled="disabled" style="width:129px;"  value="${projectGuarantee.feeRate }" class="easyui-numberbox" data-options="min:0,max:100,precision:2,groupSeparator:','"/>%
					</td> 
				</tr>
				<tr>
					<td class="label_right1">咨询费：</td>
					<td>
						<input name="projectGuarantee.guaranteeFee" disabled="disabled" style="width:129px;"  value="${projectGuarantee.guaranteeFee }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td> 
					<td class="label_right1">手续费：</td>
					<td>
						<input name="projectGuarantee.poundage" disabled="disabled" style="width:129px;" id="poundage" value="${projectGuarantee.poundage }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>
					</td>
				</tr>
			</table>
     </div>
    </div>
    <div class="panel-body1 pt10" style="padding: 10px;">
     <div class="easyui-panel" title="贷前审批总览" data-options="collapsible:true" style="width:99%;">
     <table cellpadding="0" cellspacing="0" class="table_css"
	style="width: 100%; border-top: 1px #ddd solid; border-left: 1px #ddd solid;">
	<tr>
		<th width="4%" align="center">序号</th>
		<th width="5%" align="center">状态</th>
		<th width="10%">办理步骤</th>
		<th align="center">内容</th>
		<th width="6%" align="center">操作人</th>
		<th width="13%" align="center">操作日期</th>
	</tr>
	<c:forEach var="dynamic" items="${dynamicList}" varStatus="status">
		<tr>
			<td align="center">${status.index+1}</td>
			<td align="center">
				<!-- 状态：未完成=1，进行中=2，已完成=3，失效=4 --> <c:choose>
					<c:when test="${dynamic.status==4}">
						<img src="${ctx}/p/xlkfinance/images/dynamicStatus_4.png"
							width="16" height="16" />
					</c:when>
					<c:when test="${dynamic.status==3}">
						<img src="${ctx}/p/xlkfinance/images/dynamicStatus_3.png"
							width="16" height="16" />
					</c:when>
					<c:otherwise>
						<img src="${ctx}/p/xlkfinance/images/dynamicStatus_1.png"
							width="16" height="16" />
					</c:otherwise>
				</c:choose>
			</td>
			<td>${dynamic.dynamicName}</td>
			<td>${dynamic.remark}
			</td>
			<td align="center">${dynamic.handleAuthorName}</td>
			<td align="center">${dynamic.updateDate}</td>
		</tr>

	</c:forEach>
</table>

      
     </div>
    </div>
    <div class="panel-body1 pt10" id="loanworkflowWrapDivPanel" style="padding: 10px;">
     <div class="easyui-panel" title="工作流程" data-options="collapsible:true" style="width:99%;">
      <div style="padding: 5px">
       <%@ include file="../../common/loanWorkflow.jsp"%>
       <%@ include file="../../common/task_hi_list.jsp"%>
      </div>
     </div>
    </div>
 </div>
</body>
</html>
