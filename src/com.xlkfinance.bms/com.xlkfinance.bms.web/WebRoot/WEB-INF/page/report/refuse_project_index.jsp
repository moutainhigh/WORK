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
$(document).ready(function(){
	var productId = $("#productId").combobox('getValue');//产品ID
	$('#productId').combobox({   
		url:'${basePath}productController/getProductLists.action',    
	    valueField:'pid',
	    textField:'productName',
	    onLoadSuccess: function(rec){
	    	if(productId >0){
	    		$('#productId').combobox('setValue',productId);
	    	}else{
	    		$('#productId').combobox('setValue',-1);
	    	}
        } 
	});
})
	//重置按钮
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset')
	}

	var chechanReportList = {
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
				return va;
			},
			formatTooltip:function(value, row, index){
				if (value==null) {
					return "";
				}
		        var abValue = value;
	            if (value.length>=12) {
	               abValue = value.substring(0,10) + "...";
	            }
				var va = '<a href="#" title="' + value + '" class="note">' + abValue + '</a>';
	            return va;
			}
		}
	
</script>
<title>拒单列表</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/queryRefuseProject.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
			<div style="padding:5px">
				<table>
					<tr>
						<td width="80" align="right" >项目名称：</td>
						<td>
							<input name="projectName" id="projectName" class="easyui-textbox" style="width:150px;"/>
						</td>
						<td width="80" align="right" >项目编号：</td>
						<td>
							<input name="projectNumber" id="projectNumber" class="easyui-textbox" style="width:150px;"/>
						</td>
						<td width="80" align="right" >业务来源：</td>
						<td>
							<input name="businessSourceStr" id="businessSourceStr" class="easyui-textbox" style="width:150px;"/>
						</td>
					</tr>
					<tr>
						<td width="100" align="right"  height="28">产品名称：</td>
						<td colsapn="2">
							<input class="easyui-combobox" editable="false" name="productId" id="productId" style="width:150px;"/>						
						</td>
						<td width="100" align="right" >拒单时间：</td>
						<td colsapn="2">
							<input name="refuseTimeStart" id="refuseTimeStart" class="easyui-datebox" editable="false"/> <span>~</span> 
							<input name="refuseTimeEnd" id="refuseTimeEnd" class="easyui-datebox" editable="false"/>
						</td>
						<td colspan="2">
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
							<a href="#" onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;">重置</a>
						</td>
					</tr>			
				</table>
			</div>
    </form>
    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">

    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="拒单列表"  style="height: 100%; width: auto;"  class="easyui-datagrid"
     data-options="
		    url: '<%=basePath%>reportController/queryRefuseProject.action',
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
       		<th data-options="field:'projectName',formatter:chechanReportList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <thead>
      <tr>
       <th data-options="field:'businessSourceStr',sortable:true" align="center" halign="center">业务来源</th>
       <th data-options="field:'productName',sortable:true" align="center" halign="center">产品名称</th>
       <th data-options="field:'acctName',sortable:true" align="center" halign="center">客户名称</th>
       <th data-options="field:'loanMoney',sortable:false" align="center" halign="center">借款金额</th>
       <th data-options="field:'refuseUserName',sortable:true" align="center" halign="center">拒单人员</th>
       <th data-options="field:'refuseTime',sortable:true" align="center" halign="center">拒单日期</th>
       <th data-options="field:'refuseReason',formatter:chechanReportList.formatTooltip,sortable:true,width:100" align="center" halign="center">拒单原因</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
