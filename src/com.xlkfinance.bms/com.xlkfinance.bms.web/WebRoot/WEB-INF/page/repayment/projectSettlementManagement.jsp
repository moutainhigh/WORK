<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
#tabs .tabs-header .tabs{
  height: 26px;
  position: fixed;
  top:0px;
  background: #fff;
  z-index: 9999999;
  padding-top;2px;
}
.{padding-top:7px;}
</style>
<script type="text/javascript">
	var pid = ${projectId};
	$(function(){
		$.ajax({
			url:"${basePath }beforeLoanController/getLoanProjectByProjectId.action",
			type:"post",
			data:{projectId:pid},
			dataType:'json',
			success:function(response){
				var html = "<a href='javascript:void(0)' onclick='formatterProjectName.disposeClick("+response.pid+",\""+response.projectNumber+"\")'><font color='blue'>"+response.projectName+"</font></a>"
				$('#pro_name').html(html);
				$('#pro_number').text(response.projectNumber);
			}
		}) 
		$.ajax({
			url:"${basePath }rePaymentController/getLoanSettleOne.action",
			type:"post",
			data:{projectId:pid},
			dataType:'json',
			success:function(data){
				if(data.completeDesc!=""){
					$('#completeDesc').textbox('setValue',data.completeDesc);
				}
			}
		})
	})
	/*function lookupRecord(){
	
		//${loanId}
		//查看对账信息
		$('<div id="listFinanceTotaldetailDiv"/>').dialog({
			href : '${basePath}financeController/getLoanReconciliationDtl.action?financeReceivablesId='+100021,
			width : 800,
			height : 600,
			modal : true,
			title : '查看对账信息',
			onLoad : function() {
			}
		});
		
	}*/
	
	
	function makeFile(){
		$.ajax({
			url:BASE_PATH+'templateFileController/checkFileUrl.action',
			data:{templateName:'JQWJPER'},
			dataType:'json',
			success:function(data){
				if(data==1){	
					$.ajax({
						url:BASE_PATH+'templateFileController/checkFileUrl.action',
						data:{templateName:'JQWJCOM'},
						dataType:'json',
						success:function(data){
							if(data==1){
								window.location.href='${basePath}rePaymentController/makeProjectCompleteFile.action?projectId='+pid;
							}else{
								alert('放款审批单模板不存在，请上传模板后重试');
							}
						}
					});
				}else{
					alert('放款审批单模板不存在，请上传模板后重试');
				}
			}
		})
	}
	
	//结算保存
	function save(){
		var rows = $('#grid').datagrid('getData');
		for(var i=0;i<rows.total;i++){
			if(rows.rows[i].Legalconfirmation==2){
				$.messager.alert('操作提示', '文件法务确认需要全部确认，才能保存!', 'info');
				return false;
			}
		}
		$('#setForm').form('submit',{
			success:function(data){
				if(data==1){
					$.messager.alert('操作提示', '结算保存成功!', 'success');
					var myObj = parent.$('#layout_center_tabs').tabs('getTab','结清客户查询');
					if(myObj){
					//获取iframe的URL
					var url = myObj[0].innerHTML;
					//打开数据
					parent.layout_center_addTabFun({
						title : '结清客户查询', //tab的名称
						closable : true, //是否显示关闭按钮
						content : url,//加载链接
						falg:true
					});
					}
					parent.$('#layout_center_tabs').tabs('close','项目结清办理');
				}else{
					$.messager.alert('操作提示', '结算保存失败!', 'error');
				}
				
			}
		})
	}
	//取消
	function cancel(){
		parent.removePanel();
	}
	//打开上传窗口
	function upload(){
		$('#uploadForm').form('reset');
		$('#uploadDiv').dialog('open').dialog('setTitle','上传结清文件');
	}
	//上传文件
	function saveFile(){
		$('#uploadForm').form('submit',{
			success:function(response){
				var str = JSON.parse(response);
				if(str.uploadStatus==2){
					$.messager.alert('操作提示', "文件上传成功", 'success');
				}else{
					$.messager.alert('操作提示', "文件上传失败", 'error');
				}
				$('#uploadDiv').dialog('close');
				$('#grid').datagrid('reload');
			}
		})
	}
	//文件类型
	function setFilter(value,row,index){
		return "结清文件";
	}
	//法务确认
	function LegalconfirmationFilter(value,row,index){
		if(value==1){
			return '是'
		}else{
			return '否'
		}
	}
	//操作
	function czFilter(value,row,index){
		var downloadDom = "<a href='${basePath}beforeLoanController/downloadData.action?path="+row.fileUrl+"&fileName="+row.fileName+"'><font color='blue'>下载</font></a> | ";
		var reUploadDom = "<a href='javascript:void(0)' onclick='reUpload("+row.pId+",\""+row.fileName+"\")'><font color='blue'>重新上传</font> | </a>";
		var EditDom = "<a href='javascript:void(0)' onclick='editFile("+row.pId+",\""+row.fileDesc+"\","+row.Legalconfirmation+")'><font color='blue'>编辑</font></a> | ";
		var DeleteDom = "<a href='javascript:void(0)' onclick='deleteFile("+row.pid+")'><font color='blue'>删除</font></a>";
		return downloadDom+reUploadDom+EditDom+DeleteDom;
	}
	//编辑
	function editFile(pid,fileDesc,lconfirmation){
		$('#updateForm').form('reset');
		$('#comfilePid').val(pid);
		$('#fileDesc2').textbox('setValue',fileDesc);
		if(lconfirmation==1){
			$("#updateDiv input[name='Legalconfirmation'][value=1]").attr("checked",true); 
		}else{
			$("#updateDiv input[name='Legalconfirmation'][value=2]").attr("checked",true);
		}
		$('#updateDiv').dialog('open').dialog('setTitle','修改结清文件内容');
	}	
	//编辑后保存
	function updateF(){
		$('#updateForm').form('submit',{
			success:function(response){
				if(response=="true"){
					$.messager.alert('操作提示', "文件修改成功", 'success');
				}else{
					$.messager.alert('操作提示', "文件修改失败", 'error');
				}
				$('#updateDiv').dialog('close');
				$("#grid").datagrid("reload");
			}
		})
	}
	//删除文件
	function deleteFile(fileid){
		if(confirm('是否确认删除')){
			$.ajax({
				url:'${basePath}rePaymentController/deleteProjectCompleteFile.action',
				type:'post',
				data:{pId:fileid},
				dataType:'json',
				success:function(st){
					if(st==true){
						$.messager.alert('操作提示', "文件删除成功", 'success');
					}else{
						$.messager.alert('操作提示', "文件删除失败", 'error');
					}
					$("#grid").datagrid("reload");
					$("#grid").datagrid("clearChecked");
				}
			})
		}
	}
	//批量删除
	function dele(){
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
		if(confirm('是否确认删除')){
			$.ajax({
				url:'${basePath}rePaymentController/deleteProjectCompleteFile.action',
				type:'post',
				data:{pId:pidArray},
				dataType:'json',
				success:function(st){
					if(st==true){
						$.messager.alert('操作提示', "文件删除成功", 'success');
					}else{
						$.messager.alert('操作提示', "文件删除失败", 'error');
					}
					$("#grid").datagrid("reload");
					$("#grid").datagrid("clearChecked");
				}
			})
		}
	}
	//重新上传
	function reUpload(pid,fileName){
		$('#updateForm2').form('reset');
		$('#comfilePid2').val(pid);
		$('#file3').textbox('setText',fileName);
		$('#updateDiv2').dialog('open').dialog('setTitle','重新上传');
	}
	
	function updateF2(){
		$('#updateForm2').form('submit',{
			success:function(response){
				var str = JSON.parse(response);
				if(str.uploadStatus==2){
					$.messager.alert('操作提示', "重新上传成功", 'success');
				}else{
					$.messager.alert('操作提示', "重新上传失败", 'error');
				}
				$('#updateDiv2').dialog('close');
				$('#grid').datagrid('reload');
			}
		})
	}
	
	function fileSizefiter(value,row,index){
		var sNum='';
		if(value==0 || value == undefined|| value==null){
		}else{
			sNum = getFileSize(value);
		}
		return sNum;
	}
	
	function getFileSize(fileSize)
	{
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
</script>
<body  class="easyui-layout">

<div data-options="region:'center',border:false" id="#tabs">
<div style="padding: 10px 10px">
	<div style="margin-top:10px">
		<div class="easyui-panel" data-options="title:'基本项目信息',collapsible:true" style="padding: 10px 10px 10px 80px"> 
			<span>项目名称：</span>
			<span id="pro_name"></span>
			<span style="margin: 0 0 0 300px">项目编号：</span>
			<span id="pro_number"></span>
		</div>
	</div>
	<div style="margin-top:10px">
			
		<div class="easyui-panel" title="贷款收息表" data-options="collapsible:true">
		<div id="tablegrid" class="pt10"  >
		<%@ include file="list_loanCalculate.jsp"%> 
		</div>
		</div>
	</div>
	<div style="margin-top:10px">
		<div class="easyui-panel" data-options="title:'项目结清处理',collapsible:true" style="padding: 10px 10px 10px 80px"> 
		<form action="${basePath }rePaymentController/updateLoanSettle.action" id="setForm" method="post">
			<input type="hidden" name="projectId" value="${projectId }">
			<input type="hidden" name="pId" id="pId" value="${pid}">
			<div>
				<table>
					<tr>
						<td align="left" style="width: 150px;height: 30px;">总计提示：</td>
						<td></td>
						<td style="width: 200px;"></td>
						<td></td>
					</tr>
					<tr>
						<td align="right" style="height: 30px;">贷款金额：</td>
						<td align="left" id="">${loanBaseDataBean.totalAmt}元</td>
						<td align="right">未还本金金额：</td>
						<td align="left" id="">${loanBaseDataBean.uprincipal}元</td>
					</tr>
					<tr>
						<td align="right" style="height: 30px;">应收利息总额：</td>
						<td align="left" id="">${loanBaseDataBean.rinterest}元</td>
						<td align="right">未收利息总额：</td>
						<td align="left" id="">${loanBaseDataBean.uinterest}元</td>
					</tr>
					<tr>
						<td align="right" style="height: 30px;">应收管理总额：</td>
						<td align="left" id="">${loanBaseDataBean.rmangCost}元</td>
						<td align="right">未收管理费总额：</td>
						<td align="left" id="">${loanBaseDataBean.umangCost}元</td>
					</tr>
					<tr>
						<td align="right" style="height: 30px;">应收其他费用总额：</td>
						<td align="left" id="">${loanBaseDataBean.rotherCost}元</td>
						<td align="right">未收其他费用总额：</td>
						<td align="left" id="">${loanBaseDataBean.uotherCost}元</td>
					</tr>
				</table>
			</div>
			<div style="margin-top: 10px">
				<b><span>项目已无欠款，可进行结清。</span></b>
			</div>
			<div style="margin-top: 10px">
				<p>
					<label style="float: left;">*结清意见与说明：</label>
					<input name="completeDesc" id="completeDesc" class="easyui-textbox" style="width:800px;height:60px" data-options="multiline:true" required="true">
				</p>
				<div class="clear"></div>
			</div>
			<div style="margin-top: 10px">
				<table id="grid" class="easyui-datagrid" style="height: 200px;width: 900px;"
					data-options="
						url:'${basePath }rePaymentController/getProjectCompleteFile.action?pid=${pid}',
						method: 'POST',
						rownumbers: true,
						toolbar: '#toolbar',
						idField: 'pid',
						singleSelect: false,
						selectOnCheck:true,
						checkOnSelect: false">
					<thead>
						<tr>
							<th data-options="field:'pid',checkbox:true,width:50"></th>
							<th data-options="field:'settlefileType',width:80,formatter:setFilter" align="center">文件类型</th>
							<th data-options="field:'fileType',width:80" align="center">文件种类</th>
							<th data-options="field:'fileName',width:80" align="center">文件名称</th>
							<th data-options="field:'fileSize',width:50,formatter:fileSizefiter" align="center">大小</th>
							<th data-options="field:'uploadDttm',width:120" align="center">上传时间</th>
							<th data-options="field:'fileDesc',width:100" align="center">上传说明</th>
							<th data-options="field:'fileUrl',hidden:true"></th>
							<th data-options="field:'Legalconfirmation',width:100,formatter:LegalconfirmationFilter" align="center">法务是否已确认</th>
							<th data-options="field:'contractNo',width:250,formatter:czFilter" align="center">操作</th>
						</tr>
					</thead>
				</table>
			</div>
			<div style="margin-top: 10px;padding: 0 300px;">
				<a class="easyui-linkbutton" onclick="save()" style="width: 80px">保存</a>
				<a class="easyui-linkbutton" onclick="cancel()" style="width: 80px">取消</a>
			</div>
		</div>
		</form>
	</div>
</div>

<div id="toolbar">
	<a href="javascript:void(0)" iconCls="icon-export" plain="true" class="easyui-linkbutton" onclick="makeFile()">生成结清文件</a>
	<a href="javascript:void(0)" iconCls="icon-save" plain="true" class="easyui-linkbutton" onclick="upload()">上传</a>
	<a href="javascript:void(0)" iconCls="icon-remove" plain="true" class="easyui-linkbutton" onclick="dele()">删除</a>
</div>

<div class="easyui-dialog" id="uploadDiv" data-options="closed:true" style="width: 500px;height: 220px">
	<form action="${basePath }rePaymentController/saveProjectCompleteFile.action" method="post" id="uploadForm" enctype="multipart/form-data" >
		<ul style="padding: 10px 0 0 20px">
			<input type="hidden" name="completePid" id="completePid" value="${pid}">
			<li>文件路径:<input class="easyui-filebox" data-options="buttonText:'选择文件',missingMessage:'选择一个文件'" required="true" name="file2" id="file2" style="width: 300px"></li>
			<li>上传说明:<input class="easyui-textbox" style="width:320px;height:70px" data-options="required:false,multiline:true" name="fileDesc" id="fileDesc"></li>
			<li>法务确认:<input type="radio" name="Legalconfirmation" value="1" checked="checked">是
			<input type="radio" name="Legalconfirmation" value="2">否</li>
			<li style="text-align: center;"><a class="easyui-linkbutton" onclick="saveFile()" style="width: 100px">保存</a></li>
		</ul>
	</form>
</div>
<div class="easyui-dialog" id="updateDiv" data-options="closed:true" style="width: 500px;height: 200px">
	<form action="${basePath }rePaymentController/editProjectCompleteFile.action" method="post" id="updateForm">
		<ul style="padding: 10px 0 0 20px">
			<input type="hidden" name="pId" id="comfilePid">
			<li>上传说明:<input class="easyui-textbox" style="width:320px;height:70px" data-options="required:false,multiline:true" name="fileDesc" id="fileDesc2"></li>
			<li>法务确认:<input type="radio" name="Legalconfirmation" value="1">是
			<input type="radio" name="Legalconfirmation" value="2">否</li>
			<li style="text-align: center;"><a class="easyui-linkbutton" onclick="updateF()" style="width: 100px">保存</a></li>
		</ul>
	</form>
</div>
<div class="easyui-dialog" id="updateDiv2" data-options="closed:true" style="width: 500px;height: 120px">
	<form action="${basePath }rePaymentController/reUploadProjectCompleteFile.action" method="post" id="updateForm2" enctype="multipart/form-data" >
		<ul style="padding: 10px 0 0 20px">
			<input type="hidden" name="pId" id="comfilePid2">
			<li>文件路径:<input class="easyui-filebox" data-options="buttonText:'选择文件',missingMessage:'选择一个文件'" required="true" name="file2" id="file3" style="width: 300px"></li>
			<li style="text-align: center;"><a class="easyui-linkbutton" onclick="updateF2()" style="width: 100px">保存</a></li>
		</ul>
	</form>
</div>
</div>
</body>