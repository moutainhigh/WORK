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
<script type="text/javascript">
//重置按钮
function majaxReset() {
	$('#searchFrom').form('reset');
}
function formatterFddMortgageStatus(val, row) {
	if (val == 1) {
		return "待登记入库";
	} else if (val == 2) {
		return "待出库申请";
	} else if (val == 3) {
		return "待出具注销材料";
	}  else if (val == 4) {
		return "待退证登记";
	} else if (val == 5) {
		return "已完结";
	} else {
		return "未知";
	}
}
function formatterRepaymentStatus(val, row) {
	if (val == 1) {
		return "正常还款中";
	} else if (val == 2) {
		return "已结清";
	} else if (val == 3) {
		return "逾期还款中";
	} else if (val == 4) {
		return "已逾期";
	}else{
		return "--";
	}
}
function formatProjectName(value, row, index){
	var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>";
	return va;
}
//打开入库登记弹窗
function openRegister(){
    var row = $('#grid').datagrid('getSelections');
	if (row.length == 1) {
		$('#storageinfoForm').form('reset');//清空入库登记表单
		var projectName=row[0].projectName;
		var projectId=row[0].projectId;
		var projectMortgageId=row[0].pid;
		$("#storageinfoFormProjectId").val(projectId);
		$("#storageinfoFormProjectMortgageId").val(projectMortgageId);
		$('#register_dialog').dialog('open').dialog('setTitle', "入库登记--"+projectName);
		loadStorageInfoListGrid(projectId);
	} else if (row.length > 1) {
		$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");       
	} else {
		$.messager.alert("操作提示", "请选择数据", "error");
	}
}
//加载入库材料清单
function loadStorageInfoListGrid(projectId) {
	$('#storageinfo_grid').datagrid({  
		url:'queryStorageInfo.action',  
		queryParams:{  
		    	projectId : projectId
		},//当列表数据加载完毕
        onLoadSuccess:function(data){  
        }
    }); 
}
//提交入库资料清单
function subStorageinfoForm() {
	$("#storageinfoForm").form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				$.messager.alert('操作提示','保存成功!','info');	
 				$("#storageinfo_grid").datagrid("clearChecked");
 				$("#storageinfo_grid").datagrid('reload');
 				$("#grid").datagrid("clearChecked");
				$("#grid").datagrid('reload');
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}
//根据ids删除入库资料清单
function deleteStorageInfoByIds() {
	var rows = $('#storageinfo_grid').datagrid('getSelections');
	if (rows.length >0) {
		var storageinfoFormProjectMortgageId=$("#storageinfoFormProjectMortgageId").val();
		var projectId=$("#storageinfoFormProjectId").val();
		var ids="";
	 	for (var i = 0; i < rows.length; i++) {
 	 	    if (i == 0) {
				ids += rows[i].pid;
			} else {
				ids += "," + rows[i].pid;
			}
		}
		$.ajax({
			url : "${basePath}bizProjectMortgageController/deleteStorageInfoByIds.action",
			cache : true,
			type : "POST",
			data : {'ids' : ids,'projectMortgageId' : storageinfoFormProjectMortgageId},
			async : false,
			success : function(data, status) {
				var ret = eval("("+data+")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示',ret.header["msg"],'info');	
					$("#storageinfo_grid").datagrid("clearChecked");
	 				$("#storageinfo_grid").datagrid('reload');
	 				$("#grid").datagrid("clearChecked");
	 				$("#grid").datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		 });
	} else {
		$.messager.alert("操作提示", "请选择数据", "error");
	}
}

//打开签收办理弹出框
function openHandle(type){
    var row = $('#grid').datagrid('getSelections');
	if (row.length == 1) {
		$('#handleForm').form('reset');//清空表单
		var projectName=row[0].projectName;
		var mortgageStatus=row[0].mortgageStatus;
		var pid=row[0].pid;
		var typeName="";
		if (type==2&&mortgageStatus==2) {
			typeName="出库申请";
		}else if(type==3&&mortgageStatus==3){
			typeName="出具注销材料";
		}else if(type==4&&mortgageStatus==4){
			typeName="退证登记";
		}else if(mortgageStatus==5){
			$.messager.alert("操作提示", "已完结", "error");    
			return;
		}else{
			$.messager.alert("操作提示", "未到该节点办理,或者该节点已经办理", "error");    
			return;
		}
		$("#handleFormPid").val(pid);
		$("#handleFormType").val(type);
		$('#handle_dialog').dialog('open').dialog('setTitle', typeName+"--"+projectName);
		loadStorageInfoListGrid(projectId);
	} else if (row.length > 1) {
		$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");       
	} else {
		$.messager.alert("操作提示", "请选择数据", "error");
	}
}
function subHandleForm(){
	$("#handleForm").form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				$.messager.alert('操作提示','保存成功!','info');
				$('#handle_dialog').dialog('close');
 				$("#grid").datagrid("clearChecked");
 				$("#grid").datagrid('reload');
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}
//打开查看页面
function openShowInof(){
    var row = $('#grid').datagrid('getSelections');
	if (row.length == 1) {
		var projectName=row[0].projectName;
		var pid=row[0].pid;
		parent.openNewTab("${basePath}bizProjectMortgageController/toMortgageShow.action?pid="+pid, "查看抵押详情--"+projectName, true);
	} else if (row.length > 1) {
		$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");       
	} else {
		$.messager.alert("操作提示", "请选择数据", "error");
	}
}
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<!--图标按钮 -->
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="<%=basePath%>bizProjectMortgageController/queryMortgageIndexPage" method="post" id="searchFrom" name="searchFrom" >
			<!-- 查询条件 -->
			<div style="padding:5px">
				<table>
                 <tr>
                  <td width="80" align="right" height="28">项目名称：</td>
                  <td><input class="easyui-textbox" name="projectName" id="projectName" data-options="validType:'length[0,50]'"/></td>
                  <td width="80" align="right" height="28">项目编号：</td>
                  <td><input class="easyui-textbox" name="projectNumber" id="projectNumber" data-options="validType:'length[0,50]'"/></td>
                  <td width="80" align="right" height="28">还款状态：</td>
                  <td><select class="easyui-combobox" name="repaymentStatus" style="width: 125px" panelHeight="auto" editable="false">
                    <option value=-1 selected="selected">--请选择--</option>
                    <option value=1>正常还款中</option>
                    <option value=2>已结清</option>
                    <option value=3>逾期还款中</option>
                    <option value=4>已逾期</option>
                  </select></td>
                  <td width="80" align="right" height="28">待办状态：</td>
                  <td><select class="easyui-combobox" name="mortgageStatus" style="width: 125px" panelHeight="auto" editable="false">
                    <option value=-1 selected="selected">--请选择--</option>
                    <option value=1>待登记入库</option>
                    <option value=2>待出库申请</option>
                    <option value=3>待出具注销材料</option>
                    <option value=4>待退证登记</option>
                    <option value=5>已完结</option>
                  </select></td>
                 </tr>
                 <tr>
                  <td align="right" height="28">结清日期：</td>
                  <td><input class="easyui-datebox" editable="false" name="finishRepaymentDate"/>
                                                              至 <input class="easyui-datebox" editable="false" name="finishRepaymentEndDate" /></td>
                 </tr>
                  <tr>
		        <td colspan="6"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
		         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
		        >重置</a></td>
		       </tr> 
                </table>
			</div>
		</form>
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openShowInof()">查看</a>
			<shiro:hasAnyRoles name="FDD_AFTERLOAN_REGISTER,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openRegister()">入库登记</a>
			</shiro:hasAnyRoles>
			<shiro:hasAnyRoles name="FDD_AFTERLOAN_OUTPUT,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openHandle(2)">出库申请</a>
			</shiro:hasAnyRoles>
			<shiro:hasAnyRoles name="FDD_AFTERLOAN_CANCLE_FILE,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openHandle(3)">出具注销材料</a>
			</shiro:hasAnyRoles>
			<shiro:hasAnyRoles name="FDD_AFTERLOAN_RETURN_REGISTER,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openHandle(4)">退证登记</a>
			</shiro:hasAnyRoles>
		</div>
		
	</div>
	<div class="dksqListDiv" style="height:100%;">
	<table id="grid" title="抵押列表" class="easyui-datagrid" 
		style="height:100%;width: auto;"
		data-options="
		    url: '<%=basePath%>bizProjectMortgageController/queryMortgageIndexPage.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true">
		<!-- 表头 -->
		<thead data-options="frozen:true">
			 <tr>
				<th data-options="field:'pid',checkbox:true" ></th>
		    	<th data-options="field:'projectName',formatter:formatProjectName,sortable:true" align="center" halign="center"  >项目名称</th>
			 </tr>
	 	</thead>
		<thead><tr>
		    <th data-options="field:'projectNumber',sortable:true" align="center" halign="center"  >项目编号</th>
		    <th data-options="field:'repaymentStatus',sortable:true,formatter:formatterRepaymentStatus" align="center" halign="center"  >还款状态</th>
		    <th data-options="field:'mortgageStatus',sortable:true,formatter:formatterFddMortgageStatus" align="center" halign="center"  >代办状态</th>
		    <th data-options="field:'finishRepaymentDate',sortable:true,formatter:convertDate" align="center" halign="center"  >结清时间</th>
            </tr></thead>
	</table>
	</div>
	</div>
	
     <!-- 入库登记弹出框 -->
	<div id="register_dialog" class="easyui-dialog" data-options="modal:true" buttons="#subRegisterDiv" style="width: 600px; height: 450px; padding:10px;" closed="true">
    <form id="storageinfoForm" name="storageinfoForm" action="${basePath}bizProjectMortgageController/saveStorageInfo.action" method="post">
    <table border="0" cellpadding="0" cellspacing="0" >
    <tr>
        <td class="label_right1"><font color="red">*</font>资料名称:</td>
		<td>
		<input name="fileName" class="easyui-textbox" data-options="required:true,validType:'length[0,50]'">
		</td>
        <td class="label_right1"><font color="red">*</font>入库时间:</td>
        <td>
		<input name="registerTime" class="easyui-datebox" editable="false" data-options="required:true">
		</td>
    <tr>
       <tr>
          <td class="label_right">说明：</td>
          <td colspan="3"><textarea rows="3" name="fileDesc" maxlength="500" style="width: 100%;"></textarea></td>
         </tr>
         <tr>
         <td class="label_right">
          <a href="#" id="saveStorageinfoFormBut" class="easyui-linkbutton" iconCls="icon-save" onclick="subStorageinfoForm()">保存</a> 
          <a href="#" id="deleteStorageInfoByIdsBut" class="easyui-linkbutton" iconCls="icon-save" onclick="deleteStorageInfoByIds()">删除</a> 
          </td>
          </tr>
       </table>
       <input name="projectId" id="storageinfoFormProjectId" type="hidden">
       <input name="storageinfoFormProjectMortgageId" id="storageinfoFormProjectMortgageId" type="hidden">
    </form>
    <table id="storageinfo_grid"  title="入库资料清单" class="easyui-datagrid" style="height: 270px; width: auto;"
     data-options="
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true" />
       <th data-options="field:'fileName'" align="center" halign="center">资料名称</th>
       <th data-options="field:'registerTime',formatter:convertDate" align="center" halign="center">入库时间</th>
       <th data-options="field:'fileDesc'" align="center" halign="center">说明</th>
       <th data-options="field:'createName'" align="center" halign="center">操作人</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="subRegisterDiv">
    <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#register_dialog').dialog('close')"
    >关闭</a>
   </div>
   <!-- 签收办理弹出框 -->
	<div id="handle_dialog" class="easyui-dialog" data-options="modal:true" buttons="#subHandleDiv" style="width: 260px; height: 200px; padding:10px;" closed="true">
    <form id="handleForm" name="handleForm" action="${basePath}bizProjectMortgageController/saveHandleInfo.action" method="post">
    <table border="0" cellpadding="0" cellspacing="0" >
    <tr>
        <td class="label_right1"><font color="red">*</font>签收人:</td>
		<td>
		<input name="signerUserName" class="easyui-textbox" data-options="required:true,validType:'length[0,50]'">
		</td>
    <tr>
    <tr>
        <td class="label_right1"><font color="red">*</font>签收材料:</td>
		<td>
		<input name="signerFile" class="easyui-textbox" data-options="required:true,validType:'length[0,50]'">
		</td>
    <tr>
    <tr>
        <td class="label_right1"><font color="red">*</font>签收日期:</td>
		<td>
		<input name="signerDate" class="easyui-datebox" editable="false" data-options="required:true">
		</td>
    <tr>
       </table>
       <input name="pid" id="handleFormPid" type="hidden">
       <input name="handleType" id="handleFormType" type="hidden">
    </form>
   </div>
   <div id="subHandleDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subHandleForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#handle_dialog').dialog('close')"
    >取消</a>
   </div>
	
</div>

</body>
</html>
