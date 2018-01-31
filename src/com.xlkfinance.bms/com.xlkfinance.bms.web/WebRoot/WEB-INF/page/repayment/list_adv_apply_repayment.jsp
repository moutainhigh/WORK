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
var projectId = ${projectId};
var projectNum = "${projectNum}";
var projName ="${projectName}";
var preRepayId=-1;
var advbutton =1;

$(function() {
    $("#repayDate").datebox("setValue",formatterDate(new Date()));
	$(":radio[name='isRebackInterest'][value='2']").attr("checked","checked");
	$(":radio[name='isArrears'][value='2']").attr("checked","checked");
	$(":radio[name='hasOtherLoan'][value='2']").attr("checked","checked");
});
	
// 文件操作列表格式化
var path = null;
function uploadadvoperat(value, row, index) {
	path = row.filePath;
	var downloadDom = "<a class='download' href='"+BASE_PATH+"beforeLoanController/downloadData.action?path="+row.filePath+"&fileName="+row.fileName+"'><font color='blue'>下载</font> | </a>";
	var downd = "<span  style='display: none;' id='"+row.pId+"'>"
			+ row.filePath
			+ "</span><a href='javascript:void(0)' onclick='delfilebypath("
			+ row.pId + ")'><font  color='blue'> 删除</font></a>";
			var  edit ="   <a href='javascript:void(0)' onclick='editfileinfo("+ JSON.stringify(row) + ")'><font  color='blue'> 编辑</font></a>";
	return downloadDom + downd+edit;
}

// 修改文件窗口打开
function editfileinfo(row){
	$("#editfileId").val(row.pId);
	$("#editfileKind").val(row.fileFunType);
	$("#editfileDesc").val(row.fileDesc); 
	$('#upload-edit').dialog('open').dialog('setTitle', "修改上传信息");
}

// 日期格式化
function dateupSplit(value, row, index) {
	if (null != row.uploadDttm && "" != row.uploadDttm) {
		return row.uploadDttm.split(" ")[0];
	}
	return row.uploadDttm;
}


//上传按钮
function uploadrepayfile() {
	$("#fileDesc").val("");
	$("#file1").val("");
	$("#txt1").val("");
	$('#upload').dialog('open').dialog('setTitle', "资料上传");
	$("#upload").dialog("move",{top:$(document).scrollTop()+260*0.5}); 
}

// 提前还款登记信息保存
function advRepaybutton() {
	if(!$("#advRepaybutton").form('validate')){
		return ;
	}
	
	// 根据项目ID判断是否有流程在执行
	if(preRepayId == -1){
		if(isWorkflowByStatus(projectId)){
			return;
		}
	}
	
	 
	
	
	var preRepayAmt = $("#preRepayAmt").textbox("getValue"); 
	var fineRates = $("#fineRates").textbox("getValue"); 
	var fine = $("#fine").textbox("getValue"); 
	var isArrears = $("input[name='isArrears']:checked").val();
	var isRebackInterest = $("input[name='isArrears']:checked").val();
	var hasOtherLoan = $("input[name='isArrears']:checked").val();
	var reason = $("#reason").val();
	
	var surplus = $("#surplus").textbox("getValue"); 
	var repayDate = $("#repayDate").datebox("getValue"); 
	var planRepayLoanDt = $("#planRepayLoanDt").datebox("getValue");
	var	principalBalance=$("#principalBalance").val();
	loanId=${loanId};
	
	// 提前还款日期不能大于剩余本金还款日期
	if(repayDate >= planRepayLoanDt){
		$.messager.alert("系统提示","提前还款日期不能大于剩余本金还款日期！","error"); 
		return ;
	}

	var url = "insertadvApplyrepayment.action";
	if(preRepayId != -1 ){
		url = "updateadvApplyrepayment.action";
	}
	$.post(url, {
		preRepayAmt : string.replaceAll(preRepayAmt),
		fineRates : fineRates,
		fine : string.replaceAll(fine),
		isArrears : isArrears,
		isRebackInterest : isRebackInterest,
		hasOtherLoan : hasOtherLoan,
		reason : reason,
		surplus:string.replaceAll(surplus),
		loanId:loanId,
		preRepayId : preRepayId,
		repayDate : repayDate,
		projectId:projectId
	}, function(data) {
		if(data>0){
			if(preRepayId == -1){
				preRepayId=data;
				$.messager.alert("系统提示","保存成功！","info"); 	
			}else{
				$.messager.alert("系统提示","更新成功！","info"); 	
			}
			advbutton = 2 ;
		} else{
			$.messager.alert("系统提示","系统异常!","error"); 	
		}
	});
	
}

// 删除文件
function delfilebypath(dta) {
	$.messager.confirm("操作提示","确定删除选中文件吗?",
		function(r) {
			if(r){
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

// 选择删除
function delAnyData() {
	var row = $('#up_data').datagrid('getSelections');
	if (row.length == 0) {
		$.messager.alert("操作提示", "请选择数据", "error");
		return;
	}// 获取选中的系统用户名 
	var pIds = "";
	for (var i = 0; i < row.length; i++) {
		pIds += row[i].pId;
		pIds += ",";
	}
	var str = pIds.substring(0, pIds.length-1); // 取字符串。  
	delfilebypath(str);
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
	var preId=preRepayId;
	$('#fileUploadForm').form('submit', {
		success : function(data) {
			var str = data.substring(1,(data.length-1));
			$.messager.alert("操作提示", str, "info");
			$("#up_data").datagrid("reload");
			$('#upload').dialog('close');
		}
	});
	var fileurl="${basePath}rePaymentController/queryRegAdvapplyFile.action?preRepayId="+preId;
	$('#up_data').datagrid({
	    url:fileurl
	});
	$('#upload').dialog('close');
}
function uploadType(value, row, index) {
	return "提前还款申请资料";
}

// 重新生成还款计划表按钮事件
function insertRepayPlan(){
	if(preRepayId>0){
		operRepaymentList("${basePath}rePaymentController/advmakeRepayMent.action?projectId="+projectId+"&preRepayId="+preRepayId);
	}else{	
		$.messager.alert("操作提示", "你需要先保存提前还款登记信息！", "error");
	}
}

//文件大小格式化
function sizeFileter(value,row,index){
	if(row.fileSize>0){
		return  parseInt(row.fileSize/1024)+" KB";
	}else{
		return '';
	}
}

// 打开还款计划表窗口
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

/**
 * 日期格式化
 */
function formatterDate(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day;
}

/**
 * 金额格式还原
 */
var string = {
		replaceAll:function(parm){
			return parm.replace(new RegExp(/(,)/g),'');
		}
}

/**
 * 提前还款罚金 = 提前还款金额*提前还款罚金比例/100
 */
function fineRate(){
	var preRepayAmt = string.replaceAll($("#preRepayAmt").textbox("getValue")); 
	var fineRates = $("#fineRates").textbox("getValue"); 
	$("#fine").textbox("setValue",accounting.formatMoney(((preRepayAmt*fineRates)/100), "", 2, ",", "."));	
}

/**
 * 提前还款金额onchange 事件
 */
function fineRatesAmt(){
	var preRepayAmt = string.replaceAll($("#preRepayAmt").textbox("getValue")); 
	if(preRepayAmt == ''){
		return ;
	}
	// 验证是否超出最大金额
	if(preRepayAmt > 500000000){
		$.messager.alert("操作提示", "你输入的金额大于系统最大限额!请重新输入！","error");
		$("#preRepayAmt").textbox("setValue",0);
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
		$("#preRepayAmt").textbox("setValue",0);
		return false;	
	}
	
	if($("#preRepayAmt").textbox("getValue") > principalBalance){
		$.messager.alert("操作提示", "你输入的金额大于本金余额!请重新输入！","error");
		$("#preRepayAmt").textbox("setValue",0);
		$("#surplus").textbox("setValue",0);
		return false;
	}
	
	// 计算 提前还款罚金
	fineRate();
	
	// 格式化提前还款金额
	$("#preRepayAmt").textbox("setValue",accounting.formatMoney(preRepayAmt, "", 2, ",", "."));
	
	// 设置提前还款后的剩余贷款本金 贷款剩余金额 - 提前还款金额
	$("#surplus").textbox("setValue",accounting.formatMoney((principalBalance-preRepayAmt), "", 2, ",", "."));
}

 // 提前还贷日期更改事件
 function  checkrepayDate(bdate,edate){
	var repayDate = $("#repayDate").datebox("getValue"); 
	var rows = $("#loanCaculateGrid").datagrid("getRows");
	var  dt=null;
	for(var i=0;i<rows.lenght;i++){
		if(rows[i].isReconciliation != 1){
			dt= rows[i-1].planRepayDt;
			break;
		}
	}
	if(dateCompare(repayDate,dt)==false){
	 	$("#repayDate").datebox("setValue",''); 
	 	$.messager.alert("系统提示","日期不能少于最后一次对账日期","warning"); 
	};
 }


function dateCompare(startStr,endStr){
	  var d1, d2, s, arr, arr1, arr2;
	  if(null!=startStr||null!=endStr){
		  return true; 
	  }
	  if(null!=startStr&&startStr.length > 10){
	     arr = startStr.split(" ");
	     arr1 = arr[0].split("-");
	     arr2 = arr[1].split(":");
	     d1 = new Date(arr1[0], arr1[1] - 1, arr1[2], arr2[0], arr2[1], arr2[2]);
	  }else{
	     arr = startStr.split("-");
	     d1 = new Date(arr[0], arr[1], arr[2]);
	  }
	  
	  if(null!=endStr&&endStr.length > 10){
	     arr = endStr.split(" ");
	     arr1 = arr[0].split("-");
	     arr2 = arr[1].split(":");
	     d2 = new Date(arr1[0], arr1[1] - 1, arr1[2], arr2[0], arr2[1], arr2[2]);
	  }else{
	     arr = endStr.split("-");
	     d2 = new Date(arr[0], arr[1], arr[2]);
	  }
	  
	  s = d2 - d1 ;
	  if(s < 0){
	     return false;
	  }
	  return true;
 }
	
	// 文件上传保存
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
	
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-panel" title="基本项目信息" data-options="collapsible:true">
	<div style="padding:5px">
		<table>
			<tr>
				<td height="35" width="300" align="center">项目编号:${projectNum}</td>
				<td>项目名称:<a onclick="formatterProjectName.disposeClick(${projectId},'${projectNum}')" href='#'><span style='color:blue;'>${projectName}</span></a></td>
			</tr>
		</table>
	</div>
</div>
<div id="roleDiv" style="width: 100%;">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	</div>

</div>
<div class="easyui-panel" title="贷款收息表" data-options="collapsible:true">
<div id="tablegrid" class="pt10"  >
<%@ include file="list_loanCalculate.jsp"%> 
</div>
</div>
<!-- 提前还款登记信息 -->
<div class="pt10">
<div class="easyui-panel" title="提前还款登记信息" data-options="collapsible:true" >
	<!-- 查询条件 -->
	<form action="insertadvApplyrepayment.action" method="post"
		id="advRepaybutton" enctype="multipart/form-data">
		<div style="padding: 5px">
			<table>
				<tr>
					<td class="label_right" style="width:180px"><font color="red">*</font>提前还贷金额:</td>
					<td><input class="easyui-textbox" missingMessage="请输入提前还贷金额!" required="true" name="preRepayAmt" id="preRepayAmt"  data-options="onChange:fineRatesAmt"  /></td>
					<td class="label_right" style="width:180px">本次还款后的剩余贷款本金: </td>
					<td>
						<input class="easyui-textbox" name="surplus" id="surplus"   editable="false"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>提前还款罚金比例 （%）:</td>
					<td><input class="easyui-textbox" missingMessage="请输入提前还款罚金比例!" required="true" name="fineRates" id="fineRates" data-options="onChange:fineRate" value="${Rarv.prepayLiqDmgProportion}"/></td>
					<td class="label_right">提前还款罚金:</td>
					<td><input class="easyui-textbox" name="fine" id="fine"   editable="false" /></td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>提前还贷日期:</td>
					<td><input class="easyui-datebox" missingMessage="请输入提前还贷日期!" required="true" name="repayDate" id="repayDate" editable="false" /></td>
					<td class="label_right">剩余本金还款日期:</td>
					<td><input class="easyui-datebox" name="planRepayLoanDt" id="planRepayLoanDt"  value="${Rarv.planRepayLoanDt}" readonly="readonly"/></td>
				</tr>
				<tr>
					<td class="label_right">原借款金额：</td>
					<td> <input class="easyui-textbox" name="creditAmt" id="roleName" value="${Rarv.creditAmtTemp}" readonly="readonly" /></td>
					<td class="label_right">原授信合同编号：</td>
					<td>
						<input class="easyui-textbox" name="CreditContractNo"
							id="CreditContractNo" value="${Rarv.contractCreditNo}"
							readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label_right">原借款期限 : </td>
					<td><input class="easyui-textbox" name="repayCycle"	id="roleName" value="${Rarv.repayCycle}" readonly="readonly" /></td>
					<td class="label_right">原借款种类:</td>
					<td>
							<input class="easyui-textbox" name="businessName" id="businessName"
										value="${Rarv.businessName}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label_right">原借款合同编号 : </td>
					<td><input class="easyui-textbox" name="BrocontractNo" id="BrocontractNo" value="${Rarv.contractLoadNo}" readonly="readonly" /></td>
					<td class="label_right">原借款月利率（%）：</td>
					<td>
						 <input class="easyui-textbox" name="monthLoanInterest"
							id="roleName" value="${Rarv.monthLoanInterest}"
							readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label_right">是否拖欠我公司款项：</td>
					<td colspan="3">
						<input type="radio" name="isArrears" value="1" />是&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="isArrears" value="2" />否
					</td>
				</tr>
				<tr>
					<td class="label_right">是否退还已收取的利息：</td>
					<td colspan="3">
						<input type="radio" name="isRebackInterest" value="1" />是&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="isRebackInterest" value="2" />否
					</td>
				</tr>
				<tr>
					<td class="label_right">在我公司是否有其他借款 ：</td>
					<td colspan="3">
						<input type="radio" name="hasOtherLoan" value="1" />是&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="hasOtherLoan" value="2" />否
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>申请原因：</td>
					<td colspan="3">
						<input name="reason" id="reason" required="true" missingMessage="请输入申请原因!"  class="easyui-textbox" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,500]'">
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center" height="40">
						<a href="javascript:void(0);" class="easyui-linkbutton"
						onclick="advRepaybutton()">保&nbsp;&nbsp;存</a> &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <a href="javascript:void(0);"
						class="easyui-linkbutton" onclick="insertRepayPlan()">重新生成计划还款表</a>
					</td>
				</tr>
			</table>
			
			<div style="width: 500px; text-align: right;">
				<p>
					
				</p>
			</div>
		</div>
	</form>
</div>
</div>
<div class="pt10">
<div class="easyui-panel " title="提前还款申请表" data-options="collapsible:true" >	
	<div id="filetoolbar">
		&nbsp;&nbsp;<a href="javascript:void(0);"
			class="easyui-linkbutton" data-options="iconCls:'icon-add'"  plain="true" onclick="uploadrepayfile()">上传</a> <a
			href="javascript:void(0);" data-options="iconCls:'icon-remove'" plain="true" class="easyui-linkbutton"
			onclick="delAnyData()">删除</a>
	</div>
	
	<!-- 新增文件 -->
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
			<p style="display: none;">
				<input name="fileKinds" id="selectAge" value="1" type="hidden"/>
				<input name="preRepayId" id="preRepayId"  />
			</p>
			<p>
				选择文件：<input class="input_text" type="text" id="txt1" name="fileToUpload" /> 
				<input type="button" name="uploadfile2" id="uploadfile2" style="padding-left: 10px;" value="浏览..." /> 
				<input class="input_file" size="30" type="file" name="file1" id="file1" onchange="txt1.value=this.value" />
			</p>
			<p>
				<span style="margmargin-top: 10px;">上传说明:</span>
				<textarea rows="4" style="width: 300px;" id="fileDesc" name="fileDesc"></textarea>
			</p>
		</form>
	</div>
	
	<!-- 编辑文件 -->
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
			
			<input id="preRepayIdedit" name="preRepayId"  type="hidden"/>
			<input name="fileId" id="editfileId" type="hidden" />
			<input name="fileKinds" id="selectAge" value="1" type="hidden"/>
			<p>
				<span style="margmargin-top: 10px;">上传说明:</span>
				<textarea rows="4" style="width: 300px;" id="editfileDesc" name="fileDesc"></textarea>
			</p>
		</form>
	</div>
	
	<!-- 提前还款申请表 -->
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
					<th data-options="field:'pId',checkbox:true"></th>
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
	<div class="panel-body pt10" style="border-width:2px;border-top:none;">
		<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
			<div style="padding:5px">
				<%@ include file="../common/loanWorkflow.jsp"%>
				<%@ include file="../common/task_hi_list.jsp"%>
			</div>
		</div>
	</div>
</div>

</body>