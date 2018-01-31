<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
<script>
function selectfirst() {$(this).combobox('select', $(this).combobox('getData')[0].pid);}
</script>
	<div style="width:auto;padding-top:30px;">
		<form action="<%=basePath%>beforeLoanController/addInformation.action" id="investigationForm"  name="investigationForm" method="post" >
		<input type="hidden" name="pid" id="project_id">
		<input type="hidden" name="loanId" value="-1" />
		<input type="hidden" name="creditId" />
		<input type="hidden" name="projectType" />
		<input type="hidden" name="assWay" />
		<input type="hidden" name="acctId" />
		<input type="hidden" name="assWayText" />
		<input type="hidden" name="judgeRepayCycle" value="-1" /> 
		
		<div class="fitem" style="padding-left: 25px;" >
		
		<table class="control_dis">
			<tr>
				<td colspan="2" align="center">
					<input type="checkbox" id="projectTypeDksq" align="center" /><div style="font-size: 20px; color:#2828FF;display: inline-block;">贷款申请</div>
					<input type="checkbox" id="projectTypeEdsq" /><div style="font-size: 20px; color:#2828FF;display: inline-block;">额度申请</div>
				</td>
			</tr>
			<tr>
				<td align="right" width="80" height="28">项目名称：</td>
				<td><input name="projectName" id="projectName" type="text" style="width: 500px;color: red;" class="text_style" > </td>
			</tr>
			<tr>
				<td align="right" width="80" height="28">项目编号：</td>
				<td ><input name="projectNumber" id="projectNumber" type="text" style="width: 500px;color: red;" class="text_style" readonly="readonly" > </td>
			</tr>
		</table>
		
		</div>
		<div  style="padding: 10px 10px 0 10px;">
		<div class=" easyui-panel" title="项目基本信息设置" data-options="collapsible:true">
		<table class="cus_table1 control_dis">			
			<tr>
				<td>
		
		<div id="projectBasicNews" style="padding-left: 10px;padding-top:10px;height: auto;line-height: 35px;" >
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>业务类别：</td>
					<td>
						<input name="businessCatelog" id="businessCatelog" class="easyui-combobox" editable="false" panelHeight="auto"  required="true" 
	           			 data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_BUSINESS_CATELOG'" />
					</td>
					<td class="label_right"><font color="red">*</font>我方类型：</td>
					<td>
						<input name="myType" id="myType" class="easyui-combobox" editable="false" panelHeight="auto"  required="true"
	           			 data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_MY_TYPE'" />
					</td>
					<td class="label_right"><font color="red">*</font>我方主体：</td>
					<td>
						<input name="myMain" id="myMain" class="easyui-combobox" style="width: 220px;" editable="false" panelHeight="auto"  required="true"
	            		data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_MY_MAIN'" />
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>业务品种：</td>
					<td>
						 <input name="businessType" id="businessType" class="easyui-combobox" editable="false" panelHeight="auto"  required="true"
	           			 data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_BUSINESS_TYPE'" />
					</td>
					<td class="label_right"><font color="red">*</font>流程类别：</td>
					<td>
						 <input name="flowCatelog" id="flowCatelog" class="easyui-combobox" editable="false" panelHeight="auto"  required="true"
	            		data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_FLOW_CATELOG'" />
					</td>
					<td class="label_right"><font color="red">*</font>项目经理：</td>
					<td>
						 <!-- <input name="realName" id="realName" class="easyui-textbox" style="border:0;background: white;" disabled="true"/> -->
						 
						 <input name="realName" id="realName" class="easyui-combobox" editable="false" required="true"
						 data-options="url:'${basePath}customerController/getCusManagerName.action?projectId='+projectId,valueField:'pids',textField:'userName'" />
					</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>贷款利息收取单位：</td>
					<td colspan="5">
					  	<input name="loanInterestRecord" id="loanInterestRecord" class="easyui-combobox" editable="false" panelHeight="auto" style="width: 226px;" required="true"
				            data-options="valueField:'pid',textField:'chargeName',onLoadSuccess: selectfirst,url:'<%=basePath%>secondBeforeLoanController/getFinanceAcctManage.action',onSelect:getAccountByPrimaryKeyAccrual" />
			            <input name="loanInterestRecordNo" id="loanInterestRecordNo"  type="text" class="text_style" style="width: 300px;" disabled="true" />
					</td>
				</tr>
				<tr>
					<td class="label_right2"><font color="red">*</font>贷款管理费收取单位：</td>
					<td colspan="5">
						<input name="loanMgrRecord" id="loanMgrRecord" class="easyui-combobox" editable="false" panelHeight="auto"  style="width: 226px;" required="true"
				            data-options="valueField:'pid',textField:'chargeName',onLoadSuccess: selectfirst,url:'<%=basePath%>secondBeforeLoanController/getFinanceAcctManage.action',onSelect:getAccountByPrimaryKeyFees" />
			            <input name="loanMgrRecordNo" id="loanMgrRecordNo" type="text" class="text_style" style="width: 300px;" disabled="true" />
					</td>
				</tr> 
				<tr>
					<td class="label_right1"><font color="red">*</font>其它费用收取单位：</td>
					<td colspan="5">
						<input name="loanOtherFee" id="loanOtherFee" class="easyui-combobox" editable="false" panelHeight="auto"  style="width: 226px;" required="true"
				            data-options="valueField:'pid',textField:'chargeName',onLoadSuccess: selectfirst,url:'<%=basePath%>secondBeforeLoanController/getFinanceAcctManage.action',onSelect:getAccountByPrimaryKeyOther" />
			            <input name="loanOtherFeeNo" id="loanOtherFeeNo" type="text" class="text_style" style="width: 300px;" disabled="true" />
					</td>
				</tr>
			</table>
			
		</div>
			</td>
			</tr>
		</table>
		</div>
		</div>
		<div class="control_disbase"  style="padding: 10px 10px 0 10px;">
		<div class=" easyui-panel" title="贷款基本信息" data-options="collapsible:true">
		<table class="cus_table">			
			<tr>
				<td>
		
		<div id="loanBasicNews" style="padding-left: 10px;padding-top:10px;height: auto;" >
			<table class="beforeloanTable">
				<tr>
					<td class="label_right"><font color="red">*</font>授信额度：</td>
					<td><input name="creditAmt" class="easyui-numberbox" precision="2" groupSeparator="," required="true" missingMessage="请输入授信额度!"  /></td>
					<td class="label_right"><font color="red">*</font>授信开始日期：</td>
					<td><input name="credtiStartDt" id="credtiStartDt" class="easyui-datebox"   data-options="onSelect:setValueStaus"  editable="false" required="true" missingMessage="请输入授信开始日期!"  /></td>
					<td class="label_right"><font color="red">*</font>授信结束日期：</td>
					<td><input name="credtiEndDt" id="credtiEndDt"   class="easyui-datebox"  data-options="onSelect:setValueStaus"  validType="checkDateRange['#credtiStartDt']" editable="false"   required="true" missingMessage="请输入授信结束日期!" /></td>
				</tr>
				<tr><!-- data-options="onSelect:checkInfo"  -->
					<td class="label_right"><font color="red">*</font>是否循环：</td>
					<td>
						<input name="isHoop" type="radio" value="1" onclick="isCheckHoop()" />可循环
	           			<input name="isHoop" type="radio" checked="checked" value="2" onclick="isCheckHoop()"  />不可循环
					</td>
					<td class="label_right"></td>
					<td>
						<div class="selectlist1" style="display: none;" >
						 <input name="circulateType"   id="circulateType" style="width: 146px;"  class="easyui-combobox" editable="false" panelHeight="auto"  
	           			 data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_IS_HOOP'" />
					
						</div>
					</td>
					<td class="label_right"><font color="red">*</font>担保方式：</td>
					<td>						
			            <input id="assWay" class="easyui-combobox" editable="false" required="true"  panelHeight="auto" 
			            data-options="valueField:'pid',textField:'lookupDesc',multiple:true,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=ASS_WAY',onUnselect:unSelectMortgageType,onSelect:selectMortgageType" />
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>贷款金额：</td>
					<td>
						<input name="loanAmt"  id="loanAmtId"  class="easyui-numberbox" precision="2" groupSeparator="," required="true" missingMessage="请输入贷款金额!"  />
					</td>
					<td class="label_right"><font color="red">*</font>币种：</td>
					<td>
						<input name="currency"  id="currency" class="easyui-combobox" editable="false" required="true"  panelHeight="auto"  
						data-options="valueField:'pid',textField:'lookupDesc',formatter:check,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_CURRENCY'" />
					</td>
					<td class="label_right"><font color="red">*</font>日期模式：</td>
					<td>
						<input name="dateMode" id="dateMode"  class="easyui-combobox" editable="false" required="true"  panelHeight="auto"  
						data-options="valueField:'pid',textField:'lookupDesc',formatter:check,onLoadSuccess: selectfirst,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_DATE_MODE'" />
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
						data-options="valueField:'pid',textField:'lookupDesc',formatter:check,onLoadSuccess: selectfirst,url:'<%=basePath%>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=LOAN_REPAY_CYCLE_TYPE'" />
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
									<input name="overdueLoanInterest" class="easyui-numberbox" precision="4" required="true" style="width: 60px;" missingMessage="请输入逾期贷款利率!" />%日
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
								<td class="label_right">提前还款金额的</td>
								<td><input name="prepayLiqDmgProportion" class="easyui-numberbox" required="true"  precision="4"  style="width: 60px;" />%</td>
								<td class="label_right1">贷款手续费：</td>
								<td class="label_right">贷款总额的</td>
								<td><input name="feesProportion" class="easyui-numberbox" required="true"  precision="4"  style="width: 60px;" />%</td>
								
							</tr>
						</table>
					</td>
					
				</tr>
				<tr>
					<td colspan="6" align="center" height="35">
						<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveInvestigation()">保存基本信息</a>
						<a href="#" class="easyui-linkbutton" style="" iconCls="icon-calculation" onclick="loancalc()">贷款试算</a>
		
					</td>
				</tr>
			</table>
		</div>
		</td>
		</tr>
		</table>
		</div>
		</div> 
		
		<div class="control_disbase" style="padding: 10px 10px 0 10px;">
			<div class=" easyui-panel" title="担保措施" data-options="collapsible:true">
				<div id="guaranteesMeasures" style="padding: 10px;height: auto;line-height: 35px;width: auto;" >
					<%@ include file="add_mortgage.jsp" %>
					
				</div>
			</div>
		</div>
		</form>
		<%-- 尽职报告调查 --%>
		<div class="control_disbase" style="padding: 10px 10px 0 10px;">
		<div class=" easyui-panel" title="尽职报告调查" data-options="collapsible:true">	
		
		<div id="dueDiligence" style="padding-left: 10px;padding-top:10px;height: auto;line-height: 35px;" >
			<form action="<%=basePath%>secondBeforeLoanController/addSurveyReport.action" id="surveyReport" name="surveyReport" method="post" >
			<input type="hidden" name="pid" id="pid" value="0">
			<input type="hidden" name="projectId" >
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font><span id="introductionId">个人简介：</span></label>
             	<input name="introduction" id="introduction" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font><span id="projectSource">个人主要财务状况：</span></label>
             	<input name="projectSource" id="projectSource" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>项目来源：</label>
             	<input name="financialStatus" id="financialStatus" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>贷款用途：</label>
             	<input name="loanPurposes" id="loanPurposes" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>还款能力：</label>
             	<input name="repayAnalysis" id="repayAnalysis" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>风险提示：</label>
             	<input name="riskWarning" id="riskWarning" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>
			
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>保证措施：</label>
             	<input name="assuranceMeasures" id="assuranceMeasures" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'">
			</div>

			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><font color="red">*</font>调查结论：</label>
             	<input name="surveyResults" id="surveyResults" class="easyui-textbox" style="width:65%;height:60px" required="ture" data-options="multiline:true,validType:'length[1,800]'" >
			</div>

			<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" class="easyui-linkbutton" style="margin-left: 249px;" iconCls="icon-save" onclick="saveSurveyReport()">保存调查报告</a>
				<a href="#" class="easyui-linkbutton download" style="margin-left: 249px;" iconCls="icon-save" onclick="exportSurveyReport()">导出</a>
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
	
	<%-- end 法人保证信息 --%>
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
			//担保措施 省市信息初始化
			applyCommon.initAddress();
			
			bingRepayFunOnSel();
			
			//  初始化表单数据
			// ajaxSearch('#personalGuarantee_grid','#searchFromPersonalGuarantee');

			//给月利率绑定鼠标离开事件 
			$('body').delegate('.monthLoanOtherFee > span','blur',function(){
				var v2= $(this).find(' > input').val();//获取到当前的值 
				var t=0;
				$('.monthLoanOtherFee > span').each(function(){//循环三个月利率 把值叠加起来 算出合计利率 
					t+= Number($(this).find(' > input').val());
				});
				
				$("#total").numberbox('setValue', parseFloat(t) ); //合计利率 
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
				$("#total").numberbox('setValue', parseFloat(t) ); //合计利率 
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
				$("#total").numberbox('setValue', parseFloat(t) ); //合计利率 
				$(this).parent().parent().find('.monthLoanOtherFee .easyui-numberbox').numberbox('setValue',v2*30.0);//年化利率
				$(this).parent().parent().find('.yearLoanOtherFee .easyui-numberbox').numberbox('setValue',v2*360.0);//日化利率
			})
			
			//初始化给还款期限绑定 blur事件 
			$('body').delegate('.repayCycleTd > span','blur',function(){
				//debugger;
				var   v =  $(this).find(' > input').val();//获取还款期限
				var outloanDt = $('#planOutLoanDt').datebox('getValue');//放款开始时间
				var repayloanDt = $('#planRepayLoanDt').datebox('getValue');//放款结束时间
				var StartDt = $('#credtiStartDt').datebox('getValue');//授信开始时间
				var EndDt = $('#credtiEndDt').datebox('getValue');//授信开始结束
				var testInfo = $('#repayFun').combobox('getText');//还款方式 
				if(testInfo=="等额本金"|| testInfo=="按月-先息后本" || testInfo=="等额本息" || testInfo=="等本等息"  ){
					if(StartDt!=""&&EndDt!=""){
						 if(v!="" && v!=0){
							 if(outloanDt!=""){
								var tx =  addMoth(outloanDt,Number(v)); 
								var compare  = outloanDt.substr(8); 
								var compare1 = dateInfo(tx).format('yyyy-MM-dd').substr(8);
								if(Number(compare)==Number(compare1)){
									 var newDate = DateAdd("-d", 1, dateInfo(tx)); // 在减一天
									tx =  newDate.format('yyyy-MM-dd');// 转换格式 赋值 */
								}
								 if(dateInfo(tx)>= dateInfo(StartDt) &&  dateInfo(tx)<=dateInfo(EndDt)){
									 $('#planRepayLoanDt').datebox('setValue',tx);
								 }else{
									 alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
									 $('#planOutLoanDt').datebox('setValue',"");
									 $('#planRepayLoanDt').datebox('setValue',"");
									 return ;
								 }
								 return;
							 }else  if(repayloanDt!=""){
								 var newMoth =  addMoth(repayloanDt,-v); 
								 if(dateInfo(newMoth) >= dateInfo(StartDt) && dateInfo(newMoth) <= dateInfo(EndDt)){
									 $('#planOutLoanDt').datebox('setValue',newMoth);
								 }else{
									 alert("通过还款期限与放款结束时间得出的放款开始时间不在授信时间范围内");
									 $('#planOutLoanDt').datebox('setValue',"");
									 $('#planRepayLoanDt').datebox('setValue',"");
									 return ;
								 }
								 return;
							 } 
						}else{
							return ;
						}
					}
				}else {//按天去计算
					Date.prototype.diff = function(date){
						  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
					}
					if(testInfo!="其他还款方式"){
						var str = 0;
						if(testInfo=="按日-5天收息，到期还本") {
							str = 5;
						}  
						if(testInfo=="按日-7天收息，到期还本" ) {
							str = 7;
						} 
						if(testInfo=="按日-10天收息，到期还本") {
							str = 10;
						} 
						if(testInfo=="按日-15天收息，到期还本") {
							str = 15;
						}
						if(v!=""){
							if(outloanDt!=""){
								 var newDate   =   DateAdd( "d",Number(v*str-1),dateInfo(outloanDt));  
								 var dates = newDate.format('yyyy-MM-dd');
								 if(dates>=StartDt && dates<=EndDt){
									 $('#planRepayLoanDt').datebox('setValue',dates);
								 }else{
									 alert("计划还款日期不在授信日期范围内");
									 $('#planRepayLoanDt').datebox('setValue',"");
								 }
								
							}else if(repayloanDt!=""){
								 var newDate   =   DateAdd( "-d",Number(v*str+1),dateInfo(repayloanDt));  
								 var dates = newDate.format('yyyy-MM-dd');
								 if(dates>=StartDt && dates<=EndDt){
									 $('#planOutLoanDt').datebox('setValue',str);
								 }else{
									 alert("计划开始日期不在授信日期范围内");
									 $('#planOutLoanDt').datebox('setValue',"");
								 }
							}
						}
					}
				}
			});
			
			//------------------------
	 		if(projectName){
	 			
				$('#investigationForm .easyui-linkbutton:not(.download) ,#investigationForm input,#investigationForm textarea').attr('disabled','disabled');
				$('#investigationForm .easyui-linkbutton:not(.download) ,#investigationForm input,#investigationForm textarea').attr('readOnly','readOnly');
				$('#investigationForm .easyui-linkbutton:not(.download) ,#investigationForm input,#investigationForm textarea').addClass('l-btn-disabled');

				$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
				$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
				$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
		 	}
			
	 		$.extend($.fn.validatebox.defaults.rules, {
				checkDateRange: {
					validator: function(value,param){
						return value > $(param[0]).datebox('getValue');
					},
					message: '结束时间必须大于开始时间.'
				}
			});
	 		var mcusType=$("input[name='cusType']:checked").val();
	 		if(mcusType==1){   
	 			$("#introductionId").html("个人简介：");
	 			$("#projectSource").html("个人主要财务状况：");
	 		}
	 		if(mcusType==2){
	 			$("#introductionId").html("企业简介：");
	 			$("#projectSource").html("企业经营及财务状况：");
	 		}
	 		// 判断是什么操作
			var url = "<%=basePath%>beforeLoanController/getLoanProjectByProjectId.action?projectId="+projectId;
			if(bianzhi == -1){
				url = "<%=basePath%>beforeLoanController/getProjectNameOrNumber.action?projectId="+projectId;
			}
			$.post(url,
			  	function(data){
			  		if(bianzhi == -1){
			  			$("#projectName").val(data.projectName);
			  			$("#projectNumber").val(data.projectNumber);
		  				$("#realName").combobox('select',data.pmUserId);
						// 显示 or 隐藏抵质押物担保
						setTimeout("InitializationMortgage()",1);
			  		}else{
			  			if(data.eachissueOption==2){
			  				$("#repayDate").numberbox("readonly"); 
			  			}
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
						
						//等额本息、等本等息、其他还款方式 禁用前置付息、一次性付息复选框
						if(data.repayFun == 13317 || data.repayFun == 13318){
							$("#repayOption").attr("checked",false);
							$("#repayOption").attr("disabled",'disabled');
							$("#repayOptionTest").attr("checked",false);
							$("#repayOptionTest").attr("disabled",'disabled');
						}
						
						$("#realName").combobox('select',data.pmUserId);
						// 赋值还款期限
						$("#hkqx").val(data.repayCycle);
						// 复选框    贷款申请 and 额度申请 
						if(data.projectType == 2){
							$("#projectTypeDksq").attr("checked",true);
						}else if(data.projectType == 1){
							$("#projectTypeEdsq").attr("checked",true);
						}else if(data.projectType == 5){
							$("#projectTypeDksq").attr("checked",true);
							$("#projectTypeEdsq").attr("checked",true);
						}
						if(loanInterestRecord!=""){
				 		 $.ajax({
								type: "POST",
						        data:{"pid":loanInterestRecord},
						    	url : '${basePath}financeController/editFinanceAcctManageUrl.action',
								dataType: "json",
							    success: function(data){
							    	var str = eval(data);
							    	$("#loanInterestRecordNo").val(str.bankNum);
								}
							});  
						}
						if(loanMgrRecord!=""){
				 		 $.ajax({
								type: "POST",
						        data:{"pid":loanMgrRecord},
						    	url : '${basePath}financeController/editFinanceAcctManageUrl.action',
								dataType: "json",
							    success: function(data){
							    	var str = eval(data);
							    	$("#loanMgrRecordNo").val(str.bankNum);
								}
							}); 
						}
						if(loanOtherFee!=""){
				 		 $.ajax({
								type: "POST",
						        data:{"pid":loanOtherFee},
						    	url : '${basePath}financeController/editFinanceAcctManageUrl.action',
								dataType: "json",
							    success: function(data){
							    	var str = eval(data);
							    	$("#loanOtherFeeNo").val(str.bankNum);
								}
							}); 
						}
				 		// 动态绑定担保方式 
				 		$.ajax({
								type: "POST",
						        data:{"projectId":projectId},
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
						// 尽职调查报告URL			  			
						var urlSr = "<%=basePath%>secondBeforeLoanController/getSurveyReportByProjectId.action?projectId="+projectId;
						// 加载尽职调查报告的数据
						$.post(urlSr,
							function(ret){
							if(ret.pid){
								$("#surveyReport").form('load',ret);
								surPid = ret.pid;
							}
							}, "json"); 
						// 如果下拉框,数字框，文本框的值是0 默认不显示 zhengxiu
						$('.easyui-combobox').each(function(i){
							var cValue=$(this).combobox('getValue');
							if(cValue=='0' ||cValue=='0.0'||cValue=='0.00'||cValue=='0.0000'){
								$(this).combobox('setValue','');
							}
						});
						$(".easyui-textbox").each(function(i) {
							var v=$(this).textbox('getValue');
						    if( v=='0' ||v=='0.0'||v=='0.00'||v=='0.0000'){
						    	$(this).textbox('setValue','');
						    };       
						}); 
					}
			  	}, "json");
			
			//流程控制
			if("task_LoanRequest"==workflowTaskDefKey){
				
			}else if("task_ApprovalOfficerReview"==workflowTaskDefKey || "task_OfflineMeetingResult"==workflowTaskDefKey){ 
				//审批官审查  线下待审会会议
				$('.control_dis .easyui-combobox').attr('readOnly','readOnly');
				$('.control_dis input[type="checkbox"]').attr('readOnly','readOnly');
				$('.control_dis .easyui-textbox').attr('readOnly','readOnly');
				$('.control_dis input').attr('readOnly','readOnly');
			}else{
				//风控总监审查  组织召开贷审会 办理抵押登记
				$('.control_dis .easyui-combobox').attr('disabled','disabled');
				$('.control_dis input[type="checkbox"]').attr('disabled','disabled');
				$('.control_dis .easyui-textbox').attr('disabled','disabled');
				$('.control_dis input:not(.download)').attr('readOnly','readOnly');
				$('.control_disbase .easyui-linkbutton:not(.download) ,.control_disbase input,.control_disbase textarea').attr('disabled','disabled');
				$('.control_disbase .easyui-linkbutton:not(.download) ,.control_disbase input,.control_disbase textarea').attr('readOnly','readOnly');
				$('.control_disbase .easyui-linkbutton:not(.download) ,.control_disbase input,.control_disbase textarea').addClass('l-btn-disabled');
			}
		
		});
	 	
	 	$('#repayCycleDate').numberbox({  
		 		disabled:true  
		 }); 
	 	function mdisabled(){
	 		$("input[name='dateMode']").combobox({
		 		disabled:true  
		 	});
		 	$("input[name='repayCycleType']").combobox({
		 		disabled:true 
		 	});
	 	}
	 	$("input[name='repayCycleType']").combobox({
			onSelect: function(row){
				var opts = $(this).combobox('options');
				if(row[opts.textField]=="自定义")
				{
					$("#repayCycleDate").numberbox("enable");
				}else{
					$("#repayCycleDate").numberbox("disable");
				}		 
			}
		});
	 	
	 	// 保存基本信息
	 	function saveInvestigation(){
	 		
	 		// 判断项目名称
	 		if($("#projectName").val()==null || $("#projectName").val()==""){
	 			$.messager.alert('操作提示',"项目名称不允许为空!",'error');	
	 			return;
	 		}
	 		
	 		// 判断是否选取  贷款利息收取单位
	 		if($("#investigationForm input[name='loanInterestRecordNo']").val() == ""){
	 			$.messager.alert('操作提示',"请选择贷款利息收取单位!",'error');
	 			return;
	 		}
	 		// 判断是否选取  贷款管理费收取单位
			if($("#investigationForm input[name='loanMgrRecordNo']").val() == ""){
				$.messager.alert('操作提示',"请选择贷款管理费收取单位!",'error');
	 			return;
	 		}
	 		// 判断是否选取  其它费用收取单位
			if($("#investigationForm input[name='loanOtherFeeNo']").val() == ""){
				$.messager.alert('操作提示',"请选择其它费用收取单位!",'error');
	 			return;
			}
	 		// 判断是否选择 是否循环
	 		if(!$('input:radio[name="isHoop"]:checked').val()){
	 			$.messager.alert('操作提示',"请选择是否循环!",'error');
		 		return;
	 		}
	 		// 获取所选担保方式
	 		var assWay = $("#assWay").combobox("getValues");
	 		// 判断是否选择担保方式
	 		if( assWay == "" ){
	 			$.messager.alert('操作提示',"请选择担保方式!",'error');	
	 			return;
	 		}
	 		// 赋值担保方式
	 		$("#investigationForm input[name='assWay']").val(assWay);
	 		// 获取授信额度和贷款金额
	 		var loanAmt = $("#investigationForm input[name='loanAmt']").val();
	 		var creditAmt = $("#investigationForm input[name='creditAmt']").val();
	 		//获取还款期限
	 		var repayCycle = $("#investigationForm input[name='repayCycle']").val();
	 		//授信额度录入：限制最高5000万
	 	   if(creditAmt > 50000000){
	 		   $.messager.alert('操作提示',"授信额度不能大于5000万!",'error');	
	 	    	return;
	 	   }
 		   //贷款金额：限制最高5000万
	 	   if(loanAmt > 10000000){
	 		   $.messager.alert('操作提示',"贷款金额不能大于1000万!",'error');	
	 	    	return;
	 	   }
	 		//还款期限：最高36期
	 	   if(repayCycle > 36){
	 		   $.messager.alert('操作提示',"还款期限不能大于36期!",'error');	
	 	    	return;
	 	   }
	 		// 转换数字类型
	 		loanAmt = parseFloat(loanAmt);
	 		creditAmt = parseFloat(creditAmt);
	 		
	 		// 判断贷款金额是否录入正确
	 		if(loanAmt > creditAmt){
	 			$.messager.alert('操作提示',"贷款金额不允许超过授信额度!",'error');	
	 			return;
	 		}
	 		// 判断当前项目类型
	 		if($("#projectTypeDksq:checked").length== 1 && $("#projectTypeEdsq:checked").length== 1){
	 			$("#investigationForm input[name='projectType']").val(5);
	 		}else if($("#projectTypeDksq:checked").length== 1 && $("#projectTypeEdsq:checked").length== 0){
	 			$("#investigationForm input[name='projectType']").val(2);
	 		}else if($("#projectTypeDksq:checked").length== 0 && $("#projectTypeEdsq:checked").length== 1){
	 			$("#investigationForm input[name='projectType']").val(1);
	 		}else{
	 			$.messager.alert('操作提示',"请选择项目类型为贷款还是额度申请!",'error');
	 			return;
	 		}
	 		////debugger;
	 		// 转换数字类型
	 		loanAmt = parseFloat(loanAmt);
	 		creditAmt = parseFloat(creditAmt);
	 		// 判断贷款金额是否录入正确
	 		if(loanAmt > creditAmt){
	 			$.messager.alert('操作提示',"贷款金额不允许超过授信额度!",'error');	
	 			return;
	 		}
	 		
	 		
			
			
	 		// 验证担保方式
			if(!applyCommon.validateAssWay()){
				return ;
			}
	 		// 赋值贷款ID
	 		$("#investigationForm input[name='loanId']").val(loanId);
	 		// 赋值授信ID
	 		$("#investigationForm input[name='creditId']").val(creditId);
	 		// 赋值项目ID
	 		$("#investigationForm input[name='pid']").val(projectId);
	 		// 赋值客户类型
	 		$("#investigationForm input[name='acctId']").val(acct_id);
	 		// 判断还款期限是否发生改变
	 		if($("#hkqx").val() != $("#investigationForm input[name='repayCycle']").val()){
	 			$("#investigationForm input[name='judgeRepayCycle']").val(1);
	 		}else{
	 			$("#investigationForm input[name='judgeRepayCycle']").val(-1);
	 		}
	 		// 验证其他还款的确认方法
	 		if($('#repayFun').combobox('getText') == "其他还款方式"){
	 			$.messager.confirm("操作提示","在其他还款方式下，会清空原先录入的还款计划表数据，请确认是否继续保存？",
 					function(r) {
 						if(r){
 							// 判断抵质押物存在数据,但是项目担保方式没有选择此类型
 							checkMortgageType($("#assWay").combobox("getText"));
 						}
 				});
	 		}else{
	 			// 判断抵质押物存在数据,但是项目担保方式没有选择此类型
				checkMortgageType($("#assWay").combobox("getText"));
	 		}
	 	}

	 	// 保存尽职调查报告
	 	function saveSurveyReport(){
	 		// 赋值项目ID--尽职调查报告
	 		$("#surveyReport input[name='projectId']").val(projectId);		
	 		url = "";
	 		if($("#surveyReport input[name='pid']").val() == 0){
	 			url = "<%=basePath%>secondBeforeLoanController/addSurveyReport.action";
	 		}else{
	 			url = "<%=basePath%>secondBeforeLoanController/updateSurveyReport.action";
	 		}
	 		// 提交表单
	 		$("#surveyReport").form('submit', {
	 			url : url,
	 			onSubmit : function() {return $(this).form('validate');},
	 			success : function(result) {
	 				var ret = eval("("+result+")");
	 				if(ret && ret.header["success"]){
	 					bianzhi = 1;// 标志为是流程
	 					// 判断当前是什么操作
	 					if($("#surveyReport input[name='pid']").val() == 0){
	 						$.messager.alert('操作提示',"保存成功!",'info');
	 						surPid = ret.header["msg"];
	 						$("#surveyReport input[name='pid']").val(surPid);
	 					}else{
	 						$.messager.alert('操作提示',"修改成功!",'info');
	 					}
	 				}else{
	 					$.messager.alert('操作提示',ret.header["msg"],'error');	
	 				}
	 			}
	 		});
	 	}
	 	
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
	 			 			window.location.href="${basePath}secondBeforeLoanController/exportSurveyReport.action?projectId="+projectId+"&comId="+comAccId; 
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
				 			window.location.href="${basePath}secondBeforeLoanController/exportSurveyReport.action?projectId="+projectId+"&comId="+data; 
	 					}
	 				}
	 			})
	 		}else{
	 			alert('只能请选择一个旗下公司进行导出');
	 		}
	 	}
	 	
	 	function UnselectComToExport(){
	 		window.location.href="${basePath}secondBeforeLoanController/exportSurveyReport.action?projectId="+projectId+"&comId=0"; 
	 	}
	 	
	 	//初始化默认选择 
	 	function check(row) {
	 		if ($("#businessCatelog").combobox('getValue') == "" || $("#businessCatelog").combobox('getValue') == 0) {
	 			if (row.lookupDesc == "小额贷款") {
	 				row.selected = true;
	 			}
	 		}
	 		if ($("#myType").combobox('getValue') == "" || $("#myType").combobox('getValue') == 0) {
	 			if (row.lookupDesc == "企业") {
	 				row.selected = true;
	 			}
	 		}

	 		if ($("#myMain").combobox('getValue') == "" || $("#myMain").combobox('getValue') == 0) {
	 			if (row.lookupDesc == "深圳市信利康小额贷款有限公司") {
	 				row.selected = true;
	 			}
	 		}
	 		if ($("#businessType").combobox('getValue') == "" || $("#businessType").combobox('getValue') == 0) {
	 			if (row.lookupDesc == "小额贷款业务") {
	 				row.selected = true;
	 			}
	 		}
	 		if ($("#flowCatelog").combobox('getValue') == "" || $("#flowCatelog").combobox('getValue') == 0) {
	 			if (row.lookupDesc == "授信流程") {
	 				row.selected = true;
	 			}
	 		}
	 		if ($("#loanInterestRecord").combobox('getValue') == "" || $("#loanInterestRecord").combobox('getValue') == 0) {
	 			if (row.lookupDesc == "信利康-中行-中心区支行") {
	 				row.selected = true;
	 			}
	 		}
	 		if ($("#currency").combobox('getValue') == "" || $("#currency").combobox('getValue') == 0) {
	 			if (row.lookupDesc == "人民币") {
	 				row.selected = true;
	 			}
	 		}
	 		if ($("#assWay").combobox('getValue') == "" || $("#assWay").combobox('getValue') == 0) {
	 			if (row.lookupDesc == "抵押") {
	 				row.selected = true;
	 			}
	 		}
	 		if ($("#repayFun").combobox('getValue') == "" || $("#repayFun").combobox('getValue') == 0) {
	 			if (row.lookupDesc == "按月-先息后本") {
	 				row.selected = true;
	 			}
	 		}
	 		return row.lookupDesc;
	 	}
	 	
	 	function bingRepayFunOnSel(){
	 		//给还款方式绑定onSelect事件 
	 		$("input[name='repayFun']").combobox({
				onSelect: function(row){
					var opts = $(this).combobox('options');
					var outloanDt = $('#planOutLoanDt').datebox('getValue');
					var str =  $('#planOutLoanDt').datebox('getValue');
					var repayloanDt = $('#planRepayLoanDt').datebox('getValue');
					var str2 = $('#planRepayLoanDt').datebox('getValue');
					if(row[opts.textField] == "等额本息" || row[opts.textField] == "等本等息"){
						$("#repayOption").attr("checked",false);
						$("#repayOption").attr("disabled",'disabled');
						$("#repayOptionTest").attr("checked",false);
						$("#repayOptionTest").attr("disabled",'disabled');
					}else {
						$("#repayOption").removeAttr("disabled");
						$("#repayOptionTest").removeAttr("disabled");
					}
					if(row[opts.textField]=="按月-先息后本"|| row[opts.textField]=="等额本金" || row[opts.textField]=="等额本息"||row[opts.textField]=="等本等息" || row[opts.textField]=="其他还款方式") { 
						if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
							checkLoanDate(outloanDt,repayloanDt,str,str2);
							return;
						}else if(outloanDt!="" || repayloanDt!=""){
							repayCycleCheck();
						} 
					} else if(row[opts.textField]=="按日-5天收息，到期还本" ){
						var str = 5; 
						if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
							selectRepayFun(str,outloanDt,repayloanDt);
						}else if(outloanDt!="" || repayloanDt!=""){
							repayCycleCheck();
						} 
					}else if(row[opts.textField]=="按日-7天收息，到期还本"){
						var str = 7;
						if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
							selectRepayFun(str,outloanDt,repayloanDt);
						}else if(outloanDt!="" || repayloanDt!=""){
							repayCycleCheck();
						} 
					}else if(row[opts.textField]=="按日-10天收息，到期还本"){
						var str = 10;
						if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
							selectRepayFun(str,outloanDt,repayloanDt);
						}else if(outloanDt!="" || repayloanDt!=""){
							repayCycleCheck();
						} 
					}else if(row[opts.textField]=="按日-15天收息，到期还本"){
						var str = 15;
						if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
							selectRepayFun(str,outloanDt,repayloanDt);
							return;
						}else if(outloanDt!="" || repayloanDt!=""){
							repayCycleCheck();
						} 
					}		 
				}
			});
	 	}
	 	
	 // 判断抵质押物存在数据,但是项目担保方式没有选择此类型
	 	function checkMortgageType(assWayText){
	 		// 获取抵押列表的所有数据 
	 		var rowsDY = $("#dyxx_grid").datagrid('getRows');
	 		// 获取质押列表的所有数据
	 		var rowsZY = $("#zyxx_grid").datagrid('getRows');
	 		// 获取个人和法人列表的所有数据
	 		var per_rows = $("#perGuarantee_grid").datagrid('getRows');
	 		var cor_rows = $("#corGuarantee_grid").datagrid('getRows');
	 		
	 		// 判断--没有选择抵押类型,是否存在抵押数据
	 		if( rowsDY.length > 0 && assWayText.indexOf("抵押") == -1 ){
	 			$.messager.confirm("操作提示","您填写了抵押数据,但是您没有选择抵押担保方式,如果继续会删除抵押数据,是否继续？",
	 				function(r) {
	 					if(r){
	 						// 判断--没有选择质押类型,是否存在质押数据
	 						if( rowsZY.length > 0 && assWayText.indexOf("质押") == -1 ){
	 							$.messager.confirm("操作提示","您填写了质押数据,但是您没有选择质押担保方式,如果继续会删除质押数据,是否继续？",
	 								function(r) {
	 									if(r){
	 										// 判断--没有选择保证类型,是否存在保证数据
	 										if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
	 											$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
	 												function(r) {
	 													if(r){
	 														preservation();
	 													}else{
	 														return;
	 													}
	 											});
	 										}else{
	 											preservation();
	 										}
	 									}else{
	 										return;
	 									}
	 							});
	 						}else{
	 							// 判断--没有选择保证类型,是否存在保证数据
	 							if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
	 								$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
	 									function(r) {
	 										if(r){
	 											preservation();
	 										}else{
	 											return;
	 										}
	 								});
	 							}else{
	 								preservation();
	 							}
	 						}
	 					}else{
	 						return;
	 					}
	 			});
	 		}else{
	 			// 判断--没有选择质押类型,是否存在质押数据
	 			if( rowsZY.length > 0 && assWayText.indexOf("质押") == -1 ){
	 				$.messager.confirm("操作提示","您填写了质押数据,但是您没有选择质押担保方式,如果继续会删除质押数据,是否继续？",
	 					function(r) {
	 						if(r){
	 							// 判断--没有选择保证类型,是否存在保证数据
	 							if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
	 								$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
	 									function(r) {
	 										if(r){
	 											preservation();
	 										}else{
	 											return;
	 										}
	 								});
	 							}else{
	 								preservation();
	 							}
	 						}else{
	 							return;
	 						}
	 				});
	 			}else{
	 				// 判断--没有选择保证类型,是否存在保证数据
	 				if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
	 					$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
	 						function(r) {
	 							if(r){
	 								preservation();
	 							}else{
	 								return;
	 							}
	 					});
	 				}else{
	 					preservation();
	 				}
	 			}
	 		}
	 		
	 	}
 	</script>
 	