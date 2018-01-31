<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>


<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="getCreditReportHisList.action" method="post" id="searchFrom">
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
				<!-- 查询条件 -->
				<div style="padding: 5px">
					<table>
						<tr>
							<td width="80" align="right" height="25">信息来源：</td>
							<td>
								<select class="easyui-combobox" id="query_dataSource" name="dataSource" onchange="selChange()"  style="width:120px" panelHeight="auto">
								
								</select>
							</td>
							<td width="80" align="right" height="25">姓名：</td>
							<td>
								<input class="easyui-textbox" name="queryName" style="width: 150px;" />
							</td>
							<td width="80" align="right" height="25">操作人：</td>
							<td>
								<input class="easyui-textbox" name="realName" style="width: 150px;" />
							</td>
						</tr>
						
						<tr>
							<td width="80" align="right" height="25">报告类型：</td>
							<td>
								<select class="easyui-combobox" id="query_reportType" name="reportType" 
									style="width:120px" panelHeight="auto">
								</select>
							</td>
							<td width="80" align="right" height="25">证件号码：</td>
							<td>
								<input class="easyui-textbox" name="queryDocumentNo"style="width: 150px;" />
							</td>
							<td width="80" align="right" height="25">查询时间：</td>
							<td>
								<input name="beginCreateTime" id="beginCreateTime" class="easyui-datebox" editable="false"/> <span>~</span> 
								<input name="endCreateTime" id="endCreateTime" class="easyui-datebox" editable="false"/>
							</td>
							
						</tr>
						<tr>
							<td width="80" align="right" height="25">查询原因：</td>
							<td>
								<select class="easyui-combobox" id="query_queryResonId" name="queryResonId" 
									style="width:120px" panelHeight="auto">
								</select>
							</td>
							<td  class="td_query_queryPhone" width="80" align="right" height="25">手机号码：</td>
							<td class="td_query_queryPhone">
								<input id="projectName" class="easyui-textbox" name="queryPhone" style="width: 150px;" />
							</td>
							<td width="80" align="right" height="25"></td>
							<td >
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
								<a href="#" onclick="majaxReset()" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
							</td>
						</tr>
					</table>
				</div>
			</form>

			<!-- 功能按钮 -->
			<div style="padding-bottom: 5px">
			
				<shiro:hasAnyRoles name="CUS_QUERY_REPORT,ALL_BUSINESS">
					<a href="javascript:void(0)" class="easyui-linkbutton"	
						iconCls="icon-search" plain="true" onclick="queryReport_index()">查询报告</a>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="CUS_VIEW_REPORT,ALL_BUSINESS">
					<a href="javascript:void(0)" class="easyui-linkbutton"	
						iconCls="icon-view" plain="true" onclick="getReportDetails()">查看报告</a>
				</shiro:hasAnyRoles>
			</div>

		</div>
		<div class="contentDiv" style="height: 100%;">
			<table id="grid" title="客户征信报告列表" class="easyui-datagrid"	style="height: 100%; 
				width: auto;"
				data-options="
					url: 'getCreditReportHisList.action',
				    method: 'post',
				    rownumbers: true,
				    pagination: true,
				    singleSelect: true,
				    sortOrder:'asc',
				    remoteSort:false,
				    toolbar: '#toolbar',
				    idField: 'pid',
				    width: '100%',
				 	height: '100%',  
				 	striped: true, 
				 	loadMsg: '数据加载中请稍后……',  
				    fitColumns:true">
				    
				<!--表头 -->
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true"></th>
						<th data-options="field:'dataSource',formatter:cusCreditGlobal.formatDataSource,width:80,sortable:true" align="center" halign="center">信息来源</th>
						<th data-options="field:'reportType',formatter:cusCreditGlobal.formatReportType,width:80,sortable:true" align="center" halign="center">报告类型</th>
						<th data-options="field:'queryResonId',formatter:cusCreditGlobal.formatQueryResonId,width:80,sortable:true" align="center" halign="center">查询原因</th>
						<th data-options="field:'queryName',width:80,sortable:true" align="center" halign="center">姓名</th>
						<th data-options="field:'queryDocumentNo',width:80,sortable:true" align="center" halign="center">证件号码</th>
						<th data-options="field:'queryPhone',width:80,sortable:true" align="center" halign="center">手机号码</th>
						<th data-options="field:'queryStatus',formatter:cusCreditGlobal.formatQueryStatus,width:80,sortable:true" align="center" halign="center">查询状态</th>
						<th data-options="field:'unitPrice',width:80,sortable:true" align="center" halign="center">单次价格</th>
						<th data-options="field:'isRepeat',formatter:cusCreditGlobal.formatIsRepeat,width:80,sortable:true" align="center" halign="center">是否收费</th>
						<th data-options="field:'realName',width:80,sortable:true" align="center" halign="center">操作人</th>
						<th data-options="field:'createTime',width:80,sortable:true" align="center" halign="center">查询时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

<script type="text/javascript" src="${ctx}/p/xlkfinance/module/customer/cusCredit.js?v=${v}"></script> 
<script type="text/javascript">


	$(function(){	
		
		
		//初始化信息来源
		 $('#query_dataSource').combobox({         
			data:cusCreditGlobal.dataSourceJson,  
	        valueField:'pid',
	        textField:'lookupDesc',
	        value: '0',
	        onSelect: function(){
	        	queryReportShow();
	        }
	    }); 
		
		 //初始化报告类型
		$('#query_reportType').combobox({
			data:cusCreditGlobal.reportTypeJson,  
	        valueField:'pid',
	        textField:'lookupDesc',
	        value: '0'
	    });  
		
	
		//初始化查询原因
		$('#query_queryResonId').combobox({         
			data:cusCreditGlobal.queryResonIdJson,  
	        valueField:'pid',
	        textField:'lookupDesc',
	        value: '0'
	 
	    });
		
	});
	
	//二级联动条件筛选
	function queryReportShow(){
		var reportType=cusCreditGlobal.reportTypeJson;
    	var reportTypeJson=new Array({"pid" : "0","lookupDesc" : "--请选择--"});
    	var pid=$('#query_dataSource').combobox('getValue');
    	if(pid=="1"){ //鹏元征信
			reportTypeJson=reportType.slice(0,4);
			$(".td_query_queryPhone").show();
		}else if(pid=="2"){ //优分数据
			$(".td_query_queryPhone").hide();
			reportTypeJson[1]=reportType[4];
		}else if(pid=="3"){ //FICO大数据评分
			reportTypeJson[1]=reportType[5];
		}
    	
    	$('#query_reportType').combobox({
			data:reportTypeJson,  
	        valueField:'pid',
	        textField:'lookupDesc',
	        value: '0'
	    }); 
	}
	
	//查询 
	function ajaxSearch(){
		
		var pageNumber=$('#grid').datagrid('options')['pageSize'];
		$('#rows').val(pageNumber);
		$('#searchFrom').form('submit',{
	        success:function(data){
	        	var str = JSON.parse(data);
	           	$('#grid').datagrid('loadData',str);
	           	$('#grid').datagrid('clearChecked');
	       }
	    });
	};
	
	//重置按钮
	function majaxReset(){
		$('#searchFrom').form('reset');
	}
	
	//初始化查询报告
	function queryReport_index(){
		var url = "<%=basePath%>cusCreditReportHisController/queryReport_index.action";
		parent.openNewTab(url,"查询报告",true);
	}
	//查看报告详情
	function getReportDetails(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length != 1) {
			$.messager.alert("操作提示","请选择一条数据","error");
			return;
		}
		var pid = row[0].pid;
		var url = "<%=basePath%>cusCreditReportHisController/getReportDetails.action?pid="+pid;
		parent.openNewTab(url,"查看报告",true);
	}
	
	
	
</script>
	
	
</body>
