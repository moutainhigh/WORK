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
<title>合伙人账户信息</title>
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

var pid = "${partnerInfo.pid}";
var editType = "${editType}";

$(function() {

	if(editType == '3'){
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('disabled','disabled');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('readOnly','readOnly');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}
	
	$('#phone').textbox({
		  onChange: function(value){
			  checkPhone();
		  }
		});
	
	$('#cardNo').textbox({
		  onChange: function(value){
			  checkCertNumber();
		  }
		});
});

/**
 * 保存合伙人合作信息
 */
function saveOrgPartnerInfo(){
	$("#orgForm").form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				pid = ret.header["pid"];//合作申请ID
				var userId = ret.header["userId"];//合作申请ID
				$("#pid").val(pid);
				$("#userId").val(userId);
				$.messager.alert('操作提示','保存信息成功','info');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}
/**
 * 校验手机号码
 */
function checkPhone(){
	var phoneNum = $("#phone").val();
	 var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
	if(!tel.test(phoneNum)){
		$.messager.alert('操作提示',"手机号码格式错误！",'error');
		$("#phone").textbox('setValue','');
		return;
	}
}
/**
 * 校验身份证号码
 */
function checkCertNumber(){
	var certNumber = $("#cardNo").val();
	if(certNumber.length>0 &&!(IdCardValidate(certNumber)))
    {
		$.messager.alert('操作提示',"输入的身份证号长度不对，或者身份证号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。",'error');	
		$("#cardNo").textbox('setValue','');
        return;
    }
	if(certNumber.length>='15'){
		if(certNumber.substring(12,14)> 31){
			$.messager.alert('操作提示',"请输入正确的身份证号！",'error');
			$("#cardNo").textbox('setValue','');
			 return;
		}
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
					var pid=$("#userId").val();
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
					var pid=$("#userId").val();
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
					var pid=$("#userId").val();
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
					var pid=$("#userId").val();
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
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
	<div title="合伙人信息" id="tab1">
		<div style="padding: 30px 10px 0 10px;">
		<div class="easyui-panel" title="合伙人基本信息" data-options="collapsible:true" width="1039px;">
			<div id="personal" style="padding:10px 0 15px 0;">
				<form action="<%=basePath%>orgPartnerInfoController/saveOrUpdatePartner.action" id="orgForm" name="orgForm" method="post">
				<input type="hidden" name="pid" id="pid" value="${partnerInfo.pid }">
				<input type="hidden" name="userId" id="userId" value="${partnerInfo.userId }">
				<input type="hidden" name="orgUserInfo.pid" value="${partnerInfo.orgUserInfo.pid }">
				<input type="hidden" name="status" value="${partnerInfo.status >0?partnerInfo.status : 3}">
				<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
					<tr>
						<td class="label_right"><font color="red">*</font>登录名：</td>
						<td>
							<input  class="easyui-textbox" id="loginName" <c:if test="${editType == '2'}"> readonly='readonly' </c:if> required="true" name="orgUserInfo.userName" value="${partnerInfo.orgUserInfo.userName}" data-options="required:true,validType:['checkLoginName','englishOrNum']"/>
						</td>
						<c:if test="${editType == '1'}">
							<td class="label_right"><font color="red">*</font>密码：</td>
							<td>
								<input class="easyui-textbox" name="orgUserInfo.password" id="pwd" required="true" type="password" value="${partnerInfo.orgUserInfo.password}" data-options="required:true,validType:'englishOrNum'"/>
							</td>
						</c:if>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>合伙人姓名：</td>
						<td>
							<input  class="easyui-textbox" name="orgUserInfo.realName" required="true" value="${partnerInfo.orgUserInfo.realName}" data-options="required:true,validType:'length[1,20]'"/>
						</td>
						<td class="label_right">身份证号码：</td>
						<td>
							<input class="easyui-textbox" name="cardNo" id="cardNo" value="${partnerInfo.cardNo}" data-options="validType:'length[0,18]'"/>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>手机号码：</td>
						<td>
							<input  class="easyui-textbox" name="orgUserInfo.phone" id="phone" required="true" id="phone" value="${partnerInfo.orgUserInfo.phone}" data-options="required:true,validType:['checkOrgPhone','telephone']" missingMessage="请填写手机号码!"/>
						</td>
						<td class="label_right"><font color="red">*</font>邮箱：</td>
						<td>
							<input  class="easyui-textbox" name="orgUserInfo.email" id="email" required="true" value="${partnerInfo.orgUserInfo.email}" data-options="required:true,validType:['checkOrgEmail','email']"/>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>账号状态：</td>
						<td>
							<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="orgUserInfo.status" style="width:129px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${partnerInfo.orgUserInfo.status==1 }">selected </c:if>>启用</option>
								<option value="2" <c:if test="${partnerInfo.orgUserInfo.status==2 }">selected </c:if>>禁用</option>
							</select>
						</td>
						<td class="label_right"><font color="red">*</font>认证状态：</td>
						<td>
							<select class="select_style easyui-combobox" disabled="disabled" editable="false" data-options="validType:'selrequired'" required="true" style="width:129px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${partnerInfo.status==1 }">selected </c:if>>未认证</option>
								<option value="2" <c:if test="${partnerInfo.status==2 }">selected </c:if>>认证中</option>
								<option value="3" <c:if test="${partnerInfo.status==3 || partnerInfo.status == 0 }">selected </c:if>>已认证</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>合作状态：</td>
						<td>
							<select class="select_style easyui-combobox" disabled="disabled" editable="false" style="width:129px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${partnerInfo.cooperationStatus==1 || partnerInfo.cooperationStatus==0}">selected </c:if>>未合作</option>
								<option value="2" <c:if test="${partnerInfo.cooperationStatus==2 }">selected </c:if>>已合作</option>
								<option value="3" <c:if test="${partnerInfo.cooperationStatus==3 }">selected </c:if>>合作已过期</option>
								<option value="4" <c:if test="${partnerInfo.cooperationStatus==4 }">selected </c:if>>合作待确认</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label_right">认证说明：</td>
						<td>
							<input  class="easyui-textbox" name="auditDesc" value="${partnerInfo.auditDesc}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
						</td>
					</tr>
				</table>
					<div style="padding-bottom: 20px;padding-top: 10px;padding-left: 100px;">
						<a href="#" class="easyui-linkbutton" style="margin-left: 80px;" iconCls="icon-save" onclick="saveOrgPartnerInfo()">保存</a>
					</div>
				</form>
			</div>
		</div>
		</div>
	</div>
</div>
</div>
</body>
</html>