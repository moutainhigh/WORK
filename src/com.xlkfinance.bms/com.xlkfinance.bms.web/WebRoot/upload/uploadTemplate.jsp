<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>上传</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="upload/zDialog.js"></script>
	<script type="text/javascript" src="upload/zDrag.js"></script>
	<script type="text/javascript" src="upload/javascript/prototype.js"  charset="utf-8"></script>
　　　<script type="text/javascript" src="upload/javascript/AjaxWrapper.js" charset="utf-8"></script>
　	<script type="text/javascript" src="upload/javascript/upload.js" charset="utf-8"></script>
　　　<link href="upload/css/fileUpload.css" type="text/css" rel="stylesheet"/>
　　　　<style type="text/css">
　　　　　　div#readme{
　　　　　　　　　width:100%;padding:3px 0;background: #BAFB80;
               background-image: url("upload/images/info_32.png");
               background-repeat: no-repeat;
               text-align: center;
    font:85%/1.45 "Lucida Sans Unicode","Lucida Grande",Arial,sans-serif;
    font-size:medium;
    font-weight: bold;
    line-height: 25px;
    height: 25px;
    color:gray;
    　　　　　
            }
            
　　　　</style>
  <body onload="init();">
  <div id="controlPanel">
	<div id="readme">说明:&nbsp;&nbsp;最大上传量:50MB，单个文件最大容量:10MB</div>
	<div id="uploadFileUrl"></div>
	
		
	<input class="input_text" type="text" id="txt1" name="txt1" size="60"/><input type="button" name="uploadfile2"  id="uploadfile2" style="padding-left: 26px;"/><input class="input_file" size="30" type="file" name="file1" id="file1" hidefocus onchange="txt1.value=this.value"/><a href="javascript:void(0);"  onclick="addRow();"><img src="upload/images/add.png" width="28" height="28" border="0" alt="添加一行" class="imgstyle"/></a><br>
	<div id="tool">
	</div>
	<br>
	<input type="submit" name="uploadButton" id="uploadButton" value="开始上传" class="up_btn"/>
	<input type="hidden" name="cancelUploadButton" onclick="closeWindow();" id="cancelUploadButton" value="取消上传" class="up_btn"/><br>
 
	
	<div id="progressBar">
	<div id="theMeter">
    	<div id="progressBarText"></div>
	        <div id="totalProgressBarBox">
	        	<div id="totalProgressBarBoxContent"></div>
	        </div>
        </div>
        <div id="progressStatusText"></div>
   </div>
   
</div>
<iframe name='hidden_frame' id="hidden_frame" style="display:none"></iframe> 
<script>
Element.hide('progressBar');
Event.observe('fileUploadForm','submit',startProgress,false);
Event.observe('cancelUploadButton','click',cancelProgress,false);
</script>
  </body>
</html>
