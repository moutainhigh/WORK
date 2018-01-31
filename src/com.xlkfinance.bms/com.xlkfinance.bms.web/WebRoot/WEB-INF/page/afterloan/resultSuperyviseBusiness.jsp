<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监管结果查询</title>

<script type="text/javascript">


function datiladvoperat(value, row, index) {
	var va="<a href='#'   onclick='disposeClick("+row.regulatoryPlanId+")' ><font color='blue'>查看监管詳情</font></a>";
	return va;
}

function disposeClick(planId){
	var url="<%=basePath%>afterLoanController/execuSuperyviseBusiness.action?readOnly=true&planId="+planId;
	parent.openNewTab(url,"查看监管詳情",true);
}



//查询 
/* function ajaxSearch(){
	$('#searchFrom').form('submit',{
        success:function(data){
           	$('#grid').datagrid('loadData',data);
       }
    });
}; */
function ajaxSearch(){
	$('#searchFrom').form('submit',{
        success:function(data){
           	$('#grid').datagrid('load',{
           		projectName: $("#projectName").val(),
        	    projectNumber: $("#projectNumber").val(),
        	    cusName: $("#cusName").val(),
        	    cusType: $("#cusType").combobox("getValue"),
        	    userName: $("#userName").val(),
        	    startRequestDttm:$("#startRequestDttm").datebox("getValue"),
        	    endRequestDttm:$("#endRequestDttm").datebox("getValue"),
        	    regulatoryResult: $("#regulatoryResult").combobox("getValue")
            });
       }
    });
};


function advApplyRepay(value, rowData, index){
	
	var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.projectId+",\""+rowData.projectNumber+"\")' href='#'>" +
	"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
return btn;   
} 

function showProject(row) {
	url = "<%=basePath%>beforeLoanController/addNavigation.action?projectId="+row.projectId;
	parent.openNewTab(url,"贷款申请详情",true);
	}

function doclear(){
	$("#projectName").textbox('setValue','');
	$("#projectNumber").textbox('setValue','');
	$("#cusName").textbox('setValue','');
	$("#cusType").textbox('setValue','');
	$("#reviewStatus").textbox('setValue','');
	$('#ecoTrade').combobox('select','-1');
	$('#startRequestDttm').textbox('setValue','');
	$('#endRequestDttm').textbox('setValue','');
}

//跳转到 违约页面
function addItem(v){
	
	 
	var url;
	var rows = $('#grid').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert("操作提示", "请选择数据", "error");
		return;
	} 
	
	   //根据项目ID判断是否有流程在执行
	if(isWorkflowByStatus(rows[0].projectId)){
		return;
	}
	// 挪用  
   if(v==1){
		url = "${basePath}afterLoanDivertController/afterLoanDivert.action?newFlow=true&regHistoryId="+rows[0].resultId+"&projectId="+ rows[0].projectId;
     	parent.openNewTab(url, "挪用处理申请", true);
   }
  // 违约
   if(v==2){
	   url="${basePath}afterLoanController/listBreachDispose.action?newFlow=true&regHistoryId="+rows[0].resultId+"&projectId="+rows[0].projectId;
	   parent.openNewTab(url,"申请违约处理",true);
   }
   // 坏账
   if(v==3){
	   url="${basePath}afterLoanController/newBadDebt.action?newFlow=true&regHistoryId="+rows[0].resultId+"&projectId="+rows[0].projectId;
	   parent.openNewTab(url,"申请坏账处理",true);
   }
};


function majaxReset(){
	$("#ecoTradeId1").hide();
	$("#ecoTradeId2").hide();
	$('#searchFrom').form('reset')
}


function checkFormatter(value,row,index){
	if ( 
			(row.regulatoryStatusStr=='' || typeof(row.regulatoryStatusStr)=='undefined') || 
			(row.userName=='' ||  typeof(row.userName)=='undefined') || 
			(row.actualBeginDt=='' || typeof(row.actualBeginDt)=='undefined')  || 
			(row.regulatoryResultStr=='' || typeof(row.regulatoryResultStr)=='undefined')
			
	){
		return '-';
	} else {
		return value;
	}
}


function changeCusType(newValue, oldValue){
	if(newValue==2){
		$(".tradeTypeDiv").show();
	}else{
		$(".tradeTypeDiv").hide();
	}
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

</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
 	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
 	
 		<form action="<%=basePath%>afterLoanController/getResultSuperyviseBusiness.action" method="post" id="searchFrom" name="searchFrom" >
		<!-- 查询条件 -->
		<div style="margin:5px">
		<table class="beforeloanTable">
			<tr>
				<td class="label_right">项目名称：</td>
				<td><input class="easyui-textbox" name="projectName" id="projectName" /></td>
				<td class="label_right">项目编号：</td>
				<td>
					<input class="easyui-textbox" name="projectNumber" id="projectNumber" />
				</td>
				<td class="label_right">客户名称：</td>
				<td><input class="easyui-textbox" name="cusName" id="cusName" style="width:220px" />
			</tr>

			<tr>
				
				<td class="label_right">客户类别： </td>
				<td>
					<select name="cusType" id="cusType" class="easyui-combobox" style="width: 220px;" editable="false" panelHeight="auto"  
			data-options="valueField:'pid',textField:'lookupDesc',value:'-1',onChange:changeCusType,
				url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'">
					 
					</select>
				</td>
			
				<td class="label_right">监管人：</td>
				<td>
					<input name="userName" type="text"   class="easyui-textbox" id="userName" value="" />
				</td>
				<td class="label_right">监管时间：</td>
				<td>
					<input name="startRequestDttm" type="text" class="easyui-datebox" style="width:100px;"  id="startRequestDttm" value="" /> <span>~</span> 
					<input name="endRequestDttm" type="text" class="easyui-datebox" id="endRequestDttm" value="" style="width:100px;"  />
				</td>
			</tr>

		    <tr>
				<td class="label_right">监管结果：</td>
				<td>
				    <select name="regulatoryResult" id="regulatoryResult" class="easyui-combobox"  style="width:150px;" >
					 		  <option selected value="-1">全部</option>
					          <option value="1">正常</option>
					          <option value="2">挪用嫌疑</option>
					          <option value="3">违约嫌疑</option>
					          <option value="4">存在挪用行为</option>
					          <option value="5">存在违约行为</option>
					</select>	
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
				<td colspan="3">&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearch()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="majaxReset()">重置</a>
				</td>
			</tr>
			
		</table>
		</div>
	</form>
 
 	<div style="padding-bottom:5px">
 	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem(1)">挪用处理</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem(2)">违约处理</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem(3)">坏账处理</a>
 
	</div>
 
  <!-- 操作按钮 -->
	</div>
  
   <div class="comBaseInfoDiv" style="height:100%;">
	<table id="grid" title="监管结果查询" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: '<%=basePath%>afterLoanController/getResultSuperyviseBusiness.action',
	    method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: true,
		selectOnCheck: true,
		checkOnSelect: true
	    ">
	  
	 
		<thead><tr>
		    <th data-options="field:'projectId',hidden:true" ></th>
			<th data-options="field:'resultId',checkbox:true,width:100" ></th>
		    <th data-options="field:'projectName',width:100,formatter:advApplyRepay" align="center">项目名称</th>
		    <th data-options="field:'projectNumber',width:100" align="center">项目编号</th>
		    <th data-options="field:'custType',width:100" align="center">客户类型</th>
		    <th data-options="field:'regulatoryStatusStr',width:100,sortable:true" align="center">监管状态</th>
		    <th data-options="field:'actualUser',width:100,sortable:true" align="center">监管执行人</th>
		    <th data-options="field:'actualDate',width:100,sortable:true,formatter:convertDate" align="center">监管时间</th>
		    <th data-options="field:'regulatoryResultStr',width:100,sortable:true" align="center">监管结果</th>
		    <th data-options="field:'yy',width:150,formatter:datiladvoperat">操作</th>
		</tr></thead>
	</table>
</div>
</div>
</body>
</html>