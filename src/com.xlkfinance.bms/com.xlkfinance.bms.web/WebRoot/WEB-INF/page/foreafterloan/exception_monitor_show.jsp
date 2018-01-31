<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<%@ include file="/common.jsp"%>
<title>查看在途业务监控信息</title>
<style type="text/css">
.table_css td{font-size:12px; padding: 5px 7px;border-bottom:1px #ddd solid; border-right:1px #ddd solid;}
</style>
</head>
<script type="text/javascript">
function openExceptionTransitDialog(){
	$('#exception_report_dialog').dialog('open');
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1; // 记得当前月是要+1的
	if(month < 10){
		month = "0"+month;
	}
	var dt = d.getDate();
	if(dt < 10){
		dt = "0"+dt;
	}
	var today = year + "/" + month + "/" + dt;
	$("#monitorDate").datebox('setValue', today);
}
function formatterRepaymentDateDiff(val,row){
 	if(parseInt(val) <=3 &&row.repaymentStatus==1){
 		return "<span style='color:red;'>"+val+"</span>";
 	}else{
 		return "<span style='color:blue;'>"+val+"</span>";
 	}
 }
 
//风险等级:正常风险=1,重大风险=2,紧急风险=3
 function formatDangerLevel(val,row){
 	if(val == 2){
 		return "<span style='color:red;'>重大风险</span>";
 	}else if(val == 3){
 		return "<span style='color:red;'>紧急风险</span>";
 	}else{
 		return "正常风险";
 	}
 }
//
 function formatFollowName(val,row){
 	if(val == -1 || val==null ||val == "" ){
 		return "";
 	}else{
 		return val;
 	}
 }
//异常监控状态：正常=1，异常=2，异常转正常=3
 function formatterMonitorStatus(val,row){
 	if(val == 3){
 		return "<span style='color:red;'>异常转正常</span>";
 	}else if(val == 2){
 		return "<span style='color:red;'>异常</span>";
 	}else{
 		return "正常";
 	}
 }
 
//异常监控状态：关闭=2，待跟进=1
 function formatFollowStatus(val,row){
 	if(val == 2){
 		return "关闭";
 	}else{
 		return "待跟进";
 	}
 }
function formatterNoticeWay(val,row){
	if (val==null||val=="") {
		return "";
	}
	var noticeWas=val.split(",");
	var noticeWay="";
 	for (var i = 0; i < noticeWas.length; i++) {
 		if(0==i){
	    	if (noticeWas[i]=="1") {
	    		noticeWay="短信";
			} else if(noticeWas[i]=="2") {
	    		noticeWay="邮件";
			}
	    }else{
	    	if (noticeWas[i]=="1") {
	    		noticeWay+=","+"短信";
			} else if(noticeWas[i]=="2") {
	    		noticeWay+=","+"邮件";
			}
	    }
	}
 	return noticeWay;
 }
	function subExceptionReportForm()
	{
		var noticeWays="";
		$("#exceptionReportForm input:checkbox[name='noticeWay']:checked").each(function(i){
		       if(0==i){
		    	   noticeWays = $(this).val();
		       }else{
		    	   noticeWays += (","+$(this).val());
		       }
		 });  
		$("#noticeWay").val(noticeWay);
		var dangerLevel = $("#dangerLevel").combobox('getValue');
		var monitorTitle = $("#monitorTitle").textbox('getValue');
		var monitorContent = $("#monitorOpinion").textbox('getValue');
		var monitorOpinion = $("#monitorOpinion").textbox('getValue');
		var followStatus = $("#followStatus").combobox('getValue');
		var nextMonitorDate = $("#nextMonitorDate").datebox('getValue');
		var followId = $("#followId").combobox('getValue')
		if(dangerLevel == "-1"){
			$.messager.alert("提示", "请选择风险等级", "info");
			return false;
		}
		if(monitorContent == "" || monitorContent == null){
			$.messager.alert("提示", "监控内容不能为空", "info");
			return false;
		}
		if(monitorOpinion == "" || monitorOpinion == null){
			$.messager.alert("提示", "监控意见不能为空", "info");
			return false;
		}
		if(followStatus == "-1"){
			$.messager.alert("提示", "请选择跟进状态", "info");
			return false;
		}
		//跟进状态为是的时候下次 跟进时间不能为空
		if(followStatus == "1"){
			if(nextMonitorDate== "" || nextMonitorDate == null){
				$.messager.alert("提示", "下次跟进时间不能为空", "info");
				return false;
			}
			if(followId== "-1"){
				$.messager.alert("提示", "请选择下次跟进人", "info");
				return false;
			}
		}
		// 保存提交
		$('#exceptionReportForm').form('submit', {
				url : "addExceptionList.action?noticeWays="+noticeWays,
			onSubmit : function() {
				var bool =$(this).form('validate');
				if (bool) {
		          MaskUtil.mask("提交中,请耐心等候......");  
				}
				return bool;
			},
			success : function(result) {
				MaskUtil.unmask();
				var ret = eval("("+result+")");
			//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
					if(ret && ret.header["success"] ){
						$.messager.alert('操作提示',ret.header["msg"]);
						var pid=ret.header["pid"];
						$("#pid").val(pid);
						$('#exception_report_dialog').dialog('close');
												
						$('#after_Exception_Report_List_grid').datagrid('reload');
					}else{
						$.messager.alert('操作提示',ret.header["msg"],'error');	
					}
			},error : function(result){
				alert("保存失败！");
			}
		});
	}
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
	
	<div title="监控记录" id="tab1">
	 <a href="javascript:void(0)" class="easyui-linkbutton" style="margin: 10px;" iconCls="icon-edit" plain="true" onclick="openExceptionTransitDialog()">增加监控</a>
	<div class="contractListDiv" style="height:100%;">
	<table id="after_Exception_Report_List_grid"  title="异常监控列表" class="easyui-datagrid" style="height: 668px; width: auto;"
     data-options="
		    url: '${basePath}afterExceptionReportController/toExceptionTransitIndexList.action?projectId=${projectId}',
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    singleSelect: true,
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true" />
		<th data-options="field:'monitorTitle',sortable:true" align="center" halign="center"  >监控标题</th>
		<th data-options="field:'dangerLevel',sortable:true ,formatter:formatDangerLevel" align="center" halign="center"  >风险等级</th>
        <th data-options="field:'monitorContent',sortable:true" align="center" halign="center"  >监控内容</th>
	    <th data-options="field:'monitorOpinion',sortable:true" align="center" halign="center"  >监控意见</th>
	    <th data-options="field:'monitorDate',sortable:true,formatter:convertDate" align="center" halign="center"  >本次监控时间</th>
        <th data-options="field:'followStatus',sortable:true,formatter:formatFollowStatus" align="center" halign="center">跟进状态</th>
        <th data-options="field:'nextMonitorDate',sortable:true,formatter:convertDate" align="center" halign="center">下次跟进时间</th>
        <th data-options="field:'followName',sortable:true,formatter:formatFollowName" align="center" halign="center">下次跟进人</th>
      </tr>
     </thead>
    </table>
    </div>
    
    
    <div id="exception_report_dialog" title="增加异常监控" class="easyui-dialog" data-options="modal:true" buttons="#submitDiv1" style="width: 553px; height: 350px; padding: 10px;" closed="true">
	   <form id="exceptionReportForm" name="exceptionReportForm" action="${basePath}foreAfterLoanController/addExceptionList.action" method="post">
	   <input type="hidden" name="noticeWays" id="noticeWays">
	   <input type="hidden" id="projectId" name="projectId" value="${projectId}"/>
	     <table style="width: 100%; height: 200px;">
	   <tr>
 		<td class="label_right"><font color = "red">*</font>风险等级：</td>
		<td colsapn="4" style=" width: 120px;">	
	        <select class="select_style easyui-combobox" style="width:160px;" data-options="validType:'selrequired'" editable="false"  name="dangerLevel" id="dangerLevel">												
	         <option value="-1" >--请选择--</option>
	         <option value="1" >正常风险</option>
	         <option value="2" >重大风险</option>
	         <option value="3" >紧急风险</option>
	        </select>
	    </td>
 	</tr>
 	<tr>
 	<td class="label_right"><font color = "red">*</font>监控标题:</td>
    <td>
		<input name="monitorTitle" id="monitorTitle"  class="easyui-textbox" style="width:160px;height:40px" data-options="multiline:true,validType:'length[0,500]'">
  	</td>
  	
  	 <td class="label_right"><font color = "red">*</font>监控时间:</td>
    <td>
    	<input  class="easyui-datebox" id="monitorDate" name="monitorDate" editable="false" style="width:160px;" required="true" />
   </td>
</tr>
<tr>
 	<td class="label_right"><font color = "red">*</font>监控内容:</td>
    <td>
		<input name="monitorContent" id="monitorContent"  class="easyui-textbox" style="width:160px;height:40px" data-options="multiline:true,validType:'length[0,500]'">
  	</td>
</tr>
<tr>
 	<td class="label_right"><font color = "red">*</font>监控意见:</td>
    <td>
		<input name="monitorOpinion" id="monitorOpinion"  class="easyui-textbox" style="width:160px;height:40px" data-options="multiline:true,validType:'length[0,500]'">
  	</td>
</tr>
<tr>
  	<td class="label_right"><font color = "red">*</font>跟进状态:</td>
  	<td colsapn="2">	
  			<select class="select_style easyui-combobox" style="width:160px;" data-options="validType:'selrequired'" editable="false"  name="followStatus" id="followStatus">												
	         <option value="-1" >请选择</option>
	         <option value="1" >是</option>
			 <option value="2" >否</option>
	        </select>
	</td>
  	<td class="label_right">下次跟进时间:</td>
    <td>
    	<input  class="easyui-datebox" id="nextMonitorDate" name="nextMonitorDate" editable="false" style="width:160px;" />
  	</td>
</tr>
<tr>
     <tr>
	        <td  class="label_right">待跟进人:</td>
	        <td>
	        <select class="select_style easyui-combobox" id="followId" name="followId" style="width:160px" editable="false">
	        <option value="-1">--请选择--</option>
	        <c:forEach var="item" items="${monitorUserMap}">   
		    <option value="${item.key}">${item.value}</option>
		    </c:forEach>  
	       </select>
	       </td>
	       
  	<td class="label_right">提醒方式:</td>
    <td>
	 	<input type="checkbox" name="noticeWay"  id="noticeWay" value="1"/>短信
	    <input type="checkbox" name="noticeWay"  id="noticeWay" value="2" />邮件
  	</td>
</tr>
	     </table>
	    </form>
   </div>
    <div id="submitDiv1">
	    <a id="F" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subExceptionReportForm()">提交</a> <a href="javascript:void(0)"
	     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#exception_report_dialog').dialog('close')"
	    >取消</a>
   </div>
	</div>
	
	<div title="查档历史" id="tab2" >
		<table id="check_document_his_house_list_grid"  title="查档历史列表" class="easyui-datagrid" style="height: 705px; width: auto;"
     data-options="
		    url: '${basePath}integratedDeptController/checkDocumentHisList.action?projectId=${projectId }',
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true" />
       <th data-options="field:'houseName'" align="center" halign="center"  >物业名称</th>
       <th data-options="field:'housePropertyCard'" align="center" halign="center"  >房产证号</th>
       <th data-options="field:'checkDate'" align="center" halign="center"  >查档时间</th>
       <th data-options="field:'checkStatusStr'" align="center" halign="center"  >查档状态</th>
       <th data-options="field:'checkWay',formatter:formatterCheckWay" align="center" halign="center"  >查档方式</th>
       <th data-options="field:'createrName'" align="center" halign="center"  >操作人</th>
       <th data-options="field:'createrDate',formatter:convertDateTime" align="center" halign="center"  >操作时间</th>
      </tr>
     </thead>
    </table>
	</div>
	<div title="查诉讼历史" id="tab2" >
		<table id="check_document_his_house_list_grid"  title="查诉讼历史列表" class="easyui-datagrid" style="height: 705px; width: auto;"
     data-options="
		    url: '${basePath}integratedDeptController/checkLitigationHisList.action?projectId=${projectId }',
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true" />
       <th data-options="field:'checkDate'" align="center" halign="center"  >查法院网时间</th>
       <th data-options="field:'checkStatus',formatter:formatterCheckLitigationStatus" align="center" halign="center"  >查诉讼状态</th>
       <th data-options="field:'checkWay',formatter:formatterCheckWay" align="center" halign="center"  >查诉讼方式</th>
       <th data-options="field:'approvalStatus',formatter:formatterCheckLitigationApprovalStatus" align="center" halign="center"  >审批状态</th>
       <th data-options="field:'remark'" align="center" halign="center"  >审批意见</th>
       <th data-options="field:'createrName'" align="center" halign="center"  >操作人</th>
       <th data-options="field:'createrDate',formatter:convertDateTime" align="center" halign="center"  >操作时间</th>
      </tr>
     </thead>
    </table>
	</div>
	<div title="办理动态" id="tab3" >
		<div class="easyui-panel" title="办理动态" data-options="collapsible:true">
       <div id="projectBasicNews" style="height: auto; line-height: 30px;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_css" >
          <tr class="bt">
           <td class="handle_dynamic_tb">办理流程</td>
           <td class="handle_dynamic_tb">完成日期</td>
           <td class="handle_dynamic_tb" style="width:10%">操作天数</td>
           <td class="handle_dynamic_tb" style="width:7%">操作人</td>
           <td class="handle_dynamic_tb" style="width:7%">办理人</td>
           <td class="handle_dynamic_tb" style="width:7%">规定天数</td>
           <td class="handle_dynamic_tb" style="width:5%">差异</td>
           <td class="handle_dynamic_tb" style="width:20%">备注</td>
          </tr>
         <c:forEach items="${handleFlowList }" var="item">
          <tr>
           <td class="handle_dynamic_tb">${item.name}</td>
           <td class="handle_dynamic_tb"><input type="text" name="finishDate" disabled="disabled" value="${handleDynamicMap[item.pid+''].finishDate }" class="handle_dynamic_input easyui-datebox" editable="false"></td>
           <td class="handle_dynamic_tb" style="width:10%"><input type="text" name="handleDay" disabled="disabled" value="${handleDynamicMap[item.pid+''].handleDay }" class="handle_dynamic_input easyui-textbox" data-options="min:0,max:9999999999"></td>
           <td class="handle_dynamic_tb" style="width:7%"><input type="text" name="operator" disabled="disabled" value="${handleDynamicMap[item.pid+''].operator }" class="handle_dynamic_input easyui-textbox" data-options="required:true,validType:'length[1,20]'" ></td>
           <td class="handle_dynamic_tb" style="width:7%"><input type="text" name="currentHandleUserName" disabled="disabled" value="${handleDynamicMap[item.pid+''].currentHandleUserName }" class="handle_dynamic_input easyui-textbox" data-options="required:true,validType:'length[1,20]'" ></td>
           <td class="handle_dynamic_tb" style="width:7%"><input type="text" name="fixDay" disabled="disabled" value="${item.fixDay}" class="handle_dynamic_input easyui-numberbox" data-options="min:0,max:9999999999"></td>
           <td class="handle_dynamic_tb <c:if test="${handleDynamicMap[item.pid+''].differ>0 }">ys</c:if>" style="width:5%"><input type="text" name="differ" disabled="disabled" value="${handleDynamicMap[item.pid+''].differ }" class="handle_dynamic_input easyui-textbox" style="width: 30px;"></td>
           <td class="handle_dynamic_tb" style="width:20%"><input type="text" name="remark" disabled="disabled" value="${handleDynamicMap[item.pid+''].remark }" class="handle_dynamic_input easyui-textbox" data-options="validType:'length[0,50]'" style="width:160px;"></td>
          </tr>
        </c:forEach>
          <tr>
           <td class="handle_dynamic_tb" colspan="9"><b>操作总天数：${sumHandleDay}天</b></td>
          </tr>
        </table>
       </div>
      </div>
	</div>
	<div title="异常列表" id="tab4">
	<table id="after_Exception_Report_grid"  title="异常上报列表" class="easyui-datagrid" style="height: 705px; width: auto;"
     data-options="
		    url: '${basePath}afterExceptionReportController/afterExceptionReportList.action?projectId=${projectId }',
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <div class="datagrid-toolbar">
     </div>
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true" />
       <th data-options="field:'exceptionType',formatter:formatterAfterExceptionReportType" align="center" halign="center"  >异常类型</th>
       <th data-options="field:'monitorDate',formatter:convertDateTime" align="center" halign="center"  >监控时间</th>
       <th data-options="field:'noticeWay',formatter:formatterNoticeWay" align="center" halign="center" >通知方式</th>
       <th data-options="field:'remark'" align="center" halign="center" >审批意见</th>
       <th data-options="field:'createrName'" align="center" halign="center"  >操作人</th>
       <th data-options="field:'createrDate',formatter:convertDateTime" align="center" halign="center"  >操作时间</th>
      </tr>
     </thead>
    </table>
    </div>
</div>
</body>
</html>