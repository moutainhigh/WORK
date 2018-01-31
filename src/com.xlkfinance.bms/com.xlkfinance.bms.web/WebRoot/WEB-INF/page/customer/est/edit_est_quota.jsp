<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
function saveQuota(){
		var quotaArr=$("input[id^='quota_Name_']");
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
	    $('#quotaForm').form('submit', {
			url : "saveEstQuota.action",
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				alert("保存成功！");
				var ret = eval("("+result+")");
				var factorId='${cusEstDTO.cusEstFactorWeights.pid}';
				window.$("#editEstQuota").dialog("close");
				window.editQuota(factorId);
				//window.dialogArguments.location.reload();
				//this.location='${basePath }customerController/editEstQuota.action?factorId=${cusEstDTO.cusEstFactorWeights.pid}';
			}
		}); 
		}
		else{
			alert("不能重复录入指标信息！");
		}
}

function addQuota(){
  var trIndex=$("#est_quota tr:last").attr("name");
  var trLength=$("#est_quota tr").length;
  trIndex++;
  var str='<tr id="quota_tr'+trIndex+'" name="'+trIndex+'" style="border:1px solid #999;">'+
          '<td>'+
        	'<input type="checkbox" name="isChecked"  class="isChecked" value="'+trIndex+'"/>'+
        	'<input type="hidden" name="quotas['+trIndex+'].status" value="1"/>'+
        	'<input type="hidden" name="quotas['+trIndex+'].cusEstFactorWeights.pid" value="${cusEstDTO.cusEstFactorWeights.pid}"/>'+
          '</td>'+
          '<td align="center">'+trLength+'</td>'+
 		  '<td align="center">'+
 		     '<input type="text" name="quotas['+trIndex+'].quotaName" id="quota_Name_'+trIndex+'" class="text_style"  style="width:220px;" />'+
 		  '</td>	'+
 		  '<td align="center">'+
 		     '<input type="text" name="quotas['+trIndex+'].remark" class="text_style" style="width:230px;"/>'+
 		  '</td>'+
 		  '<td align="center" style="width:50px;">&nbsp;'+
		  '</td>'+
 		  '<td  align="center"  style="width:150px;color:#ccc;">'+
 		     	'查看与维护指标选项'+
 		  '</td>'+
   		 '</tr>';
	$("#est_quota").append(str);
}
function removeQuota(quotaIndex){
	var isChecked = document.getElementsByName("isChecked");
	var num=isChecked.length;
	 for(var i=num-1;i>=0;i--){
		 if(isChecked[i].checked){
			 $("#quota_tr"+isChecked[i].value).remove();
			 flag=true;
		 }
	 }
	 if(flag==false){
		 alert("请选择一条数据删除！");
	 }
}





function editOption(quotaId){
$('<div id="editEstOption"/>').dialog({
		href : 'editEstOption.action?quotaId='+quotaId,
		width : 700,
		height : 400,
		modal : true,
		title : '新增指标选项信息',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				window.saveOption();
			}	
			},{
				text : '取消 ',
				iconCls : 'icon-cancel',
				handler : function() {
					$("#editEstOption").dialog("close");
					}
				} ],
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
		}
	});
}

$(function(){
	setCombobox("quota_factor_name","FACTOR_NAME","${cusEstDTO.cusEstFactorWeights.factorName}");
});
</script>
<div id="main_body" style="width:760px;float: left;">
<div id="cus_content" style="width:760px;">
<form id="quotaForm" action="saveEstQuota.action" method="POST">
 		<table class="cus_table" style="min-width:760px;width:760px;">
			<tr>
			    <td class="align_right"  style="width:120px;">模板名称：</td>
				<td><input type="text"  class="text_style"  readonly="readonly"  style="width:300px;" value="${cusEstDTO.cusEstTemplate.modelName }" /></td>  
			</tr>
			<tr>
				<td class="align_right" >要素名称：</td>
				<td>
					<select id="quota_factor_name" readonly="readonly" class="easyui-combobox"  style="width:300px;" /></td>  
			</tr>
		</table>
	
	    <table class="est_table" id="est_quota" style="min-width:760px;width:760px;">
       	  <tr style="border:1px solid #999;">
            <td>&nbsp;
             <!-- <input type="checkbox" name="chkAll" id="chkAll" class="isCheckeds" value=""/> -->
            </td>
            <td width="40px" align="center">序号</td>
            <td align="center">指标名称</td>
            <td align="center">描述</td>
            <td align="center">分值</td>
            <td align="center">操作</td>
      	 </tr>
      	<c:if test="${empty cusEstDTO.quotas }" >
          <tr id="quota_tr1" name="1" style="border:1px solid #999;">
              <td>
              	<input type="checkbox" name="isChecked"  class="isChecked" value="1"/>
              	<input type="hidden" name="quotas[1].status" value="1"/>
              	<input type="hidden" name="quotas[1].cusEstFactorWeights.pid" value="${cusEstDTO.cusEstFactorWeights.pid}"/>
              </td>
	          <td align="center">1</td>
	 		  <td align="center">
	 		     <input type="text" name="quotas[1].quotaName" id="quota_Name_1" class="text_style"  style="width:220px;" />
	 		  </td>	
	 		  <td align="center">
	 		     <input type="text" name="quotas[1].remark" class="text_style" style="width:230px;"/>
	 		  </td>
	 		  <td align="center" style="width:50px;">
	 		     &nbsp;
	 		  </td>
	 		  <td  align="center"  style="width:150px;color:#ccc;">
	 		     	查看与维护指标选项
	 		  </td>
          </tr>
          </c:if>
          <c:if test="${not empty cusEstDTO.quotas }" >
          	<c:forEach var="quota" items="${cusEstDTO.quotas}" varStatus="status">
          		<tr id="quota_tr${status.index }" name="${status.index }" style="border:1px solid #999;">
	              <td>
	              	<input type="checkbox" name="isChecked"  class="isChecked" value="${status.index }"/>
	              	<input type="hidden" name="quotas[${status.index }].status" value="1"/>
	              	<input type="hidden" name="quotas[${status.index }].pid" value="${quota.pid }"/>
	              	<input type="hidden" name="quotas[${status.index }].cusEstFactorWeights.pid" value="${cusEstDTO.cusEstFactorWeights.pid}"/>
	              </td>
		          <td align="center">${status.index+1 }</td>
		 		  <td align="center">
		 		     <input type="text" name="quotas[${status.index }].quotaName" id="quota_Name_${status.index}" value="${quota.quotaName }" class="text_style"  style="width:220px;" />
		 		  </td>	
		 		  <td align="center">
		 		     <input type="text" name="quotas[${status.index }].remark" value="${quota.remark }"   class="text_style" style="width:250px;"/>
		 		  </td>
		 		  <td align="center" style="width:50px;">
		 		    ${quota.totalScore }
		 		  </td>
		 		  <td  align="center"  style="width:150px;">
		 		     	<a href="#" onclick="editOption('${quota.pid}')" style="color:#72ACF3;"> 查看与维护指标选项</a>
		 		  </td>
	          </tr>
          	</c:forEach>
          </c:if>
      	</table>
 		<div style="text-align: center;padding-top:15px;">
            <input type="button" class="cus_save_btn" value="添加指标"  onclick="addQuota();"/>&nbsp;&nbsp;&nbsp; 
            <input type="button" class="cus_save_btn" value="删除指标" onclick="removeQuota();"/></div>
</form>	    
</div>
</div>