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
<title>业务办理</title>
<style type="text/css">
#tabs .tabs-header .tabs{
  height: 26px;
  position: fixed;
  top:0px;
  background: #E0ECFF;
  z-index: 9999999;
  padding-top;2px;
}
.penal_biz_handle {
	/* border: 1px solid #F00; */
	width: 100%;
	position: relative;
}
.penal_biz_handle_menu {
	width: 9.5%;
	float: left;
	border:1px #ddd solid;
	position:fixed; top:44px;
	left:10px;
}
.ys input{ border: 1px red solid;color: red; font-weight: bold;}
.penal_biz_handle_menu ul {height: auto;overflow: hidden;}
.penal_biz_handle_menu li{height: auto;overflow: hidden;text-align: center;border-bottom: 1px #ddd solid; }
.penal_biz_handle_menu li a{height:40px; line-height: 40px;display: block; }
.penal_biz_handle_menu li a:hover{color: #fff;}
.selecthover{background: #0079c3;}
.lihover{background: #0079c3;}
.selecthover a, .lihover a{color: #fff;font-weight: bold;}
.penal_biz_handle_menu li:last-child{ border-bottom: none;}
.penal_biz_handle_data {
/* border:1px solid red; */
	width: 87%;
	float: left;
	margin-left: 11%;
}

.label_center {height: 28px;text-align: left;width: 165px;}
.label_center1{height: 28px;text-align: left;width: 200px;}
.label_center2{height: 28px;text-align: left;width: 165px;}
.jl{padding-left:20px;}
.table_css{}
.table_css td{font-size:12px; padding: 5px 7px;border-bottom:1px #ddd solid; border-right:1px #ddd solid;}
.table_css td:last-child{  border-right:0;}
.bt {height: 26px;
line-height: 26px;
background:#eee; /* 一些不支持背景渐变的浏览器 */  
    background:-moz-linear-gradient(top, #ffffff, #eee);  
    background:-webkit-gradient(linear, 0 0, 0 bottom, from(#ffffff), to(#eee)); 
}
.bt td{padding:0 7px;}
table, td, tr {
    word-break: break-all;
}
.handle_dynamic_input{
width: 58px;
}
.handle_dynamic_tb{
width: 12.5%;
}
.panel-header {
margin-top: 45px;
}
.window .panel-header {
margin-top: 0;
}
.submit{background:#f9bf00; border:1px #f9bf00 solid;height:40px; line-height:40px;width:90px;box-shadow:none;font-size:1em;outline:none;-webkit-appearance: none; color:#fff; border-radius:5px; display: inline-block; text-align:center;
}
.back{background:#fff; border:1px #ddd solid; height:40px; line-height:40px;width:88px;box-shadow:none;font-size:1em;outline:none;-webkit-appearance: none; color:#333; border-radius:5px; display: inline-block; text-align:center; margin-left:10px;}
.yw .panel-body-noheader{border:0;}
.bl .panel-header {border-left:0;border-right:0;}
.bl .panel-body {border-left:0;border-right:0;border-bottom:0;}
</style>
</head>
<script type="text/javascript">
var applyHandleStatus=${handleInfo.applyHandleStatus};
    function subApplyHandleInfoForm(from){
    	if (applyHandleStatus>=<%=com.xlkfinance.bms.common.constant.Constants.APPLY_HANDLE_STATUS_3%>) {
			$.messager.alert('操作','业务办理申请状态为已完成和已归档，数据不可以修改!','error');
			return;
		}
    	$("#applyHandleInfoForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', "保存成功!", 'info');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
    }
	
	/**
	 * 关闭窗口
	 */
	function closeWindow(){
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var index = parent.$('#layout_center_tabs').tabs('getTabIndex',tab);
		parent.$('#layout_center_tabs').tabs('close',index);
	}
	//打开办理动态文件上传窗口
    function upFile(handleDynamicId,handleFlowName,handleId){
    	$('#saveFile').attr("onclick","submitFileForm('"+handleDynamicId+"','"+handleFlowName+"','"+handleId+"')");
    	$('#upload-edit').dialog('open').dialog('setTitle', "上传资料");
    }
    //提交办理动态文件
	function submitFileForm(handleDynamicId,handleFlowName,handleId){
		 MaskUtil.mask("上传中,请耐心等候......");  
		if($("#offlineMeetingFiles").textbox('getValue')!=''){
			$('#editfileUploadFormedit').attr('enctype','multipart/form-data');
			$('#editfileUploadFormedit').form('submit',{
				url:'${basePath}bizHandleController/uploadFile.action?handleDynamicId='+handleDynamicId+'&remark='+handleFlowName+'&handleId='+handleId,
				success:function(data) {
					    MaskUtil.unmask();
						if(data==1){
							$.messager.alert('操作','保存成功!','info');
							$("#upload-edit").dialog("close");
							$("#grid").datagrid("clearChecked");
							$("#grid").datagrid('reload');
						}else{
							$.messager.alert('操作','保存失败!','error');
						}
						$("#opFileTable").datagrid("reload");
						$("#offlineMeetingFiles").textbox('setValue','');
				},
				
			});
		}else{
			$.messager.alert('操作','请选择文件!','info');
		} 
	}
	// 删除办理动态文件
	function removeItem() {
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		var fileIds = "";
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			fileIds += row.fileId + ",";
		}
		$.messager.confirm("操作提示", "确定删除选择的此批文件吗?", function(r) {
			if (r) {
				$.ajax({
					url : "${basePath}bizHandleController/deleteFile.action",
					cache : true,
					type : "POST",
					data : {'fileIds' : fileIds},
					async : false,
					success : function(data, status) {
						var ret = eval("(" + data + ")");
						if (ret && ret.header["success"]) {
							$("#dlg").dialog('close');
							// 必须重置 datagr 选中的行 
							$("#grid").datagrid("clearChecked");
							$("#grid").datagrid('reload');
						} else {
							// 必须重置 datagr 选中的行 
							$("#grid").datagrid("clearChecked");
							$("#grid").datagrid('reload');
							$.messager.alert('操作提示', ret.header["msg"], 'error');
						}
					}
					});
			}
		});
	}
	//刷新办理动态文件列表
	function refreshFileList(){
		$("#grid").datagrid("clearChecked");
		$("#grid").datagrid('reload');
	}
	
	//保后监管一键下载
	function downLoadAllData(){
		var projectId = ${handleInfo.projectId};
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择需要下载的资料，打勾为选择!", "error");
			return;
		}
		var fileIdStrs = "";
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			fileIdStrs += row.fileId + ",";
		}															
		 window.location.href=BASE_PATH+"beforeLoanController/downLoadZipFileByfileIds.action?projectId="+projectId+"&fileIdStrs="+fileIdStrs;
	}
	
	//刷新业务办理操作日志
	function refreshLogList() {
		$('#grid_log').datagrid({  
		    url:'<%=basePath%>sysLogController/getAllSysLog.action?projectId=${handleInfo.projectId }',  
		    queryParams:{  
		    	logType : '贷中-业务办理'
		    }  
		}); 
	}
	var uploanFilelist = {
			formatFileUrl : function(value, row, index) {
				var fileType=row.fileType;
				fileType = fileType.toLowerCase();
				var va = "<a onclick=downLoadFileByPath('"+row.fileUrl+"','"+row.fileName+"') href='#'> <font color='blue'>下载</font></a>";	
				if (fileType=="bmp"||fileType=="jpeg"||fileType=="jpg"||fileType=="png"||fileType=="gif") {
					va = va+" <a onclick=lookUpFileByPath('"+row.fileUrl+"','"+row.fileName+"') href='#'> <font color='blue'>预览</font></a>";
	    		}
				return va;
			}
		}
	//加载数据
	$(document).ready(function() {
		refreshLogList();
	});
</script>

<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto;">
   <div title="业务办理信息" id="tab1">
    <div class="penal_biz_handle">
     <div class="penal_biz_handle_menu">
     	<ul>
	     	<li class="selecthover"><a href="#sq">申请办理</a></li>
			<li><a href="#dt">办理动态</a></li>
			<li><a href="#tk">财务退款明细</a></li>
     	</ul>     
     </div>
     
     <div class="penal_biz_handle_data" id="sq" >
     <div class="easyui-panel" title="基本项目信息" data-options="collapsible:true">
 <div style="padding:5px">
  <table>
   <tr>
    <td height="35" width="300" align="center">项目名称: <a href="#" onclick="formatterProjectOverview.disposeClick(${projectId})"><font color='blue'> ${projectName }</font></a></td>
    <td><div class="iniTitle">项目编号:${projectNumber}</div></td>
   </tr>
  </table>
 </div>
</div>
      <div class="easyui-panel" title="申请办理" data-options="collapsible:true" >
       <div id="projectBasicNews" style="height: auto; line-height: 35px;">
       <form action="${basePath}bizHandleController/updateApplyHandleInfo.action" id="applyHandleInfoForm" name="applyHandleInfoForm"  method="post">
        <table class="creditsTable" style="padding-left:20px;">
         <tr>
          <!-- <td class="label_center"><font color="red">*</font>合同及放款确认书提交时间：</td> -->
         <%--  <td class="label_center"><input name="subDate" class="easyui-datebox" data-options="required:true" value="${applyHandleInfo.subDate }" editable="false" missingMessage="请填写合同及放款确认书提交时间!"/></td> --%>
          <td class="label_right"><font color="red">*</font>赎楼联系人：</td>
          <td class="label_center"><input name="contactPerson" class="easyui-textbox" data-options="required:true,validType:'length[0,20]'" missingMessage="请填写赎楼联系人!" value="${applyHandleInfo.contactPerson }"/></td>
          <td class="label_right"><font color="red">*</font>联系电话：</td>
          <td class="label_center"><input name="contactPhone" class="easyui-textbox" data-options="required:true,validType:'phone'" missingMessage="请填写联系电话!" value="${applyHandleInfo.contactPhone }"/></td>
          <td class="label_right"><font color="red">*</font>办理时间：</td>
          <td class="label_center"><input name="handleDate" class="easyui-datebox" data-options="required:true" missingMessage="请填写办理时间!" value="${applyHandleInfo.handleDate }" editable="false"/></td>
         </tr>
         <tr>
          <td class="label_right">特殊情况说明：</td>
          <td colspan="3"><textarea rows="3" name="specialCase" maxlength="500" style="width: 100%;">${applyHandleInfo.specialCase }</textarea></td>
         </tr>
         <tr>
          <td class="label_right">备注：</td>
          <td colspan="3"><textarea rows="3" name="remark" maxlength="500" style="width: 100%;">${applyHandleInfo.remark }</textarea></td>
         </tr>
        </table>
        <input type="hidden" name="pid" value="${applyHandleInfo.pid }">
        <input type="hidden" name="handleId" value="${applyHandleInfo.handleId }">
        </form>
       </div>
       
      </div>
      <p class="jl">
         <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="subApplyHandleInfoForm()">保存</a> 
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWindow()">关闭</a>
        </p>
      <div id="dt"></div>
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
           <td class="handle_dynamic_tb">资料上传</td>
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
           <td class="handle_dynamic_tb"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="upFile('${handleDynamicMap[item.pid+''].pid }','${item.name}','${handleDynamicMap[item.pid+''].handleId }')" iconCls="icon-add"  >资料上传</a></td>
          </tr>
        </c:forEach>
          <tr>
           <td class="handle_dynamic_tb" colspan="9"><b>操作总天数：${sumHandleDay }天</b></td>
          </tr>
        </table>
        <!--图标按钮 -->
 <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
  <!-- 操作按钮 -->
  <div style="padding-bottom:5px">
   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="removeItem()">删除</a>
   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="refreshFileList()">刷新</a>
	<shiro:hasAnyRoles name="DOWN_LOAD_ALL_DATA,ALL_BUSINESS">
   	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="downLoadAllData()">一键下载</a>
   </shiro:hasAnyRoles>
   
  </div>
 </div>
 
 
 <div class="dksqListDiv bl" style="margin-top: -30px;">
 <table id="grid" title="办理动态文件列表" class="easyui-datagrid" 
  style="height:100%;width: auto;"
  data-options="
      url: '<%=basePath%>bizHandleController/uploanFilelist.action?handleId=${handleInfo.pid}',
      method: 'POST',
      rownumbers: true,
      singleSelect: false,
      pagination: true,
      sortOrder:'asc',
      remoteSort:false,
      toolbar: '#toolbar',
      idField: 'pid',
      fitColumns:true">
  <!-- 表头 -->
  <thead><tr>
      <th data-options="field:'fileId',checkbox:true" ></th>
      <th data-options="field:'fileName',sortable:true,width:167" align="center" halign="center"  >文件名</th>
      <th data-options="field:'fileType',sortable:true,width:44" align="center" halign="center"  >类型</th>
      <th data-options="field:'remark',sortable:true,width:120" align="center" halign="center"  >办理流程</th>
      <th data-options="field:'uploadUserName',sortable:true" align="center" halign="center"  >上传者</th>
      <th data-options="field:'fileUrl',formatter:uploanFilelist.formatFileUrl,sortable:true" align="center" halign="center"  >操作</th>
  </tr></thead>
 </table>
 </div>

       </div>
      </div>
     <div id="upload-edit" class="easyui-dialog" buttons="#upFileinfo" style="width: 650px; height: 150px; padding: 10px 20px;" closed="true">
      <form id="editfileUploadFormedit" name="fileUploadeditForm" method="post">
       <input type="hidden" id="projectId" name="projectId" value=""> 
       <table>
        <tr>
         <td><span class="cus_red">*</span>办理动态文件：</td>
         <td><input class="easyui-filebox offlineMeetingInput" name="offlineMeetingFile" id="offlineMeetingFiles" style="width: 425px"></td>
        </tr>
       </table>
      </form>
     </div>
     <div id="upFileinfo">
      <a id="saveFile" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitFileForm()">保存</a> <a href="javascript:void(0)"
       class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#upload-edit').dialog('close')"
      >取消</a>
     </div>
     <div id="tk"></div>
     <div class="easyui-panel" title="财务退款明细" data-options="collapsible:true">
       <div id="projectBasicNews" style="height: auto; line-height: 30px;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_css" >
          <tr class="bt">
           <td class="handle_dynamic_tb">退款项目</td>
           <td class="handle_dynamic_tb">退款金额</td>
           <td class="handle_dynamic_tb">收款帐号</td>
           <td class="handle_dynamic_tb">收款户名</td>
           <td class="handle_dynamic_tb">付款日期</td>
           <td class="handle_dynamic_tb">操作员</td>
          </tr>
         </table>
         <c:forEach var="item" varStatus="status" begin="1" end="4">
         <form action="#" id="refundDetailsForm_${refundDetailsMap[status.index+''].refundPro}" name="refundDetailsForm_${refundDetailsMap[status.index+''].refundPro}"  method="post">
         <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_css" >
          <tr>
           <td class="handle_dynamic_tb">${refundProMap[status.index+'']}<br><span class="subMsg"></span></td>
           <td class="handle_dynamic_tb"><input type="text" name="refundMoney" value="${refundDetailsMap[status.index+''].refundMoney}" class="handle_dynamic_input easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"></td>
           <td class="handle_dynamic_tb"><input type="text" name="recAccount" data-options="validType:'length[0,30]'" value="${refundDetailsMap[status.index+''].recAccount}" class="handle_dynamic_input easyui-textbox" ></td>
           <td class="handle_dynamic_tb"><input type="text" name="recName" data-options="validType:'length[0,20]'" value="${refundDetailsMap[status.index+''].recName}" class="handle_dynamic_input easyui-textbox" ></td>
           <td class="handle_dynamic_tb"><input type="text" name="payDate" value="${refundDetailsMap[status.index+''].payDate}" class="handle_dynamic_input easyui-datebox" editable="false"></td>
           <td class="handle_dynamic_tb"><input type="text" disabled="disabled" name="operator" value="${refundDetailsMap[status.index+''].operator}" class="handle_dynamic_input easyui-textbox" ></td>
          </tr>
        </table>
           <input type="hidden" name="refundPro" value="${refundDetailsMap[status.index+''].refundPro}" >
           <input type="hidden" name="handleId" value="${refundDetailsMap[status.index+''].handleId}" >
           <input type="hidden" name="pid" value="${refundDetailsMap[status.index+''].pid}" >
        </form>
        </c:forEach>
        
       </div>
      </div>
     <div class="easyui-panel yw" title="业务办理操作日志" data-options="collapsible:true">
      <div class="dksqListDiv" style="height: 300px;">
       <table id="grid_log"  style="height: 100%; width: auto;"
        data-options="
      url: '',
      method: 'POST',
      rownumbers: true,
      singleSelect: false,
      pagination: true,
      sortOrder:'asc',
      remoteSort:false,
      idField: 'pid',
      fitColumns:true"
       >
        <!-- 表头 -->
        <thead>
         <tr>
          <th data-options="field:'realName',sortable:true,width:44" align="center" halign="center">操作用户</th>
          <th data-options="field:'logDateTime',sortable:true,width:64" align="center" halign="center">日志时间</th>
          <th data-options="field:'logMsg',sortable:true,width:400" align="left" halign="center">日志内容</th>
         </tr>
        </thead>
       </table>
      </div>
     </div>
     </div>
    </div>
   </div>
  </div>
 </div>
<script>
$("li").click(function(){
$(this).addClass("selecthover").siblings().removeClass("selecthover");
}).hover(function(){
$(this).addClass("lihover");
},function () {
$(this).removeClass("lihover");
})
</script>
</body>
</html>