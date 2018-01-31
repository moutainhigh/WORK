<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监管结果新增/编辑</title>


<script type="text/javascript">
 
/* $('#myForm').form({
    url:'/createBizProjectRegHistory.action',
    onSubmit:function(){
        return $(this).form('validate');
    },
    success:function(data){
        alert(data);
    }
});  */



function create(){
	$('#myForm').form('submit', {
		url : "${basePath}/afterLoanController/createBizProjectRegHistory.action",
		onSubmit : function() {return $(this).form('validate');},
		success : function(result) {
		   window.location.href="${basePath}afterLoanController/execuSuperyviseBusiness.action?planId=${planId}";
		}
	}); 
}
function majaxReset(){
	$('#myForm').form('reset');
}
</script>

</head>
<body>
   
   <form id="myForm" method="POST"> 
           
  <%--   <input type="hidden"  name="projectId" id="projectId" value="${projectId}"/> --%>
    <input type="hidden"  name="pid" id="pid" value="${r.pid}"/>
    <input type="hidden"  name="actualUserId" id="actualUserId" value="${userId}"/>
	<input type="hidden"  name="regulatoryPlanId" id="regulatoryPlanId"   value="${regulatoryPlanId}"/>
	<table class="cus_table">
		<tr style="height: 25px;">
			<th colspan="4">监管结果录入编辑</th>
		</tr>
		<tr>
			<td class="label_right">监管时间：</td>
			<td><input name="actualBeginDt" id="actualBeginDt"   value="${r.actualBeginDt}" type="text"
				class="easyui-datebox" 
				   />  
			</td>
		</tr>
	<tr>	 
		<td class="label_right">监管标题：</td>
		<td><input class="text_style" type="text" style="width: 340px;"
			   name="regualatorySubject" id="regualatorySubject"  value="${r.regualatorySubject}"/>  </td>
	</tr>
	
	<tr>	 
		<td class="label_right">监管內容：</td>
		<td> 
		  <textarea style="height:70px;width: 340px;" class="text_style"  rows="5" cols="47" name="regualatoryContent" id="regualatoryContent"    >${r.regualatoryContent}</textarea>
		</td>
	</tr> 
	
	<tr>	 
		<td class="label_right">监管结果：</td>
		<td><select name="regualatoryResult" id="regualatoryResult">
		  <option value="1"  <c:if test="${r.regualatoryResultStr==1}"> selected </c:if> >正常</option>
          <option value="2"  <c:if test="${r.regualatoryResultStr==2}"> selected </c:if> >挪用嫌疑</option>
          <option value="3"  <c:if test="${r.regualatoryResultStr==3}"> selected </c:if> >违约嫌疑</option>
          <option value="4"  <c:if test="${r.regualatoryResultStr==4}"> selected </c:if> >存在挪用行为</option>
          <option value="5"  <c:if test="${r.regualatoryResultStr==5}"> selected </c:if> >存在违约行为</option>
		</select></td>
	</tr>
	
	 <tr>	 
		<td class="label_right"> 监管意见：</td>
		<td><textarea  style="height:70px;width: 340px;" rows="5" cols="47"  name="regualatoryMsg" id="regualatoryMsg"  class="text_style easyui-validatebox">${r.regualatoryMsg}</textarea>  </td>
	</tr>
	 
	 <tr>	 
		<td class="label_right"> 其他意见1：</td>
		<td><textarea  style="height:70px;width: 340px;" class="text_style" rows="5" cols="47" name="regualatoryMsgOt1" id="regualatoryMsgOt1">${r.regualatoryMsgOt1}</textarea>  </td>
	</tr>
	<tr>	 
		<td class="label_right"> 其他意见2：</td>
		<td><textarea style="height:70px;width: 340px;" class="text_style" rows="5" cols="47" name="regualatoryMsgOt2" id="regualatoryMsgOt2">${r.regualatoryMsgOt2}</textarea>  </td>
	</tr>
	
	 <tr>
		 <td class="label_right">&nbsp; </td>
		<td>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="create()">提交</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="majaxReset()">重置</a>
		
		</td>
	</tr>
</table>
</form>

</body>
</html>