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
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/loanWorkflow.js?version=20161031"></script>
<title>机构合作申请详情</title>
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

var pid = "${orgCooperateInfo.pid}";
var editType = "${editType}";
var status = "${orgCooperateInfo.applyStatus}";
/**************工作流字段 begin******************/
//合作申请处理
var WORKFLOW_ID = "cooperationRequestProcess"; 
var workflowTaskDefKey = "task_CooperationRequest";
var nextRoleCode = "RISK_ONE";//
/**************工作流字段 end********************/
var projectId = '${orgCooperateInfo.pid}';
var refId = '${orgCooperateInfo.pid}';

$(function() {

	setCooperationCity();
	$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('disabled','disabled');
	$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('readOnly','readOnly');
	$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').addClass('l-btn-disabled');
	$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
	$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').addClass('l-btn-disabled');
	$('#tabs .easyui-linkbutton:not(.download)').removeAttr('onclick');
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

function caozuofiter(value,row,index){

	if(row.file == null || row.file ==undefined ||row.file.fileUrl=='' || row.file.fileUrl==null ||row.file.fileUrl==undefined){
		var downloadDom = "<a href='javascript:void(0)' class='download'><font color='gray'>下载</font></a>";
	}else{
		var downloadDom = "<a href='${basePath}beforeLoanController/downloadData.action?path="+row.file.fileUrl+"&fileName="+row.file.fileName+"' class='download'><font color='blue'>下载</font></a>";
	}
	return downloadDom;
}

function formatterType(val,row){
	if(val == 10){
		return "合作合同";
	}else if(val == 20){
		return "授信合同";
	}
	
}

function formatterFileName(value,row,index){
	if(row.file != null && row.file !=undefined && row.file.fileName != undefined){
		return  row.file.fileName;
	}else{
		return '';
	}
}

function sizeFileter(value,row,index){
	if(row.file != null && row.file !=undefined && row.file.fileSize>0){
		return  parseInt(row.file.fileSize/1024)+" KB";
	}else{
		return '';
	}
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
						<td class="label_right">公司地址：</td>
						<td>
							<input  class="easyui-textbox" name="address" value="${orgAssetsInfo.address}" readonly="true" style="width:150px;"/>
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
						<td class="label_right">所属合伙人：</td>
						<td>
							<input  class="easyui-textbox" value="${orgAssetsInfo.partnerName}" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">认证说明：</td>
						<td colspan="4">
							<input  class="easyui-textbox" value="${orgAssetsInfo.auditDesc}" readonly="true" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,800]'"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">备注：</td>
						<td colspan="4">
							<input  class="easyui-textbox" value="${orgAssetsInfo.remark}" readonly="true" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,800]'"/>
						</td>
					</tr>
				</table>
				<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
						<tr>
							<td class="label_right1"><font color="red">*</font>授信额度：</td>
							<td>
								<input value="${orgCooperateInfo.creditLimit }" name="creditLimit" id="creditLimit" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入授信额度!" style="width:129px;"/>
							</td>
							<td class="label_right1"><font color="red">*</font>可用授信额度：</td>
							<td>
							    <c:if test="${orgCooperateInfo.availableLimit>0 }">
								<input value="${orgCooperateInfo.availableLimit }" id="availableLimit" readonly="readonly" name="availableLimit" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入可用授信额度!" style="width:129px;"/>
								</c:if>
							    <c:if test="${orgCooperateInfo.availableLimit<=0 }">
								<input value="${orgCooperateInfo.availableLimit }" id="availableLimit" readonly="readonly" name="availableLimit" class="easyui-numberbox" data-options="required:true,precision:2,groupSeparator:','" missingMessage="请输入可用授信额度!" style="width:129px;"/>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="label_right1"><font color="red">*</font>启用授信额度：</td>
							<td>
							    <c:if test="${orgCooperateInfo.activateCreditLimit>0 }">
								<input value="${orgCooperateInfo.activateCreditLimit }" name="activateCreditLimit" id="activateCreditLimit" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入启用授信额度!" style="width:129px;"/>
								</c:if>
							    <c:if test="${orgCooperateInfo.activateCreditLimit<=0 }">
								<input value="${orgCooperateInfo.activateCreditLimit }" name="activateCreditLimit" id="activateCreditLimit" class="easyui-numberbox" data-options="required:true,precision:2,groupSeparator:','" missingMessage="请输入启用授信额度!" style="width:129px;"/>
								</c:if>
							</td>
							<td class="label_right1"><font color="red">*</font>保证金比例：</td>
							<td>
								<input value="${orgCooperateInfo.assureMoneyProportion }" id="assureMoneyProportion" readonly="readonly" name="assureMoneyProportion" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入保证金比例!" style="width:129px;"/>%
							</td>
						</tr>
						<tr>
							<td class="label_right"><font color="red">*</font>保证金金额：</td>
							<td>
								 <input value="${orgCooperateInfo.assureMoney }" name="assureMoney" class="easyui-numberbox" id="assureMoney" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入保证金金额!" style="width:129px;"/>
							</td>
							<td class="label_right1">实收保证金：</td>
							<td>
								<input value="${orgCooperateInfo.realAssureMoney }" name="realAssureMoney" readonly="readonly" id="realAssureMoney" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
							</td>
						</tr>
						<tr>
							<td class="label_right1"><font color="red">*</font>预收费费率：</td>
							<td>
								<input value="${orgCooperateInfo.rate }" name="rate" id="rate" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入预收费费率!" style="width:129px;"/>%
							</td>
							<td class="label_right1"><font color="red">*</font>实际收费费率：</td>
							<td>
								<input value="${orgCooperateInfo.actualFeeRate }" name="actualFeeRate" id="actualFeeRate" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入实际收费费率!" style="width:129px;"/>%
							</td>
						</tr>
						<tr>
							<td class="label_right1"><font color="red">*</font>出款标准：</td>
							<td>
								<input value="${orgCooperateInfo.fundSizeMoney }" name="fundSizeMoney" id="fundSizeMoney" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入机构业务出款标准!" style="width:129px;"/>
							</td>
							<td class="label_right1"><font color="red">*</font>单笔上限：</td>
							<td>
								<input value="${orgCooperateInfo.singleUpperLimit }" name="singleUpperLimit" id="singleUpperLimit" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入单笔借款上限!" style="width:129px;"/>
							</td>
						</tr>
					<tr>
						<td class="label_right1"><font color="red">*</font>合作城市：</td>
						<td>
							<input name="cooperationCitys" id="cooperationCity" data-options="validType:'selrequired',multiple : true" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
						</td>
						<td class="label_right"><font color="red">*</font>需要保后监管：</td>
						<td>
							<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="isNeedHandle" style="width:129px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${orgCooperateInfo.isNeedHandle==1 }">selected </c:if>>需要</option>
								<option value="2" <c:if test="${orgCooperateInfo.isNeedHandle==2 }">selected </c:if>>不需要</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label_right">合作说明：</td>
						<td colspan="4">
							<input  class="easyui-textbox" name="remark" value="${orgCooperateInfo.remark}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 附件开始 -->
		<div class="clear pt10"></div>
			<div class="dueInfoDiv easyui-panel " title="附件" data-options="collapsible:true" style="width:1039px;">	
				<div id="additionFileTable" style="padding-left: 30px;padding-top:10px;height: auto;width: 939px;" >
					<table id="additionFile_grid"  class="easyui-datagrid" 
						style="height:auto;width: auto;"
						data-options="url: '<%=basePath%>orgCooperatCompanyApplyController/getOrgContractList.action?cooperationId='+${orgCooperateInfo.pid },
						    method: 'POST',
						    singleSelect: false,
						    pagination: false,
						    sortOrder:'asc',
						    remoteSort:false,
						    toolbar: '#toolbar_cooperateFile',
						    resizable: false,
						    idField: 'pid'
						    ">
						<!-- 表头 -->
						<thead>
							<tr>
							    <th data-options="field:'pid',hidden:true" ></th>
							    <th width="80" data-options="field:'contractType',formatter:formatterType,width:100,align:'center'">合同类型</th>
							    <th width="140" data-options="field:'fileName',formatter:formatterFileName,width:100,align:'center'">合同名称</th>
							    <th width="140" data-options="field:'contractNo',width:100,align:'center'">合同编号</th>
							    <th width="80" data-options="field:'fileSize',formatter:sizeFileter,width:100,align:'center'">大小</th>
							    <th width="140" data-options="field:'updateDate',width:100,align:'center'">上传时间</th>
							    <th width="180" data-options="field:'remark',width:100,align:'center'">上传说明</th>
							    <th width="160" data-options="field:'cz',width:120,formatter:caozuofiter">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<!-- 附件结束 -->
		</div>
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