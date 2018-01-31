<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/tools/dataformat.js"></script>
<style type="text/css">
.scheduleCount {
  color: #fff;
  background: rgb(41, 106, 207);
  padding: 0px 5px;
  border-radius: 5px;
  width: auto;
  height: auto;
}
</style>
<script type="text/javascript">
	//重置按钮
	function majaxReset() {
		$('#searchFrom').form('reset');
	}

	// 编辑
	function editItem() {
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			url = "${basePath}bizHandleController/" + row[0].pid+ "/edit.action?projectId="+row[0].projectId+"&projectNumber="+row[0].projectNumber+"&projectName="+row[0].projectName;
			parent.openNewTab(url, "业务办理编辑", true);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}

	// 归档（解保）
	function cancelGuarantee() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			$.messager.confirm('操作提示','归档后不能再申请退费，确认归档(解保)?',function(e){
			if (e) {
				$.ajax({
					url : "${basePath}bizHandleController/cancelGuarantee.action",
					cache : true,
					type : "POST",
					data : {'projectId':row[0].projectId},
					async : false,
					success : function(data, status) {
						var ret = eval("(" + data + ")");
						if (ret && ret.header["success"]) {
							$.messager.alert("操作提示", "归档成功！");
							$("#grid").datagrid("clearChecked");
							$("#grid").datagrid('reload');
						}else{
							$.messager.alert('操作提示', ret.header["msg"], 'error');
						}
					}
				});
			}
			});
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	
	//打开办理反馈窗口
    function openFeedback(){
    	var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			// 判断选中的状态是否是申请中,只有申请中的状态才可以编辑
			if (row[0].applyHandleStatus == 2) {
			   var handleDate = new Date().format('yyyy-MM-dd',row[0].handleDate);
    	       $("#handleDate").textbox('setValue',handleDate);
    	       $('#feedback-edit').dialog('open').dialog('setTitle', "办理反馈--"+row[0].projectName);
			} else {
				$.messager.alert("操作提示", "业务办理只有已申请才能进行反馈！", "error");
			}
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
    }
    //提交办理反馈
    function submitFeedbackForm(){
    	var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
		var feedbackForm = document.getElementById("feedbackForm");
		var handleDate=$("#handleDate").datebox('getValue');//办理时间
		var feedback=feedbackForm.feedback.value;//办理反馈内容
				$.ajax({
					url : "${basePath}bizHandleController/submitFeedback.action",
					cache : true,
					type : "post",
					data : {'handleId':row[0].pid,'handleDate':handleDate,'feedback':feedback},
					async : false,
					success : function(data, status) {
						if (data=="1") {
							$.messager.alert("操作提示", "提交成功！");
							$("#feedback-edit").dialog("close");
							$("#grid").datagrid("clearChecked");
							$("#grid").datagrid('reload');
						}else{
			                $.messager.alert("操作提示", "提交失败！", "error");
						}
					}
				});
    }
    function changeisFeedback(){
		var feedbackForm = document.getElementById("feedbackForm");
		var isFeedback =feedbackForm.isFeedback.value;
    	if (isFeedback==1) {
    		 $("#feedback").val('');
		}
	}
    function searchByDynamic(dynamicName){
    	$("#taskName").textbox('setValue',dynamicName);
    	ajaxSearchPage('#grid','#searchFrom');
    	$(".scheduleCount").text("0");
    	$.ajax({
			url : "${basePath}bizHandleController/qeuryHandleDynamicCountMapList.action",
			cache : true,
			type : "POST",
			data : $("#searchFrom").serialize(),
			async : false,
			success : function(data, status) {
				var ret = eval("(" + data + ")");
				var resultList=ret['resultList'];
				for (var i = 0; i < resultList.length; i++) {
					var dynamicName=resultList[i].dynamicName;
					var count=resultList[i].count;
					$("#"+dynamicName).text(count);
				}
			}
		});
    	$("#taskName").textbox('setValue','');
	}
	//提交办理动态表单
    function subHandleDynamicForm(){
		$("#handleDynamicForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', ret.header["msg"], 'info');
					$("#dynamicHandle-dialog").dialog("close");
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
    }
    //根据handleId获取正在执行中的办理动态信息，没有正在执行中的办理动态返回null
    function getHandleDynamicInfo(handleId){
    	var hd=null;
    	$.ajax({
			url : "${basePath}bizHandleController/getHandleDynamicInfo.action",
			cache : true,
			type : "POST",
			data : {"handleId":handleId},
			async : false,
			success : function(data, status) {
				if(status=="success"){
					hd=eval("(" + data + ")");
				}
			}
		});
    	return hd;
	}
    //打开办理动态文件上传窗口
    function upFile(){
    	var row = $('#grid').datagrid('getSelections');
    	var handleDynamicId=$("#handleDynamicId").textbox('getValue');//办理动态id
    	var handleId=$("#handleId").textbox('getValue');//业务办理id
    	var handleFlowName=row[0].taskName;//当前办理动态
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
						}else{
							$.messager.alert('操作','保存失败!','error');
						}
						$("#offlineMeetingFiles").textbox('setValue','');
				},
				
			});
		}else{
			$.messager.alert('操作','请选择文件!','info');
		} 
	}
    var bizHandleList = {
    	formatProjectName:function(value, row, index){
    		var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>";
			return va;
		},
		formatTooltip:function(value, row, index){
			if (value==null) {
				return "";
			}
	        var abValue = value;
            if (value.length>=12) {
               abValue = value.substring(0,10) + "...";
            }
			var va = '<a href="#" title="' + value + '" class="note">' + abValue + '</a>';
            return va;
		}
	}
    //初始化
	$(document).ready(function() {
		setBankCombobox("oldLoanBank",0,"-1");
		$("#oldLoanBank").combobox({ 
			onChange:function(){
				var newLoanBank = $("#oldLoanBank").combobox("getValue");
				setBankCombobox("oldLoanBankBranch",newLoanBank,"-1");
			}
		});
        $('#grid').datagrid({  
        	//当列表数据加载完毕，则初始化办理动态条数
            onLoadSuccess:function(dynamicName){  
            	//清除选择
            	$(".dynamic_linkbutton").removeClass("l-btn-selected l-btn-plain-selected");
            	$(".scheduleCount").text("0");
            	$.ajax({
        			url : "${basePath}bizHandleController/qeuryHandleDynamicCountMapList.action",
        			cache : true,
        			type : "POST",
        			data : $("#searchFrom").serialize(),
        			async : false,
        			success : function(data, status) {
        				var ret = eval("(" + data + ")");
        				var resultList=ret['resultList'];
        				for (var i = 0; i < resultList.length; i++) {
        					var dynamicName=resultList[i].dynamicName;
        					var count=resultList[i].count;
        					$("#"+dynamicName).text(count);
        				}
        			}
        		});
            	//备注提示
            	$(".note").tooltip({
                     onShow: function(){
                            $(this).tooltip('tip').css({ 
                                width:'300',
                                boxShadow: '1px 1px 3px #292929'                        
                            });
                      }
                 })
            },
            onDblClickRow: function (rowIndex, rowData) { 
            	var resurl=getHandleDynamicInfo(rowData.handleId);
            	if (resurl==null) {
            		 $.messager.alert("操作提示", "该项目暂无您办理的任务", "error");
            		 return;
				}
            	var hd=resurl['handleDynamic'];
            	//旧数据，回款，不处理
            /* 	if (hd.handleFlowId==10) {
            		return;
				} */
            	var handleUserSet=resurl['handleUserSet'];
            	$("#currentHandleUserId").combobox("loadData", handleUserSet);

            	$("#handleId").textbox('setValue',rowData.handleId);
            	$("#handleDynamicId").textbox('setValue',hd.pid);
            	$("#finishDate").datebox('setValue',hd.finishDate);
            	$("#handleDay").numberbox('setValue',hd.handleDay);
            	$("#fixDay").numberbox('setValue',hd.fixDay);
            	$("#operator").textbox('setValue',hd.operator);
            	$("#differ").numberbox('setValue',hd.differ);
            	$("#dynamicHandle_remark").val(hd.remark);
            	$('#dynamicHandle-dialog').dialog('open').dialog('setTitle', "业务办理");
            },
            rowStyler:function(index,row){    
                if (row.isChechan==1){    
                    return 'background-color:#FFAF4C;';    
                }    
            } 
        }) ;
		//业务办理动态窗口关闭时，清空表单数据
	 	$('#dynamicHandle-dialog').dialog({
	 	    onClose:function(){
	 	    	$('#handleDynamicForm').form('reset');
	 	    	$("#currentHandleUserId").combobox("loadData", "");
	 	    	$("#grid").datagrid("clearChecked");
				$("#grid").datagrid('reload');
	 	    }
	 	});
	});
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<!--图标按钮 -->
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="<%=basePath%>bizHandleController/list.action" method="post" id="searchFrom" name="searchFrom" >
			<!-- 查询条件 -->
			<div style="padding:5px">
				<table>
					<tr>
						<td width="80" align="right" height="28">项目编号：</td>
						<td>
							<input class="easyui-textbox" name="projectNumber" id="projectNumber" style="width: 150px" />
						</td>
						<td width="100" align="right"  height="28">项目名称：</td>
						<td colsapn="2">
							<input class="easyui-textbox" name="projectName" id="searchProjectName"  style="width: 150px" />						
						</td>
<!-- 						<td width="100" align="right" height="28"></font>赎楼银行：</td> -->
<!-- 						<td> -->
<!-- 							<select id="oldLoanBank" class="easyui-combobox"   name="oldLoanBank" editable="false" style="width:129px;" /> -->
<!-- 						</td> -->
<!-- 						<td colspan="2"> -->
<!-- 							<select id="oldLoanBankBranch" class="easyui-combobox"  name="oldLoanBankBranch" editable="false" style="width:200px;" /> -->
<!-- 						</td> -->
						<td align="right" height="28">客户经理：</td>
						<td>
							<input class="easyui-textbox" name="pmUserName" style="width: 150px" />
						</td>
					</tr>
					<tr>
						<td width="80" align="right" height="28">到帐状态：</td>
						<td >
							<select class="easyui-combobox" id="recStatus"  name="recStatus" style="width:150px" panelHeight="auto" editable="false" >
								<option value=-1  selected="selected" >--请选择--</option>
							    <option value=1 >未到账</option>
								<option value=2 >已到账</option>
							</select>
						</td>
						<td width="80" align="right" height="28">申请办理状态：</td>
						<td >
							<select class="easyui-combobox" id="applyHandleStatus"  name="applyHandleStatus" style="width:150px" panelHeight="auto" editable="false" >
								<option value=-1  selected="selected" >--请选择--</option>
							    <option value=1 >未申请</option>
								<option value=2 >已申请</option>
								<option value=3 >已完成</option>
								<option value=4 >已归档</option>
							</select>
						</td>
						<td width="80" align="right"  height="28">审批状态：</td>
						<td >
							<select class="easyui-combobox" name="projectStatus" style="width:150px" panelHeight="auto" editable="false" >
								<option value=-1  selected="selected" >--请选择--</option>
							    <option value=1 >待客户经理提交</option>
								<option value=2 >待部门经理审批</option>
								<option value=3 >待业务总监审批</option>
								<option value=4 >待审查员审批</option>
								<option value=5 >待风控初审</option>
								<option value=6 >待风控复审</option>
								<option value=7 >待风控终审</option>
								<option value=8 >待风控总监审批</option>
								<option value=9 >待总经理审批</option>
								<option value=10 >已审批</option>
								<option value=11 >已放款</option>
								<option value=12 >业务办理已完成</option>
								<option value=13 >已归档</option>
								<option value=15 >待合规复审</option>
							</select>                
						</td>
					</tr>
                  <tr>
                   <td align="right" height="28">物业名称：</td>
                   <td><input class="easyui-textbox" name="houseName" style="width: 150px" /></td>
                   <td align="right" height="28">买方姓名：</td>
                   <td><input class="easyui-textbox" name="buyerName" style="width: 150px" /></td>
                   <td align="right" height="28">卖方姓名：</td>
                   <td><input class="easyui-textbox" name="sellerName" style="width: 150px" /></td>
                  </tr>
					<tr>
						<td colspan="3">
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
							<a href="#" onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;">重置</a>
						</td>
					</tr>				
				</table>
			</div>
           <div class="none">
           <input type="hidden" class="easyui-textbox" name="taskName" id="taskName"/>
           </div>
		</form>
		
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">查看</a>
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a> -->
           <shiro:hasAnyRoles name="DYNAMIC_FEEDBACK,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openFeedback()">办理反馈</a>
           </shiro:hasAnyRoles>
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="test_flow()">办理动态</a> -->
           <shiro:hasAnyRoles name="ARCHIVED,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="cancelGuarantee()">归档（解保）</a>
           </shiro:hasAnyRoles>
		</div>
		<div class="easyui-panel" style="padding:5px;">
		<!-- <a href="#" onclick="searchByDynamic('发放贷款')" class="easyui-linkbutton dynamic_linkbutton" data-options="toggle:true,group:'g2',plain:true">发放贷款<span class="scheduleCount" id="发放贷款">0</span></a> -->
		<a href="#" onclick="searchByDynamic('赎楼')" class="easyui-linkbutton dynamic_linkbutton" data-options="toggle:true,group:'g2',plain:true">赎楼<span class="scheduleCount" id="赎楼">0</span></a>
		<a href="#" onclick="searchByDynamic('取旧证')" class="easyui-linkbutton dynamic_linkbutton" data-options="toggle:true,group:'g2',plain:true">取旧证<span class="scheduleCount" id="取旧证">0</span></a>
		<a href="#" onclick="searchByDynamic('注销抵押')" class="easyui-linkbutton dynamic_linkbutton" data-options="toggle:true,group:'g2',plain:true">注销抵押<span class="scheduleCount" id="注销抵押">0</span></a>
		<a href="#" onclick="searchByDynamic('过户')" class="easyui-linkbutton dynamic_linkbutton" data-options="toggle:true,group:'g2',plain:true">过户<span class="scheduleCount" id="过户">0</span></a>
		<a href="#" onclick="searchByDynamic('取新证')" class="easyui-linkbutton dynamic_linkbutton" data-options="toggle:true,group:'g2',plain:true">取新证<span class="scheduleCount" id="取新证">0</span></a>
		<a href="#" onclick="searchByDynamic('抵押')" class="easyui-linkbutton dynamic_linkbutton" data-options="toggle:true,group:'g2',plain:true">抵押<span class="scheduleCount" id="抵押">0</span></a>
		<a href="#" onclick="searchByDynamic('回款')" class="easyui-linkbutton dynamic_linkbutton" data-options="toggle:true,group:'g2',plain:true">回款<span class="scheduleCount" id="回款">0</span></a>
       </div>
	</div>
	<div class="dksqListDiv" style="height:100%;">
	<table id="grid" title="业务受理申请列表" 
		style="height:100%;width: auto;"
		data-options="
		    url: '<%=basePath%>bizHandleController/list.action',
		    method: 'POST',
		    rownumbers: true,
		    queryParams:{'projectName':'${projectName}'},<!-- //此处，如果是从任务列表跳转过来，则projectName是有值的，此时定位该任务的项目数据 -->
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true">
		<!-- 表头 -->
		<thead data-options="frozen:true">
			<tr>
			    <th data-options="field:'pid',checkbox:true" > </th>
            	<th data-options="field:'projectId',hidden:true">项目id</th>
		    	<th data-options="field:'projectName',sortable:true,formatter:bizHandleList.formatProjectName" align="center" halign="center"  >项目名称</th>
			</tr>
		</thead>
		<thead><tr>
		    <!-- <th data-options="field:'pid',checkbox:true" > </th>
            <th data-options="field:'projectId',hidden:true">项目id</th>
		    <th data-options="field:'projectName',sortable:true,formatter:bizHandleList.formatProjectName" align="center" halign="center"  >项目名称</th> -->
		    <th data-options="field:'projectNumber',sortable:true" align="center" halign="center"  >项目编号</th>
            <th data-options="field:'houseName',sortable:true" align="center" halign="center">物业名称</th>
            <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方姓名</th>
            <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方姓名</th>
              <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">客户经理</th>
		    <th data-options="field:'projectStatus',sortable:true,formatter:formatterProjectStatus" align="center" halign="center"  >项目状态</th>
            <th data-options="field:'isChechan',sortable:true,formatter:formatterIsChechan" align="center" halign="center">是否撤单</th>
		    <th data-options="field:'projectPassDate',sortable:true,formatter:convertDate" align="center" halign="center"  >审批时间</th>
		    <th data-options="field:'recStatus',sortable:true,formatter:formatterRecStatus" align="center" halign="center"  >放款到帐状态</th>
		    <th data-options="field:'applyHandleStatus',sortable:true,formatter:formatterApplyHandleStatus" align="center" halign="center"  >申请办理状态</th>
		    <th data-options="field:'taskName',sortable:true" align="center" halign="center"  >办理动态</th>
		    <th data-options="field:'oldLoanBank',sortable:true" align="center" halign="center">赎楼银行</th>
    		<th data-options="field:'oldLoanBankBranch',sortable:true" align="center" halign="center">赎楼支行</th>
		    <th data-options="field:'handleDate',sortable:true,formatter:convertDate" align="center" halign="center"  >办理时间</th>
		    <th data-options="field:'feedback',sortable:true,formatter:bizHandleList.formatTooltip,width:100" align="center" halign="center"  >办理反馈</th>
            <th data-options="field:'createrDate',sortable:true,formatter:convertDate" align="center" halign="center"  >创建时间</th>
		</tr></thead>
	</table>
	</div>
      <div id="feedback-edit" class="easyui-dialog" data-options="modal:true" buttons="#feedbackDiv" style="width: 513px; height: 248px; padding: 10px 20px;" closed="true">
      <form id="feedbackForm" name="feedbackForm" method="post">
       <input type="hidden" id="projectId" name="projectId" value=""> 
       <table>
        <tr>
         <td><input type="radio" checked="checked" name="isFeedback" onchange="changeisFeedback()" value="1">办理时间：<input class="easyui-datebox" name="handleDate" id="handleDate" style="width:200px"/></td>
         <td>
         <input type="radio" name="isFeedback" onchange="changeisFeedback()" value="2">暂缓： 
         <select class="easyui-combobox" id="xxx" name="xxx" style="width: 150px" panelHeight="auto"editable="false">
         <option value=1 selected="selected">原因</option>
       </select>
       </td>
        </tr>
        <tr>
        <td colspan="2">
        <textarea style="height:130px;width:469px;" id="feedback" class="text_style" name="feedback" placeholder="反馈内容"></textarea>
        </td>
        </tr>
       </table>
      </form>
     </div>
     <div id="feedbackDiv">
      <a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitFeedbackForm()">提交</a> <a href="javascript:void(0)"
       class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#feedback-edit').dialog('close')"
      >取消</a>
     </div>
      <div id="dynamicHandle-dialog" class="easyui-dialog" data-options="modal:true" buttons="#dynamicHandleOperateDiv" style="width: 430px; height: 330px; padding: 10px" closed="true">
      <form id="handleDynamicForm" name="handleDynamicForm" action="${basePath}bizHandleController/updateHandleDynamic.action" method="post">
       <table style="width: 100%; height: 200px;">
         <tr>
          <td>完成日期:</td>
          <td><input name="finishDate" id="finishDate" class="easyui-datebox" data-options="required:true" editable="false" ></td>
          <td>操作天数:</td>
          <td><input name="handleDay" id="handleDay" class="easyui-numberbox" editable="false" style="width: 100px;"></td>
         </tr>
         <tr>
         <tr>
          <td>操作人:</td>
          <td><input name="operator" id="operator" class="easyui-textbox" editable="false" style="width: 100px;"></td>
          <td>规定天数:</td>
          <td><input name="fixDay" id="fixDay" class="easyui-numberbox" editable="false" style="width: 100px;"></td>
         </tr>
         <tr>
          <td>办理人:</td>
          <td>
          <input class="easyui-combobox" id="currentHandleUserId" name="currentHandleUserId" style="width:100px" data-options="valueField:'id', textField:'realName', required:true" editable="false">  </td>
          <td>差异:</td>
          <td>
          <input name="differ" id="differ" class="easyui-numberbox" editable="false" style="width: 100px;"></td>
         </tr>
         <tr>
          <td>备注:</td>
          <td colspan="3"><textarea rows="2" id="dynamicHandle_remark" class="text_style" name="remark" maxlength="500" style="width: 302px;height: 53px;"></textarea></td>
         </tr>
         <tr>
          <td colspan="4"><a href="javascript:void(0)" onclick="upFile()" class="easyui-linkbutton" iconCls="icon-add" >资料上传</a></td>
         </tr>
       </table>
       <div class="none">
       <input type="hidden" class="easyui-textbox" name="handleId" id="handleId">
       <input type="hidden" class="easyui-textbox" id="handleDynamicId" name="pid"/>
       </div>
    </form>
     </div>
     <div id="dynamicHandleOperateDiv">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subHandleDynamicForm()">提交</a> <a href="javascript:void(0)"
       class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dynamicHandle-dialog').dialog('close')"
      >取消</a>
     </div>
     <div id="upload-edit" class="easyui-dialog" data-options="modal:true" buttons="#upFileinfo" style="width: 650px; height: 150px; padding: 10px 20px;" closed="true">
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
      <a id="saveFile" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">保存</a> <a href="javascript:void(0)"
       class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#upload-edit').dialog('close')"
      >取消</a>
     </div>
	</div>
	</div>
</body>
</html>
