<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>

<script type="text/javascript">
	//新增
	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		parent.openNewTab("${basePath}elementLendController/editElementLend.action?status=1","要件借出申请");
	}
 
	function detailInfo(value, rowData, index) {
		var projectDom = "";
		if(rowData.xiFeeStatus == 0 ||  rowData.xiFeeStatus == 1 || rowData.xiFeeStatus ==3){
			projectDom += "<a href='javascript:void(0)' onclick='openUpload("+rowData.pid+",\""+rowData.loanId+"\",\""+rowData.projectId+"\",\""+rowData.projectName+"\")'><font color='blue'>上传凭证</font></a>";
		}
		return projectDom;
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
	
	function formatterState(val,row){
		if(val == 1){
			return "申请中";
		}else if(val == 2){
			return "审核中";
		}else if(val == 3){
			return "已审批";
		}else if(val == 4){
			return "已归还";
		}else if(val == 5){
			return "已签收";
		}
	}

	//获取结算清单
	function getXiFeeList(){
 		$.messager.confirm("操作提示","确认获取结算清单?",
			function(r) {
	 			if(r){
	 				$.ajax({
	 					type: "POST",
	 			        url: "<%=basePath%>projectPartnerController/getXiFeeList.action",
	 			        dataType: "json",
	 			        success: function(ret){
 			       			//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
							if(ret && ret.header["success"] ){
								$.messager.alert('操作提示',ret.header["msg"]);
								// 重新项目列表信息
			 					$("#grid").datagrid('load');
			 					// 清空所选择的行数据
			 					clearSelectRows("#grid");
							}else{
								$.messager.alert('操作提示',ret.header["msg"],'error');	
							}
	 			        }
	 				 });
	 			}
			});
	}
	//发送结算指令
	function sendXiFeeSettle(){
		
		var row = $('#grid').datagrid('getSelections');
		
		if (row.length == 0) {
			$.messager.alert("操作提示","请选择数据","error");
			return;
		} 
		
		var loanIds = "";
		for(var i = 0 ; i < row.length ; i++ ){
			if( !(row[i].xiFeeStatus ==1 ||  row[i].xiFeeStatus == 3 )){
				$.messager.alert("操作提示","请选择未发送或者未到帐的数据","error");
				return ;
			}
			
			if(row[i].xiFeeVoucherPath == null || row[i].xiFeeVoucherPath ==""){
				$.messager.alert("操作提示","项目编号："+row[i].projectNumber+"未上传凭证","error");
				return ;
			}
			loanIds += row[i].loanId+ ",";
		}
		
 		$.messager.confirm("操作提示","发送结算指令?",
			function(r) {
	 			if(r){
	 				$.ajax({
	 					type: "POST",
	 			        url: "<%=basePath%>projectPartnerController/sendXiFeeSettle.action",
	 			       data:{"loanIds":loanIds},
	 			        dataType: "json",
	 			        success: function(ret){
 			       			//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
							if(ret && ret.header["success"] ){
								$.messager.alert('操作提示',ret.header["msg"]);
								// 重新项目列表信息
			 					$("#grid").datagrid('load');
			 					// 清空所选择的行数据
			 					clearSelectRows("#grid");
							}else{
								$.messager.alert('操作提示',ret.header["msg"],'error');	
							}
	 			        }
	 				 });
	 			}
			});
	}
	//获取息费到款确认
	function batchXiFeeStatus(){
		
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 0) {
			$.messager.alert("操作提示","请选择数据","error");
			return;
		}
		
		var loanIds = "";
		for(var i = 0 ; i < row.length ; i++ ){
			if( !(row[i].xiFeeStatus ==2 ||  row[i].xiFeeStatus == 3 ||  row[i].xiFeeStatus == 5)){
				$.messager.alert("操作提示","请选择已发送或者未到账的数据","error");
				return ;
			}
			loanIds += row[i].loanId+ ",";
		}
		
 		$.messager.confirm("操作提示","确认获取息费到款确认?",
			function(r) {
	 			if(r){
	 				$.ajax({
	 					type: "POST",
	 			        url: "<%=basePath%>projectPartnerController/batchXiFeeStatus.action",
	 			       data:{"loanIds":loanIds},
	 			        dataType: "json",
	 			        success: function(ret){
 			       			//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
							if(ret && ret.header["success"] ){
								$.messager.alert('操作提示',ret.header["msg"]);
								// 重新项目列表信息
			 					$("#grid").datagrid('load');
			 					// 清空所选择的行数据
			 					clearSelectRows("#grid");
							}else{
								$.messager.alert('操作提示',ret.header["msg"],'error');	
							}
	 			        }
	 				 });
	 			}
			});
	}
	
	//打开上传页面
	function openUpload(pid,loanId,projectId,projectName){
		$('#addOrupdate').form('reset');
		$('#projectName1').text(projectName);
		$('#pid').val(pid);
		$('#loanId').val(loanId);
		$('#projectId').val(projectId);
		
		$('#addOrupdate').attr('action',"${basePath}projectPartnerController/uploadXiFeeDocument.action");
		$('#dialog_data').dialog('open').dialog('setTitle','上传凭证');
	}
	
	
	//上传凭证
	function addVoucher(){
		var file2 = $('#file2').textbox('getValue');
		var proId = $('#pid').val();
		
		if(proId=="" || null==proId){
			$.messager.alert("操作提示","请重新选择项目","error"); 
			return false;
		}

		if(file2=="" || null==file2){
			$.messager.alert("操作提示","请选择文件资料","error"); 
			return false;
		}
		$('#addOrupdate').form('submit',{
			success:function(data){
				var dataObj=eval("("+data+")");//转换为json对象
				if(dataObj && dataObj.header.success ){
					$.messager.alert('操作提示',dataObj.header.msg);
					// 刷新列表信息
 					$("#grid").datagrid('load');
 					// 清空所选择的行数据
 					clearSelectRows("#grid");
 					$('#dialog_data').dialog('close');
				}else{
					$.messager.alert('操作提示',dataObj.header.msg,'error');	
				}
			}
		})
	}
	
	/**格式化息费状态*/
	function formatterXiFeeStatus(val,row){
		if(val == 1 || val == 0){
			return "未发送";
		}else if(val == 2){
			return "己发送";
		}else if(val == 3){
			return "未到账";
		}else if(val == 4){
			return "已到账";
		} else if(val == 5){
			return "数据不存在";
		} 
	}
	
	//重置按钮
	function majaxReset(){
		$('#searchFrom').form('reset');
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="xiFeeList.action" method="post" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">项目编号：</td>
				<td>
					<input id="projectNumber" class="easyui-textbox" name="projectNumber" style="width:150px;" />
				</td>
				<td width="80" align="right" height="25">项目名称：</td>
				<td>
					<input id="projectName" class="easyui-textbox" name="projectName" style="width:150px;" />
				</td>
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
					<a href="#" onclick="majaxReset()" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">

			<a href="javascript:void(0)" class="easyui-linkbutton" 
				iconCls="icon-add" plain="true" onclick="getXiFeeList()">获取结算清单</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="sendXiFeeSettle()">发送结算指令</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="batchXiFeeStatus()">获取息费到款确认</a>
	</div>

</div>
<div class="perListDiv" style="height:100%;">
<table id="grid" title="息费结算" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'xiFeeList.action',
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
			<th data-options="field:'pid',checkbox:true"></th>
			<th data-options="field:'projectId',hidden:true"></th>
			<th data-options="field:'xiFeeVoucherPath',hidden:true"></th>
			<th data-options="field:'projectNumber',width:80,sortable:true" align="center" halign="center">项目编号</th>
			<th data-options="field:'projectName',width:80,sortable:true" align="center" halign="center">项目名称</th>
			<th data-options="field:'loanId',width:80,sortable:true" align="center" halign="center">审查编号</th>
			<th data-options="field:'loanAmount',width:80,sortable:true" align="center" halign="center">借款金额</th>
			<th data-options="field:'totalCost',width:80,sortable:true" align="center" halign="center">总成本</th>
			<th data-options="field:'premium',width:80,sortable:true" align="center" halign="center">保费</th>
			<th data-options="field:'xiFeeStatus',formatter:formatterXiFeeStatus,width:80,sortable:true" align="center" halign="center">状态</th>
			<th data-options="field:'xiFeeRequestTime',width:80,sortable:true" align="center" halign="center">请求时间</th>
			<th data-options="field:'xiFeeResultTime',width:80,sortable:true" align="center" halign="center">结果通知时间</th>
			<th data-options="field:'cz',formatter:detailInfo,width:80,sortable:true" align="center" halign="center">操作</th>
		</tr>
	</thead>
</table>
</div>
</div>


<!-- 文件上传开始 -->
<div id="dialog_data"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons">
		<form id="addOrupdate" name="addOrupdate" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td width="120" align="right" height="28">项目名称：</td>
					<td>
						<span id="projectName1"></span>
						<input type="hidden" name="projectId" id="projectId">
						<input type="hidden" name="pid" id="pid">
						<input type="hidden" name="loanId" id="loanId">
					</td>
				</tr>
				<tr> 
					<td align="right" width="120" height="28"><font color="red">*</font>文件路径：</td>
					<td>
						<input class="easyui-filebox" class="download"  data-options="buttonText:'选择文件',missingMessage:'选择一个文件'" id="file2" name="file2" style="width:240px;" required="true"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="data-buttons">
		<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addVoucher()" >保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_data').dialog('close')">取消</a>
	</div>
</div>
<!-- 文件上传结束 -->


</body>
