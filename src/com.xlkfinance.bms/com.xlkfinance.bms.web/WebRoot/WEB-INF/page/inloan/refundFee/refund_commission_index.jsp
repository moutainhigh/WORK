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
<script type="text/javascript">

//重置按钮
function majaxReset(){
	$(".ecoTradeId1").hide();
	$('#searchFrom').form('reset')
}

// 编辑
function editItem(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
   		var url ="";
   		if (row[0].backFeeApplyHandleStatus>=<%=com.xlkfinance.bms.common.constant.Constants.APPLY_REFUND_FEE_STATUS_3%>) {
   		    url = "${basePath}refundFeeController/edit.action?projectId="+row[0].projectId+"&pid="+row[0].pid+"&type=4&param='refId':'"+row[0].projectId+"','projectName':'"+row[0].projectName+"','projectId':'"+row[0].projectId+"'";
		}else{
   		    url = "${basePath}refundFeeController/edit.action?projectId="+row[0].projectId+"&pid="+row[0].pid+"&type=4";
		}
 		parent.openNewTab(url,"退佣金办理",true);
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
//打印
function print(){
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		var url = "${basePath}refundFeeController/print.action?pid="+row[0].pid+"&projectId="+row[0].projectId+"&type=4&processDefinitionKey=<%=com.xlkfinance.bms.common.constant.Constants.REFUND_FEE_WORKFLOW_ID%>";
			parent.openNewTab(url, "退佣金打印", true);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}

	//业务办理状态格式化
	function formatterBizApplyHandleStatus(val, row) {
		if (val == 1) {
			return "未申请";
		} else if (val == 2) {
			return "已申请";
		} else if (val == 3) {
			return "已完成";
		} else if (val == 4) {
			return "已归档";
		} else {
			return "未知";
		}
	}
	// 放款到账状态格式化
	function formatterRecStatus(val, row) {
		if (val == 1) {
			return "未到账";
		} else if (val == 2) {
			return "已到账";
		} else {
			return "未知";
		}
	}
	//退款申请状态格式化
	function formatterBackFeeApplyHandleStatus(val, row) {
		if (val == 1) {
			return "未申请";
		} else if (val == 2) {
			return "申请中";
		} else if (val == 3) {
			return "驳回";
		} else if (val == 4) {
			return "审核中";
		} else if (val == 5) {
			return "审核通过";
		} else if (val == 6) {
			return "审核不通过";
		} else if (val == 7) {
			return "已归档";
		} else {
			return "未知";
		}
	}
	//打开退款确认窗口
	function openRefundFeeConfirmDialog() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
		   var status = row[0].backFeeApplyHandleStatus;
		    var isConfirm = row[0].isConfirm;
			if(isConfirm==2){
				$.messager.alert("操作提示", "已确认！", "error");
			}else if(status != 5){
				$.messager.alert("操作提示", "审核状态未通过，不能退款确认！", "error");
			}else{
				//初始化数据
				$("#refundFeeConfirmProjectId").val(row[0].projectId);//
				$("#returnFee").numberbox('setValue', row[0].returnFee);//申请退款金额
				$("#confirmMoney").numberbox('setValue', row[0].returnFee);//确认金额默认为申请退款金额
				$("#confirmDate").datebox('setValue', formatterDate(new Date()));//确认日期默认为当前日期
				$('#refund_fee_confirm_dialog').dialog('open').dialog('setTitle',
						"退款确认--"+row[0].projectName);
			}
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//提交退款确认
	function subRefundFeeConfirmForm() {
		// 提交表单
		$("#refundFeeConfirmForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', ret.header["msg"], 'info');
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
					$('#refund_fee_confirm_dialog').dialog('close');
					$('#collectFileForm').form('reset');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	var refundFeeList = {
		formatProjectName : function(value, row, index) {
			var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>";
			return va;
		},
		formatOperate : function(value, row, index) {
       		var va=""
			return va;
		}
	}
	$(document).ready(function() {
	    $('#grid').datagrid({  
			  rowStyler:function(index,row){    
			      if (row.isChechan==1){    
			          return 'background-color:#FFAF4C;';    
			      }    
			  } 
	    }) ;
	});
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>refundFeeController/list.action?type=4" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td width="80" align="right" height="28">项目编号：</td>
        <td><input class="easyui-textbox" name="projectNumber" id="projectNumber" /></td>
        <td width="80" align="right" height="28">项目名称：</td>
        <td colsapn="2"><input class="easyui-textbox" name="projectName" id="projectName" /></td>
        <td width="80" align="right" height="28">申请人：</td>
        <td><input class="easyui-textbox" name="customerName" id="customerName" /></td>
        <td width="80" align="right" height="28">原业主：</td>
        <td colsapn="2"><input class="easyui-textbox" name="oldHome" id="oldHome" /></td>
       </tr>
       <tr>
        <td align="right" height="28">物业名称：</td>
        <td><input class="easyui-textbox" name="houseName" /></td>
        <td align="right" height="28">买方姓名：</td>
        <td><input class="easyui-textbox" name="buyerName" /></td>
        <td align="right" height="28">卖方姓名：</td>
        <td><input class="easyui-textbox" name="sellerName" /></td>
       </tr>
       <tr>
        <td  align="right" height="28">申请状态：</td>
        <td><select class="easyui-combobox" name="backFeeApplyHandleStatus" style="width: 125px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未申请</option>
          <option value=2>申请中</option>
          <option value=3>驳回</option>
          <option value=4>审核中</option>
          <option value=5>审核通过</option>
          <option value=6>审核不通过</option>
          <option value=7>已归档</option>
        </select></td>
        <td align="right" height="28">收费状态：</td>
        <td><select class="easyui-combobox" id="recStatus" name="recStatus" style="width: 125px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未到账</option>
          <option value=2>已到账</option>
        </select></td>
        <td colspan="3"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>
      </table>
     </div>
    </form>

    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">申请办理</a> 
     <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="print()">打印</a>
     <shiro:hasAnyRoles name="REFUNDFEE_CONFIRM,ALL_BUSINESS">
     <a href="javascript:void(0)"class="easyui-linkbutton" plain="true" onclick="openRefundFeeConfirmDialog()">退款确认</a>
     </shiro:hasAnyRoles>
    </div>

   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="退佣金申请列表" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>refundFeeController/list.action?type=4',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true"
    >
     <thead data-options="frozen:true">
		<tr>
			<th data-options="field:'pid',checkbox:true"></th>
       		<th data-options="field:'projectName',sortable:true,formatter:refundFeeList.formatProjectName" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <!-- 表头 -->
     <thead>
      <tr>
       <!-- <th data-options="field:'pid',checkbox:true"></th>
       <th data-options="field:'projectName',sortable:true,formatter:refundFeeList.formatProjectName" align="center" halign="center">项目名称</th> -->
       <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th>
       <th data-options="field:'backFeeApplyHandleStatus',sortable:true,formatter:formatterBackFeeApplyHandleStatus" align="center" halign="center">退款申请状态</th>
       <th data-options="field:'loanMoney',sortable:true,formatter:formatMoney" align="center" halign="center">借款金额</th>
       <th data-options="field:'returnFee',sortable:true,formatter:formatMoney" align="center" halign="center">退款金额</th>
       <th data-options="field:'confirmMoney',sortable:true,formatter:formatMoney" align="center" halign="center">确认金额</th>
       <th data-options="field:'isConfirm',sortable:true,formatter:formatterCommonConfirm" align="center" halign="center">确认</th>
       <th data-options="field:'confirmDate',sortable:true,formatter:convertDate" align="center" halign="center">确认日期</th>
       <th data-options="field:'bizApplyHandleStatus',sortable:true,formatter:formatterBizApplyHandleStatus" align="center" halign="center">业务办理状态</th>
       <th data-options="field:'projectPassDate',sortable:true,formatter:convertDate" align="center" halign="center">审批时间</th>
       <th data-options="field:'isChechan',sortable:true,formatter:formatterIsChechan" align="center" halign="center">是否撤单</th>
       <th data-options="field:'productName',sortable:true" align="center" halign="center">产品名称</th>
       <th data-options="field:'customerName',sortable:true" align="center" halign="center">申请人</th>
       <th data-options="field:'oldHome',sortable:true" align="center" halign="center">原业主</th>
       <th data-options="field:'houseName',sortable:true" align="center" halign="center">物业名称</th>
       <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方姓名</th>
       <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方姓名</th>  
       <th data-options="field:'recStatus',sortable:true,formatter:formatterRecStatus" align="center" halign="center">放款到账状态</th>
       <th data-options="field:'cancelGuaranteeDate',sortable:true,formatter:convertDate" align="center" halign="center">解保日期</th>
       <th data-options="field:'cz',formatter:refundFeeList.formatOperate" align="center" halign="center">操作</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="refund_fee_confirm_dialog" class="easyui-dialog" buttons="#submitDiv1" style="width: 430px; height: 210px; padding: 10px;" closed="true">
    <form id="refundFeeConfirmForm" name="refundFeeConfirmForm" action="${basePath}refundFeeController/refundFeeConfirm.action" method="post">
     <table style="width: 90%; height: 100px;">
      <tr>
       <td class="label_right1">退款金额：</td>
       <td><input name="returnFee" id="returnFee" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"
        disabled="disabled" style="width: 247px;"
       ></td>
      </tr>
      <tr>
       <td class="label_right1">确认金额：</td>
       <td><input name="confirmMoney" id="confirmMoney" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"
        style="width: 247px;"
       ></td>
      </tr>
      <tr>
       <td class="label_right1">确认日期：</td>
       <td><input name="confirmDate" id="confirmDate" data-options="validType:'selrequired'" class="easyui-datebox" editable="false" panelHeight="auto"
        required="true" style="width: 250px;"
       /></td>
      </tr>
     </table>
     <input type="hidden" name="type" value="4"> <input type="hidden" name="projectId" id="refundFeeConfirmProjectId">
    </form>
   </div>
   <div id="submitDiv1">
    <a id="F" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subRefundFeeConfirmForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#refund_fee_confirm_dialog').dialog('close')"
    >取消</a>
   </div>
  </div>
 </div>
</body>
</html>
