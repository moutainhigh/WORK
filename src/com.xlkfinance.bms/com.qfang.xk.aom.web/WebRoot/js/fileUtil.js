/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年8月26日 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 文件工具js，包括文件上传下载等<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
//单文件上传工具方法
var uploadStatusTimeObj;
var initRefreshUploadStatusTime=1000;
var refreshUploadStatusTime=initRefreshUploadStatusTime;
function getUploadStatus() {
     var theBytesRead=0;
     var bytesRead=0;
     var num100Ks=0;
     var contentLengthKnown=false;
	 $.ajax({
			url : getWebRootPath()+"/filelistener/filestatus.action",
			type : 'post',
			data : {uploadStatus:true},
			async : false,
			success : function(data) {
			if (data==null) {
				uploadStatusTimeObj=startUploadStatusTimeObj();
				return;
			}
			var ret = eval("(" + data + ")");
			if (ret && ret.success) {
				var filestatusObj=ret.filestatusObj;
				var progress=0;
				var contentLength=filestatusObj.UploadTotalSize;
				bytesRead=filestatusObj.ReadTotalSize;
				 if (contentLength > -1) {
			            contentLengthKnown = true;
			        }
			        theBytesRead = bytesRead;
			        theContentLength = contentLength;
	                 
			        var nowNum100Ks = bytesRead / 100000;
			        if (nowNum100Ks > num100Ks) {
			            num100Ks = nowNum100Ks;
			            if (contentLengthKnown) {
			            	progress = parseInt(100.00 * bytesRead / contentLength);
			            }
			        }
				$("#fileUploadProgressTtriped").show();
				$("#fileUploadProgressBar").html(progress+"%")
				.attr("style","width: "+progress+"%")
				.attr("aria-valuenow",progress);
				uploadStatusTimeObj=startUploadStatusTimeObj();
			}else{
				clearUploadStatusTimeObj(uploadStatusTimeObj);//清除查看文件上传进度条定时函数
				layer.alert(ret.msg, {
					icon : 5
				});
			}
			}
		});
}
function startUploadStatusTimeObj(){
	if (refreshUploadStatusTime>0) {
		uploadStatusTimeObj = setTimeout(function() {
			getUploadStatus();
		}, refreshUploadStatusTime);
	}
	return uploadStatusTimeObj;
}
function clearUploadStatusTimeObj(uploadStatusTimeObj){
	refreshUploadStatusTime=-1;
	$("#fileUploadProgressTtriped").hide();//隐藏文件上传进度条
	window.clearInterval(uploadStatusTimeObj);//清除查看文件上传进度条定时函数
}
//提交文件
function submitFileForm(fileForm) {
	$('#fileUploadModal').on('hide.bs.modal', function () {
		clearUploadStatusTimeObj(uploadStatusTimeObj);//清除查看文件上传进度条定时函数
		$(fileForm)[0].reset();
	});
	if ($("#offlineMeetingFiles").val() != '') {
		refreshUploadStatusTime=initRefreshUploadStatusTime;
		uploadStatusTimeObj=startUploadStatusTimeObj();
		$(fileForm).ajaxSubmit({
			success : function(data) {
				var ret = eval("(" + data + ")");
				if (ret && ret.header["success"]) {
					clearUploadStatusTimeObj(uploadStatusTimeObj);//清除查看文件上传进度条定时函数
					layer.confirm("文件上传成功，为确保数据准确请刷新页面？", {
						icon : 6,
						btn : [ '是','否' ]
					//按钮
					}, function() {
						refreshPage();
					}, function() {
						$('#fileUploadModal').modal('hide');
						layer.closeAll('dialog');
					});
				} else {
					clearUploadStatusTimeObj(uploadStatusTimeObj);//清除查看文件上传进度条定时函数
					layer.alert(ret.header["msg"], {
						icon : 5
					});
				}
			}

		});
	} else {
		layer.alert('请选择文件!', {
			icon : 0
		});
	}
}
//单文件上传 end

//下载文件
function downLoadFileByPath(path,fileName){
	var webRootPath=getWebRootPath();
	window.location.href=webRootPath+"/download.action?path="+path;
}

//预览文件，如果是图片则直接在浏览器显示
function lookUpFileByPath(path,fileName){
	var webRootPath=getWebRootPath();
	var fileType=path.substring(path.lastIndexOf(".")+1);
	fileType = fileType.toLowerCase();
	if (fileType=="bmp"||fileType=="jpeg"||fileType=="jpg"||fileType=="png"||fileType=="gif") {
	    openTab(webRootPath+'/toLookFile.action?path='+path+'&fileName='+encodeURI(fileName),'查看文件','lookFile.action');
	}
}