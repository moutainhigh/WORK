<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<style type="text/css">

</style>
<script type="text/javascript">
function showProject(pId,pName){
	window.parent.addTab("${basePath}beforeLoanController/addNavigation.action?projectId="+pId+"&param='refId':'"+pId+"','projectName':'"+pName+"'","项目详情");	
}
   //根据项目projectId决定跳到那个页面
   function selectItem(){
	   var row = $('#grid').datagrid('getSelections');
	   var pId = row[0].pid;
	   var pName =row[0].value1;
		if (row.length == 1) {
			$.ajax({
				url : '${basePath}customerController/selectCredit.action?projectId='+row[0].pid,
				type : 'post',
				success : function(result) {
					var num = result;
					//如果num>0
					if(num >0){
// 						window.location.href = "返回到额度提款申请的页面";
					}
					else{
						//window.location.href ="${basePath}beforeLoanController/addNavigation.action?projectId="+row[0].pid;
						showProject(pId,pName);
						var projectDom = "<a href='javascript:void(0)' onclick='showProject("+pId+",\""+pName+"\")'><font color='blue'>"+pName+"</font> | </a>";
						return projectDom;
					}
				}
			});
		}else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
   }
</script>
<body>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="selectItem();">查看</a>
	</div>
	
</div>

<table id="grid" title="业务往来查询" class="easyui-datagrid" 
	style="height:450px;width: auto;"
	data-options="
	    url: 'searcherPerBusiness.action?acctId='+${acctId },
	     method: 'post',
	    rownumbers: true,
	    singleSelect: false,
	    pagination: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true">
	<!-- 表头 -->
	<thead><tr>
		<th data-options="field:'pid',checkbox:true,width:100" ></th>
	    <th data-options="field:'value1',width:100,sortable:true" >贷款项目名称</th>
	    <th data-options="field:'value2',width:80,sortable:true" >授信金额</th>
	    <th data-options="field:'value3',width:80,sortable:true" >担保方式</th>
	     <th data-options="field:'value4',width:80,sortable:true" >贷款金额</th>
	    <th data-options="field:'value5',width:60,sortable:true" >利率</th>
	    <th data-options="field:'value6',width:60,sortable:true" >期限</th>
	    <th data-options="field:'value7',width:120,sortable:true" >授信开始时间</th>
	    <th data-options="field:'value8',width:120,sortable:true" >授信结束时间</th>
	    <th data-options="field:'value9',width:80,sortable:true" >放款日期</th>
	    <th data-options="field:'value10',width:80,sortable:true" >还款日期</th>
	    <th data-options="field:'value11',width:80,sortable:true" >是否结清</th>
	    <th data-options="field:'value12',width:130,sortable:true" >客户证件号码</th>
	    <th data-options="field:'value13',width:120,sortable:true" >客户联系电话</th>
	    <th data-options="field:'value14',width:100,sortable:true" >业务经理</th>
	</tr></thead>
</table>
</body>