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
//挪用处理申请
var WORKFLOW_ID = "misappropriateRequestProcess"; 
var workflowTaskDefKey = "task_PrepaymentRequest";
nextRoleCode = "BIZ_DIRECTOR";//
/**************工作流字段 end********************/
	var projectId = ${projectId};
	var projectNum = "${projectNum}";
	var divertId = ${divertId};
	var divertProjectName ="${projectName}";
	var projectName='';

	$(function() {
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var atTitle = tab.panel('options').title;
		if(atTitle=="挪用处理查看"){
			projectName='${projectName}';
			$('#savebutton').attr('disabled','disabled');
			$('#savebutton').attr('readOnly','readOnly');
			$('#savebutton').addClass('l-btn-disabled');
			$('#savebutton').removeAttr('onclick');
			
			$('#planbutton').attr('disabled','disabled');
			$('#planbutton').attr('readOnly','readOnly');
			$('#planbutton').addClass('l-btn-disabled');
			$('#planbutton').removeAttr('onclick');
			
			$('#uploadbutton').attr('disabled','disabled');
			$('#uploadbutton').attr('readOnly','readOnly');
			$('#deletes').attr('disabled','disabled');
			$('#deletes').attr('readOnly','readOnly');
			$('#uploadbutton').addClass('l-btn-disabled');
			$('#uploadbutton').removeAttr('onclick');
			
			$('#delectbutton').attr('disabled','disabled');
			$('#delectbutton').attr('readOnly','readOnly');
			$('#delectbutton').addClass('l-btn-disabled');
			$('#delectbutton').removeAttr('onclick');
			
			$('#applybutton').attr('disabled','disabled');
			$('#applybutton').attr('readOnly','readOnly');
			$('#applybutton').addClass('l-btn-disabled');
			$('#applybutton').removeAttr('onclick');
		}
		 if(atTitle=="业务主管审核" ||atTitle=="风控总监审查" ||atTitle=="财务确认"||atTitle=="总经理审批"){
				$('#savebutton').attr('disabled','disabled');
				$('#savebutton').attr('readOnly','readOnly');
				$('#savebutton').addClass('l-btn-disabled');
				$('#savebutton').removeAttr('onclick');
				
				$('#planbutton').attr('disabled','disabled');
				$('#planbutton').attr('readOnly','readOnly');
				$('#planbutton').addClass('l-btn-disabled');
				$('#planbutton').removeAttr('onclick');
				
				$('#uploadbutton').attr('disabled','disabled');
				$('#uploadbutton').attr('readOnly','readOnly');
				$('#deletes').attr('disabled','disabled');
				$('#deletes').attr('readOnly','readOnly');
				$('#uploadbutton').addClass('l-btn-disabled');
				$('#uploadbutton').removeAttr('onclick');
				
				$('#delectbutton').attr('disabled','disabled');
				$('#delectbutton').attr('readOnly','readOnly');
				$('#delectbutton').addClass('l-btn-disabled');
				$('#delectbutton').removeAttr('onclick');
				
				$('#applybutton').attr('disabled','disabled');
				$('#applybutton').attr('readOnly','readOnly');
				$('#applybutton').addClass('l-btn-disabled');
				$('#applybutton').removeAttr('onclick');
				
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
	//查看控制
	function lookfor(){
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var atTitle = tab.panel('options').title;
		if(atTitle=="挪用处理查看" || workflowTaskDefKey != "task_PrepaymentRequest"){
			$('.delRement font').attr('color','gray');
			$('.delRement').removeAttr('onclick');
			$('.embezzleInfo .easyui-linkbutton:not(.download) ,.embezzleInfo input,.embezzleInfo textarea').attr('disabled','disabled');
			$('.embezzleInfo .easyui-linkbutton:not(.download) ,.embezzleInfo input,.embezzleInfo textarea').attr('readOnly','readOnly');
			$('.embezzleInfo .easyui-linkbutton:not(.download) ,.embezzleInfo input,.embezzleInfo textarea').addClass('l-btn-disabled');
			$('.embezzleInfo .easyui-linkbutton:not(.download) ,.embezzleInfo a:not(.download)').removeAttr('onclick');
			$('.embezzleInfo a:not(.download) font').attr('color','gray');
		 
		}
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

	var path = null;
	function uploadadvoperat(value, row, index) {
		path = row.filePath;
		var downloadDom = "<a class='download' href='${basePath}beforeLoanController/downloadData.action?path="
				+ row.filePath +"&fileName="+row.fileName+"'><font color='blue'>点击下载</font></a> ";
				var downd = "<span  style='display: none;' id='"+row.pId+"'>"
				+ row.filePath
				+ "</span><a href='javascript:void(0)' class='delRement' onclick='delfilebypath("
				+ row.pId + ")'><font  color='blue'> 删除</font></a>";
				var  edit ="   <a href='javascript:void(0)' class='delRement' onclick='editfileinfo("+ JSON.stringify(row) + ")'><font  color='blue'> 编辑</font></a>";
		return downloadDom + downd+edit;
	}
	
	function editfileinfo(row){
		$("#editfileDesc").val(row.fileDesc); 
		$("#fileId").val(row.divertPid); 
		$('#upload-edit').dialog('open').dialog('setTitle', "修改上传信息");
	}
	
	function delfilebypath(dta) {
		if (confirm("是否确认删除?")) {
				$.post('${basePath}rePaymentController/delectUpdateAdvapply.action',{pId : dta,path : $("#" + dta).text()},
					function(data) {
						$.messager.alert("操作提示", "删除成功！", "success");
						$.post("${basePath}afterLoanDivertController/queryRepayDivertapplyFile.action",{divertId : divertId},
								function(dt) {
											$('#up_data').datagrid('loadData',eval("{" + dt + "}"));
								});
					});
		
		}	
	}
	
	//上传文件
	function submitForm() {
		var fileName = $("#file1").val();
		if (fileName == "" || null == fileName) {
			$.messager.alert("操作提示", "请选择要上传的文件！", "error");
			return false;
		}
		$("#divertId").val(divertId);
		$('#fileUploadForm').form('submit',{
							success : function(data) {
								//debugger;
								var str = data.substring(1,data.length-1);
								$.messager.alert("操作提示", str, "success");
								$('#upload').dialog('close')
								$.post("${basePath}afterLoanDivertController/queryRepayDivertapplyFile.action",{divertId : divertId},
												function(dt) {
													$('#up_data').datagrid('loadData',eval("{"+ dt+ "}"));
												});
							}
						});
	}

	function delDivert() {

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
		var str = pIds.substring(0, pIds.length - 1); // 取字符串。  
		$.post("${basePath}rePaymentController/delectUpdateAdvapply.action",{pId : str},
						function(data) {
							$.messager.alert("操作提示", "删除成功！", "success");
							loadFileTable();
						});
	}
	
	// 加载挪用文件列表
	function loadFileTable(){
		$.post("${basePath}afterLoanDivertController/queryRepayDivertapplyFile.action",{divertId : divertId},
				function(dt) {
							$('#up_data').datagrid('loadData',eval("{" + dt + "}"));
				});
	}
	
	function uploadType(value, row, index) {
		return "挪用处理资料";
	}
	
	function dateupSplit(value, row, index) {
		if (null != row.uploadDttm && "" != row.uploadDttm) {
			return row.uploadDttm.split(" ")[0];
		}
		return row.uploadDttm;
	}
	
	function editFile(){
		var editfileDesc = $("#editfileDesc").val();
		var pid = $("#fileId").val(); 
		$.ajax({
			type: "POST",
	        async:false, 
	        data:{"pId":pid,"fileDesc":editfileDesc},
	    	url : BASE_PATH+'afterLoanDivertController/updateEmbezzleFile.action',
			dataType: "json",
		    success: function(resultData){
		    	$.messager.alert("操作提示",resultData,"info");
				$('#upload-edit').dialog("close");
				loadFileTable();
			}
		});  
		
	}
</script>
<body  class="easyui-layout">
<div data-options="region:'center',border:false">
	<div id="embezzleInfo">
		<div class="easyui-panel" title="基本项目信息" data-options="collapsible:true">
			<table class="cus_table" style="min-width: 700px; border: none; width: 700px;">
				<tr>
					<td class="label_right">项目编号:</td>
					<td>${projectNum}</td>
					<td class="label_right">项目名称:</td>
					<td><a onclick='disposeClick(${projectId})' href='#'><span style='color:blue;'>${projectName}</span></a></td>
				</tr>
			</table>
		</div>
		<!-- 放款收息表 -->
		<div class="pt10"></div>
		<div class="easyui-panel" title="放款收息表快照" data-options="collapsible:true">
			<div id="tablegrid">
			<%@ include file="../repayment/list_loanCalculate.jsp"%>
			</div>
			<!-- 提前还款登记信息 -->
			<div style="width:100%">
			<table>
				<tr>
					<td colspan="4" height="25"><div style="padding-left:10px"><b>总计提示:</b></div></td>
				</tr>
					<tr>
						<td height="25" class="formatterCss" style="width: 20%">&nbsp;&nbsp;&nbsp;&nbsp;贷款本金总额：
							<span>${custArrears.receivablePrincipalStr}</span>元
						</td>
						<td class="formatterCss" style="width: 20%">未还贷款本金： <span>${custArrears.principalSurplusStr}</span>元
						</td>
						<td class="formatterCss" style="width: 20%">应收费用总额： <span>${custArrears.totalFeedStr}</span>元
						</td>
						<td class="formatterCss" style="width: 20%">到期未收费用总额： <span>${custArrears.noReceiveTotalAmtStr}</span>元
						</td>
					</tr>
				<tr>
					<td colspan="4" height="25"><div style="padding-left:10px"><b>逾期提示：</b></div></td>
				</tr>
				<tr>
					<td height="25" style="width:20%">&nbsp;&nbsp;&nbsp;&nbsp;逾期未还款项共计 ${custArrears.overdueCount} 笔</td>
					<td  style="width:20%">逾期未还款项总额: ${custArrears.unReceivedOverdueInterestStr}元</td>
					<td colspan="2" style="width:20%">逾期违约金总额： ${custArrears.unReceivedOverduePunitiveStr}元</td>
				</tr>
			</table>
			</div>
		</div>

		<!-- 挪用处理 -->
		<!-- 挪用处理 -->
		<div class="pt10"></div>
		<div class="easyui-panel" title="挪用处理" data-options="collapsible:true">
		<form
			action="${basePath}afterLoanDivertController/updatefterLoanDivertinfo.action"
			method="post" id="savedivertinfo">
			<div class="dueInfoDiv1">
				<input type="hidden" name="divertId" value="${divertId}" />
		
				<table class="cus_table">
					<tr>
						<td colspan="4" height="10"></td>
					</tr>
					<tr>
						<td class="label_right" style="width: 150px;"><span
							class="cus_red">*</span>挪用金额：</td>
						<td><input name="divertAmt" id="divertAmt"  class="easyui-numberbox" groupSeparator="," value="${divert.divertAmt}"  data-options="onChange:divertAmtinfo,min:0,precision:2"  required="required" /></td>
						<td class="label_right"><span class="cus_red">*</span>挪用罚息利率：</td>
						<td><input name="divertFine" id="divertFine"
							class="easyui-numberbox" value="${divert.divertFine}"  data-options="onChange:divertAmtinfo,min:0,precision:4" required="required" />%日</td>
					</tr>
					<tr>
						<td class="label_right"><span class="cus_red"></span>放款日期：</td>
						<td><input name="divertFinehidEndDt" id="divertFinehidEndDt"
							class="easyui-datebox"   required="required" readonly="readonly" value="${divert.planOutLoanDt}"  data-options=""/>
							
							</td>
						<td class="label_right"><span class="cus_red"></span>挪用天数：</td>
						<td><input name="dievrtDays" id="dievrtDays" 
						readonly="readonly" class="easyui-textbox"   /></td>
					</tr>
					<tr>
						<td class="label_right"><span class="cus_red">*</span>挪用记息结束日期：</td>
						<td><input name="divertFineEndDt" id="divertFineEndDt"
							class="easyui-datebox"   required="required" value="${divert.divertFineEndDt}"  data-options="onSelect:changeAmt"/>
							
							</td>
						<td class="label_right"><span class="cus_red">*</span>挪用罚息：</td>
						<td><input name="divertFinePayAmt" id="divertFinePayAmt"
							value="${divert.divertFinePayAmt}" class="easyui-numberbox" groupSeparator=","  readonly="readonly" data-options="min:0,precision:2" /></td>
					</tr>
					<tr>
						<td class="label_right"><span class="cus_red">*</span>挪用罚息应收日期：</td>
						<td colspan="3"><input name="divertFinePayDt"
							id="divertFinePayDt" class="easyui-datebox"
							value="${divert.divertFinePayDt}"  required="required"  /></td>
					</tr>
					<tr>
						<td class="label_right">备注：</td>
						<td colspan="3"><input id="remark" name="remark"
							class="easyui-textbox" value="${divert.remark}"
							data-options="width:400,height:50,multiline:true" /></td>
					</tr>
					<tr>
						<td colspan="4" align="center" height="35"><input
							onclick="saveDivert()" type="button" class="save_data text_btn"
				id="savebutton"			value="保存"> <!-- <input type="button"
							class="update_data long_text_btn" onclick="insertRepayPlan()"
								id="planbutton"		value="更新还款计划表"> --></td>
					</tr>
				</table>
			</div>
		</form>
		</div>
		<div id="toolbar" class="easyui-panel" style="border: 0;">
			&nbsp; <a href="javascript:void(0);"
			id="uploadbutton" iconCls="icon-add" plain="true" class="easyui-linkbutton" onclick="uploadrepayfile()">上传</a>
		</div>
		<div id="status"></div>
		<div id="dlg-buttons">
			<center>
				<a id="add" href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-ok" onclick="submitForm()">保存</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#upload').dialog('close')">取消</a>
			</center>
		</div>

		<div id="dlg-buttons-edit">
			<center>
				<a id="edit-file" href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-ok" onclick="editFile()">保存</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#upload-edit').dialog('close')">取消</a>
			</center>
		</div>

		<div id="upload-edit" class="easyui-dialog"
			style="width: 500px; height: 200px; padding: 10px 20px;" closed="true"
			buttons="#dlg-buttons-edit">
				<p style="display: none;">
					<input name="pid" id="fileId" />
				</p>
				<p>
					<span style="margmargin-top: 10px;">上传说明:</span>
					<textarea rows="4" style="width: 300px;" name="fileDesc" id="editfileDesc"></textarea>
				</p>
		</div>



		<div id="upload" class="easyui-dialog"
			style="width: 500px; height: 300px; padding: 10px 20px;" closed="true"
			buttons="#dlg-buttons">
			<form id="fileUploadForm" name="fileUploadForm"
				action="${basePath}afterLoanDivertController/uploadDivertapply.action"
				method="post" enctype="multipart/form-data" target="hidden_frame">
				<p style="display: none;">
					<input name="divertId" id="divertId" />
					<input name="filekinds" id="filekinds" value="1"/>
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
		<div class="pt10"></div>
		<!-- queryRegAdvapplyFile.action?preRepayId='+preRepayId -->
		<div style="width: 100%;text-align: center;">
			<table id="up_data" class="easyui-datagrid"
				data-options="
				title:'挪用处理资料上传',
			    url: '',
			    method: 'POST',
			    idField: 'pid',
			    fitColumns:true,
				checkOnSelect: false,
			    rownumbers: true,
			    pagination: true,
			    toolbar: '#toolbar',
			    collapsible:true,
			    onLoadSuccess:lookfor"
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
						<th data-options="field:'yyyyy',width:300,formatter:uploadadvoperat">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<div class="panel-body1 pt10" id="loanworkflowWrapDivPanel" style="padding: 10px 10px 0 10px;">
		<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
			<div style="padding:5px">
				<%@ include file="../common/loanWorkflow.jsp"%> 
				<%@ include file="../common/task_hi_list.jsp"%>
			</div>
		</div>
	</div>
</div>
<script>
	var divertbutton;
	// 保存挪用
	function saveDivert() {
		if(divertbutton==3){
			$.messager.alert("操作提示", "不能修改挪用金额", "info");
			return false;
		}
		if(divertbutton==2){
			$.messager.alert("操作提示", "不能重复提交", "info");
			return false;
		}
		

		$("#savedivertinfo").form('submit', {
			success : function(result) {
				divertbutton=2;
				$.messager.alert("操作提示", "更新成功！", "info");
				// 保存成功后，顺便更新还款计划表
				insertRepayPlan();
			}
		});
	}
	function uploadrepayfile() {
		$('#upload').form('clear');
		if (divertId < 1) {
			$.messager.alert("操作提示", "请先保存数据！", "info");
			return false;
		}
		$('#upload').dialog('open').dialog('setTitle', "文件上传");
		$("#upload").dialog("move", {
			top : $(document).scrollTop() + 260 * 0.5
		});
	}

	$(function() {
		$.post("${basePath}afterLoanDivertController/queryRepayDivertapplyFile.action",{divertId : divertId},
						function(dt) {
							$('#up_data').datagrid('loadData',eval("{" + dt + "}"));
						});
	});
</script>
<script>
function changeAmt(data){
	var dat=	$("#divertAmt").numberbox("getValue");
	var dtf=	$("#divertFine").numberbox("getValue");
	 var dtfdt=	$('#divertFinehidEndDt').val();
	var dfpdt=	$('#divertFineEndDt').datebox('getValue');
	/*if(dtfdt){
		
	} */
      var dievrtDays=DateDiff(dfpdt,(dtfdt+"").split(" ")[0]);
	 //var dievrtDays=$('#dievrtDays').textbox('getValue');
	 $("#divertFinePayAmt").numberbox("setValue",dat*dtf/100*dievrtDays);	
	 $('#dievrtDays').textbox('setValue',dievrtDays);
}

   // 更新还款计划表
	function insertRepayPlan() {
		
		if (divertId > 0) {
			$.ajax({
				type : "POST",
				url : "${basePath}afterLoanDivertController/advMakeRepayCgInfo.action",
				data : {
					"projectId" : projectId,
					"divertId" : divertId
				},
				dataType : "json",
				// async:false,
				success : function(data) {
					
					if(data.header.success)
					{
						$.messager.alert("操作提示", "还款计划更新成功！", "info");
						window.location.href = "${basePath}afterLoanDivertController/afterLoanDivertbyprocess.action?divertId="+divertId;
					}
					else
					{
						$.messager.alert("提示",data.header.msg,"error");
					}	
				}
			});

		} else {
			$.messager.alert("操作提示", "请先保存违约信息！", "error");
		}
	}
	function disposeClick(pid) {
		url = "${basePath}beforeLoanController/addNavigation.action?projectId="+pid+"&param='refId':'"+pid+"','projectName':'"+divertProjectName+"'";
		parent.openNewTab(url,"贷款申请详情",true);
	} 

	function sizeFileter(value,row,index){
		if(row.fileSize>0){
			return  parseInt(row.fileSize/1024)+" KB";
		}else{
			return '';
		}
	}
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
	function	divertAmtinfo(){
		if($("#divertAmt").numberbox("getValue")>500000000){
			$.messager.alert("操作提示", "你输入的金额大于系统最大限额!请重新输入！","error");
					$("#divertAmt").numberbox("setValue",0);
		}
		else{
			var dat=	$("#divertAmt").numberbox("getValue");
			var dtf=	$("#divertFine").numberbox("getValue");
			var dtfdt=	$('#divertFinehidEndDt').val();
			var dfpdt=	$('#divertFineEndDt').datebox('getValue');
			 /*if(dtfdt){
				
			} */
		      var dievrtDays=DateDiff(dfpdt,(dtfdt+"").split(" ")[0]);
				//var dievrtDays=$('#dievrtDays').textbox('getValue');
				$("#divertFinePayAmt").numberbox("setValue",dat*dtf/100*dievrtDays);	
				$('#dievrtDays').textbox('setValue',dievrtDays);
			}
	}
	
	function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
	    var  aDate,  oDate1,  oDate2,  iDays  
	    aDate  =  sDate1.split("-")  
	    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]) //转换为12-18-2006格式  
	    aDate  =  sDate2.split("-")  
	    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
	    iDays  =  parseInt(Number(oDate1  -  oDate2)  /  1000  /  60  /  60  /24) //把相差的毫秒数转换为天数  
	    return  iDays  
	}
	
	$(function() {
				if(
					"task_LoanRequest"==workflowTaskDefKey||//贷款申请
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
					divertbutton=3;
			}
			var dtfdt=	$('#divertFinehidEndDt').textbox('getValue');
			var dfpdt=	$('#divertFineEndDt').datebox('getValue');
		   var dievrtDays=DateDiff(dfpdt,(dtfdt+"").split(" ")[0]);
		   $('#dievrtDays').textbox('setValue',dievrtDays);
		}
	);
</script>

</body>