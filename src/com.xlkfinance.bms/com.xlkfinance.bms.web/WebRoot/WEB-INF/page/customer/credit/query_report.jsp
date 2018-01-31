<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<div class="panel-header">
				<div class="panel-title">请选择报告类型</div>
				<div class="panel-tool"></div>
			</div>
			
			<form action="getCreditReportHisList.action" method="post" id="searchFrom">
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
				<input type="hidden" id="acctId" name="acctId" >
				<!-- 查询条件 -->
				<div>
					<table title="请选择报告类型">
						<tr>
							<td width="80" align="right" height="25">信息来源：</td>
							<td>
								<select class="easyui-combobox" id="query_dataSource" name="dataSource" style="width:120px" panelHeight="auto">
								</select>
							</td>
							<td width="80" align="right" height="25">姓名：</td>
							<td>
								<input class="easyui-textbox" id="query_queryName" name="queryName" style="width: 200px;" />
							</td>
							<td colspan="2">
								<a href="javascript:void(0)" class="easyui-linkbutton"	
									iconCls="icon-add" plain="true" onclick="selectPersonal()">选择客户</a>
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
								<input class="easyui-textbox" id="query_queryDocumentNo" name="queryDocumentNo"style="width: 200px;" />
							</td>
							<td width="100" align="right" height="25">费用（元/次）：</td>
							<td >
								<input class="easyui-textbox" id="query_unitPrice" disabled="disabled" style="width: 100px;" />
							</td>
						</tr>
						<tr>
							<td width="80" align="right" height="25">查询原因：</td>
							<td>
								<select class="easyui-combobox" id="query_queryResonId" name="queryResonId" 
									style="width:120px" panelHeight="auto">
								</select>
								
							</td>
							<td class="td_query_queryPhone" width="80" align="right" height="25">手机号码：</td>
							<td class="td_query_queryPhone" >
								<input class="easyui-textbox" id="query_queryPhone" name="queryPhone" style="width: 200px;" />
							</td>
							<td class="td_query_queryPbocStatus" width="100" align="right" height="25">人行征信报告：</td>
							<td class="td_query_queryPbocStatus" colspan="3">
								<select class="easyui-combobox" id="query_queryPbocStatus" name="queryPbocStatus" 
									style="width:100px" panelHeight="auto">
								</select>
							</td>
						</tr>
						
						<!-- 功能按钮 -->
						<tr>
							<td colspan="6" align="right" >
								<a href="javascript:void(0)" class="easyui-linkbutton"	
									iconCls="icon-search" plain="true" onclick="sentReport()">查询</a>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		
		
		<div class="contentDiv" style="height: 100%;">
			<div class="panel-header">
				<div class="panel-title">征信报告</div>
				<div class="panel-tool"></div>
			</div>
			
			<!-- 显示内容 -->
			<div id="div_show_report"> </div>
		</div>
	</div>
	
	
	<%-- 个人客户选取 --%>
	<div id="dlg-buttons_personal">		
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="savePersonal()">选择</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_personal').dialog('close')">关闭</a>
	</div>
	<div id="dlg_personal" class="easyui-dialog" fitColumns="true"  title="个人客户信息查询"
	     style="width:850px;top:100px; 0px 0px" buttons="#dlg-buttons_personal" closed="true" >
	    <table id="personal_grid" class="easyui-datagrid"  
	    	style="height: 300px;width: 836px;"
		 	data-options="
		    url: '<%=basePath%>customerController/getPersonalListTwo.action',
		    method: 'post',
		    rownumbers: true,
		    singleSelect: true,
	    	pagination: true,
		    toolbar: '#toolbar_personal',
		    idField: 'acctId'
		    ">
			<thead>
				<tr>
					<th data-options="field:'acctId',checkbox:true" ></th>
					<th data-options="field:'chinaName'" >姓名</th>
					<th data-options="field:'sexName'" >性别</th>
					<th data-options="field:'certTypeName'" >证件类型</th>
					<th data-options="field:'certNumber'" >证件号号码</th>
					<th data-options="field:'liveAddr'" >居住地址</th>
					<th data-options="field:'cusStatusVal'" >客户状态</th>
					<th data-options="field:'realName'" >客户经理</th>
				</tr>
			</thead>
	   	</table>
		<div id="toolbar_personal">
	        <form method="post" action="<%=basePath%>customerController/getPersonalListTwo.action"  id="searchFromPersonal" name="searchFromPersonal"  style="padding: 0px 0px;">
	     		<div style="margin:5px">
				 	<input name="pid" id="pid" type="hidden"  value="${currUser.pid}"  />
				 	<table class="beforeloanTable">
				 		<tr>
				 			<td class="label_right">姓名：</td>
				 			<td><input name="chinaName" id="chinaName" class="easyui-textbox" /></td>
				 			<td class="label_right">证件类型：</td>
				 			<td colspan="2"><input name="certType" id="certType" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CERT_TYPE'" /></td>
				 		</tr>
				 		<tr>
				 			<td class="label_right">性别：</td>
				 			<td><input name="sex" id="sex" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=SEX'" /></td>
				 			<td class="label_right">证件号码：</td>
				 			<td><input name="certNumber" id="certNumber" class="easyui-textbox" /></td>
				 			<td>
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#personal_grid','#searchFromPersonal')">查询</a>
								<a href="#" onclick="javascript:$('#searchFromPersonal').form('reset');" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
				 			</td>
				 		</tr>
				 	</table>				 		
        		</div> 
		 	</form>
	   </div>
	</div>
	<!-- 个人客户选取结束 -->
	
	
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/customer/cusCredit.js?v=${v}"></script> 
<script type="text/javascript">


	$(function(){	   
		//初始化信息来源
		$('#query_dataSource').combobox({         
			data:cusCreditGlobal.dataSourceJson,  
	        valueField:'pid',
	        textField:'lookupDesc',
   	        value: '0',
	        //如果需要设置多项隐含值，可使用以下代码
	        onLoadSuccess:function(){
	         },
			onSelect:function(){
				queryReportShow();
	       }
	    });
		//初始化报告类型
		$('#query_reportType').combobox({         
			data:cusCreditGlobal.reportTypeJson,  
	        valueField:'pid',
	        textField:'lookupDesc',
   	        value: '0',
	        //如果需要设置多项隐含值，可使用以下代码
	        onLoadSuccess:function(){
	        	var tempValue = $("#query_reportType").combobox('getValue');
			 	setReportTypeParamShow(tempValue);
	         },
			onSelect:function(){  
			 	var tempValue = $("#query_reportType").combobox('getValue');
			 	setReportTypeParamShow(tempValue);
			 	//设置费用显示
			 	getReportFeeConfig();
	       }
	    });
		//初始化查询原因
		$('#query_queryResonId').combobox({         
			data:cusCreditGlobal.queryResonIdJson,  
	        valueField:'pid',
	        textField:'lookupDesc',
   	        value: '0',
	        //如果需要设置多项隐含值，可使用以下代码
	        onLoadSuccess:function(){
	         },
			onSelect:function(){  
	       }
	    });
		
		//初始化有无人行证信
		$('#query_queryPbocStatus').combobox({         
			data:cusCreditGlobal.queryPbocStatusJson,  
	        valueField:'pid',
	        textField:'lookupDesc',
   	        value: '0',
	        //如果需要设置多项隐含值，可使用以下代码
	        onLoadSuccess:function(){
	         },
			onSelect:function(){  
	       }
	    });
		
		
	});
	
	/**
	 * 设置报告类型显示或者隐藏查询参数 
	 * reportType 报告类型
	 */
	function setReportTypeParamShow(reportType){
		if(reportType == "3" || reportType == "5"){
			$(".td_query_queryPhone").show();
			if(reportType == "5"){
				$(".td_query_queryPbocStatus").show();
			}
		}else{
			$(".td_query_queryPhone").hide();
			$(".td_query_queryPbocStatus").hide();
			//手机号码
			$("#query_queryPhone").textbox('setValue',"");  
		}
	}
	
	//二级联动条件筛选
	function queryReportShow(){
		var reportType=cusCreditGlobal.reportTypeJson;
    	var reportTypeJson=new Array({"pid" : "0","lookupDesc" : "--请选择--"});
    	var dataSource=$('#query_dataSource').combobox('getValue');
    	if(dataSource == "1"){ //鹏元征信
			reportTypeJson = reportType.slice(0,4);
			
		}else if(dataSource == "2"){ //优分数据
			$(".td_query_queryPhone").hide();
			reportTypeJson[1] = reportType[4];
		}else if(dataSource == "3"){ //FICO大数据评分
			reportTypeJson[1] = reportType[5];
			$('#td_query_queryPbocStatus').combobox('setValue',"0");
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
	
	//发送查询
	function sentReport(){
		
		//信息来源
		var dataSource = $("#query_dataSource").combobox('getValue');	
		//报告类型
		var reportType = $("#query_reportType").combobox('getValue');	
		//查询原因
		var queryResonId = $("#query_queryResonId").combobox('getValue');	
		//姓名
		var queryName = $("#query_queryName").textbox('getValue');
		//证件号码
 		var queryDocumentNo = $("#query_queryDocumentNo").textbox('getValue');
		//手机号码
		var queryPhone = $("#query_queryPhone").textbox('getValue');  
		//有无人行征信
		var queryPbocStatus = $("#query_queryPbocStatus").combobox('getValue');  
		
		if(dataSource == null  || dataSource == '0'){
			$.messager.alert('操作提示',"请选择信息来源",'error');	
			return;
		}else if(reportType == null  || reportType == '0'){
			$.messager.alert('操作提示',"请选择报告类型",'error');	
			return;
		}else if(queryResonId == null  || queryResonId == '0'){
			$.messager.alert('操作提示',"请选择查询原因",'error');	
			return;
		} else if(queryName == null  || $.trim(queryName) == '' ){
			$.messager.alert('操作提示',"请填写姓名",'error');	
			return;
		}else if(queryDocumentNo == null  || $.trim(queryDocumentNo) == ''){
			$.messager.alert('操作提示',"请填写证件号码",'error');	
			return;
		}
		
		//需要验证手机号码
		if(reportType == "3" || reportType == "5" ){
			if(queryPhone == null  || $.trim(queryPhone) == ''){
				$.messager.alert('操作提示',"请填写手机号码",'error');	
				return;
			} 
		}
		if(reportType == "5" ){
			if(queryPbocStatus == null  || queryPbocStatus == '0'){
				$.messager.alert('操作提示',"请选择有无人行征信",'error');	
				return;
			}
		}
		
		
		$.messager.confirm("操作提示","确定查询报告？",function(r) {
 			if(r){
 				MaskUtil.mask("查询中,请耐心等候......");  
 				$.ajax({
 					type: "POST",
 			        url: "<%=basePath%>cusCreditReportHisController/sentReport.action",
 			        data:{"dataSource":dataSource,"reportType":reportType,"queryResonId":queryResonId
 			        		,"queryName":queryName,"queryDocumentNo":queryDocumentNo,"queryPhone":queryPhone,
 			        		"queryPbocStatus":queryPbocStatus},
 			        dataType: "json",
 			        success: function(result){
 			        	MaskUtil.unmask();
 			        	console.log(result);
 			        	if(result.header.success == true){
 			        		$.messager.alert('操作提示',result.header.msg);	
 			        		var pid = result.body.pid;
 			        		//显示 详情
		 			    <%--    	jQuery.ajax({
		 			   			type : 'GET',
		 			   			url: "<%=basePath%>cusCreditReportHisController/getReportDetails.action?pid="+pid,
		 			   			cache : false,
		 			   			contentType : 'application/html; charset=utf-8',
		 			   			success: function(data){
		 			   				//$("#div_show_report").html(data);
		 			   			},error: function(data){
		 			   				$("#div_show_report").html("加载失败");
		 			   			}
		 			   		}); --%>
 			        		
	 			   			var iframCon = '<iframe style="width:100%;height:100%;border:0;" src="<%=basePath%>cusCreditReportHisController/getReportDetails.action?pid='+pid+'"></iframe>';  
	 						$('#div_show_report').html(iframCon);  
 			        		
 			        	}else{
 			        		$.messager.alert('操作提示',result.header.msg,'error');	
 			        		$('#div_show_report').html(result.header.msg);  
 			        	}
 					},error : function(result){
 						MaskUtil.unmask();
 						$.messager.alert('操作提示',"查询失败！",'error');	
 					}
 				 });
 			}
		}); 
	}
 
	//打开个人客户信息窗口 
	function selectPersonal(){
		$('#personal_grid').datagrid('loadData',[]);
	  	$("#searchFromPersonal #chinaName").textbox('setValue',"");
	  	$("#searchFromPersonal #certType").combobox('setValue',"-1");
	  	$("#searchFromPersonal #sex").combobox('setValue',"-1");
	  	$("#searchFromPersonal #certNumber").textbox('setValue',"");
		ajaxSearchPage('#personal_grid','#searchFromPersonal');
	  	$('#dlg_personal').dialog('open');
	}
	
	// 选取个人客户
	function savePersonal(){
		// 获取选中的行
		var row = $('#personal_grid').datagrid('getSelected');	
		// 保证必须选取客户数据
		if(!row){
			$.messager.alert("操作提示","请选择客户信息!","error");
			return false;
		}
		$("#acctId").val(row.acctId);
		
		$("#query_queryName").textbox('setValue',row.chinaName);
		$("#query_queryDocumentNo").textbox('setValue',row.certNumber);
		
		$("#query_queryPhone").textbox('setValue',row.perTelephone);  
		
		// 关闭个人客户窗口
		$('#dlg_personal').dialog('close');
	}
	
	//加载机构项目信息
	function getReportFeeConfig(){
		//报告类型
		var reportType = $("#query_reportType").combobox('getValue');	
		if(reportType > 0 ){
			$.ajax({
				type: "POST",
		        url: "<%=basePath%>creditReportFeeConfigController/getReportFeeConfig.action",
		        data:{"reportType":reportType},
		        dataType: "json",
		        success: function(result){
		        	if(result.header.success == true){
		        		$("#query_unitPrice").textbox('setValue',result.body.feeConfig.unitPrice);
		        	}else{
		        		$("#query_unitPrice").textbox('setValue',"费用未配置");
		        	}
				},
				error: function(e){
					$("#query_unitPrice").textbox('setValue',"");
				}
			 });
		}else{
			$("#query_unitPrice").textbox('setValue',"");
		}
	}
	
	
</script>
	
	
	
</body>
