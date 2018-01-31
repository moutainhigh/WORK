<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/beforeloan/projectFileArchive.js"></script>
<style>
.underlinecss{
text-decoration: underline;
}
</style>

<script type="text/javascript">
	var BASE_PATH = "${basePath}";
	var isArchiveEdit = false;
	var isArchiveFileEdit = false;
	var archiveId = 0;
	var nProjectId;
	var proStatus="${proStatus}";
	
	
	function submitProjectFileArchiveInfoForm(){
		$("#archiveProjectId").val(nProjectId);
		
		if(!isArchiveEdit){
			var url="${basePath}beforeLoanController/saveBizProjectArchive.action";
		}else{
			var url="${basePath}beforeLoanController/updateBizProjectArchive.action";
		}
		
	  $("#projectFileArchiveInfoForm").form('submit',{
		  	url:url,
		  	 onSubmit : function() {
		  		 return $(this).form('validate');
		  	 },			
             success:function(data){
            	 if(isArchiveEdit){
            		 if(data=="editSucc"){
            			 $.messager.alert('编辑', '编辑项目资料归档数据成功!', 'info');
            			 $("#buttonDIV").hide();
            			 var archiveId_1 = $("#projectArchiveId").val();
            			 $('#allArchiveFileInfoTable').datagrid("reload",BASE_PATH+'beforeLoanController/obtainBizProjectArchiveFile.action?archiveId_textbox='+archiveId_1);
   	            	  	 $("#allArchiveFileInfo").show();
   	            	 	 $("#lastStepClose").show();
   	            	 	 projectFileArchive();
                     }else{
    					 $.messager.alert('编辑', '编辑项目资料归档数据失败!', 'error');
                     }
            	 }else{
            		 var dataJSON = $.parseJSON(data);
	            	 var saveState = dataJSON.saveSucc;
            		 if(saveState=="saveSucc"){
	            		  var _archiveId = dataJSON.archiveId;
	            		  $("#projectArchiveId").val(_archiveId);
			              $("#fileArchiveId").val(_archiveId);
			              archiveId = _archiveId;
		            	  $("#buttonDIV").hide();
		            	  $("#allArchiveFileInfo").show();
		            	  $("#lastStepClose").show();
		           	  	  $.messager.alert('提示', '项目资料归档数据保存成功!', 'info');
		           	  	  projectArchiveFileInfo();
		           	  	  projectFileArchive();
            		 }else{
            			 $.messager.alert('提示', '项目资料归档数据保存失败!', 'error');
            		 }
            	 }
             }
         });
	}
	
	//上传利率变更资料上传
	function uploadArchiveFileInfo(){
		$("#fileArchiveId").val(archiveId);
		/**
		var fileBusType = $("#fileBusType_init").textbox('getText');
		$("#fileBusType").textbox('setValue',fileBusType); 
		**/
		if(!isArchiveFileEdit){
			var url="${basePath}beforeLoanController/saveBizProjectArchiveFile.action";
		}else{
			var url="${basePath}beforeLoanController/updateBizProjectArchiveFile.action";
		}
		
		$("#archiveFileInfoForm").form('submit',{
			url:url,
            success:function(data){
            	if(isArchiveFileEdit){
            		 if(data=="editSucc"){
            			$.messager.alert('提示', '编辑归档文件资料数据成功!', 'info');
            			$('#allArchiveFileInfoTable').datagrid("reload",BASE_PATH+'beforeLoanController/obtainBizProjectArchiveFile.action?archiveId_textbox='+archiveId);
                   	  	$("#archiveFileInfoWindow").window('close');
                     }else{
    					 $.messager.alert('提示', '编辑归档文件资料数据失败!', 'error');
                     }
            	}else{
            		if(data=="saveSucc"){
                  	  	$('#allArchiveFileInfoTable').datagrid("reload");
                  	  	$("#archiveFileInfoWindow").window('close');
              	  	  	$.messager.alert('提示', '归档文件资料数据成功!', 'info');
              	  	  	$('#allArchiveFileInfoTable').datagrid("reload",BASE_PATH+'beforeLoanController/obtainBizProjectArchiveFile.action?archiveId_textbox='+archiveId);
                    }else{
                  	  	$.messager.alert('提示', '归档文件资料数据失败!', 'error');
                    }
            	}
            }
        });
	}

	
	
	function closeProjectFileArchiveInfoForm(){
		$('#projectFileArchive').datagrid("reload");
		$("#projectFileArchiveInfoWindow").window('close');
	}
	
	function closeArchiveFileInfoForm(){
		//$("*[name=archiveFileInfoWindow]")[0].window('close');
		$("#archiveFileInfoWindow").window('close');
	}
	
	$(document).ready(function() {
		nProjectId = projectId;
		$("#allArchiveFileInfo").hide();
		$("#lastStepClose").hide();
		
		$("#projectArchiveFile").filebox({ 
			 buttonText: '选择文件', 
			 buttonAlign: 'right' ,
			 missingMessage:'选择一个文件'
		});
		var height = document.body.clientHeight;
		$('#projectFileArchive').datagrid({ 
			height:(height)
		});
		projectFileArchive();
	});
	
	function Iamfilter(row){ 
		var ss = row.lookupVal;
		if(ss==Iam){
			row.selected = true;
			$('#archiveCatelog_hi').val(row.pid);
		}
		return row.lookupDesc;
	} 
	function Iam2filter(row){ 
		var ss = row.lookupVal;
		if(ss==Iam){
			row.selected = true;
			$('#fileBusType_hi').val(row.pid);
		}
		return row.lookupDesc;
	}
	
	function setValueDo(newValue,oldValue){
		var value = newValue.replace(/.*(\/|\\)/, "");
		$('#projectArchiveFileName').textbox('setValue',value);
	}
</script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/beforeloan/projectFileArchiveInfo.js"></script>
<div id="projectFileArchiveDiv">
<div class="easyui-panel archive_dis" title="项目资料归档" style="width:auto" data-options="collapsible:true">
	<table id="projectFileArchive" border="0"></table>
</div>

<%--详细信息，子窗口--%>

<%--下面DIV是项目归档资料上传查看 --%>
<div align="center" id="projectFileArchiveInfoWindow" class="easyui-window" title="项目归档资料上传查看" data-options="modal:true,closed:true,tools:'#tt',maximizable:false,minimizable:false,collapsible:true" width="800px" height="380px">
	<form id="projectFileArchiveInfoForm" name="projectFileArchiveInfoForm" method="post">
		<input type="hidden" id=archiveProjectId name="archiveProjectId" value="0">
		<input type="hidden" id=projectArchiveId name="projectArchiveId" value="0">
		<table align="center" cellpadding="5" style="height: 100%;width:100%;" >
			<tr>
				<td class="label_right"><span class="cus_red">*</span>归档类型：</td>
				<td  colspan="3">
					 <input class="easyui-combobox" id="archiveCatelog" data-options="required:true,valueField:'pid',formatter:Iamfilter,textField:'lookupDesc',url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=ARCHIVE_CATELOG',disabled:true">
					 <input type="hidden" name="archiveCatelog" id="archiveCatelog_hi">
				</td>
			</tr>
			<tr>
				<td class="label_right"><span class="cus_red">*</span>归档材料名称：</td>
				<td  colspan="3"><input class="easyui-textbox" id="archiveFileName" name="archiveFileName" data-options="required:true"></input></td>
			</tr>
			<tr>
				<td class="label_right"><span class="cus_red">*</span>线下资料位置：</td>
				<td  colspan="3"><input class="easyui-textbox" id="archiveLocation" name="archiveLocation" data-options="required:true"></input></td>
			</tr>
			<tr>
				<td class="label_right"><span class="cus_red">*</span>线下份数：</td>
				<td  colspan="3"><input class="easyui-numberbox" id="offlineCnt" name="offlineCnt" data-options="required:true"></input></td>
			</tr>
			<tr>
				<td class="label_right"><span class="cus_red">*</span>线上份数：</td>
				<td  colspan="3"><input class="easyui-numberbox" id="onlineCnt" name="onlineCnt" data-options="required:true"></input></td>
			</tr>
			<tr>
				<td class="label_right"><span class="cus_red">*</span>是否归档：</td>
				<td  colspan="3">
					<input type="radio" name="isArchive" value="1">是</input>
					<input type="radio" name="isArchive" value="0" checked="checked">否</input>
				</td>
			</tr>
			<tr>
				<td class="label_right"><span class="cus_red">*</span>备注：</td>
				<td colspan="3"><input class="easyui-textbox" id="remark" name="remark" data-options="multiline:true,required:true" style="height:80px;width: 400px"/></td>
			</tr>
			<tr align="center" >
				<td colspan="4" height="30" align="center">
					<div id="buttonDIV">
				   	<a href="javascript:void(0)" id="submitProjectFileArchiveInfoForm" iconCls="icon-save" class="easyui-linkbutton" onclick="submitProjectFileArchiveInfoForm()">保存</a>
					<a href="javascript:void(0)" id="closeProjectFileArchiveInfoForm" iconcls="icon-cancel" class="easyui-linkbutton" onclick="closeProjectFileArchiveInfoForm()">取消</a>
					</div>
				</td>
			</tr>
			<tr id="allArchiveFileInfo">
				<td colspan="4"  align="left">
					<table id="allArchiveFileInfoTable" title="归档资料"></table>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<div id="lastStepClose">
					<a href="javascript:void(0)" id="lastStepCloseButton" iconcls="icon-cancel" class="easyui-linkbutton" onclick="closeProjectFileArchiveInfoForm()">关闭</a>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>

<%--下面DIV是利率变更资料上传弹出框 --%>
<div align="center" id="archiveFileInfoWindow" class="easyui-window" title="资料上传" data-options="modal:true,closed:true,tools:'#tt',maximizable:false,minimizable:false,collapsible:false" width="700px" height="400px">
	<form id="archiveFileInfoForm" name="archiveFileInfoForm" method="post" action="" enctype="multipart/form-data" target="hidden_frame">
		<input type="hidden" id="isArchiveFileInfoEdit" name="isArchiveFileInfoEdit">
		<input type="hidden" id="archiveFileId" name="archiveFileId">
		<input type="hidden" id=fileArchiveId name="fileArchiveId" value="0">
		<table align="center" cellpadding="5" style="height: auto;">
			<tr>
				<td height="28" align="right">选择文件类型：</td>
				<td  colspan="3">
				<%--
					<div style="display: none;">
						<input class="easyui-textbox" id="fileBusType" name="fileBusType"></input>
					</div>
				 --%>
					 <input class="easyui-combobox" id="fileBusType" data-options="valueField:'pid',formatter:Iam2filter,textField:'lookupDesc',value:'-1',url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=FILE_BUS_TYPE',disabled:true">
					 <input type="hidden" name="fileBusType" id="fileBusType_hi">
				</td>
			</tr>
			<tr>
				<td height="28" align="right">文件选择：</td>
				<td  colspan="3">
					<input type="hidden" id="hasProjectArchiveFile" name="hasProjectArchiveFile" value="">
					<input class="easyui-filebox"  name="projectArchiveFile" id="projectArchiveFile" data-options="prompt:'选择一个文件...',onChange:setValueDo,required:true,missingMessage:'选择一个文件'"  style="width:400px">
				</td>
			</tr>
			<tr>
				<td align="right">文件名称：</td>
				<td colspan="4"><input class="easyui-textbox" id="projectArchiveFileName" name="projectArchiveFileName" style="width:400px"></input></td>
			</tr>
			<tr>
				<td align="right">文件备注：</td>
				<td colspan="4"><input class="easyui-textbox" id="fileRemark" name="fileRemark" data-options="multiline:true" style="width:400px;height: 200px;"></input></td>
			</tr>
			<tr align="center">
				<td colspan="4" align="center">
				   	<a href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton" onclick="uploadArchiveFileInfo()">上传</a>
					<a href="javascript:void(0)" iconcls="icon-cancel" class="easyui-linkbutton" onclick="closeArchiveFileInfoForm()">取消</a>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
