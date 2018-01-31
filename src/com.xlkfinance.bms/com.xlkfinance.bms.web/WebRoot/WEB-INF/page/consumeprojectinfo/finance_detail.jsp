<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
	<div class="datum1" style="padding:30px 10px 10px 10px;width:1039px; ">
		<div id="financebar_data">
			<div style="padding-bottom:5px">
				<!-- <a href="javascript:void(0)" class="easyui-linkbutton" id="exportData" iconCls="icon-export" plain="true">导出</a> -->
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-setting" plain="true" onclick="openReceDialog()">生成放款时间</a>
			</div>
		</div>
		<div style="height:400px" class="datum"> 
			<table id="grid" title="财务明细" class="easyui-datagrid" style="height:100%;width: auto;"
		     data-options="
		      url: '<%=basePath%>consumeProjectInfoController/getRepaymentPlan.action?projectId='+projectId,
		      method: 'POST',
		      rownumbers: true,
		      singleSelect: false,
		      pagination: false,
		      toolbar: '#financebar_data',
		      sortOrder:'asc',
		      remoteSort:false,
		      idField: 'pid',
		      fitColumns:true">
	     	<!-- 表头 -->
		     <thead>
		      <tr>
		       <th data-options="field:'planRepayDt'" align="center" halign="center">应还日期</th>
		       <th data-options="field:'planCycleNum',formatter:formatPlanCycleNum" align="center" halign="center">期数</th>
		       <th data-options="field:'rentMoney',formatter:formatMoney" align="center" halign="center">签约租金</th>
		       <th data-options="field:'principalBalance',formatter:formatMoney" align="center" halign="center">剩余本金</th>
		       <th data-options="field:'total',formatter:formatMoney" align="center" halign="center">应还合计</th>
		       <th data-options="field:'shouldPrincipal',formatter:formatMoney" align="center" halign="center">应还本金</th>
		       <th data-options="field:'shouldInterest',formatter:formatMoney" align="center" halign="center">应还利息</th>
		       <th data-options="field:'shouldPaymentMoney',formatter:formatMoney" align="center" halign="center">借款人应还</th>
		      </tr>
		     </thead>
	    </table>
	    </div>
	    	<!-- 驳回操作开始 -->
		 <div id="rece_dialog" class="easyui-dialog" buttons="#rece_dialogDiv" style="width: 400px; height: 200px; padding: 10px;" closed="true">
		   <form id="receForm" name="receForm" action="" method="post">
		     <table title="生成放款时间" style="width: 62%; height: 50px; margin: 0 auto;">
		     	<input type="hidden" name="projectId" id="projectId">
				<tr>
					<td class="label_right1"><font color="red">*</font>放款时间：</td>
					<td>
						<input name="receDate" id="receDate" required="true" data-options="required:true" class="easyui-datebox" editable="false" style="width:135px;"/>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="text-align: center; padding-top: 15px;color: red;">一旦选择放款时间后，自动生成应还日期不可修改，慎选！</td>
				</tr>
		     </table>
		    </form>
		   </div>
		   <div id="rece_dialogDiv">
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subForm()">确定</a> 
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#rece_dialog').dialog('close')">取消</a>
		   </div>
		<!-- 驳回操作结束 -->
</div>
<script type="text/javascript">
	function openReceDialog(){
		var rows = $('#grid').datagrid('getRows');
		if(rows.length>0 && rows[0].rentMoney>0){
			var plan = rows[0].planRepayDt;
			if(plan == null ||plan == ""){
				$('#rece_dialog').dialog('open').dialog('setTitle', "设置放款时间");
			}else{
				$.messager.alert("操作提示","该项目已生成放款时间！","error");
				return ;
			}
		}else{
			$.messager.alert("操作提示","该项目未审批结束！","error");
			return ;
		}
		
	}
	function subForm(){
		var flag = $("#receForm").form('validate');
		if(flag){
			var receDate = $("#receDate").datebox('getValue');
			//var projectId = $('#projectId').val();
			//获取原贷款信息
			var url = "<%=basePath%>consumeProjectInfoController/getRepaymentPlan.action?projectId="+projectId+"&receDate="+receDate;
			$('#grid').datagrid('options').url = url;
			$('#grid').datagrid('reload');
			$('#rece_dialog').dialog('close');
		}
		
	}
	//formatMoney
	function formatPlanCycleNum(value,row,index){
		
		if(value == 0){
			return "-";
		}else{
			return value;
		}
	}
</script> 