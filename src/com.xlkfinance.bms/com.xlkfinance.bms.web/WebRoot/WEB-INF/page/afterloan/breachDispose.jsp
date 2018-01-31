<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">

</style>

<script type="text/javascript">

function openBank(url){
	 parent.openNewTab(url,"编辑违约处理",true);
}
// //查看控制
function lookfor(){
	var tab = parent.$('#layout_center_tabs').tabs('getSelected');
	var atTitle = tab.panel('options').title;
	if(atTitle=="违约处理查看"){
		$('.delRement font').attr('color','gray');
		$('.delRement').removeAttr('onclick');
	 
	}
}
 
function datiladvoperat(value, row, index) {
	//debugger;
	var ss = row.reviewStatus;
	var projectDom = " | <a href='javascript:void(0)' class='delRement' onclick='deldivertpor("+JSON.stringify(row)+")'><font color='blue'> 删除  </font></a>";
//  编辑或者查看
	var viewName = "编辑";
	// 如果流程已经在走
	if(row.reviewStatus !="申请中")
	{
		 viewName ="查看";
		projectDom = "";
	}	
	return "<a href='javascript:void(0)' onclick='divertinfo("+JSON.stringify(row)+")'><font color='blue'>"+viewName+" </font></a>"+  projectDom;
}

/**
 * 删除
 */
function deldivertpor(row){
	url = "${basePath}afterLoanController/deleteBreachDispose.action?pid="+row.violationId+"&projectId="+row.pid;
	//查询是否有抵押
	if(confirm("是否删除此条数据")){
		$.ajax({
			  url: url,
			 //async: false,
			  success: function(ret){ 
				  if(ret>0){
					  $.messager.alert("提示","删除成功！","info");
					  $('#grid').datagrid("reload");
				  }
			  }
		});
	}
}
// function divertinfo(row){
// 		url = "${basePath}afterLoanController/listBreachDispose.action?projectId="+row.pid+"&violationId="+row.violationId;
// 		parent.openNewTab(url,"违约处理编辑",true);
	 
// }
// function divertinfos(row){
// 	url = "${basePath}afterLoanController/listBreachDispose.action?projectId="+row.pid+"&violationId="+row.violationId;
// 	parent.openNewTab(url,"违约处理查看",true);
	 
// }
function divertinfo(row){
	if(row.divertId>0){
		url = "${basePath}afterLoanDivertController/afterLoanDivertbyprocess.action?divertId="+row.divertId;
		parent.openNewTab(url,"违约处理查看",true);
	}
	else{
		url = "${basePath}afterLoanController/listBreachDispose.action?projectId="+row.pid+"&violationId="+row.violationId;
		parent.openNewTab(url,"违约处理编辑",true);
	}
}

/**
 * 添加连接
 */
function createFrame(url)
{
    var s = '<iframe id="my_Agent_Task_grid_iframe" name="my_Agent_Task_grid_iframe" src="'+ url+'" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>';
    return s;
}

$(function(){
	setCombobox("acct_type","CUS_TYPE","");
	setCombobox("eco_trade","ECO_TRADE","");
});	  
function advApplyRepay(value, rowData, index){
	var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.pid+",\""+rowData.projectNumber+"\")' href='#'>" +
	"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
return btn;   
}

function disposeClick(row) {
url = "<%=basePath%>beforeLoanController/addNavigation.action?projectId="+row.pid;
parent.openNewTab(url,"贷款申请详情",true);
}
	
	
//判断1
function isTermin1(value, row, index){
	if("是"==row.isTermination) {
		return "-";
	}else {
		return row.violationAmt
	}
};

//判断2
function isTermin2(value, row, index){
	if("是"==row.isTermination) {
		return "-";
	}else{
		return row.violationDt
	}
};

function majaxReset(){
	$(".ecoTradeDiv").hide();
	$('#searchFrom').form('reset')
}

//查询 
function ajaxSearch(){
	$('#grid').datagrid('load',{
		projectName: $("#projectName").textbox("getValue"),
		projectNumber: $("#projectNumber").textbox("getValue"),
		cusName:$("#cusName").textbox("getValue"),
		cusType: $("#cusType").combobox('getValue'),
		startRequestDttm: $("#startRequestDttm").datebox('getValue'),
		endRequestDttm:$("#endRequestDttm").datebox('getValue'),
		ecoTrade: $("#ecoTrade").combobox('getValue'),
		reviewStatus: $("#reviewStatus").combobox('getValue')
	});
	$('#grid').datagrid('clearChecked');
}
$(function(){
	//为客户类别绑定事件
	$("#cusType").combobox({
		onChange:function(newValue,oldValue){
			if(newValue==2){
				$(".ecoTradeDiv").show();
			}else{
				$(".ecoTradeDiv").hide();
				$("#ecoTrade").combobox('setValue',"");
			}
		}
	});
	//默认隐藏经济行业类别
	$(".ecoTradeDiv").hide();
});
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="getBreachDispose.action" method="post" id="searchFrom" name="searchFrom"  >
		<!-- 查询条件 -->
		<div style="margin:5px">
		<table class="beforeloanTable" width="90%">
			<tr>
				<td class="label_right">项目名称：</td>
				<td><input class="easyui-textbox" style="width: 235px;" name="projectName" id="projectName" /></td>
				<td class="label_right">项目编号：</td>
				<td>
					<input class="easyui-textbox" style="width: 235px;" name="projectNumber" id="projectNumber" />
				</td>
			</tr>

			<tr>
				<td class="label_right">客户名称：</td>
				<td><input class="easyui-textbox" style="width: 235px;" name="cusName" id="cusName" />
				<td class="label_right">客户类别： </td>
				<td>
					<select name="cusType" id="cusType"  class="easyui-combobox" editable="false" style="width: 235px;"" >
					<option value="">--请选择--</option>
					<option value="1">个人 </option>
					<option value="2">企业 </option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td class="label_right">申请时间：</td>
				<td>
					<input name="startRequestDttm" type="text" class="easyui-datebox" editable="false" style="width:100px;"  id="startRequestDttm" value="" />&nbsp;~&nbsp;
					<input name="endRequestDttm" type="text" class="easyui-datebox"  editable="false" id="endRequestDttm" value="" style="width:100px;"  />
				</td>

				<td class="label_right"><div class="ecoTradeDiv">经济行业类别：</div></td>
				<td>
					<div class="ecoTradeDiv">
					<input class="easyui-combobox" style="width: 235px;" name="ecoTrade"
						id="ecoTrade" editable="false"
						data-options="
		            valueField:'pid',
		            textField:'lookupVal',
		            method:'get',
		            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
		            </div>
				</td>
			</tr>

		   <tr>
				<td class="label_right">处理状态：</td>
				<td>
					<select name="reviewStatus" id="reviewStatus" editable="false" class="easyui-combobox" style="width: 235px;">
						<option value="">全部</option>
						<option value="3">未处理</option>
						<option value="1">申请中</option>
						<option value="2">审核中</option>
						<option value="4">处理结束</option>
					</select>
				</td>

				<td colspan="2">
					&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="majaxReset();">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<!-- 操作按钮 -->
	</div>
	<div style="height:100%">
	<table id="grid" title="违约处理列表" class="easyui-datagrid" 
	style="height:100%;width:100%;"
	data-options="
	    url: 'getBreachDispose.action',
	    method: 'POST',
	    rownumbers: true,
	    singleSelect: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true,
		onLoadSuccess:lookfor
	    ">
	<!-- 表头 -->
		<thead><tr>
		    <th data-options="field:'violationId',hidden:true" ></th>
		    <th data-options="field:'projectName',width:100,formatter:advApplyRepay" align="center">项目名称</th>
		    <th data-options="field:'projectNumber',width:100" align="center">项目编号</th>
		    <th data-options="field:'cusType',width:100" align="center">客户类型</th>
		    <th data-options="field:'isTermination',width:100" align="center">项目是否终止</th>
		    <th data-options="field:'isBacklist',width:100" align="center">是否加入黑名单</th>
		    <th data-options="field:'violationAmt',width:100,formatter:isTermin1" align="center">应收违约金</th>
		    <th data-options="field:'violationDt',width:100,formatter:isTermin2" align="center">违约金应收日期</th>
		    <th data-options="field:'reviewStatus',width:100" align="center">处理状态</th>
		    <th data-options="field:'requestDttm',width:100" align="center">申请时间</th>
		    <th data-options="field:'yy',width:150,formatter:datiladvoperat">操作</th>
		</tr></thead>
	</table>
	</div>
	</div>
</body>