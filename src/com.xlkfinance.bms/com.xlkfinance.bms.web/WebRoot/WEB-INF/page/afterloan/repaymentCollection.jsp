<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript">

//查询  
function repayList() {
	// 获取用户输入的逾期日期
	var beginDay = $("#overdueStartDay").textbox("getValue");
	var endDay = $("#overdueEndDay").textbox("getValue");
	
	if (parseInt(beginDay) > parseInt(endDay)){
 		$.messager.alert("操作提示","已逾期日输入有误!","error");
 		return;
	}
	ajaxSearchPage('#conlletGrid','#searcFrom');
	
}

$(document).ready(function(){
	
	$("#ecoTradeId1").hide();
	$("#ecoTradeId2").hide();
	$('#acctType').combobox({
		onSelect: function(row){
			var opts = $(this).combobox('options');
			if(row[opts.textField]=='企业')
			{
				$("#ecoTradeId1").show();
				$("#ecoTradeId2").show();
			}else{
				$("#ecoTradeId1").hide();
				$("#ecoTradeId2").hide();
			}
 
		}
	});
})

//绑定赋值  列表中文本框
 $(function(){
 	 $('#conlletGrid').datagrid({
 		onLoadSuccess:function(){
 			var t=$('#conlletGrid').datagrid('getData').rows.length;
			for(var i=0;i<t;i++){
				if($('#conlletGrid').datagrid('getData').rows[i].userId!=0){
					$('#conlletGrid').datagrid('beginEdit',i);				
				}
			}
 		},
 		onSelect:function(rowIndex, rowData){
 				$('#conlletGrid').datagrid('beginEdit',rowIndex);
 	 			isEdit = true;
 		},
 		unselectRow:function(){
 			$('#conlletGrid').datagrid('endEdit',rowIndex);
	 			isEdit = false;
 		},
 		onBeforeEdit:function(index,row){  
 	        row.editing = true;  
 	        $('#conlletGrid').datagrid('refreshRow', index);  
 	    },  
 	    onAfterEdit:function(index,row){  
 	        row.editing = false;  
 	        $('#conlletGrid').datagrid('refreshRow', index);  
 	    },  
 	    onCancelEdit:function(index,row){  
 	        row.editing = false;  
 	        $('#conlletGrid').datagrid('refreshRow', index);  
 	    }
 	 });
 	 
 });
 
  
  
//保存催收专员
	function saveTask(){
		 var rows = $('#conlletGrid').datagrid('getChecked');
		 if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		 }
		 var pid = new Array();
		 var realName = new Array();
		 var planDt = new Array();
		 var reminderId=new Array();
		 for(var i=0;i<rows.length;i++){
			var temp;
			var row = rows[i];
			var index= $('#conlletGrid').datagrid('getRowIndex',rows[i]);
			var realNames=$('#conlletGrid').datagrid('getEditor', {index:index,field:'userIdStr'}).target.combobox("getValue");
			
			if(realNames=="" ||realNames==undefined||realNames==0){
				$.messager.alert("操作提示","请选择填写计划催收员","error");
				return;
			}
			var planOutLoanDts=$('#conlletGrid').datagrid('getEditor', {index:index,field:'planOutLoanDt'}).target.datebox("getValue");
			if(planOutLoanDts==""||planOutLoanDts==undefined){
				$.messager.alert("操作提示","请选择填写计划催收日期","error");
				return;
			} 
			
			pid.push(row.pid);
			reminderId.push(row.reminderId);
			realName.push(realNames);
	  		planDt.push(planOutLoanDts);
		 }
		 var isMail="0";
		 if($(".is_mail:checked").val()=="1"){
			 isMail=1;
		 }
		 var isSms="0";
		 if($(".is_sms:checked").val()=="1"){
			 isSms="1";
		 }
		$.ajax({
			type: "POST",
	        url: "conllectionTaskSave.action",
	        data:{"pid":pid,"realName":realName,"planDt":planDt,"isMail":isMail,"isSms":isSms,"reminderId":reminderId},
	        dataType: "text",
	        success: function(data){
	        	$('#addUserToRolegrid').datagrid('reload');
	        	$('#conlletGrid').datagrid('reload');
	        	$.messager.alert("系统提示","添加成功","success"); 
	        }
		});
		$('#conlletGrid').datagrid("clearChecked");
	}
	 

	//项目名称format
	var formatProjectName=function (value, rowData, index) {
		var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.pid+",\""+rowData.projectId+"\")' href='#'>" +
			"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
		return btn;   
	}
	
	function openProjectDetail(projectId,projectName){
		var url="${basePath}beforeLoanController/addNavigation.action?projectId="
				+projectId+"&param='refId':'"+projectId+"','projectName':'"+projectName+"'";
		parent.openNewTab(url,"查看项目详情",true);
	}
	
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="getAssignmentDistribution.action" method="post"
				id="searcFrom" name="searcFrom">
				<!-- 查询条件 -->
				<div style="padding: 5px">
					<table class="beforeloanTable">
						<tr>
							<td class="label_right">项目名称：</td>
							<td><input type="text" name="projectName" id="projectName"
								class="easyui-textbox" style="width: 220px;" /></td>
							<td class="label_right">项目编号：</td>
							<td><input type="text" name="projectNum" id="projectNum"
								class="easyui-textbox" style="width: 220px;" /></td>
							<td class="label_right">催收业务员：</td>
							<td><input name="collectionUser" class="easyui-combobox"
								data-options="valueField:'pid',textField:'realName',url:'${basePath }sysUserController/getUsersByRoleCode.action?roleCode=COLLETIONER'"
								id="collectionUser" style="width: 220px;" /></td>
						</tr>
						<tr>
							<td class="label_right">客户名称：</td>
							<td><input type="text" name="cusName" id="cusName"
								class="easyui-textbox" style="width: 220px;" /></td>
							<td class="label_right">客户类别：</td>
							<td><input name="acctType" id="acctType"
								style="width: 220px;" class="easyui-combobox" editable="false"
								panelHeight="auto"
								data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" /></td>
							<td class="label_right" id="ecoTradeId1">经济行业类别：</td>
							<td id="ecoTradeId2"><input name="ecoTrade" id="ecoTrade"
								class="easyui-combobox" editable="false" panelHeight="auto"
								data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /></td>
						</tr>
						<tr>
							<td class="label_right">贷款到期时间：</td>
							<td><input type="text" id="expireStartDt"
								name="expireStartDt" class="easyui-datebox"
								style="width: 100px;" editable="false" /> <span>~</span> <input
								name="expireEndt" id="expireEndt" type="text"
								class="easyui-datebox" style="width: 100px;" editable="false" />
							</td>
							<!-- 
					<td>
						<input class="easyui-combobox" name="ecoTrade" id="ecoTrade" style="width: 160px;" editable="false" 
				            data-options="
				            valueField:'pid',
				            textField:'lookupVal',
				            method:'get',
				            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'"
						/>
					</td>
					 -->
							<td class="label_right">已逾期间(日)：</td>
							<td><input id="overdueStartDay" name="overdueStartDay"
								class="easyui-textbox" style="width: 100px;" /> <span>~</span> <input
								id="overdueEndDay" name="overdueEndDay" class="easyui-textbox"
								style="width: 100px;" /></td>
							<td class="label_right">贷款申请时间：</td>
							<td><input name="requestStartDt" id="requestStartDt"
								type="text" class="easyui-datebox" style="width: 100px;"
								editable="false" /> <span>~</span> <input name="requestEndDT"
								id="requestEndDT" type="text" class="easyui-datebox"
								style="width: 100px;" editable="false" /></td>
						</tr>
						<tr>
							<td class="label_right">催收分配状态：</td>
							<td><select name="reminderStatus" id="reminderStatus"
								class="easyui-combobox" style="width: 100px;" editable="false">
									<option value="-1">--请选择--</option>
									<option value="1">已分配</option>
									<option value="2">未分配</option></td>
							<td class="label_right">计划催收时间：</td>
							<td><input name="planStartDt" id="planStartDt" type="text"
								class="easyui-datebox" style="width: 100px;" editable="false" />
								<span>~</span> <input name="planEndDT" id="planEndDT"
								type="text" class="easyui-datebox" style="width: 100px;"
								editable="false" /></td>
							<td colspan="2">&nbsp;&nbsp; <a href="javascript:void(0);"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="repayList()">查询</a> <a href="#"
								class="easyui-linkbutton" iconCls="icon-clear"
								onclick="javascript:$('#searcFrom').form('reset');">重置</a>
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td><a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-add" plain="true" onclick="saveTask();">保存催收任务</a>
							</td>
							<td width="100">任务提醒方式：</td>
							<td width="100"><input type="checkbox"
								class=" method_tip is_sms" name="isSms" value="1">&nbsp;短信提醒</td>
							<td><input type="checkbox" name="isMail"
								class="method_tip is_mail" value="1">&nbsp;邮箱提醒</td>
						</tr>
					</table>
				</div>
			</form>

			<!-- 操作按钮 -->
		</div>
		<div class="conllectTaskDistributinDiv" style="height:600px">
			<table id="conlletGrid" title="催收专员任务分配查询" class="easyui-datagrid"
				style="height: 100%; width: auto;"
				data-options="
			    url: 'getAssignmentDistribution.action',
			    method: 'POST',
			    rownumbers: true,
			    pagination: true,
			    toolbar: '#toolbar',
			    idField: 'pid',
			    idField: 'pid',
			    width: '100%',
			 	height: '100%',  
			 	striped: true, 
			 	loadMsg: '数据加载中请稍后……',
			    fitColumns:true,
				selectOnCheck: false,
				checkOnSelect: false
			    ">
				<!-- 表头 -->
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true,width:'50'"></th>
						<th
							data-options="field:'projectName',width:'100',formatter:formatProjectName"
							>项目名称</th>
						<th data-options="field:'projectId',width:'100'">项目编号</th>
						<th data-options="field:'acctType',width:'100'" >客户类别</th>
						<th data-options="field:'appDate',width:'100'" >申请时间</th>
						<th data-options="field:'telephone',width:'100'" >联系电话</th>
						<th data-options="field:'creditAmt',width:'80',formatter:formatMoney" >贷款金额</th>
						<th data-options="field:'balanceAmt',width:'100',formatter:formatMoney" >到期未收金额</th>
						<th	data-options="field:'userIdStr',width:'80',editor:{type:'combobox',options:{valueField:'pid',textField:'realName',url:'${basePath }sysUserController/getUsersByRoleCode.action?roleCode=COLLETIONER'}}"
							>计划催收业务员</th>
						<th	data-options="field:'planOutLoanDt',width:'100',editor:{type:'datebox'}">计划催收日期</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>