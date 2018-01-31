<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
<style>
div.fileupbtn{width:70px;height:26px;position:relative;display:inline-block;overflow:hidden;text-align:center;border:1px solid #ccc;background:#eee;background:linear-gradient(to bottom,#ffffff 0,#eeeeee 100%);border-radius:5px;}
div.fileupbtn span{line-height:26px;}
div.fileupbtn input{filter:alpha(opacity=0);opacity:0;font-size:99999px;position:absolute;left:-10px;top:-10px;cursor:pointer;}
</style>
<script type="text/javascript">

function save(){
	$('#fileUploadForm').form('submit', {
		url : "saveFile.action",
		onSubmit : function() {
			return checkFileForm();
		},
		success : function(result) {
			var ret = eval("("+result+")");
			var path=ret.header["path"];
			window.$("#${imgurl}_img").attr("src","${basePath}customerController/download.action?path="+path);
			window.$("#${imgurl}").val(path);
			window.$("#winUploadFile").dialog('close');
		}
	}); 
}

function checkFileForm(){
	var flag = true;
	var fileurl=$("#file1").filebox('getValue');
	if(fileurl==''){
		alert("请选择文件！");
		return false;
	}else{
		var fileT=fileurl.substring(fileurl.lastIndexOf("."));
		fileT = fileT.toLowerCase();
		if(fileT ==".gif" || fileT==".jpg" || fileT==".jpeg" || fileT ==".png" ){
			return flag=true;
		}
		else{
			alert("文件格式错误，系统只支持上传后缀名为.jpg,.jpeg,.gif,.png的图片！");
			return flag=false;
		}
		return flag;
	}
}

function xzclk(){
	$("#file1").click();
}

</script>

<form id="fileUploadForm" name="fileUploadForm" 
	action="saveFile.action" enctype="multipart/form-data" 
	method="post" onsubmit="return doSubmits()" >
 <table class="cus_table" style="width:460px;min-width:460px;margin-right:15px;height:62px;">
	  <tr>
         <td><span class="cus_red">*</span>图片文件：</td>
         <td><input class="easyui-filebox offlineMeetingInput" name="offlineMeetingFile" id="file1" style="width: 425px"  data-options="buttonText:'选择文件'"></td>
        </tr>
	 
	</table>
 	</div>
</form>	    
