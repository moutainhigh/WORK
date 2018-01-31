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
	$(document).ready(function() {
		$('#file-pretty input[type="file"]').prettyFile();
		applyOrgAssureFund();
	});
	function openContractUploadModal(contractType){
		$("#fileUploadForm").attr("action","${ctx}orgController/uploadOrgCooperationContract.action?"+"contractType="+contractType)
		$('#fileUploadModal').modal('show');
	}
	/* //保证金调整申请
	function applyOrgAssureFund(){
		$("#assureMoneyForm").validate({
			rules : {
				updateAssureMoney : {
					required : true,
					isFloatGtZero : true,
					isFloat : true
				},
			},
			submitHandler : function(form) {
				$.ajax({
					url : "${ctx}orgController/applyOrgAssureFund.action",
					type : "POST",
					data : $("#assureMoneyForm").serialize(),
					async : false,
					success : function(result) { //表单提交后更新页面显示的数据
						var ret = eval("(" + result + ")");
						if (ret && ret.header["success"]) {
							layer.confirm('提交成功', {
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
		})
	} */
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
     <label for="startTime" class="col-sm-2 control-label">合作开始时间:</label>
     <div class="col-sm-4">
      <input type="datetime" class="form-control" value="${applyInf.startTime }" id="startTime" name="startTime" maxlength="20" disabled="disabled"
       placeholder="合作开始时间"
      >
     </div>
     <label for="endTime" class="col-sm-2 control-label">合作结束时间:</label>
     <div class="col-sm-4">
      <input type="datetime" class="form-control" value="${applyInf.endTime }" maxlength="20" id="endTime" name="endTime" disabled="disabled"
       placeholder="合作结束时间"
      >
     </div>
    </div>
    <div class="form-group">
     <label for="creditLimit" class="col-sm-2 control-label">授信额度:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="<fmt:formatNumber pattern="###,###.##" value='${applyInf.creditLimit }'/>" id="creditLimit"
       name="creditLimit" disabled="disabled" placeholder="授信额度" maxlength="20"
      >
     </div>
     <label for="assureMoney" class="col-sm-2 control-label">保证金:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="<fmt:formatNumber pattern="###,###.##" value='${applyInf.assureMoney }'/>" id="assureMoney"
       name="assureMoney" placeholder="保证金" disabled="disabled" maxlength="30"
      >
     </div>
    </div>
    <div class="form-group">
     <label for="availableLimit" class="col-sm-2 control-label">可用额度:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="<fmt:formatNumber pattern="###,###.##" value='${applyInf.availableLimit }'/>" id="availableLimit"
       name="availableLimit" disabled="disabled" placeholder="授信额度" maxlength="20"
      >
     </div>
      <label for="realAssureMoney" class="col-sm-2 control-label">实收保证金:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="<fmt:formatNumber pattern="###,###.##" value='${applyInf.realAssureMoney }'/>" id="realAssureMoney"
       name="realAssureMoney" placeholder="保证金" disabled="disabled" maxlength="30"
      >
     </div>
    </div>
    <div class="form-group">
     <label for="assureMoneyProportion" class="col-sm-2 control-label">保证金比例:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="${applyInf.assureMoneyProportion }%" id="assureMoneyProportion"
       name="assureMoneyProportion" disabled="disabled" placeholder="保证金比例" maxlength="20"
      >
     </div>
      <label for="activateCreditLimit" class="col-sm-2 control-label">启用授信额度:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="<fmt:formatNumber pattern="###,###.##" value='${applyInf.activateCreditLimit }'/>" id="activateCreditLimit"
       name="activateCreditLimit" placeholder="启用授信额度" disabled="disabled" maxlength="30"
      >
     </div>
    </div>
    <div class="form-group">
     <label for="remark" class="col-sm-2 control-label">合作说明:</label>
     <div class="col-sm-10">
      <textarea rows="1" cols="10" class="form-control" name="remark" id="remark" placeholder="备注" maxlength="200" disabled="disabled">${applyInf.remark}</textarea>
     </div>
    </div>
    </div>
    </div>
    <!-- 合作状态不显示，确认合作按钮 -->
    <c:if test="${cooperationInfo.cooperateStatus!= 2}">
     <div class="form-group">
      <input type="button" class="btn btn-primary col-sm-2 col-sm-offset-5 an_bk" value="确认展开合作"
       onclick="confirmCooperat('${ctx}orgController/confirmCooperat.action')"
      >
     </div>
    </c:if>
   </form>
  </div>
  </div>
  </div>
  </div>
  <div class="row">
     <div class="col-sm-12">
      <div class="ibox float-e-margins">
       <div class="ibox-title">
        <h5 class="attachment">附件：</h5>
        <div class="ibox-tools">
         <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
         </a>
        </div>
       </div>
       <div class="ibox-content ibox-line">
  
   <table class="table">
    <thead>
     <tr>
      <th>序号</th>
      <th>合同附件</th>
      <th>上传时间</th>
      <th>操作</th>
     </tr>
    </thead>
    <tbody>
    
     <c:forEach items="${orgContractList }" var="orgContract" varStatus="status">
     <c:if test="${orgContract.file!=null }">
      <tr>
       <td>${ status.index + 1}</td>
       <td>${orgContract.contractName }</td>
       <td>${orgContract.updateDate }</td>
       <td>
         <a onclick="downLoadFileByPath('${orgContract.file.fileUrl}','${orgContract.contractName}')" >下载</a>
         <c:if test="${fn:toLowerCase(orgContract.file.fileType)=='bmp'||fn:toLowerCase(orgContract.file.fileType)=='jpeg'||fn:toLowerCase(orgContract.file.fileType)=='jpg'||fn:toLowerCase(orgContract.file.fileType)=='png'||fn:toLowerCase(orgContract.file.fileType)=='gif'}">
	     <a onclick="lookUpFileByPath('${orgContract.file.fileUrl}','${orgContract.contractName }')">预览</a>
	      </c:if>
        </td>
      </tr>
      </c:if>
     </c:forEach>
    </tbody>
   </table>
   <div class="modal fade" id="fileUploadModal" tabindex="-1" role="dialog" aria-labelledby="fileUploadModalLabel" aria-hidden="true">
    <div class="modal-dialog">
     <div class="modal-content animated flipInY">
      <div class="modal-header">
       <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
       <h4 class="modal-title" id="fileUploadModalLabel">文件上传</h4>
      </div>
      <form id="fileUploadForm" name="fileUploadForm" method="post">
       <div class="modal-body">
        <div id="file-pretty">
         <div class="form-group">
          <label class="font-noraml">文件选择（单选）</label> <input type="file"
           accept=".txt,.doc,.pdf,.bmp,.jpeg,.jpg,.png,.gif,.xls,.wps,.rtf,.docx,.xlsx,.zip,.ral,.pptx,.tiff,.tif,.rar" class="form-control"
           name="offlineMeetingFile" id="offlineMeetingFiles"
          > 
         </div>
        </div>
        <div id="fileUploadProgressTtriped" class="progress progress-striped active" style="display: none;">
         <div aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;" role="progressbar" id="fileUploadProgressBar"
          class="progress-bar progress-bar-success"
         >
          <span class="sr-only">0% Complete (success)</span>
         </div>
        </div>
       </div>
       <div class="modal-footer">
        <button type="button" class="btn btn-primary an_bk" onclick="submitFileForm('#fileUploadForm')">提交</button>
        <button type="button" class="btn btn-w-m btn-white cz_an" data-dismiss="modal">关闭</button>
       </div>
      </form>
     </div>
    </div>
   </div>
  </div>
  </div>
  </div>
  </div>
  <%-- <c:if test="${cooperationInfo.cooperateStatus== 2 }">
   <div class="row">
     <div class="col-sm-12">
      <div class="ibox float-e-margins">
       <div class="ibox-title">
        <h5>保证金调整：</h5>
        <div class="ibox-tools">
         <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
         </a>
        </div>
       </div>
       <div class="ibox-content ibox-line">
    <form role="form" id="assureMoneyForm" class="form-horizontal" action="#" method="post">
    <div class="col-sm-12">
   <label> 调整状态:</label><a href="javascript:;"><b style="font-size: 16px; color:#ff9f00">${fundFlowApplyStatusMap[applyInf.status] }</b></a>
  </div>
    <div class="row">
   <div class="col-sm-9 col-sm-offset-1">

     <div class="form-group">
      <label for="assureMoney" class="col-sm-2 control-label">原始保证金:</label>
      <div class="col-sm-4">
       <input type="text" class="form-control" value="<fmt:formatNumber pattern="###,###.##" value='${applyInf.assureMoney }'/>" id="assureMoney"
        name="assureMoney" maxlength="20" disabled="disabled" placeholder="原始保证金"
       >
      </div>
      <label for="endTime" class="col-sm-2 control-label">变更保证金:</label>
      <div class="col-sm-4">
       <input type="text" class="form-control" maxlength="20" id="updateAssureMoney" name="updateAssureMoney" placeholder="变更保证金">
      </div>
     </div>
     <div class="form-group forum-info" style=" padding-top: 10px;">
     <input type="submit" class="btn btn-primary an_bk" value="提交申请" name="commit" style="margin-bottom: 5px;">
     <input type="reset" class="btn btn-w-m btn-white cz_an" value="重置" name="commit">
     </div>
     </div>
     </div>
    </form>
   </div>
   </div>
   </div>
   </div>
  </c:if> --%>
 </div>
 <!-- 需要延迟加载的资源 -->
 <script src="${ctx }js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>