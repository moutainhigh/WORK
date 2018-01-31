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
<title>要件签收</title>
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
var pid = ${elementLend.pid};
var projectId = ${elementLend.projectId};
/**
 * 关闭窗口
 */
function closeWindow() {
	//parent.removePanel();
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

		parent.$('#layout_center_tabs').tabs('close','要件归还签收');
}

function saveElementLend(){

	$('#elementLendForm').form('submit', {
		url : "saveElementLend.action",
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			$.messager.confirm('操作提示','保存成功!',function(r){
				window.setTimeout("closeWindow()", 500);
			});
		},error : function(result){
			alert("保存失败！");
		}
	});
}

$(function(){
	
	var lendFilesId = $("#lendFilesId").val();
	var returnFilesId = $("#returnFilesId").val();
	
	var url = "<%=basePath%>elementLendController/getLendFileList.action?pid="+pid;
	$('#lend_file').datagrid('options').url = url;
	$('#lend_file').datagrid('reload');
	
	var url = "<%=basePath%>elementLendController/getReturnFileList.action?pid="+pid;
	$('#return_file').datagrid('options').url = url;
	$('#return_file').datagrid('reload');
});

</script>

<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="要件签收" id="tab1" style="padding: 10px;">
<form id="elementLendForm" action="<%=basePath%>elementLendController/saveElementLend.action" method="POST">
	<input type="hidden" name="lendState" value="5"/>
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
				<input class="easyui-textbox" type="text" value="${elementLend.orgName}" disabled="disabled" data-options="required:true"/>
			</td>
			<td class="label_right"><span class="cus_red">*</span>经办人：</td>
			<td>
				<input type="hidden" name="lendUserId" value="${elementLend.lendUserId}" />
				<input type="text" class="easyui-textbox" data-options="required:true" disabled="disabled" value="${elementLend.realName}" />
			</td>
		</tr>
		<tr>
			<td class="label_right"><span class="cus_red">*</span>借出日期：</td>
			<td><input value="${elementLend.lendTime}" class="text_style easyui-datebox" disabled="disabled" editable="false" data-options="required:true" missingMessage="请输入借出日期!" /> </td>
			<td class="label_right"><span class="cus_red">*</span>归还日期：</td>
			<td>
				<input value="${elementLend.originalReturnTime}" disabled="disabled" class="text_style easyui-datebox" editable="false" data-options="required:true" missingMessage="请输入归还日期!" />
			</td>
			<td class="label_right"><span class="cus_red">*</span>实际归还日期：</td>
			<td>
				<input value="${elementLend.actualReturnTime}" id="actualReturnTime" disabled="disabled" class="text_style easyui-datebox" editable="false" data-options="required:true" missingMessage="请输入归还日期!" />
			</td>
			<td class="label_right"><span class="cus_red">*</span>用途：</td>
			<td>
				<input type="text" class="easyui-textbox" disabled="disabled" data-options="required:true,validType:'length[1,200]'" value="${elementLend.porpuse}" />
			</td>
		</tr>
	</table>
     <div class="pt10"></div>
     <div class="panel-body1 pt10" style="padding: 10px 10px 0 10px;width: 1039px;">
	<div class=" easyui-panel yj" title="已借出要件" data-options="collapsible:true">
	<table id="lend_file" class="easyui-datagrid" fitColumns="true" style="width:auto;height: auto;"
				     data-options="
				     	url: '',
				     	method: 'post',
				     	rownumbers: true,
			    		fitColumns:true,
				    	singleSelect: false,
					    pagination: false">
		<thead>
			<tr>
				<th data-options="field:'fileId',checkbox:true" ></th>
				<th data-options="field:'fileName'" >文件名称</th>
				<th data-options="field:'uploadDttm',formatter:convertDate" >收件日期</th>
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
				<th data-options="field:'fileId',checkbox:true" ></th>
				<th data-options="field:'fileName'" >文件名称</th>
				<th data-options="field:'uploadDttm',formatter:convertDate" >收件日期</th>
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
    <td style="width:60px; text-align: right;"><span class="cus_red">*</span>签收人：</td>
	<td>
		<input type="text" name="signUser" class="easyui-textbox" data-options="required:true,validType:'length[1,20]'" value="${elementLend.signUser}" />
	</td>
	<td class="label_right"><span class="cus_red">*</span>签收日期：</td>
	<td>
		<input value="${elementLend.signTime}" name="signTime" class="text_style easyui-datebox" editable="false" data-options="required:true" missingMessage="请输入签收日期!" />
	</td>
    </tr>
    <tr>
     <td></td>
     <td class="align_center"  height="50">
		<a href="#"class="easyui-linkbutton" iconCls="icon-save" class="cus_save_btn" name="save" value="签收" onclick="saveElementLend();">签收</a>
    	<a href="#"class="easyui-linkbutton" iconCls="icon-clear" onclick="closeWindow()">取消</a>
    </td>
    
    <td></td>
   </tr>
	</table>
	</div>
</form>    
   </div>
  </div>
 </div>
</body>
</html>  
