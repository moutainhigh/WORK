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
	$(function(){	   
	   $(".order_tab > a").click(function(){
		  $(this).addClass("current").siblings().removeClass("current");  
		   var $i=$(this).index();
		   $(".nearby").eq($i).show().siblings(".nearby").hide();   
 
		   var loanStatus = $("#loanStatus").val();
		   var partnerNo = $("#partner_source_no").combobox('getValue');
		   if($(this).attr("id") == 'fileBtn1'){
			   $("#uploadFileBtn").show();
			   file_table_id = "project_file_table_1";
		   }else if($(this).attr("id") =='fileBtn2'){
			   $("#uploadFileBtn").show();
			   file_table_id = "project_file_table_2";
		   }else if($(this).attr("id") =='fileBtn3'){
			   $("#uploadFileBtn").show();
			   file_table_id = "project_file_table_3";
		   }else{
			   $("#uploadFileBtn").hide();
			   file_table_id="";
		   }
		   if(partnerNo == 'xiaoying' && !(loanStatus == 0 || loanStatus == 1 || loanStatus == 5 || loanStatus == 6)){
			   $("#uploadFileBtn").hide();
			   file_table_id="";
			}	
		   
		   //调整高度
		   var p_height = $("#partnerInfo").parent().height();
		   if(p_height > 0){
			   $('#nearby_project_file_table_1').height(p_height);
			   $('#nearby_project_file_table_2').height(p_height);
			   $('#nearby_project_file_table_3').height(p_height);
			   $('#nearby_public_man_table').height(p_height);
			   $('#nearby_workflow_table').height(p_height);
			   $('#nearby_foreclosure_table').height(p_height);
			   $('#nearby_house_table').height(p_height);
			   $('#nearby_notify_table').height(p_height);
		   }
		   
	   });
		  
	   $('#project_file_table_1').datagrid({onLoadSuccess : function(data){
			var rows = $('#project_file_table_1').datagrid('getRows');//获取当前页的数据行
			var requestFiles = $("#loanJusticeFiles").val();
			var loanStatus = $("#loanStatus").val();	//放款状态
			if(requestFiles != ''){
				var arr = requestFiles.split(",");
				for(var i = 0; i < arr.length; i++){
					for (var j = 0; j < rows.length; j++) {
						if(arr[i] == rows[j].pid){
							$('#project_file_table_1').datagrid('checkRow', j);
						}
					}
				}
				//放款驳回，需要增量补充材料
				if(loanStatus == 6){
					$("#project_file_table_1").parent().find('input:checked').each(function(index,el){
					     $(this).attr("disabled",true);
					});
				}
			}
		}}); 
	   $('#project_file_table_2').datagrid({onLoadSuccess : function(data){
			var rows = $('#project_file_table_2').datagrid('getRows');//获取当前页的数据行
			var requestFiles = $("#loanBlankFiles").val();
			var loanStatus = $("#loanStatus").val();	//放款状态
			if(requestFiles != ''){
				var arr = requestFiles.split(",");
				for(var i = 0; i < arr.length; i++){
					for (var j = 0; j < rows.length; j++) {
						if(arr[i] == rows[j].pid){
							$('#project_file_table_2').datagrid('checkRow', j);
						}
					}
				}
				//放款驳回，需要增量补充材料
				if(loanStatus == 6){
					$("#project_file_table_2").parent().find('input:checked').each(function(index,el){
					     $(this).attr("disabled",true);
					});
				}
			}
		}}); 
	   $('#project_file_table_3').datagrid({onLoadSuccess : function(data){
			var rows = $('#project_file_table_3').datagrid('getRows');//获取当前页的数据行
			var requestFiles = $("#loanOtherFiles").val();
			var loanStatus = $("#loanStatus").val();	//放款状态
			if(requestFiles != ''){
				var arr = requestFiles.split(",");
				for(var i = 0; i < arr.length; i++){
					for (var j = 0; j < rows.length; j++) {
						if(arr[i] == rows[j].pid){
							$('#project_file_table_3').datagrid('checkRow', j);
						}
					}
				}
				//放款驳回，需要增量补充材料
				if(loanStatus == 6){
					$("#project_file_table_3").parent().find('input:checked').each(function(index,el){
					     $(this).attr("disabled",true);
					});
				}
			}
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
	        'swf'       : '${ctx}/p/xlkfinance/plug-in/uploadify/uploadify.swf',  
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
		
	});
	
	//打开申请窗口
	function addItem(projectId,rowData) {
		
		$('#project_file_table_1').datagrid('loadData', { total: 0, rows: [] });  
		$('#project_file_table_2').datagrid('loadData', { total: 0, rows: [] });  
		$('#project_file_table_3').datagrid('loadData', { total: 0, rows: [] });  
		
		$("#info").click();
		setCombobox("cert_type","CERT_TYPE","");
		
		var pid = rowData.pid;
		var partnerNo = rowData.partnerNo;
		
		console.log("pid:"+pid);
		console.log("partnerNo:"+partnerNo);
		
		
   		//加载机构项目信息
   		loadProjectPartnerInfo(pid,projectId,partnerNo);
   		
   		
	}
	
	
	//加载机构项目信息
	function loadProjectPartnerInfo(pid,projectId,partnerNo){
		
		$('#partnerInfo').form('reset');
 		$('#foreclosure_form').form('reset');
 		$('#house_form').form('reset');
		
		$.ajax({
			type: "POST",
	        url: "<%=basePath%>projectPartnerController/editProjectPartner.action",
	        data:{"projectId":projectId,"pid":pid,"partnerNo":partnerNo},
	        dataType: "json",
	        success: function(row){
	        		console.log(row);
					$('#add_partner').dialog('open').dialog('setTitle', "合作项目放款申请");
					$('#partnerInfo').form('load', row);
					
					$('#partner_source_no').combobox('setValue', partnerNo);
					$("#partner_source_no").combobox("disable");
					
					changePartnerNoShow(partnerNo);
					
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
					if(row.publicManList !=null){
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
					setAreaCombobox("house_province_code_house_form","1","",row.houseProvinceCode == null ? "" : row.houseProvinceCode);
					setAreaCombobox("house_city_code_house_form","2",row.houseProvinceCode == null ? "" : row.houseProvinceCode,row.houseCityCode == null ? "" : row.houseCityCode);
					$("#house_province_code_house_form").combobox({ 
						onSelect:function(){
				        	var provinceCode = $("#house_province_code_house_form").combobox("getValue");
				        	if(provinceCode != ""){
				        		setAreaCombobox("house_city_code_house_form","2",provinceCode,"");
				    		}
				        }
					}); 
					
					//加载回调信息
					$('#notify_form').form('load', row);
					setNotifyFileDown("partnerLoanFile_div",row.partnerLoanFile);
					//设置字段为禁用
					setFileDisable(partnerNo,row.loanStatus);
					
					
					getProjectFile(projectId,1);
					getProjectFile(projectId,2);
					getProjectFile(projectId,3);
				}
		 });
		
		
	}
	
	
	//设置字段为禁用
	function setFileDisable(partnerNo,loanStatus){
		//放款中，放款成功为禁用
		if(partnerNo == 'xiaoying' && (loanStatus == 2 || loanStatus == 3 || loanStatus == 4)){
			$("#confirmLoanMoney").textbox('disable')	//确认放款金额     
			$("#confirmLoanDays").textbox('disable')	//确认借款天数 
		}else if(partnerNo == 'dianrongwang' && (loanStatus == 0 || loanStatus == 1 || loanStatus == 5 )){
		 	$("#applyLoanDate").datebox();
		}
	}
	
	
	// 设置并查询文件下载
	function setNotifyFileDown(divId,pid){
		//清空
		$("#"+divId).html("");
		
		if(pid != "" && pid > 0){
			$.ajax({
				type: "POST",
		        url: "<%=basePath%>projectPartnerController/getProjectFileByPid.action",
		        data:{"pid":pid},
		        dataType: "json",
		        success: function(row){
		        	if(row.pid != null && row.pid> 0){
						var downloadUrl = "<%=basePath%>beforeLoanController/downloadData.action?path="+row.fileUrl+"&fileName="+row.fileName;
						var projectDom = "<a href='"+downloadUrl+"'><font color='blue'>"+row.fileName+"下载</font></a>";
						$("#"+divId).html(projectDom);
		        	}
				}
			 });
		}
	}
	
	
	
	//改变合作机构显示不同字段
	function changePartnerNoShow(partnerNo){
		if(partnerNo == null){
			partnerNo=$('#partner_source_no').combobox('getValue');
		}
		if(partnerNo == "tlxt"){
			$(".partnerNoShowTr,#fileBtn4").show();
			$(".payBankShowTr,.partnerNoHnbxShowTr").hide();
			$(".confirmLoanReason_tr,.confirmLoanShowTr").hide();
			$("#fileBtn1,#fileBtn2,#fileBtn3").hide();
			$("#house_province_code_font,#house_city_code_font,.partnerNoNyyhShowTr").hide();
		}else if(partnerNo == "xiaoying"){
			$(".partnerNoShowTr,.partnerNoNyyhShowTr,.payBankShowTr,.partnerNoHnbxShowTr").hide();
			$("#fileBtn4").hide();
			$(".confirmLoanReason_tr,.confirmLoanShowTr").show();
			$("#fileBtn1,#fileBtn2,#fileBtn3").show();
			$("#house_province_code_font,#house_city_code_font").hide();
		}else if(partnerNo == "dianrongwang"){
			$(".partnerNoShowTr").show();
			$(".payBankShowTr").show();
			$(".confirmLoanReason_tr,.confirmLoanShowTr,.partnerNoNyyhShowTr,.partnerNoHnbxShowTr").hide();
			$("#fileBtn1,#fileBtn2,#fileBtn3,#fileBtn4").hide();
			$("#house_province_code_font,#house_city_code_font").show();
		}else if(partnerNo == "nyyh"){
			$(".partnerNoShowTr,.partnerNoNyyhShowTr").show();
			$(".payBankShowTr").hide();
			$(".confirmLoanReason_tr,.confirmLoanShowTr,.partnerNoHnbxShowTr").hide();
			$("#fileBtn1,#fileBtn2,#fileBtn3,#fileBtn4").hide();
			$("#house_province_code_font,#house_city_code_font").show();
		}else if(partnerNo == "hnbx"){
			$(".partnerNoShowTr,.payBankShowTr,.partnerNoHnbxShowTr").show();
			$("#house_province_code_font,#house_city_code_font").show();
			$(".partnerNoNyyhShowTr").hide();
			$("#fileBtn1,#fileBtn2,#fileBtn3,#fileBtn4").hide();
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
	
	//放款申请
	function sendLoan(){
		
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			
			//申请放款日期
			var applyLoanDate = $("#applyLoanDate").textbox('getValue');
			
			if(row[0].confirmLoanStatus != 3){
				$.messager.alert("操作提示","必须是确认要款通过才能申请放款","error");
				return ;
			}else if(!(row[0].loanStatus ==0 || row[0].loanStatus ==1 || row[0].loanStatus ==5 || row[0].loanStatus ==6 ) ){
				$.messager.alert("操作提示","放款状态必须是申请、放款失败、驳回才能操作","error");
				return ;
			}
			
			//判断统联放款日期
			var partnerNo=$('#partner_source_no').combobox('getValue');
			if(partnerNo == 'tlxt'){
				//审批放款日期
				var loanEffeDate =$("#loanEffeDate").val();
				
				if(loanEffeDate == null || loanEffeDate == ""){
					$.messager.alert("操作提示","放款有效期为空","error");
					return ;
				}
				loanEffeDate =(loanEffeDate +" 23:59:59").replace(/-/g,"/");
				if(new Date(loanEffeDate) < new Date()){
					$.messager.alert("操作提示","放款有效期己过期","error");
					return ;
				}
			}else if(partnerNo == 'xiaoying'){
			
				var confirmLoanMoney = $("#confirmLoanMoney").textbox('getValue')	//确认放款金额     
				var confirmLoanDays  = $("#confirmLoanDays").textbox('getValue')	//确认借款天数 
				var applyMoney  = $("#applyMoney").textbox('getValue')				//申请借款金额     
				
				console.log("confirmLoanMoney:"+confirmLoanMoney);
				console.log("applyMoney:"+applyMoney);
				
				if(confirmLoanDays == null || confirmLoanDays == ""){
					$.messager.alert("操作提示","确认借款天数为空","error");
					return ;
				}else if(!(confirmLoanDays >0)){
					$.messager.alert("操作提示","确认借款天数必须大于0","error");
					return ;
				}else if(confirmLoanMoney == null || confirmLoanMoney == ""){
					$.messager.alert("操作提示","确认放款金额为空","error");
					return ;
				}else if(!(confirmLoanMoney >0)){
					$.messager.alert("操作提示","确认放款金额 必须大于0","error");
					return ;
				}else if(parseFloat(confirmLoanMoney) > parseFloat(applyMoney)){
					$.messager.alert("操作提示","确认放款金额不能大于借款金额","error");
					return ;
				}
			}else if(partnerNo == 'dianrongwang'){
				//必须是当天，点荣网一通知即上标
				var date = new Date();
				var dayStr = date.getDate();
				var monthStr = date.getMonth()+1;
				
				if(dayStr <10){
					dayStr = "0"+dayStr;
				}
				if(monthStr <10){
					monthStr = "0"+monthStr;
				}
				
				var todayStr=date.getFullYear() + "-"+ monthStr +"-"+dayStr;
				
				console.log("applyLoanDate:"+applyLoanDate+"---");
				console.log("todayStr:"+todayStr+"---");
				
 				if(applyLoanDate != todayStr){
					$.messager.alert("操作提示","申请放款日期必须是今天","error");
					return ;
 				}
			}else if(partnerNo == 'nyyh'){
				var loanRemark  = $("#loanRemark").textbox('getValue')				//放款备注
				if(loanRemark == null || $.trim(loanRemark) == ''){
					$.messager.alert("操作提示","放款备注不能为空","error");
					return ;
				}
			}
			
			//==========================
			var rows_1 = $('#project_file_table_1').datagrid('getSelections');
			var rows_2 = $('#project_file_table_2').datagrid('getSelections');
			var rows_3 = $('#project_file_table_3').datagrid('getSelections');
			
			var loanJusticeFilesTemp =new Array();    
			var loanBlankFilesTemp = new Array(); 
			var loanOtherFilesTemp = new Array(); 
			loanJusticeFilesTemp = $("#loanJusticeFiles").val().split(",");
			loanBlankFilesTemp = $("#loanBlankFiles").val().split(",");
			loanOtherFilesTemp = $("#loanOtherFiles").val().split(",");
			
			console.log("loanJusticeFilesTemp:"+loanJusticeFilesTemp);
			console.log("loanBlankFilesTemp:"+loanBlankFilesTemp);
			console.log("loanOtherFilesTemp:"+loanOtherFilesTemp);
			
			//console.log("loanOtherFilesTemp:"+loanOtherFilesTemp.length);
			
			var loanJusticeFiles = "";			//公正信息文件
			var loanBlankFiles = "";			//卡片信息文件
			var loanOtherFiles = "";			//其他信息文件
			for (var i = 0; i < rows_1.length; i++) {
				//不存在
				//if(loanJusticeFilesTemp.indexOf(rows_1[i].pid+'') == -1){
					if (i == 0 || loanJusticeFiles == "") {
						loanJusticeFiles += rows_1[i].pid;
					} else {
						loanJusticeFiles += "," + rows_1[i].pid;
					}
				//}
			}
			for (var i = 0; i < rows_2.length; i++) {
				//if(loanBlankFilesTemp.indexOf(rows_2[i].pid+'') == -1){
					if (i == 0 || loanBlankFiles == "") {
						loanBlankFiles += rows_2[i].pid;
					} else {
						loanBlankFiles += "," + rows_2[i].pid;
					}
				//}
			}
			for (var i = 0; i < rows_3.length; i++) {
				//if(loanOtherFilesTemp.indexOf(rows_3[i].pid+'') == -1){
					if (i == 0 || loanOtherFiles == "") {
						loanOtherFiles += rows_3[i].pid;
					} else {
						loanOtherFiles += "," + rows_3[i].pid;
					}
				//}
			}
			
			$("#loanJusticeFiles").val(loanJusticeFiles);	
			$("#loanBlankFiles").val(loanBlankFiles);	
			$("#loanOtherFiles").val(loanOtherFiles);	
			
			console.log("loanJusticeFiles:"+loanJusticeFiles);
			console.log("loanBlankFiles:"+loanBlankFiles);
			console.log("loanOtherFiles:"+loanOtherFiles);
			
			$.messager.confirm("操作提示","确认发送放款申请?",
				function(r) {
		 			if(r){
						//提交时，解除禁用
						$("#partner_source_no").combobox("enable");
						$("#confirmLoanMoney").textbox('enable')	//确认放款金额     
						$("#confirmLoanDays").textbox('enable')	//确认借款天数 
						
		 				$('#partnerInfo').form('submit', {
							url : "sendLoan.action",
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
										$("#confirmLoanMoney").textbox('disable')	//确认放款金额     
										$("#confirmLoanDays").textbox('disable')	//确认借款天数 
										$.messager.alert('操作提示',ret.header["msg"],'error');	
									}
							},error : function(result){
								MaskUtil.unmask();
								$("#confirmLoanMoney").textbox('disable')	//确认放款金额     
								$("#confirmLoanDays").textbox('disable')	//确认借款天数 
								alert("操作失败！");
							}
						});
		 			}
				});
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	
	//申请初始化
	function sendLoanInit(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			addItem(row[0].projectId,row[0]);
			if(row[0].loanStatus == 4 || row[0].loanStatus == 3 || row[0].loanStatus == 2){
				$("#sendLoan_btn").hide();
			}else{
				$("#sendLoan_btn").show();
			}
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
		
	}

	
	//获取附件 1:公正信息 2:卡片信息 3:其它信息	
	function getProjectFile(projectId,type){
		
		var partnerNo = $("#partner_source_no").combobox("getValue");
		var partnerId = $("#pid").val();
		
		if(projectId=="" || projectId == null){
			projectId = $("#projectId").val()
		}
		
		var rows = $('#project_file_table_'+type).datagrid('getRows');//获取当前页的数据行
		
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
	//操作列格式化
	function detailInfo(value, rowData, index) {
		var projectDom ="";
		//上传资料
		if(rowData.partnerNo == 'xiaoying' && (rowData.loanStatus == 0 || rowData.loanStatus == 1 || rowData.loanStatus == 5 || rowData.loanStatus == 6)){
			//projectDom += "    <a href='javascript:void(0)' onclick='openUpload("+index+",\"add\")'><font color='blue'>上传材料</font></a>";
		}	
		if(rowData.partnerNo == 'hnbx' && (rowData.loanStatus == 0 || rowData.loanStatus == 1 )){
			projectDom += "<a href='javascript:void(0)' onclick='updateLoanStatusInit("+index+")'><font color='blue'>修改放款状态</font></a>";
		}
		return projectDom;
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
		
		var projectName = $("#projectName").textbox("getValue");
		var partnerNo = $("#partner_source_no").combobox("getValue");
		if(type == "add"){
			projectId = $("#projectId").val();
		}
		
		if(partnerNo == null ){
			$.messager.alert('操作提示','请选择合作机构再上传文件','error');	
			return;
		}
		
		$('#partner_no').combobox('setValue', partnerNo);
		
		$("#partner_no").combobox("disable");
	 	 if(partnerNo == "xiaoying" || partnerNo == 'dianrongwang'){
	 		 $("#accessoryChildTypeFont").hide();
	 	 }else if(partnerNo == "tlxt"){
	 		$("#accessoryChildTypeFont").show();
	 	 }
	 	 
		//设置合作项目id
		$("#partnerId_file").val($("#pid").val());
		
		$('#projectName_span_file').text(projectName);
		$('#projectId_file').val(projectId);
		$('#uploadFileForm').attr('action',"${basePath}projectPartnerController/uploadPartnerFile.action");
		$('#dialog_data').dialog('open').dialog('setTitle','上传材料');
	}
	
	//上传材料
	function uploadPartnerFile(){
		var projectId = $('#projectId_file').val();
		var accessoryType=$('#accessoryType').combobox('getValue');
		var accessoryChildType=$('#accessoryChildType').combobox('getValue');
		var partnerNo=$('#partner_no').combobox('getValue');
		var partnerId=$('#partnerId_file').val();
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
	function updteThirdLoanStatus(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var partnerNo = row[0].partnerNo;
			
			if("dianrongwang" != partnerNo ){
				$.messager.alert("操作提示","请选择合作机构为点荣网的数据","error");
				return;
			}
/* 			if(row[0].loanStatus == 4){
				$.messager.alert("操作提示","此项目己放款成功，请重新选择！","error");
				return;
			} */
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
	
	
	
	
	
	//修改放款状态初始化
	function updateLoanStatusInit(rowIndex){
		
		$('#updateLoanStatus_form').form('reset');
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
		
		$("#pid_updateLoanStatus").val(pid);
		$("#partnerNo_updateLoanStatus").val(partnerNo);
		$('#projectName_updateLoanStatus').text(projectName);
		$('#partnerNoName_updateLoanStatus').text(partnerNoName);
			
		$('#updateLoanStatus_data').dialog('open').dialog('setTitle','修改放款状态信息');
	}
	
	
	//修改放款状态信息
	function updateLoanStatus(){
		var loanStatus= $("#loanStatus_updateLoanStatus").combobox('getValue');
		var partnerLoanDate= $("#partnerLoanDate_updateLoanStatus").textbox('getValue');
		
		
		if(loanStatus == ''){
			$.messager.alert("操作提示","请选择放款状态","error");
			return ;
		}else if(partnerLoanDate == ''){
			$.messager.alert("操作提示","请选择资方放款时间","error");
			return ;
		}
		
		$.messager.confirm("操作提示","确认修改放款状态信息?",
			function(r) {
	 			if(r){
	 				$('#updateLoanStatus_form').form('submit', {
						url : "updateLoanStatus.action",
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
			 					$('#updateLoanStatus_data').dialog('close');
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
			<form action="loanInfoList.action" method="post" id="searchFrom">
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
							<input class="easyui-textbox" name="projectName" style="width:150px;" />
						</td>
						<td width="80" align="right" height="25">合作机构：</td>
						<td>
							<select class="easyui-combobox" id="queryPartnerNo"  name="partnerNo" style="width:120px" panelHeight="auto">
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
						
						<td width="80" align="right" height="25">放款状态 ：</td>
						<td>
							<select class="easyui-combobox" id="query_loanStatus" name="loanStatus" style="width:120px" panelHeight="auto">
								<option value='0' >--请选择--</option>
								<option value='1' >未申请</option>
							    <option value='2' >申请中</option>
								<option value='3' >放款中</option>
								<option value='4' >放款成功</option>
								<option value='5' >放款失败</option>
								<option value='6' >驳回</option>
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
			
				<shiro:hasAnyRoles name="LOAN_INDEX,ALL_BUSINESS">
				   		<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-add" plain="true" onclick="sendLoanInit()">放款申请</a>
				  </shiro:hasAnyRoles>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="updteThirdLoanStatus()">同步状态(点荣网)</a>
			</div>
		</div>
		<div class="perListDiv" style="height:100%;">
			<table id="grid" title="放款申请" class="easyui-datagrid"
				style="height:100%; width: auto;"
				data-options="
				    url: 'loanInfoList.action',
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
				    fitColumns:true" >
				    
				<!--
				 表头 -->
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true"></th>
						<th data-options="field:'projectId',hidden:true"></th>
						<th data-options="field:'confirmLoanStatus',hidden:true"></th>
						<th data-options="field:'projectNumber',width:80,sortable:true"  align="center" halign="center">项目编号</th>
						<th data-options="field:'projectName',width:80,sortable:true" align="center" halign="center">项目名称</th>
						<th data-options="field:'projectSource',formatter:partnerGlobal.formatProjectSource,width:80,sortable:true" align="center" halign="center">项目来源</th>
						<th data-options="field:'businessCategoryStr',width:80,sortable:true" align="center" halign="center">业务类型</th>
						<th data-options="field:'partnerNo',formatter:partnerGlobal.partnerFormat,width:80,sortable:true" align="center" halign="center">合作机构</th>
						<th data-options="field:'loanAmount',width:80,sortable:true" align="center" halign="center">借款金额</th>
						<th data-options="field:'approveMoney',width:80,sortable:true" align="center" halign="center">审批金额</th>
						<th data-options="field:'loanId',width:80,sortable:true" align="center" halign="center">审查编号</th>
						<th data-options="field:'loanStatus',formatter:partnerGlobal.formatLoanState,width:80,sortable:true" align="center" halign="center">放款状态</th>
						<th data-options="field:'loanRemark',width:80,sortable:true" align="center" halign="center">放款备注</th>
						<th data-options="field:'loanTime',width:80,sortable:true" align="center" halign="center">请求放款时间</th>
						<th data-options="field:'loanResultTime',width:80,sortable:true" align="center" halign="center">放款结果时间</th>
						<th data-options="field:'partnerLoanDate',width:80,sortable:true" align="center" halign="center">资方放款时间</th>
						<th data-options="field:'loanEffeDate',width:80,sortable:true" align="center" halign="center">放款有效期</th>
						<th data-options="field:'cz1',formatter:detailInfo,width:80,sortable:true" align="center" halign="center">操作</th>
					</tr>
				</thead>
			</table>	
		</div>
		
		<!-- 弹层基本信息 -->
		<div id="add_partner_info">		
		    <a href="javascript:void(0)" id="sendLoan_btn" class="easyui-linkbutton" iconCls="icon-save" onclick="sendLoan()">提交申请</a>
			<a id="uploadFileBtn" style="display: none;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="openUpload('','add');">上传资料</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#add_partner').dialog('close')">关闭</a>
		</div>
		<div id="add_partner" class="easyui-dialog" fitColumns="true"  title="合作项目放款申请"
		     style="width:850px;top:100px;padding:10px;" buttons="#add_partner_info" closed="true" >
			
			<section class="hearder" id="header">
			    <section class="info_top info_top_auto">
				        <div class="order_tab">
				        	<a href="#" id="info" class="first current">基本信息</a>
				        	<a href="#" onclick="showPerBase()" class="last">客户附加信息</a>
				        	<a href="#" id="fileBtn1" onclick="getProjectFile('',1)" class="last">公正信息</a>
				        	<a href="#" id="fileBtn2" onclick="getProjectFile('',2)" class="last">卡片信息</a>
				        	<a href="#" id="fileBtn3" onclick="getProjectFile('',3)" class="last">其它信息</a>
				        	<a href="#"  onclick="getPublicManList()" class="last">共同借款人</a>
				        	<a href="#"  onclick="getWorkflowList()" class="last">审批记录</a>
				        	<a href="#" onclick="getOriginalLoan()"  class="last">赎楼信息</a>
				        	<a href="#"  onclick="getHouseInfo()" class="last">物业信息</a>
				        	<a href="#" id="fileBtn4" class="last">回调信息</a>
				        </div>
				</section>
			</section>
		
			<div style="left:0;">
				<!-- 基本信息 -->
				<div class="nearby" style="display: block">
					<form id="partnerInfo" action="<%=basePath%>projectPartnerController/saveProjectPartner.action" method="POST">
						<table class="beforeloanTable">
							<input type="hidden" name="pid" id="pid"/>
							<input type="hidden" name="projectId" id="projectId"/>
							<input type="hidden" name="businessType"/>
							<input type="hidden" name="requestFiles" id="requestFiles"/>
							<input type="hidden" name="pmUserId"/>
							<input type="hidden" name="city" value="深圳"/>
							
							<input type="hidden" id="loanId" name="loanId"/>
							<!-- 公正信息文件-->
							<input type="hidden" name="loanJusticeFiles" id="loanJusticeFiles"/>
							<!-- 公正信息文件 -->
							<input type="hidden" name="loanBlankFiles" id="loanBlankFiles"/>
							<!-- 公正其他文件 -->
							<input type="hidden" name="loanOtherFiles" id="loanOtherFiles"/>
							<!-- 放款状态 -->
							<input type="hidden" name="loanStatus" id="loanStatus"/>
							<!-- 放款审核时间 -->
							<input type="hidden" name="loanEffeDate" id="loanEffeDate"/>
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
						  
						  <tr class="confirmLoanShowTr" style="display: none;">
						  	<td class="label_right1" ><font color="red">*</font>确认借款天数：</td>
						    <td>
						    	<input type="text" id="confirmLoanDays" name="confirmLoanDays" editable="false" class="text_style easyui-textbox"   />
						    </td>
						  	<td class="label_right1"><font color="red">*</font>确认借款金额：</td>
						 	<td>
						 		<input type="text" id="confirmLoanMoney" name="confirmLoanMoney" editable="false" class="text_style easyui-textbox"/>
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
						  <tr class="partnerNoShowTr" style="display: none;">
						  	<td class="label_right1" ><font color="red">*</font><font class="paymentFontStr">借款人银行：</font></td>
						     <td >
						     	<select id="payment_bank" name="paymentBank" class="easyui-combobox" disabled="disabled" style="width:129px;" />
						     </td>
						  	<td class="label_right1"><font color="red">*</font><font class="paymentFontStr">借款人户名：</font></td>
							 <td><input type="text"  class="text_style easyui-textbox" name="paymentAcctName" id="payment_acct_name" editable="false" data-options="required:true"/></td>
						  	<td class="label_right1" ><font color="red">*</font><font class="paymentFontStr">借款人账号：</font></td>
						     <td>
						     	<input type="text" class="easyui-textbox" name="paymentAcctNo" id="payment_acct_no" editable="false" data-options="required:true"/>
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
						  	<td class="label_right1" style="width:160px;" ><font color="red">*</font><font class="payFontStr">打款银行(公司)：</font></td>
						     <td>
						     	<select id="pay_bank_name" name="payBankName" disabled="disabled" class="easyui-combobox"   style="width:129px;" />
						     </td>
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行支行：</font></td>
						     <td>
						     	<input id="pay_bank_branch" name="payBankBranch" editable="false" class="easyui-textbox"   />
						     </td>
						  </tr>
						  <tr  class="payBankShowTr" style="display: none;">
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行开户名：</font></td>
						     <td>
						     	<input id="pay_acct_name" name="payAcctName" editable="false" class="easyui-textbox"   />
						     </td>
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行帐号：</font></td>
						     <td>
						     	<input id="pay_acct_no" name="payAcctNo" editable="false" class="easyui-textbox"/>
						     </td>
						   </tr>
						   <tr class="payBankShowTr" style="display: none;">
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行省份：</font></td>
						     <td>
						     	<select id="pay_province_code" name="payProvinceCode" class="easyui-combobox" disabled="disabled" style="width:129px;" />
						     </td>
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行城市：</font></td>
						     <td>
						     	<select id="pay_city_code" name="payCityCode" class="easyui-combobox" disabled="disabled" style="width:129px;" />
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
						  </tr>
						   <td class="label_right1">备注：</td>
							<td>
								<input name="remark" id="remark" class="easyui-textbox" style="width:100%;height:50px" data-options="multiline:true,validType:'length[0,200]'">
							</td>
						   <td class="label_right1"><font color="red" id="reApplyReasonFont" style="display: none;">*</font>复议理由：</td>
							<td >
								<input name="reApplyReason" id="reApplyReason" class="easyui-textbox" style="width:100%;height:50px" data-options="multiline:true,validType:'length[0,200]'">
							</td>
							<td class="label_right1">操作人：</td>
							 <td><input type="text" id="cert_Number" class="text_style easyui-textbox" readonly="readonly" name="pmUserName"/></td>
						  </tr>
						  <tr class="partnerNoHideTr" id="confirmLoanReason_tr" style="display: none;">
						   <td class="label_right1"><font color="red">*</font>确认要款理由：</td>
							<td colspan="3"><input name="confirmLoanReason" id="confirmLoanReason" class="easyui-textbox" style="width:65%;height:30px" data-options="multiline:true,validType:'length[0,200]'"></td>
						  </tr>
						  <tr>
						   <td class="label_right1"><font color="red">*</font>放款备注：</td>
							<td colspan="6"><input name="loanRemark" id="loanRemark" class="easyui-textbox" style="width:85%;height:50px" data-options="multiline:true,validType:'length[0,80]'"></td>
						  </tr>
						 </table> 
					</form>	  
				</div>
				
				<!-- 加载客户附加信息 -->
				<div class="nearby" id="nearby_cus_base" style="display: block">
				</div>
			     
			    <!-- 附件1 -->
				<div class="nearby" id="nearby_project_file_table_1">
					<table id="project_file_table_1" class="" fitColumns="true" style="width:790px;height:215px;"
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
			    <!-- 附件2 -->
				<div class="nearby" id="nearby_project_file_table_2" style="display: block">
					<table id="project_file_table_2" class="" fitColumns="true" style="width:790px;height:215px;"
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
			    <!-- 附件3 -->
				<div class="nearby" id="nearby_project_file_table_3" style="display: block">
					<table id="project_file_table_3" class="" fitColumns="true" style="width:790px;height:215px;"
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
				<div class="nearby"  id="nearby_workflow_table" style="display: block">
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
				<!-- 回调信息-->
				<div class="nearby" id="nearby_notify_table" style="display: block">
					<form id="notify_form" action="<%=basePath%>projectPartnerController/saveProjectPartner.action" method="POST">
						<table id="notify_table" class="beforeloanTable">
							<tr>
								<td class="label_right1">放款日期：</td>
								<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="partnerLoanDate" name="partnerLoanDate"/></td>
								<td class="label_right1">放款凭证：</td>
								<td>
									<div id="partnerLoanFile_div"></div>	
								</td>
							 </tr>
							<tr>
							 	<td class="label_right1">放款备注：</td>
							 	<td colspan="3">
							 		<input name="loanRemark"    readonly="readonly"  class="easyui-textbox" style="width:100%;height:60px" data-options="multiline:true,validType:'length[0,80]'">
							 	</td>
						  	</tr>
						  </table>
					</form>
				</div>
				 
				 
			</div>
		</div>
		
		
		<!-- 文件上传开始 -->
		<div id="dialog_data"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons">
			<form id=uploadFileForm name="uploadFileForm" method="post" enctype="multipart/form-data">
				<!-- 项目id -->
				<input type="hidden" name="projectId" id="projectId_file">
				<input type="hidden" name="pid" id="pid_file">
				<input type="hidden" name="projectName" id="projectName_file">
				<input type="hidden" name="partnerId" id="partnerId_file">
				<table>
					<tr>
						<td width="160" align="right" height="28">项目名称：</td>
						<td><span id="projectName_span_file"></span></td>
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
<!-- 								<option value="1">客户基本信息</option>
								<option value="2">交易方基本信息</option>
								<option value="3">房产信息</option>
								<option value="4">交易信息</option>
								<option value="5">还款来源信息</option>
								<option value="6">征信信息</option>
								<option value="7">合同协议信息</option>
								<option value="9">居间服务协议</option>
								<option value="8">其他材料</option> -->
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
		
		
		<!-- ---------------------------华安保险修改放款状态-begin--------------------------------------- -->
		<div id="updateLoanStatus_data"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#updateLoanStatus_btn">
			<form id="updateLoanStatus_form" name="updateLoanStatus_form" method="post" action="<%=basePath%>projectPartnerController/updateLoanStatus.action" >
				<!-- 项目id -->
				<input type="hidden" name="pid" id="pid_updateLoanStatus">
				<input type="hidden" name="partnerNo" id="partnerNo_updateLoanStatus">
				<table>
					<tr>
						<td width="120" align="right" height="28">项目名称：</td>
						<td><span id="projectName_updateLoanStatus"></span></td>
					</tr>
					<tr>
						<td width="120" align="right" height="28">合作机构：</td>
						<td><span id="partnerNoName_updateLoanStatus"></span></td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>放款状态：</td>
						<td>
							<select id="loanStatus_updateLoanStatus" name="loanStatus"    class="easyui-combobox" style="width:160px;">
								<option value="">请选择</option>
								<option value="4">放款成功</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>资方放款时间：</td>
						<td>
							<input id="partnerLoanDate_updateLoanStatus" name="partnerLoanDate" required="true"  data-options="required:true" class="easyui-datebox" editable="false"  />
						</td>
					</tr>
					<tr>
						<td class="label_right1">放款备注：</td>
						<td>
							<input id="loanRemark_updateLoanStatus" name="loanRemark"  class="easyui-textbox" style="width:100%;height:60px" data-options="multiline:true,validType:'length[0,80]'">
						</td>
					 </tr>
				</table>
			</form>
		</div>
		<div id="updateLoanStatus_btn">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="updateLoanStatus()" >提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#updateLoanStatus_data').dialog('close')">取消</a>
		</div>
		<!-- ---------------------------华安保险修改放款状态-end--------------------------------------- -->
		
		
		
		
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