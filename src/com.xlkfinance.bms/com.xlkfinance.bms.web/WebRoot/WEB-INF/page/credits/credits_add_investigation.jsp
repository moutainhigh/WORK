<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<div class="easyui-panel" style="width:auto;padding-top:10px;">
		<form action="<%=basePath%>creditsController/addInformation.action" id="investigationForm"  name="investigationForm" method="post" >
		<input type="hidden" name="pid" id="project_id" />
		<input type="hidden" name="loanId" value="-1" />
		<input type="hidden" name="creditId" />
		<input type="hidden" name="assWay" />
		<input type="hidden" name="limitId" />
		<input type="hidden"  id="aAmts" />
		<input type="hidden" name="assWayText" />
		<input type="hidden" name="judgeRepayCycle" value="-1" /> 
		<input type="hidden" name="oldProjectId" id="old_Project_Id" value="-1"/>
		<div class="fitem" style="padding-left: 25px;" >
		
		<table>
			<tr>
				<td align="right" width="80" height="28">项目名称：</td>
				<td><input name="projectName" id="projectName" type="text" style="width: 500px;color: red;" class="text_style " > </td>
			</tr>
			<tr>
				<td align="right" width="80" height="28">项目编号：</td>
				<td ><input name="projectNumber" id="projectNumber" type="text" style="width: 500px;color: red;" class="text_style  " readonly="readonly" > </td>
			</tr>
		</table>
		
		</div>
		<div class="pt10" style="padding-left:10px;padding-right:10px;">
		<div class="easyui-panel" title="项目基本信息设置" data-options="collapsible:true">	
		<div id="projectBasicNews" style="height: auto;line-height: 35px;" >
			<table class="creditsTable">
				<tr>
					<td class="label_right1">业务类别：</td>
					<td>
						<input name="businessCatelog" class="easyui-combobox" editable="false" panelHeight="auto"  
	           			 data-options="valueField:'pid',textField:'lookupDesc',url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_BUSINESS_CATELOG'" />
					</td>
					<td class="label_right">我方类型：</td>
					<td>
						<input name="myType" class="easyui-combobox" editable="false" panelHeight="auto"  
	           			 data-options="valueField:'pid',textField:'lookupDesc',url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_MY_TYPE'" />
					</td>
					<td class="label_right">我方主体：</td>
					<td>
						<input name="myMain" class="easyui-combobox" style="width: 220px;" editable="false" panelHeight="auto"  
	            		data-options="valueField:'pid',textField:'lookupDesc',url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_MY_MAIN'" />
					</td>
				</tr>
				
				<tr>
					<td class="label_right">业务品种：</td>
					<td>
						 <input name="businessType" class="easyui-combobox" editable="false" panelHeight="auto"  
	           			 data-options="valueField:'pid',textField:'lookupDesc',url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_BUSINESS_TYPE'" />
					</td>
					<td class="label_right">流程类别：</td>
					<td>
						 <input name="flowCatelog" class="easyui-combobox" editable="false" panelHeight="auto"  
	            		data-options="valueField:'pid',textField:'lookupDesc',url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_FLOW_CATELOG'" />
					</td>
					<td class="label_right">项目经理：</td>
					<td>
						<input name="realName" id="realName" class="easyui-combobox" editable="false"  required="true"
						 data-options="url:'${basePath}customerController/getCusManagerName.action?projectId='+newProjectId,valueField:'pids',textField:'userName'" />
					</td>
				</tr>
				<tr>
					<td class="label_right1">贷款利息收取单位：</td>
					<td colspan="5">
						  <input name="loanInterestRecord"  id="loanInterestRecord" class="easyui-combobox" editable="false" panelHeight="auto"  style="width: 180px;"
				            data-options="valueField:'pid',textField:'chargeName',value:'-1',url:'<%=basePath%>secondBeforeLoanController/getFinanceAcctManage.action',onSelect:getAccountByPrimaryKeyAccrual" />
				            <input name="loanInterestRecordNo" id="loanInterestRecordNo"  type="text" class="text_style" style="width: 300px;" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label_right2">贷款管理费收取单位：</td>
					<td colspan="5">
						<input name="loanMgrRecord" class="easyui-combobox" editable="false" panelHeight="auto" style="width: 180px;"
				            data-options="valueField:'pid',textField:'chargeName',value:'-1',url:'<%=basePath%>secondBeforeLoanController/getFinanceAcctManage.action',onSelect:getAccountByPrimaryKeyFees" />
			            <input name="loanMgrRecordNo" id="loanMgrRecordNo" type="text" class="text_style" style="width: 300px;" readonly="readonly" />
					</td>
				</tr> 
				<tr>
					<td class="label_right1">其它费用收取单位：</td>
					<td colspan="5">
						<input name="loanOtherFee" class="easyui-combobox" editable="false" panelHeight="auto" style="width: 180px;"
				            data-options="valueField:'pid',textField:'chargeName',value:'-1',url:'<%=basePath%>secondBeforeLoanController/getFinanceAcctManage.action',onSelect:getAccountByPrimaryKeyOther" />
			            <input name="loanOtherFeeNo" id="loanOtherFeeNo" type="text" class="text_style" style="width: 300px;" readonly="readonly" />
					</td>
				</tr>
			</table>
			
		</div>
			
		</div>
		</div>
		<div class="pt10" style="padding-left:10px;padding-right:10px;">
		<div class="easyui-panel" title="贷款基本信息" data-options="collapsible:true">	
					
		<div id="loanBasicNews" style="padding-left: 10px;padding-top:10px;height: auto;" >
			<table class="creditsTable">
				<tr>
					<td class="label_right"><font color="red">*</font>授信额度：</td>
					<td><input name="creditAmt"  id="creditAmt" class="easyui-numberbox" precision="2" groupSeparator="," readonly="readonly" /></td>
					<td class="label_right"><font color="red">*</font>授信开始日期：</td>
					<td><input name="credtiStartDt" id="credtiStartDt" class="easyui-datebox" data-options="onSelect:setValueStaus"  readonly="readonly"  /></td>
					<td class="label_right"><font color="red">*</font>授信结束日期：</td>
					<td><input name="credtiEndDt" id="credtiEndDt"   class="easyui-datebox"  data-options="onSelect:setValueStaus" validType="checkDateRange['#credtiStartDt']"  /></td>
				</tr>
				<tr><!-- data-options="onSelect:checkInfo"  -->
					<td class="label_right"><font color="red">*</font>是否循环：</td>
					<td >
						<input name="isHoop" type="radio" value="1" disabled="disabled" onclick="isCheckHoop()" />可循环
	           			 <input name="isHoop" type="radio" value="2" disabled="disabled" onclick="isCheckHoop()"  />不可循环
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
						<input type="hidden" name="assWayTemp" id="assWayTemp"/>	
			            <input id="assWay" class="easyui-combobox" editable="false" required="true"  panelHeight="auto"  
			            data-options="valueField:'pid',textField:'lookupDesc',multiple:true,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=ASS_WAY',onUnselect:applyCommon.unSelectMortgageType,onSelect:applyCommon.selectMortgageType" />
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>贷款金额：</td>
					<td>
						<input name="loanAmt" id="loanAmtId"  class="easyui-numberbox" precision="2" groupSeparator="," required="true" missingMessage="请输入贷款金额!"  />
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
 
					<td class="repayCycleTd">
						<input name="repayCycle" id="repayCycle"  class="easyui-textbox" data-options="required:true,validType:'number'"  required="true"   missingMessage="请输入还款期限!"/>
						<input id="hkqx" type="hidden" value="-1" />
					</td>
					
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
						<input type="checkbox" name="repayOption" id="repayOption" value="2"/>前置付息
					 
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
								<td><input name="misFineInterest" class="easyui-numberbox" precision="4" required="true"  style="width: 60px;" />%日</td>
							</tr>
						</table>
					</td>					
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>提前还款违约金：</td>
					<td colspan="5">
						<table>
							<tr>
								<td class="label_right">提前还款金额的：</td>
								<td><input name="prepayLiqDmgProportion" class="easyui-numberbox" required="true"  precision="4"  style="width: 60px;" />%</td>
								<td class="label_right1">贷款手续费：</td>
								<td class="label_right">贷款总额的：</td>
								<td><input name="feesProportion" class="easyui-numberbox" required="true"  precision="4"  style="width: 60px;" />%</td>
								
							</tr>
						</table>
					</td>
					
				</tr>
				<tr>
					<td colspan="6" align="center" height="35">
						<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="creditsCommon.saveInvestigation()">保存基本信息</a>
						<a href="#" class="easyui-linkbutton" style="" iconCls="icon-calculation" onclick="loancalcInfo()">贷款试算</a>
		
					</td>
				</tr>
			</table>
		</div>		
		</div>
		</div>
		</form>
		<div class="control_disbase" style="padding: 10px 10px 0 10px;">
			<div class=" easyui-panel" title="担保措施" data-options="collapsible:true">
				<div id="guaranteesMeasures" style="padding: 10px;height: auto;line-height: 35px;width: auto;" >
					<%@ include file="add_mortgage.jsp" %>
				</div>
			</div>
		</div>
		<%-- 尽职报告调查 --%>
		<div class="pt10" style="padding-left:10px;padding-right:10px;">
		<div class="easyui-panel" title="尽职报告调查" data-options="collapsible:true">	
		
		<div id="dueDiligence" style="padding-left: 10px;padding-top:10px;height: auto;line-height: 35px;" >
			<form action="<%=basePath%>secondBeforeLoanController/addSurveyReport.action" id="surveyReport" name="surveyReport" method="post" >
			<input type="hidden" name="projectId" id="surProjectId">
			<input type="hidden" name="pid" id="surPid" value="0">
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font><span id="introductionId">个人简介：</span></label>
             	<input name="introduction" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font><span id="projectSource">个人主要财务状况：</span></label>
             	<input name="projectSource" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>项目来源：</label>
             	<input name="financialStatus" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>贷款用途：</label>
             	<input name="loanPurposes" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>还款能力：</label>
             	<input name="repayAnalysis" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>风险提示：</label>
             	<input name="riskWarning" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>保证措施：</label>
             	<input name="assuranceMeasures" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>

			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>调查结论：</label>
             	<input name="surveyResults" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'" >
			</div>

			<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" class="easyui-linkbutton" style="margin-left: 249px;" iconCls="icon-save" onclick="applyCommon.saveSurveyReport()">保存调查报告</a>
				<a href="#" class="easyui-linkbutton" style="margin-left: 249px;" iconCls="icon-save" onclick="exportSurveyReport()">导出调查报告</a>
			</div>
			</form>
		</div>
		</div>
		</div>
	</div>
	 
	<div id="percom_toolbar">
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="selectComToExport()">选择该客户生成调查报告</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="UnselectComToExport()">无旗下公司继续生成调查报告</a>
		</div>
	</div>
	<div id="percomDialog" class="easyui-dialog" closed="true" style="width:880px;height:400px;">
		<table id="percomGrid" title="个人客户尽责调查报告旗下公司选择" class="easyui-datagrid" style="height:100%;width: auto;"
		 			data-options="
				    url: '',
				    method: 'get',
				    collapsible:true,
				    rownumbers: true,
				    singleSelect: true,
				    pagination: true,
				    toolbar: '#percom_toolbar',
				    idField: 'pid',
				    selectOnCheck:true,
					checkOnSelect: true">
			<thead><tr>
				<th data-options="field:'pid',checkbox:true"></th>
			    <th data-options="field:'value1',width:200,sortable:true"  >企业名称</th>
			    <th data-options="field:'value3',width:200,sortable:true"  >组织机构代码</th>
			    <th data-options="field:'value4',width:200,sortable:true" >营业执照号</th>
			    <th data-options="field:'value5',width:100,sortable:true" >所用制性质</th>
			    <th data-options="field:'value6',width:100,sortable:true" >法人</th>
			    <th data-options="field:'value7',width:100,sortable:true" >注册资金</th>
			    <th data-options="field:'value8',width:100,sortable:true" >联系电话</th>
			    <th data-options="field:'value9',width:100,sortable:true" >成立日期</th>
				</tr></thead>
		</table>
	</div>
	
	<style type="text/css">
		.investigation{
			width:120px;
			text-align: right;
		}
		
		.no_frame{
			border:0;
			background: white;
		}
		
		#table_dzywxx tr td{
			width:150px;
		}
		
		</style>
	
	<script type="text/javascript">
		
	
	
	
	// *计划还 款日期 动态计算放款日期或者期限
 	function setValueStaus(newValue, oldValue) {
 		var outloanDt = $('#planOutLoanDt').datebox('getValue');// 放款日期
 		var repayloanDt = $('#planRepayLoanDt').datebox('getValue');// 还款日期
 		var StartDt = $('#credtiStartDt').datebox('getValue');// 授信开始日期
 		var EndDt = $('#credtiEndDt').datebox('getValue');// 授信结束日期
 		var repayCycle = $('#repayCycle').textbox('getValue'); // 授信结束日期
 		 if(outloanDt!=""){
 			 if(StartDt>outloanDt){
 				 $.messager.alert('操作提示', "授信开始日期大于计划放款日期", 'info');
 				 $('#planOutLoanDt').datebox('setValue',"");// 放款日期
 				 $('#repayCycle').textbox('setValue',"");// 还款日期
 			 }else if (EndDt<repayloanDt){
 				 $.messager.alert('操作提示', "授信结束日期大于计划放款日期", 'info');
 				 $('#planRepayLoanDt').datebox('setValue',"");// 还款日期
 				 $('#repayCycle').textbox('setValue',"");// 还款日期
 			 }
 		 }
 	}
 
	 
		$(function(){
			//取父类的项目id
			//oldPprojectId action
			//alert();
			
			//	$("#investigationForm input[id='old_Project_Id']").val(p);
			 //判断是不是审批官
			
			if( workflowTaskDefKey=="task_CreditWithdrawalRequest"|| workflowTaskDefKey=="task_ApprovalOfficerReview"){
				//可以修改授信日期
				$('#credtiEndDt').attr('readonly',false);
			}else{
				$('#investigationForm .easyui-linkbutton ,#investigationForm input,#investigationForm textarea').attr('disabled','disabled');
				$('#investigationForm .easyui-linkbutton ,#investigationForm input,#investigationForm textarea').attr('readOnly','readOnly');
				$('#investigationForm .easyui-linkbutton ,#investigationForm input,#investigationForm textarea').addClass('l-btn-disabled');

				$('#tab2 .easyui-linkbutton ,#tab2 input,#tab2 textarea').attr('disabled','disabled');
				$('#tab2 .easyui-linkbutton ,#tab2 input,#tab2 textarea').attr('readOnly','readOnly');
				$('#tab2 .easyui-linkbutton ,#tab2 input,#tab2 textarea').addClass('l-btn-disabled');
				$('#credtiEndDt').attr('readonly',true);
			}
			//动态获取授信项目的可用额度 
			$('#aAmts').val($('#clientAmtSpan').attr('amtInfo'));
			applyCommon.init();
			//------------------------
			creditsCommon.init(projectId,newProjectId,projectName,biaoZhi);
			
		}); // end  $(document).ready
		
		
		function exportSurveyReport(){
	 		var comAccId = $("#enterpriseForm input[name='acctId']").val();
	 		var perAccId = $("#personalForm input[name='acctId']").val();
	 		
	 		$.ajax({
	 			url:BASE_PATH+'templateFileController/checkFileUrl.action',
	 			data:{templateName:'INVESTI'},
	 			dataType:'json',
	 			success:function(data){
	 				if(data==1){				
	 					if($("input[name='cusType']:checked").val()==1){
	 			 			$('#percomGrid').datagrid({
	 			 				url:'${basePath}customerController/listUnderComUrl.action?acctId='+perAccId
	 			 			})
	 			 			$('#percomDialog').dialog('open');
	 			 		}else if($("input[name='cusType']:checked").val()==2){
	 			 			window.location.href="${basePath}secondBeforeLoanController/exportSurveyReport.action?projectId="+newProjectId+"&comId="+comAccId; 
	 			 		}
	 				}else{
	 					alert('尽职调查导出模板不存在，请上传模板后重试');
	 				}
	 			}
	 		})
	 		
	 	}
	 	
	 	
	 	function selectComToExport(){
	 		var rows = $('#percomGrid').datagrid('getChecked');
	 		if(rows.length==0){
	 			alert('请选择旗下公司');
	 		}else if(rows.length==1){
	 			$.ajax({
	 				url:'${basePath}secondBeforeLoanController/getAcctIdByComId.action',
	 				type:'post',
	 				data:{comId:rows[0].pid},
	 				dataType:'json',
	 				success:function(data){
	 					if(data==0){
	 						alert('查询不到客户ID');
	 					}else{
				 			window.location.href="${basePath}secondBeforeLoanController/exportSurveyReport.action?projectId="+newProjectId+"&comId="+data; 
	 					}
	 				}
	 			})
	 		}else{
	 			alert('只能请选择一个旗下公司进行导出');
	 		}
	 	}
	 	
	 	function UnselectComToExport(){
	 		window.location.href="${basePath}secondBeforeLoanController/exportSurveyReport.action?projectId="+newProjectId+"&comId=0"; 
	 	}
 	</script>
</body>