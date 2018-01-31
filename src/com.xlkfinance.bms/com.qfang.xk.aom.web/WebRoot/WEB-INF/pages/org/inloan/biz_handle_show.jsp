<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<%@ page language="java" import="com.qfang.xk.aom.rpc.system.OrgUserInfo"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>新增员工</title>
<meta name="keywords" content="">
<meta name="description" content="">
<script type="text/javascript">
	$(document).ready(function() {
		initHandleDynamicFileList();
		initUploadify();
		fileUploadModalListener();
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
	        'uploader'  : '${ctx}bizHandleController/uploadMultipartFile.action;jsessionid=${pageContext.session.id}',    
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
				layer.alert("上传完成"+uploadFileCount+"个文件", {icon : 6});
				uploadFileCount = 0;
				// 重新加载
				 $("#handle_dynamic_file_table_list").jqGrid('setGridParam', {
					url : '${ctx}bizHandleController/handleDynamicFileList.action',
					datatype : 'json',
					postData : {
						handleId : ${handleId}
					}, 
					page : 1
				}).trigger("reloadGrid"); //重新载入 
			}
		});
	}
	//打开上传文件对口框
	function openUploadBizFileModal(dynamicId,dynamicName,bizHanldeId){
		$("#uploadHandleDynamicFileButton").attr('onclick','uploadHandleDynamicFile('+dynamicId+',"'+dynamicName+'",'+bizHanldeId+')')
		$('#fileUploadModal').modal('show');
	}
	//上传文件
	function uploadHandleDynamicFile(dynamicId,dynamicName,bizHanldeId){
		if($("#fileQueue .fileName") == null || $("#fileQueue .fileName").length  == 0 ){
			layer.alert("请选择文件", {icon : 5});
			return false;
		}
		//多文件上传方式
		$('#uploadify').uploadify('settings','formData',{
				'dynamicId':dynamicId,
				'bizHanldeId':bizHanldeId,
				'dynamicName':encodeURI(dynamicName)
				});
		$('#uploadify').uploadify('upload','*');
	}
	function initHandleDynamicFileList() {
		$.jgrid.defaults.styleUI = "Bootstrap";
		$("#handle_dynamic_file_table_list").jqGrid({
			url : "${ctx}bizHandleController/handleDynamicFileList.action",
			datatype : "json",
			mtype : "POST",
			postData : {
				handleId : ${handleId}
			},
			caption : "文件列表",
			height : 450,
			autowidth : true,
			shrinkToFit : true,
			multiselect : true,
			viewrecords : true,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			pager : "#pager_handle_dynamic_file_list",
			colNames : [ "fileId","文件名", "大小(kb)", "类型","办理流程","操作" ],
			colModel : [ {
				name : "fileId",
				index : "fileId",
				hidden : true
			}, {
				name  : "fileName",
				index : "fileName",
				width :130,
				sortable : false
			},  {
				name  : "fileSize",
				index : "fileSize",
				width :30,
				sortable : false,
				formatter : function(cellvalue, options, row) {
					if (cellvalue) {
					   cellvalue=(cellvalue/1024).toFixed(0);
					}
					return cellvalue;
				}
			},  {
				name  : "fileType",
				index : "fileType",
				width :30,
				sortable : false
			}, {
				name  : "remark",
				index : "remark",
				width :50,
				sortable : false
			}, {
				name  : "operation",
				index : "operation",
				width :50,
				sortable : false,
				formatter : function(cellvalue, options, row) {
					var fileType=row.fileType;
					var fileName=row.fileName.replace(" ","");
					fileType = fileType.toLowerCase();
		    		var str='<a onclick=downLoadFileByPath("'+row.fileUrl+'","'+fileName+'")>下载</a>';
		    		if (fileType=="bmp"||fileType=="jpeg"||fileType=="jpg"||fileType=="png"||fileType=="gif") {
		    			str = str+' <a onclick=lookUpFileByPath("'+row.fileUrl+'","'+fileName+'")>预览</a>';
		    		}
					return str;
				}
			} ],
			gridComplete : function() { //列表生成后,给某一列绑定操作 例如删除操作
				//afterCompleteFunction();
			}
		});
		// 设置jqgrid的宽度  
        $(window).bind("resize",function() {
    	    var width = $(".jqGrid_wrapper").width();
    	    $("#handle_dynamic_file_table_list").setGridWidth(width);
        })
		$("#handle_dynamic_file_table_list").jqGrid("navGrid",
				"#pager_handle_dynamic_file_list", {
			edit : false,
			add : false,
			del : false,
			search : false,
			refresh : true
				});
	}
	// 删除办理动态文件
	function deletehandleDynamicFile() {
		var jqGridIds = $("#handle_dynamic_file_table_list").jqGrid('getGridParam', 'selarrrow');
		if (jqGridIds.length == 0) {
			layer.alert('请选择数据', {
				icon : 0
			});
			return;
		}
		var pids = "";
		for (var i = 0; i < jqGridIds.length; i++) {
			var pid = $("#handle_dynamic_file_table_list").jqGrid('getRowData', jqGridIds[i]).fileId;
			if (i == 0) {
				pids += pid;
			} else {
				pids += "," + pid;
			}
		}
		layer.confirm("确定删除选择的此批文件吗?", {
			icon : 0,
			btn : [ '是',' 否' ]
		//按钮
		}, function() {
			$.ajax({
				url : "${ctx}bizHandleController/deletehandleDynamicFile.action",
				type : "POST",
				data : {fileIds : pids},
				async : false,
				success : function(result) { 
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						layer.alert(ret.header["msg"], {
							icon : 6
						});
						// 重新加载
						 $("#handle_dynamic_file_table_list").jqGrid('setGridParam', {
							url : '${ctx}bizHandleController/handleDynamicFileList.action',
							datatype : 'json',
							postData : {
								handleId : ${handleId}
							}, 
							page : 1
						}).trigger("reloadGrid"); //重新载入 
    				}else {
    					layer.alert(ret.header["msg"], {
    						icon : 5
    					});
				}
			}
		   });
		}, function() {
		});
	}
</script>
</head>
<body class="gray-bg">
 <div class="wrapper wrapper-content animated fadeInRight">
  <div class="row">
   <div class="ibox float-e-margins">
    <div class="ibox-title">
     <h5><!-- 编辑员工 --></h5>
     <div class="ibox-tools">
      <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
      </a>
     </div>
    </div>
    <div class="ibox-content">
     <div class="row">
      <div class="col-sm-4">
       <table class="table">
        <thead>
         <tr>
          <th>序号</th>
          <th>办理流程</th>
          <th>操作</th>
         </tr>
        </thead>
        <tbody>
         <c:forEach items="${handleFlowList }" var="item" varStatus="status">
          <tr>
           <td>${ status.index + 1}</td>
           <td>${item.name }</td>
           <td><a href="javascript:void(0)" class="btn btn-primary left_bj"
            onclick="openUploadBizFileModal('${handleDynamicMap[item.pid+''].pid }','${item.name}','${handleDynamicMap[item.pid+''].handleId }')"
           >资料上传</a></td>
           <td></td>
          </tr>
         </c:forEach>
        </tbody>
       </table>
      </div>
      <div class="col-sm-8">
      <a class="btn btn-outline btn-danger" onclick="deletehandleDynamicFile()">删除</a>
       <table id="handle_dynamic_file_table_list"></table>
       <div id="pager_handle_dynamic_file_list"></div>
      </div>
     </div>
    </div>
   </div>
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
      <a id="uploadHandleDynamicFileButton" class="btn btn-primary an_bk" >开始上传</a> 
      <a data-dismiss="modal" class="btn btn-w-m btn-white cz_an">取消上传</a>
     </div>
    </form>
   </div>
  </div>
 </div>
 <!-- 需要延迟加载的资源 -->
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>