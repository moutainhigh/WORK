<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<body class="easyui-layout">
	<div >
		<div data-options="region:'left',border:false">
			<!--图标按钮 -->
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="getSysBankInfo.action" method="post" id="searchFrom" name="searchFrom" >
					<!-- 查询条件 -->
					<input type="hidden" name="parentId" value="0"/>
					<div style="margin:5px">
						银行名称:<input class="easyui-textbox" id="bankName" name="bankName" /> 
						银行简称:<input class="easyui-textbox" id="bankShortHand" name="bankShortHand" /> 
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearch()">查询</a>
					</div>
				</form>
				
				<!-- 操作按钮 -->
				<div style="padding-bottom:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
				</div>
				
			</div>
			<div class="sysLookupDiv" style="height:50%">
				<table id="grid" title="总行数据" class="easyui-datagrid" 
					style="height:100%;width: auto;"
					data-options="
					    url: 'getSysBankInfo.action',
					    method: 'POST',
					    rownumbers: true,
					    singleSelect: true,
					    singleOnCheck: true,
					    selectOnCheck: false,
						checkOnSelect: false,
					    pagination: true,
					    sortOrder:'asc',
					    remoteSort:false,
					    toolbar: '#toolbar',
					    idField: 'pid',
					    fitColumns:true,
					    onLoadSuccess:function(data){
					    	clearSelectRows('#grid');
					    },
					    onClickRow:function(rowIndex, rowData){
					    	clickLookup(rowData.pid);
					    }">
					<!-- 表头 -->
					<thead><tr>
					    <th data-options="field:'pid',checkbox:true" ></th>
					    <th data-options="field:'bankName',sortable:true"  >银行名称</th>
					    <th data-options="field:'bankShortHand',sortable:true"  >银行简称</th>
					    <th data-options="field:'status',formatter:formatStatus,sortable:true"  >状态</th>
					</tr></thead>
				</table>
			</div>
		</div>
		<div data-options="region:'left',border:false">
			<!-- 支行数据 grid -->
			<div id="toolbarVal" class="easyui-panel" style="border-bottom: 0;">
				<!-- 操作按钮 -->
				<div style="padding-bottom:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItemVal()">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItemVal()">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItemVal()">删除</a>
				</div>
			</div>
			<div class="syslookupDiv2" style="height:50%">
				<table id="gridVal" title="支行数据" class="easyui-datagrid" 
					style="height:100%;width: auto;"
					data-options="
					    method: 'get',
					    rownumbers: true,
					    singleSelect: true,
					    singleOnCheck: true,
					    selectOnCheck: false,
						checkOnSelect: false,
					    pagination: true,
					    sortOrder:'asc',
					    remoteSort:false,
					    toolbar: '#toolbarVal',
					    idField: 'pid',
					    onLoadSuccess:function(data){
					    	clearSelectRows('#gridVal');
					    },
					    fitColumns:true">
					<!-- 表头 -->
					<thead><tr>
					    <th data-options="field:'pid',checkbox:true" ></th>
					    <th data-options="field:'bankName',sortable:true"  >银行名称</th>
					    <th data-options="field:'bankShortHand',sortable:true"  >银行简称</th>
					</tr></thead>
				</table>
			</div>
			<!-- 保存 和取消按钮 -->
			<div id="dlg-buttons">		
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveItem()">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
			</div>
		</div>
		<!--窗口 -->
		<div id="dlg" class="easyui-dialog" style="width:450px;height:200px;padding:20px 20px" 
			closed="true" buttons="#dlg-buttons">
	     	<form id="fm" method="post">
	     		<input type="hidden" name="pid" />
	     		<input type="hidden" name="type" value="1"/>
	     		<input type="hidden" name="status" value="1"/>
	     		<input type="hidden" name="parentId" value="0"/>
	     		<table>
	     			<tr>
	     				<td align="right" height="28"><label><font color="red">*</font>银行名称：</label></td>
	     				<td><input name="bankName" class="easyui-textbox" data-options="validType:'length[1,50]'" missingMessage="请填写银行名称!" /></td>
	     			</tr>
	     			<tr>
	     				<td align="right" height="28"><label>银行简称：</label></td>
	     				<td><input name="bankShortHand" class="easyui-textbox" data-options="validType:'length[0,20]'"/></td>
	     			</tr>
	     		</table>
	    	</form>
		</div>
		
		<!-- 保存 和取消按钮 -->
		<div id="dlg-buttonsVal">		
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveItemVal()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgVal').dialog('close')">取消</a>
		</div>
		<!--窗口 -->
		<div id="dlgVal" class="easyui-dialog" style="width:420px;height:200px;padding:20px 20px" 
			closed="true" buttons="#dlg-buttonsVal">
	     	<form id="fmVal" method="post">
	     		<input type="hidden" name="pid" />
	     		<input type="hidden" name="type" value="1"/>
	     		<input type="hidden" name="status" value="1"/>
	     		<input type="hidden" name="parentId" id="parentId"/>
	     		<table>
	     			<tr>
	     				<td align="right" height="28"><label><font color="red">*</font>银行名称：</label></td>
	     				<td><input name="bankName" class="easyui-textbox" data-options="validType:'length[1,50]'" missingMessage="请填写银行名称!" /></td>
	     			</tr>
	     			<tr>
	     				<td align="right" height="28"><label>银行简称：</label></td>
	     				<td><input name="bankShortHand" class="easyui-textbox" data-options="validType:'length[0,20]'"/></td>
	     			</tr>
	     		</table>    		
	    	</form>
		</div>
	</div>
</body>
<script type="text/javascript">
var url = "";
function formatStatus(val,row){
	if(val == 1){
		return "正常";
	} else{
		return "禁用";
	}
}

// 新增
function addItem(){
	$('#fm').form('reset');
	// 将 url 地址改变
	url = "addBankInfo.action";
	$('#dlg').dialog('open').dialog('setTitle', "新增总行信息");
}

// 编辑
function editItem(){
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		$('#fm').form('load', row[0]);
 		// 将 url 地址改变
		url = "updateBankInfo.action";
		$('#dlg').dialog('open').dialog('setTitle', "编辑总行信息");
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

// 删除
function removeItem(){
	var row = $('#grid').datagrid('getChecked');
 	if (!row || row.length<=0) {
 		$.messager.alert("操作提示","请选择数据","error");
		return;
	}
 	var pids = new Array();
	$.messager.confirm(
		"操作提示",
		"确定删除选择的总行数据吗?",
		function(r) {
			if (r) {
				for (var i = 0; i < row.length; i++) {
					var parentId = row[i].pid;
					$.ajax({
						url: "getSysBankByParentId.action",
						async: false,
						data: {"parentId" : parentId},
						success: function(ret){
							if (ret.length > 0 && ret != '[]') {
								$.messager.alert('操作提示',"有支行数据的记录无法删除！");
							} else {
								pids[i] = parentId;
								$.post("deleteBankInfo.action",{"pids" : pids},
									function(ret) {
										//  此方法，不需要换转换json格式数据,因为传过来的值就是json格式的数据 
										if (ret && ret.header["success"]) {
											clearSelectRows("#grid");
											// 必须重置 datagr 选中的行 
											$("#grid").datagrid('reload');
											clearSelectRows("#gridVal");
											$("#grid").datagrid("gridVal");
											$('#gridVal').datagrid('loadData',{total : 0,rows : []});
										} else {
											$.messager.alert('操作提示',ret.header["msg"],'error');
										}
									},
								'json');
							}
						}
						})
				}
			}
		});
	}
	
	// 新增  or 更新
	function saveItem() {
		$("#fm").form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				// 转换成json格式的对象
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					clearSelectRows("#grid");
					$("#dlg").dialog('close');
					$("#grid").datagrid('reload');
	 				clearSelectRows("#gridVal");
					$('#gridVal').datagrid('loadData', {
						total : 0,
						rows : []
					});
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}

	// 查询 
	function ajaxSearch() {
		$('#grid').datagrid('load',{
			bankShortHand: $("#bankShortHand").val(),
			bankName: $("#bankName").val(),
			parentId:0
		});
	}

	// 单击行事件
	function clickLookup(parentId) {
		clearSelectRows("#gridVal");
		$("#gridVal").datagrid("options").url = "getSysBankByParent.action?parentId="
				+ parentId;
		$("#gridVal").datagrid("reload");
	}

	// 新增新增分行信息
	function addItemVal() {
		// 判断你是否选择总行数据
		var row = $('#grid').datagrid('getSelections');
		if (row.length < 1) {
			$.messager.alert("操作提示", "请先选择总行数据", "error");
			return;
		}

		$('#fmVal').form('reset');
		// 赋值总行ID过来
		$("#parentId").val(row[0].pid);
		// 将 url 地址改变
		url = "addBankInfo.action";
		$('#dlgVal').dialog('open').dialog('setTitle', "新增分行信息");
	}

	// 更新分行信息
	function editItemVal() {
		var row = $('#gridVal').datagrid('getSelections');
		if (row.length == 1) {
			$('#fmVal').form('load', row[0]);
			// 将 url 地址改变 
			url = "updateBankInfo.action";
			$('#dlgVal').dialog('open').dialog('setTitle', "编辑分行信息");
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}

	// 数据分行信息 保存or更新的方法 
	function saveItemVal() {
		$("#fmVal").form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				// 转换成json格式的对象
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					clearSelectRows("#gridVal");
					$("#dlgVal").dialog('close');
					$("#gridVal").datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}

	// 删除分行信息
	function removeItemVal() {
		var rows = $('#gridVal').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		// 获取选中的分行信息ID
		var pids = new Array();
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			pids[i] = row.pid;
		}
		$.messager.confirm("操作提示", "确定删除选择的此批分行数据吗?", function(r) {
			if (r) {
				$.post("deleteBankInfo.action", {
					"pids" : pids
				}, function(ret) {
					//  此方法，不需要换转换json格式数据,因为传过来的值就是json格式的数据 
					if (ret && ret.header["success"]) {
						clearSelectRows("#gridVal");
						// 必须重置 datagr 选中的行 
						$("#gridVal").datagrid('reload');
					} else {
						$.messager.alert('操作提示', ret.header["msg"], 'error');
					}
				}, 'json');
			}
		});
	}
</script>