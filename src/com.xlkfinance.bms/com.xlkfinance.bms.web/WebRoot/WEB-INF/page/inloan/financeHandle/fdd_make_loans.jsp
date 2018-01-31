<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<style type="text/css">
.table_css{ border:1px #95B8E7 solid;}
.table_css td{ padding:4px 10px; font-size:12px;border-bottom:1px #ddd solid;  border-right:1px #ddd solid;}
.table_css td:last-child{  border-right:0;}
.bb td b{ float:left; width:95px; text-align: right; padding-right: 10px;font-weight: normal;line-height: 22px;}
.bt{ background-color: #E0ECFF;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#EFF5FF,endColorstr=#E0ECFF,GradientType=0);
font-size: 12px;
font-weight: bold;
color: #0E2D5F;}
.bt td{padding:4px 10px;border-bottom:1px #95B8E7 solid;}
.srk1{ height:60px;width:70%;}
.table_css th {
	background: linear-gradient(to bottom, #EFF5FF 0, #E0ECFF 100%);
	background-repeat: repeat-x;
	padding: 7px 5px;
	font-size: 12px;
	border-bottom: 1px #ddd solid;
	border-right: 1px #ddd solid;
}

.table_css tr {
	background: #fff;
}

.table_css tr:nth-child(even) {
	background: #fff;
}

.table_css tr:nth-child(odd) {
	background: #f9f9f9;
}

.table_css td {
	padding: 7px 5px;
	font-size: 12px;
	border-bottom: 1px #ddd solid;
	border-right: 1px #ddd solid;
}

.hidden_css {
	display: none;
}
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript">
/**************工作流字段 begin******************/
//房抵贷放款申请 
var WORKFLOW_ID = "fddMakeLoansProcess"; 
var workflowTaskDefKey = "task_Request";
nextRoleCode = "OPERATE_DEPARTMENT";//
/**************工作流字段 end********************/
var projectId = ${financeIndexDTO.projectId};
var refId = ${financeIndexDTO.makeLoansApplyFinanceId};
var capitalName='${project.capitalName}';//资方名称
var recMoney=${makeLoans.recMoney};//放款金额,工作流js用到
    //提交资方信息表单
	function subCapitalNameInfoForm() {
		// 提交表单
		$("#capitalNameInfoForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					initRoleCodeByCapitalName();      
					capitalName=$('#capitalName').combobox('getValue');
					$.messager.alert('操作提示', '保存成功', 'info');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
    //提交放款信息表单
	function subMakeLoansInfoForm() {
		// 提交表单
		$("#makeLoansInfoForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
				    recMoney=$('#recMoney').numberbox('getValue');
				    initRoleCodeByRecMoney();
					$.messager.alert('操作提示', '保存成功', 'info');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}

	//放款信息数据回显
	function initMakeLoansInfoForm(){
		var recMoney=${makeLoans.recMoney};
		var recAccount='${makeLoans.recAccount}';
		var recDate='${makeLoans.recDate}';
		var remark='${makeLoans.remark}';
		if (recMoney!=null&&recMoney>0) {
			$("#recMoney").numberbox('setValue',recMoney);
		}else{
			$("#recMoney").numberbox('setValue',${project.projectGuarantee.loanMoney});
		}
		if (recDate!='') {
			$("#recDate").datebox('setValue',recDate);
		}
		if (remark!='') {
			$("#remark").val(remark);
		}
		if (recAccount=='') {
		    setCombobox3("recAccount", "MAKE_LOANS_BANK", '4100627824511118');
		} else {
		    setCombobox3("recAccount", "MAKE_LOANS_BANK", recAccount);
		}
	}
	//资金信息数据回显
	function initCapitalNameInfoForm(){
		var capitalName='${project.capitalName}';//资方名称
		var loanType='${project.loanType}';//放款条件
		if (capitalName==null||capitalName=='') {
		   setCapitalOrgInfo("capitalName",'ziyouzijin')
		} else {
		   setCapitalOrgInfo("capitalName",capitalName)
		}
		if (loanType==null||loanType=='') {
		   setCombobox3("loanType", "FDD_LOAN_TYPE", '-1');
		} else {
		   setCombobox3("loanType", "FDD_LOAN_TYPE", loanType);
		}
	}
	//开始节点根据资金方初始化工作流审核角色
	function initRoleCodeByCapitalName(){
		if (workflowTaskDefKey!="task_Request") {
			return;
		}
		var capitalName="${project.capitalName}";
		var capitalNameTemp=$('#capitalName').combobox('getValue');
		if (capitalNameTemp!='0'&&capitalNameTemp!='-1'&&capitalNameTemp!="") {
		  capitalName=$('#capitalName').combobox('getValue');
		}
		var roleCode = null;
		var roleName = null;
		if ('ziyouzijin'==capitalName) {
			roleCode="OPERATE_DEPARTMENT";
			roleName="运营部经理（小科）";
			nextRoleCode="OPERATE_DEPARTMENT";
		} else {
			roleCode="OPERATION_COMMISSIONER";
			roleName="运营专员";
			nextRoleCode="OPERATION_COMMISSIONER";
		}
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">';
		
		htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
			+ roleCode
			+ '" class="verifier" value="'
			+ roleCode
			+ '" taskNodeName="'
			+ roleName
			+ '"/>'
			+ '<span id="transfer_down">'
			+ roleName
			+ '</span>&nbsp;&nbsp;&nbsp;';
		htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
			+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
		$("#next_node_tr").replaceWith(htmlStr);
		$('#userIds').val(null);
		$('#userIds').combobox({
			valueField : 'pid',
			textField : 'realName',
			multiple : true,
			editable : false,
			disabled : true
		});
		$('body').delegate('.verifier', 'change',findUsersByRoleCode);
	}
	//资金主管根据放款金额初始化工作流审核角色
	function initRoleCodeByRecMoney(){
		if (workflowTaskDefKey=='task_FinanceDirectorCheck'||workflowTaskDefKey=='task_FundManagerCheck') {
			//自由资金,放款金额大于等于三百万,财务总监审核则提交
			var bool1=(capitalName=='ziyouzijin'&&workflowTaskDefKey=='task_FinanceDirectorCheck'&&recMoney >= 3000000);
			//自由资金,放款金额小于三百万,资金主管审核则提交
			var bool2=(capitalName=='ziyouzijin'&&workflowTaskDefKey=='task_FundManagerCheck'&&recMoney < 3000000);
			if (bool1||bool2) {
				var htmlStr = '<tr id="next_node_tr">'
					+ '<td class="label_right" >业务提交至：</td>'
					+ '<td id="next_node_td">'
					+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
					+ nextRoleCodeStr + '" class="verifier" value="'
					+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
					+ '<span id="transfer_down">审核通过</span></td></tr>';
			     $("#next_node_tr").replaceWith(htmlStr);
			}else{
				var roleCode = "FINANCE_DIRECTOR";
				var roleName = "财务总监";
				nextRoleCode="OPERATE_DEPARTMENT";
				var htmlStr = '<tr id="next_node_tr">'
					+ '<td class="label_right" >业务提交至：</td>'
					+ '<td id="next_node_td">';
				
				htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
					+ roleCode
					+ '" class="verifier" value="'
					+ roleCode
					+ '" taskNodeName="'
					+ roleName
					+ '"/>'
					+ '<span id="transfer_down">'
					+ roleName
					+ '</span>&nbsp;&nbsp;&nbsp;';
				htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
				$("#next_node_tr").replaceWith(htmlStr);
				$('#userIds').val(null);
				$('#userIds').combobox({
					valueField : 'pid',
					textField : 'realName',
					multiple : true,
					editable : false,
					disabled : true
				});
				$('body').delegate('.verifier', 'change',findUsersByRoleCode);
			}
		}
	}
	$(document).ready(function() {
		initMakeLoansInfoForm();//放款信息数据回显
		initCapitalNameInfoForm();//资金信息数据回显
		initRoleCodeByCapitalName();//开始节点根据资金方初始化工作流审核角色
		initRoleCodeByRecMoney();//资金主管根据放款金额初始化工作流审核角色
		setCombobox3("chargesType", "MORTGAGE_LOAN_CHARGES_TYPE", '${project.projectGuarantee.chargesType}');
	});
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
   <form action="${basePath}financeHandleController/saveCapitalNameInfo.action" id="capitalNameInfoForm" name="capitalNameInfoForm" method="post">
     <div class="easyui-panel" title="资金方选择" data-options="collapsible:true" >
       <div id="capitalNameInfo" style="height: auto; line-height: 35px;">
        <table class="capitalNameInfoTable" style="padding: 10px;" cellpadding="0" cellspacing="0">
         <tr>
          <td class="label_right"><font color="red">*</font>选择资方：</td>
          <td class="label_center">
          <input name="capitalName" id="capitalName" class="easyui-combobox" data-options="validType:'selrequired'"/>
          </td>
          <td class="label_right"><font color="red">*</font>放款条件：</td>
          <td class="label_center"><input name="loanType" id="loanType" class="easyui-combobox" data-options="validType:'selrequired'" missingMessage="请选项放款条件!"/></td>
         </tr>
         <tr>
          <td class="label_right">
          <a href="#" id="saveCapitalNameInfoForm" class="easyui-linkbutton" iconCls="icon-save" onclick="subCapitalNameInfoForm()">保存</a> 
          </td>
         </tr>
        </table>
       </div>
       <input name="pid" value="${financeIndexDTO.projectId}" type="hidden"/>
      </div>
      </form>
   <form action="${basePath}financeHandleController/saveFddMakeLoansInfo.action" id="makeLoansInfoForm" name="makeLoansInfoForm" method="post">
     <div class="easyui-panel" title="放款信息" data-options="collapsible:true" >
       <div id="makeLoansInfo" style="height: auto; line-height: 35px;">
        <table class="makeLoansInfoTable" style="padding: 10px;" cellpadding="0" cellspacing="0">
         <tr>
          <td class="label_right"><font color="red">*</font>放款金额：</td>
          <td class="label_center"><input name="recMoney" id="recMoney" class="easyui-numberbox"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请填写放款金额!"/></td>
          <td class="label_right"><font color="red">*</font>放款账号：</td>
          <td class="label_center"><input name="recAccount" id="recAccount" class="easyui-combobox" data-options="validType:'selrequired'" style="width: 250px;" missingMessage="请选择放款账号!" /></td>
          <td class="label_right"><font color="red">*</font>放款日期：</td>
          <td class="label_center"><input name="recDate" id="recDate" class="easyui-datebox" data-options="required:true" missingMessage="请填写放款日期!" editable="false"/></td>
         </tr>
         <tr>
          <td class="label_right">备注：</td>
          <td colspan="3"><textarea rows="3" id="remark" name="remark" maxlength="500" style="width: 100%;"></textarea></td>
         </tr>
         <tr>
          <td class="label_right">
          <a href="#" id="saveMakeLoansInfoForm" class="easyui-linkbutton" iconCls="icon-save" onclick="subMakeLoansInfoForm()">保存</a> 
          </td>
         </tr>
        </table>
        <input type="hidden" name="pid" value="${financeIndexDTO.makeLoansApplyFinanceId }">
        <input type="hidden" name="financeHandleId" value="${financeIndexDTO.pid}">
       </div>
      </div>
    </form>
     <div class="easyui-panel" title="费用信息" data-options="collapsible:true" >
       <div id="feeInfoTable" style="height: auto; line-height: 35px;">
        <table style="padding: 10px;" cellpadding="0" cellspacing="0">
         <tr>
          <td class="label_right">借款金额：</td>
          <td class="label_center"><input class="easyui-numberbox" value="${project.projectGuarantee.loanMoney }" editable="false" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" /></td>
          <td class="label_right">期限：</td>
          <td class="label_center"><input class="easyui-textbox" value="${project.projectGuarantee.loanTerm }"  editable="false"/></td>
          <td class="label_right">月利率：</td>
          <td class="label_center"><input class="easyui-textbox"  editable="false" value="${project.projectGuarantee.feeRate }" editable="false"/>%</td>
         </tr>
         <tr>
          <td class="label_right">月返佣费率：</td>
          <td class="label_center"><input class="easyui-textbox" value="${project.projectGuarantee.monthMaidRate }" editable="false"/>%</td>
          <td class="label_right">逾期日罚息率：</td>
          <td class="label_center"><input class="easyui-textbox" value="${project.projectGuarantee.overdueRate }" editable="false"/>%</td>
          <td class="label_right">提前还款费率：</td>
          <td class="label_center"><input class="easyui-textbox" value="${project.projectGuarantee.prepaymentRate }" editable="false"/>%</td>
         </tr>
         <tr>
          <td class="label_right">收费节点：</td>
          <td class="label_center">
          <input name="chargesType" id="chargesType" class="easyui-combobox" editable="false"/>
          </td>
          <td class="label_right">收费金额：</td>
          <td class="label_center"><input class="easyui-numberbox" value="${project.projectGuarantee.poundage }" editable="false" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
         </tr>
        </table>
       </div>
      </div>
      <div class="panel-body1 pt10" style="padding: 10px;">
     <div class="easyui-panel" title="贷前审批总览" data-options="collapsible:true" style="width:99%;">
     <table cellpadding="0" cellspacing="0" class="table_css"
	style="width: 100%; border-top: 1px #ddd solid; border-left: 1px #ddd solid;">
	<tr>
		<th width="4%" align="center">序号</th>
		<th width="5%" align="center">状态</th>
		<th width="10%">办理步骤</th>
		<th align="center">内容</th>
		<th width="6%" align="center">操作人</th>
		<th width="13%" align="center">操作日期</th>
	</tr>
	<c:forEach var="dynamic" items="${dynamicList}" varStatus="status">
		<tr>
			<td align="center">${status.index+1}</td>
			<td align="center">
				<!-- 状态：未完成=1，进行中=2，已完成=3，失效=4 --> <c:choose>
					<c:when test="${dynamic.status==4}">
						<img src="${ctx}/p/xlkfinance/images/dynamicStatus_4.png"
							width="16" height="16" />
					</c:when>
					<c:when test="${dynamic.status==3}">
						<img src="${ctx}/p/xlkfinance/images/dynamicStatus_3.png"
							width="16" height="16" />
					</c:when>
					<c:otherwise>
						<img src="${ctx}/p/xlkfinance/images/dynamicStatus_1.png"
							width="16" height="16" />
					</c:otherwise>
				</c:choose>
			</td>
			<td>${dynamic.dynamicName}</td>
			<td>${dynamic.remark}
			</td>
			<td align="center">${dynamic.handleAuthorName}</td>
			<td align="center">${dynamic.updateDate}</td>
		</tr>

	</c:forEach>
</table>

      
     </div>
    </div>
    <div class="panel-body1 pt10" id="loanworkflowWrapDivPanel" style="padding: 10px;">
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
