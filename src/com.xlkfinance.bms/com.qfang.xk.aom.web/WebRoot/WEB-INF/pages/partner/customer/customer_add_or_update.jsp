<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<%@ page language="java" import="com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>新增员工</title>
<meta name="keywords" content="">
<meta name="description" content="">
<%OrgAssetsCooperationInfo cooperationInfo=(OrgAssetsCooperationInfo)request.getAttribute("cooperationInfo"); %>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#orgForm")
								.validate(
										{
											rules : {
												"orgUserInfo.userName" : {
													required : true,
													checkLoginName : true,
													checkUserNameIsExist : true
												},
												"orgUserInfo.phone" : {
													required : true,
													isMobile : true,
													checkPhoneIsExist : true
												},
												"orgUserInfo.password" : {
													required : true,
													isPwd : true
												},
												confirmPassword : {
													required : true,
													isPwd : true,
													equalTo : "#orgUserInfo\\.password"
												},
												orgName : {
													required : true,
													maxlength :20
												},
												code : {
													required : true,
													checkOrgCodeIsExist : true
												},
												contact : {
													required : true,
													maxlength :20
												},
												phone : {
													required : true,
													isMobile : true
												},
												email : {
													required : true,
													email : true,
													checkEmailIsExist : true
												}
											},
											submitHandler : function(form) {
												var pid = $("#pid").val();
												var action = (pid != null && pid > 0) ? "updateOrg.action"
														: "addOrg.action";
												$
														.ajax({
															url : "${ctx}orgController/"
																	+ action,
															type : "POST",
															data : $("#orgForm")
																	.serialize(),
															async : false,
															success : function(
																	result) { //表单提交后更新页面显示的数据
																var ret = eval("("
																		+ result
																		+ ")");
																if (ret&& ret.header["success"]) {
																	layer.confirm(ret.header["msg"], {
																		icon : 6,
																		btn : [ '是' ]
																	//按钮
																	}, function() {
																		refreshTab("${ctx}partnerController/toCustomerIndex.action","${ctx}partnerController/toCustomerIndex.action");
																		<% if(cooperationInfo.getPid()==0){%>
    																	closeTab();
    																	<%}else{%>
    																	refreshPage();
    																	<%}%>
																	});
																	
																} else {
																	layer
																			.alert(
																					ret.header["msg"],
																					{
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
.row{margin-left: -5px; margin-right: -5px;}
</style>
</head>
<body class="gray-bg">
 <div class="wrapper wrapper-content animated fadeInRight">
  <div class="row">
   <div class="ibox float-e-margins">
    <div class="ibox-title">
     <h5><c:if test="${cooperationInfo.pid==0}">增加客户</c:if>
         <c:if test="${cooperationInfo.pid>0}">编辑客户</c:if>
         </h5>
     <div class="ibox-tools">
      <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
      </a>
     </div>
    </div>
    <div class="ibox-content ibox-line">
     <div class="row">
     
     <c:if test="${cooperationInfo.pid>0}">
      <div class="col-sm-12">
       <label>认证状态：</label><b style="font-size: 16px; color:#ff9f00">${orgAuditStatusMap[cooperationInfo.auditStatus] } </b><br> <label> 合作状态：</label><b style="font-size: 16px; color:#ff9f00">${orgCooperateStatusMap[cooperationInfo.cooperateStatus] }</b>
       <hr>
      </div>
      </c:if>
      <div class="col-sm-9 col-sm-offset-1">
      <div class="col-sm-12">
       <label> 机构信息：</label>
       <form role="form" id="orgForm" class="form-horizontal" action="#" method="post">
        <div class="form-group">
         <label for="orgUserInfo.userName" class="col-sm-2 control-label"><span class="requiredSty">*</span>登录名:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${cooperationInfo.orgUserInfo.userName }" id="orgUserInfo.userName" name="orgUserInfo.userName"
           maxlength="20" placeholder="登录名" <c:if test="${cooperationInfo.pid>0}">disabled='disabled'</c:if>
          >
         </div>
         <label for="orgUserInfo.phone" class="col-sm-2 control-label"><span class="requiredSty">*</span>手机号码:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${cooperationInfo.orgUserInfo.phone }" maxlength="20" id="orgUserInfo.phone" name="orgUserInfo.phone"
           placeholder="手机号码" <c:if test="${cooperationInfo.pid>0}">disabled='disabled'</c:if>
          >
         </div>
        </div>
        <c:if test="${cooperationInfo.pid==0}">
         <div class="form-group">
          <label for="orgUserInfo.password" class="col-sm-2 control-label"><span class="requiredSty">*</span>设置密码:</label>
          <div class="col-sm-4">
           <input type="password" class="form-control" value="${orgUserInfo.password }" id="orgUserInfo.password" name="orgUserInfo.password" maxlength="20"
            placeholder="设置密码"
           >
          </div>
          <label for="confirmPassword" class="col-sm-2 control-label"><span class="requiredSty">*</span>确认密码:</label>
          <div class="col-sm-4">
           <input type="password" class="form-control" value="${confirmPassword }" maxlength="20" id="confirmPassword" name="confirmPassword" placeholder="确认密码">
          </div>
         </div>
        </c:if>
        <div class="form-group">
         <label for="orgName" class="col-sm-2 control-label"><span class="requiredSty">*</span>机构名称:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${cooperationInfo.orgName }" id="orgName" name="orgName" maxlength="20" placeholder="机构名称" <c:if test="${cooperationInfo.pid>0}">disabled='disabled'</c:if>>
         </div>
         <label for="code" class="col-sm-2 control-label"><span class="requiredSty">*</span>统一社会信用代码:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${cooperationInfo.code }" maxlength="20" id="code" name="code" placeholder="统一社会信用代码" <c:if test="${cooperationInfo.pid>0}">disabled='disabled'</c:if>>
         </div>
        </div>
<%--         <div class="form-group">
         <label for="contact" class="col-sm-2 control-label"><span class="requiredSty">*</span>联系人:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${cooperationInfo.contact }" id="contact" name="contact" placeholder="联系人" maxlength="20" <c:if test="${cooperationInfo.auditStatus==3}">disabled='disabled'</c:if>>
         </div>
         <label for="phone" class="col-sm-2 control-label"><span class="requiredSty">*</span>联系电话:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${cooperationInfo.phone }" id="phone" name="phone" placeholder="联系电话" maxlength="30" <c:if test="${cooperationInfo.auditStatus==3}">disabled='disabled'</c:if>>
         </div>
        </div> --%>
        <div class="form-group">
         <label for="email" class="col-sm-2 control-label"><span class="requiredSty">*</span>公司邮箱:</label>
         <div class="col-sm-4">
          <input type="text" class="form-control" value="${cooperationInfo.email }" id="email" name="email" placeholder="公司邮箱" maxlength="30" <c:if test="${cooperationInfo.pid>0}">disabled='disabled'</c:if>>
         </div>
        </div>
        <c:if test="${cooperationInfo.auditStatus==1||cooperationInfo.pid==0 }">
        <div class="form-group forum-info">
         <input type="submit" class="btn btn-primary an_bk" value="提交" name="commit"> <input type="reset"
          class="btn btn-w-m btn-white cz_an" value="重置" name="commit"
         >
        </div>
        </c:if>
        <input type="hidden" value="${cooperationInfo.pid }" name="pid" id="pid">
       </form>
       </div>
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