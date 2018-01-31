<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
	function saveTemplate(){
		   $('#estTemplate').form('submit', {
				url : "saveEstTemplate.action",
				onSubmit : function() {
					if(checkFactorForm()){
						return $(this).form('validate'); 
					}else{
						return false;
					}
				},
				success : function(result) {
					alert("保存成功！");
					$("#grid").datagrid('clearChecked');
					var ret = eval("("+result+")");
					var templateId=ret.header["templateId"];
					window.location='${basePath }customerController/editEstTemplate.action?pid='+templateId;
					
				},error : function(result){
					alert("保存失败！");
				}
			}); 
	}
	
 function checkFactorForm(){
	var arrWeights=$("input[id^='factor_weight_']");
	var total=0;
	for(var i=0;i<arrWeights.length;i++){
		var weights=parseFloat(arrWeights[i].value);
		total+=weights;
	}
	if(total!=100){
		alert("权重值必须等于100！");
		return false;
	}
	return true;
} 
function cancelTemplate(){
		window.location='${basePath }customerController/listEstTemplate.action';
}

function addFactor(quotaIndex){
	  var trIndex=$("#est_factor tr:last").attr("name");
	  var trLength=$("#est_factor tr").length;
	  trIndex++;
      var str='<tr id="factor_tr'+trIndex+'" name="'+trIndex+'" style="border:1px solid #999;">'+
              '<td>'+
            	'<input type="checkbox" name="isChecked"  class="isChecked" value="'+trIndex+'"/>'+
            	'<input type="hidden" name="factors['+trIndex+'].status" value="1"/>'+
              '</td>'+
	          '<td align="center">'+trLength+'</td>'+
	 		  '<td align="center">'+
	 		     '<select name="factors['+trIndex+'].factorName" id="factor_name_'+trIndex+'" data-options="validType:\'selrequired\'" class="easyui-combobox"  style="width:150px;" />'+
	 		  '</td>	'+
	 		   '<td align="center">'+
	 		     '<input type="text" id="factor_weight_'+trIndex+'" name="factors['+trIndex+'].weight" data-options="required:true,validType:\'number\'" class="text_style easyui-validatebox" style="width:100px;"/>'+
	 		  '</td>'+
	 		  '<td align="center">'+
	 		     '<input type="text" name="factors['+trIndex+'].remark" class="text_style" style="width:260px;"/>'+
	 		  '</td>'+
	 		  '<td  align="center"  style="width:150px;color:#ccc">'+
	 		     	'查看与维护指标'+
	 		  '</td>'+
       		 '</tr>';
	$("#est_factor").append(str);
	setCombobox("factor_name_"+trIndex,"FACTOR_NAME","");
}
function removeFactor(quotaIndex){
		var isChecked = document.getElementsByName("isChecked");
		var flag=false;
		var num=isChecked.length;
		for(var i=num-1;i>=0;i--){
			if(isChecked[i].checked){
				 $("#factor_tr"+isChecked[i].value).remove();
				 flag=true;
			}
		}
		if(flag==false){
			alert("请选择一条数据删除！");
		} 
 }


$(function(){
	setCombobox("cre_model_type","CUS_TYPE","${cusEstTemplate.modelType}");
	
	setCombobox("factor_name_1","FACTOR_NAME","");
	<c:forEach var="factor" items="${cusEstTemplate.factors}" varStatus="status">
	setCombobox("factor_name_${status.index}","FACTOR_NAME","${factor.factorName}");
	</c:forEach>
});


function editQuota(factorId){
	//addTab('${basePath}customerController/editEstQuota.action?factorId='+factorId,"维护指标信息");
	 $('<div id="editEstQuota"/>').dialog({
		href : '${basePath}customerController/editEstQuota.action?factorId='+factorId,
		width : 800,
		height : 450,
		modal : true,
		title : '新增指标信息',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				window.saveQuota();
			}	
			},{
				text : '取消 ',
				iconCls : 'icon-cancel',
				handler : function() {
					$("#editEstQuota").dialog("close");
					}
				} ],
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
		}
	}); 
}
</script>


<div id="main_body">
<div id="cus_content">

<div style="width:780px;float:left">
<form id="estTemplate" action="saveEstTemplate.action" method="POST">
   	<input type="hidden" name="pid" value="${cusEstTemplate.pid}" />
   	<input type="hidden" name="status" value="1" />
	<table class="cus_table" >
		<tr style="height:25px;">
			<th colspan="4">资信评估模板信息</th>
		</tr>
		
		<tr>
		    <td class="align_right"  style="width:120px;"><span class="cus_red">*</span>模板名称：</td>
			<td><input type="text"  class="text_style easyui-validatebox" data-options="required:true"  name="modelName" style="width:300px;" value="${cusEstTemplate.modelName}" /></td>  
			<td class="align_right" style="width:120px;"><span class="cus_red">*</span>评估模板类型：</td>
			<td>
				<select name="modelType" id="cre_model_type" data-options="validType:'selrequired'" class="easyui-combobox"  style="width:150px;" />
			</td>  
		</tr>
		<tr>
			<td class="align_right" >描述：</td>
			<td colspan="3" style="height:80px;">
				<textarea rows="4" style="width:608px;"  name="remark">${cusEstTemplate.remark}</textarea></td>  
		</tr>
	</table>
	
	<div id="est_template">
	    <table class="cus_table" id="est_factor">
   		   
       	  <tr style="border:1px solid #999;">
            <td>&nbsp;
             <!-- <input type="checkbox" name="chkAll" id="chkAll" class="isCheckeds" value=""/> -->
            </td>
            <td width="40px" align="center">序号</td>
            <td align="center">要素名称</td>
            <td align="center">权重%</td>
            <td align="center">描述</td>
            <td align="center">操作</td>
      	 </tr>
      	<c:if test="${empty cusEstTemplate.factors }" >
          <tr id="factor_tr1" name="1" style="border:1px solid #999;">
              <td>
              	<input type="checkbox" name="isChecked"  class="isChecked" value="1"/>
              	<input type="hidden" name="factors[1].status" value="1"/>
              </td>
	          <td align="center">1</td>
	 		  <td align="center">
	 		     <select name="factors[1].factorName" id="factor_name_1" data-options="validType:'selrequired'" class="easyui-combobox"  style="width:150px;" />
	 		  </td>	
	 		   <td align="center">
	 		     <input type="text" name="factors[1].weight" id="factor_weight_1" data-options="required:true,validType:'number'" class="text_style easyui-validatebox" style="width:100px;"/>
	 		  </td>
	 		  <td align="center">
	 		     <input type="text" name="factors[1].remark" class="text_style" style="width:260px;"/>
	 		  </td>
	 		  <td  align="center"  style="width:150px;color:#ccc;">
	 		     	查看与维护指标
	 		  </td>
          </tr>
          </c:if>
          <c:if test="${not empty cusEstTemplate.factors }" >
          	<c:forEach var="factor" items="${cusEstTemplate.factors}" varStatus="status">
          		<tr id="factor_tr${status.index }" name="${status.index }" style="border:1px solid #999;">
	              <td>
	              	<input type="checkbox" name="isChecked"  class="isChecked" value="${status.index }"/>
	              	<input type="hidden" name="factors[${status.index }].status" value="1"/>
	              	<input type="hidden" name="factors[${status.index }].pid" value="${factor.pid }"/>
	              	<input type="hidden" name="factors[${status.index }].cusEstTemplate.pid" value="${factor.cusEstTemplate.pid }"/>
	              </td>
		          <td align="center">${status.index+1 }</td>
		 		  <td align="center">
		 		     <select name="factors[${status.index }].factorName" id="factor_name_${status.index }" data-options="required:true" class="easyui-combobox"  style="width:150px;" />
		 		  </td>	
		 		   <td align="center">
		 		     <input type="text" name="factors[${status.index }].weight" id="factor_weight_${status.index }" value="${factor.weight }" data-options="required:true,validType:'number'" class="text_style easyui-validatebox" style="width:100px;"/>
		 		  </td>
		 		  <td align="center">
		 		     <input type="text" name="factors[${status.index }].remark" value="${factor.remark }"   class="text_style" style="width:260px;"/>
		 		  </td>
		 		  <td  align="center"  style="width:150px;">
		 		     	<a href="#" onclick="editQuota('${factor.pid}')" style="color:#72ACF3;" > 查看与维护指标</a>
		 		  </td>
	          </tr>
          	</c:forEach>
          </c:if>
      	</table>
	   
   </div>
         <div style="text-align: center;padding-top:15px;">
            <input type="button" class="cus_save_btn" value="添加要素"  onclick="addFactor();"/>&nbsp;&nbsp;&nbsp; 
            <input type="button" class="cus_save_btn" value="删除要素" onclick="removeFactor();"/>&nbsp;&nbsp;&nbsp; 
            <input type="button" class="cus_save_btn" value="保     存" onclick="saveTemplate();"/>&nbsp;&nbsp;&nbsp; 
            <input type="button" class="cus_save_btn" value="取     消" onclick="cancelTemplate();"/></div>
	</form>	
	</div>    
</div>
</div>
	