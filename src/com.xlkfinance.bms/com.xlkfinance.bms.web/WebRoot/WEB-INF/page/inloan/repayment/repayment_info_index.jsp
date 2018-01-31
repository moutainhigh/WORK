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
.table_label{padding:5px 0;}
.table_label b{padding:0 3px;}
</style>
<script type="text/javascript">
	//重置按钮
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset')
	}

	//打开回款页面
	function openRepaymentDialog(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 0) {
			$.messager.alert('操作提示', "请选择业务", 'error');
			return;
		}
		if (row.length == 1) {
			/* var repaymentStatus =row[0].repaymentStatus;
			if(repaymentStatus == 2){
				$.messager.alert('操作提示', "此业务已结清，请选择其他项目！", 'error');
				return;
			} */
			
	 		$("#projectId").val(row[0].projectId);
	 		initDialog(row[0].projectId);
	 		$('#repayment_dialog').dialog('open').dialog('setTitle',"还款收取-"+row[0].projectName);
	 		
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选中一条数据进行操作,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
		
	}
	
	function initDialog(projectId){
		var url = "<%=basePath%>projectInfoController/getRepaymentByPage.action?projectId="+projectId;
 		$('#grid2').datagrid('options').url = url;
 		$('#grid2').datagrid('reload');
 		$(".one").hide();
 		
 		 $.ajax({
			url : "${basePath}repaymentController/getRepaymentDetailInfo.action",
			cache : true,
			type : "POST",
			data : {'projectId' :projectId},
			async : false,
			success : function(data, status) {
				var result = eval("("+data+")");
				var overdueCycleNum = result.overdueCycleNum;
				if(overdueCycleNum == 0){
					overdueCycleNum = result.planCycleNum;
				}

				$('#principal').html(result.principalBalance);
				$('#overdueCycleNum').html(overdueCycleNum);
				$('#overdueDays').html(result.overdueDays);
				$('#overdueDay').html(result.overdueDays);
				
				$('#overdueRate').html(result.overdueRate);
				$('#shouldPenaltySpan').html(result.shouldPenalty);
				$('#actualPenaltyLabel').html(result.actualPenalty);
				var penalty = result.shouldPenalty - result.actualPenalty;
				$('#shouldPenaltyLabel').html(penalty.toFixed(2));
				$("#currentDate").datebox('setValue',formatterDate(new Date()));
				
				$("#planCycleNum").numberbox('setValue',result.planCycleNum);
				$("#shouldPenalty").numberbox('setValue',result.shouldPenaltyTotal);
				$("#shouldOverdueMoney").numberbox('setValue',result.shouldOverdueMoney);
				$("#shouldInterest").numberbox('setValue',result.shouldInterest);
				$("#shouldPrincipal").numberbox('setValue',result.shouldPrincipal);
				$("#shouldPrepaymentFee").val(result.shouldPrepaymentFee);
				$("#shouldInterestTotal").val(result.shouldInterest);
				//当前登录人
				$("#createrUser").textbox('setValue',result.createrUser);
				
				calculation();
				var shouldTotal = $("#shouldTotal").numberbox('getValue');
				if(shouldTotal == 0){
					$("#subAllocationDiv").hide();
				}else{
					$("#subAllocationDiv").show();
				}
				
				//$('#repayment_dialog').dialog('open').dialog('setTitle',"还款收取-"+row[0].projectName);
				}
			}); 
	}
	
	$(document).ready(function(){
		//$("#repaymentType").combobox("setValue","-1");
		$("#repaymentType").combobox({
	        onSelect:function(){
	        	var repaymentType = $("#repaymentType").combobox("getValue");
	        	var shouldPrepaymentFee = $("#shouldPrepaymentFee").val();
				var shouldInterest = $("#shouldInterestTotal").val();
	    		//默认正常还款
	        	if(repaymentType == "--请选择--" || repaymentType == 1){
	    			$(".one").hide();
	    			$(".two").show();
	    			$("#shouldInterest").numberbox('setValue',shouldInterest);
	    		}else{
	    			var flag = true;
	    			//还款计划明细表
	    			var rows = $('#grid2').datagrid('getRows');
	    			for(var i=0;i<rows.length;i++){
	    				var plan = rows[i];
	    				var thisStatus = plan.thisStatus;
	    				var total = plan.total;
	    				var actualTotal = plan.actualTotal;
	    				var actualOverdueMoney = plan.actualOverdueMoney;
	    				//正常未还且实际还款金额大于0，如果存在某一期部分正常还款，不允许进行提前还款操作
	    				if(thisStatus == 1 && actualTotal >0){
	    					flag = false;
	    				}
	    				//逾期未还且实际已还逾期金额大于0，如果存某一期部分逾期未还，不允许进行提前还款
	    				if(thisStatus == 3 && actualOverdueMoney>0){
	    					flag = false;
	    				}
	    			}
	    			//最后一期信息，该项目已逾期，不可进行提前还款
	    			var rowValue = rows[rows.length-1];
	    			if(rowValue.thisStatus == 3){
	    				$.messager.alert("操作提示","该项目已逾期，不可进行提前还款！","error");
	    				$(".one").hide();
		    			$(".two").show();
		    			$("#repaymentType").combobox("setValue",'--请选择--');
		    			$("#shouldInterest").numberbox('setValue',shouldInterest);
	    			}else if(flag){
	    				$(".one").show();
		    			$(".two").hide();
		    			$("#shouldInterest").numberbox('setValue',shouldPrepaymentFee);
	    			}else{
	    				$.messager.alert("操作提示","此项目最近未还的一期存在部分还款情况，请结清该期后，再进行提前还款！","error");
	    				$(".one").hide();
		    			$(".two").show();
		    			$("#repaymentType").combobox("setValue",'--请选择--');
		    			$("#shouldInterest").numberbox('setValue',shouldInterest);
	    			}
	    		}
	        	calculation();
	        	changeRepaymentMoney();
	        	}
	        });
		
		setCombobox3("accountNo","COLLECT_FEE_BANK",'--请选择--');
		
		$('#repaymentMoney').numberbox({
			onChange: function(value){
				changeRepaymentMoney();
			  }
			});
		
		//撤单窗口关闭时，清空表单数据
	 	$('#repayment_dialog').dialog({
	 	    onClose:function(){
				//清空表单数据
	 	    	$('#repaymentForm').form('reset');
	 	    }
	 	});
	});
	//计算应收合计金额
	function calculation(){
		var shouldPenaltyTotal = $("#shouldPenalty").numberbox('getValue');
		var shouldOverdueMoney = $("#shouldOverdueMoney").numberbox('getValue');
		var shouldInterest = $("#shouldInterest").numberbox('getValue');
		var shouldPrincipal = $("#shouldPrincipal").numberbox('getValue');
		var result = parseFloat(shouldPenaltyTotal) + parseFloat(shouldOverdueMoney)+ parseFloat(shouldInterest) + parseFloat(shouldPrincipal);
		$("#shouldTotal").numberbox('setValue',result);
		
	}
	//实收合计金额变更方法
	function changeRepaymentMoney(){
		 var repaymentMoney = $('#repaymentMoney').numberbox('getValue');
		 /*var shouldTotal = $("#shouldTotal").numberbox('getValue');
		if(repaymentMoney <=0){
			$.messager.alert('操作提示', "本次收款合计必须大于0！", 'error');
			return;
		}
		if(repaymentMoney - shouldTotal>0){
			$.messager.alert('操作提示', "本次收款合计不能大于当前应还合计！", 'error');
			return;
		} */
		
		var shouldPenaltyTotal = $("#shouldPenalty").numberbox('getValue');
		var shouldOverdueMoney = $("#shouldOverdueMoney").numberbox('getValue');
		var shouldInterest = $("#shouldInterest").numberbox('getValue');
		var shouldPrincipal = $("#shouldPrincipal").numberbox('getValue');
		
		var resultOne = repaymentMoney - shouldPenaltyTotal;
		var resultTwo = resultOne - shouldOverdueMoney;
		var resultThree = resultTwo - shouldInterest;
		var resultFour = resultThree - shouldPrincipal;
		
		//当实收金额小于等于罚息时，只能冲抵罚息，其余为0
		if(resultOne <=0){
			$("#actualPenalty").numberbox('setValue',repaymentMoney);
			$("#actualOverdueMoney").numberbox('setValue',0.00);
			$("#actualInterest").numberbox('setValue',0.00);
			$("#actualPrincipal").numberbox('setValue',0.00);
		//当实收金额小于等于罚息+逾期金额时，只能冲抵罚息+逾期金额，其余为0，逾期金额=实收减去罚息
		}else if(resultOne >0 && resultTwo <=0){
			$("#actualPenalty").numberbox('setValue',shouldPenaltyTotal);
			$("#actualOverdueMoney").numberbox('setValue',resultOne);
			$("#actualInterest").numberbox('setValue',0.00);
			$("#actualPrincipal").numberbox('setValue',0.00);
		//当实收金额小于等于罚息+逾期金额+(利息或者提前还款费)时，只能冲抵罚息+逾期金额+(利息或者提前还款费)，其余为0，利息或者提前还款费=实收减去（罚息+逾期金额）	
		}else if(resultOne >0 && resultTwo >0 && resultThree<=0){
			$("#actualPenalty").numberbox('setValue',shouldPenaltyTotal);
			$("#actualOverdueMoney").numberbox('setValue',shouldOverdueMoney);
			$("#actualInterest").numberbox('setValue',resultTwo);
			$("#actualPrincipal").numberbox('setValue',0.00);
		//当实收金额大于罚息+逾期金额+(利息或者提前还款费)时，冲抵罚息+逾期金额+(利息或者提前还款费)+应还本金	
		}else{
			$("#actualPenalty").numberbox('setValue',shouldPenaltyTotal);
			$("#actualOverdueMoney").numberbox('setValue',shouldOverdueMoney);
			$("#actualInterest").numberbox('setValue',shouldInterest);
			$("#actualPrincipal").numberbox('setValue',resultThree);
		}
		
	}
	//提交回款
	function saveRepayment() {
		
		var repaymentType = $("#repaymentType").combobox("getValue");
		if(repaymentType == '--请选择--'){
			$.messager.alert('操作提示', "还款类型必选！", 'error');
			return;
		}
		var repaymentMoney = $('#repaymentMoney').numberbox('getValue');
		var shouldTotal = $("#shouldTotal").numberbox('getValue');
		
		if(repaymentMoney <=0){
			$.messager.alert('操作提示', "本次收款合计必须大于0！", 'error');
			return;
		}
		if(repaymentMoney - shouldTotal>0){
			$.messager.alert('操作提示', "本次收款合计不能大于当前应还合计！", 'error');
			return;
		}
		
		if(repaymentType == 2){
			if(repaymentMoney - shouldTotal<0){
				$.messager.alert('操作提示', "提前还款时，本次收款合计不能小于当前应还合计！", 'error');
				return;
			}
		}
		
		// 提交表单
		$("#repaymentForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', '还款保存成功', 'info');
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
					$('#repaymentForm').form('reset');
					//$('#repayment_dialog').dialog('close');
					var projectId = $("#projectId").val();
					initDialog(projectId);
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	var foreInformationList = {
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>";
				return va;
			},
			// 还款时间format
			formatPlanRepayDt:function(value, row, index){
				var projectId = $("#projectId").val();
				var va="<a href='javascript:void(0)' onclick = 'openRecordDialog("+projectId+","+row.planCycleNum+")'> <font color='blue'>"+row.planRepayDt+"</font></a>";
				return va;
			}
		}
	//打开期数还款记录
	function openRecordDialog(projectId,planCycleNum){
		var url = "<%=basePath%>projectInfoController/getRepaymentRecordByPage.action?projectId="+projectId+"&planCycleNum="+planCycleNum;
 		$('#repayment_record').datagrid('options').url = url;
 		$('#repayment_record').datagrid('reload');
 		
 		$('#repayment_record_dialog').dialog('open').dialog('setTitle',"还款收取明细记录");
	}
	function formatType(val,row,index){
		if (val == 1) {
			return "实收本金";
		} else if (val == 2) {
			return "实收利息";
		} else if (val == 3) {
			return "实收逾期金额";
		} else if (val == 4) {
			return "实收罚息";
		} else if (val == 5) {
			return "实收剩余本金";
		} else if (val == 6) {
			return "实收提前还款费";
		}
	}
</script>
<title>还款管理</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>repaymentController/repaymentInfoList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td width="80" align="right" height="28">项目编号：</td>
        <td><input class="easyui-textbox" name="projectNumber" id="projectNumber" /></td>
        <td width="80" align="right" height="28">项目名称：</td>
        <td colsapn="2"><input class="easyui-textbox" name="projectName" id="searchProjectName" /></td>
        <td align="right" height="28">借款人：</td>
        <td><input class="easyui-textbox" name="acctName"/></td>
       </tr>
       <tr>
        <td align="right" height="28">还款状态：</td>
        <td>
        <select class="easyui-combobox" name="repaymentStatus" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>正常还款中</option>
          <option value=3>逾期还款中</option>
          <option value=4>已逾期</option>
          <option value=2>已结清</option>
        </select>
        </td>
        <td width="100" align="right" >放款时间：</td>
		<td colsapn="2">
			<input name="receDateStart" id="receDateStart" class="easyui-datebox" editable="false"/> <span>~</span> 
			<input name="receDateEnd" id="receDateEnd" class="easyui-datebox" editable="false"/>
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
    </div>

   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="还款列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>repaymentController/repaymentInfoList.action',
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
        	<th data-options="field:'projectName',formatter:foreInformationList.formatProjectName" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <thead>
      <tr>
       <!-- <th data-options="field:'projectId',checkbox:true"></th>
       <th data-options="field:'projectName'" align="center" halign="center">项目名称</th> -->
       <th data-options="field:'projectNumber'" align="center" halign="center">项目编号</th>
       <th data-options="field:'acctName'" align="center" halign="center">借款人</th>
       <th data-options="field:'applyLoanMoney',formatter:formatMoney" align="center" halign="center">借款金额</th>
       <th data-options="field:'planRepaymentMoney',formatter:formatMoney" align="center" halign="center">放款金额</th>
       <th data-options="field:'repaymentStatus',formatter:formatterRepaymentStatus" align="center" halign="center">还款状态</th>
       <th data-options="field:'receDate'" align="center" halign="center">放款时间</th>
      </tr>
     </thead>
    </table>
   </div>
   <!-- 还款收取 -->
   <div id="repayment_dialog" class="easyui-dialog" buttons="#subAllocationDiv" style="width: 980px; height: 500px; padding: 10px;"closed="true">
    <table id="grid2" title="财务明细" class="easyui-datagrid" style="height: 50%; width: 98%;"
     data-options="
      url: '',
      method: 'POST',
      rownumbers: true,
      singleSelect: false,
      pagination: false,
      sortOrder:'asc',
      remoteSort:false,
      idField: 'pid',
      fitColumns:true">
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'planRepayDt',formatter:foreInformationList.formatPlanRepayDt" align="center" halign="center">应还日期</th>
       <th data-options="field:'planCycleNum'" align="center" halign="center">期数</th>
       <th data-options="field:'thisStatus',formatter:formatThisStatus" align="center" halign="center">账单状态</th>
       <th data-options="field:'shouldPrincipal',formatter:formatMoney" align="center" halign="center">应还本金</th>
       <th data-options="field:'shouldInterest',formatter:formatMoney" align="center" halign="center">应还利息</th>
       <th data-options="field:'productInterest',formatter:formatMoney" align="center" halign="center">产品利息</th>
       <th data-options="field:'rebateFee',formatter:formatMoney" align="center" halign="center">返佣利息</th>
       <th data-options="field:'total',formatter:formatMoney" align="center" halign="center">应还合计</th>
       <th data-options="field:'actualPrincipal',formatter:formatMoney" align="center" halign="center">已收本金</th>
       <th data-options="field:'actualInterest',formatter:formatMoney" align="center" halign="center">已收利息</th>
       <th data-options="field:'actualTotal',formatter:formatMoney" align="center" halign="center">已收合计</th>
       <th data-options="field:'actualRepayDt'" align="center" halign="center">本息收取日</th>
       <th data-options="field:'overdueDays'" align="center" halign="center">逾期天数</th>
       <th data-options="field:'overdueMoney',formatter:formatMoney" align="center" halign="center">本期逾期金额</th>
       <th data-options="field:'actualOverdueMoney',formatter:formatMoney" align="center" halign="center">已收逾期金额</th>
       <!-- <th data-options="field:'actualPenalty',formatter:formatMoney" align="center" halign="center">已收罚息</th> -->
       <th data-options="field:'actualOverdueDt',formatter:formatOverdueDt" align="center" halign="center">逾期收取日</th>
       <th data-options="field:'principalBalance',formatter:formatMoney" align="center" halign="center">应还剩余本金</th>
       <th data-options="field:'shouldPrepaymentFee',formatter:formatMoney" align="center" halign="center">应收提前还款费</th>
       <th data-options="field:'preRepayAmt',formatter:formatMoney" align="center" halign="center">已收剩余本金</th>
       <th data-options="field:'fine',formatter:formatMoney" align="center" halign="center">已收提前还款费</th>
       <th data-options="field:'repayDate'" align="center" halign="center">提前还款日</th>
      </tr>
     </thead>
    </table>
    	<div>
    		<div class="table_label">
    			罚息提示：从第<b id="overdueCycleNum" style='color:#FF9900'></b>期开始已逾期<b id="overdueDays" style='color:red'></b>天，应还罚息=<b id="overdueDay"></b>（天）*<b id="principal"></b>（放款金额）*<b id="overdueRate"></b>%（逾期日罚息率）=<b id="shouldPenaltySpan"></b>元；已收罚息<b id="actualPenaltyLabel"></b>元；需还罚息<b id="shouldPenaltyLabel"  style='color:red'></b>元。
    		</div>
    		<form id="repaymentForm" name="repaymentForm" action="${basePath}repaymentController/saveRepaymentDetailInfo.action" method="post">
    		<input name="projectId" type="hidden" id="projectId">
    		<table style="width: 100%; height: 100px;">
			  <tr>
			  	<td class="label_right1"><font color="red">*</font>还款模式：</td>
				<td>
					<select class="easyui-combobox select_style" name="repaymentType"  id="repaymentType" style="width: 124px" panelHeight="auto" editable="false">
			          <option value="--请选择--"  selected="selected">--请选择--</option>
			          <option value="1">正常还款收取</option>
			          <option value="2">提前还款</option>
			        </select>
				</td>
			  </tr>
			  <tr>
				<td class="label_right1">当前日期：</td>
				<td>
					<input class="easyui-datebox" id="currentDate" disabled="disabled" editable="false"/>
				</td>
				<td class="label_right1">当期期数：</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" name="planCycleNum" id="planCycleNum" style="width:129px;"/>
				</td>
			  </tr>
			  <tr>
			    <td class="label_right1">应还罚息金额：</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" id="shouldPenalty" data-options="precision:2,groupSeparator:','" style="width:129px;"/>
				</td>
				<td class="label_right1">应还逾期金额：</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" id="shouldOverdueMoney" data-options="precision:2,groupSeparator:','" style="width:129px;"/>
				</td>
			  
			  </tr>
			  <tr>
				<td class="label_right1">
					<div class="one">应还提前还款费：</div>
					<div class="two">剩余应还利息：</div>
				</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" id="shouldInterest" data-options="precision:2,groupSeparator:','" style="width:129px;"/>
					<input type="hidden" id="shouldPrepaymentFee">
					<input type="hidden" id="shouldInterestTotal">
				</td>
				<td class="label_right1" >剩余应还本金：</div>
				</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" id="shouldPrincipal" data-options="precision:2,groupSeparator:','" style="width:129px;"/>
				</td>
			  </tr>
			   <tr>
				<td class="label_right1">当前应还合计：</td>
				<td>
					<input class="easyui-numberbox" name="total" id="shouldTotal" readonly="readonly" data-options="precision:2,groupSeparator:','" style="width:125px;"/>
				</td>
			  </tr>
			  <tr>
				<td class="label_right1"><font color="red">*</font>本次收款合计：</td>
				<td>
					<input class="easyui-numberbox" name="repaymentMoney" id="repaymentMoney" style="width:125px;" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>
				</td>
			  </tr>
			  <tr>
				<td class="label_right1">实收罚息金额：</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" name="actualPenalty" id="actualPenalty" data-options="precision:2,groupSeparator:','" style="width:129px;"/>
				</td>
				<td class="label_right1">实收逾期金额：</td>
				<td>
					<input class="easyui-numberbox" readonly="readonly" name="actualOverdueMoney" id="actualOverdueMoney" data-options="precision:2,groupSeparator:','" style="width:129px;"/>
				</td>
			  </tr>
			  <tr>
			  	<td class="label_right1">
			  	
			  		<div class="one">实收提前还款费：</div>
					<div class="two">实收利息：</div>
			  	</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" name="actualInterest" id="actualInterest" data-options="precision:2,groupSeparator:','" style="width:129px;"/>
				</td>
				<td class="label_right1">实收剩余本金：</div>
				</td>
				<td>
					<input readonly="readonly" class="easyui-numberbox" name="actualPrincipal" id="actualPrincipal" data-options="precision:2,groupSeparator:','" style="width:129px;"/>
				</td>
			  </tr>
			  <!-- <tr>
				<td class="label_right1">收款情况备注：</td>
				<td colspan="3">
					<input readonly="readonly" class="easyui-textbox" name="remark" id="remark" data-options="" style="width:500px;"/>
				</td>
			  </tr> -->
			  <tr>
				<td class="label_right1"><font color="red">*</font>收款账号：</td>
				<td>
					<input name="accountNo" id="accountNo" data-options="validType:'selrequired'" class="easyui-combobox" panelHeight="auto" required="true" style="width: 250px;"/>
				</td>
				<td class="label_right1"><font color="red">*</font>收款日期：</td>
				<td>
					<input editable="false" required="required" class="easyui-datebox" name="repaymentDate" id="repaymentDate" style="width:129px;"/>
				</td>
			  </tr>
			  <tr>
				<td class="label_right1"><font color="red">*</font>操作人：</td>
				<td>
					<input readonly="readonly" class="easyui-textbox" id="createrUser" style="width:129px;"/>
				</td>
			  </tr>
     		</table>
    	</form>
    	</div>
   </div>
   <div id="subAllocationDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRepayment()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#repayment_dialog').dialog('close')"
    >取消</a>
   </div>
   <!-- 还款收取结束 -->
   <!-- 资方选择记录展示 -->
	<div id="repayment_record_dialog" class="easyui-dialog" fitColumns="true"  title="还款收取记录"
				style="width:666px;height: 300px;" data-options="modal:true" buttons="#" closed="true" >
		<table id="repayment_record"  title="还款明细表" class="easyui-datagrid" style="height: 100%; width: auto;"
	     	data-options="
	     		url:'',
			    method: 'POST',
			    rownumbers: true,
			    pagination: true,
			    sortOrder:'asc',
			    remoteSort:false,
			    idField: 'pid',
			    fitColumns:true">
		     <!-- 表头 -->
		     <thead>
		      <tr>
		       <th data-options="field:'planCycleNum'" align="center" halign="center">收款期数</th>
		       <th data-options="field:'repaymentType',formatter:formatType" align="center" halign="center">收款项目</th>
		       <th data-options="field:'repaymentMoney',formatter:formatMoney" align="center" halign="center">金额</th>
		       <th data-options="field:'repaymentDate',formatter:convertDate" align="center" halign="center">收款日期</th>
		       <th data-options="field:'accountNo'" align="center" halign="center">收款账号</th>
		       <th data-options="field:'createrName'" align="center" halign="center">操作人</th>
		       <th data-options="field:'createrDate',formatter:convertDateTime" align="center" halign="center">操作时间</th>
		      </tr>
		     </thead>
	    </table>	
	</div>
	<!-- 资方选择记录展示结束 -->
  </div>
 </div>
</body>
</html>
