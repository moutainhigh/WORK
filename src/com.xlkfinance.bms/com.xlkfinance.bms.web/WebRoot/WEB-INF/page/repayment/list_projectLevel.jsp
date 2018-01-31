<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript">
	function datiladvoperat(value, row, index) {
		var bt ="";
		var result = checkOverduenum(value, row, index);
		if(row.projectLevel==0){//自定义级别没有值的话以自动判定的为基准
			if(result=="损失"){
				 bt = "<a class='easyui-linkbutton' onclick='advapply("+JSON.stringify(row)+")' href='#'>" +
				"<span style='color:blue; '>"+ "坏帐处理"+"</span></a>";
				return bt;
			}else{
				bt = " <font color='grey'>坏帐处理</font>";
				return bt;
			}
		}else{
			if(row.projectLevel==5){//损失
				 bt = "<a class='easyui-linkbutton' onclick='advapply("+JSON.stringify(row)+")' href='#'>" +
					"<span style='color:blue; '>"+ "坏帐处理"+"</span></a>";
					return bt;
			}else{
				bt = " <font color='grey'>坏帐处理</font>";
				return bt;
			}
		}
		
	}

 
	
	function advapply(row) {
		 var url = "<%=basePath%>afterLoanController/newBadDebt.action?projectId="
			+ row.projectId+"&newFlow="+true+"&regHistoryId="+-1;
		parent.openNewTab(url, "坏账处理", true); 
	}
 
	 
	
 
	
	//重置
	function resetss(){
		$(".ecoTrade").hide();	
	}
	function cusType(value, row, index){
		if (1==row.cusType) {
			return "个人";
		}
		if (2==row.cusType) {
			return "企业";
		}else{
			return "";
		}
		
	}
	
	//(申请中=0，办理中=1，审核=2)
	function checkOverduenum(value, row, index){
		var type =  row.guaranteeType;
		var strs= new Array(); //定义一数组 
		strs=type.split(","); //字符分割 
		for (i=0;i<strs.length ;i++ ) { 
			if(strs[i]=="抵押"){
				if(row.overduenum>=181 && row.overduenum<=360 ){
					return "次级";
				}else if(row.overduenum>=361 && row.overduenum<=540 ){
					return "可疑";
				}else if(row.overduenum>=541){
					return "损失";
				}
				return pledgeMortgage(row.overduenum);
			}else if(strs[i]=="质押"){
				if(row.overduenum>=181 && row.overduenum<=360 ){
					return "可疑";
				}else if(row.overduenum>=361 && row.overduenum<=540 ){
					return "可疑";
				}else if(row.overduenum>=541){
					return "损失";
				}
				return pledgeMortgage(row.overduenum);
			}else if(strs[i]=="信用"){
				return ensureCredit(row.overduenum);
			}else if(strs[i]=="保证"){
				return ensureCredit(row.overduenum);
			}
		} 
	}
	
	function pledgeMortgage(overduenum){//抵押,质押 
		if(overduenum==0){
			return "正常";
		}else if(overduenum<=30){//30以内 逾期（天数）
			return "正常";
		}else if(overduenum>=31 && overduenum<=90 ){//31-90以内 逾期（天数）
			return "关注";
		}else if(overduenum>=91 && overduenum<=180 ){
			return "次级";
		}
	}
	
	function ensureCredit(overduenum){//保证、信用
		if(overduenum==0){
			return "正常";
		}else if(overduenum<=30){//30以内 逾期（天数）
			return "关注";
		}else if(overduenum>=31 && overduenum<=90 ){//31-90以内 逾期（天数）
			return "关注";
		}else if(overduenum>=91 && overduenum<=180 ){
			return "次级";
		}else if(overduenum>=181 && overduenum<=360 ){
			return "可疑";
		}else if(overduenum>=361 && overduenum<=540 ){
			return "损失";
		}else if(overduenum>=541){
			return "损失";
		}
	}
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
	
	
	//查询
	function ajaxSearch(){
		if(!compareDate('requestAdvDttm','requestAdvDttmLast')){
			return false;
		}
		ajaxSearchPage('#grid','#searcm');
	};
	
	function projectLevelFilter(value,row,index){
		var dom = '';
		if(value==1){
			dom='<select id="projectLevel'+index+'" class="easyui-combobox" name="projectLevel"  >'
				+'<option value="0">--请选择--</option>'
				+'<option value="1" selected=selected>正常</option>'
				+'<option value="2">关注</option>'
				+'<option value="3">次级</option>'
				+'<option value="4">可疑</option>'
				+'<option value="5">损失</option>'
			    +'</select>';
		}else if(value==2){
			dom='<select id="projectLevel'+index+'" class="easyui-combobox" name="projectLevel"    >'
				+'<option value="0">--请选择--</option>'
				+'<option value="1">正常</option>'
				+'<option value="2" selected=selected>关注</option>'
				+'<option value="3">次级</option>'
				+'<option value="4">可疑</option>'
				+'<option value="5">损失</option>'
			    +'</select>';
		}else if(value==3){
			dom='<select id="projectLevel'+index+'" class="easyui-combobox" name="projectLevel"    >'
				+'<option value="0">--请选择--</option>'
				+'<option value="1">正常</option>'
				+'<option value="2">关注</option>'
				+'<option value="3" selected=selected>次级</option>'
				+'<option value="4">可疑</option>'
				+'<option value="5">损失</option>'
			    +'</select>';
		}else if(value==4){
			dom='<select id="projectLevel'+index+'" class="easyui-combobox" name="projectLevel"    >'
				+'<option value="0">--请选择--</option>'
				+'<option value="1">正常</option>'
				+'<option value="2">关注</option>'
				+'<option value="3">次级</option>'
				+'<option value="4" selected=selected>可疑</option>'
				+'<option value="5">损失</option>'
			    +'</select>';
		}else if(value==5){
			dom='<select id="projectLevel'+index+'" class="easyui-combobox" name="projectLevel"    >'
				+'<option value="0">--请选择--</option>'
				+'<option value="1" >正常</option>'
				+'<option value="2">关注</option>'
				+'<option value="3">次级</option>'
				+'<option value="4">可疑</option>'
				+'<option value="5" selected=selected>损失</option>'
			    +'</select>';
		}else{
			dom='<select id="projectLevel'+index+'" class="easyui-combobox" name="projectLevel"    >'
				+'<option value="0" selected=selected>--请选择--</option>'
				+'<option value="1">正常</option>'
				+'<option value="2">关注</option>'
				+'<option value="3">次级</option>'
				+'<option value="4">可疑</option>'
				+'<option value="5">损失</option>'
			    +'</select>';
		} 
		return dom;
	}
	
	
	
	//项目名称format
	var formatProjectName=function (value, rowData, index) {
		var btn = "<a id='"+rowData.projectId+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.projectId+",\""+rowData.projectNumber+"\")' href='#'>" +
			"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
		return btn;   
	}
	//保存修改的数据
	function saveParmData(){
		
		//debugger;
		var rows = $('#grid').datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert("操作提示","请选择数据","info")
			return;
		}
		for(var i=0;i<rows.length;i++ ){
			var index = $('#grid').datagrid('getRowIndex',rows[i]);
			var projectLevel =  $("#projectLevel"+index).val();
			rows[i].projectLevel = $("#projectLevel"+index).val();
		}
		$.ajax({
			type: "POST",
			contentType: "application/json",
		    url: "${basePath}projectLevelController/saveProjectLevelInfo.action",
		    data:JSON.stringify(rows),
		    dataType: "text",
		    success: function(data){
					if(data=="true" || data==true){
						$.messager.alert('操作提示', '保存信息成功!', 'success');
						$("#grid").datagrid('reload');
						$("#grid").datagrid('clearChecked');
					}else{
						$.messager.alert('操作提示', '保存信息失败!', 'error');
					}
		    }
		});
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<div>
			<!-- 查询条件 -->
			<form action="projectLevelList.action" method="post" id="searcm" name="searcm">
				<div style="padding: 5px">
					<table class="beforeloanTable" width="90%">
						<tr>
							<td class="label_right">项目名称：</td>
							<td><input class="easyui-textbox" style="width:220px" name="projectName" id="projectName" /></td>
							<td class="label_right">项目编号: </td>
							<td>
								<input class="easyui-textbox"  name="projectNumber" id="projectNumber" />
							</td>
						</tr>
						<tr>
							<td class="label_right">客户名称:</td>
							<td><input class="easyui-textbox" style="width:220px" name="cusName" id="cusName" /></td>
							<td class="label_right">客户类别: </td>
							<td>
								<select name="cusType" id="cusType" class="easyui-combobox"  style="width:150px;" >
								<option value="">--请选择-- </option>
								<option value="1">个人 </option>
								<option value="2">企业 </option>
								
								</select>
							</td>
						</tr>
						<tr>
							<td class="label_right">贷款申请时间: </td>
							<td ><!-- colspan="3" -->
								<input class="easyui-datebox" name="requestDttm" id="requestAdvDttm" editable="false" /> <span>~</span>
								<input class="easyui-datebox" name="requestDttmEnd" id="requestAdvDttmLast" editable="false" />
							
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);"
							class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearch()">查询</a>
								<a href="javascript:void(0);" iconCls="icon-clear" onclick="javascript:$('#searcm').form('reset');resetss()" class="easyui-linkbutton" style="width: 60px;">重置</a>
							</td>	
							
							<td class="label_right1 ecoTrade">经济行业类别：</td>
							<td class="ecoTrade">
								<input class="easyui-combobox" name="ecoTrade"
									id="ecoTrade" editable="false"
									data-options="
					            valueField:'pid',
					            textField:'lookupVal',
					            method:'get',
					            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
							</td>
						</tr>
						<tr>
							<td class="label_right1">五级分类类别：</td>
							<td>
								<select name="projectLevel"  id="projectLevel"  class="easyui-combobox"  style="width:150px;"  editable="false">
								<option value="">--请选择-- </option>
								<option value="1">正常 </option>
								<option value="2">关注 </option>
								<option value="3">次级</option>
								<option value="4">可疑 </option>
								<option value="5">损失 </option>
								</select>
							</td>
						</tr>
					</table>	
				</div>
			</form>
	</div>
	
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveParmData()">保存自定义</a>
		</div>
	</div>

	<div class="advrepaymentListDiv" style="height:100%">
	<table id="grid" title="贷款项目五级分类" class="easyui-datagrid"
		style="height: 100%; width: auto;"
		data-options="
	     url:'projectLevelList.action',
	     method: 'POST',
	     rownumbers: true,
	     pagination: true,
	     toolbar: '#toolbar', 
	     fitColumns:true
	    ">
		<!-- 表头 -->
		<thead>
			<tr>
			  <th data-options="field:'pid',checkbox:true"></th> 
				<th data-options="field:'projectName',width:180,formatter:formatProjectName">项目名称</th>
				<th	data-options="field:'projectNumber',width:80">项目编号</th>
				<th data-options="field:'cusType',width:50">客户名称</th>
				<th data-options="field:'guaranteeType',width:100">担保类型</th>
			    <th data-options="field:'requestDttm',width:90">贷款申请时间</th>
				<th data-options="field:'overduenum',width:50">逾期天数</th>
				<th data-options="field:'projectId',width:150,hidden:true"></th>
				<th data-options="field:'overduenums',width:50,formatter:checkOverduenum">自动判定级别</th>
				<th data-options="field:'projectLevel',width:90,formatter:projectLevelFilter">自定义级别</th>
			<!-- 	<th data-options="field:'status',width:150,formatter:requestStatus">处理状态</th>-->
				<th data-options="field:'yy',width:80,formatter:datiladvoperat">操作</th> 
			</tr>
		</thead>
	</table>
	</div>
</div>
</body>