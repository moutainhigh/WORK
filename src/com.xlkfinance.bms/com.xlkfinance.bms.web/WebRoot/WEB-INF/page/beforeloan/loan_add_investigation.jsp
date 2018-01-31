<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
	<div style="width:auto;padding-top:30px;">
		<div id="foreclosure">
		<div class="control_disbase" style="padding: 10px 10px 0 10px;width:1039px">
		<div class=" easyui-panel" title="尽职报告调查" data-options="collapsible:true">	
		<form action="<%=basePath%>secondBeforeLoanController/addSurveyReport.action" id="surveyReportInfo" name="surveyReportInfo" method="post" >
			<div style="padding-left: 10px;padding-top:10px;height: auto;line-height: 35px;" >
			<input type="hidden" name="pid" id="pid" value="0">
			<input type="hidden" name="projectId" >
		
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><span>特殊情况说明：</span></label>
             	<input name="specialDesc" id="specialDesc" class="easyui-textbox" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,500]'">
			</div>
			
			</div>
		</form>
		</div>
		<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveSurveyReportInfo()">保存调查报告</a>
				<!-- <a href="#" class="easyui-linkbutton download"  style="margin-left: 10px;" iconCls="icon-save" onclick="exportSurveyReport()">导出</a> -->
			</div>
		</div>
		</div>
		
		<div id="guaranteeInfo" class="none">
		
		<form action="<%=basePath%>beforeLoanController/addGuarantee.action" id="guarantee" name="guarantee" method="post">
		<input type="hidden" name="assWay" />
		<input type="hidden" name="pid" >
		<input type="hidden" name="assWayText" />
		<div class="fitem" style="padding-left: 25px;" >
		<table class="control_dis">
			<tr>
				<td class="label_right"><font color="red">*</font>担保方式：</td>
				<td>
		            <input id="assWay" class="easyui-combobox" editable="false" required="true"  panelHeight="auto" 
		            data-options="valueField:'pid',textField:'lookupDesc',multiple:true,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=ASS_WAY',onUnselect:unSelectMortgageType,onSelect:selectMortgageType" />
				</td>
			</tr>
		</table>
		</div>
		
		<div class="control_disbase" style="padding: 10px 10px 0 10px;">
			<div class=" easyui-panel" title="担保措施" data-options="collapsible:true">
				<div id="guaranteesMeasures" style="padding: 10px;height: auto;line-height: 35px;width: auto;" >
					<%@ include file="add_mortgage.jsp" %>
				</div>
			</div>
			
		</div>
		<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveGuarantee()">保存担保信息</a>
			</div>
		</form>
		
		
		<%-- 尽职报告调查 --%>
		<div class="control_disbase" style="padding: 10px 10px 0 10px;">
		<div class=" easyui-panel" title="尽职报告调查" data-options="collapsible:true">	
		<form action="<%=basePath%>secondBeforeLoanController/addSurveyReport.action" id="surveyReport" name="surveyReport" method="post" >
		<div id="dueDiligence" style="padding-left: 10px;padding-top:10px;height: auto;line-height: 35px;" >
			
			<input type="hidden" name="pid" id="pid" value="0">
			<input type="hidden" name="projectId" >
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font><span id="introductionId">个人简介：</span></label>
             	<input name="introduction" id="introduction" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font><span id="projectSource">个人主要财务状况：</span></label>
             	<input name="projectSource" id="projectSource" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>项目来源：</label>
             	<input name="financialStatus" id="financialStatus" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>贷款用途：</label>
             	<input name="loanPurposes" id="loanPurposes" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>还款能力：</label>
             	<input name="repayAnalysis" id="repayAnalysis" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>风险提示：</label>
             	<input name="riskWarning" id="riskWarning" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>保证措施：</label>
             	<input name="assuranceMeasures" id="assuranceMeasures" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>

			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>调查结论：</label>
             	<input name="surveyResults" id="surveyResults" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'" >
			</div>
			</div>
			
			</form>
		</div>
		<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveSurveyReport()">保存调查报告</a>
				<a href="#" class="easyui-linkbutton download" style="margin-left: 10px;" iconCls="icon-save" onclick="exportSurveyReport()">导出</a>
			</div>
		</div>

	<div id="percom_toolbar">
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="selectComToExport()">选择该客户生成调查报告</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="UnselectComToExport()">无旗下公司继续生成调查报告</a>
		</div>
	</div>
	<div id="percomDialog" class="easyui-dialog" closed="true" style="width:880px;height:400px;">
		<table id="percomGrid" title="个人客户尽责调查报告旗下公司选择" class="easyui-datagrid" style="height:100%;width: auto;"
		 			data-options="
				    url: '',
				    method: 'get',
				    collapsible:true,
				    rownumbers: true,
				    singleSelect: true,
				    pagination: true,
				    toolbar: '#percom_toolbar',
				    idField: 'pid',
				    selectOnCheck:true,
					checkOnSelect: true">
			<thead><tr>
				<th data-options="field:'pid',checkbox:true"></th>
			    <th data-options="field:'value1',width:200,sortable:true"  >企业名称</th>
			    <th data-options="field:'value3',width:200,sortable:true"  >组织机构代码</th>
			    <th data-options="field:'value4',width:200,sortable:true" >营业执照号</th>
			    <th data-options="field:'value5',width:100,sortable:true" >所用制性质</th>
			    <th data-options="field:'value6',width:100,sortable:true" >法人</th>
			    <th data-options="field:'value7',width:100,sortable:true" >注册资金</th>
			    <th data-options="field:'value8',width:100,sortable:true" >联系电话</th>
			    <th data-options="field:'value9',width:100,sortable:true" >成立日期</th>
				</tr></thead>
		</table>
	</div>
	
	<%-- end 法人保证信息 --%>
	<style type="text/css">
		.investigation{
			width:120px;
			text-align: right;
		}
		
		.no_frame{
			border:0;
			background: white;
		}
		
		#table_dzywxx tr td{
			width:150px;
		}
		
		</style>
	<script type="text/javascript">
	$(function(){  
		
		//担保措施 省市信息初始化
		applyCommon.initAddress();
		 if(productType == 1){//信用贷
			$("#guaranteeInfo").addClass('active');
			$("#guaranteeInfo").removeClass('none');
			$("#foreclosure").addClass('none');
		}else{//赎楼贷
			$("#guaranteeInfo").addClass('none');
			$("#foreclosure").addClass('active');
			$("#foreclosure").removeClass('none');
		} 
		 setTimeout("InitializationMortgage()",1);
		// 动态绑定担保方式 
 		$.ajax({
				type: "POST",
		        data:{"projectId":projectId},
		    	url : '${basePath}secondBeforeLoanController/getGuaranteeTypeByProjectId.action',
				dataType: "json",
			    success: function(data){
			    	var str = eval(data);
			    	// 判断担保方式的个数
			    	if(str.length == 1){
			    		// 如果只有一个
			    		$("#assWay").combobox("setValue",str[0].guaranteeType);
			    	} else {
				    	var values = "";
				    	for(var i = 0;i<str.length;i++){
				    		if(values == ""){
				    			values = str[i].guaranteeType;
				    		}else{
				    			values += ","+str[i].guaranteeType;
				    		}
				    	}
				    	if(values!=""){
				    		$("#assWay").combobox("setValues",values.split(','));
				    	}
			    	}
					// 显示 or 隐藏抵质押物担保
					setTimeout("InitializationMortgage()",1);
				}
		});	
		
 			$("#introductionId").html("个人简介：");
 			$("#projectSource").html("个人主要财务状况：");

			// 尽职调查报告URL			  			
			var urlSr = "<%=basePath%>secondBeforeLoanController/getSurveyReportByProjectId.action?projectId="+projectId;
			// 加载尽职调查报告的数据
			$.post(urlSr,
				function(ret){
				if(ret.pid){
					$("#surveyReport").form('load',ret);
					$("#surveyReportInfo").form('load',ret);
					surPid = ret.pid;
				}
				}, "json"); 
		
		//流程控制
		if("task_LoanRequest"==workflowTaskDefKey  || workflowTaskDefKey == "task_ExaminerCheck" || isEdit == 'isEdit'||"task_BusinessRequest"==workflowTaskDefKey){
			
		}else{
			//风控总监审查  组织召开贷审会 办理抵押登记
			$('.control_dis .easyui-combobox').attr('disabled','disabled');
			$('.control_dis input[type="checkbox"]').attr('disabled','disabled');
			$('.control_dis .easyui-textbox').attr('disabled','disabled');
			$('.control_dis input:not(.download)').attr('readOnly','readOnly');
			$('.control_disbase .easyui-linkbutton:not(.download) ,.control_disbase input,.control_disbase textarea').attr('disabled','disabled');
		}
		
		
	
	});

	function saveSurveyReportInfo(){
		// 赋值项目ID--尽职调查报告
 		$("#surveyReportInfo input[name='projectId']").val(projectId);		
 		url = "";
 		if($("#surveyReportInfo input[name='pid']").val() == 0){
 			url = "<%=basePath%>secondBeforeLoanController/addSurveyReport.action";
 		}else{
 			url = "<%=basePath%>secondBeforeLoanController/updateSurveyReport.action";
 		}
 	// 提交表单
 		$("#surveyReportInfo").form('submit', {
 			url : url,
 			onSubmit : function() {return $(this).form('validate');},
 			success : function(result) {
 				var ret = eval("("+result+")");
 				if(ret && ret.header["success"]){
 					bianzhi = 1;// 标志为是流程
 					// 判断当前是什么操作
 					if($("#surveyReportInfo input[name='pid']").val() == 0){
 						$.messager.alert('操作提示',"保存成功!",'info');
 						surPid = ret.header["msg"];
 						$("#surveyReportInfo input[name='pid']").val(surPid);
 					}else{
 						$.messager.alert('操作提示',"修改成功!",'info');
 					}
 				}else{
 					$.messager.alert('操作提示',ret.header["msg"],'error');	
 				}
 			}
 		});
 		
	}
	
	function saveGuarantee(){
		$("#guarantee input[name='pid']").val(projectId);
		// 获取所选担保方式
 		var assWay = $("#assWay").combobox("getValues");
 		// 判断是否选择担保方式
 		if( assWay == "" ){
 			$.messager.alert('操作提示',"请选择担保方式!",'error');	
 			return;
 		}
 		// 赋值担保方式
 		$("#guarantee input[name='assWay']").val(assWay);
 		// 验证担保方式
		if(!applyCommon.validateAssWay()){
			return ;
		}
 		checkMortgageType($("#assWay").combobox("getText"));
	}
	// 提交表单
	function guaranteeSu(){
		// 提交表单
 		$("#guarantee").form('submit', {
 			onSubmit : function() {return $(this).form('validate');},
 			success : function(result) {
 				var ret = eval("("+result+")");
 				if(ret && ret.header["success"]){
 					$.messager.alert('操作提示',"修改成功!",'info');
 				}else{
 					$.messager.alert('操作提示',ret.header["msg"],'error');	
 				}
 			}
 		});
	}
	
	// 判断抵质押物存在数据,但是项目担保方式没有选择此类型
	function checkMortgageType(assWayText){
		// 获取抵押列表的所有数据 
		var rowsDY = $("#dyxx_grid").datagrid('getRows');
		// 获取质押列表的所有数据
		var rowsZY = $("#zyxx_grid").datagrid('getRows');
		// 获取个人和法人列表的所有数据
		var per_rows = $("#perGuarantee_grid").datagrid('getRows');
		var cor_rows = $("#corGuarantee_grid").datagrid('getRows');
		
		// 判断--没有选择抵押类型,是否存在抵押数据
		if( rowsDY.length > 0 && assWayText.indexOf("抵押") == -1 ){
			$.messager.confirm("操作提示","您填写了抵押数据,但是您没有选择抵押担保方式,如果继续会删除抵押数据,是否继续？",
				function(r) {
					if(r){
						// 判断--没有选择质押类型,是否存在质押数据
						if( rowsZY.length > 0 && assWayText.indexOf("质押") == -1 ){
							$.messager.confirm("操作提示","您填写了质押数据,但是您没有选择质押担保方式,如果继续会删除质押数据,是否继续？",
								function(r) {
									if(r){
										// 判断--没有选择保证类型,是否存在保证数据
										if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
											$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
												function(r) {
													if(r){
														guaranteeSu();
													}else{
														return;
													}
											});
										}else{
											guaranteeSu();
										}
									}else{
										return;
									}
							});
						}else{
							// 判断--没有选择保证类型,是否存在保证数据
							if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
								$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
									function(r) {
										if(r){
											guaranteeSu();
										}else{
											return;
										}
								});
							}else{
								guaranteeSu();
							}
						}
					}else{
						return;
					}
			});
		}else{
			// 判断--没有选择质押类型,是否存在质押数据
			if( rowsZY.length > 0 && assWayText.indexOf("质押") == -1 ){
				$.messager.confirm("操作提示","您填写了质押数据,但是您没有选择质押担保方式,如果继续会删除质押数据,是否继续？",
					function(r) {
						if(r){
							// 判断--没有选择保证类型,是否存在保证数据
							if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
								$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
									function(r) {
										if(r){
											guaranteeSu();
										}else{
											return;
										}
								});
							}else{
								guaranteeSu();
							}
						}else{
							return;
						}
				});
			}else{
				// 判断--没有选择保证类型,是否存在保证数据
				if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
					$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
						function(r) {
							if(r){
								guaranteeSu();
							}else{
								return;
							}
					});
				}else{
					guaranteeSu();
				}
			}
		}
		
	}
	
 	// 保存尽职调查报告
 	function saveSurveyReport(){
 		
 		// 赋值项目ID--尽职调查报告
 		$("#surveyReport input[name='projectId']").val(projectId);		
 		url = "";
 		if($("#surveyReport input[name='pid']").val() == 0){
 			url = "<%=basePath%>secondBeforeLoanController/addSurveyReport.action";
 		}else{
 			url = "<%=basePath%>secondBeforeLoanController/updateSurveyReport.action";
 		}
 		// 提交表单
 		$("#surveyReport").form('submit', {
 			url : url,
 			onSubmit : function() {return $(this).form('validate');},
 			success : function(result) {
 				var ret = eval("("+result+")");
 				if(ret && ret.header["success"]){
 					bianzhi = 1;// 标志为是流程
 					// 判断当前是什么操作
 					if($("#surveyReport input[name='pid']").val() == 0){
 						$.messager.alert('操作提示',"保存成功!",'info');
 						surPid = ret.header["msg"];
 						$("#surveyReport input[name='pid']").val(surPid);
 					}else{
 						$.messager.alert('操作提示',"修改成功!",'info');
 					}
 				}else{
 					$.messager.alert('操作提示',ret.header["msg"],'error');	
 				}
 			}
 		});
 	}
 	
 	function exportSurveyReport(){
 		var perAccId = $("#personalForm input[name='acctId']").val();
 		$.ajax({
 			url:BASE_PATH+'templateFileController/checkFileUrl.action',
 			data:{templateName:'INVESTI'},
 			dataType:'json',
 			success:function(data){
 				if(data==1){				
		 			$('#percomGrid').datagrid({
		 				url:'${basePath}customerController/listUnderComUrl.action?acctId='+perAccId
		 			})
		 			$('#percomDialog').dialog('open');
 				}else{
 					alert('尽职调查导出模板不存在，请上传模板后重试');
 				}
 			}
 		})
 		
 	}
 	
 	
 	function selectComToExport(){
 		var rows = $('#percomGrid').datagrid('getChecked');
 		if(rows.length==0){
 			alert('请选择旗下公司');
 		}else if(rows.length==1){
 			$.ajax({
 				url:'${basePath}secondBeforeLoanController/getAcctIdByComId.action',
 				type:'post',
 				data:{comId:rows[0].pid},
 				dataType:'json',
 				success:function(data){
 					if(data==0){
 						alert('查询不到客户ID');
 					}else{
			 			window.location.href="${basePath}secondBeforeLoanController/exportSurveyReport.action?projectId="+projectId+"&comId="+data; 
 					}
 				}
 			})
 		}else{
 			alert('只能请选择一个旗下公司进行导出');
 		}
 	}
 	
 	function UnselectComToExport(){
 		window.location.href="${basePath}secondBeforeLoanController/exportSurveyReport.action?projectId="+projectId+"&comId=0"; 
 	}
 	
 	</script>
 	