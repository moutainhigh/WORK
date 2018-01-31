<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>
<script type="text/javascript" src="${basePath}upload/javascript/upload.js" charset="utf-8"></script>
<div>
	<form id="addOrupdate2" name="addOrupdate2" action="${basePath}afterLoanController/addOrupdate.action?fileType=${fileType}" method="post" enctype="multipart/form-data">
			                 <input type="hidden" name="dataId" id="${dataId}" >				  
							 <input type="hidden" name="refId" id="refId" value="${refId}" >
							 <input type="hidden" name="projectId" id="projectId" value="${projectId}">
							 <input type="hidden" name="fileId" id="fileId" >
						     <input type="hidden" name="dataProjectId" id="${projectId}">
	                         <input type="hidden" name="pro_Id" id="pro_Id" value="${projectId}">
				<table>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>文件类型：</td>
						<td>
							<input class="easyui-combobox" name="dataFileProperty" id="dataFileProperty2" style="width:200px"
							data-options="url:'${basePath}beforeLoanController/searchDataType.action?projectId=${projectId}&fileType=${fileType}',
										  method:'get',
										  valueField:'pid',		            
										  textField:'lookupDesc'" required="true"> 
						</td>
					</tr>
					
						<tr>
							<td width="120" align="right">上传说明：</td>
							<td><input  class="easyui-textbox" style="width:240px;height:100px" data-options="required:false,multiline:true" name="remark" id="datafileDesc"></td>
						</tr>
						<c:if test="${fileId==0}">
						<tr>
							<td align="right" width="120" height="28"><font color="red">*</font>文件路径：</td>
							<td>
									<input class="easyui-filebox" data-options="buttonText:'选择文件'" id="file3" name="file2" style="width:240px;" required="true"/>
							</td>
						</tr>
					</c:if>
				</table>
		</div>
		<div style="text-align: center;">
			<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addOrupdateData2()" >保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_data2').dialog('close')">取消</a>
		</div>
	</form>	
	<script type="text/javascript" src="${basePath}upload/javascript/upload.js" charset="utf-8"></script>
	<script type="text/javascript">
	function addOrupdateData2(){
		
		if("${fileId}" ==0)
		{
			var file = $('#file3').filebox('getValue');
			var proId =  $('#projectId').val();// $('#dataProjectId2').val();
			//var fileProperty = $('#dataFileProperty2').combobox('getValue');
			 
			if(file=="" || null==file){
				$.messager.alert("操作提示","请选择文件资料","error"); 
				return false;
			}
			if(proId=="" || null==proId){
				$.messager.alert("操作提示","请重新选择项目","error"); 
				return false;
			}
		}	
 		
		 
		$('#addOrupdate2').form('submit',{
			success:function(data){
				var datas = strToJson(data);
				if(datas.header.success)
				{
					$('#dialog_data2').dialog("close");
					$('#grid_data').datagrid("reload");
					$('#grid_data').datagrid("clearChecked");
				}
				else
				{
				  $.messager.alert("操作提示",datas.header.msg,"error");
				}	
			}
		});
	}
	
	function strToJson(str){
		return JSON.parse(str);
	} 
	
	$(function(){
		
		setTimeout(function(){
			
			// 修改
			if("${fileId}" !=0)
			{
				$("#dataFileProperty2").combobox('disable');
			}	
			// 新增
			else
			{
				$('.easyui-filebox').filebox({
					buttonText: '选择文件'
				});
			}
			
			if("" !=selectDesc)
			{
				$("#datafileDesc").textbox('setValue',selectDesc);
			}	
			
		},100);
		
	});
	</script> 
	
	
	
	
	