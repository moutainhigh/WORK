<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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
<title>机构管理</title>
<style type="text/css">
#tabs .tabs-header .tabs{
  height: 26px;
  position: fixed;
  top:0px;
  background: #fff;
  z-index: 9999999;
  padding-top;2px;
}
.{padding-top:7px;}
</style>
</head>
<script type="text/javascript">

var pid = "${orgAssetsInfo.pid}";
var editType = "${editType}";

// statrt
var orgId = "${orgId}";
function saveCityInfo(){
	$("#cityInfo").form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				pid = ret.header["orgId"];//机构ID
				$.messager.alert('操作提示','保存信息成功','info');
				$('#add_city').dialog('close');
				// 重新加载城市
					var url = "<%=basePath%>orgAssetsCooperationController/getOcityInfoList.action?orgId="+orgId;
					$('#city_info').datagrid('options').url = url;
					$('#city_info').datagrid('reload');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}

function deleteCity(){
	var row = $('#city_info').datagrid('getSelections');
	if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else if(row.length ==0){
		$.messager.alert("操作提示","请选择数据","error");
	}else{
		var pid = row[0].pid;
		var areaCode = row[0].areaCode;
		if (confirm("是否确认删除?")) {
		$.ajax({
			type: "POST",
	        url: "${basePath}orgCooperatCompanyApplyController/deleteCityInfo.action",
	        data:{"pid":pid,"orgId":orgId,"areaCode":areaCode},
	        dataType: "json",
	        success: function(ret){
	        	if(ret && ret.header["success"]){
					// 操作提示
					$.messager.alert('操作提示',ret.header["msg"]);
					// 重新加载城市
					var url = "<%=basePath%>orgAssetsCooperationController/getOcityInfoList.action?orgId="+orgId;
					$('#city_info').datagrid('options').url = url;
					$('#city_info').datagrid('reload');
	        	}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');
				}
	        }
		});
	}
}
}

function addNewCity(){
	var editType = ${editType};
	if(editType == 1){
		$.messager.alert("操作提示","请先保存机构信息!","error");
		return;
	}
	setAreaCombobox("province_code","1","","");
	//新增为1
	document.getElementById("status").value = 1
	//居住地址省市区
			$("#province_code").combobox({ 
	 			onSelect:function(){
	 	        	var provinceCode = $("#province_code").combobox("getValue");
	 	        	console.log("provinceCode:"+provinceCode);
	 	        	if(provinceCode != ""){
	 	        		setAreaCombobox("area_code","2",provinceCode,"");
	 	    		}
	 	        }
	 		}); 
	$('#add_city').dialog('open').dialog('setTitle', "新增城市信息");
	$('#cityInfo').form('reset');
	 $(function(){
         $('#province_code').combobox({    
        	 required:true,    
             multiple:false,
             disabled:false    
        });  
   });
	 $(function(){
         $('#area_code').combobox({    
        	 required:true,    
             multiple:false,
             disabled:false  
        });  
   });
		
}

function updateCity(){
	var row = $('#city_info').datagrid('getSelections');
	if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else if(row.length ==0){
		$.messager.alert("操作提示","请选择数据","error");
	}else{ 
	setAreaCombobox("province_code","1","","");
	    var pid = row[0].pid;
	    var provinceCode = row[0].provinceCode;//省编码
		var areaCode = row[0].areaCode;//市编码
		var contact = row[0].contact;//联系人
		var phone = row[0].phone;//电话
		var remark = row[0].remark;//备注
		document.getElementById("status").value = 0;
		document.getElementById("pidpid").value = pid;
		
		setAreaCombobox("province_code","1","",provinceCode);
		setAreaCombobox("area_code","2",provinceCode,areaCode);
		//居住地址省市区
			$("#province_code").combobox({
	 			onSelect:function(){
	 	        	var provinceCode = $("#province_code").combobox("getValue");
	 	        	console.log("provinceCode:"+provinceCode);
	 	        		setAreaCombobox("area_code","2",provinceCode,"");
	 	        }
	 		}); 
			$('#cityInfo').form('reset');
			$('#add_city').dialog('open').dialog('setTitle', "修改城市信息");
	 		$("#cityInfo #phone").textbox('setValue', phone);
			$("#cityInfo #remark").textbox('setValue', remark);
		  	$("#cityInfo #contact").textbox('setValue',contact);
		  	
		  	 $(function(){
		            $('#province_code').combobox({    
		                 required:true,    
		                 multiple:true,
		                 disabled:true  
		           });  
		      });
		 	 $(function(){
		            $('#area_code').combobox({    
		                 required:true,    
		                 multiple:true,
		                 disabled:true  
		           });  
		      });
	}
   }

//end 

$(function() {
	if(editType == '3'){
		$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
		$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
		$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}
	
	$("#comBases").linkbutton("enable");
	$('#comBases').bind('click', function(){
		editComBases();
    });
	
	if(orgId != null && orgId != ''){
	 if(orgId > 0){
		var url = "<%=basePath%>orgAssetsCooperationController/getOcityInfoList.action?orgId="+orgId;
		$('#city_info').datagrid('options').url = url;
		$('#city_info').datagrid('reload');
	 }
	}
	
});
//跳转到机构附加信息tab
function editComBases(){
	if(pid >0){
		parent.openNewTab(BASE_PATH+'customerController/editComBases.action?orgId='+pid+'&flag=1&comType=3',"企业附加信息",true);
	}else{
		$.messager.alert('操作提示','请先保存机构基本信息！','info');
		return;
	}
}
 $
.extend(
		$.fn.validatebox.defaults.rules,
		{
			//检查登录名
			checkLoginName : {//value值为文本框中的值
				validator : function(value) {
					var r = false;
					value = value.trim();
					var pid=$("#pid").val();
					$
							.ajax({
								url : "${basePath}orgAssetsCooperationController/checkUserNameIsExist.action",
								data : {
									"loginName" : value,
									"pid":pid
								},
								dataType : "json",
								async : false,
								success : function(ret) {
									r = ret;
								}
							});
					return r;
				},
				message : '登录名已存在!'
			},
			//检查组织代码
			checkOrgCode : {//value值为文本框中的值
				validator : function(value) {
					var r = false;
					var pid=$("#pid").val();
					value = value.trim();
					$.ajax({
								url : "${basePath}orgAssetsCooperationController/checkOrgCodeIsExist.action",
								data : {
									"code" : value,
									"pid" : pid
								},
								dataType : "json",
								async : false,
								success : function(ret) {
									r = ret;
								}
							});
					return r;
				},
				message : "组织代码已存在!"
			},
			//检查邮箱
			checkOrgEmail : {//value值为文本框中的值
				validator : function(value) {
					var r = false;
					var pid=$("#pid").val();
					value = value.trim();
					$.ajax({
								url : "${basePath}orgAssetsCooperationController/checkEmailIsExist.action",
								data : {
									"email" : value,
									"pid" : pid
								},
								dataType : "json",
								async : false,
								success : function(ret) {
									r = ret;
								}
							});
					return r;
				},
				message : "邮箱已存在!"
			},
			//检查手机号码
			checkOrgPhone : {//value值为文本框中的值
				validator : function(value) {
					var r = false;
					var pid=$("#pid").val();
					value = value.trim();
					$.ajax({
								url : "${basePath}orgAssetsCooperationController/checkEmailIsExist.action",
								data : {
									"phone" : value,
									"pid" : pid
								},
								dataType : "json",
								async : false,
								success : function(ret) {
									r = ret;
								}
							});
					return r;
				},
				message : "手机号码已存在!"
			}
		}); 

/**
 * 保存机构信息
 */
function saveOrgAssetsInfo(){
	$("#orgForm").form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				orgId = ret.header["orgId"];//机构ID
				var msg ="确定保存！";
				if(confirm(msg)==true){
					parent.openNewTab("${basePath}orgAssetsCooperationController/editOrgAssets.action?editType=2&orgId="
							+ orgId,"修改机构信息",true);
					parent.$('#layout_center_tabs').tabs('close','新增机构');
				}
				
				$("#tabs").tabs("select", "附加信息");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}


</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
	<div title="机构信息" id="tab1">
		<div style="padding: 30px 10px 0 10px;">
		<div class="easyui-panel" title="机构基本信息" data-options="collapsible:true" width="1039px;">
			<div id="personal" style="padding:10px 0 15px 0;">
			<form action="<%=basePath%>orgAssetsCooperationController/saveOrUpdateOrgAssets.action" id="orgForm" name="orgForm" method="post">
				<input type="hidden" name="pid" id="pid" value="${orgAssetsInfo.pid }">
				<input type="hidden" name="auditStatus" value="${orgAssetsInfo.auditStatus >0?orgAssetsInfo.auditStatus : 3}">
				<input type="hidden" name="cooperateStatus" value="${orgAssetsInfo.cooperateStatus >0?orgAssetsInfo.cooperateStatus : 1}">
				<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
					<tr> 
						<td class="label_right"><font color="red">*</font>登录名：</td>
						<td>
							<input  class="easyui-textbox" id="loginName" <c:if test="${editType == '2'}"> readonly='readonly' </c:if> required="true" name="orgUserInfo.userName" value="${orgAssetsInfo.loginName}" data-options="required:true,validType:['checkLoginName','englishOrNum']"/>
						</td>
						<c:if test="${editType == '1'}">
							<td class="label_right"><font color="red">*</font>密码：</td>
							<td>
								<input class="easyui-textbox" name="orgUserInfo.password" id="pwd" required="true" type="password" value="${orgAssetsInfo.orgUserInfo.password}" data-options="required:true,validType:'englishOrNum'"/>
							</td> 
						</c:if>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>机构名称：</td>
						<td>
							<input class="easyui-textbox" id = "orgName" name="orgName" required="true" value="${orgAssetsInfo.orgName}" data-options="required:true,validType:'length[1,20]'"/>
						</td>
						<td class="label_right"><font color="red">*</font>统一社会信用代码：</td>
						<td>
							<input  class="easyui-textbox" name="code" required="true" id="code" value="${orgAssetsInfo.code}" data-options="required:true,validType:['checkOrgCode','length[1,20]']"/>
						</td>
					</tr>
					<tr>
					   <td class="label_right"><font color="red">*</font>手机号码：</td>
						<td>
							<input  class="easyui-textbox" name="orgUserInfo.phone" required="true" id="phone" value="${orgAssetsInfo.orgUserInfo.phone}" data-options="required:true,validType:['checkOrgPhone','telephone']"  missingMessage="请填写手机号码!"/>
						</td>
						<td class="label_right"><font color="red">*</font>公司邮箱：</td>
						<td>
							<input  class="easyui-textbox" name="email" id="email" required="true" value="${orgAssetsInfo.email}" data-options="required:true,validType:['checkOrgEmail','email',,'length[1,50]']"/>
						</td>
						<%--<td class="label_right">公司地址：</td>
						<td>
							<input  class="easyui-textbox" name="address" value="${orgAssetsInfo.address}" data-options="validType:'length[0,50]'"/>
						</td> --%>
<!-- 						<td class="label_right"><font color="red">*</font>联系人：</td> -->
<!-- 						<td> -->
<%-- 							<input  class="easyui-textbox" name="contact" required="true" value="${orgAssetsInfo.contact}" data-options="required:true,validType:'length[1,20]'"/> --%>
<!-- 						</td> -->
<!-- 						<td class="label_right"><font color="red">*</font>联系人手机号码：</td> -->
<!-- 						<td> -->
<%-- 							<input  class="easyui-textbox" name="phone" required="true" value="${orgAssetsInfo.phone}" data-options="required:true,validType:['telephone']"  missingMessage="请填写联系人手机号码!"/> --%>
<!-- 						</td> -->
					</tr>
					<tr>
						
						<td class="label_right"><font color="red">*</font>账号状态：</td>
						<td>
							<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="orgUserInfo.status" style="width:129px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${orgAssetsInfo.status==1 }">selected </c:if>>启用</option>
								<option value="2" <c:if test="${orgAssetsInfo.status==2 }">selected </c:if>>禁用</option>
							</select>
						</td>
						<td class="label_right"><font color="red">*</font>认证状态：</td>
						<td>
							<select class="select_style easyui-combobox" disabled="disabled" editable="false" data-options="validType:'selrequired'" required="true" style="width:129px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${orgAssetsInfo.auditStatus==1}">selected </c:if>>未认证</option>
								<option value="2" <c:if test="${orgAssetsInfo.auditStatus==2 }">selected </c:if>>认证中</option>
								<option value="3" <c:if test="${orgAssetsInfo.auditStatus==3 ||  orgAssetsInfo.auditStatus==0 }">selected </c:if>>已认证</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>合作状态：</td>
						<td>
							<select class="select_style easyui-combobox" editable="false" disabled="disabled" data-options="validType:'selrequired'" required="true" name="cooperateStatus" style="width:129px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${orgAssetsInfo.cooperateStatus==1 || orgAssetsInfo.cooperateStatus==0 }">selected </c:if>>未合作</option>
								<option value="2" <c:if test="${orgAssetsInfo.cooperateStatus==2 }">selected </c:if>>已合作</option>
								<option value="3" <c:if test="${orgAssetsInfo.cooperateStatus==3 }">selected </c:if>>合作已过期</option>
								<option value="4" <c:if test="${orgAssetsInfo.cooperateStatus==4 }">selected </c:if>>合作待确认</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label_right">认证说明：</td>
						<td colspan="4">
							<input  class="easyui-textbox" name="auditDesc" value="${orgAssetsInfo.auditDesc}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">备注：</td>
						<td colspan="4">
							<input  class="easyui-textbox" name="remark" value="${orgAssetsInfo.remark}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">
					       <a href="#" class="easyui-linkbutton" id="comBases" iconCls="icon-edit">附加信息</a>
                        </td>
					</tr>
				</table>
				
				
				
				<div class="fitem" style="margin-left: 40px;">	
				<div id="toolbar_city"  style="border-bottom: 0;">
					<a href="javascript:void(0)" id="addNewCity" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addNewCity()">新增城市</a>
					<a href="javascript:void(0)" id="updateCity" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="updateCity()">修改城市</a>
					<a href="javascript:void(0)" id="deleteCity" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="deleteCity()">删除城市</a>
				</div>
			
				<table id="city_info" class="easyui-datagrid" fitColumns="true" style="width: 960px;height: auto;"
				     data-options="url:'',
				     	method: 'post',
				     	rownumbers: true,
			    		fitColumns:true,
				    	singleSelect: true,
				    	toolbar: '#toolbar_city',
					    pagination: false">
					<thead>
						<tr>
							<th data-options="field:'pid',checkbox:true" ></th>
							<th data-options="field:'provinceName'"  align="center" halign="center" >省份</th>
							<th hidden="hidden" data-options="field:'areaCode'"  align="center" halign="center" >城市编码</th>
							<th data-options="field:'cityName'"  align="center" halign="center" >城市</th>
							<th data-options="field:'contact'"  align="center" halign="center" >联系人</th>
							<th data-options="field:'phone'"  align="center" halign="center" >手机号</th>
							<th data-options="field:'remark'"  align="center" halign="center" >备注</th>
						</tr>
					</thead>
			   	</table>
				</div>
				
				
				<div style="padding-bottom: 20px;padding-top: 10px;">
					<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveOrgAssetsInfo()">保存</a>
				</div>
			</form>
			</div>
		</div>
		</div>
	</div>
	
</div>


<!-- 新增城市信息开始 -->
		<div id="add_city_info">		
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveCityInfo()">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#add_city').dialog('close')">关闭</a>
		</div>
		<div id="add_city" class="easyui-dialog" fitColumns="true"  title="新增城市信息"
		     style="width:550px;top:100px;padding:10px;" buttons="#add_city_info" closed="true" >
			<form id="cityInfo" action="<%=basePath%>orgCooperatCompanyApplyController/saveCityInfo.action" method="POST">
				<table class="beforeloanTable">
					<input name="orgId" id="orgId" type="hidden"  value="${orgId}"/>
					<input name="status" id="status" type="hidden"/>
					<input name="pid" id="pidpid" type="hidden"/>
<!-- 					<input  class="easyui-textbox" type="hidden" name="status"  id = "status" data-options="multiline:true,validType:'length[0,200]'"/> -->
					<tr id = "add_code_info">
						<td class="label_right1"><font color="red">*</font>省份：</td>
						<td>
							<select id="province_code" name="provinceCode"  class="easyui-combobox" data-options="validType:'selrequired'"  style="width:135px;" /> 
						</td> 
						<td class="label_right1"><font color="red">*</font>城市：</td>
						<td>
						   <select id="area_code" name="areaCode"  class="easyui-combobox" data-options="validType:'selrequired'" style="width:135px;" />
						</td>
					</tr>
					<tr>
					   <td class="label_right"><span class="cus_red">*</span>联系人：</td>
					   <td >
					   	<input name="contact" id="contact" required="true" data-options="required:true,validType:'length[1,50]'" class="easyui-textbox"  style="width:135px;" />
					   	</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>手机号：</td>
						<td>
						   <input  class="easyui-textbox" name="phone" required="true" id="phone"  data-options="required:true,validType:'telephone'"  missingMessage="请填写手机号码!"  style="width:135px;" />
						</td>
					</tr>
					<tr>
						<td class="label_right">备注：</td>
							<td colspan="4">
								<input  class="easyui-textbox" name="remark"  id = "remark" style="width:100%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
							</td>
					</tr>
				 </table>
			</form>	  
		</div>
		<!-- 新增城市结束 -->
</div>
</body>
</html>