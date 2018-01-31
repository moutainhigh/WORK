<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/config.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhml" xml:lang="cn" lang="cn">
<head>
<base href="${basePath}">
<title>欢迎登录</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="shortcut icon" type="image/x-icon" href="${ctx}/p/xlkfinance/extend/iconkey.ico" media="screen" />
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/xheditor/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/jquery.cookie.js"></script>
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/icon.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/zice.style.css">
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/tipsy.css">
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/buttons.css">
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/iphone.check.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/jquery-jrumble.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/jquery.tipsy.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/login.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/base64.js"></script>
<script type="text/javascript">
	if (top != self) {
		if (top.location != self.location)
			top.location = self.location;
	}
	$(document).ready(function(){
		$('body').css('height',document.documentElement.clientHeight+'px');
	});
</script>
<style type="text/css">
html {
	background-image: none;
}

label.iPhoneCheckLabelOn span {
	padding-left: 0px
}

#versionBar {
	background-color: #212121;
	position: fixed;
	width: 100%;
	height: 35px;
	bottom: 0;
	left: 0;
	text-align: center;
	line-height: 35px;
	z-index: 11;
	-webkit-box-shadow: black 0px 10px 10px -10px inset;
	-moz-box-shadow: black 0px 10px 10px -10px inset;
	box-shadow: black 0px 10px 10px -10px inset;
}

.copyright {
	text-align: center;
	font-size: 10px;
	color: #CCC;
}

.copyright a {
	color: #A31F1A;
	text-decoration: none
}
/*update-begin--Author:tanghong  Date:20130419 for：【是否】按钮错位*/
.on_off_checkbox {
	width: 0px;
}
/*update-end--Author:tanghong  Date:20130419 for：【是否】按钮错位*/
#login .logo {
	width: 500px;
	height: 51px;
}

</style>
</head>
<body>
	<div id="alertMessage"></div>
	<div id="successLogin"></div>
	<div class="text_success">
		<img src="${ctx}/p/xlkfinance/extend/loader_green.gif" alt="登录成功!请稍后...." /> <span>登陆成功!请稍后....</span>
	</div>
	<div id="login">
		<!-- 
		<div class="ribbon" style="background-image: url(${ctx}/p/xlkfinance/extend/typelogin.png);"></div>
		-->
		<div class="inner">
			<div class="logo" style="text-align:center;">
				<img src="${ctx}/p/xlkfinance/extend/login-logo.png" />
			</div>
			<div class="formLogin">

				<form name="formLogin" action="sysUserController/login.action"
					check="sysUserController/checkUserLogin.action" id="formLogin" method="post">
					<input name="userKey" type="hidden" id="userKey" value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900" />
					<div class="tip">
						<span class="usericon"></span>
						<input class="usercss" name="userName" type="text" id="userName"  title="用户名" iscookie="true"  nullmsg="请输入用户名!" />
						<input  name="userNameBase" type="hidden" id="userNameBase" />
					</div>
					<div class="tip">
						<span class="pwdicon"></span>
						<input class="pwdcss" name="password" type="password" id="password" title="密码"  nullmsg="请输入密码!" />
						<input name="passwordBase" type="hidden" id="passwordBase"/>
					</div>


				    <div id="cap" class="tip">
						<input class="captcha" style="float:left;" name="captcha" type="text" id="captcha" /><!--  nullmsg="请输入验证码!" -->
						<img style="width: 85px; height: 35px;float:right;" align="absmiddle" id="Kaptcha" src="${basePath}Kaptcha.jpg" />
						<div class="clear"></div>
					</div> 
					<div class="loginButton">
						<div style="float: left;width:95px;">
							<input type="checkbox" id="on_off" name="remember" checked="ture" 
							class="on_off_checkbox" value="0"/>
							<span class="f_help">是否记住用户名?
							</span>
						</div>
						<div style="float: right; padding: 3px 0;width:135px;">
							<div>
								<ul class="uibutton-group">
									<li><a class="uibutton normal" href="javascript:void(0);" id="but_login">登录</a></li>
									<li><a class="uibutton normal" href="javascript:void(0);" id="forgetpass">重置</a></li>
								</ul>
							</div>
							<!-- <div style="float: left; margin-left: 30px;">
								
							</div> -->
						</div>
						<div class="clear"></div>
					</div>
				</form>
			</div>
			<p style=" text-align: center;line-height: 30px;">强烈建议使用 “<a href="http://www.google.cn/intl/zh-CN/chrome/browser/desktop/index.html" target="_blank">谷歌浏览器</a>” 或者 “<a href="http://www.firefox.com.cn/download/"  target="_blank">火狐浏览器</a>” 访问本系统，不建议使用其他浏览器！</p>
		</div>
		<div class="shadow"></div>
	</div>
	<!--Login div-->
	<div class="clear"></div>
	<div id="versionBar">版权所有 © 深圳量度科技服务有限公司  未经许可不得复制、转载或摘编，违者必究！</div>
</body>
</html>
