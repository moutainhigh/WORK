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

<style type="text/css">
.cus_table{margin-bottom: 10px;}
.cus_table td{ padding: 5px;height:24px;}
.hidden_css{display: none;}
.mask_index{z-index: 9999};
</style>

<script type="text/javascript">
//项目名称format
 function formatProjectName(value, row, index){
	var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
	return va;
}
//距离回款天数
 function formatterRepaymentDateDiff(val,row){
 	if(parseInt(val) <=3 &&row.repaymentStatus==1){
 		return "<span style='color:red;'>"+val+"</span>";
 	}else{
 		return "<span style='color:blue;'>"+val+"</span>";
 	}
 }
 
//风险等级:正常风险=1,重大风险=2,紧急风险=3
 function formatDangerLevel(val,row){
 	if(val == 2){
 		return "<span style='color:red;'>重大风险</span>";
 	}else if(val == 3){
 		return "<span style='color:red;'>紧急风险</span>";
 	}else if(val == 1){
 		return "正常风险";
 	}else{
 		return "待跟进";
 	}
 }
//异常监控状态：正常=1，异常=2，异常转正常=3
 function formatterMonitorStatus(val,row){
 	if(val == 3){
 		return "<span style='color:red;'>异常转正常</span>";
 	}else if(val == 2){
 		return "<span style='color:red;'>异常</span>";
 	}else{
 		return "正常";
 	}
 }
//新增or修改申请报告：修改=2,新增=1
 function applyReport(){
	 var rows = $('#grid').datagrid("getSelections");
	 if (rows.length==0) {
		 $.messager.alert("操作提示", "请选择数据", "error");
		 return;
	   }
	 if(rows.length > 1){
		 $.messager.alert("操作提示", "只能选择一条数据", "error");
		 return;
	 }
	var applyStatus = rows[0].applyStatus;
	var projectId = rows[0].projectId;
 	if(applyStatus == 2){
 		url = BASE_PATH+'foreAfterLoanController/addOrUpdateReport.action?projectId='+projectId+'&applyStatus='+applyStatus;
		parent.openNewTab(url,"修改申请报告",true);
 	}else{
 		url = BASE_PATH+'foreAfterLoanController/addOrUpdateReport.action?projectId='+projectId+'&applyStatus='+applyStatus;
		parent.openNewTab(url,"新增申请报告",true);
 	}
 }
 //增加异常监控列表
 function addExceptionTransit(){
	 var rows = $('#grid').datagrid("getSelections");
	 var projectId = "";
	 if (rows.length==0) {
		 $.messager.alert("操作提示", "请选择数据", "error");
		 return;
	   }
	 if(rows.length > 1){
		 $.messager.alert("操作提示", "只能选择一条数据", "error");
		 return;
	 }
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				projectId += rows[i].projectId;
			} else {
				projectId += "," + rows[i].projectId;
			}
		}
	 	url = BASE_PATH+'foreAfterLoanController/addExceptionTransit.action?projectId='+projectId;
		parent.openNewTab(url,"增加异常监控列表",true);
 }
 //导出
 function cxportExceptionMonitor() {
		$.ajax({url : BASE_PATH
			+ 'templateFileController/checkFileUrl.action',
			data:{templateName : 'YCYWJK'},
			dataType:'json',
			success : function(data) {
				if (data == 1) {
					var projectName =$("#projectName").textbox('getValue');
					var businessSourceStr =$("#businessSourceStr").textbox('getValue');
					var monitorStatus =$("#monitorStatus").combobox('getValue');
					var planRepaymentDate =$("#planRepaymentDate").textbox('getValue');
					var planRepaymentEndDate =$("#planRepaymentEndDate").textbox('getValue');
					window.location.href ="${basePath}foreAfterLoanController/exportTransitExceptionIndexList.action?businessSourceStr="+businessSourceStr+"&monitorStatus="+monitorStatus+"&planRepaymentDate="+planRepaymentDate+"&planRepaymentEndDate="+planRepaymentEndDate+"&projectName="+projectName+"&page=-1";
				} else {
					alert('异常业务监控导出模板不存在....，请上传模板后重试');
				}
			}
		}) 
	}
 
 //打开查诉讼确认窗口
	function openCheckLitigationDialog() {
		$("#checkLitigationRemark").val('');
		$("#checkLitigationForm input[name='approvalStatus']").removeAttr("checked");
		$("#checkLitigationStatus").combobox('select','-1');
		$('#checkLitigationForm input[type=radio][name=approvalStatus][value=3]').prop('checked',true);
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			    var projectId=row[0].projectId;
			    var projectName=row[0].projectName;
				$("#checkLitigationProjectId").val(projectId);
				$("#checkLitigationDate").datebox('setValue',formatterDate(new Date()));
			    $('#check_litigation_dialog').dialog('open').dialog('setTitle', "查诉讼--"+projectName);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//提交查诉讼表单
	function subCheckLitigationForm() {
		var approvalStatus=$("#checkLitigationForm input[name='approvalStatus']:checked").val();
		if (approvalStatus==null||approvalStatus=='') {
			$.messager.alert('操作提示', '请选择审批结果', 'error');
			return;
		}
		var checkHours = $("#checkLitigationHours").combobox("getValue");
		checkHours+=":00:00";
		
		var checkDate = $("#checkLitigationDate").datebox("getValue");
		if(checkDate != ""){
			checkDate = checkDate+" "+checkHours;
		}
		
		$("#checkDate2").textbox("setValue",checkDate);
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
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	
	
	
	// 查档 start--------------------------------------
	function formatterCheckStatus(val, row) {
	    if(val == 1){    
	    	return "未查档";
	    }else if (val == 2) {
			return "抵押";
		} else if (val == 3) {
			return "有效";
		} else if (val == 4) {
			return "无效";
		} else if (val == 5) {
			return "查封";
		} else if (val == 6) {
			return "抵押查封";
		} else if (val == 7) {
			return "轮候查封";
		} else {
			return "未知";
		}
	}
	//加载物业列表
	function loadHouseListGrid() {
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length==0) {
			 $.messager.alert("操作提示", "请选择数据", "error");
			 return;
		   }
		 if(rows.length > 1){
			 $.messager.alert("操作提示", "只能选择一条数据", "error");
			 return;
		 }
		var projectId=rows[0].projectId;
		$("#house_list_grid").datagrid("clearChecked");
		$('#house_list_grid').datagrid({  
			url:'<%=basePath%>beforeLoanController/getHouseByProjectId.action',  
			queryParams:{  
			    	projectId : projectId
			}
	    }); 
	}
	
	//打开查档窗口
	function openCheckDocumentDialog(isLoadApprovalInfo) {//isLoadApprovalInfo:是否加载查档审批信息
		var row = $('#grid').datagrid('getSelections');
		var houseListRow = $('#house_list_grid').datagrid('getSelections');//获取选中的物业
		if (houseListRow.length == 1) {
			var projectId = row[0].projectId;
			setCombobox3("checkDocumentCheckStatus","CHECK_DOCUMENT_STATUS",-1);//初始化查档状态下拉框
			//填充买卖信息
			$("#sellerName").html(row[0].sellerName);//业主
			$("#houseName").html(houseListRow[0].houseName);//物业名称
			$("#housePropertyCard").html(houseListRow[0].housePropertyCard);//房产号
			//填充查档信息
			$("#checkDate").datebox('setValue', formatterDate(new Date()));
			$("#checkDocumentProjectId").val(row[0].projectId);
			$("#startUploadFileBtn").attr("onclick",
					"startUploadFile(1," + row[0].projectId + ")")//初始化上传文件按钮事件
			loadCheckDocumentFileList(1, "grid_checkDocumentFileList");//加载已经上传文件列表
			loadCheckDocumentHisListGrid(projectId)//加载查档历史记录列表
			//重置自动查档表单
			resetCheckDocumentAutoForm(1, row[0].sellerName,
					houseListRow[0].housePropertyCard); 
			$('#check_document_dialog').dialog('open').dialog('setTitle',
					"要件查档--" + row[0].projectName);
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
		
		var checkHours = $("#checkHours").combobox("getValue");
		checkHours+=":00:00";
		var checkDate = $("#checkDate").datebox("getValue");
		if(checkDate != ""){
			checkDate = checkDate+" "+checkHours;
		}
		
		$("#checkDate1").textbox("setValue",checkDate);
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
	//加载查档历史记录列表
	function loadCheckDocumentHisListGrid(projectId) {
		$('#check_document_his_house_list_grid').datagrid({  
			url:'<%=basePath%>integratedDeptController/checkDocumentHisList.action',  
			queryParams:{  
			    	projectId : projectId
			}
	    }); 
	}
	// 查档 end--------------------------------------	
	
	//查档文件 start
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
	//查档文件 end
	
	//重置按钮
	function majaxReset() {
		$('#searchFrom').form('reset');
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
		
        if($("#"+checkDocumentAutoForm).is(":hidden")){
			//全部显示
     		$("#"+checkDocumentForm_table).css("width","40%");
     		$("#"+checkDocumentForm_table).css("float","left");
            $("#"+check_document_dialog).dialog({width: 750}); 
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
	}
	
	 /** 使用方法: 
	 * 开启:MaskUtil.mask(); 
	 * 关闭:MaskUtil.unmask(); 
	 *  
	 * MaskUtil.mask('其它提示文字...'); 
	 */
	var MaskUtil = (function(){  
	    var $mask,$maskMsg;  
	    var defMsg = '正在处理，请稍待。。。';  
	    function init(){  
	        if(!$mask){  
	            $mask = $("<div class=\"datagrid-mask mask_index\"></div>").appendTo("body");  
	        }  
	        if(!$maskMsg){  
	            $maskMsg = $("<div class=\"datagrid-mask-msg mask_index\">"+defMsg+"</div>")  
	                .appendTo("body").css({'font-size':'12px'});  
	        }  
	          
	        $mask.css({width:"100%",height:$(document).height()});  
	          
	        var scrollTop = $(document.body).scrollTop();  
	          
	        $maskMsg.css({  
	            left:( $(document.body).outerWidth(true) - 190 ) / 2  
	            ,top:( ($(window).height() - 45) / 2 ) + scrollTop 
	        });   
	    }  
	    return {  
	        mask:function(msg){  
	            init();  
	            $mask.show();  
	            $maskMsg.html(msg||defMsg).show();  
	        }  
	        ,unmask:function(){  
	            $mask.hide();  
	            $maskMsg.hide();  
	        }  
	    }  
	}()); 
	
	
	//----------------------------自动查档结束
	$(document).ready(function() {
		initUploadify();
		$('#house_list_grid').datagrid({
			//当物业列表数据加载完毕
			onLoadSuccess : function(data) {
				//系统自动选中第一条并打开查档窗口
				$(this).datagrid('selectRow', 0);
				openCheckDocumentDialog(true);
			},
			onClickRow : function(rowIndex, rowData) {
				openCheckDocumentDialog(false);
			}
		});
	});
	
	 //查看在途业务监控信息
	 function toTransitMonitorShow() {
			var url = "";// 路径
			var row = $('#grid').datagrid('getSelections');
			if (row.length == 1) {
				url = "${basePath}foreAfterLoanController/toExceptionMonitorShow.action?projectId="+row[0].projectId;
				parent.openNewTab(url, "查看异常业务监控信息", true);
			} else if (row.length > 1) {
				$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
			} else {
				$.messager.alert("操作提示", "请选择数据", "error");
			}
		}
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<!--图标按钮 -->
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="<%=basePath%>foreAfterLoanController/toTransitExceptionIndexList.action" method="post" id="searchFrom" name="searchFrom" >
			<!-- 查询条件 -->
			<div style="padding:5px">
				<table>
					<tr>
						<td width="90" align="right"  height="28">项目名称：</td>
						<td>
							<input class="easyui-textbox" name="projectName" id="projectName"/>						
						</td>
						<td width="80" align="right" height="28">业务来源：</td>
						<td><input class="easyui-textbox" name="businessSourceStr" id="businessSourceStr" /></td>
						<td width="80" align="right" height="28">监控状态：</td>
						<td>
							<select class="select_style easyui-combobox" width="129px;" editable="false" name="monitorStatus" id = "monitorStatus" style="width: 95px">
					          <option value=-1 selected="selected">--请选择--</option>
					          <option value=1>正常</option>
					          <option value=2>异常</option>
					     	  <option value=3>异常转正常</option>
					        </select>
						</td>
					</tr>
					<tr>
						<td width="90" align="right"  height="28">预计回款日期：</td>
						<td>
							<input class="easyui-datebox" editable="false" name="planRepaymentDate" id="planRepaymentDate" /> 至 
							<input class="easyui-datebox" editable="false" name="planRepaymentEndDate" id="planRepaymentEndDate"/>				
						 </td>
					</tr>
					<tr>
						<td colspan="3">
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
							<a href="#" onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;">重置</a>
						</td>
					</tr>				
				</table>
			</div>
		</form>
		
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="addExceptionTransit()">异常监控</a> -->
			<shiro:hasAnyRoles name="CHECK_LITIGATION,ALL_BUSINESS">
		      	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openCheckLitigationDialog()">查诉讼</a>
		     </shiro:hasAnyRoles>
		     <shiro:hasAnyRoles name="CHECK_DOCUMENT,ALL_BUSINESS">
		      	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="loadHouseListGrid()">查档</a>
		     </shiro:hasAnyRoles>
		     <shiro:hasAnyRoles name="TRANSIT_EXCEPTION,ALL_BUSINESS">
		      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-setting" plain="true" onclick="toTransitMonitorShow()">监控处理</a>
		     </shiro:hasAnyRoles>
		     <shiro:hasAnyRoles name="APPLY_REPORT,ALL_BUSINESS">
		      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="applyReport()">申请报告</a>
		     </shiro:hasAnyRoles>
		</div>
		
	<div style="padding-bottom: 5px">
	   <shiro:hasAnyRoles name="EXPORT_TRANSIT_EXCEPTION,ALL_BUSINESS">
	     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportExceptionMonitor()">导出</a>
	   </shiro:hasAnyRoles>
    </div>
		
	</div>
	<div class="listDiv" style="height:100%;">
	<table id="grid" title="异常业务监控列表"  class="easyui-datagrid"
		style="height:100%;width: auto;"
		data-options="
		    url: '<%=basePath%>foreAfterLoanController/toTransitExceptionIndexList.action?type=1',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'projectId',
		    fitColumns:true">
		<!-- 表头 -->
		<thead data-options="frozen:true">
			<tr>
				<th data-options="field:'projectId',checkbox:true"></th>
				<th data-options="field:'projectName',formatter:formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
			</tr>
		</thead>
		<thead><tr>
		    <!-- <th data-options="field:'projectId',checkbox:true" ></th>
		    <th data-options="field:'projectName',formatter:formatProjectName,sortable:true" align="center" halign="center"  >项目名称</th> -->
		    <th data-options="field:'businessSourceStr',sortable:true" align="center" halign="center"  >业务来源</th>
            <th data-options="field:'buyerName',sortable:true" align="center" halign="center"  >买方</th>
            <th data-options="field:'sellerName',sortable:true" align="center" halign="center"  >卖方</th>
		    <th data-options="field:'houseName',sortable:true" align="center" halign="center"  >物业名称</th>
		    <th data-options="field:'housePropertyCard',sortable:true" align="center" halign="center"  >房产证号</th>
		    <th data-options="field:'makeLoansRes',sortable:true" align="center" halign="center"  >资金来源</th>
            <th data-options="field:'productName',sortable:true" align="center" halign="center">产品名称</th>
            <th data-options="field:'loanMoney',sortable:true,formatter:formatMoney" align="center" halign="center">借款金额</th>
            <th data-options="field:'loanDays',sortable:true" align="center" halign="center">借款天数</th>
            <th data-options="field:'dangerLevel',sortable:true ,formatter:formatDangerLevel" align="center" halign="center">风险等级</th>
            <th data-options="field:'receDate',sortable:true,formatter:convertDate" align="center" halign="center"  >放款日期</th>
		    <th data-options="field:'planRepaymentDate',sortable:true,formatter:convertDate" align="center" halign="center"  >预计回款日期</th>
		    <th data-options="field:'repaymentStatus',sortable:true,formatter:formatRepaymentStatus" align="center" halign="center"  >回款状态</th>
		    <th data-options="field:'repaymentDateDiff',sortable:true,formatter:formatterRepaymentDateDiff" align="center" halign="center"  >距离回款天数</th>
		    <th data-options="field:'repaymentDate',sortable:true,formatter:convertDate" align="center" halign="center"  >回款日期</th>
		    <th data-options="field:'monitorStatus',sortable:true,formatter:formatterMonitorStatus" align="center" halign="center">监控状态</th>
		   
            </tr></thead>
	</table>
	</div>
	<div id="check_litigation_dialog" class="easyui-dialog" data-options="modal:true" buttons="#submitDiv3" style="width: 353px; height: 358px; padding: 10px;" closed="true">
	   <form id="checkLitigationForm" name="checkLitigationForm" action="${basePath}integratedDeptController/saveCheckLitigationHis.action" method="post">
	     <table style="width: 100%; height: 200px;">
	       <tr>
	        <td>查法院网时间：</td>
	        <td>
	            <input id="checkLitigationDate" class="easyui-datebox" data-options="required:true" editable="false" >
	            <input name="checkDate" type="hidden" id="checkDate2" class="easyui-textbox" editable="false" >
	        	<select class="easyui-combobox" name="checkHours" id="checkLitigationHours" style="width: 50px;">
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
   <div id="check_document_dialog" class="easyui-dialog" data-options="modal:true" buttons="#submitDiv2" style="width: 450px; height: 558px; padding: 10px;" closed="true">
   <table id="house_list_grid"  style="height: 30%; width: auto;"
        data-options="
      url: '',
      method: 'POST',
      rownumbers: true,
      singleSelect: false,
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
   
   <form id="checkDocumentForm" name="checkDocumentForm" action="${basePath}integratedDeptController/saveCheckDocumentHis.action" method="post">
     <input name="houseId" type="hidden">
     <table style="width: 100%; height: 270px;" id="checkDocumentForm_table">
       <tr>
        <td style="text-align: right"> &nbsp;&nbsp;业主：</td>
        <td><span id="sellerName"></span></td>
       </tr>
       <tr>
        <td style="text-align: right">&nbsp;&nbsp;物业：</td>
        <td><span id="houseName"></span></td>
       </tr>
       <tr>
        <td align="right">房产证号：</td>
        <td><span id="housePropertyCard"></span></td>
       </tr>
       <tr>
        <td align="right">查档时间：</td>
        <td>
        <input id="checkDate" class="easyui-datebox" data-options="required:true" editable="false" >
		<input name="checkDate" type="hidden" id="checkDate1" class="easyui-textbox" editable="false" >
        
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
		<table style="width:60%;padding: 10px;float: left; " id="check_doc_table" >
			<tr>
				<td style="width:130px; text-align:right;padding-right: 10px;">查档范围：</td>
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
    <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#check_document_dialog').dialog('close')"
    >取消</a>
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
