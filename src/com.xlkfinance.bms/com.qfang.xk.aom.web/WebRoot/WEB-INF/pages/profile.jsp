<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>个人资料</title>
<%@ include file="../../common.jsp"%>
<link rel="shortcut icon" href="favicon.ico">
<script type="text/javascript">
$(document).ready(function() {
	$("#pwdForm").validate({
		rules : {
			oldPwd : {
				required : true,
				isPwd : true
			},
			newPwd : {
				required : true,
				isPwd : true
			},
			confirmPwd : {
				required : true,
				isPwd : true,
				equalTo: "#newPwd"
			}
		},
		submitHandler : function(form) {
			$.ajax({
				url : "${ctx}sysOrgUserController/updatePwd.action",
				type : "POST",
				data : $("#pwdForm").serialize(),
				async : false,
				success : function(result) { //表单提交后更新页面显示的数据
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						layer.alert(ret.header["msg"], {
							icon : 6
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
});
</script>
</head>
<body class="gray-bg">
 <div class="wrapper wrapper-content">
  <div class="row animated fadeInRight">
   <div class="col-sm-12">
    <div class="ibox float-e-margins">
     <div class="ibox-title">
      <h5>个人资料</h5>
      <div class="ibox-tools">
       <a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
       <!-- <a class="close-link"> <i class="fa fa-times"></i> -->
       </a>
      </div>
     </div>
     <div class="ibox-content ibox-line">
      <form role="form" id="loginUserForm" class="form-horizontal" action="#" method="post">
      <div class="row">
      <div class="col-sm-9 col-sm-offset-1">
       <input type="hidden" name="pid" id="pid" value="${loginUser.pid }">
       <div class="form-group">
        <label for="userName" class="col-sm-2 control-label">登录名:</label>
        <div class="col-sm-4">
         <input type="text" class="form-control" value="${loginUser.userName }" id="userName" name="userName" maxlength="20" disabled="disabled"
          placeholder="登录名"
         >
        </div>
        <label for="phone" class="col-sm-2 control-label">手机号码:</label>
        <div class="col-sm-4">
         <input type="text" class="form-control" value="${loginUser.phone }" maxlength="20" id="phone" name="phone" disabled="disabled" placeholder="手机号">
        </div>
       </div>
       <div class="form-group">
        <label for="realName" class="col-sm-2 control-label">真实姓名:</label>
        <div class="col-sm-4">
         <input type="text" class="form-control" value="${loginUser.realName }" id="realName" name="realName" disabled="disabled" placeholder="真实姓名"
          maxlength="20"
         >
        </div>
        <label for="email" class="col-sm-2 control-label">邮箱:</label>
        <div class="col-sm-4">
         <input type="text" class="form-control" value="${loginUser.email }" id="email" name="email" placeholder="邮箱" disabled="disabled" maxlength="30">
        </div>
       </div>
       <div class="form-group">
        <label for="jobNo" class="col-sm-2 control-label">工号:</label>
        <div class="col-sm-4">
         <input type="text" class="form-control" value="${loginUser.jobNo }" id="jobNo" name="jobNo" disabled="disabled" placeholder="工号" maxlength="20">
        </div>
        <label for="deptName" class="col-sm-2 control-label">部门:</label>
        <div class="col-sm-4">
         <input type="text" class="form-control" value="${loginUser.deptName }" id="deptName" name="deptName" disabled="disabled" placeholder="部门"
          maxlength="30"
         >
        </div>
       </div>
       <div class="form-group">
        <label for="remark" class="col-sm-2 control-label">备注:</label>
        <div class="col-sm-10">
         <textarea rows="1" cols="10" class="form-control" name="remark" id="remark" placeholder="备注" maxlength="200" disabled="disabled">${loginUser.remark}</textarea>
        </div>
       </div>
       </div>
       </div>
      </form>
      <form role="form" id="pwdForm" action="#" method="post" class="form-horizontal">
      <div class="row">
      <div class="col-sm-9 col-sm-offset-1">
       <div class="form-group">
        <label for="oldPwd" class="col-sm-2 control-label"><span class="requiredSty">*</span>旧密码:</label>
        <div class="col-sm-10"><input type="password" class="form-control" name="oldPwd" id="oldPwd"></div>
       </div>
       <div class="form-group">
        <label for="newPwd" class="col-sm-2 control-label"><span class="requiredSty">*</span>新密码:</label>
        <div class="col-sm-10"><input type="password" class="form-control" name="newPwd" id="newPwd"></div>
       </div>
       <div class="form-group">
        <label for="confirmPwd" class="col-sm-2 control-label"><span class="requiredSty">*</span>确认密码:</label>
        <div class="col-sm-10"><input type="password" class="form-control" name="confirmPwd" id="confirmPwd"></div>
       </div>
		</div>
		</div>
       <div class="form-group forum-info" style=" padding-top: 10px;">
        <input type="submit" class="btn btn-primary an_bk" value="修改密码" name="commit">
         <input type="reset" class="btn btn-w-m btn-white cz_an" value="重置" name="commit">
       </div>
      </form>
      
     </div>
    </div>

   </div>
  </div>
 </div>
 <%@ include file="../../delayLoadCommon.jsp"%>

</body>
</html>