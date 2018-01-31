<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
	function saveEstInfo(){
		   $('#estInfo').form('submit', {
				url : "saveEstInfo.action",
				onSubmit : function() {
					return checkEstForm();
					return $(this).form('validate'); 
				},
				success : function(result) {
					alert("保存成功！");
					cancelEstInfo();
					
				},error : function(result){
					alert("保存失败！");
				}
			}); 
	}
	
	function checkEstForm(){
		var acctId=$("#cus_id").val();
		if(acctId==''){
			alert("请选择客户信息！");
			return false;
		}
	}
function cancelEstInfo(){
		var cusType='${cusType}';
		var flag='${flag}';
		if(flag=='1'){
			window.location='${basePath }customerController/listEstInfo.action';
		}else{
			if(cusType=='1'){
				window.location='${basePath }customerController/listPerEst.action?acctId=${acctId}';
			}else if(cusType=='2'){
				window.location='${basePath }customerController/listComEst.action?acctId=${acctId}&comId=${comId}';
			}else{
				window.location='${basePath }customerController/listEstInfo.action';
			}
		}
		
		
}


function setTemplateCombobox(inputId,selVal,modelType){
	$('#'+inputId).combobox({    
		url:'${basePath }customerController/getTemplateName.action?modelType='+modelType,    
	    valueField:'pid',    
	    textField:'modelName',
	    onLoadSuccess: function(rec){
	    	if(selVal!=''){
	    		$("#"+inputId).combobox('setValue',selVal);
	    	}
        }
	});  
}

function nextCusEst(){
	var templateId=$('#cre_model_type').combobox('getValue');
	if(templateId==''){
		alert("请选择评估模板！");
	}else{
		loadTemplate(templateId);
	}
	
}
function loadTemplate(templateId){
	
	$.ajax({
		type:			'GET',
		url:			'makeTemplate.action?templateId='+templateId+'&estId=${cusEstInfo.pid}',
		cache:			false,
		dataType:		'html',
		success: function(data){
			$('#est_template').html(data);
			$("#next_btn").hide();
			$("#save_btn").show();
			$("#cus_est_info").hide();
			$("#cus_template").show();
			
		},
		error: function(data){
			$('#est_template').html("模板生产失败！");
		}
	});

}

//选择客户信息
function xzCusInfo(){
	var ct=$('#cus_type').combobox('getValue');
	if(ct==1){
			$('<div id="xzPerCus"/>').dialog({
					href : 'xzPerCus.action',
					width : 700,
					height : 400,
					modal : true,
					title : '选择个人客户',  
					buttons : [ {
						text : '确认',
						iconCls : 'icon-save',
						handler : function() {
							window.savePerCus();
						}	
						},{
							text : '取消 ',
							iconCls : 'icon-cancel',
							handler : function() {
								$("#xzPerCus").dialog("close");
								}
							} ],
					onClose : function() {
						$(this).dialog('destroy');
					},
					onLoad : function() {
					}
				});
	}else {
			$('<div id="xzComCus"/>').dialog({
					href : 'xzComCus.action',
					width : 700,
					height : 400,
					modal : true,
					title : '选择企业客户',
					buttons : [ {
						text : '确认',
						iconCls : 'icon-save',
						handler : function() {
							window.saveComCus();
						}	
						},{
							text : '取消 ',
							iconCls : 'icon-cancel',
							handler : function() {
								$("#xzComCus").dialog("close");
								}
							} ],
					onClose : function() {
						$(this).dialog('destroy');
					},
					onLoad : function() {
					}
				});
	}
}

$(function(){
	var ctype='${cusType}';
	if(ctype==''){
		ctype="${cusEstInfo.cusType }";
	}
	setCombobox("cus_type","CUS_TYPE",ctype);
	setTemplateCombobox("cre_model_type","${cusEstInfo.cusEstTemplate.pid}",ctype);
	
	$("#cus_type").combobox({  
        onChange:function(){  
        	var modelType=$("#cus_type").combobox('getValue');
    		setTemplateCombobox("cre_model_type","${cusEstInfo.cusEstTemplate.pid}",modelType);
        }
    });
});

</script>

<!-- <body class="easyui-layout"> -->
<!-- 	<div data-options="region:'center',border:false"> -->
<!-- 		<div id="scroll-bar-div"> -->
<div id="main_body">
<div id="cus_content">

<div style="width:780px;float:left;margin-top:8px;">
<form id="estInfo" action="saveEstInfo.action" method="POST">
   	<input type="hidden" name="pid" value="${cusEstInfo.pid}" />
   	<input type="hidden" id="cus_id" name="cusAcct.pid" value="${acctId }" />
   	<input type="hidden" name="estPerson" value="${currUser.pid }" />
   	<input type="hidden" name="status" value="1" />
	<table class="cus_table" id="cus_est_info">
		<tr style="height:25px;">
			<th colspan="3">资信评估信息</th>
		</tr>
		
		<tr>
		    <td class="align_right"  style="width:120px;">客户类型：</td>
<%-- 		    =======${cusType}========= --%>
			<td><select name="cusType" id="cus_type" class="easyui-combobox"  <c:if test="${not empty cusType }">readonly="readonly"</c:if>  style="width:150px;"></select>
			
			</td>  
			<td><c:if test="${empty cusType }"><input type="button" class="cus_save_btn" value="选择客户"  onclick="xzCusInfo();"/></c:if></td>
		</tr>
		<tr>
			<td class="align_right" >客户名称：</td>
			<td colspan="2" id="cus_name">
					${acctName}
			</td>  
		</tr>
		<tr>
			<td class="align_right" >资信评估模板：</td>
			<td colspan="2"  id="cus_name" height="30">
				<select name="cusEstTemplate.pid" id="cre_model_type" class="easyui-combobox" <c:if test="${not empty cusEstInfo }">readonly="readonly"</c:if>  style="width:150px;">
			</td>  
		</tr>
	</table>
	
	<div id="est_template" style="">
	    
	   
   </div>
         <div style="text-align: center;padding-top:15px;">
            <input type="button" class="cus_save_btn" id="next_btn" value="下一步"  onclick="nextCusEst();"/>
            <input type="button" class="cus_save_btn" id="save_btn" style="display:none;" value="保     存" onclick="saveEstInfo();"/>
            <input type="button" class="cus_save_btn" value="取     消" onclick="cancelEstInfo();"/></div>
	</form>	
	</div>    
</div>
</div>
<!-- 	</div> -->
<!-- 	</div> -->
<!-- 	</body> -->