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
		var rows = $('#grid').datagrid('getSelections');
		var projectIds = "";
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				projectIds += rows[i].projectId;
			} else {
				projectIds += "," + rows[i].projectId;
			}
		}
		$
				.ajax({
					url : BASE_PATH
							+ 'templateFileController/checkFileUrl.action',
					data : {
						templateName : 'TRACKRECORD'
					},
					dataType : 'json',
					success : function(data) {
						if (data == 1) {
							window.location.href = "${basePath}reportController/trackRecordExcelExportList.action?projectIds="
									+ projectIds;
						} else {
							alert('业绩一览报表的导出模板不存在，请上传模板后重试');
						}
					}
				})
	}
	var trackRecordReportList = {
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
				return va;
			}
		}
</script>
<title>业绩一览表</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/trackRecordReportList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td width="80" align="right" height="28">项目名称：</td>
        <td><input class="easyui-textbox" name="projectName" /></td>
        <td width="80" align="right" height="28">产品名称：</td>
        <td><input class="easyui-textbox" name="productName" /></td>
        <td width="100" align="right" height="28">业务部门：</td>
        <td><input class="easyui-textbox" name="deptName" /></td>
       </tr>
       <tr>
        <td width="100" align="right" height="28">业务经理：</td>
        <td><input class="easyui-textbox" name="pmUserName" /></td>
        <td width="100" align="right" height="28">物业/买卖方：</td>
        <td colsapn="2"><input class="easyui-textbox" name="condition" /></td>
       </tr>
       <tr>
       <td width="100" align="right" height="28">新贷款银行：</td>
        <td colsapn="2"><input class="easyui-textbox" name="newLoanBank" /></td>
        <td width="100" align="right" height="28">单据类型：</td>
        <td colsapn="2"><select class="select_style easyui-combobox" editable="false" name="innerOrOut">
          <option value="-1">--请选择--</option>
          <option value=1 >内单</option>
		  <option value=2 >外单</option>
        </select></td>
       </tr>
       <tr>
        <td align="right" height="28" width="120">办理动态完成日期：</td>
        <td colspan="3">
        <select class="select_style easyui-combobox" editable="false" name="handleFlowId">
          <option value="">--请选择--</option>
          <option value=2 >赎楼&nbsp;&nbsp;</option>
          <option value=3 >取旧证</option>
          <option value=4 >注销抵押</option>
          <option value=5 >过户</option>
          <option value=6 >取新证</option>
          <option value=7 >抵押</option>
          <option value=10 >回款</option>
        </select>
        <input class="easyui-datebox" editable="false" name="handleFlowStartDate" /> 
                   至 <input class="easyui-datebox" editable="false"name="handleFlowEndDate"/></td>
        <td align="right" height="28" width="120">放款日期：</td>
        <td colspan="3"><input class="easyui-datebox" editable="false" name="loanDate" /> 至 <input class="easyui-datebox" editable="false"
         name="loanEndDate"
        /></td>
       </tr>
       <tr>
        <td colspan="10"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>

      </table>
     </div>
    </form>
    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportDifferWarn()">导出</a>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="业绩一览表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>reportController/trackRecordReportList.action',
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
       		<th data-options="field:'projectName',formatter:trackRecordReportList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <thead>
      <tr>
       <!-- <th data-options="field:'projectId',checkbox:true"></th>
       <th data-options="field:'projectName',formatter:trackRecordReportList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th> -->
       <th data-options="field:'innerOrOut',sortable:true,formatter:formatterInnerOrOut" align="center" halign="center">单据类型</th>
       <th data-options="field:'deptName',sortable:true" align="center" halign="center">业务部门</th>
       <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">客户经理</th>
       <th data-options="field:'productName',sortable:true" align="center" halign="center">产品名称</th>
       <th data-options="field:'housePropertyCard',sortable:true" align="center" halign="center">房产证号</th>
       <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方</th>
       <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方</th>
       <th data-options="field:'newLoanBank',sortable:true" align="center" halign="center">贷款银行</th>
       <th data-options="field:'oldLoanBank',sortable:true" align="center" halign="center">原按揭银行</th>
       <th data-options="field:'loanAmt',sortable:true,formatter:formatMoney" align="center" halign="center">贷款金额（元）</th>
       <th data-options="field:'interest',sortable:true,formatter:formatMoney" align="center" halign="center">咨询费</th>
       <th data-options="field:'requestDate',sortable:true,formatter:convertDate" align="center" halign="center">报单日期</th>
       <th data-options="field:'loanDate',sortable:true,formatter:convertDate" align="center" halign="center">放款日期</th>
       <th data-options="field:'foreclosureFloorDate',sortable:true,formatter:convertDate" align="center" halign="center">赎楼日期</th>
       <th data-options="field:'getOldLicenseDate',sortable:true,formatter:convertDate" align="center" halign="center">取旧证日期</th>
       <th data-options="field:'cancelMortgageDate',sortable:true,formatter:convertDate" align="center" halign="center">注销抵押日期</th>
       <th data-options="field:'transferDate',sortable:true,formatter:convertDate" align="center" halign="center">过户日期</th>
       <th data-options="field:'getNewLicenseDate',sortable:true,formatter:convertDate" align="center" halign="center">取新证日期</th>
       <th data-options="field:'mortgageDate',sortable:true,formatter:convertDate" align="center" halign="center">抵押日期</th>
       <th data-options="field:'recLoanDate',sortable:true,formatter:convertDate" align="center" halign="center">回款日期</th>
       <th data-options="field:'overdueFee',sortable:true,formatter:formatMoney" align="center" halign="center">逾期费</th>
       <th data-options="field:'overdueDay',sortable:true" align="center" halign="center">逾期天数</th>
       <th data-options="field:'extensionFee',sortable:true,formatter:formatMoney" align="center" halign="center">展期费</th>
       <th data-options="field:'extensionDays',sortable:true,formatter:convertDate" align="center" halign="center">展期天数</th>
       <th data-options="field:'declaration',sortable:true" align="center" halign="center">报单员</th>
       <th data-options="field:'houseClerkName',sortable:true" align="center" halign="center">赎楼员</th>
       <th data-options="field:'logoutName',sortable:true" align="center" halign="center">注销人</th>
       <th data-options="field:'assignedName',sortable:true" align="center" halign="center">过户人</th>
       <th data-options="field:'newCardName',sortable:true" align="center" halign="center">取新证人</th>
       <th data-options="field:'mortgageName',sortable:true" align="center" halign="center">抵押人</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
