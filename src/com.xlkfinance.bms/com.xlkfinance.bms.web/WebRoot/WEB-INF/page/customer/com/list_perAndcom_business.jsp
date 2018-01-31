<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">

</style>

<script type="text/javascript">

//查询 
function ajaxSearch(){
	var pageNumber=$('#grid').datagrid('options')['pageSize'];
	$('#rows').val(pageNumber);
	$('#searchFrom').form('submit',{
        success:function(data){
        	var str = JSON.parse(data);
           	$('#grid').datagrid('loadData',str);
        }
    });
}
$(function() {
	setCombobox("acct_Type","CUS_TYPE","${queryPersonDTO.acctType}");
	setCombobox("business_Variet","LOAN_BUSINESS_TYPE","${queryPersonDTO.businessVariet}");
	//setCombobox("is_complete","COMPLETE_TYPE","${queryPersonDTO.isComplete}");
	
	//setcombobox("is_complete","is_complete",1);
	//var s=$('#is_complete').combobox('getValue')
	//alert(s);
	//$('#isC').value(s);
});
//查看客户详细信息
function acctName(value,rowData,rowIndex){
	var cusType=rowData.value2;
	if(cusType=='企业'){
		return "<a href='javascript:parent.openNewTab(\"${basePath}customerController/editComBases.action?acctId="+rowData.value17+"&flag=2&currUserPid="+${currUser.pid}+"&comId="+rowData.value17+"\",\"企业客户详情\",true)'><font color='blue'>"+value+"</font></a>";
	}
	else{
		return "<a href='javascript:parent.openNewTab(\"${basePath}customerController/editPerBase.action?acctId="+rowData.value17+"&flag=2&currUserPid="+${currUser.pid}+"\",\"个人客户详情\",true)'><font color='blue'>"+value+"</font></a>";
		}
	}
function showProject(pId,pName){
	window.parent.addTab("${basePath}beforeLoanController/addNavigation.action?projectId="+pId+"&param='refId':'"+pId+"','projectName':'"+pName+"'","项目详情");	
}
function projectName(value,rowData,rowIndex){
	var pId = rowData.pid;
	var pName = rowData.value3;
	var projectDom = "<a href='javascript:void(0)' onclick='showProject("+pId+",\""+pName+"\")'><font color='blue'>"+pName+"</font> | </a>";
	return projectDom;
	//return "<a href='javascript:parent.openNewTab(\"${basePath}beforeLoanController/addNavigation.action?projectId="+rowData.pid+"&param='refId':'"+rowData.pid+"','projectName':'"+rowData.value3+"'\",\"贷款申请\",true)'><font color='blue'>"+value+"</font></a>";
}

</script>
<body>
<div id="roleDiv" style="width:100%; height:100%; float: left;">
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="" method="post" id="searchFrom" name="searchFrom" >
		<!-- 查询条件 -->
		<div style="padding:5px">
			<table>
			<tr>
				<td width="80" align="right" height="25">客户名称：</td>
				<td><input class="easyui-textbox" name="cusName" /> </td>
				<td width="120" align="right">证件号码：</td>
				<td><input class="easyui-textbox" name="certNumber"/> </td>
				<td width="120" align="right">是否结清：</td>
				<td>
				<input name="isComplete" id="is_complete"
							class="easyui-combobox" style="width: 220px;" editable="false"
							panelHeight="auto"
							data-options="valueField:'lookupVal',textField:'lookupDesc',
				url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=COMPLETE_TYPE'" />
				<!-- <select id="is_complete" class="easyui-combobox" name="isComplete" style="width:150px">   
						</select> -->
				
				</td>
			</tr>
			<tr>
			 <td width="80" align="right" height="25">客户类型：</td>
					<td>
					   <select name="acctType" id="acct_Type" class="easyui-combobox" style="width: 150px;"></select>
					</td>
			<td width="80" align="right" height="25">业务品种 ：</td>
				<td>
					   <section name="businessVariet" id="business_Variet"  class="easyui-combobox" style="width: 150px;"></section>
					</td>
				<td align="right" colspan="3">
					<input type="hidden" name="page" value="1">
					<input type="hidden" name="rows" id="rows">
					<input type="hidden" name="isC" id="isC">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
					<a href="#" onclick="javascript:$('#searchFrom').form('reset');"
						iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
			</table>
		</div>
	</form>
	
	</div>
	<div class="comBaseInfoDiv" style="height:100%;">
	<table id="grid" title="业务往来信息查询" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: 'getBusinessAllInfo.action',
	    method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    width: '100%',
	 	height: '100%',  
	 	striped: true, 
	 	loadMsg: '数据加载中请稍后……', 	    
	    singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true
	    ">
	<!-- 表头 -->
		<thead><tr>
			<th data-options="field:'pid',hidden:true,checkbox:true,width:100" ></th>
		    <th data-options="field:'value1', width:'90',sortable:true,formatter:acctName"  align="center">客户名称</th>
		    <th data-options="field:'value2',width:'90',sortable:true" align="center">客户类型</th>
		    <th data-options="field:'value3',width:'100',sortable:true,formatter:projectName" align="center">贷款项目名称</th>
		    <th data-options="field:'value4',width:'100',sortable:true,formatter:formatMoney" align="right">授信金额</th>
		    <th data-options="field:'value5',width:'90',sortable:true" align="center">担保方式</th>
		    <th data-options="field:'value6',width:'90',sortable:true,formatter:formatMoney" align="right">贷款金额</th>
		    <th data-options="field:'value7',width:'90',sortable:true" align="center">利率</th>
		    <th data-options="field:'value8',width:'90',sortable:true" align="center">期限</th>
		    <th data-options="field:'value9',width:'100',sortable:true" align="center">授信开始时间</th>
		    <th data-options="field:'value10',width:'100',sortable:true" align="center">授信结束时间</th>
		    <th data-options="field:'value11',width:'100',sortable:true" align="center">放款日期</th>
		    <th data-options="field:'value12',width:'100',sortable:true" align="center">还款日期</th>
		    <th data-options="field:'value13',width:'100',sortable:true" align="center">是否结清</th>
		    <th data-options="field:'value14',width:'100',sortable:true" align="center">客户证件号码</th>
		    <th data-options="field:'value15',width:'100',sortable:true" align="center">客户联系电话</th>
		    <th data-options="field:'value16',width:'100',sortable:true"  align="center">业务经理 </th>
		    <th data-options="field:'value17'" hidden="true" align="center"> </th>
		</tr></thead>
	</table>
	</div>
</div>
</body>



 