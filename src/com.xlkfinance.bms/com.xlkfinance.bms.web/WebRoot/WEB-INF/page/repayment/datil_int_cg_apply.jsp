<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/applyCommon.js"></script>
<script  type="text/javascript">
	/**************工作流字段 begin******************/
	var WORKFLOW_ID = "interestChangeRequestProcess"; 
	workflowTaskDefKey = "task_InterestChangeRequest";
	var biaoZhi =-1;
	/**************工作流字段 end********************/
	var projectId =${projectId};
	var projName ="${projectName}";
	var projectName='';
	var interestChgId=${interestChgId};
	 function updatefileinfo(){
			$('#interestChgIdedit').val(interestChgId);  
			$('#editfileUploadFormedit').form('submit', {
				success : function(data) {
								$('#up_data').datagrid({  
					    url:'queryRepayCgapplyFile.action?interestChgId='+interestChgId
					  
					});  
					$('#upload-edit').dialog('close');
				}
			});
		}
	$(function() {
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var atTitle = tab.panel('options').title;
		if(atTitle=="利率变更查看"){
			projectName='${projectName}';
			$("#planbutton").hide();
			$("#viewRepaymentPlan").show();
			
			$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('disabled','disabled');
			$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('readOnly','readOnly');
			$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').addClass('l-btn-disabled');
			$('#loan_workflow_from .beforeloanTable').addClass('none');
		}
	
		 if(atTitle=="业务主管审核" ||atTitle=="风控总监审查" ||atTitle=="财务还款处理"||atTitle=="总经理审批"||atTitle=="财务确认"||atTitle=="合同制作签署"){
				$('#savebutton').attr('disabled','disabled');
				$('#savebutton').attr('readOnly','readOnly');
				$('#savebutton').addClass('l-btn-disabled');
				$('#savebutton').removeAttr('onclick');
				
				$('#planbutton').attr('disabled','disabled');
				$('#planbutton').attr('readOnly','readOnly');
				$('#planbutton').addClass('l-btn-disabled');
				$('#planbutton').removeAttr('onclick');
				
				$('#uploadbutton').attr('disabled','disabled');
				$('#uploadbutton').attr('readOnly','readOnly');
				$('#uploadbutton').addClass('l-btn-disabled');
				$('#uploadbutton').removeAttr('onclick');
				
				$('#delectbutton').attr('disabled','disabled');
				$('#delectbutton').attr('readOnly','readOnly');
				$('#delectbutton').addClass('l-btn-disabled');
				$('#delectbutton').removeAttr('onclick');
				
				$('#applybutton').attr('disabled','disabled');
				$('#applybutton').attr('readOnly','readOnly');
				$('#applybutton').addClass('l-btn-disabled');
				$('#applybutton').removeAttr('onclick');
				
				analysisParam();
		}
		
	});
	var interestChgId = ${interestChgId};
	function sizeFileter(value,row,index){
		if(row.fileSize>0){
			return  parseInt(row.fileSize/1024)+" KB";
		}else{ 
			return '';
		} 
	}
var saveCgLoanInfo=1;
	
/* 	function saveLoanInfo() {
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var atTitle = tab.panel('options').title;
		if(atTitle=="利率变更查看"){
			$.messager.alert("系统提示","此流程已在审核不能修改数据","warning"); 	
			return false;
		}
		if(saveCgLoanInfo==2){
			$.messager.alert("系统提示","不能重复提交","warning"); 	
			return false;
		}
		
		if(atTitle=="利率变更编辑"||atTitle=="审批官审查"){
			$('#saveloaninfo').attr('action', 'updateRepayCgapplyInfo.action');	
		
			$('#updateinterestChgId').val(interestChgId);
			$('#saveloaninfo').form('submit', {
				success : function(data) {
					if (data > 0) {
						$.messager.alert("操作提示", "更新成功！", "success");
					}
				}
			});
			return false;
		}
		if(atTitle=="利率变更申请"){
			$('#saveloaninfo').attr('action', 'insertRepayCgapplyInfo.action');	
			
			$('#saveloaninfo').form('submit', {
				success : function(data) {
					if (data > 0) {
					 interestChgId = data;
					 saveCgLoanInfo=2;
						$.messager.alert("操作提示", "保存成功！", "success");
					}
				}
			});
			return false;
		}
		
		

	}
	 */
	 
	function saveLoanInfo() {
		var interstId =  $("#updateinterestChgId").val();
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var atTitle = tab.panel('options').title;
		if(atTitle=="利率变更查看"){
			$.messager.alert("系统提示","此流程已在审核不能修改数据","warning"); 	
			return false;
		}
		if($("#updateinterestChgId").val()=="-1"){
			// 根据项目ID判断是否有流程在执行
			if(isWorkflowByStatus(projectId)){
				return;
			}
		}

		$('#saveloaninfo').attr('action', 'insertRepayCgapplyInfo.action');	
		$('#saveloaninfo').form('submit', {
			success : function(data) {
				if (data > 0) {//大于0 表示 是新增 
				 	 interestChgId = data;
					 if( $("#updateinterestChgId").val()=="-1"){
						 $("#updateinterestChgId").val(interestChgId);
					 }
					 $.messager.alert("操作提示", "保存成功！", "success");
				}else{
					 $.messager.alert("操作提示", "修改成功！", "success");
				}
			}
		});
		 
 
	}
	 
	
	function updateLoanInfo() {
		$('#saveloaninfo').attr('action', 'updateRepayCgapplyInfo.action');
		$('#saveloaninfo').form('submit', {
			success : function(data) {
				if (data > 0) {
					var interestChgId = data;
					$.messager.alert("操作提示", "保存成功！", "success");
				}
			}
		});
	}
	
	function disposeClick(pid) {
		url = "${basePath}beforeLoanController/addNavigation.action?projectId="+pid+"&param='refId':'"+pid+"','projectName':'"+projName+"'";
		parent.openNewTab(url,"贷款申请详情",true);
	} 
	
	$(function(){
		//给月利率绑定鼠标离开事件 
		$('body').delegate('.monthLoanOtherFee > span','blur',function(){
			var v2= $(this).find(' > input').val();//获取到当前的值 
			var t=0;
			$('.monthLoanOtherFee > span').each(function(){//循环三个月利率 把值叠加起来 算出合计利率 
				t+= Number($(this).find(' > input').val());
			});
			
			$("#month_total").numberbox('setValue', parseFloat(t) ); //合计利率 
			$(this).parent().parent().find('.yearLoanOtherFee .easyui-numberbox').numberbox('setValue',v2*12.0);//年化利率
			$(this).parent().parent().find('.dayLoanOtherFee .easyui-numberbox').numberbox('setValue',v2/30.0);//日化利率
		})
		
		//年利率绑定鼠标离开事件  
		$('body').delegate('.yearLoanOtherFee > span','blur',function(){//给月利率绑定鼠标离开事件 
			var v2= $(this).find(' > input').val();//获取到当前的值 
			var t=0;
			$('.monthLoanOtherFee > span').each(function(){//循环三个月利率 把值叠加起来 算出合计利率 
				t+= Number($(this).find(' > input').val());
			});
			$("#month_total").numberbox('setValue', parseFloat(t) ); //合计利率 
			$(this).parent().parent().find('.monthLoanOtherFee .easyui-numberbox').numberbox('setValue',v2/12.0);//年化利率
			$(this).parent().parent().find('.dayLoanOtherFee .easyui-numberbox').numberbox('setValue',v2/360.0);//日化利率
		})
		
		//日利率绑定鼠标离开事件  
		$('body').delegate('.dayLoanOtherFee > span','blur',function(){//给月利率绑定鼠标离开事件 
			var v2= $(this).find(' > input').val();//获取到当前的值 
			var t=0;
			$('.monthLoanOtherFee > span').each(function(){//循环三个月利率 把值叠加起来 算出合计利率 
				t+= Number($(this).find(' > input').val());
			});
			$("#month_total").numberbox('setValue', parseFloat(t) ); //合计利率 
			$(this).parent().parent().find('.monthLoanOtherFee .easyui-numberbox').numberbox('setValue',v2*30.0);//年化利率
			$(this).parent().parent().find('.yearLoanOtherFee .easyui-numberbox').numberbox('setValue',v2*360.0);//日化利率
		})
	});
	function uploadType(value, row, index) {
		if (1 == row.fileFunType) {
			return "利率变更申请资料";
		}
		if (2 == row.fileFunType) {
			return "调查资料";
		}
		return "利率变更申请资料";
	}
</script>
<script type="text/javascript"	src="${basePath}upload/javascript/upload.js" charset="utf-8"></script>
	
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<title>贷款申请</title>
<link href="/BMS/upload/css/fileUpload.css" type="text/css"
	rel="stylesheet" />
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<!-- 基本项目信息 -->
<div class="easyui-panel" title="基本项目信息" data-options="collapsible:true">
	<div style="padding:5px">
		<table>
			<tr>
				<td height="35" width="300" align="center">项目编号:${projectNum}</td>
				<td><div class="iniTitle">项目名称:<a onclick="formatterProjectName.disposeClick(${projectId},'${projectNum}')" href='#'><span style='color:blue;'>${projectName}</span></a></div></td>
			</tr>
		</table>
	</div>
</div>
<div class="clear pt10"></div>
<div class="easyui-panel" title="贷款收息表" data-options="collapsible:true">
<div id="tablegrid">
<%@ include file="list_loanCalculate.jsp"%>
</div>
</div>
<div class="clear pt10"></div>
<!-- 提前还款登记信息 -->
<div class="easyui-panel " title="利率变更登记信息" data-options="collapsible:true">	
	<table class="cus_table" style="border:none;">		
		<tr>
			<td>				
			  <table >
			  	<tr>
					<td colspan="6" class="int_title2"><div class="reldiv"><span>利率变更前</span></div></td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>贷款利率：</td>
					<td colspan="5">
						<table>
							<tr>
								<td class="label_right">年化利率：</td>
								<td><input name="yearLoanInterest" id="yearLoanInterest"
									value="${interest.yearLoanInterest}" class="easyui-numberbox"
									precision="4" readonly="readonly" required="true"
									style="width: 60px;" />%</td>
								<td class="label_right">月化利率：</td>
								<td><input name="monthLoanInterest" id="monthLoanInterest"
									value="${interest.monthLoanInterest}" class="easyui-numberbox"
									precision="4" readonly="readonly"
									required="true"
									style="width: 60px;" />%</td>
								<td class="label_right">日化利率：</td>
								<td><input name="dayLoanInterest" id="dayLoanInterest"
									value="${interest.dayLoanInterest}" class="easyui-numberbox"
									precision="4" readonly="readonly" required="true"
									style="width: 60px;" />%</td>
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
								<td><input name="yearLoanMgr" id="yearLoanMgr"
									value="${interest.yearLoanMgr}" class="easyui-numberbox"
									precision="4" readonly="readonly" required="true"
									style="width: 60px;" />%</td>
								<td class="label_right">月化利率：</td>
								<td><input name="monthLoanMgr" id="monthLoanMgr"
									value="${interest.monthLoanMgr}" class="easyui-numberbox"
									precision="4" readonly="readonly"
									required="true"
									style="width: 60px;" />%</td>
								<td class="label_right">日化利率：</td>
								<td><input name="dayLoanMgr" id="dayLoanMgr"
									value="${interest.dayLoanMgr}" class="easyui-numberbox"
									precision="4" readonly="readonly" required="true"
									style="width: 60px;" />%</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>其它费用费率：</td>
					<td colspan="5">
						<table>
							<tr>
								<td class="label_right">年化利率：</td>
								<td><input name="yearLoanOtherFee" id="yearLoanOtherFee"
									value="${interest.yearLoanOtherFee}" class="easyui-numberbox"
									precision="4" readonly="readonly" required="true"
									style="width: 60px;" />%</td>
								<td class="label_right">月化利率：</td>
								<td><input name="monthLoanOtherFee" id="monthLoanOtherFee"
									value="${interest.monthLoanOtherFee}" class="easyui-numberbox"
									precision="4" readonly="readonly"
									 required="true"
									style="width: 60px;" />%</td>
								<td class="label_right">日化利率：</td>
								<td><input name="dayLoanOtherFee" id="dayLoanOtherFee"
									value="${interest.dayLoanOtherFee}" class="easyui-numberbox"
									precision="4" readonly="readonly" required="true"
									style="width: 60px;" />%</td>
								<td class="label_right">合计费利率：</td>
								<td><input name="" id="heji" class="easyui-numberbox"
									readonly="readonly"
									value="${interest.monthLoanOtherFee+interest.monthLoanMgr+interest.monthLoanInterest}"
									precision="4" required="true" style="width: 60px;" />%</td>
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
								<td><input name="liqDmgProportion" class="easyui-numberbox"
									readonly="readonly" value="${interest.liqDmgProportion}"
									precision="4" required="true" style="width: 60px;" />%</td>
								<td class="label_right"><font color="red">*</font>逾期贷款利率：</td>
								<td><input name="overdueLoanInterest" class="easyui-numberbox"
									value="${interest.overdueLoanInterest}" precision="4"
									required="true" style="width: 60px;" readonly="readonly"
									missingMessage="请输入逾期贷款利率!" />%</td>
								<td class="label_right"><font color="red">*</font>逾期罚息利率：</td>
								<td><input name="overdueFineInterest" class="easyui-numberbox"
									readonly="readonly" value="${interest.overdueFineInterest}"
									precision="4" required="true" style="width: 60px;"
									missingMessage="请输入逾期罚息利率!" />%日</td>
								<td class="label_right">挪用罚息利率：</td>
								<td><input name="misFineInterest" class="easyui-numberbox"
									readonly="readonly" value="${interest.misFineInterest}"
									precision="4" style="width: 60px;" />%日</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="label_right1">提前还款违约金：</td>
					<td colspan="5">
						<table>
							<tr>
								<td class="label_right">提前还款金额的：</td>
								<td><input name="prepayLiqDmgProportion"
									class="easyui-numberbox" precision="4" style="width: 60px;"
									value="${interest.prepayLiqDmgProportion}"
									missingMessage="请输入提前还款违约金利率!" readonly="readonly" />
									%</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>				
				<form   id="saveloaninfo" name="saveloaninfo" method="post" >
				<input type="hidden" name="interestChgId" value="${interestChgId}"
						id="updateinterestChgId" >
				<input type="hidden"  name="projectId" value="${interest.projectId}"> 
				<input type="hidden"  name="loanId" value="${loanId}">
				<input type="hidden"  name="projectName" value="${interest.projectName}">
				<input type="hidden"  name="status" value="${interest.status}">
				<input type="hidden"  name="completeDttm" value="${interest.completeDttm}">
			<%-- 	<input type="hidden"  name="requestDttm" value="${interest.requestDttm}"> --%>
				<input type="hidden"  name="requestStatus" value="${interest.requestStatus}">
				<input type="hidden"  name="repayDate" value="${interest.repayDate}">
				<input type="hidden"  name="repayOption" value="${interest.repayOption}">
				<input type="hidden"  name="planRepayLoanDt" value="${interest.planRepayLoanDt}">
				<input type="hidden"  name="planOutLoanDt" value="${interest.planOutLoanDt}">
				<input type="hidden"  name="repayCycle" value="${interest.repayCycle}">
				<input type="hidden"  name="repayCycleDate" value="${interest.repayCycleDate}">
				<input type="hidden"  name="repayCycleTypeText" value="${interest.repayCycleTypeText}">
				<input type="hidden"  name="repayCycleType" value="${interest.repayCycleType}">
				<input type="hidden"  name="reoayFunText" value="${interest.reoayFunText}">
				<input type="hidden"  name="repayFun" value="${interest.repayFun}">
				<input type="hidden"  name="dateMode" value="${interest.dateMode}">
				<input type="hidden"  name="currencyText" value="${interest.currencyText}">
				<input type="hidden"  name="currency" value="${interest.currency}">
				<input type="hidden"  name="creditAmt" value="${interest.creditAmt}">
				<input type="hidden"  name="projectNumber" value="${interest.projectNumber}">
				<input type="hidden"  name="repayCycleTypeText" value="${interest.repayCycleTypeText}">
				<input type="hidden"  name="interestVersion" value="${interest.interestVersion}"> 
				  <table width="100%">
					<tr>
						<td colspan="6" class="int_title2"><div class="reldiv"><span>利率变更后</span></div></td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>贷款利率：</td>
						<td colspan="5">
							<table>
								<tr>
									<td class="label_right">年化利率：</td>
									<td class="yearLoanOtherFee"><input name="yearLoanInterest" id="yearLoanInterest"
										value="${reqlist.yearLoanInterest}" class="easyui-numberbox"
										precision="4"  required="true"
										style="width: 60px;" />%</td>
									<td class="label_right">月化利率：</td>
									<td class="monthLoanOtherFee"><input name="monthLoanInterest" id="monthLoanInterest"
										value="${reqlist.monthLoanInterest}" class="easyui-numberbox"
										precision="4" 
										required="true"
										style="width: 60px;" />%</td>
									<td class="label_right">日化利率：</td>
									<td class="dayLoanOtherFee"><input name="dayLoanInterest" id="dayLoanInterest"
										value="${reqlist.dayLoanInterest}" class="easyui-numberbox"
										precision="4"  required="true"
										style="width: 60px;" />%</td>
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
									<td class="yearLoanOtherFee"><input name="yearLoanMgr" id="yearLoanMgr"
										value="${reqlist.yearLoanMgr}" class="easyui-numberbox"
										precision="4"  required="true"
										style="width: 60px;" />%</td>
									<td class="label_right">月化利率：</td>
									<td class="monthLoanOtherFee"><input name="monthLoanMgr" id="monthLoanMgr"
										value="${reqlist.monthLoanMgr}" class="easyui-numberbox"
										precision="4" 
										required="true"
										style="width: 60px;" />%</td>
									<td class="label_right">日化利率：</td>
									<td class="dayLoanOtherFee"><input name="dayLoanMgr" id="dayLoanMgr"
										value="${reqlist.dayLoanMgr}" class="easyui-numberbox"
										precision="4"  required="true"
										style="width: 60px;" />%</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>其它费用费率：</td>
						<td colspan="5">
							<table>
								<tr>
									<td class="label_right">年化利率：</td>
									<td class="yearLoanOtherFee"><input name="yearLoanOtherFee" id="yearLoanOtherFee"
										value="${reqlist.yearLoanOtherFee}" class="easyui-numberbox"
										precision="4"  required="true"
										style="width: 60px;" />%</td>
									<td class="label_right">月化利率：</td>
									<td class="monthLoanOtherFee"><input name="monthLoanOtherFee" id="monthLoanOtherFee"
										value="${reqlist.monthLoanOtherFee}" class="easyui-numberbox"
										precision="4" 
										 required="true"
										style="width: 60px;" />%</td>
									<td class="label_right">日化利率：</td>
									<td class="dayLoanOtherFee"><input name="dayLoanOtherFee" id="dayLoanOtherFee"
										value="${reqlist.dayLoanOtherFee}" class="easyui-numberbox"
										precision="4"  required="true"
										style="width: 60px;" />%</td>
									<td class="label_right">合计费利率：</td>
									<td><input name="" id="month_total" class="easyui-numberbox"
										readonly="readonly"
										value="${reqlist.monthLoanOtherFee+reqlist.monthLoanMgr+reqlist.monthLoanInterest}"
										precision="4" required="true" style="width: 60px;" />%</td>
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
									<td><input name="liqDmgProportion" class="easyui-numberbox"
										 value="${reqlist.liqDmgProportion}"
										precision="4" required="true" style="width: 60px;" />%</td>
									<td class="label_right"><font color="red">*</font>逾期贷款利率：</td>
									<td><input name="overdueLoanInterest" class="easyui-numberbox"
										value="${reqlist.overdueLoanInterest}" precision="4"
										required="true" style="width: 60px;" 
										missingMessage="请输入逾期贷款利率!" />%</td>
									<td class="label_right"><font color="red">*</font>逾期罚息利率：</td>
									<td><input name="overdueFineInterest" class="easyui-numberbox"
										 value="${reqlist.overdueFineInterest}"
										precision="4" required="true" style="width: 60px;"
										missingMessage="请输入逾期罚息利率!" />%日</td>
									<td class="label_right">挪用罚息利率：</td>
									<td><input name="misFineInterest" class="easyui-numberbox"
										 value="${reqlist.misFineInterest}"
										precision="4" style="width: 60px;" />%日</td>
								</tr>
							</table>
						</td>
					</tr>
				
					<tr>
						<td class="label_right1">提前还款违约金：</td>
						<td colspan="5">
							<table>
								<tr>
									<td class="label_right">提前还款金额的：</td>
									<td><input name="prepayLiqDmgProportion"
										class="easyui-numberbox" precision="4" style="width: 60px;"
										value="${reqlist.prepayLiqDmgProportion}"
										/>%</td>
				
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>利率变更日期：</td>
						<td><input name="requestDttm" id="requestDttm" class="easyui-datebox"  editable="false" value="${reqlist.requestDttm}" required="true" missingMessage="利率变更日期!" /></td>
					</tr>
				</table>
				<div style="padding-left: 30px;">
					<p>
						利率变更原因：
						<textarea rows="4" class="text_style" style="width: 594px;height:70px;" name="reason" id="reason" >${reqlist.reason}
					     </textarea>
					</p>
				</div>
				
				</form>
			</td>
		</tr>
	
		<tr>
			<td align="center" height="35">
				<div id="dlg_spgsc11" >		
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="savebutton" onclick="saveLoanInfo()">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="applybutton"  onclick="applyCgbutton()">生成利率变更申请书</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="planbutton"  onclick="saveRepayPlan()">重新生成计划还款表</a>
				<a href="javascript:void(0);" id="viewRepaymentPlan" style="display:none" class="easyui-linkbutton download" onclick="searchReapyList()">查看还款计划表</a>
				</div>
			</td>
		</tr>
	</table>
</div>

	<div id="upload-edit" class="easyui-dialog"
		style="width: 500px; height: 300px; padding: 10px 20px;" closed="true">
		<form id="editfileUploadFormedit" name="fileUploadeditForm" action="${basePath}repaymanageloan/updateloadfileinfo.action" method="post" target="hidden_frame">
			<p style="display: none;">
				<input id="interestChgIdedit" name="interestChgId"  />
			</p>
			<p style="display: none;">
			 	<input type="hidden" name="fileId"id="fileId">
			</p>
			<p>
				<span style="margmargin-top: 10px;">上传说明:</span>
				<textarea rows="4" style="width: 300px;" id="editfileDesc" name="fileDesc"></textarea>
			</p>
			<p>
				<a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="updatefileinfo()">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#upload-edit').dialog('close')">取消</a>
			</p>
		</form>
	</div>
	<div id="toolbar_data">
		<div style="padding-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="uploadbutton" iconCls="icon-add" plain="true" onclick="addData()">上传</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" id="delectbutton" iconCls="icon-remove" plain="true" onclick="delAnyDataDele()">删除</a>
		</div>
	</div>
	<div class="up_dataDiv pt10">
		<table id="up_data" title="利率变更资料上传" class="easyui-datagrid"
			data-options="
				url: 'queryRepayCgapplyFile.action?interestChgId='+interestChgId,
				method: 'POST',
			    idField: 'pId',
		   		rownumbers: true,
			    singleSelect: false,
			    collapsible:true,
			    pagination: true,
			    fitColumns:true,
			    toolbar: '#toolbar_data',
				onLoadSuccess:lookfor"
				 >
			<thead>
				<tr>
				<th data-options="checkbox:true,field:'pId',width:30"></th>
					<th data-options="field:'fileFunType',width:300 ,formatter:uploadType">文件种类</th>
					<th data-options="field:'fileType',width:120" >文件类型</th>
					<th hidden="true" data-options="field:'fileUrl'"></th>
					<th data-options="field:'fileName',width:170">文件名称</th>
					<th data-options="field:'fileSize',width:50,formatter:sizeFileter">大小</th>
					<th data-options="field:'uploadDttm',width:120 ,formatter:convertDate">上传时间</th>
					<th data-options="field:'fileDesc',width:120">上传说明</th>
					<th data-options="field:'cz',width:190,formatter:caozuofiter">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="diaCgupdate" class="easyui-dialog" style="width: 500px; height: auto; top: 100px; padding: 10px 20px;" closed="true" buttons="#data-buttons">   
		<form id="addCgupdate"
			action="${basePath}repaymanageloan/saveData.action"
			name="addOrupdate" method="post" enctype="multipart/form-data">
			<table>
<!-- 				<tr>
					<td width="120" align="right" height="28"><font color="red">*</font>文件类型：</td>
					<td>	 <select name="fileKinds" id="selectAge"
					style="border: 1px solid #000">
					<option value="1">营业执照</option>
					<option value="2">调查资料</option>
					<option value="3">其他</option>
				</select> </td>
						
				</tr> -->
				
				<tr>
					<td align="right" width="120" height="28"><font color="red">*</font>文件路径：</td>
					<td>
					<input class="input_text" type="text" id="txt1"	name="fileToUpload" /> 
					<input type="button" name="uploadfile2"	id="uploadfile2" style="padding-left: 10px;" value="浏览..." /> 
					<input class="input_file" size="30" type="file" name="file1" id="file1"
								onchange="txt1.value=this.value" />
					<input type="hidden" name="interestChgId" id="interestChgId"></td>
				</tr>
				<tr>
					<td width="120" align="right">上传说明：</td>
					<td><input class="easyui-textbox"
						style="width: 240px; height: 100px"
						data-options="required:false,multiline:true" name="fileDesc"
						id="fileDesc"></td>
				</tr>

			</table>
		</form>
	</div>
	
	<div id="data-buttons">
		<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addOrupdateData()">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#diaCgupdate').dialog('close')">取消</a>
	</div>
	<input type="hidden" name="pro_Id" id="pro_Id">
	
	<!-- 放款前落实条件 --> 
	<div  class="pt10"  id="include_approve_scrutiny">
	 	<%@ include file="procedures/approve_scrutiny_cg.jsp"%>
	</div>  
	<div  class="pt10"  id="include_approve_contract">
	 	<%@ include file="procedures/rate_cg_add_contract.jsp"%>
	</div>  
	
	<!-- 工作流程 -->	
	<div class="pt10" style="border-width:2px;border-top:none;">
		<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
			<div class="loanworkflowWrapDiv"  style="padding:5px">
				<%@ include file="../common/loanWorkflow.jsp"%>
				<%@ include file="../common/task_hi_list.jsp"%>
			</div>
		</div>
	</div>
</div>
</body>




<script type="text/javascript">
	$(function() {
		$('#pro_Id').val(projectId);
	});

	function caozuofiter(value, row, index) {
		path = row.filePath;
		var downloadDom = "<a class='download' href='${basePath}beforeLoanController/downloadData.action?path="
				+ row.filePath+"&fileName="+row.fileName+"'><font color='blue'>点击下载</font></a> ";
		var downd = "<span  style='display:none;' id='"+row.pId+"'>"
				+ row.filePath
				+ "</span><a href='javascript:void(0)'  class='delRement' onclick='delData("+ row.pId + ")'><font  color='blue'> 删除</font></a>";
				var  edit ="   <a href='javascript:void(0)' class='delRement' onclick='editfileinfo("+ JSON.stringify(row) + ")'><font  color='blue'> 编辑</font></a>";
				return downloadDom + downd+edit;
	}
	function editfileinfo(row){
	$("#fileId").val(row.pId);
	$("#editfileKind").val(row.fileFunType);
	  $("#editfileDesc").val(row.fileDesc); 
	$('#upload-edit').dialog('open').dialog('setTitle', "修改上传信息");
}
	function delfilebypath(dta) {
		$.post("${basePath}rePaymentController/delectUpdateAdvapply.action", {
			pId:dta
		}, function(data) {
			$.post("queryRepayCgapplyFile.action",{
				interestChgId : interestChgId
			}, function(dt) {
				$('#up_data').datagrid('loadData', eval("{" + dt + "}"));
			});
		});
	}
	function fileSizefiter(value, row, index) {
		var sNum = '';
		if (value == 0 || value == undefined || value == null) {
			value == 0;
		} else {
			sNum = (value / 1024).toFixed(1) + 'k';
			if (value / 1024 / 1024 >= 1) {
				sNum = (value / 1024 / 1024).toFixed(1) + 'M';
			}
		}
		return sNum;
	}

	function addData() {
		$('#addCgupdate').form('clear');
		$('#diaCgupdate').dialog('open').dialog('setTitle','上传资料');
		$("#diaCgupdate").dialog("move",{top:$(document).scrollTop()+260*0.5}); 
	}
	function editData(dId, fDesc, fPropertyName, fProperty) {
		if (dId == '' || dId == undefined || dId == null) {
			if (confirm("该类文件不存在，是否进行上传?")) {
				$('#addOrupdate').form('clear');
				$('#filePropertyName').text(fPropertyName);
				$('#dataFileProperty').val(fProperty);
				$('#dataProjectId').val($('#pro_Id').val());
				$('#addOrupdate').attr('action',
						"${basePath}beforeLoanController/saveData.action");
				$('#dialog_data').dialog('open').dialog('setTitle', '上传资料');
			}
			return false;
		}
		$('#addOrupdate').form('clear');
		$('#filePropertyName').text(fPropertyName);
		$('#dataPid').val(dId);
		if (fDesc == '' || fDesc == null || fDesc == 'null') {
			$('#datafileDesc').textbox('setValue', '');
		} else {
			$('#datafileDesc').textbox('setValue', fDesc);
		}
		$('#dataFileProperty').val(fProperty);
		$('#dataProjectId').val($('#pro_Id').val());
		$('#addOrupdate').attr('action',
				"${basePath}beforeLoanController/editData.action");
		$('#dialog_data').dialog('open').dialog('setTitle', '修改资料');
	}

	function addOrupdateData() {
		//debugger;
		var file2 = $('#txt1').val();
		var proId = $('#dataProjectId').val();
		var fileProperty = $('#dataFileProperty').val();
		if (file2 == "" || null == file2) {
			$.messager.alert("操作提示", "请选择文件资料", "error");
			return false;
		}
		if (interestChgId == -1) {
			$.messager.alert("操作提示", "请先选择保存变更利息", "error");
			return false;
		}
		$('#interestChgId').val(interestChgId);
		$('#addCgupdate').form('submit', {
			success : function(data) {
				var str = data.substring(1,data.length-1);
				$.messager.alert("操作提示", str, "info");
				$('#diaCgupdate').dialog("close");
				$('#up_data').datagrid({
					url:'queryRepayCgapplyFile.action?interestChgId='+interestChgId
				});
				$('#up_data').datagrid("reload"); 
			}
		})

	}

	function delData(dId) {
		if (dId == "" || null == dId) {
			$.messager.alert("操作提示", "该类型文件不存在，无法删除！", "error");
			return false;
		} else {
			if (confirm("是否确认删除?")) {
				$.post("${basePath}rePaymentController/delectUpdateAdvapply.action", {
					pId : dId
				}, function(data) {
					$.post("queryRepayCgapplyFile.action", {
						interestChgId : interestChgId
					}, function(dt) {
						$('#up_data').datagrid('reload');
						$('#up_data').datagrid('clearChecked');
					});
				});
			}
		}
	}

	function strToJson(str) {
		return JSON.parse(str);
	}

	function getPidArray(row) {
		var pidArray = "";
		for (var i = 0; i < row.length; i++) {
			if (i != row.length - 1) {
				pidArray = pidArray + row[i].pId + ",";
			} else if (i == 0) {
				pidArray = pidArray + row[i].pId;
			} else if (i == row.length - 1) {
				pidArray = pidArray + row[i].pId;
			}
		}
		return pidArray;
	}

	
	
	function delAnyDataDele() {
			var row = $('#up_data').datagrid('getChecked');
			if (row.length == 0) {
				$.messager.alert("操作提示", "请选择数据", "error");
				return;
			}// 获取选中的系统用户名 
			var pIds = "";
			for (var i = 0; i < row.length; i++) {
				pIds += row[i].pId;
				pIds += ",";
			}
			//var str = pIds.substring(0, pIds.length - 1); // 取字符串。  
			
			
			var str = pIds.substring(0, pIds.length-1); // 取字符串。  
			$.messager.confirm("操作提示","确定删除选择的此文件吗?",
				function(r) {
					if(r){
						$.post("${basePath}rePaymentController/delectUpdateAdvapply.action", {
							pId : str
						}, function(data) {
							$.post("queryRepayCgapplyFile.action", {
								interestChgId : interestChgId
							}, function(dt) {
								$('#up_data').datagrid('reload');
								$('#up_data').datagrid('clearChecked');
							});
						});
			 		}
				});
		}
	
	
	function delAnyData() {
		var row = $('#up_data').datagrid('getSelections');
		if (row.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}// 获取选中的系统用户名 
		var pIds = "";
		for (var i = 0; i < row.length; i++) {
			pIds += row[i].pId;
			pIds += ",";
		}
		var str = pIds.substring(0, pIds.length - 1); // 取字符串。  
		
		
		var str = pIds.substring(0, pIds.length-1); // 取字符串。  
		$.messager.confirm("操作提示","确定删除选择的此文件吗?",
			function(r) {
				if(r){
					$.post("${basePath}rePaymentController/delectUpdateAdvapply.action", {
						pId : str
					}, function(data) {
						$.post("queryRepayCgapplyFile.action", {
							interestChgId : interestChgId
						}, function(dt) {
							$('#up_data').datagrid('loadData', eval("{" + dt + "}"));
						});
					});
		 		}
			});
	}
	
	function saveRepayPlan(){
		if(interestChgId>0){
			operRepaymentList('${basePath}repaymanageloan/advMakeRepayCgInfo.action?projectId=${projectId}&interestChgId='+interestChgId);
		}else{	
			$.messager.alert("操作提示", "你需要先保存利率变更登记信息！", "error");
		}
	}
	
	//查看控制
	function lookfor(){
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var atTitle = tab.panel('options').title;
		if(atTitle=="利率变更查看"){
			$('.delRement font').attr('color','gray');
			$('.delRement').removeAttr('onclick');
		 
		}
	}
	
	/**
	 * 查看还款计划表
	 */
	function searchReapyList(){
		operRepaymentList('${basePath}repaymanageloan/advMakeRepayCgInfo.action?projectId=${projectId}&interestChgId='+interestChgId);
	}
	function applyCgbutton(){
		if(interestChgId>0){
			window.location.href="${basePath}repaymanageloan/makeCgApplyFile.action?interestChgId="+interestChgId;	
		}
	}

 	checkInfo = -1;//利率为0时做判断 让界面能正常显示0
 	
 	$(function() {
		var tab1 = parent.$('#layout_center_tabs').tabs('getSelected');
		var atTitle1 = tab1.panel('options').title;
		if(atTitle1=="总经理审批"||atTitle1=="财务确认"||atTitle1=="业务主管审核"||atTitle1=="合同制作签署"){
				$('#spgbjesave').attr('disabled','disabled');
				$('#spgbjesave').attr('readOnly','readOnly');
				$('#spgbjesave').addClass('l-btn-disabled');
				$('#spgbjesave').removeAttr('onclick');
			
				$('#spgbjdet').attr('disabled','disabled');
				$('#spgbjdet').attr('readOnly','readOnly');
			    $('#spgbjdet').addClass('l-btn-disabled');
				$('#spgbjdet').removeAttr('onclick');
				
				$('#spgbjedit').attr('disabled','disabled');
				$('#spgbjedit').attr('readOnly','readOnly');
			    $('#spgbjedit').addClass('l-btn-disabled');
				$('#spgbjedit').removeAttr('onclick');
				
				$('#spgbjadd').attr('disabled','disabled');
				$('#spgbjadd').attr('readOnly','readOnly');
				 $('#spgbjadd').addClass('l-btn-disabled');
				$('#spgbjadd').removeAttr('onclick');
				
				//**下面是条件取消，确认 
				$('#akeLoanCol').attr('disabled','disabled');
				$('#akeLoanCol').attr('readOnly','readOnly');
			 	$('#akeLoanCol').addClass('l-btn-disabled');
				$('#akeLoanCol').removeAttr('onclick');

				$('#akeLoanSpg').attr('disabled','disabled');
				$('#akeLoanSpg').attr('readOnly','readOnly');
			 	$('#akeLoanSpg').addClass('l-btn-disabled');
				$('#akeLoanSpg').removeAttr('onclick');
				$('#up_data').datagrid({
					onLoadSuccess:function(){
			    		$('.up_dataDiv .easyui-combobox').attr('disabled','disabled');
						$('.up_dataDiv input[type="checkbox"]').attr('disabled','disabled');
						$('.up_dataDiv .easyui-textbox').attr('disabled','disabled');
						$('.up_dataDiv a:not(.download) font').css('color','gray');
						$('.up_dataDiv .easyui-linkbutton:not(.download) ,.up_dataDiv a:not(.download)').removeAttr('onclick');
						$('.up_dataDiv .easyui-linkbutton:not(.download) ,.up_dataDiv input,.up_dataDiv textarea,.up_dataDiv a:not(.download)').attr('disabled','disabled');
						$('.up_dataDiv .easyui-linkbutton:not(.download) ,.up_dataDiv input,.up_dataDiv textarea,.up_dataDiv a:not(.download)').attr('readOnly','readOnly');
						$('.up_dataDiv .easyui-linkbutton:not(.download) ,.up_dataDiv input,.up_dataDiv textarea,.up_dataDiv a:not(.download)').addClass('l-btn-disabled');
						$("#planbutton").hide();
						$("#viewRepaymentPlan").show();
					}
				})
			}
	}); 
</script>
</html>

