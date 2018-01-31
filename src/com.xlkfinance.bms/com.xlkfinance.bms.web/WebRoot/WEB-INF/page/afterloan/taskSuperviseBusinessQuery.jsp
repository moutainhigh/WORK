<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贷后监管任务</title>

<script type="text/javascript">


/* function datiladvoperat(value, row, index) {
 
	var va="<a href='execuSuperyviseBusiness.action?pid="+row.pid;
	var re=	 "'><font color='blue'>";
	var fo= "</font></a>";
	var projectDom = "|<a href='viewResultSuperviseHitory.action?pid="+row.pid+"'><font color='blue'> 查看监管记录</font></a>";  
	var deva="<a href='deleteProjectbyId.action?pId=";
	var dere= "'><font color='blue'>";
	var defo= "</font></a>";
	
	return va+re+ "执行监管"+fo+ projectDom ;
} */


function datiladvoperat(value, row, index) {
	
	var va = "";
	if(row.pid>0)
	{
		 va +="<a href='#'   onclick='disposeClick2("+row.projectId+")'><font color='blue'>查看监管记录</font></a>";
		// 如果非完成状态
		if(row.regulatoryStatus<3)
		{
		  va +=" | <a href='#'   onclick='disposeClick1("+row.pid+")'><font color='blue'>执行监管</font></a>";
		}	
	}	
	
	return va; 
}
function disposeClick1(pid) {
	
	var url = "<%=basePath%>afterLoanController/execuSuperyviseBusiness.action?planId="+pid;
	parent.openNewTab(url,"执行监管",true);
} 
function disposeClick2(projectId) {
	
	url = "<%=basePath%>afterLoanController/viewResultSuperviseHitory.action?projectId="+projectId;
	parent.openNewTab(url,"查看监管记录",true);
} 
 

function reprojectbyid(pid){
	return "viewResultSuperviseHitory.action?pid="+pid
}
 



//查询 
function ajaxSearch(){
	$('#searchFrom').form('submit',{
        success:function(data){
           	$('#grid').datagrid('load', {
           		projectName: $("#projectName").val(),
        	    projectNumber: $("#projectNumber").val(),
        	    cusName: $("#cusName").val(),
        	    cusType: $("#cusType").combobox("getValue"),
        	    ecoTrade: $("#ecoTrade").combobox("getValue"),
        	    startRequestDttm:$("#startRequestDttm").datebox("getValue"),
        	    endRequestDttm:$("#endRequestDttm").datebox("getValue"),
           	    regulatoryStatus: $("#regulatoryStatus").combobox("getValue"),
           	   regulatoryUserName: $("#regulatoryUserName").val()
           	 
            });
       }
    });
};

function doclear(){
	$("#projectName").textbox('setValue','');
	$("#projectNumber").textbox('setValue','');
	$("#cusName").textbox('setValue','');
	$("#cusType").textbox('setValue','');
	$('#regulatoryStatus').combobox('select','0');
	$('#startRequestDttm').textbox('setValue','');
	$('#endRequestDttm').textbox('setValue','');
	 $("#regulatoryUserName").textbox('setValue','')
	
}


//跳转到 添加页面
function addItem(){
	   
	   $('<div id="editComAssure"/>').dialog({
			href : '${basePath}afterLoanController/editAfterloanRegulatoryPlan.action?comId=${comId}&acctId=${acctId}', 
			width : 890,
			height : 205,
			modal : true,
			title : '新增监管计划',
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					window.save();
				}	
				},{
					text : '取消 ',
					iconCls : 'icon-cancel',
					handler : function() {
						$("#editComAssure").dialog("close");
						}
					} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
			}
		});
	   
};

function majaxReset(){
	$('#searchFrom').form('reset');
}


function checkFormatter(value,row,index){
	if (
			(  row.regulatoryUser==''   || typeof(row.regulatoryUser)=='undefined')/*  || 
			(row.planBeginDt=='' ||    typeof(row.planBeginDt)=='undefined') || 
			(row.userName=='' ||   typeof(row.userName)=='undefined')  || 
			(row.actualBeginDt==''   || typeof(row.actualBeginDt)=='undefined') */
			
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


//项目名称format
var formatProjectName=function (value, rowData, index) {
	var btn = "<a id='"+rowData.projectId+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.projectId+",\""+rowData.projectNumber+"\")' href='#'>" +
		"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
	return btn;   
}
 
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
 	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
 	
 		<form action="doSearchSuperviseTast.action" method="post" id="searchFrom" name="searchFrom" >
		<!-- 查询条件 -->
		<div style="margin:5px">
		<table class="beforeloanTable" width="90%">
			<tr>
				<td class="label_right">项目名称：</td>
				<td><input class="easyui-textbox" name="projectName" style="width:220px" id="projectName" /></td>
				<td class="label_right">项目编号：</td>
				<td colspan="2">
					<input class="easyui-textbox" name="projectNumber" style="width:220px" id="projectNumber" />
				</td>
			</tr>

			<tr>
				<td class="label_right">客户名称：</td>
				<td><input class="easyui-textbox" name="cusName" style="width:220px" id="cusName" />
				<td class="label_right">客户类别： </td>
				<td>
					<select name="cusType" id="cusType" class="easyui-combobox"  style="width: 220px;" editable="false" panelHeight="auto"  
			data-options="valueField:'pid',textField:'lookupDesc',value:'-1',onChange:changeCusType,
				url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'"   >
				 
					</select>
				</td>
				<td width="290">
					<div class="tradeTypeDiv none" style="padding-left:10px">
					   经济行业类别：
						<input class="easyui-combobox" name="ecoTrade"
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
				<td class="label_right">计划监管时间：</td>
				<td>
					<input name="startRequestDttm" type="text" class="easyui-datebox" style="width:100px;"  id="startRequestDttm" value="" /> <span>~</span> 
					<input name="endRequestDttm" type="text" class="easyui-datebox" id="endRequestDttm" value="" style="width:100px;"  />
				</td>		
				<td class="label_right">计划监管人：</td>
				<td colspan="2">
					<input name="regulatoryUserName" type="text"   class="easyui-textbox" style="width:100px;"  id="regulatoryUserName" value="" />
				</td>
			</tr>
            <tr>
               	<td class="label_right">监管状态：</td>
				<td>
				    <select name="regulatoryStatus" id="regulatoryStatus" class="easyui-combobox"  style="width:150px;" >
					 		  <option selected value="0">全部</option>
					          <option value="1">等待监管</option>
					          <option value="2">监管中</option>
					          <option value="3">监管完成</option>
					</select>	
				</td>
				
				<td colspan="3">
					&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearch()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="majaxReset()">重置</a>
				</td>
			</tr>
			
		</table>
		</div>
	</form>


 
 
	
 
 
  <!-- 操作按钮 -->
	</div>
  
  <div class="comBaseInfoDiv" style="height:100%;">
	<table id="grid" title="监管任务查询" class="easyui-datagrid" 
	style="height:100%;width: 99%;"
	data-options="
	    url: 'doSearchSuperviseTast.action',
	    method: 'POST',
	    rownumbers: true,
	    singleSelect: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
		selectOnCheck: true,
		checkOnSelect: true,
		sortOrder:'asc'
	    ">	    
		<thead><tr>
			<th data-options="field:'projectId',hidden:true" ></th>
			<th data-options="field:'pid',checkbox:true,width:100" ></th>
		    <th data-options="field:'projectName',width:100,sortable:true,formatter:formatProjectName" align="center">项目名称</th>
		    <th data-options="field:'projectNumber',width:100" align="center">项目编号</th>
		    <th data-options="field:'cusTypeStr',width:100" align="center">客户类型</th>
		    <th data-options="field:'startRequestDttm',width:100,formatter:convertDate" align="center">贷款申请时间</th>
		    <th data-options="field:'regulatoryUserName',width:100,sortable:true" align="center">计划监管人</th>
		    <th data-options="field:'planDt',width:100,formatter:convertDate" align="center">计划监管时间</th>
		    <th data-options="field:'regulatoryStatusStr',width:100" align="center">监管状态</th>
		    <th data-options="field:'assigneUserName',width:100,sortable:true" align="center">监管执行人</th>
		    <th data-options="field:'planBeginDt',width:100,sortable:true,formatter:convertDate" align="center">监管时间</th>
		    <th data-options="field:'yy',width:150,formatter:datiladvoperat">操作</th>
		</tr></thead>
	</table></div>
</div>
</body>
</html>