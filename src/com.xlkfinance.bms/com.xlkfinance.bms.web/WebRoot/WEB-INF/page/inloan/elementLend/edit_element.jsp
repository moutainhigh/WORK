<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<%@ include file="/common.jsp"%>

<title>要件借出</title>
<style type="text/css">
#baseInfo {
		width: 500px;
}
#baseInfo label {
	width: 250px;
}
#baseInfo label.error, #baseInfo input.submit {
	margin-left: 253px;
}
.yj .panel-body-noheader{border: 0;}
.hidden_css{
display: none;
}
</style>
<script type="text/javascript">
/**************工作流字段 begin******************/
//赎楼展期处理申请
var WORKFLOW_ID = "elementLendRequestProcess"; 
var workflowTaskDefKey = "task_ElementLendProcess";
nextRoleCode = "DEPARTMENT_MANAGER";//
/**************工作流字段 end********************/
var pid = '${elementLend.pid}';
var projectId = '${elementLend.projectId}';
var refId = '${elementLend.pid}';
/**
 * 关闭窗口
 */
function closeWindow() {
	var tab = parent.$('#layout_center_tabs').tabs('getSelected');
	var index = parent.$('#layout_center_tabs').tabs('getTabIndex', tab);
	parent.$('#layout_center_tabs').tabs('close', index);
}
/**
 * 买卖方转换
 */
function formatType(val,row){
	if(val == 1){
		return "买方";
	}else if(val == 2){
		return "卖方";
	}
}

function saveElementLend(){
	
	var lendTime = $('#lendTime').datebox('getValue');// 借出日期
	var originalReturnTime = $('#originalReturnTime').datebox('getValue');// 归还日期
	if(lendTime != '' && originalReturnTime != ''){
		if(lendTime > originalReturnTime){
			$.messager.alert('操作提示', "借出日期大于归还日期", 'info');
			 $('#originalReturnTime').datebox('setValue',"");// 放款日期
			 return;
		}
	}
	
	var lendFilesId = "";
	
	var row = $('#project_file').datagrid('getRows');
	if(row.length > 0){
		for(var i=0;i<row.length;i++){
	  		var result = row[i];
	  		lendFilesId += result.pid+",";
	  	}
		if(lendFilesId != ""){
			lendFilesId = lendFilesId.substring(0,lendFilesId.length-1);
		}
	}else{
		$.messager.alert("操作提示","请至少选择一个借出的要件！","error");
		return;
	}
	clearSelectRows("#project_file");
	$("#lendFilesId").val(lendFilesId);
	
	$('#elementLendForm').form('submit', {
		url : "saveElementLend.action",
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
					$.messager.alert('操作提示',"保存成功!",'info');
					pid=ret.header["pid"];
					$("#elementLendForm input[name='pid']").val(pid);
				}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
		},error : function(result){
			alert("保存失败！");
		}
	});
}

$(function(){
	//展开收起
 	$(document).ready(function(){
 		$(".btn-slide").click(function(){
 			$("#panel").slideToggle("slow");
 			$(".btn-slide").toggleClass("active"); return false;
 		}); 
 	});
 	$('#collect_file_list_dialog').dialog({
 	    onClose:function(){
 	    	$('#collectFileListForm').form('reset');
 	    	$('#collectFileListForm input:checkbox').attr("checked",false);
 	    	$('#collectFileListForm input:checkbox').removeAttr("disabled");
				$('#collectFileListForm .easyui-textbox').textbox("enable");
			$('#collectFileListForm .easyui-numberbox').numberbox("enable");
			$('#ORDER_REMARK').removeAttr("disabled");
 	    }
 	});
 	$.ajax({
		type: "POST",
        data:{"projectId":projectId},
    	url : '${basePath}elementLendController/getProductInfo.action',
		dataType: "json",
	    success: function(data){
	    		var productId = data.pid;
	    		var productName = data.productName;
	    		$("#productId").val(productId);
	    		$("#productName").textbox('setValue',productName);
		}
	});	
 	var pids = $("#lendFilesId").val();
 	if(pids != ""){
 	 	var url = "<%=basePath%>elementLendController/getCollectFileListByPids.action?pids="+pids;
 		$('#project_file').datagrid('options').url = url;
 		$('#project_file').datagrid('reload');
 	}
	//初始化买卖方按钮
 	$.ajax({
		url : "<%=basePath%>integratedDeptController/getBuyerSellerByProjectId.action",
		cache : true,
		type : "POST",
		data : {'projectId' : projectId},
		async : false,
		success : function(data, status) {
			var result = eval("("+data+")");
			var buyerMap = result['buyerMap'];
			var sellerMap = result['sellerMap'];
			//初始化买方姓名按钮
			$.parser.parse($("#buyerNameBtns").append(appendBuyerSellerButs(buyerMap,1)));
			//初始化卖方姓名按钮
			$.parser.parse($("#sellerNameBtns").append(appendBuyerSellerButs(sellerMap,2))); 
		}
	});
});

//通过传入的买卖方list，返回按钮的html
function appendBuyerSellerButs(buyerSellerMap,buyerSellerType){
	if (buyerSellerMap==null||buyerSellerMap.length==0) {
		return "";
	}
	var html="";
	$.each(buyerSellerMap, function(key, value) {
		var name=key;
		html+="<a href='#' onclick=\"initCollectFile('"+name+"','"+buyerSellerType+"')\" style='margin-left: 5px' class='easyui-linkbutton buyer_seller_linkbutton' data-options=\"toggle:true,group:'g2',plain:true\">"+name+"</a>";
	}); 
	return html;
}

//点击买卖方按钮时，初始化该买卖方的收件数据
function initCollectFile(name,buyerSellerType){
	
	var lendFileCodes = "";
	var row = $('#project_file').datagrid('getRows');
	if(row.length > 0){
		for(var i=0;i<row.length;i++){
	  		var result = row[i];
	  		if(result.buyerSellerType == buyerSellerType && result.buyerSellerName == name){
	  			lendFileCodes += result.code+",";
	  		}
	  	}
		if(lendFileCodes != ""){
			lendFileCodes = lendFileCodes.substring(0,lendFileCodes.length-1);
		}
	}
	
	if (buyerSellerType==1) {//等于1显示class 为buyerSellerType的买方属性，等于2的隐藏
	    $(".buyerSellerType2").addClass("hidden_css");
	    $(".buyerSellerType1").removeClass("hidden_css");
	}else{
	    $(".buyerSellerType1").addClass("hidden_css");
	    $(".buyerSellerType2").removeClass("hidden_css");
	} 
	$("#collectFileListProjectId").val(projectId);//项目id
	$("#buyerSellerType").val(buyerSellerType);//买卖类型：买方=1，卖方=2
	$("#buyerSellerName").val(name);//买卖方姓名
	var url = '${basePath}integratedDeptController/getRefundFileInfo.action';
	$.ajax({
		url : url,
		cache : true,
		type : "POST",
		data : {'projectId' : projectId,'buyerSellerType' : buyerSellerType,'buyerSellerName' : name},
		async : false,
		success : function(data, status) {
			var result = eval("("+data+")");
			var collectFileList = result['collectFileList'];//获取已经收取的要件
			var notCollectFileCodeSet = result['notCollectFileCodeSet'];//获取没有收取的要件编码set
			//初始化已经收取的要件信息
			for (var i = 0; i < collectFileList.length; i++) {
				if(collectFileList[i].code=="ORDER"){
			   	 	$('#'+collectFileList[i].code+'_REMARK').val(collectFileList[i].remark);
				}else{
			    	$('#'+collectFileList[i].code+'_REMARK').textbox('setValue',collectFileList[i].remark);
				}
				if(lendFileCodes != ""){
					var lendFiles = lendFileCodes.split(",");
					for(var j = 0; j < lendFiles.length; j++){
						if(collectFileList[i].code == lendFiles[j]){
							$('#'+collectFileList[i].code).prop('checked',true);
						}
					}
				}
			}
			//把没有收件的要件的复选按钮设置成不可操作
  			for (var i = 0; i < notCollectFileCodeSet.length; i++) {
  				$('#'+notCollectFileCodeSet[i]).prop("disabled","disabled");
  			}
		
  			$('#collectFileListForm .easyui-textbox').textbox("disable");
  			$('#collectFileListForm .easyui-numberbox').numberbox("disable");
  			$('#ORDER_REMARK').prop("disabled","disabled");
			$('#collect_file_list_dialog').dialog('open').dialog('setTitle', "选择借出要件");
	}
});
}
/**
 * 选择要件时
 */
function subCollectFileListForm(){
	var lendFilesId = "";
	
	var row = $('#project_file').datagrid('getRows');
	if(row.length > 0){
		for(var i=0;i<row.length;i++){
	  		var result = row[i];
	  		lendFilesId += result.pid+",";
	  	}
		if(lendFilesId != ""){
			lendFilesId = lendFilesId.substring(0,lendFilesId.length-1);
		}
	}
	
	// 提交表单
	$("#collectFileListForm").form('submit', {
		url : "getCollectFilesByCodes.action",
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var ret = eval("(" + result + ")");
			for (var i = 0; i < ret.length; i++) {
				if(lendFilesId.indexOf(ret[i]) != -1){
					
				}else{
					if (i == 0 && lendFilesId == "") {
						lendFilesId = ret[i]+"";
					} else {
						lendFilesId += "," + ret[i];
					}
				}
			}
			$("#lendFilesId").val(lendFilesId);
			$('#collect_file_list_dialog').dialog('close');
			var url = "<%=basePath%>elementLendController/getCollectFileListByPids.action?pids="+lendFilesId;
			$('#project_file').datagrid('options').url = url;
			$('#project_file').datagrid('reload');
		}
	});
}



/**
 * 删除借出要件
 */
function deleteCollectFiles(){
	var rows = $('#project_file').datagrid('getSelections');
	// 要件ID
	var pids = "";
	// 判断是否选择数据
	if(rows.length == 0){
 		$.messager.alert("操作提示","请选择数据!","error");
		return;
	}
	$.messager.confirm("操作提示","确定删除选择的此批要件吗?",
		function(r) {
 			if(r){
		     	var copyRows = [];
		        for ( var j= 0; j < rows.length; j++) {
		        	copyRows.push(rows[j]);
		        }
		     	for(var i =0;i<copyRows.length;i++){
		            var index = $('#project_file').datagrid('getRowIndex',copyRows[i]);
		            $('#project_file').datagrid('deleteRow',index); 
		        }
 			}
		});
}
function collectFilePrint(){
	var codes ="";
	$.each($("#collectFileListForm").find(":checkbox:checked"), function(k, v) {
		codes += $(v).val()+",";
	});
	
	if(codes != ""){
		codes = codes.substring(0,codes.length-1);
	}
	var porpuse= $("#porpuse").textbox('getValue');
	var buyerSellerName=$("#buyerSellerName").val(); 
	var buyerSellerType=$("#buyerSellerType").val(); 
	var url = "${basePath}elementLendController/toPrintElementFile.action?projectId="+projectId+"&buyerSellerName="+buyerSellerName+"&buyerSellerType="+buyerSellerType+"&codes="+codes+"&porpuse="+porpuse;
	window.open(url);
}
</script>

<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="要件借出" id="tab1" style="padding: 10px;" class="contract_dis">
<form id="elementLendForm" action="<%=basePath%>elementLendController/saveElementLend.action" method="POST">
	<input type="hidden" name="lendState" value="${elementLend.lendState }"/>
	<input type="hidden" name="pid" value="${elementLend.pid }"/>
	<input type="hidden" id="projectId" name="projectId" value="${elementLend.projectId }"/>
	<input type="hidden" id="lendFilesId" name="lendFilesId" value="${elementLend.lendFilesId }"/>
	
	<table class="cus_table">
		<tr>
			<td class="label_right"><span class="cus_red">*</span>项目名称：</td>
			<td>
				<input class="easyui-textbox" id="projectName" disabled="disabled" class="easyui-textbox" type="text" value="${elementLend.projectName}" />
			</td>
			<td class="label_right"><span class="cus_red">*</span>产品名称：</td>
			<td>
				<input type="hidden" id="productId" name="productId" value="${elementLend.productId}" />
				<input class="easyui-textbox" id="productName" data-options="required:true" disabled="disabled" value="${elementLend.productName}" />
			</td>
			<td class="label_right"><span class="cus_red">*</span>部门：</td>
			<td>
				<input type="hidden" name="orgId" value="${elementLend.orgId}"/>
				<input class="easyui-textbox" type="text" name="orgName" value="${elementLend.orgName}" disabled="disabled" data-options="required:true"/>
			</td>
			<td class="label_right"><span class="cus_red">*</span>经办人：</td>
			<td>
				<input type="hidden" name="lendUserId" value="${elementLend.lendUserId}" />
				<input type="text" class="easyui-textbox" data-options="required:true" name="realName" disabled="disabled" value="${elementLend.realName}" />
			</td>
		</tr>
		<tr>
			<td class="label_right"><span class="cus_red">*</span>借出日期：</td>
			<td><input name="lendTime" id="lendTime" value="${elementLend.lendTime}" class="text_style easyui-datebox" editable="false" data-options="required:true" missingMessage="请输入借出日期!" /> </td>
			<td class="label_right"><span class="cus_red">*</span>计划归还日期：</td>
			<td>
				<input value="${elementLend.originalReturnTime}" id="originalReturnTime" name="originalReturnTime" class="text_style easyui-datebox" editable="false" data-options="required:true" missingMessage="请输入计划归还日期!" />
			</td>
			<td class="label_right"><span class="cus_red">*</span>用途：</td>
			<td>
				<input type="text" class="easyui-textbox" id="porpuse" data-options="required:true,validType:'length[1,200]'" name="porpuse" value="${elementLend.porpuse}" />
			</td>
		</tr>
	</table>
     <div class="pt10"></div>
     <div id="buyerNameBtns" style="clear:both ;">
                    选择买方：
      </div>
      <div id="sellerNameBtns">
                    选择卖方：
      </div>
     <div class="panel-body1 pt10" style="padding: 10px 10px 0 10px;width: 1039px;">
	<div class=" easyui-panel yj" title="需借出要件" data-options="collapsible:true">
	<div id="toolbar_gtjkr"  style="border-bottom: 0;">
		<a href="javascript:void(0)" id="gtjkrRemove" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteCollectFiles()">删除</a>
	</div>
	<table id="project_file" class="easyui-datagrid" fitColumns="true" style="width:800px;height: auto;"
				     data-options="
				     	url: '',
				     	method: 'post',
				     	rownumbers: true,
			    		fitColumns:true,
				    	singleSelect: false,
				    	toolbar: '#toolbar_gtjkr',
					    pagination: false">
		<thead>
			<tr>
				<th data-options="field:'pid',checkbox:true" ></th>
				<th data-options="field:'buyerSellerType',formatter:formatType" >买方/卖方</th>
				<th data-options="field:'buyerSellerName'" >买卖方姓名</th>
				<th data-options="field:'name'" >文件名</th>
				<th data-options="field:'remark'" >补充</th>
				<th data-options="field:'collectDate',formatter:convertDate" >收件日期</th>
			</tr>
		</thead>
	</table>	
	</div>
	<table class="cus_table" style="width:1030px; padding-top: 10px;">
	<tr>
  	<td style="width:40px; text-align: right;">备注：</td>
    <td colspan="2">
    	<input name="remark" id="remark" class="easyui-textbox" value="${elementLend.remark}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,100]'">
    </td>
    </tr>
    <tr>
     <td></td>
    <td class="align_center"  height="50">
		<a href="#"class="easyui-linkbutton" iconCls="icon-save" class="cus_save_btn" name="save" value="保     存" onclick="saveElementLend();">保存</a>
    	<a href="#"class="easyui-linkbutton" iconCls="icon-clear" onclick="closeWindow()">取消</a>
    </td>
   </tr>
	</table>
	</div>
</form>

   <div id="collect_file_list_dialog" class="easyui-dialog" buttons="#submitCollectFileListDialogDiv" style="width: 533px; height: 500px;" closed="true">
    <div style="margin-left: 30px;margin-top: 10px;line-height: 35px;">
   <form id="collectFileListForm" name="collectFileListForm" action="<%=basePath%>elementLendController/getCollectFilesByCodes.action" method="post">
    <table border="0" cellpadding="0" class="cus_table" cellspacing="0" style="width: 90%;">
    <tr class="buyerSellerType2">
    <td width="120px" ><input type="checkbox" value="SELLER_CARD_NO" id="SELLER_CARD_NO" name="collectFileMapList[0].code">卖方身份证：</td>
    <td><input id="SELLER_CARD_NO_REMARK" style="width: 200px;" name="collectFileMapList[0].remark" class="easyui-textbox"></td>
	</tr>
	
    <tr class="buyerSellerType2">
    <td><input type="checkbox" value="REPAYMENT_CARD" id="REPAYMENT_CARD" name="collectFileMapList[1].code">回款卡/折：</td>
    <td><input id="REPAYMENT_CARD_REMARK" style="width: 200px;" name="collectFileMapList[1].remark" class="easyui-textbox"></td>
    </tr>
    
    <tr class="buyerSellerType2">
    <td><input type="checkbox" value="FORECLOSURE_CARD" id="FORECLOSURE_CARD" name="collectFileMapList[2].code">赎楼卡/折：</td>
    <td><input id="FORECLOSURE_CARD_REMARK" name="collectFileMapList[2].remark" style="width: 200px;" class="easyui-textbox"></td>
    </tr>
    
    <tr class="buyerSellerType1">
    <td width="120px"><input type="checkbox" value="BUYER_CARD_NO" id="BUYER_CARD_NO" name="collectFileMapList[31].code">买方身份证：</td>
    <td><input id="BUYER_CARD_NO_REMARK" style="width: 200px;" name="collectFileMapList[31].remark" class="easyui-textbox"></td>
    </tr>
    
    <tr class="buyerSellerType1">
    <td><input type="checkbox" value="SUPERVISE_CARD" id="SUPERVISE_CARD" name="collectFileMapList[32].code">监管卡/折：</td>
    <td><input id="SUPERVISE_CARD_REMARK" name="collectFileMapList[32].remark" style="width: 200px;" class="easyui-textbox"></td>
    </tr>

    <tr>
    <td><input type="checkbox" value="BANK_CARD" id="BANK_CARD" name="collectFileMapList[3].code">银行卡/折：</td>
    <td> <input id="BANK_CARD_REMARK" name="collectFileMapList[3].remark" style="width: 200px;" class="easyui-textbox"></td>
    </tr>
    
    <tr>
    <td><input type="checkbox" value="NET_SILVER" id="NET_SILVER" name="collectFileMapList[4].code">网上银行：</td>
    <td><input id="NET_SILVER_REMARK" name="collectFileMapList[4].remark" style="width: 200px;" class="easyui-textbox"></td>
    </tr>
    
    <tr>
    <td><input type="checkbox" value="SUPER_NET_SILVER" id="SUPER_NET_SILVER" name="collectFileMapList[5].code">超级网银：</td>
    <td><input id="SUPER_NET_SILVER_REMARK" name="collectFileMapList[5].remark" style="width: 200px;" class="easyui-textbox"></td>
    </tr>
    
    <tr>
    <td><input type="checkbox" value="BANK_PHONE" id="BANK_PHONE" name="collectFileMapList[6].code">银行预留手机号：</td>
    <td><input id="BANK_PHONE_REMARK" name="collectFileMapList[6].remark" style="width: 200px;" class="easyui-textbox" data-options="validType:'phone'"></td>
    </tr>
    
    <tr>
    <td><input type="checkbox" value="TRANSFER_LIMIT_MONEY" id="TRANSFER_LIMIT_MONEY" name="collectFileMapList[7].code">转账限额：</td>
    <td> <input id="TRANSFER_LIMIT_MONEY_REMARK" name="collectFileMapList[7].remark" style="width: 50px;" class="easyui-numberbox"></td>
    </tr>
    
    <tr>
    <td><input type="checkbox" value="COMPANY_NAME" id="COMPANY_NAME" name="collectFileMapList[8].code">公司名称：</td>
    <td><input id="COMPANY_NAME_REMARK" name="collectFileMapList[8].remark" style="width: 200px;" class="easyui-textbox"></td>
    </tr>
    <tr>
    <td><input type="checkbox" value="E_BANK_RELATE_ACCOUNT" id="E_BANK_RELATE_ACCOUNT" name="collectFileMapList[30].code">网银关联账户：</td>
    <td><input id="E_BANK_RELATE_ACCOUNT_REMARK" name="collectFileMapList[30].remark" style="width: 200px;" class="easyui-textbox"></td>
    </tr>
    </table>
    
    <table border="0" cellpadding="0" class="cus_table" cellspacing="0" style="display:none;width: 90%;height: 177px;" id="panel">
    <tr>
    <td><input type="checkbox" value="OFFICIAL_SEAL" id="OFFICIAL_SEAL" name="collectFileMapList[9].code">公章<input id="OFFICIAL_SEAL_REMARK" name="collectFileMapList[9].remark" style="width: 50px;" class="easyui-textbox"></td>
    <td><input type="checkbox" value="PERSONAL_SEAL" id="PERSONAL_SEAL" name="collectFileMapList[10].code">私章<input id="PERSONAL_SEAL_REMARK" name="collectFileMapList[10].remark" style="width: 50px;" class="easyui-textbox"></td>
    <td><input type="checkbox" value="FINANCE_SEAL" id="FINANCE_SEAL" name="collectFileMapList[11].code">财务章</td>
    </tr>
    <tr>
    <td><input type="checkbox" value="OPEN_ACCOUNT_LICENCE" id="OPEN_ACCOUNT_LICENCE" name="collectFileMapList[12].code">开户许可证</td>
    <td><input type="checkbox" value="RESERVED_SIGNATURE_CARD" id="RESERVED_SIGNATURE_CARD" name="collectFileMapList[13].code">预留印鉴卡</td>
    <td><input type="checkbox" value="APPLY_SEAL" id="APPLY_SEAL" name="collectFileMapList[14].code">刻章申请</td>
    </tr>
    <tr>
    <td><input type="checkbox" value="BANK_DEBIT_CARD" id="BANK_DEBIT_CARD" name="collectFileMapList[15].code">银行结算卡</td>
    <td><input type="checkbox" value="BUSINESS_LICENSE_ORIGINAL" id="BUSINESS_LICENSE_ORIGINAL" name="collectFileMapList[16].code">营业执照正本
    <input type="checkbox" value="BUSINESS_LICENSE_COPY" id="BUSINESS_LICENSE_COPY" name="collectFileMapList[17].code">副本</td>
    <td><input type="checkbox" value="TAX_REGISTRATION_ORIGINAL" id="TAX_REGISTRATION_ORIGINAL" name="collectFileMapList[18].code">税务登记证正本
    <input type="checkbox" value="TAX_REGISTRATION_COPY" id="TAX_REGISTRATION_COPY" name="collectFileMapList[19].code">副本</td>
    </tr>
     <tr>
    <td><input type="checkbox" value="ORG_CODE__LICENSE_ORIGINAL" id="ORG_CODE__LICENSE_ORIGINAL" name="collectFileMapList[20].code">组织机构代码证正本</td>
    <td><input type="checkbox" value="ORG_CODE__LICENSE_COPY" id="ORG_CODE__LICENSE_COPY" name="collectFileMapList[21].code">副本</td>
    <td><input type="checkbox" value="CREDIT_ORG_CODE_LICENSE" id="CREDIT_ORG_CODE_LICENSE" name="collectFileMapList[22].code">信用机构代码证</td>
    </tr>
    <tr>
    <td><input type="checkbox" value="PUBLIC_CONVERSION_PRIVATE" id="PUBLIC_CONVERSION_PRIVATE" name="collectFileMapList[23].code">公转私</td>
    <td><input type="checkbox" value="CHEQUE" id="CHEQUE" name="collectFileMapList[24].code">支票 剩余<input id="CHEQUE_REMARK" name="collectFileMapList[24].remark" style="width: 50px;" class="easyui-numberbox">张</td>
    <td><input type="checkbox" value="CHEQUE_PWD_MACHINE" id="CHEQUE_PWD_MACHINE" name="collectFileMapList[25].code">支票密码器</td>
    </tr>
    <tr>
    <td><input type="checkbox" value="POSTING_FEE" id="POSTING_FEE" name="collectFileMapList[26].code">过账费</td>
    <td><input type="checkbox" value="WECHAT_PAY" id="WECHAT_PAY" name="collectFileMapList[27].code">微信支付</td>
    <td><input type="checkbox" value="PAY_TREASURE" id="PAY_TREASURE" name="collectFileMapList[28].code">支付宝</td>
    </tr>
    <tr>
    <td colspan="3"><input type="checkbox" value="ORDER" id="ORDER" name="collectFileMapList[29].code">其他
    <textarea rows="2" id="ORDER_REMARK" name="collectFileMapList[29].remark" maxlength="500" style="width: 250px;"></textarea>
    </td>
    </tr>
     </table>
     <div class="stretch_box">
    	<p class="slide"><a href="javascript:;" class="btn-slide active"></a></p>
    </div>
    <input type="hidden" id="collectFileListCollectFileDate" name="collectDate"/>
    <input type="hidden" id="collectFileListRefundFileDate" name="refundDate"/>
    <input type="hidden" id="collectFileListProjectId" name="projectId"/>
    <input type="hidden" id="buyerSellerType" name="buyerSellerType"/>
    <input type="hidden" id="buyerSellerName" name="buyerSellerName"/>
    <input type="hidden" id="collectFileListRefundFinishStatus" name="refundFinishStatus"/>
    <input type="hidden" id="openType"/>
    </form>
    </div>
   </div>
   <div id="submitCollectFileListDialogDiv">
   		<a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="collectFilePrint()">打印</a>
    	<a id="saveForm" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subCollectFileListForm()">选择</a> 
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#collect_file_list_dialog').dialog('close')">取消</a>
   </div>

 <div class="panel-body1 pt10" id="loanworkflowWrapDivPanel" style="padding: 10px 10px 0 10px;width: 1039px;">
	<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
		<div style="padding:5px">
			<%@ include file="../../common/loanWorkflow.jsp"%> 
			<%@ include file="../../common/task_hi_list.jsp"%>
		</div>
	</div>
</div>	    
   </div>
  </div>
 </div>
</body>
</html>  
