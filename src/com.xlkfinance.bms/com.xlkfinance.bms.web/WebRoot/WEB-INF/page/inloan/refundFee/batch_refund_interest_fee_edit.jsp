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
<title>退咨询费</title>
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
var WORKFLOW_ID = "refundInterestFeeProcess"; 
var workflowTaskDefKey = "task_Request";
nextRoleCode = "OPERATE_DEPARTMENT";//
/**************工作流字段 end********************/
var projectSource = 2;
var batchRefundFeeMainId = ${batchRefundFeeMain.pid };
var refId = ${batchRefundFeeMain.pid };
var projectId = ${batchRefundFeeMain.pid };
var applyStatus=${batchRefundFeeMain.applyStatus };
var batchRefundFeeFlag=1;//批量退费标志
//提交表单
function subBatchRefundFeeForm() {
	// 提交表单
	$("#batchRefundFeeForm").form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				batchRefundFeeMainId=ret.body.batchRefundFeeMainId;
				refId=batchRefundFeeMainId;
				projectId=batchRefundFeeMainId;
				$("#batchRefundFeeMainId").val(batchRefundFeeMainId);
				$.messager.alert('操作提示', '保存成功', 'info');
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}   
    //表单的退费金额金额输入框的id
	var ids="";
	//统计退费总额
	function initTotalMoney(){
		var idArray=ids.split(",");
		var totalMoney=0;
    	for (var i = 0; i < idArray.length; i++) {
    		totalMoney=totalMoney+parseFloat($(idArray[i]).numberbox('getValue'))
		}
		$("#totalMoney").numberbox('setValue',totalMoney);
	}
	$(document).ready(function() {
		for (var i = 0; i < ${refundRefundFeeListSize}; i++) {
			if (i == 0) {
				ids += "#batchRefundFeeMapList"+i+"_money";
			} else {
				ids += ",#batchRefundFeeMapList"+i+"_money";
			}
		}
		//初始化退费总额
		initTotalMoney();
		$(ids).numberbox({
	        onChange:function(){  
	        	initTotalMoney();
	        }  
	    }); 
	});
</script>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
   <div class="easyui-panel" title="放款信息" data-options="collapsible:true" >
   <form action="${basePath}refundFeeController/saveBatchRefundFee.action" id="batchRefundFeeForm" name="batchRefundFeeForm" method="post">
        <table style="padding: 10px;" cellpadding="0" cellspacing="0">
         <tr>
          <td class="label_right">合作机构：</td>
          <td class="label_center">${businessSourceStr}</td>
          <td class="label_right">批量名称：</td>
          <td class="label_center" colspan="3">${batchRefundFeeMain.batchName }</td>
         </tr>
         <tr>
          <td class="label_right"><font color="red">*</font>收款人户名：</td>
          <td class="label_center"><input name="recAccountName" value="${refundRefundFeeList[0].recAccountName }" class="easyui-textbox" data-options="required:true,validType:'length[0,30]'"></td>
          <td class="label_right"><font color="red">*</font>账号：</td>
          <td class="label_center"><input name="recAccount" value="${refundRefundFeeList[0].recAccount }" class="easyui-textbox" data-options="required:true,validType:'length[0,30]'"></td>
          <td class="label_right"><font color="red">*</font>开户行：</td>
          <td class="label_center"><input name="bankName" value="${refundRefundFeeList[0].bankName }" class="easyui-textbox" data-options="required:true,validType:'length[0,30]'"></td>
         </tr>
        </table>
        <table style="padding: 10px;" cellpadding="0" cellspacing="0">
        <c:forEach items="${refundRefundFeeList }" var="item" varStatus="status">
         <tr>
          <td class="label_right">项目名称：</td>
          <td class="label_center"><a href="#" onclick="formatterProjectOverview.disposeClick(${item.projectId})"><font color='blue'> ${item.projectName }</font></a></td>
          <td class="label_right">项目编号：</td>
          <td class="label_center">${item.projectNumber }</td>
          <td class="label_right">已收咨询费：</td>
          <td class="label_center">${item.recGuaranteeMoney }</td>
          <td class="label_right"><font color="red">*</font>退费金额：</td>
          <td class="label_center"><input id="batchRefundFeeMapList${status.index }_money" name="batchRefundFeeMapList[${status.index }].money" value="${item.returnFee }" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"></td>
         </tr>
          <input name="batchRefundFeeMapList[${status.index }].projectId" value="${item.projectId }" type="hidden">
          <input name="batchRefundFeeMapList[${status.index }].pid" value="${item.pid }" type="hidden">
         </c:forEach>
          <tr>
          <td class="label_right">退费总金额：</td>
          <td class="label_center"><input id="totalMoney" editable="false" value="${item.returnFee }" class="easyui-numberbox" data-options="min:0,max:999999999999999999999,precision:2,groupSeparator:','"></td>
         </tr>
          <tr>
          <td class="label_right">
          <a href="#" id="saveBatchRefundFeeForm" class="easyui-linkbutton" iconCls="icon-save" onclick="subBatchRefundFeeForm()">保存</a> 
          </td>
         </tr>
        </table>
          <input name="batchRefundFeeMainId" id="batchRefundFeeMainId" value="${batchRefundFeeMain.pid }" type="hidden">
          <input name="batchName" value="${batchRefundFeeMain.batchName }" type="hidden">
        </form>
      </div>
    <div class="panel-body1 pt10" id="loanworkflowWrapDivPanel">
     <div class="easyui-panel" title="工作流程" data-options="collapsible:true" style="width:99%;">
      <div style="padding: 5px">
       <%@ include file="../../common/loanWorkflow.jsp"%>
       <%@ include file="../../common/task_hi_list.jsp"%>
      </div>
     </div>
    </div>
 </div>
</body>
</html>