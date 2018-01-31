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
<script type="text/javascript">
	//重置按钮
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset')
	}
	function formatterBusinessSource(val, row) {
		var businessSourceStr="";
		if (row.businessSourceStr!=null) {
		  businessSourceStr="--"+row.businessSourceStr;
		}
	    return row.businessSourceName+businessSourceStr;
	}
	function formatterInnerOrOut(val, row) {
		if (val == 1) {
			return "内单";
		} else if (val == 2) {
			return "外单";
		}else {
			return "未知";
		}
	}
	function cxportDifferWarn() {
		
		$.ajax({
					url : BASE_PATH
							+ 'templateFileController/checkFileUrl.action',
					data : {
						templateName : 'YJTJBB'
					},
					dataType : 'json',
					success : function(data) {
						if (data == 1) {
							var projectName =$("#projectName").textbox('getValue');//1
					        var productName =$("#productName").textbox('getValue');
					        var businessSourceStr =$("#businessSourceStr").textbox('getValue');
					        var deptName =$("#deptName").textbox('getValue');
					        
					         var pmUserName =$("#pmUserName").textbox('getValue');
					        var recFeeStatus =$("#recFeeStatus").combobox('getValue');//6
					        var sellerName =$("#sellerName").textbox('getValue');
					         var buyerName =$("#buyerName").textbox('getValue');
					       
					         var oldLoanBank =$("#oldLoanBank").textbox('getValue');
					        var innerOrOut =$("#innerOrOut").combobox('getValue');
					        var foreclosureStatus =$("#foreclosureStatus").combobox('getValue');
					        var bizHandleDynamic =$("#bizHandleDynamic").combobox('getValue');
					        
					        var handleFlowId =$("#handleFlowId").combobox('getValue'); //13
					        var handleFlowStartDate =$("#handleFlowStartDate").textbox('getValue');
					        var handleFlowEndDate =$("#handleFlowEndDate").textbox('getValue');
					        var loanDate =$("#loanDate").textbox('getValue');
					        
					         var loanEndDate =$("#loanEndDate").textbox('getValue');
					         var loanAmt =$("#loanAmt").numberbox('getValue'); 
					         var loanAmtMax =$("#loanAmtMax").numberbox('getValue'); 
					        var repaymentStatus =$("#repaymentStatus").combobox('getValue');
// 																															projectName productName businessSourceStr deptName
							window.location.href ="${basePath}reportController/businessSummaryExcelExportList.action?projectName="+projectName+"&productName="+productName+"&businessSourceStr="+businessSourceStr+"&deptName="+deptName+
									"&oldLoanBank="+oldLoanBank+"&innerOrOut="+innerOrOut+"&foreclosureStatus="+foreclosureStatus+"&bizHandleDynamic="+bizHandleDynamic+
									"&pmUserName="+pmUserName+"&recFeeStatus="+recFeeStatus+"&sellerName="+sellerName+"&buyerName="+buyerName+
									"&handleFlowId="+handleFlowId+"&handleFlowStartDate="+handleFlowStartDate+"&handleFlowEndDate="+handleFlowEndDate+"&loanDate="+loanDate+
									"&loanEndDate="+loanEndDate+"&loanAmt="+loanAmt+"&loanAmtMax="+loanAmtMax+"&repaymentStatus="+repaymentStatus+"&page=-1";
						} else {
							alert('业务汇总报表的导出模板不存在，请上传模板后重试');
						}
					}
				})
	}
	var businessSummaryReportList = {
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
				return va;
			}
		}
</script>
<title>业务汇总报表</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/businessSummaryReportList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td width="80" align="right" height="28">项目名称：</td>
        <td><input class="easyui-textbox" name="projectName" id="projectName" /></td>
        <td width="80" align="right" height="28">产品名称：</td>
        <td><input class="easyui-textbox" name="productName" id="productName"/></td>
        <td width="80" align="right">业务来源：</td>
        <td>
        <input name="businessSourceStr" id="businessSourceStr" class="easyui-textbox" />
       </td>
        <td width="100" align="right" height="28">业务部门：</td>
        <td><input class="easyui-textbox" name="deptName"  id="deptName"/></td>
       </tr>
       <tr>
        <td width="100" align="right" height="28">业务经理：</td>
        <td><input class="easyui-textbox" name="pmUserName" id="pmUserName" /></td>
        <td width="80" align="right" height="28">收费状态：</td>
        <td><select class="select_style easyui-combobox" editable="false" id="recFeeStatus" name="recFeeStatus">
          <option value="-1">--请选择--</option>
          <option value=1 >未收费</option>
		  <option value=2 >已收费</option>
		  <option value=5>放款申请中</option>
		  <option value=3 >已放款</option>
          <option value=4 >放款未完结</option>
        </select></td>
        <td width="100" align="right" height="28">卖方：</td>
        <td colsapn="2"><input class="easyui-textbox" name="sellerName" id="sellerName"/></td>
        <td width="100" align="right" height="28">买方：</td>
        <td colsapn="2"><input class="easyui-textbox" name="buyerName" id="buyerName"/></td>
       </tr>
       <tr>
       <td width="100" align="right" height="28">原按揭银行：</td>
        <td colsapn="2"><input class="easyui-textbox" name="oldLoanBank" id="oldLoanBank" /></td>
        <td width="100" align="right" height="28">单据类型：</td>
        <td colsapn="2"><select class="select_style easyui-combobox" editable="false" name="innerOrOut" id="innerOrOut">
          <option value="-1">--请选择--</option>
          <option value=1 >内单</option>
		  <option value=2 >外单</option>
        </select></td>
        <td width="80" align="right">贷款审批节点：</td>
        <td><select class="select_style easyui-combobox" editable="false" name="foreclosureStatus" id="foreclosureStatus">
          <option value="-1">--请选择--</option>
          <option value=1 >待客户经理提交</option>
		  <option value=2 >待部门经理审批</option>
		  <option value=3 >待业务总监审批</option>
		  <option value=4 >待审查员审批</option>
		  <option value=14 >待审查主管审批</option>
		  <option value=5 >待风控初审</option>
		  <option value=6 >待风控复审</option>
		  <option value=7 >待风控终审</option>
		  <option value=8 >待风控总监审批</option>
		  <option value=9 >待总经理审批</option>
		  <option value=10 >已审批</option>
		  <option value=11 >已放款</option>
		  <option value=12 >业务办理已完成</option>
		  <option value=13 >已归档</option>
		  <option value=15 >待合规复审</option>
        </select></td>
        <td width="80" align="right" height="28">业务办理节点：</td>
        <td ><select class="select_style easyui-combobox" editable="false" name="bizHandleDynamic" id="bizHandleDynamic">
          <option value="">--请选择--</option>
          <option value='发放贷款' >发放贷款&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
		  <option value='赎楼' >赎楼</option>
		  <option value='取旧证' >取旧证</option>
		  <option value='注销抵押' >注销抵押</option>
		  <option value='过户' >过户</option>
		  <option value='取新证' >取新证</option>
		  <option value='抵押' >抵押</option>
		  <option value='回款' >回款</option>
        </select></td>
       </tr>
       <tr>
        <td align="right" height="28" width="120">办理动态完成日期：</td>
        <td colspan="3">
        <select class="select_style easyui-combobox" editable="false" name="handleFlowId" id="handleFlowId">
          <option value="">--请选择--</option>
          <option value=1 >发放贷款&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
          <option value=2 >赎楼</option>
          <option value=3 >取旧证</option>
          <option value=4 >注销抵押</option>
          <option value=5 >过户</option>
          <option value=6 >取新证</option>
          <option value=7 >抵押</option>
          <option value=10 >回款</option>
        </select>
        <input class="easyui-datebox" editable="false" name="handleFlowStartDate" id="handleFlowStartDate" />

                   至 <input class="easyui-datebox" editable="false" name="handleFlowEndDate" id="handleFlowEndDate"/></td>
        <td align="right" height="28" width="120">放款日期：</td>
        <td colspan="3"><input class="easyui-datebox" editable="false" name="loanDate" id="loanDate"/>
        至 <input class="easyui-datebox" editable="false"
         name="loanEndDate" id="loanEndDate"/>
         </td>
       </tr>
       <tr>
       <td width="80" align="right" height="28">贷款金额：</td>
        <td colspan="3"><input class="easyui-numberbox" name="loanAmt" id="loanAmt" />
        	 至 <input class="easyui-numberbox"
         name="loanAmtMax"  id="loanAmtMax"/></td>
       <td width="80" align="right" height="28">回款状态：</td>
        <td>
        <select class="select_style easyui-combobox" editable="false" name="repaymentStatus" id="repaymentStatus">
          <option value="">--请选择--</option>
          <option value=1 >未回款</option>
          <option value=2 >已回款</option>
        </select>
        </td>
        </tr>
        <tr>
        <td width="80" align="right" height="28">项目编号：</td>
        <td><input class="easyui-textbox" name="projectNumber" id="projectNumber" /></td>
        <td colspan="10"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>

      </table>
     </div>
    </form>
    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
    <shiro:hasAnyRoles name="CXPORT_BUSINESS_SUMMARY_REPORT,ALL_BUSINESS">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportDifferWarn()">导出</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="业务汇总列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>reportController/businessSummaryReportList.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: false,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'projectId',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead data-options="frozen:true">
		<tr>
		 	<th data-options="field:'projectId',checkbox:true"></th>
       		<th data-options="field:'projectName',formatter:businessSummaryReportList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <thead>
      <tr>
       <!-- <th data-options="field:'projectId',checkbox:true"></th>
       <th data-options="field:'projectName',formatter:businessSummaryReportList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th> -->
       <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th>
       <th data-options="field:'businessSourceStr',sortable:true" align="center" halign="center">业务来源</th>
       <th data-options="field:'innerOrOut',sortable:true,formatter:formatterInnerOrOut" align="center" halign="center">单据类型</th>
       <th data-options="field:'productName',sortable:true" align="center" halign="center">产品名称</th>
       <th data-options="field:'loanAmt',sortable:true,formatter:formatMoney" align="center" halign="center">贷款金额</th>
       <th data-options="field:'realLoan',sortable:true,formatter:formatMoney" align="center" halign="center">放款金额</th>
       <th data-options="field:'loanDate',sortable:true,formatter:convertDate" align="center" halign="center">放款日期</th>
       <th data-options="field:'deptName',sortable:true" align="center" halign="center">业务部门</th>
       <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">业务经理</th>
       <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方</th>
       <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方</th>
       <th data-options="field:'foreclosureStatus',formatter:formatterProjectStatus,sortable:true" align="center" halign="center">贷款审批节点</th>
       <th data-options="field:'bizHandleDynamic',sortable:true" align="center" halign="center">业务办理节点</th>
       <th data-options="field:'planRecLoanDate',sortable:true,formatter:convertDate" align="center" halign="center">应回款日期</th>
       <th data-options="field:'repaymentStatus',sortable:true,formatter:formatRepaymentStatus" align="center" halign="center">回款状态</th>
       <th data-options="field:'recMoney',sortable:true,formatter:formatMoney" align="center" halign="center">已回款金额</th>
       <th data-options="field:'recLoanDate',sortable:true,formatter:convertDate" align="center" halign="center">回款日期</th>
       <th data-options="field:'remainingRecMoney',sortable:true,formatter:formatMoney" align="center" halign="center">未回款金额</th>
       <th data-options="field:'overdueFee',sortable:true,formatter:formatMoney" align="center" halign="center">逾期费</th>
       <th data-options="field:'overdueDay',sortable:true" align="center" halign="center">逾期天数</th>
       <th data-options="field:'extensionFee',sortable:true,formatter:formatMoney" align="center" halign="center">展期费</th>
       <th data-options="field:'extensionDate',sortable:true,formatter:convertDate" align="center" halign="center">展期日期</th>
       <th data-options="field:'interest',sortable:true,formatter:formatMoney" align="center" halign="center">咨询费</th>
       <th data-options="field:'poundage',sortable:true,formatter:formatMoney" align="center" halign="center">手续费</th>
       <th data-options="field:'recInterestDate',sortable:true,formatter:convertDate" align="center" halign="center">收费日期</th>
       <!-- <th data-options="field:'feeTotal',sortable:true,formatter:formatMoney" align="center" halign="center">费用合计</th> -->
       <th data-options="field:'oldLoanBank',sortable:true" align="center" halign="center">原按揭银行</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
