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
<title>费用详情</title>
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

var pid = "${pid}";
var orgId = "${pid}";
$(function() {

	setCooperationCity();
	$('#personal .easyui-linkbutton ,#personal input,#personal textarea').attr('disabled','disabled');
	$('#personal .easyui-linkbutton ,#personal input,#personal textarea').attr('readOnly','readOnly');
	$('#personal .easyui-linkbutton ,#personal input,#personal textarea').addClass('l-btn-disabled');
	$('#personal .easyui-linkbutton:not(.download) ,#personal input:not(.download),#personal textarea').attr('readOnly', 'readOnly');
	$('#personal .easyui-linkbutton:not(.download) ,#personal input:not(.download),#personal textarea').addClass('l-btn-disabled');
	$('#personal .easyui-linkbutton:not(.download)').removeAttr('onclick');

});

function setCooperationCity(){
	var citys = "${orgCooperateInfo.cooperationCitys}";
	$('#cooperationCity').combobox({    
		url:BASE_PATH+'sysLookupController/getSysLookupValByLookType.action?lookupType=COOPERATION_CITY',    
		valueField:'lookupVal',    
		textField:'lookupDesc',
		loadFilter: function(data){
			var bbc = new Array();
		 	for(var i=0;i<data.length;i++){
	 			if(data[i].lookupVal!=""){
	 				bbc.push(data[i]);
	 			}
		 	}
		 	if(citys != ""){
		 		$('#cooperationCity').combobox('setValues',citys.split(','))
		 	}
			return bbc;
		}
	});
}
//放款列表导出
function cxportLoanProject() {
	var pid = ${pid };
	var orgId = ${orgId};
	$.ajax({
			url : BASE_PATH + 'templateFileController/checkFileUrl.action',
			data : {
				templateName : 'LOANPROJECTLIST'
			},
			dataType : 'json',
			success : function(data) {
				if (data == 1) {
					window.location.href = "${basePath}orgFeeSettleController/loanProjectExcelExportList.action?pid="
							+ pid+"&orgId="+orgId;
				} else {
					alert('放款列表的导出模板不存在，请上传模板后重试');
				}
			}
		});
}
//解保列表导出
function cxportCancleProject() {
	var pid = ${pid};
	var orgId = ${orgId};
	$.ajax({
			url : BASE_PATH
					+ 'templateFileController/checkFileUrl.action',
			data : {
				templateName : 'CANCLEPROJECTLIST'
			},
			dataType : 'json',
			success : function(data) {
				if (data == 1) {
					window.location.href = "${basePath}orgFeeSettleController/cancleProjectExcelExportList.action?pid="
							+ pid+"&orgId="+orgId;
				} else {
					alert('放款列表的导出模板不存在，请上传模板后重试');
				}
			}
		});
}
function formatAomMoney(value,row,index){
	if(value){
		return accounting.formatMoney(value, "", 2, ",", ".");
	}else{
		return "0.00";
	}
}
function formatAomRate(value,row,index){
	if(value){
		value = value*100;
		return accounting.formatMoney(value, "", 2, ",", ".");
	}else{
		return "0.00";
	}
}
//项目名称format
function formatProjectName(value, row, index){
	var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
	return va;
}
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
							<input class="easyui-textbox" readonly="true" value="${orgCooperateInfo.orgAssetsInfo.orgName}" style="width:150px;" />
						</td>
						<td class="label_right">组织代码：</td>
						<td>
							<input  class="easyui-textbox" value="${orgCooperateInfo.orgAssetsInfo.code}" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">公司邮箱：</td>
						<td>
							<input  class="easyui-textbox" value="${orgCooperateInfo.orgAssetsInfo.email}"  style="width:150px;" readonly="true"/>
						</td>
						<td class="label_right">公司地址：</td>
						<td>
							<input  class="easyui-textbox" name="address" value="${orgCooperateInfo.orgAssetsInfo.address}" readonly="true" style="width:150px;"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">联系人：</td>
						<td>
							<input  class="easyui-textbox" value="${orgCooperateInfo.orgAssetsInfo.contact}" style="width:150px;" readonly="true"/>
						</td>
						<td class="label_right">联系人手机号码：</td>
						<td>
							<input  class="easyui-textbox" value="${orgCooperateInfo.orgAssetsInfo.phone}" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">所属合伙人：</td>
						<td>
							<input  class="easyui-textbox" value="${orgCooperateInfo.orgAssetsInfo.partnerName}" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">认证说明：</td>
						<td colspan="4">
							<input  class="easyui-textbox" value="${orgCooperateInfo.orgAssetsInfo.auditDesc}" readonly="true" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,800]'"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">备注：</td>
						<td colspan="4">
							<input  class="easyui-textbox" value="${orgCooperateInfo.orgAssetsInfo.remark}" readonly="true" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,800]'"/>
						</td>
					</tr>
					
				</table>
				<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
					<tr>
						<td class="label_right"><font color="red">*</font>合作开始时间：</td>
						<td>
							<input value="${orgCooperateInfo.startTime}" class="easyui-datebox" id="startTime" editable="false" style="width:150px;"   required="true" missingMessage="请输入合作开始日期!"/>
						</td>
						<td class="label_right"><font color="red">*</font>合作结束时间：</td>
						<td>
							<input value="${orgCooperateInfo.endTime}" validType="checkDateRange['#startTime']" class="easyui-datebox" editable="false" style="width:150px;"   required="true" missingMessage="请输入合作结束日期!"/>
						</td>
					</tr>
					<tr>
						<td class="label_right1"><font color="red">*</font>授信额度：</td>
						<td>
							<input value="${orgCooperateInfo.creditLimit }" id="creditLimit" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入授信额度!" style="width:129px;"/>
						</td>
						<td class="label_right1"><font color="red">*</font>可用授信额度：</td>
						<td>
							<input value="${orgCooperateInfo.availableLimit }" name="availableLimit" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入可用授信额度!" style="width:129px;"/>
						</td>
					</tr>
					<tr>
						<td class="label_right1"><font color="red">*</font>预收费费率：</td>
						<td>
							<input value="${orgCooperateInfo.rate }" id="rate" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入预收费费率!" style="width:129px;"/>%
						</td>
						<td class="label_right1"><font color="red">*</font>实际收费费率：</td>
						<td>
							<input value="${orgCooperateInfo.actualFeeRate }" id="actualFeeRate" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入实际收费费率!" style="width:129px;"/>%
						</td>
					</tr>
					<tr>
						<td class="label_right1"><font color="red">*</font>出款标准：</td>
						<td>
							<input value="${orgCooperateInfo.fundSizeMoney }" id="fundSizeMoney" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入机构业务出款标准!" style="width:129px;"/>
						</td>
						<td class="label_right1"><font color="red">*</font>单笔上限：</td>
						<td>
							<input value="${orgCooperateInfo.singleUpperLimit }" id="singleUpperLimit" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入单笔借款上限!" style="width:129px;"/>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>保证金金额：</td>
						<td>
							 <input value="${orgCooperateInfo.assureMoney }" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入保证金金额!" style="width:129px;"/>
						</td>
						<td class="label_right1"><font color="red">*</font>合作城市：</td>
						<td>
							<input name="cooperationCitys" id="cooperationCity" data-options="validType:'selrequired',multiple : true" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>需要保后监管：</td>
						<td>
							<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" style="width:129px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${orgCooperateInfo.isNeedHandle==1 }">selected </c:if>>需要</option>
								<option value="2" <c:if test="${orgCooperateInfo.isNeedHandle==2 }">selected </c:if>>不需要</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label_right">合作说明：</td>
						<td colspan="4">
							<input  class="easyui-textbox" value="${orgCooperateInfo.remark}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 放款列表开始 -->
		<div class="clear pt10"></div>
			<div class="dueInfoDiv easyui-panel " title="放款列表" data-options="collapsible:true" style="width:1039px;">	
				<div id="loanProjectTable" style="padding-left: 30px;padding-top:10px;padding-bottom:10px;height: auto;width: 939px;" >
					<shiro:hasAnyRoles name="EXPORT_LOAN_PROJECT,ALL_BUSINESS">
					<div id="toolbar_loanProject"  style="border-bottom: 0;">
						<a href="javascript:void(0)" id="gtjkrAdd" class="easyui-linkbutton" iconCls="icon-export" plain="true"  onclick="cxportLoanProject()">导出</a>
					</div>
					</shiro:hasAnyRoles>
					<table id="loanProject_grid"  class="easyui-datagrid" 
						style="height:auto;width: auto;"
						data-options="url: '<%=basePath%>orgFeeSettleController/getLoanProjectList.action?pid='+${pid },
						    method: 'POST',
						    singleSelect: false,
						    pagination: false,
						    sortOrder:'asc',
						    remoteSort:false,
						    toolbar: '#toolbar_loanProject',
						    resizable: false,
						    idField: 'pid'
						    ">
						<!-- 表头 -->
						<thead>
							<tr>
							    <th data-options="field:'pid',hidden:true" ></th>
							    <th width="150" data-options="field:'projectName',formatter:formatProjectName,width:150,align:'center'">订单名称</th>
							    <th width="80" data-options="field:'projectNumber',width:100,align:'center'">订单编号</th>
							    <th width="80" data-options="field:'orgName',width:100,align:'center'">业务来源</th>
							    <th width="80" data-options="field:'customerName',width:100,align:'center'">客户名称</th>
							    <th width="120" data-options="field:'customerCard',width:100,align:'center'">身份证号</th>
							    <th width="80" data-options="field:'requestDate',formatter:convertDate,width:100,align:'center'">提单日期</th>
							    <th width="80" data-options="field:'applyMoney',formatter:formatAomMoney,width:100,align:'center'">申请金额</th>
							    <th width="80" data-options="field:'loanMoney',formatter:formatAomMoney,width:120,align:'center'">放款金额</th>
							    <th width="80" data-options="field:'loanDate',formatter:convertDate,width:120,align:'center'">放款日期</th>
							    <th width="80" data-options="field:'planLoanDays',width:120,align:'center'">借款天数(天)</th>
							    <th width="80" data-options="field:'charge',formatter:formatAomMoney,width:120,align:'center'">收费金额</th>
							    <th width="80" data-options="field:'extimateFeeRate',width:120,align:'center'">收费费率(%)</th>
							    <th width="80" data-options="field:'houseName',width:120,align:'center'">物业名称</th>
							    <th width="80" data-options="field:'pmUserName',width:120,align:'center'">经办人</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<!-- 解保列表 -->
			<div class="clear pt10"></div>
			<div class="dueInfoDiv easyui-panel " title="解保列表" data-options="collapsible:true" style="width:1039px;">	
				<div id="cancleProjectTable" style="padding-left: 10px;padding-top:10px;height: auto;width: 1039px;" >
					<shiro:hasAnyRoles name="EXPORT_PAYMENT_PROJECT,ALL_BUSINESS">
					<div id="toolbar_cancleProject"  style="border-bottom: 0;">
						<a href="javascript:void(0)" id="gtjkrAdd" class="easyui-linkbutton" iconCls="icon-export" plain="true"  onclick="cxportCancleProject()">导出</a>
					</div>
					</shiro:hasAnyRoles>
					<table id="cancleProject_grid"  class="easyui-datagrid" 
						style="height:auto;width: auto;"
						data-options="url: '<%=basePath%>orgFeeSettleController/getCancelProjectList.action?pid='+${pid}+'&orgId='+${orgId},
						    method: 'POST',
						    singleSelect: false,
						    pagination: false,
						    sortOrder:'asc',
						    remoteSort:false,
						    toolbar: '#toolbar_cancleProject',
						    resizable: false,
						    idField: 'pid'
						    ">
						<!-- 表头 -->
						<thead>
							<tr>
							    <th data-options="field:'pid',hidden:true" ></th>
							    <th width="80" data-options="field:'projectName',formatter:formatProjectName,width:100,align:'center'">订单名称</th>
							    <th width="80" data-options="field:'projectNumber',width:100,align:'center'">订单编号</th>
							    <th width="80" data-options="field:'orgName',width:100,align:'center'">业务来源</th>
							    <th width="80" data-options="field:'customerName',width:100,align:'center'">客户名称</th>
							    <th width="80" data-options="field:'customerCard',width:100,align:'center'">身份证号</th>
							    <th width="80" data-options="field:'loanMoney',formatter:formatAomMoney,width:120,align:'center'">放款金额</th>
							    <th width="80" data-options="field:'loanDate',formatter:convertDate,width:120,align:'center'">放款日期</th>
							    <th width="80" data-options="field:'planLoanDays',width:120,align:'center'">预计借款天数(天)</th>
							    <th width="80" data-options="field:'charge',formatter:formatAomMoney,width:120,align:'center'">预收咨询费</th>
							    <th width="80" data-options="field:'extimateFeeRate',width:120,align:'center'">预收费率(%)</th>
							    <th width="80" data-options="field:'actualLoanDays',width:120,align:'center'">实际借款天数(天)</th>
							    <th width="80" data-options="field:'paymentDate',formatter:convertDate,width:120,align:'center'">回款日期</th>
							    <th width="80" data-options="field:'paymentMoney',formatter:formatAomMoney,width:120,align:'center'">回款金额</th>
							    <th width="80" data-options="field:'actualFeeRate',width:120,align:'center'">应收费率(%)</th>
							    <th width="80" data-options="field:'realChargeMoney',formatter:formatAomMoney,width:120,align:'center'">应收咨询费</th>
							    <th width="80" data-options="field:'refund',formatter:formatAomMoney,width:120,align:'center'">退咨询费</th>
							    <th width="80" data-options="field:'returnCommRate',formatter:formatAomRate,width:120,align:'center'">返佣率(%)</th>
							    <th width="80" data-options="field:'returnComm',formatter:formatAomMoney,width:120,align:'center'">返佣金额</th>
							    <th width="80" data-options="field:'pmUserName',width:120,align:'center'">经办人</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</body>
</html>