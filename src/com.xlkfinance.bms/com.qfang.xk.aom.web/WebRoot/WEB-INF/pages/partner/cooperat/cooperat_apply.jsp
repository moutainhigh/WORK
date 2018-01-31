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
		initUploadify();
		fileUploadModalListener();
		$("#cooperationInfoForm").validate({
			rules : {
				cardNo : {
					required : true,
					maxlength :20,
					isIdCardNo : true
				},
				payeeAccount : {
					required : true,
					maxlength :20,
					isEnglishNumber : true
				}
			},
			submitHandler : function(form) {
				$.ajax({
					url : "${ctx}partnerController/subApplyCooperat.action",
					type : "POST",
					data : $("#cooperationInfoForm").serialize(),
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
		})
	});
	//文件上传对话框监听器
	function fileUploadModalListener() {
		$('#fileUploadModal').on('hide.bs.modal', function () {
			if($("#fileQueue .fileName") != null && $("#fileQueue .fileName").length  > 0 ){
				if(window.confirm('关闭窗口会取消未上传文件，是否关闭？')){
			        $('#uploadify').uploadify('cancel','*');
	                return true;
	             }else{
					$('#fileUploadModal').modal('show');
	                return false;
	            }
			}
			});
	}
	function initUploadify() {
		//多文件上传  //兼容火狐 需要  sessionid，否则拦截登陆302
		var uploadFileCount = 0 ;
	    $("#uploadify").uploadify({  
	        'swf'       : '${ctx}js/plugins/uploadify/uploadify.swf',  
	        'uploader'  : '${ctx}sysOrgUserController/uploadUserFile.action;jsessionid=${pageContext.session.id}',    
	        //'folder'         : '/upload',  
	        'queueID'        : 'fileQueue',
	        'cancelImg'      : '${ctx}js/plugins/uploadify/css/img/uploadify-cancel.png',
	        'buttonText'     : '上传文件',
	        'auto'           : false, //设置true 自动上传 设置false还需要手动点击按钮 
	        'multi'          : true,  
	        'wmode'          : 'transparent',  
	        'simUploadLimit' : 9,  
	        'fileSizeLimit'   : '200MB',
	        'fileTypeExts'        : '*.txt;*.doc;*.pdf;*.bmp;*.jpeg;*.jpg;*.png;*.gif;*.xls;*.wps;*.rtf;*.docx;*.xlsx;*.zip;*.ral;*.pptx;*.tiff;*.tif;*.rar',  
	        'fileTypeDesc'       : 'All Files',
	        'method'       : 'get',
			onUploadSuccess : function(file, data, response) {
				 uploadFileCount++;
			},
			onQueueComplete : function(file) { //当队列中的所有文件全部完成上传时触发 
				layer.confirm("上传完成"+uploadFileCount+"个文件，刷新页面？", {
					icon : 6,
					btn : [ '是' ,'否']
				//按钮
				}, function() {
					refreshPage();
				}, function() {
				});
				uploadFileCount = 0;
			}
		});
	}
	//打开上传文件对口框
	function openUploadFileModal(){
		$('#fileUploadModal').modal('show');
	}
	//上传文件
	function uploadFile(){
		if($("#fileQueue .fileName") == null || $("#fileQueue .fileName").length  == 0 ){
			layer.alert("请选择文件", {icon : 5});
			return false;
		}
		//多文件上传方式
		$('#uploadify').uploadify('settings','formData',{		});
		$('#uploadify').uploadify('upload','*');
	}
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
        <h5>合伙人信息：</h5>
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
     <label for="orgUserInfo.realName" class="col-sm-2 control-label">合伙人名称:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="${partnerInfo.orgUserInfo.realName }" id="orgUserInfo.realName" name="orgUserInfo.realName" maxlength="20"
       disabled="disabled" placeholder="登录名"
      >
     </div>
     <label for="cardNo" class="col-sm-2 control-label"><span class="requiredSty">*</span>身份证:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="${partnerInfo.cardNo }" maxlength="20" id="cardNo" name="cardNo" placeholder="身份证">
     </div>
    </div>
    <div class="form-group">
     <label for="orgUserInfo.phone" class="col-sm-2 control-label">手机号码:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="${partnerInfo.orgUserInfo.phone }" id="orgUserInfo.phone" name="orgUserInfo.phone" placeholder="手机号码"
       disabled="disabled" maxlength="30"
      >
     </div>
     <label for="orgUserInfo.email" class="col-sm-2 control-label">邮 箱:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="${partnerInfo.orgUserInfo.email }" id="orgUserInfo.email" name="orgUserInfo.email" disabled="disabled"
       placeholder="邮 箱" maxlength="20"
      >
     </div>
    </div>
    <div class="form-group">
      <label for="payeeAccount" class="col-sm-2 control-label"><span class="requiredSty">*</span>收款账号:</label>
     <div class="col-sm-4">
      <input type="text" class="form-control" value="${partnerInfo.payeeAccount }" id="payeeAccount" name="payeeAccount" 
       placeholder="收款账号" maxlength="20"
      >
     </div>
    </div>
    <div class="form-group">
     <label for="remark" class="col-sm-2 control-label">备注:</label>
     <div class="col-sm-10">
      <textarea rows="1" cols="10" class="form-control" name="remark" id="remark" placeholder="备注" maxlength="200">${partnerInfo.remark}</textarea>
     </div>
    </div>
    <c:if test="${partnerInfo.cooperationStatus==1 && partnerInfo.status==3 }">
    <div class="form-group">
     <a onclick="openUploadFileModal()" class="btn btn-primary left_bj" >上传资料</a>
    </div>
    </c:if>
   </div>
   </div>
   <c:if test="${partnerInfo.cooperationStatus==1 || partnerInfo.cooperationStatus==30}">
    <div class="form-group forum-info" style="padding-top: 10px;">
     <input type="submit" class="btn btn-primary an_bk" value="提交申请" name="commit" style="margin-bottom: 5px;">
     <input type="reset" class="btn btn-w-m btn-white cz_an" value="重置" name="commit">
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
        <h5 class="attachment">附件： </h5>
        <div class="ibox-tools">
         <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
         </a>
        </div>
       </div>
       <div class="ibox-content ibox-line">
  <div class="col-sm-12" style="height:300px; overflow-y: auto;">
   <table class="table">
    <thead>
     <tr>
      <th>序号</th>
      <th>资料附件</th>
      <th>上传时间</th>
      <th>类型</th>
      <th>操作</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${userFileList }" var="item" varStatus="status">
      <tr>
       <td>${ status.index + 1}</td>
       <td>${item.file.fileName }</td>
       <td>${item.file.uploadDttm }</td>
       <td>${item.file.fileType }</td>
       <td><c:if test="${item.file!=null }">
         <a onclick="downLoadFileByPath('${item.file.fileUrl }','${fn:replace(item.file.fileName,' ', '')}')" href="#">下载</a>
        </c:if>
        <c:if test="${fn:toLowerCase(item.file.fileType)=='bmp'||fn:toLowerCase(item.file.fileType)=='jpeg'||fn:toLowerCase(item.file.fileType)=='jpg'||fn:toLowerCase(item.file.fileType)=='png'||fn:toLowerCase(item.file.fileType)=='gif'}">
	     <a onclick="lookUpFileByPath('${item.file.fileUrl}','${fn:replace(item.file.fileName,' ', '')}')">预览</a>
	      </c:if>
        </td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </div>
 </div>
 <div class="modal fade" id="fileUploadModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="fileUploadModalLabel" aria-hidden="true">
  <div class="modal-dialog">
   <div class="modal-content animated flipInY">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
     <h4 class="modal-title" id="fileUploadModalLabel">文件上传</h4>
    </div>
    <form id="fileUploadForm" name="fileUploadForm" method="post">
     <div class="modal-body">
      <input type="hidden" id="isUpdateFile" name="isUpdateFile" value="0" />
      <div class="uploadmutilFile" style="margin-top: 10px;">
       <input type="file" name="uploadify" id="uploadify" />
      </div>
      <%--用来作为文件队列区域--%>
      <div id="fileQueue" style="max-height: 200; overflow: scroll; right: 50px; bottom: 100px; z-index: 999"></div>
     </div>
     <div class="modal-footer">
      <a id="uploadFileButton" onclick="uploadFile()" class="btn btn-primary left_bj" >开始上传</a> 
      <a data-dismiss="modal" class="btn btn-primary left_bj">取消上传</a>
     </div>
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