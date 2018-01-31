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

var finish = false;
$(document).ready(function(){
	$('.detailTable').datagrid({
		url:"loanBadDetCalculation.action?loanId=${loanId}",
		collapsible:true,
		rownumbers:true,
		selectOnCheck:false,
		checkOnSelect:false,
		columns:[[
		          {field:'typeName',title:'类别',width:120,align:'center'},
		          {field:'col1',title:'本金',formatter:formatMoney,width:100,align:'center'},
		          {field:'col2',title:'管理费',formatter:formatMoney,width:100,align:'center'},
		          {field:'col3',title:'其他费用',formatter:formatMoney,width:100,align:'center'},
		          {field:'col4',title:'贷款利息',formatter:formatMoney,width:100,align:'center'},
		          {field:'totalAmt',title:'合计',formatter:formatMoney,width:100,align:'center'}
		          ]]
	});
	
	var reviewStatus = "${badDebtBean.reviewStatus}";
	$("#reviewStatus").combobox("setValue",reviewStatus);
	
	// 已经完结了
	if(reviewStatus == "1")
	{
		finish = true;
		
		$("#badDetCalculation").hide();
		$("#saveBadDebt").hide();
		
		$("#remark").attr("disabled",true);
		$("#asOfDT").datebox('disable');
		$("#shouldDt").datebox('disable');
		$("#reviewStatus").combobox("disable");
		
		$("#fileCBtn").hide();
	}	
});

//  保存
function saveBadDebt()
{
	var postData ={"loanId":"${loanId}","pid":$("#pid").val(),"remark":$("#remark").val(),"reviewStatus":$("#reviewStatus").combobox("getValue")};
	// 保存提交
	$.ajax({
		type: "POST",
        data:postData,
		url : "updateBadDebt.action",
		dataType: "json",
	    success: function(data){
	    	// 保存成功
	    	if(data.header.success)
	    	{
	    		$.messager.alert("提示","保存成功","info", function () {
	    			window.location.reload();// 关闭当前页
	            });
	    		
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
//  重算坏账呆账
function reCalculationBadDebt()
{
	
	var postData ={"pid":$("#pid").val(),"asOfDT":$("#asOfDT").datebox('getValue'),"shouldDt":$("#shouldDt").datebox('getValue'),"projectId":"${badDebtBean.projectId}"};
	// 保存提交
	$.ajax({
		type: "POST",
        data:postData,
		url : "reCalculationBadDebt.action",
		dataType: "json",
	    success: function(data){
	    	// 保存成功
	    	if(data.header.success)
	    	{
	    		// 避免页面乱点，还原处理状态，保存一下
	    		var reviewStatus = "${badDebtBean.reviewStatus}";
	    		$("#reviewStatus").combobox("setValue",reviewStatus);
	    		saveBadDebt();
	    	}
	    	// 保存失败
	    	else
    		{
	    		$.messager.alert("提示",data.header.msg,"error");
    		}
		},error : function(result){
			$.messager.alert("提示","重算失败","error");
		}
	}); 
}



function datiladvoperat(value, row, index) {
	
	var reviewStatus = "${badDebtBean.reviewStatus}";
	// 已经完结了
	if(reviewStatus == "1")
	{
		$("#badDetCalculation").hide();
		$("#saveBadDebt").hide();
		
		return "";
	}	
	
	var va="<a href='viewCollateralProcessing.action?pid="+row.pid+"'><font color='blue'>抵质押处理</font></a>";
	return va;
}


	
</script>
<div style="padding:10px 10px 10px 10px">
		<div class="easyui-panel" data-options="title:'基本项目信息',collapsible:true" style="padding: 5px 5px 5px 10px"> 
			<table class="cus_table">
				<tr>
					<td class="label_right">项目名称：</td>
					<td><a style="color:blue;cursor:pointer;" onclick="formatterProjectName.disposeClick('${bean.projectId}')">${bean.projectName}</a></td>
					<td class="label_right">项目编号：</td>
					<td ><h2>${bean.projectNumber}</h2></td>
				</tr>
				<tr>
				   <td class="label_right">业务类别：</td>
				   <td style="width:300px;"><h2>${bean.businessCatelog}</h2></td>
				   <td class="label_right">业务品种：</td>
				   <td><h2>${bean.businessType}</h2></td>
				</tr>
				<tr>
				  <td class="label_right">流程类别：</td>
				  <td><h2>${bean.flowCatelog }</h2></td>
				  <td class="label_right" >项目经理：</td>
				  <td ><h2/>${bean.realName}</h2></td>
				</tr>
			</table>
		</div>
	<div style="margin-top:10px">
		<div class="easyui-panel" data-options="title:'放款收息表',collapsible:true,collapsed:true" style="padding: 5px 5px 5px 10px"> 
				<%@ include file="../repayment/list_loanCalculate.jsp"%> 
		</div>
	</div>
	<div style="margin-top:10px">
		<div class="easyui-panel" data-options="title:'呆账坏账处理'" style="padding: 5px 5px 5px 10px"> 
		  <input type="hidden" id="pid" value="${badDebtBean.pid}" />
			 <table style="width: 95%">
			   <%--  <tr>
			       <td>
				              坏账记息截止日：&nbsp;
				       <input id='asOfDT' class='endDateText easyui-datebox' value="${badDebtBean.asOfDT}"  data-options='editable:false'/> &nbsp;
				       <input type="button" id="badDetCalculation" class="text_btn"  value="坏账未收计算" onclick="reCalculationBadDebt();"/>
			       </td>
			    </tr> --%>
			    <tr> 
			       <td >
			           <table class="detailTable"></table>
			       </td>
			    </tr>
			     <tr>
			        <td style="vertical-align: top;">处理备注：
			       <textarea rows="5" cols="60" id="remark" class="text_style" style="width:600px;height:60px;">${badDebtBean.remark}</textarea></td>
			    </tr>
			    <tr>
			       <td>
			           <%--    应收日期：&nbsp;
				       <input id='shouldDt' class='endDateText easyui-datebox' value="${badDebtBean.shouldDt}" data-options='editable:false'/>&nbsp; --%>
			       
			             处理状态:&nbsp;
			             <select class="easyui-combobox" id="reviewStatus" style="width:100px;">
			                <option value="0">处理中</option>
			                <option value="1">处理完成</option>
			             </select>
			         <input type="button" id="saveBadDebt" class="text_btn"  value="保存" onclick="saveBadDebt();"/>
			       </td>
			    </tr>
			 </table>
		</div>
	</div>
	
	<div style="margin-top:10px">
		<div class="easyui-panel" data-options="title:'抵质押物处理'" style="padding: 5px 5px 5px 10px"> 
		     <table class="easyui-datagrid" data-options="url:'getProjectAssBase.action?projectId=${badDebtBean.projectId}',rownumbers:true,fitColumns:true,singleSelect:true">
			     <thead>
			        <tr>
			         <th data-options="field:'pid',hidden:true"></th>
			         <th data-options="field:'ownName',align:'center'">权利人</th>
				     <th data-options="field:'addressDetail',align:'center'">物件地址</th>            
				     <th data-options="field:'warrantsNumber',align:'center'">权证编号<br>（房地产证号）</th>
				     <th data-options="field:'regDt',align:'center'">登记日期</th>         
				     <th data-options="field:'itemName',align:'center'">物件名称<br>（房地产名称）</th>         
				     <th data-options="field:'aa',align:'center'">成色描述<br>（建筑面积）</th>         
				     <th data-options="field:'purpose',align:'center'">用途</th>         
				     <th data-options="field:'regPrice',formatter:formatMoney,align:'center'">登记价（元）</th> 
				     <th data-options="field:'saveRemark',align:'center'">备注</th>
				     <th data-options="field:'aa',align:'center'">是否已处理</th>
				     <th data-options="field:'processDt',align:'center'">处理时间</th> 
				     <th data-options="field:'transactExplain',align:'center'">办理说明</th>   
				     <th data-options="field:'yy',align:'center',formatter:datiladvoperat">操作</th>   
			       </tr>
			     </thead>
		     </table>
		</div>
	</div>
	
	<div style="margin-top:10px">
		<div class="easyui-panel" data-options="title:'坏账处理资料上传'" style="padding: 5px 5px 5px 10px"> 
		
	<!--  上传开始-->	
	<div id="toolbar" class="easyui-panel" style="border: 0;"></div>
<div id="fileCBtn">
	&nbsp; <a href="javascript:void(0);"
		class="easyui-linkbutton" iconCls='icon-add' plain="true" onclick="uploadrepayfile()">上传</a> <a
		href="javascript:void(0);" iconCls='icon-remove' plain="true" class="easyui-linkbutton"
		onclick="delDivert()">删除</a>
</div>
<div id="status"></div>
<div id="dlg-buttons">
	<center>
		<a id="add" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok"  onclick="submitForm()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#upload').dialog('close')">取消</a>
	</center>
</div>
<div id="upload" class="easyui-dialog"
	style="width: 500px; height: 300px; padding:0 20px 10px 20px;" closed="true"
	buttons="#dlg-buttons">
	<form id="fileUploadForm" name="fileUploadForm"
		action="${basePath}badDebtController/uploadBaddealFile.action"
		method="post" enctype="multipart/form-data" target="hidden_frame">
		<p>
			文件类型： <select name="filekinds" id="selectAge"
				style="border: 1px solid #000">
				<option value="1">坏账处理资料</option>
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
			<textarea rows="4" style="width: 300px;" name="fileDesc" id="fileDesc"></textarea>
		</p>
	</form>
</div>
<!-- queryRegAdvapplyFile.action?preRepayId='+preRepayId -->
<div style="width: 100%;text-align: center;">
	<table id="up_data" class="easyui-datagrid"
		data-options="
	    url: '${basePath}badDebtController/queryBaddealFile.action?badId='+badId,			
	    method: 'POST',
	    idField: 'pid',
	    fitColumns:true,
		checkOnSelect: false,
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#fileCBtn'
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
	var downloadDom = "<a class='download' href='"+BASE_PATH+"beforeLoanController/downloadData.action?path="+row.filePath+"&fileName="+row.fileName+"'><font color='blue'>下载</font> | </a>";
	var downd = "<span  style='display: none;' id='"+row.pId+"'>"
			+ row.filePath
			+ "</span>";
			
	 if(!finish)
	 {
		 downd+="<a href='javascript:void(0)' onclick='delfilebypath("
			+ row.pId + ")'><font  color='blue'> 删除</font></a>";
	 }
	
	return downloadDom + downd;
}
function delfilebypath(dta){
	if(confirm("是否删除此数据？")){
	$.post('${basePath}rePaymentController/delectUpdateAdvapply.action',{pId : dta,path : $("#" + dta).text()},
		function(data) {
			$.messager.alert("操作提示", "删除成功！", "success");
			$.post("${basePath}badDebtController/queryBaddealFile.action",{badId :badId},
					function(dt) {
						$('#up_data').datagrid('loadData',eval("{"+ dt+ "}"));
					});
		});
	}
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
		var ret = eval("("+data+")");
		var saveSucc=ret.header["saveSucc"];
			if(saveSucc=="saveSucc"){
				$("#file1").val('');
				$("#fileDesc").val('');
				$("#txt1").val('');
				$.messager.alert("操作提示", "上传成功！", "success");
				$('#upload').dialog('close')
			}else{
				$.messager.alert("操作提示", "上传失败！", "error");
			}
			
			$.post("${basePath}badDebtController/queryBaddealFile.action",{badId :badId},
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
	if(confirm("是否删除所选数据？")){
		$.post("${basePath}rePaymentController/delectUpdateAdvapply.action",{pId : str},
				function(data) {
					$.messager.alert("操作提示", "删除成功！", "success");
					$.post("${basePath}badDebtController/queryBaddealFile.action",{badId :badId},
							function(dt) {
								$('#up_data').datagrid('loadData',eval("{"+ dt+ "}"));
							});
				});
	}
}
function uploadType(value, row, index) {
	if (1 == row.fileFunType) {
		return "坏账处理资料";
	}
	return "坏账处理资料";
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