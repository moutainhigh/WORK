<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<style type="text/css">

</style>

<script type="text/javascript">

function savePerCus(){
	var row = $('#personal_grid').datagrid('getSelections');
 	if (row.length == 1) {
 		window.$("#cus_id").val(row[0].acctId);
 		window.$("#cus_name").html(row[0].chinaName);
 		window.$("#xzPerCus").dialog("close");
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

$(function(){
	setCombobox2("cert_type","CERT_TYPE");
	setCombobox2("sex","SEX");
});
</script>

<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form method="post" action="getPersonalList.action"  id="searchFromPersonal" name="searchFromPersonal"  style="padding: 0px 0px;">
    		<div style="margin:5px">
	        <input name="page" id="page" type="hidden"  />
		 	<input name="rows" id="rows" type="hidden"  />
	 		姓名：<input name="chinaName" class="easyui-textbox" />
			证件类型：<input name="certType" id="cert_type" class="easyui-combobox" panelHeight="auto" editable="false"   />
			<p />
			性别：<input name="sex" id="sex" class="easyui-combobox" panelHeight="auto" editable="false"   />
	 		证件号码：<input name="certNumber" class="easyui-textbox" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"  onclick="ajaxSearch('#personal_grid','#searchFromPersonal')">查询</a>
			<a href="#" onclick="javascript:$('#searchFromPersonal').form('reset');" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
      		<p />
      		</div> 
 	</form>
	
</div>


   <table id="personal_grid"  class="easyui-datagrid" 
	style="height:450px;width: auto;"
	data-options="
	    url: 'getPersonalList.action',
	    method: 'post',
	    rownumbers: true,
	    singleSelect: true,
	   	pagination: true,
	    toolbar: '#toolbar_personal',
	    fitColumns:true,
	    idField: 'acctId'
	    ">
	<thead>
		<tr>
			<th data-options="field:'acctId',checkbox:true" ></th>
			<th data-options="field:'chinaName',width:200" >姓名</th>
			<th data-options="field:'sexName',width:60" >性别</th>
			<th data-options="field:'certTypeName',width:100" >证件类型</th>
			<th data-options="field:'certNumber',width:100" >证件号号码</th>
			<th data-options="field:'workUnit',width:130" >工作单位</th>
			<th data-options="field:'cusStatusVal',width:100" >客户状态</th>
			<th data-options="field:'realName',width:100" >客户经理</th>
		</tr>
	</thead>
  	</table>
 
