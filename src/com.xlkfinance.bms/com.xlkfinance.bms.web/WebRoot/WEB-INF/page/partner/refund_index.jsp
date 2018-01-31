<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<script type="text/javascript">


	$(function(){
		$('#currRealCapitalMoney_refund,#currRealXiFee_refund,#currRealManageFee_refund,#currRealOtherFee_refund').textbox({  
			  onChange: function(value){  
				  caclRealTotal();
			  }
			}); 
		
		$('#isSettlementStatus_refund').combobox({         
			onChange:function(){  
				var isSettlementStatusTemp = $("#isSettlementStatus_refund").combobox('getValue');
				//获取提前还款信息
			 	getAdvancedSettlement(isSettlementStatusTemp);
				
				if(isSettlementStatusTemp == 1){
					$(".comm_show_class_1").show();
					$(".comm_show_class").hide();
				}else{
					$(".comm_show_class").show();
					$(".comm_show_class_1").hide();
				}
	       }
	    });
		
	});

	/**获取提前还款信息*/
	function getAdvancedSettlement(isSettlementStatus){
		
		var pid = $("#partnerId_refund").val();
		
		if(isSettlementStatus == 1){
			
			$("#currRealOtherFee_refund").textbox("setValue","");
			$("#currRealManageFee_refund").textbox("setValue","");
			$("#currRealXiFee_refund").textbox("setValue","");
			$(".comm_show_class").hide();
			//显示并加载	
			$.ajax({
				type: "POST",
		        url: "<%=basePath%>projectPartnerController/getAdvancedSettlement.action",
		        data:{"pid":pid},
		        dataType: "json",
		        success: function(result){
					$('#advancedSettlement_refund').combobox({         
						data:result.header["paymentList"],  
				        valueField:'date',
				        textField:'date_amount',
				        onLoadSuccess:function(){
				        	var data = $('#advancedSettlement_refund').combobox('getData');
				        	
							if(data != null){
								var dateTemp = data[0].date;
								var dateAmountTemp =data[0].date_amount;
								
								if(dateTemp != "" ){
						        	$("#advancedSettlement_refund").combobox('select',dateTemp);
						        	if(dateAmountTemp.split("_").length >1){
										var amount  = dateAmountTemp.split("_")[1];
										$("#currRealRefundDate_refund").textbox("setValue",dateTemp); 
										$("#currRealCapitalMoney_refund").textbox("setValue",amount); 
						        	}
								}
							}
				         },
				         onSelect:function(){  
							var dateAmountTemp = $("#advancedSettlement_refund").combobox('getText');
							var dateTemp = $("#advancedSettlement_refund").combobox('getValue');
							if(dateTemp != "" ){
								if(dateAmountTemp.split("_").length >1){
									var amount  = dateAmountTemp.split("_")[1];
									$("#currRealRefundDate_refund").textbox("setValue",dateTemp); 
									$("#currRealCapitalMoney_refund").textbox("setValue",amount); 
								}
								
							}
				       }
				    });
				},error : function(result){
					alert("操作失败！");
				}
			 });
		}else{
			$(".comm_show_class").show();
		}
	}
	
	
	//列表操作
	var isCurrFlag = false;
	function detailInfo(value, rowData, index) {
		if(rowData.refundStatus ==null || rowData.refundStatus == 0 || 
				rowData.refundStatus == 1 || rowData.refundStatus == 4){
			var projectDom = "<a href='javascript:void(0)' onclick='uploadPartnerThirdRefundInit("+index+")'><font color='blue'>编辑</font></a>";
			return projectDom;
		}
	}
	
	/**格式化是否提前结清*/
	function formatIsSettlementStatus(val,row){
		if(val == 1){
			return "是";
		}else if(val == 2){
			return "否";
		}
	}
	/**格式化是否作废*/
	function formatIsForbit(val,row){
		if(val == 1){
			return "是";
		}else if(val == 2){
			return "否";
		}
	}
	/**格式化逾期状态*/
	function formatCurrOverdueStatus(val,row){
		if(val == 1){
			return "未逾期";
		}else if(val == 2){
			return "逾期未结清";
		}else if(val == 3){
			return "逾期已结清";
		}
	}
	/**格式化还款状态*/
	function formatRefundStatus(val,row){
		if(val == 1 || val == 0){
			return "未申请";
		}else if(val == 2){
			return "申请中";
		}else if(val == 3){
			return "还款成功";
		}else if(val == 4){
			return "还款失败";
		}
	}
	
	//更新还款初始化
	function uploadPartnerThirdRefundInit(rowIndex){
		$('#refundPartnerForm_2').form('reset');
		
		var rowData = $('#grid').datagrid('getData').rows[rowIndex];
		
		var pid = rowData.pid;
		var currNo = rowData.currNo;			//期数
		var currPlanRefundDate = rowData.currPlanRefundDate;				//预计还款日期
		var currShouldCapitalMoney = rowData.currShouldCapitalMoney;		//应还本金金额
		var currShouldXiFee = rowData.currShouldXiFee;						//应还利息
		var currShouldManageFee = rowData.currShouldManageFee;				//应还管理费
		var currShouldOtherFee = rowData.currShouldOtherFee;				//应还其他费用
		var currShouldTotalMoney = rowData.currShouldTotalMoney;			//还款总金额
		
		var currRealRefundDate = rowData.currRealRefundDate;				//实际还款日期
		var currRealCapitalMoney = rowData.currRealCapitalMoney;			//实还本金
		var currRealXiFee = rowData.currRealXiFee;							//实还利息
		var currRealManageFee = rowData.currRealManageFee;					//实还管理费
		var currRealOtherFee = rowData.currRealOtherFee;					//实还其他费用
		
		var currOverdueStatus = rowData.currOverdueStatus;					//逾期状态
		var isSettlementStatus = rowData.isSettlementStatus;				//是否提前结清
		
		if(isSettlementStatus == 1){
			$(".comm_show_class_1").show();
			$(".comm_show_class").hide();
		}else{
			$(".comm_show_class").show();
			$(".comm_show_class_1").hide();
		}
		
		$("#currRealRefundDate_refund").datebox();
		
		$("#pid_refund").val(pid);
		$("#partnerId_refund").val(rowData.partnerId);
 		$("#currNo_refund").textbox('setValue',currNo);		
 		$("#currPlanRefundDate_refund").textbox('setValue',currPlanRefundDate);		
		$("#currShouldCapitalMoney_refund").textbox('setValue',currShouldCapitalMoney);		
		$("#currShouldXiFee_refund").textbox('setValue',currShouldXiFee);		
		$("#currShouldManageFee_refund").textbox('setValue',currShouldManageFee);		
		$("#currShouldOtherFee_refund").textbox('setValue',currShouldOtherFee);		
		$("#currShouldTotalMoney_refund").textbox('setValue',currShouldTotalMoney);		
		
		$("#currRealRefundDate_refund").textbox('setValue',currRealRefundDate);		
		$("#currRealCapitalMoney_refund").textbox('setValue',currRealCapitalMoney);		
		$("#currRealXiFee_refund").textbox('setValue',currRealXiFee);		
		$("#currRealManageFee_refund").textbox('setValue',currRealManageFee);		
		$("#currRealOtherFee_refund").textbox('setValue',currRealOtherFee);	 	
		
		if(currOverdueStatus > 0){
			$('#currOverdueStatus_refund').combobox('setValue', currOverdueStatus);
		}
		if(isSettlementStatus > 0){
			$('#isSettlementStatus_refund').combobox('setValue', isSettlementStatus);
		}
		$('#refund_data_2').dialog('open').dialog('setTitle','编辑还款信息');
		
	}
	
	
	//更新还款信息
	function uploadPartnerThirdRefund(){
		
  		var currPlanRefundDate = $("#currPlanRefundDate_refund").textbox('getValue');	//预计还款日
  		var currRealRefundDate = $("#currRealRefundDate_refund").textbox('getValue');
  		
  		var currRealCapitalMoney = $("#currRealCapitalMoney_refund").textbox('getValue');
 		var currRealXiFee = $("#currRealXiFee_refund").textbox('getValue');
 		var currRealManageFee = $("#currRealManageFee_refund").textbox('getValue');
 		var currRealOtherFee = $("#currRealOtherFee_refund").textbox('getValue');     
		
  		var currOverdueStatus=$('#currOverdueStatus_refund').combobox('getValue');
		var isSettlementStatus=$('#isSettlementStatus_refund').combobox('getValue');
		  
		
		if(isSettlementStatus == null || isSettlementStatus == ""){
			$.messager.alert('操作提示',"请选择是否提前结清",'error');	
			return;
		}else if(currRealRefundDate == "" || currRealRefundDate == null){
			$.messager.alert("操作提示","请选择实际还款日期","error"); 
			return false;
		}else if(currRealCapitalMoney == ""){
			$.messager.alert('操作提示',"请填写实还本金",'error');	
			return;
		}else if(!(currRealCapitalMoney >=0)){
			$.messager.alert('操作提示',"实还本金必须大于等于0",'error');	
			return;
		}
		
		var planDate =(currPlanRefundDate +" 23:59:59").replace(/-/g,"/");
		var realDate =(currRealRefundDate +" 23:59:59").replace(/-/g,"/");
		//提前还款
		if(isSettlementStatus ==  1){
			if(new Date(planDate) < new Date(realDate)){
				$.messager.alert('操作提示',"提前还款，实际还款日期错误",'error');	
				return; 
			}
		}else{
			if(currRealXiFee == null || currRealXiFee == ''){
				$.messager.alert('操作提示',"实还利息不能为空",'error');	
				return;
			}
		}
	 	if(currOverdueStatus == null || currOverdueStatus == ""){
			$.messager.alert('操作提示',"请选择逾期状态",'error');	
			return;
		} 
	 	
		$.messager.confirm("操作提示","确认更新还款信息?",
				function(r) {
		 			if(r){
		 				$('#refundPartnerForm_2').form('submit', {
							url : "uploadPartnerThirdRefund.action",
							onSubmit : function() {
								MaskUtil.mask("提交中,请耐心等候......");  
									return true; 
							},
							success : function(result) {
								MaskUtil.unmask();
								var ret = eval("("+result+")");
								//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
								if(ret && ret.header["success"] ){
									$.messager.alert('操作提示',ret.header["msg"]);
									// 刷新列表信息
				 					$("#grid").datagrid('load');
				 					// 清空所选择的行数据
				 					clearSelectRows("#grid");
				 					$('#refund_data_2').dialog('close');
								}else{
									$.messager.alert('操作提示',ret.header["msg"],'error');	
								}
							},error : function(result){
								MaskUtil.unmask();
								alert("操作失败！");
							}
						});
		 			}
				});
	}
	
	//计算实还总金额
	function caclRealTotal(){
		var realTotal =  0 ;
  		var currRealCapitalMoney = $("#currRealCapitalMoney_refund").textbox('getValue');
 		var currRealXiFee = $("#currRealXiFee_refund").textbox('getValue');
 		var currRealManageFee = $("#currRealManageFee_refund").textbox('getValue');
 		var currRealOtherFee = $("#currRealOtherFee_refund").textbox('getValue');     
 		
 		realTotal += parseFloat(currRealCapitalMoney == "" ? 0 : currRealCapitalMoney);
 		realTotal += parseFloat(currRealXiFee == "" ? 0 : currRealXiFee);
 		realTotal += parseFloat(currRealManageFee == "" ? 0 : currRealManageFee);
 		realTotal += parseFloat(currRealOtherFee == "" ? 0 : currRealOtherFee);
 		realTotal =realTotal.toFixed(2);
 		
		$("#currRealTotalMoney_refund").textbox('setValue',realTotal);		
	}
	
	//提交还款
	function applyPayment(){
		var rows = $('#grid').datagrid('getSelections');
		var pids = "";
		
		if (rows.length == 0) {
			$.messager.alert("操作提示","请选择数据！","error");
			return;
		}
		var isSettlementStatusCount = 0 ;
		for (var i = 0; i < rows.length; i++) {
			//验证状态
			if(rows[i].refundStatus == 3){
				$.messager.alert("操作提示","还款成功不允许操作","error");
				return ;
			}else if(rows[i].isForbit == 1){
				$.messager.alert("操作提示","己作废不允许操作","error");
				return ;
			}else if(rows[i].currRealRefundDate == null || rows[i].currRealRefundDate == ""){
				$.messager.alert("操作提示","请完善第"+rows[i].currNo+"期还款信息","error");
				return ;
			}
			if (i == 0) {
				pids += rows[i].pid;
			} else {
				pids += "," + rows[i].pid;
			}
			if(rows[i].isSettlementStatus == 1){
				isSettlementStatusCount ++ ;
			}
		}
		
		if(isSettlementStatusCount > 1){
			$.messager.alert("操作提示","只能存在一条提前还款记录","error");
			return ;
		}
		
		$.messager.confirm("操作提示","确认提交还款?",
			function(r) {
	 			if(r){
					$.post("<%=basePath%>projectPartnerController/applyPayment.action",{pids : pids}, 
						function(ret) {
							//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
							if(ret && ret.header["success"] ){
								$.messager.alert('操作提示',ret.header["msg"]);
								// 重新项目列表信息
			 					$("#grid").datagrid('load');
			 					// 清空所选择的行数据
			 					clearSelectRows("#grid");
							}else{
								$.messager.alert('操作提示',ret.header["msg"],'error');	
							}
						},'json');
	 			}
			});
	}
	
	
	
	//查询提前还款应还金额
	
	
	

	
	//重置按钮
	function majaxReset(){
		$('#searchFrom').form('reset');
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="refundList.action?partnerId=${partnerId}" method="post" id="searchFrom">
				<!-- 查询条件 -->	
				<div style="padding: 5px">
				<table>
					<tr>
						<input type="hidden" id="rows" name="rows">
						<input type="hidden" id="page" name="page" value='1'>
					</tr>
				</table>
				</div>
			</form>
		
			<div style="padding-bottom: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" 
						iconCls="icon-add" plain="true" onclick="applyPayment()">提交还款</a>
<!-- 					<a href="javascript:void(0)" class="easyui-linkbutton" 
						iconCls="icon-add" plain="true" onclick="history.go(-1)">返回</a> -->
			</div>
		</div>
		<div class="perListDiv" style="height:100%;">
			<table id="grid" title="还款记录" class="easyui-datagrid"
				style="height:100%; width: auto;"
				data-options="
				    url: 'refundList.action?partnerId=${partnerId }',
				    method: 'post',
				    rownumbers: true,
				    pagination: true,
				    singleSelect: false,
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
						<th data-options="field:'pid',checkbox:true"></th>
						<th data-options="field:'currNo'">当前期数</th>
						<th data-options="field:'currPlanRefundDate'">预计还款日期</th>
						<th data-options="field:'currShouldCapitalMoney'">应还本金金额</th>
						<th data-options="field:'currShouldXiFee'">应还利息</th>
						<th data-options="field:'currShouldManageFee'">应还管理费</th>
						<th data-options="field:'currShouldOtherFee'">应还其他费用</th>
						<th data-options="field:'currShouldTotalMoney'">还款总金额</th>
						<th data-options="field:'currRealRefundDate'">实际还款日期</th>
						<th data-options="field:'currRealCapitalMoney'">实还本金</th>
						<th data-options="field:'currRealXiFee'">实还利息</th>
						<th data-options="field:'currRealManageFee'">实还管理费</th>
						<th data-options="field:'currRealOtherFee'">实还其他费用</th>
						<th data-options="field:'currRealTotalMoney'">实还总金额</th>
						<th data-options="field:'currOverdueStatus',formatter:formatCurrOverdueStatus">逾期状态</th>
						<th data-options="field:'currOverdueDays'">逾期天数</th>
						<th data-options="field:'oweCapitalMoney'">剩余本金</th>
						<th data-options="field:'isSettlementStatus',formatter:formatIsSettlementStatus">是否提前结清</th>
						<th data-options="field:'isForbit',formatter:formatIsForbit">是否作废</th>
						<th data-options="field:'refundStatus',formatter:formatRefundStatus">还款状态</th>
						<th data-options="field:'updateTime'">更新时间</th>
						<th data-options="field:'cz',formatter:detailInfo">操作</th>
						
					</tr>
				</thead>
			</table>
		</div>
		
		<!-- 还款信息填写 -->
		<div id="refund_data_2"  class="easyui-dialog" style="width:600px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons_refund_2">
			<form id="refundPartnerForm_2" name="refundPartnerForm_2" method="post" action="<%=basePath%>projectPartnerController/updatePartnerRefund.action" >
				<!-- 项目id -->
				<input type="hidden" name="pid" id="pid_refund">
				<input type="hidden" name="partnerId" id="partnerId_refund">
				<table>
					<tr>
						<td width="120" align="right" height="28">期数：</td>
						<td colspan="3">
							<input name="currNo" id="currNo_refund" class="easyui-textbox" disabled="disabled" />
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28">预计还款日期：</td>
						<td colspan="3">
							<input name="currPlanRefundDate" id="currPlanRefundDate_refund" class="easyui-textbox" disabled="disabled" />
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28">应还本金：</td>
						<td>
							<input name="currShouldCapitalMoney" id="currShouldCapitalMoney_refund" class="easyui-textbox" disabled="disabled" />
						</td>
						<td width="120" align="right" height="28">应还利息：</td>
						<td>
							<input name="currShouldXiFee" id="currShouldXiFee_refund" class="easyui-textbox" disabled="disabled" />
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28">应还管理费：</td>
						<td>
							<input name="currShouldManageFee" id="currShouldManageFee_refund" class="easyui-textbox" disabled="disabled" />
						</td>
						<td width="120" align="right" height="28">应还其他费用：</td>
						<td>
							<input name="currShouldOtherFee" id="currShouldOtherFee_refund" class="easyui-textbox" disabled="disabled" />
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28">还款总金额：</td>

						<td colspan="3">
							<input name="currShouldTotalMoney" id="currShouldTotalMoney_refund" class="easyui-textbox" disabled="disabled"/>
						</td>
					</tr>
					
 					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>是否提前结清：</td>
						<td colspan="3">
						
				 			 <select  name="isSettlementStatus" id="isSettlementStatus_refund"  class="select_style easyui-combobox" data-options="validType:'selrequired'"style="width:129px;">
								<option value="2" >否</option>
								<option value="1" >是</option>
							</select>
						</td>
					</tr>
					
 					<tr class="comm_show_class_1">
						<td width="120" align="right" height="28">提前还款日期：</td>
						<td colspan="3">
				 			 <select id="advancedSettlement_refund"  class="select_style easyui-combobox" 
				 			 	data-options="validType:'selrequired'" style="width:150px;">
								<!-- <option value="">请选择</option> -->
							</select>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>实际还款日期：</td>
						<td>
							<input name="currRealRefundDate" id="currRealRefundDate_refund" class="easyui-textbox"  />
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>实还本金：</td>
						<td>
							<input name="currRealCapitalMoney" id="currRealCapitalMoney_refund" class="easyui-textbox"  data-options="required:true,min:0,max:9999999999,precision:2"/>
						</td>
						<td class="comm_show_class" width="120" align="right" height="28"><font color="red">*</font>实还利息：</td>
						<td class="comm_show_class">
							<input name="currRealXiFee" id="currRealXiFee_refund" class="easyui-textbox" data-options="required:true,min:0,max:9999999999,precision:2" />
						</td>
					</tr>
					<tr class="comm_show_class">
						<td width="120" align="right" height="28">实还管理费：</td>
						<td>
							<input name="currRealManageFee" id="currRealManageFee_refund" class="easyui-textbox" data-options="min:0,max:9999999999,precision:2"  />
						</td>
						<td width="120" align="right" height="28">实还其他费用：</td>
						<td>
							<input name="currRealOtherFee" id="currRealOtherFee_refund" class="easyui-textbox" data-options="min:0,max:9999999999,precision:2" />	
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>实还款总金额：</td>
						<td colspan="3">
							<input name="currRealTotalMoney" id="currRealTotalMoney_refund" class="easyui-textbox" editable="false" data-options="required:true,min:0,max:9999999999,precision:2"  />
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>逾期状态：</td>
						<td colspan="3">
				 			 <select  name="currOverdueStatus" id="currOverdueStatus_refund"  class="select_style easyui-combobox" data-options="validType:'selrequired'" style="width:129px;">
								<option value="">--请选择--</option>
								<option value="1" >未逾期</option>
								<option value="2" >逾期未结清</option>
								<option value="3" >逾期已结清</option>
							</select>
						</td>
					</tr> 
				</table>
			</form>
		</div>
		<div id="data-buttons_refund_2">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="uploadPartnerThirdRefund()" >保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#refund_data_2').dialog('close')">取消</a>
		</div>
		
		
		
	</div>

</body>
