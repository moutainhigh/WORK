<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<link  type="text/css" href="/upload/css/fileUpload.css" rel="stylesheet"/>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
<div id="templateDiv" style="width:100%;height:100%;">
<div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<!-- 查询条件 -->
	<div style="paddiing:5px">
	<!-- 查询条件 -->
	<table>
		<tr>
			<td width="80" align="right">合同类别：</td>
			<td align="left">
				<input id="searchTemplateCatelog" class="easyui-combobox" name="searchTemplateCatelog" editable="false" 
		            data-options="
		            url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=CONTRACT_TEMPLATE_CATELOG',
		            method:'get',
		            panelHeight:'auto',
		            valueField:'pid',		            
		            textField:'lookupDesc',
		            value:'-1'" /> 
			</td>
			<td width="80" align="right">合同类型：</td>
			<td align="left">
				<input id="searchTempLateType" class="easyui-combobox" name="searchTempLateType" editable="false" 
		            data-options="
		            url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=CONTRACT_TEMPLATE_TYPE',
		            method:'get',
		            panelHeight:'auto',
		            valueField:'pid',		            
		            textField:'lookupDesc',
		            value:'-1'" /> 
			</td>
			<td width="100" align="right">合同模板名称：</td>
			<td><input class="easyui-textbox" name="templateNamessss" id="searchTtemplateName"/></td>
			<td width="60" align="right"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTempLateByLike()">查询</a></td>
			<td width="60" align="right"><a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="doclear()">重置</a></td>
		</tr>
	</table>
	</div>
	<!-- 操作按钮 -->
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="showAddDiv()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="showUpdateDiv()">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteTempLate()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editTempLate()">编辑文本项</a>
	</div> 
	
	</div>
	<div class="templateListDiv" style="height:100%">
	<table id="grid" title="合同模板管理" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: '${basePath}contractTemplateController/pageTempLateList.action',
	    method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    height:'100%',
	    fitColumns:true,
	    singleSelect: false,
		selectOnCheck:true,
		checkOnSelect: true,
		onClickRow:function(rowIndex,rowData){
			showuploadDiv(2,rowData);
		},
		onLoadSuccess:function(data){
			$('#grid').datagrid('clearChecked');
		}
	    ">
		<!-- 表头 -->
		<thead><tr>
		<th data-options="field:'pid',checkbox:true,width:50"></th>
		<th data-options="field:'templateName',width:70"  >合同模板名称</th>
		<th data-options="field:'templateCatelogText',width:45" align="center" >合同类别</th>
		<th data-options="field:'templateTypeText',width:35" align="center" >合同类型</th>
		<th data-options="field:'contractTypeCode',width:35" align="center" >类型代码</th>
		<th data-options="field:'applyType',width:25,formatter:applyTypeFilter" align="center" >适用项目类型</th>
		<th data-options="field:'cycleType',width:25,formatter:cycleTypeFilter" align="center" >项目循环类型</th>
		<th data-options="field:'templateOwner',width:25,formatter:templateOwnerFilter" align="center" >适用对象</th>
		<th data-options="field:'templateUseMode',width:25,formatter:templateUseModeFilter" align="center" >使用形式</th>	    
	    <th hidden="true" data-options="field:'templateUrl',width:10"></th>
	    <th hidden="true" data-options="field:'templateDesc',width:10"></th>
	    <th hidden="true" data-options="field:'contractNumberFun',width:10"></th>
	    <th data-options="field:'ss',width:20,formatter:downloadfiter" align="center" >下载模板</th>
	    <th data-options="field:'yy',width:20,formatter:uploadFilter"  align="center" >上传模板</th>
	    <th data-options="field:'updateDttm',width:35,formatter:updateDttmFilter" align="center" >上传/修改日期</th> 
		</tr></thead>
	</table>
	</div>
</div>
</div>
</div>
</body>
<div id="add-buttons" style="margin-top: 0">
	<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="subMitAddForm()" >保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addT').dialog('close')">取消</a>
</div>
<div id="addT"  class="easyui-dialog" style="width:650px;height:auto;top:80px;padding:10px 20px;" closed="true"  buttons="#add-buttons">
	<form id="addTemplates" name="addTemplates"   target="hidden_frame"
	action="${basePath}contractTemplateController/addTemplate.action" enctype="multipart/form-data" 
	method="post">
	<table>
		<tr>
			<td align="right" width="120" height="28"><font color="red">*</font>合同模板名称：</td>
			<td><input style="width:320px;" name="templateName" class="text_style" id="addtemplateName"/></td>
		</tr>
		<tr>
			<td align="right" width="120" height="28"><font color="red">*</font>合同类别：</td>
			<td>
				<input style="width:320px;" id="addtemplateCatelog" class="easyui-combobox" name="templateCatelog"  
		            data-options="
		            panelHeight:'auto',
		            valueField:'pid',
		            textField:'lookupDesc',		            
		            method:'get',
		            value:'-1',
		            url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=CONTRACT_TEMPLATE_CATELOG'"  />
			</td>
		</tr>
		<tr>
			<td align="right" width="120" height="28"><font color="red">*</font>合同类型：</td>
			<td>
				<input style="width:320px;" id="addtemplateType" class="easyui-combobox" name="templateType"  
		            data-options="
		            panelHeight:'auto',
		            valueField:'pid',
		            textField:'lookupDesc',
		            method:'get',
		            value:'-1',
		            url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=CONTRACT_TEMPLATE_TYPE'"  />
			</td>
		</tr>
		
		<tr>
			<td align="right" width="120" height="28"><font color="red">*</font>适用对象：</td>
			<td>
				<input name="templateOwner" type="radio" value="1" />个人
	           	<input name="templateOwner" type="radio" value="2"  />法人
	           	<input name="templateOwner" id="add_templateOwner" type="radio" value="3" />都适用
			</td>
		</tr>
		
		<tr>
			<td align="right" width="120" height="28"><font color="red"></font>使用形式：</td>
			<td>
				<input name="templateUseMode" type="radio" value="1" />最高额
	           	<input name="templateUseMode" type="radio" value="2"  />单笔
	           	<input name="templateUseMode" id="add_templateUseMode" type="radio" value="0" />都适用
			</td>
		</tr>
		<tr>
			<td align="right" width="120" height="28"><font color="red">*</font>适用项目类型：</td>
			<td>
				<input name="applyType" type="radio" value="1" />授信
	           	<input name="applyType" type="radio" value="2"  />贷款
	           	<input name="applyType" id="add_applyType" type="radio" value="3" />都适用
			</td>
		</tr>
		<tr>
			<td align="right" width="120" height="28"><font color="red"></font>项目循环类型：</td>
			<td>
				<input name="cycleType" type="radio" value="1" />最高额授信
	           	<input name="cycleType" type="radio" value="2" />最高额贷款
	           	<input name="cycleType" id="add_cycleType" type="radio" value="3" />都适用
			</td>
		</tr>
		
		<tr>
			<td align="right" width="120" height="28"><font color="red"></font>编号规则：</td>
			<td><input style="width:320px;"  name="contractNumberFun" class="text_style" id="addcontractNumberFun"/></td>
		</tr>
		
		<tr>
			<td align="right" width="120" height="28"><font color="red"></font>取值算法：</td>
			<td>
				<input style="width:320px;" id="addtemplateParFun" class="easyui-combobox" name="templateParFun"  
		            data-options="
		            panelHeight:'auto',
		            valueField:'pid',
		            textField:'lookupDesc',
		            method:'get',
		            value:'-1',
		            url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=CONTRACT_TEMPLATE_PAR_FUN'"  />
			</td>
		</tr>
		<tr>
			<td align="right" width="120" height="28"><font color="red"></font>类型代码：</td>
			<td><input style="width:320px;"  name="contractTypeCode" class="text_style" id="addcontractTypeCode"/></td>
		</tr>
		<tr>
			<td align="right" width="120" height="28">合同模板描述：</td>
			<td>
				<input class="easyui-textbox" style="width:320px;height:70px" data-options="required:false,multiline:true" name="templateDesc" id="addtemplateDesc"></textarea>
			</td>
		</tr>
		<tr>
			<td align="right" width="120" height="28"><font color="red">*</font>合同模板路径：</td>
			<td>
				<input style="width:320px;" class="text_style" type="text" id="txt2" name="txt2" />
				<input class="input_file" size="30" type="file" style="width:65px;" name="file2" id="file2" onchange="txt2.value=this.value"/>
			</td>
		</tr>
	</table>
	</form>
</div>

<div id="update-buttons" style="margin-top: 0">
	<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="updateTempLate()" >保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#updateT').dialog('close')">取消</a>
</div>

<div id="updateT"  class="easyui-dialog" style="width:600px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#update-buttons">
	<form id="updateTemplates" name="updateTemplates" method="post">
		<table>
			<tr>
				<td width="120" align="right" height="28"><font color="red">*</font>合同模板名称：</td>
				<td><input style="width:320px;" class="easyui-validatebox text_style" name="templateName" id="updateTemplateName"/></td>
			</tr>
			<tr>
				<td align="right" width="120" height="28"><font color="red">*</font>合同类别：</td>
				<td>
					<input style="width:320px;" id="updatetemplateCatelog" class="easyui-combobox" name="templateCatelog"  
			            data-options="
			            panelHeight:'auto',
			            valueField:'pid',
			            textField:'lookupDesc',
			            method:'get',
			            url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=CONTRACT_TEMPLATE_CATELOG'"  />
				</td>
			</tr>
			<tr>
				<td width="120" align="right" height="28"><font color="red">*</font>合同类型：</td>
				<td>
					<input style="width:320px;" id="updateTemplateType" class="easyui-combobox" name="templateType"  
		            data-options="
		            panelHeight:'auto',
		            valueField:'pid',
		            textField:'lookupDesc',
		            method:'get',
		            url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=CONTRACT_TEMPLATE_TYPE'"  />
				</td>
			</tr>
			
			<tr>
				<td align="right" width="120" height="28"><font color="red">*</font>适用对象：</td>
				<td>
					<input name="templateOwner" type="radio" value="1" />个人
		           	<input name="templateOwner" type="radio" value="2" />法人
		           	<input name="templateOwner" type="radio" value="3" />都适用
				</td>
			</tr>
			
			<tr>
				<td align="right" width="120" height="28"><font color="red"></font>使用形式：</td>
				<td>
					<input name="templateUseMode" type="radio" value="1" />最高额
		           	<input name="templateUseMode" type="radio" value="2"  />单笔
		           	<input name="templateUseMode" type="radio" value="0"  />都适用
				</td>
			</tr>
			<tr>
			<td align="right" width="120" height="28"><font color="red">*</font>适用项目类型：</td>
			<td>
				<input name="applyType" type="radio" value="1" />授信
	           	<input name="applyType" type="radio" value="2"  />贷款
	           	<input name="applyType" id="edit_applyType" type="radio" value="3" />都适用
			</td>
			</tr>
			<tr>
				<td align="right" width="120" height="28"><font color="red"></font>项目循环类型：</td>
				<td>
					<input name="cycleType" type="radio" value="1" />最高额授信
		           	<input name="cycleType" type="radio" value="2" />最高额贷款
		           	<input name="cycleType" id="edit_cycleType" type="radio" value="3" />都适用
				</td>
			</tr>
			<tr>
				<td width="120" align="right" height="28"><font color="red"></font>编号规则：</td>
				<td><input style="width:320px;" class="easyui-validatebox text_style" name="contractNumberFun" id="updatecontractNumberFun"/></td>
			</tr>
			
			<tr>
				<td align="right" width="120" height="28"><font color="red"></font>取值算法：</td>
				<td>
					<input style="width:320px;" id="updatetemplateParFun" class="easyui-combobox" name="templateParFun"  
			            data-options="
			            panelHeight:'auto',
			            valueField:'pid',
			            textField:'lookupDesc',
			            method:'get',
			            
			            url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=CONTRACT_TEMPLATE_PAR_FUN'"  />
				</td>
			</tr>
			<tr>
			<td align="right" width="120" height="28"><font color="red"></font>类型代码：</td>
				<td><input style="width:320px;"  name="contractTypeCode" class="text_style" id="updatecontractTypeCode"/></td>
			</tr>
			<tr>
				<td width="120" align="right">合同模板描述：</td>
				<td><input  class="easyui-textbox" style="width:320px;height:70px" data-options="required:false,multiline:true" name="templateDesc" id="updateTemplateDesc"></td>
			</tr>
		</table>
		
	</form>
</div>


<div id="dlg-buttons">
	    <a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="submitForm()" >保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#upload').dialog('close')">取消</a>
</div>

<div id="upload" class="easyui-dialog" style="width:500px;height:300px;padding:10px 20px;" closed="true"  buttons="#dlg-buttons">
		<form id="fileUploadForm" name="fileUploadForm" 
	action="${basePath}contractTemplateController/upload.action" enctype="multipart/form-data" 
	method="post"  target="hidden_frame">
	
		<p style="display: none;"><input  name="pid" id="pids"/> </p> 
		<table>
			<tr>
				<td width="120" align="right" height="28">重传模板名称：</td>
				<td><input  value="" name="templateName" class="text_style" id="templateNames" readonly="readonly" /></td>
			</tr>
			<tr>
				<td width="120" align="right" height="28">合同模板路径：</td>
				<td>
					<input class="text_style" type="text" id="txt1" name="txt1" />
					
					<input class="input_file" size="30" style="width:65px;" type="file" name="file1" id="file1" onchange="txt1.value=this.value"/>
				<td>
			</tr>
		</table>
		<p> </p> 
		<p></p>
	</form>
		
</div>


<div id="parm-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveParmData()">保存更改</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#tempLateParmDiv').dialog('close')">取消编辑</a>
	
</div>
<div id="tempLateParmDiv" class="easyui-dialog" style="width:88%;height:auto;top:50px;padding:0px 0px;" closed="true"  buttons="#parm-buttons">

	<div id="parmtoolbar" class="easyui-panel" style="border-bottom: 0;">
		<!-- 查询条件 -->
		<table>
			<tr>
				<td width="80" height="30" align="right">合同类别：</td>
				<td><input type="text" readonly="readonly" id="tempLateParmCatelog" class="textbox text_style" name = "tempLateParmCatelog"></td>
			
				<td width="80" height="30" align="right">合同类型：</td>
				<td><input type="text" readonly="readonly" id="tempLateParmType" class="textbox text_style" name = "tempLateParmType"></td>
			
				<td width="120" align="right">合同模板名称：</td>
				<td><input type="text" readonly="readonly" id="tempLateParmName" class="textbox text2_style" name = "tempLateParmName"> </td>
			</tr>
		</table>
	</div>

	<table id="parmGrid" class="easyui-datagrid" 
		style="height:auto;width: auto;">
	</table>
</div>
<iframe name='hidden_frame' id="hidden_frame" style="display:none"></iframe> 
<script type="text/javascript">
	function showUpdateDiv(){
		var current_tab = $('#grid').datagrid("getChecked");
		if(current_tab.length>1){
			$.messager.alert("操作提示", "一次只能选中一个模板!", "error");
			return;
		}
		
		if(current_tab.length==0){
			$.messager.alert("操作提示", "请选择要修改的合同模板!", "error");
			return;
		}
		$('#updateTemplates').form('reset');
		$('#updateTemplates').form('load',current_tab[0]);
		$('#updateT').dialog('open').dialog('setTitle', "修改模板");
		
		var pH=$('#updateT').parent().height()+12;
		$("#updateT").dialog("move",{top:($(document).scrollTop()?$(document).scrollTop()+pH*0.5:$(document).height()*0.5-pH*0.5 )});
	}
	
	function updateTempLate(){
		var current_tab = $('#grid').datagrid("getChecked");
		var pid = current_tab[0].pid;
		
		var templateName = $("#updateTemplateName").val();
		var templateCatelog = $("#updatetemplateCatelog").combobox('getValue');
		var templateType = $("#updateTemplateType").combobox('getValue');
		var contractNumberFun = $("#updatecontractNumberFun").val();	
		var contractTypeCode = $("#updatecontractTypeCode").val();	
		var templateDesc = $("#updateTemplateDesc").val();
		var applyType=$('#updateT input:radio[name="applyType"]:checked').val();		
		var cycleType=$('#updateT input:radio[name="cycleType"]:checked').val();
		if(applyType=="" || null==applyType){
			$.messager.alert("操作提示","请选择适用项目类型！","error"); 
			return false;
		}
		if(cycleType=="" || null==cycleType){
			$.messager.alert("操作提示","请选择项目循环类型！","error"); 
			return false;
		}
		
		var templateOwnerVal = -1;
		if($('#updateT input:radio[name="templateOwner"]:checked').val() !=undefined){
			templateOwnerVal=$('#updateT input:radio[name="templateOwner"]:checked').val();
		}
		
		var templateUseModeVal = -1;
		if($('#updateT input:radio[name="templateUseMode"]:checked').val() !=undefined){
			templateUseModeVal=$('#updateT input:radio[name="templateUseMode"]:checked').val();
		}
		var templateParFun = $("#updatetemplateParFun").combobox('getValue');
		
		if(templateName=="" || templateCatelog=="0" || templateType=="0"){
			$.messager.alert("操作提示", "信息录入不完整!", "error");
			return;
		}
		$.ajax({
			type: "POST",
		    url: "${basePath}contractTemplateController/updateTempLate.action",
		    data:{
		    	"templateName":templateName,
		    	"templateCatelog":templateCatelog,
		    	"templateType":templateType,        	
		    	"templateOwner":templateOwnerVal,
		    	"templateUseMode":templateUseModeVal,
		    	"contractNumberFun":contractNumberFun,        	
		    	"templateParFun":templateParFun,
		    	"contractTypeCode":contractTypeCode,
		    	"templateDesc":templateDesc,
		    	"applyType":applyType,
		    	"cycleType":cycleType,
		    	"pid":pid
		    },
		    dataType: "json",
		    success: function(data){
		    	 $("#grid").datagrid('reload');
		    	 $("#grid").datagrid('clearChecked');
		    }
		});
		$('#updateT').dialog('close'); 
	
	}
	
	function showAddDiv(){
		$('#addTemplates').form("reset"); 
		$('#addT').dialog('open').dialog('setTitle', "新增模板");
		var pH=$('#addT').parent().height()+12;
		$("#addT").dialog("move",{top:($(document).scrollTop()?$(document).scrollTop()+pH*0.5:$(document).height()*0.5-pH*0.5 )});
		$("#add_templateOwner").attr("checked","checked");
		$("#add_templateUseMode").attr("checked","checked");
		$("#add_applyType").attr("checked","checked");
		$("#add_cycleType").attr("checked","checked");
	}
		
		function subMitAddForm(){
		var addtemName = $("#addtemplateName").val();
		var addtemCatelog = $("#addtemplateCatelog").combobox('getValue');
		var addtemType = $("#addtemplateType").combobox('getValue');
		var templateOwnerVal=$('#addT input:radio[name="templateOwner"]:checked').val();
		var templateUseModeVal=$('#addT input:radio[name="templateUseMode"]:checked').val();		
		var applyType=$('#addT input:radio[name="applyType"]:checked').val();		
		var cycleType=$('#addT input:radio[name="cycleType"]:checked').val();		
		var addconNumberFun = $("#addcontractNumberFun").val();	
		var addtemParFun = $("#addtemplateParFun").combobox('getValue');
		var addcontractTypeCode = $("#addcontractTypeCode").val();
		var fileName = $("#file2").val();
		
		if(addtemName=="" || null==addtemName){
			 $.messager.alert("操作提示","请填写合同模板名称！","error"); 
			 return false;
		}
		if(addtemCatelog=="" || null==addtemCatelog || addtemCatelog=="-1"){
			$.messager.alert("操作提示","请选择合同类别！","error"); 
			return false;
		}
		if(addtemType=="" || null==addtemType || addtemType=="-1"){
			$.messager.alert("操作提示","请选择合同类型！","error"); 
			return false;
		}
		if(templateOwnerVal=="" || null==templateOwnerVal){
			$.messager.alert("操作提示","请选择合同适用对象！","error"); 
			return false;
		}
		if(applyType=="" || null==applyType){
			$.messager.alert("操作提示","请选择适用项目类型！","error"); 
			return false;
		}
		if(cycleType=="" || null==cycleType){
			$.messager.alert("操作提示","请选择项目循环类型！","error"); 
			return false;
		}
		if(fileName=="" || null==fileName){
			$.messager.alert("操作提示","请选择要上传的文件！","error"); 
			return false;
		}
		if(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)!=="doc" && fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)!=="docx"){
			$.messager.alert("操作提示","只能上传doc、docx文档！","error"); 
			return false;
		}
		
		$("#addTemplates").form('submit',{
			success:function(){
				 $('#addT').dialog('close');
				 $("#grid").datagrid('reload');
			} 
		});
	}
	
	function downloadfiter(value,row,index){
		return "<a href='${basePath}beforeLoanController/downloadData.action?path="+row.templateUrl+"&fileName="+row.templateName+"'><font color='blue'>点击下载</font></a>";
	}
	
	function uploadFilter(value,row,index){
		return '<a href="javascript:void(0)" onclick="showuploadDiv(1,2)"><font color="blue">重新上传</font></a>';
	}
	
	function submitForm(){
		var fileName = $("#file1").val();
		if(fileName=="" || null==fileName){
		 $.messager.alert("操作提示","请选择要上传的文件！","error"); 
		 return false;
		}
		if(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)!=="doc" && fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)!=="docx"){
		 $.messager.alert("操作提示","只能上传doc、docx文档！","error"); 
		 return false;
		}
		$("#fileUploadForm").form('submit',{
		 success:function(){
			 $('#upload').dialog('close');
			 $("#grid").datagrid("reload");
			 $("#grid").datagrid('clearChecked');
		 }
		})
	 
	}
	
	
	function showuploadDiv(s,data){
		var pid,t;
		if(data.pid!="undefined" && data.pid!=undefined){
			pid = data.pid;
			t = data.templateName; 
		}
		 
		if(s==1){
			$('#txt1').val('');
			$('#file1').val('');
			$('#upload').dialog('open').dialog('setTitle', "修改模板文件路径");
		}
		 
		$("#templateNames").val(t);
		$("#pids").val(pid);
	}
	
	function deleteTempLate(){
		var delArray = $('#grid').datagrid("getChecked");
		if(delArray.length<1){
			$.messager.alert("操作提示","请选择要删除的模板！","error"); 
			return;
		} 
		var pidArray = "";
		for(var i=0; i<delArray.length; i++){
			if(i!=delArray.length-1){
				pidArray =pidArray+delArray[i].pid+",";
			}else if(i==0){
				pidArray =pidArray+delArray[i].pid;
			}else if(i==delArray.length-1){
				pidArray =pidArray+delArray[i].pid;
			} 
		}
		
		if (confirm("是否确认删除?")) {
			$.ajax({
				type: "POST",
		        url: "${basePath}contractTemplateController/updateStatus.action",
		        data:{"pidArray":pidArray},
		        dataType: "text",
		        success: function(data){
		        	$("#grid").datagrid("reload");
		        	$("#grid").datagrid('clearChecked');
		    		$.messager.alert("操作提示",data,"success"); 
		        }
			});
		}
	} 
	
	//模糊查询模板
	function searchTempLateByLike(){
		var tempLateName = $("#searchTtemplateName").textbox('getValue');
		var tempLateType =  $('#searchTempLateType').combobox('getValue');
		var tempLateCatelog =  $('#searchTemplateCatelog').combobox('getValue');
		$("#grid").datagrid('clearChecked');
		if((tempLateName=="" || tempLateName==null) && tempLateType=="-1" && tempLateCatelog=="-1"){
			$("#grid").datagrid({
			    url: '${basePath}contractTemplateController/pageTempLateList.action',
			    method: 'POST',
			    rownumbers: true,
			    pagination: true,
			    toolbar: '#toolbar',
			    idField: 'pid',
			    fitColumns:true,
			    singleSelect: false,
				selectOnCheck:true,
				checkOnSelect: true,
				onClickRow:function(rowIndex,rowData){
					showuploadDiv(2,rowData);
				},
				onLoadSuccess:function(data){
					$('#grid').datagrid('clearChecked');
				},
			    columns: [[
			        { field: 'pid',checkbox:true,width:50},
			        { field: 'templateName', title: '合同模板名称', width: 70},
			        { field: 'templateCatelogText', title: '合同类别', width: 45, align:'center'},
			        { field: 'templateTypeText', title: '合同类型', width: 35, align:'center'},
			        { field: 'contractTypeCode', title: '类型代码', width: 35, align:'center'},
			        { field: 'applyType', title: '适用项目类型', width: 25, align:'center',formatter:applyTypeFilter},
			        { field: 'cycleType', title: '项目循环类型', width: 25, align:'center',formatter:cycleTypeFilter},
			        { field: 'templateOwner', title: '适用对象', width: 25, align:'center',formatter:templateOwnerFilter},
			        { field: 'templateUseMode', title: '使用形式', width: 25, align:'center',formatter:templateUseModeFilter},
			        { field: 'templateUrl', hidden:'true', title: '文件地址', width: 1 },				        
			        { field: 'ss', title: '下载模板', width: 20, align:'center', formatter:downloadfiter},
			        { field: 'yy', title: '上传模板', width: 20, align:'center', formatter:uploadFilter},
			        { field: 'updateDttm', title: '上传/修改时间', width: 35, align:'center',formatter:updateDttmFilter}
			    ]]
			}); 
			return;
		}
		$("#grid").datagrid({
		    url: '${basePath}contractTemplateController/pageTempLateListByLike.action',
		    method: 'POST',								
		    queryParams:{"templateName":tempLateName,"templateType":tempLateType,"templateCatelog":tempLateCatelog},
		    rownumbers: true,
		    pagination: true,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true,
		    singleSelect: false,
			selectOnCheck:true,
			checkOnSelect: true,
			onClickRow:function(rowIndex,rowData){
				showuploadDiv(2,rowData);
			},
			onLoadSuccess:function(data){
				$('#grid').datagrid('clearChecked');
			},
		    columns: [[
		        { field: 'pid',checkbox:true,width:50},
		        { field: 'templateName', title: '合同模板名称', width: 70},
		        { field: 'templateCatelogText', title: '合同类别', width: 45, align:'center'},
		        { field: 'templateTypeText', title: '合同类型', width: 35, align:'center'},
		        { field: 'contractTypeCode', title: '类型代码', width: 35, align:'center'},
		        { field: 'applyType', title: '适用项目类型', width: 25, align:'center',formatter:applyTypeFilter},
		        { field: 'cycleType', title: '项目循环类型', width: 25, align:'center',formatter:cycleTypeFilter},
		        { field: 'templateOwner', title: '适用对象', width: 25, align:'center',formatter:templateOwnerFilter},
		        { field: 'templateUseMode', title: '使用形式', width: 25, align:'center',formatter:templateUseModeFilter},
		        { field: 'templateUrl', hidden:'true', title: '文件地址', width: 1 },				        
		        { field: 'ss', title: '下载模板', width: 20, align:'center', formatter:downloadfiter},
		        { field: 'yy', title: '上传模板', width: 20, align:'center', formatter:uploadFilter},
		        { field: 'updateDttm', title: '上传/修改时间', width: 35, align:'center',formatter:updateDttmFilter}
		    ]]
		});
		
	 
	}
	
	function editTempLate(){
		var editArray = $("#grid").datagrid("getChecked");
		if(editArray.length<1){
			$.messager.alert("操作提示","请选择要编辑的模板！","error"); 
			return;
		} 
		
		if(editArray.length>1){
			$.messager.alert("操作提示","一次只能选中一个模板进行编辑！","error"); 
			return;
		} 
		var tempLateId = editArray[0].pid;
		var templateCatelog = editArray[0].templateCatelogText;
		var templateType = editArray[0].templateTypeText;
		var templateName = editArray[0].templateName;
		
		$("#tempLateParmCatelog").val(templateCatelog);
		$("#tempLateParmType").val(templateType);
		$("#tempLateParmName").val(templateName);
		
		
		$("#parmGrid").datagrid({
			    url: '${basePath}contractTemplateController/pageTempLateParmList.action',
			    method: 'POST',
			    rownumbers: true,
			    height:358,
			    queryParams:{'templateId':tempLateId},
			    pagination: true,
			    toolbar: '#parmtoolbar',
			    idField: 'pid',
			    fitColumns:true,
			    singleSelect: true,
			    singleOnCheck: true,
				selectOnCheck: false,
				checkOnSelect: false,
			    columns: [[
			        {field: 'pid',hidden:true,width:10},
			        {field: 'matchFlag', title: '匹配符号', width: 480,formatter:matchFlagFilter},
			        {field: 'matchName', title: '显示名称', width: 480,formatter:matchNameFilter},
			        {field: 'showIndex', title: '显示顺序', width: 120,formatter:showIndexFilter},
			        {field: 'outputType', title: '输出类型', width: 170,formatter:outputTypeFilter},
			        {field: 'isTable', title: '是否表格', width: 110,formatter:isTableFilter},
			        {field: 'valConvertFlag', title: '转换算法', width: 200,formatter:valConvertFlagFilter},
			        {field: 'fixedVal', title: '固定输出', width: 160,formatter:fixedValFilter},
			        {field: 'del', title: '操作', width: 80,formatter:delFilter}
			        ]]
			}); 
		 $('#tempLateParmDiv').dialog('open').dialog('setTitle', "编辑合同文本项");
	}
	
	//保存修改的数据
	function saveParmData(){
		var rows = $("#parmGrid").datagrid("getRows");
		 $("#tempLateParmDiv").find("input[name='matchName']").each(function (i){
			 rows[i].matchName=$(this).val();
		 });
		 
		 $("#tempLateParmDiv").find("input[name='showIndex']").each(function (i){
			 rows[i].showIndex=$(this).val();
		 });
		 
		 $("#tempLateParmDiv").find("select[name='outputType']").each(function (i){ 
			 rows[i].outputType=$(this).val();
		 });
		 
		 $("#tempLateParmDiv").find("select[name='isTable']").each(function (i){ 
			 rows[i].isTable=$(this).val();
		 });
		 
		 $("#tempLateParmDiv").find("select[name='valConvertFlag']").each(function (i){ 
			 rows[i].valConvertFlag=$(this).val();
		 });
		 	 
		 $("#tempLateParmDiv").find("input[name='fixedVal']").each(function (i){ 
			 rows[i].fixedVal=$(this).val();
		 });
		 
		$.ajax({
			type: "POST",
			contentType: "application/json",
		    url: "${basePath}contractTemplateController/updateTempParm.action",
		    data:JSON.stringify(rows),
		    dataType: "text",
		    success: function(data){
					if(data=="true" || data==true){
						$.messager.alert('操作提示', '保存信息成功!', 'success');
						$("#grid").datagrid('reload');
						$("#grid").datagrid('clearChecked');
					}else{
						$.messager.alert('操作提示', '保存信息失败!', 'error');
					}
		    }
		});
	}
	function find(pageNumber, pageSize,tempLateId) {
		$("#parmGrid").datagrid('getPager').pagination({pageSize : pageSize, pageNumber : pageNumber});//重置
		$("#parmGrid").datagrid("loading"); //加屏蔽
		$.ajax({
		    type : "POST",
		    dataType : "json",
		    url :"${basePath}contractTemplateController/pageTempLateParmList.action",
		    data : {
		        'page' : pageNumber,
		        'rows' : pageSize,
		        'templateId':tempLateId
		    },
		    success : function(data) {
		        //$("#parmGrid").datagrid('loadData',pageData(data.rows,data.total));//这里的pageData是我自己创建的一个对象，用来封装获取的总条数，和数据，data.rows是我在控制器里面添加的一个map集合的键的名称
		        //var total =data.total;
		        $("#parmGrid").datagrid("loaded"); //移除屏蔽
		    },
		    error : function(err) {
		        $.messager.alert('操作提示', '获取信息失败...请联系管理员!', 'error');
		        $("#parmGrid").datagrid("loaded"); //移除屏蔽
		    }
		});
	}
	
	function pageData(list,total){
		var obj=new Object(); 
		obj.total=total; 
		obj.rows=list; 
		return obj; 
	}
	
	//所有的字段过滤方法
	function matchFlagFilter(value,row,index){
		return '<input class="easyui-textbox text2_style" readonly="readonly" name="machFlag" id="machFlag" value='+value+' />';
	}
	
	function cycleTypeFilter(value,row){
		if(value==undefined || value=="undefined"){
			return "";
		}else if(value==1){
			return "最高额授信";
		}else if(value==2){
			return "最高额贷款";
		}else if(value==3){
			return "都适用";
		}
	}
	
	function applyTypeFilter(value,row){
		if(value==undefined || value=="undefined"){
			return "";
		}else if(value==1){
			return "授信";
		}else if(value==2){
			return "贷款";
		}else if(value==3){
			return "都适用";
		}
	}
	
	function templateOwnerFilter(value,row){
		if(value==undefined || value=="undefined"){
			return "";
		}else if(value==1){
			return "个人";
		}else if(value==2){
			return "法人";
		}else if(value==3){
			return "都适用";
		}
	}
	
	function templateUseModeFilter(value,row){
		if(value==undefined || value=="undefined"){
			return "";
		}else if(value==1){
			return "最高额";
		}else if(value==2){
			return "单笔";
		}else if(value==0){
			return "都适用";
		}
	}
	
	function matchNameFilter(value,row,index){
		if(value==undefined || value=="undefined"){
			value="";
		}
		return '<input class="easyui-textbox text2_style" name="matchName" id="matchName" value="'+value+'" />';
	}
	
	function showIndexFilter(value,row,index){
		if(value==undefined || value=="undefined"){
			value="";
		}
		return '<input class="easyui-textbox text3_style" name="showIndex" id="showIndex" value="'+value+'" />';
	}
	
	
	function isTableFilter(value,row,index){
		var dom = '';
		if(value==undefined || value=="undefined"){
			value="";
		}
		if(value==1){
			dom='<select id="isTable" class="easyui-combobox" name="isTable">'
				+'<option value="1" selected=selected>是</option>'
				+'<option value="0">否</option>'
			    +'</select>';
		}else{
			dom='<select id="isTable" class="easyui-combobox" name="isTable">'
				+'<option value="1">是</option>'
				+'<option value="0" selected=selected>否</option>'
			    +'</select>';
		}
 
		return dom;
	}
	
	function outputTypeFilter(value,row,index){
		var dom = '';
		if(value==1){
			dom='<select id="outputType" class="easyui-combobox" name="outputType" onchange="changePutType(this);">'
				+'<option value="0">--请选择--</option>'
				+'<option value="1" selected=selected>手动</option>'
				+'<option value="2">固定值</option>'
				+'<option value="3">自动</option>'
			    +'</select>';
		}else if(value==2){
			dom='<select id="outputType" class="easyui-combobox" name="outputType" onchange="changePutType(this);">'
				+'<option value="0">--请选择--</option>'
				+'<option value="1">手动</option>'
				+'<option value="2" selected=selected>固定值</option>'
				+'<option value="3">自动</option>'
			    +'</select>';
		}else if(value==3){
			dom='<select id="outputType" class="easyui-combobox" name="outputType" onchange="changePutType(this);">'
				+'<option value="0">--请选择--</option>'
				+'<option value="1">手动</option>'
				+'<option value="2">固定值</option>'
				+'<option value="3" selected=selected>自动</option>'
			    +'</select>';
		}else{
			dom='<select id="outputType" class="easyui-combobox" name="outputType" onchange="changePutType(this);">'
				+'<option value="0" selected=selected>--请选择--</option>'
				+'<option value="1">手动</option>'
				+'<option value="2">固定值</option>'
				+'<option value="3">自动</option>'
			    +'</select>';
		} 
		return dom;
	}
	
	function changePutType(change){
		var value = $(change).val();
		if(value==1){
			$(change).parent().parent().parent("tr").find("#fixedVal").attr("readonly","readonly");
		}else if(value==2){
			$(change).parent().parent().parent("tr").find("#fixedVal").removeAttr("readonly");
		}else if(value==3){
			$(change).parent().parent().parent("tr").find("#fixedVal").attr("readonly","readonly");
		}else{
			$(change).parent().parent().parent("tr").find("#fixedVal").attr("readonly","readonly");
		}	
	}
	
	function valConvertFlagFilter(value,row,index){
		var dom='';
		if(value==1){
			dom='<select id="valConvertFlag" class="easyui-combobox" name="valConvertFlag">'
				+'<option value="0">--请选择--</option>'
				+'<option value="1" selected=selected>日期(分隔符)</option>'
				+'<option value="2">日期(中文)</option>'
				+'<option value="3">金额(大写)</option>'
				+'<option value="4">数值(大写)</option>'
				+'<option value="5">千分位</option>'
			    +'</select>';
		}else if(value==2){
			dom='<select id="valConvertFlag" class="easyui-combobox" name="valConvertFlag">'
				+'<option value="0">--请选择--</option>'
				+'<option value="1">日期(分隔符)</option>'
				+'<option value="2" selected=selected>日期(中文)</option>'
				+'<option value="3">金额(大写)</option>'
				+'<option value="4">数值(大写)</option>'
				+'<option value="5">千分位</option>'
			    +'</select>';
		}else if(value==3){
			dom='<select id="valConvertFlag" class="easyui-combobox" name="valConvertFlag">'
				+'<option value="0">--请选择--</option>'
				+'<option value="1">日期(分隔符)</option>'
				+'<option value="2">日期(中文)</option>'
				+'<option value="3" selected=selected>金额(大写)</option>'
				+'<option value="4">数值(大写)</option>'
				+'<option value="5">千分位</option>'
			    +'</select>';
		}else if(value==4){
			dom='<select id="valConvertFlag" class="easyui-combobox" name="valConvertFlag">'
				+'<option value="0">--请选择--</option>'
				+'<option value="1">日期(分隔符)</option>'
				+'<option value="2">日期(中文)</option>'
				+'<option value="3">金额(大写)</option>'
				+'<option value="4" selected=selected>数值(大写)</option>'
				+'<option value="5">千分位</option>'
			    +'</select>';
		}else if(value==5){
			dom='<select id="valConvertFlag" class="easyui-combobox" name="valConvertFlag">'
				+'<option value="0">--请选择--</option>'
				+'<option value="1">日期(分隔符)</option>'
				+'<option value="2">日期(中文)</option>'
				+'<option value="3">金额(大写)</option>'
				+'<option value="4">数值(大写)</option>'
				+'<option value="5" selected=selected>千分位</option>'
			    +'</select>';
		}else{
			dom='<select id="valConvertFlag" class="easyui-combobox" name="valConvertFlag">'
				+'<option value="0" selected=selected>--请选择--</option>'
				+'<option value="1">日期(分隔符)</option>'
				+'<option value="2">日期(中文)</option>'
				+'<option value="3">金额(大写)</option>'
				+'<option value="4">数值(大写)</option>'
				+'<option value="5">千分位</option>'
			    +'</select>';
		}
		return dom;
	}
	
	
	function updateDttmFilter(value,row,index){
		if(value!="" && null!=value && undefined!=value && 'undefined'!=value){
			return  value.split(".")[0];
		}
	}
	
	function fixedValFilter(value,row,index){
		if(value==undefined || value=="undefined"){
			value="";
		}
		var dom='';
		if(row.outputType==2){
			dom='<input type="text" class="easyui-textbox text4_style" name="fixedVal" id="fixedVal" value='+value+'>';
		}else{
			dom='<input readonly="readonly" class="easyui-textbox text4_style" type="text" name="fixedVal" id="fixedVal" value="">';
		}
		return dom;
	}
	
	function doclear(){
		$("#searchTtemplateName").textbox('setValue','');
		$('#searchTempLateType').combobox('select','-1');
		$('#searchTemplateCatelog').combobox('select','-1');
	}
	
	function delFilter(value,row,index){
		return "<a href='javascript:void(0)' onclick='delMatch("+row.pid+")'><font color='blue'>删除</font></a>"
	}
	
	function delMatch(pid){
		if(confirm("确定删除？")){
			$.ajax({
				url:"${basePath}contractTemplateController/delTempParm.action",
				type:"post",
				data:{"pid":pid},
				dataType:"json",
				success:function(response){
					if(response==true){
						$.messager.alert('操作提示', '删除信息成功!', 'success');
						$("#parmGrid").datagrid("reload");
					}else{
						$.messager.alert('操作提示', '删除信息失败!', 'error');
						$("#parmGrid").datagrid("reload");
					}
				}
			})
		}
	}
</script>
<script type="text/javascript" src="${basePath}upload/javascript/upload.js" charset="utf-8" />