<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/beforeloan/loanApprovalPaper.js"></script>
<style>
.underlinecss{
text-decoration: underline;
}
</style>

<script type="text/javascript">
	var BASE_PATH = "${basePath}";
	var nProjectId = newProjectId;
	function submitUploadLoanApprovalPaperFileForm(){
		//var projectId = $("#projectId").val();
		$("#loanAP_projectId").val(newProjectId);
		
		$('#uploadLoanApprovalPaperFileForm').form('submit', {
			success : function(data) {
				//alert(data);
				if(data=="saveSucc"){
					$.messager.alert('操作','保存成功!','info');
				}else{
					$.messager.alert('操作','保存失败!','error');
				}
				$("#loanApprovalPaperFile").textbox('setValue','');
				loadTable();
			},
			failure:function(){
					$.messager.alert('操作','保存失败!','error');
			}
			//fail:
		});
	}
	
	function reload(){
		$("#loadTable").datagrid("reload");
	}
	
	$(document).ready(function() {
		nProjectId = newProjectId;
		$("#loanAP_projectId").val(newProjectId);
		
		$("#loanApprovalPaperFile").filebox({ 
			 buttonText: '选择文件', 
			 buttonAlign: 'right' ,
			 missingMessage:'选择一个文件'
		});
		loadTable();
		
	});

	/**
	function refreshUploadLoanApprovalPaperFileForm(){
			$.ajax({
					type: "POST",
			        url:BASE_PATH+'beforeLoanController/obtainLoanApprovalPaperFileInfo.action?projectId='+projectId, 
			        data:{projectFileArchivePid:projectFileArchivePid},
			        dataType: "text",
			        success: function(fileLoadData){
			        	fileLoadDataJSON = $.parseJSON(fileLoadData);
			        	$('#loadTable').datagrid('loadData', fileLoadDataJSON);
			        }
				});
			
				//$("#opFileTable").datagrid("reload");
		}
	**/
	
</script>
<div class="easyui-panel loanApprovalPaper" title="放款审批单" data-options="collapsible:true" style="width:auto">
	<form id="uploadLoanApprovalPaperFileForm" method="post" action="${basePath}beforeLoanController/uploadLoanApprovalPaperFile.action" enctype="multipart/form-data" >
		<input type="hidden" id="loanAP_projectId" name="loanAP_projectId" value="">
		<table>
			<tr>
				<td height="28">上传放款审批资料：</td>
				<td><input class="easyui-filebox"  name="loanApprovalPaperFile" id="loanApprovalPaperFile" data-options="prompt:'选择一个文件...'" style="width:400px"></td>
				<td>
					<a href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton" onclick="submitUploadLoanApprovalPaperFileForm()">保存</a>
					<%--<input align="right" type="submit" class="cus_save_btn" name="submit" value="提交" onclick="submitUploadLoanApprovalPaperFileForm()">--%>
					<%--<input align="right" type="reset" class="cus_save_btn" name="reload" value="重置" onclick="resetForm()">--%>
					<%--<a href="javascript:void(0)" class="easyui-linkbutton cus_save_btn" onclick="refreshUploadLoanApprovalPaperFileForm()">刷新</a> --%>
					<%--<input align="right" type="reset" class="cus_save_btn" name="refresh" value="刷新" onclick="refreshUploadLoanApprovalPaperFileForm()">--%>
				</td>
			</tr>
		</table>
	</form>
	<div style="text-align:center;padding:5px">
		<table id="loadTable"></table>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	$('#loadTable').datagrid({
		onLoadSuccess:function(){
			if("task_ConfirmSingleLoanApproval"==workflowTaskDefKey){
			}else{
				$('.loanApprovalPaper .easyui-combobox').attr('disabled','disabled');
				$('.loanApprovalPaper input[type="checkbox"]').attr('disabled','disabled');
				$('.loanApprovalPaper .easyui-textbox').attr('disabled','disabled');
				$('.loanApprovalPaper .easyui-linkbutton ,.loanApprovalPaper a:not(.download)').removeAttr('onclick');
				$('.loanApprovalPaper a:not(.download) font').attr('color','gray');
				$('.loanApprovalPaper a:not(.download) span').css('color','gray');
				$('.loanApprovalPaper .easyui-linkbutton ,.loanApprovalPaper  input,.loanApprovalPaper  textarea').attr('disabled','disabled');
				$('.loanApprovalPaper .easyui-linkbutton ,.loanApprovalPaper  input,.loanApprovalPaper  textarea').attr('readOnly','readOnly');
				$('.loanApprovalPaper a:not(.download),.loanApprovalPaper .easyui-linkbutton ,.loanApprovalPaper  input,.loanApprovalPaper  textarea').addClass('l-btn-disabled');
			}
		}
	})
	
})
</script>