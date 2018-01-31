<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<link href="/BMS/upload/css/fileUpload.css" type="text/css"
	rel="stylesheet" />

<script type="text/javascript">
var projectId = ${projectId};

function onSelect(date) {
//	debugger;
	var endMonthStr = $("#endMonth").datebox("getValue");
	var endWeekStr = $("#endWeek").datebox("getValue");
	var endDayStr = $("#endDay").datebox("getValue");
	if(""!= endMonthStr && null != endMonthStr){
		document.getElementById('input_show').value = endMonthStr;
	}else if(""!= endWeekStr && null != endWeekStr){
		document.getElementById('input_show').value = endWeekStr;
	}else if(""!= endDayStr && null != endDayStr){
		document.getElementById('input_show').value = endDayStr;
	}else{
		 var now = new Date();
	        var year = now.getYear();
	        var month = now.getMonth();
	        var day = now.getDate();
	        var time_str = "2" + year + "-" + month + "-" + day;
	        document.getElementById('input_show').value=time_str;
	        //一秒刷新一次显示时间
	        var timeID = setTimeout(showLeftTime, 5000);
	}
}
function saveReport()
{
	var monitorTitle = document.getElementById("monitorTitle").value;
	var monitorContent = document.getElementById("monitorContent").value;
	
	if(monitorTitle == "" || monitorTitle == null){
		$.messager.alert("提示", "监控标题不能为空", "info");
		return false;
	}
	if(monitorContent == "" || monitorContent == null){
		$.messager.alert("提示", "监控内容不能为空", "info");
		return false;
	}
	// 保存提交
	$('#myform').form('submit', {
		url : "addExceptionList.action",
		onSubmit : function() {
				return true; 
		},
		success : function(result) {
			var ret = eval("("+result+")");
		//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
				if(ret && ret.header["success"] ){
					$.messager.alert('操作提示',ret.header["msg"]);
 					// 清空所选择的行数据
 					clearSelectRows("#grid");
					var pid=ret.header["pid"];
					$("#pid").val(pid);
				}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');	
				}
		},error : function(result){
			alert("保存失败！");
		}
	});
}
</script>
<div style="padding:10px 10px 10px 10px">
<div class="easyui-panel" data-options="title:'新增异常监控',collapsible:true" style="padding: 5px 5px 5px 10px">
<form id="myform" action="${basePath}foreAfterLoanController/addExceptionList.action" method="post"> 
<table class="beforeloanTable common_flow" style="width:96%" >
<tbody>
 	<tr>
 		<td class="label_right"><font color = "red">*</font>风险等级：</td>
		<td colsapn="4" style=" width: 80px;">													
			<select class="select_style easyui-combobox" editable="false" name="dangerLevel" >
	          <option value="-1">--请选择--</option>
	          <option value=1 >正常状态</option>
			  <option value=2 >正常风险</option>
			  <option value=3 >重大风险</option>
			  <option value=4 >紧急风险</option>
	        </select>
	    </td>
 	</tr>
 	
 	<tr>
 	<td class="label_right"><font color = "red">*</font>监控标题:</td>
    <td>
		<input name="monitorTitle" id="monitorTitle"  class="easyui-textbox" style="width:80%;height:60px" data-options="multiline:true,validType:'length[0,500]'">
  	</td>
  	
  	 <td class="label_right"><font color = "red">*</font>监控时间:</td>
    <td>
    	<input id="monitorDate" name="monitorDate" class="easyui-datebox" data-options="onSelect:onSelect"/>
   </td>
</tr>
<tr>
 	<td class="label_right"><font color = "red">*</font>监控内容:</td>
    <td>
		<input name="monitorContent" id="monitorContent"  class="easyui-textbox" style="width:80%;height:60px" data-options="multiline:true,validType:'length[0,500]'">
  	</td>
</tr>
<tr>
 	<td class="label_right"><font color = "red">*</font>监控意见:</td>
    <td>
		<input name="monitorOpinion" id="monitorOpinion"  class="easyui-textbox" style="width:80%;height:60px" data-options="multiline:true,validType:'length[0,500]'">
  	</td>
</tr>
<tr>

  	<td class="label_right"><font color = "red">*</font>跟进状态:</td>
  	<td colsapn="2">													
			<select class="select_style easyui-combobox" editable="false" name="followStatus">
	          <option value="-1">--请选择--</option>
	          <option value=1 >是</option>
			  <option value=2 >否</option>
	        </select>
	</td>
  	<td class="label_right"><font color = "red">*</font>下次跟进时间:</td>
    <td>
    	<input id="nextMonitorDate" name="nextMonitorDate" class="easyui-datebox" data-options="onSelect:onSelect"/>
  	</td>
</tr>

<tr>

  	<td class="label_right"><font color = "red">*</font>待跟进人:</td>
  	<td colsapn="2">													
			<select class="select_style easyui-combobox" editable="false" name="nextFollowId">
	          <option value="-1">--请选择--</option>
	          <option value=1 >我</option>
			  <option value=2 >你</option>
	        </select>
	 </td>
  	<td class="label_right">提醒方式:</td>
    <td>
	 	<input type="checkbox" name="noticeWay" value="1"   />短信
	    <input type="checkbox" name="noticeWay" value="2"  />邮件
  	</td>
</tr>
<tr>
	<td></td>
	<td class="align_center" height="50">
		<input type="button" id="save" class="save_data text_btn"  value="保存" onclick="saveReport()"/>
		<input name="projectId" id="projectId" type="hidden" value = ${projectId}>
	</td> 
	<td>
	</td>
</tr>
</tbody>
</table>
		</form>
	</div>
