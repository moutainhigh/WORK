<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>


<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="list.action" method="post" id="searchFrom">
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
				<!-- 查询条件 -->
				<div style="padding: 5px">
					<table>
						<tr>
							<td width="80" align="right" height="25">信息来源：</td>
							<td>
								<select class="easyui-combobox" id="query_dataSource" name="dataSource"   style="width:140px" panelHeight="auto">
								</select>
							</td>
							<td width="80" align="right" height="25">报告类型：</td>
							<td>
								<select class="easyui-combobox" id="query_reportType" name="reportType" 
									style="width:120px" panelHeight="auto">
								</select>
							</td>
							<td width="80" align="right" height="25">操作人：</td>
							<td>
								<input class="easyui-textbox" name="operatorName" style="width: 150px;" />
							</td>
						</tr>
						<tr>
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
			
				 <shiro:hasAnyRoles name="CREDIT_REPORT_FEE_CONFIG_ADD,ALL_BUSINESS"> 
					<a href="javascript:void(0)" class="easyui-linkbutton"	
						iconCls="icon-add" plain="true" onclick="addInit()">新增</a>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="CREDIT_REPORT_FEE_CONFIG_UPDATE,ALL_BUSINESS">
					<a href="javascript:void(0)" class="easyui-linkbutton"	
						iconCls="icon-edit" plain="true" onclick="updateInit()">修改</a>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="CREDIT_REPORT_FEE_CONFIG_DELETE,ALL_BUSINESS">
					<a href="javascript:void(0)" class="easyui-linkbutton"	
						iconCls="icon-remove" plain="true" onclick="deleteBatch()">删除</a>
				</shiro:hasAnyRoles>
			</div>

		</div>
		<div class="contentDiv" style="height: 100%;">
			<table id="grid" title="征信报告费用配置管理" class="easyui-datagrid"	style="height: 100%; 
				width: auto;"
				data-options="
					url: 'list.action',
				    method: 'post',
				    rownumbers: true,
				    pagination: true,
				    singleSelect: false,
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
						<th data-options="field:'unitPrice',width:80,sortable:true" align="center" halign="center">单次价格（元）</th>
						<th data-options="field:'operatorName',width:80,sortable:true" align="center" halign="center">操作人</th>
						<th data-options="field:'updateTime',width:80,sortable:true" align="center" halign="center">操作时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		
		
		<!-- 弹层基本信息 -->
		<div id="div_btn">		
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="save()">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#div_show').dialog('close')">关闭</a>
		</div>
		
		<div id="div_show" class="easyui-dialog" fitColumns="true"  title="征信报告费用配置"
		     style="width:500px;top:150px;padding:10px;" buttons="#div_btn" closed="true" >

			<div style="left: 0;">
				<!-- 基本信息 -->
				<div class="nearby"  style="display: block">
					<form id="div_show_form" action="<%=basePath%>creditReportFeeConfigController/save.action" method="POST">
						<table class="beforeloanTable">
							<!-- 隐藏ID -->
							<input type="hidden" name="pid" id="pid" />
							
							<tr>
								<td class="label_right1"><font color="red">*</font>信息来源：</td>
								<td>
									<select id="dataSource" name="dataSource"
										class="easyui-combobox" editable="false" style="width: 130px;" />
								</td>
							</tr>
							<tr>
								<td class="label_right1"><font color="red">*</font>报告类型：</td>
								<td>
									<select id="reportType" name="reportType" 
										class="easyui-combobox" editable="false" style="width: 130px;" />
								</td>
							</tr>
							<tr>
								<td class="label_right1"><font color="red">*</font>单次价格（元）：</td>
								<td>
									<input type="text" id="unitPrice" name="unitPrice"  class="easyui-numberbox"
										  data-options="required:true,min:-1,max:999999,precision:2"/>
								</td>
							</tr>
							<tr>
								<td class="label_right1">备注：</td>
								<td>
									<input name="remark" id="remark"
										class="easyui-textbox" style="width: 300px; height: 90px"
										data-options="multiline:true,validType:'length[0,200]'">
								</td>
							</tr>
						</table>
					</form>
				</div>


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
		 
		 
		//初始化信息来源
		 $('#dataSource').combobox({         
			data:cusCreditGlobal.dataSourceJson,  
	        valueField:'pid',
	        textField:'lookupDesc',
	        value: '0',
	        onSelect: function(){
	        	setReportType();
	        }
	    }); 
		 //初始化报告类型
		$('#reportType').combobox({
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
			$(".td_query_queryPhone").hide();
			reportTypeJson[1]=reportType[5];
		}
    	$('#query_reportType').combobox({
			data:reportTypeJson,  
	        valueField:'pid',
	        textField:'lookupDesc',
	        value: '0'
	    }); 
	}
	
	
	//二级联动条件筛选
	function setReportType(value){
		if(value == null || value == ""){
			value = "0" ;
		}
		var reportType=cusCreditGlobal.reportTypeJson;
    	var reportTypeJson=new Array({"pid" : "0","lookupDesc" : "--请选择--"});
    	var pid=$('#dataSource').combobox('getValue');
    	if(pid=="1"){ //鹏元征信
			reportTypeJson=reportType.slice(0,4);
		}else if(pid=="2"){ //优分数据
			reportTypeJson[1]=reportType[4];
		}else if(pid=="3"){ //FICO大数据评分
			reportTypeJson[1]=reportType[5];
		}
    	$('#reportType').combobox({
			data:reportTypeJson,  
	        valueField:'pid',
	        textField:'lookupDesc',
	        value: value
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
	
	
	//新增初始化
	function addInit(){
		$('#div_show_form').form('reset');
		$('#div_show').dialog('open').dialog('setTitle', "添加征信报告费用配置");
	}
	
	//修改初始化
	function updateInit(){
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length == 1) {
			$.ajax({
				type : "POST",
				url : "<%=basePath%>creditReportFeeConfigController/details.action",
		        data:{"pid":rows[0].pid},
		        dataType: "json",
		        success: function(row){
						$('#div_show_form').form('reset');
						$('#div_show').dialog('open').dialog('setTitle', "修改征信报告费用配置");
						$('#div_show_form').form('load', row);
						
						//重置报告类型
						setReportType(row.reportType);
						
		        },error : function(result){
		        	$.messager.alert('操作提示',"操作失败",'error');
		        }
		    });
		}else if(rows.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	//保存
	function save(){
		var dataSource=$('#dataSource').combobox('getValue');	//信息来源
		var reportType=$('#reportType').combobox('getValue');	//报告类型
		var unitPrice = $("#unitPrice").numberbox("getValue");	//单次价格
		var remark = $("#remark").textbox("getValue");		//备注
		
		if(dataSource <= 0 ){
			$.messager.alert("操作提示","请选择信息来源!","error");
			return;
		}else if(reportType <= 0 ){
			$.messager.alert("操作提示","请选择报告类型!","error");
			return;
		}else if(unitPrice == "" ){
			$.messager.alert("操作提示","请填写单次价格!","error");
			return;
		}else if(remark != "" && remark.length > 200 ){
			$.messager.alert("操作提示","备注输入内容长度必须介于0和200之间!","error");
			return;
		}
		
		$.messager.confirm("操作提示","确定保存?",function(r) {
 			if(r){
 				$('#div_show_form').form('submit', {
					url : "<%=basePath%>creditReportFeeConfigController/save.action",
					dataType: "json",
					onSubmit : function() {
							MaskUtil.mask("保存中,请耐心等候......");  
							return true; 
					},
					success : function(result) {
						var resultObj = eval("("+result+")");
						MaskUtil.unmask(); 
 			        	if(resultObj.header.success == true){
 			        		$.messager.alert('操作提示',resultObj.header.msg);	
 							
 			        		$('#div_show').dialog('close');
 			        		
 			        		// 清空所选择的行数据
 							clearSelectRows("#grid");
 							// 重新加载列表
 							$("#grid").datagrid('load');
 							
 							
 			        	}else{
 			        		$.messager.alert('操作提示',resultObj.header.msg,'error');	
 			        	}
					},error : function(result){
						MaskUtil.unmask(); 
						$.messager.alert('操作提示',"操作失败！",'error');	
					}
				});
 			}
		});
	}
	
	//删除
	function deleteBatch(){
		var rows = $('#grid').datagrid('getSelections');
		
		if (rows.length == 0) {
			$.messager.alert("操作提示","至少选择一条数据！","error");
			return;
		}
		
		var pidListStr = "";
		for(var i = 0 ; i < rows.length; i++){
			pidListStr += rows[i].pid+",";
		}
		pidListStr = pidListStr.substr(0,pidListStr.length-1);
		$.ajax({
			type : "POST",
			url : "<%=basePath%>creditReportFeeConfigController/deleteBatch.action",
	        data:{"pidListStr":pidListStr},
	        dataType: "json",
	        success: function(result){
	        	
	        	if(result.header.success == true){
		        	$.messager.alert('操作提示',result.header.msg);
					// 清空所选择的行数据
					clearSelectRows("#grid");
					// 重新加载列表
					$("#grid").datagrid('load');
	        	}else{
		        	$.messager.alert('操作提示',result.header.msg,'error');	
		        }
	        },error : function(result){
	        	$.messager.alert('操作提示',"操作失败",'error');
	        }
	    });
		
	}
	
	
</script>
	
	
</body>
