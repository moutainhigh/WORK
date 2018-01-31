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

	$('#grid').datagrid({  
		 onDblClickRow: function (rowIndex, rowData){
			 var url="";
			 url = "${basePath}consumeProjectInfoController/toEditConsumeProject.action?type=2&projectId="+rowData.pid+"&param='projectId':'"+rowData.pid+"','projectName':'"+rowData.projectName+"','WORKFLOW_ID':'"+rowData.loanWorkProcess+"'";
			 parent.openNewTab(url,"查看消费贷款申请",true);
		 }
	 });
	setCombobox3("loanTerm","LOAN_TERM_CAL","-1");
	
	//计算器窗口关闭时，清空表单数据
 	$('#calloan_dialog').dialog({
 	    onClose:function(){
			//清空表单数据
 	    	$('#calLoanMoneyForm').form('reset');
 	    	$('#cal_loan_grid').datagrid('loadData',[]);
 	    	//$("#cal_loan_grid").datagrid('reload');
 	    }
 	});
})

//重置按钮
function majaxReset(){
	$('#searchFrom').form('reset');
}
//格式所有datebox的长度,默认全部设置为100px
$(document).ready(function(){
	$('.easyui-datebox').datebox({    
		width:150   
	});  

});

// 新增消费贷项目信息
function addItem(){
	parent.openNewTab("${basePath}consumeProjectInfoController/toEditConsumeProject.action?type=2","新增消费贷申请");
}

// 修改消费贷项目信息
function editItem() {
	// 获取选中的行
	var row = $('#grid').datagrid('getSelections'), isEdit = 2, loginId = "${shiroUser.pid}";
	// 保证必须选取客户数据
	if(row.length == 0){
		$.messager.alert("操作提示","请选择项目信息!","error");
		return false;
	}
	if(row[0].foreclosureStatus > 1) {
		$.messager.alert("操作提示","贷款申请提交流程后不允许进行编辑！","error");
		return;
	}
	if(loginId != row[0].recordClerkId && loginId != row[0].pmUserId){
		$.messager.alert("操作提示","贷款申请只有收单人以及录单员才能进行编辑！","error");
		return;
	}
	var url ="${basePath}consumeProjectInfoController/toEditConsumeProject.action?type="+ isEdit +"&projectId="+row[0].pid;
	var datas = getTaskVoByWPDefKeyAndRefId(row[0].loanWorkProcess, row[0].pid);
	if(datas) {
		url += "&"+datas;
	}
	parent.openNewTab(url, "编辑消费贷申请");
}

function lookupItem(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
		url = "${basePath}consumeProjectInfoController/toEditConsumeProject.action?type=2&showType=2&projectId="+row[0].pid+"&param='projectId':'"+row[0].pid+"','projectName':'"+row[0].projectName+"','WORKFLOW_ID':'"+row[0].loanWorkProcess+"'";
		parent.openNewTab(url,"查看消费贷款申请",true);
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
var foreInformationList = {
		// 项目名称format
		formatProjectName:function(value, row, index){
			var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.pid+")'> <font color='blue'>"+row.projectName+"</font></a>";
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
		},
		// 贷款金额(复审额度)format
		formatProjectGuaranteeMoney:function(value, row, index){
			if(row.foreclosureStatus == 5){
				return value;
			}else{
				return "--";
			}
		}
	}
//打开可贷金额计算器页面
function openCalLoan(){
	$('#calloan_dialog').dialog('open').dialog('setTitle',"可贷金额计算器");
}

function subForm(){
	var loanTerm = $("#loanTerm").combobox("getText");
	$("#loanTermVal").val(loanTerm);
	var loanCoef = 0.0;
	if(loanTerm == 36){
		loanCoef = 0.68;
	}else if(loanTerm == 24){
		loanCoef = 0.78;
	}else if(loanTerm == 12){
		loanCoef = 0.88;
	}else{
		$.messager.alert("操作提示","请选择期限","error");
		return;
	}
	var rentMoney = $("#rentMoney").numberbox("getValue");
	if(rentMoney>0){
		
	}else{
		$.messager.alert("操作提示","请输入正确的签约租金","error");
		return;
	}
	
	var feeRate = $("#feeRate").numberbox("getValue");
	if(feeRate>0){
		
	}else{
		$.messager.alert("操作提示","请输入正确的月利率","error");
		return;
	}
	$("#loanCoef").val(loanCoef);
	$("#cal_loan_grid").datagrid('load', $.serializeObject($("#calLoanMoneyForm")));
}
</script>
<title>贷前管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<!--图标按钮 -->
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="<%=basePath%>projectInfoController/getProjectByPage.action" method="post" id="searchFrom" name="searchFrom" >
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
						<td width="80" align="right" >待办理节点：</td>
						<td>
							<select class="select_style easyui-combobox" editable="false" name="foreclosureStatus"  style="width:150px;">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${project.foreclosureStatus==1 }">selected </c:if>>待提交</option>
								<option value="2" <c:if test="${project.foreclosureStatus==2 }">selected </c:if>>待初审</option>
								<option value="3" <c:if test="${project.foreclosureStatus==3 }">selected </c:if>>待下户</option>
								<option value="4" <c:if test="${project.foreclosureStatus==6 }">selected </c:if>>待复审</option>
								<option value="5" <c:if test="${project.foreclosureStatus==7 }">selected </c:if>>复审通过</option>
							</select>
						</td>
						<td width="80" align="right" >录单员：</td>
						<td>
							<input name="declaration" id="declaration" class="easyui-textbox" style="width:150px;"/>
						</td>
						<td width="80" align="right" height="28">收单人：</td>
						<td>
							<input name="realName" id="realName" class="easyui-textbox" style="width:150px;"/>
						</td>
					</tr>
					<tr>
						<td width="100" align="right" >提单日期：</td>
						<td colsapn="2">
							<input name="beginRequestDttm" id="beginRequestDttm" class="easyui-datebox" editable="false"/> <span>~</span> 
							<input name="endRequestDttm" id="endRequestDttm" class="easyui-datebox" editable="false"/>
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
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-setting" plain="true" onclick="openCalLoan()">可贷金额计算器</a>
		</div>
	</div>
	<div class="dksqListDiv" style="height:100%;">
	<table id="grid" title="贷款申请列表"
		style="height:100%;width: auto;" class="easyui-datagrid"
		data-options="
		    url: '<%=basePath%>consumeProjectInfoController/getConsumeProjectByPage.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true, 
		    pagination: true,
		    sortOrder:'desc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid', 
		    fitColumns:true">
		<thead data-options="frozen:true">
           <tr>
            <th data-options="field:'pid',checkbox:true" ></th>
		    <th data-options="field:'projectName',formatter:foreInformationList.formatProjectName" align="center" halign="center" >项目名称</th>
           </tr>
        </thead>
		<thead><tr>
			<th data-options="field:'projectNumber'" align="center" halign="center">项目编号</th>
		    <th data-options="field:'productName'" align="center" halign="center"  >产品名称</th>
		    <th data-options="field:'businessSourceStr'" align="center" halign="center">业务来源</th>
		    <th data-options="field:'acctName'" align="center" halign="center">客户名称</th>
		    <th data-options="field:'applyLoanMoney',formatter:formatMoney" align="center" halign="center"  >贷款金额</th>
		    <th data-options="field:'loanMoney',formatter:foreInformationList.formatProjectGuaranteeMoney" align="center" halign="center"  >复审额度</th>
		    <th data-options="field:'foreclosureStatus',formatter:formatterProjectStatus" align="center" halign="center">办理节点</th>
		    <th data-options="field:'nextUserName'" align="center" halign="center"  >待办人</th>
		    <th data-options="field:'requestDttm'" align="center" halign="center">提单日期</th>
		    <th data-options="field:'declaration'" align="center" halign="center">录单员</th>
		    <th data-options="field:'realName'" align="center" halign="center"  >收单人</th>
		</tr></thead>
	</table>
	</div>
	
	<div id="calloan_dialog" class="easyui-dialog" data-options="modal:true" buttons="#subCalloanDiv" style="width: 980px; height: 450px; padding:10px;" closed="true">
     <!-- 消费贷可贷金额计算 -->
    <form id="calLoanMoneyForm" name="calLoanMoneyForm" action="${basePath}consumeProjectInfoController/makeBizCalLoanMoney.action" method="post">
    <table border="0" cellpadding="0" cellspacing="0" >
    <tr>
        <td class="label_right1">签约租金:</td>
        <td>
			<input name="rentMoney" id="rentMoney" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','">
		</td>
    	<td class="label_right1">月利率(%)：</td>
		<td>
			 <input name="feeRate" id="feeRate" class="easyui-numberbox" data-options="min:0,max:100,precision:2,groupSeparator:','"/>
		</td>

    	<td class="label_right1">期限:</td>
    	<td>
        	<input id="loanTerm" class="easyui-combobox" editable="false" style="width: 125px;"/>
       		<input type="hidden" name="loanTerm" id="loanTermVal">
       		<input type="hidden" name="loanCoef" id="loanCoef">
       </td>
       <td>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="subForm()">生成</a>
	  </td>
      </tr>
       </table>
    </form>
     <!-- 可贷金额列表 -->
    <table id="cal_loan_grid"  title="可贷金额列表" class="easyui-datagrid" style="height: 90%; width: 98%;"
     data-options="
     		url: '<%=basePath%>consumeProjectInfoController/makeBizCalLoanMoney.action',
		    method: 'POST',
		    rownumbers: true,
		    pagination: false,
		    sortOrder:'asc',
		    remoteSort:false,
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'rentTerm'" align="center" halign="center">租赁期限</th>
       <th data-options="field:'rentMoney',formatter:formatMoney" align="center" halign="center">签约租金</th>
       <th data-options="field:'rentalReturn',formatter:formatMoney" align="center" halign="center">租金回报</th>
       <th data-options="field:'loanMoney',formatter:formatMoney" align="center" halign="center">借款金额</th>
       <th data-options="field:'loanTerm'" align="center" halign="center">借款期限</th>
       <th data-options="field:'repaymentMoneyTotal',formatter:formatMoney" align="center" halign="center">总还款</th>
       <th data-options="field:'monthlyRepaymentMoney',formatter:formatMoney" align="center" halign="center">月还款</th>
       <th data-options="field:'payMoney',formatter:formatMoney" align="center" halign="center">借款人承担</th>
       <th data-options="field:'resultMoney',formatter:formatMoney" align="center" halign="center">租金回报-总还款</th>
      </tr>
     </thead>
    </table>
   </div>
   <!-- 消费贷可贷金额计算 -->
	</div>
	</div>
</body>
</html>
