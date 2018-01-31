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
	function formatterLoanWay(val, row) {
		if (val == 1) {
			return "按揭";
		} else if (val == 2) {
			return "组合贷";
		} else if (val == 3) {
			return "公积金贷";
		} else if (val == 4) {
			return "一次性付款";
		} else if (val == 5) {
			return "经营贷";
		} else if (val == 6) {
			return "消费贷";
		} else {
			return "未知";
		}
	}
	function cxportDifferWarn() {
			$.ajax({
					url:BASE_PATH+'templateFileController/checkFileUrl.action',
					data:{templateName:'YJTJBB'},
					dataType:'json',
					success:function(data){
						if(data==1){
							var projectName =$("#projectName").textbox('getValue');
							var productName =$("#productName").textbox('getValue');
							
							var businessSource =$("#businessSource").combobox('getValue');
							var deptName =$("#deptName").textbox('getValue');
					          
							var pmUserName =$("#pmUserName").textbox('getValue');
							var applyUserName =$("#applyUserName").textbox('getValue');
							
					        var loanBank =$("#loanBank").textbox('getValue');
							var transferOperator =$("#transferOperator").textbox('getValue');
							var sellerName =$("#sellerName").textbox('getValue');
							var buyerName =$("#buyerName").textbox('getValue');
							
					        var foreclosureFloorDate =$("#foreclosureFloorDate").textbox('getValue');
							var foreclosureFloorEndDate =$("#foreclosureFloorEndDate").textbox('getValue');
							
					        var transferDate =$("#transferDate").textbox('getValue');
							var transferEndDate =$("#transferEndDate").textbox('getValue');
							
					        var getNewLicenseDate =$("#getNewLicenseDate").textbox('getValue');
							var getNewLicenseEndDate =$("#getNewLicenseEndDate").textbox('getValue');
							
							var mortgageDate =$("#mortgageDate").textbox('getValue');
							var mortgageEndDate =$("#mortgageEndDate").textbox('getValue');
																																																																												    
							window.location.href ="${basePath}reportController/differWarnExcelExportList.action?projectName="+projectName+"&productName="+productName+"&businessSource="+businessSource+
									"&deptName="+deptName+"&pmUserName="+pmUserName+"&applyUserName="+applyUserName+"&loanBank="+loanBank+"&transferOperator="+transferOperator+"&sellerName="+sellerName+
									"&buyerName="+buyerName+"&foreclosureFloorDate="+foreclosureFloorDate+"&foreclosureFloorEndDate="+foreclosureFloorEndDate+"&transferDate="+transferDate+"&transferEndDate="+transferEndDate+
									"&getNewLicenseDate="+getNewLicenseDate+"&getNewLicenseEndDate="+getNewLicenseEndDate+"&mortgageDate="+mortgageDate+"&mortgageEndDate="+mortgageEndDate+"&page=-1";
// 							window.location.href ="${basePath}reportController/differWarnExcelExportList.action?projectIds="+projectIds;
						}else{
							alert('预警统计报表的导出模板不存在，请上传模板后重试');
						}
					}
				})
	}
	function operateLoadBusiness(value, row, index) {
		return '<a href="javascript:void(0)" onclick="openDifferWarnRemark('+ row.projectId+')"><font color="blue">查看</font></a>';
	}
	// 项目名称format
	function formatProjectName(value, row, index) {
		var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
		return va;
	}
	//预警备注列表
	function openDifferWarnRemark(projectId) {
		var url = "";// 路径
		$('#grid_hisDifferWarnList').datagrid({  
			url:'<%=basePath%>bizHandleController/hisDifferWarnList.action?projectId='+projectId,  
		}); 
		$('#feedback-edit').dialog('open').dialog('setTitle', "预警备注列表");
	}
	//初始化
	$(document).ready(function() {
		//差异预警处理备注窗口关闭时，清空数据
	 	$('#feedback-edit').dialog({
	 	    onClose:function(){
	 	    	$('#grid_hisDifferWarnList').datagrid('loadData',{total:0,rows:[]}); 
	 	    }
	 	});
	});
</script>
<title>预警统计报表</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/differWarnReportList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td width="80" align="right" height="28">项目名称：</td>
        <td><input class="easyui-textbox" name="projectName"  id="projectName"/></td>
        <td width="80" align="right" height="28">产品名称：</td>
        <td><input class="easyui-textbox" name="productName" id="productName"/></td>
        <td width="80" align="right">业务来源：</td>
        <td>
        <input name="businessSource" id="businessSource" class="easyui-combobox" editable="false" panelHeight="auto"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=BUSINESS_SOURCE'" />
       </td>
        <td width="100" align="right" height="28">业务部门：</td>
        <td colsapn="2"><input class="easyui-textbox" name="deptName" id="deptName"/></td>
        <td width="100" align="right" height="28">客户经理：</td>
        <td colsapn="2"><input class="easyui-textbox" name="pmUserName" id="pmUserName" /></td>
       </tr>
       <tr>
        <td width="80" align="right" height="28">业务申请人：</td>
        <td><input class="easyui-textbox" name="applyUserName" id="applyUserName"/></td>
        <td width="80" align="right">贷款银行：</td>
        <td><input class="easyui-textbox" name="loanBank" id="loanBank"/></td>
        <td width="80" align="right" height="28">过户专员：</td>
        <td><input class="easyui-textbox" name="transferOperator" id="transferOperator" /></td>
        <td width="100" align="right" height="28">卖方：</td>
        <td colsapn="2"><input class="easyui-textbox" name="sellerName" id="sellerName"/></td>
        <td width="100" align="right" height="28">买方：</td>
        <td colsapn="2"><input class="easyui-textbox" name="buyerName" id="buyerName"/></td>
       </tr>
       <tr>
        <td align="right" height="28" width="120">赎楼日期：</td>
        <td colspan="3"><input class="easyui-datebox" editable="false" name="foreclosureFloorDate" id="foreclosureFloorDate"/> 至
         <input class="easyui-datebox" editable="false"
         name="foreclosureFloorEndDate"  id="foreclosureFloorEndDate"
        /></td>
        <td align="right" height="28" width="120">过户申请递件日期：</td>
        <td colspan="3"><input class="easyui-datebox" editable="false" name="transferDate" id="transferDate"/> 至
         <input class="easyui-datebox" editable="false"
         name="transferEndDate" id="transferEndDate"
        /></td>
       </tr>
       <tr>
        <td align="right" height="28" width="120">领新房产证日期：</td>
        <td colspan="3"><input class="easyui-datebox" editable="false" name="getNewLicenseDate" id="getNewLicenseDate"/> 至 <input class="easyui-datebox" editable="false"
         name="getNewLicenseEndDate" id="getNewLicenseEndDate"
        /></td>
        <td align="right" height="28" width="120">抵押递件日期：</td>
        <td colspan="5"><input class="easyui-datebox" editable="false" name="mortgageDate" id="mortgageDate"  /> 至 
        <input class="easyui-datebox" editable="false"
         name="mortgageEndDate"  id="mortgageEndDate"
        /></td>
       </tr>
       <tr>
        <td colspan="8"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>
       
      </table>
     </div>
    </form>
    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
    <shiro:hasAnyRoles name="CXPORT_DIFFER_WARN_REPORT,ALL_BUSINESS">
     <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportDifferWarn()">导出</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="预警统计列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>reportController/differWarnReportList.action',
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
       		<th data-options="field:'projectName',formatter:formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <thead>
      <tr>
       <!-- <th data-options="field:'projectId',checkbox:true"></th>
       <th data-options="field:'projectName',formatter:formatProjectName,sortable:true" align="center" halign="center">项目名称</th> -->
       <!-- <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th> -->
       <th data-options="field:'productName',sortable:true" align="center" halign="center">产品名称</th>
       <th data-options="field:'businessSourceStr',sortable:true" align="center" halign="center">业务来源</th>
       <th data-options="field:'deptName',sortable:true" align="center" halign="center">业务部门</th>
       <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">客户经理</th>
       <th data-options="field:'applyUserName',sortable:true" align="center" halign="center">申请人</th>
       <th data-options="field:'loanBank',sortable:true" align="center" halign="center">贷款银行</th>
       <th data-options="field:'loanAmt',sortable:true,formatter:formatMoney" align="center" halign="center">贷款金额（元）</th>
       <th data-options="field:'houseName',sortable:true" align="center" halign="center">物业名称</th>
       <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方姓名</th>
       <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方姓名</th>
       <!-- <th data-options="field:'houseType',sortable:true" align="center" halign="center">物业类型</th> -->
       <th data-options="field:'loanWay',sortable:true,formatter:formatterLoanWay" align="center" halign="center">贷款方式</th>
       <th data-options="field:'foreclosureFloorDate',sortable:true,formatter:convertDate" align="center" halign="center">赎楼日期</th>
       <th data-options="field:'cancelMortgageDate',sortable:true,formatter:convertDate" align="center" halign="center">注销抵押登记日期</th>
       <th data-options="field:'transferDate',sortable:true,formatter:convertDate" align="center" halign="center">过户申请递件日期</th>
       <th data-options="field:'getNewLicenseDate',sortable:true,formatter:convertDate" align="center" halign="center">领新房产证日期</th>
       <th data-options="field:'mortgageDate',sortable:true,formatter:convertDate" align="center" halign="center">抵押递件日期</th>
       <th data-options="field:'cancelGuaranteeDate',sortable:true,formatter:convertDate" align="center" halign="center">解除担保日期</th>
       <th data-options="field:'transferOperator',sortable:true" align="center" halign="center">过户专员</th>
       <th data-options="field:'yyy',formatter:operateLoadBusiness" align="center" halign="center">预警跟进标记</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="feedback-edit" class="easyui-dialog" buttons="#upFileinfo" style="width: 1013px; height: 448px; padding: 10px 20px;" closed="true">
    <table id="grid_hisDifferWarnList" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'projectName',sortable:true" align="center" halign="center">项目名称</th>
       <th data-options="field:'flowName',sortable:true" align="center" halign="center">办理动态</th>
       <th data-options="field:'differ',sortable:true" align="center" halign="center">差异</th>
       <th data-options="field:'handleAuthorName',sortable:true" align="center" halign="center">处理者</th>
       <th data-options="field:'handleDate',sortable:true,formatter:convertDateTime" align="center" halign="center">处理时间</th>
       <th data-options="field:'remark',sortable:true,width:120" align="center" halign="center">备注</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
