<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<div class="clear pt10"></div>
<!-- 赎楼信息开始 -->
	<div  id="foreclosureInfo">
	<div style="padding: 10px 10px 0 10px;">
		<div class=" easyui-panel" title="赎楼" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<input id="pid" name="pid" type="hidden">
			<input type="hidden" name="projectForeclosure.pid" id="foreclosureId" value="${project.projectForeclosure.pid }">
			<input type="hidden" name="projectForeclosure.projectId" value="${project.projectForeclosure.projectId }">
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>原贷款银行：</td>
					<td>
						<select id="oldLoanBank" class="easyui-combobox" data-options="validType:'selrequired'" name="projectForeclosure.oldLoanBank" editable="false" style="width:129px;" />
					</td>
					<td class="label_right1"><font color="red">*</font>银行联系人：</td>
					<td>
						<input name="projectForeclosure.oldLoanPerson" data-options="required:true,validType:'length[1,50]'" value="${project.projectForeclosure.oldLoanPerson }" class="easyui-textbox" style="width:129px;"/>
					</td>
					<td class="label_right1"><font color="red">*</font>原贷款金额：</td>
					<td>
						<input class="easyui-numberbox" precision="2" groupSeparator="," required="true" min="0" maxLength='12' value="${project.projectForeclosure.oldLoanMoney }" name="projectForeclosure.oldLoanMoney" style="width:129px;"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>联系电话：</td>
					<td>
						 <input name="projectForeclosure.oldLoanPhone" data-options="required:true,validType:'phone'" value="${project.projectForeclosure.oldLoanPhone }"  class="easyui-textbox" style="width:129px;"/>
					</td>
				</tr>
				<tr style="padding-top:10px;">
					<td class="label_right"><font color="red">*</font>新贷款银行：</td>
					<td>
						<select id="newLoanBank" class="easyui-combobox"  data-options="validType:'selrequired'" name="projectForeclosure.newLoanBank" editable="false" style="width:129px;" />
					</td>
					<td class="label_right1"><font color="red">*</font>银行联系人：</td>
					<td>
						<input  class="easyui-textbox" data-options="required:true,validType:'length[1,50]'" name="projectForeclosure.newLoanPerson" value="${project.projectForeclosure.newLoanPerson }" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>新贷款金额：</td>
					<td>
						<input class="easyui-numberbox" precision="2" groupSeparator="," required="true" min="0" maxLength='12' name="projectForeclosure.newLoanMoney" value="${project.projectForeclosure.newLoanMoney }" style="width:129px;"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>联系电话：</td>
					<td>
						 <input name="projectForeclosure.newLoanPhone" data-options="required:true,validType:'phone'" value="${project.projectForeclosure.newLoanPhone }"  class="easyui-textbox" style="width:129px;"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>付款方式：</td>
					<td>
						<select class="select_style easyui-combobox" editable="false" name="projectForeclosure.paymentType" style="width:129px;">
							<option value="-1">--请选择--</option>
							<option value="1" <c:if test="${project.projectForeclosure.paymentType==1 }">selected </c:if>>按揭</option>
							<option value="2" <c:if test="${project.projectForeclosure.paymentType==2 }">selected </c:if>>组合贷</option>
							<option value="3" <c:if test="${project.projectForeclosure.paymentType==3 }">selected </c:if>>公积金贷</option>
							<option value="4" <c:if test="${project.projectForeclosure.paymentType==4 }">selected </c:if>>一次性付款</option>
						</select>
					</td>
					<td class="label_right">公积金银行：</td>
					<td>
						<select id="accumulationFundBank" class="easyui-combobox" name="projectForeclosure.accumulationFundBank" editable="false" style="width:129px;" />
					</td>
					<td class="label_right">公积金贷款金额：</td>
					<td>
						<input class="easyui-numberbox" precision="2" groupSeparator="," min="0" maxLength='12' name="projectForeclosure.accumulationFundMoney" value="${project.projectForeclosure.accumulationFundMoney }" style="width:129px;"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>贷款天数：</td>
					<td>
						 <input name="projectForeclosure.loanDays" value="${project.projectForeclosure.loanDays }" min="1" maxLength='12' class="easyui-numberbox" precision="2" groupSeparator="," style="width:129px;"/>
					</td>
					<td class="label_right">应回款日期：</td>
					<td>
						 <input name="projectForeclosure.paymentDate" value="${project.projectForeclosure.paymentDate }" class="easyui-datebox" editable="false" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>回款户名：</td>
					<td>
						<input  class="easyui-textbox" data-options="required:true,validType:'length[1,50]'" name="projectForeclosure.paymentName" value="${project.projectForeclosure.paymentName }" style="width:129px;"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>回款账号：</td>
					<td>
						<input  class="easyui-textbox" data-options="required:true,validType:'length[0,50]'" name="projectForeclosure.paymentAccount" value="${project.projectForeclosure.paymentAccount }" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>资金监管金额：</td>
					<td>
						<input class="easyui-numberbox" precision="2" id="fundsMoney" groupSeparator="," min="0" maxLength='12' name="projectForeclosure.fundsMoney" value="${project.projectForeclosure.fundsMoney }" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>监管单位：</td>
					<td>
						<input  class="easyui-textbox" data-options="validType:'length[1,50]'" id="superviseDepartment" name="projectForeclosure.superviseDepartment" value="${project.projectForeclosure.superviseDepartment }" style="width:129px;"/>
					</td>

				</tr>
				<tr>
					<td class="label_right">委托公证日期：</td>
					<td>
						 <input name="projectForeclosure.notarizationDate" value="${project.projectForeclosure.notarizationDate }" class="easyui-datebox" editable="false" style="width:129px;"/>
					</td>
					<td class="label_right1">签暑合同日期：</td>
					<td>
						<input class="easyui-datebox" editable="false" name="projectForeclosure.signDate" value="${project.projectForeclosure.signDate }" style="width:129px;"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
		</div>
	
	<div  style="padding: 10px 10px 0 10px;">
		<div class=" easyui-panel" title="物业及买卖双方信息" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<input type="hidden" name="projectProperty.pid" id="propertyId" value="${project.projectProperty.pid }">
			<input type="hidden" name="projectProperty.projectId" value="${project.projectProperty.projectId }">
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>物业名称：</td>
					<td>
						<input name="projectProperty.houseName" data-options="required:true,validType:'length[1,50]'" value="${project.projectProperty.houseName }"  class="easyui-textbox"/>
					</td>
					<td class="label_right1"><font color="red">*</font>面积：</td>
					<td>
						<input name="projectProperty.area" required="true" class="easyui-numberbox" min="0" precision="2" maxLength='12' groupSeparator="," value="${project.projectProperty.area }"/>平方
					</td>
					<td class="label_right"><font color="red">*</font>原价：</td>
					<td>
						<input class="easyui-numberbox" required="true" precision="2" min="0" groupSeparator="," maxLength='12' name="projectProperty.costMoney" value="${project.projectProperty.costMoney }"/>
					</td>
				</tr>
				
				<tr>
					<td class="label_right"><font color="red">*</font>成交价：</td>
					<td>
						 <input name="projectProperty.tranasctionMoney" value="${project.projectProperty.tranasctionMoney }" maxLength='12' min="0" class="easyui-numberbox" precision="2" groupSeparator=","/>
					</td>
					<td class="label_right1"><font color="red">*</font>房产证号：</td>
					<td>
						<input name="projectProperty.housePropertyCard" data-options="required:true,validType:'length[1,50]'" value="${project.projectProperty.housePropertyCard }"  class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>卖方姓名：</td>
					<td>
						 <input name="projectProperty.sellerName" data-options="required:true,validType:'length[1,50]'" value="${project.projectProperty.sellerName }"  class="easyui-textbox"/>
					</td>
					<td class="label_right1"><font color="red">*</font>身份证号：</td>
					<td>
						<input name="projectProperty.sellerCardNo" data-options="required:true,validType:'length[1,50]'" value="${project.projectProperty.sellerCardNo }"  class="easyui-textbox"/>
					</td>
					<td class="label_right"><font color="red">*</font>联系电话：</td>
					<td>
						<input name="projectProperty.sellerPhone" data-options="required:true,validType:'phone'" value="${project.projectProperty.sellerPhone }"  class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>家庭住址：</td>
					<td>
						 <input name="projectProperty.sellerAddress" data-options="required:true,validType:'length[1,50]'" value="${project.projectProperty.sellerAddress }"  class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>买家名称：</td>
					<td>
						 <input name="projectProperty.buyerName" id="buyerName" data-options="validType:'length[0,50]'" value="${project.projectProperty.buyerName }"  class="easyui-textbox"/>
					</td>
					<td class="label_right1"><font color="red">*</font>身份证号：</td>
					<td>
						<input name="projectProperty.buyerCardNo" id="buyerCardNo" data-options="validType:'length[0,50]'" value="${project.projectProperty.buyerCardNo }"  class="easyui-textbox"/>
					</td>
					<td class="label_right1"><font color="red">*</font>联系电话：</td>
					<td>
						<input name="projectProperty.buyerPhone" id="buyerPhone" data-options="validType:'phone'" value="${project.projectProperty.buyerPhone }"  class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>家庭地址：</td>
					<td>
						 <input name="projectProperty.buyerAddress" id="buyerAddress" data-options="validType:'length[0,50]'" value="${project.projectProperty.buyerAddress }"  class="easyui-textbox"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
		</div>
	
		<div  style="padding: 10px 10px 0 10px;">
		<div class=" easyui-panel" title="费用" data-options="collapsible:true" width="1039px;">
		<div style="padding: 10px 0 10px 10px;height: auto;line-height: 35px;" >
			<input type="hidden" name="projectGuarantee.pid" id="guaranteeId" value="${project.projectGuarantee.pid }">
			<input type="hidden" name="projectGuarantee.projectId" value="${project.projectGuarantee.projectId }">
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>借款金额：</td>
					<td>
						<input name="projectGuarantee.loanMoney" required="true" min="0" maxLength='12' value="${project.projectGuarantee.loanMoney }" class="easyui-numberbox" precision="2" groupSeparator=","/>
					</td>
					<td class="label_right1"><font color="red">*</font>咨询费收入：</td>
					<td>
						<input name="projectGuarantee.guaranteeFee" required="true" min="0" maxLength='12' value="${project.projectGuarantee.guaranteeFee }" class="easyui-numberbox" precision="2" groupSeparator=","/>
					</td> 
					<td class="label_right1"><font color="red">*</font>手续费：</td>
					<td>
						<input name="projectGuarantee.poundage" required="true" min="0" maxLength='12' value="${project.projectGuarantee.poundage }" class="easyui-numberbox" precision="2" groupSeparator=","/>
					</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>收费方式：</td>
					<td>
					<select class="select_style easyui-combobox" editable="false" name="projectGuarantee.chargesType" style="width:129px;">
							<option value="-1">--请选择--</option>
							<option value="1" <c:if test="${project.projectGuarantee.chargesType==1 }">selected </c:if>>贷前收费</option>
							<option value="2" <c:if test="${project.projectGuarantee.chargesType==2 }">selected </c:if>>贷后收费</option>
						</select>
					</td>
					<td class="label_right1"><font color="red">*</font>欠款金额：</td>
					<td>
						<input name="projectGuarantee.deptMoney" required="true" min="0" maxLength='12' value="${project.projectGuarantee.deptMoney }" class="easyui-numberbox" precision="2" groupSeparator=","/>
					</td>
					<td class="label_right1"><font color="red">*</font>手续费补贴：</td>
					<td>
						<input name="projectGuarantee.chargesSubsidized" required="true" min="0" maxLength='12' value="${project.projectGuarantee.chargesSubsidized }" class="easyui-numberbox" precision="2" groupSeparator=","/>
					</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>应付佣金：</td>
					<td>
						 <input name="projectGuarantee.receMoney" required="true" min="0" maxLength='12' value="${project.projectGuarantee.receMoney }" class="easyui-numberbox" precision="2" groupSeparator=","/>
					</td>
					<td class="label_right1"><font color="red">*</font>付费银行：</td>
					<td>
						<select id="payBank" class="easyui-combobox"  data-options="validType:'selrequired'" name="projectGuarantee.payBank" editable="false" style="width:129px;" />
					</td>
				</tr>
			</table>
		</div>
		</div>
	</div>
	</div>
	<!-- 赎楼信息结束 -->