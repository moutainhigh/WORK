<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<<style>
#est_template table tr td{
	border:1px solid #95B8E7;
}
</style>
 <script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/credits/credits_add_investigation.js"></script>
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/common/investigation.js"></script>
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/credits/creditsFlowPath.js"></script>
 
<script type="text/javascript">
var projectName ='';
	function saveTemplate() {
		var rows = $('#dhglyqGrid').datagrid('getData');
		if(rows.total>=1){//超过一条就确认，没有纪录就可以提交
			for(var i=0;i<rows.total;i++){
				if(rows.rows[i].isConfirm!=1){
					$.messager.alert("提示","请确认全部的贷后管理要求","error");
					return false;
				}
			}
		}
		$('#estTemplate').form('submit',{
				url : "updateBizProjectRegPlanRegulatoryStatus.action",
				onSubmit : function() {
					//checkFactorForm();
					return $(this).form('validate');
				},
				success : function(result) {
					
					var data = jQuery.parseJSON(result);
					// 保存成功
			    	if(data.header.success)
			    	{
			    		//$.messager.alert("提示","保存成功","info");
			    		 // 刷新本页面
						window.location.reload();
			    		var myObj = parent.$('#layout_center_tabs').tabs('getTab','监管任务查询');
			    		//获取iframe的URL
			    		var url = myObj[0].innerHTML;
			    		//打开数据
			    		parent.layout_center_addTabFun({
			    			title : '监管任务查询', //tab的名称
			    			closable : true, //是否显示关闭按钮
			    			content : url,//加载链接 
			    			falg:true
			    		});
			    			parent.$('#layout_center_tabs').tabs('close','执行监管');
			    	}
			    	// 保存失败
			    	else
		    		{
			    		$.messager.alert("提示",data.header.msg,"error");
		    		}
					

				},
				error : function(result) {
					alert("保存失败！");
				}
			});
	}

	function checkFactorForm() {
		var arrWeights = $("input[id^='factor_weight_']");
		var total = 0;
		for (var i = 0; i < arrWeights.length; i++) {
			var weights = parseFloat(arrWeights[i].value);
			total += weights;
		}
		if (total != 100) {
			alert("权重值必须等于100！");
			return false;
		}
	}
	function cancelTemplate() {
		window.location = '${basePath }customerController/listEstTemplate.action';
	}

	function addFactor(pid) {
		
		window.location='${basePath}afterLoanController/goCreateResultSuperviseHistory.action?regulatoryPlanId='+pid;
		//var url='${basePath}afterLoanController/goCreateResultSuperviseHistory.action?regulatoryPlanId='+pid;
		//parent.openNewTab(url,"新增监管结果录入");
	}
	function removeFactor(quotaIndex) {
		var isChecked = document.getElementsByName("isChecked");
		var flag = false;
		var num = isChecked.length;
		for (var i = num - 1; i >= 0; i--) {
			if (isChecked[i].checked) {
				$("#factor_tr" + isChecked[i].value).remove();
				flag = true;
			}
		}
		if (flag == false) {
			alert("请选择一条数据删除！");
		}
	}
 
	
	/**
	
	删除
	*/
	function deleteData(pid,projectId){
		//window.location.href = '${basePath}afterLoanController/deleteResultSuperviseHistory.action?pid='+pid+'&projectId='+projectId;
		if(confirm("是否删除该数据？")){
			$.ajax({
				url : '${basePath}afterLoanController/deleteResultSuperviseHistory.action?pid='
					+ pid
		   			+ '&projectId='
		   			+ projectId,
				type : 'post',
				cache: false,
				success : function(result) {
					//$.messager.alert("操作提示","删除成功","success"); 
					var myObj = parent.$('#layout_center_tabs').tabs('getTab','执行监管');
					if(myObj){
					//获取iframe的URL
					var url = myObj[0].innerHTML;
					//打开数据
					parent.layout_center_addTabFun({
						title : '执行监管', //tab的名称
						closable : true, //是否显示关闭按钮
						content : url,//加载链接
						falg:true
					});
					}
	 			}
			});
		}
	}
	
	//edit
	function editQuota(pid,projectId) {
		//window.location='${basePath}afterLoanController/goCreateResultSuperviseHistory.action?regulatoryPlanId='+pid;
		
		window.location='${basePath}afterLoanController/goUpdateResultSuperviseHistory.action?pid='+pid+'&projectId=${projectId}&planId=${planId}';
		//parent.openNewTab(url,"编辑监管结果录入");
	}
	
//查看收息表
function findIncome(loanId,projectId){
	if(loanId==''){
	  alert("该项目还没有贷款!");
	  return false;
	}
	parent.openNewTab("${basePath}financeController/getFinanceListLoanCalculate.action?loanId="+loanId+'&projectId='+projectId,"查看收息表",true);
}

$(function() { 
	// 判断是什么操作
	var url = "<%=basePath%>beforeLoanController/getLoanProjectByProjectId.action?projectId=${projectId}";
	$.post(url,
		  	function(data){
		  			if(data.isHoop==1){//是否循环项是否隐藏
		  				$(".selectlist1").css("display",'block');
		  			}else{
		  				$(".selectlist1").css("display",'none');
		  			}
		  			if(data.repayOption==3){//还款选项：2个都选中默认为3
		  				data.repayOption = 2;
		  				data.repayOptionTest = 4;
					}else if(data.repayOption==4){
						data.repayOptionTest = 4;//
					}else if(data.repayOption==2){
						data.repayOption = 2;//
					}
		  			// 设置合计费利率
		  			$("#total").numberbox('setValue', parseFloat(data.monthLoanInterest+data.monthLoanMgr+data.monthLoanOtherFee) ); //合计利率 
		  			var loanInterestRecord = data.loanInterestRecord;
		  			var loanMgrRecord = data.loanMgrRecord;
					var loanOtherFee =data.loanOtherFee;
					// 设置贷款ID
					loanId = data.loanId;
					$("#investigationForm").form('load',data);
					// 如果下拉框,数字框，文本框的值是0 默认不显示 zhengxiu
					$('.easyui-combobox').each(function(i){
						var cValue=$(this).combobox('getValue');
						if(cValue=='0' ||cValue=='0.0'||cValue=='0.00'||cValue=='0.0000'){
							$(this).combobox('setValue','');
						}
					});
					$(".easyui-numberbox").each(function(i) {
						var v=$(this).numberbox('getValue');
					    if( v=='0' ||v=='0.0'||v=='0.00'||v=='0.0000'){
					    	$(this).numberbox('setValue','');
					    };       
					}); 
					$(".easyui-textbox").each(function(i) {
						var v=$(this).textbox('getValue');
					    if( v=='0' ||v=='0.0'||v=='0.00'||v=='0.0000'){
					    	$(this).textbox('setValue','');
					    };       
					}); 
					// 复选框    贷款申请 and 额度申请 
					if(data.projectType == 2){
						$("#projectTypeDksq").attr("checked",true);
					}else if(data.projectType == 1){
						$("#projectTypeEdsq").attr("checked",true);
					}else if(data.projectType == 5){
						$("#projectTypeDksq").attr("checked",true);
						$("#projectTypeEdsq").attr("checked",true);
					}
			 	
					// 动态绑定担保方式 
			 		$.ajax({
							type: "POST",
					        data:{"projectId":"${projectId}"},
					    	url : '${basePath}secondBeforeLoanController/getGuaranteeTypeByProjectId.action',
							dataType: "json",
						    success: function(data){
						    	var str = eval(data);
						    	// 判断担保方式的个数
						    	if(str.length == 1){
						    		// 如果只有一个
						    		$("#assWay").combobox("setValue",str[0].guaranteeType);
						    	} else {
							    	var values = "";
							    	for(var i = 0;i<str.length;i++){
							    		if(values == ""){
							    			values = str[i].guaranteeType;
							    		}else{
							    			values += ","+str[i].guaranteeType;
							    		}
							    	}
							    	if(values!=""){
							    		$("#assWay").combobox("setValues",values.split(','));
							    	}
						    	}
								// 显示 or 隐藏抵质押物担保
								setTimeout("InitializationMortgage()",1);
							}
					});	
		  	}, "json");
	
	
	
	setDateAndStatue();
	
	var urlGL = "<%=basePath%>secondBeforeLoanController/getProjectApprovalGl.action?projectId=${projectId}";
	$('#dhglyqGrid').datagrid('options').url = urlGL;
	$('#dhglyqGrid').datagrid('reload');
	loadApproveScrutiny(${projectId});
});

// 回显数据，disabled等
function setDateAndStatue()
{
	
	// 贷款基本信息只读
	$(".beforeloanTable").find("input").attr("disabled","disabled");
	// 只读模式
	if("${readOnly}"=="true")
	{
		$("#addFactorBtn").hide();
		$("#saveTemplateBtn").hide();
		$(".editRegualatoryDiv").hide();
		$(".pt10").find("a").hide();
	}	
	
	// 设置监管状态
   $("#regulatoryStatus").val("${plan.regulatoryStatus}");	
}
var loadApproveScrutiny=function(projectId){
		if(projectId!=-1){
		$.post(BASE_PATH+"beforeLoanController/getLoanProjectByProjectId.action?projectId="+projectId,
			function(ret){
				if(ret.surveyResult!="" && ret.surveyResult!=null){
					$("#approveForm").form('load',ret);
				}
			}, "json");
	}
	}
//格式化显示--是否确认
function isConfirmfilter(value,row,index){
	if(value==1){
		return "是";
	}else{
		return "否";
	}
}

//确认
function confirmMakeLoan(datagridId){
	// 获取所有选中的数据
	var rows = $(datagridId).datagrid('getSelections');
	// 判断是否选中数据
 	if ( rows.length == 0 ) {
 		$.messager.alert("操作提示","请选择数据","error");
		return;
	}
	var pids = "";
	// 循环获取所选中的数据的PID,进行拼接
	for(var i=0;i<rows.length;i++){
  		var row = rows[i];
  		// 判断选中的数据中是否存在已经确认了数据,如果存在,就提醒
  		if( row.isConfirm == 1 ){
  			$.messager.alert("操作提示","选中的数据中有已经确认的,请重新选择!","error");
  			// 调用清除选中数据的函数
			clearSelectRows(datagridId);
			return;
  		}
  		pids += row.pid+",";
  	}
	// 更新路径
	var url = "<%=basePath%>secondBeforeLoanController/updateIsConfirmPrimaryKey.action";
	$.messager.confirm("操作提示","确定确认选择的数据吗?",
		function(r) {
				if(r){
					$.post(url,{pids : pids,isConfirm : 1}, 
					function(ret) {
						//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
						if(ret && ret.header["code"] == 200){
							// 加载数据的路径
							var urlGL = "<%=basePath%>secondBeforeLoanController/getProjectApprovalGl.action?projectId=${projectId}";
							$('#dhglyqGrid').datagrid('options').url = urlGL;
							$(datagridId).datagrid('reload');
							// 清空datagrid的选中数据
							clearSelectRows(datagridId);
						}else{
							$.messager.alert('操作提示',ret.header["msg"],'error');	
						}
					},'json')	
				}
		}
	);
}

// 取消
function cancelMakeLoan(datagridId){
	// 获取所有选中的数据
	var rows = $(datagridId).datagrid('getSelections');
	// 判断是否选中数据
 	if ( rows.length == 0 ) {
 		$.messager.alert("操作提示","请选择数据","error");
		return;
	}
	var pids = "";
	// 循环获取所选中的数据的PID,进行拼接
	for(var i=0;i<rows.length;i++){
  		var row = rows[i];
  		// 判断选中的数据中是否存在已经确认了数据,如果存在,就提醒
  		if( row.isConfirm == -1 ){
  			$.messager.alert("操作提示","选中的数据中有没有确认的,请重新选择!","error");
  			// 调用清除选中数据的函数
			clearSelectRows(datagridId);
			return;
  		}
  		pids += row.pid+",";
  	}
	// 更新路径
	var url = "<%=basePath%>secondBeforeLoanController/updateIsConfirmPrimaryKey.action";
	$.messager.confirm("操作提示","确定取消确认选择的数据吗?",
		function(r) {
				if(r){
					$.post(url,{pids : pids,isConfirm : -1}, 
					function(ret) {
						//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
						if(ret && ret.header["code"] == 200){
							// 加载数据的路径
							var urlGL = "<%=basePath%>secondBeforeLoanController/getProjectApprovalGl.action?projectId=${projectId}";
							$('#dhglyqGrid').datagrid('options').url = urlGL;
							$(datagridId).datagrid('reload');
							// 清空datagrid的选中数据
							clearSelectRows(datagridId);
						}else{
							$.messager.alert('操作提示',ret.header["msg"],'error');	
						}
					},'json')	
				}
		}
	);
}

</script>

<body class="easyui-layout" style="margin: 10px;">
<div data-options="region:'center',border:false">
		<div class=" easyui-panel" title="项目基础信息" data-options="collapsible:true">
        <div class="dueInfoDivxx">
			<table class="beforeloanTable">
				<tr>
				  
					<td align="right" width="80" height="28">项目名称：</td>
					<td><a href="#" onclick="formatterProjectName.disposeClick(${projectId},'${project.projectNumber}')"><font color='blue'> ${project.projectName}</font></a></td>
					<td align="right" width="80" height="28">项目编号：</td>
					<td>${project.projectNumber}</td>
				</tr>
				<tr>
					<td class="label_right1">业务类别：</td>
					<td>${project.businessCatelogName}</td>
					<td class="label_right">业务品种：</td>
					<td>${project.businessTypeName}</td>
				</tr>

				<tr>
					<td class="label_right">流程类别：</td>
					<td>${project.flowCatelogName}</td>
					<td class="label_right">项目经理：</td>
					<td>
					   ${project.accName}
					</td>
				</tr>
			</table>
		</div>	 
	</div>

		<div class="pt10"></div>
		<div class=" easyui-panel" title="贷款基本信息" data-options="collapsible:true">
		<table class="cus_table">			
			<tr>
				<td>
		<form action="<%=basePath%>beforeLoanController/addInformation.action" id="investigationForm"  name="investigationForm" method="post" >
		<div id="loanBasicNews" style="padding-left: 10px;padding-top:10px;height: auto;" >
			<table class="beforeloanTable">
				<tr>
					<td class="label_right"><font color="red">*</font>授信额度：</td>
					<td><input name="creditAmt" class="easyui-numberbox" precision="4" groupSeparator="," required="true" missingMessage="请输入授信额度!"  /></td>
					<td class="label_right"><font color="red">*</font>授信开始日期：</td>
					<td><input name="credtiStartDt" id="credtiStartDt" class="easyui-datebox" editable="false" required="true" missingMessage="请输入授信开始日期!"  /></td>
					<td class="label_right"><font color="red">*</font>授信结束日期：</td>
					<td><input name="credtiEndDt" id="credtiEndDt"   class="easyui-datebox"   validType="checkDateRange['#credtiStartDt']" editable="false"   required="true" missingMessage="请输入授信结束日期!" /></td>
				</tr>
				<tr><!-- data-options="onSelect:checkInfo"  -->
					<td class="label_right"><font color="red">*</font>是否循环：</td>
					<td>
						<input name="isHoop" type="radio" value="1"  onclick="isCheckHoop()" />可循环
	           			 <input name="isHoop" type="radio" value="2" checked="checked"  onclick="isCheckHoop()"  />不可循环
					</td>
					<td class="label_right"></td>
					<td>
						<div class="selectlist1" style="display: none">
						 <input name="circulateType"   id="circulateType"  class="easyui-combobox" editable="false" panelHeight="auto"  
	           			 data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_IS_HOOP'" />
					
						</div>
					</td>
					<td class="label_right"><font color="red">*</font>担保方式：</td>
					<td>						
			            <input id="assWay" class="easyui-combobox" editable="false" required="true"  panelHeight="auto" 
			            data-options="valueField:'pid',textField:'lookupDesc',formatter:check,multiple:true,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=ASS_WAY',onUnselect:unSelectMortgageType,onSelect:selectMortgageType" />
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>贷款金额：</td>
					<td>
						<input name="loanAmt"  class="easyui-numberbox" precision="4" groupSeparator="," required="true" missingMessage="请输入贷款金额!"  />
					</td>
					<td class="label_right"><font color="red">*</font>币种：</td>
					<td>
						<input name="currency"  id="currency" class="easyui-combobox" editable="false" required="true"  panelHeight="auto"  
						data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_CURRENCY'" />
					</td>
					<td class="label_right"><font color="red">*</font>日期模式：</td>
					<td>
						<input name="dateMode" id="dateMode"  class="easyui-combobox" editable="false" required="true"  panelHeight="auto"  
						data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_DATE_MODE'" />
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>还款方式：</td>
					<td>
						<input name="repayFun" id="repayFun"  class="easyui-combobox" editable="false" required="true" panelHeight="auto"
						data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_REPAY_FUN'" />
					</td>
					<td class="label_right"><font color="red">*</font>还款周期：</td>
					<td colspan="3">
						<input name="repayCycleType" id="repayCycleType"  class="easyui-combobox" editable="false" required="true"  panelHeight="auto"  
						data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_REPAY_CYCLE_TYPE'" />
						<input name="repayCycleDate" id="repayCycleDate" class="easyui-numberbox" style="width: 60px;float: left;"  />日/期
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>还款期限：</td>
 
					<td class="repayCycleTd"><input name="repayCycle" id="repayCycle"  class="easyui-textbox" data-options="required:true,validType:'number'"  required="true"   missingMessage="请输入还款期限!"/></td>
 
					<td class="label_right"><font color="red">*</font>计划放款日期：</td>
					<td>
						<input name="planOutLoanDt" id="planOutLoanDt" class="easyui-datebox" editable="false" required="true"  missingMessage="请输入放款日期!"  data-options="onSelect:setValue1Do"/>
					</td>
					<td class="label_right"><font color="red">*</font>计划还款日期：</td>
					<td>
						<input name="planRepayLoanDt" id="planRepayLoanDt" class="easyui-datebox" editable="false" required="true" validType="checkDateRange['#planOutLoanDt']" missingMessage="请输入还款日期!" data-options="onSelect:setValue2Do"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">还款选项：</td>
					<td colspan="1">
						<input type="checkbox" name="repayOption" id="repayOption"  value="2"/>前置付息
					 
						<input type="checkbox" name="repayOptionTest" id="repayOptionTest" value="4"/>一次性支付全部利息
					</td>
					<td class="label_right" colspan="4" >每期还款日： <input name="eachissueOption" id="eachissueOption" type="radio" onclick="isRepayDate()" value="1"   />固定在<input name="repayDate" id="repayDate" class="easyui-numberbox" min="1" max="31"  style="width: 60px;" required="true"   missingMessage="请输入每期还款日!" /> 号还款 <input name="eachissueOption" id="eachissueOption1" type="radio" value="2" onclick="isRepayDate()" />按实际放款日对日还款
					</td>
						  
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>贷款利率：</td>
					<td colspan="5">
						<table>
							<tr>
								<td class="label_right">年化利率：</td>
								<td class="yearLoanOtherFee">
									<input name="yearLoanInterest" id="yearLoanInterest" class="easyui-numberbox" precision="4"  required="true"  style="width: 60px;"  />%
								</td>
								<td class="label_right">月化利率：</td>
								<td class ="monthLoanOtherFee">
									<input name="monthLoanInterest" id="monthLoanInterest" class="easyui-numberbox" precision="4"    required="true" style="width: 60px;"  />%
								</td>
								<td class="label_right">日化利率：</td>
								<td class="dayLoanOtherFee">
									<input name="dayLoanInterest" id="dayLoanInterest" class="easyui-numberbox" precision="4"   required="true" style="width: 60px;"  />%
								</td>
							</tr>
						</table>
					</td>					
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>贷款管理费率：</td>
					<td colspan="5">
						<table>
							<tr>
								<td class="label_right">年化利率：</td>
								<td class="yearLoanOtherFee">
									<input name="yearLoanMgr" id="yearLoanMgr" class="easyui-numberbox" precision="4"   required="true" style="width: 60px;"/>%
								</td>
								<td class="label_right">月化利率：</td>
								<td class ="monthLoanOtherFee">
									<input name="monthLoanMgr" id="monthLoanMgr" class="easyui-numberbox" precision="4"    required="true" style="width: 60px;" />%
								</td>
								<td class="label_right">日化利率：</td>
								<td class="dayLoanOtherFee">
									<input name="dayLoanMgr" id="dayLoanMgr" class="easyui-numberbox" precision="4"    required="true" style="width: 60px;" />%
								</td>
							</tr>
						</table>
					</td>					
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>其它费用费率：</td>
					<td colspan="5">
						<table >
							<tr>
								<td class="label_right">年化利率：</td>
								<td class="yearLoanOtherFee">
									<input name="yearLoanOtherFee" id="yearLoanOtherFee" class="easyui-numberbox" precision="4"   required="true" style="width: 60px;" />%
								</td>
								<td class="label_right">月化利率：</td>
								<td class ="monthLoanOtherFee">
									<input name="monthLoanOtherFee" id="monthLoanOtherFee" class="easyui-numberbox" precision="4"  required="true" style="width: 60px;" />%
								</td>
								<td class="label_right">日化利率：</td>
								<td class="dayLoanOtherFee">
									<input name="dayLoanOtherFee" id="dayLoanOtherFee" class="easyui-numberbox" precision="4"   required="true" style="width: 60px;" />%
								</td>
								<td class="label_right">合计费利率：</td>
								<td><input name="" id="total" class="easyui-numberbox" precision="4" readonly="readonly" required="true" style="width: 60px;" />%</td>
							</tr>
						</table>
					</td>					
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>违约金比例：</td>
					<td colspan="5">
						<table>
							<tr>
								<td class="label_right">贷款总额的：</td>
								<td>
									<input name="liqDmgProportion" class="easyui-numberbox" precision="4" required="true"  style="width: 60px;" />%
								</td>
								<td class="label_right"><font color="red">*</font>逾期贷款利率：</td>
								<td>
									<input name="overdueLoanInterest" class="easyui-numberbox" precision="4" required="true" style="width: 60px;" missingMessage="请输入逾期贷款利率!" />%
								</td>
								<td class="label_right"><font color="red">*</font>逾期罚息利率：</td>
								<td>
									<input name="overdueFineInterest" class="easyui-numberbox" precision="4" required="true" style="width: 60px;" missingMessage="请输入逾期罚息利率!"  />%日
								</td>
								<td class="label_right">挪用罚息利率：</td>
								<td><input name="misFineInterest" class="easyui-numberbox" precision="4"  style="width: 60px;" />%日</td>
							</tr>
						</table>
					</td>					
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>提前还款违约金：</td>
					<td colspan="5">
						<table>
							<tr>
								<td class="label_right">提前还款金额的</td>
								<td><input name="prepayLiqDmgProportion" class="easyui-numberbox"  precision="4"  style="width: 60px;" />%</td>
								<td class="label_right1">贷款手续费：</td>
								<td class="label_right">贷款总额的</td>
								<td><input name="feesProportion" class="easyui-numberbox"  precision="4"  style="width: 60px;" />%</td>
								
							</tr>
						</table>
					</td>
					
				</tr>
				<!-- <tr>
					<td colspan="6" align="center" height="35">
						<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveInvestigation()">保存基本信息</a>
						<a href="#" class="easyui-linkbutton" style="" iconCls="icon-calculation" onclick="loancalc()">贷款试算</a>
		
					</td>
				</tr> -->
			</table>
		</div>
		</form>
		</td>
		</tr>
	</table>
</div>
		
  <!-- 贷款收息表  -->
  <div class="pt10"></div>
 <div class=" easyui-panel" title="贷款收息表" data-options="collapsible:true">
        <%-- <a href="javascript:void(0)" class="easyui-linkbutton" onclick="lookupRecord(${projectId},${loanId})">查看对账记录</a> --%>
			<jsp:include page="../repayment/list_loanCalculate.jsp"></jsp:include>
            <div class="pt10">
			<table width="90%">
				<tr>
					<td colspan="4" height="25"><div style="padding-left:10px"><b>总计提示:</b></div></td>
				</tr>
				<tr>
					<td height="25" style="width: 20%">&nbsp;&nbsp;&nbsp;&nbsp;贷款本金总额：
						${custArrears.receivablePrincipalStr}元</td>
					<td style="width: 20%">未还贷款本金：
						${custArrears.principalSurplusStr}元</td>
					<td style="width: 20%">应收费用总额：
						${custArrears.totalFeedStr}元
					</td>
					<td style="width: 20%">到期未收费用总额：
						${custArrears.noReceiveTotalAmtStr}元</td>
				</tr>
				<tr>
					<td height="25" colspan="3"><div style="padding-left:10px"><b>逾期提示：</b></div></td>
				</tr>
				<tr>
					<td height="25" style="width: 20%">&nbsp;&nbsp;&nbsp;&nbsp;逾期未还款项共计${custArrears.overdueCount} 笔</td>
					<td style="width: 20%">逾期未还款项总额:
						${custArrears.unReceivedOverdueInterestStr}元</td>
					<td style="width: 20%">逾期违约金总额：
						${custArrears.unReceivedOverduePunitiveStr}元</td>
				</tr>
			</table>
		</div>
  </div>
  
 	<div id="dhglyqToolbar" class="easyui-panel" style="border-bottom: 0;">
		<!-- 操作按钮 -->
		<div style="border-bottom: 5px;padding:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  onclick="confirmMakeLoan('#dhglyqGrid')">条件确认</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  onclick="cancelMakeLoan('#dhglyqGrid')">取消确认</a>
		</div>
	</div>
	<div class="dksqListDiv" style="height:auto;width: auto;">
		<table id="dhglyqGrid" title="贷后管理要求列表" class="easyui-datagrid" 
			style="height:auto;width: auto;"
			data-options="
			    url: '',
			    method: 'get',
			    rownumbers: true,
			    singleSelect: false, 
			    fitColumns:false,
			    toolbar: '#dhglyqToolbar',
			    idField: 'pid'">
			<!-- 表头 -->
			<thead><tr>
			    <th data-options="field:'pid',checkbox:true" ></th>
			    <th data-options="field:'infoContent',align:'center'"  >管理要求</th>
			    <th data-options="field:'isConfirm',align:'center',formatter:isConfirmfilter"  >是否确认</th>
			</tr></thead>
		</table>
	</div>
	<div>
		<form action="<%=basePath%>secondBeforeLoanController/addProcedures.action" id="approveForm" name="approveForm" method="post" >
			<input name="pid" type="hidden" />
			<table class="beforeloanTable" style="padding-left: 50px;line-height: 35px;">
				<tr>
					<td class="label_right">是否可提前还款：</td>
					<td>
						<input name="isAllowPrepay" type="radio" value="1" checked="checked" disabled="disabled"/>可
	           			<input name="isAllowPrepay" type="radio" value="0" disabled="disabled"/>不可
					</td>
				</tr>
				<tr>
					<td class="label_right">是否退还利息：</td>
					<td>
						<input name="isReturnInterest" type="radio" value="1" checked="checked" disabled="disabled"/>可
	           			<input name="isReturnInterest" type="radio" value="0" disabled="disabled"/>不可
					</td>
				</tr>
			</table>
		</form>
	</div>
 
  <!-- 监管结果录入  -->
	<div class="pt10"></div>
	<div class=" easyui-panel" title="监管结果录入" data-options="collapsible:true">
		<form id="estTemplate" method="POST">
			<input type="hidden" name="pid" value="${plan.pid}" />
			<input type="hidden" name="status" value="1" />
			
			<table class="cus_table">
				<tr>
					<td class="label_right" style="width: 120px;">计划监管时间：</td>
					<td>${plan.planBeginDt}</td>
					<td class="label_right" style="width: 120px;">计划监管人：</td>
					<td>${plan.regulatoryUser}</td>
					<td class="label_right" style="width: 120px;">监管状态：</td>
					<td><select name="regulatoryStatus" id="regulatoryStatus">
<!-- 							<option value="1">等待监管</option> -->
							<option value="2">监管中</option>
							<option value="3">监管完成</option>
					</select></td>
				
				</tr>
				<tr>
			
					
				<td class="label_right" style="width: 120px;">监管计划备注：</td>
					<td colspan="5"> <textarea rows="5" cols="80" disabled="disabled">${plan.remark}</textarea></td>
				</tr>
				<tr>
					<td colspan="6"></td>
				</tr>
			</table>
			
			<div style="padding-left:10px;padding-top: 5px;"  id="addFactorBtn" >
				<a href="#" plain="true" class="easyui-linkbutton" iconCls="icon-add" onclick="addFactor(${plan.pid});">新增</a>
			</div>
			<div id="est_template" >
			     <table id="regualatoryRecoidGrid" title="监管记录" class="easyui-datagrid" 
			       style=""
					data-options="
					    rownumbers: true,
					    singleSelect: true,
						selectOnCheck: true,
						checkOnSelect: true
					    ">
						<thead><tr>
						    <th data-options="field:'actualDate',width:70,formatter:convertDate" align="center">监管时间</th>
						    <th data-options="field:'regualatorySubject'" align="center">监管标题</th>
						    <th data-options="field:'regualatoryContent'" align="center">监管内容</th>
						    <th data-options="field:'regulatoryResultStr'" align="center">监管结果</th>
						    <th data-options="field:'regualatoryMsg'" align="center">监管意见</th>
						    <th data-options="field:'regualatoryMsgOt1'" align="center">其他意见1</th>
						    <th data-options="field:'regualatoryMsgOt2'" align="center">其他意见1</th>
						    <th data-options="field:'yy',width:120" align="center">操作</th>
						</tr></thead>
						<tbody>
						    <c:forEach items="${historyList}" var="v">
						      <tr>
						        <td align="center">${v.actualDate}</td>
								<td align="center">${v.regualatorySubject}</td>
								<td align="center">${v.regualatoryContent}</td>
								<td align="center">${v.regulatoryResultStr}</td>
								<td align="center">${v.regualatoryMsg}</td>
								<td align="center">${v.regualatoryMsgOt1}</td>
								<td align="center">${v.regualatoryMsgOt2}</td>
						        <td>
						           <div  style="text-align: left;" class="editRegualatoryDiv">
						                   <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editQuota('${v.resultId}','${v.projectId}')">编辑</a>
						                    <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteData('${v.resultId}','${v.projectId}')">刪除</a>
						            </div>       
						        <td>
						      </tr>
						   </c:forEach>
						</tbody>
			      </table>
             </div>
			<div style="text-align: center; padding: 10px;" id="saveTemplateBtn">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save"  onclick="saveTemplate()">保存监管</a>
			</div>
							
			</form>
	</div>
	<!-- 资料上传列表  -->
	<div class="pt10">
	   <jsp:include page="loan_add_datum.jsp"></jsp:include>  
	</div>
</div>
</body>