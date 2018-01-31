<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<div>
	<div class="fitem" style="">
		<div class="easyui-panel" style="border: 0;">
			<div id="contact_toolbar" style="border-bottom: 0; padding-bottom: 10px;">
				<c:choose>			
					<c:when test="${project.foreclosureStatus <= 1 }">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="toAdd()">新增</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="toEdit()">修改</a>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<table id="contact_grid" data-options="url: '',method: 'post',rownumbers: true,fitColumns:true,singleSelect: true,toolbar: '#contact_toolbar', pagination: false"
			style="width: 1000px;height: auto;" fitColumns="true" class="easyui-datagrid">
			 <thead>
					<tr>
						<th data-options="field:'pid',hidden:'true'"></th>
					    <th data-options="field:'contactsName'" align="center" halign="center" >联系人</th>
						<th data-options="field:'contactsPhone'" align="center" halign="center">联系电话</th>
					    <th data-options="field:'contactsRalation'" align="center" halign="center">关系</th>				   
					    <th data-options="field:'cz',formatter:contact_caozuofiter" align="center" halign="center">操作</th>
					</tr>
			</thead>
		</table>
	</div>
</div>





<!-- 保存/关闭按钮 -->
<div id="dlg-buttons_edit">
	<a class="easyui-linkbutton" iconCls="icon-save" onclick="saveContact()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_edit').dialog('close')">关闭</a>
</div>


<script>


/**
 * 操作按钮
 */
function contact_caozuofiter(value,row,index){
	var status="${project.foreclosureStatus}";
	var showType="${showType}";
	if(status>1 || showType==2){
		var dataDelDom = "<a href='javascript:void(0)' class='consumerDelete'  onclick=''><font color='gray'>删除</font></a>";
	}else{
		var dataDelDom = "<a href='javascript:void(0)' class='consumerDelete'  onclick='delContact("+row.pid+")'><font color='blue'>删除</font></a>";
	}
	return dataDelDom;
	
}

/**
 * 删除联系人信息
 */
function delContact(pid){
	if (confirm("是否确认删除?")) {
		$.ajax({
			type: "POST",
	        url: "${basePath}consumeProjectInfoController/delContacts.action",
	        data:{"contactIds":pid},
	        dataType: "json",
	        success: function(ret){
	        	//var ret = eval("("+data+")");
	        	if(ret && ret.header["success"]){
					// 操作提示
					$.messager.alert('操作提示',ret.header["msg"]);
					//重新加载
					loadContactInfo("");
	        	}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');
				}
	        }
		});
	}
}
//新增抵押物
function toAdd(){
	$('#contact_form').form('reset');
	projectContactDialog(1);
}
//修改抵押物
function toEdit(){
	var row = $('#contact_grid').datagrid('getSelections');
 	if (row.length == 1) {
 		$('#contact_form').form('reset');	 		
 		$("#contact_form").initForm(row[0]);
 		projectContactDialog(2);
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
	
	
$(document).ready(function(){
	//获取联系人信息
	var projectId = ${project.pid};
	if(projectId){
		//$("#house_info").datagrid
		var url = "<%=basePath%>consumeProjectInfoController/getContactsByProjectId.action?projectId="+projectId;
		$('#contact_grid').datagrid('options').url = url;
		$('#contact_grid').datagrid('reload');		
	}
	
});
	
function projectContactDialog(type){
	//获取联系人信息
	var projectId = ${project.pid};
	if(projectId){
		$('#contact_form input[name="projectId"]').val(projectId);
	}
	if(type == 1)$('#contact_form input[name="pid"]').val("");
	var title = type == 1 ? "新增联系人信息" : "修改联系人信息";
	$("#dialog_edit").dialog({
		 title: title,
		 width: 400,
               modal: true,
               top: 200,
	});
	
	$("#dialog_edit").dialog("open").dialog("center");
}

//保存联系人信息
function saveContact(){
	$('#contact_form').form('submit', {
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);			
				var contactId = ret.header["contactId"];//返回的项目联系人ID
				//重新加载联系人信息
				loadContactInfo(contactId);
				//关闭弹窗
				$("#dialog_edit").dialog("close");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	});
}


/**
 * 重新加载联系人信息
 */
function loadContactInfo(contactId){
	
	var rows = $('#contact_grid').datagrid('getRows');
	// 联系人信息
	var contactIds = "";
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		contactIds += row.pid+",";
	  	}
	}
	contactIds+=contactId;
	// 重新加载联系人信息
	var url = "<%=basePath%>consumeProjectInfoController/getContactList.action?contactIds="+contactIds;
	$('#contact_grid').datagrid('options').url = url;
	$('#contact_grid').datagrid('reload');
}
	
</script>
