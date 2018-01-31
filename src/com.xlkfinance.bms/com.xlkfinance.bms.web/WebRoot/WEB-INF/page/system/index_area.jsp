<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<body class="easyui-layout">
	<div>
		<div data-options="region:'left',border:false" style="width: 50%;float: left;">
			<!--图标按钮 -->
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="getSysBankInfo.action" method="post">
					<!-- 查询条件 -->
					<div style="margin:5px">
						省份:<input class="easyui-textbox" id="areaName" name="areaName" /> 
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryAreaInfo(this, 1)">查询</a>
					</div>
				</form>
				
				<!-- 操作按钮 -->
				<div style="padding-bottom:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addAreaInfo(1)">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editAreaInfo(1)">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeAreaInfo(1)">删除</a>
				</div>
				
			</div>
			<div class="sysLookupDiv" style="height:50%; width: auto;">
				<table id="provinceGrid" title="省份" class="easyui-datagrid" 
					style="height:100%;width: auto;"
					data-options="
					    url: 'pagedAreaInfo.action',
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
					    	clearSelectRows('provinceGrid');
					    },
					    onClickRow:function(rowIndex, rowData){
					    	queryNextNode(rowData.areaCode, 2, 'gridCity');
					    }">
					<!-- 表头 -->
					<thead><tr>
					    <th data-options="field:'oldAreaCode',checkbox:true" ></th>
					    <th data-options="field:'areaCode',sortable:true">省份编码</th>
					    <th data-options="field:'areaName',sortable:true">省份</th>
					    <th data-options="field:'areaTelCode',sortable:true">区号</th>
					</tr></thead>
				</table>
			</div>
			<!-- 区级信息数据 grid -->
			<div id="toolbarDsitrict" class="easyui-panel" style="border-bottom: 0;">
				<form action="pagedAreaInfo.action" method="post">
					<!-- 查询条件 -->
					<div style="margin:5px">
						区县:<input class="easyui-textbox" id="areaName" name="areaName" /> 
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryAreaInfo(this, 3)">查询</a>
					</div>
				</form>
				<!-- 操作按钮 -->
				<div style="padding-bottom:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addAreaInfo(3)">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editAreaInfo(3)">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeAreaInfo(3)">删除</a>
				</div>
			</div>
			<div class="syslookupDiv2" style="height:50%; width: auto;">
				<table id="gridDistrict" title="区县" class="easyui-datagrid" 
					style="height:100%;width: auto;"
					data-options="
					    method: 'post',
					    rownumbers: true,
					    singleSelect: true,
					    singleOnCheck: true,
					    selectOnCheck: false,
						checkOnSelect: false,
					    pagination: true,
					    sortOrder:'asc',
					    remoteSort:false,
					    toolbar: '#toolbarDsitrict',
					    idField: 'pid',
					    onLoadSuccess:function(data){
					    	clearSelectRows('#gridDsitrict');
					    },
					    fitColumns:true">
					<!-- 表头 -->
					<thead><tr>
					     <th data-options="field:'oldAreaCode',checkbox:true" ></th>
					     <th data-options="field:'areaCode',sortable:true">区县编码</th>
					    <th data-options="field:'areaName',sortable:true">区县</th>
					    <th data-options="field:'areaTelCode',sortable:true"  >区号</th>
					</tr></thead>
				</table>
			</div>
		</div>
		<div data-options="region:'left',border:false" style="width: 50%;float: left;">
			<!-- 城市信息数据 grid -->
			<div id="toolbarCity" class="easyui-panel" style="border-bottom: 0;">
				<form action="pagedAreaInfo.action" method="post" >
					<!-- 查询条件 -->
					<div style="margin:5px">
						城市:<input class="easyui-textbox" id="areaName" name="areaName" /> 
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryAreaInfo(this, 2)">查询</a>
					</div>
				</form>
				<!-- 操作按钮 -->
				<div style="padding-bottom:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addAreaInfo(2)">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editAreaInfo(2)">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeAreaInfo(2)">删除</a>
				</div>
			</div>
			<div class="syslookupDiv2" style="height:50%; width: auto;">
				<table id="gridCity" title="城市" class="easyui-datagrid" 
					style="height:100%;width: auto;"
					data-options="
					    method: 'post',
					    rownumbers: true,
					    singleSelect: true,
					    singleOnCheck: true,
					    selectOnCheck: false,
						checkOnSelect: false,
					    pagination: true,
					    sortOrder:'asc',
					    remoteSort:false,
					    toolbar: '#toolbarCity',
					    idField: 'pid',
					    onLoadSuccess:function(data){
					    	clearSelectRows('#gridCity');
					    },
					    onClickRow:function(rowIndex, rowData){
					    	queryNextNode(rowData.areaCode, 3, 'gridDistrict');
					    },
					    fitColumns:true">
					<!-- 表头 -->
					<thead><tr>
					    <th data-options="field:'oldAreaCode',checkbox:true" ></th>
					    <th data-options="field:'areaCode',sortable:true">城市编码</th>
					    <th data-options="field:'areaName',sortable:true">城市</th>
					    <th data-options="field:'areaTelCode',sortable:true"  >区号</th>
					</tr></thead>
				</table>
			</div>
		</div>
		
		<!-- 省份保存 和取消按钮 -->
		<div id="dlg-buttons">		
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAreaInfo(1)">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#provinceGlg').dialog('close')">取消</a>
		</div>
		<!--窗口 -->
		<div id="provinceGlg" class="easyui-dialog" style="width:450px;height:200px;padding:20px 20px" 
			closed="true" buttons="#dlg-buttons">
	     	<form id="provinceForm" method="post">
	     		<input type="hidden" name="levelNo" value="1"/>
	     		<input type="hidden" name="oldAreaCode"/>
	     		<input type="hidden" name="parentCode" value="0"/>
	     		<table>
	     			<tr>
	     				<td align="right" height="28"><label><font color="red">*</font>省份编码：</label></td>
	     				<td><input name="areaCode" class="easyui-textbox" data-options="validType:'length[1,50]'" missingMessage="请填写省份编码!" /></td>
	     			</tr>
	     			<tr>
	     				<td align="right" height="28"><label><font color="red">*</font>省份名称：</label></td>
	     				<td><input name="areaName" class="easyui-textbox" data-options="validType:'length[0,20]'"  missingMessage="请填写省份名称!"/></td>
	     			</tr>
	     			<tr>
	     				<td align="right" height="28"><label>区号：</label></td>
	     				<td><input name="areaTelCode" class="easyui-textbox" data-options="validType:'length[0,20]'"  missingMessage="请填写区号!"/></td>
	     			</tr>
	     		</table>
	    	</form>
		</div>
		
		<!-- 城市信息保存 和取消按钮 -->
		<div id="dlg-buttons-city">		
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAreaInfo(2)">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#cityGlg').dialog('close')">取消</a>
		</div>
		<!--窗口 -->
		<div id="cityGlg" class="easyui-dialog" style="width:450px;height:200px;padding:20px 20px" closed="true" buttons="#dlg-buttons-city">
	     	<form id="cityForm" method="post">
	     		<input type="hidden" name="levelNo" value="2"/>
	     		<input type="hidden" name="oldAreaCode"/>
	     		<input type="hidden" name="parentCode"/>
	     		<table>
	     			<tr>
	     				<td align="right" height="28"><label><font color="red">*</font>城市编码：</label></td>
	     				<td><input name="areaCode" class="easyui-textbox" data-options="validType:'length[1,50]'" missingMessage="请填写城市编码!" /></td>
	     			</tr>
	     			<tr>
	     				<td align="right" height="28"><label><font color="red">*</font>城市名称：</label></td>
	     				<td><input name="areaName" class="easyui-textbox" data-options="validType:'length[0,20]'"  missingMessage="请填写城市名称!"/></td>
	     			</tr>
	     			<tr>
	     				<td align="right" height="28"><label>区号：</label></td>
	     				<td><input name="areaTelCode" class="easyui-textbox" data-options="validType:'length[0,20]'"  missingMessage="请填写区号!"/></td>
	     			</tr>
	     		</table>
	    	</form>
		</div>
		
		<!-- 区县信息保存 和取消按钮 -->
		<div id="dlg-buttons-district">		
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAreaInfo(3)">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#districtGlg').dialog('close')">取消</a>
		</div>
		<!--窗口 -->
		<div id="districtGlg" class="easyui-dialog" style="width:450px;height:200px;padding:20px 20px" closed="true" buttons="#dlg-buttons-district">
	     	<form id="districtForm" method="post">
	     		<input type="hidden" name="levelNo" value="3"/>
	     		<input type="hidden" name="oldAreaCode"/>
	     		<input type="hidden" name="parentCode"/>
	     		<table>
	     			<tr>
	     				<td align="right" height="28"><label><font color="red">*</font>区县编码：</label></td>
	     				<td><input name="areaCode" class="easyui-textbox" data-options="validType:'length[1,50]'" missingMessage="请填写区县编码!" /></td>
	     			</tr>
	     			<tr>
	     				<td align="right" height="28"><label><font color="red">*</font>区县名称：</label></td>
	     				<td><input name="areaName" class="easyui-textbox" data-options="validType:'length[0,20]'"  missingMessage="请填写区县名称!"/></td>
	     			</tr>
	     			<tr>
	     				<td align="right" height="28"><label>区号：</label></td>
	     				<td><input name="areaTelCode" class="easyui-textbox" data-options="validType:'length[0,20]'"  missingMessage="请填写区号!"/></td>
	     			</tr>
	     		</table>
	    	</form>
		</div>
	</div>
</body>
<script type="text/javascript">
var url = "", areaInfoMap = {
	1: {grid: "#provinceGrid", form: "#provinceForm", dialog: "#provinceGlg", message: "省份"},
	2: {grid: "#gridCity", form: "#cityForm", dialog: "#cityGlg", message: "城市"},
	3: {grid: "#gridDistrict", form: "#districtForm", dialog: "#districtGlg", message: "区县"}
};

// 新增
function addAreaInfo(levelNo) {
	var $form = $(areaInfoMap[levelNo].form), $dialog = $(areaInfoMap[levelNo].dialog), parentCode = 0;
	if (levelNo == 2) {
		var rows = $("#provinceGrid").datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示","请先选择省份数据","error");
			return;
		}
		parentCode = rows[0].areaCode;
	}
	if (levelNo == 3) {
		var rows = $("#gridCity").datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示","请先选择城市数据","error");
			return;
		}
		parentCode = rows[0].areaCode;
	}
	
	$form.form('reset');
	$form.form('load', {levelNo: levelNo, parentCode: parentCode});
	// 将 url 地址改变
	url = "saveAreaInfo.action";
	$dialog.dialog('open').dialog('setTitle', "新增"+ areaInfoMap[levelNo].message +"信息");
}

// 编辑
function editAreaInfo(levelNo) {
	var $form = $(areaInfoMap[levelNo].form), $dialog = $(areaInfoMap[levelNo].dialog), $grid = $(areaInfoMap[levelNo].grid);
	var row = $grid.datagrid('getSelections');
	if (levelNo == 2) {
		var rows = $("#provinceGrid").datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示","请先选择省份数据","error");
			return;
		}
		parentCode = rows[0].areaCode;
	}
	if (levelNo == 3) {
		var rows = $("#gridCity").datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示","请先选择城市数据","error");
			return;
		}
		parentCode = rows[0].areaCode;
	}
 	if (row.length == 1) {
 		$form.form('load', row[0]);
 		// 将 url 地址改变
		url = "saveAreaInfo.action";
		$dialog.dialog('open').dialog('setTitle', "编辑"+ areaInfoMap[levelNo].message +"信息");
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

// 删除
function removeAreaInfo(levelNo) {
	var $form = $(areaInfoMap[levelNo].form), $dialog = $(areaInfoMap[levelNo].dialog), $grid = $(areaInfoMap[levelNo].grid);
	var row = $grid.datagrid('getChecked'), areaCodes = [];
 	if (!row || row.length <= 0) {
 		$.messager.alert("操作提示","请选择数据", "error");
		return;
	}
	$.messager.confirm("操作提示", "确定删除选择的"+ areaInfoMap[levelNo].message +"数据吗?", function(r) {
			if (r) {
				for (var i = 0; i < row.length; i++) {
					var parentCode = row[i].areaCode, nextLevel = levelNo + 1;
					$.ajax({
						url: "getSysAreaInfo.action",
						async: false,
						data: {levelNo : nextLevel, parentCode: parentCode},
						success: function(ret) {
							if (ret.length > 0 && ret != '[]') {
								$.messager.alert('操作提示',"有关联"+ areaInfoMap[nextLevel].message +"数据的记录无法删除！");
							} else {
								areaCodes.push(parentCode);
								$.post("deleteAreaInfo.action",{areaCodes : areaCodes.join(",")}, function(ret) {
									//  此方法，不需要换转换json格式数据,因为传过来的值就是json格式的数据 
									if (ret && ret.header["success"]) {
										clearSelectRows(areaInfoMap[levelNo].grid);
										// 必须重置 datagr 选中的行 
										$grid.datagrid('reload');
									} else {
										$.messager.alert('操作提示',ret.header["msg"],'error');
									}
								}, 'json');
							}
						}
					});
				}
			}
		});
	}
	
	// 新增  or 更新
	function saveAreaInfo(levelNo) {
		var $form = $(areaInfoMap[levelNo].form), $dialog = $(areaInfoMap[levelNo].dialog), $grid = $(areaInfoMap[levelNo].grid);
		$form.form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				// 转换成json格式的对象
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					clearSelectRows(areaInfoMap[levelNo].grid);
					$dialog.dialog('close');
					$grid.datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}

	// 查询 
	function queryAreaInfo(target, levelNo) {
		var $elem = $(areaInfoMap[levelNo].grid);
		$elem.datagrid('load',{
			areaName: $(target).parents("form").find("#areaName").val()
		});
	}

	// 单击行事件
	function queryNextNode(parentId, levelNo, elem) {
		var $this = $("#" + elem);
		clearSelectRows($this);
		$this.datagrid("options").url = "pagedAreaInfo.action?parentCode="
				+ parentId +"&levelNo=" + levelNo;
		$this.datagrid("reload");
	}
</script>