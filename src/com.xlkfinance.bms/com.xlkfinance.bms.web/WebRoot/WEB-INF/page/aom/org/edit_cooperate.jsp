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

var pid = "${orgCooperateInfo.pid}";
var editType = "${editType}";
var status = "${orgCooperateInfo.applyStatus}";
/**************工作流字段 begin******************/
//合作申请处理
var WORKFLOW_ID = "cooperationRequestProcess"; 
var workflowTaskDefKey = "task_CooperationRequest";
var nextRoleCode = "REGIONAL_DIRECTOR";//
/**************工作流字段 end********************/
var projectId = '${orgCooperateInfo.pid}';
var refId = '${orgCooperateInfo.pid}';
isEdit = "${isEdit}";
$(function() {

	setCooperationCity();
	if(editType == '3' && status != '1' && status != '5'){
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('disabled','disabled');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('readOnly','readOnly');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}
	
	$.extend($.fn.validatebox.defaults.rules, {
		checkDateRange: {
			validator: function(value,param){
				return value > $(param[0]).datebox('getValue');
			},
			message: '结束时间必须大于开始时间.'
		}
	});
	//启用额度改变事件：启用额度联动可用授信额度
	$("#activateCreditLimit").numberbox({
	    "onChange":function(){
	    	$("#availableLimit").numberbox("setValue", $("#activateCreditLimit").numberbox("getValue"));
	    }
	  });
	//授信额度改变事件：
	$("#creditLimit,#assureMoney").numberbox({
	    "onChange":function(){
	    	
	    	setAssureMoneyProportion();
	    }
	  });
});
/**
 * 根据授信额度和保证金金额设置计算出来的保证金比例
 */
function setAssureMoneyProportion(){
	var creditLimit=parseFloat($("#creditLimit").val());//授信额度
	var assureMoney=parseFloat($("#assureMoney").val());//保证金金额
	//（保证金/授信额度）*100=保证金比例
	var assureMoneyProportion=(assureMoney/creditLimit)*100;
	    	
	$("#assureMoneyProportion").numberbox("setValue", assureMoneyProportion);
}
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

/**
 * 保存机构合作信息
 */
function saveOrgCooperateInfo(){
	var creditLimit = $("#creditLimit").numberbox("getValue");
	var assureMoney = $("#assureMoney").numberbox("getValue");
	var singleUpperLimit = $("#singleUpperLimit").numberbox("getValue");
	var rate = $("#rate").numberbox("getValue");
// 	var citys = $('#cooperationCity').combobox('getValues');
	var actualFeeRate = $("#actualFeeRate").numberbox("getValue");
	var activateCreditLimit = $("#activateCreditLimit").numberbox("getValue");//启用授信额度
	var assureMoneyProportion = $("#assureMoneyProportion").numberbox("getValue");//保证金比例
	if(creditLimit<=0){
		$.messager.alert('操作提示','授信额度必须大于0','error');	
		return;
	}
	if(assureMoney<=0){
		$.messager.alert('操作提示','保证金金额必须大于0','error');	
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
// 	if(citys == ''){
// 		$.messager.alert('操作提示','合作城市不能为空','error');	
// 		return;
// 	}
	if(activateCreditLimit <=0){
		$.messager.alert('操作提示','启用授信额度必须大于0','error');	
		return;
	}
	
	if(activateCreditLimit-creditLimit >0){
		$.messager.alert('操作提示','启用授信额度必须小于或等于授信额度','error');	
		return;
	}
	if(assureMoneyProportion <=0){
		$.messager.alert('操作提示','保证金比例必须大于0','error');	
		return;
	}
	
	$("#orgForm").form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				pid = ret.header["pid"];//合作申请ID
				projectId = pid;
				$("#pid").val(pid);
				$("#applyStatus").val(ret.header["applyStatus"]);
				$.messager.alert('操作提示','保存信息成功','info');
				var url = "<%=basePath%>orgCooperatCompanyApplyController/getOrgContractList.action?cooperationId="+pid;
        		$('#additionFile_grid').datagrid('options').url = url;
        		$('#additionFile_grid').datagrid('reload');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}
function caozuofiter(value,row,index){

	var fileName = "";
	if(row.file != null || row.file !=undefined && fileName!='' && fileName!=null &&fileName!=undefined){
		fileName = row.file.fileName;
		fileName = fileName.replace(/\ +/g,"");
	}
	if(row.file == null || row.file ==undefined ||row.file.fileUrl=='' || row.file.fileUrl==null ||row.file.fileUrl==undefined){
		var downloadDom = "<a href='javascript:void(0)' class='download'><font color='gray'>下载</font> | </a>";
	}else{
		var downloadDom = "<a href='javascript:void(0)' onclick=downLoadFileByPath('"+row.file.fileUrl+"','"+ fileName+"') class='download'><font color='blue'>下载</font> | </a> ";
	}
	var lookUpDom = "";
	if(row.file != null && row.file !=undefined &&row.file.fileUrl!='' && row.file.fileUrl!=null &&row.file.fileUrl!=undefined){
		var path = row.file.fileUrl;
		var fileType=path.substring(path.lastIndexOf(".")+1);
		fileType = fileType.toLowerCase();
		if (fileType=="bmp"||fileType=="jpeg"||fileType=="jpg"||fileType=="png"||fileType=="gif") {
			lookUpDom = "<a href='javascript:void(0)' onclick=lookUpFileByPath('"+row.file.fileUrl+"','"+ fileName+"') class='download'><font color='blue'>预览</font> | </a> ";
		}
	}
	if(editType == '3'){
		var dataEditDom = "<a href='javascript:void(0)'><font color='gray'>上传</font></a> | ";
	}else{
		var dataEditDom = "<a href='javascript:void(0)' class='download' onclick='addContract("+row.pid+")'><font color='blue'>上传</font></a> | ";
	}	
	
	if(row.file == null || row.file ==undefined ||row.file.fileUrl=='' || row.file.fileUrl==null ||row.file.fileUrl==undefined || editType == '3'){
		var dataDelDom = "<a href='javascript:void(0)' ><font color='gray'>删除</font></a>";
	}else{
		var dataDelDom = "<a href='javascript:void(0)' class='download'  onclick='delData("+row.pid+")'><font color='blue'>删除</font></a>";
	}
	return downloadDom+lookUpDom+dataEditDom+dataDelDom;
}

function delData(pid){
	if(pid=="" || null==pid){
		 $.messager.alert("操作提示","该类型文件不存在，无法删除！","error"); 
		 return false;
	}else{
		if (confirm("是否确认删除?")) {
			$.ajax({
				type: "POST",
		        url: "${basePath}orgCooperatCompanyApplyController/delContractFile.action",
		        data:{"pid":pid},
		        dataType: "json",
		        success: function(ret){
					if(ret && ret.header["success"]){
						$.messager.alert('操作提示','删除成功','info');
		        		$('#additionFile_grid').datagrid("reload");
		        		$('#additionFile_grid').datagrid("clearChecked");
					}else{
						$.messager.alert('操作提示',ret.header["msg"],'error');	
					}
		        }
			});
		}
	}
}

function addContract(contractId){
	if(pid == ''){
		$.messager.alert('操作提示',"请先保存申请信息！",'error');
		return;
	}
	$('#additionFileForm').form('clear');
	$('#cooperationId').val(pid);
	$('#contractId').val(contractId);
	$('#additionFileForm').attr('action',"${basePath}orgCooperatCompanyApplyController/uploadFile.action");
	$('#dlg_extensionFile').dialog('open').dialog('setTitle','上传合同');
}

function saveContract(){
	$('#additionFileForm').form('submit',{
		success:function(data){
			var ret = eval("("+data+")");
			if(ret && ret.header["success"]){
				$.messager.alert('操作提示','保存信息成功','info');
				$('#dlg_extensionFile').dialog("close");
				$('#additionFile_grid').datagrid("reload");
				$('#additionFile_grid').datagrid("clearChecked");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
		}
	})
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
				<form action="<%=basePath%>orgCooperatCompanyApplyController/saveOrgCompanyApplyInfo.action" id="orgForm" name="orgForm" method="post">
					<input type="hidden" name="orgId" id="orgId" value="${orgAssetsInfo.pid }">
					<input type="hidden" name="pid" id="pid" value="${orgCooperateInfo.pid }">
					<input type="hidden" name="userId" value="${orgAssetsInfo.pid }">
					<input type="hidden" id="applyStatus" name="applyStatus" value="${orgCooperateInfo.applyStatus }">
					<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
						<tr>
							<td class="label_right"><font color="red">*</font>合作开始时间：</td>
							<td>
								<input value="${orgCooperateInfo.startTime}" name="startTime" class="easyui-datebox" id="startTime" editable="false" style="width:150px;"   required="true" missingMessage="请输入合作开始日期!"/>
							</td>
							<td class="label_right"><font color="red">*</font>合作结束时间：</td>
							<td>
								<input value="${orgCooperateInfo.endTime}" name="endTime" validType="checkDateRange['#startTime']" class="easyui-datebox" editable="false" style="width:150px;"   required="true" missingMessage="请输入合作结束日期!"/>
							</td>
						</tr>
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
							<td class="label_right"><font color="red">*</font>需要保后监管：</td>
							<td>
								<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="isNeedHandle" style="width:129px;">
									<option value="-1">--请选择--</option>
									<option value="1" <c:if test="${orgCooperateInfo.isNeedHandle==1 }">selected </c:if>>需要</option>
									<option value="2" <c:if test="${orgCooperateInfo.isNeedHandle==2 }">selected </c:if>>不需要</option>
								</select>
							</td>
<!-- 							<td class="label_right1"><font color="red">*</font>合作城市：</td> -->
<!-- 							<td> -->
<!-- 								<input name="cooperationCitys" id="cooperationCity" data-options="validType:'selrequired',multiple : true" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/> -->
<!-- 							</td> -->
						</tr>
						<tr>
							<td class="label_right">保证金备注：</td>
							<td colspan="4">
								<input  class="easyui-textbox" name="assureMoneyRemark" value="${orgCooperateInfo.assureMoneyRemark}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
							</td>
						</tr>
						<tr>
							<td class="label_right">合作说明：</td>
							<td colspan="4">
								<input  class="easyui-textbox" name="remark" value="${orgCooperateInfo.remark}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
							</td>
						</tr>
					</table>
					<div style="padding-bottom: 20px;padding-top: 10px;padding-left: 100px;">
						<a href="#" class="easyui-linkbutton" style="margin-left: 80px;" iconCls="icon-save" onclick="saveOrgCooperateInfo()">保存</a>
					</div>
				</form>
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
<!--  附件上传start -->
<div id="dlg_buttons_extensionFile">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveContract()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_extensionFile').dialog('close')">关闭</a>
</div>
<div id="dlg_extensionFile" class="easyui-dialog" fitColumns="true"  buttons="#dlg_buttons_extensionFile" 
 	style="width:600px;top:100px;padding: 10px;" closed="true" >
	<form id="additionFileForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="cooperationId" id="cooperationId"/>
		<input type="hidden" name="pid" id="contractId"/>
		<table>
			<tr id="fileTypeTr">
				<td width="120" align="right" height="28"><font color="red">*</font>合同编号：</td>
				<td>
					<input class="easyui-textbox" name="contractNo"  data-options="required:true,validType:'length[1,20]'" required="required" style="width:150px;"/>
				</td>
			</tr>
			<tr id="fileUrlTr">
				<td align="right" width="120" height="28"><font color="red">*</font>文件选择：</td>
				<td>
			        <input class="easyui-filebox" class="download" data-options="buttonText:'选择文件',missingMessage:'选择一个文件'" id="fileName" name="fileName" style="width:240px;" required="true"/>
				</td>
			</tr>
			<tr id="fileDescTr">
				<td width="120" align="right">上传说明：</td>
				<td><input  class="easyui-textbox" style="width:300px;height:50px" name="remark" id="remark" data-options="multiline:true,validType:'length[0,800]'"></td>
			</tr>
		</table>
	</form>
</div>
<!--  附件上传end -->
</div>
</body>
</html>