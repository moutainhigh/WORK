<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common.jsp"%>

<div id="menuDiv" style="width:100%;float: left;">

	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
 		<table>
 			<tr>
 				<td class="label_right">项目编号：</td>
 				<td><input class="easyui-textbox" id="projectNumber" style="width:220px" name = "projectNumber"></td>
 				<td class="label_right">项目名称：</td>
 				<td><input class="easyui-textbox" id="projectName" style="width:220px" name = "projectName"> </td>
 				<td class="label_right">客户名称：</td>
 				<td><input class="easyui-textbox" id="cusName" name = "cusName"></td>
 			</tr>
 			<tr>
 				<td class="label_right">客户类别：</td>
 				<td>
 					 <input id="cusType" class="easyui-combobox" style="width:220px" name="cusType" editable="false"  
		            data-options="
		            valueField:'pid',
		            textField:'lookupVal',
		            method:'get',
		            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" />
 				</td>
 				<td class="label_right">经济行业：</td>
 				<td>
 				<input id="tradeType" class="easyui-combobox"  style="width:220px" name="tradeType" editable="false" 
			            data-options="
			            valueField:'pid',
			            textField:'lookupVal',
			            method:'get',
			            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
			   </td>
 				<td class="label_right">  已逾期了：</td>
 				<td>
 					<select id="overdueDate" name="overdueDate"style="width:152px" class="easyui-combobox"  editable="false">
			        	<option value="">请选择</option>
						<option value="13">已逾期1-3天</option>
						<option value="47">已逾期4-7天</option>
						<option value="815">已逾期8-15天</option>
						<option value="1630">已逾期16-30天</option>
						<option value="3100">已逾期31-100天</option>
						<option value="1">所有已逾期</option>
			  </select> 
 				</td>
 			</tr>
 			<tr>
 				<td class="label_right"> 即将到期：</td>
 				<td>
 					<input id="startDate" class="easyui-datebox" name="startDate" editable="false" />
			  		 <span>~</span> <input id="endDate" class="easyui-datebox" name="endDate" editable="false" />	
 				</td>
 				<td class="label_right">申请时间：</td>
 				<td>
 					 <input id="startApplyDate" class="easyui-datebox" name=""startApplyDate"" editable="false" />
			  		 <span>~</span> <input id="endApplyDate" class="easyui-datebox" name="endApplyDate" editable="false" />
			  	</td>
 				<td colspan="2"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="dosubmit()">查询</a></td>
 				
 			</tr>
 		</table> 		
		
	</div>

	<table id="grid" title=" 贷款展期管理" class="easyui-datagrid" 
	style="height:auto;width: auto;"
	data-options="
	    url: '/BMS/loanExtensionController/extentionList.action',
	    method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
        singleSelect: true,
		selectOnCheck:false,
		checkOnSelect: false
	    ">
		<thead>
		<tr>
			<th data-options="field:'pid',hidden:true,width:100">菜单Id</th>
			<th data-options="field:'projectNumber',width:100">项目编号</th>
		    <th data-options="field:'projectName',width:100">项目名字</th>
		    <th data-options="field:'extensionReqStatus',width:100,formatter:reFilter">贷款申请状态</th>
		    <th data-options="field:'extensionDt',width:100">申请时间</th>
		    <th data-options="field:'customerType',width:100">客户类别</th>
		    <th data-options="field:'loanAmt',width:100">贷款金额</th>
		    <th data-options="field:'projectManagerName',width:100">项目经理</th>
		    <th data-options="field:'loanStarDate',width:100">贷款开始日期</th>
		    <th data-options="field:'loanEndDate',width:100">贷款结束日期</th>
		    <th data-options="field:'cz',width:100,formatter:czFilter">操作</th>
		</tr>
		</thead>
	</table>
</div>
 
 

<script type="text/javascript">

	function dosubmit(){
		var projectNumber = $("#projectNumber").val();
		var projectName = $("#projectName").val();
		var cusName = $("#cusName").val();
		var cusType = $("#cusType").combobox('getValue');
		var tradeType = $("#tradeType").combobox('getValue');
		var startDate = $("#startDate").combobox('getValue');
		var endDate = $("#endDate").combobox('getValue');
		var startApplyDate = $("#startApplyDate").combobox('getValue');
		var endApplyDate = $("#endApplyDate").combobox('getValue');
		var overdueDate = $("#overdueDate").combobox('getValue');
		alert(projectNumber);
		if(projectNumber!='' || projectName!='' || cusName!='' || cusType!=''
		   ||(startDate!='' && endDate !='') || (startApplyDate!='' && endApplyDate!='') 
		   || overdueDate!='' ){
			$("#grid").datagrid({
			    url: 'serchExtensionList.action',
			    method: 'POST',
			    rownumbers: true,
			    queryParams:{'projectNumber':projectNumber,'projectName':projectName,
			    			 'cusName':cusName,'cusType':cusType,
			    			 'tradeType':tradeType,'startDate':startDate,
			    			 'endDate':endDate,'startApplyDate':startApplyDate,
			    			 'endApplyDate':endApplyDate,"overdueDate":overdueDate},
			    pagination: true,
			    toolbar: '#toolbar',
			    idField: 'pid',
			    fitColumns:true,
			    singleSelect: true,
				selectOnCheck:false,
				checkOnSelect: false,
			    columns: [[
			        {field: 'pid',hidden:true,width:10},
			        {field: 'projectNumber', title: '项目编号', width: 100},
			        {field: 'projectName', title: '项目名字', width: 100},
			        {field: 'extensionReqStatus', title: '贷款申请状态', width: 180},
			        {field: 'extensionDt', title: '申请时间', width: 100},
			        {field: 'customerType', title: '客户类别', width: 100},
			        {field: 'loanAmt', title: '贷款金额', width: 100},
			        {field: 'projectManagerName', title: '项目经理', width: 100},
			        {field: 'loanStarDate', title: '贷款开始日期', width: 100},
			        {field: 'loanEndDate', title: '贷款结束日期', width: 100},
			        {field: 'cz', title: '操作', width: 100,formatter:czFilter},
			        ]]
			});
			return;
		}
		$("#grid").datagrid({
		    url: 'extentionList.action',
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true,
		    singleSelect: true,
			selectOnCheck:false,
			checkOnSelect: false,
		    columns: [[
		        {field: 'pid',hidden:true,width:10},
		        {field: 'projectNumber', title: '项目编号', width: 100},
		        {field: 'projectName', title: '项目名字', width: 100},
		        {field: 'extensionReqStatus', title: '贷款申请状态', width: 180},
		        {field: 'extensionDt', title: '申请时间', width: 100},
		        {field: 'customerType', title: '客户类别', width: 100},
		        {field: 'loanAmt', title: '贷款金额', width: 100},
		        {field: 'projectManagerName', title: '项目经理', width: 100},
		        {field: 'loanStarDate', title: '贷款开始日期', width: 100},
		        {field: 'loanEndDate', title: '贷款结束日期', width: 100},
		        {field: 'cz', title: '操作', width: 100,formatter:czFilter},
		        ]]
		});
	}
	
	function reFilter(row,value,index){
		if(value==1){
			return "申请中";
		}else if(value==2){
			return "办理中";
		}else if(value==3){
			return "审核中";
		}
	}
	
	function czFilter(){
		var dom1 = '<a href="#"><font color="blue">贷款详细 </font>|</a>';
		var dom2 = '<a href="#"><font color="blue"> 提前还款办理</font></a>'
		return dom1+dom2;
	}
</script>