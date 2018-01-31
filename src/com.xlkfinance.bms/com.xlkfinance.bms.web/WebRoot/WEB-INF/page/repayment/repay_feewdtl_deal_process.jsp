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
</style>

<script type="text/javascript" src="${ctx}/p/xlkfinance/module/repayment/repay_feewdtl_deal.js"></script>
<script type="text/javascript">
/**************工作流字段 begin******************/
//费用减免工作流
var WORKFLOW_ID = "feeWaiversRequestProcess"; 
workflowTaskDefKey = "task_FeeWaiversRequest";
/**************工作流字段 end********************/
var projectId = "${projectId}";
var projectNum = "${projectNum}";
var repayId= "${repayId}";//费用减免id
var projName ="${projectName}";
var projectName='';
var loanId="${loanId}";

$(function() {
	var tab = parent.$('#layout_center_tabs').tabs('getSelected');
	var atTitle = tab.panel('options').title;
	if(atTitle=="费用减免查看"){
		projectName='${projectName}';
		$('#savefeewinfo').attr('disabled','disabled');
		$('#savefeewinfo').attr('readOnly','readOnly');
		$('#savefeewinfo').addClass('l-btn-disabled');
		$('#savefeewinfo').removeAttr('onclick');
		
		$('#makeapply').attr('disabled','disabled');
		$('#makeapply').attr('readOnly','readOnly');
		$('#makeapply').addClass('l-btn-disabled');
		$('#makeapply').removeAttr('onclick');
		
		//费用减免按钮
		$('#openfeewinfo').attr('disabled','disabled');
		
	}
	if(atTitle=="业务主管审核" ||atTitle=="财务确认"||atTitle=="总经理审批"){
		$('#savefeewinfo').attr('disabled','disabled');
		$('#savefeewinfo').attr('readOnly','readOnly');
		$('#savefeewinfo').addClass('l-btn-disabled');
		$('#savefeewinfo').removeAttr('onclick');
		
		$('#makeapply').attr('disabled','disabled');
		$('#makeapply').attr('readOnly','readOnly');
		$('#makeapply').addClass('l-btn-disabled');
		$('#makeapply').removeAttr('onclick');
		
		$('#uploadrepayfile').attr('disabled','disabled');
		$('#uploadrepayfile').attr('readOnly','readOnly');
		$('#uploadrepayfile').addClass('l-btn-disabled');
		$('#uploadrepayfile').removeAttr('onclick');
		
		$('#delecta').attr('disabled','disabled');
		$('#delecta').attr('readOnly','readOnly');
		$('#delecta').addClass('l-btn-disabled');
		$('#delecta').removeAttr('onclick');
		//费用减免按钮
		$('#openfeewinfo').attr('disabled','disabled');
		$('#openfeewinfo').attr('readOnly','readOnly');
		$('#openfeewinfo').addClass('l-btn-disabled');
		$('#openfeewinfo').removeAttr('onclick');
		
		//附件上传的编辑按钮
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

});
	function uploadrepayfile() {
		$('#fileUploadForm').form('reset');
		$('#upload').dialog('open').dialog('setTitle', "修改模板文件路径");
	}
	function dispaly(dta) {
		if (dta == 1) {
			$("#tablegrid").show();
		}
		if (dta == 2) {
			$("#tablegrid").hide();
		}
		if (dta == 3) {
			$("#uploadpage").show();
			window.location.hash = "#uploadpage1";
		}
		if (dta == 4) {
			$("#uploadpage").hide();
		}
	}

	function exeSubmit() {
		var applyProgress = $("#applyProgress").val();
		var applyReason = $("#applyReason").val();
		var remind = [];
		$("#executiveOper  :checked").each(function(i) {
			remind.push($(this).val());
		});
		var remindType = remind.join("-");
		$.post("executiveOperation.action", {
			applyProgress : applyProgress,
			applyReason : applyReason,
			remindType : remindType
		}, function(data) {
		}, 'json');
	}
	
	function displayimg() {
		$("#divimg").hide();
	}
	function advRepaybutton() {
		var preRepayAmt = $("#preRepayAmt").val();
		var fineRates = $("#fineRates").val();
		var fine = $("#fine").val();
		var isArrears = $("input[name='isArrears']:checked").val();
		var isRebackInterest = $("input[name='isArrears']:checked").val();
		var hasOtherLoan = $("input[name='isArrears']:checked").val();
		var reason = $("#reason").val();
		var repayDate = $("#repayDate").val();
		$.post("insertadvApplyrepayment.action", {
			preRepayAmt : preRepayAmt,
			fineRates : fineRates,
			fine : fine,
			isArrears : isArrears,
			isRebackInterest : isRebackInterest,
			hasOtherLoan : hasOtherLoan,
			reason : reason,
			repayDate : repayDate
		}, function(data) {
		});
	}
	function uploadapply() {
		$("#uploadadvapply").submit();
	}
	function selectAll() {
		$("#applyrepayfile").click(function() {
			if (this.checked) {
				$("input[name='applyrepayfile']").each(function() {
					this.checked = true;
				});
			} else {
				$("input[name='applyrepayfile']").each(function() {
					this.checked = false;
				});
			}
		});
	}

	function delectable() {
		var param = [];
		$("input[name='applyrepayfile']").each(function() {
			param.push($(this).val());
		});

		$.post("delectapplyrepay.action", {
			applyProgress : applyProgress,
			applyReason : applyReason,
			remindType : remindType
		}, function(data) {
		}, 'json');

	}
	function dateupSplit(value, row, index) {
		if (null != row.uploadDttm && "" != row.uploadDttm) {
			return row.uploadDttm.split(" ")[0];
		}
		return row.uploadDttm;
	}
</script>

<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<!-- 基本项目信息 -->
		<div class="easyui-panel" title="基本项目信息" data-options="collapsible:true">
			<div style="padding:5px">
				<table>
					<tr>
						<td height="35" width="300" align="center">项目编号:${projectNum}</td>
						<td><div class="iniTitle">项目名称:<a onclick='disposeClick(${projectId})' href='#'><span style='color:blue;'>${projectName}</span></a></div></td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 利息信息-->
		<div class="pt10"></div>
		<div class="easyui-panel" title="利息信息" data-options="collapsible:true">
			<div id="tablegrid" class="pt10"  >
			<span><a href="javascript:void(0);" id="openfeewinfo" class="easyui-linkbutton" onclick="RepayFeewdtl.feewdtlckClick()">费用减免</a></span>
				<%@ include file="list_loanCalculate.jsp" %>
			</div>
		</div>

		<!-- 费用减免信息 -->
		<!-- <div class="pt10"></div> -->
		<div class="easyui-panel" title="费用减免信息" data-options="collapsible:true">
			<form name="free_info_Form" >
				<div class="free_info_txt">
					<table width="100%">
						<tr>
							<td width="90">费用减免原因：</td>
							<td>
								<div style="width:100%">
									<textarea id="reason" name="reason" class="text_style" style="width:98%;height:50px"></textarea>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<table id="free_info_grid" class="free_info"></table>
				<div class="free_info_button"> 
					<a class="long_text_btn"  id="savefeewinfo" onclick="savefeewinfo()">保存减免信息</a>
					<a class="long_text_btn"  id="makeapply"  style="width: 180px"  onclick="makefeewinfapply()">生成费用减免申请书</a>
				</div>
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
				action="${basePath}repayFeewdtlController/updateloadfileinfo.action" method="post"
				enctype="multipart/form-data" target="hidden_frame">
				<p style="display: none;">
					<input id="repayIdedit" name="repayId"  />
					<input name="fileId" id="editfileId"  />
				</p>
				<p>
					<span style="margmargin-top: 10px;">上传说明:</span>
					<textarea rows="4" style="width: 300px;" id="editfileDesc" name="fileDesc"></textarea>
				</p>
			</form>
		</div>
	
		<!-- 费用减免文件上传 -->
		<div style="padding-top:10px"></div>
		<div class="easyui-panel" title="费用减免文件上传" data-options="collapsible:true">
			<div id="upToolbar">
				<a href="javascript:void(0);" class="easyui-linkbutton" id="uploadrepayfile" iconCls="icon-add" plain="true" onclick="uploadrepayfile()">上传</a> 
				<a href="javascript:void(0);" class="easyui-linkbutton" id="delecta" iconCls="icon-remove" plain="true" onclick="delecta()">删除</a>
			</div>
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
					<p style="display: none;">
						<input name="repayId" id="repayId"  />
					</p>
					<p>
						选择文件：<input class="input_text" type="text" id="txt1"
							name="fileToUpload" /> <input type="button" name="uploadfile2"
							id="uploadfile2" style="padding-left: 10px;" value="浏览..." /> <input
							class="input_file" size="30" type="file" name="file1" id="file1"
							onchange="txt1.value=this.value" />
					</p>
					<p>
						<span style="margmargin-top: 10px;">上传说明:</span>
						<textarea rows="4" style="width: 300px;" name="fileDesc"></textarea>
					</p>
				</form>
			</div>
			<!-- queryRegAdvapplyFile.action?preRepayId='+preRepayId -->
			<div
				style="width: 100%; text-align: center;">
				<table id="fee_file_list_grid" class="easyui-datagrid"
					data-options="
				    url: '${basePath}repayFeewdtlController/queryRegFeewapplyFile.action',
				    queryParams:{repayId : repayId},
				    method: 'POST',
				    idField: 'pId',
				    fitColumns:true,
				    rownumbers: true,
				    singleSelect: false,
				    pagination: true,
				    sortOrder:'asc',
				    remoteSort:false,
				    onLoadSuccess:function(data){
						clearSelectRows('#fee_file_list_grid');
					}, 
				    toolbar: '#upToolbar'
				    ">
					<!-- 表头 -->
					<thead>
						<tr>
							<th data-options="field:'pId',checkbox:true"></th>
							<th data-options="field:'fileType',width:100">文件类型</th>
							<th data-options="field:'fileFunType',width:150,formatter:uploadType">文件种类</th>
							<th data-options="field:'fileName',width:150">文件名称</th>
							<th data-options="field:'fileSize',width:150,formatter: sizeFileter">大小</th>
							<th data-options="field:'uploadDttm',width:150,formatter:dateupSplit">上传时间</th>
							<th data-options="field:'fileDesc',width:150">上传说明</th>
							<th data-options="field:'yyyyy',width:300,formatter:uploadadvoperat">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<div onclick="displayimg()" id="divimg"
			style="display: none; position: absolute; z-index: 100;">
			<img id="img" style="width: 386px; height: 300px;" />
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
<script type="text/javascript">
	function delecta() {
		var row = $('#fee_file_list_grid').datagrid('getChecked');
	 	if ( row.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}
	 	var pIds = "";
	 	
		for(var i=0;i<row.length;i++){
			pIds+=row[i].pId;
			pIds+=",";
	  	}
		var str = pIds.substring(0, pIds.length-1); // 取字符串。
		$.messager.confirm("操作提示", "确定删除选择的此批系统用户吗?", function(r) {
			if (r) {
				$.post("${basePath}rePaymentController/delectUpdateAdvapply.action", {
					pId:str
				}, function(data) {
					//是否删除
					var msg = "确定要删除?"
						if(confirm(msg)){
							$("#fee_file_list_grid").datagrid('load',{
								repayId : repayId
							});
						}
				}); 
			}
		});
	}

	var path = null;
	function uploadadvoperat(value, row, index) {
		path = row.filePath;
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var atTitle = tab.panel('options').title;
		var downloadDom =null;
		var downd =null;
		var  edit =null; 
		if(atTitle=="费用减免查看" || atTitle=="业务主管审核" ||atTitle=="财务确认"||atTitle=="总经理审批"){
		 downloadDom = "<a href='${basePath}beforeLoanController/downloadData.action?path="
				+ row.filePath + "&fileName="+row.fileName+"'><font color='blue'>点击下载</font></a> ";
		 downd = "<span  style='display:none;' id='"+row.pId+"'>"
				+ row.filePath
				+ "</span><a href='javascript:void(0)' readOnly><font class='l-btn-disabled'  > 删除</font></a>";
		  edit ="<a href='javascript:void(0)' readOnly><font class='l-btn-disabled'> 编辑</font></a>";
		}else{
		 downloadDom = "<a href='${basePath}beforeLoanController/downloadData.action?path="
				+ row.filePath + "&fileName="+row.fileName+"'><font color='blue'>点击下载</font></a> ";
		 downd = "<span  style='display:none;' id='"+row.pId+"'>"
				+ row.filePath
				+ "</span><a href='javascript:void(0)' onclick='delfilebypath("
				+ row.pId + ")'><font  color='blue'> 删除</font></a>";
		  edit ="   <a href='javascript:void(0)' onclick='editfileinfo("+ JSON.stringify(row) + ")'><font  color='blue'> 编辑</font></a>";
		}
		
		return downloadDom + downd+edit;
	}
	function editfileinfo(row){
		$("#editfileId").val(row.pId);
		$("#editfileKind").val(row.fileFunType);
	  $("#editfileDesc").val(row.fileDesc); 
	$('#upload-edit').dialog('open').dialog('setTitle', "修改上传信息");
}
	function delfilebypath(dta) {
		//是否确认删除
		$.post("${basePath}rePaymentController/delectUpdateAdvapply.action", {
			pId:dta
		}, function(data) {
		   
			var msg = "确定要删除?"
				if(confirm(msg)){
					$("#fee_file_list_grid").datagrid('load',{
						repayId : repayId
					});
				}
			
		}); 
	}
	//上传文件
	function submitForm() {
		if(repayId==-1){
			$.messager.alert("操作提示", "你需要先保存提前还款登记信息！", "error");
			return false;
		}
		var fileName = $("#file1").val();
		if (fileName == "" || null == fileName) {
			$.messager.alert("操作提示", "请选择要上传的文件！", "error");
			return false;
		}
		$('#repayId').val(repayId);
		$('#fileUploadForm').form('submit', {
			success : function(data) {
				data = eval('('+data+')');
				if(data){
					if(data.falg=="false"){
						$.messager.alert("操作提示", data.manager, "error");
					}else{
						$.messager.alert("操作提示", data.manager, "into");
						$("#fee_file_list_grid").datagrid('load',{
							repayId : repayId
						});
					}
				}
				$('#upload').dialog('close');
			}
		});
	}
	function uploadType(value, row, index) {
		if (1 == row.fileFunType) {
			return "营业执照";
		}
		if (2 == row.fileFunType) {
			return "调查资料";
		}
		return "费用减免";
	}
	function dateupSplit(value, row, index) {
		if (null != row.uploadDttm && "" != row.uploadDttm) {
			return row.uploadDttm.split(" ")[0];
		}
		return row.uploadDttm;
	}

$(function(){
	loadFeewReasonByprocess();

	 $.post("${basePath}repayFeewdtlController/queryRegFeewReasonbyprocess.action", {
			repayId: repayId
		}, function(data) {
			var str =data.replace('"','');
			str=str.replace('"','')
			if(str==null||str==''||str=='null'){
				$("#reason").val('');
			}else{
				$("#reason").val(str);
			}
			
		});  
});
/**
 * 加载期数数据
 */
function loadFeewReasonByprocess(){
	 $.post("${basePath}repayFeewdtlController/queryRegFeewDealbyprocess.action", {
			repayId: repayId
		}, function(data1) {
			$('#free_info_grid').datagrid('loadData', eval("{" + data1 + "}"));
			
		}); 
}
</script>
 <script>
		var grid;
		var editIndex = undefined;
		$.extend($.fn.datagrid.methods, {
			 editCell: function(jq,param){
				 return jq.each(function(){
				 	var opts = $(this).datagrid('options');
				 	var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor1 = col.editor;
						if (fields[i] != param.field){
							col.editor = null;
						}
					}
				 	$(this).datagrid('beginEdit', param.index);
				 	for(var i=0; i<fields.length; i++){
				 		var col = $(this).datagrid('getColumnOption', fields[i]);
				 		col.editor = col.editor1;
				 	}
				 });
			 }
		});
		function onClickCellsIt(rowIndex, field, value){
			if (endEditing()){
				var rows = grid.datagrid('getRows');
				for(var i=0;i<rows.length;i++){
					if(((i+1)%3)/2==1){
						var shouldInterest=Number(rows[i-1]['shouldInterest'])-Number(rows[i]['shouldInterest']).toFixed(2);
						var shouldMangCost=Number(rows[i-1]['shouldMangCost'])-Number(rows[i]['shouldMangCost']).toFixed(2);
						var shouldOtherCost=Number(rows[i-1]['shouldOtherCost'])-Number(rows[i]['shouldOtherCost']).toFixed(2);
						var overdueFine=Number(rows[i-1]['overdueFine'])-Number(rows[i]['overdueFine']).toFixed(2);
						var overdueInterest=Number(rows[i-1]['overdueInterest'])-Number(rows[i]['overdueInterest']).toFixed(2);
						var total=Number(Number(rows[i]['shouldPrincipal'])+Number(rows[i]['shouldInterest'])+Number(rows[i]['shouldMangCost'])+Number(rows[i]['shouldOtherCost'])+Number(rows[i]['overdueInterest'])+Number(rows[i]['overdueFine'])).toFixed(2);
						var total1=Number(Number(rows[i+1]['shouldPrincipal'])+shouldInterest+shouldMangCost+shouldOtherCost+overdueInterest+overdueFine).toFixed(2);
						// 修改
						grid.datagrid('updateRow',{
							 index:i,
							 row:{'total':total}
						 });
						grid.datagrid('updateRow',{
							 index:i+1,
							 row:{'shouldInterest':shouldInterest,'shouldMangCost':shouldMangCost,'shouldOtherCost':shouldOtherCost,'overdueInterest':overdueInterest,'overdueFine':overdueFine,'total':total1}
						 });
					}
				}
				if(((rowIndex+1)%3)/2==1){
					editIndex = rowIndex;					
					grid.datagrid('selectRow', rowIndex).datagrid('editCell', {index:rowIndex,field:field});	
				}else{return false;}				
			}
		}
		
		function endEditing(){
			 if (editIndex == undefined){return true;}
			 if (grid.datagrid('validateRow', editIndex)){
				 grid.datagrid('endEdit', editIndex);
				 grid.datagrid('unselectRow', editIndex);
				 editIndex = undefined;
				
				 return true;
			 } else {
			 	return false;
			 }
		 }
		$(document).ready(function(){
			grid=$("#free_info_grid").datagrid({				
				collapsible: true,
				singleSelect: true,
			    columns:[[
			        {field:'planCycleNum',title:'期数',width:40,align:'center'},
			        {field:'typeName',title:'类别',width:100,align:'center'},
			        {field:'shouldPrincipal',title:'本金',width:100,align:'center',formatter:formatMoney},
			        {field:'shouldInterest',title:'利息',width:100,align:'center',formatter:formatMoney,
			        	editor:{type:'numberbox',options:{precision:2,groupSeparator:','}} },
			        {field: 'shouldMangCost',
			        	title:'管理费',
			        	width:140,
			        	align:'center',
			        	formatter:formatMoney,
			        	editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}},
				    {field: 'shouldOtherCost',
				        	title:'其他费用',
				        	width:140,
				        	align:'center',
				        	formatter:formatMoney,
				        	editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}},
					{field: 'overdueInterest',
					        	title:'逾期利息',
					        	width:140,
					        	align:'center',
					        	formatter:formatMoney,
					        	editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}},
					{field: 'overdueFine',
				        	title:'逾期罚息',
				        	width:140,
				        	align:'center',
				        	formatter: formatMoney},
			        {field:'total',title:'合计',width:180,align:'center',formatter:formatMoney}
			    ]],
			   rowStyler:freeInfoStyle,
				onClickCell: onClickCellsIt
			}); 
		});
		function freeInfoStyle(index,row){
			
			if ((index!=0&&(index+1)%3==0)){
				return 'font-weight:bold;';
			}
		}
 function savefeewinfo(){
	var tab = parent.$('#layout_center_tabs').tabs('getSelected');
	var atTitle = tab.panel('options').title;
	if(atTitle=="费用减免查看"){
		$.messager.alert("系统提示","此流程已在审核不能修改数据","warning"); 	
		return false;
	}
	var reason=$("#reason").val();
	var row =$("#free_info_grid").datagrid("getRows");
	var rowData = [];
	for (var i = 1; i < row.length; i=(i+3)) {
		if("减免"==row[i].typeName){
			$("#free_info_grid").datagrid("endEdit",i);
			rowData[rowData.length] = row[i];
		}
	}
	var datViews = JSON.stringify(rowData);
 	$.post("${basePath}repayFeewdtlController/updateRegFeewDealbyprocess.action", {
		 reason: reason,
		 datViews: datViews,
		 repayId: repayId
		}, function(data) {
			loadFeewReasonByprocess();
			$.messager.alert("操作提示","更新成功","info");
		}); 
}
function disposeClick(pid) {
	url = "${basePath}beforeLoanController/addNavigation.action?projectId="+pid+"&param='refId':'"+pid+"','projectName':'"+projName+"'";
	parent.openNewTab(url,"贷款申请详情",true);
} 
function sizeFileter(value,row,index){
	if(row.fileSize>0){
		return  parseInt(row.fileSize/1024)+" KB";
	}else{
		return '';
	}
}
function updatefileinfo(){
	var editfileDesc = $('#editfileDesc').val();
	var editfileId = $('#editfileId').val();
	$.post("${basePath}repayFeewdtlController/updateloadfileinfo.action", {
		repayId : repayId,
		fileDesc : editfileDesc,
		fileId : editfileId
	}, function(data) {
		$("#fee_file_list_grid").datagrid('load',{
			repayId : repayId
		});
		$('#upload-edit').dialog('close');
	}, 'json');
}

/**
 * 费用减免申请书
 */
function  makefeewinfapply(){
	if(repayId>0){
		window.location.href="${basePath}repayFeewdtlController/feeWaiverApplication.action?repayId="+repayId;
	}else{
		$.messager.alert("操作提示", "请您先保存费用减免信息！", "info");
	}
}
</script>


