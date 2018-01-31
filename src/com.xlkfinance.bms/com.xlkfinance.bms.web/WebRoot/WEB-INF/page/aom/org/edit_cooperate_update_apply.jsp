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
<title>机构合作申请</title>
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

/**************工作流字段 begin******************/
//合作申请处理
var WORKFLOW_ID = "cooperationUpdateProcess"; 
var workflowTaskDefKey = "task_CooperationRequest";
var nextRoleCode = "REGIONAL_DIRECTOR";//
var projectId = ${orgCooperateInfo.pid};//合作申请id
var orgId = ${orgCooperateInfo.orgId};
var refId =${cooperationUpdateApply.pid == null ? -1 : cooperationUpdateApply.pid};
var orgCooperationUpdateApplyId = ${cooperationUpdateApply.pid == null ? -1 : cooperationUpdateApply.pid};
$(function() {

// 	setCooperationCity();
	$.extend($.fn.validatebox.defaults.rules, {
		checkDateRange: {
			validator: function(value,param){
				return value > $(param[0]).datebox('getValue');
			},
			message: '结束时间必须大于开始时间.'
		}
	});
	//授信额度改变事件：
	$("#creditLimit,#assureMoney").numberbox({
	    "onChange":function(){
	    	
	    	setAssureMoneyProportion();
	    	setActivateCreditLimit();
	    }
	  });
});
/**
 * 根据授信额度和保证金金额设置计算出来的保证金比例
 */
function setAssureMoneyProportion(){
	var creditLimit=parseFloat($("#creditLimit").val());//授信额度
	var assureMoney=parseFloat($("#assureMoney").val());//保证金金额
	//保证金比例=(保证金/授信额度)*100
	var assureMoneyProportion=(assureMoney/creditLimit)*100;
	    	
	$("#assureMoneyProportion").numberbox("setValue", assureMoneyProportion);
}
/**
 * 根据保证金金额和保证金比例设置计算出来的启用授信额度
 */
function setActivateCreditLimit(){
	var assureMoneyProportion=parseFloat($("#assureMoneyProportion").val());//保证金比例
	var realAssureMoney=parseFloat($("#realAssureMoney").val());//实收保证金
	var assureMoney=parseFloat($("#assureMoney").val());//保证金金额
	var creditLimit=parseFloat($("#creditLimit").val());//creditLimit
	if (realAssureMoney==assureMoney) {//实收保证金等于保证金金额，启用授信额度等于授信额度
		$("#activateCreditLimit").numberbox("setValue", creditLimit);
	}else if (realAssureMoney>0) {
		//启用授信额度=实收保证金/(保证金比例/100)
		var activateCreditLimit=realAssureMoney/(assureMoneyProportion/100);
		$("#activateCreditLimit").numberbox("setValue", activateCreditLimit);
	}
}
// function setCooperationCity(){
// // 	var citys = "${orgCooperateInfo.cooperationCitys}";
// 	$('#cooperationCity').combobox({    
// 		url:BASE_PATH+'sysLookupController/getSysLookupValByLookType.action?lookupType=COOPERATION_CITY',    
// 		valueField:'lookupVal',    
// 		textField:'lookupDesc',
// 		loadFilter: function(data){
// 			var bbc = new Array();
// 		 	for(var i=0;i<data.length;i++){
// 	 			if(data[i].lookupVal!=""){
// 	 				bbc.push(data[i]);
// 	 			}
// 		 	}
// 		 	if(citys != ""){
// 		 		$('#cooperationCity').combobox('setValues',citys.split(','))
// 		 	}
// 			return bbc;
// 		}
// 	});
// }

/**
 * 保存机构合作信息
 */
function saveCooperationUpdateApply(){
	var creditLimit = parseFloat($("#creditLimit").numberbox("getValue"));
	var availableLimit = parseFloat($("#availableLimit").numberbox("getValue"));//可用授信额度
	var assureMoney = parseFloat($("#assureMoney").numberbox("getValue"));
	var singleUpperLimit = parseFloat($("#singleUpperLimit").numberbox("getValue"));
	var rate = parseFloat($("#planRate").numberbox("getValue"));
// 	var citys = parseFloat($('#cooperationCity').combobox('getValues'));
	var actualFeeRate = parseFloat($("#actualFeeRate").numberbox("getValue"));
	<c:if test="${orgCooperateInfo.realAssureMoney==0 }">
	var activateCreditLimit = parseFloat($("#activateCreditLimit").numberbox("getValue"));//启用授信额度
	</c:if>
	var assureMoneyProportion = parseFloat($("#assureMoneyProportion").numberbox("getValue"));//保证金比例
	var realAssureMoney = parseFloat($("#realAssureMoney").numberbox("getValue"));//实收保证金
	if(creditLimit<=0){
		$.messager.alert('操作提示','授信额度必须大于0','error');	
		return;
	}
	if(availableLimit>creditLimit){
		$.messager.alert('操作提示','可用授信额度必须小于授信额度','error');	
		return;
	}
	if(availableLimit>activateCreditLimit){
		$.messager.alert('操作提示','可用授信额度必须小于启用授信额度','error');	
		return;
	}
	if(assureMoney<=0){
		$.messager.alert('操作提示','保证金金额必须大于0','error');	
		return;
	}
	if(assureMoney>=creditLimit){
		$.messager.alert('操作提示','保证金金额必须小于授信额度','error');	
		return;
	}
	if(realAssureMoney>assureMoney){
		$.messager.alert('操作提示','保证金金额必须大于等于实收保证金','error');	
		return;
	}
	if(singleUpperLimit<=0){
		$.messager.alert('操作提示','单笔上限必须大于0','error');	
		return;
	}
	if(rate<=0){
		$.messager.alert('操作提示','预收费费率必须大于0','error');	
		return;
	}
	if(actualFeeRate<=0){
		$.messager.alert('操作提示','实际收费费率必须大于0','error');	
		return;
	}
	<c:if test="${orgCooperateInfo.realAssureMoney==0 }">
	if(activateCreditLimit <=0){
		$.messager.alert('操作提示','启用授信额度必须大于0','error');	
		return;
	}
	if(activateCreditLimit-creditLimit >0){
		$.messager.alert('操作提示','启用授信额度必须小于或等于授信额度','error');	
		return;
	}
	</c:if>
	if(assureMoneyProportion <=0){
		$.messager.alert('操作提示','保证金比例必须大于0','error');	
		return;
	}
	
	$("#orgForm").form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				pid = ret.body.pid;//
				orgCooperationUpdateApplyId=pid;
				refId=pid;
				$("#pid").val(pid);
				$.messager.alert('操作提示',ret.header["msg"],'info');
				// 刷新历史审批列表
				$("#grid_applyHisList").datagrid("clearChecked");
				$("#grid_applyHisList").datagrid('reload');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}
$(function() {
	<c:if test="${openType==2 }">
	$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('disabled', 'disabled');
	$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
	$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').addClass('l-btn-disabled');
	$('#tabs .easyui-linkbutton:not(.download)').removeAttr('onclick');
	</c:if>
});
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
	<div title="机构信息" id="tab1">
		<div style="padding: 30px 10px 0 10px;">
		<div class="easyui-panel" title="机构基本信息" data-options="collapsible:true" width="1039px;">
			<div id="personal" style="padding:10px 0 15px 0;">
				<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
					<tr>
						<td class="label_right">机构名称：</td>
						<td>
							<input class="easyui-textbox" readonly="true" value="${orgAssetsInfo.orgName}" style="width:150px;" />
						</td>
						<td class="label_right">组织代码：</td>
						<td>
							<input  class="easyui-textbox" value="${orgAssetsInfo.code}" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">公司邮箱：</td>
						<td>
							<input  class="easyui-textbox" value="${orgAssetsInfo.email}"  style="width:150px;" readonly="true"/>
						</td>
						<td class="label_right">所属合伙人：</td>
						<td>
							<input  class="easyui-textbox" value="${orgAssetsInfo.partnerName}" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">联系人：</td>
						<td>
							<input  class="easyui-textbox" value="${orgAssetsInfo.contact}" style="width:150px;" readonly="true"/>
						</td>
						<td class="label_right">联系人手机号码：</td>
						<td>
							<input  class="easyui-textbox" value="${orgAssetsInfo.phone}" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">认证说明：</td>
						<td colspan="4">
							<input  class="easyui-textbox" value="${orgAssetsInfo.auditDesc}" readonly="true" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,300]'"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">备注：</td>
						<td colspan="4">
							<input  class="easyui-textbox" value="${orgAssetsInfo.remark}" readonly="true" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,300]'"/>
						</td>
					</tr>
				</table>
				<form action="<%=basePath%>orgCooperationUpdateApplyController/saveOrgCooperationUpdateApply.action" id="orgForm" name="orgForm" method="post">
					<input type="hidden" name="pid" id="pid" value="${cooperationUpdateApply.pid }">
					<input type="hidden" name="orgId" id="orgId" value="${orgAssetsInfo.pid }">
					<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
						<tr>
							<td class="label_right"></td>
							<td ><b>修改前</b><b style="padding-left: 100px;">修改后</b></td>
							<td class="label_right"></td>
							<td><b>修改前</b><b style="padding-left: 100px;">修改后</b></td>
						</tr>
						<tr>
							<td class="label_right"><font color="red">*</font>合作开始时间：</td>
							<td>
								<input value="${orgCooperateInfo.startTime}"class="easyui-datebox" id="startTime" editable="false" style="width:150px;"   required="true" missingMessage="请输入合作开始日期!"/>
							</td>
							<td class="label_right"><font color="red">*</font>合作结束时间：</td>
							<td>
								<input value="${orgCooperateInfo.endTime}"validType="checkDateRange['#startTime']" class="easyui-datebox" editable="false" style="width:150px;"   required="true" missingMessage="请输入合作结束日期!"/>
							</td>
						</tr>
						<tr>
							<td class="label_right1"><font color="red">*</font>授信额度：</td>
							<td>
								<input value="${orgCooperateInfo.creditLimit }" name="oldCreditLimit" id="oldCreditLimit" readonly="readonly" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入授信额度!" style="width:129px;"/>
								<input value="${cooperationUpdateApply.creditLimit }" name="creditLimit" id="creditLimit" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入授信额度!" style="width:129px;"/>
							</td>
							<td class="label_right1"><font color="red">*</font>可用授信额度：</td>
							<td>
							    <c:if test="${orgCooperateInfo.availableLimit>0 }">
								<input value="${orgCooperateInfo.availableLimit }" id="availableLimit" readonly="readonly" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入可用授信额度!" style="width:129px;"/>
								</c:if>
							    <c:if test="${orgCooperateInfo.availableLimit<=0 }">
								<input value="${orgCooperateInfo.availableLimit }" id="availableLimit" readonly="readonly" class="easyui-numberbox" data-options="required:true,precision:2,groupSeparator:','" missingMessage="请输入可用授信额度!" style="width:129px;"/>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="label_right1"><font color="red">*</font>启用授信额度：</td>
							<td>
							    <c:if test="${orgCooperateInfo.activateCreditLimit>0 }">
								<input value="${orgCooperateInfo.activateCreditLimit }" name="oldActivateCreditLimit" id="oldActivateCreditLimit" readonly="readonly" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入启用授信额度!" style="width:129px;"/>
								</c:if>
							    <c:if test="${orgCooperateInfo.activateCreditLimit<=0 }">
								<input value="${orgCooperateInfo.activateCreditLimit }" name="oldActivateCreditLimit" id="oldActivateCreditLimit" readonly="readonly" class="easyui-numberbox" data-options="required:true,precision:2,groupSeparator:','" missingMessage="请输入启用授信额度!" style="width:129px;"/>
								</c:if>
								
								<input value="${(cooperationUpdateApply.applyStatus==2||cooperationUpdateApply.applyStatus==3||cooperationUpdateApply.applyStatus==4)?cooperationUpdateApply.activateCreditLimit:''  }" name="activateCreditLimit" id="activateCreditLimit" <c:if test="${orgCooperateInfo.realAssureMoney>0 }">readonly="readonly"</c:if> class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入启用授信额度!" style="width:129px;"/>
							</td>
							<td class="label_right1"><font color="red">*</font>保证金比例：</td>
							<td>
								<input value="${orgCooperateInfo.assureMoneyProportion }"  name="oldAssureMoneyProportion" id="oldAssureMoneyProportion" readonly="readonly" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入保证金比例!" style="width:129px;"/>%
								<input value="${cooperationUpdateApply.assureMoneyProportion }" id="assureMoneyProportion" readonly="readonly" name="assureMoneyProportion" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入保证金比例!" style="width:129px;"/>%
							</td>
						</tr>
						<tr>
							<td class="label_right"><font color="red">*</font>保证金金额：</td>
							<td>
								 <input value="${orgCooperateInfo.assureMoney }"  name="oldAssureMoney" id="oldAssureMoney" readonly="readonly" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入保证金金额!" style="width:129px;"/>
								 <input value="${cooperationUpdateApply.assureMoney }" name="assureMoney" class="easyui-numberbox" id="assureMoney" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入保证金金额!" style="width:129px;"/>
							</td>
							<td class="label_right1">实收保证金：</td>
							<td>
								<input value="${orgCooperateInfo.realAssureMoney }" readonly="readonly" id="realAssureMoney" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"  style="width:129px;"/>
							</td>
						</tr>
						<tr>
							<td class="label_right1"><font color="red">*</font>预收费费率：</td>
							<td>
								<input value="${orgCooperateInfo.rate }"  name="oldPlanRate" id="oldPlanRate" readonly="readonly" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入预收费费率!" style="width:129px;"/>%
								<input value="${cooperationUpdateApply.planRate }" name="planRate" id="planRate" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入预收费费率!" style="width:129px;"/>%
							</td>
							<td class="label_right1"><font color="red">*</font>实际收费费率：</td>
							<td>
								<input value="${orgCooperateInfo.actualFeeRate }"  name="oldActualFeeRate" id="oldActualFeeRate" readonly="readonly" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入实际收费费率!" style="width:129px;"/>%
								<input value="${cooperationUpdateApply.actualFeeRate }" name="actualFeeRate" id="actualFeeRate" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入实际收费费率!" style="width:129px;"/>%
							</td>
						</tr>
						<tr>
							<td class="label_right1"><font color="red">*</font>出款标准：</td>
							<td>
								<input value="${orgCooperateInfo.fundSizeMoney }"  name="oldFundSizeMoney" id="oldFundSizeMoney" readonly="readonly" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入机构业务出款标准!" style="width:129px;"/>
								<input value="${cooperationUpdateApply.fundSizeMoney }" name="fundSizeMoney" id="fundSizeMoney" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入机构业务出款标准!" style="width:129px;"/>
							</td>
							<td class="label_right1"><font color="red">*</font>单笔上限：</td>
							<td>
								<input value="${orgCooperateInfo.singleUpperLimit }"  name="oldSingleUpperLimit" id="oldSingleUpperLimit" readonly="readonly" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入单笔借款上限!" style="width:129px;"/>
								<input value="${cooperationUpdateApply.singleUpperLimit }" name="singleUpperLimit" id="singleUpperLimit" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入单笔借款上限!" style="width:129px;"/>
							</td>
						</tr>
						<tr>
							<td class="label_right"><font color="red">*</font>需要保后监管：</td>
							<td>
								<select class="select_style easyui-combobox" disabled="disabled" editable="false" data-options="validType:'selrequired'" required="true" style="width:129px;">
									<option value="-1">--请选择--</option>
									<option value="1" <c:if test="${orgCooperateInfo.isNeedHandle==1 }">selected </c:if>>需要</option>
									<option value="2" <c:if test="${orgCooperateInfo.isNeedHandle==2 }">selected </c:if>>不需要</option>
								</select>
							</td>
							<!-- <td class="label_right1"><font color="red">*</font>合作城市：</td>
							<td>
								<input id="cooperationCity" data-options="validType:'selrequired',multiple : true" class="easyui-combobox" editable="false" panelHeight="auto" required="true"  style="width:129px;"/>
							</td> -->
						</tr>
						<tr>
							<td class="label_right">保证金备注：</td>
							<td colspan="4">
								<input  class="easyui-textbox" editable="false"  name="oldAssureMoneyRemark" id="oldActivateCreditLimit" value="${orgCooperateInfo.assureMoneyRemark}" style="width:40%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
								<input  class="easyui-textbox" value="${cooperationUpdateApply.assureMoneyRemark}" name="assureMoneyRemark" style="width:40%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
							</td>
						</tr>
						<tr>
							<td class="label_right">合作说明：</td>
							<td colspan="4">
								<input  class="easyui-textbox" editable="false" value="${orgCooperateInfo.remark}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
							</td>
						</tr>
						<tr>
							<td class="label_right">修改原因：</td>
							<td colspan="4">
								<input  class="easyui-textbox" name="remark" value="${cooperationUpdateApply.remark}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,500]'"/>
							</td>
						</tr>
						<tr>
							<td class="label_right">计算公式：</td>
							<td colspan="4">
							<font color="red">启用授信额度=实收保证金/(保证金比例/100)<br>
							保证金比例=(保证金/授信额度)*100<br>
							可用授信额度=新授信额度-旧已使用授信额度<br>
							旧已使用授信额度=旧授信额度-旧可用授信额度<br>
							授信额度>=启用授信额度>=可用授信额度<br>
 					                保证金金额&lt;授信额度<br>
 					                实收保证金&lt;保证金金额<br>
 					                </font>
							</td>
						</tr>
					</table>
					<div style="padding-bottom: 20px;padding-top: 10px;padding-left: 100px;">
						<a href="#" id="saveCooperationUpdateApply" class="easyui-linkbutton" style="margin-left: 80px;" iconCls="icon-save" onclick="saveCooperationUpdateApply()">保存</a>
					</div>
				</form>
				      <table id="grid_applyHisList" title="历史审批列表" style="height: 300px; width: auto;" class="easyui-datagrid"
        data-options="
      url: 'applyHisList.action?orgId=${orgCooperateInfo.orgId}',
      method: 'POST',
      rownumbers: true,
      singleSelect: false,
      pagination: true,
      sortOrder:'asc',
      remoteSort:false,
      idField: 'pid',
      fitColumns:true"
       >
        <!-- 表头 -->
        <thead>
         <tr>
          <th data-options="field:'pid',checkbox:true" align="center" halign="center"></th>
          <th data-options="field:'creditLimit',sortable:true,formatter:formatMoney" align="center" halign="center">授信额度</th>
          <th data-options="field:'oldCreditLimit',sortable:true,formatter:formatMoney" align="center" halign="center">旧授信额度</th>
          <th data-options="field:'activateCreditLimit',sortable:true,formatter:formatMoney" align="center" halign="center">启用授信额度</th>
          <th data-options="field:'oldActivateCreditLimit',sortable:true,formatter:formatMoney" align="center" halign="center">旧启用授信额度</th>
          <th data-options="field:'planRate',sortable:true" align="left" halign="center">预收费费率</th>
          <th data-options="field:'oldPlanRate',sortable:true" align="left" halign="center">旧预收费费率</th>
          <th data-options="field:'fundSizeMoney',sortable:true,formatter:formatMoney" align="left" halign="center">出款标准</th>
          <th data-options="field:'oldFundSizeMoney',sortable:true,formatter:formatMoney" align="left" halign="center">旧出款标准</th>
          <th data-options="field:'assureMoneyProportion',sortable:true" align="left" halign="center">保证金比例</th>
          <th data-options="field:'oldAssureMoneyProportion',sortable:true" align="left" halign="center">旧保证金比例</th>
          <th data-options="field:'assureMoney',sortable:true,formatter:formatMoney" align="left" halign="center">保证金</th>
          <th data-options="field:'oldAssureMoney',sortable:true,formatter:formatMoney" align="left" halign="center">旧保证金</th>
          <th data-options="field:'actualFeeRate',sortable:true" align="left" halign="center">实际收费费率</th>
          <th data-options="field:'oldActualFeeRate',sortable:true" align="left" halign="center">旧实际收费费率</th>
          <th data-options="field:'singleUpperLimit',sortable:true,formatter:formatMoney" align="left" halign="center">单笔上限</th>
          <th data-options="field:'oldSingleUpperLimit',sortable:true,formatter:formatMoney" align="left" halign="center">旧单笔上限</th>
          <th data-options="field:'assureMoneyRemark',sortable:true" align="left" halign="center">保证金备注</th>
          <th data-options="field:'assureMoneyRemark',sortable:true" align="left" halign="center">旧保证金备注</th>
          <th data-options="field:'applyStatus',sortable:true,formatter:formatterCommonApplyHandleStatus" align="left" halign="center">申请状态</th>
          <th data-options="field:'remark',sortable:true" align="left" halign="center">修改原因</th>
          <th data-options="field:'createrDate',sortable:true,formatter:convertDateTime" align="left" halign="center">发起时间</th>
          <th data-options="field:'updateDate',sortable:true,formatter:convertDateTime" align="left" halign="center">最后修改时间</th>
          <th data-options="field:'createrName',sortable:true" align="left" halign="center">发起人</th>
         </tr>
        </thead>
       </table>
			</div>
		</div>
		</div>
	</div>
</div>

<c:if test="${openType==1 }">
 <div class="panel-body1 pt10" id="loanworkflowWrapDivPanel" style="padding: 10px 10px 0 10px;width: 1039px;">
	<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
		<div style="padding:5px">
			<%@ include file="../../common/loanWorkflow.jsp"%> 
			<%@ include file="../../common/task_hi_list.jsp"%>
		</div>
	</div>
</div>
</c:if>
</div>
</body>
</html>