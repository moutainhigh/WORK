<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>

<script type="text/javascript">
function formatterStatus(val,row){
	if(val == 1){
		return "有效";
	}else if(val == -1){
		return "无效";
	}
}
function formatterSystemPlatform(val,row){
	if(val == 1){
		return "Android";
	}else if(val == 2){
		return "IOS";
	}
}
function formatterUpgradesStatus(val,row){
	if(val == 1){
		return "不强制升级";
	}else if(val == 2){
		return "强制升级";
	}
}
function formatterSize(val,row){
	if(row.file != null && row.file !=undefined && row.file.fileSize>0){
		return  parseInt(row.file.fileSize/1024)+" KB";
	}else{
		return '--';
	}
}
//查询 
function ajaxSearch(){
	var pageNumber=$('#grid').datagrid('options')['pageSize'];
	$('#rows').val(pageNumber);
	$('#searchFrom').form('submit',{
        success:function(data){
        	var str = JSON.parse(data);
           	$('#grid').datagrid('loadData',str);
           	$('#grid').datagrid('clearChecked');
       }
    });
};
function editItem(){
	var row = $('#grid').datagrid('getSelections');
	if (row.length == 1) {
		var url="${basePath}sysAppVersionInfoController/toAddOrUpdate.action?pid="+row[0].pid;
		parent.openNewTab(url, "APP版本信息编辑", true);
	} else if (row.length > 1) {
		$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
	} else {
		$.messager.alert("操作提示", "请选择数据", "error");
	}
	
}
//打开文件上传窗口
function openFileDialog(){
	var row = $('#grid').datagrid('getSelections');
	if (row.length == 1) {
		var pid=row[0].pid;
		$('#saveFile').attr("onclick","submitFileForm('"+pid+"')");
		$('#uploanFile-dialog').dialog('open').dialog('setTitle', "上传程序文件");
	} else if (row.length > 1) {
		$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
	} else {
		$.messager.alert("操作提示", "请选择数据", "error");
	}
	
}
//提交文件
function submitFileForm(pid){
	if($("#offlineMeetingFiles").textbox('getValue')!=''){
		$('#foreclosureFileForm').attr('enctype','multipart/form-data');
		$('#foreclosureFileForm').form('submit',{
			url:'${basePath}sysAppVersionInfoController/uploadFile.action?appId='+pid,
			success:function(data) {
				var ret = eval("(" + data + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert("操作提示", ret.header["msg"]);
					$("#uploanFile-dialog").dialog("close");
					 // 重新加载列表
	                 $("#grid").datagrid("clearChecked");
	                 $("#grid").datagrid('reload'); 
				}else{
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			   $("#offlineMeetingFiles").textbox('setValue','');
			},
			
		});
	}else{
		$.messager.alert('操作','请选择文件!','info');
	} 
}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getAppList.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">app名称：</td>
				<td>
					<input name="appName" class="easyui-textbox" style="width: 200px;" />
				</td>
				<td width="80" align="right" height="25">app版本：</td>
				<td>
					<input name="appVersion" class="easyui-textbox" style="width: 200px;" />
				</td>
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
				<td>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search"
							style="margin-left: 120px;"
							onclick="ajaxSearch()">查询</a>
					<a href="#" onclick="javascript:$('#searchFrom').form('reset');"
						iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">

			<a href="javascript:void(0)" class="easyui-linkbutton" 
				iconCls="icon-add" plain="true" onclick="parent.openNewTab('${basePath}sysAppVersionInfoController/toAddOrUpdate.action?pid=0','新增APP版本信息', true)">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="openFileDialog()">上传程序</a>
	</div>

</div>
<div class="perListDiv" style="height:100%;">
<table id="grid" title="app版本列表" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getAppList.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    singleSelect: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    width: '100%',
	 	height: '100%',  
	 	 striped: true, 
	 	 loadMsg: '数据加载中请稍后……',  
	    fitColumns:true" >
	<!--
	 表头 -->
	<thead>
		<tr>
			<th data-options="field:'pid',checkbox:true"></th>
			<th data-options="field:'appName',sortable:true" >app名称</th>
			<th data-options="field:'appVersion',width:'10%',sortable:true">版本号</th>
			<th data-options="field:'status',formatter:formatterStatus,sortable:true">app状态</th>
			<th data-options="field:'systemPlatform',formatter:formatterSystemPlatform,sortable:true">系统类型</th>
			<th data-options="field:'coercivenessUpgradesStatus',formatter:formatterUpgradesStatus,width:'20%',sortable:true">强制升级状态</th>
			<!-- <th data-options="field:'downloanCount'">下载次数</th> -->
			<th data-options="field:'fileSize',formatter:formatterSize">文件大小</th>
			<th data-options="field:'createrDate',formatter:convertDate">创建时间</th>
		</tr>
	</thead>
</table>
</div>
 <div id="uploanFile-dialog" class="easyui-dialog" buttons="#upFileinfo" style="width: 650px; height: 150px; padding: 10px 20px;" closed="true">
      <form id="foreclosureFileForm" name="foreclosureFileForm" method="post">
       <table>
        <tr>
         <td><span class="cus_red">*</span>程序文件：</td>
         <td><input class="easyui-filebox offlineMeetingInput" name="offlineMeetingFile" id="offlineMeetingFiles" style="width: 425px"  data-options="buttonText:'选择文件'"></td>
        </tr>
       </table>
      </form>
     </div>
      <div id="upFileinfo">
      <a id="saveFile" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitFileForm()">保存</a> <a href="javascript:void(0)"
       class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#uploanFile-dialog').dialog('close')"
      >取消</a>
     </div>
</div>
</body>
