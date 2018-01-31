<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<%@ include file="/common.jsp"%>
<title>新增广告</title>
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
function cancelAdvPic(){ 
	//parent.removePanel();
	//获取tabs对象
	var myObj = parent.$('#layout_center_tabs').tabs('getTab','广告管理');
	if(myObj){
	//获取iframe的URL
	var url = myObj[0].innerHTML;
	//打开数据
	parent.layout_center_addTabFun({
		title : '广告管理', //tab的名称
		closable : true, //是否显示关闭按钮
		content : url,//加载链接
		falg:true
	});
	}
	//var tital = "修改个人客户"
	var pid='${advPic.pid}';
	if(pid>0){
		parent.$('#layout_center_tabs').tabs('close','修改广告');
	}else{
		parent.$('#layout_center_tabs').tabs('close','新增广告');
	}
}
//删除文件
function delFile(){
	var srcurl="${ctx}/p/xlkfinance/images/u53.png";
	$("#picture_url_img").attr("src",srcurl);
	$("#picture_url").val("");
};
function saveAdvPic(){
	var picture_url = $("#picture_url").val();
	if(picture_url == null ||picture_url == '' ){
		$.messager.alert('操作提示',"图片不能为空！");
		return;
	}
	$('#advPicInfoForm').form('submit', {
		url : "saveAdvPic.action",
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$("#pid").val(ret.header["pid"]);
				$.messager.alert('操作提示',"保存成功!",'info');
				cancelAdvPic();
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
		},error : function(result){
			alert("保存失败！");
		}
	});
}

$(function(){
	
	var cityId = ${advPic.orgId};
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
	
	$('#url').textbox({
		  onChange: function(value){
			 var flag = IsURL(value);
			 /* if(!flag){
				 alert("url错误，请输入合法的url地址！");
				 $('#url').textbox("setValue","");
			 } */
		  }
		});
	
});


function IsURL (str_url) {
	if(str_url == null || str_url == ''){
		return true;
	}
	var strRegex = '^((https|http|ftp|rtsp|mms)?://)' 
	+ '?(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?' //ftp的user@ 
	+ '(([0-9]{1,3}.){3}[0-9]{1,3}' // IP形式的URL- 199.194.52.184 
	+ '|' // 允许IP和DOMAIN（域名） 
	+ '([0-9a-z_!~*\'()-]+.)*' // 域名- www. 
	+ '([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].' // 二级域名 
	+ '[a-z]{2,6})' // first level domain- .com or .museum 
	+ '(:[0-9]{1,4})?' // 端口- :80 
	+ '((/?)|' // a slash isn't required if there is no file name 
	+ '(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$'; 
	var re=new RegExp(strRegex); 
	//re.test() 
	if (re.test(str_url)) { 
	return true; 
	} else { 
	return false; 
	} 
	} 
</script>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="新增广告" id="tab1" style="padding: 10px;">
<form id="advPicInfoForm" action="<%=basePath%>sysAdvPicController/saveAdvPic.action" method="POST">
	<input type="hidden" name="status" value="${advPic.status }"/>
	<input type="hidden" id="pid" name="pid" value="${advPic.pid }"/>
	
	<table class="cus_table">
		<tr>
			<td class="label_right"><span class="cus_red">*</span>广告标题：</td>
			<td>
				<input class="easyui-textbox" data-options="required:true,validType:'length[1,20]'" name="title" value="${advPic.title}"  style="width:150px;"/
			</td>
			<td class="label_right">广告链接：</td>
			<td>
				<input class="easyui-textbox" id="url" data-options="validType:['length[0,150]','url']" name="url" value="${advPic.url}"  style="width:150px;"/>
			</td>
			<td class="label_right"><span class="cus_red">*</span>地区：</td>
			<td>
				<select id="city_id" class="easyui-combobox" name="orgId" editable="false" data-options="validType:'selrequired'" style="width:150px;" />
			</td>
			<td class="label_right"><span class="cus_red">*</span>显示顺序：</td>
			<td>
				<select class="select_style easyui-combobox" required="true" data-options="validType:'selrequired'" editable="false" name="orderIndex" style="width:129px;">
					<option value="-1">--请选择--</option>
					<option value="1" <c:if test="${advPic.orderIndex==1 }">selected </c:if>>1</option>
					<option value="2" <c:if test="${advPic.orderIndex==2 }">selected </c:if>>2</option>
					<option value="3" <c:if test="${advPic.orderIndex==3 }">selected </c:if>>3</option>
					<option value="4" <c:if test="${advPic.orderIndex==4 }">selected </c:if>>4</option>
					<option value="5" <c:if test="${advPic.orderIndex==5 }">selected </c:if>>5</option>
					<option value="6" <c:if test="${advPic.orderIndex==6 }">selected </c:if>>6</option>
					<option value="7" <c:if test="${advPic.orderIndex==7 }">selected </c:if>>7</option>
					<option value="8" <c:if test="${advPic.orderIndex==8 }">selected </c:if>>8</option>
					<option value="9" <c:if test="${advPic.orderIndex==9 }">selected </c:if>>9</option>
					<option value="10" <c:if test="${advPic.orderIndex==10 }">selected </c:if>>10</option>
					<option value="11" <c:if test="${advPic.orderIndex==11 }">selected </c:if>>11</option>
					<option value="12" <c:if test="${advPic.orderIndex==12 }">selected </c:if>>12</option>
					<option value="13" <c:if test="${advPic.orderIndex==13 }">selected </c:if>>13</option>
					<option value="14" <c:if test="${advPic.orderIndex==14 }">selected </c:if>>14</option>
					<option value="15" <c:if test="${advPic.orderIndex==15 }">selected </c:if>>15</option>
				</select>
			</td>
		</tr>
		<tr>
	  		<td style="width:40px; text-align: right;">广告内容：</td>
	    	<td colspan="10">
	    		<input name="content" id="content" class="easyui-textbox" value="${advPic.content}" style="width:65%;height:120px" data-options="multiline:true,validType:'length[0,200]'">
	    	</td>
	    </tr>
	    <tr>
	  		<td style="width:40px; text-align: right;">备注：</td>
	    	<td colspan="10">
	    		<input name="remark" id="remark" class="easyui-textbox" value="${advPic.remark}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'">
	    	</td>
	    </tr>
	  </table>
		<div class="pt10"></div>
			<div class=" easyui-panel" title="广告图片上传" data-options="collapsible:true">
			<table class="cus_table">
			   <tr>
			      <td align="center"><input type="button" class="text_btn"  onclick="openFileUpload('picture_url',350);" value="上传"/></td>
			      <td >&nbsp;</td>
			   </tr>
			   <tr>
			      <td height="160px"  align="center">
			      	 <c:if test="${empty advPic.pictureUrl||advPic.pictureUrl=='' }">
			     	 	<img id="picture_url_img" alt="广告图片" src="${ctx}/p/xlkfinance/images/u53.png" width="300px" height="180px">
			     	 </c:if>
			     	 <c:if test="${not empty advPic.pictureUrl && advPic.pictureUrl!='' }">
			     	 	 <img id="picture_url_img" alt="广告图片" src="${serverUrl}/${advPic.pictureUrl}" width="300px" height="180px">
			     	 </c:if>
			      </td>
			      <td>&nbsp;<input type="hidden" name="pictureUrl" id="picture_url" value="${advPic.pictureUrl}"/></td>
			   </tr>
			</table>
	</div>
	<table>
	    <tr>
    	<td class="align_center"  height="50">
		<a href="#"class="easyui-linkbutton" iconCls="icon-save" class="cus_save_btn" name="save" value="保     存" onclick="saveAdvPic();">保存</a>
    	<a href="#"class="easyui-linkbutton" iconCls="icon-clear" onclick="cancelAdvPic()">取消</a>
    </td>
   </tr>
	</table>
	</div>
</form>    
   </div>
  </div>
 </div>
</body>
</html>  