<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>


<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="creditReportFeeList.action" method="post" id="searchFrom">
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
							<td width="80" align="right" height="25">报告类型：</td>
							<td>
								<select class="easyui-combobox" id="query_reportType" name="reportType" 
									style="width:120px" panelHeight="auto">
								</select>
							</td>
							<td width="80" align="right" height="25">操作人：</td>
							<td colspan="2">
								<input class="easyui-textbox" name="realName" style="width: 150px;" />
							</td>
						</tr>
						<tr>
							<td width="80" align="right" height="25">是否收费：</td>
							<td>
								<select class="easyui-combobox" id="query_isRepeat" name="isRepeat" 
									style="width:120px" panelHeight="auto">
									<option value="0">--请选择--</option>
									<option value="2">是</option>
									<option value="1">否</option>
								</select>
							</td>
							<td width="80" align="right" height="25">查询时间：</td>
							<td >
								<input name="beginCreateTime" id="beginCreateTime" class="easyui-datebox" editable="false"/> <span>~</span> 
								<input name="endCreateTime" id="endCreateTime" class="easyui-datebox" editable="false"/>
							</td>
							<td width="80" align="right" height="25"></td>
							<td >
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
								<a href="#" onclick="majaxReset()" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
							</td>
							<td width="80" align="right" height="25"></td>
							<td style="font-weight:bold;font-size: 13px;">
								总次数：<span id="span_totalCount">0</span>
								</br>   
								总费用(元)：<span id="span_totalPrice">0.00</span>
							</td>
						</tr>
					</table>
				</div>
			</form>

		</div>
		<div class="contentDiv" style="height: 100%;">
			<table id="grid" title="征信报告费用统计列表" class="easyui-datagrid"	style="height: 100%; 
				width: auto;"
				data-options="
					url: 'creditReportFeeList.action',
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
						<th data-options="field:'dataSource',formatter:cusCreditGlobal.formatDataSource,width:80,sortable:true" align="center" halign="center">信息来源</th>
						<th data-options="field:'reportType',formatter:cusCreditGlobal.formatReportType,width:80,sortable:true" align="center" halign="center">报告类型</th>
						<th data-options="field:'isRepeat',formatter:cusCreditGlobal.formatIsRepeat,width:80,sortable:true" align="center" halign="center">是否收费</th>
						<th data-options="field:'realName',width:80,sortable:true" align="center" halign="center">操作人</th>
						<th data-options="field:'totalCount',width:80,sortable:true" align="center" halign="center">总次数</th>
						<th data-options="field:'totalPrice',width:80,sortable:true" align="center" halign="center">总费用（元）</th>
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
	        }
	    }); 
		
		 //初始化报告类型
		$('#query_reportType').combobox({
			data:cusCreditGlobal.reportTypeJson,  
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
	
	
	//获取费用总和
	function creditReportFeeSum(){
    	$.ajax({
            cache: true,
            type: "POST",
            url: "<%=basePath%>reportController/creditReportFeeSum.action",
            data:$('#searchFrom').serialize(),// 你的formid
			dataType:'json',
            async: false,
            success: function(result) {
            	if(result.header.success == true){
	        		$("#span_totalCount").text(result.body.feeSum.totalCount);
	        		$("#span_totalPrice").text(result.body.feeSum.totalPrice);
	        	}
            } 
        });
	}
	
	
	//物业信息加载成功后获取总评估价以及总成交价
	$("#grid").datagrid({
		onLoadSuccess : function(data){
			creditReportFeeSum();
		}
	});
	
	
</script>
	
	
</body>
