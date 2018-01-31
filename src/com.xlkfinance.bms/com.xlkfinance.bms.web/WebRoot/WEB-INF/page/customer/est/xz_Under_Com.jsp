<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ include file="/config.jsp"%>

<style type="text/css">

</style>

<script type="text/javascript">

function saveComCus(){
	var row = $('#enterprise_grid').datagrid('getSelections');
 	if (row.length == 0) {
 		$.messager.alert("操作提示","请选择数据","error");
 		return;
	}
 	var pid = "";
	for(var i=0;i<row.length;i++){
  		if(i==0){
  			pid+=row[i].pid;
  		}else{
  			pid+=","+row[i].pid;
  		}
  	}
	var acctId='${acctId}';
 	$.messager.confirm("操作提示","确定添加选择的企业客户吗?",
		function(r) {
 			if(r){
				$.post("inUnderAddCusCom.action",{pid : pid,acctId : acctId}, 
					function(ret) {
						//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
						    $("#grid").datagrid("clearChecked");
							$("#grid").datagrid('reload');
							$("#xzComCus").dialog("close");
							//$("#dlg").dialog('close');
					},'json');
 			}
		});
}
$(function (){
	ajaxSearch('#enterprise_grid','#searchFromEnterprise');
})
</script>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form method="post" action="getEnterpriseList.action"  id="searchFromEnterprise" name="searchFromEnterprise"  style="padding: 0px 0px;">
	     		<div style="padding:5px">
	     		<table>
	     			<tr>
	     				<td align="right" width="80">客户名称：</td>
	     				<td><input name="cpyName" class="easyui-textbox" /></td>
	     				<td align="right" width="100">经济行业类别：</td>
	     				<td><input name="ecoTrade" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /></td>
	     				<td>
	     					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#enterprise_grid','#searchFromEnterprise')">查询</a>
							<a href="#" onclick="javascript:$('#searchFromEnterprise').form('reset');" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
	     				</td>
	     			</tr>
	     		</table>
			        <input name="page" id="page" type="hidden"  />
				 	<input name="rows" id="rows" type="hidden"  />
			 							
        		</div> 
		 	</form>
	

</div>

<table id="enterprise_grid"  class="easyui-datagrid"  
	    	style="height: 400px;"
		 	data-options="
		    url: 'getEnterpriseList.action',
		    method: 'post',
		    rownumbers: true,
		    singleSelect: false,
	    	pagination: true,
		    toolbar: '#toolbar_enterprise',
		    fitColumns:true,
		    idField: 'pid'
		    ">
			<thead>
				<tr>
					<th data-options="field:'pid',checkbox:true" ></th>
					<th data-options="field:'cpyName'" >企业名称</th>
					<th data-options="field:'cpyAbbrName'" >企业简介</th>
					<th data-options="field:'busLicCert'" >组织机构代码</th>
					<th data-options="field:'orgCode'" >营业执照号</th>
					<th data-options="field:'orgCode'" >所有制性质</th>
					<th data-options="field:'chinaName'" >法人</th>
					<th data-options="field:'cusRegMoney'" >注册资金（万元）</th>
					<th data-options="field:'cusTelephone'" >联系电话</th>
					<th data-options="field:'cusFoundDate'" >企业成立日期</th>
					<th data-options="field:'cusStatus'" >企业状态</th>	
					<th data-options="field:'realName'" >客户经理</th>	
				</tr>
			</thead>
	   	</table>
