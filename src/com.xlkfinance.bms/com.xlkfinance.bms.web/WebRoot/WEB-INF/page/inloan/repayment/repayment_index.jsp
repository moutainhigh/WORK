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
<style type="text/css">
td {
	padding: 5px;
}
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript">
	//重置按钮
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset')
	}

	function formatterRepaymentStatus(val, row) {
		if (val == 1) {
			return "未回款";
		} else if (val == 2) {
			return "已回款";
		} else {
			return "未知";
		}
	}
	function formatterOverdueFeePaymentWay(val, row) {
		if (val == 1) {
			return "从尾款扣";
		} else if (val == 2) {
			return "转账收费";
		} else {
			return "-";
		}
	}
	var repaymentList = {
		formatOperate : function(value, row, index) {
			var va = ""
		    if(row.repaymentStatus==2&&row.overdueFeeConfirm==1){
			   va = "<a href='javascript:void(0)' onclick = 'openOverdueFeeDialog()'> <font color='blue'>逾期费确认</font></a>"
		    }
			return va;
		},
		// 项目名称format
		formatProjectName:function(value, row, index){
			var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
			return va;
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
	//打开回款窗口
	function openRepaymentDialog() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var projectId = row[0].projectId;
			var overdueFeeMoney = row[0].overdueFee;
			$("#repaymentProjectId").val(projectId);
			$("#overdueFeeProjectId").val(projectId);
			$("#repaymentDate").datebox('setValue', formatterDate(new Date()));
			$("#repaymentMoney").numberbox('setValue','');
			$('input:radio[name=paymentWay]')[0].checked = true;
			$('input:radio[name=paymentWay]')[1].checked = false;
			if(row[0].projectSource==1){
			     setCombobox3("repaymentAccountNo","WT_REPAYMENT_BANK",'--请选择--');
			}else{
			     setCombobox3("repaymentAccountNo","COLLECT_FEE_BANK",'--请选择--');
			}
			$.ajax({
				url : "getRepaymentInfo.action",
				cache : true,
				type : "POST",
				data : {'projectId' : projectId},
				async : false,
				success : function(data, status) {
					if (data == null || data == "") {
						$('#repayment_dialog').dialog('open').dialog('setTitle', "回款--"+row[0].projectName);
						return;
					}
					var result = eval("(" + data + ")");
					//初始化回款记录
					var repaymentRecordList=result['repaymentRecordList'];
					var repaymentMoneyTotal=0;
					for (var i = 0; i < repaymentRecordList.length; i++) {
						var repaymentMoney=repaymentRecordList[i].repaymentMoney;
						var repaymentDate=repaymentRecordList[i].repaymentDate;
						var accountNo=repaymentRecordList[i].accountNo;
						repaymentMoneyTotal=repaymentMoney+repaymentMoneyTotal;
						var htmlStr="<div name='repaymentRecordList'><span>  回款金额:"+repaymentMoney+"（元） 回款日期:"+repaymentDate+"  账号:"+accountNo+"</span><div>";
						$("#repayment_dialog").append(htmlStr);
					}
					$("#repayment_dialog").append("<div name='repaymentRecordList'>总计："+repaymentMoneyTotal+"（元）</div>");
					//初始化逾期费数据
					var overdueFee=result['overdueFee'];
					var accountNo="";
					if(overdueFee!=null&&overdueFee!=""){
					    var paymentWay=overdueFee.paymentWay;
					    accountNo=overdueFee.accountNo;
					    if(paymentWay==1){
					    	$('input:radio[name=paymentWay]')[0].checked = true;
					       $("input[type=radio][name=paymentWay][value=2]").removeAttr("checked");  
					    }else{
					       $("input[type=radio][name=paymentWay][value=1]").removeAttr("checked");  
					       $('input:radio[name=paymentWay]')[1].checked = true;
					    }
					}
					$('#repayment_dialog').dialog('open').dialog('setTitle',"回款--"+row[0].projectName);
				}
			});
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//打开逾期窗口
	function openOverdueFeeDialog() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var projectId = row[0].projectId;
			var overdueFeeMoney = row[0].overdueFee;
			$("#overdueFeeProjectId").val(projectId);
			$("#overdueFee").numberbox('setValue',overdueFeeMoney);
			$('input:radio[name=paymentWay]')[0].checked = true;
			$('input:radio[name=paymentWay]')[1].checked = false;
			$.ajax({
				url : "getOverdueFeeInfo.action",
				cache : true,
				type : "POST",
				data : {'projectId' : projectId},
				async : false,
				success : function(data, status) {
					if (data == null || data == "") {
						$('#overdueFee_dialog').dialog('open').dialog('setTitle', "逾期费--"+row[0].projectName);
						return;
					}
					var result = eval("(" + data + ")");
					var overdueFee=result['overdueFee'];
					var accountNo="";
					if(overdueFee!=null&&overdueFee!=""){
					    var paymentWay=overdueFee.paymentWay;
					    accountNo=overdueFee.accountNo;
					    if(paymentWay==1){
					    	$("input[type=radio][name=paymentWay][value=1]").prop('checked',true)
					    	$("input[type=radio][name=paymentWay][value=2]").removeAttr("checked");  
					    }else if((paymentWay==2)){
					       $("input[type=radio][name=paymentWay][value=1]").removeAttr("checked");  
					       $("input[type=radio][name=paymentWay][value=2]").prop('checked',true)
					    }
					}
					//初始化逾期转账账号
					//有保存过，则选择保存的值
					if(row[0].projectSource==1){
		            if(accountNo==null||accountNo==""){
	                     //还没有保存过，默认第一个
	                     setCombobox3("accountNo","WT_COLLECT_FEE_BANK",'--请选择--');
	                 }else{
		                 setCombobox3("accountNo","WT_COLLECT_FEE_BANK",accountNo);
	                 }
					}else{
			            if(accountNo==null||accountNo==""){
		                     //还没有保存过，默认第一个
		                     setCombobox3("accountNo","COLLECT_FEE_BANK",'--请选择--');
		                 }else{
			                 setCombobox3("accountNo","COLLECT_FEE_BANK",accountNo);
		                 }
					}
					$('#overdueFee_dialog').dialog('open').dialog('setTitle',"逾期费--"+row[0].projectName);
				}
			});
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//提交回款
	function subRepaymentForm() {
/* 		var repaymentDate=$("#repaymentDate").datebox('getValue');
		if (repaymentDate<formatterDate(new Date())) {
			$.messager.alert('操作提示', '回款日期不可再实际回款日期前', 'error');
			return false;
		} */
		// 提交表单
		$("#repaymentForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', '回款保存成功', 'info');
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
					$('#repayment_dialog').dialog('close');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	//提交逾期费
	function subOverdueFeeForm() {
		var row = $('#grid').datagrid('getSelections');
		var overdueDay=row[0].overdueDay;
		$("#overdueFeeOverdueDay").val(overdueDay);
		// 提交表单
		$("#overdueFeeForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', '逾期费保存成功', 'info');
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
					$("#overdueFeeOverdueDay").val(0);
					$('#overdueFee_dialog').dialog('close');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	 //逾期费转账方式事件
 	function changePaymentWay(value){  
	    	   //查档选项无效等，默认审批结果为不通过
	         if (value==1) {
	        	 $("#accountNo").combobox('disable');
	        	 $("#accountNo").combobox('select','--请选择--');
			 }else{
				 $("#accountNo").combobox('enable');
			 }
	};  
	$(document).ready(function() {
	//回款窗口关闭时，清空回款记录
 	  $('#repayment_dialog').dialog({
 	    onClose:function(){
 	    	$("div[name='repaymentRecordList']").remove();
 	    }
 	  });
	  setTimeout(function(){
			<%if(request.getAttribute("projectName")!=null){%>
			//此处，如果是从任务列表跳转过来，则projectName是有值的，此时定位该任务的项目数据
			$("#searchProjectName").textbox("setValue",'${projectName}');
			ajaxSearchPage('#grid','#searchFrom');
			<%}%>
      },500);
	});
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>repaymentController/repaymentIndexList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td width="80" align="right" height="28">项目编号：</td>
        <td><input class="easyui-textbox" name="projectNumber" id="projectNumber" /></td>
        <td width="80" align="right" height="28">项目名称：</td>
        <td colsapn="2"><input class="easyui-textbox" name="projectName" id="searchProjectName" /></td>
       </tr>
       <tr>
        <td align="right" height="28">物业名称：</td>
        <td><input class="easyui-textbox" name="houseName"/></td>
        <td align="right" height="28">买方姓名：</td>
        <td><input class="easyui-textbox" name="buyerName"/></td>
        <td align="right" height="28">卖方姓名：</td>
        <td><input class="easyui-textbox" name="sellerName"/></td>
       </tr>
       <tr>
        <td align="right" height="28">回款状态：</td>
        <td>
        <select class="easyui-combobox" name="repaymentStatus" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未回款</option>
          <option value=2>已回款</option>
        </select>
        </td>
        <td align="right" height="28">逾期费确认状态：</td>
        <td>
        <select class="easyui-combobox" name="overdueFeeConfirm" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未确认</option>
          <option value=2>已确认</option>
        </select>
        </td>
        <td align="right" height="28">逾期费付款方式：</td>
        <td>
        <select class="easyui-combobox" name="overdueFeePaymentWay" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>从尾款扣</option>
          <option value=2>转账收费</option>
        </select>
        </td>
       </tr>
       <tr>
        <td colspan="3"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>
      </table>
     </div>
    </form>

    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openRepaymentDialog()">回款</a>
     <shiro:hasAnyRoles name="ARCHIVED,ALL_BUSINESS">
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="cancelGuarantee()">归档（解保）</a>
     </shiro:hasAnyRoles>
    </div>

   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="回款列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>repaymentController/repaymentIndexList.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'projectId',
		    fitColumns:true"
    >
     <thead data-options="frozen:true">
		<tr>
			<th data-options="field:'projectId',checkbox:true"></th>
        	<th data-options="field:'cz',formatter:repaymentList.formatOperate" align="center" halign="center">操作</th>
        	<th data-options="field:'projectName',formatter:repaymentList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <!-- 表头 -->
     <thead>
      <tr>
       <!-- <th data-options="field:'projectId',checkbox:true"></th>
       <th data-options="field:'cz',formatter:repaymentList.formatOperate" align="center" halign="center">操作</th>
       <th data-options="field:'projectName',formatter:repaymentList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th> -->
       <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th>
       <th data-options="field:'houseName',sortable:true" align="center" halign="center">物业名称</th>
       <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方姓名</th>
       <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方姓名</th>   
       <th data-options="field:'realRepaymentMoney',sortable:true,formatter:formatMoney" align="center" halign="center">实际回款金额</th>
       <th data-options="field:'realRepaymentDate',sortable:true,formatter:convertDate" align="center" halign="center">实际回款时间</th>
       <th data-options="field:'planRepaymentMoney',sortable:true,formatter:formatMoney" align="center" halign="center">预计回款金额</th>
       <th data-options="field:'planRepaymentDate',sortable:true,formatter:convertDate" align="center" halign="center">预计回款时间</th>
       <th data-options="field:'repaymentStatus',sortable:true,formatter:formatterRepaymentStatus" align="center" halign="center">回款状态</th>
       <th data-options="field:'overdueFee',sortable:true,formatter:formatMoney" align="center" halign="center">逾期费</th>
       <th data-options="field:'overdueDay',sortable:true" align="center" halign="center">逾期天数</th>
       <th data-options="field:'overdueFeeConfirm',sortable:true,formatter:formatterOverdueFeeConfirm" align="center" halign="center">逾期费确认</th>
       <th data-options="field:'overdueFeePaymentWay',sortable:true,formatter:formatterOverdueFeePaymentWay" align="center" halign="center">逾期费付款方式</th>
       <th data-options="field:'overdueFeeAccountNo',sortable:true" align="center" halign="center">逾期费转账账号</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="repayment_dialog" class="easyui-dialog" data-options="modal:true" buttons="#subRepaymentDiv" style="width: 430px; height: 410px; padding: 10px;" closed="true">
    <form id="repaymentForm" name="repaymentForm" action="${basePath}repaymentController/saveRepayment.action" method="post">
     <table style="width: 90%; height: 30px;">
      <tr>
       <td class="label_right1">回款金额：</td>
       <td><input name="repaymentMoney" id="repaymentMoney" class="easyui-numberbox" style="width:247px;"
        data-options="min:0,max:999999999,precision:2,groupSeparator:','"
       ></td>
      </tr>
      <tr>
       <td class="label_right1">回款日期：</td>
       <td><input name="repaymentDate" id="repaymentDate" class="easyui-datebox" data-options="required:true" editable="false" style="width:247px;"></td>
      </tr>
      <tr>
       <td class="label_right1">回款账号：</td>
       <td>
       <input name="accountNo" id="repaymentAccountNo" editable="false" data-options="validType:'selrequired'" class="easyui-combobox" panelHeight="auto" required="true" style="width: 250px;"/>
       </td>
      </tr>
     </table>
     <input type="hidden" name="pid" id="repaymentId"> <input type="hidden" name="projectId" id="repaymentProjectId">
    </form>
    <div style="padding-top: 10px; margin-top:10px; border-top: 1px #ddd dashed;">
   	<b>回款历史：</b><br/>
   	
    </div>
   </div>
   <div id="subRepaymentDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subRepaymentForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#repayment_dialog').dialog('close')"
    >取消</a>
   </div>
   <div id="overdueFee_dialog" class="easyui-dialog" data-options="modal:true" buttons="#subOverdueFeeDiv" style="width: 430px; height: 210px; padding: 10px;" closed="true">
    <form id="overdueFeeForm" name="overdueFeeForm" action="${basePath}repaymentController/saveOverdueFee.action" method="post">
     <table style="width: 90%; height: 100px;">
      <tr>
       <td class="label_right1">逾期费：</td>
       <td><input name="overdueFee" id="overdueFee" class="easyui-numberbox"
        data-options="min:0,max:999999999,precision:2,groupSeparator:','"  style="width:247px;"
       ></td>
      </tr>
      <tr>
       <td class="label_right1">&nbsp;</td>
       <td><input type="radio" name="paymentWay" id="paymentWay" onchange="changePaymentWay(this.value)" checked="checked" value="1">从尾款扣 <input type="radio" name="paymentWay" onchange="changePaymentWay(this.value)" value="2">转账收费</td>
      </tr>
      <tr>
       <td class="label_right1">收费账号：</td>
       <td>
       <input name="accountNo" id="accountNo" data-options="validType:'selrequired'" class="easyui-combobox" disabled="disabled" panelHeight="auto" required="true" style="width: 250px;"/>
       </td>
      </tr>
     </table>
     <input type="hidden" name="overdueDay" id="overdueFeeOverdueDay">
     <input type="hidden" name="projectId" id="overdueFeeProjectId">
    </form>
   </div>
   <div id="subOverdueFeeDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subOverdueFeeForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#overdueFee_dialog').dialog('close')"
    >取消</a>
   </div>
  </div>
 </div>
</body>
</html>
