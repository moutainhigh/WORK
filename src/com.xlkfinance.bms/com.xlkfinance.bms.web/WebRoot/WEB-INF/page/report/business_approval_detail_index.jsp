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
	function cxportBusinessApprovalDetail() {
		
			$.ajax({
					url : BASE_PATH + 'templateFileController/checkFileUrl.action',
					data : {templateName :'YWSPMXTJ'},
					dataType : 'json',
					success : function(data) {
						if (data == 1) {
// 							           

							var projectName =$("#projectName").textbox('getValue');
							var businessSourceStr =$("#businessSourceStr").textbox('getValue');
							var department =$("#department").textbox('getValue');
							var approvalPerson =$("#approvalPerson").textbox('getValue');
							
							var approvalStep =$("#approvalStep").textbox('getValue');
							var businessCatelog =$("#businessCatelog").combobox('getValue');
							
							var createDate =$("#createDate").textbox('getValue');
							var createEndDate =$("#createEndDate").textbox('getValue');
							
							var approvalDate =$("#approvalDate").textbox('getValue');
							var approvalEndDate =$("#approvalEndDate").textbox('getValue');
							
							
							window.location.href ="${basePath}reportController/businessApprovalDetailExportList.action?projectName="
									+projectName+"&businessSourceStr="+businessSourceStr+"&department="+department+"&approvalPerson="+approvalPerson+
									"&approvalStep="+approvalStep+"&businessCatelog="+businessCatelog+"&createDate="+createDate+"&createEndDate="+createEndDate+
									"&approvalDate="+approvalDate+"&approvalEndDate="+approvalEndDate+"&page=-1";
							
						} else {
							alert('业务审批明细报表的导出模板不存在，请上传模板后重试');
						}
					}
				})
	}
	var businessApprovalDetailList = {
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
				return va;
			}
		}
	function formatApprovalStatus(val, row) {
		if (val == 3) {
			return "通过";
		} else {
			return "不通过";
		}
	}
</script>
<title>业务审批明细</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/businessApprovalDetailList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->								
     <div style="padding: 5px">
      <table>
      <tr>
	     <td width="80" align="right">项目名称：</td>
	      <td><input class="easyui-textbox"   name="projectName" id="projectName"/></td>
	      <td width="80" align="right">业务来源：</td>
	      <td>
	       <input class="easyui-textbox" name="businessSourceStr" id="businessSourceStr" />
	      </td>
	       <td width="80" align="right">部门：</td>
		     <td><input class="easyui-textbox"   name="department" id="department"/>
		   </td>
	      <td width="80" align="right">操作人：</td>
		  <td>
		     	<input class="easyui-textbox"   id="approvalPerson" name="approvalPerson"/>
		   </td>
		   </tr>
		   <tr>
		  <td width="80" align="right">操作步骤：</td>
		  <td>
		     	<input class="easyui-textbox"   id="approvalStep" name="approvalStep"/>
		   </td>
	      <td width="80" align="right">业务类型：</td>
	      <td>
	         <select class="easyui-combobox" name="businessCatelog" id="businessCatelog" style="width: 125px" panelHeight="auto" editable="false">
	            <option value="" selected="selected">--请选择--</option>
	            <option value="交易现金赎楼">交易</option>
	            <option value="非交易现金赎楼">非交易</option>
	      </select>
	      </td>
       </tr> 
       <tr>
	       <td align="right" height="28" width="120">提单时间：</td>
	        <td colspan="3">
	        	<input class="easyui-datebox" editable="false" name="createDate"  id="createDate"/> 至 <input class="easyui-datebox" editable="false" name="createEndDate" id="createEndDate"/>
	         </td>
	        <td align="right" height="28" width="120">操作时间：</td>
	        <td colspan="3"> 
	        	<input class="easyui-datebox" editable="false" name="approvalDate" id="approvalDate" /> 至 <input class="easyui-datebox" editable="false" name="approvalEndDate" id="approvalEndDate"/>
	        </td>
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
    <shiro:hasAnyRoles name="EXPORT_BUSINESS_APPROVAL_DETAIL,ALL_BUSINESS">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportBusinessApprovalDetail()">导出</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="业务审批明细" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>reportController/businessApprovalDetailList.action',
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
     <thead data-options="frozen:true">
		<tr>
		 	<th data-options="field:'projectId',checkbox:true"></th>
         	<th data-options="field:'projectName',formatter:businessApprovalDetailList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <!-- 表头 -->
     <thead>
      <tr>
       <!-- <th data-options="field:'projectId',checkbox:true"></th>
       <th data-options="field:'projectName',formatter:businessApprovalDetailList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th> -->
       <th data-options="field:'businessSourceStr',sortable:true" align="center" halign="center">业务来源</th>
       
       <th data-options="field:'customerName',sortable:true" align="center" halign="center">客户名称</th>
       <th data-options="field:'realLoan',sortable:true,formatter:formatMoney" align="center" halign="center">放款金额</th>
       <th data-options="field:'businessCatelog',sortable:true" align="center" halign="center">业务类型</th>
       
       <th data-options="field:'createDate',sortable:true,formatter:convertDate" align="center" halign="center">提单时间</th>
       <th data-options="field:'approvalStep',sortable:true" align="center" halign="center">操作步骤</th>
       <th data-options="field:'approvalStatus',sortable:true,formatter:formatApprovalStatus" align="center" halign="center">操作状态</th>
       <th data-options="field:'department',sortable:true" align="center" halign="center">部门</th>
       <th data-options="field:'approvalPerson',sortable:true" align="center" halign="center">操作人</th>
       <th data-options="field:'approvalDate',sortable:true" align="center" halign="center">操作时间</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
