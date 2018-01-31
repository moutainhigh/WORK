<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/common.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript">
	function savee() {
		$('#baseInfo').form('submit',{
							url : "${basePath }afterLoanController/createSupervisePlan.action",
						/* 	onSubmit : function() {
								//checkFactorForm();
								return $(this).form('validate');
							}, */
							success : function(result) {
								$.messager.alert("操作提示", "保存成功！", "success");	
								window.location = "${basePath }afterLoanController/listSuperviseBusiness.action";
								/* var ret = eval("(" + result + ")");
								var templateId = ret.header["templateId"];
								window.location = '${basePath }customerController/editEstTemplate.action?pid='
										+ templateId; */

							},
							error : function(result) {
								$.messager.alert("操作提示", "保存失败！", "success");	
							}
						});
	}
	
	//绑定赋值
	$(function() {
		/* setSalesList("regulatoryUserId", "SUPERVISER",
				"${supervisionTask.regulatoryUser}"); */
	});
	
function closeDialog(){
	$('#dialogDiv').dialog('close');
}
	
//  新增/修改监管计划	
function editItem(pid)
{
	var url="${basePath}afterLoanController/viewAfterloanRegulatoryPlan.action?projectId=${projectId}&pid="+pid;
	$('#dialogDiv').dialog({
		href : url,
		width : 550,
		height : 320,
		modal : true,
		title : '新增/编辑监管计划',
		onBeforeClose:function(){
			 // 刷新本页面
			window.location.reload();
		}
	});
}

function removeItem(pid)
{
	$.messager.confirm('Confirm','确认要删除监管计划?',function(r){
	    if (r){
	    	
	    	postData={"pids":pid};
	    	// 保存提交
	    	$.ajax({
	    		type: "POST",
	    		dataType: "json",
	            data:postData,
	    		url : "deleteSupervisePlan.action",
	    	    success: function(data){
	    	    	// 保存成功
	    	    	if(data.header.success)
	    	    	{
	    	    		$.messager.alert("提示","删除成功","info");
	    	    		 // 刷新本页面
	    				window.location.reload();
	    	    	}
	    	    	// 保存失败
	    	    	else
	        		{
	    	    		$.messager.alert("提示",data.header.msg,"error");
	        		}
	    		},error : function(result){
	    			$.messager.alert("提示","提交失败","error");
	    		}
	    	}); 
	    }
	});
	
}

</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div id="afterToolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="editItem('')">新增监管计划</a>
	</div>
	
<table id="grid" title="监管计划" class="easyui-datagrid" 
	style=""
	data-options="
	    rownumbers: true,
	    singleSelect: true,
	    sortOrder:'asc',
	    idField: 'pid',
		selectOnCheck: true,
		checkOnSelect: true,
	    toolbar: '#afterToolbar'
	    ">
		<thead><tr>
		    <th data-options="field:'regulatoryUser',width:100,sortable:true" align="center">计划监管人</th>
		    <th data-options="field:'value3',width:100,formatter:convertDate" align="center">计划监管时间</th>
		    <th data-options="field:'regulatoryResult',width:100" align="center">监管状态</th>
		    <th data-options="field:'isSms',width:100" align="center">短信提醒</th>
		    <th data-options="field:'isMail',width:100" align="center">邮件提醒</th>
		    <th data-options="field:'remark',formatter:formatTextarea" align="center">备注</th>
		    <th data-options="field:'yy',width:120" align="center">操作</th>
		</tr></thead>
		<tbody>
		    <c:forEach items="${list}" var="bean">
		      <tr>
		        <td>${bean.regulatoryUserName}</td>
		        <td>${bean.planDt}</td>
		        <td>${bean.regulatoryStatusStr}</td>
		        <td>
		           <c:if test="${bean.isSms ==1}">是</c:if>
			     </td>
		        <td>
		           <c:if test="${bean.isMail ==1}">是</c:if>
		        </td>
		        <td>${bean.remark}</td>
		        <td>
		           <div  style="text-align: left;">
		                   <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem('${bean.pid}')">编辑</a>
		                   
		                  <c:if test="${bean.regulatoryStatus == 1 }">
		                      <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem('${bean.pid}')">删除</a>
		                  </c:if>  
		            </div>       
		        <td>
		      </tr>
		   </c:forEach>
		</tbody>
	</table>
	<div id="dialogDiv" ></div>
</div>			
</body>