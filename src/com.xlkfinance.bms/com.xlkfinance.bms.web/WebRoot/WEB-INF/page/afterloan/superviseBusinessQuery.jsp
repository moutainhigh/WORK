<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贷后监管业务查询</title>

<script type="text/javascript">
 

function datiladvoperat(value, row, index) {
	var url="<%=basePath%>afterLoanController/editAfterloanRegulatoryPlan.action?projectId="+row.pid;
	var va="<a href='#'   onclick='disposeClick1("+JSON.stringify(row)+")' ";
	var re=	 "'><font color='blue'>";
	var fo= "</font></a>";
	return va+re+ "新增/编辑监管计划"+fo;
}
 
function disposeClick1(row) {
	url = "<%=basePath%>afterLoanController/editAfterloanRegulatoryPlan.action?projectId="+row.pid;
	parent.openNewTab(url,"编辑监管计划",true);
} 

function trimStr(str){return str.replace(/(^\s*)|(\s*$)/g,"");}

//查询 
function ajaxSearch(){
	$('#searchFrom').form('submit',{
        success:function(data){
           	$('#grid').datagrid('load', {
           		projectName: $("#projectName").val(),
        	    projectNumber: $("#projectNumber").val(),
        	    cusName: $("#cusName").val(),
        	    cusType: $("#cusType").combobox("getValue"),
        	    startRequestDttm:$("#startRequestDttm").datebox("getValue"),
        	    endRequestDttm:$("#endRequestDttm").datebox("getValue"),	     
        	    ecoTrade: $("#ecoTrade").val(),
        	    startDttm:$("#startDttm").datebox("getValue"),
        	    endDttm:$("#endDttm").datebox("getValue"),
        	    startApplyDttm:$("#startApplyDttm").datebox("getValue"),
        	    endApplyDttm:$("#endApplyDttm").datebox("getValue"),
        	    supervisionPeople: $("#supervisionPeople").val(),
        	    startSupervisionDttm: $("#endApplyDttm").datebox("getValue"),
        	    endSupervisionDttm: $("#endSupervisionDttm").datebox("getValue"),
        	    distributionStatus: $("#distributionStatus").combobox("getValue")
            });
       }
    });
};


 
function advApplyRepay(value, row, index){
	var url="<%=basePath%>beforeLoanController/addNavigation.action?projectId="+row.pid;
	var str="<a href='#' onclick='disposeClick("+JSON.stringify(row)+")' ><font color=blue>"+row.value1+"</font></a>" ;
    return str;   
}
function disposeClick(row) {
	url = "<%=basePath%>beforeLoanController/addNavigation.action?projectId="+row.pid;
	parent.openNewTab(url,"项目详情",true);
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

function editItem(pid){
	//var row = $('#grid').datagrid('getSelections');
	
	//alert(row[0].pid)
	
	  /*  $('<div id="editComAssure"/>').dialog({
			href : '${basePath}afterLoanController/editAfterloanRegulatoryPlan.action?projectId='+pid, 
			width : 890,
			height : 305,
			modal : true,
			title : '新增监管计划',
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					window.update();
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
		}); */
	   
};

 

/* function removeItem(){
	var row = $('#grid').datagrid('getSelections');
	alert(row[0].pid)
    confirm_ = confirm('This action will delete current order! Are you sure?');
    if(confirm_){
        $.ajax({
            type:"POST",
            url:'${basePath}afterLoanController/deleteSupervisePlan.action?pid='+row[0].pid, 
            success:function(msg){
                 alert(msg);
                //all delete is success,this can be execute
               // $("#tr_"+order_id).remove();
            }
        });
    }
} */



function removeItem() {
	var rows = $('#grid').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert("操作提示", "请选择数据", "error");
		return;
	}// 获取选中的系统用户名 
	var pids = "";
	for (var i = 0; i < rows.length; i++) {
		if (i == 0) {
			pids += rows[i].pid;
		} else {
			pids += "," + rows[i].pid;
		}
	}
	$.messager
			.confirm(
					"操作提示",
					"确定删除选择的新增监管计划信息吗?",
					function(r) {
						if (r) {
							$
									.post(
											"deleteSupervisePlan.action",
											{
												pids : pids
											},
											function(ret) {
												window.location.href = "${basePath}afterLoanController/listSuperviseBusiness.action";
											}, 'json');
						}
					});
}



function majaxReset(){

	$(".tradeTypeDiv").hide();
	$('#searchFrom').form('reset')
}


function checkDateFormatter(value,row,index){
	if (row.value7==''||row.value8==''||row.value9==''){
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
	var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.pid+",\""+rowData.value2+"\")' href='#'>" +
		"<span style='color:blue; '>"+rowData.value1+"</span></a>";  
	return btn;   
}
$(document).ready(function() {
	$("#ecoTradeId1").hide();
	$("#ecoTradeId2").hide();
	$('#acctType').combobox({
		onSelect : function(row) {
			var opts = $(this).combobox('options');
			if (row[opts.textField] == '企业') {
				$("#ecoTradeId1").show();
				$("#ecoTradeId2").show();
			} else {
				$("#ecoTradeId1").hide();
				$("#ecoTradeId2").hide();
			}

		}
	});
})


</script>
</head>
<body>
	<div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
 	
 	    
 		<form action="superviseBusinessList.action" method="post" id="searchFrom" name="searchFrom" >
		<!-- 查询条件 -->
		<div style="margin:5px">
		<table class="beforeloanTable" width="90%">
			<tr>
				<td class="label_right">项目名称：</td>
				<td><input class="easyui-textbox" name="projectName" style="width:220px" id="projectName" /></td>
				<td class="label_right">项目编号：</td>
				<td colspan="3">
					<input class="easyui-textbox" name="projectNumber"  style="width:220px" id="projectNumber" />
				</td>
			</tr>

			<tr>
				<td class="label_right">客户名称：</td>
				<td><input class="easyui-textbox" name="cusName"  style="width:220px" id="cusName" />
				<td class="label_right">客户类别：</td>
					<td><input name="acctType" id="acctType"
						class="easyui-combobox" style="width: 220px;" editable="false"
						panelHeight="auto"
						data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" /></td>
					<td class="label_right" id="ecoTradeId1">经济行业类别：</td>
					<td id="ecoTradeId2"><input name="ecoTrade"
						class="easyui-combobox" editable="false" panelHeight="auto"
						data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /></td>
			</tr>

			<tr>
				<td class="label_right">到期时间：</td>
				<td>
					<input name="startRequestDttm" type="text" class="easyui-datebox" editable="false"  style="width:100px;"  id="startRequestDttm" value="" /> <span>~</span> 
					<input name="endRequestDttm" type="text" class="easyui-datebox"  editable="false" id="endRequestDttm" value="" style="width:100px;"  />
				</td>
				<td class="label_right">已逾期间（日）：</td>
				<td colspan="3">
					<input name="startDttm" type="text" class="easyui-textbox" style="width:100px;"  id="startDttm" value="" />
					<span>~</span> <input name="endDttm" class="easyui-textbox" type="text" id="endDttm" value="" style="width:100px;"  />
				</td>
			</tr>


           <tr>
				
				<td class="label_right">贷款申请时间：</td>
				<td>
					<input name="startApplyDttm" type="text" editable="false" class="easyui-datebox" style="width:100px;"  id="startApplyDttm" value="" />
					<span>~</span> <input name="endApplyDttm" editable="false" type="text" class="easyui-datebox" id="endApplyDttm" value="" style="width:100px;"  />
				</td>
			<!-- 	<td class="label_right">计划监管人：</td>
				<td colspan="3">
					<input name="supervisionPeople" type="text"   class="easyui-textbox" style="width:100px;"  id="supervisionPeople" value="" />
				</td> -->
					<td colspan="2" width="290">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="majaxReset()">重置</a>
				</td>
			</tr>
 

            <tr>
			<!-- 	
				<td class="label_right">计划监管时间：</td>
				<td>
					<input name="startSupervisionDttm" type="text" class="easyui-datebox" style="width:100px;"  id="startSupervisionDttm" value="" />
					<span>~</span> <input name="endSupervisionDttm" type="text" class="easyui-datebox" id="endSupervisionDttm" value="" style="width:100px;"  />
				</td>
		
				<td class="label_right">监管分配状态：</td>
				<td>
					<select name="distributionStatus" id="distributionStatus" style="width:100px;" class="easyui-combobox">
						<option value="-1">==全部==</option>
						<option value="1">已分配</option>
						<option value="2">未分配</option>
					</select>
				</td> -->
			
			</tr>
			
		</table>
		</div>
	</form>
</div>
<div class="comBaseInfoDiv" style="height:100%;height:600px;">
<table id="grid" title="监管业务查询" class="easyui-datagrid" 
	style="height:100%;width: 99%;"
	data-options="
	    url: 'superviseBusinessList.action',
	    method: 'POST',
	    rownumbers: true,
	    singleSelect: true,
	    pagination: true,
	    sortOrder:'asc',
	    toolbar: '#toolbar',
	    idField: 'pid',
		selectOnCheck: true,
		checkOnSelect: true
	    ">
		<thead><tr>
			<th data-options="field:'pid',checkbox:true,width:100" ></th>
			<th data-options="field:'value1',width:200,formatter:formatProjectName" align="center">项目名称</th>
		    <th data-options="field:'value2',width:100,sortable:true" align="center">项目编号</th>
		    <th data-options="field:'value3',width:100,sortable:true" align="center">客户类型</th>
		    <th data-options="field:'value4',width:100,sortable:true,formatter:convertDate" align="center">贷款申请时间</th>
		    <th data-options="field:'value5',width:100,sortable:true" align="center">联系电话</th>
		    <th data-options="field:'value6',width:100,sortable:true" align="center">贷款金额</th>
		    <th data-options="field:'value9',width:100,sortable:true,formatter:formatMoney" align="center">到期未收金额</th>
		<!--     <th data-options="field:'value7',width:100,sortable:true,formatter:checkDateFormatter" align="center">计划监管人</th>
		    <th data-options="field:'value8',width:100,sortable:true,formatter:checkDateFormatter" align="center">计划监管时间</th>
		    <th data-options="field:'value9',width:100,sortable:true,formatter:checkDateFormatter" align="center">监管计划备注</th> -->
		    <th data-options="field:'yy',width:150,formatter:datiladvoperat">操作</th>
		   <!--  <th data-options="field:'projectName',width:100,formatter:advApplyRepay" align="center">项目名称</th>
		    <th data-options="field:'projectNumber',width:100,sortable:true" align="center">项目编号</th>
		    <th data-options="field:'custypestr',width:100" align="center">客户类型</th>
		    <th data-options="field:'startRequestDttm',width:100" align="center">贷款申请时间</th>
		    <th data-options="field:'phone',width:100" align="center">联系电话</th>
		    <th data-options="field:'creditAmt',width:100" align="center">贷款金额</th>
		    <th data-options="field:'inMoneyAndOutDttm',width:100" align="center">到期未收金额</th>
		    <th data-options="field:'regulatoryuser',width:100" align="center">计划监管人</th>
		    <th data-options="field:'planBeginDt',width:100" align="center">计划监管时间</th>
		    <th data-options="field:'remark',width:100" align="center">监管计划备注</th>
		    <th data-options="field:'yy',width:150,formatter:datiladvoperat">操作</th> -->
		</tr></thead>
	</table>
</div> 
</div>
</div>
</body>
</html>