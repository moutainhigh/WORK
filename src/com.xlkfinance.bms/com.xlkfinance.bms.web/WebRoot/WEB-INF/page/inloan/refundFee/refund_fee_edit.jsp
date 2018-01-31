<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<%@ include file="/common.jsp"%>
<title>退手续费</title>
<style type="text/css">
.table_css td {
	padding: 7px 10px;
	font-size: 12px;
	border-bottom: 1px #95B8E7 solid;
	border-right: 1px #95B8E7 solid;
}
.bt{ background-color: #E0ECFF;
background: -webkit-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
background: -moz-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
background: -o-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
background: linear-gradient(to bottom,#EFF5FF 0,#E0ECFF 100%);
background-repeat: repeat-x;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#EFF5FF,endColorstr=#E0ECFF,GradientType=0);
font-size: 12px;
font-weight: bold;
color: #0E2D5F;}
</style>
</head>
<script type="text/javascript">
/**************工作流字段 begin******************/
//退手续费处理申请
var WORKFLOW_ID = "refundFeeProcess"; 
var workflowTaskDefKey = "task_Request";
nextRoleCode = "FINANCE_CHECK_ROLE";//
/**************工作流字段 end********************/
var projectId = ${editRefundFeeDTO.projectId};
var refId = ${editRefundFeeDTO.projectId};
//var projectNum = "${projectNum}";
	function subForm() {
		// 提交表单
		$("#refundFeeForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', '保存成功', 'info');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	function refreshPage() {
		window.location.reload();
	}
	/**
	 * 关闭窗口
	 */
	function closeWindow() {
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var index = parent.$('#layout_center_tabs').tabs('getTabIndex', tab);
		parent.$('#layout_center_tabs').tabs('close', index);
	}
	
	$(document).ready(function() {
		$("#returnFee").next("span").children().first().blur(function(){
			$("#returnFeeCny").text(numToCny($('#returnFee').numberbox('getValue')));
		});
		$("#returnFeeCny").text(numToCny($('#returnFee').numberbox('getValue')));
	});
</script>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="退手续费信息" id="tab1" style="padding: 10px;">
  <table>
   <tr>
    <td height="35" width="300" align="center">项目名称: <a href="#" onclick="formatterProjectOverview.disposeClick(${editRefundFeeDTO.projectId})"><font color='blue'> ${editRefundFeeDTO.projectName }</font></a></td>
    <td><div class="iniTitle">项目编号:${editRefundFeeDTO.projectNumber}</div></td>
   </tr>
  </table>
    <form action="${basePath}refundFeeController/addOrUpdate.action" id="refundFeeForm" name="refundFeeForm" method="post">
     <table cellpadding="0" cellspacing="0" class="table_css" style="width: 99%; border-top: 1px #95B8E7 solid; border-left: 1px #95B8E7 solid;">
      <tr>
       <td colspan="5" class="bt" style="padding: 3px 10px;">基本信息</td>
      </tr>
      <tr>
       <td>产品名称：</td>
       <td><input name="productName" value="${editRefundFeeDTO.productName }" type="text" class="easyui-textbox" disabled="disabled"></td>
       <td>交易方式：</td>
       <td colspan="2"><input name="tradeWay" value="${editRefundFeeDTO.tradeWay }" type="text" class="easyui-textbox" disabled="disabled"></td>
      </tr>
      <tr>
       <td>交易日期：</td>
       <td><input name="tradeDate" value="${editRefundFeeDTO.tradeDate }" type="text" class="easyui-datebox" disabled="disabled"></td>
       <td>抵押编号：</td>
       <td colspan="2"><input name="mortgageNumber" value="${editRefundFeeDTO.mortgageNumber }" type="text" class="easyui-textbox" disabled="disabled"></td>
      </tr>
      <tr>
       <td>借款申请人：</td>
       <td><input name="customerName" value="${editRefundFeeDTO.customerName }" type="text" class="easyui-textbox" disabled="disabled"></td>
       <td>经办人：</td>
       <td colspan="2"><input name="pmUserName" value="${editRefundFeeDTO.pmUserName }" type="text" class="easyui-textbox" disabled="disabled"></td>
      </tr>
      <tr>
       <td>房产名称：</td>
       <td><input name="homeName" value="${editRefundFeeDTO.homeName }" type="text" class="easyui-textbox" disabled="disabled"></td>
       <td>原业主：</td>
       <td colspan="2"><input name="oldHome" value="${editRefundFeeDTO.oldHome }" type="text" class="easyui-textbox" disabled="disabled"></td>
      </tr>
      <tr>
       <td>收费日期：</td>
       <td><input name="recDate" value="${editRefundFeeDTO.recDate }" type="text" class="easyui-datebox" disabled="disabled"></td>
       <td>收费金额：</td>
       <td colspan="2"><input name="recMoney" value="${editRefundFeeDTO.recMoney }" type="text" class="easyui-numberbox"
        data-options="min:0,max:9999999999,precision:2,groupSeparator:','" disabled="disabled"
       ></td>
      </tr>
      <tr>
       <td>解保日期：</td>
       <td><input name="cancelGuaranteeDate" value="${editRefundFeeDTO.cancelGuaranteeDate }" type="text" class="easyui-datebox" disabled="disabled"></td>
       <td>放款日期：</td>
       <td colspan="2"><input name="payDate" value="${editRefundFeeDTO.payDate }" type="text" class="easyui-datebox" disabled="disabled"></td>
      </tr>
      <tr>
       <td>业务部门：</td>
       <td colspan="4"><input name="deptName" value="${editRefundFeeDTO.deptName }" type="text" class="easyui-textbox" disabled="disabled"></td>
      </tr>
      <tr>
       <td><font color="red">*</font>退费金额：</td>
       <td><input name="returnFee" id="returnFee"  value="${editRefundFeeDTO.returnFee }" type="text" class="easyui-numberbox"
        data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"
       ></td>
       <td>大写：</td>
       <td colspan="2" id="returnFeeCny"></td>
      </tr>
<%--       <tr>
       <td><font color="red">*</font>收款人户名：</td>
       <td><input name="recAccountName" value="${editRefundFeeDTO.recAccountName }" type="text" class="easyui-textbox" data-options="required:true,validType:'length[0,20]'"></td>
       <td><font color="red">*</font>收款人账号：</td>
       <td colspan="2"><input name="recAccount" value="${editRefundFeeDTO.recAccount }" type="text" class="easyui-textbox" data-options="required:true,validType:'length[0,30]'"></td>
      </tr>
      <tr>
       <td><font color="red">*</font>开户行：</td>
       <td colspan="4"><input name="bankName" value="${editRefundFeeDTO.bankName }" type="text" class="easyui-textbox" data-options="required:true,validType:'length[0,30]'"></td>
      </tr> --%>
     </table>
     <input type="hidden" name="projectId" value="${editRefundFeeDTO.projectId }${projectId }"> <input type="hidden" name="pid" value="${editRefundFeeDTO.pid }">
     <input type="hidden" name="applyStatus" id="applyStatus" value="${editRefundFeeDTO.applyStatus }">
     <input type="hidden" name="type" id="type" value="1">
    </form>
    <p class="jl">
     <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="subForm()">保存</a> <a href="javascript:void(0)" class="easyui-linkbutton"
      iconCls="icon-cancel" onclick="closeWindow()"
     >关闭</a>
    </p>
    <div class="panel-body1 pt10" id="loanworkflowWrapDivPanel">
     <div class="easyui-panel" title="工作流程" data-options="collapsible:true" style="width:99%;">
      <div style="padding: 5px">
       <%@ include file="../../common/loanWorkflow.jsp"%>
       <%@ include file="../../common/task_hi_list.jsp"%>
      </div>
     </div>
    </div>
   </div>
  </div>
 </div>
</body>
</html>