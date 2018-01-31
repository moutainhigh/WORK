<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>业务管理系统 - 登录</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!-- <link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">
<link href="css/style.min.css" rel="stylesheet"> -->
<link href="${ctx}css/login.min.css" rel="stylesheet">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script>
$(document).ready(function() {
$('#Kaptcha').click(     
        function() {     
           $(this).hide().attr('src','Kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();     
    });
    
$("#loginForm").validate({
	rules : {
		userName : {
			required : true
		},
		password : {
			required : true
		},
		captcha : {
			required : true
		}
	},
	submitHandler : function(form) {
		$.ajax({
		    url: "${ctx}ignore/checkUserLogin.action",
		    cache: true,
		    type: "POST",
		    data: $("#loginForm").serialize(),
		    async: false,
			success : function(result) { //表单提交后更新页面显示的数据
				var ret = eval("(" + result + ")");
				if (ret && ret["success"]) {
					window.parent.location=getWebRootPath()+"/index.action";
				} else {
					$("#loginMsg").html(ret["msg"]);
					$("#loginMsgDiv").show(300).delay(5000).hide(300);
				}
			}
		});
	}
});
})
</script>
<style>
html,body{height: auto;}
.middle-box{margin-top: 80px;max-width: 450px;}
.middle-box h1{padding-bottom: 30px;}
.middle-box h3{padding-bottom: 10px;}
.alert-danger{color: #FF0A06; background: none;border: none;margin-bottom:0; }
.form-group strong, .form-group strong a {text-shadow:0 0 0.2em #fff, -0 -0 0.2em #fff; -webkit-text-shadow:0 0 0.2em #fff, -0 -0 0.2em #fff;
-moz-text-shadow:0 0 0.2em #fff, -0 -0 0.2em #fff;}
.login_box{
    background-color: rgba(255, 255, 255, 0.8);
    padding: 40px 70px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    -webkit-box-shadow: rgba(200, 200, 200, 0.50) 0px 0px 8px;
    -moz-box-shadow: rgba(200, 200, 200, 0.50) 0px 0px 8px;
    box-shadow: rgba(200, 200, 200, 0.50) 0px 0px 8px;
}
.loginscreen.middle-box {width: 450px}
.form-group {margin-bottom: 25px; position: relative;}
label.error{position: absolute;left: 10px; top: 35px;}
.btn-info{background-color: #FFFFFF; border-color: #e5e6e7; color: #1C84C6;  padding: 6px 7px;}
.input-group-btn img{float: left;border-radius: 3px;border-top-left-radius: 0;border-bottom-left-radius: 0;}

</style>
</head>
<body>
 <div class="middle-box text-center loginscreen  animated fadeInDown">
  <div class="login_box">
  <%--  <div class="col-sm-7">
    <div class="signin-info">
     <div class="logopanel m-b">
      <h1>[ AOM ]</h1>
     </div>
     <div class="m-b"></div>
     <h4>
      欢迎使用 <strong>AOM</strong>
     </h4>
     <ul class="m-b">
      <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势一</li>
      <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势二</li>
      <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势三</li>
      <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势四</li>
      <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势五</li>
     </ul>
     <strong>还没有账号？ <a href="${ctx}orgController/ignore/toOrgRegister.action">立即注册机构&raquo;</a> <a href="${ctx}partnerController/ignore/toPartnerRegister.action">立即注册合伙人&raquo;</a></strong>
    </div>
   </div> --%>
   
	<div>
	<h1 class="logo-name"><img alt="image" class="img-circle" src="img/login_logo.png" /></h1>
	</div>
	
	<h3>欢迎登录</h3>
   <div class="">
    <form method="post" action="#" id="loginForm" >
     <div class="form-group">
     <input type="text" name="userName" class="form-control uname" required="" placeholder="用户名" />
     </div>
     
     <div class="form-group">
	     <div class="input-group">
	     <input type="password" name="password" required="" class="form-control" placeholder="密码" />
	     <span class="input-group-btn">
	            <a  class="btn btn-info" type="button" href="ignore/toForgetPassword.action">忘记密码？</a>
	     </span>
	     </div>
     </div>
     
     <div class="form-group">
	     <div class="input-group">
	     <input type="text" name="captcha" class="form-control pword" maxlength="4" placeholder="验证码" />
	     <span class="input-group-btn">
	            <img id="Kaptcha" src="Kaptcha.jpg" />
	     </span>
	     </div>
     </div>
     
     <div class="form-group" id="loginMsgDiv" style="display: none;">
      <div class="alert alert-danger" id="loginMsg">${msg}</div>
     </div>
     
     <div class="form-group">
     <button type="submit" class="btn btn-success btn-block">登录</button>
     </div>
     
     <div class="form-group">
     <strong>还没有账号？ <a href="${ctx}orgController/ignore/toOrgRegister.action">立即注册机构&raquo;</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${ctx}partnerController/ignore/toPartnerRegister.action">立即注册合伙人&raquo;</a></strong>
     </div>
    </form>
   </div>
  </div>
  
 </div>
 <div class="signup-footer">
   <div class="text-center">&copy; 2016 All Rights Reserved.</div>
  </div>
 <div class="signin">
 <img src="img/login-background.jpg">
 </div>
</body>
</html>