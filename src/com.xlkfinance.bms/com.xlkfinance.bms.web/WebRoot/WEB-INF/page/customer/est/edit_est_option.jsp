<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
function saveOption(){
	var quotaArr=$("input[id^='quota_option_']");
	var flag=true;
	var quotaN='';
	for(var i=0;i<quotaArr.length;i++){
		for(var j=0;j<quotaArr.length;j++){
			if(quotaArr[i].value==quotaArr[j].value && i !=j){
				flag=false;
				quotaN=quotaArr[i].value;
			}
		}
	}
	if(flag){
	   $('#optionForm').form('submit', {
			url : "saveEstOption.action",
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				alert("保存成功！");
				window.$("#editEstOption").dialog("close");
				window.$("#editEstQuota").dialog("close");
				window.editQuota('${cusEstDTO.cusEstFactorWeights.pid}');
			}
		}); 
	}
	else{
		alert("不能重复录入指标信息！");
	}
}

function addOption(){
var trIndex=$("#est_option tr:last").attr("name");
var trLength=$("#est_option tr").length;
trIndex++;
var str='<tr id="option_tr'+trIndex+'" name="'+trIndex+'" style="border:1px solid #999;">'+
       '<td>'+
     	'<input type="checkbox" name="isChecked"  class="isChecked" value="'+trIndex+'"/>'+
     	'<input type="hidden" name="options['+trIndex+'].status" value="1"/>'+
     	'<input type="hidden" name="options['+trIndex+'].cusEstQuota.pid" value="${cusEstDTO.cusEstQuota.pid}"/>'+
       '</td>'+
       '<td align="center">'+trLength+'</td>'+
		  '<td align="center">'+
		     '<input type="text" name="options['+trIndex+'].optionName" id="quota_option_'+trIndex+'"  class="text_style"  style="width:240px;" />'+
		  '</td>	'+
		  '<td align="center">'+
		     '<input type="text" name="options['+trIndex+'].score" class="text_style" style="width:100px;"/>'+
		  '</td>'+
		  '</td>'+
		  '<td align="center">'+
		     '<input type="text" name="options['+trIndex+'].remark" class="text_style" style="width:220px;"/>'+
		  '</td>'+	  
		 '</tr>';
	$("#est_option").append(str);
}
function removeOption(quotaIndex){
	var isChecked = document.getElementsByName("isChecked");
	var flag=false;
	var num=isChecked.length;
	 for(var i=num-1;i>=0;i--){
		 if(isChecked[i].checked){
			 $("#option_tr"+isChecked[i].value).remove();
			 flag=true;
		 }
	 }
	 if(flag==false){
		 alert("请选择一条数据删除！");
	 }
}







$(function(){
	setCombobox("factor_name","FACTOR_NAME","${cusEstDTO.cusEstFactorWeights.factorName}");
});

</script>
<div id="main_body" style="width:660px;float: left;">
<div id="cus_content" style="width:660px;">
<form id="optionForm" action="saveEstQuota.action" method="POST">
 		<table class="cus_table" style="min-width:660px;width:660px;">
			<tr>
			    <td class="align_right"  style="width:120px;">模板名称：</td>
				<td><input type="text"  class="text_style"  readonly="readonly" style="width:300px;" value="${cusEstDTO.cusEstTemplate.modelName }" /></td>  
			</tr>
			<tr>
				<td class="align_right" >要素名称：</td>
				<td>
					<select id="factor_name" class="easyui-combobox" readonly="readonly" style="width:300px;" /></td>  
			</tr>
			<tr>
				<td class="align_right" >指标名称：</td>
				<td>
					<input type="text"  class="text_style" readonly="readonly"  style="width:300px;" value="${cusEstDTO.cusEstQuota.quotaName }" /></td>  
			</tr>
		</table>
	
	    <table class="est_table" id="est_option"  style="min-width:660px;width:660px;">
       	  <tr style="border:1px solid #999;">
            <td>&nbsp;
             <!-- <input type="checkbox" name="chkAll" id="chkAll" class="isCheckeds" value=""/> -->
            </td>
            <td width="40px" align="center">序号</td>
            <td align="center">选项名称</td>
            <td align="center">分值</td>
            <td align="center">描述</td>
      	 </tr>
      	<c:if test="${empty cusEstDTO.options }" >
          <tr id="option_tr1" name="1" style="border:1px solid #999;">
              <td>
              	<input type="checkbox" name="isChecked"  class="isChecked" value="1"/>
              	<input type="hidden" name="options[1].status" value="1"/>
              	<input type="hidden" name="options[1].cusEstQuota.pid" value="${cusEstDTO.cusEstQuota.pid}"/>
              </td>
	          <td align="center">1</td>
	 		  <td align="center">
	 		     <input type="text" name="options[1].optionName" id="quota_option_1" class="text_style"  style="width:240px;" />
	 		  </td>	
	 		  <td align="center">
	 		     <input type="text" name="options[1].score" class="text_style"  style="width:100px;" />
	 		  </td>	
	 		  <td align="center">
	 		     <input type="text" name="options[1].remark" class="text_style" style="width:220px;"/>
	 		  </td>
          </tr>
          </c:if>
          <c:if test="${not empty cusEstDTO.options }" >
          	<c:forEach var="option" items="${cusEstDTO.options}" varStatus="status">
          		<tr id="option_tr${status.index }" name="${status.index }" style="border:1px solid #999;">
	              <td>
	              	<input type="checkbox" name="isChecked"  class="isChecked" value="${status.index }"/>
	              	<input type="hidden" name="options[${status.index }].status" value="1"/>
	              	<input type="hidden" name="options[${status.index }].pid" value="${option.pid }"/>
	              	<input type="hidden" name="options[${status.index }].cusEstQuota.pid" value="${cusEstDTO.cusEstQuota.pid}"/>
	              </td>
		          <td align="center">${status.index+1 }</td>
		 		  <td align="center">
		 		     <input type="text" name="options[${status.index }].optionName"  id="quota_option_${status.index}"  value="${option.optionName }" class="text_style"  style="width:240px;" />
		 		  </td>	
		 		  <td align="center">
		 		     <input type="text" name="options[${status.index }].score" value="${option.score }" class="text_style"  style="width:100px;" />
		 		  </td>	
		 		  <td align="center">
		 		     <input type="text" name="options[${status.index }].remark" value="${option.remark }"   class="text_style" style="width:220px;"/>
		 		  </td>		 		 
	          </tr>
          	</c:forEach>
          </c:if>
      	</table>
		<div style="text-align: center;padding-top:15px;">
            <input type="button" class="cus_save_btn" value="添加选项"  onclick="addOption();"/>&nbsp;&nbsp;&nbsp; 
            <input type="button" class="cus_save_btn" value="删除选项" onclick="removeOption();"/></div>
</form>	    
</div>
</div>