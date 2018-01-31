var whichchannel = 0;
function resetPosition() {
	var clientWidth = document.body.clientWidth;
	var cW = $('#channel-list').width();
	clientWidth = clientWidth.toInt();
	//$$(".layout-middle")[0].style.marginLeft=(clientWidth-cW)/2-66;
}
function toLeft(myFx) {
	if (myFx.isRunning())
		return;
	if (whichchannel == 3)
		return;
	whichchannel++;
	$("pre").setStyles({
		"display" : "none",
		"marginLeft" : $("pre").getStyle("marginLeft").toInt() + 1040
	});
	$("next").setStyles({
		"display" : "none",
		"marginLeft" : $("next").getStyle("marginLeft").toInt() + 1040
	});
	mg = $$(".layout-middle")[0].getStyle("marginLeft");
	myFx.start("marginLeft", mg.toInt() - 1040);
}
function toRight(myFx) {
	if (myFx.isRunning())
		return;
	if (whichchannel == 0)
		return;
	whichchannel--;
	$("pre").setStyles({
		"display" : "none",
		"marginLeft" : $("pre").getStyle("marginLeft").toInt() - 1040
	});
	$("next").setStyles({
		"display" : "none",
		"marginLeft" : $("next").getStyle("marginLeft").toInt() - 1040
	});
	mg = $$(".layout-middle")[0].getStyle("marginLeft");
	myFx.start("marginLeft", mg.toInt() + 1040);
}
Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  


function seachTaskList(){
	$('#task_from').form('submit',{
        success:function(data){
           	$('#grid').datagrid('loadData', eval("{"+data+"}"));
       }
    });
	
}
/****************************************************************************************/
/**
 * @param taskId
 */
function processLogging(rowData){
	loadProcessLoggingGrid(rowData);
	var height = document.body.clientHeight*0.9;
	var width = document.body.clientWidth*0.9;
	$('#my_agent_win').window({
		width:width,
		height:height,
		title : "任务处理详情",
		modal:true
	});
}

$(document).ready(function() {
	
	$('.layout-button-left').click();
});
	