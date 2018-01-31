<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

	<div>
		<form action="<%=basePath%>secondBeforeLoanController/insertZzzkdsh.action" id="zzzkdshForm" name="zzzkdshForm" >
			<input type="hidden" name="pid" id="meetingId">
			<input type="hidden" name="projectId" id="meetingProjectId">
			<input type="hidden" name="conveneUserId" id="conveneUserId">
			<table class="beforeloanTable" style="padding-left: 50px;">
				<tr>
					<td></td>
					<td>
						<a href="javascript:void(0)" onclick="openZzzkdshDiv()" ><font color='blue'>选择贷审会委员</font></a>
					</td>
				</tr>
				<tr>
					<td class="label_right">贷审会委员：</td>
					<td>
						<input id="realNameText" class="easyui-textbox" readonly="readonly" data-options="width:400" required="true"/>
						<input name="meetingMenberUser" id="userPids" type="hidden" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<a href="#" class="easyui-linkbutton" style="margin-left: 42%;" iconCls="icon-save" onclick="zzzkdshSave()">保存</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons_zzzkdsh">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="selectUser()">选择</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_zzzkdsh').dialog('close')">关闭</a>
	</div>
	<%-- 人员选取--%>
	<div id="dlg_zzzkdsh" class="easyui-dialog" fitColumns="true"  title="企业客户信息查询"
	     style="width:700px;top:100px; 0px 0px" buttons="#dlg-buttons_zzzkdsh"  closed="true" >
	    <table id="zzzkdsh_grid" class="easyui-datagrid"  		    	
		 	data-options="
		    url: '<%=basePath%>sysUserController/getAllSysUser.action',
		    method: 'post',
		    rownumbers: true,
		    singleSelect: false,
	    	pagination: true,
		    toolbar: '#toolbar_zzzkdsh',
		    fitColumns:true,
		    idField: 'pid'
		    ">
			<thead>
				<tr>
					<th data-options="field:'pid',checkbox:true" ></th>
					<th data-options="field:'realName'" >真实姓名</th>
		    		<th data-options="field:'userName'"  >用户账户</th>
					<th data-options="field:'department'" >部门</th>
				</tr>
			</thead>
	   	</table>
		<div id="toolbar_zzzkdsh">
     		<div style="margin:5px">
			 	<table class="beforeloanTable">
			 		<tr>
			 			<td class="label_right">真实姓名：</td>
			 			<td><input name="realName" class="easyui-textbox" id="realName"/></td>
			 			<td class="label_right">用户账户：</td>
			 			<td><input name="userName" class="easyui-textbox" id="userName"/></td>
			 			<td>
			 				<a href="#" class="easyui-linkbutton" iconCls="icon-search"  onclick="zzzkdshSearch()">查询</a>
							<a href="#" onclick="javascript:$('#searchFromZzzkdsh').form('reset');" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
			 			</td>
			 		</tr>
			 	</table>
       		</div> 
	   </div>
	</div>
		
<script type="text/javascript">
	$(function(){
		applyCommon.loadOrganizationInfo(newProjectId);
	})

	
	// 打开人员选取DIV
	function openZzzkdshDiv(){
		//$('#zzzkdsh_grid').datagrid('loadData',[]);
		//ajaxSearch('#zzzkdsh_grid','#searchFromZzzkdsh'); 	
	  	$('#dlg_zzzkdsh').dialog('open');
	}
	
	// 确认选取人员信息
	function selectUser(){
		var rows = $('#zzzkdsh_grid').datagrid('getSelections');
		if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择参与贷审会的人员","error");
			return;
		}
		// 获取选中的真实姓名  and 用户ID
	 	var userNames = "";
		var pids = "";
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
			// 判断是否是最后一个
			if(i != rows.length){
		  		userNames += row.realName+",";
		  		pids += row.pid+",";
			}else{
		  		userNames += row.realName;
		  		pids += row.pid;
			}
	  	}
		// 赋值
		$("#realNameText").textbox("setValue",userNames);
		$("#userPids").val(pids);
		// 关闭div 
		$('#dlg_zzzkdsh').dialog('close');
	}
	
	// 保存贷审会信息
	function zzzkdshSave(){
		$('#meetingProjectId').val(newProjectId);
		$('#conveneUserId').val(${currUser.pid});
		$("#zzzkdshForm").form('submit',{
			onSubmit : function() {return $(this).form('validate');},
	        success : function(result){
				var ret = eval("("+result+")");
				if(ret!='' || ret!=null){
					$('#meetingId').val(ret);
					$.messager.alert('操作提示','保存信息成功','info');
					// 更新状态为 1 ,流程提交时允许通过
					applyCommon.organization = 1;
				}else{
					$.messager.alert('操作提示','保存信息失败','error');	
				}
	        }
		});
	}
	
	function zzzkdshSearch(){
		$('#zzzkdsh_grid').datagrid('load',{
			realName:$('#realName').textbox('getValue'),
			userName:$('#userName').textbox('getValue')
		});
	}
</script>
