<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/common.jsp"%>
<style type="text/css">
</style>
<body>
<div style="margin-top: 10px; margin-left: 10px;">
<form id="baseInfo" action="saveSupervisePlan.action" method="POST">
     <input type="hidden" name="pid" value="${bean.pid}"/>
     <input type="hidden" name="projectId" value="${projectId}"/>
     <table>
     	<tr>
     		<td class="label_right">任务提醒方式：</td>
     		<td>
     			<input type="checkbox" id="isSms" name="isSms"  <c:if test="${bean.isSms ==1}">checked="checked"</c:if>         >
					<span>短信提醒</span> &nbsp;&nbsp;&nbsp; 
				<input type="checkbox" id="isMail" name="isMail"  <c:if test="${bean.isMail ==1}">checked="checked"</c:if> >
					<span>邮件提醒</span>
     		</td>
     	</tr>
     		<td class="label_right">监管人：</td>
     		<td>
     			<select class="select_style easyui-combobox"  data-options="required:true"  style="width: 150px;"
						name="regulatoryUserId" id="regulatoryUserId">
				</select>
     		</td>
     	</tr>
     		<td class="label_right">计划监管时间：</td>
     		<td>
     			<input type="text" class="text_style easyui-datebox"  data-options="required:true,editable:false" id="planDt"  name="planDt" value="${bean.planDt}" />
     		</td>
     	</tr>
     		<td class="label_right">备注：</td>
     		<td><textarea rows="8" cols="50" id="remark">${bean.remark}</textarea></td>
     	</tr>
     	<tr>
     		<td colspan="2" align="center" height="40">
     			<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="doSave()">保存</a>
					&nbsp; 
				<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="closeDialog();">取消</a>
     		</td>
     	</tr>
     </table>
		</form>
  </div>
<script type="text/javascript">
  
setSalesList("regulatoryUserId", "SUPERVISER");
// 由于获取tableId的事件在当前事件后，所以需要延迟100毫秒
setTimeout(function(){
  onloandFinish();
},100);
  
//加载完毕了
  function onloandFinish()
  {
  	// 修改 
  	 if("0" !=  "${bean.pid}")
	{
  		 
		 	$("#regulatoryUserId").combobox('setValue','${bean.regulatoryUserId}');
		  // 非初始化状态
		  if("${bean.regulatoryStatus}" !="0" && "${bean.regulatoryStatus}" !="1")
		  {
			  $("#isSms").attr("disabled","disabled");
			  $("#isMail").attr("disabled","disabled");
			  $("#regulatoryUserId").combobox('disable');
		      $("#planDt").datebox('disable');
		 }	  
    } 
  }
  
function doSave() {
	if(!$("#baseInfo").form('validate'))
	{
		return false;
	}	
	
	var sms = $("#isSms").is(':checked')?"1":"0";
	var mail = $("#isMail").is(':checked')?"1":"0";
	
	var postData={"isSms":sms,"isMail":mail,"regulatoryStatus":"${bean.regulatoryStatus}","pid":"${bean.pid}","projectId":"${projectId}","regulatoryUserId":$("#regulatoryUserId").combobox('getValue'),"planDt":$("#planDt").datebox('getValue'),"remark":$("#remark").val()};
	
		// 保存提交
		$.ajax({
			type: "POST",
			dataType: "json",
	        data:postData,
			url : "saveSupervisePlan.action",
		    success: function(data){
		    	// 保存成功
		    	if(data.header.success)
		    	{
		    		$.messager.alert("提示","保存成功","info");
		    		closeDialog();
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
</body>