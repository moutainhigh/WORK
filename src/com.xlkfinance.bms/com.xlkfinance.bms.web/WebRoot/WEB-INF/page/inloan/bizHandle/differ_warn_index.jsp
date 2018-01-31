<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/tools/dataformat.js"></script>
<script type="text/javascript">
	//重置按钮
	function majaxReset() {
		$('#searchFrom').form('reset');
	}
	//打开预警事项备注窗口
	function openAddWarnDialog() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var handleDynamicId=row[0].handleDynamicId;
			var projectId=row[0].projectId;
			var projectName=row[0].projectName;
			var differ=row[0].differ;
			$("#handleDynamicId").val(handleDynamicId);
			$("#projectId").val(projectId);
			$("#projectName").val(projectName);
			$("#differ").val(differ);
			$(".projectName").text("项目：" + projectName);
			$('#addWarnDialog').dialog('open').dialog('setTitle', "预警事项备注--"+row[0].projectName);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}

	}
	//提交差异预警处理备注
	function submitWarnRemark() {
		var remark = $("#remark").val();
		if (remark == null || remark.trim().length == 0) {
			$.messager.alert("操作提示", "请输入备注", "error");
			return;
		}
		$("#warnForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', ret.header["msg"], 'info');
					$("#addWarnDialog").dialog("close");
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	function operateLoadBusiness(value, row, index) {
		return '<a href="javascript:void(0)" onclick="openHisDifferWarnListDialog('+ row.projectId+')"><font color="blue">查看历史</font></a>';
	}
	//预警备注历史列表
	function openHisDifferWarnListDialog(projectId) {
		var url = "";// 路径
		$('#grid_hisDifferWarnList').datagrid({  
			url:'<%=basePath%>bizHandleController/hisDifferWarnList.action?projectId='+projectId,  
		}); 
		$('#hisDifferWarnListDialog').dialog('open').dialog('setTitle', "预警备注历史列表");
	}
	//初始化
	$(document).ready(function() {
		//差异预警处理备注窗口关闭时，清空表单数据
	 	$('#addWarnDialog').dialog({
	 	    onClose:function(){
	 	    	$("#remark").val('');
	 	    }
	 	});
	 	$('#hisDifferWarnListDialog').dialog({
	 	    onClose:function(){
	 	    	$('#grid_hisDifferWarnList').datagrid('loadData',{total:0,rows:[]}); 
	 	    }
	 	});
	});
	var differWarnList = {
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>";
				return va;
			}
		}
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>bizHandleController/needHandleWarnList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td align="right" height="28">项目编号：</td>
        <td><input class="easyui-textbox" name="projectNumber" istyle="width: 150px" /></td>
        <td align="right" height="28">项目名称：</td>
        <td><input class="easyui-textbox" name="projectName" style="width: 150px" /></td>
        <td align="right" height="28">业务办理节点：</td>
        <td><select class="select_style easyui-combobox" style="width: 150px" editable="false" name="flowName">
          <option value="">--请选择--</option>
          <option value='发放贷款'>发放贷款&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
          <option value='赎楼'>赎楼</option>
          <option value='取旧证'>取旧证</option>
          <option value='注销抵押'>注销抵押</option>
          <option value='过户'>过户</option>
          <option value='取新证'>取新证</option>
          <option value='抵押'>抵押</option>
          <option value='回款'>回款</option>
        </select></td>
       </tr>
       <tr>
        <td align="right" height="28">物业名称：</td>
        <td><input class="easyui-textbox" name="houseName" style="width: 150px" /></td>
        <td align="right" height="28">买方姓名：</td>
        <td><input class="easyui-textbox" name="buyerName" style="width: 150px" /></td>
        <td align="right" height="28">卖方姓名：</td>
        <td><input class="easyui-textbox" name="sellerName" style="width: 150px" /></td>
       </tr>
       <tr>
        <td align="right" height="28">客户经理：</td>
        <td><input class="easyui-textbox" name="pmUserName" style="width: 150px" /></td>
       </tr>
       <tr>
        <td colspan="6"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>
      </table>
     </div>
    </form>

    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
     <shiro:hasAnyRoles name="DIFFER_WARN_TRACK_REMARK,ALL_BUSINESS">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openAddWarnDialog()">跟进备注</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="预警列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>bizHandleController/needHandleWarnList.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true"></th>
       <th data-options="field:'projectName',formatter:differWarnList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
       <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th>
       <th data-options="field:'houseName',sortable:true" align="center" halign="center">物业名称</th>
       <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方姓名</th>
       <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方姓名</th>
        <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">客户经理</th>
       <th data-options="field:'flowName',sortable:true" align="center" halign="center">办理动态</th>
       <th data-options="field:'fixDay',sortable:true" align="center" halign="center">固定天数</th>
       <th data-options="field:'differ',sortable:true" align="center" halign="center">差异</th>
       <th data-options="field:'yyy',formatter:operateLoadBusiness" align="center" halign="center">查看历史</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="addWarnDialog" class="easyui-dialog" data-options="modal:true" buttons="#addWarnDiv" style="width: 513px; height: 248px; padding: 10px 20px;" closed="true">
    <form id="warnForm" name="warnForm" method="post" action="${basePath}bizHandleController/addWarnRemark.action" >
     <table>
      <tr>
       <td colspan="2"><b><span class="projectName"></span></b></td>
      </tr>
      <tr>
       <td colspan="2"><textarea style="height: 130px; width: 469px;" id="remark" maxlength="300" class="text_style" name="remark" placeholder="备注内容"></textarea></td>
      </tr>
     </table>
     <input type="hidden" id="handleDynamicId" name="handleDynamicId" value="">
     <input type="hidden" id="projectId" name="projectId" value="">
     <input type="hidden" id="projectName" name="projectName" value="">
     <input type="hidden" id="differ" name="differ" value="">
    </form>
   </div>
   <div id="addWarnDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitWarnRemark()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addWarnDialog').dialog('close')"
    >取消</a>
   </div>
   <div id="hisDifferWarnListDialog" class="easyui-dialog" data-options="modal:true" style="width: 1013px; height: 448px; padding: 10px 20px;" closed="true">
    <table id="grid_hisDifferWarnList" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
      method: 'POST',
      rownumbers: true,
      singleSelect: true,
      pagination: true,
      sortOrder:'asc',
      remoteSort:false,
      idField: 'pid',
      fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'projectName',sortable:true" align="center" halign="center">项目名称</th>
       <th data-options="field:'flowName',sortable:true" align="center" halign="center">办理动态</th>
       <th data-options="field:'differ',sortable:true" align="center" halign="center">差异</th>
       <th data-options="field:'handleAuthorName',sortable:true" align="center" halign="center">处理者</th>
       <th data-options="field:'handleDate',sortable:true,formatter:convertDateTime" align="center" halign="center">处理时间</th>
       <th data-options="field:'remark',sortable:true,width:120" align="center" halign="center">备注</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
