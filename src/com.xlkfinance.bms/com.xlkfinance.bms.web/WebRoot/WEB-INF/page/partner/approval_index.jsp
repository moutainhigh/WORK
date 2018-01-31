<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style>
.info_top{ width:100%; padding:5px 0;height:auto; overflow:hidden;  }
.info_top_auto{ color:#333; font-weight: normal;text-shadow: 0px; position: relative; padding:8px 0; height:auto; overflow:hidden; text-align:center;}

.order_tab{ width:100%; text-align:left;border-bottom:1px #ddd solid; }
.order_tab a{ display:inline-block;width:12%; color:#666;text-align:center; padding:5px 0; font-size:16px; height:28px; margin-left:10px; line-height:28px; border:1px #ddd solid;border-bottom:none; text-decoration:none;}
.order_tab .first{ border-radius:5px 5px 0 0; position:relative;}
.order_tab .last{ border-radius:5px 5px 0 0;margin-left:5px;}
.order_tab .current{ background-color:#0079c3; color:#fff;border:1px #0079c3 solid;border-bottom:none;}
.nearby { display:none; height:auto; overflow:scroll; margin-top:10px; padding:10px;}

/**多图上传按钮*/
.uploadify-button {
    /* background-color: #505050; */
    background-image: linear-gradient(bottom, #505050 0%, #707070 100%);
    background-image: -o-linear-gradient(bottom, #505050 0%, #707070 100%);
    background-image: -moz-linear-gradient(bottom, #505050 0%, #707070 100%);
    /* background-image: -webkit-linear-gradient(bottom, #505050 0%, #707070 100%); */
    background-image: -ms-linear-gradient(bottom, #505050 0%, #707070 100%);
    background-image: -webkit-gradient(
	 linear,
	 left bottom,
	 left top,
	 color-stop(0, #505050),
	 color-stop(1, #707070)
	 );
    background-position: center top;
    background-repeat: no-repeat;
    -webkit-border-radius: 30px;
    -moz-border-radius: 30px;
    border-radius: 0px;
    border: 2px solid #808080;
    color: #FFF;
    font: bold 12px Arial, Helvetica, sans-serif;
    text-align: center;
    text-shadow: 0 -1px 0 rgba(0,0,0,0.25);
    width: 100%;
}

</style>

<script type="text/javascript" src="${ctx}/p/xlkfinance/module/partner/partner.js?t=20171227"></script> 

<script type="text/javascript">
	
	//符件列表表格id
	var file_table_id = "project_file_table_1";

	$(function(){	   
	   $(".order_tab > a").click(function(){
		  $(this).addClass("current").siblings().removeClass("current");  
		   var $i=$(this).index();
		   $(".nearby").eq($i).show().siblings(".nearby").hide();   
		   
		   var approvalStatus = $("#approvalStatus").val();
		   if($(this).attr("id") == 'getProjectFile_btn'){
			   $("#uploadFileBtn").show();
			   file_table_id = "project_file_table_1";
		   }else if($(this).attr("id") =='requestAddFilesBtn'){
			   $("#uploadFileBtn").show();
			   file_table_id = "project_file_table_2";
		   }else{
			   $("#uploadFileBtn").hide();
			   file_table_id="";
		   }
		   if(!(approvalStatus == "" || approvalStatus == 0  || approvalStatus == 3 || approvalStatus == 4)){
			   $("#uploadFileBtn").hide();
			   file_table_id="";
		   }
		   
		   
		   //调整高度
		   var p_height = $("#nearby_project").height();
		   if(p_height > 0){
			   $("#nearby_project_file_1").height(p_height);
			   $("#nearby_project_file_2").height(p_height);
			   $('#nearby_public_man_table').height(p_height);
			   $('#nearby_workflow_table').height(p_height);
			   $('#nearby_foreclosure_table').height(p_height);
			   $('#nearby_house_table').height(p_height);
		   }
		   
	   });
	   $('#project_file_table_1').datagrid({onLoadSuccess : function(data){
			var rows = $('#project_file_table_1').datagrid('getRows');//获取当前页的数据行
			var requestFiles = $("#requestFiles").val();
			if(requestFiles != ''){
				var arr = requestFiles.split(",");
				for(var i = 0; i < arr.length; i++){
					for (var j = 0; j < rows.length; j++) {
						if(arr[i] == rows[j].pid){
							$('#project_file_table_1').datagrid('checkRow', j);
						}
					}
				}
			}
		}}); 
	   $('#project_file_table_2').datagrid({onLoadSuccess : function(data){
		}}); 
 	   $('#workflow_table').datagrid({onLoadSuccess : function(){
		}});  
	   $('#public_man_table').datagrid({onLoadSuccess : function(){
		}});  
 	   $('#house_info_table').datagrid({onLoadSuccess : function(){
		}});  
 	   
 	   $('#originalLoan_table').datagrid({onLoadSuccess : function(){
			var oldLoanMoney = 0.0;//原贷款借款金额
			var oldOwedAmount = 0.0;//原贷款欠款金额
			var rows = $('#originalLoan_table').datagrid('getRows');
			// 银行信息
			if(rows.length > 0){
				for(var i=0;i<rows.length;i++){
			  		var row = rows[i];
			  		oldLoanMoney += row.oldLoanMoney;
			  		oldOwedAmount += row.oldOwedAmount;
			  	}
			}
			$("#foreclosure_table_oldLoanMoney").numberbox('setValue',oldLoanMoney);//原贷款借款总金额
			$("#foreclosure_table_oldOwedAmount").numberbox('setValue',oldOwedAmount);// 原贷款欠款总金额
		}});   	   
 	   
	 	$("#applyLoanDate").datebox();
	 	
 
		$('#accessoryType').combobox({         
			data:partnerGlobal.accessoryTypeJson, //此为重点
	        valueField:'pid',
	        textField:'lookupDesc',
	        //如果需要设置多项隐含值，可使用以下代码
	        onLoadSuccess:function(){
	         },
			onSelect:function(){  
			 	var accessoryTypeTemp = $("#accessoryType").combobox('getValue');
	       		changeFileChileType(accessoryTypeTemp);
	       }
	    });
		
		//多文件上传  //兼容火狐 需要  sessionid，否则拦截登陆302
		var uploadFileCount = 0 ;
	    $("#uploadify").uploadify({  
	    	'swf'       : '${basePath}/upload/uploadify.swf',   
	        'uploader'  : '${basePath}/projectPartnerController/uploadMultipartFile.action;jsessionid=${pageContext.session.id}',    
	        //'folder'         : '/upload',  
	        'queueID'        : 'fileQueue',
	        'cancelImg'      : '${ctx}/p/xlkfinance/plug-in/uploadify/css/img/uploadify-cancel.png',
	        'buttonText'     : '上传文件',
	        'auto'           : false, //设置true 自动上传 设置false还需要手动点击按钮 
	        'multi'          : true,  
	        'wmode'          : 'transparent',  
	        'simUploadLimit' : 999,  
	        'fileTypeExts'        : '*.*',  
	        'fileTypeDesc'       : 'All Files',
	        'method'       : 'get',
			onUploadSuccess : function(file, data, response) {
				 console.log("onUploadSuccess:"+data);
				 uploadFileCount++;
			},
			onQueueComplete : function(file) { //当队列中的所有文件全部完成上传时触发 
				console.info(file);
				$.messager.alert("操作提示", "上传完成"+uploadFileCount+"个文件");
				uploadFileCount = 0;
				
				// 重新加载
				$("#"+file_table_id).datagrid('load');
				//$('#dialog_data').dialog('close');
			}
		});
	    
	    
   		$('#partner_no,#partner_source_no').combobox({         
   			data:partnerGlobal.partnerJson, //此为重点
   	        valueField:'pid',
   	        textField:'lookupDesc'
   	    });

	 	
	})
	
 
	/**
	 * 窗口提交 
	 * projectId 项目id
	 * type  1:查看   2：修改   3: 提交申请      4： 确认要款   5： 复议确认要款  
	 * 审核状态
	 */
	function addItem(projectId,type,approvalStatus,rowIndex) {
		 
		var rowData = null;
		if(rowIndex == null){
			rowData = $('#grid').datagrid('getSelections')[0];
		}else{
			rowData = $('#grid').datagrid('getData').rows[rowIndex];
		}
		
		if(type == 2 && rowData.partnerNo == 'nyyh'){
			$.messager.alert("操作提示","南粤银行不支持修改","error");
			return ;
		}
		
		
		var pid = rowData.pid;
		var partnerNo = rowData.partnerNo;
		
		
		
   		$('#project_file_table_1').datagrid('loadData', { total: 0, rows: [] });  
		$('#project_file_table_2').datagrid('loadData', { total: 0, rows: [] });   
 		getProjectFile(projectId,1);
		getProjectFile(projectId,2);   
		
		$("#reApplyReasonFont").hide();
		$("#partner_btn_submit").hide();
		$("#partner_btn_save").hide();
		$("#confirmLoanReason_tr").hide();
		
		$("#requestAddFilesBtn").hide();
		
		
		$("#approvalStatus").val(approvalStatus);
		$("#projectId").val(projectId);
		
		var title ="合作项目信息";

		var approvalStatus = rowData.approvalStatus;
		var isClosed = rowData.isClosed;	//是否关闭  1未闭闭 2关闭
		
		console.log("projectId:"+projectId+",type:"+type+",approvalStatus:"+approvalStatus+",rowIndex:"+rowIndex);
		
		if(type == 2){
			if(isClosed != 2){
				if(approvalStatus == 3  || approvalStatus == 4){
					$("#partner_btn_save").show();
					$("#partner_btn_submit").show();
					if(approvalStatus == 4){
						$("#requestAddFilesBtn").show();
					}else if(approvalStatus == 3){
						$("#reApplyReasonFont").show();
					}
				}
				$("#partner_btn_submit").attr("onClick","applyPartnerInfo()");
			}
		}else if(type == 3){
			title = "合作项目提交审核";
			if(isClosed != 2){
				$("#partner_btn_submit").show();
				$("#partner_btn_submit").attr("onClick","applyPartnerInfo()");
				
				//补充材料
				if(approvalStatus == 4){
					title = "合作项目补充材料";
					$("#requestAddFilesBtn").show();
				}
			}
		}else if(type == 4 || type == 5){
			if(isClosed != 2){
				$("#partner_btn_submit").show();
				$("#confirmLoanReason_tr").show();
				if(type == 4){
					title = "合作项目确认要款";
					$("#partner_btn_submit").attr("onClick","confirmLoan()");
				}else if(type == 5){
					title = "确作项目复议确认要款";
					$("#partner_btn_submit").attr("onClick","reConfirmLoan()");
				}
			}
		}
		
		$("#info").click();
		setCombobox("cert_type","CERT_TYPE","");
		
		
   		$('#partner_source_no').combobox({         
   			data:partnerGlobal.partnerJson, //此为重点
   	        valueField:'pid',
   	        textField:'lookupDesc',
   	        value:partnerNo,
   	        //如果需要设置多项隐含值，可使用以下代码
   	        onLoadSuccess:function(){
   	        	changePartnerNoShow(partnerNo);
   	        }
   	    });
		
   		//加载机构项目信息
   		loadProjectPartnerInfo(pid,projectId,partnerNo,title);
	}
	
	
	
	//加载机构项目信息
	function loadProjectPartnerInfo(pid,projectId,partnerNo,title){
		
		$('#partnerInfo').form('reset');
 		$('#foreclosure_form').form('reset');
 		$('#house_form').form('reset');
 		
		$.ajax({
			type: "POST",
	        url: "<%=basePath%>projectPartnerController/editProjectPartner.action",
	        data:{"projectId":projectId,"pid":pid,"partnerNo":partnerNo},
	        dataType: "json",
	        success: function(row){
					$('#partnerInfo').form('reset');
					$('#add_partner').dialog('open').dialog('setTitle', title);
					$('#partnerInfo').form('load', row);
					
					//setCombobox3("partner_source_no","PARTNER_NAME",partnerNo);
					$("#partner_source_no").combobox("disable");
					
					setCombobox("sex","SEX",row.sex);
					setBankCombobox2("payment_bank",0,row.paymentBank == null ? "--请选择--" : row.paymentBank);
					
					setAreaCombobox("province_code","1","",row.provinceCode == null ? "" : row.provinceCode);
					setAreaCombobox("city_code","2",row.provinceCode == null ? "" : row.provinceCode,row.cityCode == null ? "" : row.cityCode);
				
					$("#province_code").combobox({ 
						onSelect:function(){
				        	var provinceCode = $("#province_code").combobox("getValue");
				        	if(provinceCode != ""){
				        		setAreaCombobox("city_code","2",provinceCode,"");
				    		}
				        }
					}); 
					
					
					//打款银行
					setBankCombobox2("pay_bank_name",0,row.payBankName == null ? "--请选择--" : row.payBankName);
					setAreaCombobox("pay_province_code","1","",row.payProvinceCode == null ? "" : row.payProvinceCode);
					setAreaCombobox("pay_city_code","2",row.payProvinceCode == null ? "" : row.payProvinceCode,row.payCityCode == null ? "" : row.payCityCode);
					$("#pay_province_code").combobox({ 
						onSelect:function(){
				        	var payProvinceCode = $("#pay_province_code").combobox("getValue");
				        	if(payProvinceCode != ""){
				        		setAreaCombobox("pay_city_code","2",payProvinceCode,"");
				    		}
				        }
					}); 
					
					//加载共同借款人
					if(row.publicManList != null){
						$('#public_man_table').datagrid('loadData',{"total":row.publicManList.length,"rows":row.publicManList}); 
					}
					//加载审批记录
					if(row.taskHistoryList != null){
						$('#workflow_table').datagrid('loadData',{"total":row.taskHistoryList.length,"rows":row.taskHistoryList}); 
					}
					
					//加载赎楼信息
					if(row.projectForeclosure != null){
						$('#foreclosure_form').form('load', row.projectForeclosure);
					}
					//加载银行列表
					loadProjectOriginalLoan(row.projectId);
					
					//加载物业信息
					$('#house_form').form('load', row.projectProperty);
					if(row.estateList != null){
						$('#house_info_table').datagrid('loadData',{"total":row.estateList.length,"rows":row.estateList}); 
					}
					
					
					//加载物业省市
					setAreaCombobox("house_province_code_house_form","1","",row.houseProvinceCode == null ? "440000" : row.houseProvinceCode);
					setAreaCombobox("house_city_code_house_form","2",row.houseProvinceCode == null ? "440000" : row.houseProvinceCode,row.houseCityCode == null ? "" : row.houseCityCode);
					$("#house_province_code_house_form").combobox({ 
						onSelect:function(){
				        	var provinceCode = $("#house_province_code_house_form").combobox("getValue");
				        	if(provinceCode != ""){
				        		setAreaCombobox("house_city_code_house_form","2",provinceCode,"");
				    		}
				        }
					}); 
				}
		 });
		
	}
	
	//打开申请审核页面
	function editItem(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			addItem(row[0].projectId,2,row[0].approvalStatus);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	
	//改变合作机构显示不同字段
	function changePartnerNoShow(partnerNo){
		
		if(partnerNo == null){
			partnerNo=$('#partner_source_no').combobox('getValue');
		}
		console.log(partnerNo);
		if(partnerNo == "tlxt"){
			$(".partnerNoShowTr").show();
			$(".payBankShowTr,.partnerNoHnbxShowTr").hide();
			$(".confirmLoanReason_tr").hide();
			$("#house_province_code_font,#house_city_code_font,.partnerNoNyyhShowTr").hide();
		}else if(partnerNo == "xiaoying"){
			$(".partnerNoShowTr").hide();
			$(".payBankShowTr,.partnerNoHnbxShowTr").hide();
			$(".confirmLoanReason_tr").show();
			$("#house_province_code_font,#house_city_code_font,.partnerNoNyyhShowTr").hide();
		}else if(partnerNo == "dianrongwang"){
			$(".partnerNoShowTr").show();
			$(".payBankShowTr").show();
			$(".confirmLoanReason_tr,.partnerNoNyyhShowTr,.partnerNoHnbxShowTr").hide();
			$("#house_province_code_font,#house_city_code_font").show();
		}else if(partnerNo == "nyyh"){
			$(".partnerNoShowTr,.payBankShowTr,.partnerNoNyyhShowTr").show();
			$("#house_province_code_font,#house_city_code_font").show();
			$(".payBankShowTr,.partnerNoHnbxShowTr").hide();
		}else if(partnerNo == "hnbx"){
			$(".partnerNoShowTr,.payBankShowTr,.partnerNoHnbxShowTr").show();
			$("#house_province_code_font,#house_city_code_font").show();
			$(".partnerNoNyyhShowTr").hide();
		}
		
		//修改文本提示
		var paymentFontStrList = $(".paymentFontStr");	//借款人》收款
		var payFontStrList = $(".payFontStr");			//打款》还款
		if(partnerNo == "hnbx"){
			$(paymentFontStrList).each(function(i){
				var tempText = $(this).html();
				$(this).html(tempText.replace("借款人","收款"));
			});
			$(payFontStrList).each(function(i){
				var tempText = $(this).html();
				$(this).html(tempText.replace("打款","还款"));
			});
		}else{
			$(paymentFontStrList).each(function(i){
				var tempText = $(this).html();
				$(this).html(tempText.replace("收款","借款人"));
			});
			$(payFontStrList).each(function(i){
				var tempText = $(this).html();
				$(this).html(tempText.replace("还款","打款"));
			});
		}
		
	}
	
	
	//操作列格式化
	function detailInfo(value, rowData, index) {
		
		var partnerNo = rowData.partnerNo;
		
		var projectDom ="";
			//审核不通过
		if(!(partnerNo == 'dianrongwang' || partnerNo == 'nyyh' || partnerNo == 'hnbx') ){
			if(rowData.approvalStatus == 3 && rowData.isClosed != 2){
				projectDom += "<a href='javascript:void(0)' onclick='addItem("+rowData.projectId+",3,"+rowData.approvalStatus+","+index+")'><font color='blue'>提交审核</font></a>";
			}else if(rowData.approvalStatus == 4 && rowData.isClosed != 2){
				//补充材料
				projectDom += "<a href='javascript:void(0)' onclick='addItem("+rowData.projectId+",3,"+rowData.approvalStatus+","+index+")'><font color='blue'>补充材料</font></a>";
			}
		}
		
			
		if(partnerNo == 'nyyh'){
			if(rowData.isClosed != 2 &&  (rowData.approvalStatus == 3  ||  rowData.requestStatus == 4) ){
				projectDom += "<a href='javascript:void(0)' onclick='resetPartnerOrderCode("+rowData.pid+")'><font color='blue'>重置申请</font></a>";
			}
		}
		//修改审核状态，
		if(partnerNo == 'hnbx'){
			if(rowData.isClosed != 2 &&  rowData.approvalStatus == 1 ){
				projectDom += "<a href='javascript:void(0)' onclick='updateApprovalStatusInit("+index+")'><font color='blue'>修改审核状态</font></a>";
			}
		}
		return projectDom;
	}

	//记录内容页面
	function openRecord(partnerId){
		var url = "<%=basePath%>projectPartnerController/getApprovalList.action?partnerId="+partnerId;
	    $('#open_record_table').datagrid({onLoadSuccess : function(data){

		}});
		$('#open_record').dialog('open').dialog('setTitle', "合作项目审批记录");
		$('#open_record_table').datagrid('options').url = url;
		$('#open_record_table').datagrid('load');
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
 
	//关闭单据
	function closePartner(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			
/* 			if(row[0].partnerNo == 'dianrongwang'){
				$.messager.alert("操作提示","合作机构点荣网不支持此方法！","error");
				return;
			} */
			if(row[0].isClosed == 2){
				$.messager.alert("操作提示","此项目已关闭，请重新选择！","error");
				return;
			}
			//南粤银行
			if(row[0].partnerNo == 'nyyh' &&  !(row[0].approvalStatus == 2  && (row[0].loanStatus == 1 || row[0].loanStatus == 0) ) ){
				$.messager.alert("操作提示","只有审核通过并且未申请放款，才允许关闭！","error");
				return;
			}
			
			//未申请，放款失败，驳回 才能关闭
			if(!(row[0].loanStatus == 0 || row[0].loanStatus == 1  || row[0].loanStatus == 5 || row[0].loanStatus == 6)){
				$.messager.alert("操作提示","放款状态为未申请，失败，驳回才能操作！","error");
				return;
			}
 			if(row[0].loanStatus == 4 ){
				$.messager.alert("操作提示","放款成功不能操作！","error");
				return;
			} 
			
			$.messager.confirm("操作提示","确认把当前选中的项目关闭?",
 					function(r) {
 			 			if(r){
 							$.post("<%=basePath%>projectPartnerController/closedPartnerInfo.action",{pid : row[0].pid}, 
 								function(ret) {
 									//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
 									if(ret && ret.header["success"] ){
 										$.messager.alert('操作提示',ret.header["msg"]);
 										// 重新项目列表信息
 					 					$("#grid").datagrid('load');
 					 					// 清空所选择的行数据
 					 					clearSelectRows("#grid");
 									}else{
 										$.messager.alert('操作提示',ret.header["msg"],'error');	
 									}
 								},'json');
 			 			}
 					});
			
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	//查看
	function lookUpItem(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			addItem(row[0].projectId,1,row[0].approvalStatus);
			$("add_partner_info").hide();
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	//记录内容列格式化
	function formatRecord(val,row){
		var projectDom = "<a href='javascript:void(0)' onclick='openRecord("+row.pid+")'><font color='blue'>记录内容</font></a>";
		return projectDom;
	}
	
	//保存信息
	function savePartnerInfo(){
		
		var partnerNo=$('#partner_source_no').combobox('getValue');
		
		if($("#applyLoanDate").textbox('getValue') == ""){
			$.messager.alert('操作提示',"申请放款日期不能为空");
			return;
		}
		var provinceCode=$('#province_code').combobox('getValue');
		if(provinceCode == null || provinceCode == '--请选择--' ){
			$.messager.alert("操作提示","请选择省份","error"); 
			return false;
		}
		var cityCode=$('#city_code').combobox('getValue');
		if(cityCode == null || cityCode == '--请选择--' ){
			$.messager.alert("操作提示","请选择城市","error"); 
			return false;
		}	
		
		if(partnerNo == 'tlxt' || partnerNo== 'dianrongwang' ){
			var paymentBank=$('#payment_bank').combobox('getValue');
			if(paymentBank == null || paymentBank == '--请选择--' ){
				$.messager.alert("操作提示","请选择借款人银行","error"); 
				$('#payment_bank').focus();
				return false;
			}	
			var paymentAcctName =$("#payment_acct_name").textbox('getValue')
			if(paymentAcctName == null || paymentAcctName == '' ){
				$.messager.alert("操作提示","请填写借款人户名","error"); 
				return false;
			}
			var paymentAcctNo =$("#payment_acct_no").textbox('getValue')
			if(paymentAcctNo == null || paymentAcctNo == '' ){
				$.messager.alert("操作提示","请填写借款人帐户","error"); 
				return false;
			}
		}
		if(partnerNo == 'dianrongwang'){
			var payBankName=$('#pay_bank_name').combobox('getValue');
			if(payBankName == null || payBankName == '--请选择--' ){
				$.messager.alert("操作提示","请选择打款银行","error"); 
				return false;
			}	
			var payBankBranch =$("#pay_bank_branch").textbox('getValue')
			if(payBankBranch == null || payBankBranch == '' ){
				$.messager.alert("操作提示","请填写打款银行支行","error"); 
				return false;
			}
			var payAcctName =$("#pay_acct_name").textbox('getValue')
			if(payAcctName == null || payAcctName == '' ){
				$.messager.alert("操作提示","请填写打款银行开户名","error"); 
				return false;
			}
			var payAcctNo =$("#pay_acct_no").textbox('getValue')
			if(payAcctNo == null || payAcctNo == '' ){
				$.messager.alert("操作提示","请填写打款银行帐号","error"); 
				return false;
			}
			var payProvinceCode=$('#pay_province_code').combobox('getValue');
			if(payProvinceCode == null || payProvinceCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择打款银行省份","error"); 
				return false;
			}
			var payCityCode=$('#pay_city_code').combobox('getValue');
			if(payCityCode == null || payCityCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择打款银行城市","error"); 
				return false;
			}
			//物业省市
			var houseProvinceCode=$('#house_province_code_house_form').combobox('getValue');
			if(houseProvinceCode == null || houseProvinceCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择物业信息>物业省份","error"); 
				return false;
			}
			var houseCityCode=$('#house_city_code_house_form').combobox('getValue');
			if(houseCityCode == null || houseCityCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择物业信息>物业城市","error"); 
				return false;
			}
			$("#house_province_code").val(houseProvinceCode);
			$("#house_city_code").val(houseCityCode);
		}
		
		var rows = $('#project_file_table_1').datagrid('getSelections');
		var resFilesId = "";
		for (var i = 0; i < rows.length; i++) {
				if (i == 0) {
					resFilesId += rows[i].pid;
				} else {
					resFilesId += "," + rows[i].pid;
				}
		}
		
		$("#requestFiles").val(resFilesId);
		$('#partnerInfo').form('submit', {
			url : "savePartnerInfo.action",
			onSubmit : function() {
					return true; 
			},
			success : function(result) {
				$.messager.alert('操作提示',"保存成功！");
				var ret = eval("("+result+")");
				var pid=ret.header["pid"];
				$("#pid").val(pid);
				//$('#add_partner').dialog('close');
			},error : function(result){
				$.messager.alert('操作提示',"保存失败！");
			}
		});
	}

	//发送申请
	function applyPartnerInfo(){
		//勾选的文件
		var rows = $('#project_file_table_1').datagrid('getSelections');
		var resFilesId = "";
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				resFilesId += rows[i].pid;
			} else {
				resFilesId += "," + rows[i].pid;
			}
		}
		$("#requestFiles").val(resFilesId);
		
		//补充材料
		var rows2 = $('#project_file_table_2').datagrid('getSelections');
		var resAddFilesId = "";
		for (var i = 0; i < rows2.length; i++) {
			if (i == 0) {
				resAddFilesId += rows2[i].pid;
			} else {
				resAddFilesId += "," + rows2[i].pid;
			}
		}
		$("#requestAddFiles").val(resAddFilesId);
		
		if($("#applyLoanDate").textbox('getValue') == ""){
			$.messager.alert('操作提示',"申请放款日期不能为空");
			return;
		}
		
		var provinceCode=$('#province_code').combobox('getValue');
		if(provinceCode == null || provinceCode == '--请选择--' ){
			$.messager.alert("操作提示","请选择省份","error"); 
			return false;
		}
		var cityCode=$('#city_code').combobox('getValue');
		if(cityCode == null || cityCode == '--请选择--' ){
			$.messager.alert("操作提示","请选择城市","error"); 
			return false;
		}	
		
		var partnerNo=$('#partner_source_no').combobox('getValue');
		if(partnerNo == 'tlxt' || partnerNo== 'dianrongwang' ){

			var paymentBank=$('#payment_bank').combobox('getValue');
			if(paymentBank == null || paymentBank == '--请选择--' ){
				$.messager.alert("操作提示","请选择借款人银行","error"); 
				$('#payment_bank').focus();
				return false;
			}	
			var paymentAcctName =$("#payment_acct_name").textbox('getValue')
			if(paymentAcctName == null || paymentAcctName == '' ){
				$.messager.alert("操作提示","请填写借款人户名","error"); 
				return false;
			}
			var paymentAcctNo =$("#payment_acct_no").textbox('getValue')
			if(paymentAcctNo == null || paymentAcctNo == '' ){
				$.messager.alert("操作提示","请填写借款人帐户","error"); 
				return false;
			}
		}
		if(partnerNo == 'dianrongwang'){
			var payBankName=$('#pay_bank_name').combobox('getValue');
			if(payBankName == null || payBankName == '--请选择--' ){
				$.messager.alert("操作提示","请选择打款银行","error"); 
				return false;
			}	
			var payBankBranch =$("#pay_bank_branch").textbox('getValue')
			if(payBankBranch == null || payBankBranch == '' ){
				$.messager.alert("操作提示","请填写打款银行支行","error"); 
				return false;
			}
			var payAcctName =$("#pay_acct_name").textbox('getValue')
			if(payAcctName == null || payAcctName == '' ){
				$.messager.alert("操作提示","请填写打款银行开户名","error"); 
				return false;
			}
			var payAcctNo =$("#pay_acct_no").textbox('getValue')
			if(payAcctNo == null || payAcctNo == '' ){
				$.messager.alert("操作提示","请填写打款银行帐号","error"); 
				return false;
			}
			var payProvinceCode=$('#pay_province_code').combobox('getValue');
			if(payProvinceCode == null || payProvinceCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择打款银行省份","error"); 
				return false;
			}
			var payCityCode=$('#pay_city_code').combobox('getValue');
			if(payCityCode == null || payCityCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择打款银行城市","error"); 
				return false;
			}
			
			//物业省市
			var houseProvinceCode=$('#house_province_code_house_form').combobox('getValue');
			if(houseProvinceCode == null || houseProvinceCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择物业信息>物业省份","error"); 
				return false;
			}
			var houseCityCode=$('#house_city_code_house_form').combobox('getValue');
			if(houseCityCode == null || houseCityCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择物业信息>物业城市","error"); 
				return false;
			}
			$("#house_province_code").val(houseProvinceCode);
			$("#house_city_code").val(houseCityCode);
		} 
		
		
		var url = "reApplyPartner.action";
		var approvalStatus = $("#approvalStatus").val();
		
		if(approvalStatus == 3){  //复议
			url = "reApplyPartner.action";
			//审核意见不能为空
			if($.trim($("#reApplyReason").val()) == ""){
				$.messager.alert('操作提示',"复议理由不能为空");
				$("#reApplyReason").focus();
				return;
			}
			if(resFilesId == ""){
				$.messager.alert('操作提示',"请选择材料再提交");
				return;			
			}
		}else if(approvalStatus == 4){	//补充材料
			if(resAddFilesId == ""){
				$.messager.alert('操作提示',"请选择补充材料再提交");
				return;			
			}
			url = "applyPartner.action";
		}else {
			$.messager.alert('操作提示',"当前状态不允许提交");
			return ;
		}
		
		$.messager.confirm("操作提示","确定发送申请?",
				function(r) {
		 			if(r){
						//提交时，解除禁用
						$("#partner_source_no").combobox("enable");
		 				$('#partnerInfo').form('submit', {
		 					url : url,
		 					onSubmit : function() {
		 						MaskUtil.mask("提交中,请耐心等候......");  
		 						return true; 
		 					},
		 					success : function(result) {
		 						 MaskUtil.unmask();
		 						var ret = eval("("+result+")");
		 						//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
		 						if(ret && ret.header["success"] ){
		 							$.messager.alert('操作提示',ret.header["msg"]);
		 							// 重新赎楼项目列表信息
		 		 					$("#grid").datagrid('load');
		 		 					// 清空所选择的行数据
		 		 					clearSelectRows("#grid");
		 		 					$('#add_partner').dialog('close');
		 						}else{
		 							$.messager.alert('操作提示',ret.header["msg"],'error');	
		 						}
		 					},error : function(result){
		 						 MaskUtil.unmask();
		 						alert("保存失败！");
		 					}
		 				});
		 			}
			});
	}
	
	
	//重置按钮
	function majaxReset(){
		$('#searchFrom').form('reset');
	}
	
	//获取附件 1:材料信息 2:补充材料	
	function getProjectFile(projectId,type,partnerId,partnerNo){
		
		if(projectId=="" || projectId == null){
			projectId = $("#projectId").val()
		}
		
		if(partnerNo == null || partnerNo == ''){
			partnerNo = $("#partner_source_no").combobox("getValue");
		}
		if(partnerId == null || partnerId == ""){
			partnerId = $("#pid").val();
		}
		
		var rows = $('#project_file_table_'+type).datagrid('getRows');//获取当前页的数据行
		var partnerNo = $("#partner_source_no").combobox("getValue");
		if(rows.length == 0 ){
			console.log("getProjectFile:projectId:"+projectId+",partnerNo:"+partnerNo);
			var url = "<%=basePath%>projectPartnerController/getProjectFileList.action?projectId="+projectId+"&partnerNo="+partnerNo+"&partnerId="+partnerId;
			$('#project_file_table_'+type).datagrid('options').url = url;
			$('#project_file_table_'+type).datagrid('load');
		}else{
			$('#project_file_table_'+type).datagrid("resize");
		}
	}
	
	//共同借款人
	function getPublicManList(){
		$("#public_man_table").datagrid("resize");
	}
	//审批记录
	function getWorkflowList(){
		$("#workflow_table").datagrid("resize");
	}
	//物业信息
	function getHouseInfo(){
		$("#house_info_table").datagrid("resize");
	}
	//赎楼信息
	function getOriginalLoan(){
		$("#originalLoan_table").datagrid("resize");
	}
	
	
	//确认要款初始化，弹窗
	function confirmLoanInit(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			
			if( row[0].partnerNo != 'xiaoying' ){
				$.messager.alert("操作提示","该合作机构不支持此方法！","error");
				return;
			}
			
			if(row[0].isClosed == 2){
				$.messager.alert("操作提示","此项目已关闭，请重新选择！","error");
				return;
			}
			if(row[0].approvalStatus != 2 ){
				$.messager.alert("操作提示","审核状态必须审核通过才能操作！","error");
				return;
			}else if(!(row[0].confirmLoanStatus == 0 ||row[0].confirmLoanStatus == 1 ) ){
				$.messager.alert("操作提示","确认要款状态必须是未发送才能操作","error");
				return;
			}
			
			addItem(row[0].projectId,4,row[0].approvalStatus);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	//确认要款
	function confirmLoan(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			
			if(row[0].isClosed == 2){
				$.messager.alert("操作提示","此项目已关闭，请重新选择！","error");
				return;
			}
			
			if(row[0].approvalStatus != 2 ){
				$.messager.alert("操作提示","审核状态必须审核通过才能操作！","error");
				return;
			}else if(!(row[0].confirmLoanStatus == 0 ||row[0].confirmLoanStatus == 1) ){
				$.messager.alert("操作提示","确认要款状态必须是未发送才能操作","error");
				return;
			}
			
			
			var confirmLoanReason = $.trim($("#confirmLoanReason").val());
			
			if( confirmLoanReason== ""){
				$.messager.alert("操作提示","确认要款理由不能为空","error");
				return;
			}
			
			$.messager.confirm("操作提示","发送确认要款请求?",
 					function(r) {
 			 			if(r){
 							$.post("<%=basePath%>projectPartnerController/confirmLoan.action",{loanId : row[0].loanId,confirmLoanReason:confirmLoanReason}, 
 								function(ret) {
 									//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
 									if(ret && ret.header["success"] ){
 										$.messager.alert('操作提示',ret.header["msg"]);
 										// 刷新列表信息
 					 					$("#grid").datagrid('load');
 					 					// 清空所选择的行数据
 					 					clearSelectRows("#grid");
 					 					$('#add_partner').dialog('close');
 									}else{
 										$.messager.alert('操作提示',ret.header["msg"],'error');	
 									}
 								},'json');
 			 			}
 					});
			
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	
	//复议确认要款初始化，弹窗
	function reConfirmLoanInit(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			
			if( row[0].partnerNo != 'xiaoying' ){
				$.messager.alert("操作提示","该合作机构不支持此方法！","error");
				return;
			}
			
			if(row[0].isClosed == 2){
				$.messager.alert("操作提示","此项目已关闭，请重新选择！","error");
				return;
			}
			if(row[0].approvalStatus != 2 ){
				$.messager.alert("操作提示","审核状态必须审核通过才能操作！","error");
				return;
			}else if(row[0].confirmLoanStatus == 4){
				$.messager.alert("操作提示","复议确认要款只能发送一次","error");
				return;
			}else if( !(row[0].confirmLoanStatus == 0 || row[0].confirmLoanStatus == 1)){
				$.messager.alert("操作提示","确认要款状态必须是未发送才能操作","error");
				return;
			}else if(!(row[0].loanStatus == 0 ||row[0].loanStatus == 1)){
				$.messager.alert("操作提示","放款状态只有未申请才能操作","error");
				return;
			}
			
			addItem(row[0].projectId,5,row[0].approvalStatus);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	
	//复议确认要款
	function reConfirmLoan(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if(row[0].isClosed == 2){
				$.messager.alert("操作提示","此项目已关闭，请重新选择！","error");
				return;
			}
			
			if(row[0].approvalStatus != 2 ){
				$.messager.alert("操作提示","审核状态必须审核通过才能操作！","error");
				return;
			}else if(row[0].confirmLoanStatus == 4){
				$.messager.alert("操作提示","复议确认要款只能发送一次","error");
				return;
			}else if( !(row[0].confirmLoanStatus == 0 || row[0].confirmLoanStatus == 1)){
				$.messager.alert("操作提示","确认要款状态必须是未发送才能操作","error");
				return;
			}else if(!(row[0].loanStatus == 0 ||row[0].loanStatus == 1)){
				$.messager.alert("操作提示","放款状态只有未申请才能操作","error");
				return;
			}else if(row[0].loanStatus == 4){
				$.messager.alert("操作提示","放款状态只有未申请才能操作","error");
				return;
			}
			
			
			var confirmLoanReason = $.trim($("#confirmLoanReason").val());
			if( confirmLoanReason== ""){
				$.messager.alert("操作提示","确认要款理由不能为空","error");
				return;
			}
			
			
			$.messager.confirm("操作提示","发送确认要款请求?",
 					function(r) {
 			 			if(r){
 							$.post("<%=basePath%>projectPartnerController/reConfirmLoan.action",{loanId : row[0].loanId,confirmLoanReason:confirmLoanReason}, 
 								function(ret) {
 									//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
 									if(ret && ret.header["success"] ){
 										$.messager.alert('操作提示',ret.header["msg"]);
 										// 刷新列表信息
 					 					$("#grid").datagrid('load');
 					 					// 清空所选择的行数据
 					 					clearSelectRows("#grid");
 					 					$('#add_partner').dialog('close');
 									}else{
 										$.messager.alert('操作提示',ret.header["msg"],'error');	
 									}
 								},'json');
 			 			}
 					});
			
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
 
	
	//材料信息操作
	function projectFileCzFormat(value, rowData, index) {
		var downloadUrl = "<%=basePath%>beforeLoanController/downloadData.action?path="+rowData.fileUrl+"&fileName="+rowData.fileName;
		var projectDom = "<a href='"+downloadUrl+"'><font color='blue'>下载</font></a>";
		return projectDom;
	}
	
	//打开上传文件   type  add:新增  update:修改
	function openUpload(rowIndex,type){
		$('#uploadFileForm').form('reset');
		var rowData =null;
		var projectName = $("#projectName").textbox("getValue");
		var partnerNo = $("#partner_source_no").combobox("getValue");
		if(type == "add"){
			projectId = $("#projectId").val();
		}
		if(partnerNo == null ){
			$.messager.alert('操作提示','请选择合作机构再上传文件','error');	
			return;
		}
		
		//setCombobox3("partner_no","PARTNER_NAME",partnerNo);
		$('#partner_no').combobox('setValue', partnerNo);
		$("#partner_no").combobox("disable");
		
	 	 if(partnerNo == "xiaoying" || partnerNo == 'dianrongwang'){
	 		 $("#accessoryChildTypeFont").hide();
	 	 }else if(partnerNo == "tlxt"){
	 		$("#accessoryChildTypeFont").show();
	 	 } 
		//设置合作项目id
		$("#partnerId_file").val($("#pid").val());
		
		$('#projectName_file').text(projectName);
		$('#projectId_file').val(projectId);
		$('#uploadFileForm').attr('action',"${basePath}projectPartnerController/uploadPartnerFile.action");
		$('#dialog_data').dialog('open').dialog('setTitle','上传材料');
	}
	
	//上传材料
	function uploadPartnerFile(){
		//var file = $('#file').textbox('getValue');
		var projectId = $('#projectId_file').val();
		var accessoryType=$('#accessoryType').combobox('getValue');
		var accessoryChildType=$('#accessoryChildType').combobox('getValue');
		var partnerNo=$('#partner_no').combobox('getValue');
		var partnerId=$('#partnerId_file').val();
		if(partnerNo == "" || partnerNo == null){
			$.messager.alert("操作提示","请选择合作机构","error"); 
			return false;
		}
		if(projectId=="" || null==projectId){
			$.messager.alert("操作提示","请选择项目","error"); 
			return false;
		}
		if(accessoryType == "" || accessoryType == null){
			$.messager.alert("操作提示","请选择附件类型","error"); 
			return false;
		}
		if(partnerNo == 'tlxt' ){
			if(accessoryChildType == "" || accessoryChildType == null){
				$.messager.alert("操作提示","请选择附件子类型","error"); 
				return false;
			}
		}
		
		if($("#fileQueue .fileName") == null || $("#fileQueue .fileName").length  == 0 ){
			$.messager.alert("操作提示","请选择文件","error"); 
			return false;
		}
		//多文件上传方式
		$('#uploadify').uploadify('settings','formData',{'projectId':projectId,
				'accessoryType':accessoryType,
				'accessoryChildType':accessoryChildType,
				'partnerNo':partnerNo,
				'partnerId':partnerId,
				'remark':encodeURI($("#remark_file").val())});
		$('#uploadify').uploadify('upload','*');
		
		
/* 		//解除禁用提交
		$("#partner_no").combobox("enable");
		
		$('#uploadFileForm').form('submit',{
			success:function(data){
				var dataObj=eval("("+data+")");//转换为json对象
				if(dataObj && dataObj.header.success ){
					$.messager.alert('操作提示',dataObj.header.msg);
 					
 					if($("#pid_file").val()>0){
 						// 重新加载
 						$("#project_file_table_1").datagrid('load');
	 					$('#dialog_data').dialog('close');
 					}else{
 						$('#file').textbox('setValue', "");
 					}
				}else{
					$.messager.alert('操作提示',dataObj.header.msg,'error');	
				}
			}
		}) */
	}
	
	//更新子文件对应类型
	function changeFileChileType(accessoryTypeVal){
		
		var accessoryChildTypeJson = null;
		if(accessoryTypeVal == ""){
			accessoryChildTypeJson = [{ "pid": "", "lookupDesc": "请选择" }];
		}else if(accessoryTypeVal == "1"){
			accessoryChildTypeJson = partnerGlobal.accessoryChildTypeJson_1;
		}else if(accessoryTypeVal == "2"){
			accessoryChildTypeJson = partnerGlobal.accessoryChildTypeJson_2;
		}else if(accessoryTypeVal == "3"){
			accessoryChildTypeJson = partnerGlobal.accessoryChildTypeJson_3;
		}else if(accessoryTypeVal == "4"){
			accessoryChildTypeJson = partnerGlobal.accessoryChildTypeJson_4;
		}else if(accessoryTypeVal == "5"){
			accessoryChildTypeJson = partnerGlobal.accessoryChildTypeJson_5;
		}else if(accessoryTypeVal == "6"){
			accessoryChildTypeJson = partnerGlobal.accessoryChildTypeJson_6;
		}else if(accessoryTypeVal == "7"){
			accessoryChildTypeJson = partnerGlobal.accessoryChildTypeJson_7;
		}else if(accessoryTypeVal == "8"){
			accessoryChildTypeJson = partnerGlobal.accessoryChildTypeJson_8;
		}else if(accessoryTypeVal == "9"){
			accessoryChildTypeJson = partnerGlobal.accessoryChildTypeJson_9;
		}else if(accessoryTypeVal == "10"){
			accessoryChildTypeJson =  partnerGlobal.accessoryChildTypeJson_10;
		}else if(accessoryTypeVal == "11"){
			accessoryChildTypeJson =  partnerGlobal.accessoryChildTypeJson_11;
		}
		$('#accessoryChildType').combobox({         
            data:accessoryChildTypeJson, //此为重点
            valueField:'pid',
            textField:'lookupDesc',
            //如果需要设置多项隐含值，可使用以下代码
            onLoadSuccess:function(){
             }
        });
	}
	
	
	//更新同步第三方贷款状态
	function updteThirdLoanStatus(partnerNo_btn){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var partnerNo = row[0].partnerNo;
			
			if(row[0].isClosed == 2){
				$.messager.alert("操作提示","此项目已关闭，请重新选择！","error");
				return;
			}
			
			if(partnerNo_btn =="dianrongwang" && "dianrongwang" != partnerNo ){
				$.messager.alert("操作提示","请选择合作机构为点荣网的数据","error");
				return;
			}
			if(partnerNo_btn =="nyyh" && "nyyh" != partnerNo ){
				$.messager.alert("操作提示","请选择合作机构为南粤银行的数据","error");
				return;
			}
			
			//南粤银行
			if("nyyh" == partnerNo){
				//审核中才允许同步
				if(row[0].approvalStatus != 1){
					$.messager.alert("操作提示","只有审核中的才允许操作","error");
					return;
				}
			}
			
			$.messager.confirm("操作提示","确认同步第三方贷款状态?",
 					function(r) {
 			 			if(r){
 							$.post("<%=basePath%>projectPartnerController/updteThirdLoanStatus.action",{pid : row[0].pid}, 
 								function(ret) {
 									//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
 									if(ret && ret.header["success"] ){
 										$.messager.alert('操作提示',ret.header["msg"]);
 										// 重新项目列表信息
 					 					$("#grid").datagrid('load');
 					 					// 清空所选择的行数据
 					 					clearSelectRows("#grid");
 									}else{
 										$.messager.alert('操作提示',ret.header["msg"],'error');	
 									}
 								},'json');
 			 			}
 					});
			
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
 
	
	/**
	 * 重新加载原贷款银行信息
	 */
	function loadProjectOriginalLoan(projectId){
		//获取原贷款信息
		var url = "<%=basePath%>beforeLoanController/getOriginalLoanByProjectId.action?projectId="+projectId;
		$('#originalLoan_table').datagrid('options').url = url;
		$('#originalLoan_table').datagrid('reload');
	}
	
	
	//重置业务申请
	function resetPartnerOrderCode(pid){
		$.messager.confirm("操作提示","确定重置业务申请?",function(r) {
			if(r){
				MaskUtil.mask("重置中,请耐心等候......");  
				$.ajax({
					type: "POST",
			        url: "<%=basePath%>projectPartnerController/resetPartnerOrderCode.action",
			        data:{"pid":pid},
			        dataType: "json",
			        success: function(result){
			        	MaskUtil.unmask();
 			        	if(result.header.success == true){
				        	$.messager.alert("操作提示","操作成功，请到机构合作项目申请");
 			        	}else{
 			        		$.messager.alert('操作提示',result.header.msg,'error');	
 			        	}
					},error : function(result){
 						MaskUtil.unmask();
 						$.messager.alert('操作提示',"重置失败！",'error');	
 					}
				 });
 			}
		});
	}
	
	//修改审核状态初始化
	function updateApprovalStatusInit(rowIndex){
		
		$('#updateApprovalStatus_form').form('reset');
		var rowData = $('#grid').datagrid('getData').rows[rowIndex];
		var pid = rowData.pid;
		var projectName = rowData.projectName;				//项目名称
		var partnerNo = rowData.partnerNo;					//合作机构
		
		var partnerNoName = "";
		if(partnerNo == 'xiaoying'){
			partnerNoName = "小赢";
		}else if(partnerNo == 'tlxt'){
			partnerNoName = "统联信托";
		}else if(partnerNo == 'dianrongwang'){
			partnerNoName = "点荣网";
		}else if(partnerNo == 'nyyh'){
			partnerNoName = "南粤银行";
		}else if(partnerNo == 'hnbx'){
			partnerNoName = "华安保险";
		}
		
		$("#pid_updateApprovalStatus").val(pid);
		$("#partnerNo_updateApprovalStatus").val(partnerNo);
		$('#projectName_updateApprovalStatus').text(projectName);
		$('#partnerNoName_updateApprovalStatus').text(partnerNoName);
			
		$('#updateApprovalStatus_data').dialog('open').dialog('setTitle','修改审核状态信息');
	}
	
	//修改审核状态
	function updateApprovalStatus(){
		var approvalStatus= $("#approvalStatus_updateApprovalStatus").combobox('getValue');
		if(approvalStatus == ''){
			$.messager.alert("操作提示","请选择审核状态","error");
			return ;
		}
		
		$.messager.confirm("操作提示","确认修改审核状态信息?",
			function(r) {
	 			if(r){
	 				$('#updateApprovalStatus_form').form('submit', {
						url : "updateApprovalStatus.action",
						onSubmit : function() {
								MaskUtil.mask("提交中,请耐心等候......");  
								return true; 
						},
						success : function(result) {
							MaskUtil.unmask(); 
							var ret = eval("("+result+")");
							//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
							if(ret && ret.header["success"] ){
								$.messager.alert('操作提示',ret.header["msg"]);
								// 刷新列表信息
			 					$("#grid").datagrid('load');
			 					// 清空所选择的行数据
			 					clearSelectRows("#grid");
			 					$('#updateApprovalStatus_data').dialog('close');
							}else{
								$.messager.alert('操作提示',ret.header["msg"],'error');	
							}
						},error : function(result){
							MaskUtil.unmask(); 
							alert("操作失败！");
						}
					});
	 			}
		});
	}
	
	
	
	
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="partnerInfoList.action" method="post" id="searchFrom" name="searchFrom">
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
				<!-- 查询条件 -->
				<div style="padding: 5px">
				<table>
					<tr>
						<td width="80" align="right" height="25">项目编号：</td>
						<td>
							<input id="projectNumber" class="easyui-textbox" name="projectNumber" style="width:150px;" />
						</td>
						<td width="80" align="right" height="25">项目名称：</td>
						<td>
							<input  class="easyui-textbox" name="projectName" style="width:150px;" />
						</td>
						<td width="80" align="right" height="25">合作机构：</td>
						<td>
							<select class="easyui-combobox" id="queryPartnerNo" name="partnerNo" style="width:120px" panelHeight="auto">
								<option value='' >--请选择--</option>
								<option value='xiaoying' >小赢</option>
							    <option value='tlxt' >统联信托</option>
								<option value='dianrongwang' >点荣网</option>
								<option value='nyyh' >南粤银行</option>
								<option value='hnbx'>华安保险</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="80" align="right" height="25">审批编号：</td>
						<td>
							<input  id="query_loanId" class="easyui-textbox" name="loanIds" style="width:150px;" />
						</td>
						
						<td width="80" align="right" height="25">审核状态 ：</td>
						<td>
							<select class="easyui-combobox" id="query_approvalStatus" name="approvalStatus" style="width:120px" panelHeight="auto">
								<option value='0' >--请选择--</option>
								<option value='1' >审核中</option>
							    <option value='2' >通过</option>
								<option value='3' >未通过</option>
								<option value='4' >补充材料</option>
							</select>
						</td>
						
						
						<td>
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
							<a href="#" onclick="majaxReset()" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
						</td>
					</tr>
				</table>
				</div>
			</form>
		
			<div style="padding-bottom: 5px">
		

				<shiro:hasAnyRoles name="APPROVAL_INDEX,ALL_BUSINESS">
			   		<a href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-add" plain="true" onclick="editItem()">修改</a> 
			     </shiro:hasAnyRoles>
			     <shiro:hasAnyRoles name="APPROVAL_INDEX,ALL_BUSINESS">
			   		<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="closePartner()">关闭单据</a>
			     </shiro:hasAnyRoles>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="lookUpItem()">查看</a>
				<shiro:hasAnyRoles name="APPROVAL_INDEX,ALL_BUSINESS">
			   		<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="confirmLoanInit()">确认要款</a>
			     </shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="APPROVAL_INDEX,ALL_BUSINESS">
			   		<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="reConfirmLoanInit()">复议确认要款</a>
			     </shiro:hasAnyRoles>	
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="updteThirdLoanStatus('dianrongwang')">同步状态(点荣网)</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="updteThirdLoanStatus('nyyh')">同步状态(南粤银行)</a>
			</div>
		</div>
		<div class="perListDiv" style="height:100%;">
			<table id="grid" title="机构审核" class="easyui-datagrid"
				style="height:100%; width: auto;"
				data-options="
				    url: 'partnerInfoList.action',
				    method: 'POST',
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
				    fitColumns:true" >
				    
				<!--
				 表头 -->
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true"></th>
						<th data-options="field:'partnerOrderCode',hidden:true"></th>
						<th data-options="field:'projectNumber',width:80,sortable:true"  align="center" halign="center">项目编号</th>
						<th data-options="field:'projectName',width:80,sortable:true" align="center" halign="center">项目名称</th>
						<th data-options="field:'projectSource',formatter:partnerGlobal.formatProjectSource,width:80,sortable:true" align="center" halign="center">项目来源</th>
						<th data-options="field:'businessCategoryStr',width:80,sortable:true" align="center" halign="center">业务类型</th>
						<th data-options="field:'partnerNo',formatter:partnerGlobal.partnerFormat,width:80,sortable:true" align="center" halign="center">合作机构</th>
						<th data-options="field:'loanId',width:80,sortable:true" align="center" halign="center">审查编号</th>
						
						<th data-options="field:'loanAmount',width:80,sortable:true" align="center" halign="center">借款金额</th>
						<th data-options="field:'approveMoney',width:80,sortable:true" align="center" halign="center">审批金额</th>
						
						<th data-options="field:'requestStatus',formatter:partnerGlobal.formatterRequestStatus,width:80,sortable:true" align="center" halign="center">申请状态</th>
						<th data-options="field:'approvalStatus',formatter:partnerGlobal.formatterStatus,width:80,sortable:true" align="center" halign="center">审核状态</th>
						<th data-options="field:'confirmLoanStatus',formatter:partnerGlobal.formatConfirmStatus,width:80,sortable:true" align="center" halign="center">确认要款状态</th>
						<th data-options="field:'loanStatus',formatter:partnerGlobal.formatLoanState,width:80,sortable:true" align="center" halign="center">放款状态</th>
						<th data-options="field:'approvalComment',formatter:partnerGlobal.formatTooltip,width:80,sortable:true" align="center" halign="center">审批意见</th>
						<th data-options="field:'submitApprovalTime',width:80,sortable:true" align="center" halign="center">提交审核时间</th>
						<th data-options="field:'approvalTime',width:80,sortable:true" align="center" halign="center">审批时间</th>
						<th data-options="field:'cz',formatter:formatRecord,width:80,sortable:true" align="center" halign="center">审批记录</th>
						<th data-options="field:'isClosed',formatter:partnerGlobal.formatterCloseState,width:80,sortable:true" align="center" halign="center">关闭状态</th>
						
						<!-- 数据权限 -->
						<shiro:hasAnyRoles name="APPROVAL_INDEX,ALL_BUSINESS">
							<th data-options="field:'cz1',formatter:detailInfo,width:160,sortable:true" align="center" halign="center">操作</th>
						</shiro:hasAnyRoles>
					</tr>
				</thead>
			</table>
		</div>  
		<!-- 提交审核页面开始 -->
		<div id="add_partner_info">		
			<a href="javascript:void(0)" id="partner_btn_save" class="easyui-linkbutton" iconCls="icon-save" onclick="savePartnerInfo()">保存</a>
			<a id="uploadFileBtn" style="display: none;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="openUpload('','add');">上传资料</a>
		    <a href="javascript:void(0)" id="partner_btn_submit" class="easyui-linkbutton" iconCls="icon-save" onclick="applyPartnerInfo()">提交审核</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#add_partner').dialog('close')">关闭</a>
		</div>
		<div id="add_partner" class="easyui-dialog" fitColumns="true"  title="合作项目提交审核"
		     style="width:850px;top:100px;padding:10px;" buttons="#add_partner_info" closed="true" >
			
			<section class="hearder" id="header">
			    <section class="info_top info_top_auto">
				        <div class="order_tab">
				        	<a href="#" id="info" class="first current">基本信息</a>
				        	<a href="#" onclick="showPerBase()" class="last">客户附加信息</a>
				        	<a href="#" id="getProjectFile_btn" onclick="getProjectFile('',1)" class="last">材料信息</a>
				        	<a href="#" id="requestAddFilesBtn" onclick="getProjectFile('',2)" class="last">补充材料</a>
				        	<a href="#"  onclick="getPublicManList()" class="last">共同借款人</a>
				        	<a href="#"  onclick="getWorkflowList()" class="last">审批记录</a>
				        	<a href="#" onclick="getOriginalLoan()"  class="last">赎楼信息</a>
				        	<a href="#"  onclick="getHouseInfo()" class="last">物业信息</a>
				        </div>
				</section>
			</section>
		
			<div style="left:0;">
				<div class="nearby" style="display: block" id="nearby_project">
					<form id="partnerInfo" action="<%=basePath%>projectPartnerController/saveProjectPartner.action" method="POST">
						<table class="beforeloanTable">
							<input type="hidden" name="pid" id="pid"/>
							<input type="hidden" name="projectId" id="projectId"/>
							<input type="hidden" name="businessType"/>
							<input type="hidden" name="requestFiles" id="requestFiles"/>
							<!-- 补充材料 -->
							<input type="hidden" name="requestAddFiles" id="requestAddFiles"/>
							<input type="hidden" name="pmUserId"/>
							<input type="hidden" name="city" value="深圳"/>
							<input type="hidden" name="loanId" id="loanId"/>
							<!-- 审核状态 -->					
							<input type="hidden" name="approvalStatus" id="approvalStatus"/>
							
							<input type="hidden" id="house_province_code" name="houseProvinceCode" />
							<input type="hidden" id="house_city_code" name="houseCityCode" />	
							<input type="hidden" id="acctId" name="acctId" />						
							
						  <tr>
							 <td class="label_right1">项目名称：</td>
							 <td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="projectName" name="projectName"/></td>
							 <td class="label_right1">产品名称：</td>
							 <td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="businessTypeStr" name="businessTypeStr"/></td>
							<td class="label_right1"><font color="red">*</font>合作机构：</td>
							 <td>
								<select id="partner_source_no" name="partnerNo" class="easyui-combobox" editable="false" style="width:129px;"  />
							 </td>
						  </tr>
							<tr>

							<td class="label_right1">客户姓名：</td>
							 <td>
								<select id="userName" class="text_style easyui-textbox" readonly="readonly" name="userName"  style="width:150px;"   />
							 </td>
						  	<td class="label_right1" >性别：</td>
						  	<td>
						     	<select id="sex" editable="false" disabled="disabled" class="easyui-combobox" data-options="validType:'selrequired'" name="sex" style="width:150px;" />
						     </td>
						  	<td class="label_right1">手机号码：</td>
							 <td><input type="text" id="phone" class="text_style easyui-textbox" readonly="readonly" name="phone"/></td>
						  </tr>
						  <tr>
						  	<td class="label_right1" >证件类型：</td>
						     <td>
						     	<select id="cert_type" editable="false" disabled="disabled" class="easyui-combobox" data-options="validType:'selrequired'" name="certType" style="width:150px;" />
						     </td>
						  	<td class="label_right1" >身份证：</td>
						     <td><input type="text" class="text_style easyui-textbox" readonly="readonly" name="cardNo"/></td>
						  	<td class="label_right1">现居住地址：</td>
							 <td><input type="text"  class="text_style easyui-textbox" readonly="readonly" name="liveAddr" id="liveAddr"/></td>
						  </tr>

						  <tr>
						  	<td class="label_right1" >借款天数：</td>
						     <td><input type="text" class="text_style easyui-textbox" readonly="readonly" name="applyDate"/></td>
						  	<td class="label_right1">借款金额：</td>
							 <td><input type="text" id="applyMoney" class="text_style easyui-textbox" readonly="readonly" name="applyMoney"/></td>
						  	<td class="label_right1" ><font color="red">*</font>申请放款日期：</td>
						     <td>
						     	<input type="text" class="easyui-textbox" editable="false" name="applyLoanDate" id="applyLoanDate"/>
						     </td>
						  </tr>
						  <tr>
						  	<td class="label_right1" >客户经理：</td>
						     <td>
						     	<input type="text" class="easyui-textbox" editable="false" name="pmCustomerName" id="pmCustomerName"/>
						     </td>
						  	<td class="label_right1" >业务联系人：</td>
						     <td>
						     	<input type="text" class="easyui-textbox" editable="false" name="businessContacts" id="businessContacts"/>
						     </td>
						  	<td class="label_right1" >经办人：</td>
						     <td>
						     	<input type="text" class="easyui-textbox" editable="false" name="managers" id="managers"/>
						     </td>

						  </tr>
						  <tr>
						  	<td class="label_right1" ><font color="red">*</font>省份：</td>
						     <td>
						     	<select id="province_code" name="provinceCode" class="easyui-combobox" editable="false" style="width:129px;" />
						     </td>
						  	<td class="label_right1" ><font color="red">*</font>城市：</td>
						     <td>
						     	<select id="city_code" name="cityCode" class="easyui-combobox" editable="false" style="width:129px;" />
						     </td>
						  </tr>
						  <tr  class="partnerNoShowTr" style="display: none;">
						  </tr>
						  <tr class="partnerNoShowTr">
						  	<td class="label_right1" ><font color="red">*</font><font class="paymentFontStr"> 借款人银行：</font></td>
						     <td >
						     	<select id="payment_bank" name="paymentBank" class="easyui-combobox" editable="false" style="width:129px;" />
						     </td>
						  	<td class="label_right1"><font color="red">*</font><font class="paymentFontStr">借款人户名：</font></td>
							 <td><input type="text"  class="text_style easyui-textbox" name="paymentAcctName" id="payment_acct_name" data-options="required:true,validType:'length[1,50]'"/></td>
						  	<td class="label_right1" ><font color="red">*</font><font class="paymentFontStr">借款人账号：</font></td>
						     <td>
						     	<input type="text" class="easyui-textbox" name="paymentAcctNo" id="payment_acct_no" data-options="required:true,validType:'length[1,25]'"/>
						     </td>
						  </tr>
						  
						  <tr  class="partnerNoNyyhShowTr">
						  	 <td class="label_right1" ><font color="red">*</font>借款人预留手机号：</td>
						     <td>
						     	<input type="text" id="payment_bank_phone" name="paymentBankPhone"  class="easyui-textbox" editable="false"  data-options="required:true,validType:'length[1,11]'"/>
						     </td>
						  	 <td class="label_right1" ><font color="red">*</font>借款期限（月）：</td>
						     <td>
						     	<input type="text" id="loan_period_limit" name="loanPeriodLimit"  class="easyui-numberbox" editable="false"  data-options="required:true,validType:'length[1,2]',min:1,max:10"/>
						     </td>
						  	 <td class="label_right1" ><font color="red">*</font>他行有无未结清信用贷款：</td>
						     <td>
						     	<select id="is_credit_loan" name="isCreditLoan"  panelHeight="150" class="easyui-combobox" editable="false" data-options="required:true" style="width:160px;">
									<option value="0">请选择</option>
									<option value="1">有</option>
									<option value="2">无</option>
								</select>								
						     </td>
						  </tr>
						  <tr  class="partnerNoNyyhShowTr">
						  	 <td class="label_right1" ><font color="red">*</font>借款人银行联行行号：</td>
						     <td>
						     	<input type="text" id="payment_bank_line_no" name="paymentBankLineNo"  class="easyui-textbox" editable="false"  data-options="required:true,validType:'length[1,50]'"/>
						     </td>
						  </tr>
						  
						  <!-- 打款信息 -->
						  <tr  class="payBankShowTr" style="display: none;">
						  	<td class="label_right1" style="width:160px;" ><font color="red">*</font><font class="payFontStr">打款银行/公司：</font></td>
						     <td>
						     	<select id="pay_bank_name" name="payBankName" class="easyui-combobox" editable="false" style="width:129px;" />
						     </td>
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行支行：</font></td>
						     <td>
						     	<input id="pay_bank_branch" name="payBankBranch" class="easyui-textbox"  data-options="required:true,validType:'length[1,15]'"  />
						     </td>
						  </tr>
						  <tr  class="payBankShowTr" style="display: none;">
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行开户名：</font></td>
						     <td>
						     	<input id="pay_acct_name" name="payAcctName" class="easyui-textbox"  data-options="required:true,validType:'length[1,50]'" />
						     </td>
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行帐号：</font></td>
						     <td>
						     	<input id="pay_acct_no" name="payAcctNo" class="easyui-textbox" data-options="required:true,validType:'length[1,25]'"/>
						     </td>
						   </tr>
						   <tr class="payBankShowTr" style="display: none;">
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行省份：</font></td>
						     <td>
						     	<select id="pay_province_code" name="payProvinceCode" class="easyui-combobox" editable="false" style="width:129px;" />
						     </td>
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行城市：</font></td>
						     <td>
						     	<select id="pay_city_code" name="payCityCode" class="easyui-combobox" editable="false" style="width:129px;" />
						     </td>
						  </tr>
						  <tr class="partnerNoHnbxShowTr">
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">执行利率：</font></td>
					     	<td>
					     		<input type="text" id="partnerGrossRate" name="partnerGrossRate" class="text_style easyui-textbox" editable="false"  data-options="required:true"/>
					     	</td>
						  	 <td class="label_right1" ><font color="red">*</font><font class="payFontStr">推送帐号：</font></td>
						     <td>
						     	<select id="partnerPushAccount" name="partnerPushAccount" class="easyui-combobox" editable="false" d style="width:129px;" >
						     		<option value="">请选择</option>
						     		<option value="2">小科</option>
						     		<option value="1">万通</option>
						     	</select>
						     </td>
						  </tr>
						  <tr>
						  	<td class="label_right1">操作人：</td>
							 <td><input type="text" id="cert_Number" class="text_style easyui-textbox" readonly="readonly" name="pmUserName"/></td>
						  </tr>
						  </tr>
						   <td class="label_right1">备注：</td>
							<td>
								<input name="remark" id="remark" class="easyui-textbox" style="width:100%;height:50px" data-options="multiline:true,validType:'length[0,200]'">
							</td>
						   <td class="label_right1"><font color="red" id="reApplyReasonFont" style="display: none;">*</font>复议理由：</td>
							<td >
								<input name="reApplyReason" id="reApplyReason" class="easyui-textbox" style="width:100%;height:50px" data-options="multiline:true,validType:'length[0,200]'">
							</td>
						  </tr>
						  <tr class="partnerNoHideTr" id="confirmLoanReason_tr" style="display: none;">
						   <td class="label_right1"><font color="red">*</font>确认要款理由：</td>
							<td colspan="3"><input name="confirmLoanReason" id="confirmLoanReason" class="easyui-textbox" style="width:65%;height:40px" data-options="multiline:true,validType:'length[0,200]'"></td>
						  </tr>
						 </table> 
					</form>	  
				</div>
				<!-- 加载客户附加信息 -->
				<div class="nearby" id="nearby_cus_base" style="display: block">
				</div>
			        
			     <!-- 材料1 -->
				<div class="nearby" id="nearby_project_file_1">
					<table id="project_file_table_1" class="" fitColumns="true" style="width:790px;"
								     data-options="
								     	url: '',
								     	method: 'post',
								     	rownumbers: true,
							    		fitColumns:true,
								    	singleSelect: false,
									    pagination: false">
						<thead>
							<tr>
								<th data-options="field:'remark',hidden:true" ></th>
								<th data-options="field:'fileUrl',hidden:true" ></th>
								<th data-options="field:'pid',checkbox:true" ></th>
								<th data-options="field:'fileName'" >文件名称</th>
								<th data-options="field:'partnerNo',formatter:partnerGlobal.partnerFormat" >合作机构</th>
								<th data-options="field:'accessoryType',formatter:partnerGlobal.accessoryTypeFormat" >文件类型</th>
								<th data-options="field:'accessoryChildType',formatter:partnerGlobal.accessoryChildTypeFormat,width:120,sortable:true" >文件子类型</th>
								<th data-options="field:'fileType'" >文件种类</th>
								<th data-options="field:'thirdFileUrl',formatter:partnerGlobal.thirdFileUrlFormat" >是否上传第三方</th>
								<th data-options="field:'cz',formatter:projectFileCzFormat,width:80,sortable:true" >操作</th>
							</tr>
						</thead>
					</table>  
				</div> 
				<!-- 补充材料2 -->
				<div class="nearby" id="nearby_project_file_2">
					<table id="project_file_table_2" class="" fitColumns="true" style="width:850px;"
								     data-options="
								     	url: '',
								     	method: 'post',
								     	rownumbers: true,
							    		fitColumns:true,
								    	singleSelect: false,
									    pagination: false">
						<thead>
							<tr>
								<th data-options="field:'remark',hidden:true" ></th>
								<th data-options="field:'fileUrl',hidden:true" ></th>
								<th data-options="field:'pid',checkbox:true" ></th>
								<th data-options="field:'fileName'" >文件名称</th>
								<th data-options="field:'partnerNo',formatter:partnerGlobal.partnerFormat" >合作机构</th>
								<th data-options="field:'accessoryType',formatter:partnerGlobal.accessoryTypeFormat" >文件类型</th>
								<th data-options="field:'accessoryChildType',formatter:partnerGlobal.accessoryChildTypeFormat,width:120,sortable:true" >文件子类型</th>
								<th data-options="field:'fileType'" >文件种类</th>
								<th data-options="field:'cz',formatter:projectFileCzFormat,width:80,sortable:true" >操作</th>
							</tr>
						</thead>
					</table>  
				</div> 
				
				
				<!-- 共同借款人 -->
				<div class="nearby" id="nearby_public_man_table" style="display: block">
					<table id="public_man_table" class="" fitColumns="true" style="width:800px;height:330px;"
								     data-options="
								     	url: '',
								     	method: 'post',
								     	rownumbers: true,
							    		fitColumns:true,
								    	singleSelect: false,
									    pagination: false">
						<thead>
							<tr>
								<th data-options="field:'chinaName'" >姓名</th>
								<th data-options="field:'sexName'" >性别</th>
								<th data-options="field:'certTypeName'" >证件类型</th>
								<th data-options="field:'certNumber'" >证件号码</th>
								<th data-options="field:'relationText'" >关系</th>
								<th data-options="field:'workService'" >职务</th>
								<th data-options="field:'monthIncome'" >月收入</th>
							</tr>
						</thead>
					</table>  
				</div>
				<!-- 审批记录 -->
				<div class="nearby" id="nearby_workflow_table" style="display: block">
					<table id="workflow_table" class="" fitColumns="true" style="width:800px;height:330px;"
								     data-options="
								     	url: '',
								     	method: 'post',
								     	rownumbers: true,
							    		fitColumns:true,
								    	singleSelect: false,
									    pagination: false,
									    nowrap:false">
						<thead>
							<tr>
								<th data-options="field:'taskName'" >任务描述</th>
								<th data-options="field:'executeUserRealName'" >操作人</th>
								<th data-options="field:'executeDttm'" >操作时间</th>
								<th data-options="field:'message',width:200,align:'left'" >意见与说明 </th>
							</tr>
						</thead>
					</table>  
				</div>
				
				<!-- 赎楼信息 -->
				<div class="nearby" id="nearby_foreclosure_table" style="display: block">
				
					<!-- 原贷款银行列表 -->
					<table id="originalLoan_table" class=""  style="width: 750px;"
					     data-options="
					     	url: '',
					     	method: 'post',
					     	rownumbers: true,
				    		fitColumns:true,
						    pagination: false,
						    nowrap:false">
						<thead>
							<tr>
								<th data-options="field:'originalLoanId',checkbox:true" ></th>
								<th data-options="field:'projectId',hidden:true" ></th>
								<th data-options="field:'loanTypeStr'"  align="center" halign="center" >原贷款类型</th>
								<th data-options="field:'oldLoanBankStr',formatter:partnerGlobal.formatTooltip"  align="center" halign="center" >原贷款银行</th>
								<th data-options="field:'oldLoanMoney',formatter:formatMoney"  align="center" halign="center" >原贷款金额(元)</th>
								<th data-options="field:'oldOwedAmount',formatter:formatMoney"  align="center" halign="center" >原欠款金额(元)</th>
								<th data-options="field:'estateName',formatter:partnerGlobal.formatTooltip"  align="center" halign="center" >原贷款物业</th>
								<th data-options="field:'oldLoanAccount',formatter:partnerGlobal.formatTooltip"  align="center" halign="center" >供楼账号</th>
							</tr>
						</thead>
				   	</table>
				
					<form id="foreclosure_form" action="<%=basePath%>projectPartnerController/saveProjectPartner.action" method="POST">
						<table id="foreclosure_table" class="beforeloanTable">
							
							<!-- 隐藏赎楼信息id -->
							<input type="hidden" id="foreclosure_table_pid" name="pid">
							
							<tr>
								<td class="label_right1">原贷款银行：</td>
								<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="oldBankStr" name="oldBankStr"/></td>
								<td class="label_right1">原贷款总金额：</td>
								<td><input id="foreclosure_table_oldLoanMoney"  class="easyui-numberbox" readonly="readonly"   name="oldLoanMoney" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
								<td class="label_right1"> 原贷款欠款总金额：</td>
								<td>
									 <input id="foreclosure_table_oldOwedAmount" name="oldOwedAmount" readonly="readonly" class="easyui-numberbox" readonly="readonly" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"  style="width:129px;"/>
								</td>
							 </tr>
							<tr>
							 	<td class="label_right1">原银行联系人：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="oldLoanPerson" name="oldLoanPerson"/></td>
								<td class="label_right1">原贷款联系电话：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="oldLoanPhone" name="oldLoanPhone"/></td>
						  	</tr>
							<tr>
								<td class="label_right1">新贷款银行：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="newBankStr" name="newBankStr"/></td>
								<td class="label_right1">新贷款金额：</td>
							 	<td><input type="text" class="text_style easyui-numberbox" readonly="readonly" id="newLoanMoney" name="newLoanMoney" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
						  	</tr>
							<tr>
							 	<td class="label_right1">新银行联系人：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="newLoanPerson" name="newLoanPerson"/></td>
								<td class="label_right1">新贷款联系电话：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="newLoanPhone" name="newLoanPhone"/></td>
						  	</tr>
						  	
							<tr>
							 	<td class="label_right1"><font color="red">*</font>新贷款申请人：</td>
							 	<td><input type="text" class="text_style easyui-textbox"  readonly="readonly" id="foreclosure_table_newRecePerson" name="newRecePerson"    /></td>
							 	<td class="label_right1"><font color="red">*</font>新贷款收款银行</br>(回款)：</td>
							 	<td><input type="text" class="text_style easyui-textbox"  readonly="readonly" id="foreclosure_table_newReceBank" name="newReceBank"/></td>
						  	</tr>
							<tr>
								<td class="label_right1">新贷款收款户名</br>(回款)：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="foreclosure_table_paymentName" name="paymentName"/></td>
								<td class="label_right1">新贷款收款账号</br>(回款)：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="foreclosure_table_paymentAccount" name="paymentAccount" style="width:200px;"/></td>
						  	</tr>
						  	
						  	
							<tr>
								<td class="label_right1">贷款方式：</td>
							 	<td>
						 			 <select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="paymentType" style="width:129px;">
										<option value="-1">--请选择--</option>
										<option value="1" <c:if test="${paymentType==1 }">selected </c:if>>按揭</option>
										<option value="2" <c:if test="${paymentType==2 }">selected </c:if>>组合贷</option>
										<option value="3" <c:if test="${paymentType==3 }">selected </c:if>>公积金贷</option>
										<option value="4" <c:if test="${paymentType==4 }">selected </c:if>>一次性付款</option>
										<option value="5" <c:if test="${paymentType==5 }">selected </c:if>>经营贷</option>
										<option value="6" <c:if test="${paymentType==6 }">selected </c:if>>消费款</option>
									</select>
							 	</td>
								<td class="label_right1">公积金银行：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="accumulationFundBankStr" name="accumulationFundBankStr"/></td>
						  	</tr>
							<tr>
								<td class="label_right1">公积金贷款金额：</td>
							 	<td><input type="text" class="text_style easyui-numberbox" readonly="readonly" id="accumulationFundMoney" name="accumulationFundMoney" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
								<td class="label_right1">监管单位：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="superviseDepartmentStr" name="superviseDepartmentStr"/></td>
						  	</tr>
							<tr>
								<td class="label_right1">资金监管金额：</td>
							 	<td><input type="text" class="text_style easyui-numberbox" readonly="readonly" id="fundsMoney" name="fundsMoney"  data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
								<td class="label_right1">委托公证日期：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="notarizationDate" name="notarizationDate"/></td>
						  	</tr>
						  </table>
					</form>
				</div>
				<!-- 物业信息 -->
				<div class="nearby" id="nearby_house_table" style="display: block">
					<table id="house_info_table" class="" fitColumns="true" style="width:750px;"
								     data-options="
								     	url: '',
								     	method: 'post',
								     	rownumbers: true,
							    		fitColumns:true,
								    	singleSelect: false,
									    pagination: false,
									    nowrap:false">
						<thead>
							<tr>
								<th data-options="field:'houseId',hidden:true" align="center" halign="center"></th>
								<th data-options="field:'projectId',hidden:true" align="center" halign="center"></th>
								<th data-options="field:'houseName',formatter:partnerGlobal.formatTooltip,width:80"  align="center" halign="center" >物业名称</th>
								<th data-options="field:'area',width:60"  align="center" halign="center" >面积(㎡)</th>
								<th data-options="field:'housePropertyCard',formatter:partnerGlobal.formatTooltip,width:70"  align="center" halign="center" >房产证号</th>
								<th data-options="field:'costMoney',formatter:formatMoney,width:70"  align="center" halign="center" >登记价(元)</th>
								<th data-options="field:'tranasctionMoney',formatter:formatMoney,width:70"  align="center" halign="center" >成交价(元)</th>
								<th data-options="field:'evaluationPrice',formatter:formatMoney,width:70"  align="center" halign="center" >评估价(元)</th>
								<th data-options="field:'evaluationNet',formatter:formatMoney,width:70"  align="center" halign="center" >评估净值(元)</th>
								<th data-options="field:'downPayment',formatter:formatMoney,width:70"  align="center" halign="center" >首期款(元)</th>
								<th data-options="field:'purchaseDeposit',formatter:formatMoney,width:70"  align="center" halign="center" >购房定金(元)</th>
								<th data-options="field:'purchaseBalance',formatter:formatMoney,width:70"  align="center" halign="center" >购房余款(元)</th>
								<th data-options="field:'propertyRatio',width:80,formatter:partnerGlobal.formatTooltip"  align="center" halign="center" >权属人占比</th>
								<th data-options="field:'houseAddress',width:80,formatter:partnerGlobal.formatTooltip"  align="center" halign="center" >地址</th>
							</tr>
						</thead>
					</table>  
				 
					 <form id="house_form" action="<%=basePath%>projectPartnerController/saveProjectPartner.action" method="POST">
						<table id="house_table" class="beforeloanTable">
							<tr>
								<td class="label_right1">物业名称：</td>
								<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="houseName" name="houseName"/></td>
								<td class="label_right1">赎楼成数：</td>
								<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="foreRate" name="foreRate"/>%</td>
							 </tr>
							 <tr>
							 	<td class="label_right1">总成交价：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="tranasctionMoney" name="tranasctionMoney"/></td>
								<td class="label_right1">总评估价格：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="evaluationPrice" name="evaluationPrice"/></td>
						  	 </tr>
							 <tr>
								<td class="label_right1">评估净值总价：</td>
								<td>
									 <input name="evaluationNet" readonly="readonly" id="house_form_evaluationNet"   class="easyui-numberbox"/>
								</td>
						  	 </tr>
							  <tr class="house_table_area_tr">
							  	<td class="label_right1" ><font id="house_province_code_font" color="red">*</font>物业省份：</td>
							     <td>
							     	<select id="house_province_code_house_form" name="houseProvinceCode" class="easyui-combobox" editable="false" style="width:129px;" />
							     </td>
							  	<td class="label_right1" ><font id="house_city_code_font" color="red">*</font>物业城市：</td>
							     <td>
							     	<select id="house_city_code_house_form" name="houseCityCode" class="easyui-combobox" editable="false" style="width:129px;" />
							     </td>
							  </tr>
							<tr>
								<td class="label_right1">业主姓名：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="sellerName" name="sellerName"/></td>
								<td class="label_right1">业主身份证号：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="sellerCardNo" name="sellerCardNo"/></td>
						  	</tr>
							<tr>
								<td class="label_right1">业主联系电话：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="sellerPhone" name="sellerPhone"/></td>
								<td class="label_right1">业主家庭住址：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="sellerAddress" name="sellerAddress"/></td>
						  	</tr>
							<tr>
								<td class="label_right1">买方名称：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="buyerName" name="buyerName"/></td>
								<td class="label_right1">买方身份证号：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="buyerCardNo" name="buyerCardNo"/></td>
						  	</tr>
							<tr>
								<td class="label_right1">买方联系电话：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="buyerPhone" name="buyerPhone"/></td>
								<td class="label_right1">买方家庭住址：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="buyerAddress" name="buyerAddress"/></td>
						  	</tr>
						  </table>
					</form> 
				</div>
				
				
			</div>
		</div>
		<!-- 提交审核页面结束 -->
	
		<!-- 审核记录页面开始 -->
		<div id="open_record_info">		
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#open_record').dialog('close')">关闭</a>
		</div>
		<div id="open_record" class="easyui-dialog" fitColumns="true"  title="合作项目审批记录"
		     style="width:850px;top:100px;padding:10px;" buttons="#open_record_info" closed="true" >
			 
			<section class="hearder" id="header">
			</section>
			 
			<div style="left:0;">
				<div class="nearby" style="display: block" >
					<table id="open_record_table" class="" fitColumns="true" style="width: auto;height:300px;"
								     data-options="
								     	url: '',
								     	method: 'post',
								     	rownumbers: true,
							    		fitColumns:true,
								    	singleSelect: false,
									    pagination: false">
						<thead>
							<tr>
								<th data-options="field:'projectNumber'" >项目编号</th>
								<th data-options="field:'projectName'" >项目名称</th>
								<th data-options="field:'notifyType',formatter:partnerGlobal.formatterNotifyType" >类型</th>
								<th data-options="field:'approvalStatus',formatter:partnerGlobal.formatterStatus" >审核状态</th>
								<th data-options="field:'approveMoney'" >审批金额</th>
								<th data-options="field:'approvalComment'" >审批意见</th>
								<th data-options="field:'submitApprovalTime'" >提交审核时间</th>
								<th data-options="field:'approvalTime'" >审批意见时间</th>
								<th data-options="field:'reApplyReason'" >复议内容</th>
							</tr>
						</thead>
					</table>  
				</div> 
			</div>
		</div>
		<!-- 审核记录页面结束 -->
		
		<!-- 文件上传开始 -->
		<div id="dialog_data"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons">
			<form id=uploadFileForm name="uploadFileForm" method="post" enctype="multipart/form-data">
				<!-- 项目id -->
				<input type="hidden" name="projectId" id="projectId_file">
				<input type="hidden" name="partnerId" id="partnerId_file">
				<input type="hidden" name="pid" id="pid_file">
				<table>
					<tr>
						<td width="160" align="right" height="28">项目名称：</td>
						<td><span id="projectName_file"></span></td>
					</tr>
					<tr>
						<td width="160" align="right" height="28"><font color="red">*</font>合作机构：</td>
						<td>
							<select id="partner_no" name="partnerNo" class="easyui-combobox" editable="false" style="width:129px;" />
						</td>
					</tr>
					<tr>
						<td width="160" align="right" height="28"><font color="red">*</font>附件类型：</td>
						<td>
							<select id="accessoryType" name="accessoryType" class="easyui-combobox" style="width:129px;">
								<option value="">请选择</option>
								<option value="1">客户基本信息</option>
								<option value="2">交易方基本信息</option>
								<option value="3">房产信息</option>
								<option value="4">交易信息</option>
								<option value="5">还款来源信息</option>
								<option value="6">征信信息</option>
								<option value="7">合同协议信息</option>
								<option value="9">居间服务协议</option>
								<option value="8">其他材料</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="160" align="right" height="28"><font id="accessoryChildTypeFont" color="red">*</font>附件子类型：</td>
						<td>
							<select id="accessoryChildType" name="accessoryChildType"  panelHeight="450" class="easyui-combobox" style="width:160px;">
								<option value="">请选择</option>
							</select>
						</td>
					</tr>
					<tr> 
						<td align="right" width="160" height="28"><font color="red">*</font>文件路径：</td>
						<td>
							<div class="uploadmutilFile" style="margin-top: 10px;">
						        <input type="file" name="uploadify" id="uploadify"/>
						    </div>
						</td>
						<td>
							<div class="uploadmutilFile">
						        <a href="javascript:$('#uploadify').uploadify('cancel','*')">取消上传</a>
						    </div>
						</td>
					</tr>
					<tr class="uploadmutilFile">
						<td width="160" align="right" height="28"></td>
						<td>
					        <%--用来作为文件队列区域--%>
					        <div id="fileQueue" style="max-height:200;overflow:scroll; right:50px; bottom:100px;z-index:999">
					        </div>
						</td>
					</tr>
					<tr>
						<td width="160" align="right" height="28">备注：</td>
						<td>
							<textarea id="remark_file" name="remark" rows="3" cols="30" maxlength="80"></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="data-buttons">
			<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="uploadPartnerFile()" >保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_data').dialog('close')">取消</a>
		</div>
		<!-- 文件上传结束 -->
		
		
		
		<!-- ---------------------------华安保险修改审核状态-begin--------------------------------------- -->
		<div id="updateApprovalStatus_data"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#updateApprovalStatus_btn">
			<form id="updateApprovalStatus_form" name="updateApprovalStatus_form" method="post" action="<%=basePath%>projectPartnerController/updateApprovalStatus.action" >
				<!-- 项目id -->
				<input type="hidden" name="pid" id="pid_updateApprovalStatus">
				<input type="hidden" name="partnerNo" id="partnerNo_updateApprovalStatus">
				<table>
					<tr>
						<td width="120" align="right" height="28">项目名称：</td>
						<td><span id="projectName_updateApprovalStatus"></span></td>
					</tr>
					<tr>
						<td width="120" align="right" height="28">合作机构：</td>
						<td><span id="partnerNoName_updateApprovalStatus"></span></td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>审核状态：</td>
						<td>
							<select id="approvalStatus_updateApprovalStatus" name="approvalStatus"  panelHeight="300" class="easyui-combobox" style="width:160px;">
								<option value="">请选择</option>
								<option value="2">通过</option>
								<option value="3">未通过</option>
							</select>
						</td>
					</tr>
				  <tr>
				  	<td class="label_right1">审批意见：</td>
					<td>
						<input id="approvalComment_updateApprovalStatus" name="approvalComment"  class="easyui-textbox" style="width:100%;height:60px" data-options="multiline:true,validType:'length[0,200]'">
					</td>
				  </tr>
					
				</table>
			</form>
		</div>
		<div id="updateApprovalStatus_btn">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="updateApprovalStatus()" >提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#updateApprovalStatus_data').dialog('close')">取消</a>
		</div>
		<!-- ---------------------------华安保险修改审核状态-end--------------------------------------- -->
		
		
		
	</div>
</body>

<script type="text/javascript">
	var BASE_PATH = "${basePath}";
</script>

<script type="text/javascript">
	function showPerBase(){
		var acctId = $("#acctId").val();
		jQuery.ajax({
			type:			'GET',
			url:			"<%=basePath%>projectPartnerController/editPerBaseInit.action?acctId="+acctId+"&flag=3",
			cache:			false,
			contentType:	'application/html; charset=utf-8',
			success: function(data){
				$("#nearby_cus_base").html(data);
			},error: function(data){
				console.log("加载失败");
			}
		});
	}
</script>
