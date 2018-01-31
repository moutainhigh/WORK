<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<style type="text/css">
.cus_table{margin-bottom: 10px;}
.cus_table td{ padding: 5px;height:24px;}
.hidden_css{display: none;}
.mask_index{z-index: 9999};
</style>
<script type="text/javascript">
	//重置按钮
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset');
		$('#productId').combobox('setValue',"-1");
	}
	$(document).ready(function() {
		 var productId = $("#productId").combobox('getValue');//产品ID
			$('#productId').combobox({
				url:'${basePath}productController/getProductLists.action',    
			    valueField:'pid',
			    textField:'productName',
			    onLoadSuccess: function(rec){
			    	if(productId >0){
			    		$('#productId').combobox('setValue',productId);
			    	}else{
			    		$('#productId').combobox('setValue',-1);
			    	}
		       } 
			});
		
	});

	function formatterRecFeeStatus(val, row) {
		if (val == 1) {
			return "未收费";
		} else if (val == 2) {
			return "已收费";
		} else if (val == 3) {
			return "已放款";
		} else if (val == 4) {
			return "放款未完结";
		} else if (val == 5) {
			return "放款申请中";
		} else {
			return "未知";
		}
	}
	function formatterCollectFileStatus(val, row) {
		if (val == 1) {
			return "未收件";
		} else if (val == 2) {
			return "已收件";
		} else {
			return "未知";
		}
	}
	function formatterRefundFileStatus(val, row) {
		if (val == 1) {
			return "未退件";
		} else if (val == 2) {
			return "已退件";
		} else {
			return "未知";
		}
	}
	function formatterPerformJobRemarkStatus(val, row) {
		if (val == 1) {
			return "未备注";
		} else if (val == 2) {
			return "已备注";
		} else {
			return "未知";
		}
	}
	function formatFileName(val, row) {
			return row.file.fileName;
	}
	function formatFileType(val, row) {
			return row.file.fileType;
	}
	function formatUploadDttm(val, row) {
			return convertDateTime(row.file.uploadDttm);
	}
	//格式化查档文件操作
	function formatCheckDocumentFileListOperate(val, row) {
		var fileType=row.file.fileType;
		var fileUrl=row.file.fileUrl;
		var fileName=row.file.fileName;
		fileType = fileType.toLowerCase();
		fileName=fileName.replace(" ","");
		var va = "<a onclick=downLoadFileByPath('"+fileUrl+"','"+fileName+"') href='#'> <font color='blue'>下载</font></a>";	
		if (fileType=="bmp"||fileType=="jpeg"||fileType=="jpg"||fileType=="png"||fileType=="gif") {
			va = va+" <a onclick=lookUpFileByPath('"+fileUrl+"','"+fileName+"') href='#'> <font color='blue'>预览</font></a>";
		}
		return va;
	}
	var buyerMap;//买方姓名身份证map
	var sellerMap;//卖方姓名身份证map
	var supersionReceAccount;//监管账号
	var paymentAccount;//回款账号
	var foreAccount;//赎楼账号
	var idCardNumber;//回款人身份证
	var paymentName;//回款户名
	//打开要件窗口
	function openCollectFileDialog(openType) {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var projectId=row[0].projectId;
			var refundFinishStatus=row[0].refundFileStatus;//退件完结状态：未完结=1，已完结=2
			$("#projectId").val(projectId);
			$("#openType").val(openType);//打开方式：收取要件=1.，退还要件=2
			 $.ajax({
					url : "getBuyerSellerByProjectId.action",
					cache : true,
					type : "POST",
					data : {'projectId' : row[0].projectId},
					async : false,
					success : function(data, status) {
						var result = eval("("+data+")");
						buyerMap = result['buyerMap'];
						sellerMap = result['sellerMap'];
						supersionReceAccount = result['supersionReceAccount'];
						paymentAccount = result['paymentAccount'];
						foreAccount = result['foreAccount'];
						idCardNumber = result['idCardNumber'];
						paymentName = result['paymentName'];
						//初始化买方姓名按钮
						$.parser.parse($("#buyerNameBtns").append(appendBuyerSellerButs(buyerMap,1)));
						//初始化卖方姓名按钮
						$.parser.parse($("#sellerNameBtns").append(appendBuyerSellerButs(sellerMap,2))); 
						if (openType==1) {//打开方式：收取要件=1.，退还要件=2
			                $("#collectFileDate").datebox('setValue',formatterDate(new Date()));
							$("#refundFileDateDiv").addClass("hidden_css");
							$("#collectDateDiv").removeClass("hidden_css");
						}else{
			                $("#refundFileDate").datebox('setValue',formatterDate(new Date()));
							$("#collectDateDiv").addClass("hidden_css");
							$("#refundFileDateDiv").removeClass("hidden_css");
							$("#refundFileDateDiv input[type=radio][name=refundFinishStatus][value="+refundFinishStatus+"]").prop('checked',true);
						}
			            $('#collect_file_dialog').dialog('open').dialog('setTitle', "要件信息--"+row[0].projectName);
					}
				});
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
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
// 		debugger;
		if (buyerSellerType==1) {//等于1显示class 为buyerSellerType的买方属性，等于2的隐藏
		    $(".buyerSellerType2").addClass("hidden_css");
		    $(".buyerSellerType1").removeClass("hidden_css");
		}else{
		    $(".buyerSellerType1").addClass("hidden_css");
		    $(".buyerSellerType2").removeClass("hidden_css");
		} 
		var row = $('#grid').datagrid('getSelections');
		var openType=$("#openType").val();//打开方式：收取要件=1.，退还要件=2
		$("#collectFileListProjectId").val(row[0].projectId);//项目id
		$("#buyerSellerType").val(buyerSellerType);//买卖类型：买方=1，卖方=2
		$("#buyerSellerName").val(name);//买卖方姓名
		if (openType==1) {
		    var collectFileDate=$("#collectFileDate").datebox('getValue');
		    $("#collectFileListCollectFileDate").val(collectFileDate);//收件日期
		    $.ajax({
				url : "getCollectFileInfo.action",
				cache : true,
				type : "POST",
				data : {'projectId' : row[0].projectId,'buyerSellerType' : buyerSellerType,'buyerSellerName' : name},
				async : false,
				success : function(data, status) {
					var result = eval("("+data+")");
					var collectFileList = result['collectFileList'];//获取已经收取的要件
					//初始化已经收取的要件信息
					for (var i = 0; i < collectFileList.length; i++) {
						$('#'+collectFileList[i].code).prop('checked',true);
						if(collectFileList[i].code=="ORDER"){
						    $('#'+collectFileList[i].code+'_REMARK').val(collectFileList[i].remark);
						}else{
						    $('#'+collectFileList[i].code+'_REMARK').textbox('setValue',collectFileList[i].remark);
						}
					}
					$('#collect_file_list_dialog').dialog('open').dialog('setTitle', "选择收取要件");
				}
			});
		}else{
     		var refundFileDate=$("#refundFileDate").datebox('getValue');
     		$("#collectFileListRefundFileDate").val(refundFileDate);//退件日期
     		$.ajax({
    			url : "getRefundFileInfo.action",
    			cache : true,
    			type : "POST",
    			data : {'projectId' : row[0].projectId,'buyerSellerType' : buyerSellerType,'buyerSellerName' : name},
    			async : false,
    			success : function(data, status) {
    				var result = eval("("+data+")");
    				var collectFileList = result['collectFileList'];//获取已经收取的要件
    				var refundFileList = result['refundFileList'];//获取已经退还的要件
    				var notCollectFileCodeSet = result['notCollectFileCodeSet'];//获取没有收取的要件编码set
    				//初始化已经收取的要件信息
    				for (var i = 0; i < collectFileList.length; i++) {
    					if(collectFileList[i].code=="ORDER"){
    					    $('#'+collectFileList[i].code+'_REMARK').val(collectFileList[i].remark);
    					}else{
    					    $('#'+collectFileList[i].code+'_REMARK').textbox('setValue',collectFileList[i].remark);
    					}
    				}
    				//把没有收件的要件的复选按钮设置成不可操作
        			for (var i = 0; i < notCollectFileCodeSet.length; i++) {
        				$('#'+notCollectFileCodeSet[i]).prop("disabled","disabled");
        			}
    				//勾选已退还的要件
        			for (var i = 0; i < refundFileList.length; i++) {
        				$('#'+refundFileList[i].code).prop('checked',true);
        			}
    				
        			$('#collectFileListForm .easyui-textbox').textbox("disable");
        			$('#collectFileListForm .easyui-numberbox').numberbox("disable");
        			$('#ORDER_REMARK').prop("disabled","disabled");
    				$('#collect_file_list_dialog').dialog('open').dialog('setTitle', "选择退还要件");
    			}
    		});
		}
	}
	//收件打印
	function collectFilePrint(){
		var openType=$("#openType").val(); //打开方式：收取要件=1.，退还要件=2
		var projectId=$("#collectFileListProjectId").val(); 
		var buyerSellerName=$("#buyerSellerName").val(); 
		var buyerSellerType=$("#buyerSellerType").val(); 
		var url = "${basePath}integratedDeptController/toCollectFilePrint.action?projectId="+projectId+"&buyerSellerName="+buyerSellerName+"&buyerSellerType="+buyerSellerType+"&printType="+openType;
		//parent.openNewTab(url, "收件打印", true);
		window.open(url);
	}
	//打开执行岗备注窗口
	function openPerformJobRemarkDialog() {
		$("#performJobRemark").val("");
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var projectId=row[0].projectId;
			var performJobRemarkDate=row[0].performJobRemarkDate;
			var performJobRemark=row[0].performJobRemark;
			var performJobRemarkStatus=row[0].performJobRemarkStatus;
			$("#performJobRemarkProjectId").val(projectId);
			$("#performJobRemarkDate").val(projectId);
			if(performJobRemarkDate!=null&&performJobRemarkDate!=''){
			   $("#performJobRemarkDate").datebox('setValue',performJobRemarkDate);
			}else{
			   $("#performJobRemarkDate").datebox('setValue',formatterDate(new Date()));
			}
			if(performJobRemark!=null&&performJobRemark!=''){
				$("#performJobRemark").val(performJobRemark);
			}else if(performJobRemarkStatus==1){
				$("#performJobRemark").val("资料齐全");
			}
			$('#perform_job_remark_dialog').dialog('open').dialog('setTitle', "执行岗备注--"+row[0].projectName);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//打开查档窗口
	function openCheckDocumentDialog(isLoadApprovalInfo) {//isLoadApprovalInfo:是否加载查档审批信息
		var row = $('#grid').datagrid('getSelections');
		var houseListRow = $('#house_list_grid').datagrid('getSelections');//获取选中的物业
		if (houseListRow.length == 1) {
			if(row[0].collectFileStatus==1){
				$.messager.alert("操作提示", "请收件后再查档！", "error");
				return;
			}
			if(row[0].performJobRemarkStatus==1){
				$.messager.alert("操作提示", "请在执行岗备注后再查档！", "error");
				return;
			}
			var projectId=row[0].projectId;
			   $.ajax({
					url : "getCheckDocument.action",
					cache : true,
					type : "POST",
					data : {'checkDocumentId' : row[0].checkDocumentId},
					async : false,
					success : function(data, status) {
						 var result = eval("("+data+")");
						 var checkDocument = result['checkDocument'];
						 var projectProperty = result['projectProperty'];
						 //填充买卖信息
						 $("#sellerName").html(projectProperty.sellerName);//业主
						 $("#houseName").html(houseListRow[0].houseName);//物业名称
						 $("#housePropertyCard").html(houseListRow[0].housePropertyCard);//房产号
						 //填充查档审批信息
						 if (isLoadApprovalInfo) {
						    loadCheckDocumentApprovalInfo(checkDocument);
						 }
						 //填充查档信息
						 $("#checkDate").datebox('setValue',formatterDate(new Date()));
						 $("#checkDocumentProjectId").val(row[0].projectId);
						 $("#checkDocumentId").val(row[0].checkDocumentId);
						 setCombobox3("checkDocumentCheckStatus","CHECK_DOCUMENT_STATUS",-1);//初始化查档状态下拉框
						 $("#startUploadFileBtn").attr("onclick","startUploadFile(1,"+row[0].projectId+")")//初始化上传文件按钮事件
						 loadCheckDocumentFileList(1,"grid_checkDocumentFileList");//加载已经上传文件列表
						 loadCheckDocumentHisListGrid(projectId)//加载查档历史记录列表
			            //重置自动查档表单
			            resetCheckDocumentAutoForm(1,projectProperty.sellerName,projectProperty.housePropertyCard);

						$('#check_document_dialog').dialog('open').dialog('setTitle', "要件查档--"+row[0].projectName);
						
						
						
					}
				});
		} else if (houseListRow.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
			//重置买卖信息
			 $("#sellerName").html("");//业主
			 $("#houseName").html("");//物业名称
			 $("#housePropertyCard").html("");//房产号
			 
			 //重置自动查档表单
			 resetCheckDocumentAutoForm(1,"","");
		}
	}
	//加载物业列表
	function loadHouseListGrid() {
		var row = $('#grid').datagrid('getSelections');
		var projectId=row[0].projectId;
		$("#house_list_grid").datagrid("clearChecked");
		$('#house_list_grid').datagrid({  
			url:'<%=basePath%>beforeLoanController/getHouseByProjectId.action',  
			queryParams:{  
			    	projectId : projectId
			}
	    }); 
	}
	//加载查档历史记录列表
	function loadCheckDocumentHisListGrid(projectId) {
		$('#check_document_his_house_list_grid').datagrid({  
			url:'checkDocumentHisList.action',  
			queryParams:{  
			    	projectId : projectId
			}
	    }); 
	}
	//加载查档审批信息
	function loadCheckDocumentApprovalInfo(checkDocument){
		//填充查档审批信息
		 if (checkDocument.remark!=null&&checkDocument.remark!='') {
		        $("#checkDocumentRemark").val(checkDocument.remark);
		 }
		 if(checkDocument.approvalStatus==2){
		    	$('#submitDiv2 input:radio[name=approvalStatus]')[1].checked = true;
		       $("#submitDiv2 input[type=radio][name=approvalStatus][value=3]").removeAttr("checked");  
		 }else if(checkDocument.approvalStatus==3){
		       $("#submitDiv2 input[type=radio][name=approvalStatus][value=2]").removeAttr("checked");  
		       $('#submitDiv2 input:radio[name=approvalStatus]')[0].checked = true;
		 }
	}
	//根据查档文件类型和项目id加载文件列表
	function loadCheckDocumentFileList(fileCategory,gridId){
		var row = $('#grid').datagrid('getSelections');
		var projectId=row[0].projectId;
		$('#'+gridId).datagrid({  
			    url:'<%=basePath%>integratedDeptController/checkDocumentFileList.action',  
			    queryParams:{  
			    	fileCategory : fileCategory,
			    	projectId : projectId
			    }
		}); 
	}
	//打开查档复核窗口
	function openCheckDocumentReCheckDialog() {
		$("#checkDocumentRemark").val('');
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if(row[0].approvalStatus!=3){
				$.messager.alert("操作提示", "请查档审批通过后再复核！", "error");
				return;
			}
			if(row[0].checkLitigationApprovalStatus!=3){
				$.messager.alert("操作提示", "请查诉讼审批通过后再复核！", "error");
				return;
			}
			var reCheckStatus=row[0].reCheckStatus;//查档复核状态：未复核=1，重新复核=2，同意=3，不同意=4
			var reCheckRemark=row[0].reCheckRemark;//查档复核备注
			var projectId=row[0].projectId;//项目id
			var checkDocumentId=row[0].checkDocumentId;//查档id
			if (reCheckStatus==3||reCheckStatus==4) {
			   $("input[type=radio][name=reCheckStatus][value="+reCheckStatus+"]").prop('checked',true)
			}
			$("#reCheckProjectId").val(projectId);
			$("#reCheckRemark").val(reCheckRemark);
			$("#reCheckCheckDocumentId").val(checkDocumentId);
			$("#startUploadFileBtn").attr("onclick","startUploadFile(2,"+row[0].projectId+")")//初始化上传文件按钮事件
			loadCheckDocumentFileList(2,"grid_documentReCheckFileList");//加载已经上传文件列表
			
            //重置自动查档表单
            resetCheckDocumentAutoForm(2,row[0].sellerName,row[0].housePropertyCard);
			
			$('#check_document_re_check_dialog').dialog('open').dialog('setTitle', "查档复核--"+row[0].projectName);
			
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//打开查诉讼确认窗口
	function openCheckLitigationDialog() {
		$("#checkLitigationRemark").val('');
		 $("#checkLitigationForm input[name='approvalStatus']").removeAttr("checked");
		  $("#checkLitigationStatus").combobox('select','-1');
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			   $.ajax({
					url : "getCheckLitigation.action",
					cache : true,
					type : "POST",
					data : {'checkLitigationId' : row[0].checkLitigationId},
					async : false,
					success : function(data, status) {
						 var checkLitigation = eval("("+data+")");
						 if(checkLitigation.checkDate==null||checkLitigation.checkDate==""){
						    $("#checkLitigationDate").datebox('setValue',formatterDate(new Date()));
						 }else{
						    $("#checkLitigationDate").datebox('setValue',checkLitigation.checkDate);
						 }
						 if(checkLitigation.approvalStatus==2){
							  $('#checkLitigationForm input[type=radio][name=approvalStatus][value=2]').prop('checked',true);
						      $('#checkLitigationForm input[type=radio][name=approvalStatus][value=3]').prop('checked',false);
						 }else if(checkLitigation.approvalStatus==3){
						       $('#checkLitigationForm input[type=radio][name=approvalStatus][value=3]').prop('checked',true);
						       $('#checkLitigationForm input[type=radio][name=approvalStatus][value=2]').prop('checked',false);  
						 }
						// $("input[type=radio][name=approvalStatus][value="+checkDocument.approvalStatus+"]").attr("checked",true);  
						 if (checkLitigation.remark!=null&&checkLitigation.remark!='') {
						        $("#checkLitigationRemark").val(checkLitigation.remark);
						 }
						 if(checkLitigation.checkStatus>1){
						    $("#checkLitigationStatus").combobox('select',checkLitigation.checkStatus);
						 }
						 $("#checkLitigationProjectId").val(row[0].projectId);
						 $("#checkLitigationId").val(row[0].checkLitigationId);
			             $('#check_litigation_dialog').dialog('open').dialog('setTitle', "查诉讼--"+row[0].projectName);
					}
				});
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//选中买卖身份证时，加载名字和身份证
	function getBuyerOrSeller(obj){
		var openType=$("#openType").val();//打开方式：收取要件=1.，退还要件=2
		if (openType==2) {//退要件不需要动态加载
			return;
		}
		 //判断是否选中
		if(!$(obj).is(':checked')){
		    return;
		}
		var buyerSellerName=$("#buyerSellerName").val(); 
		var content="";
		var buyerSellerCardNo="";
		//卖方身份证
		if (obj.value=="SELLER_CARD_NO"&&sellerMap!=null) {
			buyerSellerCardNo=sellerMap[buyerSellerName];
			content=buyerSellerName+"("+buyerSellerCardNo+")"
		//买方身份证	
		}else if(obj.value=="BUYER_CARD_NO"&&buyerMap!=null){
			buyerSellerCardNo=buyerMap[buyerSellerName];
			content=buyerSellerName+"("+buyerSellerCardNo+")"
		//回款卡/折	
		}else if(obj.value=="REPAYMENT_CARD"){
			content=paymentAccount;
		//赎楼卡/折	
		}else if(obj.value=="FORECLOSURE_CARD"){
			content=foreAccount;
		//监管卡/折	
		}else if(obj.value=="SUPERVISE_CARD"){
			content=supersionReceAccount;
		}
		//回款户名
		else if(obj.value=="PAYMENT_NAME"){
			content=paymentName;
		}
		//回款人身份证
		else if(obj.value=="ID_CARD_NUMBER"){
			content=idCardNumber;
		}
		$('#'+obj.value+'_REMARK').textbox('setValue',content);
	}
	//提交查档表单
	function subCheckDocumentForm() {
		var row = $('#house_list_grid').datagrid('getSelections');
		var length=row.length;
		if (length>1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
			return;
		}else if(length==0||length==null){
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		var houseId=row[0].houseId;//物业id
		var projectId=row[0].projectId;//项目id
		$("#checkDocumentForm input[name='houseId']").val(houseId);
		// 提交表单
		$("#checkDocumentForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', '保存成功', 'info');
					loadCheckDocumentHisListGrid(projectId);
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	//提交查档复核表单
	function subReCheckCheckDocument() {
		// 提交表单
		$("#reCheckCheckDocumentForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', '保存成功', 'info');
	                //关闭查档复核窗口
					$('#check_document_re_check_dialog').dialog('close');
					// 刷新查档列表
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	//提交查诉讼表单
	function subCheckLitigationForm() {
		var approvalStatus=$("#checkLitigationForm input[name='approvalStatus']:checked").val();
		if (approvalStatus==null||approvalStatus=='') {
			$.messager.alert('操作提示', '请选择审批结果', 'error');
			return;
		}
		// 提交表单
		$("#checkLitigationForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', '保存成功', 'info');
					
	                //关闭查诉讼窗口
					$('#check_litigation_dialog').dialog('close');
					// 刷新查档列表
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
					//清空表单数据
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	//提交查档审批结果
	function subApprovalStatus() {
		var row = $('#grid').datagrid('getSelections');
		var projectId=row[0].projectId;//项目id
		var url="updateCheckDocumentApprovalStatus.action"//更新查档审批结果url
	    var approvalStatus=$("#submitDiv2 input[name='approvalStatus']:checked").val();//查档的审批结果
		var checkDocumentRemark=$("#checkDocumentRemark").val();//查档的审批意见
		if (approvalStatus==null) {
			$.messager.alert('操作提示', '请选择审批结果', 'error');
			return;
		}
		$.ajax({
				url : url,
				cache : true,
				type : "POST",
				data : {
					'projectId' : projectId,
					'approvalStatus' : approvalStatus,
					'remark' : checkDocumentRemark
				},
				async : false,
				success : function(result, status) {
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						$.messager.alert('操作提示', ret.header["msg"], 'info');
					    //关闭查档窗口
						$('#check_document_dialog').dialog('close');
						 $("#grid").datagrid("clearChecked");
						 $("#grid").datagrid('reload');
					} else {
						$.messager.alert('操作提示', ret.header["msg"], 'error');
					}
				}
			});
	}
	//提交收件
	function subCollectFileListForm() {
		var openType=$("#openType").val();//打开方式：收取要件=1.，退还要件=2
		var row = $('#grid').datagrid('getSelections');
		var refundFinishStatus=row[0].refundFileStatus;//退件完结状态：未完结=1，已完结=2
		var collectFileStatus=row[0].collectFileStatus;//收件状态：未收件=1，已收件=2
		if (refundFinishStatus==2) {
			$.messager.alert('操作提示', '退件已完结，不可再操作', 'error');
			return;
		}
		if (openType==2&&collectFileStatus==1) {
			$.messager.alert('操作提示', '未收件，不可再退件', 'error');
			return;
		}
		var url;
		if (openType==1) {
			url='${basePath}integratedDeptController/collectFile.action'
		} else {
			url='${basePath}integratedDeptController/refundFile.action'
		}
		// 提交表单
		$("#collectFileListForm").form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', '保存成功', 'info');
	                //关闭收件窗口
					$('#collect_file_list_dialog').dialog('close');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	//提交执行岗备注
	function subPerformJobRemarkForm() {
		// 提交表单
		$("#performJobRemarkForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', '保存成功', 'info');
	                //关闭收件窗口
					$('#perform_job_remark_dialog').dialog('close');
					// 刷新查档列表
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
					//清空表单数据
					$('#performJobRemarkForm').form('reset')
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	//打开日志窗口
	function openLogListDialog(logType) {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			$('#log_dialog').dialog('open').dialog('setTitle', "日志--"+row[0].projectName);
			$('#grid_log').datagrid({  
			    url:'<%=basePath%>sysLogController/getAllSysLog.action?projectId='+row[0].projectId,  
			    queryParams:{  
			    	logType : logType
			    }
			}); 
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//自动输入资料补全字符串
	function autoImportData(){
		if($('input:radio[name=dataCompleteStatus]')[0].checked==true){
			$("#performJobRemark").val("资料齐全");
		}else{
			$("#performJobRemark").val("资料未齐全");
		}
	}
	//退件完结
	function refundFileFinish(){
		var row = $('#grid').datagrid('getSelections');
		var projectId=row[0].projectId;
		var refundFinishStatus=row[0].refundFileStatus;
		var collectFileStatus=row[0].collectFileStatus;
		if (collectFileStatus==1) {
			$.messager.alert('操作提示', '未收件，不可完结', 'error');
			return;
		}
		if (refundFinishStatus!=2) {
			$.messager.confirm('温馨提示','确认后则不可再退件,是否确认?',function(r){
			    if (r){
			    	$.ajax({
						url : "refundFileFinish.action",
						cache : true,
						type : "POST",
						data : {'projectId' : projectId},
						async : false,
						success : function(data, status) {
							var ret = eval("(" + data + ")");
							if (ret && ret.header["success"]) {
								$.messager.alert("操作提示", ret.header["msg"]);
								$("#grid").datagrid("clearChecked");
								$("#grid").datagrid('reload');
							}else{
								$.messager.alert('操作提示', ret.header["msg"], 'error');
							}
						}
					});
			    }
			});	
		}
	}
	  var integratedDeptList = {
				// 项目名称format
				formatProjectName:function(value, row, index){
					var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>";
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
				}
			}
	$(function(){
		$('#checkDocumentCheckStatus').combobox({  
		       onChange:function(value){  
		    	   //查档选项无效等，默认审批结果为不通过
		         if (value>=4) {
					$("#submitDiv2 input[name='approvalStatus']")[1].checked = true;
				 }
		       }  
		   });  
		//收件窗口关闭时，清空表单数据
	 	$('#collect_file_dialog').dialog({
	 	    onClose:function(){
	 	     	buyerMap=null;
	 		    sellerMap=null;
	 	    	$('#collectFileForm').form('reset');
	 	    	$(".buyer_seller_linkbutton").remove();//把买卖双方的姓名按钮移除
	 	    	$("#refundFileDateDiv input[type=radio][name=refundFinishStatus][value=1]").prop('checked',true);
	 	    	$("#refundFileDateDiv input[type=radio][name=refundFinishStatus][value=2]").prop('checked',false);
	 	    }
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
		//查档窗口关闭时，清空表单数据
	 	$('#check_document_dialog').dialog({
	 	    onClose:function(){
	 	    	$('#checkDocumentForm').form('reset');
				 $("#sellerName").html("");
				 $("#houseName").html("");
				 $("#housePropertyCard").html("");
				 $("#checkDocumentRemark").val('');
				 $("#startUploadFileBtn").removeAttr("onclick")//溢出上传文件按钮事件
	 	    }
	 	});
		//查档复核窗口关闭时，清空表单数据
	 	$('#check_document_re_check_dialog').dialog({
	 	    onClose:function(){
	 	    	$("input[type=radio][name=reCheckStatus][value=3]").prop('checked',true);
	 	    	$("input[type=radio][name=reCheckStatus][value=4]").prop('checked',false);
	 	    	$("#reCheckRemark").val("");
	 	    	$("#startUploadFileBtn").removeAttr("onclick")//溢出上传文件按钮事件
	 	    }
	 	});
		//执行岗备注窗口关闭时，清空表单数据
	 	$('#perform_job_remark_dialog').dialog({
	 	    onClose:function(){
	 	    	$('#performJobRemarkForm').form('reset');
	 	    	$('input:radio[name=dataCompleteStatus]')[0].checked==true;
	 	    	$('input:radio[name=dataCompleteStatus]')[1].checked==false;
	 	    }
	 	});
		//文件窗口关闭时，清空上传数据
	 	$('#fileUploadDialog').dialog({
	 	    onClose:function(){
	 	    	$('#uploadify').uploadify('cancel','*');
	 	    }
	 	});
	 	//收件展开收起
	    $(".btn-slide").click(function(){
	 		$("#panel").slideToggle("slow");
	 		$(".btn-slide").toggleClass("active"); return false;
	 	}); 
	    $('#grid').datagrid({  
        	//当列表数据加载完毕
            onLoadSuccess:function(data){  
            	//备注提示
            	$(".note").tooltip({
                     onShow: function(){
                            $(this).tooltip('tip').css({ 
                                width:'300',
                                boxShadow: '1px 1px 3px #292929'                        
                            });
                      }
                 })
            },
            rowStyler:function(index,row){    
                if (row.isChechan==1){    
                    return 'background-color:#FFAF4C;';    
                }    
            } 
        }) ;
	    $('#house_list_grid').datagrid({  
        	//当物业列表数据加载完毕
            onLoadSuccess:function(data){  
            	//系统自动选中第一条并打开查档窗口
            	$(this).datagrid('selectRow',0);
            	openCheckDocumentDialog(true);
            },
            onClickRow : function(rowIndex, rowData) {
            	   openCheckDocumentDialog(false);
			}
        }) ;
	    initUploadify();
	});
	  function initUploadify() {
			//多文件上传  //兼容火狐 需要  sessionid，否则拦截登陆302
			var uploadFileCount = 0 ;
		    $("#uploadify").uploadify({  
		    	'swf'       : '${ctx}/p/xlkfinance/plug-in/uploadify/uploadify.swf',  
		        'uploader'  : '${basePath}integratedDeptController/uploadCheckDocumentFile.action;jsessionid=${pageContext.session.id}',    
		        //'folder'         : '/upload',  
		        'queueID'        : 'fileQueue',
		        'cancelImg'      : '${ctx}/p/xlkfinance/plug-in/uploadify/css/img/uploadify-cancel.png',
		        'buttonText'     : '上传文件',
		        'auto'           : false, //设置true 自动上传 设置false还需要手动点击按钮 
		        'multi'          : true,  
		        'wmode'          : 'transparent',  
		        'simUploadLimit' : 9,  
		        'fileSizeLimit'   : '200MB',
		        'fileTypeExts'        : '*.txt;*.doc;*.pdf;*.bmp;*.jpeg;*.jpg;*.png;*.gif;*.xls;*.wps;*.rtf;*.docx;*.xlsx;*.zip;*.ral;*.pptx;*.tiff;*.tif;*.rar',  
		        'fileTypeDesc'       : 'All Files',
		        'method'       : 'get',
				onUploadSuccess : function(file, data, response) {
					 uploadFileCount++;
				},
				onQueueComplete : function(file) { //当队列中的所有文件全部完成上传时触发 
					$.messager.alert("操作提示", "上传完成"+uploadFileCount+"个文件");
					uploadFileCount = 0;
					loadCheckDocumentFileList(1,"grid_checkDocumentFileList");//加载已经上传文件列表
					loadCheckDocumentFileList(2,"grid_documentReCheckFileList");//加载已经上传文件列表
				}
			});
		}
		//打开上传文件对口框
		function openUploadFileDialog(){
			$('#fileUploadDialog').dialog('open').dialog('setTitle', "文件上传");
		}
		//上传文件,fileCategory:文件类型：查档=1，查档复合=2
		function startUploadFile(fileCategory,projectId){
			if($("#fileQueue .fileName") == null || $("#fileQueue .fileName").length  == 0 ){
				$.messager.alert("请选择文件");
				return false;
			}
			//多文件上传方式
			$('#uploadify').uploadify('settings','formData',{"projectId":projectId,"fileCategory":fileCategory});
			$('#uploadify').uploadify('upload','*');
		}
		function deleteCheckDocumentFile(gridId){
			var row = $('#'+gridId).datagrid('getSelections');
			if (row.length == 0) {
				$.messager.alert("操作提示", "请选择数据", "error");
			}
			var projectId=row[0].projectId;
			var ids="";
			for (var i = 0; i < row.length; i++) {
				var pid = row[i].pid;
				ids=ids+pid+",";
			}
			$.messager.confirm('温馨提示','确认删除该批文件,是否确认?',function(r){
			    if (r){
			    	$.ajax({
						url : "${basePath}integratedDeptController/deleteCheckDocumentFile.action",
						cache : true,
						type : "POST",
						data : {'projectId' : projectId,'idsStr' : ids},
						async : false,
						success : function(data, status) {
							var ret = eval("(" + data + ")");
							if (ret && ret.header["success"]) {
								$.messager.alert("操作提示", ret.header["msg"]);
								$('#'+gridId).datagrid("clearChecked");
								$('#'+gridId).datagrid('reload');
							}else{
								$.messager.alert('操作提示', ret.header["msg"], 'error');
							}
						}
					});
			    }
			});	
		}
		
		
		
		
		//----------------------------自动查档开始

		//设置自动查档显示/隐藏
		function setCheckDocShow(id){
			if($("#"+id).is(":hidden")){
				//显示
				$("#check_doc_a_showbtn").text("自动查档-收起");
				$("#"+id).show();
				//调整高度
				$("#check_document_dialog").height(520);
				$("#check_document_dialog").panel("move",{top:50});  
			}else{
				//隐藏
				$("#check_doc_a_showbtn").text("自动查档-展开");
				$("#"+id).hide();
				$("#check_document_dialog").height(258);
				$("#check_document_dialog").panel("move",{top:150});  
			}
		}
		
		
		
		//重置房产信息
		function resetCheckDocumentAutoForm(checkDocType,sellerName,housePropertyCard){
			var check_doc_queryType ="check_doc_queryType";
			var check_doc_certType ="check_doc_certType";
			var check_doc_year ="check_doc_year";
			var check_doc_certNo ="check_doc_certNo";
			var check_doc_certNo2 ="check_doc_certNo2";
			var check_doc_personInfo ="check_doc_personInfo";
			var check_doc_result ="check_doc_result";
			
			var checkDocumentAutoForm = "checkDocumentAutoForm";
			var check_document_dialog = "check_document_dialog";
			var checkDocumentForm_table = "checkDocumentForm_table";
			
			if(checkDocType == 2){
				check_doc_queryType ="re_check_doc_queryType";
				check_doc_certType ="re_check_doc_certType";
				check_doc_year ="re_check_doc_year";
				check_doc_certNo ="re_check_doc_certNo";
				check_doc_certNo2 ="re_check_doc_certNo2";
				check_doc_personInfo ="re_check_doc_personInfo";
				check_doc_result ="re_check_doc_result";
				
				check_document_dialog = "check_document_re_check_dialog";
				checkDocumentAutoForm = "re_checkDocumentAutoForm";
				checkDocumentForm_table = "reCheckCheckDocumentForm_table";
			}
			
			//判断是否是小科，小科用户则显示
/* 			$.ajax({
				type: "POST",
		        url: "${basePath}sysUserController/getUserOrg.action",
		        dataType: "json",
		        async: false,
		        success: function(result){
		        	console.log(result);
		        	//万通用户，隐藏查档,小科显示
		        	if(result.header.success == true && result.body.userOrg == 2){
		        		$("#"+checkDocumentForm_table).css("width","50%");
		        		$("#"+checkDocumentForm_table).css("float","left");
 		                $("#"+check_document_dialog).dialog({
		                    width: 750
		                }); 
		        		$("#"+checkDocumentAutoForm).show();
		        		$("#"+check_document_dialog).dialog("move",{left:200,top:50}); 
		        	}
				},error : function(result){
					alert("操作失败！");
				}
			 }); */
			 
            
            if($("#"+checkDocumentAutoForm).is(":hidden")){
				//全部显示
	     		$("#"+checkDocumentForm_table).css("width","45%");
	     		$("#"+checkDocumentForm_table).css("float","left");
	            $("#"+check_document_dialog).dialog({width: 788}); 
            	$("#"+checkDocumentAutoForm).show();
	     		$("#"+check_document_dialog).dialog("move",{left:200,top:50}); 
	        } 
			
			var queryType = document.getElementsByName(check_doc_queryType);
			queryType[0].checked = true;
			var check_doc_certType_temp = document.getElementsByName(check_doc_certType);
			check_doc_certType_temp[0].checked = true;
			
			setCheckDocCertType(checkDocType,1);
			
			$("#"+check_doc_year).textbox('setValue',"");
			$("#"+check_doc_certNo2).textbox('setValue',"");
			$("#"+check_doc_certNo).textbox('setValue',housePropertyCard);
			$("#"+check_doc_personInfo).textbox('setValue',sellerName);
			$("#"+check_doc_result).val("");
		}
		
		/*
		 * 选择产权证书类型 
		 * checkDocType 1:查档  2：复核查档
		 * certType 1：房地产权证书 2：不动产权证书
		 */
		function setCheckDocCertType(checkDocType,certType){
			var certTypeContent = "certTypeContent";
			var certTypeContent2 = "certTypeContent2";

			if(checkDocType == 2){
				certTypeContent = "re_certTypeContent";
				certTypeContent2 = "re_certTypeContent2";
			}
			if(certType == "1"){
				$("#"+certTypeContent).show();
				$("#"+certTypeContent2).hide();
			}else{
				$("#"+certTypeContent).hide();
				$("#"+certTypeContent2).show();
			}
		}
		
		//立即查询 checkDocType 1:查档  2：复核查档
		function searchCheckDoc(checkDocType){
			
			var checkDocumentAutoForm = "checkDocumentAutoForm";
			var check_doc_queryType = "check_doc_queryType";
			var check_doc_certType = "check_doc_certType";
			var check_doc_personInfo = "check_doc_personInfo";
			var check_doc_certNo = "check_doc_certNo";
			var check_doc_year = "check_doc_year";
			var check_doc_certNo2 = "check_doc_certNo2";
			var check_doc_result = "check_doc_result";
			
			if(checkDocType  == 2 ){
				checkDocumentAutoForm = "re_checkDocumentAutoForm";
				check_doc_queryType = "re_check_doc_queryType";
				check_doc_certType = "re_check_doc_certType";
				check_doc_personInfo = "re_check_doc_personInfo";
				check_doc_certNo = "re_check_doc_certNo";
				check_doc_year = "re_check_doc_year";
				check_doc_certNo2 = "re_check_doc_certNo2";
				check_doc_result = "re_check_doc_result";
			}
			
			var queryType=$("#"+checkDocumentAutoForm+" input[name='"+check_doc_queryType+"']:checked").val();
			var certType=$("#"+checkDocumentAutoForm+" input[name='"+check_doc_certType+"']:checked").val();
			
			var certNo = "";
			var year = "" ;
			var personInfo=$("#"+check_doc_personInfo).textbox('getValue');
			
			if(certType == "1"){
				certNo=$("#"+check_doc_certNo).textbox('getValue');
				
				console.log("certNo.length:"+certNo.length);
				if(certNo == "" ){
					$.messager.alert('操作提示', "产权证书编号不能为空", 'error');
					return ;
				}else if(certNo.length >20 ){
					$.messager.alert('操作提示', "产权证书编号最长为20位", 'error');
					return ;
				}else if(isNaN(certNo)){
					$.messager.alert('操作提示', "产权证书编号只能为数字", 'error');
					return ;
				}
			}else{
				year=$("#"+check_doc_year).numberbox('getValue');
				certNo=$("#"+check_doc_certNo2).textbox('getValue');
				
				if(year == "" ){
					$.messager.alert('操作提示', "产权证书年份不能为空", 'error');
					return ;
				}else if(!(year.length  == 4) ){
					$.messager.alert('操作提示', "产权证书年份长度为4位", 'error');
					return ;
				}else if(certNo == "" ){
					$.messager.alert('操作提示', "产权证书编号不能为空", 'error');
					return ;
				}else if(certNo.length >20 ){
					$.messager.alert('操作提示', "产权证书编号最长为20位", 'error');
					return ;
				}else if(isNaN(certNo)){
					$.messager.alert('操作提示', "产权证书编号只能为数字", 'error');
					return ;
				}
			}
			if(personInfo == "" ){
				$.messager.alert('操作提示', "权利人/身份证不能为空", 'error');
				return ;
			}else if(personInfo.length > 50 ){
				$.messager.alert('操作提示', "权利人/身份证最长为50位", 'error');
				return ;
			}
			
			MaskUtil.mask("查询中,请耐心等候......");  
    		//显示并加载	
	    		$.ajax({
    			type: "POST",
    	        url:  "${basePath}checkDocApi/saveHouseCheckDoc.action",
    	        data:{queryType:queryType,
    	        	certType:certType,
    	        	year:year,
    	        	certNo:certNo,
    	        	personInfo:personInfo
    	        	},
    	        dataType: "json",
    	        timeout:60000,
    	        success: function(result){
    	        	MaskUtil.unmask()
    	        	console.log(result);
    	        	
    	        	if(result.header.success == true){
    	        		$("#"+check_doc_result).val("查档时间："+result.body["checkDocTime"]+"\n" +result.body["checkDocContent"]);
    	        		$.messager.alert('操作提示', result.header["msg"]);
    	        	}else{
    	        		$.messager.alert('操作提示', result.header["msg"], 'error');
    	        	}
    			 },error : function(result){
    				 if(result.statusText == "timeout"){
    					 $.messager.alert('操作提示', "查询超时", 'error');
    				 }else{
    					 $.messager.alert('操作提示', "操作失败", 'error');
    				 }
            		 MaskUtil.unmask()
    			}
    		 }); 
			
			
		/* 	$.ajax({
				type: "POST",
		        url: "${basePath}checkDocApi/getConfigUrl.action",
		        data:{configType:1},
		        dataType: "json",
		        success: function(result1){
		        	 
					console.log(result1);
					if(result1.header.success== false ){
						$.messager.alert('操作提示',result1.header["msg"],'error');
						return ;
					}   
					
					var url = result1.body["url"];
		        	MaskUtil.mask("查询中,请耐心等候......");  
		    		//显示并加载	
	 	    		$.ajax({
		    			type: "POST",
		    	        url: url,
		    	        data:{queryType:queryType,
		    	        	certType:certType,
		    	        	year:year,
		    	        	certNo:certNo,
		    	        	personInfo:personInfo,
		    	        	dataType:"jsonp"
		    	        	},
		    	        dataType: "jsonp",
		    	        timeout:60000,
		    	        success: function(result){
		    	        	
		    	        	MaskUtil.unmask()
		    	        	console.log(result);
		    	        	if(result.code == "ok"){
		    	        		$("#"+check_doc_result).val("查档时间："+result.data.createTime+"\n" +result.data.result);
		    	        		$.messager.alert('操作提示', result.msg);
		    	        	}else{
		    	        		$.messager.alert('操作提示', result.msg, 'error');
		    	        	}
		    			 },error : function(result){
		    				 if(result.statusText == "timeout"){
		    	        		$.messager.alert('操作提示', "查询超时", 'error');
		    				 }else{
		    	        		$.messager.alert('操作提示', "操作失败", 'error');
		    				 }
		            		 MaskUtil.unmask()
		    			}
		    		 }); 
		        	 
				 },error : function(result){
	        		$.messager.alert('操作提示', "查询配置失败", 'error');
				}
			 }); */
			
		}
		
		
		
		//----------------------------自动查档结束
		
 function convertDateHours(strDate){
	// 判断是否存在数据  如果不存在，直接退出方法 
	if(null == strDate || "" == strDate){
		return "";
	}
	// 去掉时间后面的  .加数字 
	var index = strDate.indexOf(".");
	// 如果不存在,那index就等于总长度
	if(index == -1){
		index = strDate.length;
	}
	// 截取时间字符串,并转换
	var str=strDate.substring(0,index).toString();
	// 把时间里面的  -  转换城 /
	str = str.replace(/-/g,"/");
	// 转换成时间
	var date = new Date(str);
	// 获取时间的年月日时分秒
	var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    // 拼接年月日
    var strDateTime = year+'-'+(month<10?('0'+month):month)+'-'+(day<10?('0'+day):day);
    // 拼接时分秒
    strDateTime += " "+(hour<10?('0'+hour):hour) ;
    // 返回 YYYY-MM-dd hh:mm:ss
    return strDateTime;
}
		
		
		
		
		
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>integratedDeptController/checkDocumentList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td width="80" align="right" height="28">项目编号：</td>
        <td><input class="easyui-textbox" name="projectNumber" id="projectNumber" /></td>
        <td width="100" align="right" height="28">项目名称：</td>
        <td><input class="easyui-textbox" name="projectName" id="projectName" /></td>
        <td width="80" align="right" height="28">放款状态：</td>
        <td><select class="easyui-combobox" id="recFeeStatus" name="recFeeStatus" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未收费</option>
          <option value=2>已收费</option>
          <option value=5>放款申请中</option>
          <option value=3>已放款</option>
          <option value=4>放款未完结</option>
        </select></td>
        <td width="90" align="right" height="28">项目审批状态：</td>
        <td><select class="easyui-combobox" name="projectForeclosureStatus" style="width: 150px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>待客户经理提交</option>
          <option value=2>待部门经理审批</option>
          <option value=3>待业务总监审批</option>
          <option value=4>待审查员审批</option>
          <option value=5>待风控初审</option>
          <option value=6>待风控复审</option>
          <option value=7>待风控终审</option>
          <option value=8>待风控总监审批</option>
          <option value=9>待总经理审批</option>
          <option value=10>已审批</option>
          <option value=11>已放款</option>
          <option value=12>业务办理已完成</option>
          <option value=13>已归档</option>
          <option value=15>待合规复审</option>
          
        </select></td>
       </tr>
       <tr>
        <td width="80" align="right" height="28">要件状态：</td>
        <td>
        <select class="easyui-combobox" name="collectFileStatus" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未收件</option>
          <option value=2>已收件</option>
        </select>
        </td>
        <td width="100" align="right" height="28">执行岗备注状态：</td>
        <td>
        <select class="easyui-combobox" name="performJobRemarkStatus" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未备注</option>
          <option value=2>已备注</option>
        </select>
        </td>
        <td width="80" align="right" height="28">诉讼状态：</td>
        <td>
        <select class="easyui-combobox" name="checkLitigationStatus" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未查讼</option>
          <option value=2>无新增诉讼</option>
          <option value=3>有增诉讼</option>
          <option value=4>有新增诉讼非本人</option>
        </select>
        </td>
        <td width="90" align="right" height="28">诉讼审批状态：</td>
        <td>
        <select class="easyui-combobox" name="checkLitigationApprovalStatus" style="width: 150px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未审批</option>
          <option value=2>不通过</option>
          <option value=3>通过</option>
          <option value=4>重新查诉讼</option>
        </select>
        </td>
       </tr>
       <tr>
       <tr>
        <!-- <td width="80" align="right" height="28">查档状态：</td>
        <td>
        <select class="easyui-combobox" name="checkStatus" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未查档</option>
          <option value=2>抵押</option>
          <option value=3>有效</option>
          <option value=4>无效</option>
          <option value=5>查封</option>
          <option value=6>抵押查封</option>
          <option value=7>轮候查封</option>
        </select>
        </td> -->
        <td width="100" align="right" height="28">查档审批状态：</td>
        <td>
        <select class="easyui-combobox" name="approvalStatus" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未审批</option>
          <option value=2>不通过</option>
          <option value=3>通过</option>
          <option value=4>重新查档</option>
        </select>
        </td>
        <td width="100" align="right" height="28">查档复核状态：</td>
        <td>
        <select class="easyui-combobox" name="reCheckStatus" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未复核</option>
          <option value=2>重新复核</option>
          <option value=3>同意</option>
          <option value=4>不同意</option>
        </select>
        </td>
        
         <td align="right" >客户经理：</td>
        <td><input class="easyui-textbox" name="pmUserName" /></td>
        
        <td>
        <div class="none">
        <select class="easyui-combobox" name="ddfff" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
        </select>
        </div>
        </td>
       </tr>
       <tr>
       <tr>
        <td align="right" >物业名称：</td>
        <td><input class="easyui-textbox" name="houseName" /></td>
        <td align="right" >买方姓名：</td>
        <td><input class="easyui-textbox" name="buyerName" /></td>
        <td align="right" >卖方姓名：</td>
        <td><input class="easyui-textbox" name="sellerName" /></td>
       </tr>
       <tr>
          <td width="100" align="right"  height="28">产品名称：</td>
		  <td colsapn="2">
			 <input class="easyui-combobox" editable="false" style="width:129px;" name="productId" id="productId"/>						
		  </td>
       </tr>
       
       <tr>
        <td colspan="5"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>
      </table>
     </div>
    </form>

    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
     <shiro:hasAnyRoles name="COLLECT_FILE,ALL_BUSINESS">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openCollectFileDialog(1)">收件</a>
     </shiro:hasAnyRoles>
     <shiro:hasAnyRoles name="REFUND_COLLECT_FILE,ALL_BUSINESS">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openCollectFileDialog(2)">退要件</a>
     </shiro:hasAnyRoles>
     <shiro:hasAnyRoles name="PERFORM_JOB_REMARK,ALL_BUSINESS">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openPerformJobRemarkDialog()">执行岗备注</a>
     </shiro:hasAnyRoles>
     <shiro:hasAnyRoles name="CHECK_LITIGATION,ALL_BUSINESS">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openCheckLitigationDialog()">查诉讼</a>
     </shiro:hasAnyRoles>
     <shiro:hasAnyRoles name="CHECK_DOCUMENT,ALL_BUSINESS">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="loadHouseListGrid()">查档</a>
     </shiro:hasAnyRoles>
     <shiro:hasAnyRoles name="CHECK_DOCUMENT_RE_CHECK,ALL_BUSINESS">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openCheckDocumentReCheckDialog()">查档复核</a>
     </shiro:hasAnyRoles>
     <shiro:hasAnyRoles name="CHECK_LITIGATION_LOG,ALL_BUSINESS">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openLogListDialog('贷中-查诉讼')">查诉讼日志</a>
     </shiro:hasAnyRoles>
     <shiro:hasAnyRoles name="CHECK_DOCUMENT_LOG,ALL_BUSINESS">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openLogListDialog('贷中-查档')">查档日志</a>
     </shiro:hasAnyRoles>
    </div>

   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="要件查档列表"  style="height: 100%; width: auto;"
     data-options="
		    url: '${basePath}integratedDeptController/checkDocumentList.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead data-options="frozen:true">
		 <tr>
			<th data-options="field:'pid',checkbox:true"></th>
       		<th data-options="field:'projectId',hidden:true">项目id</th>
       		<th data-options="field:'housePropertyCard',hidden:true">房产证号</th>
       		<th data-options="field:'projectName',formatter:integratedDeptList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <thead>
      <tr>
<!--        <th data-options="field:'pid',checkbox:true"></th>
       <th data-options="field:'projectId',hidden:true">项目id</th>
       <th data-options="field:'housePropertyCard',hidden:true">房产证号</th>
       <th data-options="field:'projectName',formatter:integratedDeptList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th> -->
       <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th>
       <th data-options="field:'productName',sortable:true" align="center" halign="center">产品名称</th>
       <th data-options="field:'houseName',sortable:true" align="center" halign="center">物业名称</th>
       <th data-options="field:'loanMoney',sortable:true,formatter:formatMoney" align="center" halign="center">借款金额</th>
       <th data-options="field:'loanDays',sortable:true" align="center" halign="center">借款天数</th>
       <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方姓名</th>
       <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方姓名</th>
        <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">客户经理</th>
       <th data-options="field:'recFeeStatus',sortable:true,formatter:formatterRecFeeStatus" align="center" halign="center">收费状态</th>
       <th data-options="field:'projectForeclosureStatus',sortable:true,formatter:formatterProjectStatus" align="center" halign="center">项目审批状态</th>
       <th data-options="field:'isChechan',sortable:true,formatter:formatterIsChechan" align="center" halign="center">是否撤单</th>
       <th data-options="field:'createrDate',sortable:true,formatter:convertDate" align="center" halign="center">审批时间</th>
       <th data-options="field:'collectFileStatus',sortable:true,formatter:formatterCollectFileStatus" align="center" halign="center">要件收取状态</th>
       <th data-options="field:'collectFileDate',sortable:true,formatter:convertDate" align="center" halign="center">收件时间</th>
       <th data-options="field:'refundFileStatus',sortable:true,formatter:formatterRefundFileStatus" align="center" halign="center">退要件状态</th>
       <th data-options="field:'performJobRemarkStatus',sortable:true,formatter:formatterPerformJobRemarkStatus" align="center" halign="center">执行岗备注状态</th>
       <th data-options="field:'checkLitigationStatus',sortable:true,formatter:formatterCheckLitigationStatus" align="center" halign="center">查诉讼状态</th>
       <th data-options="field:'checkLitigationApprovalStatus',sortable:true,formatter:formatterCheckLitigationApprovalStatus" align="center" halign="center">查诉讼审批状态</th>
       <th data-options="field:'checkStatusStr',sortable:true" align="center" halign="center">查档状态</th>
       <th data-options="field:'approvalStatus',sortable:true,formatter:formatterCheckDocumentApprovalStatus" align="center" halign="center">查档审批状态</th>
       <th data-options="field:'reCheckStatus',sortable:true,formatter:formatterReCheckStatus" align="center" halign="center">查档复核状态</th>
       <th data-options="field:'performJobRemark',sortable:true,formatter:integratedDeptList.formatTooltip,width:100" align="center" halign="center">执行岗备注</th>
       <th data-options="field:'reCheckRemark',sortable:true,formatter:integratedDeptList.formatTooltip,width:100" align="center" halign="center">查档复核备注</th>
       <th data-options="field:'checkDocumentId',hidden:true"></th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="collect_file_dialog" class="easyui-dialog" data-options="modal:true" buttons="#submitDiv1" style="width: 433px; height: 235px;" closed="true">
    <div style="margin-left: 30px;margin-top: 10px;line-height: 35px;">
   <form id="collectFileForm" name="collectFileForm" action="#" method="post">
    <div style="padding:5px;">
      <div style="float: left;" id="collectDateDiv">
                    收件日期：<input name="collectDate" id="collectFileDate" class="easyui-datebox" data-options="required:true" editable="false">
      </div>
      <div id="refundFileDateDiv" style="float: left;">
                    退件日期：<input name="refundFileDate" id="refundFileDate" class="easyui-datebox" data-options="required:true" editable="false">
       <br>
                    退件是否完结：<input type="radio" name="refundFinishStatus" checked="checked" value="1">未完结
                <input type="radio" name="refundFinishStatus" value="2" onclick="refundFileFinish()">已完结
      </div>
      <div id="sellerNameBtns" style="clear:both ;">
                    选择卖方：
      </div>
      <div id="buyerNameBtns">
                    选择买方：
      </div>
    </div>
    <input type="hidden" id="projectId" name="projectId"/>
    </form>
    </div>
   </div>
    <div id="submitDiv1">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="javascript:$('#collect_file_dialog').dialog('close');$('#grid').datagrid('clearChecked');$('#grid').datagrid('reload');">关闭刷新</a> 
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#collect_file_dialog').dialog('close')">关闭</a> 
   </div> 
   <div id="collect_file_list_dialog" class="easyui-dialog" data-options="modal:true" buttons="#submitCollectFileListDialogDiv" style="width: 533px; height: 500px;" closed="true">
    <div style="margin-left: 30px;margin-top: 10px;line-height: 35px;">
   <form id="collectFileListForm" name="collectFileListForm" action="#" method="post">
    <table border="0" cellpadding="0" class="cus_table" cellspacing="0" style="width: 90%;">
    
    <tr class="buyerSellerType2">
    <td width="120px" ><input type="checkbox" onchange="getBuyerOrSeller(this)" value="SELLER_CARD_NO" id="SELLER_CARD_NO" name="collectFileMapList[0].code">卖方身份证：</td>
    <td><input id="SELLER_CARD_NO_REMARK" style="width: 200px;" data-options="validType:'length[0,100]'" name="collectFileMapList[0].remark" class="easyui-textbox"></td>
	</tr>
	
	<tr class="buyerSellerType1">
	    <td width="120px"><input type="checkbox" onchange="getBuyerOrSeller(this)" value="BUYER_CARD_NO" id="BUYER_CARD_NO" name="collectFileMapList[31].code">买方身份证：</td>
	    <td><input id="BUYER_CARD_NO_REMARK" style="width: 200px;" data-options="validType:'length[0,100]'" name="collectFileMapList[31].remark" class="easyui-textbox"></td>
    </tr>
    
	<tr>
		<td ><input type="checkbox" onchange="getBuyerOrSeller(this)" value="PAYMENT_NAME" id="PAYMENT_NAME" name="collectFileMapList[33].code">回款账号名：</td>
		<td><input id="PAYMENT_NAME_REMARK" style="width: 200px;" data-options="validType:'length[0,100]'" name="collectFileMapList[33].remark" class="easyui-textbox" editable="false"></td>
	</tr>
	
    <tr class="buyerSellerType2">
	    <td ><input type="checkbox" onchange="getBuyerOrSeller(this)" value="REPAYMENT_CARD" id="REPAYMENT_CARD" name="collectFileMapList[1].code">回款卡/折：</td>
	    <td><input id="REPAYMENT_CARD_REMARK" style="width: 200px;" data-options="validType:'length[0,100]'" name="collectFileMapList[1].remark" class="easyui-textbox"></td>
    </tr>
    
	<tr>
		<td ><input type="checkbox" onchange="getBuyerOrSeller(this)" value="ID_CARD_NUMBER" id="ID_CARD_NUMBER" name="collectFileMapList[34].code">回款人身份证号：</td>
		<td><input id="ID_CARD_NUMBER_REMARK" style="width: 200px;" data-options="validType:'length[0,100]'" name="collectFileMapList[34].remark" class="easyui-textbox" editable="false"></td>
	</tr>
	
    <tr class="buyerSellerType2">
    <td><input type="checkbox" onchange="getBuyerOrSeller(this)" value="FORECLOSURE_CARD" id="FORECLOSURE_CARD" name="collectFileMapList[2].code">赎楼卡/折：</td>
    <td><input id="FORECLOSURE_CARD_REMARK" name="collectFileMapList[2].remark" style="width: 200px;" data-options="validType:'length[0,100]'" class="easyui-textbox"></td>
    </tr>
    
    <tr class="buyerSellerType1">
    <td><input type="checkbox" onchange="getBuyerOrSeller(this)" value="SUPERVISE_CARD" id="SUPERVISE_CARD" name="collectFileMapList[32].code">监管卡/折：</td>
    <td><input id="SUPERVISE_CARD_REMARK" name="collectFileMapList[32].remark" style="width: 200px;" data-options="validType:'length[0,100]'" class="easyui-textbox"></td>
    </tr>

    <tr>
    <td><input type="checkbox" value="BANK_CARD" id="BANK_CARD" name="collectFileMapList[3].code">银行卡/折：</td>
    <td> <input id="BANK_CARD_REMARK" name="collectFileMapList[3].remark" style="width: 200px;"  data-options="validType:'length[0,100]'" class="easyui-textbox"></td>
    </tr>
    
    <tr>
    <td><input type="checkbox" value="NET_SILVER" id="NET_SILVER" name="collectFileMapList[4].code">网上银行：</td>
    <td><input id="NET_SILVER_REMARK" name="collectFileMapList[4].remark" style="width: 200px;" data-options="validType:'length[0,100]'" class="easyui-textbox"></td>
    </tr>
    
    <tr>
    <td><input type="checkbox" value="SUPER_NET_SILVER" id="SUPER_NET_SILVER" name="collectFileMapList[5].code">超级网银：</td>
    <td><input id="SUPER_NET_SILVER_REMARK" name="collectFileMapList[5].remark" style="width: 200px;" data-options="validType:'length[0,100]'" class="easyui-textbox"></td>
    </tr>
    
    <tr>
    <td><input type="checkbox" value="BANK_PHONE" id="BANK_PHONE" name="collectFileMapList[6].code">银行预留手机号：</td>
    <td><input id="BANK_PHONE_REMARK" name="collectFileMapList[6].remark" style="width: 200px;" data-options="validType:'length[0,100]'" class="easyui-textbox" data-options="validType:'phone'"></td>
    </tr>
    
    <tr>
    <td><input type="checkbox" value="TRANSFER_LIMIT_MONEY" id="TRANSFER_LIMIT_MONEY" name="collectFileMapList[7].code">转账限额：</td>
    <td> <input id="TRANSFER_LIMIT_MONEY_REMARK" name="collectFileMapList[7].remark" style="width: 200px;" data-options="validType:'length[0,100]'" class="easyui-numberbox"></td>
    </tr>
    
    <tr>
    <td><input type="checkbox" value="COMPANY_NAME" id="COMPANY_NAME" name="collectFileMapList[8].code">公司名称：</td>
    <td><input id="COMPANY_NAME_REMARK" name="collectFileMapList[8].remark" style="width: 200px;" data-options="validType:'length[0,100]'" class="easyui-textbox"></td>
    </tr>
    <tr>
    <td><input type="checkbox" value="E_BANK_RELATE_ACCOUNT" id="E_BANK_RELATE_ACCOUNT" name="collectFileMapList[30].code">网银关联账户：</td>
    <td><input id="E_BANK_RELATE_ACCOUNT_REMARK" name="collectFileMapList[30].remark" style="width: 200px;" data-options="validType:'length[0,100]'" class="easyui-textbox"></td>
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
    <input type="hidden" id="openType"/>
    </form>
    </div>
   </div>
   <div id="submitCollectFileListDialogDiv">
    <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="collectFilePrint()"
     >打印</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subCollectFileListForm()">保存</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#collect_file_list_dialog').dialog('close')"
    >取消</a>
   </div>
   
   <div id="check_document_dialog" class="easyui-dialog" data-options="modal:true" buttons="#submitDiv2" style="width: 450px; height: 558px; padding: 10px;" closed="true">
   <table id="house_list_grid"  style="height: 30%; width: auto;"
        data-options="
      url: '',
      method: 'POST',
      rownumbers: true,
      singleSelect: true,
      pagination: false,
      sortOrder:'asc',
      remoteSort:false,
      idField: 'houseId',
      fitColumns:true"
       >
        <!-- 表头 -->
        <thead>
         <tr>
          <th data-options="field:'projectId',hidden:true" ></th>
          <th data-options="field:'houseId',hidden:true" ></th>
          <th data-options="field:'houseName',sortable:true" align="center" halign="center">物业名称</th>
          <th data-options="field:'costMoney',sortable:true" align="left" halign="center">登记价</th>
          <th data-options="field:'area',sortable:true" align="left" halign="center">面积</th>
          <th data-options="field:'housePropertyCard',sortable:true" align="left" halign="center">房产证号</th>
          <th data-options="field:'tranasctionMoney',sortable:true" align="left" halign="center">成交价</th>
          <th data-options="field:'evaluationPrice',sortable:true" align="left" halign="center">评估价</th>
          <th data-options="field:'purpose',sortable:true" align="left" halign="center">用途</th>
         </tr>
        </thead>
       </table>
   
   <form id="checkDocumentForm" name="checkDocumentForm" action="${basePath}integratedDeptController/updateCheckDocument.action" method="post">
     <input name="houseId" type="hidden">
     <table style="width: 100%; height: 270px;" id="checkDocumentForm_table">
       <tr>
        <td width="75" align="right">业主：</td>
        <td><span id="sellerName"></span></td>
       </tr>
       <tr>
        <td align="right">物业：</td>
        <td><span id="houseName"></span></td>
       </tr>
       <tr>
        <td align="right">房产证号：</td>
        <td><span id="housePropertyCard"></span></td>
       </tr>
       <tr>
        <td align="right">查档时间：</td>
        <td>
        	<input name="checkDate" id="checkDate" class="easyui-datebox" data-options="required:true" editable="false" >
        	  <select class="easyui-combobox" name="checkHours" id="checkHours" style="width: 50px;">
      			  <option value="00">00</option>
	              <option value="01">01</option>
	              <option value="02">02</option>
	              <option value="03">03</option>
	              <option value="04">04</option>
	              <option value="05">05</option>
	              <option value="06">06</option>
	              <option value="07">07</option>
	              <option value="08">08</option>
	              <option value="09">09</option>
	              <option value="10">10</option>
	              <option value="11">11</option>
	              <option value="12">12</option>
	              <option value="13">13</option>
	              <option value="14">14</option>
	              <option value="15">15</option>
	              <option value="16">16</option>
	              <option value="17">17</option>
	              <option value="18">18</option>
	              <option value="19">19</option>
	              <option value="20">20</option>
	              <option value="21">21</option>
	              <option value="22">22</option>
	              <option value="23">23</option>
             </select>点
        </td>
       </tr>
       <tr>
        <td align="right">查档状态：</td>
        <td>
        <input name="checkStatus" id="checkDocumentCheckStatus" style="width: 100px;" data-options="validType:'selrequired',required:true" class="easyui-combobox" editable="false"/>
      </td>
       </tr>
       <tr>
        <td colspan="2"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="subCheckDocumentForm()">保存查档</a> 
             <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-add" onclick="openUploadFileDialog()">上传文件</a>
             <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteCheckDocumentFile('grid_checkDocumentFileList')">删除文件</a>
        </td>
       </tr>
     </table>
    <input type="hidden" id="checkDocumentProjectId" name="projectId"/>
    <input type="hidden" id="checkDocumentId" name="pid"/>
    </form>
    
   
   	<!--查档-自动查档 -->
     <form id="checkDocumentAutoForm" name="checkDocumentAutoForm" action="" method="post" style="display: none;">
       
	    <!-- <a id="check_doc_a_showbtn" onclick="setCheckDocShow('check_doc_table');" style="color:blue;cursor:pointer;">自动查档-展开</a> -->				
		<table style="width:50%;padding: 10px;float: left; " id="check_doc_table" >
			<tr>
				<td width="105" align="right" style="padding-right: 10px;">查档范围：</td>
				<td>深圳市</td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right: 10px;">查询方式：</td>
				<td>
					<input type="radio" name="check_doc_queryType" value="1" checked="checked">分户 
					<input type="radio" name="check_doc_queryType" value="2">分栋  
				</td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right: 10px;">产权证书类型：</td>
				<td>
					<input type="radio" name="check_doc_certType" value="1" checked="checked" onclick="setCheckDocCertType(1,'1');">房地产权证书
					<input type="radio" name="check_doc_certType" value="2" onclick="setCheckDocCertType(1,'2');">不动产权证书
				</td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right: 10px;">产权证书编号：</td>
				<td>
					<div id="certTypeContent" >
						深房地字第
						<input type="text" id="check_doc_certNo" class="text_style easyui-textbox" data-options="required:true,validType:'length[1,20]'" style="width:110px;">号
					</div>
					<div id="certTypeContent2" style="display: none;">
						粤(<input type="text" id="check_doc_year" class="text_style easyui-numberbox"  style="width: 46px" data-options="required:true,validType:'length[1,4]',min:0,max:9999">)
						深圳市不动产权第<input type="text" id="check_doc_certNo2" class="text_style easyui-textbox" data-options="required:true,validType:'length[1,20]'"  style="width:90px;">号
					</div>
				</td>
			</tr>
			
			<tr>
				<td style="text-align:right;padding-right: 10px;">权利人/身份证：</td>
				<td><input type="text" id="check_doc_personInfo" class="text_style easyui-textbox" data-options="required:true,validType:'length[1,50]'" ></td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right: 10px;">&nbsp;&nbsp;查档结果：</td>
				<td>
					<textarea rows="7" name="check_doc_result" id="check_doc_result" style="width: 240px;"></textarea>
					</br><span style="color: red;">注：查档结果为即时查询显示,未自动保存</span>
				</td>
			</tr>
			<tr style="text-align: center;">
				<td colspan="2">
					<a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-add" onclick="searchCheckDoc(1);">立即查询</a>
				</td>
			</tr>
		</table>
	</form>
    
    <!-- 清除浮动 -->
    <div style="clear: both;"></div>
    
    <!-- 文件 -->
    <table id="grid_checkDocumentFileList"  title="文件列表" class="easyui-datagrid" style="height: 270px; width: auto;"
     data-options="
		     method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true" />
       <th data-options="field:'file.fileName',formatter:formatFileName" align="center" halign="center"  >文件名</th>
       <th data-options="field:'file.fileType',formatter:formatFileType" align="center" halign="center"  >类型</th>
       <th data-options="field:'file.uploadDttm',formatter:formatUploadDttm" align="center" halign="center"  >上传时间</th>
       <th data-options="field:'fileUrl',formatter:formatCheckDocumentFileListOperate" align="center" halign="center"  >操作</th>
      </tr>
     </thead>
    </table>
     <!-- 查档历史 -->
    <table id="check_document_his_house_list_grid"  title="查档历史列表" class="easyui-datagrid" style="height: 270px; width: auto;"
     data-options="
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true" />
       <th data-options="field:'houseName'" align="center" halign="center"  >物业名称</th>
       <th data-options="field:'housePropertyCard'" align="center" halign="center"  >房产证号</th>
       <th data-options="field:'checkDate'" align="center" halign="center"  >查档时间</th>
       <th data-options="field:'checkStatusStr'" align="center" halign="center"  >查档状态</th>
       <th data-options="field:'checkWay',formatter:formatterCheckWay" align="center" halign="center"  >查档方式</th>
       <th data-options="field:'createrName'" align="center" halign="center"  >操作人</th>
       <th data-options="field:'createrDate',formatter:convertDateTime" align="center" halign="center"  >操作时间</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="submitDiv2">
      审批结果：<input type="radio" name="approvalStatus" checked="checked" value="3">通过
             <input type="radio" name="approvalStatus" value="2">不通过
      审批意见：<textarea rows="2" id="checkDocumentRemark" name="remark" maxlength="500" style="width: 240px;"></textarea>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subApprovalStatus()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#check_document_dialog').dialog('close')"
    >取消</a>
   </div>
   <div id="check_litigation_dialog" class="easyui-dialog" data-options="modal:true" buttons="#submitDiv3" style="width: 353px; height: 358px; padding: 10px;" closed="true">
   <form id="checkLitigationForm" name="checkLitigationForm" action="${basePath}integratedDeptController/updateCheckLitigation.action" method="post">
     <table style="width: 100%; height: 200px;">
       <tr>
        <td>查法院网时间：</td>
        <td>
        <input name="checkDate" id="checkLitigationDate" class="easyui-datebox" data-options="required:true" editable="false" >
            <select class="easyui-combobox" name="checkHours" id="checkHours" style="width: 50px;">
      			  <option value="00">00</option>
	              <option value="01">01</option>
	              <option value="02">02</option>
	              <option value="03">03</option>
	              <option value="04">04</option>
	              <option value="05">05</option>
	              <option value="06">06</option>
	              <option value="07">07</option>
	              <option value="08">08</option>
	              <option value="09">09</option>
	              <option value="10">10</option>
	              <option value="11">11</option>
	              <option value="12">12</option>
	              <option value="13">13</option>
	              <option value="14">14</option>
	              <option value="15">15</option>
	              <option value="16">16</option>
	              <option value="17">17</option>
	              <option value="18">18</option>
	              <option value="19">19</option>
	              <option value="20">20</option>
	              <option value="21">21</option>
	              <option value="22">22</option>
	              <option value="23">23</option>
             </select>点
        </td>
       </tr>
       <tr>
        <td>诉讼情况：</td>
        <td><select class="select_style easyui-combobox" id="checkLitigationStatus" name="checkStatus" required="true" data-options="validType:'selrequired'" style="width:100px" editable="false">
        <option value="-1">--请选择--</option>
        <option value="2">无新增诉讼</option>
        <option value="3">有诉讼</option>
        <option value="4">有新增诉讼非本人</option>
       </select>
       </td>
       </tr>
       <tr>
        <td>审批意见：</td>
        <td><textarea rows="2" id="checkLitigationRemark" name="remark" maxlength="500" style="width: 220px;"></textarea></td>
       </tr>
       <tr>
        <td>审批结果：</td>
        <td><input type="radio" name="approvalStatus" value="3">通过
             <input type="radio" name="approvalStatus" value="2">不通过</td>
       </tr>
     </table>
    <input type="hidden" id="checkLitigationProjectId" name="projectId"/>
    <input type="hidden" id="checkLitigationId" name="pid"/>
    </form>
   </div>
   <div id="submitDiv3">
    <a id="F" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subCheckLitigationForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#check_litigation_dialog').dialog('close')"
    >取消</a>
   </div>
   <div id="perform_job_remark_dialog" class="easyui-dialog" data-options="modal:true" buttons="#submitDiv4" style="width: 353px; height: 200px; padding: 10px;" closed="true">
   <form id="performJobRemarkForm" name="performJobRemarkForm" action="${basePath}integratedDeptController/updatePerformJobRemark.action" method="post">
     <table style="width: 100%; height: 100px;">
       <tr>
        <td>录入时间:</td>
        <td><input name="remarkDate" id="performJobRemarkDate" class="easyui-datebox" data-options="required:true" editable="false" >
                  &nbsp;<input type="radio" name="dataCompleteStatus" value="1" checked="checked" onclick="autoImportData()">资料齐全<input type="radio" name="dataCompleteStatus" value="2" onclick="autoImportData()">资料未齐全
        </td>
       </tr>
       <tr>
        <td>备注:</td>
        <td ><textarea rows="2" id="performJobRemark" name="remark" maxlength="500" style="width: 250px;"></textarea></td>
       </tr>
     </table>
    <input type="hidden" id="performJobRemarkProjectId" name="projectId"/>
    </form>
   </div>
   <div id="submitDiv4">
    <a id="F" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subPerformJobRemarkForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#perform_job_remark_dialog').dialog('close')"
    >取消</a>
   </div>
   
   
   
   <!-- 查档复核 -->
   <div id="check_document_re_check_dialog" class="easyui-dialog" data-options="modal:true" buttons="#submitDiv5" style="width: 450px; height: 480px; padding: 10px;" closed="true">
   <form id="reCheckCheckDocumentForm" name="reCheckCheckDocumentForm" action="${basePath}integratedDeptController/reCheckCheckDocument.action" method="post">
     <table style="width: 100%; height: 100px; " id="reCheckCheckDocumentForm_table">
       <tr>
        <td>复核状态:</td>
        <td><input type="radio" name="reCheckStatus" value="3" checked="checked">同意
            <input type="radio" name="reCheckStatus" value="4">不同意
        </td>
       </tr>
       <tr>
        <td>备注:</td>
        <td><textarea rows="2" id="reCheckRemark" name="reCheckRemark" maxlength="500" style="width: 250px;"></textarea></td>
       </tr>
     </table>
        <input type="hidden" id="reCheckProjectId" name="projectId"/>
        <input type="hidden" id="reCheckCheckDocumentId" name="pid"/>
    </form>
    
     <!--查档复核-自动查档 -->
     <form id="re_checkDocumentAutoForm" name="re_checkDocumentAutoForm" action="" method="post" style="display: none;">
       
	    <!-- <a id="check_doc_a_showbtn" onclick="setCheckDocShow('check_doc_table');" style="color:blue;cursor:pointer;">自动查档-展开</a> -->				
		<table style="width:50%;padding: 10px;float: left; " id="re_checkDocumentAutoForm_table" >
			<tr>
				<td style="width:130px; text-align:right;padding-right: 10px;">查档范围：</td>
				<td>深圳市</td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right: 10px;">查询方式：</td>
				<td>
					<input type="radio" name="re_check_doc_queryType" value="1" checked="checked">分户 
					<input type="radio" name="re_check_doc_queryType" value="2">分栋  
				</td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right: 10px;">产权证书类型：</td>
				<td>
					<input type="radio" name="re_check_doc_certType" value="1" checked="checked" onclick="setCheckDocCertType(2,'1');">房地产权证书
					<input type="radio" name="re_check_doc_certType" value="2" onclick="setCheckDocCertType(2,'2');">不动产权证书
				</td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right: 10px;">产权证书编号：</td>
				<td>
					<div id="re_certTypeContent" >
						深房地字第
						<input type="text" id="re_check_doc_certNo" class="text_style easyui-textbox" data-options="required:true,validType:'length[1,20]'" style="width:110px;">号
					</div>
					<div id="re_certTypeContent2" style="display: none;">
						粤(<input type="text" id="re_check_doc_year" class="text_style easyui-numberbox" data-options="required:true,validType:'length[1,4]',min:0,max:9999" style="width: 46px">)
						深圳市不动产权第<input type="text" id="re_check_doc_certNo2" class="text_style easyui-textbox" data-options="required:true,validType:'length[1,20]'" style="width:90px;">号
					</div>
				</td>
			</tr>
			
			<tr>
				<td style="text-align:right;padding-right: 10px;">权利人/身份证：</td>
				<td><input TYPE="text" id="re_check_doc_personInfo" class="text_style easyui-textbox" data-options="required:true,validType:'length[1,50]'"></td>
			</tr>
			<tr>
				<td style="text-align:right;padding-right: 10px;">&nbsp;&nbsp;查档结果：</td>
				<td>
					<textarea rows="7" name="re_check_doc_result" id="re_check_doc_result" style="width: 240px;"></textarea>
					</br><span style="color: red;">注：查档结果为即时查询显示,未自动保存</span>
				</td>
			</tr>
			<tr style="text-align: center;">
				<td colspan="2">
					<a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-add" onclick="searchCheckDoc(2);">立即查询</a>
				</td>
			</tr>
		</table>
	</form>
    
    <!-- 清除浮动 -->
    <div style="clear: both;"></div>
    
  
    
    
    
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="openUploadFileDialog()">上传文件</a>
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteCheckDocumentFile('grid_documentReCheckFileList')">删除文件</a>
        <table id="grid_documentReCheckFileList"  title="文件列表" class="easyui-datagrid" style="height: 270px; width: auto;"
     data-options="
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true" />
       <th data-options="field:'projectId',hidden:true" />
       <th data-options="field:'file.fileName',formatter:formatFileName" align="center" halign="center"  >文件名</th>
       <th data-options="field:'file.fileType',formatter:formatFileType" align="center" halign="center"  >类型</th>
       <th data-options="field:'file.uploadDttm',formatter:formatUploadDttm" align="center" halign="center"  >上传时间</th>
       <th data-options="field:'fileUrl',formatter:formatCheckDocumentFileListOperate" align="center" halign="center"  >操作</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="submitDiv5">
    <a id="F" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subReCheckCheckDocument()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#check_document_re_check_dialog').dialog('close')"
    >取消</a>
   </div>
   <div id="log_dialog" class="easyui-dialog" data-options="modal:true" style="width: 800px; height: 300px; padding: 10px;" closed="true">
      <table id="grid_log"  style="height: 100%; width: auto;"
        data-options="
      url: '',
      method: 'POST',
      rownumbers: true,
      singleSelect: false,
      pagination: true,
      sortOrder:'asc',
      remoteSort:false,
      idField: 'pid',
      fitColumns:true"
       >
        <!-- 表头 -->
        <thead>
         <tr>
          <th data-options="field:'realName',sortable:true" align="center" halign="center">操作用户</th>
          <th data-options="field:'logDateTime',sortable:true" align="center" halign="center">日志时间</th>
          <th data-options="field:'logMsg',sortable:true" align="left" halign="center">日志内容</th>
         </tr>
        </thead>
       </table>
   </div>
     <div id="fileUploadDialog" class="easyui-dialog" data-options="modal:true" buttons="#uploanFileOperateDiv" style="width: 430px; height: 330px; padding: 10px" closed="true">
        <form id="fileUploadForm" name="fileUploadForm" method="post">
         <input type="hidden" id="isUpdateFile" name="isUpdateFile" value="0" />
         <div class="uploadmutilFile" style="margin-top: 10px;">
          <input type="file" name="uploadify" id="uploadify" />
         </div>
         <%--用来作为文件队列区域--%>
         <div id="fileQueue" style="max-height: 200; overflow: scroll; right: 50px; bottom: 100px; z-index: 999"></div>
        </form>
       </div>
       <div id="uploanFileOperateDiv">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="startUploadFileBtn">开始上传</a> <a href="javascript:void(0)"
         class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#uploadify').uploadify('cancel','*')"
        >取消上传</a>
       </div>
  </div>
 </div>
</body>
</html>
