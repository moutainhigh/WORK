<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript">
//新增
function addWorkDay(){
	$('#workDayInfo').form('reset');
	$("#pid").next().hide();//隐藏pid
	$('#add_work_info').dialog('open').dialog('setTitle', "新增国家法定节假日信息");
 }
// 编辑
function editWorkDay() {
	var row = $('#grid').datagrid('getSelections');
	if (row.length == 1) {
		var pid = row[0].pid;
		var correctDate = row[0].correctDate;//法定节假日
		var remark = row[0].remark;//备注
 		$("#correctDate").datebox('setValue', correctDate);
 		$("#pid").textbox('setValue', pid);
		$("#remark").textbox('setValue', remark);
		$("#pid").next().hide();//隐藏pid
		$('#add_work_info').dialog('open').dialog('setTitle', "修改国家法定节假日信息");
	} else if (row.length > 1) {
		$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
	} else {
		$.messager.alert("操作提示", "请选择数据", "error");
	}
}

//删除
function removeWorkDay(){
	var row = $('#grid').datagrid('getSelections');
	if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else if(row.length ==0){
		$.messager.alert("操作提示","请选择数据","error");
	}else{
		var pid = row[0].pid;
		if (confirm("是否确认删除?")) {
		$.ajax({
			type: "POST",
	        url: "<%=basePath%>workDayController/deleteById.action",
	        data:{"pid":pid},
	        dataType: "json",
	        success: function(ret){
	        	if(ret && ret.header["success"]){
					// 操作提示
					$.messager.alert('操作提示',ret.header["msg"]);
					// 重新加载法定节假日信息
					var url = "<%=basePath%>workDayController/workDayList.action";
					$('#grid').datagrid('options').url = url;
					$('#grid').datagrid('reload');
	        	}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');
				}
	        }
		});
	 }
   }
}
 
//保存法定节假日信息
function saveWorkDayInfo(){
	var correctDate =$("#correctDate1").datebox('getValue');
	if(correctDate ==null || correctDate == ''){
		$.messager.alert('操作提示', '请选择日期!', 'error');
		return;
	}
	$("#workDayInfo").form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$.messager.alert('操作提示','保存信息成功','info');
				$('#add_work_info').dialog('close');
				// 重新加载法定节假日信息
					var url = "<%=basePath%>workDayController/workDayList.action";
					$('#grid').datagrid('options').url = url;
					$('#grid').datagrid('reload');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}

//是否为法定节假日
function formatterIsHolidays(val,row){
	if(val == 2){
		return "否";
	}else 
		return "是";
	}

</script>
<body>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="listComPotential.action" method="post" id="searchFrom" >
	<!-- 查询条件 -->
	<div style="padding:5px">
		<table>
		<tr>
			<td align="right" width="80" height="25">年份：</td>
			<td><input name="correctYear" id="correctYear"  class="easyui-textbox"  style="width: 129px;" /></td>
			<td align="right" width="80" height="25">日期：</td>
			<td><input name="fromCorrectDate" id="fromCorrectDate"  class="easyui-datebox"  style="width: 129px;" /><span>~</span></td>
			<td><input name="toCorrectDate" id="toCorrectDate"  class="easyui-datebox"  style="width: 129px;" /></td>
		</tr>
		<tr>
			<td align="right" width="80" height="25">是否节假日</td>
			<td>
			<select class="easyui-combobox" name="isHolidays" id="isHolidays"style="width: 129px;">
              <option value=-1 selected="selected">--请选择--</option>
              <option value=1>是</option>
              <option value=2>否</option>

             </select>
			</td>
			<td>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 20px;" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
				<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
			</td>
		</tr>
		</table>
	</div>
	</form>
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addWorkDay()">新增</a>
<!-- 		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editWorkDay()">修改</a> -->
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeWorkDay()">删除</a>
	</div>
	
</div>
<div style="height:100%">
<table id="grid" title="法定节假日管理" class="easyui-datagrid" singleSelect="true"
	style="height:100%;width: auto;"
	data-options="
	    url: 'workDayList.action',
	    method: 'get',
	    rownumbers: true,
	    singleSelect: false,
	    pagination: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
		selectOnCheck: true,
		checkOnSelect: true">
	<!-- 表头 -->
	<thead>
		<tr>
		 	<th data-options="field:'pid',checkbox:true"></th>
		    <th width="80" data-options="field:'correctYear',width:100,align:'center'">年份</th>
		    <th width="80" data-options="field:'correctDate',width:100,align:'center'">日期</th>
		    <th width="80" data-options="field:'isHolidays',width:200,formatter:formatterIsHolidays,align:'center'">是否为节假日</th>
		    <th width="100" data-options="field:'remark',width:200,align:'center'">备注</th>
		</tr>
	</thead>
</table>

<!-- 新增法定节假日信息开始 -->
		<div id="add_work">		
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveWorkDayInfo()">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#add_work_info').dialog('close')">关闭</a>
		</div>
		<div id="add_work_info" class="easyui-dialog" fitColumns="true"  title="新增法定节假日信息"
		     style="width:550px;top:100px;padding:10px; height: 190px" buttons="#add_work" closed="true" >
			<form id="workDayInfo" action="<%=basePath%>workDayController/saveWorkDayInfo.action " method="POST">
				<table class="beforeloanTable">
				<input id="pid" name="pid" class="easyui-textbox" hidden="hidden"/>
				<tr id = "add_code_info">
					<td class="label_right1"><font color="red">*</font>设置法定日期</td>
					<td>
						<input id="correctDate1" name="correctDate" class="easyui-datebox" style="width: 350px;" editable="false"  /> 
					</td> 
					<td class="label_right1"><font color="red">*</font>是否法定假日</td>
					<td>
					<select name="isHolidays" id="isHolidays" editable="false" class="easyui-combobox" style="width: 105px;">
						<option value=1>是</option>
						<option value=2>否</option>
					</select>
					</td>
				</tr>
				<tr>
					<td class="label_right">备注：</td>
					<td colspan="4">
						<input  id = "remark" name="remark" class="easyui-textbox"  style="width:100%;height:60px" data-options="multiline:true,validType:'length[0,80]'"/>
					</td>
				</tr>
				 </table>
			</form>
		</div>
		<!-- 新增法定节假日结束 -->
</div>
</body>
