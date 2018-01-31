<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<title>找回密码</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script>
	$(document).ready(function() {
		$("#forgetPasswordForm").validate({
			rules : {
				phone : {
					required : true,
					isMobile : true
				},
				password : {
					required : true,
					isPwd : true 
				},
				msgCode : {
					required : true,
					isDigits : true
				}
			},
			submitHandler : function(form) {
				$.ajax({
				    url: "${ctx}sysOrgUserController/ignore/forgetPassword.action",
				    cache: true,
				    type: "POST",
				    data: $("#forgetPasswordForm").serialize(),
				    async: false,
					success : function(result) { //表单提交后更新页面显示的数据
						var ret = eval("(" + result + ")");
						if (ret && ret.header["success"]) {
							layer.confirm('恭喜你密码已修改成功，跳转到登录页面？', {
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
.form-group {margin-bottom: 25px; position: relative;}
.form-group label.error{position: absolute;left: 10px;top: 58px;}
.form-group .input-group label.error{position: absolute;left: 10px; top: 35px;}
.input-group{margin-bottom: 25px;}

</style>
</head>
<body class="gray-bg">
 <div class="wrapper">
 <!--顶部分开始-->
	<div class="gray-bg dashbard-1">
	   <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
	   <div class="navbar-header">
	    <img alt="image" class="img-circle" src="../img/logo.png" />
	   </div>
	   <ul class="nav navbar-top-links navbar-right">
	    <li class="dropdown hidden-xs tel">400-9393-888</li>
	   </ul>
	  
	   </nav>
	</div>

   <div class="ibox float-e-margins">
    <div class="ibox-title">
     <h5>
      忘记密码<small> 通过短信设置新的密码</small>
     </h5>
    </div>
    <div class="ibox-content ibox-line">
     <div class="row">
      <div class="col-sm-6 br">
       <form role="form" id="forgetPasswordForm" action="#" method="post">
        <div class="form-group">
         <label><span class="requiredSty">*</span>手机号码</label> <input type="text" name="phone" id="phone" placeholder="请输入您注册的手机号码" class="form-control">
        </div>
        <div class="form-group">
         <label><span class="requiredSty">*</span>设置新的密码</label> <input type="password" name="password" id="password" placeholder="请输入新的密码" class="form-control">
        </div>
        <div class="form-group">
        <label><span class="requiredSty">*</span>验证码</label>
        <div class="input-group">
         <input type="text" id="msgCode" name="msgCode" maxlength="6" class="form-control" placeholder="请输入短信验证码"> <span class="input-group-btn">
          <button class="btn btn-info" id="send_code_btn" type="button" onclick="sendMsg('#phone',2)" name="button">获取验证码</button>
         </span>
        </div>
        </div>
        <span style="display: none; color: red;" id="msgCodeErr">请输入验证码</span>
        <div class="form-group">
         <input type="submit" class="btn btn-w-m btn-success" value="立即申请" name="commit">
        </div>
       </form>
      </div>
      <div class="col-sm-6">
       <h4>还不是会员？</h4>
       <p>您可以注册一个新账户</p>
       <p class="text-center">
        <a href="${ctx}orgController/ignore/toOrgRegister.action"><i class="fa fa-sign-in big-icon"></i></a>
       </p>
      </div>
     </div>
    </div>
   </div>

 </div>
</body>
</html>