<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<%@ page language="java" import="com.qfang.xk.aom.rpc.system.OrgUserInfo" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>新增员工</title>
<meta name="keywords" content="">
<meta name="description" content="">
<%OrgUserInfo updateUser=(OrgUserInfo)request.getAttribute("userInfo"); %>
<script type="text/javascript">
	$(document).ready(function() {
		$("#userInfoForm").validate({
			rules : {
				<% if(updateUser==null){%>
				userName : {
					required : true,
				 	checkLoginName : true,
					checkUserNameIsExist : true
				},
				phone : {
					required : true,
					isMobile : true,
					checkPhoneIsExist : true
				},<%}%>
				realName : {
					required : true,
				 	maxlength :20
				},
				status : {
					required : true
				},
				jobNo : {
					required : true,
					maxlength :20
				},
				deptName : {
					required : true,
					maxlength :20
				},
				email : {
					required : true,
					email : true,
					checkEmailIsExist : true
				}
				<% if(updateUser==null){%>
				,
				password : {
					required : true,
					isPwd : true
				}
				<%}%>
			},
			submitHandler : function(form) {
				$.ajax({
					url : "${ctx}empController/addOrUpdate.action",
					type : "POST",
					data : $("#userInfoForm").serialize(),
					async : false,
					success : function(result) { //表单提交后更新页面显示的数据
						var ret = eval("(" + result + ")");
						if (ret && ret.header["success"]) {
							layer.confirm(ret.header["msg"], {
								icon : 6,
								btn : [ '是' ]
							//按钮
							}, function() {
								refreshTab("${ctx}empController/toEmpIndex.action","${ctx}empController/toEmpIndex.action");
								<% if(updateUser==null){%>
								closeTab();
								<%}else{%>
								refreshPage();
								<%}%>
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
<style>
.row {
    margin-right: -5px; 
    margin-left:-5px;
}
</style>
</head>
<body class="gray-bg">
 <div class="wrapper wrapper-content animated fadeInRight">
  <div class="row">
   <div class="ibox float-e-margins">
    <div class="ibox-title">
     <c:if test="${userInfo eq null}"><h5>增加员工</h5></c:if>
     <c:if test="${userInfo.pid>0}"><h5>编辑员工</h5></c:if>
     <div class="ibox-tools">
      <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
      </a> 
     </div>
    </div>
    <div class="ibox-content ibox-line">
     <div class="row">
      <div class="col-sm-9 col-sm-offset-1">
       <form role="form" id="userInfoForm" class="form-horizontal" action="#" method="post">
        <% if(updateUser!=null){%>
        <input type="hidden" name="pid" id="pid" value="${userInfo.pid }">
        <%} %>
        <div class="form-group">
         <label for="userName" class="col-sm-2 control-label"><span class="requiredSty">*</span>登录名:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${userInfo.userName }" id="userName" name="userName" maxlength="20"
           <c:if test="${userInfo.pid>0}">readonly="readonly"</c:if> placeholder="登录名"
          >
         </div>
         <label for="phone" class="col-sm-2 control-label"><span class="requiredSty">*</span>手机号码:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${userInfo.phone }" maxlength="20" id="phone" name="phone" <c:if test="${userInfo.pid>0}">readonly="readonly"</c:if>
           placeholder="手机号"
          >
         </div>
        </div>
        <div class="form-group">
         <label for="realName" class="col-sm-2 control-label"><span class="requiredSty">*</span>真实姓名:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${userInfo.realName }" id="realName" name="realName" placeholder="真实姓名" maxlength="20">
         </div>
         <label for="status" class="col-sm-2 control-label"><span class="requiredSty">*</span>状态:</label>
         <div class="col-sm-4">
          <select class="form-control" name="status" id="status">
           <option value=1 <c:if test="${userInfo.status==1 }">selected="selected"</c:if>>启用</option>
           <option value=2 <c:if test="${userInfo.status==2 }">selected="selected"</c:if>>禁用</option>
          </select>
         </div>
        </div>
        <div class="form-group">
         <label for="jobNo" class="col-sm-2 control-label"><span class="requiredSty">*</span>工号:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${userInfo.jobNo }" id="jobNo" name="jobNo" placeholder="工号" maxlength="20">
         </div>
         <label for="deptName" class="col-sm-2 control-label"><span class="requiredSty">*</span>部门:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${userInfo.deptName }" id="deptName" name="deptName" placeholder="部门" maxlength="30">
         </div>
        </div>
        <div class="form-group">
         <label for="email" class="col-sm-2 control-label"><span class="requiredSty">*</span>邮箱:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" <c:if test="${userInfo.pid>0}">readonly="readonly"</c:if> value="${userInfo.email }" id="email" name="email" placeholder="邮箱" maxlength="30">
         </div>
         <% if(updateUser==null){%>
         <label for="password" class="col-sm-2 control-label"><span class="requiredSty">*</span>密码:</label>
         <div class="col-sm-4">
          <input type="password" class="form-control" id="password" name="password" placeholder="密码"  maxlength="30">
         </div>
         <%} %>
        </div>
        <div class="form-group">
         <label for="remark" class="col-sm-2 control-label">备注:</label>
         <div class="col-sm-10">
          <textarea rows="1" cols="10" class="form-control" name="remark" id="remark" placeholder="备注"  maxlength="200">${userInfo.remark}</textarea>
         </div>
        </div>
        <div class="form-group forum-info">
         <input type="submit" class="btn btn-primary an_bk" value="提交" name="commit">
         <input type="reset" class="btn btn-w-m btn-white cz_an" value="重置" name="commit">
        </div>
       </form>
      </div>
     </div>
    </div>
   </div>

  </div>

 </div>
 <!-- 需要延迟加载的资源 -->
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>