<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>

<script type="text/javascript">
	$(function(){
		
		var orgId = $("#orgId").combobox('getValue');
		$('#orgId').combobox({
			url:'${basePath}elementLendController/getLoginUserOrgList.action',    
		    valueField:'id',
		    textField:'name',
		    onLoadSuccess: function(rec){
		    	if(orgId >0){
		    		$('#orgId').combobox('setValue',projectId);
		    	}else{
		    		$('#orgId').combobox('setValue',-1);
		    	}
	        } 
		});
		
		
		var productId = $("#productId").combobox('getValue');//产品ID
		$('#productId').combobox({   
			url:'${basePath}productController/getProductLists.action',    
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
				 parent.openNewTab('${basePath}elementLendController/editElementLend.action?pid='
							+ rowData.pid+"&status=2"+"&param='refId':'"+rowData.pid+"','projectName':'1'"+",'projectId':'"+rowData.value15+"'","要件借出查看",true);
			 }
		 });
	});

	// 编辑
	function editItem() {
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if(row[0].value14 == 1){
			url = '<%=basePath%>elementLendController/editElementLend.action?pid='+row[0].pid+"&status=1";
			//根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
			var datas = getTaskVoByWPDefKeyAndRefId("elementLendRequestProcess",row[0].pid);
			if(datas){
				url+="&"+datas;
			}
			parent.openNewTab(url,"修改要件借出",true);
			}else{
	 			$.messager.alert("操作提示","要件借出只有申请中才能进行编辑！","error");
	 		}
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}

	//查询 
	function ajaxSearch(){
		var pageNumber=$('#grid').datagrid('options')['pageSize'];
		$('#rows').val(pageNumber);
		$('#searchFrom').form('submit',{
	        success:function(data){
	        	var str = JSON.parse(data);
	           	$('#grid').datagrid('loadData',str);
	           	$('#grid').datagrid('clearChecked');
	       }
	    });
	};
	
	function formatterState(val,row){
		if(val == 1){
			return "申请中";
		}else if(val == 2){
			return "审核中";
		}else if(val == 3){
			return "已审批";
		}else if(val == 4){
			return "已归还";
		}else if(val == 5){
			return "已签收";
		}
	}
	//查看
	function lookupItem(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			parent.openNewTab('${basePath}elementLendController/editElementLend.action?pid='
					+ row[0].pid+"&status=2"+"&param='refId':'"+row[0].pid+"','projectName':'1'"+",'projectId':'"+row[0].value15+"'","要件借出查看",true);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能查看一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//归还
	function returnItem(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if(row[0].value14 >= 3){
				parent.openNewTab('${basePath}elementLendController/editElementLend.action?pid='
						+ row[0].pid+"&status=3"+"&param='projectId':'"+row[0].value15+"','projectName':''","要件归还",true);
				}else{
		 			$.messager.alert("操作提示","要件借出只有已审批后的要件才能进行归还！","error");
		 		}
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能操作一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//签收
	function signItem(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if(row[0].value14 == 4){
				parent.openNewTab('${basePath}elementLendController/editElementLend.action?pid='
						+ row[0].pid+"&status=4"+"&param='projectId':'"+row[0].value15+"','projectName':''","要件归还签收",true);
				}else{
		 			$.messager.alert("操作提示","要件借出只有已归还的要件才能进行签收！","error");
		 		}
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能操作一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//重置按钮
	function majaxReset(){
		$('#searchFrom').form('reset');
		$('#orgId').combobox('setValue',"-1");
		$('#productId').combobox('setValue',"-1");
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="list.action" method="post" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">项目名称：</td>
				<td>
					<input name="projectName" class="easyui-textbox"/></td>
				</td>
				<td width="80" align="right">产品名称：</td>
				<td ><input name="productId" id="productId" class="easyui-combobox" editable="false" panelHeight="auto"/></td>
				<td width="80" align="right">部门：</td>
				<td ><select id="orgId" class="easyui-combobox" name="orgId" editable="false" style="width:150px;" /></td>
				<td width="80" align="right">经办人：</td>
				<td ><input name="realName" class="easyui-textbox"/></td>
			</tr>
			
			<tr>
				<td width="80" align="right" >借出时间：</td>
				<td colsapn="3">
					<input name="lendTime" id="beginRequestDttm" class="easyui-datebox" editable="false"/> <span>~</span> 
					<input name="lendTimeEnd" id="endRequestDttm" class="easyui-datebox" editable="false"/>
				</td>
				<td width="120" align="right" >计划归还时间：</td>
				<td colsapn="3">
					<input name="actualReturnTime" id="actualReturnTime" class="easyui-datebox" editable="false"/> <span>~</span> 
					<input name="actualReturnTimeEnd" id="actualReturnTimeEnd" class="easyui-datebox" editable="false"/>
				</td>
			</tr>
			
			<tr>
				<td width="80" align="right" height="25">状态：</td>
				<td>
					<select class="select_style easyui-combobox" editable="false" name="lendState">
						<option value="-1">--请选择--</option>
						<option value="1" <c:if test="${elementLend.lendState==1 }">selected </c:if>>申请中</option>
						<option value="2" <c:if test="${elementLend.lendState==2 }">selected </c:if>>审核中</option>
						<option value="3" <c:if test="${elementLend.lendState==3 }">selected </c:if>>已审批</option>
						<option value="4" <c:if test="${elementLend.lendState==4 }">selected </c:if>>已归还</option>
						<%-- <option value="5" <c:if test="${elementLend.lendState==5 }">selected </c:if>>已签收</option> --%>
					</select>
				</td>
				<td width="120" align="right" height="28">物业/买卖双方：</td>
				<td colspan="2"><input name="condition" class="easyui-textbox" /></td>
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearch()">查询</a>
					<a href="#" onclick="majaxReset()" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">
		<shiro:hasAnyRoles name="ADD_UPDATE_ELEMENT,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="RETURN_ELEMENT,ALL_BUSINESS"> 
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-setting" plain="true" onclick="returnItem()">归还</a>
		 </shiro:hasAnyRoles>
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-setting" plain="true" onclick="signItem()">签收</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
	</div>

</div>
<div class="perListDiv" style="height:100%;">
<table id="grid" title="要件借出" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'list.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    singleSelect: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    width: '100%',
	 	height: '100%',  
	 	 striped: true, 
	 	 loadMsg: '数据加载中请稍后……',  
	    fitColumns:true" >
	<thead data-options="frozen:true">
		<tr>
		    <th data-options="field:'pid',checkbox:true"></th>
			<th data-options="field:'value15',hidden:true"></th>
			<th data-options="field:'value1',width:80,sortable:true" >项目名称</th>
		</tr>
	</thead>
	<!--
	 表头 -->
	<thead>
		<tr>
			<!-- <th data-options="field:'pid',checkbox:true"></th>
			<th data-options="field:'value15',hidden:true"></th>
			<th data-options="field:'value1',width:80,sortable:true" >项目名称</th> -->
			<th data-options="field:'value2',width:80,sortable:true">产品名称</th>
			<th data-options="field:'value4',width:80,sortable:true">部门</th>
			<th data-options="field:'value3',width:80,sortable:true">经办人</th>
			<th data-options="field:'value5',width:80,sortable:true">物业名称</th>
			<th data-options="field:'value6',width:80,sortable:true">买方</th>
			<th data-options="field:'value7',width:80,sortable:true">卖方</th>
			<th data-options="field:'value9',width:80,sortable:true">用途</th>
			<th data-options="field:'value10',width:80,sortable:true">借出日期</th>
			<th data-options="field:'value11',width:80,sortable:true">计划归还日期</th>
			<th data-options="field:'value16',width:80,sortable:true">实际归还日期</th>
			<th data-options="field:'value12',width:80,sortable:true">签收日期</th>
			<th data-options="field:'value13',width:80,sortable:true">签收人</th>
			<th data-options="field:'value14',formatter:formatterState,width:80,sortable:true">状态</th>
		</tr>
	</thead>
</table>
</div>
</div>
</body>
