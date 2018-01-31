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
$(document).ready(function(){
	var productId = $("#productId").combobox('getValue');//产品ID
	$('#productId').combobox({   
		url:'${basePath}productController/getProductLists.action?productType=3',    
	    valueField:'pid',
	    textField:'productName',
	    onLoadSuccess: function(rec){
	    	if(productId >0){
	    		$('#productId').combobox('setValue',productId);
	    	}else{
	    		$('#productId').combobox('setValue',-1);
	    	}
        } 
	});

	$('#grid').datagrid({  
		 onDblClickRow: function (rowIndex, rowData){
			 var url="";
			 url = "${basePath}projectInfoController/toEditProject.action?type=2&projectId="+rowData.pid+"&param='projectId':'"+rowData.pid+"','projectName':'"+rowData.projectName+"','WORKFLOW_ID':'"+rowData.loanWorkProcess+"'";
			 parent.openNewTab(url,"查看抵押贷款申请",true);
		 }
	 });
})

// 格式所有datebox的长度,默认全部设置为100px
	$(document).ready(function(){
		$('.easyui-datebox').datebox({    
			width:150   
		});  

	});
//重置按钮
function majaxReset(){
	$('#searchFrom').form('reset');
}

// 新增房抵贷项目信息
function addItem(){
	parent.openNewTab("${basePath}projectInfoController/toEditProject.action?type=2","新增抵押贷款申请");
}

// 修改房抵贷项目信息
function editItem() {
	// 获取选中的行
	var row = $('#grid').datagrid('getSelections'), isEdit = 2, loginId = "${shiroUser.pid}";
	// 保证必须选取客户数据
	if(row.length == 0){
		$.messager.alert("操作提示","请选择项目信息!","error");
		return false;
	}
	if(row[0].foreclosureStatus > 1) {
		$.messager.alert("操作提示","贷款申请提交流程后不允许进行编辑！","error");
		return;
	}
	if(loginId != row[0].recordClerkId && loginId != row[0].pmUserId){
		$.messager.alert("操作提示","贷款申请只有收单人以及录单员才能进行编辑！","error");
		return;
	}
	var url ="${basePath}projectInfoController/toEditProject.action?type="+ isEdit +"&projectId="+row[0].pid;
	var datas = getTaskVoByWPDefKeyAndRefId(row[0].loanWorkProcess, row[0].pid);
	if(datas) {
		url += "&"+datas;
	}
	parent.openNewTab(url, "编辑抵押贷款申请");
}

function lookupItem(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
		url = "${basePath}projectInfoController/toEditProject.action?type=2&projectId="+row[0].pid+"&param='projectId':'"+row[0].pid+"','projectName':'"+row[0].projectName+"','WORKFLOW_ID':'"+row[0].loanWorkProcess+"'";
		parent.openNewTab(url,"查看抵押贷款申请",true);
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
var foreInformationList = {
		// 项目名称format
		formatProjectName:function(value, row, index){
			var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.pid+")'> <font color='blue'>"+row.projectName+"</font></a>";
			return va;
		},
		formatTooltip:function(value, row, index){
			if (value==null) {
				return "";
			}
	        var abValue = value;
            if (value.length>=12) {
               abValue = value.substring(0,10) + "...";
            }
			var va = '<a href="#" title="' + value + '" class="note">' + abValue + '</a>';
            return va;
		},
		
		// 资金方format
		formatCapitalName:function(value, row, index){
			if(row.capitalName == null || row.capitalName == 'undefined'){
				var va='';
			}else{
				var va="<a href='javascript:void(0)' onclick = 'capitalClick("+row.pid+")'> <font color='blue'>"+row.capitalName+"</font></a>";
			}
			return va;
		}
	}
function capitalClick(pid){
	var url = "<%=basePath%>projectInfoController/getCapitalSelRecord.action?projectId="+pid;
	$('#capital_grid').datagrid('options').url = url;
	$('#capital_grid').datagrid('reload');
	$('#capital_sel_record').dialog('open').dialog('setTitle',row[0].projectName+"资方选择记录");
}
function formatCapitalStatus(val,row,index){
	if (val == 1) {
		return "未审批";
	} else if (val == 0) {
		return "--";
	}
}
</script>
<title>贷前管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<!--图标按钮 -->
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="<%=basePath%>projectInfoController/getProjectByPage.action" method="post" id="searchFrom" name="searchFrom" >
			<!-- 查询条件 -->
			<div style="padding:5px">
				<table>
					<tr>
						<td width="80" align="right" >项目名称：</td>
						<td>
							<input name="projectName" id="projectName" class="easyui-textbox" style="width:150px;"/>
						</td>
						<td width="80" align="right" >项目编号：</td>
						<td>
							<input name="projectNumber" id="projectNumber" class="easyui-textbox" style="width:150px;"/>
						</td>
					</tr>
					<tr>
						<td width="80" align="right" height="28">收单人：</td>
						<td>
							<input name="realName" id="realName" class="easyui-textbox" style="width:150px;"/>
						</td>
						<td width="100" align="right"  height="28">产品名称：</td>
						<td colsapn="2">
							<input class="easyui-combobox" editable="false" name="productId" id="productId" style="width:150px;"/>						
						</td>
					</tr>
					<tr>
						<td width="80" align="right" >待办理节点：</td>
						<td>
							<select class="easyui-combobox"  editable="false" name="foreclosureStatus"  style="width:150px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${project.foreclosureStatus==1 }">selected </c:if>>待提交</option>
								<option value="2" <c:if test="${project.foreclosureStatus==2 }">selected </c:if>>待评估</option>
								<option value="3" <c:if test="${project.foreclosureStatus==3 }">selected </c:if>>待下户</option>
								<option value="6" <c:if test="${project.foreclosureStatus==6 }">selected </c:if>>待复审</option>
								<option value="7" <c:if test="${project.foreclosureStatus==7 }">selected </c:if>>待终审</option>
								<option value="8" <c:if test="${project.foreclosureStatus==8 }">selected </c:if>>待放款申请</option>
								<option value="9" <c:if test="${project.foreclosureStatus==9 }">selected </c:if>>待放款审核</option>
								<option value="10" <c:if test="${project.foreclosureStatus==10 }">selected </c:if>>待资金放款</option>
								<option value="11" <c:if test="${project.foreclosureStatus==11 }">selected </c:if>>还款中</option>
								<option value="12" <c:if test="${project.foreclosureStatus==12 }">selected </c:if>>已结清</option>
							</select>
						</td>
						 <td width="80" align="right" >业务来源：</td>
						<td>
							<input name="businessSourceStr" id="businessSourceStr" class="easyui-textbox" style="width:150px;"/>
						</td>
					</tr>
					<tr>
						<td width="100" align="right" >申请时间：</td>
						<td colsapn="2">
							<input name="beginRequestDttm" id="beginRequestDttm" class="easyui-datebox" editable="false"/> <span>~</span> 
							<input name="endRequestDttm" id="endRequestDttm" class="easyui-datebox" editable="false"/>
						</td>
							<td width="80" align="right" >录单员：</td>
						<td>
							<input name="declaration" id="declaration" class="easyui-textbox" style="width:150px;"/>
						</td>
						<td colspan="2">
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
							<a href="#" onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;">重置</a>
						</td>
					</tr>			
				</table>
			</div>
		</form>
		
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
			<shiro:hasAnyRoles name="ADD_FDD_PERMISSION,ALL_BUSINESS">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
			</shiro:hasAnyRoles>
			<shiro:hasAnyRoles name="UPDATE_FDD_PERMISSION,ALL_BUSINESS">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
			</shiro:hasAnyRoles>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
		</div>
	</div>
	<div class="dksqListDiv" style="height:100%;">
	<table id="grid" title="贷款申请列表"
		style="height:100%;width: auto;" class="easyui-datagrid"
		data-options="
		    url: '<%=basePath%>projectInfoController/getProjectByPage.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true, 
		    pagination: true,
		    sortOrder:'desc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid', 
		    fitColumns:true">
		<thead data-options="frozen:true">
           <tr>
            <th data-options="field:'pid',checkbox:true" ></th>
		    <th data-options="field:'projectName',formatter:foreInformationList.formatProjectName" align="center" halign="center" >项目名称</th>
           </tr>
        </thead>
		<thead><tr>
			<th data-options="field:'projectNumber'" align="center" halign="center">项目编号</th>
		    <th data-options="field:'productName'" align="center" halign="center"  >产品名称</th>
		    <th data-options="field:'businessSourceStr'" align="center" halign="center">业务来源</th>
		    <th data-options="field:'acctName'" align="center" halign="center">客户名称</th>
		    <th data-options="field:'applyLoanMoney',formatter:formatMoney" align="center" halign="center"  >申请贷款金额</th>
		    <th data-options="field:'loanMoney',formatter:formatMoney" align="center" halign="center"  >贷款金额</th>
		    <th data-options="field:'foreclosureStatus',formatter:formatterProjectStatus" align="center" halign="center">办理节点</th>
		    <th data-options="field:'nextUserName'" align="center" halign="center"  >待办人</th>
		    <th data-options="field:'requestDttm'" align="center" halign="center">提单日期</th>
		    <th data-options="field:'declaration'" align="center" halign="center">录单员</th>
		    <th data-options="field:'realName'" align="center" halign="center"  >收单人</th>
		    <th data-options="field:'loanType'" align="center" halign="center"  >放款条件</th>
		    <th data-options="field:'capitalName',formatter:foreInformationList.formatCapitalName" align="center" halign="center"  >资方</th>
		</tr></thead>
	</table>
	</div>
	<!-- 资方选择记录展示 -->
	<div id="capital_sel_record" class="easyui-dialog" fitColumns="true"  title="资方选择记录"
				style="width:666px;" data-options="modal:true" buttons="#" closed="true" >
				<table id="capital_grid"   class="easyui-datagrid"
					style="height: 300px;width: auto;"
					data-options="
					url: '',
					method: 'post',
					rownumbers: true,
					fitColumns:true,
					singleSelect: false,
					pagination: false,
					idField: 'pid'
					">
					<thead>
						<tr>
							<th data-options="field:'capitalName'" >资方</th>
							<th data-options="field:'createrName'" >操作人</th>
							<th data-options="field:'createDate'" >操作时间</th>
							<th data-options="field:'capitalApprovalStatus',formatter:formatCapitalStatus" >审批状态</th>
							<th data-options="field:'capitalLoanStatus',formatter:formatCapitalStatus" >放款状态</th>
							<th data-options="field:'reviewName'" >复核人</th>
							<th data-options="field:'reviewTime'" >复核时间</th>
						</tr>
					</thead>
				</table>
			</div>
	<!-- 资方选择记录展示结束 -->
	</div>
	</div>
</body>
</html>
