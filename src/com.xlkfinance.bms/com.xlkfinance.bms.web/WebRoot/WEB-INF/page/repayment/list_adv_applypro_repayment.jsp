<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<link href="/BMS/upload/css/fileUpload.css" type="text/css"
	rel="stylesheet" />
<style type="text/css">
.table_border td {
	border-top: 1px #DDD solid;
	border-right: 1px #DDD solid;
	height: 50px;
}

.table_border th {
	border-top: 1px #DDD solid;
	border-right: 1px #DDD solid;
	height: 50px;
}

.table_border {
	border-bottom: 1px #DDD solid;
	border-left: 1px #DDD solid;
	width: 100%
}
</style>

<script type="text/javascript">
/**************工作流字段 begin******************/
var WORKFLOW_ID = "prepaymentRequestProcess"; 
workflowTaskDefKey = "task_PrepaymentRequest";
/**************工作流字段 end********************/
var projectId = ${reginfo.projectId};
var projectNum ='${reginfo.projectNum}';
var isRebackInterest =${advinfo.isRebackInterest};
var isArrears=${advinfo.isArrears};
var hasOtherLoan=${advinfo.hasOtherLoan};
var reason='${advinfo.reason}';
var preRepayId=${preRepayId};
var loanId=${loanId};
var projName ="${projectName}";
var isShow = "${isShow}";
var projectName='';
$(function() {
	var tab = parent.$('#layout_center_tabs').tabs('getSelected');
	var atTitle = tab.panel('options').title;
	if(atTitle=="提前还款查看" ){
		projectName='${projectName}';
	}
	if(atTitle=="业务主管审核"||atTitle=="风控总监审查" ||atTitle=="财务还款处理" || isShow || isShow == 'true'){
		 	$("#up_data").datagrid({
		 		onLoadSuccess:function(){
			 		$('.prepaymentl .easyui-combobox').attr('disabled','disabled');
					$('.prepayment input[type="checkbox"]').attr('disabled','disabled');
					$('.prepayment .easyui-textbox').attr('disabled','disabled');
					$('.prepayment .easyui-linkbutton:not(.download) ,.prepayment a:not(.download)').removeAttr('onclick');
					$('.prepayment a:not(.download) font').attr('color','gray');
					$('.prepayment .easyui-linkbutton:not(.download) ,.prepayment  input,.prepayment  textarea').attr('disabled','disabled');
					$('.prepayment .easyui-linkbutton:not(.download) ,.prepayment  input,.prepayment  textarea').attr('readOnly','readOnly');
					$('.prepayment .easyui-linkbutton:not(.download) ,.prepayment  input,.prepayment  textarea').addClass('l-btn-disabled');
					$("#planbutton").hide();
					$("#viewRepaymentPlan").show();
		 		}
		 	});
		 	
			$.ajax({
				type: "GET",
			    url: BASE_PATH+"workflowController/findWorkflowProject.action",
			    data:{"projectId":projectId,"processDefinitionKey":WORKFLOW_ID},
			    dataType: "json",
			    async: false,
			    success: function(data){
			    	if(data!=null){
			    		workflowTaskDefKey = data.workflowTaskDefKey;
			    		var resData = {workflowInstanceId: data.workflowInstanceId};
			    		loadProcessLoggingGrid(resData);
			    	}
			    }
			});
	}

	if( "task_LoanRequest"==workflowTaskDefKey||//贷款申请
		"task_BusinessManagerCheck"==workflowTaskDefKey||//业务主管审核
		"task_ApprovalOfficerAllocation"==workflowTaskDefKey||//审批官分配
		"task_ApprovalOfficerReview"==workflowTaskDefKey||//审批官审查
		"task_RiskControlDirectorReview"==workflowTaskDefKey||//风控总监审查
		"task_ConveneMeeting"==workflowTaskDefKey||//组织召开贷审会
		"task_OfflineMeetingResult"==workflowTaskDefKey||//线下贷审会决议
		"task_OnlineMeetingResult"==workflowTaskDefKey||//线上贷审会决议
		"task_ContractSign"==workflowTaskDefKey||//合同制作与签署
		"task_MortgageRegistration"==workflowTaskDefKey||//办理抵押登记
		"task_ConfirmSingleLoanApproval"==workflowTaskDefKey||//确认放款审批单
		"task_RiskControlDirectorApproval"==workflowTaskDefKey||//风控总监放款审批
		"task_GeneralManagerApproval"==workflowTaskDefKey||//总经理放款审批
		"task_FinancialLoans"==workflowTaskDefKey||//财务放款
		"task_FinancialConfirm"==workflowTaskDefKey||
		"task_ProjectArchive"==workflowTaskDefKey){//项目归档
		advbutton=3;
	}
});

	// 初始化 是否欠款之类的信息
	$(function() {
		//fineRatesAmt();
		// 格式化提前还款金额
		//alert($("#surplus").textbox("getValue"));
		//$("#preRepayAmt").textbox("setValue",accounting.formatMoney($("#preRepayAmt").textbox("getValue"), "", 2, ",", "."));
		//$("#surplus").textbox("setValue",accounting.formatMoney($("#surplus").textbox("getValue"), "", 2, ",", "."));
		//$("#fine").textbox("setValue",accounting.formatMoney($("#fine").textbox("getValue"), "", 2, ",", "."));	
		
		$("#reason").val(reason);
		//alert($("#surplus").textbox("getValue"));
		if(isRebackInterest==1){
			$(":radio[name='isRebackInterest'][value='1']").attr("checked","checked");
		}
		if(isRebackInterest==2){
			$(":radio[name='isRebackInterest'][value='2']").attr("checked","checked");
		}
		 if(isArrears==1){
		$(":radio[name='isArrears'][value='1']").attr("checked","checked");
		}
		if(isArrears==2){
			$(":radio[name='isArrears'][value='2']").attr("checked","checked");
		}
		if(hasOtherLoan==1){
			$(":radio[name='hasOtherLoan'][value='1']").attr("checked","checked");
		}
		if(hasOtherLoan==2){
			$(":radio[name='hasOtherLoan'][value='2']").attr("checked","checked");
		} 
	});
	
	function uploadrepayfile() {
		$('#upload').dialog('open').dialog('setTitle', "资料上传");
		$("#upload").dialog("move",{top:$(document).scrollTop()+260*0.5}); 
		$("#fileDesc").val("");
		$("#file1").val("");
		$("#txt1").val("");
	}
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<!-- 基本项目信息 -->
<div class="easyui-panel" title="基本项目信息" data-options="collapsible:true">
	<div style="padding:5px">
		<table>
			<tr>
				<td height="35" width="300" align="center">项目编号:${reginfo.projectNum}</td>
				<td><div class="iniTitle">项目名称:<a onclick='formatterProjectName.disposeClick(${reginfo.projectId})' href='#'><span style='color:blue;'>${reginfo.projectName}</span></a></div></td>
			</tr>
		</table>
	</div>
</div>

<!-- 利息信息 -->
<div class="clear pt10"></div>
<div class="easyui-panel" title="贷款收息表" data-options="collapsible:true">
<div id="tablegrid" class="pt10"  >
<%@ include file="list_loanCalculate.jsp"%> 
</div>
</div>

<div class="prepayment">
	<!-- 提前还款登记信息 -->
	<div class="clear pt10"></div>
	<div class="easyui-panel" title="提前还款登记信息" data-options="collapsible:true">
		<!-- 查询条件 -->
		<form action="insertadvApplyrepayment.action" method="post"
			id="advRepaybutton" enctype="multipart/form-data">
			<div style="padding: 5px">
				<table>
					<tr>
						<td class="label_right" style="width:180px"><font color="red">*</font>提前还贷金额:</td>
						<td><input class="easyui-textbox" name="preRepayAmt" missingMessage="请输入提前还贷金额!" value="${advinfo.preRepayAmt}" required="true"  data-options="onChange:fineRatesAmt"   id="preRepayAmt" /></td>
						<td class="label_right" style="width:180px">本次还款后的剩余贷款本金: </td>
						<td>
							<input class="easyui-textbox" name="surplus" id="surplus" value="${advinfo.principalBalance}"  editable="false" />
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>提前还款罚金比例 （%）:</td>
						<td><input class="easyui-textbox" name="fineRates" missingMessage="请输入提前还款罚金比例!" data-options="onChange:fineRate" required="true" value="${advinfo.fineRates}" id="fineRates" /></td>
						<td class="label_right">提前还款罚金:</td>
						<td><input class="easyui-textbox" name="fine" id="fine" value="${advinfo.fine}"   editable="false"/></td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>提前还贷日期:</td>
						<td><input class="easyui-datebox"	name="repayDate" id="repayDate" editable="false" required="true" value="${advinfo.repayDate}"/></td>
						<td class="label_right">剩余本金还款日期:</td>
						<td><input class="easyui-datebox" name="planRepayLoanDt" id="planRepayLoanDt"  value="${reginfo.planRepayLoanDt}" readonly="readonly" /></td>
					</tr>
					<tr>
						<td class="label_right">原借款金额：</td>
						<td> <input class="easyui-textbox" name="creditAmt" id="roleName" value="${reginfo.creditAmtTemp}" readonly="readonly" /></td>
						<td class="label_right">原授信合同编号：</td>
						<td>
							<input class="easyui-textbox" name="CreditContractNo"
								id="CreditContractNo" value="${reginfo.contractCreditNo}"
								readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="label_right">原借款期限 : </td>
						<td><input class="easyui-textbox" name="repayCycle" id="roleName" value="${reginfo.repayCycle}" readonly="readonly" /></td>
						<td class="label_right">原借款种类:</td>
						<td>
						<input class="easyui-textbox" name="businessName" id="businessName"
									value="${reginfo.businessName}" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="label_right">原借款合同编号 : </td>
						<td>
							<input class="easyui-textbox" name="BrocontractNo"
								id="BrocontractNo" value="${reginfo.contractLoadNo}"
								readonly="readonly" />
						</td>
						<td class="label_right">原借款月利率（%）：</td>
						<td>
							 <input class="easyui-textbox" name="monthLoanInterest"
								id="roleName" value="${reginfo.monthLoanInterest}"
								readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="label_right">是否拖欠我公司款项：</td>
						<td colspan="3">
							<input type="radio" name="isArrears"  id="isArrears1" value="1" />是&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="isArrears" value="2"  id="isArrears2"/>否
						</td>
					</tr>
					<tr>
						<td class="label_right">是否退还已收取的利息：</td>
						<td colspan="3">
							<input type="radio" name="isRebackInterest" id="isRebackInterest1" value="1" />是&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="isRebackInterest" value="2"  id="isRebackInterest2" />否
						</td>
					</tr>
					<tr>
						<td class="label_right">在我公司是否有其他借款 ：</td>
						<td colspan="3">
							<input type="radio" name="hasOtherLoan"  id="hasOtherLoan1" value="1" />是&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="hasOtherLoan" id="hasOtherLoan2" value="2"  />否
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>申请原因：</td>
						<td colspan="3">
							<input name="reason" id="reason" required="true" value="${advinfo.reason}"  missingMessage="请输入申请原因!" class="easyui-textbox" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,500]'">
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center" height="40">
							<a href="javascript:void(0);" class="easyui-linkbutton" id="savebutton"		onclick="advRepaybutton()">保&nbsp;&nbsp;存</a> 
						</td>
						<td colspan="2" align="center" height="40"> 
							<a href="javascript:void(0);" id="planbutton" class="easyui-linkbutton" onclick="insertRepayPlan()">重新生成计划还款表</a>
							
							<a href="javascript:void(0);" id="viewRepaymentPlan" style="display:none" class="easyui-linkbutton download" onclick="searchReapyList()">查看还款计划表</a>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	
	<!-- 提前还款申请表 -->
	<div class="clear pt10"></div>
	<div class="easyui-panel" title="提前还款申请表" data-options="collapsible:true">
		<div id="status"></div>
		<div id="dlg-buttons">
			<center>
				<a id="add" href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-save" onclick="submitForm()">保存</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#upload').dialog('close')">取消</a>
			</center>
		</div>
		<div id="upload" class="easyui-dialog"
			style="width: 500px; height: 300px; padding: 10px 20px;" closed="true"
			buttons="#dlg-buttons">
			<form id="fileUploadForm" name="fileUploadForm"
				action="uploadadvapply.action" method="post"
				enctype="multipart/form-data" target="hidden_frame">
				<input name="fileKinds" id="selectAge" value="1" type="hidden"/>
				<input name="preRepayId" id="preRepayId" type="hidden" />
				<p>
					选择文件：<input class="input_text" type="text" id="txt1"
						name="fileToUpload" /> <input type="button" name="uploadfile2"
						id="uploadfile2" style="padding-left: 10px;" value="浏览..." /> <input
						class="input_file" size="30" type="file" name="file1" id="file1"
						onchange="txt1.value=this.value" />
				</p>
				<p>
					<span style="margmargin-top: 10px;">上传说明:</span>
					<textarea rows="4" style="width: 300px;" name="fileDesc" id="fileDesc"></textarea>
				</p>
			</form>
		</div>
		<div id="editdlg-buttons">
			<center>
				<a id="add" href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-save" onclick="updatefileinfo()">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#upload-edit').dialog('close')">取消</a>
			</center>
		</div>
		<div id="upload-edit" class="easyui-dialog"
			style="width: 500px; height: 300px; padding: 10px 20px;" closed="true" buttons="#editdlg-buttons">
			<form id="editfileUploadFormedit" name="fileUploadeditForm"
				action="${basePath}rePaymentController/updateloadfileinfo.action" method="POST" target="hidden_frame">
				<input id="preRepayIdedit" name="preRepayId" type="hidden" />
				<input name="fileId" id="editfileId" type="hidden" />
				<input name="fileKinds" id="selectAge" value="1" type="hidden"/>
				<p>
					<span style="margmargin-top: 10px;">上传说明:</span>
					<textarea rows="4" style="width: 300px;" id="editfileDesc" name="fileDesc"></textarea>
				</p>
				
			</form>
		</div>
		<div id="filetoolbar">
			&nbsp;&nbsp;<a href="javascript:void(0);"
			id="uploadbutton"	class="easyui-linkbutton" data-options="iconCls:'icon-add'"  plain="true" onclick="uploadrepayfile()">上传</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" id="delectbutton"	onclick="delectable()">删除</a>
		</div>
		<div style="padding:10px;text-align: center;">
			<table id="up_data"  class="easyui-datagrid"
				data-options="
			    url: 'queryRegAdvapplyFile.action?preRepayId='+preRepayId,
			    method: 'POST',
			    idField: 'pId',
			    fitColumns:true,
			    rownumbers: true,
			    pagination: true,
			    toolbar: '#filetoolbar'
			    ">
				<!-- 表头 -->
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true"></th>
						<th data-options="field:'fileType',width:100">文件类型</th>
						<th
							data-options="field:'fileFunType',width:150,formatter:uploadType">文件种类</th>
						<th data-options="field:'fileName',width:150">文件名称</th>
						<th data-options="field:'fileSize',width:150,formatter: sizeFileter">大小</th>
						<th
							data-options="field:'uploadDttm',width:150,formatter:dateupSplit">上传时间</th>
						<th data-options="field:'fileDesc',width:150">上传说明</th>
						<th
							data-options="field:'yyyyy',width:300,formatter:uploadadvoperat">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<!-- 工作流程 -->
	<div class="pt10" style="border-width:2px;border-top:none;border:none;">
		<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
			<div class="loanworkflowWrapDiv"  style="padding:5px">
				<%@ include file="../common/loanWorkflow.jsp"%>
				<%@ include file="../common/task_hi_list.jsp"%>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

	var advbutton=1;
	function advRepaybutton() {
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var atTitle = tab.panel('options').title;
		if(atTitle=="提前还款查看"){
			$.messager.alert("系统提示","此流程已在审核不能修改数据","warning"); 	
			return;
		}
		if(advbutton==3){
			$.messager.alert("系统提示","你不能修改或者更新提前还款信息","warning"); 
			return;
		}
		
		if(!$("#advRepaybutton").form('validate')){
			return ;
		}
		
		var preRepayAmt = string.replaceAll($("#preRepayAmt").val());
		var fineRates = $("#fineRates").val();
		var fine = string.replaceAll($("#fine").val());
		var isArrears = $("input[name='isArrears']:checked").val();
		var isRebackInterest = $("input[name='isArrears']:checked").val();
		var hasOtherLoan = $("input[name='isArrears']:checked").val();
		
		var reason = $.trim($('#reason').textbox('getValue'));
		
	 	var surplus = string.replaceAll($("#surplus").val()); 
		var repayDate = $("#repayDate").datebox("getValue"); 
		var planRepayLoanDt = $("#planRepayLoanDt").datebox("getValue");
		
		// 提前还款日期不能大于剩余本金还款日期
		if(repayDate >= planRepayLoanDt){
			$.messager.alert("系统提示","提前还款日期不能大于剩余本金还款日期！","error"); 
			return ;
		}

		$.post("updateadvApplyrepayment.action", {
			preRepayAmt : preRepayAmt,
			fineRates : fineRates,
			fine : fine,
			isArrears : isArrears,
			isRebackInterest : isRebackInterest,
			hasOtherLoan : hasOtherLoan,
			reason : reason,
			repayDate : repayDate,
			preRepayId : preRepayId,
			surplus:surplus
		}, function(data) {
			if(data>0){
				$.messager.alert("系统提示","更新成功！","info"); 	
			}else{	
				$.messager.alert("系统提示","系统异常!","error"); 	
			}
		});
	}

	function delectable() {
		var rowData = $('#up_data').datagrid('getSelections');
		if (rowData.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}// 获取选中的系统用户名 
		var pIds = "";
		for (var i = 0; i < rowData.length; i++) {
			pIds += rowData[i].pId;
			pIds += ",";
		}
		var str = pIds.substring(0, pIds.length-1); // 取字符串。  
		delfilebypath(str) ;
	}
	
	
	function delfilebypath(dta) {
		$.messager.confirm('操作提示','确定删除选中文件吗?',function(r){
		    if (r){
				$.post("delectUpdateAdvapply.action", {
					pId:dta
				}, function(data) {
					var result = eval("("+data+")");
					$.messager.alert("操作提示", result.delStatus, "info");
					$("#up_data").datagrid("reload");
				}); 
		    }
		});	    
	}
	
	var path = null;
	function uploadadvoperat(value, row, index) {
		path = row.filePath;
		var downloadDom = "<a class='download' href='"+BASE_PATH+"beforeLoanController/downloadData.action?path="+row.filePath+"&fileName="+row.fileName+"'><font color='blue'>下载</font> | </a>";
		var downd = "<span  style='display:none;' id='"+row.pId+"'>"
				+ row.filePath
				+ "</span><a href='javascript:void(0)' onclick='delfilebypath("
				+ row.pId + ")'><font  color='blue'> 删除</font></a>";
		var  edit ="   <a href='javascript:void(0)' onclick='editfileinfo("+ JSON.stringify(row) + ")'><font  color='blue'> 编辑</font></a>";
		return downloadDom + downd+edit;
	}
	
	function editfileinfo(row){
		$("#editfileId").val(row.pId);
		$("#editfileKind").val(row.fileFunType);
		$("#editfileDesc").val(row.fileDesc); 
		$('#upload-edit').dialog('open').dialog('setTitle', "修改上传信息");
	}
	
	//上传文件
	function submitForm() {
		if(preRepayId==-1){
			$.messager.alert("操作提示", "你需要先保存提前还款登记信息！", "error");
			return false;
		}
		var fileName = $("#file1").val();
		if (fileName == "" || null == fileName) {
			$.messager.alert("操作提示", "请选择要上传的文件！", "error");
			return false;
		}
		$('#preRepayId').val(preRepayId);
		$('#fileUploadForm').form('submit', {
			success : function(data) {
				var str = data.substring(1,data.length-1);
				$.messager.alert("操作提示", str, "info");
				$("#up_data").datagrid("reload");
				$('#upload').dialog('close');
			}
		});
	}
	
	function updatefileinfo(){
		$('#preRepayIdedit').val(preRepayId); 
		$('#editfileUploadFormedit').form('submit', {
			success : function(data) {
				if(data> 0 ){
					$.messager.alert("操作提示", "更新成功!", "info");
					$("#up_data").datagrid("reload");
					$('#upload-edit').dialog('close');
				}else{
					$.messager.alert("操作提示", "更新失败!", "info");
				}
			}
		});
	}
	
	function uploadType(value, row, index) {
		return "提前还款申请资料";
	}
	
	function dateupSplit(value, row, index) {
		if (null != row.uploadDttm && "" != row.uploadDttm) {
			return row.uploadDttm.split(" ")[0];
		}
		return row.uploadDttm;
	}
/**
 * 查看还款计划表
 */
function searchReapyList(){
	operRepayment('${basePath}/beforeLoanController/loanCalc.action?loanId='+loanId);
}

/**
 * 查看还款计划表window
 */
function operRepayment(url){
	$('<div id="repaymentDiv"/>').dialog({
		href : url,
		width : 750,
		height : 400,
		modal : true,
		title : '还款计划表',
		buttons : [ {
				text : '关闭',
				iconCls : 'icon-no',
				handler : function() {
					$("#repaymentDiv").dialog("close");
					}
				} ],
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
		}
	});
}


function sizeFileter(value,row,index){
	if(row.fileSize>0){
		return  parseInt(row.fileSize/1024)+" KB";
	}else{
		return '';
	}
}


/**
 * 重新生成还款计划表
 */
function insertRepayPlan(){
	if(preRepayId>0){
		operRepaymentList("${basePath}rePaymentController/advmakeRepayMent.action?projectId="+projectId+"&preRepayId="+preRepayId);
	}else{	
		$.messager.alert("操作提示", "你需要先保存提前还款登记信息！", "error");
	}
}

/**
 * 重新生成还款计划表,可保存
 */
function operRepaymentList(url){
	$('<div id="repaymentDiv"/>').dialog({
		href : url,
		width : 750,
		height : 400,
		modal : true,
		title : '还款计划表',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				window.saveRepaymentPlan();
			}	
			},{
				text : '取消 ',
				iconCls : 'icon-no',
				handler : function() {
					$("#repaymentDiv").dialog("close");
					}
				} ],
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
		}
	});
}

var string = {
		replaceAll:function(parm){
			return parm.replace(new RegExp(/(,)/g),'');
		}
}

function fineRate(){
	var preRepayAmt = string.replaceAll($("#preRepayAmt").textbox("getValue")); 
	var fineRates = $("#fineRates").textbox("getValue"); 
	 $("#fine").textbox("setValue",accounting.formatMoney(((preRepayAmt*fineRates)/100), "", 2, ",", "."));	
}

function fineRatesAmt(){
	var preRepayAmt = string.replaceAll($("#preRepayAmt").textbox("getValue")); 
	if(preRepayAmt == ''){
		return ;
	}
	// 验证是否超出最大金额
	if(preRepayAmt > 500000000){
		$.messager.alert("操作提示", "你输入的金额大于系统最大限额!请重新输入！","error");
		$("#preRepayAmt").textbox("setValue",'');
		return;
	}

	// 获取贷款剩余本金
	var principalBalance=0;
	$.ajax({
		type: "POST",
        url: "${basePath}rePaymentController/getOverplusAmt.action?loanId="+${loanId},
        dataType: "json",
        async:false, 
        success: function(data){
        	principalBalance =  data ;
        },
        error: function(data){
        	$.messager.alert("操作提示", "获取贷款剩余本金金额失败！", "error");
        }
	}); 
	
	if(principalBalance == 0 ){
		$.messager.alert("操作提示", "本金余额为0不可以提前还款！","error");
		$("#preRepayAmt").textbox("setValue",'');
		return false;	
	}
	
	if($("#preRepayAmt").textbox("getValue") > principalBalance){
		$.messager.alert("操作提示", "你输入的金额大于本金余额!请重新输入！","error");
		$("#preRepayAmt").textbox("setValue",'');
		$("#surplus").textbox("setValue",'');
		return false;
	}
	
	// 计算 提前还款罚金
	fineRate();
	
	// 格式化提前还款金额
	$("#preRepayAmt").textbox("setValue",accounting.formatMoney(preRepayAmt, "", 2, ",", "."));
	
	// 设置提前还款后的剩余贷款本金 贷款剩余金额 - 提前还款金额
	$("#surplus").textbox("setValue",accounting.formatMoney((principalBalance-preRepayAmt), "", 2, ",", "."));
}


function  checkrepayDate(newValue,oldValue){
	var repayDate = $("#repayDate").datebox("getValue"); 
	var rows = $("#loanCaculateGrid").datagrid("getRows");
	var  dt=null;
	for(var i=0;i<rows.length;i++){
		if(rows[i].isReconciliation != 1){
			dt= rows[i-1].planRepayDt;
			break;
		}
	}
	if(dateCompare(repayDate,dt)==false){
	 	$("#repayDate").datebox("setValue",''); 
	 	$.messager.alert("系统提示","日期不能少于最后一次对账日期","warning"); 
	}
}

function dateCompare(startStr,endStr)
{
 var d1, d2, s, arr, arr1, arr2;
 if(startStr.length > 10)
 {
    arr = startStr.split(" ");
    arr1 = arr[0].split("-");
    arr2 = arr[1].split(":");
    d1 = new Date(arr1[0], arr1[1] - 1, arr1[2], arr2[0], arr2[1], arr2[2]);
 }
 else
 {
    arr = startStr.split("-");
    d1 = new Date(arr[0], arr[1], arr[2]);
 }
 if(endStr.length > 10)
 {
    arr = endStr.split(" ");
    arr1 = arr[0].split("-");
    arr2 = arr[1].split(":");
    d2 = new Date(arr1[0], arr1[1] - 1, arr1[2], arr2[0], arr2[1], arr2[2]);
 }
 else
 {
    arr = endStr.split("-");
    d2 = new Date(arr[0], arr[1], arr[2]);
 }
 
 s = d2 - d1 ;
 if(s < 0)
 {
    return false;
 }
 return true;
}

</script>
</body>