<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style>
.info_top{ width:100%; padding:5px 0;height:auto; overflow:hidden;  }
.info_top_auto{ color:#333; font-weight: normal;text-shadow: 0px; position: relative; padding:8px 0; height:auto; overflow:hidden; text-align:center;}

.order_tab{ width:100%; text-align:left;border-bottom:1px #ddd solid; }
.order_tab a{ display:inline-block;width:12%; color:#666;text-align:center; padding:2px 0; font-size:16px; height:28px; margin-left:2px; line-height:28px; border:1px #ddd solid;border-bottom:none; text-decoration:none;}
.order_tab .first{ border-radius:5px 5px 0 0; position:relative;}
.order_tab .last{ border-radius:5px 5px 0 0;margin-left:2px;}
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
		   
		   
		   var refundStatusTemp = $("#repaymentRepurchaseStatus").val();
		   var partnerNo = $("#partner_source_no").combobox('getValue');
		   if($(this).attr("id") == 'projectFileTab1'){
			   $("#uploadFileBtn").show();
			   file_table_id = "project_file_table_1";
		   }else if($(this).attr("id") =='projectFileTab2'){
			   $("#uploadFileBtn").show();
			   file_table_id = "project_file_table_2";
		   }else if($(this).attr("id") =='projectFileTab3'){
			   $("#uploadFileBtn").show();
			   file_table_id = "project_file_table_3";
		   }else{
			   $("#uploadFileBtn").hide();
			   file_table_id="";
		   }
		   if((partnerNo == 'xiaoying' || partnerNo == 'tlxt') && !(refundStatusTemp == 0 || refundStatusTemp == 1 || refundStatusTemp == 4)){
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
	   
	   //还款
	   $('#project_file_table_1').datagrid({onLoadSuccess : function(data){
			var rows = $('#project_file_table_1').datagrid('getRows');//获取当前页的数据行
			var requestFiles = $("#loanJusticeFiles").val();
			
			var repaymentRepurchaseType = $("#repaymentRepurchaseType").val();
			var repaymentVoucherPath = $("#repaymentVoucherPath").val();
			
			if(repaymentRepurchaseType == 1){
				if(repaymentVoucherPath != ''){
					var arr = repaymentVoucherPath.split(",");
					for(var i = 0; i < arr.length; i++){
						for (var j = 0; j < rows.length; j++) {
							if(arr[i] == rows[j].pid){
								$('#project_file_table_1').datagrid('checkRow', j);
							}
						}
					}
				}
			}
			
		}}); 
	   //回购
	   $('#project_file_table_2').datagrid({onLoadSuccess : function(data){
			var rows = $('#project_file_table_2').datagrid('getRows');//获取当前页的数据行
			var repaymentRepurchaseType = $("#repaymentRepurchaseType").val();
			var repaymentVoucherPath = $("#repaymentVoucherPath").val();
			if(repaymentRepurchaseType == 2){
				if(repaymentVoucherPath != ''){
					var arr = repaymentVoucherPath.split(",");
					for(var i = 0; i < arr.length; i++){
						for (var j = 0; j < rows.length; j++) {
							if(arr[i] == rows[j].pid){
								$('#project_file_table_2').datagrid('checkRow', j);
							}
						}
					}
				}
			}
		}}); 
	   //息费凭证
	   $('#project_file_table_3').datagrid({onLoadSuccess : function(data){
			var rows = $('#project_file_table_3').datagrid('getRows');//获取当前页的数据行
			var xiFeeVoucherPath = $("#xiFeeVoucherPath").val();
				if(xiFeeVoucherPath != ''){
					var arr = xiFeeVoucherPath.split(",");
					for(var i = 0; i < arr.length; i++){
						for (var j = 0; j < rows.length; j++) {
							if(arr[i] == rows[j].pid){
								$('#project_file_table_3').datagrid('checkRow', j);
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
 	   
	   $("#refundDate_refund").datebox();
	   
/* 		var accessoryTypeJson = [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "1", "lookupDesc": "客户基本信息" },
		                         { "pid": "2", "lookupDesc": "交易方基本信息" },{ "pid": "3", "lookupDesc": "房产信息" },
		                         { "pid": "4", "lookupDesc": "交易信息" },{ "pid": "5", "lookupDesc": "还款来源信息" },
		                         { "pid": "6", "lookupDesc": "征信信息" },{ "pid": "7", "lookupDesc": "合同协议信息" },
		                         { "pid": "9", "lookupDesc": "居间服务协议" },{ "pid": "8", "lookupDesc": "其他材料" }]; */
		var accessoryTypeJson = [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "5", "lookupDesc": "还款来源信息" }];
		$('#accessoryType').combobox({         
			data:accessoryTypeJson, //此为重点
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
		
		
 
		$('#repaymentRepurchaseType_refund').combobox({         
			data:partnerGlobal.repaymentTypeJson, //此为重点
	        valueField:'pid',
	        textField:'lookupDesc',
	        //如果需要设置多项隐含值，可使用以下代码
	        onLoadSuccess:function(){
	         },
			onSelect:function(){  
			 	var repaymentType = $("#repaymentRepurchaseType_refund").combobox('getValue');
				var partnerNo=$('#partner_no_refund').combobox('getValue');
				if(partnerNo == 'xiaoying'){
					$(".repaymentXifeeTr,.repaymentBankNameTr,.repaymentAcctNoTr").hide();
					$(".repaymentBankCodeTr,.repaymentAcctNameTr").show();
				}else if(partnerNo == 'tlxt'){
					if(repaymentType == 2){
						$(".repaymentBankNameTr,.repaymentAcctNameTr,.repaymentAcctNoTr,.repaymentXifeeTr").show();
					}else{
						$(".repaymentBankNameTr,.repaymentBankCodeTr,.repaymentAcctNameTr,.repaymentAcctNoTr").hide();
					}
				}
	       }
	    });
		$('#repaymentRepurchaseType_refund_tab').combobox({         
			data:partnerGlobal.repaymentTypeJson, //此为重点
	        valueField:'pid',
	        textField:'lookupDesc' 
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
		
	    
   		$('#partner_no,#partner_no_refund,#partner_source_no').combobox({         
   			data:partnerGlobal.partnerJson, //此为重点
   	        valueField:'pid',
   	        textField:'lookupDesc'
   	    });
   		
   		//绑定变更计算还款总金额事件
   		//南粤银行
   		$('#nyyh_refundDate_refund_before,#nyyh_refundLoanAmount_refund_before,#nyyh_refundXifee_refund_before,#nyyh_refundPenalty_refund_before,#nyyh_refundFine_refund_before,#nyyh_refundCompdinte_refund_before').textbox({  
   			  onChange: function(value){  
   				caclRefundTotalAmount('nyyh');
   			  }
   			}); 
   		
   		//华安保险
   		$('#refundLoanAmount_updateRepaymentRepurchaseStatus,#refundXifee_updateRepaymentRepurchaseStatus').textbox({  
 			  onChange: function(value){  
 				caclRefundTotalAmount('hnbx');
 			  }
 			}); 
	   
	});
	
	
	//打开申请窗口
	function addItem(projectId,rowData) {
		$('#project_file_table_1').datagrid('loadData', { total: 0, rows: [] });  
		$('#project_file_table_2').datagrid('loadData', { total: 0, rows: [] });  
		$('#project_file_table_3').datagrid('loadData', { total: 0, rows: [] });  
		
		$("#info").click();
		
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
					$('#add_partner').dialog('open').dialog('setTitle', "合作项目还款回购申请");
					$('#partnerInfo').form('load', row);
					
					$('#partner_source_no').combobox('setValue', partnerNo);
					$("#partner_source_no").combobox("disable");
					changePartnerNoShow(row.projectId,partnerNo);
					
					
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
					if(row.publicManList !=null ){
						$('#public_man_table').datagrid('loadData',{"total":row.publicManList.length,"rows":row.publicManList}); 
					}
					//加载审批记录
					if(row.taskHistoryList != null ){
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
					setNotifyFileDown("partnerRefundFile_div",row.partnerRefundFile);
					
					//还款回购信息
					var payBankNameTemp = row.payBankName  == null ? "--请选择--" : row.payBankName;
			 		setBankCombobox2("payBankName_refund_tab",0,payBankNameTemp);
			 		//加载回调信息
					$('#refund_tab_form').form('load', row);
					$("#repaymentRepurchaseType_refund_tab").combobox("disable");
					$("#payBankName_refund_tab").combobox("disable");
					//隐藏对应字段
					if(partnerNo == 'xiaoying'){
						$(".refund_tab_table_loanAmountFont").text("还款金额：");
						$(".refund_tab_table_tr_payBank").show();
						$(".refund_tab_table_td_xifee1,.refund_tab_table_td_xifee2,.refund_tab_table_tr_payAcctNo").hide();
					}else if(partnerNo == "tlxt"){
						$(".refund_tab_table_loanAmountFont").text("还款本金：");
						if(row.repaymentRepurchaseType == 2){
							$(".refund_tab_table_tr_payAcctNo,.refund_tab_table_tr_payBank").show();
						}else{
							$(".refund_tab_table_tr_payAcctNo,.refund_tab_table_tr_payBank").hide();
						}
					}
				}
		 });
		
		
	}
	
 
	
	
	// 设置并查询文件下载
	function setNotifyFileDown(divId,pid){
		
		console.log("setNotifyFileDown divId:"+divId);
		console.log("setNotifyFileDown pid:"+pid);
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
	function changePartnerNoShow(projectId,partnerNo){
		
		if(partnerNo == null){
			partnerNo=$('#partner_source_no').combobox('getValue');
		}
		console.log("changePartnerNoShow:partnerNo:"+partnerNo);
		
		if(partnerNo == "tlxt"){
			$("#projectFileTab1,#projectFileTab3,#projectFileTab4,#projectFileTab5").show();
			$("#projectFileTab2").hide();
			$(".payBankShowTr,.confirmLoanReason_tr,.confirmLoanShowTr,.partnerNoNyyhShowTr,.partnerNoHnbxShowTr").hide();
			$("#house_province_code_font,#house_city_code_font").hide();
			getProjectFile(projectId,1,partnerNo);
			getProjectFile(projectId,3,partnerNo);
		}else if(partnerNo == "xiaoying"){
			$(".partnerNoShowTr,.payBankShowTr,.partnerNoNyyhShowTr,.partnerNoHnbxShowTr").hide();
			$(".confirmLoanReason_tr,.confirmLoanShowTr").show();
			$("#projectFileTab1,#projectFileTab2,#projectFileTab5").show();
			$("#projectFileTab3,#projectFileTab4").hide();
			$("#house_province_code_font,#house_city_code_font").hide();
			getProjectFile(projectId,1,partnerNo);
			getProjectFile(projectId,2,partnerNo);
		}else if(partnerNo == "dianrongwang"){
			$(".partnerNoShowTr,.payBankShowTr").show();
			$(".confirmLoanReason_tr,.confirmLoanShowTr,.partnerNoNyyhShowTr,.partnerNoHnbxShowTr").hide();
			$("#projectFileTab1,#projectFileTab2,#projectFileTab3,#projectFileTab4,#projectFileTab5").hide();
			$("#house_province_code_font,#house_city_code_font").show();
		}else if(partnerNo == "nyyh"){
			$(".partnerNoShowTr,.partnerNoNyyhShowTr").show();
			$(".payBankShowTr").hide();
			$(".confirmLoanReason_tr,.confirmLoanShowTr,.partnerNoHnbxShowTr").hide();
			$("#projectFileTab1,#projectFileTab2,#projectFileTab3,#projectFileTab4,#projectFileTab5").hide();
			$("#house_province_code_font,#house_city_code_font").show();
		}else if(partnerNo == "hnbx"){
			$(".partnerNoShowTr,.payBankShowTr,.partnerNoHnbxShowTr").show();
			$("#house_province_code_font,#house_city_code_font").show();
			$(".partnerNoNyyhShowTr").hide();
			$("#projectFileTab1,#projectFileTab2,#projectFileTab3,#projectFileTab4,#projectFileTab5").hide()
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
		var projectDom ="";
		//上传资料
		if(rowData.repaymentRepurchaseStatus == 0 || rowData.repaymentRepurchaseStatus == 1 || rowData.repaymentRepurchaseStatus == 4 ){
			
			if(rowData.partnerNo =='xiaoying' || rowData.partnerNo == 'tlxt' ){
				projectDom += "    <a href='javascript:updatePartnerRefundInit("+index+")'><font color='blue'>编辑</font></a>";
				//projectDom += "    <a href='javascript:void(0)' onclick='openUpload("+index+",\"add\")'><font color='blue'>上传凭证</font></a>";
			}
		}	
		if (rowData.partnerNo == 'dianrongwang'){
			projectDom += "<a href='javascript:void(0)'  onclick='openRefundIndex("+rowData.pid+")' ><font color='blue'>还款列表</font></a>";
		}
		if(rowData.partnerNo == 'hnbx' && (rowData.repaymentRepurchaseStatus == 0 || rowData.repaymentRepurchaseStatus == 1 )){
			projectDom += "<a href='javascript:void(0)' onclick='updateRepaymentRepurchaseStatusInit("+index+")'><font color='blue'>修改还款状态</font></a>";
		}
		return projectDom;
	}
	
	//打开还款列表
	function openRefundIndex(partnerId){
		var url = "<%=basePath%>projectPartnerController/refundIndex.action?partnerId="+partnerId;
		parent.openNewTab(url,"还款列表",true);
	}
	
	
	//上传凭证
	function addVoucher(){
		var file2 = $('#file2').textbox('getValue');
		var proId = $('#pid').val();
		
		if(proId=="" || null==proId){
			$.messager.alert("操作提示","请重新选择项目","error"); 
			return false;
		}

		if(file2=="" || null==file2){
			$.messager.alert("操作提示","请选择文件资料","error"); 
			return false;
		}
		$('#addOrupdate').form('submit',{
			success:function(data){
				var dataObj=eval("("+data+")");//转换为json对象
				if(dataObj && dataObj.header.success ){
					$.messager.alert('操作提示',dataObj.header.msg);
					// 重新赎楼项目列表信息
 					$("#grid").datagrid('load');
 					// 清空所选择的行数据
 					clearSelectRows("#grid");
 					$('#dialog_data').dialog('close');
				}else{
					$.messager.alert('操作提示',dataObj.header.msg,'error');	
				}
			}
		})
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
	
	
	//还款回购申请初始化
	function sendRepaymentInit(){
		
		
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			addItem(row[0].projectId,row[0]);
			
			if(!(row[0].repaymentRepurchaseStatus == 0 || row[0].repaymentRepurchaseStatus == 1 || row[0].repaymentRepurchaseStatus == 4) || row[0].partnerNo =='dianrongwang'){
				$("#sendRepayment_btn").hide();
			}else{
				$("#sendRepayment_btn").show();
			}
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	

	
	//获取附件   1:还款 2:回购 3:息费
	function getProjectFile(projectId,type,partnerNo){
		
		var rows = $('#project_file_table_'+type).datagrid('getRows');//获取当前页的数据行
		if(partnerNo == "" ||  partnerNo == null){
			partnerNo = $("#partner_source_no").combobox('getValue');
		}
		if(projectId == null || projectId == ""){
			projectId = $("#projectId").val();
		}
		var partnerId = $("#pid").val();
		console.log("getProjectFile:projectId:"+projectId+",partnerNo:"+partnerNo+",rows.length:"+rows.length);
		if(rows.length == 0 ){
			var url = "<%=basePath%>projectPartnerController/getProjectFileList.action?projectId="+projectId+"&partnerNo="+partnerNo+"&accessoryType=5&partnerId="+partnerId;
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
 
		$('#projectName_file').text(projectName);
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
		if(partnerNo == 'tlxt'){
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
 
	
	//发送还款回购
	function sendRepayment() {
		var rows_1 = $('#project_file_table_1').datagrid('getSelections');
		var rows_2 = $('#project_file_table_2').datagrid('getSelections');
		var rows_3 = $('#project_file_table_3').datagrid('getSelections');
		var partnerNo=$('#partner_source_no').combobox('getValue');
		
		
		var repaymentRepurchaseType = $("#repaymentRepurchaseType").val();
		if(repaymentRepurchaseType == null || repaymentRepurchaseType == "" || repaymentRepurchaseType == 0){
			$.messager.alert("操作提示","请先编辑还款信息再操作","error");
			return;
		}
		
		var repaymentVoucherPath ="" ;			//凭证文件
		
		if("tlxt" == partnerNo){
			if(rows_1.length == 0 ){
				$.messager.alert("操作提示","请选择还款凭证文件","error");
				return;
			}else if($("#refundLoanAmount").val() == "" || $("#refundLoanAmount").val() ==0){
				$.messager.alert("操作提示","请先填写还款本金再提交","error");
				return;
			}else if($("#refundDate").val() == ""){
				$.messager.alert("操作提示","请先填写还款日期再提交","error");
				return;
			}
			for (var i = 0; i < rows_1.length; i++) {
				if (i == 0) {
					repaymentVoucherPath += rows_1[i].pid;
				} else {
					repaymentVoucherPath += "," + rows_1[i].pid;
				}
			}
		}else if("xiaoying" == partnerNo){
			if(repaymentRepurchaseType == 1){
				if(rows_1.length == 0){
					$.messager.alert("操作提示","请选择还款凭证文件","error");
					return;
				}
				for (var i = 0; i < rows_1.length; i++) {
					if (i == 0) {
						repaymentVoucherPath += rows_1[i].pid;
					} else {
						repaymentVoucherPath += "," + rows_1[i].pid;
					}
				}
			}else if(repaymentRepurchaseType == 2){
				if(rows_2.length == 0){
					$.messager.alert("操作提示","请选择回购凭证文件","error");
					return;
				}
				for (var i = 0; i < rows_2.length; i++) {
					repaymentRepurchaseType = 2;		//还款
					if (i == 0) {
						repaymentVoucherPath += rows_2[i].pid;
					} else {
						repaymentVoucherPath += "," + rows_2[i].pid;
					}
				}
			}
		}
		
		var xiFeeVoucherPath ="" ;				//息费文件
		for (var i = 0; i < rows_3.length; i++) {
			if (i == 0) {
				xiFeeVoucherPath += rows_3[i].pid;
			} else {
				xiFeeVoucherPath += "," + rows_3[i].pid;
			}
		}
 
		$("#repaymentVoucherPath").val(repaymentVoucherPath);
		$("#xiFeeVoucherPath").val(xiFeeVoucherPath);
 
		var repaymentRepurchaseStatus = $("#repaymentRepurchaseStatus").val();
		if(repaymentRepurchaseStatus == 0 || repaymentRepurchaseStatus == 1 || repaymentRepurchaseStatus == 4){
				
			$.messager.confirm("操作提示","确认还款回购申请?",
					function(r) {
			 			if(r){
			 				$('#partnerInfo').form('submit', {
								url : "repaymentApply.action",
								onSubmit : function() {
									MaskUtil.mask("提交中,请耐心等候......");  
										return true; 
								},
								success : function(result) {
									MaskUtil.unmask();
									var ret = eval("("+result+")");
									var pid=ret.header["pid"];
									$("#pid").val(pid);
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
									alert("操作失败！");
								}
							});
			 			}
					});
		 
		}else{
			$.messager.alert("操作提示","只有未申请和未收到才能提交还款回购申请！","error");
		}
		
	}
	
	
	
	//更新还款金额初始化
	function updatePartnerRefundInit(rowIndex){
 
		$('#refundPartnerForm').form('reset');
		rowData = $('#grid').datagrid('getData').rows[rowIndex];
		
		var pid = rowData.pid;
		var projectName = rowData.projectName;				//项目名称
		var partnerNo = rowData.partnerNo;					//合作机构
		var repaymentRepurchaseType = rowData.repaymentRepurchaseType;	//还款回购类型
		var refundLoanAmount = rowData.refundLoanAmount;	//还款本金
		var refundXifee = rowData.refundXifee;				//还款息费
		var refundDate = rowData.refundDate;				//还款日期
		
		var payBankName = rowData.payBankName;				//还款银行
		var payBankCode = rowData.payBankCode;				//还款银行code
		var payAcctName = rowData.payAcctName;				//还款账号户名
		var payAcctNo = rowData.payAcctNo;					//还款帐号
		
		var payBankNameTemp = rowData.payBankName  == null ? "--请选择--" : rowData.payBankName;
		
		$("#pid_refund").val(pid);
		$('#projectName_refund').text(projectName);
		$('#partner_no_refund').combobox('setValue', partnerNo);
		$('#repaymentRepurchaseType_refund').combobox('setValue', repaymentRepurchaseType);
		$("#refundLoanAmountFont_refund").text(partnerNo == 'xiaoying' ? '还款金额' : '还款本金');
		
		if(partnerNo == 'xiaoying'){
			
			$(".repaymentXifeeTr,.repaymentBankNameTr,.repaymentAcctNoTr").hide();
			$(".repaymentBankCodeTr,.repaymentAcctNameTr").show();
		}else if(partnerNo == 'tlxt'){
			if(repaymentRepurchaseType == "2"){
				$(".repaymentBankNameTr,.repaymentAcctNameTr,.repaymentAcctNoTr,.repaymentXifeeTr").show();
				$(".repaymentBankCodeTr").hide();
			}else{
				$(".repaymentXifeeTr").show();
				$(".repaymentBankNameTr,.repaymentBankCodeTr,.repaymentAcctNameTr,.repaymentAcctNoTr").hide();
			}
	 		setBankCombobox2("payBankName_refund",0,payBankNameTemp);
		}
		
		
		$("#refundLoanAmount_refund").textbox('setValue',refundLoanAmount);
		$("#refundXifee_refund").textbox('setValue',refundXifee);
		$("#refundDate_refund").textbox('setValue',refundDate);
		$("#payBankName_refund").textbox('setValue',payBankName);
		$("#payAcctName_refund").textbox('setValue',payAcctName);
		$("#payAcctNo_refund").textbox('setValue',payAcctNo);
		$('#payBankCode_refund').combobox('setValue', payBankCode);
		
		$("#partner_no_refund").combobox("disable");
 
		$('#refund_data').dialog('open').dialog('setTitle','编辑还款信息');
		
	}
	//更新还款金额
	function updatePartnerRefund(){
		
		var partnerNo=$('#partner_no_refund').combobox('getValue');
		var payBankCode=$('#payBankCode_refund').combobox('getValue');
		var repaymentRepurchaseType=$('#repaymentRepurchaseType_refund').combobox('getValue');
		var refundLoanAmount = $("#refundLoanAmount_refund").numberbox('getValue');
		var refundDate = $("#refundDate_refund").numberbox('getValue');
		var refundXifee = $("#refundXifee_refund").numberbox('getValue');
		var payBankName=$('#payBankName_refund').combobox('getValue');
		var payAcctName = $("#payAcctName_refund").textbox('getValue');
		var payAcctNo = $("#payAcctNo_refund").textbox('getValue');
		
		var refundLoanAmountStr = partnerNo == "xiaoying" ? "还款金额" : "还款本金";
		
		if(repaymentRepurchaseType == "" || repaymentRepurchaseType == null || repaymentRepurchaseType == 0){
			$.messager.alert("操作提示","请选择还款回购类型","error"); 
			return false;
		}else if(refundDate == null || refundDate == ""){
			$.messager.alert('操作提示',"还款回购日期不能为空",'error');	
			return;
		}else if(!(refundLoanAmount >0)){
			$.messager.alert('操作提示',refundLoanAmountStr+"必须大于0!",'error');	
			return;
		}
		
		if(partnerNo == "xiaoying"){
			if(payBankCode == null || payBankCode == "--请选择--"){
				$.messager.alert('操作提示',"请选择还款银行",'error');	
				return;
			}else if(payAcctName == null || payAcctName == ""){
				$.messager.alert('操作提示',"请填写还款账号户名",'error');	
				return;
			}
			//设置银行名称
			$('#payBankName_refund').combobox('setValue',$('#payBankCode_refund').combobox('getText'));
			
		}else if(partnerNo == 'tlxt'){
			if(!(refundXifee >0)){
				$.messager.alert('操作提示',"还款息费必须大于0!",'error');	
				return;
			}
			if(repaymentRepurchaseType == "2"){
				if(payBankName == null || payBankName == "--请选择--"){
					$.messager.alert('操作提示',"请选择还款银行",'error');	
					return;
				}else if(payAcctName == null || payAcctName == ""){
					$.messager.alert('操作提示',"请填写还款账号户名",'error');	
					return;
				}else if(payAcctNo == null || payAcctNo == ""){
					$.messager.alert('操作提示',"请填写还款帐号",'error');	
					return;
				}
			}
		}
		
		
		$.messager.confirm("操作提示","确认更新还款信息?",
				function(r) {
		 			if(r){
		 				$("#partner_no_refund").combobox("enable");
		 				$('#refundPartnerForm').form('submit', {
							url : "updatePartnerRefund.action",
							onSubmit : function() {
									return true; 
							},
							success : function(result) {
								var ret = eval("("+result+")");
								//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
								if(ret && ret.header["success"] ){
									$.messager.alert('操作提示',ret.header["msg"]);
									// 刷新列表信息
				 					$("#grid").datagrid('load');
				 					// 清空所选择的行数据
				 					clearSelectRows("#grid");
				 					$('#refund_data').dialog('close');
								}else{
									$.messager.alert('操作提示',ret.header["msg"],'error');	
								}
							},error : function(result){
								alert("操作失败！");
							}
						});
		 			}
				});
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
	
	
	//同步还款计划(点荣网)
	function updateThirdLoanRefund(){
		
		var row = $('#grid').datagrid('getSelections');
		if(row.length != 1){
			$.messager.alert("操作提示","请选择一条数据","error");
			return;
		}
		
		if(row[0].partnerNo != 'dianrongwang'){
			$.messager.alert("操作提示","请选择合作机构为点荣网的数据","error");
			return;
		}
		if(row[0].repaymentRepurchaseStatus == 3){
			$.messager.alert("操作提示","该数据己还款完成，不允许操作","error");
			return;
		}
		
		$.messager.confirm("操作提示","确认同步还款计划?",
			function(r) {
	 			if(r){
					$.post("<%=basePath%>projectPartnerController/updateThirdLoanRefund.action",{pid : row[0].pid}, 
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
	}
	
	//更新同步第三方贷款状态
	function updteThirdLoanStatus(partnerNo_btn){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var partnerNo = row[0].partnerNo;
			
			if("dianrongwang" != partnerNo  && partnerNo_btn=='dianrongwang'){
				$.messager.alert("操作提示","请选择合作机构为点荣网的数据","error");
				return;
			}else if("nyyh" != partnerNo  && partnerNo_btn=='nyyh'){
				$.messager.alert("操作提示","请选择合作机构为南粤银行的数据","error");
				return;
			}
 
			$.messager.confirm("操作提示","确认同步还款状态?",
 					function(r) {
 			 			if(r){
 							$.post("<%=basePath%>projectPartnerController/updteThirdLoanStatus.action",{pid : row[0].pid,includePostLoanStatus:'true',thirdSyncStatusType:2}, 
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
	
	
	//提前还款初始化
	function updatePartnerRefundBeforeInit(){
		
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			rowData = row[0];
			
			var partnerNo = rowData.partnerNo;
			var repaymentRepurchaseStatus = rowData.repaymentRepurchaseStatus;
			
			if("dianrongwang" != partnerNo ){
				$.messager.alert("操作提示","请选择合作机构为点荣网的数据","error");
				return;
			}
			
			$("#applyAdvancedSettlement").show();
			
			if(!(repaymentRepurchaseStatus == 0 || repaymentRepurchaseStatus == 1 || repaymentRepurchaseStatus == 4 || repaymentRepurchaseStatus == 6
					|| repaymentRepurchaseStatus == 7 || repaymentRepurchaseStatus == 8 || repaymentRepurchaseStatus == 9)){
				//$.messager.alert("操作提示","该状态不允许操作","error");
				//return;
				$("#applyAdvancedSettlement").hide();
			}
			
			var pid = rowData.pid;
			var projectName = rowData.projectName;				//项目名称
			var partnerNo = rowData.partnerNo;					//合作机构
			var loanAmount = rowData.loanAmount;				//借款金额
			
			var refundLoanAmount = rowData.refundLoanAmount;	//还款金额
			var refundDate = rowData.refundDate;				//还款日期
	 
			$('#refundBeforePartnerForm').form('reset');
			$("#refundDate_refund_before").datebox();
			
			if(refundDate != null && refundDate != ""){
				$("#refundDate_refund_before").textbox("setValue",refundDate);
			}
			if(refundLoanAmount != null && refundLoanAmount > 0 ){
				$("#refundLoanAmount_refund_before").textbox("setValue",refundLoanAmount);
			}
			
			$("#pid_refund_before").val(pid);
			$('#projectName_refund_before').text(projectName);
			
			var partnerNoName = "";
			if(partnerNo == 'xiaoying'){
				partnerNoName = "小赢";
			}else if(partnerNo == 'tlxt'){
				partnerNoName = "统联信托";
			}else if(partnerNo == 'dianrongwang'){
				partnerNoName = "点荣网";
			}else if(partnerNo == 'nyyh'){
				partnerNoName = "南粤银行";
			}
			
			$('#partnerNo_refund_before').textbox('setValue',partnerNoName);
			$('#loanAmount_refund_before').textbox('setValue',loanAmount);
			
			//加载提前还款信息
			getAdvancedSettlement(pid);
			
			$('#refund_before_data').dialog('open').dialog('setTitle','提前还款信息');
			
			
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	//加载提前还款信息
	function getAdvancedSettlement(pid){
		//显示并加载	
		$.ajax({
			type: "POST",
	        url: "<%=basePath%>projectPartnerController/getAdvancedSettlement.action",
	        data:{"pid":pid},
	        dataType: "json",
	        success: function(result){
	        	var paymentList =result.header.paymentList;
	        	var optionStr = "";
	        	if(paymentList!= null && paymentList.length > 0 ){
		        	for(var i = 0; i<paymentList.length ; i++ ){
	       				var indexObj = paymentList[i];
		           			optionStr = optionStr + "<option value='" + indexObj.date 
											 		 + "' amount='"+ indexObj.amount+"' ";
		           			optionStr = optionStr +  ">" + indexObj.date_amount+  "</option>";
	       			}
	        	}
	        	$("#refundDate_refund_before_temp").empty();
	        	$("#refundDate_refund_before_temp").append(optionStr);	//追加
			},error : function(result){
				$("#refundDate_refund_before_temp").empty();
				alert("操作失败！");
			}
		 });
	}
	
	//设置提前还款金额
	function setRefundLoanAmount(){
		var refundDateObj = $("#refundDate_refund_before_temp option:selected"); 	//提前还款日期
		if(refundDateObj.val() != -1 ){
			$("#refundLoanAmount_refund_before").textbox("setValue",refundDateObj.attr("amount"));
			$("#refundDate_refund_before").textbox("setValue",refundDateObj.val());
		}else{
			$("#refundLoanAmount_refund_before").textbox("setValue","");
			$("#refundDate_refund_before").textbox("setValue","");
		}
	}
	
	
	//提交还款
	function applyAdvancedSettlement(){
		var refundDateObj = $("#refundDate_refund_before_temp option:selected"); 	//提前还款日期
		var refundDate = $("#refundDate_refund_before").textbox("getValue"); 	//提前还款日期
		var refundLoanAmount = $("#refundLoanAmount_refund_before").textbox("getValue");
		var amount = refundDateObj.attr("amount");
		
		if(refundDate == ""  ||  refundDate == -1){
			$.messager.alert("操作提示","请选择提前还款日期！","error");
			return ;
		}else if(refundLoanAmount == ""  || !refundLoanAmount > 0 ){
			$.messager.alert("操作提示","提前还款金额必须大于0！","error");
			return ;
		}
		
		var remark = "确定发送提前还款?";
		if(amount !="" && parseFloat(amount) > 0 &&  parseFloat(amount) != parseFloat(refundLoanAmount) ){
			remark = "还款金额跟初始不一致，确定发送提前还款?";
		}
		$.messager.confirm("操作提示",remark,
				function(r) {
		 			if(r){
		 				$('#refundBeforePartnerForm').form('submit', {
		 					url : "applyAdvancedSettlement.action",
		 					onSubmit : function() {
		 						 MaskUtil.mask("提交中,请耐心等候......");  
		 							return true; 
		 					},
		 					success : function(result) {
		 						MaskUtil.unmask();
		 						var dataObj=eval("("+result+")");//转换为json对象
		 						if(dataObj && dataObj.header.success ){
		 							$.messager.alert('操作提示',dataObj.header.msg);
		 							// 重新赎楼项目列表信息
		 		 					$("#grid").datagrid('load');
		 		 					// 清空所选择的行数据
		 		 					clearSelectRows("#grid")
		 		 					$('#refund_before_data').dialog('close');
		 						}else{
		 							$.messager.alert('操作提示',dataObj.header.msg,'error');	
		 						}
		 					},error : function(result){
		 						MaskUtil.unmask();
		 						$.messager.alert('操作提示',"操作失败",'error');	
		 					}
		 				});
		 			}
			});
	 
		
	}
	
	
	
	//提前还款初始化(南粤银行)
	function updateNyyhPartnerRefundBeforeInit(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			rowData = row[0];
			var partnerNo = rowData.partnerNo;
			var repaymentRepurchaseStatus = rowData.repaymentRepurchaseStatus;
			
			if("nyyh" != partnerNo ){
				$.messager.alert("操作提示","请选择合作机构为南粤银行的数据","error");
				return;
			}
			
			//可提交
			if(repaymentRepurchaseStatus == 0 || repaymentRepurchaseStatus == 1 || repaymentRepurchaseStatus == 4 ){
				$("#nyyh_prepaymentAdvance_btn").show();
				$("#nyyh_advance_btn").show();
			}else{
				$("#nyyh_prepaymentAdvance_btn").hide();
				$("#nyyh_advance_btn").hide();
			}
			
/* 			if(!(repaymentRepurchaseStatus == 0 || repaymentRepurchaseStatus == 1 || repaymentRepurchaseStatus == 4 || repaymentRepurchaseStatus == 6)){
				$.messager.alert("操作提示","该状态不允许操作","error");
				return;
			} */
			
			var pid = rowData.pid;
			var projectName = rowData.projectName;				//项目名称
			var partnerNo = rowData.partnerNo;					//合作机构
			var loanAmount = rowData.loanAmount+"";				//借款金额
			var refundLoanAmount = rowData.refundLoanAmount+"";	//还款金额
			var refundDate = rowData.refundDate;				//还款日期
			
			var refundXifee = rowData.refundXifee+"";		//还款总金额
			var refundTotalAmount = rowData.refundTotalAmount+"";		//还款总金额
			var refundPenalty = rowData.refundPenalty+"";				//还款违约金
			var refundFine = rowData.refundFine+"";				//还款罚息
			var refundCompdinte = rowData.refundCompdinte+"";				//还款复利
			
			var payAcctName = rowData.payAcctName;				//还款帐号户名
			var payAcctNo = rowData.payAcctNo;				//还款帐号
			
			$('#nyyh_refundBeforePartnerForm').form('reset');
			
			var partnerNoName = "";
			if(partnerNo == 'xiaoying'){
				partnerNoName = "小赢";
			}else if(partnerNo == 'tlxt'){
				partnerNoName = "统联信托";
			}else if(partnerNo == 'dianrongwang'){
				partnerNoName = "点荣网";
			}else if(partnerNo == 'nyyh'){
				partnerNoName = "南粤银行";
			}
			$("#nyyh_pid_refund_before").val(pid);
			$('#nyyh_projectName_refund_before').text(projectName);
			$('#nyyh_partnerNo_refund_before').textbox('setValue',partnerNoName);
			$('#nyyh_loanAmount_refund_before').textbox('setValue',loanAmount);
			
			$("#nyyh_refundDate_refund_before").datebox();
			$("#nyyh_refundDate_refund_before").textbox("setValue",refundDate);
			$("#nyyh_refundLoanAmount_refund_before").textbox("setValue",refundLoanAmount);
			$("#nyyh_refundXifee_refund_before").textbox("setValue",refundXifee);
			$('#nyyh_refundPenalty_refund_before').textbox('setValue',refundPenalty);
			$('#nyyh_refundFine_refund_before').textbox('setValue',refundFine);
			$('#nyyh_refundCompdinte_refund_before').textbox('setValue',refundCompdinte);
			$('#nyyh_refundTotalAmount_refund_before').textbox('setValue',refundTotalAmount);
			
			$("#nyyh_payAcctName_refund_before").textbox("setValue",payAcctName);
			$("#nyyh_payAcctNo_refund_before").textbox("setValue",payAcctNo);
			
			//默认加载字典还款银行
			loadRepaymentBankAccount();
			
			$('#nyyh_refund_before_data').dialog('open').dialog('setTitle','提前还款信息');
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	
	//提前还款试算（南粤银行）
	function nyyh_prepaymentAdvance(){
		var pid = $("#nyyh_pid_refund_before").val();
		
		//判断
		var refundDate= $("#nyyh_refundDate_refund_before").textbox('getValue');
		
		if(refundDate == "" || refundDate == null){
			$.messager.alert("操作提示","请选择提前还款日期","error"); 
			return false;
		}
		
		$.messager.confirm("操作提示","确定提前还款试算?",function(r) {
 			if(r){
 				MaskUtil.mask("查询中,请耐心等候......");  
 				$.ajax({
 					type: "POST",
 			        url: "<%=basePath%>projectPartnerController/prepaymentAdvance.action",
 			        data:{"pid":pid,"refundDate":refundDate},
 			        dataType: "json",
 			        success: function(result){
 			        	MaskUtil.unmask();
 			        	console.log(result);
 			        	if(result.header.success == true){
 			        		var data = result.body.data;
 			        		
 			        		console.log(data);
 			        		
 			        		//设置页面值
 			        		var nyyh_refundTotalAmount_refund_before = data.total_amt  == "" ? "0" :data.total_amt+"";
 			        		var nyyh_refundLoanAmount_refund_before = data.capital_amt  == "" ? "0" :data.capital_amt+"";
 			        		var nyyh_refundXifee_refund_before = data.interests_amt  == "" ? "0" :data.interests_amt+"";
 			        		var nyyh_refundPenalty_refund_before = data.penalty_amt  == "" ? "0" :data.penalty_amt+"";
 			        		var nyyh_refundFine_refund_before = data.fine_amt  == "" ? "0" :data.fine_amt+"";
 			        		var nyyh_refundCompdinte_refund_before = data.compdinte_amt  == "" ? "0" :data.compdinte_amt+"";
 			        		
 			        		$("#nyyh_refundTotalAmount_refund_before").textbox("setValue",nyyh_refundTotalAmount_refund_before); 
 			        		$("#nyyh_refundLoanAmount_refund_before").textbox("setValue",nyyh_refundLoanAmount_refund_before); 
 			        		$("#nyyh_refundXifee_refund_before").textbox("setValue",nyyh_refundXifee_refund_before); 
 			        		$("#nyyh_refundPenalty_refund_before").textbox("setValue",nyyh_refundPenalty_refund_before); 
 			        		$("#nyyh_refundFine_refund_before").textbox("setValue",nyyh_refundFine_refund_before); 
 			        		$("#nyyh_refundCompdinte_refund_before").textbox("setValue",nyyh_refundCompdinte_refund_before); 
 			        	}else{
 			        		$.messager.alert('操作提示',result.header.msg,'error');	
 			        	}
 					},error : function(result){
 						MaskUtil.unmask();
 						$.messager.alert('操作提示',"查询失败！",'error');	
 					}
 				 });
 			}
		});
	}
	
	//提交还款（南粤银行）
	function nyyh_advance(){
  		var nyyh_refundDate_refund_before = $("#nyyh_refundDate_refund_before").textbox('getValue');
  		var nyyh_refundLoanAmount_refund_before = $("#nyyh_refundLoanAmount_refund_before").textbox('getValue');
  		var nyyh_refundXifee_refund_before = $("#nyyh_refundXifee_refund_before").textbox('getValue');
  		var nyyh_refundPenalty_refund_before = $("#nyyh_refundPenalty_refund_before").textbox('getValue');
  		var nyyh_refundFine_refund_before = $("#nyyh_refundFine_refund_before").textbox('getValue');
  		var nyyh_refundCompdinte_refund_before = $("#nyyh_refundCompdinte_refund_before").textbox('getValue');
  		var nyyh_refundTotalAmount_refund_before = $("#nyyh_refundTotalAmount_refund_before").textbox('getValue');
		
  		
  		var nyyh_payAcctName_refund_before = $("#nyyh_payAcctName_refund_before").textbox('getValue');
  		var nyyh_payAcctNo_refund_before = $("#nyyh_payAcctNo_refund_before").textbox('getValue');
  		
		if(nyyh_refundDate_refund_before == null || nyyh_refundDate_refund_before == ""){
			$.messager.alert('操作提示',"请选择提前还款日期",'error');	
			return;
		}else if(nyyh_refundLoanAmount_refund_before == null || nyyh_refundLoanAmount_refund_before == ""){
			$.messager.alert('操作提示',"请填写提前还款本金额",'error'); 
			return false;
		}else if(nyyh_refundXifee_refund_before == null || nyyh_refundXifee_refund_before == ""){
			$.messager.alert('操作提示',"请填写提前还款利息",'error'); 
			return false;
		}else if(nyyh_refundPenalty_refund_before == null || nyyh_refundPenalty_refund_before == ""){
			$.messager.alert('操作提示',"请填写应还违约金额",'error'); 
			return false;
		}else if(nyyh_refundFine_refund_before == null || nyyh_refundFine_refund_before == ""){
			$.messager.alert('操作提示',"请填写应还罚息",'error'); 
			return false;
		}else if(nyyh_refundCompdinte_refund_before == null || nyyh_refundCompdinte_refund_before == ""){
			$.messager.alert('操作提示',"请填写应还复利金额",'error'); 
			return false;
		}
  		
		if(nyyh_refundTotalAmount_refund_before == null || nyyh_refundTotalAmount_refund_before == "" 
				|| parseInt(nyyh_refundTotalAmount_refund_before) <=0 ){
			$.messager.alert('操作提示',"应还总金额不能为空或者为0",'error'); 
			return false;
		}else if(nyyh_payAcctName_refund_before == null || nyyh_payAcctName_refund_before == ""){
			$.messager.alert('操作提示',"请填写还款帐号户名",'error'); 
			return false;
		}
		else if(nyyh_payAcctNo_refund_before == null || nyyh_payAcctNo_refund_before == ""){
			$.messager.alert('操作提示',"请填写还款帐号",'error'); 
			return false;
		}
		
		$.messager.confirm("操作提示","确认提交提前还款信息?",
				function(r) {
		 			if(r){
		 				$('#nyyh_refundBeforePartnerForm').form('submit', {
							url : "applyAdvancedSettlement.action",
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
				 					$('#nyyh_refund_before_data').dialog('close');
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
		 
	//计算还款总金额
	function caclRefundTotalAmount(partnerNo){
		//南粤银行
		if(partnerNo == 'nyyh'){
			var realTotal =  0 ;
	  		var nyyh_refundLoanAmount_refund_before = $("#nyyh_refundLoanAmount_refund_before").textbox('getValue');
	  		var nyyh_refundXifee_refund_before = $("#nyyh_refundXifee_refund_before").textbox('getValue');
	  		var nyyh_refundPenalty_refund_before = $("#nyyh_refundPenalty_refund_before").textbox('getValue');
	  		var nyyh_refundFine_refund_before = $("#nyyh_refundFine_refund_before").textbox('getValue');
	  		var nyyh_refundCompdinte_refund_before = $("#nyyh_refundCompdinte_refund_before").textbox('getValue');
	 		
	 		realTotal += parseFloat(nyyh_refundLoanAmount_refund_before == "" ? 0 : nyyh_refundLoanAmount_refund_before);
	 		realTotal += parseFloat(nyyh_refundXifee_refund_before == "" ? 0 : nyyh_refundXifee_refund_before);
	 		realTotal += parseFloat(nyyh_refundPenalty_refund_before == "" ? 0 : nyyh_refundPenalty_refund_before);
	 		realTotal += parseFloat(nyyh_refundFine_refund_before == "" ? 0 : nyyh_refundFine_refund_before);
	 		realTotal += parseFloat(nyyh_refundCompdinte_refund_before == "" ? 0 : nyyh_refundCompdinte_refund_before);
	 		realTotal =realTotal.toFixed(2);
	 		
			$("#nyyh_refundTotalAmount_refund_before").textbox('setValue',realTotal);		
		}else if(partnerNo == 'hnbx'){
			
			var realTotal =  0 ;
			//还款本金
	  		var refundLoanAmount_updateRepaymentRepurchaseStatus = $("#refundLoanAmount_updateRepaymentRepurchaseStatus").numberbox('getValue');
			//还款息费
	  		var refundXifee_updateRepaymentRepurchaseStatus = $("#refundXifee_updateRepaymentRepurchaseStatus").numberbox('getValue');
	 		
	 		realTotal += parseFloat(refundLoanAmount_updateRepaymentRepurchaseStatus == "" ? 0 : refundLoanAmount_updateRepaymentRepurchaseStatus);
	 		realTotal += parseFloat(refundXifee_updateRepaymentRepurchaseStatus == "" ? 0 : refundXifee_updateRepaymentRepurchaseStatus);
	 		realTotal =realTotal.toFixed(2);
	 		//还款总金额
			$("#refundTotalAmount_updateRepaymentRepurchaseStatus").textbox('setValue',realTotal);		
		}
		
	}
	
	/**
	 * 重新加载原贷款银行信息
	 */
	function loadProjectOriginalLoan(projectId){
		//获取原贷款信息
		var url = "<%=basePath%>beforeLoanController/getOriginalLoanByProjectId.action?projectId="+projectId;
		
		console.log(url);
		$('#originalLoan_table').datagrid('options').url = url;
		$('#originalLoan_table').datagrid('reload');
	}
	
	
	
	//加载还款银行帐号,不存在则加载
	function loadRepaymentBankAccount(){
		//还款帐号户名
		var nyyh_payAcctName_refund_before = $("#nyyh_payAcctName_refund_before").textbox("getValue");
		//还款帐号
		var nyyh_payAcctNo_refund_before = $("#nyyh_payAcctNo_refund_before").textbox("getValue");
		if($.trim(nyyh_payAcctName_refund_before) == "" && $.trim(nyyh_payAcctNo_refund_before) == ''){
			$.ajax({
					type: "POST",
			        url: "<%=basePath%>sysLookupController/getSysLookupValByChildType.action",
			        data:{"lookupType":'PARTNER_LOAN_BANK',"lookupVal":'PARTNER_REPAYMENT_BANK_1'},
			        dataType: "json",
			        success: function(result){
			        	console.log("加载还款银行帐号--------");
			        	console.log(result);
			        	if(result != null && result.lookupDesc != null){
			        		var tempArray = result.lookupDesc.split("_");
			        		if(tempArray!= null && tempArray.length == 2){
					        	$("#nyyh_payAcctName_refund_before").textbox("setValue",tempArray[0]);
					        	$("#nyyh_payAcctNo_refund_before").textbox("setValue",tempArray[1]);
			        		}
			        	}
					},error : function(result){
						console.log('加载还款银行帐号失败');	
					}
				 });
		}
	}
	
	
	
	
	
	//修改还款状态初始化
	function updateRepaymentRepurchaseStatusInit(rowIndex){
		
		$('#updateRepaymentRepurchaseStatus_form').form('reset');
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
		
		$("#pid_updateRepaymentRepurchaseStatus").val(pid);
		$("#partnerNo_updateRepaymentRepurchaseStatus").val(partnerNo);
		$('#projectName_updateRepaymentRepurchaseStatus').text(projectName);
		$('#partnerNoName_updateRepaymentRepurchaseStatus').text(partnerNoName);
		$('#loanAmount_updateRepaymentRepurchaseStatus').textbox("setValue",rowData.loanAmount);
			
		$('#updateRepaymentRepurchaseStatus_data').dialog('open').dialog('setTitle','修改还款状态信息');
	}
	
	
	//修改还款状态信息
	function updateRepaymentRepurchaseStatus(){
		
		var repaymentRepurchaseStatus= $("#repaymentRepurchaseStatus_updateRepaymentRepurchaseStatus").combobox('getValue');
		var refundDate= $("#refundDate_updateRepaymentRepurchaseStatus").textbox('getValue');
		var refundLoanAmount = $("#refundLoanAmount_updateRepaymentRepurchaseStatus").numberbox('getValue');
		var refundXifee = $("#refundXifee_updateRepaymentRepurchaseStatus").numberbox('getValue');
		var refundTotalAmount = $("#refundTotalAmount_updateRepaymentRepurchaseStatus").numberbox('getValue');
		
		if(repaymentRepurchaseStatus == ''){
			$.messager.alert("操作提示","请选择还款状态","error");
			return ;
		}else if(refundDate == ''){
			$.messager.alert("操作提示","请选择还款日期","error");
			return ;
		}else if(refundLoanAmount == ''){
			$.messager.alert("操作提示","请填写还款本金","error");
			return ;
		}else if(refundTotalAmount == null || refundTotalAmount == "" 
				|| parseInt(refundTotalAmount) <=0 ){
			$.messager.alert('操作提示',"还款总金额不能为空或者为0",'error'); 
			return false;
		}
		
		$.messager.confirm("操作提示","确认修改还款状态信息?",
			function(r) {
	 			if(r){
	 				$('#updateRepaymentRepurchaseStatus_form').form('submit', {
						url : "updateRepaymentRepurchaseStatus.action",
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
			 					$('#updateRepaymentRepurchaseStatus_data').dialog('close');
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
			<form action="repaymentList.action" method="post" id="searchFrom">
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
						
						<td width="80" align="right" height="25">还款状态 ：</td>
						<td>
							<select class="easyui-combobox" id="query_repaymentRepurchaseStatus" name="repaymentRepurchaseStatus" style="width:120px" panelHeight="auto">
								<option value='0' >--请选择--</option>
								<option value='1' >未申请</option>
							    <option value='2' >己申请</option>
								<option value='3' >确认收到</option>
								<option value='4' >未收到</option>
								<option value='5' >确认中</option>
								<option value='6' >己逾期</option>
								<option value='7' >坏帐</option>
								<option value='8' >正常</option>
								<option value='9' >宽限期内</option>
							</select>
 
						</td>
						<td width="80" align="right" >预计到期日期：</td>
						<td colsapn="2">
							<input name="fromPlanRefundDate" id="fromPlanRefundDate" class="easyui-datebox" editable="false"/> <span>~</span> 
							<input name="toPlanRefundDate" id="toPlanRefundDate" class="easyui-datebox" editable="false"/>
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
			
			<shiro:hasAnyRoles name="RE_PAYMENT_INDEX,ALL_BUSINESS">
			   		<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="sendRepaymentInit()">还款回购申请</a>
			  </shiro:hasAnyRoles>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="updateThirdLoanRefund()">同步还款计划(点荣网)</a>
						
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="updteThirdLoanStatus('dianrongwang')">同步还款状态(点荣网)</a>
						
				<shiro:hasAnyRoles name="RE_PAYMENT_INDEX,ALL_BUSINESS">
			   		<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="updatePartnerRefundBeforeInit()">渠道提前还款(点荣网)</a>
			  </shiro:hasAnyRoles>
			  <shiro:hasAnyRoles name="RE_PAYMENT_INDEX,ALL_BUSINESS">
			   		<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="updateNyyhPartnerRefundBeforeInit()">渠道提前还款(南粤银行)</a>
						
			  </shiro:hasAnyRoles>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="updteThirdLoanStatus('nyyh')">同步还款状态(南粤银行)</a>	
			</div>
		</div>
		<div class="perListDiv" style="height:100%;">
			<table id="grid" title="还款-回购" class="easyui-datagrid"
				style="height:100%; width: auto;" 
				data-options="
				    url: 'repaymentList.action',
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
						<th data-options="field:'repaymentVoucherPath',hidden:true"></th>
						<th data-options="field:'xiFeeVoucherPath',hidden:true"></th>
						<th data-options="field:'payBankName',hidden:true"></th>
						<th data-options="field:'payAcctName',hidden:true"></th>
						<th data-options="field:'payAcctNo',hidden:true"></th>
						<th data-options="field:'payBankCode',hidden:true"></th>
						
						<th data-options="field:'refundTotalAmount',hidden:true"></th>
						<th data-options="field:'refundPenalty',hidden:true"></th>
						<th data-options="field:'refundFine',hidden:true"></th>
						<th data-options="field:'refundCompdinte',hidden:true"></th>
						
						<th data-options="field:'projectNumber',width:80,sortable:true" align="center" halign="center">项目编号</th>
						<th data-options="field:'projectName',width:80,sortable:true" align="center" halign="center">项目名称</th>
						<th data-options="field:'projectSource',formatter:partnerGlobal.formatProjectSource,width:80,sortable:true" align="center" halign="center">项目来源</th>
						<th data-options="field:'businessCategoryStr',width:80,sortable:true" align="center" halign="center">业务类型</th>
						<th data-options="field:'partnerNo',formatter:partnerGlobal.partnerFormat,width:80,sortable:true" align="center" halign="center">合作机构</th>
						<th data-options="field:'loanId',width:80,sortable:true" align="center" halign="center">审查编号</th>
						<th data-options="field:'loanAmount',width:80,sortable:true" align="center" halign="center">借款金额</th>
						<th data-options="field:'approveMoney',width:80,sortable:true" align="center" halign="center">审批金额</th>
						<th data-options="field:'repaymentRepurchaseType',formatter:partnerGlobal.formatterRepaymentType,width:80,sortable:true" align="center" halign="center">类型</th>
						<th data-options="field:'repaymentRepurchaseStatus',formatter:partnerGlobal.formatterRepaymentStatus,width:80,sortable:true" align="center" halign="center">还款状态</th>
						<th data-options="field:'refundDate',width:80,sortable:true" align="center" halign="center">还款日期</th>
						<th data-options="field:'refundLoanAmount',width:80,sortable:true" align="center" halign="center">还款本金</th>
						<th data-options="field:'refundXifee',width:80,sortable:true" align="center" halign="center">还款息费</th>
						<th data-options="field:'partnerInterests',width:80,sortable:true" align="center" halign="center">应补利息</th>
						<th data-options="field:'repaymentRepurchaseTime',width:80,sortable:true" align="center" halign="center">请求时间</th>
						<th data-options="field:'rpResultTime',width:80,sortable:true" align="center" halign="center">结果通知时间</th>
						<th data-options="field:'planRefundDate',width:80,sortable:true" align="center" halign="center">预计到期日期</th>
						<shiro:hasAnyRoles name="RE_PAYMENT_INDEX,ALL_BUSINESS">
						   <th data-options="field:'cz',formatter:detailInfo,sortable:true" align="center" halign="center">操作</th>
						</shiro:hasAnyRoles>
					</tr>
				</thead>
			</table>
		</div>
		
		
		<!-- 弹层基本信息 -->
		<div id="add_partner_info">		
		    <a href="javascript:void(0)" id="sendRepayment_btn" class="easyui-linkbutton" iconCls="icon-save" onclick="sendRepayment()">提交申请</a>
			<a id="uploadFileBtn" style="display: none;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="openUpload('','add');">上传资料</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#add_partner').dialog('close')">关闭</a>
		</div>
		<div id="add_partner" class="easyui-dialog" fitColumns="true"  title="合作项目放款申请"
		     style="width:880px;top:100px;padding:10px;" buttons="#add_partner_info" closed="true" >
			
			<section class="hearder" id="header">
			    <section class="info_top info_top_auto">
				        <div class="order_tab">
				        	<a href="#" id="info" class="first current">基本信息</a>
				        	<a href="#" onclick="showPerBase()" class="last">客户附加信息</a>
				        	<a href="#" id="projectFileTab1" onclick="getProjectFile('',1)" class="last">还款凭证</a>
				        	<a href="#" id="projectFileTab2" onclick="getProjectFile('',2)" class="last">回购凭证</a>
				        	<a href="#" id="projectFileTab3" onclick="getProjectFile('',3)" class="last">息费凭证</a>
				        	<a href="#"  onclick="getPublicManList()" class="last">共同借款人</a>
				        	<a href="#"  onclick="getWorkflowList()" class="last">审批记录</a>
				        	<a href="#"  onclick="getOriginalLoan()" class="last">赎楼信息</a>
				        	<a href="#"  onclick="getHouseInfo()" class="last">物业信息</a>
				        	<a href="#" id="projectFileTab4" class="last">回调信息</a>
				        	<a href="#" id="projectFileTab5" class="last">还款回购</a>
				        </div>
				</section>
			</section>
		
			<div style="left:0;">
				<!-- 基本信息 -->
				<div class="nearby" style="display: block">
					<form id="partnerInfo" action="<%=basePath%>projectPartnerController/repaymentApply.action" method="POST">
						<table class="beforeloanTable">
							<input type="hidden" name="pid" id="pid"/>
							<input type="hidden" name="projectId" id="projectId"/>
							<input type="hidden" name="pmUserId"/>
							<input type="hidden" name="city" value="深圳"/>
							<input type="hidden" id="loanId" name="loanId"/>
							<!-- 还款、回购类型 -->
							<input type="hidden" name="repaymentRepurchaseType" id="repaymentRepurchaseType"/>
							<!-- 还款、回购文件 -->
							<input type="hidden" name="repaymentVoucherPath" id="repaymentVoucherPath"/>
							<!-- 还款、回购状态 -->
							<input type="hidden" name="repaymentRepurchaseStatus" id="repaymentRepurchaseStatus"/>
							<!-- 息费文件 -->
							<input type="hidden" name="xiFeeVoucherPath" id="xiFeeVoucherPath"/>
							<!--还款本金 -->
							<input type="hidden" name="refundLoanAmount" id="refundLoanAmount"/>
							<!--还款日期 -->
							<input type="hidden" name="refundDate" id="refundDate"/>
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
						     	<select id="province_code" name="provinceCode" class="easyui-combobox" disabled="disabled"   style="width:129px;" />
						     </td>
						  	<td class="label_right1" ><font color="red">*</font>城市：</td>
						     <td>
						     	<select id="city_code" name="cityCode" class="easyui-combobox" disabled="disabled" style="width:129px;" />
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
						     	<input type="text" id="payment_bank_phone" name="paymentBankPhone"  class="easyui-textbox" editable="false" data-options="required:true,validType:'length[1,11]'"/>
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
						     	<input type="text" id="payment_bank_line_no" name="paymentBankLineNo"  class="easyui-textbox"  editable="false" data-options="required:true,validType:'length[1,50]'"/>
						     </td>
						  </tr>
						  
						  <!-- 打款信息 -->
						  <tr  class="payBankShowTr" style="display: none;">
						  	<td class="label_right1" style="width:160px;" ><font color="red">*</font><font class="payFontStr">打款银行(公司)：</font></td>
						     <td>
						     	<select id="pay_bank_name" name="payBankName" class="easyui-combobox" disabled="disabled" style="width:129px;" />
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
						     	<select id="pay_province_code" name="payProvinceCode" class="easyui-combobox" disabled="disabled"  style="width:129px;" />
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
						   <td class="label_right1">放款备注：</td>
							<td ><input name="loanRemark" id="loanRemark" class="easyui-textbox" style="width:100%;height:50px" data-options="multiline:true,validType:'length[0,80]'"></td>
						  </tr>
						  <tr class="partnerNoHideTr" id="confirmLoanReason_tr" style="display: none;">
						   <td class="label_right1"><font color="red">*</font>确认要款理由：</td>
							<td colspan="3"><input name="confirmLoanReason" id="confirmLoanReason" class="easyui-textbox" style="width:65%;height:30px" data-options="multiline:true,validType:'length[0,200]'"></td>
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
			<div class="nearby" id="nearby_project_file_table_2">
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
			<div class="nearby" id="nearby_project_file_table_3">
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
			<div class="nearby" id="nearby_public_man_table">
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
			<div class="nearby" id="nearby_workflow_table">
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
						 		<input name="loanRemark" id="loanRemark"  readonly="readonly"  class="easyui-textbox" style="width:100%;height:60px" data-options="multiline:true,validType:'length[0,80]'">
						 	</td>
					  	</tr>
						<tr>
							<td class="label_right1">实际还款到帐日：</td>
							<td><input type="text" class="text_style easyui-textbox" readonly="readonly" id="partnerRealRefundDate" name="partnerRealRefundDate"/></td>
							<td class="label_right1">到帐凭证：</td>
							<td>
								<div id="partnerRefundFile_div"></div>	 
							</td>
						 </tr>
						<tr>
							<td class="label_right1">应补利息金额：</td>
							<td><input type="text" id="partnerInterests" name="partnerInterests" class="text_style easyui-textbox" readonly="readonly" /></td>
						 </tr>
						<tr>
							<td class="label_right1">还款备注：</td>
						 	<td colspan="3">
						 		<input name="repaymentRepurchaseRemark" id="repaymentRepurchaseRemark"  readonly="readonly"  class="easyui-textbox" style="width:100%;height:60px" data-options="multiline:true,validType:'length[0,80]'">
						 	</td>
					  	</tr>
					  </table>
				</form>
			</div>
			<!-- 还款回购信息-->
			<div class="nearby" id="nearby_refund_tab_table" style="display: block">
				<form id="refund_tab_form" action="<%=basePath%>projectPartnerController/saveProjectPartner.action" method="POST">
					<table id="refund_tab_table" class="beforeloanTable">
						<tr>
							<td class="label_right1">还款回购类型：</td>
							<td>
								<select id="repaymentRepurchaseType_refund_tab" name="repaymentRepurchaseType" class="easyui-combobox" style="width:129px;">
									<option value="">请选择</option>
								</select>
							</td>
							<td class="label_right1">还款回购日期：</td>
							<td>
								<input type="text"  id="refundDate_refund_tab" name="refundDate" class="text_style easyui-textbox" readonly="readonly" />
							</td>
						 </tr>
						<tr>
						 	<td class="label_right1"><font class="refund_tab_table_loanAmountFont">还款本金：</font></td>
						 	<td >
						 		<input type="text" id="refundLoanAmount_refund_tab" name="refundLoanAmount"   readonly="readonly"  class="easyui-textbox">
						 	</td>
						 	 <div>
							<td class="label_right1 refund_tab_table_td_xifee1">还款息费：</td>
							<td class="refund_tab_table_td_xifee2">
								<input type="text" id="refundXifee_refund_tab" name="refundXifee"   readonly="readonly"  class="easyui-textbox" >
							</td>
					  	</tr>
						<tr class="refund_tab_table_tr_payBank">
							<td class="label_right1">还款银行：</td>
							<td>
								<select id="payBankName_refund_tab" name="payBankName" class="easyui-combobox" editable="false" style="width:129px;" />
							</td>
							<td class="label_right1">还款账号户名：</td>
							<td><input type="text" id="payAcctName_refund_tab" name="payAcctName" class="text_style easyui-textbox" readonly="readonly" /></td>
						 </tr>
						<tr class="refund_tab_table_tr_payAcctNo">
							<td class="label_right1">还款帐号：</td>
							<td colspan="3">
								<input type="text" id="payAcctNo_refund_tab" name="payAcctNo" class="text_style easyui-textbox" readonly="readonly" />
							</td>
						 </tr>
	 
					  </table>
				</form>
			</div>
			
		</div>
	
		<!-- 文件上传开始 -->
		<div id="dialog_data"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons">
			<form id=uploadFileForm name="uploadFileForm" method="post" enctype="multipart/form-data">
				<!-- 上传来源，标记还款和息费 -->
				<input type="hidden" name="upload_refund_file_flag" id="upload_refund_file_flag" value="0">
				<!-- 项目id -->
				<input type="hidden" name="projectId" id="projectId_file">
				<input type="hidden" name="pid" id="pid_file">
				<input type="hidden" name="partnerId" id="partnerId_file">
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
			


		<!-- 还款信息填写 -->
		<div id="refund_data"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons_refund">
			<form id="refundPartnerForm" name="refundPartnerForm" method="post" action="<%=basePath%>projectPartnerController/updatePartnerRefund.action" >
				<!-- 项目id -->
				<input type="hidden" name="projectId" id="projectId_refund">
				<input type="hidden" name="pid" id="pid_refund">
				<table>
					<tr>
						<td width="120" align="right" height="28">项目名称：</td>
						<td><span id="projectName_refund"></span></td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>合作机构：</td>
						<td>
							<select id="partner_no_refund" name="partnerNo" class="easyui-combobox" editable="false" style="width:129px;" />
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>还款回购类型：</td>
						<td>
							<select id="repaymentRepurchaseType_refund" name="repaymentRepurchaseType" class="easyui-combobox" style="width:129px;">
								<option value="">请选择</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>还款回购日期：</td>
						<td>
							<input type="text" class="easyui-textbox" editable="false" name="refundDate" id="refundDate_refund"/>
						</td>
					</tr>
					<tr> 
						<td align="right" width="120" height="28"><font color="red">*</font><font id="refundLoanAmountFont_refund">还款本金</font>：</td>
						<td><input id="refundLoanAmount_refund" name="refundLoanAmount" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
					</tr>
					<tr class="repaymentXifeeTr"> 
						<td align="right" width="120" height="28"><font color="red">*</font>还款息费：</td>
						<td><input id="refundXifee_refund" name="refundXifee" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
					</tr>
					
					<tr class="repaymentBankCodeTr"> 
						<td align="right" width="120" height="28"><font color="red">*</font>还款银行：</td>
						<td>
							<select id="payBankCode_refund" name="payBankCode"  panelHeight="300" class="easyui-combobox" style="width:160px;">
								<option value="">请选择</option>
								<option value="ABC">农业银行</option>
								<option value="BCCB">北京银行份证</option>
								<option value="BJRCB">北京农商行</option>
								<option value="BOC">中国银行</option>
								<option value="BOS">上海银行</option>
								<option value="CBHB">渤海银行</option>
								<option value="CCB">建设银行</option>
								<option value="CCQTGB">重庆三峡银行</option>
								<option value="CEB">光大银行</option>
								<option value="CIB">兴业银行</option>
								<option value="CITIC">中信银行</option>
								<option value="CMB">招商银行</option>
								<option value="CMBC">民生银行</option>
								<option value="COMM">交通银行</option>
								<option value="CSCB">长沙银行</option>
								<option value="CZB">浙商银行</option>
								<option value="CZCB">浙江稠州商业银行</option>
								<option value="GDB">广州发展银行</option>
								<option value="GNXS">广州市农信社</option>
								<option value="GZCB">广州市商业银行</option>
								<option value="HCCB">杭州银行</option>
								<option value="HKBCHINA">汉口银行</option>
								<option value="HSBANK">徽商银行</option>
								<option value="HXB">华夏银行</option>
								<option value="ICBC">工商银行</option>
								<option value="NBCB">宁波银行</option>
								<option value="NJCB">南京银行</option>
								<option value="PSBC">中国邮储银行</option>
								<option value="SHRCB">上海农村商业银行</option>
								<option value="SNXS">深圳农村商业银行</option>
								<option value="SPDB">浦东发展银行</option>
								<option value="SXJS">晋城市商业银行</option>
								<option value="SZPAB">平安银行</option>
								<option value="UPOP">银联在线支付</option>
								<option value="WZCB">温州市商业银行</option>
 							</select>
							
						</td>
					</tr>
					<tr class="repaymentBankNameTr"> 
						<td align="right" width="120" height="28"><font color="red">*</font>还款银行：</td>
						<td>
							<select id="payBankName_refund" name="payBankName" class="easyui-combobox" editable="false" style="width:129px;" />
						</td>
					</tr>
					<tr class="repaymentAcctNameTr"> 
						<td align="right" width="120" height="28"><font color="red">*</font>还款账号户名：</td>
						<td><input type="text" id="payAcctName_refund" name="payAcctName" class="easyui-textbox" data-options="required:true,validType:'length[1,50]'" /></td>
					</tr>
					<tr class="repaymentAcctNoTr"> 
						<td align="right" width="120" height="28"><font color="red">*</font>还款帐号：</td>
						<td><input type="text" id="payAcctNo_refund" name="payAcctNo" class="easyui-textbox" data-options="required:true,validType:'length[1,25]'" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="data-buttons_refund">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="updatePartnerRefund()" >保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#refund_data').dialog('close')">取消</a>
		</div>
			
		<!-- 点荣网提前还款 -->
		<div id="refund_before_data"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons_refund_before">
			<form id="refundBeforePartnerForm" name="refundBeforePartnerForm" method="post" action="<%=basePath%>projectPartnerController/updatePartnerRefund.action" >
				<!-- 项目id -->
				<input type="hidden" name="projectId" id="projectId_refund_before">
				<input type="hidden" name="pid" id="pid_refund_before">
				<table>
					<tr>
						<td width="120" align="right" height="28">项目名称：</td>
						<td><span id="projectName_refund_before"></span></td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>合作机构：</td>
						<td>
							<input type="text" id="partnerNo_refund_before" name="partnerNo" readonly="readonly" class="easyui-textbox"/>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>借款金额：</td>
						<td>
							<input type="text" id="loanAmount_refund_before" name="loanAmount" readonly="readonly" class="easyui-textbox"/>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28">选择还款日期：</td>
						<td>
							<select  id="refundDate_refund_before_temp" onchange="setRefundLoanAmount();" 
							  class="select_style" data-options="validType:'selrequired'" style="width: 151px;height: 26px;border-radius: 5px;border-color: #95B8E7;">
			 					<option value="">请选择</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>提前还款日期：</td>
						<td>
							<input id="refundDate_refund_before" name="refundDate"  class="easyui-textbox"  />
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>提前还款金额：</td>
						<td>
							<input type="text" id="refundLoanAmount_refund_before" name="refundLoanAmount"  class="easyui-textbox"/>
						</td>
					</tr>
					
				</table>
			</form>
		</div>
		<div id="data-buttons_refund_before">
			<a href="javascript:void(0)" id="applyAdvancedSettlement" class="easyui-linkbutton" iconCls="icon-save" onclick="applyAdvancedSettlement()" >提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#refund_before_data').dialog('close')">取消</a>
		</div>
		
		
		
		
		<!-- 南粤银行提前还款 -->
		<div id="nyyh_refund_before_data"  class="easyui-dialog" style="width:600px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#nyyh_data-buttons_refund_before">
			<form id="nyyh_refundBeforePartnerForm" name="nyyh_refundBeforePartnerForm" method="post" action="<%=basePath%>projectPartnerController/updatePartnerRefund.action" >
				<!-- 项目id -->
				<input type="hidden" name="projectId" id="nyyh_projectId_refund_before">
				<input type="hidden" name="pid" id="nyyh_pid_refund_before">
				<table>
					<tr>
						<td width="120" align="right" height="28">项目名称：</td>
						<td><span id="nyyh_projectName_refund_before"></span></td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>合作机构：</td>
						<td>
							<input type="text" id="nyyh_partnerNo_refund_before" name="partnerNo" readonly="readonly" class="easyui-textbox"/>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>借款金额：</td>
						<td>
							<input type="text" id="nyyh_loanAmount_refund_before" name="loanAmount" readonly="readonly" class="easyui-textbox"/>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>提前还款日期：</td>
						<td>
							<input id="nyyh_refundDate_refund_before" name="refundDate"  class="easyui-textbox"  />
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>提前还款本金额：</td>
						<td>
							<input type="text" id="nyyh_refundLoanAmount_refund_before" name="refundLoanAmount"  class="easyui-textbox"/>
						</td>
						<td width="120" align="right" height="28"><font color="red">*</font>提前还款利息：</td>
						<td>
							<input type="text" id="nyyh_refundXifee_refund_before" name="refundXifee"  class="easyui-textbox"/>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>应还违约金额：</td>
						<td>
							<input type="text" id="nyyh_refundPenalty_refund_before" name="refundPenalty"  class="easyui-textbox"/>
						</td>
						<td width="120" align="right" height="28"><font color="red">*</font>应还罚息：</td>
						<td>
							<input type="text" id="nyyh_refundFine_refund_before" name="refundFine"  class="easyui-textbox"/>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>应还复利金额：</td>
						<td>
							<input type="text" id="nyyh_refundCompdinte_refund_before" name="refundCompdinte"  class="easyui-textbox"/>
						</td>
						<td width="120" align="right" height="28"><font color="red">*</font>应还总金额：</td>
						<td>
							<input type="text" id="nyyh_refundTotalAmount_refund_before" name="refundTotalAmount" readonly="readonly"  class="easyui-textbox"/>
						</td>
					</tr>
					<tr class="repaymentAcctNameTr"> 
						<td align="right" width="120" height="28"><font color="red">*</font>还款账号户名：</td>
						<td><input type="text" id="nyyh_payAcctName_refund_before" name="payAcctName" class="easyui-textbox" data-options="required:true,validType:'length[1,50]'" style="width: 220" /></td>
					</tr>
					<tr class="repaymentAcctNoTr"> 
						<td align="right" width="120" height="28"><font color="red">*</font>还款帐号：</td>
						<td><input type="text" id="nyyh_payAcctNo_refund_before" name="payAcctNo" class="easyui-textbox" data-options="required:true,validType:'length[1,25]'" style="width: 220"/></td>
					</tr>
					
				</table>
			</form>
		</div>
		<div id="nyyh_data-buttons_refund_before">
			<a href="javascript:void(0)" id="nyyh_prepaymentAdvance_btn" class="easyui-linkbutton" iconCls="icon-save" onclick="nyyh_prepaymentAdvance()" >提前还款试算</a>
			<a href="javascript:void(0)" id="nyyh_advance_btn" class="easyui-linkbutton" iconCls="icon-save" onclick="nyyh_advance()" >提交还款</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#nyyh_refund_before_data').dialog('close')">取消</a>
		</div>
			
			
			
		<!-- ---------------------------华安保险修改还款状态-begin--------------------------------------- -->
		<div id="updateRepaymentRepurchaseStatus_data"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#updateRepaymentRepurchaseStatus_btn">
			<form id="updateRepaymentRepurchaseStatus_form" name="updateRepaymentRepurchaseStatus_form" method="post" action="<%=basePath%>projectPartnerController/updateRepaymentRepurchaseStatus.action" >
				<!-- 项目id -->
				<input type="hidden" name="pid" id="pid_updateRepaymentRepurchaseStatus">
				<input type="hidden" name="partnerNo" id="partnerNo_updateRepaymentRepurchaseStatus">
				<table>
					<tr>
						<td width="120" align="right" height="28">项目名称：</td>
						<td><span id="projectName_updateRepaymentRepurchaseStatus"></span></td>
					</tr>
					<tr>
						<td width="120" align="right" height="28">合作机构：</td>
						<td><span id="partnerNoName_updateRepaymentRepurchaseStatus"></span></td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>借款金额：</td>
						<td>
							<input type="text" id="loanAmount_updateRepaymentRepurchaseStatus" disabled="disabled" class="easyui-textbox"/>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>还款状态：</td>
						<td>
							<select id="repaymentRepurchaseStatus_updateRepaymentRepurchaseStatus" name="repaymentRepurchaseStatus"    class="easyui-combobox" style="width:160px;">
								<option value="">请选择</option>
								<option value="3">确认收到</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>还款日期：</td>
						<td>
							<input id="refundDate_updateRepaymentRepurchaseStatus" name="refundDate" required="true"  data-options="required:true" class="easyui-datebox" editable="false"  />
						</td>
					</tr>
					<tr>
					 	<td class="label_right1"><font color="red">*</font>还款本金：</td>
					 	<td>
					 		<input type="text" id="refundLoanAmount_updateRepaymentRepurchaseStatus" name="refundLoanAmount"  class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2">
					 	</td>
					 </tr>
					 <tr>	
						<td class="label_right1 ">还款息费：</td>
						<td>
							<input type="text" id="refundXifee_updateRepaymentRepurchaseStatus" name="refundXifee"  class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2" >
						</td>
				  	 </tr>
				  	 <tr>
				  		<td width="120" align="right" height="28"><font color="red">*</font>应还总金额：</td>
						<td>
							<input type="text" id="refundTotalAmount_updateRepaymentRepurchaseStatus" name="refundTotalAmount" readonly="readonly"  class="easyui-numberbox"/>
						</td>
					 </tr>
					 <tr>
						<td class="label_right1">还款备注：</td>
						<td>
							<input id="repaymentRepurchaseRemark_updateRepaymentRepurchaseStatus" name="repaymentRepurchaseRemark"  class="easyui-textbox" style="width:100%;height:60px" data-options="multiline:true,validType:'length[0,80]'">
						</td>
					 </tr>
				</table>
			</form>
		</div>
		<div id="updateRepaymentRepurchaseStatus_btn">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="updateRepaymentRepurchaseStatus()" >提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#updateRepaymentRepurchaseStatus_data').dialog('close')">取消</a>
		</div>
		<!-- ---------------------------华安保险修改还款状态-end--------------------------------------- -->
		
			
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