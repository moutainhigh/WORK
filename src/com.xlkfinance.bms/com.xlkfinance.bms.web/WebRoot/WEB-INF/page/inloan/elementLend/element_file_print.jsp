<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style>
/*打印*/
.table_css td {
	padding: 7px 5px;
	font-size: 12px;
}

.table_css input {
	vertical-align: middle;
	margin-bottom: 5px;
}
</style>
</head>
<script type="text/javascript">
var projectId=${collectFilePrintInfo.projectId};
var buyerSellerType=${collectFilePrintInfo.buyerSellerType};
var buyerSellerName='${collectFilePrintInfo.buyerName}${collectFilePrintInfo.sellerName}';
var pids = '${pids}';
function closeWindow() {
	var tab = parent.$('#layout_center_tabs').tabs('getSelected');
	var index = parent.$('#layout_center_tabs').tabs('getTabIndex', tab);
	parent.$('#layout_center_tabs').tabs('close', index);
}

function print() {
	$("#myPrintArea").printArea();
	//$("#myPrintArea").jqprint();
}
$(document).ready(function() {
		if(pids != ""){
			pids = pids.substring(0,pids.length-1);
			$.ajax({
				url : "getCollectFileListByPids.action",
				cache : true,
				type : "POST",
				data : {'pids' : pids},
				async : false,
				success : function(data, status) {
					var collectFileList = eval("("+data+")");
					//var collectFileList = result['collectFileList'];//获取已经收取的要件
					//初始化已经收取的要件信息
					for (var i = 0; i < collectFileList.length; i++) {
						$('#'+collectFileList[i].code).prop('checked',true);
						$('#'+collectFileList[i].code+'_REMARK').append(collectFileList[i].remark);
					}
				}
			});
		}
	    
});
</script>
<body>
<div id="myPrintArea">
 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_css" style="margin-bottom: 10px;">
  <tr>
   <td height="20" valign="top"><b>项目名称：</b>${collectFilePrintInfo.projectName }</td>
   <td valign="top"><b>部门：</b>${collectFilePrintInfo.deptName }</td>
   <td valign="top"><b>经办人：</b>${collectFilePrintInfo.pmName }</td>
  </tr>
  <tr>
   <td height="30" valign="top"><b>卖方：</b>${collectFilePrintInfo.sellerName }</td>
   <td valign="top"><b>买方：</b>${collectFilePrintInfo.buyerName }</td>
   <td valign="top"><b>物业名称：</b>${collectFilePrintInfo.houseName }</td>
  </tr>
  <c:if test="${collectFilePrintInfo.buyerSellerType==2 }">
   <tr>
    <td colspan="3"><input type="checkbox" value="SELLER_CARD_NO" id="SELLER_CARD_NO" name="collectFileMapList[0].code"><b>卖方身份证：</b> <span
     id="SELLER_CARD_NO_REMARK"
    ></span></td>
   </tr>

   <tr>
    <td colspan="3"><input type="checkbox" value="REPAYMENT_CARD" id="REPAYMENT_CARD" name="collectFileMapList[1].code"><b>回款卡/折： </b><span
     id="REPAYMENT_CARD_REMARK"
    ></span></td>
   </tr>

   <tr>
    <td colspan="3"><input type="checkbox" value="FORECLOSURE_CARD" id="FORECLOSURE_CARD" name="collectFileMapList[2].code"><b>赎楼卡/折： </b><span
     id="FORECLOSURE_CARD_REMARK"
    ></span></td>
   </tr>
  </c:if>
  <c:if test="${collectFilePrintInfo.buyerSellerType==1 }">
   <tr>
    <td colspan="3"><input type="checkbox" value="BUYER_CARD_NO" id="BUYER_CARD_NO" name="collectFileMapList[31].code"><b>买方身份证：</b> <span
     id="BUYER_CARD_NO_REMARK"
    ></span></td>
   </tr>

   <tr>
    <td><input type="checkbox" onchange="getBuyerOrSeller(this)" value="SUPERVISE_CARD" id="SUPERVISE_CARD" name="collectFileMapList[32].code"><b>监管卡/折：</b>
     <span id="SUPERVISE_CARD_REMARK"></span></td>
   </tr>
  </c:if>
  <tr>
   <td colspan="3"><input type="checkbox" value="BANK_CARD" id="BANK_CARD" name="collectFileMapList[3].code"><b>银行卡/折： </b><span
    id="BANK_CARD_REMARK"
   ></span></td>
  </tr>

  <tr>
   <td colspan="3"><input type="checkbox" value="NET_SILVER" id="NET_SILVER" name="collectFileMapList[4].code"><b>网上银行： </b><span
    id="NET_SILVER_REMARK"
   ></span></td>
  </tr>

  <tr>
   <td colspan="3"><input type="checkbox" value="SUPER_NET_SILVER" id="SUPER_NET_SILVER" name="collectFileMapList[5].code"><b>超级网银： </b><span
    id="SUPER_NET_SILVER_REMARK"
   ></span></td>
  </tr>

  <tr>
   <td colspan="3"><input type="checkbox" value="BANK_PHONE" id="BANK_PHONE" name="collectFileMapList[6].code"><b>银行预留手机号：</b> <span
    id="BANK_PHONE_REMARK"
   ></span></td>
  </tr>

  <tr>
   <td colspan="3"><input type="checkbox" value="TRANSFER_LIMIT_MONEY" id="TRANSFER_LIMIT_MONEY" name="collectFileMapList[7].code"><b>转账限额：</b> 
   <span  id="TRANSFER_LIMIT_MONEY_REMARK"></span></td>
  </tr>

  <tr>
   <td colspan="3"><input type="checkbox" value="COMPANY_NAME" id="COMPANY_NAME" name="collectFileMapList[8].code"><b>公司名称：</b> <span
    id="COMPANY_NAME_REMARK"
   ></span></td>
  </tr>
  <tr>
   <td colspan="3"><input type="checkbox" value="E_BANK_RELATE_ACCOUNT" id="E_BANK_RELATE_ACCOUNT" name="collectFileMapList[30].code"><b>网银关联账户：</b>
    <span id="E_BANK_RELATE_ACCOUNT_REMARK"></span></td>
  </tr>

 </table>

 <table width="100%" border="0" cellpadding="0" class="table_css" cellspacing="0" style="margin-bottom: 30px;">
  <tbody>
   <tr>
    <td><input type="checkbox" value="OFFICIAL_SEAL" id="OFFICIAL_SEAL" name="collectFileMapList[9].code">公章<span id="OFFICIAL_SEAL_REMARK" >:</span></td>
    <td><input type="checkbox" value="PERSONAL_SEAL" id="PERSONAL_SEAL" name="collectFileMapList[10].code">私章<span id="PERSONAL_SEAL_REMARK" >:</span></td>
    <td><input type="checkbox" value="FINANCE_SEAL" id="FINANCE_SEAL" name="collectFileMapList[11].code">财务章</td>
   </tr>
   <tr>
    <td><input type="checkbox" value="OPEN_ACCOUNT_LICENCE" id="OPEN_ACCOUNT_LICENCE" name="collectFileMapList[12].code">开户许可证</td>
    <td><input type="checkbox" value="RESERVED_SIGNATURE_CARD" id="RESERVED_SIGNATURE_CARD" name="collectFileMapList[13].code">预留印鉴卡</td>
    <td><input type="checkbox" value="APPLY_SEAL" id="APPLY_SEAL" name="collectFileMapList[14].code">刻章申请</td>
   </tr>
   <tr>
    <td><input type="checkbox" value="BANK_DEBIT_CARD" id="BANK_DEBIT_CARD" name="collectFileMapList[15].code">银行结算卡</td>
    <td><input type="checkbox" value="BUSINESS_LICENSE_ORIGINAL" id="BUSINESS_LICENSE_ORIGINAL" name="collectFileMapList[16].code">营业执照正本
    <input type="checkbox" value="BUSINESS_LICENSE_COPY" id="BUSINESS_LICENSE_COPY" name="collectFileMapList[17].code">副本</td>
    <td><input type="checkbox" value="TAX_REGISTRATION_ORIGINAL" id="TAX_REGISTRATION_ORIGINAL" name="collectFileMapList[18].code">税务登记证正本
    <input type="checkbox" value="TAX_REGISTRATION_COPY" id="TAX_REGISTRATION_COPY" name="collectFileMapList[19].code">副本</td>
   </tr>
   <tr>
    <td><input type="checkbox" value="ORG_CODE__LICENSE_ORIGINAL" id="ORG_CODE__LICENSE_ORIGINAL" name="collectFileMapList[20].code">组织机构代码证正本</td>
    <td><input type="checkbox" value="ORG_CODE__LICENSE_COPY" id="ORG_CODE__LICENSE_COPY" name="collectFileMapList[21].code">副本</td>
    <td><input type="checkbox" value="CREDIT_ORG_CODE_LICENSE" id="CREDIT_ORG_CODE_LICENSE" name="collectFileMapList[22].code">信用机构代码证</td>
   </tr>
   <tr>
    <td><input type="checkbox" value="PUBLIC_CONVERSION_PRIVATE" id="PUBLIC_CONVERSION_PRIVATE" name="collectFileMapList[23].code">公转私</td>
    <td><input type="checkbox" value="CHEQUE" id="CHEQUE" name="collectFileMapList[24].code">支票 剩余<span id="CHEQUE_REMARK"></span>张</td>
    <td><input type="checkbox" value="CHEQUE_PWD_MACHINE" id="CHEQUE_PWD_MACHINE" name="collectFileMapList[25].code">支票密码器</td>
   </tr>
   <tr>
    <td><input type="checkbox" value="POSTING_FEE" id="POSTING_FEE" name="collectFileMapList[26].code">过账费</td>
    <td><input type="checkbox" value="WECHAT_PAY" id="WECHAT_PAY" name="collectFileMapList[27].code">微信支付</td>
    <td><input type="checkbox" value="PAY_TREASURE" id="PAY_TREASURE" name="collectFileMapList[28].code">支付宝</td>
   </tr>
   <tr>
    <td colspan="3"><input type="checkbox" value="ORDER" id="ORDER" name="collectFileMapList[29].code">其他:
    <span id="ORDER_REMARK"></span>
    </td>
   </tr>
  </tbody>
 </table>

 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_css" style="margin-bottom:30px;">
  
  <tr>
    <td colspan="2"><b>用于办理：</b>${porpuse}</td>
  </tr>
  
  <tr>
    <td colspan="2"><b>归还时间：</b>需<input type="text" autocomplete="off" placeholder="" style="padding: 2px; border:0; width: 40px;">年<input type="text" autocomplete="off" placeholder="" style="padding: 2px; border:0; width: 40px;">月<input type="text" autocomplete="off" placeholder="" style="padding: 2px; border:0; width: 40px;">日前归还</td>
  </tr>
  
  <tr>
    <td width="90" valign="top"><b><input name="" type="checkbox" checked="checked">无需归还：</b></td>
    <td valign="top">微信、支付宝、公转私等都无需归还</td>
  </tr>
</table>

 <table width="40%" border="0" cellspacing="0" cellpadding="0" class="table_css"  style="float:right">
  
 
  <tr>
    <td><b>经办人签收：</b></td>
  </tr>
  <tr>
    <td><b>日期：</b></td>
  </tr>
</table>
</div>
</body>
</html>