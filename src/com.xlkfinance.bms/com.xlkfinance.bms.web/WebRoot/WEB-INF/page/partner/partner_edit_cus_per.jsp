<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	request.setAttribute("basePath", basePath);
%>
 
 <style type="text/css">
#baseInfo {
		width: 500px;
}
#baseInfo label {
	width: 250px;
}
#baseInfo label.error, #baseInfo input.submit {
	margin-left: 253px;
}
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/common.js?v=${v}" charset="utf-8"></script>
<script type="text/javascript">

	var BASE_PATH = "${basePath}";
	$(function(){
		//绑定下拉框的值
		setCombobox("education","EDUCATION","${cusPerBaseDTO.cusPerson.education}");
  		setCombobox("nation","NATION","${cusPerBaseDTO.cusPerson.nation}");
		setCombobox("unitNature","UNIT_NATURE","${cusPerBaseDTO.cusPerson.unitNature}");
		setCombobox("occName","OCC_NAME","${cusPerBaseDTO.cusPerson.occName}");
		setCombobox("trade","TRADE","${cusPerBaseDTO.cusPerson.trade}");  
		
		var flag = $("#flag").val();
		
		
		if(flag == '3'){
			/* $('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('disabled', 'disabled');
			$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('readOnly', 'readOnly');
			$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').addClass('l-btn-disabled');
			$('.panel-body .easyui-linkbutton:not(.download)').removeAttr('onclick');
			$('.cus_save_btn').removeAttr('onclick');
			$('.cus_save_btn').attr('disabled', 'disabled');
			$('.cus_save_btn').addClass('l-btn-disabled');
			 */
			$("#cus_save_btn").hide();
		}
		
	});
	
	//保存
	function saveBaseInfo(){
		
		var pid = $("#cusPerson.pid").val();
		
		var certAddr = $('#certAddr').val();
		var liveCode = $('#liveCode').val();
		var education=$('#education').combobox('getValue');
		var workUnit=$('#workUnit').val();
		var unit_nature=$('#unitNature').combobox('getValue');
		var occupation=$('#occupation').val();
		var workService=$('#workService').val();
		var occ_name=$('#occName').combobox('getValue');
		var entryTime=$('#entryTime').combobox('getValue');
		var monthIncome=$('#monthIncome').val();
		var trade=$('#trade').combobox('getValue');
		
		if(certAddr == "" ){
			$.messager.alert("操作提示","请填写身份证发证机关所在地","error"); 
			$('#certAddr').focus();
			return false;
		}else if(liveCode == "" ){
			$.messager.alert("操作提示","请填写居住地址邮编","error"); 
			$('#liveCode').focus();
			return false;
		}else if(education == "" || education == -1 ){
			$.messager.alert("操作提示","请选择最高学历","error"); 
			$('#education').focus();
			return false;
		}else if(workUnit == ""){
			$.messager.alert("操作提示","请填写工作单位","error"); 
			$('#workUnit').focus();
			return false;
		}else if(unit_nature == "" || unit_nature == -1 ){
			$.messager.alert("操作提示","请选择单位性质","error"); 
			$('#unit_nature').focus();
			return false;
		}else if(occupation == ""){
			$.messager.alert("操作提示","请填写职业","error"); 
			$('#occupation').focus();
			return false;
		}else if(workService == ""){
			$.messager.alert("操作提示","请填写职务","error"); 
			$('#workService').focus();
			return false;
		}else if(occ_name == "" || occ_name == -1 ){
			$.messager.alert("操作提示","请选择职称","error"); 
			$('#occ_name').focus();
			return false;
		}else if(entryTime == ""){
			$.messager.alert("操作提示","请选择入职时间","error"); 
			$('#entryTime').focus();
			return false;
		}else if(monthIncome == ""){
			$.messager.alert("操作提示","请填写月收入","error"); 
			$('#monthIncome').focus();
			return false;
		}else if(trade == "" || trade == -1 ){
			$.messager.alert("操作提示","请选择所属行业","error"); 
			$('#trade').focus();
			return false;
		} 
		 
		$('#baseInfoForm').form('submit', {
			url : "saveCusPer.action",
			success : function(result) {
				
				$.messager.alert('操作提示',"保存成功！");
				var ret = eval("("+result+")");
				
			},error : function(result){
				$.messager.alert('操作提示',"保存失败！",'error');
			}
		}); 
 
	}
	
	
	
	
    
</script>
<div id="main_body">
 <form id="baseInfoForm" action="{basePath}customerController/saveCusPer" method="POST">
 
 	<input type="hidden" id="flag" value="${flag}"/>
 	<input type="hidden" id="cusPerson.pid" name="pid" value="${cusPerBaseDTO.cusPerson.pid}"/>
 	<input type="hidden" id="cusPerson.acctId" name="cusAcct.pid" value="${cusPerBaseDTO.cusAcct.pid }"/>
 
	<div class=" easyui-panel" title="填写个人教育/工作信息" data-options="collapsible:true">
		<table class="cus_table2">
			
		    <tr>
		     <td class="align_right"><font color="red">*</font>身份证发证机关所在地：</td>
		     <td><input id="certAddr" name="certAddr" type="text" class="text_style easyui-textbox"  value="${cusPerBaseDTO.cusPerson.certAddr}" /></td>
		     <td class="align_right"><font color="red">*</font>居住地址邮编：</td>
		     <td><input id="liveCode" name="liveCode" type="text" class="text_style easyui-textbox"  value="${cusPerBaseDTO.cusPerson.liveCode}" /></td>
		   </tr>
		   <tr>
		      <td class="align_right" style="width:90px;"><font color="red">*</font>最高学历：</td>
		      <td>
				<select id="education" name="education" editable="false" class="easyui-combobox" data-options="required:true" style="width:220px;" />
			  </td>
		   </tr>
		    <tr>
		     <td class="align_right"><font color="red">*</font>工作单位：</td>
		     <td><input id="workUnit" name="workUnit" type="text" class="text_style easyui-textbox"  value="${cusPerBaseDTO.cusPerson.workUnit}" /></td>
		     <td class="align_right"><font color="red">*</font>单位性质：</td>
		     <td>
				<select id="unitNature"  name="unitNature" editable="false" class="easyui-combobox" data-options="required:true" style="width:220px;" />
		     </td>
		   </tr>
	 
	 
		   <tr>
		       <td class="align_right" ><font color="red">*</font>职业：</td>
		       <td>
		       		<input id="occupation" name="occupation" type="text" class="text_style"  value="${cusPerBaseDTO.cusPerson.occupation}" data-options="required:true"/>
		       </td>
		       <td class="align_right"><font color="red">*</font>职务：</td>
		       <td>
		       		<input id="workService" name="workService" type="text" class="text_style" value="${cusPerBaseDTO.cusPerson.workService}" data-options="required:true"/>
			   </td>
		   </tr>
		     <tr>
		       <td class="align_right" ><font color="red">*</font>职称：</td>
		       <td>
				 <select id="occName" name="occName" editable="false" class="easyui-combobox"  data-options="required:true" style="width:220px;"  />
		       </td>
		   </tr>
		     <tr>
		       <td class="align_right" ><font color="red">*</font>入职时间：</td>
		       <td>
				 <input type="text" id="entryTime" name="entryTime" value="${cusPerBaseDTO.cusPerson.entryTime}" editable="false" class="text_style easyui-datebox" editable="false"  data-options="required:true" style="width:220px;"  />
		       </td>
		   </tr>
		   <tr>
		     <td class="align_right" ><font color="red">*</font>月收入：</td>
		     <td><input id="monthIncome" name="monthIncome" type="text" class="text_style easyui-numberbox" precision="2" groupSeparator=","   value="${cusPerBaseDTO.cusPerson.monthIncome}" data-options="required:true"/>&nbsp;万元</td>
		     <td class="align_right" ><font color="red">*</font>所属行业：</td>
		     <td>
				<select id="trade" name="trade" editable="false" class="easyui-combobox"  data-options="required:true" style="width:220px;" />
		     </td>
		   </tr>
			 
		</table>
		
		</div>
 	</form>
	 </div>
		<div class="pb10" style="text-align: center; padding-top: 15px;">
			<input type="button" id="cus_save_btn" class="cus_save_btn" name="save" value="保     存" onclick="saveBaseInfo();"/>&nbsp;&nbsp;&nbsp; 
		</div>	    
	</div>
 </div>
 
 
 
	
