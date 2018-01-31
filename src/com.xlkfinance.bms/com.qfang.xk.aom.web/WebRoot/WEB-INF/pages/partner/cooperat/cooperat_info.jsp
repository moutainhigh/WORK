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
</script>
</head>
<body class="gray-bg">
 <div class="wrapper wrapper-content animated">
  <div class="col-sm-12">
   <label> 合作状态：</label><a href="javascript:;"><b style="font-size: 16px; color:#ff9f00">${orgCooperateStatusMap[partnerInfo.cooperationStatus] }</b></a>
  </div>
  <div class="row">
     <div class="col-sm-12">
      <div class="ibox float-e-margins">
       <div class="ibox-title">
        <h5>合作信息：</h5>
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
     <label for="startTime" class="col-sm-2 control-label">合作开始时间:</label>
     <div class="col-sm-4">
      <input type="datetime" class="form-control" value="<comm:dateFormat pattern="yyyy-MM-dd" value="${partnerInfo.startTime }"/>" id="startTime" name="startTime" maxlength="20" disabled="disabled">
     </div>
     <label for="rate" class="col-sm-2 control-label">提成标准:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="${partnerInfo.rate }" id="rate" name="availableLimit" disabled="disabled" maxlength="20"><span class="percent">%</span>
     </div>
    </div>
    <div class="form-group">
     <label for="remark" class="col-sm-2 control-label">合作说明:</label>
     <div class="col-sm-10">
      <textarea rows="5" cols="10" class="form-control" name="remark" id="remark" maxlength="200" disabled="disabled">${partnerInfo.cooperationDesc}</textarea>
     </div>
    </div>
    <!-- 合作状态不显示，确认合作按钮 -->
    <c:if test="${partnerInfo.cooperationStatus!= 2}">
     <div class="form-group forum-info">
      <input type="button" class="btn btn-primary col-sm-2 col-sm-offset-5  an_bk" value="确认展开合作"
       onclick="confirmCooperat('${ctx}partnerController/confirmCooperat.action')"
      >
     </div>
    </c:if>
    </div>
    </div>
   </form>
  </div>
 </div>
 </div>
 </div>
 </div>
 <!-- 需要延迟加载的资源 -->
 <script src="${ctx }js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>