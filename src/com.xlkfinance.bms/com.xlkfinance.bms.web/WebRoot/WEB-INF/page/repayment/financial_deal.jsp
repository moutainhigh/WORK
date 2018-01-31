<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
	
<div class="easyui-panel" title="还款对账处理" data-options="collapsible:true">
	<!-- 查询条件 -->
	<form action="listrepaymenturl.action" method="post" id="searcFrom"
		name="searcFrom">
		<div style="padding: 5px">
			<table class="beforeloanTable" width="90%">
				<tr>
					<td class="label_right" style="width: 40%">提前还款日期：</td>
					<td><input class="easyui-datebox"
									name="repayDate" id="repayDate" editable="false" value="${advinfo.repayDate}"/> </td>
					<td class="label_right"  style="width: 40%">本次还款后的剩余贷款本金：</td>
					<td><input class="easyui-textbox"
							name="principalBalance" id="principalBalance" value="${advinfo.principalBalance}" /></td>

				</tr>

				<tr>
					<td class="label_right"  style="width: 40%">应收提前还贷金额：</td>
					<td><input class="easyui-textbox" name="preRepayAmt" value="${advinfo.preRepayAmt}"
							id="preRepayAmt" /></td>
					<td class="label_right"  style="width: 40%">应收提前还款罚金：</td>
					<td><input class="easyui-textbox" name="fine" id="fine" value="${advinfo.fine}" /></td>
				</tr>
				<tr>
					<td colspan="4" height="35" align="center">
						<a href="javascript:void(0);" class="easyui-linkbutton"
							iconCls="icon-search" onclick="repayRec()">还款对账处理</a>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
