<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--360浏览器优先以webkit内核解析-->
<title>申请加入</title>
<%@ include file="../../../../common.jsp"%>
<link rel="shortcut icon" href="favicon.ico">
<style>
li {
	list-style-type: none;
}
</style>
<script type="text/javascript">
	function subApplyCooperat() {
		$.ajax({
			url : "${ctx}orgController/subApplyCooperat.action",
			type : "POST",
			data : {},
			async : false,
			success : function(result) { //表单提交后更新页面显示的数据
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					layer.confirm('提交成功，刷新页面？', {
						icon : 6,
						btn : [ '是' ]
					//按钮
					}, function() {
						refreshPage();
					});
				} else {
					layer.alert(ret.header["msg"], {
						icon : 5
					});
				}
			}
		});
	}
</script>
</head>
<body class="gray-bg">
 <div class="wrapper wrapper-content animated">
  <div class="col-sm-12">
   <label> 合作状态：</label><a href="javascript:;"><b style="font-size: 16px; color:#ff9f00">${orgCooperateStatusMap[cooperationInfo.cooperateStatus] }</b></a>
  </div>
  
  <div class="row">
     <div class="col-sm-12">
      <div class="ibox float-e-margins">
       <div class="ibox-title">
        <h5>机构信息：</h5>
        <div class="ibox-tools">
         <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
         </a>
        </div>
       </div>
       <div class="ibox-content ibox-line">
   <form role="form" id="cooperationInfoForm" class="form-horizontal" action="#" method="post">
   <div class="row">
   <div class="col-sm-9 col-sm-offset-1">
	    <div class="form-group">
	     <label for="orgName" class="col-sm-2 control-label">机构名称:</label>
	     <div class="col-sm-4">
	      <input type="text" class="form-control" value="${cooperationInfo.orgName }" id="orgName" name="orgName" maxlength="20" disabled="disabled"
	       placeholder="登录名">
	     </div>
	     <label for="code" class="col-sm-2 control-label">统一社会信用代码:</label>
	     <div class="col-sm-4">
	      <input type="text" class="form-control" value="${cooperationInfo.code }" maxlength="20" id="code" name="code" disabled="disabled" placeholder="手机号">
	     </div>
	    </div>
	    <div class="form-group">
	     <label for="contact" class="col-sm-2 control-label">联系人:</label>
	     <div class="col-sm-4">
	      <input type="text" class="form-control" value="${cooperationInfo.contact }" id="contact" name="contact" disabled="disabled" placeholder="真实姓名"
	       maxlength="20"
	      >
	     </div>
	     <label for="phone" class="col-sm-2 control-label">联系电话:</label>
	     <div class="col-sm-4">
	      <input type="text" class="form-control" value="${cooperationInfo.phone }" id="phone" name="phone" placeholder="邮箱" disabled="disabled" maxlength="30">
	     </div>
	    </div>
	    <div class="form-group">
	     <label for="email" class="col-sm-2 control-label">邮 箱:</label>
	     <div class="col-sm-4">
	      <input type="text" class="form-control" value="${cooperationInfo.email }" id="email" name="email" disabled="disabled" placeholder="工号" maxlength="20">
	     </div>
	    </div>
    </div>
    </div>
    <c:if test="${cooperationInfo.cooperateStatus==1||cooperationInfo.cooperateStatus==5 }">
  <div class="col-sm-12">
   <!-- <br> <input type="button" class="btn btn-primary col-sm-2 col-sm-offset-3" value="保存" name="commit">  --><input type="button"
    class="btn btn-primary col-sm-2 col-sm-offset-5 an_bk" onclick="subApplyCooperat()" value="提交申请" name="commit"
   >
  </div>
  </c:if>
   </form>
  </div>
  </div>
  </div>
  </div>
  <%-- <div class="col-sm-12">
   附加信息：
   <div class="tabs-container">
    <div class="tabs-left">
     <ul class="nav nav-tabs">
      <li class="active"><a data-toggle="tab" href="#tab-1">财务信息</a></li>
      <li class=""><a data-toggle="tab" href="#tab-2">员工机构</a></li>
     </ul>
     <div class="tab-content ">
      <div id="tab-1" class="tab-pane active">
       <div class="panel-body">
        <form role="form" id="cooperationInfoForm" class="form-horizontal" action="#" method="post">
         <div class="form-group">
          <label for="orgName" class="col-sm-2 control-label">企业名称:</label>
          <div class="col-sm-3">
           <input type="text" class="form-control input-sm" value="${cooperationInfo.orgName }" id="orgName" name="orgName" maxlength="20" placeholder="登录名">
          </div>
          <label for="code" class="col-sm-2 control-label">客户经理:</label>
          <div class="col-sm-3">
           <input type="text" class="form-control input-sm" value="${cooperationInfo.code }" maxlength="20" id="code" name="code" placeholder="手机号">
          </div>
         </div>
        </form>
       </div>
      </div>
      <div id="tab-2" class="tab-pane">
       <div class="panel-body">
        <form role="form" id="cooperationInfoForm" class="form-horizontal" action="#" method="post">
         <div class="form-group">
          <label for="orgName" class="col-sm-2 control-label">企业地址:</label>
          <div class="col-sm-3">
           <input type="text" class="form-control input-sm" value="${cooperationInfo.orgName }" id="orgName" name="orgName" maxlength="20" placeholder="登录名">
          </div>
          <label for="code" class="col-sm-2 control-label">企业规模:</label>
          <div class="col-sm-3">
           <input type="text" class="form-control input-sm" value="${cooperationInfo.code }" maxlength="20" id="code" name="code" placeholder="手机号">
          </div>
         </div>
        </form>
       </div>
      </div>
     </div>
    </div>
   </div>
  </div> --%>
  
 </div>
 <!-- 需要延迟加载的资源 -->
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>