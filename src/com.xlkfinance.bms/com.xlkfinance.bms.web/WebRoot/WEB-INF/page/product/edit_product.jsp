<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script charset="uft-8" src="${ctx}/p/xlkfinance/plug-in/ueditor/ueditor.config.js"></script>
<script charset="uft-8" src="${ctx}/p/xlkfinance/plug-in/ueditor/ueditor.all.js"></script>
<script charset="uft-8" src="${ctx}/p/xlkfinance/plug-in/ueditor/lang/zh-cn/zh-cn.js"></script>
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
<script type="text/javascript">
$(document).ready(function() {
	var pid = ${product.pid};
	if(pid > 0){
		 $(function(){
	         $('#productNumber').textbox({    
	              required:true,    
	              multiple:true,
	              disabled:true  
	        });  
	   });
	}
	
});

function cancelProduct(){ 
	//parent.removePanel();
	//获取tabs对象
	var myObj = parent.$('#layout_center_tabs').tabs('getTab','产品管理');
	if(myObj){
	//获取iframe的URL
	var url = myObj[0].innerHTML;
	//打开数据
	parent.layout_center_addTabFun({
		title : '产品管理', //tab的名称
		closable : true, //是否显示关闭按钮
		content : url,//加载链接
		falg:true
	});
	}
	//var tital = "修改个人客户"
	var pid='${product.pid}';
	if(pid>0){
		parent.$('#layout_center_tabs').tabs('close','修改产品');
	}else{
		parent.$('#layout_center_tabs').tabs('close','新增产品');
	}
}

function saveProductInfo(){
	$('#productInfoForm').form('submit', {
		url : "saveProduct.action",
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			var msg = ret.header["msg"];
			alert(msg);
				var templateId=ret.header["pid"];
				cancelProduct();			
		},error : function(result){
			alert("保存失败！");
		}
	});
}

$(function(){
	var cityId = ${product.cityId};
	$('#city_id').combobox({
		url:'${basePath}productController/getCityLists.action',    
	    valueField:'id',
	    textField:'name',
	    onLoadSuccess: function(rec){
	    	if(cityId >0){
	    		$('#city_id').combobox('setValue',cityId);
	    	}else{
	    		$('#city_id').combobox('setValue',-1);
	    	}
	    	
        } 
	});
	var loanWorkProcessId = '${product.loanWorkProcessId}';
	if(loanWorkProcessId == null || loanWorkProcessId ==''){
		setCombobox3("loanWorkProcessId", "BEFORE_LOAN_PROCESS", -1);
	}else{
		setCombobox3("loanWorkProcessId", "BEFORE_LOAN_PROCESS", loanWorkProcessId);
	}
	
	var bizHandleWorkProcessId = '${product.bizHandleWorkProcessId}';
	if(bizHandleWorkProcessId == null || bizHandleWorkProcessId ==''){
		setCombobox3("bizHandleWorkProcessId", "HANDLE_WORK_PROCESS", -1);
	}else{
		setCombobox3("bizHandleWorkProcessId", "HANDLE_WORK_PROCESS", bizHandleWorkProcessId);
	}
	var productType = '${product.productType}';
	if(productType == null || productType ==''){
		setCombobox3("productType", "PRODUCT_TYPE", -1);
	}else{
		setCombobox3("productType", "PRODUCT_TYPE", productType);
	}
	
});

</script>

<div id="main_body">
<div id="cus_content">
<jsp:include page="product_tab.jsp">
<jsp:param value="1" name="tab"/>
</jsp:include>
<div >
<form id="productInfoForm" action="<%=basePath%>productController/saveProduct.action" method="POST">
	<input type="hidden" name="status" value="${product.status }"/>
	<input type="hidden" name="pid" value="${product.pid }"/>
	
	<table class="cus_table" style="margin: 0;">
		<tr>
			<td class="label_right1"><span class="cus_red">*</span>产品类型：</td>
			<td>
				<input name="productType" id="productType" class="easyui-combobox" data-options="validType:'selrequired'" missingMessage="请选项产品类型!"/>
			</td>
			<td class="label_right1"><span class="cus_red">*</span>产品编号：</td>
			<td><input class="easyui-textbox" data-options="required:true,validType:'length[1,50]'"  id= "productNumber" name="productNumber" value="${product.productNumber}"  style="width:150px;"/></td>
			
			<td class="label_right1"><span class="cus_red">*</span>地区：</td>
			<td>
				<select id="city_id" class="easyui-combobox" name="cityId" editable="false" data-options="validType:'selrequired'" style="width:150px;" />
			</td>
		</tr>
		<tr>
			<td class="label_right1"><span class="cus_red">*</span>产品名称：</td>
			<td><input class="easyui-textbox"  name="productName" value="${product.productName}" data-options="required:true,validType:'length[1,50]'"  style="width:150px;"/> </td>
			<td class="label_right1"><span class="cus_red">*</span>交易类型：</td>
		<td>
			<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" name="tradeType" style="width:150px;">
				<option value="" >--请选择--</option>
				<option value=13755 <c:if test="${product.tradeType== '13755' }">selected </c:if>>交易</option>
				<option value=13756 <c:if test="${product.tradeType== '13756' }">selected </c:if>>非交易</option>
			</select>
		</td>
		<td class="label_right1"><span class="cus_red">*</span>是否赎楼：</td>
		<td>
			<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" name="isForeclosure" style="width:150px;">
			     <option value="" >--请选择--</option>
				<option value=1 <c:if test="${product.isForeclosure== '1'}">selected </c:if>>是</option>
				<option value=2 <c:if test="${product.isForeclosure== '2'}">selected </c:if>>否</option>
			</select>
		</td>
	</tr>
    <tr>
    <td class="label_right1"><span class="cus_red">*</span>贷前流程：</td>
		<td>
			<input name="loanWorkProcessId" id="loanWorkProcessId" class="easyui-combobox" data-options="validType:'selrequired'" missingMessage="请选项贷前流程!"/>
		</td>
    <td class="label_right1"><span class="cus_red">*</span>业务办理流程：</td>
   <td>
    	<input name="bizHandleWorkProcessId" id="bizHandleWorkProcessId" class="easyui-combobox" data-options="validType:'selrequired'" missingMessage="请选项业务办理流程!"/>
   </td>
    
    </tr>
	</table>
  
     <div class="pb10" style="text-align:left;padding-top:10px;width:1039px;">&nbsp;&nbsp;
     	<input type="button" class="cus_save_btn" name="save" value="保     存" onclick="saveProductInfo();"/>&nbsp;&nbsp;&nbsp; 
        <input type="button" class="cus_save_btn" id="can_cle" name="cancel" value="取     消" onclick="cancelProduct();"/>
     </div>

</form>
 </div>	    
</div>
</div>	   
