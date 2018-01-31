<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 回款管理的页面 -->
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">
	//审批状态
	function formatterApplyStatus(val,row){
		if (val == 1) {
			return "未申请";
		} else if (val == 2) {
			return "审核中";
		} else if (val == 3) {
			return "已回款";
		} else if (val == 4) {
			return "审核驳回";
		} else if (val == 5) {
			return "审核否决";
		} else {
			return "未知";
		}
	}
	//formatAomMoney
	function formatAomMoney(value,row,index){
		if(value){
			return accounting.formatMoney(value, "", 2, ",", ".");
		}else{
			return "--";
		}
	}
	
	function lookupItem(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
			parent.openNewTab("${basePath}aomFinanceController/toEditPaymentInfo.action?editType=3&pid="
					+ row[0].orgCashFlowApplyId,"查看回款信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	//打开放款确认
	function openPaymentInfo(){
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
	 		if(row[0].orgCashFlowApplyStatus == 2){
	 			$("#pid").val(row[0].orgCashFlowApplyId);
	 			$("#orderId").val(row[0].orderId);
	 			$("#money").numberbox('setValue',row[0].money);
	 			
	 			$("#bankName").textbox("setValue",row[0].bankName);
	 			$("#accountName").textbox("setValue",row[0].accountName);
	 			$("#bankCardNo").textbox("setValue",row[0].bankCardNo);
	 			var url = '${basePath}beforeLoanController/downloadData.action?path='+row[0].attachmentUrl+"&fileName="+row[0].projectName+"-回款凭证";
	 			$("#fileUrl").attr("href",url);
	 			$("#fileUrl").text(row[0].attachmentName);
		 		$('#payment_dialog').dialog('open').dialog('setTitle', row[0].projectName+"回款确认");
	 		}else{
	 			$.messager.alert("操作提示","只有审核中的回款才能进行确认操作！","error");
	 		}
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选中一条数据进行操作,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	function updatePaymentInfo(){
		// 提交表单
		$("#paymentForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', ret.header["msg"], 'info');
	                //关闭窗口
					$('#payment_dialog').dialog('close');
					// 刷新
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
		
	}
</script>
<title>回款管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getOrgRepaymentIndex.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">项目名称：</td>
				<td>
					<input name="projectName" class="easyui-textbox" style="width: 129px;" />
				</td>
				<td width="80" align="right">客户名称：</td>
				<td><input name="orgCustomerName" class="easyui-textbox" style="width: 129px;" /></td>
				<td width="80" align="right" >回款状态：</td>
				<td colsapn="2">
					<select class="select_style easyui-combobox" width="129px;" editable="false" name="orgCashFlowApplyStatus">
						  <option value=-1 selected="selected">--请选择--</option>
			              <option value=2>审核中</option>
			              <option value=3>已回款</option>
			              <option value=4>审核驳回</option>
			              <option value=5>审核否决</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="80" align="right" >提单日期：</td>
				<td colsapn="2">
					<input name="requestDttm" id="requestDttm" class="easyui-datebox" editable="false"/> <span>~</span> 
					<input name="requestDttmEnd" id="requestDttmEnd" class="easyui-datebox" editable="false"/>
				</td>
				<td width="80" align="right" >回款日期：</td>
				<td colsapn="2">
					<input name="accountDate" class="easyui-datebox" editable="false"/> <span>~</span> 
					<input name="accountDateEnd" class="easyui-datebox" editable="false"/>
				</td>
				<td colspan="2">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 10px;" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
				<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="openPaymentInfo()">回款确认</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
	</div>

</div>
<div class="dksqListDiv" style="height:100%;">
<table id="grid" title="回款管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getOrgRepaymentIndex.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    singleSelect: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    width: '100%',
	 	height: '100%',  
	 	 striped: true, 
	 	 loadMsg: '数据加载中请稍后……',  
	    fitColumns:true" >
	    
	<!--
	 表头 -->
	<thead>
		<tr>
			<th data-options="field:'orgCashFlowApplyId',checkbox:true"></th>
			<th data-options="field:'projectName',width:80,sortable:true" >项目名称</th>
			<th data-options="field:'pmUserName',width:50,sortable:true">提交人</th>
			<th data-options="field:'customerName',width:50,sortable:true">客户名称</th>
			<th data-options="field:'receMoney',formatter:formatAomMoney,width:50">放款金额(元)</th>
			<th data-options="field:'receDate',width:50,sortable:true">放款日期</th>
			<th data-options="field:'loanDays',width:50,sortable:true">借款天数</th>
			<th data-options="field:'paymentDate',width:50,sortable:true">预计回款日期</th>
			<th data-options="field:'money',formatter:formatAomMoney,width:50">回款金额(元)</th>
			<th data-options="field:'accountDate',width:50,sortable:true">回款日期</th>
			<th data-options="field:'attachmentName',width:50,sortable:true">回款凭证</th>
			<th data-options="field:'orgCashFlowApplyStatus',width:50,formatter:formatterApplyStatus">回款状态</th>
			<th data-options="field:'customerPhone',width:50,sortable:true">客户电话</th>
			<th data-options="field:'customerCard',width:80,sortable:true">身份证号</th>
		</tr>
	</thead>
</table>
</div>
<!-- 回款确认开始 -->
 <div id="payment_dialog" class="easyui-dialog" buttons="#payment_dialogDiv" style="width: 650px; height: 320px; padding: 10px;" closed="true">
   <form id="paymentForm" name="paymentForm" action="${basePath}aomFinanceController/savePaymentInfo.action" method="post">
     <table style="width: 100%; height: 50px;">
     	<input type="hidden" name="pid" id="pid">
     	<input type="hidden" name="orderId" id="orderId">
     	<tr>
     		<td class="label_right"><font color="red">*</font>回款状态：</td>
     		<td colsapn="2">
				<select class="select_style easyui-combobox" width="129px;"  editable="false" data-options="validType:'selrequired'" required="true" name="status">
					  <option value=-1 selected="selected">--请选择--</option>
		              <option value=2>审核中</option>
		              <option value=3>已回款</option>
				</select>
			</td>
     	</tr>
       <tr>
       	<td class="label_right"><font color="red">*</font>实际到账金额：</td>
		<td>
			<input  class="easyui-numberbox" name="money" id="money" required="true" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" style="width:150px;"/>
		</td>
		<td class="label_right">实际到账日期：</td>
		<td>
			<input class="easyui-datebox" name="accountDate" id="accountDate" editable="false" required="true" style="width:150px;"  />
		</td>
		</tr>
		
		<tr>
       	<td class="label_right">开户行：</td>
		<td>
			<input class="easyui-textbox" id="bankName" readonly="true" style="width:150px;"/>
		</td>
		<td class="label_right">账户名：</td>
		<td>
			<input class="easyui-textbox" id="accountName" readonly="true" style="width:150px;"  />
		</td>
		</tr>
		<tr>
			<td class="label_right">银行卡号：</td>
			<td>
				<input class="easyui-textbox" id="bankCardNo" readonly="true" style="width:150px;"  />
			</td>
			<td class="label_right">回款凭证：</td>
			<td>
				<a href='#' id="fileUrl" style="color:blue;float: left;width: 145px;padding-left: 5px; ">${cashFlowApplyInfo.attachmentName}</a>
			</td>
		</tr>
		<tr>
        <td class="label_right">备注:</td>
        <td><textarea rows="2" id="auditDesc" name="auditDesc" maxlength="500" style="height: 100px;width: 250px;"></textarea></td>
       </tr>
     </table>
    </form>
   </div>
   <div id="payment_dialogDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="updatePaymentInfo()">提交</a> 
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#payment_dialog').dialog('close')">取消</a>
   </div>
<!-- 回款确认结束 -->
</div>
</div>
</body>
</html>