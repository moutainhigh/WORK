<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<link href="/BMS/upload/css/fileUpload.css" type="text/css"
	rel="stylesheet" />

<style type="text/css">
 li {
 	margin-bottom: 5px;
 }
</style>
<script type="text/javascript">

function saveBadDebt()
{
	var postData ={"pid":"${bean.pid}","processDt":$("#processDt").datebox('getValue'),"processRemark":$("#processRemark").val()};
	// 保存提交
	$.ajax({
		type: "POST",
        data:postData,
		url : "updateProjectAssBaseProcessing.action",
		dataType: "json",
	    success: function(data){
	    	// 保存成功
	    	if(data.header.success)
	    	{
	    		$.messager.alert("提示","保存成功","info");
	    		history.go(-1);
	    	}
	    	// 保存失败
	    	else
    		{
	    		$.messager.alert("提示",data.header.msg,"error");
    		}
		},error : function(result){
			$.messager.alert("提示","提交失败","error");
		}
	}); 
	
}


</script>
<div style="padding:10px 10px 10px 10px">
		<div class="easyui-panel" data-options="title:'抵质押物处理',collapsible:true" style="padding: 5px 5px 5px 10px"> 
			<table class="cus_table">
				<tr>
					<td class="align_right">所有权人：</td>
					<td colspan="3">${bean.ownName}</td>
				</tr>
				<tr>
				   <td class="align_right">权证编号（房地产证号）：</td>
				   <td style="width:300px;">${bean.warrantsNumber}</td>
				   <td class="align_right">建筑面积：</td>
				   <td></td>
				</tr>
				<tr>
				  <td class="align_right">物件地点</td>
				  <td colspan="3">${bean.addressDetail}</td>
				</tr>
				<tr>
				   <td class="align_right">物件名称：</td>
				   <td colspan="3">${bean.itemName}</td>
				</tr>
				<tr>
				   <td class="align_right">用途：</td>
				   <td style="width:300px;">${bean.purpose}</td>
				   <td class="align_right">评估净值：</td>
				   <td>${bean.assessValue}</td>
				</tr>
				<tr>
				   <td class="align_right">抵押登记编号：</td>
				   <td style="width:300px;">${bean.regNumber}</td>
				   <td class="align_right">登记时间：</td>
				   <td>${bean.regDt}</td>
				</tr>
				<tr>
				   <td class="align_right">保管时间：</td>
				   <td style="width:300px;">${bean.saveDttm}</td>
				   <td class="align_right">保管备注：</td>
				   <td>${bean.saveRemark}</td>
				</tr>
				<tr>
				   <td class="align_right">处理备注：</td>
				   <td colspan="3"><textarea rows="5" cols="60" id="processRemark">${bean.processRemark}</textarea></td>
				</tr>
				<tr>
				   <td class="align_right">处理时间：</td>
				   <td colspan="3"> <input id='processDt' class='endDateText easyui-datebox' value="${bean.processDt}" data-options='editable:false'/>&nbsp;
				   
				    <input type="button" id="saveBadDebt" class="text_btn"  value="保存" onclick="saveBadDebt();"/>
				   
				   </td>
				</tr>
			</table>
		</div>
	
	<div style="margin-top:10px">
		<div class="easyui-panel" data-options="title:'抵质押物处理资料上传'" style="padding: 5px 5px 5px 10px"> 
	<!--  上传开始-->	
	<div id="toolbar" class="easyui-panel" style="border: 0;"></div>
<div>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0);"
		class="easyui-linkbutton" onclick="uploadrepayfile()">上传</a> <a
		href="javascript:void(0);" class="easyui-linkbutton"
		onclick="delDivert()">删除</a>
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
<div id="upload" class="easyui-dialog"
	style="width: 500px; height: 300px; padding: 10px 20px;" closed="true"
	buttons="#dlg-buttons">
	<form id="fileUploadForm" name="fileUploadForm"
		action="${basePath}badDebtController/uploadBaddealFile.action"
		method="post" enctype="multipart/form-data" target="hidden_frame">
		<p>
			文件类型： <select name="filekinds" id="selectAge"
				style="border: 1px solid #000">
				<option value="1">营业执照</option>
				<option value="2">调查资料</option>
				<option value="3">其他</option>
			</select>
		</p>
		<p style="display: none;">
			<input name="badId" id="badId" />
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
<!-- queryRegAdvapplyFile.action?preRepayId='+preRepayId -->
<div style="width: 100%; padding-top: 20px; text-align: center;">
	<table id="up_data" class="easyui-datagrid"
		data-options="
	    url: '${basePath}badDebtController/queryBaddealFile.action?badId='+badId,			
	    method: 'POST',
	    idField: 'pid',
	    fitColumns:true,
		checkOnSelect: false,
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar'
	    ">
		<!-- 表头 -->
		<thead>
			<tr>
				<th data-options="field:'pid',checkbox:true"></th>
				<th data-options="field:'fileType',width:100">文件类型</th>
				<th
					data-options="field:'fileFunType',width:150,formatter:uploadType">文件种类</th>
				<th data-options="field:'fileName',width:150">文件名称</th>
				<th data-options="field:'fileSize',width:150">大小</th>
				<th
					data-options="field:'uploadDttm',width:150,formatter:dateupSplit">上传时间</th>
				<th data-options="field:'fileDesc',width:150">上传说明</th>
				<th data-options="field:'yyyyy',width:300,formatter:uploadadvoperat">操作</th>
			</tr>
		</thead>
	</table>
</div>
<div style="height: 40px"></div>	
		
		
<!--  上传结束-->	
		
		
		
		
		
		</div>
	</div>
</div>
<script>
var projectId = "";
var projectNum = "";
var badId =10007;
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
	var downloadDom = "<a href='${basePath}rePaymentController/downloadadvapply.action?path="
			+ row.filePath + "'><font color='blue'>点击下载</font></a> ";
	var downd = "<span  style='display: none;' id='"+row.pId+"'>"
			+ row.filePath
			+ "</span><a href='javascript:void(0)' onclick='delfilebypath("
			+ row.pId + ")'><font  color='blue'> 删除</font></a>";
	return downloadDom + downd;
}
function delfilebypath(dta){
	$.post('${basePath}rePaymentController/delectUpdateAdvapply.action',
		{pId : dta,path : $("#" + dta).text()},
		function(data) {
			$.messager.alert("操作提示", "删除成功！", "success");
			$.post("${basePath}badDebtController/queryBaddealFile.action",
					{badId :badId},
					function(dt) {
						$('#up_data').datagrid('loadData',eval("{"+ dt+ "}"));
					});
		});
}
//上传文件
function submitForm() {
	var fileName = $("#file1").val();
	if (fileName == "" || null == fileName) {
		$.messager.alert("操作提示", "请选择要上传的文件！", "error");
		return false;
	}
	$("#badId").val(badId);
	$('#fileUploadForm').form('submit',{
		success : function(data) {
				$.messager.alert("操作提示", "上传成功！", "success");
				$('#upload').dialog('close')
				$.post("${basePath}badDebtController/queryBaddealFile.action",
						{badId :badId},
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
	$.post("${basePath}rePaymentController/delectUpdateAdvapply.action",
					{pId : str},
					function(data) {
						$.messager.alert("操作提示", "删除成功！", "success");
						$.post("${basePath}badDebtController/queryBaddealFile.action",
								{badId :badId},
								function(dt) {
									$('#up_data').datagrid('loadData',eval("{"+ dt+ "}"));
								});
					});
}
function uploadType(value, row, index) {
	if (1 == row.fileFunType) {
		return "营业执照";
	}
	if (2 == row.fileFunType) {
		return "调查资料";
	}
	return "其他";
}
function dateupSplit(value, row, index) {
	if (null != row.uploadDttm && "" != row.uploadDttm) {
		return row.uploadDttm.split(" ")[0];
	}
	return row.uploadDttm;
}
function uploadrepayfile() {
	if (typeof(badId) == "undefined"||badId < 1||badId==''||badId==null) {
		$.messager.alert("操作提示", "请先保存数据！", "info");
		return false;
	}
	$('#upload').dialog('open').dialog('setTitle', "修改模板文件路径");
	$("#upload").dialog("move", {
		top : $(document).scrollTop() + 260 * 0.5
	});
}
	</script>