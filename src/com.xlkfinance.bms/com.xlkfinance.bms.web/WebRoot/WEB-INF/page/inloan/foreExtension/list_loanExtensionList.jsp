<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/repayment/extensionApply/list_foreLoanExtensionList.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
var loginId = "${shiroUser.pid}";
$(document).ready(function(){
	$('#cusType').combobox({
		onSelect: function(row){
			if(row.value==2){
				$(".ecoTrade").show();
			}else{
				$(".ecoTrade").hide();
			}
		}
	});
	$(".ecoTrade").hide();
});
function formatterForeStatus(val,row){
	if(val == 1){
		return "待客户经理提交";
	}else if(val == 2){
		return "待部门经理审批";
	}else if(val == 3){
		return "待业务总监审批";
	}else if(val == 4){
		return "待审查员审批";
	}else if(val == 5){
		return "待风控初审";
	}else if(val == 6){
		return "待风控复审";
	}else if(val == 7){
		return "待风控终审";
	}else if(val == 8){
		return "待风控总监审批";
	}else if(val == 9){
		return "待总经理审批";
	}else if(val == 10){
		return "已审批";
	}else if(val == 11){
		return "已放款";
	}else if(val == 12){
		return "业务办理已完成";
	}else if(val == 13){
		return "已收费";
	}else if(val == 14){
		return "待审查主管审批";
	}else if(val == 15){
		return "待财务总监审批";
	}
}
/**
 * 展期编辑
 */
function openNewTab(extensionProjectId,projectId,userId){
	var url = BASE_PATH+"foreLoanExtensionController/toEditLoanExtensionApply.action?projectId="+extensionProjectId+"&newProjectId="+projectId;
	
	if(loginId != userId){
		$.messager.alert("操作提示","只有经办人才能进行编辑！","error");
		return;
	}
	var isWantong=1;
	$.ajax({
		url : BASE_PATH+ "beforeLoanController/checkUserOrgInfo.action",
		async : false,
		success : function(data) {
			isWantong = data;
		}
	});
	
	var workProcess="";
	
	if(isWantong == 1){
		workProcess="foreExtensionRequestProcess";
	}else if(isWantong == 2){
		workProcess="creditExtensionRequestProcess";
	}
	//根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
	var datas = getTaskVoByWPDefKeyAndRefId(workProcess,projectId);
	if(datas){
		url+="&"+datas;
	}
	parent.openNewTab(url,"展期申请编辑",true);
}
/**
 * 展期删除
 */
function removeExtensionPorject(pid,projectId,userId){
	if(loginId != userId){
		$.messager.alert("操作提示","只有经办人才能进行编辑！","error");
		return;
	}
	
	$.messager.confirm("操作提示","确定删除选择的展期申请吗?",
		function(r) {
 			if(r){
				$.post("removeExtensionProject.action",{pid : pid,projectId:projectId}, 
					function(ret) {
						if(ret && ret.header["success"]){
							$.messager.alert('操作提示',"删除成功!",'info');
							$("#grid").datagrid('reload');
						}else{
							$.messager.alert('操作提示',ret.header["msg"],'error');	
						}
					},'json');
 			}
		}
 	);
}

var foreExtensionList = {
    	formatOperate:function(value, row, index){
    		// 只要申请中的展期 才能编辑删除
			if( 1 != row.requestStatus){
				return "<font color='grey'>编辑</font>";
			}
			var edit="<a href='javascript:openNewTab("+row.extensionProjectId+','+row.projectId+','+row.pmUserId+")'><font color='blue'>编辑</font></a>";
			var remove = "|<a href='javascript:void(0)' onclick='removeExtensionPorject("+row.pid+','+row.projectId+','+row.pmUserId+")'><font color='blue'> 删除</font></a>";
			return edit;
		}
	}
	
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<div>
			<!-- 查询条件 -->
			<form action="extentionRequestList.action" method="post" id="searcm"
				name="searcm">
				<div style="padding: 5px">
					<table class="beforeloanTable" width="90%">
						<tr>
							<td class="label_right">项目名称：</td>
							<td><input class="easyui-textbox" name="projectName" style="width:220px"  id="projectName" />
							</td>
							<td class="label_right">项目编号: </td>
							<td><input class="easyui-textbox" name="projectNum" style="width:220px"  id="projectNum" />
								
							</td>
						</tr>
						<tr>
							<td class="label_right1">处理状态：</td>
							<td>
							<select class="select_style easyui-combobox" editable="false" name="requestStatus" id="requestStatus">
								<option value="-1">--请选择--</option>
								<option value="1" <c:if test="${project.foreclosureStatus==1 }">selected </c:if>>待客户经理提交</option>
								<option value="2" <c:if test="${project.foreclosureStatus==2 }">selected </c:if>>待部门经理审批</option>
								<option value="5" <c:if test="${project.foreclosureStatus==5 }">selected </c:if>>待风控初审</option>
								<option value="6" <c:if test="${project.foreclosureStatus==6 }">selected </c:if>>待风控复审</option>
								<option value="7" <c:if test="${project.foreclosureStatus==7 }">selected </c:if>>待风控终审</option>
								<option value="8" <c:if test="${project.foreclosureStatus==8 }">selected </c:if>>待风控总监审批</option>
								<option value="15" <c:if test="${project.foreclosureStatus==15 }">selected </c:if>>待财务总监审批</option>
								<option value="9" <c:if test="${project.foreclosureStatus==9 }">selected </c:if>>待总经理审批</option>
								<option value="10" <c:if test="${project.foreclosureStatus==10 }">selected </c:if>>已审批</option>
								<option value="13" <c:if test="${project.foreclosureStatus==13 }">selected </c:if>>已收费</option>
								<option value="15" <c:if test="${project.foreclosureStatus==15 }">selected </c:if>>待合规复审</option>
							</select>
							</td>
						</tr>
						<tr>
							<td class="label_right">展期申请时间: </td>
							<td colspan="3">
								<input class="easyui-datebox"
									name="requestDttm" id="requestDttm" editable="false" /> <span>~</span>
								<input class="easyui-datebox" name="requestDttmLast"
									id="requestDttmLast" editable="false" />
							<input type="hidden" id="rows" name="rows">
							<input type="hidden" id="page" name="page" value='1'>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
								href="javascript:void(0);" class="easyui-linkbutton"
								iconCls="icon-search" onclick="loanExtensionList.searchData()">查询</a>
								<a href="javascript:void(0);" iconCls="icon-clear" onclick="javascript:$('#searcm').form('reset');resetss()" class="easyui-linkbutton" style="width: 60px;">重置</a>
							</td>							
						</tr>
					</table>	
						<div >
	</div>		
	
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="loanExtensionList.batchRemove()">删除</a> -->
				</div>
			</form>
	
	
	</div>
	
		<!-- 操作按钮 -->
		
	</div>

	<div class="loanExtensionListDiv" style="height:100%">
	
	<table id="grid" title="展期申请列表" class="easyui-datagrid"
		style="height: 100%; width: auto;"
		data-options="
	     url:'extentionRequestList.action',
       method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleOnCheck: true,
		selectOnCheck: true,
		 singleSelect: false,
		checkOnSelect: true,
		onLoadSuccess:function(data){
				clearSelectRows('#grid');
			}
	    ">
		<!-- 表头 -->
		<thead>
			<tr>
			  <th data-options="field:'pid',checkbox:true"></th> 
				<th	data-options="field:'projectNum',width:150">项目编号</th>
				<th data-options="field:'projectName',width:150,formatter:loanExtensionList.formatProjectName">项目名称</th>
				<th data-options="field:'requestStatus',formatter:formatterForeStatus,width:150">处理状态</th>
				<th data-options="field:'requestDttm',width:150">申请时间</th>
				<th data-options="field:'creditAmt',width:150,align:'right',formatter:formatMoney">展期金额</th>
				<th data-options="field:'extensionFee',width:'100',align:'right',formatter:formatMoney">展期费用</th>
				<th data-options="field:'chargeAmount',width:'100',align:'right',formatter:formatMoney">实际收费金额</th>
				<th data-options="field:'pmUserName',width:150">客户经理</th>
				<th data-options="field:'planOutLoanDt',width:150">展期开始时间</th>
				<th data-options="field:'planRepayLoanDt',width:150">展期结束时间</th>
				<th data-options="field:'yy',width:150,formatter:foreExtensionList.formatOperate">操作</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>
</body>