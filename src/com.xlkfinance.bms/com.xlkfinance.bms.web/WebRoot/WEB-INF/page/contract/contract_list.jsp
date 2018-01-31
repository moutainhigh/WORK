<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
 <style>.datagrid-cell-c4-fileDesc{width:100px;}
 	#templateDialog .fitem label{width:200px;}
 	#templateDialog .fitem .text_style{width:200px;}
 </style>
 <script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/project_contract.js"></script>
 <body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<div class="contractListDiv" style="height:100%;">
	<table id="grid" title="合同列表" class="easyui-datagrid" 
	style="height:100%;width:auto"
	data-options="
	   		url: '${basePath}contractInfoController/pageContractList.action',
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:false,
		    singleSelect: false,
			selectOnCheck:true,
			checkOnSelect: false">
	<!-- 表头 -->
		<thead><tr>
		 	<th hidden="true" data-options="field:'pid',width:1"></th>
			<th data-options="field:'contractNo'"  >合同编号</th>
			<th data-options="field:'contractTypeText' "  >合同类型</th>
			<th data-options="field:'contractName'"  >合同名称</th>
			<th data-options="field:'projectName'"  >项目名称</th>
			<th data-options="field:'projectNuber'"  >项目编号</th>
			<th data-options="field:'fw',formatter:fwFilter">是否法务确认</th>
			<th data-options="field:'qs',formatter:qsFilter">是否签署</th> 
			<th data-options="field: 'isLegalConfirmation',hidden:true"></th>
		    <th data-options="field: 'isSigned',hidden:true"></th>
			<th data-options="field:'signedDate',formatter:dateFileter">签署日期</th>
			<th data-options="field:'cz',formatter:czFilter" >操作</th>
			<th hidden="true" data-options="field:'contractUrl',width:1"></th>
			<th hidden="true" data-options="field:'contractTemplateId',width:1"></th>
			<th hidden="true" data-options="field:'parentId'"></th>
		</tr></thead>
	</table>
	</div>

 
 
 <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<!-- 查询条件 -->
	<table width="90%">
		<tr>
			<td class="label_right">合同名称：</td>
			<td><input class="easyui-textbox" id="contractName" name = "contractName"></td>
			<td class="label_right">合同编号：</td>
			<td ><input class="easyui-textbox" id="contractNo" name = "contractNo"> </td>
		</tr>
		
		<tr>
			<td class="label_right">客户名称：</td>
			<td><input class="easyui-textbox" id="customerName" name = "customerName"> </td>
			<td class="label_right">客户类型：</td>
			<td>
							 <input id="searchCusType" name="searchCusType" class="easyui-combobox" editable="false" panelHeight="auto" 
					        data-options="
					        valueField:'pid',
					        textField:'lookupDesc',
					        value:'-1',
					        url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" />
			</td>
			<td>
				<div class="searchET">
				<table>
					<tr>
						
						<td class="label_right ">经济行业类别：</td>
						<td >
							<input id="searchEconomyType" class="easyui-combobox" name="searchEconomyType" editable="false" 
					            data-options="
					            panelHeight:'auto',
					            valueField:'pid',		            
					            textField:'lookupDesc',
					            value:'-1',
					            url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /> 
						</td>
					</tr>
				</table>
		       </div>
			</td>			
			
		</tr>
		<tr>
			<td class="label_right">项目名称：</td>
			<td><input class="easyui-textbox" id="projectName" name = "projectName"> </td>
			<td class="label_right">项目编号：</td>
			<td ><input class="easyui-textbox" id="projectNuber" name = "projectNuber"></td>
			<td width="260"> <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="dosubmit()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="doclear()">重置</a></td>
		</tr>
	</table>
	</div>
	</div>
	</div>
	</body>
<!-- 保存 和取消按钮 -->

<div id="access-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAttachment()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#accessDialog').dialog('close')">关闭</a>
</div>
<div id="accessDialog" class="easyui-dialog" style="width:880px;height:400px;top:100px"
        closed="true" buttons="#access-buttons">
    <div style="margin-left: 20px">
    <form action="${basePath}contractInfoController/addContractAttachment.action" method="post" id="attachment">
	    <input type="hidden" name="contractId" class="fileContractId">
	    <div style="margin-left: 50px">
	    	<table>
	    		<tr>
	    			<td style="height: 25px;">合同名称：</td>
	    			<td><input class="easyui-textbox" id="attContractName" style="width: 200px;" data-options="editable:false"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">合同编号：</td>
	    			<td><input class="easyui-textbox" id="attContractNumber" style="width: 200px" data-options="editable:false"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">合同类型：</td>
	    			<td><input class="easyui-combobox" id="attContractType" style="width: 200px" data-options="editable:false" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">附件名称：</td>
	    			<td><input class="easyui-textbox" name="attachmentFileName" style="width: 200px" required="true"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">线下资料位置：</td>
	    			<td><input class="easyui-textbox" name="attachmentLocation" style="width: 200px"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">线下份数：</td>
	    			<td><input class="easyui-numberbox" name="offlineCnt" style="width: 200px"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">线上份数：</td>
	    			<td><input class="easyui-numberbox" name="onlineCnt" style="width: 200px"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">备注：</td>
	    			<td><input class="easyui-textbox" name="remark" style="width: 200px"/></td>
	    		</tr>
	    	</table>
	    </div>
    </form>
    <!-- 操作按钮 -->
	<div style="padding-bottom:5px" id="toolbar_Data">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addFileAccessArray()">新增附件</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delFileAccessArray()">删除</a>
	</div> 
	    
    <table id="accessGrid" class="easyui-datagrid" 
		style="height:200px;width: 800px;" data-options="toolbar:'#toolbar_Data'">
	</table>
	</div>
</div>

<div id="template-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveEidtContent()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#templateDialog').dialog('close')">关闭</a>
</div>

<div id="templateDialog" class="easyui-dialog" style="width:900px;height:400px;"
        closed="true" buttons="#template-buttons">
    <center> 
    	<div class="fitem" style="margin-top:5px;padding-left:50px;">
			<label style="text-align: left;">合同名称：</label>
            <input name="contractName" id="editcontractName" class="easyui-validatebox" style="width:350px;" />
		</div>
		<div class="fitem" style="margin-top:5px;padding-left:50px;">
			<label style="text-align: left;">合同模版：</label>
            <input name="edit_templateName" id="edit_templateName" class="easyui-combobox" style="width: 350px;" editable="false" data-options="valueField:'pid',textField:'comboboxTemplateText',url:'<%=basePath%>beforeLoanController/getTempLateList.action'" />
		</div>
		<div class="fitem" style="margin-top:5px;padding-left:50px;">
			<label style="text-align: left;">所属合同：</label>
            <input name="" id="edit_parent_contract_id" class="easyui-combobox" style="width: 350px;" editable="false" data-options="valueField:'pid',textField:'contractName',url:'<%=basePath%>contractInfoController/getParentContracts.action?projectId=${projectId }&isParent=1'" />
		</div>
	    <div id="edit_templateDialog" class="datagrid-btable" style="width: 860px;height:auto;min-height:280px;">
	    	 
		</div>
	</center>
</div>

<div id="fileDialog" class="easyui-dialog" style="width:500px;height:200px;" closed="true">
	<form action="${basePath}contractInfoController/addAccess.action" method="post" enctype="multipart/form-data" id="fileDialogForm">
	<input type="hidden" name="contractId" class="fileContractId">
	<table style="margin-left: 30px">
		<tr>
			<td style="height: 25px;">选择文件类型：</td>
			<td><input class="easyui-combobox" name="fileType" value="合同附件资料" readonly="readonly" style="width: 300px"/></td>
		</tr>
		<tr>
			<td style="height: 25px;">文件选择：</td>
			<td><input class="easyui-filebox" name="file2" data-options="buttonText:'选择文件',prompt:'选择一个文件...',onChange:setValueDo" style="width: 300px" required="true"></td>
		</tr>
		<tr>
			<td style="height: 25px;">文件名称：</td>
			<td><input class="easyui-textbox" name="fileName" id="fileName" style="width: 300px" required="true"></td>
		</tr>
		<tr>
			<td style="height: 25px;">上传备注：</td>
			<td><input class="easyui-textbox" name="fileDesc" style="width: 300px"></td>
		</tr>
		<tr>
			<td style="height: 25px;" colspan="2">
				<input type="button" value="上传" onclick="saveFileDialog()" style="width: 80px;height: 25px;">
				<input type="button" value="取消" onclick="javascript:$('#fileDialog').dialog('close')" style="width: 80px;height: 25px;">
			</td>
		</tr>
	</table>
	</form>
</div>

<div id="editDescDialog" class="easyui-dialog" style="width:500px;height:150px;" closed="true">
	<form action="${basePath}contractInfoController/editAccessDesc.action" method="post" id="descDialogForm">
	<input type="hidden" name="pid" class="pid">
	<table style="margin-left: 30px">
		<tr>
			<td style="height: 25px;">选择文件类型：</td>
			<td><input class="easyui-combobox" name="fileType" value="合同附件资料" readonly="readonly" style="width: 300px"/></td>
		</tr>
		<tr>
			<td style="height: 25px;">上传备注：</td>
			<td><input class="easyui-textbox" name="fileDesc" id="fileDesc" style="width: 300px"></td>
		</tr>
		<tr>
			<td style="height: 25px;" colspan="2">
				<input type="button" value="保存" onclick="saveDescDialog()" style="width: 80px;height: 25px;">
				<input type="button" value="取消" onclick="javascript:$('#editDescDialog').dialog('close')" style="width: 80px;height: 25px;">
			</td>
		</tr>
	</table>
	</form>
</div>

<div id="editFileDialog" class="easyui-dialog" style="width:500px;height:150px;" closed="true">
	<form action="${basePath}contractInfoController/editAccess.action" method="post" enctype="multipart/form-data" id="editFileDialogForm">
	<input type="hidden" name="pid" class="pid">
	<table style="margin-left: 30px">
		<tr>
			<td style="height: 25px;">文件选择：</td>
			<td><input class="easyui-filebox" name="file2" data-options="buttonText:'选择文件',prompt:'选择一个文件...',onChange:setValueDo1" style="width: 300px" required="true"></td>
		</tr>
		<tr>
			<td style="height: 25px;">文件名称：</td>
			<td><input class="easyui-textbox" name="fileName" id="fileName1" style="width: 300px" required="true"></td>
		</tr>
		<tr>
			<td style="height: 25px;" colspan="2">
				<input type="button" value="上传" onclick="editFileDialog()" style="width: 80px;height: 25px;">
				<input type="button" value="取消" onclick="javascript:$('#editFileDialog').dialog('close')" style="width: 80px;height: 25px;">
			</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">
	$(function(){
		$('.searchET').css('display','none');
		$('#searchCusType').combobox({
			onSelect:function(param){
				if(param.pid=='2'){
					$('.searchET').css('display','table-cell');
				}else{
					$('.searchET').css('display','none');
				}
			}
		});
	})
	//合同编辑
	function editContractInfo(contractUrl,tempLateId,contractId,contractName,projectId,parentId){
	 $('#templateDialog').dialog('open').dialog('setTitle', "合同编辑");
	 $("#editcontractName").val(contractName);
	 $('#edit_parent_contract_id').combobox({url:"${basePath}contractInfoController/getParentContracts.action?projectId="+projectId+"&isParent=1",valueField:'pid',textField:'contractName' });
	 $("#edit_parent_contract_id").combobox("setValue",parentId);
	 $('#edit_parent_contract_id').combobox('disable');
		
		
	$("#edit_templateName").combobox({valueField:'pid',textField:'comboboxTemplateText',url:'${basePath}beforeLoanController/getTempLateList.action?templateCatelogText=ALL'});
	$("#edit_templateName").combobox("setValue",tempLateId);
	$('#edit_templateName').combobox('disable');
	  editContract("edit_templateDialog",contractUrl,tempLateId,contractId);
		
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
	        	 if(data=="true" || data==true){
	        		 alert("编辑成功");
	        		 $('#templateDialog').dialog('close');
	        		 $('#grid').dialog('reload');
	        		 
	        	 }
	        }
		});
	}
		
	//模糊查询
	function dosubmit(){
		var contractNo = $("#contractNo").val();
		var contractName = $("#contractName").val();
		var projectNuber = $("#projectNuber").val();
		var projectName = $("#projectName").val();
		var customerName = $("#customerName").val();
		var economyType =  $('#searchEconomyType').combobox('getValue');
		var customerType =  $('#searchCusType').combobox('getValue');
		if(contractNo=="" && contractName=="" && projectNuber=="" && projectName=="" && customerName=="" && economyType=="-1" && customerType=="-1"){
			$("#grid").datagrid({
			    url: '${basePath}contractInfoController/pageContractList.action',
			    method: 'POST',
			    rownumbers: true,
			    pagination: true,
			    toolbar: '#toolbar',
			    idField: 'pid',
			    fitColumns:false,
			    singleSelect: false,
				selectOnCheck:true,
				checkOnSelect: false,
			    columns: [[
			        { field: 'contractNo', title: '合同编号', width: 100},
			        { field: 'contractTypeText', title: '合同类型'},
			        { field: 'contractName', title: '合同名称'},
			        { field: 'projectName', title: '项目名称'},
			        { field: 'projectNuber', title: '项目编号', width: 100},
			        { field: 'fw', title: '是否法务确认', width: 90,formatter:fwFilter},
			        { field: 'qs', title: '是否签署', width: 70,formatter:qsFilter},
			        { field: 'signedDate', title: '签署日期', width: 90,formatter:dateFileter}, 
			        { field: 'isLegalConfirmation',hidden:true, title: '是否法务确认', width: 10},
			        { field: 'isSigned',hidden:true, title: '是否签署', width: 10},
			        { field: 'cz', title: '操作', width:410,formatter:czFilter},
			        { field: 'contractUrl', title: '合同文件目录',hidden:true},
			        { field: 'projectId', title: '项目ID',hidden:true},
			        { field: 'contractTemplateId', title: '，模板ID',hidden:true},
			        { field: 'parentId', hidden:true}
			    ]]
			}); 
		 
		 return;
		}
		var contract ={"contractNo":contractNo,"contractName":contractName,
	        "projectNuber":projectNuber,"projectName":projectName,
	        "customerName":customerName,"economyType":economyType,
	        "customerType":customerType
			};
		$("#grid").datagrid({
		    url: '${basePath}contractInfoController/pageContractLike.action',
		    method: 'POST',
		    queryParams:contract,
		    rownumbers: true,
		    pagination: true,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:false,
		    singleSelect: false,
			selectOnCheck:true,
			checkOnSelect: false,
		    columns: [[
		        { field: 'contractNo', title: '合同编号', width: 100},
		        { field: 'contractTypeText', title: '合同类型'},
		        { field: 'contractName', title: '合同名称'},
		        { field: 'projectName', title: '项目名称'},
		        { field: 'projectNuber', title: '项目编号', width: 100},
		        { field: 'fw', title: '是否法务确认', width: 90,formatter:fwFilter},
		        { field: 'qs', title: '是否签署', width: 70,formatter:qsFilter},
		        { field: 'signedDate', title: '签署日期', width: 90,formatter:dateFileter},
		        { field: 'isLegalConfirmation',hidden:true, title: '是否法务确认', width: 10},
		        { field: 'isSigned',hidden:true, title: '是否签署', width: 10},
		        { field: 'cz', title: '操作', width: 410,formatter:czFilter},
		        { field: 'contractUrl', title: '合同文件目录', width: 1,hidden:true},
		        { field: 'projectId', title: '项目ID', width: 1,hidden:true},
		        { field: 'contractTemplateId', title: '，模板ID', width: 1,hidden:true},
		        { field: 'parentId', hidden:true}
		    ]]
		});
		
	}
	
	
	function showFileAccess(pid,cName,cTypeText,projectNuber){
		$('#accessDialog').dialog('open').dialog('setTitle', "合同附件查看编辑");
		$('#attachment').form('reset');
		$('.fileContractId').val(pid);
		$('#attContractName').textbox('setValue',cName);
		if(projectNuber!=null && projectNuber!="null"){
			$('#attContractNumber').textbox('setValue',projectNuber);
		}
		$('#attContractType').combobox('setValue',cTypeText);
		$.ajax({
			url:'${basePath}contractInfoController/getContractAttachment.action',
			type:'post',
			data:{pid:pid},
			dataType:'json',
			success:function(data){
				if(data.contractId!=0){
					$('#attachment').form('load',data);
				}
			}
		});
		$("#accessGrid").datagrid({
		    url: '${basePath}contractInfoController/pageAccesstList.action',
		    method: 'POST',
		    queryParams:{"pid":pid},
		    rownumbers: true,
		    pagination: true,
		    idField: 'pid',
		    fitColumns:true,
		    singleSelect: false,
			selectOnCheck:true,
			checkOnSelect: false,
		    columns: [[
		        { field: 'pid',checkbox:true,width:50},
		        { field: 'fileType', title: '文件类型', width: 50},
		        { field: 'fileName', title: '文件名称', width: 50},
		        { field: 'fileSize', title: '文件大小', width: 50,formatter:sizeFileter},
		        { field: 'uploadDttm', title: '上传日期', width: 50,formatter:convertDate},
		        { field: 'fileDesc', title: '备注', width: 50},
		        { field: 'fileUrl',hidden:true, width: 1},
		        { field: 'do',title: '操作', width: 80,formatter:doFileter}
		    ]]
		});
	}
	
	//删除文件
	function delFileAccess(pid){
		var pidArray = new Array();
		pidArray.push(pid);
		if (confirm("是否确认删除?")) {
			$.ajax({
				type: "POST",
		        url: "${basePath}contractInfoController/deleteAccess.action",
		        data:{"pidArray":pidArray},
		        dataType: "text",
		        success: function(data){
		        	$('#accessGrid').datagrid('reload');
		        	$.messager.alert("系统提示","删除附件成功","success");
		        }
			});
		}
	}
	
	//批量删除文件
	function delFileAccessArray(){
		var pidArrays = $("#accessGrid").datagrid("getSelections");
		if(pidArrays.length<1){
			$.messager.alert("系统提示","请选择要删除的附件","error"); 
			return;
		}
		var pidArray = new Array();
		for (var i = 0; i < pidArrays.length; i++) {
			pidArray.push(pidArrays[i].pid);
		}
		if (confirm("是否确认删除?")) {
			$.ajax({
				type: "POST",
		        url: "${basePath}contractInfoController/deleteAccess.action",
		        data:{"pidArray":pidArray},
		        dataType: "text",
		        success: function(data){
		        	$('#accessGrid').datagrid('reload');
		        	$.messager.alert("系统提示","删除附件成功","success"); 
		        	
		        }
			});
		}
	}
	
	//合同预览
	function contractReview(url){
		$.ajax({
			type: "POST",
	        url: "${basePath}contractInfoController/reviewDocument.action",
	        data:{"url":url},
	        dataType: "text",
	        success: function(data){
	        	data = data.replace('"',"").replace('"',"");
	        	//var tempwindow=window.open("${basePath}"+data);
	        	//window.open("${basePath}"+data);
	        	//tempwindow.location("${basePath}"+data);
	        	openUrl("${basePath}"+data);
	        	
	        }
		});
	}
	function openUrl( url ){
	       var f=document.createElement("form");
	       f.setAttribute("action" , url );
	       f.setAttribute("method" , 'get' );
	       f.setAttribute("target" , '_black' );
	       document.body.appendChild(f);
	       f.submit();
	    }
	
	
	function doFileter(value,row,index){
		var downloadDom = "<a href='${basePath}beforeLoanController/downloadData.action?path="+row.fileUrl+"&fileName="+row.fileName+"'><font color='blue'>下载</font></a> | ";
		var editDom = "<a href='javascript:void(0)' onclick='editFileAccess("+row.pid+",\""+row.fileDesc+"\",1)'><font color='blue'>编辑</font></a> | ";
		var reload = "<a href='javascript:void(0)' onclick='editFileAccess("+row.pid+",\""+row.fileDesc+"\",2)'><font color='blue'>重新上传</font></a> | ";
		var projectDom = "<a href='javascript:void(0)' onclick='delFileAccess("+row.pid+")'><font color='blue'> 删除</font></a>";
		return downloadDom+editDom+reload+projectDom;
	}
	
	function sizeFileter(value,row,index)
	{
		var fileSize = value;
		var num = new Number();
		var unit = '';
		
		if (fileSize > 1*1024*1024*1024){
			num = fileSize/1024/1024/1024;
			unit = "G"
		}
		else if (fileSize > 1*1024*1024){
			num = fileSize/1024/1024;
			unit = "M"			
		}
		else if (fileSize > 1*1024){
			num = fileSize/1024;
			unit = "K"
		}
		else{
			return fileSize;
		}
		
		return num.toFixed(2) + unit;
		
	}
	
	function czFilter(value,row,index){
		var projectDom = "<a href='javascript:void(0)' onclick='formatterProjectName.disposeClick("+row.projectId+",\""+row.projectNuber+"\")'><font color='blue'>项目详情</font> | </a>";
		var downloadDom = "<a href='${basePath}beforeLoanController/downloadData.action?path="+row.contractUrl+"&fileName="+row.contractName+"'><font color='blue'>点击下载</font></a> | ";
		var contractReadDom = "<a href='javascript:void(0)' onclick='contractReview("+'"'+row.contractUrl+'"'+")'><font color='blue'>合同预览</font></a> | ";
		var contractEditDom = "<a href='javascript:void(0)' onclick='editContractInfo("+'"'+row.contractUrl+'"'+","+'"'+row.contractTemplateId+'"'+","+'"'+row.pid+'"'+","+'"'+row.contractName+'"'+","+'"'+row.projectId+'"'+","+'"'+row.parentId+'"'+")'><font color='blue'>合同编辑</font></a> | ";
		var fjReadDom = "<a href='javascript:void(0)' onclick='showFileAccess("+row.pid+",\""+row.contractName+"\",\""+row.contractTypeText+"\",\""+row.contractNo+"\")'><font color='blue'>附件查看</font></a> |";
		var errorData = "<a href='javascript:void(0)' onclick='editCreditContractData("+'"'+row.contractCatelogKey+'"'+","+'"'+row.contractTemplateId+'"'+","+'"'+row.pid+'"'+","+'"'+row.projectId+'"'+","+'"'+row.contractName+'"'+")'><font color='blue'>错误数据编辑</font></a> ";
		return projectDom+downloadDom+contractReadDom+contractEditDom+fjReadDom+errorData;
	}
	
	function showProject(pId,pName){
		window.parent.addTab("${basePath}beforeLoanController/addNavigation.action?projectId="+pId+"&param='refId':'"+pId+"','projectName':'"+pName+"'","项目详情");	
	}
	function fwFilter(value,row,index){
		if(row.isLegalConfirmation==1 || row.isLegalConfirmation=='1'){
			return "是";
		}else{
			return "否";
		}
		 
	}
	
	function qsFilter(value,row,index){
		if(row.isSigned==1 || row.isSigned=='1'){
			return "是";
		}else{
			return "否";
		}
		 
	}
	
	function dateFileter(value,row,index){
		if(value!="" && null!=value && undefined!=value && 'undefined'!=value){
			return  value.substring(0,10);
		}
	}
	
	function doclear(){
		$("#contractNo").textbox('setValue','');
		$("#contractName").textbox('setValue','');
		$("#projectNuber").textbox('setValue','');
		$("#projectName").textbox('setValue','');
		$("#customerName").textbox('setValue','');
		$('#searchEconomyType').combobox('select','-1');
		$('#searchCusType').combobox('select','-1');
	}
	
	function saveAttachment(){
		$('#attachment').form('submit',{
			success:function(data){
				if(data==0){
					$.messager.alert("系统提示","附件保存失败","error"); 
				}else{
					$.messager.alert("系统提示","附件保存成功","success"); 
					$('#accessDialog').dialog('close');
				}
			}
		})
	}
	
	function addFileAccessArray(){
		$('#fileDialogForm').form('reset');
		$('#fileDialog').dialog('open').dialog('setTitle', "新增合同附件资料");
	}
	
	function setValueDo(newValue,oldValue){
		var value = newValue.replace(/.*(\/|\\)/, "");
		$('#fileName').textbox('setValue',value);
	}
	
	function setValueDo1(newValue,oldValue){
		var value = newValue.replace(/.*(\/|\\)/, "");
		$('#fileName1').textbox('setValue',value);
	}
	
	function saveFileDialog(){
		$('#fileDialogForm').form('submit',{
			success:function(data){
				var datas = strToJson(data);
				$.messager.alert("操作提示",datas.uploadStatus,"success");
				$('#fileDialog').dialog("close");
				$('#accessGrid').datagrid("reload");
			}
		})
	}	
	
	function editFileAccess(pid,desc,aId){
		if(aId==1){
			$('#descDialogForm').form('reset');
			$('.pid').val(pid);
			$('#fileDesc').textbox('setValue',desc);
			$('#editDescDialog').dialog('open').dialog('setTitle','编辑');
		}
		if(aId==2){
			$('#editFileDialogForm').form('reset');
			$('.pid').val(pid);
			$('#editFileDialog').dialog('open').dialog('setTitle','重新上传');
		}
		
	}
	
	function saveDescDialog(){
		$('#descDialogForm').form('submit',{
			success:function(data){
				var datas = strToJson(data);
				$.messager.alert("操作提示",datas.uploadStatus,"success");
				$('#editDescDialog').dialog("close");
				$('#accessGrid').datagrid("reload");
			}
		})	
	}
	
	function editFileDialog(){
		$('#editFileDialogForm').form('submit',{
			success:function(data){
				var datas = strToJson(data);
				$.messager.alert("操作提示",datas.uploadStatus,"success");
				$('#editFileDialog').dialog("close");
				$('#accessGrid').datagrid("reload");
			}
		})
	}
	
	
	function strToJson(str){
		return JSON.parse(str);
	} 
</script>
 