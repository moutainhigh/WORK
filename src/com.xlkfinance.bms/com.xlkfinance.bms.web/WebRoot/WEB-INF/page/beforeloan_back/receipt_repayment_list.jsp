<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>

<style>
	.datagrid-cell-c4-fileDesc{width:100px;}
 	#templateReceiptDialog .fitem label{width:180px;}
 	#templateReceiptDialog .fitem .text_style{width:220px;}
 </style> 
<div class="receipt_dis"  style="padding: 30px 10px 10px 10px;width:1039px">
		
	<div class="easyui-panel" title="借据" data-options="collapsible:true">		
		<div id="receiptContractList" style="min-height:200px;line-height: 35px;">
		<div id="toolbar_receipt_contract" style="padding:5px;">
			操作按钮
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openCreditsReceiptWindow()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editReceiptContract()">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteReceiptContract()">删除</a>
		</div>
		<table id="grid_receipt_contract" class="easyui-datagrid" 
			style="height:100%;width: auto;"
			data-options="
			    url: '${basePath}creditsContractController/getContractListUrl.action?projectId=${projectId }&contractCatelogKey=JJ',
			    method: 'POST',
			    rownumbers: true,
			    singleSelect: false,
				border:false,
			    pagination: true,
			    toolbar: '#toolbar_receipt_contract',
			    idField: 'pid',
			    fitColumns:true,
			    collapsible:true">
			<!-- 表头 -->
			<thead><tr>
			    <th data-options="field:'pid',checkbox:true,width:50" ></th>
			    <th data-options="field:'contractTemplateId'"  hidden="true"></th>
			    <th data-options="field:'contractUrl'"  hidden="true"></th>
			    <th data-options="field:'contractTypeText',width:100"  >借据类型</th>
			    <th data-options="field:'contractName',width:100"  >借据名称</th>
			    <th data-options="field:'contractNo',width:100"  >借据编号</th>
			    <th data-options="field:'num',width:100"  >借据份数</th>
			    <th data-options="field:'firstNum',width:100" >甲方</th>
			    <th data-options="field:'secondNum',width:100"  >乙方</th>
			    <th data-options="field:'cz',formatter:contractOperationFilter" >操作</th>
			</tr></thead>
		</table>
		</div>
		
		</div>
	
	<div style="margin-top:10px;">
		<div class="easyui-panel" title="还款计划表" data-options="collapsible:true">		
			<div id="receiptContractBiao">
				<jsp:include page="repayment_list.jsp">
					<jsp:param value="2" name="isEdit"/>
				</jsp:include>
			</div>				
		</div>
	</div>
	<div id="dlg-openReceiptContract">
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveReceiptCreditContract()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#openReceiptContract').dialog('close')">取消</a>
	
	</div>
	<div id="openReceiptContract" class="easyui-dialog" style="width:870px;height:400px;padding:10px;" 
		closed="true" buttons="#dlg-openReceiptContract">
		<div class="fitem">
			<label style="text-align: right;">借据名称：</label>
            <input name="contractName" id="addReceiptContractName" style="width: 320px;" class="easyui-validatebox"  data-options="required:true" />
		</div>
		<div class="fitem">
			<label style="text-align: right;">借据模版：</label>
            <input name="" id="receiptTemplateName" class="easyui-combobox" style="width: 320px;" editable="false" data-options="valueField:'pid',textField:'comboboxTemplateText',url:'<%=basePath%>beforeLoanController/getTempLateList.action'" />
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="ajaxReceiptContract()">读取借据信息</a>
		</div>
		<div class="fitem">
			<label style="text-align: right;">所属合同：</label>
            <input name="" id="add_receipt_parent_id" class="easyui-combobox" style="width: 320px;" editable="false" data-options="valueField:'pid',textField:'contractName',url:'<%=basePath%>contractInfoController/getParentContracts.action?projectId=${projectId }&isParent=0'" />
		</div>
		<div id="templateReceiptDialog" class="datagrid-btable"  style="width: 800px;">
			
		</div>
	</div>
	
	<div id="dlg_receipt_but">		
	    <center>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="openReceiptCreditContract()">生成合同</a>
	    </center>
	</div>
 	<!-- 抵押合同、质押合同、保证合同时  -->
 	<div id="dlg_receipt" class="easyui-dialog" style="width:550px;height:300px;padding:10px" 
			closed="true" buttons="#dlg_receipt_but" data-options="modal:true">
 		<table id="receipt_grid" class="easyui-datagrid" 
				style="height:auto;width: auto;"
				data-options="
				    url: '',
				    method: 'get',
				    rownumbers: true,
				    singleSelect: false, 
				    fitColumns:true">
				<!-- 表头 -->
				<thead><tr>
				    <th data-options="field:'pid',checkbox:true" ></th>
				    <th data-options="field:'dataObject'" width="200px" >合同数据对象</th>
				</tr></thead>
			</table>
 	</div>
	
	<div id="edit_receipt_template-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveEidtReceipt()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editReceiptContractDialog').dialog('close')">关闭</a>
	</div>
	<div id="editReceiptContractDialog" class="easyui-dialog" style="width:740px;height:400px;"
        closed="true" buttons="#edit_receipt_template-buttons">
    <center> 
    	<div class="fitem" style="margin-top:5px;padding-left:50px;">
			<label style="text-align: left;">借据名称：</label>
            <input name="contractName" id="editReceiptContractName" class="easyui-validatebox" style="width:350px;" />
		</div>
		<div class="fitem" style="margin-top:5px;padding-left:50px;">
			<label style="text-align: left;">合同模版：</label>
            <input name="edit_receipt_templateName" id="edit_receipt_templateName" class="easyui-combobox" style="width: 350px;" editable="false"  />
		</div>
		<div class="fitem" style="margin-top:5px;padding-left:50px;">
			<label style="text-align: left;">所属合同：</label>
            <input name="" id="edit_receipt_parent_id" class="easyui-combobox" style="width: 350px;" editable="false" data-options="valueField:'pid',textField:'contractName',url:'<%=basePath%>contractInfoController/getParentContracts.action?projectId=${projectId }&isParent=1'" />
		</div>
	    <table id="edit_receipt_dialog" class="datagrid-btable" style="width: 700px;height:270px;">
	    	 
		</table>
	</center>
</div>
</div>	

<!-- 隐藏域 保存生成合同所需的参数  -->
<!-- <div style="display: none;">
 	<input type="text" id="suretyId" />
 	<input type="text" id="mortgagorId" />
 	<input type="text" id="contractType"/>
</div> -->
 	
<script type="text/javascript">
//打开选取抵质押物or保证合同窗口
function openCreditsReceiptWindow(){
	
	//var isReadContract=0;
	var url = BASE_PATH+"creditsController/getProjectAcctAndPublicManByProjectId.action?projectId="+projectId;
	
	// 获取datagrid的数据
	$('#receipt_grid').datagrid('options').url = url;
	$('#receipt_grid').datagrid('reload'); 
	$('#dlg_receipt').dialog('open').dialog('setTitle', "合同生成数据对象选择");
	
	
}

	// 打开新增合同页面
	function openReceiptCreditContract(){
		// 获取所选择的数据行
		var rows = $("#receipt_grid").datagrid('getSelections');
		// 判断是否选择数据 
		if(rows.length == 0){
			$.messager.alert("操作提示","请选择数据","error");
			return;
		}
		
		// 判断是否选择数据 
		if(rows.length > 4){
			$.messager.alert("操作提示","最多只能选择4个合同对象","error");
			return;
		}
		
		commonDebtorPids="";
		var mainPerson=true;
		for (var i = 0; i < rows.length; i++) {
			if(rows[i].relationType ==-1){
				mainPerson=false;
			}else{
				if (commonDebtorPids =="") {
					commonDebtorPids += rows[i].refId;
				} else {
					commonDebtorPids += "," + rows[i].refId;
				}
			}
		}
		if(mainPerson){
			$.messager.alert("操作提示","合同对象必须选择借款人","error");
			return;
		}
		isReadContract=0;
		
		getContractTemplateCombox('receiptTemplateName','add_receipt_parent_id','templateReceiptDialog',0,'JJ',projectId);
		
		$('#openReceiptContract').dialog('open').dialog('setTitle', "新增借据");
		
		//清空合同表单
		$('#templateReceiptDialog').html('');
		$("#addReceiptContractName").val('');
		$("#add_receipt_parent_id,#receiptTemplateName").combobox('setValue','0');
		
		// 关闭当前窗口
		$('#dlg_receipt').dialog('close');
	}
	function editReceiptContract(){
		var row = $('#grid_receipt_contract').datagrid('getSelections');
		if (row.length == 1) {
			editReceiptContractDialog(row[0].contractUrl,row[0].contractTemplateId,row[0].pid,row[0].contractName,row[0].parentId);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	
	//合同编辑
	function editReceiptContractDialog(contractUrl,tempLateId,contractId,contractName,parentId){
	 $('#editReceiptContractDialog').dialog('open').dialog('setTitle', "借据编辑");
		$.ajax({
			type: "POST",
	        url: "${basePath}contractInfoController/editDocument.action",
	        data:{"contractUrl":contractUrl,"tempLateId":tempLateId,"contractId":contractId},
	        dataType: "json",
	        success: function(data){
	        	var dom = "<tr  style='line-height:30px'>";
	        	 for (var i = 0; i < data.length; i++) {
	       		 	if(i%2==0){
	       		 		dom = dom+'<td align="right">'+data[i].matchName+':</td><td><input class="textbox-text text_style"   id="'+data[i].pid+'" value="'+(data[i].matchValue==null ||data[i].matchValue=='null' ?'':data[i].matchValue)+'" name="'+data[i].matchFlag+'"/></td>'; 
	       		 	} 
	       		 	if(i%2!=0){
	       		 		dom = dom+'<td align="right">'+data[i].matchName+':</td><td><input class="textbox-text text_style"  id="'+data[i].pid+'" name="'+data[i].matchFlag+'" value="'+(data[i].matchValue==null ||data[i].matchValue=='null' ?'':data[i].matchValue)+'"/></td></tr><tr style="line-height:30px">'; 
	       		 	}
				}
	        	dom=dom+"<input type='hidden' value='"+contractId+"' id='editContractId'>";
	         	dom=dom+"<input type='hidden' value='"+contractUrl+"' id='editContractUrl'>";
	         	dom=dom+"<input type='hidden' value='"+tempLateId+"' id='editTempLateId'>";
	         	$("#editReceiptContractName").val(contractName);
	         	$('#edit_receipt_parent_id').combobox({url:"${basePath}contractInfoController/getParentContracts.action?projectId="+projectId+"&isParent=1",valueField:'pid',textField:'contractName' });
	         	$("#edit_receipt_parent_id").combobox("setValue",parentId);
	         	$('#edit_receipt_parent_id').combobox('disable');
	         	
				$("#edit_receipt_templateName").combobox({valueField:'pid',textField:'comboboxTemplateText',url:'${basePath}beforeLoanController/getTempLateList.action?templateCatelogText=JJ'});
				$("#edit_receipt_templateName").combobox("setValue",tempLateId);
				$('#edit_receipt_templateName').combobox('disable');
	        	 $("#edit_receipt_dialog").html(dom); 
	        }
		});
	}
	
	//保存编辑后的类容
	function saveEidtReceipt(){
		var content = "";
		$("#edit_receipt_dialog").find("input").each(function (i){
			var key = $(this).attr("id");
			var value = $(this).val();
			var flag = $(this).attr("name");
			if(key!="editContractId" && key!="editContractUrl" && key!="editTempLateId"){
				content=content+key+":"+value+":"+flag+"###";
			}
			
		});
		var contractId = $("#editContractId").val();
		var contractUrl = $("#editContractUrl").val();
		var tempLateId = $("#editTempLateId").val();
		var contractName=$("#editReceiptContractName").val();
		$.ajax({
			type: "POST",
	        url: "${basePath}contractInfoController/saveEditDocument.action",
	        data:{"content":content,"contractId":contractId,"contractUrl":contractUrl,"tempLateId":tempLateId,"contractName":contractName},
	        dataType: "json",
	        success: function(data){
	        	if(data=="true" || data==true){
	        		 $.messager.alert("操作提示", "编辑保存成功！", "success");
	        		 $('#editReceiptContractDialog').dialog('close');
	        		 $('#grid_receipt_contract').datagrid('reload');
					 $('#grid_receipt_contract').datagrid('clearSelections');
	        	 }else{
	        		 $.messager.alert("操作提示", "编辑保存失败,请检查！", "error");
	        	 }
	        }
		});
	}
	
	function deleteReceiptContract(){
		var rows = $('#grid_receipt_contract').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		var pids = "";
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				pids += rows[i].pid;
			} else {
				pids += "," + rows[i].pid;
			}
		}
		$.messager.confirm(
			"操作提示",
			"确定删除选择的合同信息吗?",
			function(r) {
				if (r) {
					$.post(
						"${basePath}contractInfoController/deleteContracts.action",
						{
							pids : pids
						},
						function(ret) {
							 $('#grid_receipt_contract').datagrid('reload');
							 $('#grid_receipt_contract').datagrid('clearChecked');
						}, 'json');
				}
			});
	}
	
	/* //获取合同表格数据
	function getReceiptTableParameterVal(tempLateId){
		var tableDom = "";
		$.ajax({
			type: "POST",
	        url: "${basePath}contractInfoController/getContractTableDynamicParmList.action",
	        data:{	"tempLateId":tempLateId,
	        	  	"projectId":projectId
			},
	        dataType: "json",
	        success: function(data){
				if(data!=null && data.length!=null){
					tableDom = "<hr>"+"";
					
					return tableDom;
				}
	        }
		});
	} */
	
	// ajax 动态加载合同信息
	function ajaxReceiptContract(){
		var tempLateId = $("#receiptTemplateName").combobox("getValue");
		if(tempLateId ==0 || tempLateId==-1){
			$.messager.alert("操作提示", "请选择合同模板!", "error");
			return ;
		}
		
		var contractType ="JJ";		
		var suretyId =-1;
		var mortgagorId =-1;		
		
		$.ajax({
			type: "POST",
	        url: "${basePath}contractInfoController/getContractDynamicParmList.action",
	        data:{	"contractType":contractType,
	        		"tempLateId":tempLateId,
	        		"debtorProjectId":projectId,
	        		"creditProjectId":projectId,
	        	  	"projectId":projectId,
	        	  	"mortgagorId":mortgagorId,
	        	  	"suretyId":suretyId,
				  	"exdProjectId":-1,
				  	"commonDebtorPids":commonDebtorPids
			},
	        dataType: "json",
	        success: function(data){
	        	if(data!=null && data.length!=null){
	        		//加载合同成功
		        	isReadContract=1;
		        	var dom = '<table id="add_templateDialogReceipt" class="datagrid-btable"> <tr style="line-height:30px">';
		        	 for (var i = 0; i < data.length; i++) {
	        			if(data[i].fixedVal==null){
	        				data[i].fixedVal=='';
	        			}
	      
	        			dom = dom+'<td align="right" width="160">'+data[i].matchName+':</td>';
	        			dom = dom+'<td align="left"  width="200"><input class="easyui-textbox text2_style" id="'+data[i].pid+'" value="'+(data[i].fixedVal==null?'':data[i].fixedVal)+'" name="'+data[i].matchFlag+'"/></td>'; 
	        			
	 	       		 	if(i%2!=0){
	 	       		 		dom=dom+'</tr><tr style="line-height:30px">';
	 	       		 	}
					}
		        	dom=dom+"<input type='hidden' value='"+tempLateId+"' id='addTempLateId'></tr></table>";
		        	
		        	$("#templateReceiptDialog").html(dom);
	        	}else{
	        		$.messager.alert("操作提示","读取合同失败，请检查是否上传合同模板！","error");
	    			return ;
	        	}
	        }
		});
	}
	
	// 新增合同页面
	function saveReceiptCreditContract(){
		var contractName=$("#addReceiptContractName").val();
		var contractParentId=$("#add_receipt_parent_id").combobox('getValue');
		
		if(contractName==null || contractName==''){
			$.messager.alert("操作提示", "请填写合同名称!", "error");
			return ;
		}
		if(isReadContract==0){
			$.messager.alert("操作提示","请先读取合同内容！","error");
			return ;
		}
		//判断是否要选择父合同
		if(isParent==1){
			if(contractParentId==0){
				$.messager.alert("操作提示", "请选择所属合同!", "error");
				return;
			}
		}
		var content = "";
		$("#templateReceiptDialog").find("input").each(function (i){
			var key = $(this).attr("id");
			var value = $(this).val();
			var flag = $(this).attr("name");
			if(key!="addTempLateId"){
				content=content+key+":"+value+":"+flag+"###";
			}
		});
		var tempLateId = $("#addTempLateId").val();
		var contractCatelog='JJ';
		
		
		var projectId='${projectId}';
		$.ajax({
			type: "POST",
	        url: "${basePath}contractInfoController/saveContract.action",
	        data:{"content":content,"tempLateId":tempLateId,"contractCatelog":contractCatelog,"contractName":contractName,"projectId":projectId,"contractParentId":contractParentId},
	        dataType: "json",
	        success: function(data){
	        		 $.messager.alert("操作提示","生成借据成功！","info");
	        		 $('#grid_receipt_contract').datagrid('reload');
	        		 $('#openReceiptContract').dialog('close');
	        },
	        error: function(data){
	        	$.messager.alert("操作提示","生成借据失败！","error");
	        }
		});
	}
	
	
	$(document).ready(function(){
		if(projectName){		
			$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
			$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
			$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
	 	}
		if("task_LoanRequest"==workflowTaskDefKey || "task_ApprovalOfficerReview"==workflowTaskDefKey ||"task_OfflineMeetingResult"==workflowTaskDefKey ||"task_ContractSign"==workflowTaskDefKey){
			
		}else{
			$('.receipt_dis .easyui-combobox').attr('disabled','disabled');
			$('.receipt_dis input[type="checkbox"]').attr('disabled','disabled');
			$('.receipt_dis .easyui-textbox').attr('disabled','disabled');
			$('.receipt_dis .easyui-linkbutton:not(.download) ,.datum a').removeAttr('onclick');
			$('.receipt_dis a:not(.download) font').attr('color','gray');
			$('.receipt_dis .easyui-linkbutton:not(.download) ,.receipt_dis  input,.receipt_dis  textarea').attr('disabled','disabled');
			$('.receipt_dis .easyui-linkbutton:not(.download) ,.receipt_dis  input,.receipt_dis  textarea').attr('readOnly','readOnly');
			$('.receipt_dis .easyui-linkbutton:not(.download) ,.receipt_dis  input,.receipt_dis  textarea').addClass('l-btn-disabled');
		}
	});
</script>
