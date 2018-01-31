<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style>
.info_top {
	width: 100%;padding: 5px 0;height: auto;overflow: hidden;
}
.info_top_auto {color: #333;
	font-weight: normal;
	text-shadow: 0px;
	position: relative;
	padding: 8px 0;
	height: auto;
	overflow: hidden;
	text-align: center;
}

.order_tab {
	width: 100%;
	text-align: left;
	border-bottom: 1px #ddd solid;
}

.order_tab a {
	display: inline-block;
	width: 12%;
	color: #666;
	text-align: center;
	padding: 5px 0;
	font-size: 16px;
	height: 28px;
	margin-left: 10px;
	line-height: 28px;
	border: 1px #ddd solid;
	border-bottom: none;
	text-decoration: none;
}

.order_tab .first {
	border-radius: 5px 5px 0 0;
	position: relative;
}

.order_tab .last {
	border-radius: 5px 5px 0 0;
	margin-left: 5px;
}

.order_tab .current {
	background-color: #0079c3;
	color: #fff;
	border: 1px #0079c3 solid;
	border-bottom: none;
}

.nearby {
	display: none;
	height: auto;
	overflow: scroll;
	margin-top: 10px;
	padding: 10px;
}

/**多图上传按钮*/
.uploadify-button {
	/* background-color: #505050; */
	background-image: linear-gradient(bottom, #505050 0%, #707070 100%);
	background-image: -o-linear-gradient(bottom, #505050 0%, #707070 100%);
	background-image: -moz-linear-gradient(bottom, #505050 0%, #707070 100%);
	/* background-image: -webkit-linear-gradient(bottom, #505050 0%, #707070 100%); */
	background-image: -ms-linear-gradient(bottom, #505050 0%, #707070 100%);
	background-image: -webkit-gradient(linear, left bottom, left top, color-stop(0, #505050),
		color-stop(1, #707070));
	background-position: center top;
	background-repeat: no-repeat;
	-webkit-border-radius: 30px;
	-moz-border-radius: 30px;
	border-radius: 0px;
	border: 2px solid #808080;
	color: #FFF;
	font: bold 12px Arial, Helvetica, sans-serif;
	text-align: center;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	width: 100%;
}



<!--多资方申请按钮样式 -->
#partner_request_div {
	padding: 10px;
	width: 120px;
	border: 1px #ddd solid;
	border-radius: 5px;
	background: #fff;
	position: absolute;
	top: 10px;
	right: 10px;
}
#partnerNoRequestDiv {
	BORDER-RIGHT: #f7ce34 1px solid;
	PADDING-RIGHT: 10px;
	BORDER-TOP: #f7ce34 1px solid;
	PADDING-LEFT: 10px;
	Z-INDEX: 100000000;
	BACKGROUND: #f1f2f4;
	PADDING-BOTTOM: 10px;
	BORDER-LEFT: #f7ce34 1px solid;
	WIDTH: 70px;
	COLOR: #000;
	PADDING-TOP: 10px;
	BORDER-BOTTOM: #f7ce34 1px solid;
	POSITION: absolute;
	TEXT-ALIGN: left;
	left: 20px;
}
.partnerNoRequest_box a {
	line-height: 24px;
	display: block;
}
</style>


<script type="text/javascript" src="${ctx}/p/xlkfinance/module/partner/partner.js?t=20171227"></script> 
<script type="text/javascript">


	$(function(){	   
	   $(".order_tab > a").click(function(){
		  $(this).addClass("current").siblings().removeClass("current");  
		   var $i=$(this).index();
		   $(".nearby").eq($i).show().siblings(".nearby").hide();   
		   
		   if($(this).attr("id") == 'getProjectFile_btn'){	
			   $("#uploadFileBtn").show();
			   
			   //隐藏和显示南粤银行上传按钮
			   var partnerNo=$('#partner_source_no').combobox('getValue');
			   if(partnerNo == 'nyyh'){
				   $("#uploadFileBatchBtn").show();
			   }else{
				   $("#uploadFileBatchBtn").hide();
			   }
			   if(partnerNo == 'hnbx'){
				   $("#uploadFileBatchBtn_hnbx").show();
			   }else{
				   $("#uploadFileBatchBtn_hnbx").hide();
			   }
			   
			   
		   }else{
			   $("#uploadFileBtn").hide();
			   $("#uploadFileBatchBtn").hide();
			   $("#uploadFileBatchBtn_hnbx").hide();
		   }
		   
		   //调整高度
		   var p_height = $("#nearby_project").height();
		   if(p_height > 0){
			   $("#nearby_project_file_table").height(p_height);
			   $('#nearby_public_man_table').height(p_height);
			   $('#nearby_workflow_table').height(p_height);
			   $('#nearby_foreclosure_table').height(p_height);
			   $('#nearby_house_table').height(p_height);
			   $('#nearby_cus_base').height(p_height);
		   }
	   });
	  
 	   $('#project_file_table').datagrid({onLoadSuccess : function(data){
			var rows = $('#project_file_table').datagrid('getRows');//获取当前页的数据行
			var requestFiles = $("#requestFiles").val();
			if(requestFiles != ''){
				var arr = requestFiles.split(",");
				for(var i = 0; i < arr.length; i++){
					for (var j = 0; j < rows.length; j++) {
						if(arr[i] == rows[j].pid){
							$('#project_file_table').datagrid('checkRow', j);
						}
					}
				}
			}
		}});  
 	   $('#workflow_table').datagrid({onLoadSuccess : function(){
		}});  
 	   $('#public_man_table').datagrid({onLoadSuccess : function(){
		}});  
 	   $('#house_info_table').datagrid({onLoadSuccess : function(){
 		   
 		   //更新评估净值
 			var rows = $('#house_info_table').datagrid('getRows');
 			// 物业信息
 			var evaluationNet = 0 ;
 			if(rows.length > 0){
 				for(var i=0;i<rows.length;i++){
 			  		var row = rows[i];
 			  		evaluationNet += row.evaluationNet;
 			  	}
 			}
 			$("#house_form_evaluationNet").numberbox('setValue',evaluationNet);//评估净值
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
		
		//机构推送帐号
		$('#partnerPushAccount').combobox({         
			data:partnerGlobal.partnerPushAccountJson, //此为重点
	        valueField:'pid',
	        textField:'lookupDesc',
	        //如果需要设置多项隐含值，可使用以下代码
	        onLoadSuccess:function(){
	         },
			onSelect:function(){  
				//获取执行利率
				getPartnerGrossRate();
	       }
	    });
		
		
		//多文件上传  //兼容火狐 需要  sessionid，否则拦截登陆302
		var uploadFileCount = 0 ;
	    $("#uploadify").uploadify({  
	        //'swf'       : '${ctx}/p/xlkfinance/plug-in/uploadify/uploadify.swf',  
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
	       // 'scriptAccess'       : 'sameDomain',
	        'method'       : 'get',
	        onSelect :function(file) {  
	        	console.log(file);
	        	console.log("onSelect");
	        	console.log(file.size/1024);
	        	var partnerNo=$('#partner_source_no').combobox('getValue');
				if(partnerNo == 'nyyh'){
					if(file.size/1024 > 300){
						$.messager.alert("操作提示", "文件不能超过300kb");
						$("#uploadify").uploadify("cancel",file.id);//将上传队列中的文件删除.
					}
				}        	
	        },  
			onUploadSuccess : function(file, data, response) {
				 console.log("onUploadSuccess:"+data);
				 uploadFileCount++;
			},
			onQueueComplete : function(file) { //当队列中的所有文件全部完成上传时触发 
				console.info(file);
				$.messager.alert("操作提示", "上传完成"+uploadFileCount+"个文件");
				uploadFileCount = 0;
				// 重新加载
				$("#project_file_table").datagrid('load');
				//$('#dialog_data').dialog('close');
			}
		});

	});
	
	/**打开申请窗口*/
	function openDiv(pid,projectId,partnerNo) {
		$("#info").click();
		$('#project_file_table').datagrid('loadData', {
			total : 0,
			rows : []
		});

		//加载文件
		getProjectFile(projectId,pid,partnerNo);
		//身份证类型		
		setCombobox("cert_type", "CERT_TYPE", "");
		
   		//加载机构项目信息
   		loadProjectPartnerInfo(pid,projectId,partnerNo);
   		
   		$('#partner_source_no').combobox({         
   			data:partnerGlobal.partnerJson, //此为重点
   	        valueField:'pid',
   	        textField:'lookupDesc',
   	        value:partnerNo,
   	        //如果需要设置多项隐含值，可使用以下代码
   	        onLoadSuccess:function(){
   	        	changePartnerNoShow(partnerNo);
   	        	loadSystemBankConfig(partnerNo);
   	        },onSelect:function(){  
   	        	changePartnerNoShow();
   	        	loadSystemBankConfig();
	       }
   	    });

	}
	
	//加载机构项目信息
	function loadProjectPartnerInfo(pid,projectId,partnerNo){
		console.log("-----------loadProjectPartnerInfo begin-----------------");
		$('#partnerInfo').form('reset');
 		$('#foreclosure_form').form('reset');
 		$('#house_form').form('reset');
		$.ajax({
			type : "POST",
			url : "<%=basePath%>projectPartnerController/editProjectPartner.action",
	        data:{"projectId":projectId,"pid":pid,"partnerNo":partnerNo},
	        dataType: "json",
	        async: false,
	        success: function(row){
	        		console.log(row);
					$('#add_partner').dialog('open').dialog('setTitle', "合作项目提交审核");
					$('#partnerInfo').form('load', row);
					
					if(row.partnerNo != null || row.partnerNo != ""){
						partnerNo = row.partnerNo;	
					}
					                         
					//setCombobox3("partner_source_no","PARTNER_NAME",partnerNo);
					setCombobox("sex","SEX",row.sex);
					setBankCombobox2("payment_bank",0,row.paymentBank == null ? "--请选择--" : row.paymentBank);
					
					setAreaCombobox("province_code","1","",row.provinceCode == null ? "440000" : row.provinceCode);
					setAreaCombobox("city_code","2",row.provinceCode == null ? "440000" : row.provinceCode,row.cityCode == null ? "440300" : row.cityCode);
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
					setAreaCombobox("pay_province_code","1","",row.payProvinceCode == null ? "440000" : row.payProvinceCode);
					setAreaCombobox("pay_city_code","2",row.payProvinceCode == null ? "440000" : row.payProvinceCode,row.payCityCode == null ? "440300" : row.payCityCode);
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
					
					console.log(row.projectForeclosure);
					
					//加载赎楼信息
					if(row.projectForeclosure != null){
						$('#foreclosure_form').form('load', row.projectForeclosure);
						//根据项目来源，万通可编辑新贷款收款银行
						if(row.projectSource ==  1){
							//万通
							$("#foreclosure_table_newReceBank").textbox({disabled:false}) ;
														
						}else{
							$("#foreclosure_table_newReceBank").textbox({disabled:true}) ;
						}
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
		console.log("-----------loadProjectPartnerInfo end-----------------");
	}
	
	
	
	


	//打开申请窗口
 <%-- 	function addItem(projectId) {
		$('#project_file_table').datagrid('loadData', {
			total : 0,
			rows : []
		});
		getProjectFile(projectId);

		$("#info").click();
		setCombobox("cert_type", "CERT_TYPE", "");

		$.ajax({
			type : "POST",
			url : "<%=basePath%>projectPartnerController/editProjectPartner.action",
	        data:{"projectId":projectId},
	        dataType: "json",
	        success: function(row){
					$('#partnerInfo').form('reset');
					$('#add_partner').dialog('open').dialog('setTitle', "合作项目提交审核");
					$('#partnerInfo').form('load', row);
					var partnerNo = row.partnerNo;
					if(partnerNo == null || partnerNo == ""){
						partnerNo = "";	
					}
					                         
               		$('#partner_source_no').combobox({         
               			data:partnerGlobal.partnerJson, //此为重点
               	        valueField:'pid',
               	        textField:'lookupDesc',
               	        value:partnerNo,
               	        //如果需要设置多项隐含值，可使用以下代码
               	        onLoadSuccess:function(){
               	        	changePartnerNoShow(partnerNo);
               	        },onSelect:function(){  
               	        	changePartnerNoShow();
            	       }
               	    });
					                         
					//setCombobox3("partner_source_no","PARTNER_NAME",partnerNo);
					setCombobox("sex","SEX",row.sex);
					setBankCombobox2("payment_bank",0,row.paymentBank == null ? "--请选择--" : row.paymentBank);
					
					setAreaCombobox("province_code","1","",row.provinceCode == null ? "440000" : row.provinceCode);
					setAreaCombobox("city_code","2",row.provinceCode == null ? "440000" : row.provinceCode,row.cityCode == null ? "440300" : row.cityCode);
					$("#province_code").combobox({ 
						onSelect:function(){
				        	var provinceCode = $("#province_code").combobox("getValue");
				        	if(provinceCode != ""){
				        		setAreaCombobox("city_code","2",provinceCode,"");
				    		}
				        }
					}); 
					console.info("row.payProvinceCode:"+row.payProvinceCode);
					console.info("row.payCityCode:"+row.payCityCode);
					console.info("row.provinceCode:"+row.provinceCode);
					console.info("row.cityCode:"+row.cityCode);
					
					//打款银行
					setBankCombobox2("pay_bank_name",0,row.payBankName == null ? "--请选择--" : row.payBankName);
					setAreaCombobox("pay_province_code","1","",row.payProvinceCode == null ? "440000" : row.payProvinceCode);
					setAreaCombobox("pay_city_code","2",row.payProvinceCode == null ? "440000" : row.payProvinceCode,row.payCityCode == null ? "440300" : row.payCityCode);
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
					
					console.log(row.projectForeclosure);
					
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
	} --%>
	  
	
	//改变合作机构显示不同字段
	function changePartnerNoShow(partnerNo){
		if(partnerNo == null){
			partnerNo=$('#partner_source_no').combobox('getValue');
		}
		console.log(partnerNo);
		if(partnerNo == "tlxt"){
			$(".partnerNoShowTr").show();
			$(".payBankShowTr,.partnerNoHnbxShowTr").hide();
			$("#house_province_code_font,#house_city_code_font,.partnerNoNyyhShowTr").hide();
			$("#applyLoanDateShowFont").show();
			//查询征信按钮
			$("#btn_searchCusCredit").hide();
			$(".partnerNoNyyhShowTd_openAccount").hide();
		}else if(partnerNo == "xiaoying"){
			$(".partnerNoShowTr").hide();
			$(".payBankShowTr,.partnerNoHnbxShowTr").hide();
			$("#house_province_code_font,#house_city_code_font,.partnerNoNyyhShowTr").hide();
			$("#applyLoanDateShowFont").show();
			//查询征信按钮
			$("#btn_searchCusCredit").hide();
			$(".partnerNoNyyhShowTd_openAccount").hide();
		}else if(partnerNo == "dianrongwang"){
			$(".partnerNoShowTr,.payBankShowTr").show();
			$("#house_province_code_font,#house_city_code_font").show();
			$("#applyLoanDateShowFont,.partnerNoNyyhShowTr,.partnerNoHnbxShowTr").hide();
			//查询征信按钮
			$("#btn_searchCusCredit").hide();
			$(".partnerNoNyyhShowTd_openAccount").hide();
		}else if(partnerNo == "nyyh"){
			$(".partnerNoShowTr,.payBankShowTr,.partnerNoNyyhShowTr").show();
			$("#house_province_code_font,#house_city_code_font").show();
			$("#applyLoanDateShowFont").hide();
			$(".payBankShowTr,.partnerNoHnbxShowTr").hide();
			
			//查询征信按钮
			$("#btn_searchCusCredit").show();
			//开户绑卡按钮
			if($("#isPartnerOpenAccount").val() == 1){
				$(".partnerNoNyyhShowTd_openAccount").hide();
			}else{
				$(".partnerNoNyyhShowTd_openAccount").show();
			}
			
		}else if(partnerNo == "hnbx"){
			$(".partnerNoShowTr,.payBankShowTr,.partnerNoHnbxShowTr").show();
			$("#house_province_code_font,#house_city_code_font").show();
			$("#applyLoanDateShowFont,.partnerNoNyyhShowTr").hide();
			$("#applyLoanDateShowFont").show();
			//查询征信按钮
			$("#btn_searchCusCredit").hide();
			$(".partnerNoNyyhShowTd_openAccount").hide();
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
	
	
	//加载系统银行配置
	function loadSystemBankConfig(partnerNo){
		
		console.log("-----------loadSystemBankConfig begin-----------------");
		
		if(partnerNo == null){
			partnerNo=$('#partner_source_no').combobox('getValue');
		}
		
		//打款银行（点融）-还款（华安保险）
		var pay_bank_name = $("#pay_bank_name").combobox("getValue")
		//打款银行支行-还款
		var pay_bank_branch = $("#pay_bank_branch").textbox("getValue")
		//打款银行开户名-还款
		var pay_acct_name = $("#pay_acct_name").textbox("getValue")
		//打款银行帐号-还款
		var pay_acct_no = $("#pay_acct_no").textbox("getValue")
		
		
		if(partnerNo == 'dianrongwang'){
			//点荣网
			//打款银行
			if(($.trim(pay_bank_name) == "" || $.trim(pay_bank_name) == "--请选择--" ) && $.trim(pay_bank_branch) == '' 
					&& $.trim(pay_acct_name) == '' && $.trim(pay_acct_no) == ''){
				
				$.ajax({
						type: "POST",
				        url: "<%=basePath%>sysLookupController/getSysLookupValByChildType.action",
				        data:{"lookupType":'PARTNER_LOAN_BANK',"lookupVal":'PARTNER_LOAN_BANK_1'},
				        dataType: "json",
				        success: function(result){
				        	console.log("--------------加载点融打款银行帐号--------");
				        	console.log(result);
				        	if(result != null && result.lookupDesc != null){
				        		var tempArray = result.lookupDesc.split("_");
				        		if(tempArray!= null && tempArray.length == 6){
				        			//打款银行
				        			$("#pay_bank_name").combobox("setValue",tempArray[0]);
				        			//打款银行支行
				        			$("#pay_bank_branch").textbox("setValue",tempArray[1]);
				        			//打款银行开户名
				        			$("#pay_acct_name").textbox("setValue",tempArray[2]);
				        			//打款银行帐号
				        			$("#pay_acct_no").textbox("setValue",tempArray[3]);
				        			//打款银行省份
				        			$("#pay_province_code").combobox("setValue",tempArray[4]);
				        			//打款银行城市
				        			$("#pay_city_code").combobox("setValue",tempArray[5]);
				        		}
				        	}
						},error : function(result){
							console.log('加载点融打款银行帐号');	
						}
					 });
			}
		}else if(partnerNo == 'hnbx'){
			//华安保险
			//收款银行
			var payment_bank = $("#payment_bank").combobox("getValue")
			//收款银行开户名
			var payment_acct_name = $("#payment_acct_name").textbox("getValue")
			//收款银行帐号
			var payment_acct_no = $("#payment_acct_no").textbox("getValue")
			
			//收款银行
			if(($.trim(payment_bank) == "" || $.trim(payment_bank) == "--请选择--" ) && $.trim(payment_acct_name) == ""  && $.trim(payment_acct_no) == ""  ){
				$.ajax({
					type: "POST",
			        url: "<%=basePath%>sysLookupController/getSysLookupValByChildType.action",
			        data:{"lookupType":'PARTNER_LOAN_BANK',"lookupVal":'PARTNER_LOAN_BANK_2'},
			        dataType: "json",
			        success: function(result){
			        	if(result != null && result.lookupDesc != null){
			        		var tempArray = result.lookupDesc.split("_");
			        		if(tempArray!= null && tempArray.length == 3){
			        			//收款银行
			        			$("#payment_bank").combobox("setValue",tempArray[0]);
			        			//收款银行支行
			        			$("#payment_acct_name").textbox("setValue",tempArray[1]);
			        			//收款银行开户名
			        			$("#payment_acct_no").textbox("setValue",tempArray[2]);
			        		}
			        	}
					},error : function(result){
						console.log('加载华安保险收款银行帐号失败');	
					}
				 });
			}
			//还款款银行
			if(($.trim(pay_bank_name) == "" || $.trim(pay_bank_name) == "--请选择--" ) && $.trim(pay_bank_branch) == '' 
					&& $.trim(pay_acct_name) == '' && $.trim(pay_acct_no) == ''){	
				$.ajax({
					type: "POST",
			        url: "<%=basePath%>sysLookupController/getSysLookupValByChildType.action",
			        data:{"lookupType":'PARTNER_LOAN_BANK',"lookupVal":'PARTNER_REPAYMENT_BANK_2'},
			        dataType: "json",
			        success: function(result){
			        	if(result != null && result.lookupDesc != null){
			        		var tempArray = result.lookupDesc.split("_");
			        		if(tempArray!= null && tempArray.length == 6){
			        			//还款银行
			        			$("#pay_bank_name").combobox("setValue",tempArray[0]);
			        			//还款银行支行
			        			$("#pay_bank_branch").textbox("setValue",tempArray[1]);
			        			//还款银行开户名
			        			$("#pay_acct_name").textbox("setValue",tempArray[2]);
			        			//还款银行帐号
			        			$("#pay_acct_no").textbox("setValue",tempArray[3]);
			        			//还款银行省份
			        			$("#pay_province_code").combobox("setValue",tempArray[4]);
			        			//还款银行城市
			        			$("#pay_city_code").combobox("setValue",tempArray[5]);
			        		}
			        	}
					},error : function(result){
						console.log('加载华安保险还款银行帐号失败');	
					}
				});
			}
		}
		
		console.log("-----------loadSystemBankConfig end-----------------");
	}
	
	
	
	//获取附件
	var globaPartnerNo = "";
	function getProjectFile(projectId,partnerId,partnerNo){
		
		if(projectId=="" || projectId == null){
			$("#uploadFileBtn").show();
			$("#uploadFileBatchBtn").show();
			
			projectId = $("#projectId").val()
		}
		if(partnerNo == null || partnerNo == ''){
			partnerNo = $("#partner_source_no").combobox("getValue");
		}
		if(partnerId == null || partnerId == ""){
			partnerId = $("#pid").val();
		}
		
		//多资方防止文件查旧
		if(partnerId == null || partnerId == 0 ){
			partnerId = -1;
		}
		
		var rows = $('#project_file_table').datagrid('getRows');//获取当前页的数据行
		if((partnerNo !="" && partnerNo != null) &&rows.length == 0 || globaPartnerNo != partnerNo){
			globaPartnerNo = partnerNo;
			var url = "<%=basePath%>projectPartnerController/getProjectFileList.action?projectId="+projectId+"&partnerNo="+partnerNo+"&partnerId="+partnerId;
			$('#project_file_table').datagrid('options').url = url;
			$('#project_file_table').datagrid('load');
		}else{
			$("#project_file_table").datagrid("resize");
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
	
	//打开申请审核页面
/* 	function openPartner(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			addItem(row[0].projectId);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	} */
	
	//列表操作
// 	function detailInfo(value, rowData, index) {
// 		var projectDom = "<a href='javascript:void(0)' onclick='addItem("+rowData.projectId+")'><font color='blue'>申请</font></a>";
// 		//projectDom += "    <a href='javascript:void(0)' onclick='openUpload("+index+",\"add\")'><font color='blue'>上传材料</font></a>";
// 		return projectDom;
// 	}
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
	
	/**
	 * 保存信息
	 * isTip    是否操作提示    0否   1：是
	 */
	function savePartnerInfo(isTip){
		
		var partnerNo=$('#partner_source_no').combobox('getValue');
		var applyDate =$("#applyDate").textbox('getValue')
		
		var paymentTipStr = "借款人";
		var payTipStr = "打款";
		//华安保险，提示文字
		if(partnerNo == 'hnbx'){
			paymentTipStr = "收款";
			payTipStr = "还款";
		}
		
		if(partnerNo == "" || partnerNo == null){
			$.messager.alert("操作提示","请选择合作机构","error"); 
			$('#partner_source_no').focus();
			return false;
		}else if(applyDate == "" || applyDate == null || applyDate <= 0){
			$.messager.alert("操作提示","请填写大于0的借款天数","error"); 
			return false;
		}else if($("#applyLoanDate").textbox('getValue') == "" && partnerNo != 'dianrongwang'){
			$.messager.alert('操作提示',"申请放款日期不能为空");
			return;
		}else if(partnerNo == 'tlxt'){
			if($("#businessCategoryStr").val() != '交易' ){
				$.messager.alert("操作提示","合作机构统联信托只支持交易现金赎楼","error"); 
				return false;
			}
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
		
		
		if(partnerNo == 'tlxt' || partnerNo== 'dianrongwang'  || partnerNo== 'nyyh' || partnerNo== 'hnbx'){
			var paymentBank=$('#payment_bank').combobox('getValue');
			if(paymentBank == null || paymentBank == '--请选择--' ){
				$.messager.alert("操作提示","请选择"+paymentTipStr+"银行","error"); 
				$('#payment_bank').focus();
				return false;
			}	
			var paymentAcctName =$("#payment_acct_name").textbox('getValue')
			if(paymentAcctName == null || paymentAcctName == '' ){
				$.messager.alert("操作提示","请填写"+paymentTipStr+"户名","error"); 
				return false;
			}
			var paymentAcctNo =$("#payment_acct_no").textbox('getValue')
			if(paymentAcctNo == null || paymentAcctNo == '' ){
				$.messager.alert("操作提示","请填写"+paymentTipStr+"帐户","error"); 
				return false;
			}
			
			//南粤银行
			if(partnerNo == 'nyyh'){
				var payment_bank_phone =$("#payment_bank_phone").textbox('getValue')
				if(payment_bank_phone == null || payment_bank_phone == '' ){
					$.messager.alert("操作提示","请填写借款人预留手机号","error"); 
					return false;
				}
				var loan_period_limit =$("#loan_period_limit").textbox('getValue')
				if(loan_period_limit == null || loan_period_limit == '' || loan_period_limit <=0  ){
					$.messager.alert("操作提示","请填写借款期限","error"); 
					return false;
				}
				var is_credit_loan =$("#is_credit_loan").combobox('getValue')
				if(is_credit_loan == null || loan_period_limit == '' || is_credit_loan ==0  ){
					$.messager.alert("操作提示","请选择他行有无未结清信用贷款","error"); 
					return false;
				}
				var payment_bank_line_no =$("#payment_bank_line_no").textbox('getValue')
				if(payment_bank_line_no == null || payment_bank_line_no == ''  ){
					$.messager.alert("操作提示","请填写借款人银行联行行号","error"); 
					return false;
				}
			}
		}
		if(partnerNo == 'dianrongwang' ||  partnerNo== 'hnbx'){
			var payBankName=$('#pay_bank_name').combobox('getValue');
			if(payBankName == null || payBankName == '--请选择--' ){
				$.messager.alert("操作提示","请选择"+payTipStr+"银行","error"); 
				return false;
			}	
			var payBankBranch =$("#pay_bank_branch").textbox('getValue')
			if(payBankBranch == null || payBankBranch == '' ){
				$.messager.alert("操作提示","请填写"+payTipStr+"银行支行","error"); 
				return false;
			}
			var payAcctName =$("#pay_acct_name").textbox('getValue')
			if(payAcctName == null || payAcctName == '' ){
				$.messager.alert("操作提示","请填写"+payTipStr+"银行开户名","error"); 
				return false;
			}
			var payAcctNo =$("#pay_acct_no").textbox('getValue')
			if(payAcctNo == null || payAcctNo == '' ){
				$.messager.alert("操作提示","请填写"+payTipStr+"银行帐号","error"); 
				return false;
			}
			var payProvinceCode=$('#pay_province_code').combobox('getValue');
			if(payProvinceCode == null || payProvinceCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择"+payTipStr+"银行省份","error"); 
				return false;
			}
			var payCityCode=$('#pay_city_code').combobox('getValue');
			if(payCityCode == null || payCityCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择"+payTipStr+"银行城市","error"); 
				return false;
			}
			
			
			//物业省市
			var houseProvinceCode=$('#house_province_code_house_form').combobox('getValue');
			var houseCityCode=$('#house_city_code_house_form').combobox('getValue');

			if(partnerNo == 'dianrongwang'){
				if(houseProvinceCode == null || houseProvinceCode == '--请选择--' ){
					$.messager.alert("操作提示","请选择物业信息>物业省份","error"); 
					return false;
				}
				if(houseCityCode == null || houseCityCode == '--请选择--' ){
					$.messager.alert("操作提示","请选择物业信息>物业城市","error"); 
					return false;
				}
			}
			
			$("#house_province_code").val(houseProvinceCode);
			$("#house_city_code").val(houseCityCode);
		} 
		
		//执行利率
		if(partnerNo== 'hnbx'){
			var partnerPushAccount = $("#partnerPushAccount").combobox('getValue');				//推送帐号
			var partnerGrossRate = $("#partnerGrossRate").textbox("getValue");					//执行利率
			
			if(partnerGrossRate == "" || isNaN(partnerGrossRate) || partnerGrossRate == 0 || partnerGrossRate < 0 ){
				$.messager.alert("操作提示","执行利率必须是大于0的数字","error"); 
				return false;
			}else if(partnerPushAccount == "" || partnerPushAccount == 0 ){
				$.messager.alert("操作提示","请选择推送帐号","error"); 
				return false;
			}
		}
		
		
		
		var rows = $('#project_file_table').datagrid('getSelections');
		
		var resFilesId = "";
		for (var i = 0; i < rows.length; i++) {
			
			if (i == 0) {
				resFilesId += rows[i].pid;
			} else {
				resFilesId += "," + rows[i].pid;
			}
		}
		$("#requestFiles").val(resFilesId);
		
		var resultFlag = false;
    	$.ajax({
            cache: true,
            type: "POST",
            url:"savePartnerInfo.action",
            data:$('#partnerInfo').serialize(),// 你的formid
            async: false,
			dataType:'json',
            success: function(result) {
            	console.log(result);
    			if(isTip == 1){
					if(result && result.header.success == true){
						$.messager.alert('操作提示',result.header.msg);
						// 重新赎楼项目列表信息
	 					$("#grid").datagrid('load');
	 					// 清空所选择的行数据
	 					clearSelectRows("#grid");
						var pid=result.body.pid;
						$("#pid").val(pid);
						resultFlag = true ;
						console.log("成功resultFlag:"+resultFlag);
					}else{
						$.messager.alert('操作提示',result.header.msg,'error');	
					}
				}else{
					if(result && result.header.success == false){
						$.messager.alert('操作提示',result.header.msg);
					}else{
						resultFlag = true;
					}
				}
            },
            error: function(request) {
            	$.messager.alert('操作提示',"保存失败！",'error');	
            }
        });
    	return resultFlag;
	}

	//发送申请
	function applyPartnerInfo(){
		var pid = $("#pid").val();
		if(pid == null || pid == "" || pid == 0){
			$.messager.alert("操作提示","请先保存后再提交申请","error"); 
			return false;
		}
		
		//勾选的文件
		var partnerNo=$('#partner_source_no').combobox('getValue');
		
		
		var paymentTipStr = "借款人";
		var payTipStr = "打款";
		//华安保险，提示文字
		if(partnerNo == 'hnbx'){
			paymentTipStr = "收款";
			payTipStr = "还款";
		}
		
		var applyDate =$("#applyDate").textbox('getValue')
		if(applyDate == "" || applyDate == null || applyDate <= 0){
			$.messager.alert("操作提示","请填写大于0的借款天数","error"); 
			return false;
		}
		if($("#applyLoanDate").textbox('getValue') == "" && partnerNo!= 'dianrongwang'){
			$.messager.alert('操作提示',"申请放款日期不能为空");
			return;
		}
		if(partnerNo == "" || partnerNo == null){
			$.messager.alert("操作提示","请选择合作机构","error"); 
			return false;
		}/* else if(!(partnerNo == 'tlxt' || partnerNo == 'xiaoying' || partnerNo== 'dianrongwang') ){
			$.messager.alert("操作提示","合作机构只能选择统联信托、小赢、点荣网","error"); 
			$('#partner_source_no').focus();
			return false;
		} */
		
		if(partnerNo == 'tlxt'){
			if($("#businessCategoryStr").val() != '交易' ){
				$.messager.alert("操作提示","合作机构统联信托只支持交易现金赎楼","error"); 
				return false;
			}
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
		if(partnerNo == 'tlxt' || partnerNo== 'dianrongwang' || partnerNo== 'hnbx'){
			var paymentBank=$('#payment_bank').combobox('getValue');
			if(paymentBank == null || paymentBank == '--请选择--' ){
				$.messager.alert("操作提示","请选择"+paymentTipStr+"银行","error"); 
				$('#payment_bank').focus();
				return false;
			}	
			var paymentAcctName =$("#payment_acct_name").textbox('getValue')
			if(paymentAcctName == null || paymentAcctName == '' ){
				$.messager.alert("操作提示","请填写"+paymentTipStr+"户名","error"); 
				return false;
			}
			var paymentAcctNo =$("#payment_acct_no").textbox('getValue')
			if(paymentAcctNo == null || paymentAcctNo == '' ){
				$.messager.alert("操作提示","请填写"+paymentTipStr+"帐户","error"); 
				return false;
			}
		}
		
		if(partnerNo == 'dianrongwang'){
			var payBankName=$('#pay_bank_name').combobox('getValue');
			if(payBankName == null || payBankName == '--请选择--' ){
				$.messager.alert("操作提示","请选择"+payTipStr+"银行","error"); 
				return false;
			}	
			var payBankBranch =$("#pay_bank_branch").textbox('getValue')
			if(payBankBranch == null || payBankBranch == '' ){
				$.messager.alert("操作提示","请填写"+payTipStr+"银行支行","error"); 
				return false;
			}
			var payAcctName =$("#pay_acct_name").textbox('getValue')
			if(payAcctName == null || payAcctName == '' ){
				$.messager.alert("操作提示","请填写"+payTipStr+"银行开户名","error"); 
				return false;
			}
			var payAcctNo =$("#pay_acct_no").textbox('getValue')
			if(payAcctNo == null || payAcctNo == '' ){
				$.messager.alert("操作提示","请填写"+payTipStr+"银行帐号","error"); 
				return false;
			}
			var payProvinceCode=$('#pay_province_code').combobox('getValue');
			if(payProvinceCode == null || payProvinceCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择"+payTipStr+"银行省份","error"); 
				return false;
			}
			var payCityCode=$('#pay_city_code').combobox('getValue');
			if(payCityCode == null || payCityCode == '--请选择--' ){
				$.messager.alert("操作提示","请选择"+payTipStr+"银行城市","error"); 
				return false;
			}
			
			//物业省市
			var houseProvinceCode=$('#house_province_code_house_form').combobox('getValue');
			var houseCityCode=$('#house_city_code_house_form').combobox('getValue');

			if(partnerNo == 'dianrongwang'){
				if(houseProvinceCode == null || houseProvinceCode == '--请选择--' ){
					$.messager.alert("操作提示","请选择物业信息>物业省份","error"); 
					return false;
				}
				if(houseCityCode == null || houseCityCode == '--请选择--' ){
					$.messager.alert("操作提示","请选择物业信息>物业城市","error"); 
					return false;
				}
			}
			
			$("#house_province_code").val(houseProvinceCode);
			$("#house_city_code").val(houseCityCode);
		}
		
		
		var rows = $('#project_file_table').datagrid('getSelections');
		var resFilesId = "";
		for (var i = 0; i < rows.length; i++) {
			if(partnerNo  != rows[i].partnerNo ){
				$.messager.alert("操作提示","请选择材料信息","error"); 
				return false;
			}
			if (i == 0) {
				resFilesId += rows[i].pid;
			} else {
				resFilesId += "," + rows[i].pid;
			}
			if(partnerNo == 'nyyh' || partnerNo == 'hnbx'){
				if(rows[i].thirdFileUrl == null ||  rows[i].thirdFileUrl == ''){
					$.messager.alert("操作提示","选择的文件，必须先上传第三方","error"); 
					return;
				}
			}
		}

		if(resFilesId == ""){
			$.messager.alert('操作提示',"请选择材料再提交");
			return;			
		}
		
		console.log("rows.length:"+rows.length);
		console.log("rows.resFilesId:"+resFilesId);
		
		$("#requestFiles").val(resFilesId);
		
		$.messager.confirm("操作提示","确定发送申请?",
			function(r) {
	 			if(r){
	 				//保存后再提交
	 				var saveFlag = savePartnerInfo(0) ;
	 				console.log("saveFlag:"+saveFlag);
	 				if(saveFlag == false){
	 					return false;
	 				}
	 				
	 				$('#partnerInfo').form('submit', {
	 					url : "applyPartner.action",
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
	 						$.messager.alert('操作提示',"保存失败！",'error');	
	 					} 
	 				});
	 			}
		});
	 
		

	}
	
	//材料信息操作
	function projectFileCzFormat(value, rowData, index) {
		var downloadUrl = "<%=basePath%>beforeLoanController/downloadData.action?path="+rowData.fileUrl+"&fileName="+rowData.fileName;
		var projectDom = "<a href='"+downloadUrl+"'><font color='blue'>下载</font></a>";
		projectDom += "    <a href='javascript:void(0)' onclick='openUpload("+index+",\"update\")'><font color='blue'>编辑</font></a>";
		projectDom += "   <a href='javascript:void(0)' onclick='deleteProjectFile("+index+")'><font color='blue'>删除</font></a>";
		
		if( rowData.accessoryType == '10'  && (rowData.thirdFileUrl == null || rowData.thirdFileUrl == "" )){
			projectDom += " <a href='javascript:void(0)' onclick='uploadFileToThird("+index+")'><font color='blue'>上传南粤</font></a>";
		}
		
		return projectDom;
	}
	
	//打开上传文件   type  add:新增  update:修改
	function openUpload(rowIndex,type){
		
		//先保存才能上传资料
		if($("#pid").val() == null  || $("#pid").val() ==  0){
			$.messager.alert('操作提示','请先保存后再上传资料','error');	
			return;
		}
		
		$('#uploadFileForm').form('reset');
		var partnerNo = $("#partner_source_no").combobox("getValue");
		if(partnerNo==""){
			$.messager.alert('操作提示','请选择合作机构再上传文件','error');	
			return;
		}
		
		var rowData =null;
		var projectName = "";
		var projectId = "";
		
		$("#file_operator_type").val(type);
		
		if(type == "add"){
			$("#isUpdateFile").val(0);
			projectName = $("#projectName").textbox("getValue");
			projectId = $("#projectId").val();

			//文件选择显示
			$(".uploadmutilFile").show();
			$(".uploadSimpleFile").hide();
			
			//南粤银行，自动选中材料
			if(partnerNo == 'nyyh'){
				$("#accessoryType").combobox("setValue",10);
				changeFileChileType(10);
			}else if(partnerNo == 'hnbx'){
				$("#accessoryType").combobox("setValue",11);
				changeFileChileType(11);
			}
			
			//设置合作项目id
			$("#partnerId_file").val($("#pid").val());
			
			
		}else if(type == "update"){
			rowData = $('#project_file_table').datagrid('getData').rows[rowIndex];
			projectName = $("#projectName").textbox('getValue');;
			$("#pid_file").val(rowData.pid);
			$('#accessoryType').combobox('setValue', rowData.accessoryType);
			$("#partnerId_file").val(rowData.partnerId);
			
			//加载子类型
			changeFileChileType(rowData.accessoryType);
			
			$('#accessoryChildType').combobox('setValue', rowData.accessoryChildType);
			$("#remark_file").val(rowData.remark);
			$('#file').filebox('setText', rowData.fileUrl);
			$("#isUpdateFile").val(1);
			
			//文件选择显示
			$(".uploadmutilFile").hide();
			$(".uploadSimpleFile").show();
			
			$('#fileSize_file').val(rowData.fileSize);
			projectId = rowData.projectId;
		}
		console.info(rowData);

		if(partnerNo == null ){
			partnerNo = "";
		}
		
   		$('#partner_no').combobox({         
   			data:partnerGlobal.partnerJson, //此为重点
   	        valueField:'pid',
   	        textField:'lookupDesc',
   	        value:partnerNo,
   	        //如果需要设置多项隐含值，可使用以下代码
   	        onLoadSuccess:function(){
			 	 if(partnerNo == "xiaoying" || partnerNo == "dianrongwang"){
			 		 $("#accessoryChildTypeFont").hide();
			 	 }else if(partnerNo == "tlxt" || partnerNo == "nyyh"){
			 		$("#accessoryChildTypeFont").show();
			 	 }
   	        }, 
			onSelect:function(){  
				var partnerNoTemp=$('#partner_no').combobox('getValue');
			 	 if(partnerNoTemp == "xiaoying" || partnerNoTemp == "dianrongwang"){
			 		 $("#accessoryChildTypeFont").hide();
			 	 }else if(partnerNoTemp == "tlxt"){
			 		$("#accessoryChildTypeFont").show();
			 	 } 
	       }
   	    });
   		
   		$("#partner_no").combobox("disable");
		
		$('#projectName_file').text(projectName);
		$('#projectId_file').val(projectId);

		$('#uploadFileForm').attr('action',"${basePath}projectPartnerController/uploadPartnerFile.action");
		$('#dialog_data').dialog('open').dialog('setTitle','上传材料');
		
	}
	//上传材料
	function uploadPartnerFile(){
		var file = $('#file').textbox('getValue');
		var projectId = $('#projectId_file').val();
		var accessoryType=$('#accessoryType').combobox('getValue');
		var accessoryChildType=$('#accessoryChildType').combobox('getValue');
		var partnerNo=$('#partner_no').combobox('getValue');
		var partnerId=$('#partnerId_file').val();
		
		if(partnerId == "" ||  partnerId == 0 ){
			$.messager.alert("操作提示","请先保存项目后再操作","error"); 
			return false;
		}
		if(projectId=="" || null==projectId){
			$.messager.alert("操作提示","请选择项目","error"); 
			return false;
		}
		if(partnerNo == "" || partnerNo == null){
			$.messager.alert("操作提示","请选择合作机构","error"); 
			return false;
		}
		if(accessoryType == "" || accessoryType == null){
			$.messager.alert("操作提示","请选择附件类型","error"); 
			return false;
		}
		if(partnerNo == 'tlxt' || partnerNo == 'nyyh' || partnerNo == 'hnbx'){
			if(accessoryChildType == "" || accessoryChildType == null){
				$.messager.alert("操作提示","请选择附件子类型","error"); 
				return false;
			}
		}
		
		//只能上传jpg可式的文件
		if(partnerNo == 'nyyh'){
			if(accessoryType != "10"){
				$.messager.alert("操作提示","附件类型只能选择南粤银行材料","error"); 
				return false;
			}
 			var exp = /.jpg*|.JPG*/;
 			var fileNameList = $("#fileQueue .fileName");
 			if(fileNameList != null && fileNameList.length> 0){
 				for(var  i = 0 ; i < fileNameList.length ; i++ ){
 					
 					console.log($(fileNameList[i]).text());
 					
 		 			if(exp.exec($(fileNameList[i]).text()) == null) {
 		 				$.messager.alert("操作提示","只能上传jpg格式的文件","error"); 
 		 				return false;
 		 			}
 				}
 			}
		}
		
		var file_operator_type =$("#file_operator_type").val();
		
		
		if(file_operator_type == "update"){
			if((file=="" || null==file) &&  $("#isUpdateFile").val() == 0){
				$.messager.alert("操作提示","请选择文件","error"); 
				return false;
			}
			$("#partner_source_no").combobox("enable");
			$('#uploadFileForm').form('submit',{
				success:function(data){
					var dataObj=eval("("+data+")");//转换为json对象
					if(dataObj && dataObj.header.success ){
						$.messager.alert('操作提示',dataObj.header.msg);
	 					if($("#pid_file").val()>0){
	 						// 重新加载
	 						$("#project_file_table").datagrid('load');
		 					$('#dialog_data').dialog('close');
	 					}else{
	 						$('#file').textbox('setValue', "");
	 					}
					}else{
						$.messager.alert('操作提示',dataObj.header.msg,'error');	
					}
					$("#partner_no").combobox("disable");
				}
			})
		}else{
			
			if($("#fileQueue .fileName") == null || $("#fileQueue .fileName").length  == 0 ){
				$.messager.alert("操作提示","请选择文件","error"); 
				return false;
			}
			//多文件上传方式
			$('#uploadify').uploadify('settings','formData',{'projectId':projectId,
					'accessoryType':accessoryType,
					'accessoryChildType':accessoryChildType,
					'partnerNo':encodeURI(partnerNo),
					'partnerId':partnerId,
					'remark':encodeURI($("#remark_file").val())});
			$('#uploadify').uploadify('upload','*');
		}
	}
	
	
	//删除附件
	function deleteProjectFile(rowIndex){
		var rowData = $('#project_file_table').datagrid('getData').rows[rowIndex];
		$.messager.confirm("操作提示","确定删除?",
			function(r) {
	 			if(r){
	 				$.ajax({
	 					type: "POST",
	 			        url: "<%=basePath%>projectPartnerController/deleteProjectFile.action",
	 			        data:{"pid":rowData.pid},
	 			        dataType: "json",
	 			        success: function(result){
	 						// 重新加载
	 						$("#project_file_table").datagrid('load');
	 					}
	 				 });
	 			}
		});
	}
	
	
	//上传附件到第三方平台
	function uploadFileToThird(rowIndex){
		var rowData = $('#project_file_table').datagrid('getData').rows[rowIndex];
		$.messager.confirm("操作提示","确定上传南粤?",
			function(r) {
	 			if(r){
	 				$.ajax({
	 					type: "POST",
	 			        url: "<%=basePath%>projectPartnerController/uploadFileToThird.action",
	 			        data:{"pid":rowData.pid},
	 			        dataType: "json",
	 			       	beforeSend: function(){
	 			       		MaskUtil.mask("上传中,请耐心等候......");  
	 			    	},
	 			    	complete: function(){
	 			    		MaskUtil.unmask(); 
	 			    	},
	 			        success: function(result){
	 			        	$.messager.alert('操作提示',result.header.msg);
	 			        	if(result.header.success == true){
		 						// 重新加载
		 						$("#project_file_table").datagrid('load');
	 			        	}
	 					},error:function(){
 	 						$.messager.alert('操作提示',"上传失败！",'error');	
	 					}
	 				 });
	 			}
		});
	}
 
	//批量上传南粤银行材料
	function batchUploadFileToThird(){
		var row = $('#project_file_table').datagrid('getSelections');
		if (row.length == 0) {
			$.messager.alert("操作提示","请选择数据","error");
			return ;
		}
		if (row.length > 40) {
			$.messager.alert("操作提示","每次最大上传40条数据，当前选中:"+row.length+"条","error");
			return ;
		}
		for(var i= 0 ; i < row.length; i ++){
			var rowData = row[i];	
			if($.trim(rowData.thirdFileUrl) !="" ){
				$.messager.alert("操作提示","请选择未上传的数据","error");
				return ;
			}
		}
		MaskUtil.mask("上传中,请耐心等候......");  
		console.log("上传中,请耐心等候......");
	 	var successCount = 0 ; 
		for(var i= 0 ; i < row.length; i ++){
			var rowData = row[i];	
			$.ajax({
				type: "POST",
		        url: "<%=basePath%>projectPartnerController/uploadFileToThird.action",
		        data:{"pid":rowData.pid},
		        async: false,
		        dataType: "json",
		        success: function(result){
		        	if(result.header.success == true){
		        		successCount ++ ; 
		        	}
		        	console.log("上传完成！"+i);
				},error:function(){
						console.log("上传失败！");
				}
			 });
		}
		console.log("关闭......");
		$.messager.alert("操作提示","上传完成，成功上传数量："+successCount);
		MaskUtil.unmask(); 
		$("#project_file_table").datagrid('load');  
	}
	
	
	
	//批量上传华安保险
	function batchUploadFileToThird_hnbx(){
		var rows = $('#project_file_table').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示","请选择数据","error");
			return ;
		}
	 	//文件id
	 	var pidListStr = "";
		for(var i= 0 ; i < rows.length; i ++){
			var rowData = rows[i];	
			pidListStr += rowData.pid+",";
		}
		//去掉最后一位逗号
		pidListStr = pidListStr.substr(0,pidListStr.length-1);
		
	 	var partnerId =rows[0].partnerId;
	 	
		MaskUtil.mask("上传中,请耐心等候......");  
		$.ajax({
			type: "POST",
	        url: "<%=basePath%>projectPartnerController/uploadFileToThird_hnbx.action",
	        data:{"pidListStr":pidListStr,"partnerId":partnerId},
	        async: false,
	        dataType: "json",
	        success: function(result){
	        	$.messager.alert('操作提示',result.header.msg);
			},error:function(){
				$.messager.alert("操作提示","上传失败","error");
			}
		 });
		MaskUtil.unmask(); 
		$("#project_file_table").datagrid('load');  
	}
	
	
	
	
		
	//重置按钮
	function majaxReset(){
		$('#searchFrom').form('reset');
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
	
	
	
	//物业信息操作
	function estateCzFormat(value, rowData, index) {
		var projectDom = "<a href='javascript:void(0)' onclick='updateProjectEstateInit("+index+")'><font color='blue'>编辑</font></a>";
		return projectDom;
	}
	
	//更新物业信息初始化
	function updateProjectEstateInit(index){
		var rowData = $('#house_info_table').datagrid('getData').rows[index];
		
 		$("#project_estate_data_houseId").val(rowData.houseId); 
 		$("#project_estate_data_projectId").val(rowData.projectId); 
		$("#project_estate_data_houseName").html(rowData.houseName); 
 		$("#project_estate_data_propertyRatio").textbox("setValue",rowData.propertyRatio);
 		$("#project_estate_data_evaluationNet").numberbox("setValue",rowData.evaluationNet);
 		
 		$("#project_estate_data_downPayment").numberbox("setValue",rowData.downPayment);
 		$("#project_estate_data_purchaseDeposit").numberbox("setValue",rowData.purchaseDeposit);
 		$("#project_estate_data_purchaseBalance").numberbox("setValue",rowData.purchaseBalance);
 		
		
		$('#project_estate_data').dialog('open').dialog('setTitle','更新物业信息');
		$("#project_estate_data").dialog("move",{left:200,top:200}); 
		
	}
	
	//更新物业信息
	function updateProjectEstate(){
		var propertyRatio = $("#project_estate_data_propertyRatio").textbox("getValue");
		var evaluationNet = $("#project_estate_data_evaluationNet").numberbox("getValue");
		
		if(evaluationNet == null ||  evaluationNet == 0 ){
			$.messager.alert('操作提示',"请填写大于0的评估净值！",'error');	
			return ;
		}
		if(propertyRatio == null || $.trim(propertyRatio) == ""){
			$.messager.alert('操作提示',"请填写产权人占比！",'error');	
			return ;
		}
		if(!(propertyRatio.indexOf("(") >= 0 &&  propertyRatio.indexOf(")") >= 0)){
			$.messager.alert('操作提示',"请填写正确格式！",'error');	
			return ;
		}
		$.messager.confirm("操作提示","确认保存物业信息?",
				function(r) {
		 			if(r){
		 				$('#project_estate_data_form').form('submit', {
							url : "<%=basePath%>beforeLoanController/saveHouseInfo.action",
							onSubmit : function() {
									MaskUtil.mask("保存中,请耐心等候......");  
									return true; 
							},
							success : function(result) {
								MaskUtil.unmask(); 
								var ret = eval("("+result+")");
								//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
								if(ret && ret.header["success"] ){
				 					$.messager.alert('操作提示',"保存成功");	
				 					$('#project_estate_data').dialog('close');
				 					loadProjectEstate($("#project_estate_data_projectId").val());
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
	
	/**
	 * 重新加载物业信息
	 */
	function loadProjectEstate(projectId){
		var url = "<%=basePath%>beforeLoanController/getHouseByProjectId.action?projectId="+projectId;
		$('#house_info_table').datagrid('options').url = url;
		$('#house_info_table').datagrid('reload');
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
	
	
	
	//原贷款银行操作
	function originalLoanCzFormat(value, rowData, index) {
		var projectDom = "<a href='javascript:void(0)' onclick='updateOriginalLoanInit("+index+")'><font color='blue'>编辑</font></a>";
		return projectDom;
	}
	
	//更新原贷款银行操作初始化
	function updateOriginalLoanInit(index){
		var rowData = $('#originalLoan_table').datagrid('getData').rows[index];
 		$("#originalLoan_data_originalLoanId").val(rowData.originalLoanId);
 		$("#originalLoan_data_projectId").val(rowData.projectId);
		$("#originalLoan_data_oldLoanBankStr").html(rowData.oldLoanBankStr); 
 		$("#originalLoan_data_oldLoanAccount").textbox("setValue",rowData.oldLoanAccount);
		
		$('#originalLoan_data').dialog('open').dialog('setTitle','更新原贷款银行信息');
		$("#originalLoan_data").dialog("move",{left:200,top:200}); 
	}
	//更新原贷款银行操作
	function updateOriginalLoan(){
		
		var oldLoanAccount = $("#originalLoan_data_oldLoanAccount").textbox("getValue");
		var projectId = $("#originalLoan_data_projectId").val();
		
		if(oldLoanAccount == null || $.trim(oldLoanAccount) == ""){
			$.messager.alert('操作提示',"请填写原贷款银行供楼帐号！",'error');	
			return ;
		}
 
		$.messager.confirm("操作提示","确认保存原贷款银行信息?",
				function(r) {
		 			if(r){
		 				$('#originalLoan_data_form').form('submit', {
							url : "<%=basePath%>beforeLoanController/saveOriginalLoanInfo.action",
							onSubmit : function() {
									MaskUtil.mask("保存中,请耐心等候......");  
									return true; 
							},
							success : function(result) {
								MaskUtil.unmask(); 
								var ret = eval("("+result+")");
								//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
								if(ret && ret.header["success"] ){
				 					$.messager.alert('操作提示',"保存成功");	
				 					$('#originalLoan_data').dialog('close');
				 					loadProjectOriginalLoan(projectId);
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

	//修改赎楼信息（资金机构）
	function updateForeclosureByPartner(){
		var pid= $("#foreclosure_table_pid").val();
		var newRecePerson= $("#foreclosure_table_newRecePerson").textbox("getValue");
		var newReceBank= $("#foreclosure_table_newReceBank").textbox("getValue");
		
		
		if(newRecePerson == null || $.trim(newRecePerson) == ""){
			$.messager.alert('操作提示',"请填写新贷款申请人！",'error');	
			return;
		}else if(newReceBank == null || $.trim(newReceBank) == ""){
			$.messager.alert('操作提示',"请填写新贷款收款银行！",'error');
			return;
		}
 
		$.messager.confirm("操作提示","确认保存赎楼信息?",
				function(r) {
		 			if(r){
		 				$('#foreclosure_form').form('submit', {
							url : "<%=basePath%>projectPartnerController/updateForeclosureByPartner.action",
							onSubmit : function() {
									MaskUtil.mask("保存中,请耐心等候......");  
									return true; 
							},
							success : function(result) {
								MaskUtil.unmask(); 
								var ret = eval("("+result+")");
								//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
								if(ret && ret.header["success"] ){
									$.messager.alert('操作提示',"保存成功");	
								}else{
									$.messager.alert('操作提示',ret.header["msg"],'error');	
								}
							},error : function(result){
								MaskUtil.unmask(); 
								$.messager.alert('操作提示','操作失败！','error');	
							}
						});
		 			}
				});
	}

	
	
	//加载执行利率，从系统配置获取
	function getPartnerGrossRate(){
		var partnerPushAccount = $("#partnerPushAccount").combobox('getValue');				//推送帐号
		var partnerGrossRate = $("#partnerGrossRate").textbox("getValue");					//执行利率
		var pid = $("#pid").val();
		
		//更新加载配置
		var updateLoadConfig = false;
		
		if((pid == '' || pid == 0 ) && (partnerPushAccount == "" || partnerPushAccount == 0 ) ){
			$("#partnerGrossRate").textbox("setValue","");	//执行利率
			return false;
		}else if((pid == '' || pid == 0 ) && partnerPushAccount > 0){
			updateLoadConfig = true;
		}else{
			if((partnerGrossRate == "" || partnerGrossRate == 0)  && partnerPushAccount > 0 ){
				updateLoadConfig = true;
			}
		}
		
		if(updateLoadConfig == true ){
			//小科
			var configName = "HNBX2_PARTNER_GROSS_RATE";
			//万通
			if(partnerPushAccount == 1){
				configName = "HNBX_PARTNER_GROSS_RATE";
			} 
			//动态获取配置
			$.ajax({
				type: "POST",
		        url: "<%=basePath%>sysConfigController/getByConfigName.action",
		        data:{"configName":configName},
		        dataType: "json",
		        success: function(result){
		        	if(result != null && result.configVal != ""){
		        		$("#partnerGrossRate").textbox("setValue",result.configVal);	//执行利率
		        	}
				},error : function(result){
					alert("加载执行利率失败！");
				}
			 });
				
		}
	}
	
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="projectList.action" method="post" id="searchFrom">
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
							<select class="easyui-combobox" id="queryPartnerNo"  name="partnerNo" style="width:120px" panelHeight="auto">
								<option value='' >--请选择--</option>
								<option value='xiaoying' >小赢</option>
							    <option value='tlxt' >统联信托</option>
								<option value='dianrongwang' >点荣网</option>
								<option value='nyyh'>南粤银行</option>
								<option value='hnbx'>华安保险</option>
							</select>
							
						</td>
						<input type="hidden" id="rows" name="rows">
						<input type="hidden" id="page" name="page" value='1'>
						<td>
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
							<a href="#" onclick="majaxReset()" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
						</td>
					</tr>
				</table>
				</div>
			</form>
		
			<div style="padding-bottom: 5px">
				<%--新版本需求，爸爸不要你了--%>
<%--  			<shiro:hasAnyRoles name="PARTNER_INDEX,ALL_BUSINESS">
			   		<a href="javascript:void(0)" class="easyui-linkbutton" 
						iconCls="icon-add" plain="true" onclick="openPartner()">申请审核</a>
			  </shiro:hasAnyRoles>  --%>
			 
			</div>
		</div>
		
		<!-- 列表 -->
		<div class="perListDiv" style="height:100%;">
			<table id="grid" title="机构合作项目" class="easyui-datagrid"
				style="height:100%; width: auto;"
				data-options="
				    url: 'projectList.action',
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
				    fitColumns:true" >
				    
				<!--
				 表头 -->
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true" align="center" halign="center"></th>
						<th data-options="field:'projectId',hidden:true" ></th>
						<th data-options="field:'requestFiles',hidden:true" ></th>
						<th data-options="field:'projectNumber',width:80,sortable:true" align="center" halign="center">项目编号</th>
						<th data-options="field:'projectName',width:80,sortable:true" align="center" halign="center">项目名称</th>
						<th data-options="field:'projectSource',formatter:partnerGlobal.formatProjectSource,width:80,sortable:true" align="center" halign="center">项目来源</th>
						<th data-options="field:'businessCategoryStr',width:80,sortable:true" align="center" halign="center">业务类型</th>
						<th data-options="field:'loanAmount',width:80,sortable:true" align="center" halign="center">借款金额</th>
						<th data-options="field:'projectStatus',formatter:partnerGlobal.formatterState,width:80,sortable:true" align="center" halign="center">项目状态</th>
						<th data-options="field:'partnerNo',formatter:partnerGlobal.partnerMoreFormat,width:80,sortable:true" align="center" halign="center">合作机构</th>
						<th data-options="field:'projectStatusTime',width:80,sortable:true" align="center" halign="center">最后状态时间</th>
						<shiro:hasAnyRoles name="PARTNER_INDEX,ALL_BUSINESS">
							<th data-options="field:'cz',formatter:partnerGlobal.formatPartnerNoRequestBtn,width:80,sortable:true"  align="center" halign="center">操作</th>
						</shiro:hasAnyRoles>
 
					</tr>
				</thead>
			</table>
		</div>
		
		
		<!-- 申请二级菜单 -->
	  <div class="partnerNoRequest_box">
			<div id=partnerNoRequestDiv onMouseOver="showPartnerNoTipsInfo('1')"  onMouseOut="hidePartnerNoTipsInfo('1')">
			<!-- <div id=partnerNoRequestDiv > -->
				<a id="partnerNoRequestBtn_xy" onclick="" href="javascript:void(0);" >小赢</a>
				<a id="partnerNoRequestBtn_tlxt" onclick="" href="javascript:void(0);" >统联信托</a>
				<a id="partnerNoRequestBtn_drw" onclick="" href="javascript:void(0);" >点荣网</a>
				<a id="partnerNoRequestBtn_nyyh" onclick="" href="javascript:void(0);" >南粤银行</a>
				<a id="partnerNoRequestBtn_hnbx" onclick="" href="javascript:void(0);" >华安保险</a>
			</div>
	  </div>
		
		
		<!-- 弹层基本信息 -->
		<div id="add_partner_info">		
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="savePartnerInfo(1)">保存</a>
			<a id="uploadFileBtn" style="display: none;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="openUpload('','add');">上传资料</a>
			<a id="uploadFileBatchBtn" style="display: none;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="batchUploadFileToThird();">批量上传南粤</a>
			<a id="uploadFileBatchBtn_hnbx" style="display: none;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="batchUploadFileToThird_hnbx();">批量上传华安保险</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="applyPartnerInfo()">提交审核</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#add_partner').dialog('close')">关闭</a>
		</div>
		<div id="add_partner" class="easyui-dialog" fitColumns="true"  title="合作项目提交审核"
		     style="width:850px;top:100px;padding:10px;" buttons="#add_partner_info" closed="true" >
			
			<section class="hearder" id="header">
			    <section class="info_top info_top_auto">
				        <div class="order_tab">
				        	<a href="#" id="info" class="first current">基本信息</a>
				        	<a href="#" onclick="showPerBase()" class="last">客户附加信息</a>
				        	<a href="#" id="getProjectFile_btn" onclick="getProjectFile()" class="last">材料信息</a>
				        	<a href="#"  onclick="getPublicManList()" class="last">共同借款人</a>
				        	<a href="#"  onclick="getWorkflowList()" class="last">审批记录</a>
				        	<a href="#"  onclick="getOriginalLoan()" class="last">赎楼信息</a>
				        	<a href="#"  onclick="getHouseInfo()" class="last">物业信息</a>
				        </div>
				</section>
			</section>
		
			<div style="left:0;">
				<!-- 基本信息 -->
				<div class="nearby" style="display: block" id="nearby_project">
					<form id="partnerInfo" action="<%=basePath%>projectPartnerController/saveProjectPartner.action" method="POST">
						<table class="beforeloanTable">
							<input type="hidden" name="pid" id="pid"/>
							<input type="hidden" name="projectId" id="projectId"/>
							<input type="hidden" name="businessType" id="businessType"/>
							<input type="hidden" name="businessCategoryStr" id="businessCategoryStr"/>
							<input type="hidden" name="requestFiles" id="requestFiles"/>
							<input type="hidden" name="pmUserId"/>
							<input type="hidden" name="city" value="深圳"/>
							<input type="hidden" id="house_province_code" name="houseProvinceCode" />
							<input type="hidden" id="house_city_code" name="houseCityCode" />
							<input type="hidden" id="acctId" name="acctId" />
							<!-- 开户绑卡标识 -->
							<input type="hidden" id="isPartnerOpenAccount" name="isPartnerOpenAccount" />
												
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
								<input id="userName" class="text_style easyui-textbox" readonly="readonly" name="userName"  style="width:150px;"   />
								
								<input type="button" id="btn_searchCusCredit" class="cus_save_btn" style="width: 70px;" value="查询征信" onclick="searchCusCredit();"/> 
								
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
						  	<td class="label_right1" ><font color="red">*</font>借款天数：</td>
						     <td><input type="text" class="text_style easyui-textbox" id="applyDate" name="applyDate" data-options="required:true"/></td>
						  	<td class="label_right1">借款金额：</td>
							 <td><input type="text" id="applyMoney" class="text_style easyui-textbox" readonly="readonly" name="applyMoney"/></td>
						  	<td class="label_right1" ><font id="applyLoanDateShowFont" color="red">*</font>申请放款日期：</td>
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
						  <tr class="">
						  	<td class="label_right1" ><font color="red">*</font>省份：</td>
						     <td>
						     	<select id="province_code" name="provinceCode" class="easyui-combobox" editable="false" style="width:129px;" />
						     </td>
						  	<td class="label_right1" ><font color="red">*</font>城市：</td>
						     <td>
						     	<select id="city_code" name="cityCode" class="easyui-combobox" editable="false" style="width:129px;" />
						     </td>
						  </tr>
						  <tr class="partnerNoShowTr">
						  	<td class="label_right1" ><font color="red">*</font><font class="paymentFontStr"> 借款人银行：</font></td>
						     <td >
						     	<select id="payment_bank" name="paymentBank" class="easyui-combobox" editable="false" style="width:129px;" />
						     </td>
						  	<td class="label_right1"><font color="red">*</font><font class="paymentFontStr">借款人户名：</font></td>
							 <td><input  class="text_style easyui-textbox" name="paymentAcctName" id="payment_acct_name" data-options="required:true,validType:'length[1,50]'"/></td>
						  	<td class="label_right1" ><font color="red">*</font><font class="paymentFontStr">借款人账号：</font></td>
						     <td>
						     	<input class="easyui-textbox" name="paymentAcctNo" id="payment_acct_no" data-options="required:true,validType:'length[1,25]'"/>
						     </td>
						  </tr>
						  <tr  class="partnerNoNyyhShowTr">
						  	 <td class="label_right1" ><font color="red">*</font>借款人预留手机号：</td>
						     <td>
						     	<input type="text" id="payment_bank_phone" name="paymentBankPhone"  class="easyui-textbox"  data-options="required:true,validType:'length[1,11]'"/>
						     </td>
						  	 <td class="label_right1" ><font color="red">*</font>借款期限（月）：</td>
						     <td>
						     	<input type="text" id="loan_period_limit" name="loanPeriodLimit"  class="easyui-numberbox"  data-options="required:true,validType:'length[1,3]',min:1,max:999"/>
						     </td>
						  	 <td class="label_right1" ><font color="red">*</font>他行有无未结清信用贷款：</td>
						     <td>
						     	<select id="is_credit_loan" name="isCreditLoan"  panelHeight="150" class="easyui-combobox" data-options="required:true" style="width:160px;">
									<option value="0">请选择</option>
									<option value="2">无</option>
									<option value="1">有</option>
								</select>								
						     </td>
						  </tr>
						  <tr  class="partnerNoNyyhShowTr">
						  	 <td class="label_right1" ><font color="red">*</font>借款人银行联行行号：</td>
						     <td>
						     	<input type="text" id="payment_bank_line_no" name="paymentBankLineNo"  class="easyui-textbox"  data-options="required:true,validType:'length[1,50]'"/>
						     </td>
						  	 <td class="label_right1 partnerNoNyyhShowTd_openAccount" ><font color="red">*</font>开户绑卡验证码：</td>
						     <td class="partnerNoNyyhShowTd_openAccount">
						     	<input type="text" id="otp" name="otp"  class="easyui-textbox"  data-options="required:true,validType:'length[1,50]'"/>
						     	<input type="button" id="btn_openAccount" class="cus_save_btn" style="width: 70px;" value="开户绑卡" onclick="openAccount();"/> 
						     	<input type="button" id="btn_getOtp" class="cus_save_btn" style="width: 70px;" value="获取验证码" onclick="getOtp();"/> 
						     </td>
						     
						  </tr>
						  
						  <!-- 打款信息========================== -->
						  <tr  class="payBankShowTr">
						  	<td class="label_right1" style="width:160px;" ><font color="red">*</font><font class="payFontStr">打款银行/公司：</font></td>
						     <td>
						     	<select id="pay_bank_name" name="payBankName" class="easyui-combobox" editable="false" style="width:129px;" />
						     </td>
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行支行：</font></td>
						     <td>
						     	<input id="pay_bank_branch" name="payBankBranch" class="easyui-textbox" data-options="required:true,validType:'length[1,15]'"  />
						     </td>
						  </tr>
						  <tr  class="payBankShowTr">
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行开户名：</font></td>
						     <td>
						     	<input id="pay_acct_name" name="payAcctName" class="easyui-textbox"  data-options="required:true,validType:'length[1,50]'" />
						     </td>
						  	<td class="label_right1" ><font color="red">*</font><font class="payFontStr">打款银行帐号：</font></td>
						     <td>
						     	<input id="pay_acct_no" name="payAcctNo" class="easyui-textbox" data-options="required:true,validType:'length[1,25]'"/>
						     </td>
						   </tr>
						   <tr class="payBankShowTr">
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
					     		<input type="text" id="partnerGrossRate" name="partnerGrossRate" class="text_style easyui-textbox"  data-options="required:true"/>
					     	</td>
						  	 <td class="label_right1" ><font color="red">*</font><font class="payFontStr">推送帐号：</font></td>
						     <td>
						     	<select id="partnerPushAccount" name="partnerPushAccount" class="easyui-combobox"  style="width:129px;" >
						     		<option value="">请选择</option>
						     		<option value="2">小科</option>
						     		<option value="1">万通</option>
						     	</select>
						     </td>
						  </tr>
 
						  <tr>
						   <td class="label_right1">备注：</td>
							<td colspan="3"><input name="remark" id="remark" class="easyui-textbox" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'" ></td>
						  </tr>
						  <tr>
						  	<td class="label_right1">操作人：</td>
							 <td><input type="text" id="cert_Number" class="text_style easyui-textbox" readonly="readonly" name="pmUserName"/></td>
						  </tr>
						 </table> 
					</form>	  
				</div>
				
				<!-- 加载客户附加信息 -->
				<div class="nearby" id="nearby_cus_base" style="display: block">
					
				</div>
			     
			    <!-- 附件 -->
				<div class="nearby" id="nearby_project_file_table">
					<table id="project_file_table" class="" fitColumns="true" style="width:790px;height:330px;"
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
								<th data-options="field:'partnerId',hidden:true" ></th>
								<th data-options="field:'fileUrl',hidden:true" ></th>
								<th data-options="field:'fileSize',hidden:true" ></th>
								<th data-options="field:'pid',checkbox:true" ></th>
								<th data-options="field:'fileName'" >文件名称</th>
								<th data-options="field:'partnerNo',formatter:partnerGlobal.partnerFormat" >合作机构</th>
								<th data-options="field:'accessoryType',formatter:partnerGlobal.accessoryTypeFormat" >文件类型</th>
								<th data-options="field:'accessoryChildType',formatter:partnerGlobal.accessoryChildTypeFormat,width:120,sortable:true" >文件子类型</th>
								<th data-options="field:'fileType'" >文件种类</th>
								<th data-options="field:'thirdFileUrl',formatter:partnerGlobal.thirdFileUrlFormat" >是否上传第三方</th>
								<th data-options="field:'cz',formatter:projectFileCzFormat,width:120,sortable:true" >操作</th>
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
					<table id="workflow_table" class="" fitColumns="true" style="width:800px;min-height:330px;"
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
								<th data-options="field:'cz',formatter:originalLoanCzFormat"  align="center" halign="center" >编辑</th>
							</tr>
						</thead>
				   	</table>
				   	
				   	
					<!-- 编辑 原贷款银行信息 -->
					<div id="originalLoan_data"  class="easyui-dialog" style="width:600px;height:auto;top:100px;padding:10px 20px;" closed="true">
						<form id="originalLoan_data_form" name="originalLoan_data_form" method="post" action="<%=basePath%>beforeLoanController/saveOriginalLoanInfo.action" >
							<!-- 原贷款银行id -->
							<input type="hidden" name="originalLoanId" id="originalLoan_data_originalLoanId">
							<input type="hidden" id="originalLoan_data_projectId">
							<table>
								<tr>
									<td width="120" align="right" height="28">原贷款银行：</td>
									<td><span id="originalLoan_data_oldLoanBankStr"></span></td>
								</tr>
								<tr>
									<td class="label_right1"><font color="red">*</font>原贷款供楼帐号：</td>
									<td>
										 <input id="originalLoan_data_oldLoanAccount" name="oldLoanAccount"  data-options="required:true" class="easyui-textbox" style="width:270px;"/>
									</td>
								</tr>
 
							</table>
						</form>
						<div class="pb10" style="text-align: center; padding-top: 15px;">
							<input type="button"  class="cus_save_btn" name="save" value="保     存" onclick="updateOriginalLoan();"/>&nbsp;&nbsp;&nbsp; 
						</div>	 
					</div>
				
				
					<form id="foreclosure_form" action="<%=basePath%>projectPartnerController/updateForeclosureByPartner.action" method="POST">
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
							 	<td><input type="text" class="text_style easyui-textbox"  id="foreclosure_table_newRecePerson" name="newRecePerson"  /></td>
							 	<td class="label_right1"><font color="red">*</font>新贷款收款银行</br>(回款)：</td>
							 	<td><input type="text" class="text_style easyui-textbox"  id="foreclosure_table_newReceBank" name="newReceBank"/></td>
						  	</tr>
							<tr>
								<td class="label_right1">新贷款收款户名</br>(回款)：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="foreclosure_table_paymentName" name="paymentName"/></td>
								<td class="label_right1">新贷款收款账号</br>(回款)：</td>
							 	<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="foreclosure_table_paymentAccount" name="paymentAccount" style="width:200px;"/></td>
						  	</tr>
						  	
						  	
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
					
					<div class="pb10" style="text-align: center; padding-top: 15px;">
						<input type="button" class="cus_save_btn" name="save" value="保     存" onclick="updateForeclosureByPartner();"/>
					</div>	  
					
				</div>
				<!-- 物业信息 -->
				<div class="nearby" id="nearby_house_table" style="display: block">
					<table id="house_info_table" class=""   style="width:750px;height: auto;"
								     data-options="
								     	url: '',
								     	method: 'post',
								     	rownumbers: true,
							    		fitColumns:true,
								    	singleSelect: false,
									    pagination: false,
									    nowrap:false">
						<thead>
							<tr style="height: auto;">
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
								<th data-options="field:'houseAddress',width:110,formatter:partnerGlobal.formatTooltip"  align="center" halign="center" >地址</th>
								<th data-options="field:'cz',formatter:estateCzFormat"  align="center" halign="center" >操作</th>
							</tr>
						</thead>
					</table>  
					
					<!-- 编辑物业信息 -->
					<div id="project_estate_data"  class="easyui-dialog" style="width:600px;height:auto;top:100px;padding:10px 20px;" closed="true">
						<form id="project_estate_data_form" name="project_estate_data_form" method="post" action="<%=basePath%>projectPartnerController/updatePartnerRefund.action" >
							<!-- 项目id -->
							<input type="hidden" name="houseId" id="project_estate_data_houseId">
							<input type="hidden" name="projectId" id="project_estate_data_projectId">
							
							<!-- 带上，不然后更新  -1 表示不更新-->
							<input type="hidden" name="area"  id="project_estate_data_area" value="-1">
							<input type="hidden" name="tranasctionMoney" id="project_estate_data_tranasctionMoney" value="-1" >
							<input type="hidden" name="evaluationPrice" id="project_estate_data_evaluationPrice" value="-1" >
							
							<table>
								<tr>
									<td width="120" align="right" height="28">物业名称：</td>
									<td><span id="project_estate_data_houseName"></span></td>
								</tr>
								
									<tr>
										<td class="label_right1"><font color="red">*</font>评估净值：</td>
										<td>
											 <input id="project_estate_data_evaluationNet" name="evaluationNet"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
										</td>
									</tr>
								
								<tr>
									<td width="120" align="right" height="58"><font color="red">*</font>产权人占比：</td>
									<td>
										<input id="project_estate_data_propertyRatio" name="propertyRatio"  data-options="required:true" class="easyui-textbox" style="width:300px"/>
									</td>
								</tr>
								<tr>
									<td width="120" align="right" height="28"></td>
									<td>
										<font color="red">产权人占比格式：权属人(占比);
											</br>多个用英文逗号隔开，如张三(50%),李四(50%)
										</font>
									</td>
								</tr>
								<tr>
									<td class="label_right1">首期款金额：</td>
									<td>
										 <input id="project_estate_data_downPayment" name="downPayment"  data-options="min:-2,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
									</td>
								</tr>
								<tr>
									<td class="label_right1">购房定金：</td>
									<td>
										 <input id="project_estate_data_purchaseDeposit" name="purchaseDeposit"  data-options="min:-2,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
									</td>
								</tr>
								<tr>
									<td class="label_right1">购房余款：</td>
									<td>
										 <input id="project_estate_data_purchaseBalance" name="purchaseBalance"  data-options="min:-2,max:9999999999,precision:2,groupSeparator:','" class="easyui-numberbox"/>
									</td>
								</tr>
							</table>
						</form>
						
						<div class="pb10" style="text-align: center; padding-top: 15px;">
							<input type="button" id="cus_save_btn" class="cus_save_btn" name="save" value="保     存" onclick="updateProjectEstate();"/>&nbsp;&nbsp;&nbsp; 
						</div>	 
					</div>
					
				 
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
		
		<!-- 文件上传开始 -->
		<div id="dialog_data"  class="easyui-dialog" style="overflow:scroll; width:600px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons">
			<form id=uploadFileForm name="uploadFileForm" method="post" enctype="multipart/form-data">
				<!-- 项目id -->
				<input type="hidden" name="partnerId" id="partnerId_file">
				<input type="hidden" name="projectId" id="projectId_file">
				<input type="hidden" name="pid" id="pid_file">
				<input type="hidden" name="fileSize" id="fileSize_file">
				<!-- 标识文件是新增还是修改 -->
				<input type="hidden" id="file_operator_type" value="add">
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
							<select id="accessoryType" name="accessoryType" panelHeight="260" class="easyui-combobox" style="width:129px;">
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
								<option value="10">南粤银行材料</option>
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
							<input type="hidden" id="isUpdateFile" name="isUpdateFile" value="0"/>
								<div class="uploadmutilFile" style="margin-top: 10px;">
							        <input type="file" name="uploadify" id="uploadify"/>
							    </div>
							  <div class="uploadSimpleFile">
							  	<input id="file"  name="file"  class="easyui-filebox" class="download"  data-options="buttonText:'选择文件',missingMessage:'选择一个文件',onChange:function(){$('#isUpdateFile').val(1)}" style="width:240px;" required="true"/>
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
						<td colspan="2">
					        <%--用来作为文件队列区域--%>
					        <div id="fileQueue" style="max-height:200;overflow:scroll; right:50px; bottom:100px;z-index:999">
					        </div>
						</td>
					</tr>
					<tr>
						<td width="160" align="right" height="28">备注：</td>
						<td><textarea id="remark_file" name="remark" rows="3" cols="30" maxlength="80"></textarea></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="data-buttons">
			<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="uploadPartnerFile()" >保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_data').dialog('close')">取消</a>
		</div>
		<!-- 文件上传结束 -->
		

		
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
			url:			"<%=basePath%>projectPartnerController/editPerBaseInit.action?acctId="+acctId+"&flag=1",
			cache:			false,
			contentType:	'application/html; charset=utf-8',
			success: function(data){
				$("#nearby_cus_base").html(data);
			},error: function(data){
				console.log("加载失败");
			}
		});
	}
	
	//查询客户征信（后台添加）
	function searchCusCredit(){
		var acctId = $("#acctId").val();
		var projectId = $("#projectId").val();
		var businessType = 0 ; 
		$.messager.confirm("操作提示","确定查询客户征信?",function(r) {
 			if(r){
 				$.ajax({
 					type: "POST",
 			        url: "<%=basePath%>cusCreditController/searchCusCredit.action",
 			        data:{"acctId":acctId,"projectId":projectId,"businessType":businessType},
 			        dataType: "json",
 			        timeout:100000,
 			        beforeSend:function (){
 			        	MaskUtil.mask("查询中,请耐心等候......");  
 			        },
 			        success: function(result){
 			        	//MaskUtil.unmask();
 			        	$.messager.alert('操作提示',result.header.msg);
 					},error : function(result){
 						//MaskUtil.unmask();
 						$.messager.alert('操作提示',"查询失败！",'error');	
 					},
 					complete : function(){
 						MaskUtil.unmask();
 					}
 				 });
 			}
		});
	}
	
	
	//用户开户验证码获取接口
	function getOtp(){
		var paymentBankPhone = $("#payment_bank_phone").val();	//借款人预留手机号
		var isPartnerOpenAccount = $("#isPartnerOpenAccount").val();	//开户绑卡标识
		
		if(isPartnerOpenAccount != null && isPartnerOpenAccount == 1){
			$.messager.alert("操作提示","客户己经开户绑卡成功，不允许重复操作","error"); 
			return false;
		}else if(paymentBankPhone == null || $.trim(paymentBankPhone) ==""){
			$.messager.alert("操作提示","请填写借款人预留手机号","error"); 
			$('#payment_bank_phone').next('span').find('input').focus();
			return false;
		}
		
		$.messager.confirm("操作提示","确定获取开户绑卡验证码?",function(r) {
 			if(r){
 				$.ajax({
 					type: "POST",
 			        url: "<%=basePath%>partnerCustomerBankController/getOtp.action",
 			        data:{"bankMobileNo":paymentBankPhone},
 			        dataType: "json",
 			        timeout:600000,
 			        beforeSend:function (){
 			        	MaskUtil.mask("操作中,请耐心等候......");  
 			        },
 			        success: function(result){
 			        	if(result && result.header.success == true){
 			        		$.messager.alert('操作成功',result.header.msg);
 			        	}else{
 			        		$.messager.alert('操作失败',result.header.msg,'error');	
 			        	}
 					},error : function(result){
 						$.messager.alert('操作提示',"查询失败！",'error');	
 					},
 					complete : function(){
 						MaskUtil.unmask();
 					}
 				 });
 			}
		});
	}
	
	
	//客户开户绑卡（后台添加）
	function openAccount(){
		var acctId = $("#acctId").val();
		var paymentAcctNo = $("#payment_acct_no").val();		//借款人账号
		var paymentBankPhone = $("#payment_bank_phone").val();	//借款人预留手机号
		var otp = $("#otp").val();	//借款人预留手机号验证码
		var isPartnerOpenAccount = $("#isPartnerOpenAccount").val();	//开户绑卡标识
		
		if(isPartnerOpenAccount != null && isPartnerOpenAccount == 1){
			$.messager.alert("操作提示","客户己经开户绑卡成功，不允许重复操作","error"); 
			return false;
		}else if(acctId == null || $.trim(acctId) ==""){
			$.messager.alert("操作提示","客户acctId不能为空","error"); 
			return false;
		}else if(paymentAcctNo == null || $.trim(paymentAcctNo) ==""){
			$.messager.alert("操作提示","请填写借款人账号","error"); 
			$('#payment_acct_no').next('span').find('input').focus();
			return false;
		}else if(paymentBankPhone == null || $.trim(paymentBankPhone) ==""){
			$.messager.alert("操作提示","请填写借款人预留手机号","error"); 
			$('#payment_bank_phone').next('span').find('input').focus();
			return false;
		}else if(otp == null || $.trim(otp) ==""){
			$.messager.alert("操作提示","请填写开户绑卡验证码","error"); 
			$('#otp').next('span').find('input').focus();
			return false;
		}
		
		$.messager.confirm("操作提示","确定开户绑卡?",function(r) {
 			if(r){
 				$.ajax({
 					type: "POST",
 			        url: "<%=basePath%>partnerCustomerBankController/openAccount.action",
 			        data:{"acctId":acctId,"bankCard":paymentAcctNo,"bankMobileNo":paymentBankPhone,"otp":otp},
 			        dataType: "json",
 			        timeout:600000,
 			        beforeSend:function (){
 			        	MaskUtil.mask("操作中,请耐心等候......");  
 			        },
 			        success: function(result){
 			        	if(result && result.header.success == true){
 			        		$.messager.alert('操作成功',result.header.msg);
 			        		//赋值
 			        		$("#isPartnerOpenAccount").val(1);
 			        		//隐藏按钮
 			        		$(".partnerNoNyyhShowTd_openAccount").hide();
 			        	}else{
 			        		$.messager.alert('操作失败',result.header.msg,'error');	
 			        	}
 					},error : function(result){
 						$.messager.alert('操作提示',"查询失败！",'error');	
 					},
 					complete : function(){
 						MaskUtil.unmask();
 					}
 				 });
 			}
		});
	}
	
	
	
	
	var Browser = new Object();
	Browser.ua = window.navigator.userAgent.toLowerCase();
	Browser.ie = /msie/.test(Browser.ua);
	Browser.moz = /gecko/.test(Browser.ua);

	var Position = {
		getElementLeft : function(o){
			(typeof o == "string") ? o = document.getElementById(o) : "";
			var L = 0;
			while (o){
				L += o.offsetLeft || 0;
				o = o.offsetParent;
			}
			return L;
		},
		getElementTop : function(o){
			(typeof o == "string") ? o = document.getElementById(o) : "";
			var T = 0;
			while (o){
				T += o.offsetTop || 0;
				o = o.offsetParent;
			}
			return T;
		}
	};
	function showPartnerNoTipsInfo(sID){
		//显示
		document.getElementById("partnerNoRequestDiv").style.display = "block";
		//动态改变div 下标
		$("#partnerNoRequestDiv").attr("onMouseOver","showPartnerNoTipsInfo("+sID+")");
	    document.getElementById("partnerNoRequestDiv").style.left = Position.getElementLeft('partnerNoRequestBtn'+sID) + -30+"px";
		if(Browser.moz){
		    document.getElementById("partnerNoRequestDiv").style.top = Position.getElementTop('partnerNoRequestBtn'+sID)  + 0 + "px";
		}
		else{
		    document.getElementById("partnerNoRequestDiv").style.top = Position.getElementTop('partnerNoRequestBtn'+sID)  + 0+ "px";
		}
		
		
		var projectId = $("#"+'partnerNoRequestBtn'+sID).attr("projectId");
		var partnerNo = $("#"+'partnerNoRequestBtn'+sID).attr("partnerNo");
		
		$("#partnerNoRequestBtn_xy").attr("onclick","openDiv(-1,"+projectId+",'xiaoying')");
		$("#partnerNoRequestBtn_tlxt").attr("onclick","openDiv(-1,"+projectId+",'tlxt')");
		$("#partnerNoRequestBtn_drw").attr("onclick","openDiv(-1,"+projectId+",'dianrongwang')");
		$("#partnerNoRequestBtn_nyyh").attr("onclick","openDiv(-1,"+projectId+",'nyyh')");
		$("#partnerNoRequestBtn_hnbx").attr("onclick","openDiv(-1,"+projectId+",'hnbx')");
		
		//设置对应的按钮和事件
		if(partnerNo!= null && partnerNo!= ''){
			var valList = partnerNo.split(";");
			for(var i = 0 ; i < valList.length; i ++){
				var tempVal = valList[i].split("##")[0];
				if(tempVal == 'xiaoying'){
					$("#partnerNoRequestBtn_xy").attr("onclick","openDiv("+valList[i].split("##")[1]+","+projectId+",'"+tempVal+"')");
				}else if(tempVal == 'tlxt'){
					$("#partnerNoRequestBtn_tlxt").attr("onclick","openDiv("+valList[i].split("##")[1]+","+projectId+",'"+tempVal+"')");
				}else if(tempVal == 'dianrongwang'){
					$("#partnerNoRequestBtn_drw").attr("onclick","openDiv("+valList[i].split("##")[1]+","+projectId+",'"+tempVal+"')");
				}else if(tempVal == 'nyyh'){
					$("#partnerNoRequestBtn_nyyh").attr("onclick","openDiv("+valList[i].split("##")[1]+","+projectId+",'"+tempVal+"')");
				}else if(tempVal == 'hnbx'){
					$("#partnerNoRequestBtn_hnbx").attr("onclick","openDiv("+valList[i].split("##")[1]+","+projectId+",'"+tempVal+"')");
				}
			}
		}
		
	}

	function hidePartnerNoTipsInfo(sID){
		document.getElementById("partnerNoRequestDiv").style.display = "none";
	}
 
	
	

</script>

