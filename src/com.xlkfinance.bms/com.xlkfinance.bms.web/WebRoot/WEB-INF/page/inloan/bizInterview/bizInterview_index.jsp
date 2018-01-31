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
<script type="text/javascript">

var userId ='<shiro:principal property="pid"/>';

var InterviewSearch = function(){
	$("")
}

//指派弹窗
var toAsign = function(){
	var row = $('#interviewGrid').datagrid('getSelections');
 	if (row.length == 1) {
 		$("#interview_asign_form").form("reset");
 		//已经开始面签，公证，抵押，领他证的不能指派
 		var count = 0;
		var id;
 		if(row[0].interviewStatus == "1"){
 			$("input#interviewer").combobox("disable");
 			count ++;
 		}else{
 			$("input#interviewer").combobox("enable");
 		}
 		if(row[0].notarizationStatus  == "1"){
 			$("input#notarizator").combobox("disable");
 			count ++;
 		}else{
 			$("input#notarizator").combobox("enable");
 		}
 		if(row[0].mortgageStatus  == "1"){
 			$("input#mortgator").combobox("disable");
 			count ++;
 		}else{
 			$("input#mortgator").combobox("enable");
 		}
 		$("input#interviewer").combobox("setValue",row[0].interviewId || "");
 		$("input#notarizator").combobox("setValue",row[0].notarizationId || "");
 		$("input#mortgator").combobox("setValue",row[0].mortgageUser || "");
		if(count == 3){
			$("#saveAsign").linkbutton('disable');
		}else{
			$("#saveAsign").linkbutton('enable');
		}
 		$("#dialog_interview_asign").dialog({
 			 title: '指派',
 			 width: 300,
 	         modal: true,
 	         top: 200,
 		});
 		
 		$("#dialog_interview_asign").dialog("open").dialog("center");
 		
		$("#interview_asign_form").find("input[name='pid']").val(row[0].pid);
		$("#interview_asign_form").find("input[name='projectId']").val(row[0].projectId);
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
	
}

var saveAsign = function(){
	
	$('#interview_asign_form').form('submit', {
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$("#dialog_interview_asign").dialog("close");
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);
				
				$("#interviewGrid").datagrid("reload");
				
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	});
	
}

//面签弹窗
var toInterview = function(){
	var row = $('#interviewGrid').datagrid('getSelections');
 	if (row.length == 1) {
 		//已经开始面签，公证，抵押，领他证的不能指派
 		$("#interview_inter_form").form("reset");
 		if(userId != row[0].interviewId){
 			$.messager.alert("操作提示","非面签只有指派的面签人员才能编辑","error");
 			return;
 		}
 		if(!row[0].interviewer){
 			$.messager.alert("操作提示","未指派面签人员！","error");
 			return;
 		}
 		if(row[0].interviewStatus  == "1" ){
 			$.messager.alert("操作提示","面签已完成，不能再编辑","error");
 			return;
 		}
 		
 		$("#interview_inter_form").find("input[name='pid']").val(row[0].pid);
 		$("#interview_inter_form").find("input[name='projectId']").val(row[0].projectId);
 		$("#interview_inter_form").find("input[id='interviewer2']").textbox("setValue",row[0].interviewer);
 		
 		$("#interview_inter_form").find("input.interviewTime").datebox('setValue',formatterDate(new Date()));
 		
 		$("#dialog_interview_info").dialog({
 			 title: '面签',
 			 width: 750,
 	        modal: true,
 	        top: 200,
 		});
 		
 		$("#dialog_interview_info").dialog("open").dialog("center");
		//根据项目Id带出银行卡信息
 		$.ajax({
			type: "POST",
	        url: "<%=basePath%>bizInterviewController/getCusCardInfoByProjectId.action",
	        data:{"projectId":row[0].projectId},
	        dataType: "json",
	        success: function(result){
	        	var ret = result;
				if(ret && ret.header["success"]){
					var data = ret.body.data;
					for(op in data){
						if(op == "pid")$("#interview_inter_form").find("input[name='cusCardInfo.pid']").val(data[op]);
						if($("#interview_inter_form").find("#"+op).length ==1){
							$("#interview_inter_form").find("#"+op).textbox("setValue",data[op]);
						}
					}										
				}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');
				}
	        }
		});
 		
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
	
}

/**
 * 签约操作
 */
var saveInterview = function(){
	$("#interview_inter_form").form('submit', {
		onSubmit : function() {
			return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$("#dialog_interview_info").dialog("close");
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);
				$("#interviewGrid").datagrid("reload");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	});
}

//公证弹窗
var toNotarization = function(){
	
	var row = $('#interviewGrid').datagrid('getSelections');
 	if (row.length == 1) {
 		//已经开始面签，公证，抵押，领他证的不能指派
		$("#interview_notarization_form").form("reset");
 		if(userId != row[0].notarizationId){
 			$.messager.alert("操作提示","公证只有指派的公证人员才能编辑","error");
 			return;
 		}
 		if(!row[0].interviewer){
 			$.messager.alert("操作提示","未指派公证人员！","error");
 			return;
 		}
 		if(row[0].notarizationStatus  == "1" ){
 			$.messager.alert("操作提示","公证已完成，不能再编辑","error");
 			return;
 		}
 		$("#interview_notarization_form").find("input[name='pid']").val(row[0].pid);
 		$("#interview_notarization_form").find("input[name='projectId']").val(row[0].projectId);
 		$("#interview_notarization_form").find("input[id='notarization_id']").textbox("setValue",row[0].notarizator);
 		$("#interview_notarization_form").find("input.receiveTime").datebox('setValue',formatterDate(new Date()));
 		$("#interview_notarization_form").find("input.handingTime").datebox('setValue',formatterDate(new Date()));
 		
 		$("#dialog_interview_notarization").dialog({
 			 title: '公证',
 			 width: 300,
 	       modal: true,
 	       top: 200,
 		});
 		
 		$("#dialog_interview_notarization").dialog("open").dialog("center");
		
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
/**
 * 保存公证
 */
var saveNotarization = function(){
	$('#interview_notarization_form').form('submit', {
		onSubmit : function() {
			return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$("#dialog_interview_notarization").dialog("close");
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);
				$("#interviewGrid").datagrid("reload");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	});
}

//抵押弹窗
var toMortgage = function(){
	
	var row = $('#interviewGrid').datagrid('getSelections');
 	if (row.length == 1) {
 		//已经开始面签，公证，抵押，领他证的不能指派
 		$("#interview_mortgage_form").form("reset");
 		if(userId != row[0].mortgageUser){
 			$.messager.alert("操作提示","抵押只有指派的抵押人员才能编辑","error");
 			return;
 		}
 		if(!row[0].interviewer){
 			$.messager.alert("操作提示","未指派抵押人员！","error");
 			return;
 		}
 		if(row[0].mortgageStatus  == "1" ){
 			$.messager.alert("操作提示","抵押已完成，不能再编辑","error");
 			return;
 		}
 		$("#interview_mortgage_form").find("input[name='pid']").val(row[0].pid);
 		$("#interview_mortgage_form").find("input[name='projectId']").val(row[0].projectId);
 		$("#interview_mortgage_form").find("input[id='mortgageUser']").textbox("setValue",row[0].mortgator);
 		$("#interview_mortgage_form").find("input.mortgageTime").datebox('setValue',formatterDate(new Date()));
 		$("#interview_mortgage_form").find("input.mortgageHandTime").datebox('setValue',formatterDate(new Date()));
 		
 		$("#dialog_interview_mortgage").dialog({
 			 title: '抵押',
 			 width: 300,
 	         modal: true,
 	         top: 200,
 		});
 		
 		$("#dialog_interview_mortgage").dialog("open").dialog("center");
		
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
	
}
/**
 * 抵押操作
 */
var saveMortgage = function(){
	$('#interview_mortgage_form').form('submit', {
		onSubmit : function() {
			return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$("#dialog_interview_mortgage").dialog("close");
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);
				$("#interviewGrid").datagrid("reload");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	});
}

//领他证弹窗
var toHis = function(){
	
	var row = $('#interviewGrid').datagrid('getSelections');
 	if (row.length == 1) {
 		$("#interview_his_form").form("reset");
 		if(row[0].interviewStatus != 1 || row[0].notarizationStatus != 1 || row[0].mortgageStatus != 1){
 			$.messager.alert("操作提示","面签、公证、抵押没有全部完成，不能领他证","error");
 			return;
 		}
 		if(row[0].pmUserId != userId){
 			$.messager.alert("操作提示","非收单人人员，不可办理","error");
 			return;
 		}
 		//已经处理过
 		if(row[0].hisWarrantUserName){
 			$.messager.alert("操作提示","领他证已完成，不能再编辑","error");
 			return;
 		}
 		
 		$("#interview_his_form").find("input[name='pid']").val(row[0].pid);
 		$("#interview_his_form").find("input[name='projectId']").val(row[0].projectId);
 		//收单员
 		$("#interview_his_form").find("input[id='pmUserName']").textbox("setValue",row[0].pmUserName);
 		$("#interview_his_form").find("input[name='hisWarrantUser']").val(row[0].pmUserId);
 		$("#interview_his_form").find("input.hisWarrantTime").datebox('setValue',formatterDate(new Date()));
 		
 		$("#dialog_interview_his").dialog({
 			 title: '领他证',
 			 width: 750,
 	         modal: true,
 	         top: 200,
 		});
 		
 		$("#dialog_interview_his").dialog("open").dialog("center");
		
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
	
}

/**
 * 保存领他证操作
 */
var saveHis = function(){
	$('#interview_his_form').form('submit', {
		onSubmit : function() {
			var flag = true;
			if($(this).form('validate')){
				if(!$(this).find("input[name='interviewFile.fileId']").val()){
					$.messager.alert("操作提示","他证影象不能为空！","error");
					flag = false;
				}
			}else{
				flag = false;
			}
			return flag; 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$("#dialog_interview_his").dialog("close");
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);
				$("#interviewGrid").datagrid("reload");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	});
}


var formatterProjectName = function (value, row, index){
	var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>";
	return va;
}

var dateformatter = function(value, row, index){
	if(value){
		return value.substring(0,10);
	}
}

$(document).ready(function(){
	
	$("input#interviewer").combobox({
		validType:'selrequired',
		valueField:'pid',
		textField:'realName',
		url:'${basePath}systemRoleController/getUsersByRoleCode.action?roleCode=INTERVIEWINFO_INTERVIEWER',
	});
	$("input#notarizator").combobox({
		validType:'selrequired',
		valueField:'pid',
		textField:'realName',
		url:'${basePath}systemRoleController/getUsersByRoleCode.action?roleCode=INTERVIEWINFO_NOTARIZATOR',
	});
	$("input#mortgator").combobox({
		validType:'selrequired',
		valueField:'pid',
		textField:'realName',
		url:'${basePath}systemRoleController/getUsersByRoleCode.action?roleCode=INTERVIEWINFO_MORTGATOR',
	});
	
	$("input#interviewPlace").combobox({
		validType:'selrequired',
		valueField:'lookupVal',
		textField:'lookupDesc',
		url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=INLOAN_INTERVIEW_PLACE',
	});

});

//删除文件
function delFile(imgurl){
	$("#"+imgurl).val("");
	var srcurl="${ctx}/p/xlkfinance/images/u53.png";
	$("#"+imgurl+"_img").attr("src",srcurl);
};

</script>
<title>贷前管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<!--图标按钮 -->
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="<%=basePath%>bizInterviewController/getAllProject.action" method="post" id="bizInterview_searchForm" name="bizInterview_searchForm" >
			<!-- 查询条件 -->
			<div style="padding:5px">
				<table>
					<tr>
						<td width="100" align="right"  height="28">项目名称：</td>
						<td colsapn="2">
							<input class="easyui-textbox" name="projectName"/>						
						</td>
						<td width="80" align="right" height="28">项目编号：</td>
						<td>
							<input class="easyui-textbox" name="projectNumber"/>
						</td>
						<td width="80" align="right" height="28">客户名称：</td>
						<td>
							<input class="easyui-textbox" name="acctName"/>
						</td>
					</tr>
					<tr>
						<td width="80" align="right"  height="28">待办理节点：</td>
						<td >
							<select class="easyui-combobox" id="requestStatus"  name="foreclosureStatus" style="width:130px" panelHeight="auto" editable="false" >
								<option value=-1  selected="selected" >--请选择--</option>
							    <option value=1 >待提交</option>
								<option value=2 >待评估</option>
								<option value=3 >待下户</option>
								<option value=6 >待复审</option>
								<option value=7 >待终审</option>
								<option value=8 >待放款申请</option>
								<option value=9 >待放款复核</option>
								<option value=10 >待资金放款</option>
								<option value=11 >还款中</option>
								<option value=12 >已结清</option>
							</select>
						</td>
						<td width="80" align="right" >领他证时间：</td>
						<td>
							<input name="hisWarrantStartTime" class="easyui-datebox" editable="false"  data-options="" />
							~
							<input name="hisWarrantEndTime" class="easyui-datebox" editable="false"  data-options="" />
						</td>
					</tr>
					<tr>
						<td width="100" align="right"  height="28">面签状态：</td>
						<td >
							<select class="easyui-combobox" panelHeight="auto" name="interviewStatus" style="width:130px" editable="false" >
								<option value=-1  selected="selected" >--请选择--</option>
								<option value="2" >未面签</option>
							    <option value="1" >已面签</option>
							</select>
						</td>
						<td width="100" align="right"  height="28">公证状态：</td>
						<td >
							<select class="easyui-combobox" panelHeight="auto" name="notarizationStatus" style="width:130px" editable="false" >
								<option value=-1  selected="selected" >--请选择--</option>
								<option value="2" >未公证</option>
							    <option value="1" >已公证</option>
							</select>
						</td>
						<td width="100" align="right"  height="28">抵押状态：</td>
						<td >
							<select class="easyui-combobox" panelHeight="auto" name="mortgageStatus" style="width:130px" editable="false" >
								<option value=-1  selected="selected" >--请选择--</option>
								<option value="2" >未抵押</option>
							    <option value="1" >已抵押</option>
							</select>
						</td>
						<td>
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#interviewGrid','#bizInterview_searchForm');">查询</a>
							<a href="#" onclick="$('#bizInterview_searchForm').form('reset')" class="easyui-linkbutton" style="width: 60px;">重置</a>
						</td>
					</tr>
				</table>
			</div>
		</form>
		
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
			<shiro:hasPermission name="interviewAsignPermissions">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-zp" plain="true" onclick="toAsign();">指派</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="interviewInterviewPermissions">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-mq" plain="true" onclick="toInterview();">面签</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="interviewNotarizationPermissions">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-gz" plain="true" onclick="toNotarization()">公证</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="interviewMortgagePermissions">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-dy" plain="true" onclick="toMortgage();">抵押</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="interviewHisPermissions">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ltz" plain="true" onclick="toHis();">领他证</a>
			</shiro:hasPermission>
		</div>
		
	</div>
	<div class="dksqListDiv" style="height:100%;">
	<table id="interviewGrid" title="面签管理列表" class="easyui-datagrid" 
		style="height:100%;width: auto;"
		data-options="
		    url: '<%=basePath%>bizInterviewController/getAllProject.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true">
		<!-- 表头 -->
		<thead><tr>
		    <th data-options="field:'pid',checkbox:true" ></th>
		    <th data-options="field:'projectName',width:80,formatter:formatterProjectName" align="center" halign="center">项目名称</th>
		    <th data-options="field:'projectNumber',width:80" align="center" halign="center">项目编号</th>
		    <th data-options="field:'acctName',width:80" align="center" halign="center">客户名称</th>
		    <th data-options="field:'loanMoney',width:80,formatter:formatMoney" align="center" halign="center">贷款金额</th>
		    <th data-options="field:'foreclosureStatus',formatter:formatterProjectStatus,width:80" align="center" halign="center">办理节点</th>
		    <th data-options="field:'interviewStatus',formatter:formatterInterviewInterviewStatus,width:80" align="center" halign="center">面签状态</th>
		    <th data-options="field:'interviewer',width:80" align="center" halign="center">面签人员</th>
		    <th data-options="field:'notarizationStatus',width:80,formatter:formatterInterviewNotarizationStatus" align="center" halign="center">公证状态</th>
		    <th data-options="field:'notarizator',width:80" align="center" halign="center">公证人员</th>
		    <th data-options="field:'mortgageStatus',width:80,formatter:formatterInterviewMortgageStatus" align="center" halign="center">抵押状态</th>
		    <th data-options="field:'mortgator',width:80" align="center" halign="center">抵押人员</th>
		    <th data-options="field:'hisWarrantTime',width:150,formatter:dateformatter" align="center" halign="center">领他证时间</th>
		    <th data-options="field:'pmUserName',width:80" align="center" halign="center">收单人</th>
		</tr></thead>
	</table>
	</div>
	</div>
	</div>

<!-- 指派弹窗 -->
<div id="dialog_interview_asign" class="easyui-dialog" buttons="#dlg-buttons_asign" closed="true">
	<form id="interview_asign_form" method="post" action="<%=basePath%>bizInterviewController/asign.action">
		<!-- 面签管理ID -->
		<input type="hidden" name="pid" value="">
		<input type="hidden" name="projectId" value="">
		
		<table style="width: 100%;">
			<tbody>
				<tr>
					<td class="label_right required">面签人员:</td>
					<td>
						<input id="interviewer" name="interviewId" required="true" class="easyui-combobox" editable="false" panelHeight="auto" >
					</td>
				</tr>
				<tr>
					<td class="label_right required">公证人员:</td>
					<td>
						<input id="notarizator" name="notarizationId" required="true" class="easyui-combobox" editable="false" panelHeight="auto" >
					</td>
				</tr>
				<tr>
					<td class="label_right required">抵押人员:</td>
					<td>
						<input id="mortgator" name="mortgageUser" required="true" class="easyui-combobox" editable="false" panelHeight="auto" >
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<!-- 指派按钮 -->
<div id="dlg-buttons_asign">
	<a id="saveAsign" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAsign()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_interview_asign').dialog('close')">取消</a>
</div>

<!-- 面签弹窗 -->
<div id="dialog_interview_info" class="easyui-dialog" buttons="#dlg-buttons_info" closed="true">
	<form id="interview_inter_form" method="post" action="<%=basePath%>bizInterviewController/interview.action">
		<!-- 抵押物ID/项目ID -->
		<input type="hidden" name="pid" value="">
		<input type="hidden" name="projectId" value="">
		<table style="width: 100%;">
			<tbody>
				<tr>
					<td class="label_right required border-b-dashed">面签人员:</td>
					<td class="border-b-dashed">
						<input style="width: 120px;" class="easyui-textbox" id="interviewer2" readonly="readonly"/>
					</td>
					<td class="label_right required border-b-dashed">面签时间:</td>
					<td class="border-b-dashed">
						<input style="width: 120px;" class="easyui-datebox interviewTime" editable="false" name="interviewTime" data-options="required:true"/>
					</td>
					<td class="label_right required border-b-dashed">面签地点:</td>
					<td class="border-b-dashed">
						<input style="width: 120px;" class="easyui-textbox" id="interviewPlace" editable="false" panelHeight="auto" name="interviewPlace" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td class="label_right required">收款银行卡户名:</td>
					<td>
						<input id="cusCardInfo.pid" name="cusCardInfo.pid" type="hidden"/>
						<input style="width: 120px;" class="easyui-textbox" id="receBankCardName" name="cusCardInfo.receBankCardName" data-options="required:true,validType:'length[1,50]'"/>
					</td>
					<td class="label_right required">收款银行卡账号:</td>
					<td>
						<input style="width: 120px;" class="easyui-textbox" id="receBankCardCode" name="cusCardInfo.receBankCardCode" data-options="required:true,validType:'length[1,50]'"/>
					</td>
					<td class="label_right required" style="width: 120px;">收款银行卡开户行:</td>
					<td>
						<input style="width: 120px;" class="easyui-textbox" id="receBankName" name="cusCardInfo.receBankName" data-options="required:true,validType:'length[1,50]'"/>
					</td>
				</tr>
				<tr>
					<td class="label_right required">还款银行卡户名:</td>
					<td>
						<input style="width: 120px;" class="easyui-textbox" id="repaymentBankCardName" name="cusCardInfo.repaymentBankCardName" data-options="required:true,validType:'length[1,50]'"/>
					</td>
					<td class="label_right required">还款银行卡账号:</td>
					<td>
						<input style="width: 120px;" class="easyui-textbox" id="repaymentBankCardCode" name="cusCardInfo.repaymentBankCardCode" data-options="required:true,validType:'length[1,50]'"/>
					</td>
					<td class="label_right required">还款银行卡开户行:</td>
					<td>
						<input style="width: 120px;" class="easyui-textbox" id="repaymentBankName" name="cusCardInfo.repaymentBankName" data-options="required:true,validType:'length[1,50]'"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<!-- 按钮 -->
<div id="dlg-buttons_info">
	<a class="easyui-linkbutton" iconCls="icon-save" onclick="saveInterview()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_interview_info').dialog('close')">取消</a>
</div>

<!-- 公证弹窗 -->
<div id="dialog_interview_notarization" class="easyui-dialog" buttons="#dlg-buttons_notarization" closed="true">
	<form id="interview_notarization_form" method="post" action="<%=basePath%>bizInterviewController/notarization.action">
		<!-- 面签ID/项目ID -->
		<input type="hidden" name="pid">
		<input type="hidden" name="projectId">
		
		<table style="width: 100%;">
			<tbody>
				<tr>
					<td class="label_right required">公证类型:</td>
					<td>
						<label><input type="checkbox" style="width: auto;" validType="checkBox[':input[name=&#34;notarizationType&#34;]']" class="easyui-validatebox" value="全委" name="notarizationType">全委</label>
						<label><input type="checkbox" style="width: auto;" class="easyui-validatebox" value="强执" name="notarizationType">强执</label>
					</td>
				</tr>
				<tr>
					<td class="label_right required">取证日期:</td>
					<td>
						<input class="easyui-datebox receiveTime" editable="false" name="receiveTime" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td class="label_right required">办理人员:</td>
					<td>
						<input class="easyui-textbox" readonly disabled="disabled" id="notarization_id"/>
					</td>
				</tr>
				<tr>
					<td class="label_right required">办理时间:</td>
					<td>
						<input class="easyui-datebox handingTime" editable="false" name="handingTime" data-options="required:true"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<!-- 按钮 -->
<div id="dlg-buttons_notarization">
	<a class="easyui-linkbutton" iconCls="icon-save" onclick="saveNotarization()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_interview_notarization').dialog('close')">关闭</a>
</div>

<!-- 抵押弹窗 -->
<div id="dialog_interview_mortgage" class="easyui-dialog" buttons="#dlg-buttons_mortgage" closed="true">
	<form id="interview_mortgage_form" method="post" action="<%=basePath%>bizInterviewController/mortgage.action">
		<!-- 面签ID/项目ID -->
		<input type="hidden" name="pid">
		<input type="hidden" name="projectId">
		<table style="width: 100%;">
			<tbody>
				<tr class="eval_split_line">
					<td class="label_right required">抵押权人:</td>
					<td>
						<input class="easyui-textbox" name="mortgageName" data-options="required:true,validType:'length[1,20]'"/>
					</td>
				</tr>
				<tr>
					<td class="label_right required">抵押回执编号:</td>
					<td>
						<input class="easyui-textbox" name="mortgageCode" data-options="required:true,validType:'length[1,50]'"/>
					</td>
				</tr>
				<tr>
					<td class="label_right required">取证日期:</td>
					<td>
						<input class="easyui-datebox mortgageTime" editable="false" name="mortgageTime" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td class="label_right required">办理人员:</td>
					<td>
						<input class="easyui-textbox" readonly id="mortgageUser"/>
					</td>
				</tr>
				<tr>
					<td class="label_right required">办理日期:</td>
					<td>
						<input class="easyui-datebox mortgageHandTime" editable="false" name="mortgageHandTime" data-options="required:true"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<!-- 按钮 -->
<div id="dlg-buttons_mortgage">
	<a class="easyui-linkbutton" iconCls="icon-save" onclick="saveMortgage()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_interview_mortgage').dialog('close')">关闭</a>
</div>

<!-- 领他证弹窗 -->
<div id="dialog_interview_his" class="easyui-dialog" buttons="#dlg-buttons_his" closed="true">
	<form id="interview_his_form" method="post" action="<%=basePath%>bizInterviewController/his.action">
	
		<input type="hidden" name="pid">
		<input type="hidden" name="projectId">
		<!-- 带出客户经理（收单员） -->
		<input type="hidden" name="hisWarrantUser">
		
		<table style="width: 100%;">
			<tbody>
				<tr>
					<td class="label_right required">他项权证:</td>
					<td>
						<input class="easyui-textbox" name="hisWarrant" data-options="required:true,validType:'length[1,20]'"/>
					</td>
				</tr>
				<tr>
					<td class="label_right required">领取时间:</td>
					<td>
						<input class="easyui-datebox hisWarrantTime" editable="false" name="hisWarrantTime" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<!-- 录单人 -->
					<td class="label_right required">收单人:</td>
					<td>
						<input class="easyui-textbox" id="pmUserName" readonly />
					</td>
				</tr>
				<tr>
					<td class="label_right required">他证影象:</td>
					<td>
						<input type="button" class="text_btn" onclick="openFileUpload('interview_picture_url',150);" value="上传"/>
						&nbsp;<input type="button" onclick="delFile('interview_picture_url');" class="text_btn"  value="删除"/>
						<input type="hidden" id="interview_picture_url"  name="interviewFile.pictureUrl" value=""/>
						<input type="hidden" id="interview_picture_url_fileId" name="interviewFile.fileId" value=""/>
						<input type="hidden" id="interview_picture_url_pid" name="interviewFile.pid" value=""/>
					</td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="2">
						<img id="interview_picture_url_img" alt="头像" src="${ctx}/p/xlkfinance/images/u53.png" width="150px" height="150px">
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<!-- 按钮 -->
<div id="dlg-buttons_his">
	<a class="easyui-linkbutton" iconCls="icon-save" onclick="saveHis()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_interview_his').dialog('close')">关闭</a>
</div>
	
</body>
</html>
