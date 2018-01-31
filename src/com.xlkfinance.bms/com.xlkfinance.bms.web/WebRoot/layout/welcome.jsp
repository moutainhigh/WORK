
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
	<%@ include file="/config.jsp"%>

	<style>
#layout-middle {
	position:relative;
	height:100%;
	min-width:1248px;
	background: #EAEEF4 url(${ctx}/p/xlkfinance/images/index/bg.png)
		no-repeat center bottom;
}
#channel-list{
	position:absolute;
	top:50%;	
	left:50%;
	margin-top:-256px;
	margin-left:-624px;
}
</style>

    <div class="layout-middle" id="layout-middle">
    
		<div id="channel-list">
			<!-- logo div -->
			<div class="none" style="padding: 8px 0 8px 0px;">
				<table>
					<tr>
						<td><img src="${ctx}/p/xlkfinance/images/index/logo.png"></td>
						<td width="10" align="center"><img
							src="${ctx}/p/xlkfinance/images/index/line.png"></td>
						<td><img src="${ctx}/p/xlkfinance/images/index/bms.png"></td>
					</tr>
				</table>
			</div>
			<div class="clear">
			
			</div>
			<div class="metro_mid">
				<!-- left 待办事项 -->
				<div id="channel-left" class="grid d458x552"
					style="position:relative;float:left;background:url(${ctx}/p/xlkfinance/images/index/00.png)no-repeat">
					<!-- <div class="scheduleUlDiv1">
					  <ul id="transaction_grid"></ul>
			   		</div> -->
					<div class="sCountDiv">
						<span class="scheduleCount">0</span>
					</div>
					<div class="scheduleTitle">待办事项</div>
					<div class="scheduleUlDiv">
						<ul id="welcome_my_Agent_Task_grid"></ul>
						<div class="clear"></div>
					</div>
					<div class="scheduleTitle" style="padding-top:0px;">预警事项</div>
					<div class="scheduleUlDiv">
						<ul id="welcome_my_Warn_Task_grid"></ul>
						<div class="clear"></div>
					</div>
				</div>
				<div id="home" class="channel" style="float: left;">
				</div>
				<div class="clear"></div>
				<script type="text/javascript"
				src="${ctx}/p/xlkfinance/module/task/task_common.js" charset="utf-8"></script>
				<script type="text/javascript"
					src="${ctx}/p/xlkfinance/module/system/welcome.js" charset="utf-8"></script>
     <div id="warn-edit" class="easyui-dialog" buttons="#upFileinfo" style="width: 513px; height: 248px; padding: 10px 20px;" closed="true">
      <form id="warnForm" name="warnForm" method="post">
       <input type="hidden" id="pid" name="pid" value=""> 
       <table>
        <tr>
        <td colspan="2">
       <b> <span id="warnName"></span></b>
        </td>
        </tr>
        <tr>
        <td colspan="2">
        <textarea style="height:130px;width:469px;" id="remark" maxlength="300" class="text_style" name="remark" placeholder="备注内容"></textarea>
        </td>
        </tr>
       </table>
      </form>
     </div>
     <div id="upFileinfo">
      <a id="saveFile" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitWarnRemark()">提交</a> <a href="javascript:void(0)"
       class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#warn-edit').dialog('close')"
      >取消</a>
     </div>
			</div>
		</div>
	</div>
<script type="text/javascript">
//查询资金信息
function financeTransaction(){
	//
	/* $.ajax({
		  type: "POST",
		  url: BASE_PATH+'/financeTransactionController/getTransactionIndexUrl.action',
		  dataType: "json",
		  success: function(ret){ 
			  var res="";
			  res+="<li  class='easyui-linkbutton left bold'>贷款余额:<span class='red bold'>"+
			  	accounting.formatMoney(ret.loanBalance, "", 2, ",", ".")
			  	//+"</span></li><br/><li class='easyui-linkbutton left'>放款本金:<span class='right red bold'>"+ 
			  //	accounting.formatMoney(ret.loanPrincipalTotal, "", 2, ",", ".")
			  	+"</span><em class='unit'> 万元</em></li><li class='easyui-linkbutton left bold'>可用余额:<span class='red bold'> "+
			  	accounting.formatMoney(ret.availableFundBalance, "", 2, ",", ".")
			  	+"</span><em class='unit'> 万元</em></li><li class='easyui-linkbutton left bold'>客户总数:<span class='red bold'>"+
			  	ret.custSum
			  	+"</span><em class='unit'> 户</em></li>";
				  $('#transaction_grid').html(res).click(function(e){
					  var t = $(e.target);
					  return ;
				  });
				  $('#transaction_grid').html(res);
		  }
	}); */
	
};
/**<li class='easyui-linkbutton left bold'>一周到期金额:<span class='red bold'>"+
	ret.weekAmt
	+"</span><em class='unit'> 万</em></li><li class='easyui-linkbutton left bold'>一月到期金额:<span class='red bold'>"+
	ret.aprilAmt
	+"</span><em class='unit'> 万</em></li>*/

function loadMyAgentList(){
	//
	$.ajax({
		  type: "POST",
		  
		  url: BASE_PATH+'taskController/searchTaskList.action',
		  dataType: "json",
		  success: function(ret){ 
			  var len = ret.rows.length;
			  $('.scheduleCount').html(parseInt($('.scheduleCount').text())+len);
			  var res='';
			  if(ret.total){
				  for(var i=0;i<len;i++){
					  var str=ret.rows[i].allocationDttm.substring(0,10); 
					  var s2=''+new Date();
					  var s1 = str.replace(/-/g, "/");
					  s2 = s2.replace(/-/g, "/");
					  s1 = new Date(s1);
					  s2 = new Date(s2);
					  var time= s2.getTime() - s1.getTime();
					  var days = parseInt(time / (1000 * 60 * 60 * 24));
					  res+="<li><a href='#' title='"+ret.rows[i].workfloPprojectName
					  	+"' ix='" + i +"' class='easyui-linkbutton left'>"
					  	+ret.rows[i].workfloPprojectName.substring(0,8)+"</a><span class='right'>"
					  	+ret.rows[i].taskName.substring(0,5)+"</span><span class='right red bold'>&nbsp;超期"
					  	+days+"天&nbsp;</span><span class='right'>"+str+"</span></li>";
				  }
				  $('#welcome_my_Agent_Task_grid').html(res).click(function(e){
					  var t = $(e.target);
					  if(t.is("a[ix]")){
						  disposeClick(ret.rows[parseInt(t.attr('ix'))]);
						  return false;
					  }
				  });
			  }
		  }
	});
};
function loadMyWarnList(){
	//
	$.ajax({
		  type: "POST",
		  
		  url: BASE_PATH+'bizHandleController/findIndexHandleDifferWarn.action',
		  dataType: "json",
		  success: function(ret){ 
			  var len = ret.rows.length;
			  $('.scheduleCount').html(parseInt($('.scheduleCount').text())+len);
			  var res='';
			  	
			  	  var remarkStr = "超期"
			  
				  for(var i=0;i<len;i++){
					  //查档
					  if(ret.rows[i].handleType == 3 ){
						  remarkStr = "异常" ;
					  }else{
						  remarkStr = "超期" ;
					  }
					  res+="<li><a href='#' onclick='openWarnEdit("+ret.rows[i].pid+",\""+ret.rows[i].projectName+"\")' title='"+ret.rows[i].projectName
					  	+"' ix='" + i +"' class='easyui-linkbutton left'>"
					  	+ret.rows[i].projectName.substring(0,8)+"</a><span class='right'>"
					  	+ret.rows[i].flowName.substring(0,5)+"</span><span class='right red bold'>&nbsp;"+remarkStr
					  	+ret.rows[i].differ+"天&nbsp;</span><span class='right'>"+ret.rows[i].createDate+"</span></li>";
				  }
				  $('#welcome_my_Warn_Task_grid').html(res);
			  }
	});
};
function initIndex(){
	//
	var s='';
	$.ajax({
		  type: "POST",
		  url: BASE_PATH+'sysMetroUiController/findSysMetroUiPage.action',
		  dataType: "json",
		  success: function(ret){ 
			  var rets = ret.rows;
			  var url = "${basePath}sysMetroUiController/selectMetroUiByUserName.action";
				$.post(url,
				   function(data){
					var datas =  eval("("+data+")");
					var result='';
					for(var i=0;i<rets.length;i++){
						var gCss=' d364x175 ';
						if(i==2||i==5||i==7||i==8||i==9){
							gCss=' g177x175 ';
						}
						var falg = false;
						for (var j = 0; j < datas.length; j++) {
							if(rets[i].menuId == datas[j].menuId){
								falg = true;
							}
						}
						if(falg){
							s+='<a class="grid '+gCss+'" href="#" onclick=\'addNewTabNav("'+rets[i].menuName+'","${basePath}'+rets[i].actionUrl+'")\'>';
						}else{
							s+='<a class="grid '+gCss+'" href="#" onclick="shield()">';
						}
						s+='<div class="imgcenter">';
						s+='<img src="${ctx}/p/xlkfinance/images/index/'+rets[i].showImg+'_metro.png" alt="" />';
						s+='</div><div class="layout-title">'+rets[i].menuName+'</div></a>';
							
					}
					$('#home').html(s);
					document.addEvent('domready',
						function() {
							//定位
							//resetPosition();
							//window.onresize = resetPosition;
							//随机背景色
							var colors = [ 'ffffff', '009cae', 'b63774', '365aa7',
									'06a1df', '43a556', '01bfe1', 'ff6501', '237ae4',
									'583bb4', '5f9229' ];
							var i = 0;
							$$(".grid").each(function(item) {
								item.setStyle('backgroundColor', '#' + colors[i]);
								item.setStyle('color', '#fff');
								item.setStyle('fontSize', '16px');
								i++;
							});
						});
				});
		  }
	});
}

/**
 * 屏蔽
 */
function shield(){
	$.messager.alert("提示","您没有打开该功能模块的权限，请与系统管理员联系！");
	return;
}
/**
 * 打开teb页
 */
function addNewTabNav(title,url){
	parent.layout_center_addTabFun({
		title : title, //tab的名称
		closable : true, //是否显示关闭按钮
		content : createFrame(url),//加载链接
		falg:true
	});
}
//打开预警事项备注窗口
function openWarnEdit(warnId,warnName){
	$("#warnName").text("项目："+warnName);
	$("#pid").val(warnId);
	$('#warn-edit').dialog('open').dialog('setTitle', "预警事项备注");
}
//提交更改差异预警处理备注
function submitWarnRemark(){
	var remark=$("#remark").val();
	if (remark==null||remark.trim().length == 0) {
		$.messager.alert("操作提示", "请输入备注", "error");
		return;
	}
			$.ajax({
				url : "${basePath}bizHandleController/submitWarnRemark.action",
				cache : true,
				type : "post",
				data : $("#warnForm").serialize(),
				async : false,
				success : function(data, status) {
					if (data=="1") {
						$.messager.alert("操作提示", "提交成功！");
						$("#remark").val('');
						$("#warn-edit").dialog("close");
						window.setTimeout("refreshPage()",1000);
					}else{
		                $.messager.alert("操作提示", "提交失败！", "error");
					}
				}
			});
}
function refreshPage()
{
window.location.reload();
}
$(document).ready(function() {
	//加载任务管理列表数据
	loadMyAgentList();	
	loadMyWarnList();
	initIndex();
	financeTransaction();
	

	
});
</script>


