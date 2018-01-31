<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<title>机构注册</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script>
	$(document).ready(function() {
		$("#orgRegisterForm").validate({
			rules : {
				"orgUserInfo.userName" : {
					required : true,
					checkLoginName : true,
					checkUserNameIsExist : true
				},
				"orgUserInfo.realName" : {
					required : true,
					maxlength : 20
				},
				"orgUserInfo.phone" : {
					required : true,
					isMobile : true,
					maxlength : 20,
					checkPhoneIsExist : true
				},
				msgCode : {
					required : true,
					isDigits : true
				},
				orgName : {
					required : true,
					maxlength : 20,
				},
				code : {
					required : true,
					maxlength : 20,
					checkOrgCodeIsExist : true
				},
				email : {
					required : true,
					email : true,
					maxlength : 50,
					checkEmailIsExist : true
				},
				"orgUserInfo.password" : {
					isPwd : true,
					required : true
				},
				confirmPassword : {
					required : true,
					isPwd : true,
					equalTo: "#orgUserInfo\\.password"
				}
			},
			submitHandler : function(form) {
				$.ajax({
				    url: "${ctx}orgController/ignore/orgRegister.action",
				    type: "POST",
				    data: $("#orgRegisterForm").serialize(),
				    async: false,
					success : function(result) { //表单提交后更新页面显示的数据
						var ret = eval("(" + result + ")");
						if (ret && ret.header["success"]) {
							layer.confirm('恭喜你已成功注册，跳转到登录页面？', {
								  icon : 6,
								  btn: ['是','否'] //按钮
								}, function(){
									window.location.href ="${ctx}orgLogin.action"
								}, function(){
								  
								});
						} else {
							layer.alert(ret.header["msg"], {
								icon : 5
							});
						}
					}
				});
			}
		})
	})
</script>
<style>
.gray-bg{background: #fff;}
.wrapper{padding:0;}
.ibox-title{border: 0;}
.navbar-header{padding:10px 0 0 20px;}
@media (min-width:768px) {
	.ibox-content{padding: 15px 20px 20px 17%;}
}
/* @media (min-width:1000px) {
	.col-sm-5 {width: 32%;}
	.col-sm-4{width: 35%;}
}
@media (min-width:1280px) {
	.col-sm-5 {width: 35%;}
	.col-sm-4{width: 40%;}
} */
</style>
</head>
<body class="gray-bg">
 <div class="wrapper">
	<!--顶部分开始-->
	<div class="gray-bg dashbard-1">
	   <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
	   <div class="navbar-header">
	    <img alt="image" class="img-circle" src="../../img/logo.png" />
	   </div>
	   <ul class="nav navbar-top-links navbar-right">
	    <li class="dropdown hidden-xs tel">400-9393-888</li>
	   </ul>
	  
	   </nav>
	</div>
   <div class="ibox float-e-margins">
    <div class="ibox-title">
     <h5>
      注册
     </h5>
     <p class="text-muted text-right"><small>已经有账户了？</small><a href="${ctx}orgLogin.action">点此登录</a>
     </p>
    </div>
    <div class="ibox-content">
     <div class="row">
      <div class="col-sm-12">
        <form role="form" id="orgRegisterForm" class="form-horizontal" action="#" method="post">
        <div class="form-group">
         <label for="userName" class="col-sm-3 control-label"><span class="requiredSty">*</span>登录名:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" id="orgUserInfo.userName" name="orgUserInfo.userName" placeholder="登录名">
         </div>
        </div>
        <div class="form-group">
         <label for="realName" class="col-sm-3 control-label"><span class="requiredSty">*</span>真实姓名:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" id="orgUserInfo.realName" name="orgUserInfo.realName" placeholder="真实姓名">
         </div>
        </div>
        <div class="form-group">
         <label for="phone" class="col-sm-3 control-label"><span class="requiredSty">*</span>手机号码:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" id="phone" name="orgUserInfo.phone" placeholder="手机号">
         </div>
        </div>
        <div class="form-group">
         <label for="code" class="col-sm-3 control-label"><span class="requiredSty">*</span>验证码:</label>
         <div class="col-sm-4">
          <div class="input-group">
           <input type="text" id="msgCode" name="msgCode" maxlength="6" class="form-control" placeholder="验证码"> <span class="input-group-btn">
            <button class="btn btn-info" id="send_code_btn" type="button" onclick="sendMsg('#phone',1)" name="button">获取</button>
           </span>
          </div>
          <span style="display: none; color: red;" id="msgCodeErr">请输入验证码</span>
         </div>
        </div>
        <div class="form-group">
         <label for="orgName" class="col-sm-3 control-label"><span class="requiredSty">*</span>机构名称:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" id="orgName" name="orgName" placeholder="机构名称">
         </div>
        </div>
        <div class="form-group">
         <label for="orgCode" class="col-sm-3 control-label"><span class="requiredSty">*</span>统一社会信用代码:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" id="orgCode" name="code" placeholder="统一社会信用代码">
         </div>
        </div>
        <div class="form-group">
         <label for="email" class="col-sm-3 control-label"><span class="requiredSty">*</span>公司邮箱:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" id="email" name="email" placeholder="公司邮箱" maxlength="30">
         </div>
        </div>
        <div class="form-group">
         <label for="orgUserInfo.password" class="col-sm-3 control-label"><span class="requiredSty">*</span>设置密码:</label>
         <div class="col-sm-4">
          <input type="password" class="form-control" id="orgUserInfo.password" name="orgUserInfo.password" placeholder="设置密码">
         </div>
        </div>
        <div class="form-group">
         <label for="confirmPassword" class="col-sm-3 control-label"><span class="requiredSty">*</span>确认密码:</label>
         <div class="col-sm-4">
          <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="确认密码">
         </div>
        </div>
        <div class="form-group">
			<div class="col-sm-8 col-sm-offset-3">                     
			<input type="submit" class="btn btn-w-m btn-success" value="提交" name="commit">
			<input type="reset" class="btn btn-w-m btn-white" value="重置" name="commit">
			</div>
        </div>
       </form>
      </div>
     </div>
    </div>
   </div>

 </div>
</body>
</html>