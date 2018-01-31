 var flag=1;
  var stop=true;
  var upload=true;
  function getFileSize(fileSize)
	{
		var num = new Number();
		var unit = '';
		
		if (fileSize > 1*1024*1024*1024){
			num = fileSize/1024/1024/1024;
			unit = "G"
		}
		else if (fileSize > 1*1024*1024){
			num = fileSize/1024/1024;
			unit = "M"			
		}
		else if (fileSize > 1*1024){
			num = fileSize/1024;
			unit = "K"
		}
		else{
			return fileSize;
		}
		
		return num.toFixed(2) + unit;
		
	}	
  function addRow()
  {
  
     if(flag>4)
     {
		alert("一次最多上传５个文件！"); 
       	return;
     }
  
     var form=document.getElementById('tool');
     
     var text1 = document.createElement("input");
	 text1.size = "60";
	 text1.type = "text";
	 text1.name = "txt"+flag+1;
	 text1.id = "txt"+flag+1;
     text1.className='input_text';
     
     var btn1=  document.createElement("input");
     btn1.name='uploadfile'+flag+1;
     btn1.id='uploadfile'+flag+1;
     btn1.size=40;
     btn1.value='浏览...';
     btn1.className="uploadfile2";
     btn1.hidefocus='';
     
     var inputNode1 = document.createElement("input");
	 inputNode1.size = "30";
	 inputNode1.type = "file";
	 inputNode1.name = "file"+flag+1;
	 inputNode1.id = "file"+flag+1;
	 inputNode1.className='input_file';
	 
	 
     if(inputNode1.addEventListener)
	{
					inputNode1.addEventListener("change",changeValue(text1,inputNode1),false);
	}
	else if(inputNode1.attachEvent)
	{
				
					inputNode1.attachEvent("onchange", changeValue(text1,inputNode1)) ;
	}	
	
	 var inputNode3 = document.createElement("a");
	 inputNode3.href = "javascript:void(0);";
	 
	 
	 var img=document.createElement("img");
	 img.src='upload/images/delete.png';
	 img.width=28;
	 img.height=28;
	 img.border='0';
	 img.className='imgstyle';
	 img.alt='删除一行';
	 inputNode3.appendChild(img);
	 
	 if(inputNode3.addEventListener)
	{
					inputNode3.addEventListener("click",deleterow(form,text1,btn1,inputNode1,inputNode3),false);
	}
	else if(inputNode3.attachEvent)
	{
				
					inputNode3.attachEvent("onclick", deleterow(form,text1,btn1,inputNode1,inputNode3)) ;
	}
	 
	 form.appendChild(text1);
	 form.appendChild(btn1);
	 form.appendChild(inputNode1);
	 form.appendChild(inputNode3);
	 flag++;
	 
	 //window.addHeight();
	 
  }

  var changeValue=function changeValue(v1,v2)
  {
     return function()
     {
        v1.value=v2.value;
     }
  }
 function init()
  {
     var btn1=document.getElementById('uploadfile2');
     btn1.size=40;
     btn1.value='浏览...';
     btn1.className="uploadfile2";
     
  }
  var deleterow = function(form,text1,btn1,inputNode1,inputNode3){
				 return function(){
				    form.removeChild(text1);
      form.removeChild(btn1);
      form.removeChild(inputNode1);
      form.removeChild(inputNode3);
      flag--;
      window.decreaseHeight();
	}
  }
 function doSubmits(){
	 var objects = document.getElementsByTagName("input");  
	 for (var i=0;i < objects.length;i++) {
        if(objects[i].type=="file" || objects[i].type=="text") {
         	if(objects[i].value=="" || objects[i].value==null){
         		alert("请选择要上传的文件！");  
         		upload=false;
         		break;
         	}else{
         		upload=true;
         	} 
        }
       }  
	 return upload;
 }
 
 //刷新上传状态
 function refreshUploadStatus(){
 	
 	var ajaxW = new AjaxWrapper(false);
 	ajaxW.putRequest(
 		'/BMS/filelistener/filestatus.action',
 		'uploadStatus=true',
 		function(responseText){
 				eval("uploadInfo = " + responseText);
 				var progressPercent = Math.ceil(
 					(uploadInfo.ReadTotalSize) / uploadInfo.UploadTotalSize * 100);
 				
 				if(uploadInfo.UploadFlag=='http'){
 				   flag='(HTTP状态)';
 				   
 				}else
 				{
 				   flag='(FTP状态)';
 				}
 				$('progressBarText').innerHTML=flag;
 				$('progressBarText').innerHTML += ' 上传处理进度: '+progressPercent+'% 【'+
 					getFileSize(uploadInfo.ReadTotalSize)+'/'+getFileSize(uploadInfo.UploadTotalSize) +
 					'】 正在处理第'+uploadInfo.CurrentUploadFileNum+'个文件'+
 					' 耗时: '+(uploadInfo.ProcessRunningTime-uploadInfo.ProcessStartTime)+' ms';
 					
 				$('progressStatusText').innerHTML=' 反馈状态: '+uploadInfo.Status;
 				$('totalProgressBarBoxContent').style.width = parseInt(progressPercent * 3.5) + 'px';
 				if(progressPercent==100 || progressPercent=="100"){
 					stop=false;
 					Element.hide('progressBar');
 					 var objects = document.getElementsByTagName("input");
 					 for (var i=0;i < objects.length;i++) {
 				        if(objects[i].type=="file" || objects[i].type=="text" ) {
 			        		objects[i].value=="";
 				        }
 				     }  
 				}
 		}
 	);
 }
  
 //上传处理
 function startProgress(){
 	
     if(upload){
     	Element.show('progressBar');
     	$('progressBarText').innerHTML = ' 上传处理进度: 0%';
    	    $('progressStatusText').innerHTML=' 反馈状态:';
    	    $('uploadButton').disabled = true;
    	    $('cancelUploadButton').disabled = true;
    	    var periodicalExe=new PeriodicalExecuter(refreshUploadStatus,0.5);
    	    return true;
     }
    
 }
 //取消上传处理
 function cancelProgress(){
 	$('cancelUploadButton').disabled = true;
 	var ajaxW = new AjaxWrapper(false);
 	ajaxW.putRequest(
 		'/BMS/filelistener/filestatus.action',
 		'cancelUpload=true',
 		//因为form的提交，这可能不会执行
 		function(responseText){
 			eval("uploadInfo = " + responseText);
 			$('progressStatusText').innerHTML=' 反馈状态: '+uploadInfo.status;
 			if (msgInfo.cancel=='true'){
 				alert('删除成功!');
 				window.location.reload();
 			};
 		}
 	);
 }