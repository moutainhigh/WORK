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
<title>要件借出</title>
<style type="text/css">
#baseInfo {
		width: 500px;
}
#baseInfo label {
	width: 250px;
}
#baseInfo label.error, #baseInfo input.submit {
	margin-left: 253px;
}
.yj .panel-body-noheader{border: 0;}
</style>
<script type="text/javascript">
/**************工作流字段 begin******************/
//赎楼展期处理申请
var WORKFLOW_ID = "elementLendRequestProcess"; 
var workflowTaskDefKey = "task_ElementLendProcess";
nextRoleCode = "DEPARTMENT_MANAGER";//
/**************工作流字段 end********************/
var pid = ${elementLend.pid};
var projectId = ${elementLend.projectId};
var refId = ${elementLend.pid};
function cancelElement(){
	//获取tabs对象
	var myObj = parent.$('#layout_center_tabs').tabs('getTab','要件借出');
	if(myObj){
	//获取iframe的URL
	var url = myObj[0].innerHTML;
	//打开数据
	parent.layout_center_addTabFun({
		title : '要件借出', //tab的名称
		closable : true, //是否显示关闭按钮
		content : url,//加载链接
		falg:true
	});
	}
	
	var tab = parent.$('#layout_center_tabs').tabs('getSelected');
	var index = parent.$('#layout_center_tabs').tabs('getTabIndex', tab);
	parent.$('#layout_center_tabs').tabs('close', index);
}

$(function(){
	
	$(
	'.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea')
	.attr('disabled', 'disabled');
	$(
			'.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea')
			.attr('readOnly', 'readOnly');
	$(
			'.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea')
			.addClass('l-btn-disabled');
	$('.panel-body .easyui-linkbutton:not(.download)')
			.removeAttr('onclick');
	
	var lendFilesId = $("#lendFilesId").val();
	var returnFilesId = $("#returnFilesId").val();
	
	var url = "<%=basePath%>elementLendController/getLendFileList.action?pid="+pid;
	$('#lend_file').datagrid('options').url = url;
	$('#lend_file').datagrid('reload');
	
	var url = "<%=basePath%>elementLendController/getReturnFileList.action?pid="+pid;
	$('#return_file').datagrid('options').url = url;
	$('#return_file').datagrid('reload');
});
/**
 * 买卖方转换
 */
function formatType(val,row){
	if(val == 1){
		return "买方";
	}else if(val == 2){
		return "卖方";
	}
}
</script>

<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="要件借出" id="tab1" style="padding: 10px;">
<form id="elementLendForm" action="<%=basePath%>elementLendController/saveElementLend.action" method="POST">
	<input type="hidden" name="lendState" value="${elementLend.lendState }"/>
	<input type="hidden" name="pid" value="${elementLend.pid }"/>
	<input type="hidden" id="lendFilesId" value="${elementLend.lendFilesId }"/>
	<input type="hidden" id="returnFilesId" value="${elementLend.returnFilesId }"/>
	<input type="hidden" id="projectId" value="${elementLend.projectId }"/>
	<table class="cus_table">
		<tr>
			<td class="label_right"><span class="cus_red">*</span>项目名称：</td>
			<td>
				<input class="easyui-textbox" id="projectName" disabled="disabled" class="easyui-textbox" type="text" value="${elementLend.projectName}" />
			</td>
			<td class="label_right"><span class="cus_red">*</span>产品名称：</td>
			<td>
				<input type="hidden" id="productId" name="productId" value="${elementLend.productId}" />
				<input class="easyui-textbox" id="productName" data-options="required:true" disabled="disabled" value="${elementLend.productName}" />
			</td>
			<td class="label_right"><span class="cus_red">*</span>部门：</td>
			<td>
				<input type="hidden" name="orgId" value="${elementLend.orgId}"/>
				<input class="easyui-textbox" type="text" name="orgName" value="${elementLend.orgName}" disabled="disabled" data-options="required:true"/>
			</td>
			<td class="label_right"><span class="cus_red">*</span>经办人：</td>
			<td>
				<input type="hidden" name="lendUserId" value="${elementLend.lendUserId}" />
				<input type="text" class="easyui-textbox" data-options="required:true" name="realName" disabled="disabled" value="${elementLend.realName}" />
			</td>
		</tr>
		<tr>
			<td class="label_right"><span class="cus_red">*</span>借出日期：</td>
			<td><input name="lendTime" value="${elementLend.lendTime}" class="text_style easyui-datebox" disabled="disabled" editable="false" data-options="required:true" missingMessage="请输入借出日期!" /> </td>
			<td class="label_right"><span class="cus_red">*</span>计划归还日期：</td>
			<td>
				<input name="originalReturnTime" value="${elementLend.originalReturnTime}" class="text_style easyui-datebox" disabled="disabled" editable="false" data-options="required:true" missingMessage="请输入归还日期!" />
			</td>
			<%-- <td class="label_right"><span class="cus_red">*</span>实际归还日期：</td>
			<td>
				<input value="${elementLend.actualReturnTime}" id="actualReturnTime" disabled="disabled" class="text_style easyui-datebox" editable="false" data-options="required:true" missingMessage="请输入归还日期!" />
			</td> --%>
			<td class="label_right"><span class="cus_red">*</span>用途：</td>
			<td>
				<input type="text" class="easyui-textbox" data-options="required:true,validType:'length[1,200]'" name="porpuse" value="${elementLend.porpuse}" />
			</td>
		</tr>
	</table>
     <div class="pt10"></div>
     <div class="panel-body1 pt10" style="padding: 10px 10px 0 10px;width: 1039px;">
	<div class=" easyui-panel yj" title="已借出要件" data-options="collapsible:true" >
	<table id="lend_file" class="easyui-datagrid" fitColumns="true" style="width: auto;height: auto;"
				     data-options="
				     	url: '',
				     	method: 'post',
				     	rownumbers: true,
			    		fitColumns:true,
				    	singleSelect: false,
					    pagination: false">
		<thead>
			<tr>
				<th data-options="field:'buyerSellerType',formatter:formatType" >买方/卖方</th>
				<th data-options="field:'buyerSellerName'" >买卖方姓名</th>
				<th data-options="field:'elementFileName'" >文件名</th>
				<th data-options="field:'remark'" >补充</th>
				<th data-options="field:'lendTime',formatter:convertDate" >借出日期</th>
			</tr>
		</thead>
	</table>	
	</div>
	
	<div class="pt10"></div>
	<div class=" easyui-panel yj" title="已归还要件" data-options="collapsible:true">
	<table id="return_file" class="easyui-datagrid" fitColumns="true" style="width:auto;height: auto;"
				     data-options="
				     	url: '',
				     	method: 'post',
				     	rownumbers: true,
			    		fitColumns:true,
				    	singleSelect: false,
					    pagination: false">
		<thead>
			<tr>
				<th data-options="field:'buyerSellerType',formatter:formatType" >买方/卖方</th>
				<th data-options="field:'buyerSellerName'" >买卖方姓名</th>
				<th data-options="field:'elementFileName'" >文件名</th>
				<th data-options="field:'remark'" >补充</th>
				<th data-options="field:'returnTime',formatter:convertDate" >归还日期</th>
			</tr>
		</thead>
	</table>	
	</div>
	<table class="cus_table" style="width:1030px; padding-top: 10px;">
	<tr>
  	<td style="width:60px; text-align: right;">备注：</td>
    <td>
    	<textarea style="height:60px;width:100%;" id="idea" class="text_style" name="remark">${elementLend.remark}</textarea>
    </td>
    </tr>
    <tr>
    <%-- <td style="width:60px; text-align: right;"><span class="cus_red">*</span>签收人：</td>
	<td>
		<input type="text" name="signUser" class="easyui-textbox" value="${elementLend.signUser}" />
	</td>
	<td class="label_right"><span class="cus_red">*</span>签收日期：</td>
	<td>
		<input value="${elementLend.signTime}" name="signTime"  disabled="disabled" class="text_style easyui-datebox" editable="false" data-options="required:true" missingMessage="请输入签收日期!" />
	</td> --%>
    </tr>
    <tr>
     <td></td>
     <td class="align_center"  height="50">
		<a href="#"class="easyui-linkbutton" iconCls="icon-save" class="cus_save_btn"  name="save" value="签收" onclick="saveElementLend();">保存</a>
    	<a href="#"class="easyui-linkbutton" iconCls="icon-clear" onclick="cancelElement()">取消</a>
    </td>
    
    <td></td>
   </tr>
	</table>
	</div>
</form>   
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
