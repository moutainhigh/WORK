<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="projectList.action" method="post" id="searchFrom">
				<!-- 查询条件 -->
				<div style="padding: 5px">
				<table>
					<tr>
						<td width="100" align="right" height="25">征信交易编号：</td>
						<td>
							<input  class="easyui-textbox" name="creditDealId" style="width:150px;" />
						</td>
						<td width="80" align="right" height="25">客户名称：</td>
						<td>
							<input  class="easyui-textbox" name="chinaName" style="width:150px;" />
						</td>
						<input type="hidden" id="rows" name="rows">
						<input type="hidden" id="page" name="page" value='1'>
						<td>
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
							<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
						</td>
					</tr>
				</table>
				</div>
			</form>
		
			<div style="padding-bottom: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" 
						iconCls="icon-add" plain="true" onclick="searchCusCredit()">查询征信</a>
			</div>
		</div>
		
		<!-- 列表 -->
		<div class="perListDiv" style="height:100%;">
			<table id="grid" title="客户征信列表" class="easyui-datagrid"
				style="height:100%; width: auto;"
				data-options="
				    url: '<%=basePath%>cusCreditController/cusCreditList.action',
				    method: 'post',
				    rownumbers: true,
				    pagination: true,
				    singleSelect: false,
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
						<th data-options="field:'pid',checkbox:true" align="center" halign="center"></th>
						<th data-options="field:'acctId',hidden:true" ></th>
						<th data-options="field:'partnerNo',formatter:formatPartnerNo,width:80,sortable:true" align="center" halign="center">征信平台</th>
						<th data-options="field:'businessType',formatter:formatBuSinessType" >交易类型</th>
						<th data-options="field:'creditDealId',width:80,sortable:true" align="center" halign="center">征信交易编号</th>
						<th data-options="field:'chinaName',width:80,sortable:true" align="center" halign="center">客户名称</th>
						<th data-options="field:'certType',formatter:formatCertType,width:80,sortable:true" align="center" halign="center">证件类型</th>
						<th data-options="field:'certNumber',width:80,sortable:true" align="center" halign="center">证件号码</th>
						
						<th data-options="field:'overdue',width:80,sortable:true" align="center" halign="center">当前逾期否决码</th>
						<th data-options="field:'result1',width:80,sortable:true" align="center" halign="center">M4否决码</th>
						<th data-options="field:'result2',width:80,sortable:true" align="center" halign="center">3个月综合否决码</th>
						<th data-options="field:'result3',width:80,sortable:true" align="center" halign="center">异常负债否决码</th>
						<th data-options="field:'result4',width:80,sortable:true" align="center" halign="center">对外担保负债否决码</th>
						<th data-options="field:'result5',width:80,sortable:true" align="center" halign="center">失信信息否决码</th>
						<th data-options="field:'result6',width:80,sortable:true" align="center" halign="center">查询记录否决码</th>
						<th data-options="field:'createTime',width:80,sortable:true" align="center" halign="center">创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
	</div>
	
	<script type="text/javascript">
		//格式化征信平台
		function formatPartnerNo(val,row){
			if(val == 'nyyh'){
				return "南粤银行";
			} 
		}
		//格式化证件类型
		function formatCertType(val,row){
			if(val == '13088'){
				return "身份证";
			}else if(val == '13089'){
				return "护照";
			} else if(val == '13090'){
				return "回乡证（仅HK）";
			} else if(val == '13091'){
				return "军官证";
			} else if(val == '13092'){
				return "驾照";
			} else if(val == '13093'){
				return "台胞证";
			} else if(val == '13094'){
				return "其它";
			} else if(val == '13820'){
				return "营业执照";
			}else {
				return "";
			} 
		}
		
		//格式化交易类型
		function formatBuSinessType(val,row){
			if(val == '1'){
				return "交易";
			}else if(val == '2'){
				return "非交易";
			}
		}
		
		
		//查询客户征信（后台添加）
		function searchCusCredit(){
			
			var row = $('#grid').datagrid('getSelections');
			if(row.length != 1){
				$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
				return;
			} 
			
			
			var acctId = row[0].acctId;
			var projectId = 0;
			var businessType = row[0].businessType;
			
			$.messager.confirm("操作提示","确定查询客户征信?",function(r) {
	 			if(r){
	 				$.ajax({
	 					type: "POST",
	 			        url: "<%=basePath%>cusCreditController/searchCusCredit.action",
	 			        data:{"acctId":acctId,"projectId":projectId,"businessType":businessType},
	 			        dataType: "json",
	 			        timeout:100000,
	 			        beforeSend:function (){
	 			        	MaskUtil.mask("查询中,请耐心等候......");  
	 			        },
	 			        success: function(result){
	 			        	//MaskUtil.unmask();
	 			        	$.messager.alert('操作提示',result.header.msg);
	 					},error : function(result){
	 						//MaskUtil.unmask();
	 						$.messager.alert('操作提示',"查询失败！",'error');	
	 					},
	 					complete : function(){
	 						MaskUtil.unmask();
	 					}
	 				 });
	 			}
			});
		}
		
		
	</script>
</body>

 

