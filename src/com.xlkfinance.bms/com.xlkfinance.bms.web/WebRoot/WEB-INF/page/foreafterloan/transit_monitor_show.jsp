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
function openExceptionReportDialog(){
	$('#exception_report_dialog').dialog('open')
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
function subExceptionReportForm(){
	var noticeWay="";
	$("#exceptionReportForm input:checkbox[name='noticeWayTemp']:checked").each(function(i){
	       if(0==i){
	    	   noticeWay = $(this).val();
	       }else{
	    	   noticeWay += (","+$(this).val());
	       }
	 });  
	$("#noticeWay").val(noticeWay);
	// 提交表单
	$("#exceptionReportForm").form('submit', {
		onSubmit : function() {
			var bool =$(this).form('validate');
			if (bool) {
	          MaskUtil.mask("提交中,请耐心等候......");  
			}
			return bool;
		},
		success : function(result) {
			MaskUtil.unmask();
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				$.messager.alert('操作提示', ret.header["msg"], 'info');
                //关闭窗口
				$('#exception_report_dialog').dialog('close');
				$('#after_Exception_Report_List_grid').datagrid("clearChecked");
				$('#after_Exception_Report_List_grid').datagrid('reload');
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}
$(document).ready(function(){
	
});
	
</script>
<body class="easyui-layout">

<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
	<div title="上报异常" id="tab1">
     <a href="javascript:void(0)" class="easyui-linkbutton" style="margin: 10px;" iconCls="icon-edit" plain="true" onclick="openExceptionReportDialog()">上报异常</a>
	<table id="after_Exception_Report_List_grid"  title="异常上报列表" class="easyui-datagrid" style="height: 655px; width: auto;"
     data-options="
		    url: '${basePath}afterExceptionReportController/afterExceptionReportList.action?projectId=${projectId }',
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    idField: 'pid',
		    singleSelect: true,
		    fitColumns:true"
    >
     <!-- 表头 -->
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
    
    <div id="exception_report_dialog" title="异常上报" class="easyui-dialog" data-options="modal:true" buttons="#submitDiv1" style="width: 353px; height: 300px; padding: 10px;" closed="true">
	   <form id="exceptionReportForm" name="exceptionReportForm" action="${basePath}afterExceptionReportController/saveAfterExceptionReport.action" method="post">
	   <input type="hidden" name="noticeWay" id="noticeWay">
	   <input type="hidden" id="projectId" name="projectId" value="${projectId }"/>
	     <table style="width: 100%; height: 200px;">
	       <tr>
	        <td>通知方式：</td>
	        <td><input type="checkbox" name="noticeWayTemp" value="1">短信<input type="checkbox" name="noticeWayTemp" value="2">邮件
	       </td>
	       </tr>
	       <tr>
	        <td>监控人：</td>
	        <td><select class="select_style easyui-combobox" id="monitorUserId" name="monitorUserId" required="true" data-options="validType:'selrequired'" style="width:100px" editable="false">
	        <option value="-1">--请选择--</option>
	        <c:forEach var="item" items="${monitorUserMap}">   
		    <option value="${item.key}">${item.value}</option>
		    </c:forEach>  
	       </select>
	       </td>
	       </tr>
	       <tr>
	        <td>监控类型：</td>
	        <td><select class="select_style easyui-combobox" id="exceptionType" name="exceptionType" required="true" data-options="validType:'selrequired'" style="width:100px" editable="false">
	        <option value="-1">--请选择--</option>
	        <option value="1">展期</option>
	        <option value="2">逾期</option>
	        <option value="3">房产查封</option>
	        <option value="4">新增诉讼</option>
	        <option value="100">其他</option>
	       </select>
	       </td>
	       </tr>
	       <!--  <tr>
	        <td>监控时间：</td>
	        <td><input name="monitorDate" id="monitorDate" class="easyui-datebox" data-options="required:true" editable="false" ></td>
	       </tr> -->
	       <tr>
	        <td>监控说明：</td>
	        <td><textarea rows="2" id="remark" name="remark" maxlength="500" style="width: 220px;"></textarea></td>
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
	<div title="办理动态" id="tab2" >
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
           <td class="handle_dynamic_tb" colspan="9"><b>操作总天数：${sumHandleDay }天</b></td>
          </tr>
        </table>
       </div>
      </div>
	</div>
</div>

</body>
</html>