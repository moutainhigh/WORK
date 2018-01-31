<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
<style>
	.datagrid-cell-c4-fileDesc{width:100px;}
 	#editContractDialog .fitem label{width:180px;}
 	#editContractDialog .fitem .text_style{width:220px;}
 	#templateDialog .fitem label{width:180px;}
 	#templateDialog .fitem .text_style{width:220px;}
 </style> 
 <div class="contract_dis">
	<div  style="padding: 30px 10px 10px 10px;width:1039px">
			<div id="contractList">
				 <div id="toolbar_contract"  class="easyui-panel" >
					<!-- 操作按钮 -->
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openCreditsWindow(1)">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCreditContract('SX')">编辑</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteCreditContract('SX')">删除</a>
				</div>
				<div class="contractListDiv">
					<table id="grid_contract" title="授信合同" class="easyui-datagrid" 
						style="height:250px;width: auto;"
						data-options="
						    url: '${basePath}creditsContractController/getContractListUrl.action?projectId=${projectId }&contractCatelogKey=SX,DK',
						    method: 'POST',
						    collapsible:true,
						    rownumbers: true,
						    singleSelect: false,
						    pagination: true,
						    toolbar: '#toolbar_contract',
						    idField: 'pid',
						    fitColumns:true
						    ">
						<!-- 表头 -->
						<thead><tr>
						    <th data-options="field:'pid',checkbox:true,width:50" ></th>
						    <th data-options="field:'contractTemplateId'"  hidden="true"></th>
						    <th data-options="field:'contractUrl'"  hidden="true"></th>
						    <th data-options="field:'contractTypeText',width:100"  >合同类型</th>
						    <th data-options="field:'contractName',width:200"  >合同名称</th>
						    <th data-options="field:'contractNo',width:100"  >合同编号</th>
						    <th data-options="field:'num',width:100"  >合同份数</th>
						    <th data-options="field:'firstNum',width:100" >甲方</th>
						    <th data-options="field:'secondNum',width:100"  >乙方</th>
						    <th data-options="field:'cz',formatter:contractOperationFilter" >操作</th>
						</tr></thead>
					</table>
				</div>
			</div>
			
	</div>
	<div id="dlg-openContract">
		<center>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveCreditContract()">生成</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#openContract').dialog('close')">取消</a>
		</center>
	</div>
	<div id="openContract" class="easyui-dialog" style="width:870px;height:400px;padding:10px;" 
		closed="true" buttons="#dlg-openContract">
		<div class="fitem">
			<label style="text-align: right;">合同名称：</label>
            <input name="contractName" id="contractName" style="width: 320px;" class="easyui-validatebox"  data-options="required:true" />
		</div>
		<div class="fitem">
			<label style="text-align: right;">合同模版：</label>
            <input name="" id="templateName" class="easyui-combobox" style="width: 320px;"  data-options="valueField:'pid',textField:'comboboxTemplateText',url:'<%=basePath%>beforeLoanController/getTempLateList.action'" />
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="ajaxContract()">读取合同信息</a>
		</div>
		<div class="fitem">
			<label style="text-align: right;">所属合同：</label>
            <input name="" id="add_parent_contract_id" class="easyui-combobox" style="width: 320px;" editable="false" data-options="valueField:'pid',textField:'contractName',url:'<%=basePath%>contractInfoController/getParentContracts.action?projectId=${projectId }&isParent=0'" />
		</div>
		<div id="templateDialog" class="datagrid-btable" style="width: 800px;">
			
		</div>
	</div>
	
	<div id="edit_template-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveEidtContent()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editContractDialog').dialog('close')">关闭</a>
	</div>
	<div id="editContractDialog" class="easyui-dialog" style="width:850px;height:400px;"
        closed="true" buttons="#edit_template-buttons">
    <center> 
    	<div class="fitem" style="margin-top:5px;padding-left:50px;">
			<label style="text-align: left;">合同名称：</label>
            <input name="contractName" id="editcontractName" class="easyui-validatebox"  style="width:350px;" />
		</div>
		<div class="fitem" style="margin-top:5px;padding-left:50px;">
			<label style="text-align: left;">合同模版：</label>
            <input name="edit_templateName" id="edit_templateName" class="easyui-combobox" style="width: 350px;" editable="false" data-options="valueField:'pid',textField:'comboboxTemplateText',url:'<%=basePath%>beforeLoanController/getTempLateList.action'" />
		</div>
		<div class="fitem" style="margin-top:5px;padding-left:50px;">
			<label style="text-align: left;">所属合同：</label>
            <input name="" id="edit_parent_contract_id" class="easyui-combobox" style="width: 350px;" editable="false" data-options="valueField:'pid',textField:'contractName',url:'<%=basePath%>contractInfoController/getParentContracts.action?projectId=${projectId }&isParent=1'" />
		</div>
	    <div id="edit_templateDialog" class="datagrid-btable" style="width: 800px;">
	    	 
		</div>
	</center>
</div>

 	<div id="dlg_dzywbz_but">		
	    <center>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="openCreditContract()">生成合同</a>
	    </center>
	</div>
 	<!-- 抵押合同、质押合同、保证合同时  -->
 	<div id="dlg_dzywbz" class="easyui-dialog" style="width:550px;height:300px;padding:10px" 
			closed="true" buttons="#dlg_dzywbz_but" data-options="modal:true">
 		<table id="dzywbz_grid" class="easyui-datagrid" 
				style="height:auto;width: auto;"
				data-options="
				    url: '',
				    method: 'get',
				    rownumbers: true,
				    singleSelect: true, 
				    fitColumns:true">
				<!-- 表头 -->
				<thead><tr>
				    <th data-options="field:'pid',checkbox:true" ></th>
				    <th data-options="field:'dataObject'" width="200px" >合同数据对象</th>
				</tr></thead>
			</table>
 	</div>
 	
 	<!-- 隐藏域 保存生成合同所需的参数  -->
 	<div style="display: none;">
	 	<input type="text" id="suretyId" />
	 	<input type="text" id="mortgagorId" />
	 	<input type="text" id="contractType" />
	 	<input type="text" id="customerType" />
 	</div>
 	
 </div>
 	
 	
<script type="text/javascript">
	//var isReadContract=0;
	// 选择的附件ID
	var assBaseIds="";
	var contractCatelog="";
	var mortgagorType=1;
	var mgrid="";
	
	function mgridType(mcontracType){
		contractCatelog=mcontracType;
		if(mcontracType=="SX"){
			mgrid="#grid_contract";
		}else if(mcontracType=="DY"){
			mgrid="#grid_contract22";
		}else if(mcontracType=="ZY"){
			mgrid="#grid_contract33";
		}else if(mcontracType=="BZ"){
			mgrid="#grid_contract44";
		}
	}
	
	// 打开选取抵质押物or保证合同窗口
	function openCreditsWindow(type){
		var xzflag=true;
		// 公用的URL
		var url = "";
		// 判断type的类型  1.授信 2.抵押  3.质押  4.保证
		if(type == 1){
			// 把URL地址改变为授信的接口
			url = BASE_PATH+"creditsController/getProjectAcctAndPublicManByProjectId.action?projectId="+projectId;
			xzflag=false;
			$('#contractType').val("SX");
		}else if(type == 2){
			// 把URL地址改变为抵押的接口
			url = BASE_PATH+"mortgageController/getProjectAssBaseByMortgageGuaranteeType.action?projectId="+projectId+"&mortgageGuaranteeType=1&oldProject=-1";
			$('#contractType').val("DY");
		}else if(type == 3){
			// 把URL地址改变为质押的接口
			url = BASE_PATH+"mortgageController/getProjectAssBaseByMortgageGuaranteeType.action?projectId="+projectId+"&mortgageGuaranteeType=2&oldProject=-1";
			$('#contractType').val("ZY");
		}else if(type == 4){
			// 把URL地址改变为保证的接口
			url = BASE_PATH+"secondBeforeLoanController/getAssureByProjectId.action?projectId="+projectId;
			$('#contractType').val("BZ");
		}
		var customerType=0;
		if(type !=1){
			customerType=$('#searchFrom'+type+type+' input:radio[name="customerType"]:checked').val();
		}
		$('#customerType').val(customerType);
		//授信合同 ，直接打开合同新增页面
		
		// 获取datagrid的数据
		$('#dzywbz_grid').datagrid('options').url = url;
		$('#dzywbz_grid').datagrid('reload'); 
		$('#dzywbz_grid').datagrid('clearSelections');
		$('#dzywbz_grid').datagrid({'singleSelect':xzflag});
		$('#dlg_dzywbz').dialog('open').dialog('setTitle', "合同生成数据对象选择");
		
		
	}
	
	// 打开新增合同页面
	function openCreditContract(){
		
		
		commonDebtorPids="";
		var mcontracType = $('#contractType').val();
		var customerType = $("#customerType").val();
		// 获取所选择的数据行
		var rows = $("#dzywbz_grid").datagrid('getSelections');
		// 判断是否选择数据 
		if(rows.length == 0){
			$.messager.alert("操作提示","请选择数据","error");
			return;
		}
		var templateCatelog=mcontracType;
		if(mcontracType == "BZ"){
			// 赋值担保ID
			$("#suretyId").val(rows[0].pid);
			$("#mortgagorId").val("");
		}else if(mcontracType == "DY"){
			// 赋值抵质押物ID
			mortgagorType=1;
			$("#mortgagorId").val(rows[0].pid);
			$("#suretyId").val("");
		}else if(mcontracType == "ZY"){
			// 赋值抵质押物ID
			mortgagorType=2;
			$("#mortgagorId").val(rows[0].pid);
			$("#suretyId").val("");
		}else if(mcontracType == "SX"){
			templateCatelog="SX,DK";
			// 判断是否选择数据 
			if(rows.length > 4){
				$.messager.alert("操作提示","最多只能选择4个合同对象","error");
				return;
			}
			
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
			
		}
		mgridType(mcontracType);
		
		// 附件ID
		assBaseIds="";
		for (var i = 0; i < rows.length; i++) {
			if(i>0)
			{
				assBaseIds+= ",";
			}	
			assBaseIds+=  rows[i].pid;
		}
		
		
		getContractTemplateCombox('templateName','add_parent_contract_id','templateDialog',customerType,templateCatelog,projectId);
		$('#openContract').dialog('open').dialog('setTitle', "新增合同");
		
		//清空合同表单
		$('#templateDialog').html('');
		$("#contractName").val('');
		$("#add_parent_contract_id,#templateName").combobox('setValue','0');
		
		// 关闭当前窗口
		$('#dlg_dzywbz').dialog('close');
	}
	
	function editCreditContract(mcontracType){
		mgridType(mcontracType)
		var row = $(mgrid).datagrid('getSelections');
		if (row.length == 1) {
			$('#editContractDialog').dialog('open').dialog('setTitle', "合同编辑");
			$("#editcontractName").val(row[0].contractName);
			$('#edit_parent_contract_id').combobox({url:"${basePath}contractInfoController/getParentContracts.action?projectId="+projectId+"&isParent=1",valueField:'pid',textField:'contractName' });
			$("#edit_parent_contract_id").combobox("setValue",row[0].parentId);
			$('#edit_parent_contract_id').combobox('disable');
			
			if(mcontracType=='SX'){
				mcontracType='SX,DK';
			}
			$("#edit_templateName").combobox({valueField:'pid',textField:'comboboxTemplateText',url:'${basePath}beforeLoanController/getTempLateList.action?templateCatelogText='+mcontracType});
			$("#edit_templateName").combobox("setValue",row[0].contractTemplateId);
			$('#edit_templateName').combobox('disable');
			editContract("edit_templateDialog",row[0].contractUrl,row[0].contractTemplateId,row[0].pid);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	
	
	function deleteCreditContract(mcontracType){
		mgridType(mcontracType)
		var rows = $(mgrid).datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		var pids = "";
		for (var i = 0; i < rows.length; i++) {
			var contractName=getChildContractName(rows[i].pid);
			if(contractName!=null && contractName !=''){
				$.messager.alert("操作提示", "请先删除子类合同【"+contractName+"】", "error");
				return ;
			}
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
						"${basePath}/contractInfoController/deleteContracts.action",
						{
							pids : pids
						},
						function(ret) {
							 $(mgrid).datagrid('reload');
							 $(mgrid).datagrid('clearChecked'); 
						}, 'json');
				}
			});
	}
	
	// ajax 动态加载合同信息
	function ajaxContract(){
		var tempLateId = $("#templateName").combobox("getValue");
		if(tempLateId ==0 || tempLateId==-1){
			$.messager.alert("操作提示", "请选择合同模板!", "error");
			return ;
		}
		var contractType = $("#contractType").val();		
		var suretyId = $("#suretyId").val()==""?-1:$("#suretyId").val();
		var mortgagorId = $("#mortgagorId").val()==""?-1:$("#mortgagorId").val();	
		loadProjectContract("templateDialog",contractType,tempLateId,projectId,projectId,projectId,mortgagorId,suretyId,-1,mortgagorType,commonDebtorPids,assBaseIds);
		
	}
	
	
	// 新增合同页面
	function saveCreditContract(){
		var tempLateId = $("#tempLateId").val();
		var contractName=$("#contractName").val();
		var contractParentId=$("#add_parent_contract_id").combobox('getValue');
		
		if(contractName==null || contractName==''){
			$.messager.alert("操作提示", "请填写合同名称!", "error");
			return ;
		}
		if(isReadContract==0){
			$.messager.alert("操作提示", "请先读取合同内容!", "error");
			return ;
		}
		if(checkContractCount(projectId,tempLateId,1)){
			$.messager.alert("操作提示", "一个项目不能新增多个授信合同!", "error");
			return;
		}
		if(checkContractCount(projectId,tempLateId,2)){
			$.messager.alert("操作提示", "一个项目不能新增多个借款合同!", "error");
			return;
		}
		
		//判断是否要选择父合同
		if(isParent==1){
			if(contractParentId==0){
				$.messager.alert("操作提示", "请选择所属合同!", "error");
				return;
			}
		}
		var content = "";
		//判断表格是否存在
		var contractTableContent="";
		var contractT=document.getElementById("add_contract_table");
		if(contractT){
			endEditingsItContract("add_contract_table");
			contractTableContent=JSON.stringify($("#add_contract_table").datagrid('getRows'));
		}

		$("#templateDialog").find("input").each(function (i){
			var key = $(this).attr("id");
			var value = $(this).val();
			var flag = $(this).attr("name");
			if(key!="tempLateId"){
				content=content+key+":"+value+":"+flag+"###";
			}
		});
		
		$.ajax({
			type: "POST",
	        url: "${basePath}contractInfoController/saveContract.action",
	        data:{"content":content,
	        	  "tempLateId":tempLateId,
	        	  "contractCatelog":contractCatelog,
	        	  "contractName":contractName,
	        	  "projectId":projectId,
	        	  "contractTableContent":contractTableContent,
	        	  "contractParentId":contractParentId},
	        dataType: "json",
	        success: function(data){
        		 alert("生成合同成功");
        		 $(mgrid).datagrid('reload');
        		 $('#openContract').dialog({close:function(){
        			 $(this).dialog("destroy").remove();
        		 }});
	        },
	        error: function(data){
	        	alert("合同生成错误");
	        }
		}); 
	}
	
	//保存编辑后的类容
	function saveEidtContent(){
		var content = "";
		//判断表格是否存在
		var contractTableContent="";
		var contractT=document.getElementById("edit_contract_table");
		if(contractT){
			endEditingsItContract("edit_contract_table");
			contractTableContent=JSON.stringify($("#edit_contract_table").datagrid('getRows'));
		}
		
		$("#edit_templateDialog").find("input").each(function (i){
			var key = $(this).attr("id");
			var value = $(this).val();
			var flag = $(this).attr("name");
			if(key!="edit_contractId" && key!="edit_contractUrl" && key!="edit_tempLateId"){
				content=content+key+":"+value+":"+flag+"###";
			}
			
		});
		var contractId = $("#edit_contractId").val();
		var contractUrl = $("#edit_contractUrl").val();
		var tempLateId = $("#edit_tempLateId").val();
		var contractName=$("#editcontractName").val();

		$.ajax({
			type: "POST",
	        url: "${basePath}contractInfoController/saveEditDocument.action",
	        data:{"content":content,"contractId":contractId,"contractUrl":contractUrl,"tempLateId":tempLateId,"contractTableContent":contractTableContent,"contractName":contractName},
	        dataType: "json",
	        success: function(data){
	        	if(data)
	        	{
	        	 	alert("编辑成功");
		        	 $(mgrid).datagrid('reload');
		        	 $('#editContractDialog').dialog('close');
	        	}
	        	else
	        	{
	        		alert("编辑失败");
	        	}	
	       
	        }
		});
	}
 
	function changeEvent(mcontracType,str){
		mgridType(mcontracType);
		$(mgrid).datagrid({url:"getContractListUrl.action?customerType="+str+"&projectId="+projectId+"&contractCatelogKey="+contractCatelog }); 
		$(mgrid).datagrid('clearChecked');
	}
	$(document).ready(function(){
		if(projectName){
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
	 	}
		if("task_LoanRequest"==workflowTaskDefKey || workflowTaskDefKey == "task_ExaminerCheck" || isEdit == 'isEdit' ||"task_BusinessRequest"==workflowTaskDefKey){
			
		}else{
			$('.contract_dis .easyui-combobox').attr('disabled','disabled');
			$('.contract_dis input[type="checkbox"]').attr('disabled','disabled');
			$('.contract_dis .easyui-textbox').attr('disabled','disabled');
			$('.contract_dis .easyui-linkbutton ,.contract_dis a').removeAttr('onclick');
			$('.contract_dis a font').attr('color','gray');
			$('.contract_dis .easyui-linkbutton ,.contract_dis  input:not(.download),.contract_dis  textarea').attr('disabled','disabled');
			$('.contract_dis .easyui-linkbutton ,.contract_dis  input:not(.download),.contract_dis  textarea').attr('readOnly','readOnly');
			$('.contract_dis .easyui-linkbutton ,.contract_dis  input:not(.download),.contract_dis  textarea').addClass('l-btn-disabled');
		}
	})
</script>

<div id="include_projectArchive2">
	<%@ include file="loan_contract_like.jsp" %>  
</div>	
 