<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript">
	var BASE_PATH = "${basePath}";
</script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/loanWorkflow.js"></script>

</style>
<script type="text/javascript">
	function czfilter(value,row,index){  
		 if(row.requestStatusVal=='申请中'){			 
			 return "<a href='Javascript:void(0);'  onclick='editProject("+row.projectId+","+row.oldProjectId+")' ><font color='blue'>编辑</font></a> | <a href='Javascript:void(0);'  onclick='delProject("+row.projectId+")' ><font color='blue'>删除</font></a>";
		 }else{
			 return "<a><font color='gray'>编辑</font></a> | <a><font color='gray'>删除</font></a>";
		 }
	}
	function delectProject(data){
			var rows = $('#limitGrid').datagrid('getChecked');
			for(var i =0;i<rows.length;i++){
				if(rows[i].requestStatusVal!='申请中'){	
					$.messager.alert("操作提示","只有状态为申请中的数据才能删除 ","success"); 
					return;
				}
			}
			var pidArray = getPidArray(rows);
			if(confirm("你确定要删除吗?")){
			$.ajax({
				url:"<%=basePath%>creditsController/delLoanProjectUrl.action",
				type:"post",
				data:{"pidArray":pidArray},
				dataType:"json",
				success:function(response){
					if(response.delstatus=="delsuc"){
						$.messager.alert("操作提示","删除成功","success"); 
					}else{
						$.messager.alert("操作提示","删除失败","success"); 
					}
					$('#limitGrid').datagrid('reload');
					$('#limitGrid').datagrid('clearChecked');
				}
			})
		}
	}
	
	function delProject(pid){
		if (confirm("是否确认删除?")) {
			$.ajax({
				type: "POST",
		        url: "<%=basePath%>creditsController/delLoanProjectUrl.action",
		        data:{"pidArray":pid},
		        dataType: "json",
		        success: function(response){
		        	if(response.delstatus=="delsuc"){
						$.messager.alert("操作提示","删除成功","success"); 
					}else{
						$.messager.alert("操作提示","删除失败","success"); 
					}
					$('#limitGrid').datagrid('reload');
					$('#limitGrid').datagrid('clearChecked');
		        }
			});
		}
	}

	function getPidArray(row){
		var pidArray = "";
		for(var i=0; i<row.length; i++){
			if(i!=row.length-1){
				pidArray =pidArray+row[i].projectId+",";
			}else if(i==0){
				pidArray =pidArray+row[i].projectId;
			}else if(i==row.length-1){
				pidArray =pidArray+row[i].projectId;
			} 
		}
		return pidArray;
	}
	
	
	function editProject(newProjectId,oldProjectId){
		var url="<%=basePath%>creditsCustomerInfoController/toAddCredit.action?projectId="+oldProjectId+"&newProjectId="+newProjectId+"&biaoZhi=2";
		var datas = getTaskVoByWPDefKeyAndRefId("creditWithdrawalRequestProcess",newProjectId);
		if(datas){
			url+="&"+datas;
		}
		parent.openNewTab(url,"编辑额度提取");
	}
	$(function() {
		setCombobox("acct_type", "CUS_TYPE", "");
		setCombobox("eco_trade", "ECO_TRADE", "");

		ajaxSearch();
	});
	
	function showTrade(newValue,oldValue){
		if(newValue.pid==2){
			$(".eco_trade_span").show();
		}else{
			$(".eco_trade_span").hide();
		}
	}
	
	//查询 
	function ajaxSearch(){
		$('#limitGrid').datagrid('load',{
			projectName:$.trim($('#projectName').textbox('getValue')),
			projectNumber:$.trim($('#projectNumber').textbox('getValue')),
			cusName:$.trim($('#cusName').textbox('getValue')),
			acctType:$('#acct_type').combobox('getValue'),
			requestStatus:$('#requestStatus').combobox('getValue'),
			ecoTrade:$('#eco_trade').combobox('getValue'),
			requestStartDt:$('#requestStartDt').datebox('getValue'),
			requestEndDt:$('#requestEndDt').datebox('getValue')
		});
	};
	
	
	function resetss(){
		$(".eco_trade_span").hide();
		$('#limitForm').form('reset');
	}
	
	function cusTypefilter(value,row,index){
		if(value==1){
			return "个人";
		}else{
			return "企业";
		}
	}
	
	
	function pNameFilter(value,row,index){
		var result="<a href='javascript:void(0)' onclick = 'searchProjectDeil("+JSON.stringify(row)+")'> <font color='blue'>"+row.projectName+"</font></a>"
		return result;
	}
	
	
	function searchProjectDeil(row){
		//debugger;
		loadHistoryApprover(row.projectId,"creditWithdrawalRequestProcess");
		var url = "<%=basePath%>creditsCustomerInfoController/toAddCredit.action?projectId="+row.oldProjectId+"&newProjectId="+row.projectId+"&biaoZhi=3"
		parent.openNewTab(url,"项目详情",true);
	}
	
	function loadHistoryApprover(projectId,WORKFLOW_ID){
		$.ajax({
			type: "GET",
		    url: BASE_PATH+"workflowController/findWorkflowProject.action",
		    data:{"projectId":projectId,"processDefinitionKey":WORKFLOW_ID},
		    dataType: "json",
		    async: false,
		    success: function(data){
		    	if(data!=null){
		    		workflowTaskDefKey = data.workflowTaskDefKey;
		    		hideOrShow(workflowTaskDefKey);
		    		var resData = {workflowInstanceId: data.workflowInstanceId};
		    		loadProcessLoggingGrid(resData);
		    	}
		    }
		});
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<!-- 查询条件 -->
	<div style="padding: 5px">
		<form action="" id="limitForm">
		<table class="beforeloanTable">
			<tr>
				<td class="label_right">项目名称：</td>
				<td><input name="projectName" class="easyui-textbox" style="width: 180px;" id="projectName"/></td>
				<td class="label_right">项目编号：</td>
				<td>
					 <input name="projectNumber" class="easyui-textbox" style="width: 220px;" id="projectNumber"/>
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td class="label_right">客户名称：</td>
				<td><input name="cusName" class="easyui-textbox" style="width: 180px;" id="cusName"/></td>
				<td class="label_right">客户类别：</td>
				<td><select name="acctType" id="acct_type" class="easyui-combobox" style="width: 220px;"  data-options="onSelect:showTrade"></select></td>
				<td class="label_right eco_trade_span" style="display: none;">经济行业类别：</td>
				<td class="eco_trade_span" style="display: none;">
					<select
					name="ecoTrade" id="eco_trade" class="easyui-combobox"
					style="width: 120px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label_right">申请状态：</td>
				<td>
					<select name="requestStatus" id="requestStatus"
						class="easyui-combobox" style="width: 180px;">
						<option value="0">请选择</option>
						<option value="1">申请中</option>
						<option value="2">审核中</option>
						<option value="3">已放款</option>
						<option value="4">已归档</option>
						<option value="7">已否决</option>
					</select>
				</td>
				<td class="label_right">申请时间：</td>
				<td>
					<input name="requestStartDt" class="easyui-datebox" id="requestStartDt"
					style="width: 80px;" /> <span>~</span> <input name="requestEndDt" id="requestEndDt"
					class="easyui-datebox" style="width: 80px;" />
				</td>						
				<td colspan="2">
					<input type="hidden" id="rows" name="rows">
					<input type="hidden" id="page" name="page" value='1'>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 20px;" onclick="ajaxSearch()">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" style="margin-left: 20px;" onclick="resetss()">重置</a>
				</td>
			</tr>
		</table>
		</form>
	</div>

	<div style="padding-bottom: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="delectProject()">删除</a>
	</div>

</div>
<div class="loanProjectListDiv" style="height:100%">
<table id="limitGrid" title="额度提款申请管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: '${basePath}creditsController/listLoanProjectUrl.action',
	    method: 'post',
	    rownumbers: true,
	    singleSelect: false,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'projectId',
	    fitColumns:true">
	<!-- 表头 -->
	<thead>
		<tr>
			<th data-options="field:'projectId',checkbox:true"></th>
			<th data-options="field:'oldProjectId',hidden:true"></th>
			<th data-options="field:'projectName',width:130,formatter:pNameFilter">项目名称</th>
			<th data-options="field:'projectNumber',width:100">项目编号</th>
			<th data-options="field:'requestStatusVal',width:100">提款申请状态</th>
			<th data-options="field:'requestDttm',width:100">提取申请时间</th>
			<th data-options="field:'acctType',width:100,formatter:cusTypefilter">客户类型</th>
			<th data-options="field:'creditAmt',width:100,align:'right',formatter:formatMoney">提取金额</th>
			<th data-options="field:'pmUser',width:100">项目经理</th>
			<th data-options="field:'planOutLoanDT',width:100,formatter:convertDate">还款开始日期</th>
			<th data-options="field:'planRepayLoanDT',width:100,formatter:convertDate">还款结束日期</th>
			<th data-options="field:'yy',width:100,formatter:czfilter">操作</th>
		</tr>
	</thead>
</table>
</div>
</div>
<!-- 工作流程 -->	
		<div class="pt10" style="border-width:2px;border-top:none;">
			<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
				<div class="loanworkflowWrapDiv"  style="padding:5px">
					<%@ include file="../common/loanWorkflow.jsp"%>
					<%@ include file="../common/task_hi_list.jsp"%>
				</div>
			</div>
		</div>
</div>
</body>