<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<link href="/BMS/upload/css/fileUpload.css" type="text/css"
	rel="stylesheet" />
<style type="text/css">
.today_titil {
	font-size:20px;
	font-weight:bold;
	text-align:center;
}
</style>

<script type="text/javascript">

var todayUpdate = { 
		searchData:function(){
			var selectDate = $('#selectDate').datebox('getValue');
			
			$('#todayToEnterprise').datagrid("options").url="<%=basePath%>afterLoanController/getToDayUpdateList.action?type=2&toDate="+selectDate;
			$('#todayToEnterprise').datagrid('reload');
			$('#todayToIndividual').datagrid("options").url="<%=basePath%>afterLoanController/getToDayUpdateList.action?type=1&toDate="+selectDate;
			$('#todayToIndividual').datagrid('reload');
		},
		// 业务类别格式化
		businessTypeFormat:function(value,row,index){
			if(value == 1){
				return "放款";
			}else if(value == 2){
				return "回本";
			}else if(value ==  4 ){
				return "退息";
			}else if(value ==  3 ){
				if(row.dtlType == 50){
					return "收息";
				}else if(row.dtlType == 40){
					return "收管理费";
				}else if(row.dtlType == 60){
					return "收其他费用";
				}else if(row.dtlType >-1000 && row.dtlType <0 ){
					return "收逾期利息";
				}else if(row.dtlType >-2000 && row.dtlType < -1000 ){
					return "收逾期罚息";
				}else{
					return "";
				}
			}else{
				return "";
			}
		},
		// 客户类别格式化
		acctTypFormat:function(value,row,index){
			if(value == 1){
				return "个人";
			}else if(value == 2){
				return "企业";
			}else if(value == 3){
				return "小计";
			}else{
				return "";
			}
		},
		// 是否抵押格式化
		isMortgageFormat:function(value,row,index){
			if(value == 0){
				return "否";
			}else if(value > 0){
				return "是";
			}else{
				return "";
			}
		},
		// 金额格式化
		amtFormat:function(value,row,index){
			if(value){
				var formatManey = accounting.formatMoney(value, "", 2, ",", ".");
				return formatManey;
			}else{
				return "-";
			}
		},// 序号格式化
		rowNumFormat:function(value,row,index){
			if(value == 0){
				return "";
			}
			return value;
		}
}
</script>
<body  class="easyui-layout">
	<div data-options="region:'center',border:false">
		<p>
		<div class="today_titil pt10" style="width: 80%">
			今日更新 ( ${year } 年  ${month } 月 ${day } 日 ) 
		</div>
		</p>
		<div>选择日期:<input name="selectDate" id="selectDate" value="${toDay}" class="easyui-datebox" editable="false"  data-options="onSelect:todayUpdate.searchData"/></div>
		<p>
		<div style="width: 100%; text-align: center;">
			<table id="todayToEnterprise" class="easyui-datagrid" style="width: 98%;"
				data-options="
			    url: '<%=basePath%>afterLoanController/getToDayUpdateList.action?type=2',
			    title:'${ENTERPRISE}',
			    height:300,
			    method: 'POST'
			    ">
				<!-- 表头 -->
				<thead>
					<tr>
						<th data-options="field:'rowNum',width:100,align:'center',formatter:todayUpdate.rowNumFormat">序号</th>
						<th data-options="field:'acctName',width:250,align:'center'">客户名称</th>
						<th data-options="field:'acctType',width:150,align:'center',formatter:todayUpdate.acctTypFormat">客户类别</th>
						<th data-options="field:'businessType',align:'center',width:100,formatter:todayUpdate.businessTypeFormat">业务类别</th>
						<th data-options="field:'outputAmt',width:250,align:'right',formatter:todayUpdate.amtFormat">金额</th>
						<th data-options="field:'pmUserName',width:150,align:'center'">业务经理</th>
						<th data-options="field:'isMortgage',width:100,align:'center',formatter:todayUpdate.isMortgageFormat">是否抵押</th>
					</tr>
				</thead>
			</table>
		</div>
		<p>
		<div style="width: 100%; text-align: center;">
			<table id="todayToIndividual" class="easyui-datagrid" style="width: 98%;"
				data-options="
			    url: '<%=basePath%>afterLoanController/getToDayUpdateList.action?type=1',
			    title:'${INDIVIDUAL}',
			    method: 'POST',
			    height:300
			    ">
				<!-- 表头 -->
				<thead>
					<tr>
						<th data-options="field:'rowNum',width:100,align:'center',formatter:todayUpdate.rowNumFormat">序号</th>
						<th data-options="field:'acctName',width:250,align:'center'">客户名称</th>
						<th data-options="field:'acctType',width:150,align:'center',formatter:todayUpdate.acctTypFormat">客户类别</th>
						<th data-options="field:'businessType',align:'center',width:100,formatter:todayUpdate.businessTypeFormat">业务类别</th>
						<th data-options="field:'outputAmt',width:250,align:'right',formatter:todayUpdate.amtFormat">金额</th>
						<th data-options="field:'pmUserName',width:150,align:'center'">业务经理</th>
						<th data-options="field:'isMortgage',width:100,align:'center',formatter:todayUpdate.isMortgageFormat">是否抵押</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>