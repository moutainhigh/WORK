<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目监管结果记录查看</title>

<script type="text/javascript">

function disposeClick2(planId) {
	url = '<%=basePath%>afterLoanController/execuSuperyviseBusiness.action?planId='+ planId;
		parent.openNewTab(url, "查看监管详情", true);
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
			<div class=" easyui-panel" title="项目监管结果记录查看"
				data-options="collapsible:true">

				<table class="cus_table" width="780">
					<tr>
						<td class="label_right" style="width: 120px;">项目名称：</td>
						<td>${projectName}</td>
						<td class="label_right" style="width: 120px;">项目编号：</td>
						<td>${projectNumber}</td>
					</tr>
				</table>
			</div>
			<div class="pt10"></div>
			<table id="grid" title="项目监管记录" class="easyui-datagrid" style=""
				data-options="
	    rownumbers: true,
	    singleSelect: true,
	    sortOrder:'asc',
	    idField: 'pid',
		selectOnCheck: true,
		checkOnSelect: true,
		collapsible:true
	    ">
				<thead>
					<tr>
						<th data-options="field:'planUser',width:100,sortable:true"
							align="center">计划监管人</th>
						<th
							data-options="field:'planDate',width:100,formatter:convertDate"
							align="center">计划监管时间</th>
						<th data-options="field:'regulatoryStatusStr',width:100"
							align="center">监管状态</th>
						<th data-options="field:'actualUser',width:100" align="center">监管执行人</th>
						<th
							data-options="field:'actualDate',width:100,formatter:convertDate"
							align="center">监管时间</th>
						<th data-options="field:'regulatoryResultStr'" align="center">监管结果</th>
						<th data-options="field:'yy',width:120" align="center">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${supervisionTaskList}" var="v">
						<tr>
							<td align="center">${v.planUser}</td>
							<td align="center">${v.planDate}</td>
							<td align="center">${v.regulatoryStatusStr}</td>
							<td align="center">${v.actualUser}</td>
							<td align="center">${v.actualDate}</td>
							<td align="center">${v.regulatoryResultStr}</td>
							<td>
								<div style="text-align: left;">
									<a class="easyui-linkbutton" iconCls="icon-view" plain="true"
										onclick="disposeClick2('${v.regulatoryPlanId}')">查看监管详情</a>
								</div>
							<td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
	</div>
</body>
</html>