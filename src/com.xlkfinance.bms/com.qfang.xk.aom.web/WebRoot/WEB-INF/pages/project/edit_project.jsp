<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>新增业务申请</title>
<meta name="keywords" content="">
<meta name="description" content="">
<script type="text/javascript">
	var pid= "${project.pid}";
	var editType = "${editType}";
	var isReject ="${project.isReject}";
	$(document).ready(function() {
		initUploadify();
		fileUploadModalListener();
		if(editType == 3){
			$("#projectInfo").find("input,select,textarea").attr(
					"disabled", true);
		}
		if(isReject == 3){
			$("#projectRow").find("input,select,textarea").attr(
					"disabled", true);
			$("#customerRow").find("input,select,textarea").attr(
					"disabled", true);
			$("#foreclosureRow").find("input,select,textarea").attr(
					"disabled", true);
			$("#houseRow").find("input,select,textarea").attr(
					"disabled", true);
		}
		var pid = $("#pid").val();
		if(pid >0){
			$("#SelectPer_button").attr(
					"disabled", true);
			getPublicMan(pid);
		}
		
		var acctId = '${project.acctId}';
		if(acctId>0){
			getAcct(acctId);
		}
		 //默认绑定省
	    proviceBind();
	    //绑定事件
	    $("#houseProvinceCode").change( function () {
	        cityBind();
	    })
	    
	    $("#houseCityCode").change(function () {
	        villageBind();
	    })
	    //产品信息变更
	    $("#productId").change(function () {
	    	changeProduct();
	    })
	    
	    var productId = '${project.productId}';
	    if(productId >0){
	    	changeProduct();
	    }
	    
	    comboxBind("businessCategory","BUSINESS_CATEGORY","${project.businessCategory}");
	    comboxBind("loanType","LOAN_TYPE","-1");
	    
	    initPublicMan();
	    setCombobox("sex", "SEX", "");
	});
	//打开选择借款人弹窗
	function openSelectPer(){
		$.jgrid.defaults.styleUI = "Bootstrap";
		$("#person_table_list").jqGrid({
			url : "${basePath}/customerController/perListUrl.action",
			datatype : "json",
			mtype : "POST",
			caption : "客户列表",
			height : 250,
			width : 850,
			shrinkToFit : true,
			multiselect : true,
			multiboxonly : true,
			viewrecords : true,
			postData : $.serializeObject($("#searchFrom")),
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			pager : "#person_pager_list",
			colNames : [ "pid", "姓名","性别","证件类型","证件号码","手机号码","居住地址"],
			colModel : [{
				name : "pid",
				index : "pid",
				hidden : true
			},  {
				name : "value2",
				index : "value2",
				width:50
			}, {
				name : "value3",
				index : "value3",
				width:50
			}, {
				name : "value4",
				index : "value4",
				width:50
			}, {
				name : "value5",
				index : "value5",
			}, {
				name : "value11",
				index : "value11",
			}, {
				name : "value13",
				index : "value13",
			}],
			gridComplete : function() { //列表生成后,给某一列绑定操作 例如删除操作
			}
		});
		$("#person_table_list").jqGrid("navGrid", "#person_pager_list", {
			edit : false,
			add : false,
			del : false,
			search : false,
			refresh : true
		});
		$('#personInfoModal').modal('show');
	}
	//选择借款人
	function selectPerson(){
		var id = $("#person_table_list").jqGrid('getGridParam', 'selrow');
		if (id == null) {
			layer.alert('请选择数据', {
				icon : 0
			});
			return;
		}
		var jqGridIds = $("#person_table_list").jqGrid('getGridParam',
		'selarrrow');
		if (jqGridIds.length>1) {
			layer.alert('请不要多选数据', {
				icon : 0
			});
			return;
		}
		var rowData = $("#person_table_list").jqGrid('getRowData', id);
		getAcct(rowData.pid);
		getPublicPerson(rowData.pid);
		$('#personInfoModal').modal('hide');
	}
	//选择借款人后加载借款人信息
	function getAcct(acct_id){
		// 根据客户ID查询用户数据
		$.ajax({
			type: "POST",
	        url: "${ctx}projectController/getCusPersonByAcctId.action",
	        data:{"acctId":acct_id},
	        dataType: "json",
	        success: function(row){
	        		$("#chinaName").val(row.chinaName);
	        		$("#sexName").val(row.sexName);
	        		$("#certType").val(row.certTypeName);
	        		$("#certNumber").val(row.certNumber);
	        		$("#perTelephone").val(row.perTelephone);
	        		$("#mail").val(row.mail);
	        		$("#liveAddr").val(row.liveAddr);
	        		$("#acctId").val(row.acctId);
	        		
	        }
		});
	}
	//根据客户ID加载共同借款人
	function getPublicPerson(acct_id){
		var url = "${ctx}projectController/getNoSpouseList.action?acctId="+acct_id;
		$("#publicInfoList tbody").html("");
		$.ajax({
			url:url,
			type:"post",
			dataType:"json",
		  	success:function(data){
		  		if(data.length>0){
			    	for(var i=0;i<data.length;i++){
			    		var checkStr = '<input type="checkbox" class="isChecked" value="'+data[i].pid+'"/>';
					    var cazuo = '<a onclick=delPublicInfo(this,'+data[i].pid+')>删除</a>'
			    		var str = '<tr><td>'+checkStr+'</td>'
					    		+'<td>'+data[i].chinaName+'</td>'
					    		+'<td>'+data[i].sexName+'</td>'
					    		+'<td>'+data[i].certTypeName+'</td>'
					    		+'<td>'+data[i].certNumber+'</td>'
					    		+'<td>'+data[i].relationText+'</td>'
					    		+'<td>'+data[i].perTelephone+'</td>'
					    		+'<td>'+data[i].proportionProperty+'</td>'
					    		+'<td>'+cazuo
					    		+'</td></tr>';
					    $("#publicInfoList tbody").append(str);
			    	}
			    }
		  		}
		  	});
	}
	
	//根据项目信息加载项目相关关系人
	function getPublicMan(project_id){
		var url = "${ctx}projectController/getNoSpouseLists.action?projectId="+project_id;
		$("#publicInfoList tbody").html("");
		$.ajax({
			url:url,
			type:"post",
			dataType:"json",
		  	success:function(data){
		  		if(data.length>0){
			    	for(var i=0;i<data.length;i++){
			    		var checkStr = '<input type="checkbox" class="isChecked" value="'+data[i].projectPublicManId+'"/>';
					    if(editType == 3 || isReject == 3){
					    	var cazuo = '';
					    }else{
					    	var cazuo = '<a onclick=delPublicInfo(this,'+data[i].projectPublicManId+')>删除</a>';
					    }
			    		
			    		var str = '<tr><td>'+checkStr+'</td>'
					    		+'<td>'+data[i].chinaName+'</td>'
					    		+'<td>'+data[i].sexName+'</td>'
					    		+'<td>'+data[i].certTypeName+'</td>'
					    		+'<td>'+data[i].certNumber+'</td>'
					    		+'<td>'+data[i].relationText+'</td>'
					    		+'<td>'+data[i].perTelephone+'</td>'
					    		+'<td>'+data[i].proportionProperty+'</td>'
					    		+'<td>'+cazuo
					    		+'</td></tr>';
					    $("#publicInfoList tbody").append(str);
			    	}
			    }
		  		}
		  	});
	}
	
	function delPublicInfo(obj,publicId){
		layer.confirm("确定要删除此记录吗？", {
			icon : 3,
			btn : [ '是','否' ]
		//按钮
		}, function() {
			$(obj).parents("tr").remove();
			var pid = $("#pid").val();
			if(pid>0){
			$.ajax({
				type: "POST",
		        url: "${ctx}projectController/delPublicMan.action",
		        data:{"userPids":publicId},
		        dataType: "json",
		        success: function(ret){
		        	//var ret = eval("("+data+")");
		        	if(ret && ret.header["success"]){
						// 操作提示
						layer.alert('操作成功！', {
							icon : 1
						}); 
		        	}else{
		        		layer.alert(ret.header["msg"], {
							icon : 5
						});
					}
		        }
			});
			}else{
				layer.alert('操作成功！', {
					icon : 1
				});
			}
		}, function() {
		});
		
	}
	function initPublicMan(){
		$.jgrid.defaults.styleUI = "Bootstrap";
		$("#public_table_list").jqGrid({
			url : "${basePath}/projectController/getNoSpouseList.action",
			datatype : "json",
			mtype : "POST",
			caption : "客户列表",
			height : 250,
			width : 830,
			shrinkToFit : true,
			multiselect : true,
			multiboxonly : true,
			viewrecords : true,
			colNames : [ "pid", "姓名","性别","证件类型","证件号码","手机号码","与客户关系","产权占比"],
			colModel : [{
				name : "pid",
				index : "pid",
				hidden : true
			},  {
				name : "chinaName",
				index : "chinaName",
				width:150
			}, {
				name : "sexName",
				index : "sexName",
				width:50
			}, {
				name : "certTypeName",
				index : "certTypeName",
			}, {
				name : "certNumber",
				index : "certNumber",
			}, {
				name : "perTelephone",
				index : "perTelephone",
			}, {
				name : "relationText",
				index : "relationText",
			}, {
				name : "proportionProperty",
				index : "proportionProperty",
			}],
			gridComplete : function() { //列表生成后,给某一列绑定操作 例如删除操作
				
			}
		});

		$("#public_table_list").jqGrid("navGrid", "#public_pager_list", {
			edit : false,
			add : false,
			del : false,
			search : false,
			refresh : true
		});
	}
	//打开共同借款人选择窗口
	function openPublic(){
		var acctId = $("#acctId").val();
		if(acctId == null || acctId=='' || acctId <=0){
			layer.alert('请先选择借款人', {
				icon : 0
			});
			return;
		}
		$("#public_table_list").jqGrid('setGridParam', {
			datatype : 'json',
			postData :{'acctId':acctId}, //发送数据  
			page : 1
		}).trigger("reloadGrid"); //重新载入
		 
		$('#publicInfoModal').modal('show');
	}
	//选择共同借款人
	function selectPublic(){
		var jqGridIds = $("#public_table_list").jqGrid('getGridParam',
		'selarrrow');
		//获取选择的共同借款人id
		var fnPids = "";
		for (var i = 0; i < jqGridIds.length; i++) {
			var pid = $("#public_table_list").jqGrid('getRowData',
					jqGridIds[i]).pid;
			if (i == 0) {
				fnPids += pid;
			} else {
				fnPids += "," + pid;
			}
		}
		var pid = $("#pid").val();
		if(pid >0){
			$.ajax({
				type: "POST",
		        url: "${ctx}projectController/addProjectPublicMan.action",
		        data:{"userPids":fnPids,"projectId":pid},
		        dataType: "json",
		        success: function(ret){
		        	//var ret = eval("("+data+")");
		        	if(ret && ret.header["success"]){
						// 操作提示
						layer.alert('操作成功！', {
							icon : 1
						}); 
						//刷新共同借款人列表
						getPublicMan(pid);
						$('#publicInfoModal').modal('hide');
		        	}else{
		        		layer.alert(ret.header["msg"], {
							icon : 5
						});
					}
		        }
			});
		}else{
			var userIds = "";
			//获取共同借款人列表中数据
			$.each($("#publicInfoList").find("tr:gt(0)"), function(k, v) {
	    		var $td = $(v).children('td');
	    		 userIds += $td.eq(0).find("input").val()+','; //第一个td的内容
			});
			userIds +=fnPids;
			if(userIds != ""){
				var url = "${ctx}projectController/getSpouseListByPids.action?userIds="+userIds;
				$("#publicInfoList tbody").html("");
				$.ajax({
					url:url,
					type:"post",
					dataType:"json",
				  	success:function(data){
				  		if(data.length>0){
					    	for(var i=0;i<data.length;i++){
					    		var checkStr = '<input type="checkbox" class="isChecked" value="'+data[i].pid+'"/>';
							    var cazuo = '<a onclick=delPublicInfo(this,'+data[i].pid+')>删除</a>'
					    		var str = '<tr><td>'+checkStr+'</td>'
							    		+'<td>'+data[i].chinaName+'</td>'
							    		+'<td>'+data[i].sexName+'</td>'
							    		+'<td>'+data[i].certTypeName+'</td>'
							    		+'<td>'+data[i].certNumber+'</td>'
							    		+'<td>'+data[i].relationText+'</td>'
							    		+'<td>'+data[i].perTelephone+'</td>'
							    		+'<td>'+data[i].proportionProperty+'</td>'
							    		+'<td>'+cazuo
							    		+'</td></tr>';
							    $("#publicInfoList tbody").append(str);
					    	}
					    }
				  		}
				  	});
			}
			$('#publicInfoModal').modal('hide');
		}
	}
	
	//产品下拉框变更后页面属性的调整
	function changeProduct(){
		var productNumber = $("#productId").find("option:selected").attr("productNum");
		//交易有赎楼提放贷和非交易有赎楼提放贷提放贷显示
		if(productNumber == 'JYYSLTFD' || productNumber == 'FJYYSLTFD'){
			//周转资金信息
			$(".foreclosure").show();
			$(".turnover").show();
			$(".old_loan").show();
			$("#foreclosureMoney").removeAttr("readonly");
			$("#planLoanMoney").attr("readonly","readonly");
		//交易无赎楼提放贷和非交易无赎楼提放贷提放贷时屏蔽原贷款信息
		}else if(productNumber == 'JYWSLTFD' || productNumber == 'FJYWSLTFD'){
			//原贷款
			$(".foreclosure").hide();
			$(".old_loan").hide();
			$(".turnover").hide();
			$("#turnoverMoney").val(0.00);
			$("#foreclosureMoney").val(0.00);
			$("#planLoanMoney").removeAttr("readonly");
		}else{
			$(".foreclosure").hide();
			$(".old_loan").show();
			$(".turnover").hide();
			$("#turnoverMoney").val(0.00);
			$("#foreclosureMoney").val(0.00);
			$("#planLoanMoney").removeAttr("readonly");
		}
	}
	//赎楼金额以及周转金额的变更事件
	function changeMoney(){
		var productNumber = $("#productId").find("option:selected").attr("productNum");
		if(productNumber == 'JYYSLTFD' || productNumber == 'FJYYSLTFD'){
			$("#turnoverMoney").toNumber();
			$("#foreclosureMoney").toNumber();
			var turnoverMoney = Number($("#turnoverMoney").val());
			var foreclosureMoney = Number($("#foreclosureMoney").val());
			var loanMoney = turnoverMoney + foreclosureMoney;
			$("#planLoanMoney").val(loanMoney);
			getGuaranteeFee();
		}
	}
	//校验项目信息
	function checkProject(){
		var acctId = $("#acctId").val();
		if(acctId == null || acctId=='' || acctId <=0){
			layer.alert('请先选择借款人', {
				icon : 2
			});
			return;
		}
		
		var chargeType= $("#chargeType").val();
		if(chargeType == ""){
			layer.alert('请选择收费类型！', {
				icon : 2
			});
			return false;
		}
		
		$("#planLoanMoney").toNumber();
		$("#availableLimit").toNumber();
		$("#singleUpperLimit").toNumber();
		$("#newLoanMoney").toNumber();
		var availableLimit = $("#availableLimit").val();//可用额度
		var planLoanMoney = $("#planLoanMoney").val();//借款金额
		var singleUpperLimit = $("#singleUpperLimit").val();//单笔上限
		var newLoanMoney = $("#newLoanMoney").val();//新贷款金额
		var fundsMoney = $("#fundsMoney").val();
		var downPayment = $("#downPayment").val();//首付款
		if(fundsMoney == ""){
			$("#fundsMoney").val(0.00);
		}
		if(downPayment == ""){
			$("#downPayment").val(0.00);
		}
		if(planLoanMoney == '' || planLoanMoney <=0){
			layer.alert('借款金额必须大于0，请重新填写借款金额！', {
				icon : 2
			});
			return false;
		}
		if((planLoanMoney - singleUpperLimit)>0){
			layer.alert('借款金额不能大于单笔借款上限，请重新填写借款金额！', {
				icon : 2
			});
			return false;
		}
		if((planLoanMoney - availableLimit)>0){
			layer.alert('借款金额不能大于可用额度，请重新填写借款金额！', {
				icon : 2
			});
			return false;
		}
		
		 if((planLoanMoney-newLoanMoney-fundsMoney-downPayment)>0){
			layer.alert('新贷款金额+监管金额+首付款金额≥借款金额！', {
				icon : 2
			});
			return false;
		} 
		
		var accumulationFundMoney = $("#accumulationFundMoney").val();
		if(accumulationFundMoney == ""){
			$("#accumulationFundMoney").val(0.00);
		}
		
		var tranasctionMoney = $("#tranasctionMoney").val();
		if(tranasctionMoney == ""){
			$("#tranasctionMoney").val(0.00);
		}
		var length = $("#houseInfoList tr").length-1;
		
		if(length <=0){
			layer.alert('物业信息不能为空！', {
				icon : 2
			});
			return false;
		}
		//返回值
		var flag = true;
		
		//非赎楼提放贷不用填写原贷款信息
		var productNumber = $("#productId").find("option:selected").attr("productNum");
		if(productNumber != 'JYWSLTFD' && productNumber != 'FJYWSLTFD'){
			flag = checkOldLoan();
		}else{
			$("#oldLoanMoney1").val(0.00);
			$("#oldOwedAmount1").val(0.00);
		}
		
		if(productNumber == 'JYYSLTFD' || productNumber == 'FJYYSLTFD'){
			var foreclosureMoney = $("#foreclosureMoney").val();
			if(foreclosureMoney == "" || foreclosureMoney <=0){
				layer.alert('赎楼金额必须大于0，请重新填写赎楼金额！', {
					icon : 2
				});
				return false;
			}
			
			var turnoverMoney = $("#turnoverMoney").val();
			if(turnoverMoney == "" || turnoverMoney<=0){
				layer.alert('周转金额必须大于0，请重新填写周转金额！', {
					icon : 2
				});
				return false;
			}
		var turnoverCapitalBank= $("#turnoverCapitalBank").val();
		if(turnoverCapitalBank == ""){
			layer.alert('周转资金开户行不能为空！', {
				icon : 2
			});
			return false;
		}
		var turnoverCapitalName= $("#turnoverCapitalName").val();
		if(turnoverCapitalName == ""){
			layer.alert('周转资金户名不能为空！', {
				icon : 2
			});
			return false;
		}
		var turnoverCapitalAccount= $("#turnoverCapitalAccount").val();
		if(turnoverCapitalAccount == ""){
			layer.alert('周转资金账号不能为空！', {
				icon : 2
			});
			return false;
		}
			
		}else{
			 $("#turnoverCapitalBank").val("");
			 $("#turnoverCapitalName").val("");
			 $("#turnoverCapitalAccount").val("");
			 $("#foreclosureMoney").val(0.0);
			 $("#turnoverMoney").val(0.0)
		}
		//校验交易类型
		flag = checkBusinessCategory();
		
		return flag;
	}
	
	//提交申请
	function subProjectInfo(){
		//校验项目信息
		if(!checkProject()){
			return;
		}
		
		//交易/非交易无赎楼提房贷不校验原贷款银行信息
		var productNumber = $("#productId").find("option:selected").attr("productNum");
		if(productNumber != 'JYWSLTFD' && productNumber != 'FJYWSLTFD'){
			if (!checkOldLoan()) {
				return;
			}
		}
		var userPids = "";
		//获取共同借款人列表中数据
		$.each($("#publicInfoList").find("tr:gt(0)"), function(k, v) {
    		var $td = $(v).children('td');
    		userPids += $td.eq(0).find("input").val()+','; //第一个td的内容
		});
		
		$("#userPids").val(userPids);
		
		if($("#projectInfoForm").validate().form()){
			$("#requestStatus").val(2);//已提交状态
			$(".currency").toNumber();
			$.ajax({
				url : "${ctx}projectController/submitProjectInfo.action",
				type : "POST",
				data : $("#projectInfoForm").serialize(),
				async : false,
				success : function(result) { //表单提交后更新页面显示的数据
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						pid = ret.header["projectId"];
						$("#pid").val(ret.header["projectId"]);
						$("#foreclosureId").val(ret.header["foreclosureId"]);
						$("#propertyId").val(ret.header["propertyId"]);
						$("#guaranteeId").val(ret.header["guaranteeId"]);
						$("#SelectPer_button").attr(
								"disabled", true);
						layer.confirm('提交成功', {
							icon : 6,
							btn : [ '是' ]
						//按钮
						}, function() {
							refreshTab("${ctx}projectController/toProjectIndex.action","${ctx}projectController/toProjectIndex.action");
							closeTab();
						}); 
					} else {
						layer.alert(ret.header["msg"], {
							icon : 5
						});
					}
				}
			});
		}
	}
	//保存信息
	function saveProjectInfo(){
		//校验项目信息
		if(!checkProject()){
			return;
		}
		
		var userPids = "";
		//获取共同借款人列表中数据
		$.each($("#publicInfoList").find("tr:gt(0)"), function(k, v) {
    		var $td = $(v).children('td');
    		userPids += $td.eq(0).find("input").val()+','; //第一个td的内容
		});
		
		$("#userPids").val(userPids);
		
		if($("#projectInfoForm").validate().form()){
			$(".currency").toNumber();
			$.ajax({
				url : "${ctx}projectController/saveProjectInfo.action",
				type : "POST",
				data : $("#projectInfoForm").serialize(),
				async : false,
				success : function(result) { //表单提交后更新页面显示的数据
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						pid = ret.header["projectId"];
						$("#pid").val(ret.header["projectId"]);
						$("#foreclosureId").val(ret.header["foreclosureId"]);
						$("#propertyId").val(ret.header["propertyId"]);
						$("#guaranteeId").val(ret.header["guaranteeId"]);
						getProjectDataInfo();
						$("#SelectPer_button").attr(
								"disabled", true);
						layer.alert('保存成功！', {
							icon : 1
						});
					} else {
						layer.alert(ret.header["msg"], {
							icon : 5
						});
					}
				}
			});
		}
	}
	//校验交易信息
	function checkBusinessCategory(){
		var businessCategory = $("#businessCategory").val();
		if(businessCategory == 13755){//交易
			if($("#superviseDepartment").val() == -1){
				layer.alert('交易类赎楼，监管银行不允许为空!', {
					icon : 2
				});
				return false;
			}
			if($("#fundsMoney").val() < 0.0){
				layer.alert('交易类赎楼，资金监管金额必须大于0!', {
					icon : 2
				});
				return false;
			}
			
			if($("#superviseAccount").val() ==''){
				layer.alert('交易类赎楼，资金监管收款账号不允许为空!', {
					icon : 2
				});
				return false;
			}
			
			if($("#supersionReceBank").val() == ''){
				layer.alert('交易类赎楼，资金监管开户行不允许为空!', {
					icon : 2
				});
				return false;
			}
			if($("#supersionReceName").val() == ''){
				layer.alert('交易类赎楼，资金监管收款户名不允许为空!', {
					icon : 2
				});
				return false;
			}
			
			if($("#buyerName").val() ==''){
				layer.alert('交易类赎楼，买方姓名不允许为空!', {
					icon : 2
				});
				return false;
			}
			
			if($("#buyerCardNo").val()==''){
				layer.alert('交易类赎楼，买方身份证号不允许为空!', {
					icon : 2
				});
				return false;
			}
		}
		return true;
	}
	//校验原贷款信息
	function checkOldLoan(){
		var loanLength = $("#originalLoanInfoList tr").length-1;
		if(loanLength <=0){
			layer.alert('原贷款信息不能为空！', {
				icon : 2
			});
			return false;
		}
		return true;
	}
	
	//获取上传资料列表
	function getProjectDataInfo(){
		var projectId= $("#pid").val();
		var url = "${ctx}projectController/getProjectDataInfoList.action?projectId="+projectId;
		$("#dataInfoList tbody").html("");
		$.ajax({
			url:url,
			type:"post",
			dataType:"json",
		  	success:function(data){
		  		if(data.length>0){
			    	for(var i=0;i<data.length;i++){
			    		var trIndex=$("#dataInfoList tr").length -1;
			    		var path = data[i].fileUrl;
			    		var lookUpDom='';
			    		var fileName = data[i].fileName;
			    		if(path != null && path !="" && path != undefined){
			    			var fileType=path.substring(path.lastIndexOf(".")+1);
				    		fileType = fileType.toLowerCase();
				    		fileName = fileName.replace(/\ +/g,"");
				    		if (fileType=="bmp"||fileType=="jpeg"||fileType=="jpg"||fileType=="png"||fileType=="gif") {
				    			lookUpDom = '|<a onclick=lookUpFileByPath("'+data[i].fileUrl+'","'+fileName+'")>预览</a>';
				    		}
			    		}
			    		if(fileName == null || fileName == "" || fileName == undefined){
			    			fileName = "";
			    		} 
			    		var caozuoStr ="";
			    		if(data[i].dataId>0){
			    			caozuoStr ='<a onclick=downLoadFileByPath("'+data[i].fileUrl+'","'+fileName+'")>下载</a>'+lookUpDom+'|<a href="#" onclick="delDataInfo('+data[i].dataId+')">删除</a>';
			    		}else{
			    			caozuoStr='<a onclick=openUploadFile("'+data[i].fileProperty+'")>上传</a>';
			    		}
			    		var fileSize = data[i].fileSize;
			    		if(fileSize >0){
			    			fileSize = Math.round(data[i].fileSize/1024)+'KB';
			    		}else{
			    			fileSize = "";
			    		}
			    		
					    var str = '<tr><td>'+(i+1)+'</td>'
					    		+'<td>'+data[i].filePropertyName+'</td>'
					    		+'<td>'+fileName+'</td>'
					    		+'<td>'+fileSize+'</td>'
					    		+'<td>'+data[i].uploadDttm+'</td>'
					    		+'<td>'+caozuoStr+'</td></tr>';
					    $("#dataInfoList tbody").append(str);
			    	}
			    }
		  		}
		  	});
	}
	//删除上传资料
	function delDataInfo(dataId){
		layer.confirm("确认删除?", {
			icon : 3,
			btn : [ '是','否' ]
		//按钮
		}, function() {
				$.ajax({
					type: "POST",
			        url: "${ctx}projectController/delDataInfo.action",
			        data:{"dataId":dataId},
			        dataType: "json",
			        success: function(data){
						if (data && data.header["success"]) {
							layer.confirm(data.header["msg"], {
								icon : 6,
								btn : [ '是' ]
							//按钮
							}, function() {
							    layer.closeAll('dialog');
							    getProjectDataInfo();
							});
			        	}
			        }
				});
		}, function() {
		});
	}
	
	//金额格式化
	function initformat(obj) {
		$(obj).formatCurrency();
	}
	//计算咨询费
	function getGuaranteeFee(){
		$("#planLoanMoney").toNumber();
		$("#loanDays").toNumber();
		var planLoanMoney = $("#planLoanMoney").val();//借款金额
		var planLoanDays = $("#loanDays").val();//借款天数
		var rate = $("#rate").val();
		var guaranteeFee = (planLoanMoney*rate/100*planLoanDays/30).toFixed(2);
		$("#guaranteeFee").val(guaranteeFee);
		changForeRate();
	}
	//文件上传对话框监听器
	function fileUploadModalListener() {
		$('#fileUploadModal').on('hide.bs.modal', function () {
			if($("#fileQueue .fileName") != null && $("#fileQueue .fileName").length  > 0 ){
				if(window.confirm('关闭窗口会取消未上传文件，是否关闭？')){
			        $('#uploadify').uploadify('cancel','*');
	                return true;
	             }else{
					$('#fileUploadModal').modal('show');
	                return false;
	            }
			}
			});
	}
	function initUploadify() {
		//多文件上传  //兼容火狐 需要  sessionid，否则拦截登陆302
		var uploadFileCount = 0 ;
	    $("#uploadify").uploadify({  
	        'swf'       : '${ctx}js/plugins/uploadify/uploadify.swf',  
	        'uploader'  : '${ctx}projectController/uploadProjectFile.action;jsessionid=${pageContext.session.id}',    
	        //'folder'         : '/upload',  
	        'queueID'        : 'fileQueue',
	        'cancelImg'      : '${ctx}js/plugins/uploadify/css/img/uploadify-cancel.png',
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
				getProjectDataInfo();
				layer.alert("上传完成"+uploadFileCount+"个文件!", {
					icon : 6
				});
				uploadFileCount = 0;
			}
		});
	}
	//打开上传文件对口框
	function openUploadFileModal(){
		if(pid>0){
			getProjectFileTypeList("");
			$('#fileUploadModal').modal('show');
		}else{
			layer.alert('请先保存业务申请信息！', {
				icon : 0
			});
			return;
		}
	}
	//上传文件
	function uploadFile(){
		if($("#fileQueue .fileName") == null || $("#fileQueue .fileName").length  == 0 ){
			layer.alert("请选择文件", {icon : 5});
			return false;
		}
		var fileProperty = $("#fileProperty").val();
		if(fileProperty <=0){
			layer.alert('请选择文件类型', {
				icon : 0
			});
			return;
		}
		//多文件上传方式
		$('#uploadify').uploadify('settings','formData',{
			'projectId':pid,
			'fileProperty':fileProperty
		
		});
		$('#uploadify').uploadify('upload','*');
	}
	//获取上传资料类型
	function getProjectFileTypeList(fileProperty){
		var projectId= $("#pid").val();
		var url = "${ctx}projectController/searchDataType.action?projectId="+projectId;
		$("#fileProperty").html("");
		$("#fileProperty").prepend("<option value='0'>请选择</option>");
		$.ajax({
			url:url,
			type:"post",
			dataType:"json",
		  	success:function(data){
		  		if(data.length>0){
			    	for(var i=0;i<data.length;i++){
			    		$("#fileProperty").append("<option value='"+data[i].pid+"'>"+data[i].lookupDesc+"</option>");
			    	}
			    	$("#fileProperty").val(fileProperty);
			    }
		  		}
		  	});
	}
	
	//打开物业信息页面
	function openAddHouseModal(){
		var length = $("#houseInfoList tr").length-1;
		//暂时只能保存5个物业信息
		if(length >= 5){
			layer.alert('已达到物业数量的上限，请去除一个再添加！', {
				icon : 2
			});
			return;
		}
		$('#houseInfoForm').resetForm();
		$('#houseId').val(0);
		$('#houseInfoModal').modal('show');
	}
	
	//绑定省
	function proviceBind() {
	    //清空下拉数据
	    $("#houseProvinceCode").html("");

	    var str = "<option value='-1'>--请选择--</option>";
	    $.ajax({
	        type: "POST",
	        url: "${ctx}sysAreaInfoController/getSysAreaInfo.action",
	        data: { "parentCode": "", "levelNo": "1" },
	        dataType: "JSON",
	        async: false,
	        success: function (data) {
	            //从服务器获取数据进行绑定
	            $.each(data, function (i, item) {
	                str += "<option value=" + item.areaCode + ">" + item.areaName + "</option>";
	            })
	            //将数据添加到省份这个下拉框里面
	            $("#houseProvinceCode").append(str);
	        },
	        error: function () { alert("Error"); }
	    });
	}
	//绑定市
	function cityBind() {
	    var provice = $("#houseProvinceCode").val();
	    //判断省份这个下拉框选中的值是否为空
	    if (provice == "") {
	        return;
	    }
	    $("#houseCityCode").html("");
	    var str = "<option value='-1'>--请选择--</option>";
	    $.ajax({
	        type: "POST",
	        url: "${ctx}sysAreaInfoController/getSysAreaInfo.action",
	        data: { "parentCode": provice, "levelNo": "2" },
	        dataType: "JSON",
	        async: false,
	        success: function (data) {
	            //从服务器获取数据进行绑定
	            $.each(data, function (i, item) {
	                str += "<option value=" + item.areaCode + ">" + item.areaName + "</option>";
	            })
	            //将数据添加到省份这个下拉框里面
	            $("#houseCityCode").append(str);
	        },
	        error: function () { alert("Error"); }
	    });
	}
	//绑定区
	function villageBind() {
	    var houseCityCode = $("#houseCityCode").val();
	    //判断市这个下拉框选中的值是否为空
	    if (houseCityCode == "") {
	        return;
	    }
	    $("#houseDistrictCode").html("");
	    var str = "<option value='-1'>--请选择--</option>";
	    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
	    $.ajax({
	        type: "POST",
	        url: "${ctx}sysAreaInfoController/getSysAreaInfo.action",
	        data: { "parentCode": houseCityCode, "levelNo": "3" },
	        dataType: "JSON",
	        async: false,
	        success: function (data) {
	            //从服务器获取数据进行绑定
	            $.each(data, function (i, item) {
	            	str += "<option value=" + item.areaCode + ">" + item.areaName + "</option>";
	            })
	            //将数据添加到省份这个下拉框里面
	            $("#houseDistrictCode").append(str);
	        },
	        error: function () { alert("Error"); }
	    });
	}
	
	//保存物业信息
	function saveHouseInfo(){
		var projectId= $("#pid").val();
		$("#projectId").val(projectId);
		
		var evaluationNet = $("#evaluationNet").val();
		if(evaluationNet == ""){
			$("#evaluationNet").val(0.00);
		}
		
		if($("#houseInfoForm").validate().form()){
			$(".currency").toNumber();
			$.ajax({
				url : "${ctx}projectController/saveHouseInfo.action",
				type : "POST",
				data : $("#houseInfoForm").serialize(),
				async : false,
				success : function(result) { //表单提交后更新页面显示的数据
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						var houseId = ret.header["houseId"];
						layer.alert('保存成功！', {
							icon : 1
						}); 
						$('#houseInfoModal').modal('hide');
						loadHouseInfo(houseId);
					} else {
						layer.alert(ret.header["msg"], {
							icon : 5
						});
					}
				}
			});
		}
	}
	
	//获取物业列表
	function loadHouseInfo(houseId){
		var houseIds = "";
		$.each($("#houseInfoList").find("tr:gt(0)"), function(k, v) {
			$.each($(v).find("input"),function(m, n) {
				houseIds += $(n).val()+",";
			});
		});
		
		houseIds+= houseId+"";

		var url = "${ctx}projectController/getHouseList.action?houseIds="+houseIds;
		$("#houseInfoList tbody").html("");
		$.ajax({
			url:url,
			type:"post",
			dataType:"json",
		  	success:function(data){
		  		if(data.length>0){
			    	for(var i=0;i<data.length;i++){
			    		var checkStr = '<input type="checkbox" class="isChecked" value="'+data[i].houseId+'"/>';
					    var cazuo = '<a onclick=openEditHouseModal(this,"'+data[i].houseProvinceCode+'","'+data[i].houseCityCode+'","'+data[i].houseDistrictCode+'")>编辑</a>'+'|<a onclick=delHouseInfo("'+data[i].houseId+'")>删除</a>'
			    		var str = '<tr><td>'+checkStr+'</td>'
					    		+'<td>'+data[i].houseName+'</td>'
					    		+'<td>'+data[i].housePropertyCard+'</td>'
					    		+'<td>'+data[i].area+'</td>'
					    		+'<td>'+data[i].costMoney+'</td>'
					    		+'<td>'+data[i].evaluationPrice+'</td>'
					    		+'<td>'+data[i].tranasctionMoney+'</td>'
					    		+'<td>'+data[i].evaluationNet+'</td>'
					    		+'<td>'+data[i].purpose+'</td>'
					    		+'<td>'+data[i].houseAddress+'</td>'
					    		+'<td>'+cazuo
					    		+'</td></tr>';
					    $("#houseInfoList tbody").append(str);
			    	}
			    }
		  		//重新计算总评估价、总成交价以及赎楼成数
		  		operate();
		  		}
		  	});
	}
	//删除物业信息
	function delHouseInfo(houseId){
		layer.confirm("确认删除?", {
			icon : 3,
			btn : [ '是','否' ]
		//按钮
		}, function() {
			$.ajax({
				type: "POST",
		        url: "${ctx}projectController/delHouse.action",
		        data:{"houseIds":houseId},
		        dataType: "json",
		        success: function(ret){
		        	//var ret = eval("("+data+")");
		        	if(ret && ret.header["success"]){
						// 操作提示
						layer.alert('操作成功！', {
							icon : 1
						}); 
						//重新加载
						loadHouseInfo("");
		        	}else{
		        		layer.alert(ret.header["msg"], {
							icon : 5
						});
					}
		        }
			});
		}, function() {
		});
	}
	
	//修改物业信息
	function openEditHouseModal(thisObj,houseProvinceCode,houseCityCode,houseDistrictCode){
		//获取编辑行的数据
		var $td = $(thisObj).parents('tr').children('td');
		var houseId = $td.eq(0).find("input").val(); //第一个td的内容  
		var houseName = $td.eq(1).text();//第二个td的内容 
		var housePropertyCard = $td.eq(2).text();//第三个td的内容 
		var area = $td.eq(3).text();//第四个td的内容 
		var costMoney = $td.eq(4).text();//第五个td的内容 
		var evaluationPrice = $td.eq(5).text();//第六个td的内容 
		var tranasctionMoney = $td.eq(6).text();//第七个td的内容 
		var purpose = $td.eq(8).text();//第9个td的内容 
		var evaluationNet = $td.eq(7).text();//第八个td的内容 
		
		$("#houseId").val(houseId);
		$("#houseName").val(houseName);
		$("#housePropertyCard").val(housePropertyCard);
		$("#area").val(area);
		$("#costMoney").val(costMoney);
		$("#evaluationPrice").val(evaluationPrice);
		$("#tranasctionMoney").val(tranasctionMoney);
		$("#purpose").val(purpose);
		$("#houseProvinceCode").val(houseProvinceCode);
		
		$("#evaluationNet").val(evaluationNet);
		cityBind();
		$("#houseCityCode").val(houseCityCode);
		villageBind();
		$("#houseDistrictCode").val(houseDistrictCode);
	    
	    $('#houseInfoModal').modal('show');
	}
	//计算总评估价、总成交价以及赎楼成数
	function operate(){
		var evaluationPrice = 0.0;//评估价
		var tranasctionMoney = 0.0;//成交价
		var evaluationNet = 0.0;//评估净值
		var houseName="";
		var housePropertyCard = "";
		var houseIds ="";
    	$.each($("#houseInfoList").find("tr:gt(0)"), function(k, v) {
    		var $td = $(v).children('td');
    		 houseIds += $td.eq(0).find("input").val()+','; //第一个td的内容
    		 houseName += $td.eq(1).text()+',';//第二个td的内容 
    		 housePropertyCard += $td.eq(2).text()+',';//第三个td的内容 
    		 evaluationPrice += Number($td.eq(5).text());//第六个td的内容 
    		 tranasctionMoney += Number($td.eq(6).text());//第七个td的内容 
    		 evaluationNet +=Number($td.eq(7).text());
		});
    	if(houseName != ""){
    		houseName = houseName.substring(0, houseName.length-1);
    	}
    	if(housePropertyCard != ""){
    		housePropertyCard = housePropertyCard.substring(0, housePropertyCard.length-1);
    	}
    	if(houseIds != ""){
    		houseIds = houseIds.substring(0, houseIds.length-1);
    	}
    	$("#houseIds").val(houseIds);
		$("#houseName1").val(houseName);
		$("#housePropertyCard1").val(housePropertyCard);
		$("#evaluationPrice1").val(evaluationPrice);
		$("#tranasctionMoney1").val(tranasctionMoney);
		$("#evaluationNet1").val(evaluationNet);
		changForeRate();
		$(".currency").formatCurrency();
	}
	
	//赎楼成数
	function changForeRate(){
		$("#planLoanMoney").toNumber();
		$("#evaluationPrice1").toNumber();
		var loanMoney = $("#planLoanMoney").val();
		//评估价
		var evaluationPrice = $("#evaluationPrice1").val();
		
		if(evaluationPrice > 0){
			var foreRate = loanMoney/evaluationPrice;
			foreRate = (foreRate*100).toFixed(2)
			$("#foreRate").val(foreRate);
		}
	}
	//驳回补充资料提交
	function saveReject(){
		layer.confirm("确定所有资料都已补充完毕吗?", {
			icon : 3,
			btn : [ '是','否' ]
		//按钮
		}, function() {
			$.post("${ctx}/projectController/saveReject.action", {
				projectId : pid
			}, function(result) {
				if (result && result.header["success"]) {
					layer.confirm('提交成功', {
						icon : 6,
						btn : [ '是' ]
					//按钮
					}, function() {
						refreshTab("${ctx}projectController/toProjectIndex.action","${ctx}projectController/toProjectIndex.action");
						closeTab();
					}); 
				} else {
					layer.alert(ret.header["msg"], {
						icon : 5
					});
				}
			}, "json");
		}, function() {
		});
	}
	
	function openUploadFile(fileProperty){
		if(pid>0){
			getProjectFileTypeList(fileProperty);
			$('#fileUploadModal').modal('show');
		}else{
			layer.alert('请先保存业务申请信息！', {
				icon : 0
			});
			return;
		}
	}
	
	//绑定原贷款类型
	function comboxBind(id,type,selVal) {
	    //清空下拉数据
	    $("#"+id).html("");

	    var str = "";
	    $.ajax({
	        type: "POST",
	        url: "${ctx}projectController/getSysLookupValByLookType.action",
	        data: { "lookupType": type },
	        dataType: "JSON",
	        async: false,
	        success: function (data) {
	            //从服务器获取数据进行绑定
	            $.each(data, function (i, item) {
	                str += "<option value=" + item.pid + ">" + item.lookupDesc + "</option>";
	            })
	            
	            //将数据添加到原贷款类型这个下拉框里面
	            $("#"+id).append(str);
	        },
	        error: function () { alert("Error"); }
	    });
	    if(selVal!='' && selVal!='0' && selVal!='-1'){
	    	$("#"+id).val(selVal);
	    }
	}
	//打开原贷款新增页面
	function openAddOriginalLoanModal(){
		var length = $("#originalLoanInfoList tr").length-1;
		//暂时只能保存5个原贷款信息
		if(length >= 5){
			layer.alert('已达到原贷款信息的上限，请去除一个再添加！', {
				icon : 2
			});
			return;
		}
		
		$('#originalLoanInfoForm').resetForm();
		$('#originalLoanId').val(0);
		setEstateCombox();
		$('#originalLoanInfoModal').modal('show');
	}

	//保存原贷款信息
	function saveOriginalLoanInfo(){
		var projectId= $("#pid").val();
		$("#projectId1").val(projectId);
		if($("#originalLoanInfoForm").validate().form()){
			$(".currency").toNumber();
			$.ajax({
				url : "${ctx}projectController/saveOriginalLoanInfo.action",
				type : "POST",
				data : $("#originalLoanInfoForm").serialize(),
				async : false,
				success : function(result) { //表单提交后更新页面显示的数据
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						var originalLoanId = ret.header["originalLoanId"];
						layer.alert('保存成功！', {
							icon : 1
						}); 
						$('#originalLoanInfoModal').modal('hide');
						loadOriginalLoanInfo(originalLoanId);
					} else {
						layer.alert(ret.header["msg"], {
							icon : 5
						});
					}
				}
			});
		}
	}
	
	//获取原贷款列表
	function loadOriginalLoanInfo(originalLoanId){
		var originalLoanIds = "";
		$.each($("#originalLoanInfoList").find("tr:gt(0)"), function(k, v) {
			$.each($(v).find("input"),function(m, n) {
				originalLoanIds += $(n).val()+",";
			});
		});
		
		originalLoanIds+= originalLoanId+"";
		
		var url = "${ctx}projectController/getOriginalLoanList.action?originalLoanIds="+originalLoanIds;
		$("#originalLoanInfoList tbody").html("");
		$.ajax({
			url:url,
			type:"post",
			dataType:"json",
		  	success:function(data){
		  		if(data.length>0){
			    	for(var i=0;i<data.length;i++){
			    		var checkStr = '<input type="checkbox" class="isChecked" value="'+data[i].originalLoanId+'"/>';
					    var cazuo = '<a onclick=openEditOriginalLoanModal(this,"'+data[i].loanType+'","'+data[i].oldLoanBank+'","'+data[i].estateId+'")>编辑</a>'+'|<a onclick=delOriginalLoanInfo("'+data[i].originalLoanId+'")>删除</a>'
			    		var oldLoanPerson = data[i].oldLoanPerson==null?"":data[i].oldLoanPerson;
					    var oldLoanPhone = data[i].oldLoanPhone==null?"":data[i].oldLoanPhone;
					    var oldLoanAccount = data[i].oldLoanAccount==null?"":data[i].oldLoanAccount;
					    var str = '<tr><td>'+checkStr+'</td>'
					    		+'<td>'+data[i].loanTypeStr+'</td>'
					    		+'<td>'+data[i].oldLoanBankStr+'</td>'
					    		+'<td>'+data[i].oldLoanMoney+'</td>'
					    		+'<td>'+data[i].oldOwedAmount+'</td>'
					    		+'<td>'+data[i].oldLoanTime+'</td>'
					    		+'<td>'+data[i].estateName+'</td>'
					    		+'<td>'+oldLoanAccount+'</td>'
					    		+'<td>'+oldLoanPerson+'</td>'
					    		+'<td>'+oldLoanPhone+'</td>'
					    		+'<td>'+cazuo
					    		+'</td></tr>';
					    $("#originalLoanInfoList tbody").append(str);
			    	}
			    }
		  		//重新计算原贷款总借款金额，总欠款金额
		  		operateOriginalLoan();
		  		}
		  	});
	}
	//删除原贷款信息
	function delOriginalLoanInfo(originalLoanId){
		layer.confirm("确认删除?", {
			icon : 3,
			btn : [ '是','否' ]
		//按钮
		}, function() {
			$.ajax({
				type: "POST",
		        url: "${ctx}projectController/delOriginalLoan.action",
		        data:{"originalLoanIds":originalLoanId},
		        dataType: "json",
		        success: function(ret){
		        	//var ret = eval("("+data+")");
		        	if(ret && ret.header["success"]){
						// 操作提示
						layer.alert('操作成功！', {
							icon : 1
						}); 
						//重新加载
						loadOriginalLoanInfo("");
		        	}else{
		        		layer.alert(ret.header["msg"], {
							icon : 5
						});
					}
		        }
			});
		}, function() {
		});
	}
	
	//修改物业信息
	function openEditOriginalLoanModal(thisObj,loanType,oldLoanBank,estateId){
		//获取编辑行的数据
		var $td = $(thisObj).parents('tr').children('td');
		var originalLoanId = $td.eq(0).find("input").val(); //第一个td的内容  
		var oldLoanMoney = $td.eq(3).text();//第四个td的内容 
		var oldOwedAmount = $td.eq(4).text();//第五个td的内容 
		var oldLoanTime = $td.eq(5).text();//第六个td的内容 
		var oldLoanPerson = $td.eq(8).text();//第9个td的内容 
		var oldLoanPhone = $td.eq(9).text();//第10个td的内容 
	    var oldLoanAccount = $td.eq(7).text();
	    setEstateCombox();
		
		$("#originalLoanId").val(originalLoanId);
		$("#oldLoanMoney").val(oldLoanMoney);
		$("#oldOwedAmount").val(oldOwedAmount);
		$("#oldLoanTime").val(oldLoanTime);
		$("#oldLoanPerson").val(oldLoanPerson);
		$("#oldLoanPhone").val(oldLoanPhone);
		$("#loanType").val(loanType);
		$("#oldLoanBank").val(oldLoanBank);
	    $('#originalLoanInfoModal').modal('show');
	    $("#estateId").val(estateId);
	    $("#oldLoanAccount").val(oldLoanAccount);
	    
	}
	
	function operateOriginalLoan(){
		var oldLoanMoney = 0.0;//原贷款借款金额
		var oldOwedAmount = 0.0;//原贷款欠款金额
		var originalLoanIds ="";
    	$.each($("#originalLoanInfoList").find("tr:gt(0)"), function(k, v) {
    		var $td = $(v).children('td');
    		 originalLoanIds += $td.eq(0).find("input").val()+','; //第一个td的内容
    		 oldLoanMoney += Number($td.eq(3).text());//第四个td的内容 
    		 oldOwedAmount += Number($td.eq(4).text());//第五个td的内容 
		});
    	if(originalLoanIds != ""){
    		originalLoanIds = originalLoanIds.substring(0, originalLoanIds.length-1);
    	}
    	
    	$("#originalLoanIds").val(originalLoanIds);
		$("#oldLoanMoney1").val(oldLoanMoney);
		$("#oldOwedAmount1").val(oldOwedAmount);
		$(".currency").formatCurrency();
	}
	//物业下拉框
	function setEstateCombox() {
	    //清空下拉数据
	    $("#estateId").html("");

	    var houseIds = "";
		$.each($("#houseInfoList").find("tr:gt(0)"), function(k, v) {
			$.each($(v).find("input"),function(m, n) {
				houseIds += $(n).val()+",";
			});
		});
		if(houseIds != ""){
    		houseIds = houseIds.substring(0, houseIds.length-1);
    	}
	    var str = "";
	    $.ajax({
	        type: "POST",
	        url: "${ctx}projectController/getEstateList.action",
	        data: { "houseIds": houseIds },
	        dataType: "JSON",
	        async: false,
	        success: function (data) {
	            //从服务器获取数据进行绑定
	            $.each(data, function (i, item) {
	                str += "<option value=" + item.houseId + ">" + item.houseName + "</option>";
	            })
	            
	            //将数据添加到原贷款类型这个下拉框里面
	            $("#estateId").append(str);
	        },
	        error: function () { alert("Error"); }
	    });
	}
</script>
<style type="text/css">
.col-sm-3{padding-right: 15px;}
 .btn-primary[disabled]{ background-color:#fff; border-color:#005E98;}
 .row{margin-right: -5px;margin-left: -5px;}
 .col-sm-4{position: relative;}
 .col-sm-4 i{position: absolute;right: -5px;top: 7px;font-style: normal;}
 .modal-body .control-label {padding-top: 7px;margin-bottom: 0; text-align: right;}
 .table {border: 1px solid #DDD;}
 .table th{border-right: 1px solid #DDD;background-color: #F5F5F6;}
 .table th:last-child{border-right: none;}
 .table th, .table td{ font-size: 13px;}
  .table td{border-bottom: 1px solid #DDD;border-right: 1px solid #DDD;}
 .table td:last-child{border-right: none;}
 .left_bj{margin-left: 2%;}
 #person_pager_list #person_pager_list_left, #public_pager_list #public_pager_list_left{ width: 50px}
</style>
</head>
<body class="gray-bg">
 <div class="wrapper wrapper-content animated fadeInRight" id="projectInfo">
 <form role="form" id="projectInfoForm" class="form-horizontal" action="#" method="post">
        <input type="hidden" name="pid" id="pid" value="${project.pid }">
        <input type="hidden" name="houseIds" id="houseIds">
        <input type="hidden" name="userPids" id="userPids">
        <input type="hidden" name="originalLoanIds" id="originalLoanIds">
        <input type="hidden" name="innerOrOut" value="2">
        <input type="hidden" name="projectSource" value="3">
        <input type="hidden" value="${companyInfo.rate }" id="rate" name="loanRate">
        <input type="hidden" name="projectGuarantee.feeRate" value="${project.projectGuarantee.feeRate > 0?project.projectGuarantee.feeRate:companyInfo.rate }">
        <input type="hidden" name="isNeedHandle" id="isNeedHandle" value="${companyInfo.isNeedHandle }">
        <input type="hidden" name="requestStatus" id="requestStatus" value="${project.requestStatus == 0?1:project.requestStatus }">
        <input type="hidden" name="projectForeclosure.pid" id="foreclosureId" value="${project.projectForeclosure == null?0:project.projectForeclosure.pid }">
        <input type="hidden" name="projectProperty.pid" id="propertyId" value="${project.projectProperty == null?0:project.projectProperty.pid }">
        <input type="hidden" name="projectGuarantee.pid" id="guaranteeId" value="${project.projectGuarantee == null?0:project.projectGuarantee.pid }">
  		<input type="hidden" name="businessSource" value="13782">
  		<input type="hidden" name="businessSourceNo" value="${companyInfo.orgId}"/>
  <div class="row" id="customerRow">
   <div class="ibox float-e-margins"> 
    <div class="ibox-title">
    <c:if test="${project.pid>0 && editType != 3}">
    	<h5>编辑业务申请</h5>
    </c:if>
     <c:if test="${project.pid>0 && editType == 3}">
    	<h5>查看业务申请</h5>
    </c:if>
    <c:if test="${project == null || project.pid==0}">
    	<h5>新增业务申请</h5>
    </c:if>
     <div class="ibox-tools">
      <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
      </a> 
     </div>
    </div>
    <div class="ibox-content ibox-line">
     	<div class="row">
			<div class="col-sm-10">
				<div class="form-group">
				   <label class="col-sm-3 control-label">可用额度:</label>
				   <div class="col-sm-3">
				    	<input type="text" class="form-control" id="availableLimit" value="<fmt:formatNumber pattern="#,#00" value="${companyInfo.availableLimit }"/>" maxlength="20" readonly="readonly"><i>元</i>
				 	</div>
				 	<label class="col-sm-3 control-label">单笔借款上限:</label>
					<div class="col-sm-3">
					  	<input type="text" class="form-control" value="<fmt:formatNumber pattern="#,#00" value="${companyInfo.singleUpperLimit }"/>" id="singleUpperLimit" readonly="readonly"><i>元</i>
					</div>
				</div>
				<div class="form-group">
				 	<label for="orgCustomerName" class="col-sm-3 control-label red"><b>*</b>客户姓名:</label>
				 	<div class="col-sm-3">
				 		<input type="hidden" name="acctId" value="${project.acctId }" id="acctId">
				  		<input type="text" class="form-control" readonly="readonly" value="${project.orgCustomerName }" id="chinaName" name="orgCustomerName" placeholder="客户姓名">
					</div>
					<input type="button" class="btn btn-outline btn-primary left_bj" id="SelectPer_button" value="选择借款人" name="commit" onclick="openSelectPer()"> 
				</div>
				<div class="form-group turnover">
		        	<label for="certType" class="col-sm-3 control-label">证件类型：</label>
		         	<div class="col-sm-3">
		          		<input type="text" class="form-control" readonly="readonly" id="certType" name="certType">
		         	</div>
		        	<label for="orgCustomerCard" class="col-sm-3 control-label">证件号码：</label>
		         	<div class="col-sm-3">
		          		<input type="text" class="form-control" readonly="readonly" value="${project.orgCustomerCard }" id="certNumber" name="orgCustomerCard">
		         	</div>
		        </div>
		        <div class="form-group">
		        	<label for="sexName" class="col-sm-3 control-label">性别：</label>
		         	<div class="col-sm-3">
		          		<input type="text" class="form-control" readonly="readonly" id="sexName" name="sexName">
		         	</div>
		        	<label for="orgCustomerPhone" class="col-sm-3 control-label">手机号码：</label>
		         	<div class="col-sm-3">
		          		<input type="text" class="form-control" readonly="readonly" value="${project.orgCustomerPhone }" id="perTelephone" name="orgCustomerPhone">
		         	</div>
		        </div>
		        <div class="form-group">
		        	<label for="mail" class="col-sm-3 control-label">电子邮箱：</label>
		         	<div class="col-sm-3">
		          		<input type="text" class="form-control" readonly="readonly" id="mail" name="mail">
		         	</div>
		        	<label for="liveAddr" class="col-sm-3 control-label">通讯地址：</label>
		         	<div class="col-sm-3">
		          		<input type="text" class="form-control"  readonly="readonly" id="liveAddr" name="liveAddr">
		         	</div>
		        </div>
		    </div>
		    </div>
		    <div class="row">
			<div class="col-sm-12">
		        <div class="form-group"> 
			     	<c:if test="${project.isReject != 3 }">
			         <input type="button" class="btn btn-outline btn-primary left_bj" value="选择共同借款人" name="commit" onclick="openPublic()"> 
			       	</c:if>
			       </div>
			     <table class="table" id="publicInfoList">
				    <thead>
				     <tr>
				      <th width="5%">序号</th>
				      <th width="15%">姓名</th>
				      <th width="10%">性别</th>
				      <th width="12%">证件类型</th>
				      <th width="20%">证件号码</th>
				      <th width="10%">与客户关系</th>
				      <th width="12%">手机号码</th>
				      <th width="9%">产权占比</th>
				      <th width="6%">操作</th>
				     </tr>
				    </thead>
				    <tbody>
				    <c:forEach items="${publicList }" var="publicInfo" varStatus="status">
				     <tr>
				      <td><input type="checkbox" value='${publicInfo.projectPublicManId}'></td>
				      <td>${publicInfo.chinaName }</td>
				      <td>${publicInfo.sexName }</td>
				      <td>${publicInfo.certTypeName }</td>
				      <td>${publicInfo.certNumber }</td>
				      <td>${publicInfo.relationText }</td>
				      <td>${publicInfo.perTelephone }</td>
				      <td>${publicInfo.proportionProperty }</td>
				      <td>
				      <c:if test="${editType != 3 && project.isReject != 3}">
				      	<a href="#" onclick="delPublicInfo(${publicInfo.pid})">删除</a></td>
				      </c:if>
				     </tr>
				     </c:forEach>
				    </tbody>
				   </table>
	    	</div>
    	</div>
    </div>
  </div>
</div>
    
<div class="row" id="projectRow">
   <div class="ibox float-e-margins"> 
    <div class="ibox-title">
		<h5>项目基本信息</h5>
		<div class="ibox-tools">
		<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> 
		</div>
	</div>
    <div class="ibox-content ibox-line">
     	<div class="row">
			<div class="col-sm-10">
				<div class="form-group">
				 	<label for="productId" class="col-sm-3 control-label red"><b>*</b>产品名称:</label>
				 	<div class="col-sm-3">
				  		<select class="form-control selectNone" name="productId" id="productId">
					    	<option value="-1">--请选择--</option>
							<c:forEach items="${productList }" var="product" varStatus="status">
								<option value="${product.pid}" productNum="${product.productNumber }" <c:if test="${product.pid == project.productId}">selected</c:if>  >${product.productName}</option>
							</c:forEach>
				 		</select>
					</div>
					<label for="businessCategory" class="col-sm-3 control-label red"><b>*</b>业务类型:</label>
					<div class="col-sm-3">
						<select class="form-control selectNone" name="businessCategory" id="businessCategory">
						</select>
					</div>
				</div>
				<div class="form-group">
				 	<label for="areaCode" class="col-sm-3 control-label red"><b>*</b>收费类型:</label>
				 	<div class="col-sm-3">
					  	<select class="form-control selectNone" name="projectGuarantee.chargeType" id="chargeType">
					    	<option value="">--请选择--</option>
		 				 	<option value=1 <c:if test="${project.projectGuarantee.chargeType==1 }">selected="selected"</c:if>>标准业务</option>
           					<option value=2 <c:if test="${project.projectGuarantee.chargeType==2 }">selected="selected"</c:if>>非标准业务</option>
					 	</select>
					</div>
					<label for="projectGuarantee.foreclosureMoney" style="display:none;" class="col-sm-3 control-label red foreclosure"><b>*</b>赎楼金额:</label>
					<div class="col-sm-3 foreclosure" style="display:none;">
				 		<input type="text" class="form-control currency required isFloatGteMoney foreclosure" id="foreclosureMoney"  onchange="changeMoney()" onblur="initformat(this)" onkeypress="return onlynum(event)" value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectGuarantee.foreclosureMoney == 0 ? '':project.projectGuarantee.foreclosureMoney }"/>" name="projectGuarantee.foreclosureMoney">
				 	</div>
				</div>
				<div class="form-group turnover" style="display:none;">
		        	<label for="projectGuarantee.turnoverMoney" class="col-sm-3 control-label red"><b>*</b>周转金额：</label>
		         	<div class="col-sm-3">
		          		<input type="text" class="form-control currency isFloatGteMoney" id="turnoverMoney" onkeypress="return onlynum(event)" onchange="changeMoney()" onblur="initformat(this)" onkeypress="return onlynum(event)" value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectGuarantee.turnoverMoney == 0 ? '':project.projectGuarantee.turnoverMoney }"/>" name="projectGuarantee.turnoverMoney">
		         	</div>
		        	<label for="projectForeclosure.turnoverCapitalBank" class="col-sm-3 control-label red"><b>*</b>周转资金开户行：</label>
		         	<div class="col-sm-3">
		          		<input type="text" class="form-control" id="turnoverCapitalBank" value="${project.projectForeclosure.turnoverCapitalBank }" name="projectForeclosure.turnoverCapitalBank" maxlength="30">
		         	</div>
		        </div>
		        <div class="form-group turnover" style="display:none;">
		        	<label for="projectForeclosure.turnoverCapitalName" class="col-sm-3 control-label red"><b>*</b>周转资金户名：</label>
		         	<div class="col-sm-3">
		          		<input type="text" class="form-control" id="turnoverCapitalName" value="${project.projectForeclosure.turnoverCapitalName }" name="projectForeclosure.turnoverCapitalName" maxlength="30">
		         	</div>
		        	<label for="projectForeclosure.turnoverCapitalAccount" class="col-sm-3 control-label red"><b>*</b>周转资金账号：</label>
		         	<div class="col-sm-3">
		          		<input type="text" class="form-control" id="turnoverCapitalAccount" value="${project.projectForeclosure.turnoverCapitalAccount }" name="projectForeclosure.turnoverCapitalAccount" maxlength="50">
		         	</div>
		        </div>
				
				<div class="form-group">
					<label for="projectGuarantee.loanMoney" class="col-sm-3 control-label red"><b>*</b>借款金额:</label>
					<div class="col-sm-3">
				 		<input type="text" class="form-control currency required isFloatGtMoney" id="planLoanMoney" onblur="initformat(this)" onchange="getGuaranteeFee()" onkeypress="return onlynum(event)" value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectGuarantee.loanMoney == 0 ? '':project.projectGuarantee.loanMoney }"/>" name="projectGuarantee.loanMoney" placeholder="借款金额">
				 	</div>
				 	<label for="projectForeclosure.loanDays" class="col-sm-3 control-label red"><b>*</b>借款天数:</label>
				 	<div class="col-sm-3">
				  		<input type="text" class="form-control required isIntGtZero" id="loanDays" onkeypress="return onlynum(event)"  onchange="getGuaranteeFee()" value="<fmt:formatNumber pattern="#,###.##" value="${project.projectForeclosure.loanDays }"/>" name="projectForeclosure.loanDays" placeholder="借款天数" maxlength="5"><i>天</i>
					</div>
				</div>
				<div class="form-group">
					<label for="projectGuarantee.guaranteeFee" class="col-sm-3 control-label red"><b>*</b>咨询费:</label>
					<div class="col-sm-3">
				 		<input type="text" class="form-control currency required isFloatGtMoney" onblur="initformat(this)" id="guaranteeFee" onkeypress="return onlynum(event)" value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectGuarantee.guaranteeFee }"/>" name="projectGuarantee.guaranteeFee" placeholder="咨询费">
				 	</div>
					<label for="projectForeclosure.receDate" class="col-sm-3 control-label red"><b>*</b>计划放款日期:</label>
					<div class="col-sm-3">
				 		<input type="text" class="form-control input-sm laydate-icon layer-date required" value="${project.projectForeclosure.receDate }" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"  id="planLoanDate" name="projectForeclosure.receDate" placeholder="放款日期">
				     </div>
				</div>
				<div class="form-group">
					<label for="areaCode" class="col-sm-3 control-label red"><b>*</b>所属城市:</label>
				 	<div class="col-sm-3">
					  	<select class="form-control selectNone" name="areaCode" id="areaCode">
					    	<option value="-1">--请选择--</option>
							<c:forEach items="${cityInfoList }" var="cityInfo" varStatus="status">
								<option value="${cityInfo.areaCode}" <c:if test="${cityInfo.areaCode == project.areaCode}">selected</c:if>  >${cityInfo.cityName}</option>
							</c:forEach>
					 	</select>
					</div>
					<label for="address" class="col-sm-3 control-label">区域：</label>
		         	<div class="col-sm-3">
		          		<input type="text" class="form-control" value="${project.address }" name="address" maxlength="50">
		         	</div>
				</div>
				<div class="form-group">
					<label for="surveyResult" class="col-sm-3 control-label red">特殊情况说明:</label>
					<div class="col-sm-9">
						  <textarea rows="5" cols="10" class="form-control" name="surveyResult" id="surveyResult" placeholder="备注"  maxlength="200">${project.surveyResult}</textarea>
					</div>
				</div>
	    	</div>
    	</div>
    </div>
    </div>
</div>    

<div class="row" id="houseRow">
<div class="ibox float-e-margins">
<div class="ibox-title">
	<h5>附加信息--物业信息</h5>
	<div class="ibox-tools">
	<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> 
	</div>
</div>
  <div class="ibox-content ibox-line">
     <div class="row">
     <div class="form-group"> 
     	<c:if test="${project.isReject != 3 }">
         <input type="button" class="btn btn-outline btn-primary left_bj" value="新增" name="commit" onclick="openAddHouseModal()"> 
       	</c:if>
       </div>
	   <table class="table" id="houseInfoList">
	    <thead>
	     <tr>
	      <th width="5%">序号</th>
	      <th width="12%">物业名称</th>
	      <th width="10%">房产证号</th>
	      <th width="8%">面积(㎡)</th>
	      <th width="10%">登记价(元)</th>
	      <th width="10%">评估价(元)</th>
	      <th width="8%">成交价(元)</th>
	      <th width="6%">评估净值(元)</th>
	      <th width="8%">用途</th>
	      <th width="16%">物业地址</th>
	      <th width="8%">操作</th>
	     </tr>
	    </thead>
	    <tbody>
	    <c:forEach items="${project.estateList }" var="houseInfo" varStatus="status">
	     <tr>
	      <td><input type="checkbox" value='${houseInfo.houseId}'></td>
	      <td>${houseInfo.houseName }</td>
	      <td>${houseInfo.housePropertyCard }</td>
	      <td>${houseInfo.area }</td>
	      <td><fmt:formatNumber pattern="#,#00.00#" value="${houseInfo.costMoney }"/></td>
	      <td><fmt:formatNumber pattern="#,#00.00#" value="${houseInfo.evaluationPrice }"/></td>
	      <td><fmt:formatNumber pattern="#,#00.00#" value="${houseInfo.tranasctionMoney }"/></td>
	      <td><fmt:formatNumber pattern="#,#00.00#" value="${houseInfo.evaluationNet }"/></td>
	      <td>${houseInfo.purpose }</td>
	      <td>${houseInfo.houseAddress }</td>
	      <td>
	      <c:if test="${editType != 3 && project.isReject != 3}">
	      	<a href="#" onclick="openEditHouseModal(this,'${houseInfo.houseProvinceCode}','${houseInfo.houseCityCode}','${houseInfo.houseDistrictCode}')">编辑</a>|
	      	<a href="#" onclick="delHouseInfo(${houseInfo.houseId})">删除</a></td>
	      </c:if>
	     </tr>
	     </c:forEach>
	    </tbody>
	   </table>
     <div class="col-sm-10">
   		<input type="hidden" value="${project.projectProperty.houseName }" id="houseName1" name="projectProperty.houseName">
   		<input type="hidden" value="${project.projectProperty.housePropertyCard }" id="housePropertyCard1" name="projectProperty.housePropertyCard">
        <div class="form-group">
         	<label for="projectProperty.evaluationPrice" class="col-sm-3 control-label red"><b>*</b>总评估价:</label>
         	<div class="col-sm-3">
           		<input type="text" class="form-control currency required" onchange="changForeRate()" readonly="readonly" onblur="initformat(this)" onkeypress="return onlynum(event)"  value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectProperty.evaluationPrice}"/>" id="evaluationPrice1" name="projectProperty.evaluationPrice" placeholder="评估价">
         	</div>
         	<label for="projectProperty.tranasctionMoney" class="col-sm-3 control-label red">总成交价:</label>
         	<div class="col-sm-3">
          	 	<input type="text" class="form-control currency required" readonly="readonly" onblur="initformat(this)" onkeypress="return onlynum(event)"  value="<fmt:formatNumber pattern="#,#00.00#" value="${ project.projectProperty.tranasctionMoney}"/>" id="tranasctionMoney1" name="projectProperty.tranasctionMoney" placeholder="成交价">
         	</div>
         </div>
         <div class="form-group">	
         	<label for="projectProperty.evaluationNet" class="col-sm-3 control-label">评估净值:</label>
         	<div class="col-sm-3">
           		<input type="text" id="evaluationNet1" readonly="readonly" class="form-control currency" onblur="initformat(this)" onkeypress="return onlynum(event)" name="projectProperty.evaluationNet" value="<fmt:formatNumber pattern="#,#00.00#" value="${ project.projectProperty.evaluationNet}"/>" placeholder="评估净值">
         	</div>
         	<label for="projectProperty.foreRate" class="col-sm-3 control-label red"><b>*</b>赎楼成数:</label>
         	<div class="col-sm-3">
           		<input type="text" class="form-control currency required" readonly="readonly" onblur="initformat(this)" onkeypress="return onlynum(event)"  value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectProperty.foreRate }"/>" id="foreRate" name="projectProperty.foreRate" placeholder="赎楼成数"><i>%</i>
         	</div>
        </div>
        <div class="form-group">
         	<label for="projectProperty.sellerName" class="col-sm-3 control-label red"><b>*</b>业主姓名:</label>
         	<div class="col-sm-3">
           		<input type="text" class="form-control required" value="${project.projectProperty.sellerName }" id="sellerName" name="projectProperty.sellerName" maxlength="20" placeholder="业主姓名">
         	</div>
         	<label for="projectProperty.sellerCardNo" class="col-sm-3 control-label red"><b>*</b>身份证号:</label>
         	<div class="col-sm-3">
           		<input type="text" class="form-control required" value="${project.orgCustomerPhone }" maxlength="100" id="sellerCardNo" name="projectProperty.sellerCardNo" placeholder="身份证号">
         	</div>
        </div>
        <div class="form-group">
         	<label for="projectProperty.sellerPhone" class="col-sm-3 control-label red"><b>*</b>联系电话:</label>
         	<div class="col-sm-3">
           		<input type="text" class="form-control required" value="${project.projectProperty.sellerPhone }" id="projectProperty.sellerPhone" name="projectProperty.sellerPhone" maxlength="100" placeholder="联系电话">
         	</div>
         	<label for="projectProperty.sellerAddress" class="col-sm-3 control-label red"><b>*</b>联系地址:</label>
         	<div class="col-sm-3">
           		<input type="text" class="form-control required" value="${project.projectProperty.sellerAddress }" maxlength="100" id="sellerAddress" name="projectProperty.sellerAddress" placeholder="联系地址">
         	</div>
        </div>
        <div class="form-group">
         	<label for="projectProperty.buyerName" class="col-sm-3 control-label">买方姓名:</label>
         	<div class="col-sm-3">
           		<input type="text" class="form-control" value="${project.projectProperty.buyerName }" id="buyerName" name="projectProperty.buyerName" maxlength="20" placeholder="买方姓名">
         	</div>
         	<label for="projectProperty.buyerCardNo" class="col-sm-3 control-label">身份证号:</label>
         	<div class="col-sm-3">
           		<input type="text" class="form-control" value="${project.projectProperty.buyerCardNo }" maxlength="100" id="buyerCardNo" name="projectProperty.buyerCardNo" placeholder="身份证号">
         	</div>
        </div>
        <div class="form-group">
         	<label for="projectProperty.buyerPhone" class="col-sm-3 control-label">联系电话:</label>
         	<div class="col-sm-3">
           		<input type="text" class="form-control" value="${project.projectProperty.buyerPhone }" id="buyerPhone" name="projectProperty.buyerPhone" maxlength="100" placeholder="联系电话">
         	</div>
         <label for="projectProperty.buyerAddress" class="col-sm-3 control-label">地址:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control" value="${project.projectProperty.buyerAddress }" maxlength="100" id="buyerAddress" name="projectProperty.buyerAddress" placeholder="地址">
         	</div>
        </div>
        </div>
      </div>
     </div>
    </div>
   </div>
      
 <div class="row"  id="foreclosureRow">
    <div class="ibox float-e-margins">
		<div class="ibox-title">
			<h5>附加信息--赎楼信息</h5>
			<div class="ibox-tools">
			<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> 
			</div>
		</div>
	<div class="ibox-content ibox-line">
     <div class="row">
     <div class="form-group old_loan"> 
     	<c:if test="${project.isReject != 3 }">
         <input type="button" class="btn btn-outline btn-primary left_bj" value="新增" name="commit" onclick="openAddOriginalLoanModal()"> 
       	</c:if>
       </div>
     <table class="table old_loan" id="originalLoanInfoList">
	    <thead>
	     <tr>
	      <th width="5%">序号</th>
	      <th width="10%">原贷款类型</th>
	      <th width="10%">原贷款银行</th>
	      <th width="12%">原贷款金额(元)</th>
	      <th width="12%">原贷款欠款金额(元)</th>
	      <th width="10%">原贷款结束时间</th>
	      <th width="8%">关联物业</th>
	      <th width="9%">供楼账号</th>
	      <th width="6%">原贷款联系人</th>
	      <th width="12%">原贷款联系电话</th>
	      <th width="6%">操作</th>
	     </tr>
	    </thead>
	    <tbody>
	    <c:forEach items="${project.originalLoanList }" var="originalLoanInfo" varStatus="status">
	     <tr>
	      <td><input type="checkbox" value='${originalLoanInfo.originalLoanId}'></td>
	      <td>${originalLoanInfo.loanTypeStr }</td>
	      <td>${originalLoanInfo.oldLoanBankStr }</td>
	      <td><fmt:formatNumber pattern="#,#00.00#" value="${originalLoanInfo.oldLoanMoney }"/></td>
	      <td><fmt:formatNumber pattern="#,#00.00#" value="${originalLoanInfo.oldOwedAmount }"/></td>
	      <td>${originalLoanInfo.oldLoanTime }</td>
	      <td>${originalLoanInfo.estateName }</td>
	      <td>${originalLoanInfo.oldLoanAccount }</td>
	      <td>${originalLoanInfo.oldLoanPerson }</td>
	      <td>${originalLoanInfo.oldLoanPhone }</td>
	      <td>
	      <c:if test="${editType != 3 && project.isReject != 3}">
	      	<a href="#" onclick="openEditOriginalLoanModal(this,'${originalLoanInfo.loanType}','${originalLoanInfo.oldLoanBank}','${originalLoanInfo.estateId }')">编辑</a>|
	      	<a href="#" onclick="delOriginalLoanInfo(${originalLoanInfo.originalLoanId})">删除</a></td>
	      </c:if>
	     </tr>
	     </c:forEach>
	    </tbody>
	   </table>
     <div class="col-sm-10">

        <div class="form-group old_loan">
        	<label for="projectForeclosure.oldLoanMoney" class="col-sm-3 control-label red"><b>*</b>原贷款金额:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control currency isFloatGteMoney" readonly="readonly" onblur="initformat(this)" onkeypress="return onlynum(event)" value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectForeclosure.oldLoanMoney }"/>" id="oldLoanMoney1" name="projectForeclosure.oldLoanMoney" placeholder="原贷款金额">
         	</div>
         	<label for="projectForeclosure.oldOwedAmount" class="col-sm-3 control-label red"><b>*</b>原贷款欠款金额:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control currency isFloatGteMoney" readonly="readonly" onblur="initformat(this)" onkeypress="return onlynum(event)"  value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectForeclosure.oldOwedAmount }"/>" id="oldOwedAmount1" name="projectForeclosure.oldOwedAmount" placeholder="原贷款欠款金额">
         	</div>
        </div>
        
        <div class="form-group">
         	<label for="projectForeclosure.newLoanBank" class="col-sm-3 control-label red"><b>*</b>新贷款银行:</label>
         	<div class="col-sm-3">
         		<select class="form-control selectNone" name="projectForeclosure.newLoanBank" id="newLoanBank">
	         		<option value="-1">--请选择--</option>
					<c:forEach items="${bankList }" var="bank" varStatus="status">
						<option value="${bank.pid}" <c:if test="${bank.pid == project.projectForeclosure.newLoanBank}">selected</c:if>  >${bank.bankName}</option>
					</c:forEach>
			 	</select>
         	</div>
         	<label for="projectForeclosure.newLoanMoney" class="col-sm-3 control-label red"><b>*</b>新贷款金额:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control currency required isFloatGteMoney" onblur="initformat(this)" onkeypress="return onlynum(event)" value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectForeclosure.newLoanMoney }"/>" id="newLoanMoney" name="projectForeclosure.newLoanMoney" placeholder="新贷款金额">
         	</div>
        </div>
        
        <div class="form-group">
         	<label for="projectForeclosure.newLoanPerson" class="col-sm-3 control-label">银行联系人:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control" value="${project.projectForeclosure.newLoanPerson }" id="newLoanPerson" name="projectForeclosure.newLoanPerson" maxlength="20" placeholder="银行联系人">
         	</div>
         	<label for="projectForeclosure.newLoanPhone" class="col-sm-3 control-label">联系电话:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control isMobile" value="${project.projectForeclosure.newLoanPhone }" maxlength="20" id="newLoanPhone" name="projectForeclosure.newLoanPhone" placeholder="联系电话">
         	</div>
        </div>
        
        <div class="form-group">
        	<label for="projectForeclosure.newReceBank" class="col-sm-3 control-label red"><b>*</b>回款账号开户行:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control required" value="${project.projectForeclosure.newReceBank }" name="projectForeclosure.newReceBank" maxlength="20" placeholder="回款账号开户行">
         	</div>
         	<label for="projectForeclosure.paymentName" class="col-sm-3 control-label red"><b>*</b>回款账号户名:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control required" value="${project.projectForeclosure.paymentName }" maxlength="20" name="projectForeclosure.paymentName" placeholder="回款账号户名">
         	</div>
        </div>
        
         <div class="form-group">
         	<label for="projectForeclosure.paymentAccount" class="col-sm-3 control-label red"><b>*</b>回款账号:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control required" value="${project.projectForeclosure.paymentAccount }" maxlength="50" name="projectForeclosure.paymentAccount" placeholder="回款账号">
         	</div>
        	<label for="projectForeclosure.idCardNumber" class="col-sm-3 control-label red">回款人证件号:</label>
         	<div class="col-sm-3">
          		<input type="text" id="idCardNumber" class="form-control" value="${project.projectForeclosure.idCardNumber }" name="projectForeclosure.idCardNumber" maxlength="20" placeholder="回款人身份证">
         	</div>
        </div>
        
        <div class="form-group">
        	<label for="projectForeclosure.downPayment" class="col-sm-3 control-label">首付款:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control currency required isFloatGteMoney"  onblur="initformat(this)" onkeypress="return onlynum(event)" value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectForeclosure.downPayment}"/>" id="downPayment" name="projectForeclosure.downPayment" placeholder="首付款">
         	</div>
         	<label for="projectForeclosure.paymentType" class="col-sm-3 control-label red"><b>*</b>贷款方式:</label>
         	<div class="col-sm-3">
           		<select class="form-control selectNone" name="projectForeclosure.paymentType" id="paymentType">
	           		<option value="-1">--请选择--</option>
	           		<option value="5" <c:if test="${project.projectForeclosure.paymentType==5 }">selected </c:if>>经营贷</option>
					<option value="6" <c:if test="${project.projectForeclosure.paymentType==6 }">selected </c:if>>消费贷</option>
					<option value="1" <c:if test="${project.projectForeclosure.paymentType==1 }">selected </c:if>>按揭</option>
					<option value="2" <c:if test="${project.projectForeclosure.paymentType==2 }">selected </c:if>>组合贷</option>
					<option value="3" <c:if test="${project.projectForeclosure.paymentType==3 }">selected </c:if>>公积金贷</option>
					<option value="4" <c:if test="${project.projectForeclosure.paymentType==4 }">selected </c:if>>一次性付款</option>
          		</select>
         	</div>
        </div>
        
        <div class="form-group">
        	<label for="projectForeclosure.foreAccount" class="col-sm-3 control-label red"><b>*</b>出款账号:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control required" onkeypress="return onlynum(event)" value="${project.projectForeclosure.foreAccount }" maxlength="20" id="foreAccount" name="projectForeclosure.foreAccount" placeholder="出款账号">
         	</div>
         	<label for="projectForeclosure.accumulationFundBank" class="col-sm-3 control-label">公积金银行:</label>
         	<div class="col-sm-3">
         		<select class="form-control" name="projectForeclosure.accumulationFundBank" id="accumulationFundBank">
	         		<option value="-1">--请选择--</option>
					<c:forEach items="${bankList }" var="bank" varStatus="status">
						<option value="${bank.pid}" <c:if test="${bank.pid == project.projectForeclosure.accumulationFundBank}">selected</c:if>  >${bank.bankName}</option>
					</c:forEach>
			 	</select>
         	</div>
         	
        </div>
        
        <div class="form-group">
        	<label for="projectForeclosure.accumulationFundMoney" class="col-sm-3 control-label">公积金贷款金额:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control currency isFloatGteMoney" onblur="initformat(this)" onkeypress="return onlynum(event)"  value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectForeclosure.accumulationFundMoney }"/>" id="accumulationFundMoney" name="projectForeclosure.accumulationFundMoney" placeholder="公积金贷款金额">
         	</div>
         	<label for="projectForeclosure.superviseDepartment" class="col-sm-3 control-label red"><b>*</b>资金监管银行:</label>
         	<div class="col-sm-3">
	         	<select class="form-control" name="projectForeclosure.superviseDepartment" id="superviseDepartment" >
		         	<option value="-1">--请选择--</option>
					<c:forEach items="${bankList }" var="bank" varStatus="status">
						<option value="${bank.pid}" <c:if test="${bank.pid == project.projectForeclosure.superviseDepartment}">selected</c:if>  >${bank.bankName}</option>
					</c:forEach>
				 </select>
	         </div>
        </div>
        
        <div class="form-group">
        	 <label for="projectForeclosure.fundsMoney" class="col-sm-3 control-label red"><b>*</b>资金监管金额:</label>
	         <div class="col-sm-3">
	          	<input type="text" class="form-control currency isFloatGteMoney" onblur="initformat(this)" onkeypress="return onlynum(event)"  value="<fmt:formatNumber pattern="#,#00.00#" value="${project.projectForeclosure.fundsMoney}"/>" id="fundsMoney" name="projectForeclosure.fundsMoney" placeholder="非交易类型不用填！">
	         </div>
         	<label for="projectForeclosure.superviseAccount" class="col-sm-3 control-label red"><b>*</b>资金监管收款账号:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control" onkeypress="return onlynum(event)" value="${project.projectForeclosure.superviseAccount }" id="superviseAccount" name="projectForeclosure.superviseAccount" maxlength="20" placeholder="非交易类型不用填！">
         	</div>
        </div>
        
        <div class="form-group">
        	<label for="projectForeclosure.supersionReceBank" class="col-sm-3 control-label red"><b>*</b>监管账号开户行:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control" value="${project.projectForeclosure.supersionReceBank }" id="supersionReceBank" name="projectForeclosure.supersionReceBank" maxlength="30" placeholder="非交易类型不用填！">
         	</div>
        	<label for="projectForeclosure.supersionReceName" class="col-sm-3 control-label red"><b>*</b>资金监管收款户名:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control" value="${project.projectForeclosure.supersionReceName }" id="supersionReceName" name="projectForeclosure.supersionReceName" maxlength="30" placeholder="非交易类型不用填！">
         	</div>
        </div>
        
        <div class="form-group">
        	<label for="projectForeclosure.notarizationDate" class="col-sm-3 control-label">委托公证日期:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control input-sm laydate-icon layer-date" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"  value="${project.projectForeclosure.notarizationDate }" id="notarizationDate" name="projectForeclosure.notarizationDate" placeholder="委托公证日期">
         	</div>
         	<label for="projectForeclosure.signDate" class="col-sm-3 control-label">签署合同日期:</label>
         	<div class="col-sm-3">
          		<input type="text" class="form-control input-sm laydate-icon layer-date" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"  value="${project.projectForeclosure.signDate }" id="signDate" name="projectForeclosure.signDate" placeholder="签署合同日期">
         	</div>
        </div>
      </div>
    </div>
  </div>
 </div>
</div>
      <c:if test="${editType != 3 && project.isReject != 3}">
		<div class="form-group forum-info" style="padding-bottom: 10px;"> 
		<input type="button" class="btn btn-w-m btn-white cz_an" style="margin: 0 5px 0 0;" value="保存"  onclick="saveProjectInfo()" name="commit"> 
		<input type="button" class="btn btn-primary  an_bk" onclick="subProjectInfo()" value="提交申请" name="commit">
		</div>
      </c:if>
      <c:if test="${editType != 3 && project.isReject == 3}">
			<div class="form-group forum-info" style="padding-bottom: 10px;"> 
				<input type="button" class="btn btn-primary  an_bk" onclick="saveReject()" value="提交申请" name="commit">
			</div>
      	</c:if>
       </form>
   <div class="row">   
   <div class="ibox float-e-margins">
		<div class="ibox-title">
			<h5 class="attachment">附件</h5>
			<div class="ibox-tools">
			<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> 
			</div>
		</div>
	<div class="ibox-content">
     <div class="row">
	   <div class="form-group"> 
         <input type="button" class="btn btn-outline btn-primary left_bj" value="上传" name="commit" onclick="openUploadFileModal()"> 
       </div>
	   <table class="table" id="dataInfoList">
	    <thead>
	     <tr>
	      <th>序号</th>
	      <th>附件类型</th>
	      <th>附件名称</th>
	      <th>大小</th>
	      <th>上传时间</th>
	      <th>操作</th>
	     </tr>
	    </thead>
	    <tbody>
	    <c:forEach items="${dataInfoList }" var="dataInfo" varStatus="status">
	     <tr>
	      <td>${ status.index + 1}</td>
	      <td>${dataInfo.filePropertyName }</td>
	      <td>${dataInfo.fileName }</td>
	      <c:if test="${dataInfo.dataId <= 0 }">
	      	<td>  </td>
	      </c:if>
	      <c:if test="${dataInfo.dataId > 0 }">
	      <td><fmt:formatNumber type="number" value="${dataInfo.fileSize/1024 }" maxFractionDigits="0"/>  KB</td>
	      </c:if>
	      <td>${dataInfo.uploadDttm }</td>
	      <td>
	      <c:if test="${dataInfo.dataId > 0 }">
	      <a onclick="downLoadFileByPath('${dataInfo.fileUrl}','${dataInfo.fileName }')">下载 </a>
	      </c:if>
	      <c:if test="${fn:toLowerCase(dataInfo.fileType)=='bmp'||fn:toLowerCase(dataInfo.fileType)=='jpeg'||fn:toLowerCase(dataInfo.fileType)=='jpg'||fn:toLowerCase(dataInfo.fileType)=='png'||fn:toLowerCase(dataInfo.fileType)=='gif'}">
	      	 | <a onclick="lookUpFileByPath('${dataInfo.fileUrl}','${dataInfo.fileName }')">预览</a>
	      </c:if>
	      <c:if test="${(editType == 1 || editType == 2)&&(dataInfo.dataId > 0)}">
	      	 | <a href="#" onclick="delDataInfo(${dataInfo.dataId})">删除</a></td>
	      </c:if>
	      <c:if test="${(editType == 1 || editType == 2)&&(dataInfo.dataId <= 0)}">
	      	<a href="#" onclick="openUploadFile(${dataInfo.fileProperty})">上传</a></td>
	      </c:if>
	     </tr>
	     </c:forEach>
	    </tbody>
	   </table>
	   <!-- 文件上传开始 -->
	   <div class="modal fade" id="fileUploadModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="fileUploadModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		   <div class="modal-content animated bounceInRight">
		    <div class="modal-header">
		     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		     <h4 class="modal-title" id="fileUploadModalLabel">文件上传</h4>
		    </div>
		    <form id="fileUploadForm" name="fileUploadForm" method="post">
		     <div class="modal-body">
		     	<label for="fileProperty" class="col-sm-2 control-label red"><b>*</b>文件类型:</label>
	         	<div class="col-sm-6">
	         		<select class="form-control selectNone" name="fileProperty" id="fileProperty">
		         		<option value="-1">--请选择--</option>
						<c:forEach items="${fileTypeList }" var="property" varStatus="status">
							<option value="${property.pid}">${property.lookupDesc}</option>
						</c:forEach>
				 	</select>
	         	</div>
		     </div>
		     <div class="modal-body">
		      <input type="hidden" id="isUpdateFile" name="isUpdateFile" value="0" />
		      <div class="uploadmutilFile" style="margin-top: 10px;">
		       <input type="file" name="uploadify" id="uploadify" />
		      </div>
		      <%--用来作为文件队列区域--%>
		      <div id="fileQueue" style="max-height: 200; overflow: scroll; right: 50px; bottom: 100px; z-index: 999"></div>
		     </div>
		     <div class="modal-footer">
		      <a id="uploadHandleDynamicFileButton" onclick="uploadFile()" class="btn btn-primary an_bk" >开始上传</a> 
		      <a data-dismiss="modal" class="btn btn-w-m btn-white cz_an">取消上传</a>
		     </div>
		    </form>
		   </div>
		  </div>
		 </div>
		 <!-- 文件上传结束 -->
		 <!-- 物业信息弹出开始 -->
		 <div class="modal fade" id="houseInfoModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="houseInfoModalLabel" aria-hidden="true">
		  <div class="modal-dialog" style="width: 680px;">
		   <div class="modal-content animated bounceInRight">
		    <div class="modal-header">
		     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		     <h4 class="modal-title" id="houseInfoModalLabel">物业信息</h4>
		    </div>
		    <form id="houseInfoForm" name="houseInfoForm" method="post">
		     <div class="modal-body" style="height: auto; overflow: hidden;">
		     	<div class="form-group" style="width:100%; height: auto; overflow: hidden;">
		     		<input type="hidden" name="projectId" id="projectId">
		     		<input type="hidden" name="houseId" id="houseId" value="0">
		         	<label for="houseName" class="col-sm-2 control-label red"><b>*</b>物业名称:</label>
		         	<div class="col-sm-4">
		          		<input type="text" class="form-control required" id="houseName" name="houseName" maxlength="50" placeholder="物业名称">
		         	</div>
		         	<label for="costMoney" class="col-sm-2 control-label red"><b>*</b>登记价:</label>
		         	<div class="col-sm-4">
		          		<input type="text" id="costMoney" class="form-control currency required isFloatGteMoney" onblur="initformat(this)" onkeypress="return onlynum(event)" name="costMoney" placeholder="原价">
		         	</div>
		        </div>
		        <div class="form-group" style="width:100%;height: auto; overflow: hidden;">
		        	<label for="area" class="col-sm-2 control-label red"><b>*</b>面积:</label>
		         	<div class="col-sm-4">
		          		<input type="text" id="area" class="form-control currency required isFloatGtMoney" onblur="initformat(this)" onkeypress="return onlynum(event)" name="area" placeholder="面积"><i>㎡</i>
		         	</div>
		         	<label for="housePropertyCard" class="col-sm-2 control-label red"><b>*</b>房产证号:</label>
		         	<div class="col-sm-4">
		          		<input type="text" id="housePropertyCard" class="form-control required" maxlength="50" name="housePropertyCard" placeholder="房产证号">
		         	</div>
		       </div>
		       <div class="form-group" style="width:100%;height: auto; overflow: hidden;">
		         	<label for="purpose" class="col-sm-2 control-label red"><b>*</b>用途:</label>
		         	<div class="col-sm-4">
		           		<input type="text" id="purpose" class="form-control required" name="purpose" maxlength="50" placeholder="用途">
		         	</div>
		         	<label for="evaluationPrice" class="col-sm-2 control-label red"><b>*</b>评估价:</label>
		         	<div class="col-sm-4">
		           		<input type="text" id="evaluationPrice" class="form-control currency required isFloatGteMoney" onblur="initformat(this)" onkeypress="return onlynum(event)" name="evaluationPrice" placeholder="评估价">
		         	</div>
		        </div>
		        <div class="form-group" style="width:100%;height: auto; overflow: hidden;">
		        	<label for="evaluationNet" class="col-sm-2 control-label">评估净值:</label>
		         	<div class="col-sm-4">
		           		<input type="text" id="evaluationNet" class="form-control currency isFloatGteMoney" onblur="initformat(this)" onkeypress="return onlynum(event)" name="evaluationNet" placeholder="评估净值">
		         	</div>
		         	<label for="tranasctionMoney" class="col-sm-2 control-label red"><b>*</b>成交价:</label>
		         	<div class="col-sm-4">
		          	 	<input type="text" id="tranasctionMoney" class="form-control currency required isFloatGteMoney" onblur="initformat(this)" onkeypress="return onlynum(event)" name="tranasctionMoney" placeholder="成交价">
		         	</div>
		        </div>
		        <div class="form-group" style="width:100%;height: auto; overflow: hidden;">
		        <label for="houseProvinceCode" class="col-sm-2 control-label red"><b>*</b>物业所在地:</label>
		            <div id="area-cascade">
		                <div class="col-sm-3">
		                    <select class="form-control selectNone" name="houseProvinceCode" id="houseProvinceCode">
		                        <option>--请选择--</option>
		                    </select>
		                </div>
		                <div class="col-sm-3">
		                    <select class="form-control selectNone" name="houseCityCode" id="houseCityCode">
		                        <option>--请选择--</option>
		                    </select>
		                </div>
		                <div class="col-sm-3">
		                    <select class="form-control selectNone" name="houseDistrictCode" id="houseDistrictCode">
		                        <option>--请选择--</option>
		                    </select>
		                </div>
		            </div>
		        </div>
		     </div>
		     <div class="modal-footer">
		      <a id="uploadHandleDynamicFileButton" onclick="saveHouseInfo()" class="btn btn-primary an_bk" >保存</a> 
		      <a data-dismiss="modal" class="btn btn-w-m btn-white cz_an">取消</a>
		     </div>
		    </form>
		   </div>
		  </div>
		 </div>
		 <!-- 物业信息弹出结束 -->
		 
		 <!-- 原贷款信息弹出开始 -->
		 <div class="modal fade" id="originalLoanInfoModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="originalLoanInfoModalLabel" aria-hidden="true">
		  <div class="modal-dialog"  style="width: 880px;">
		   <div class="modal-content animated bounceInRight">
		    <div class="modal-header">
		     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		     <h4 class="modal-title" id="originalLoanInfoModalLabel">原贷款信息</h4>
		    </div>
		    <form id="originalLoanInfoForm" name="originalLoanInfoForm" method="post">
		     <div class="modal-body" style="height: auto; overflow: hidden;">
		     	<div class="form-group" style="width:100%; height: auto; overflow: hidden;">
		     		<input type="hidden" name="projectId" id="projectId1">
		     		<input type="hidden" name="originalLoanId" id="originalLoanId" value="0">
		         	<label for="loanType" class="col-sm-2 control-label red"><b>*</b>原贷款类型:</label>
		         	<div class="col-sm-4">
		          		<select class="form-control required" name="loanType" id="loanType">
						</select>
		         	</div>
		         	<label for="oldLoanBank" class="col-sm-2 control-label red"><b>*</b>原贷款银行:</label>
		         	<div class="col-sm-4">
		          		<select class="form-control selectNone" name="oldLoanBank" id="oldLoanBank">
			         		<option value="-1">--请选择--</option>
							<c:forEach items="${bankList }" var="bank" varStatus="status">
								<option value="${bank.pid}" >${bank.bankName}</option>
							</c:forEach>
					 	</select>
		         	</div>
		        </div>
		         <div class="form-group" style="width:100%;height: auto; overflow: hidden;">
		        	<label for="estateId" class="col-sm-2 control-label red"><b>*</b>关联物业:</label>
		         	<div class="col-sm-4">
		          		<select class="form-control selectNone" name="estateId" id="estateId">
						</select>
		         	</div>
		         	<label for="oldLoanAccount" class="col-sm-2 control-label">供楼账号:</label>
		         	<div class="col-sm-4">
		          		<input type="text" class="form-control" onkeypress="return onlynum(event)" id="oldLoanAccount" name="oldLoanAccount" maxlength="50" placeholder="供楼账号">
		         	</div>
		       </div>
		        <div class="form-group" style="width:100%;height: auto; overflow: hidden;">
		        	<label for="oldLoanMoney" class="col-sm-2 control-label red"><b>*</b>原贷款借款金额:</label>
		         	<div class="col-sm-4">
		          		<input type="text" id="oldLoanMoney" name="oldLoanMoney" class="form-control currency required" onblur="initformat(this)" onkeypress="return onlynum(event)" placeholder="原贷款金额">
		         	</div>
		         	<label for="oldOwedAmount" class="col-sm-2 control-label red"><b>*</b>原贷款欠款金额:</label>
		         	<div class="col-sm-4">
		          		<input type="text" class="form-control currency required" onblur="initformat(this)" onkeypress="return onlynum(event)" id="oldOwedAmount" name="oldOwedAmount" placeholder="原贷款欠款金额">
		         	</div>
		       </div>
		       
		       <div class="form-group" style="width:100%;height: auto; overflow: hidden;">
		         	<label for="oldLoanTime" class="col-sm-2 control-label red"><b>*</b>原贷款结束时间:</label>
		         	<div class="col-sm-4">
		           		<input type="text" class="form-control input-sm laydate-icon layer-date required" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"  id="oldLoanTime" name="oldLoanTime" placeholder="原贷款结束时间">
		         	</div>
		         	<label for="oldLoanPerson" class="col-sm-2 control-label">原贷款联系人:</label>
		         	<div class="col-sm-4">
						<input type="text" class="form-control" id="oldLoanPerson" name="oldLoanPerson" maxlength="20" placeholder="银行联系人">
		         	</div>
		        </div>
		        <div class="form-group" style="width:100%;height: auto; overflow: hidden;">
		         	<label for="oldLoanPhone" class="col-sm-2 control-label">原贷款联系电话:</label>
		         	<div class="col-sm-4">
						<input type="text" class="form-control isMobile" maxlength="20" id="oldLoanPhone" name="oldLoanPhone" placeholder="联系电话">
		         	</div>
		        </div>
		     </div>
		     <div class="modal-footer">
		      <a id="saveOriginalLoanInfo" onclick="saveOriginalLoanInfo()" class="btn btn-primary an_bk" >保存</a> 
		      <a data-dismiss="modal" class="btn btn-w-m btn-white cz_an">取消</a>
		     </div>
		    </form>
		   </div>
		  </div>
		 </div>
		 <!-- 原贷款信息弹出结束 -->
		 
		 <!-- 选择借款人弹出开始 -->
		 <div class="modal fade" id="personInfoModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="personInfoModalLabel" aria-hidden="true">
		  <div class="modal-dialog"  style="width: 900px;">
		   <div class="modal-content animated bounceInRight">
		    <div class="modal-header">
		     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		     <h4 class="modal-title" id="personInfoModalLabel">借款人</h4>
		    </div>
	    	<div class="modal-body">
                <div class="row">
	              <div class="col-md-12" style="padding: 0">
			        <form  method="post" id="searchFrom" name="searchFrom" class="form-horizontal"> 
			         <input type="hidden" name="cusAcct.orgId" value="${loginUser.orgId}"/>
			         <!-- 查询条件 -->
			         <div class="form-group">
			            <label class="col-md-1 control-label" for="cusName"  style="padding-right: 0">姓名:</label>
			            <div class="col-md-3">
			             <input type="text" class="form-control input-sm" name="cusName" id="cusName" placeholder="姓名">
			            </div>
			            <label class="col-md-1 control-label" for="sexId"  style="padding-right: 0">性别:</label>
			            <div class="col-md-3">
			             <select class="form-control input-sm" name="sexId" id="sex"></select>
			            </div>
			            <label class="col-md-1 control-label" for="certNumber"  style="padding-right: 0">证件号码:</label>
			            <div class="col-md-3">
			             <input type="text" class="form-control input-sm" name="certNumber" id="certNumber" placeholder="证件号码">
			            </div>
			         </div>
			         <div class="modal-footer" style="border-top: 0px; padding-top:0; margin-bottom: 10px">
			         	<!-- 操作按钮 -->
			             <button class="btn btn-primary an_bk" type="button" onclick="searchList('#person_table_list')">
			              <i class="fa fa-check"></i> <span class="bold">查询</span>
			             </button>
			             <button class="btn btn-w-m btn-white cz_an" type="reset">
			              <i class="fa fa-warning"></i> <span class="bold">重置</span>
			             </button>
			         </div>
			        </form>
	                </div>
			        <div class="jqGrid_wrapper">
			         <table id="person_table_list"></table>
			         <div id="person_pager_list"></div>
			        </div>
                </div>
                </div>
	            <div class="modal-footer">
	                 <button type="button" class="btn btn-info" onclick="selectPerson()">选择</button>
	                 <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            </div>
		   </div>
		  </div>
		 </div>
		 <!-- 选择借款人弹出结束 -->
		 
		 <!-- 共同借款人弹出开始 -->
		 <div class="modal fade" id="publicInfoModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="publicInfoModalLabel" aria-hidden="true">
		  <div class="modal-dialog"  style="width: 880px;">
		   <div class="modal-content animated bounceInRight">
		    <div class="modal-header">
		     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		     <h4 class="modal-title" id="publicInfoModalLabel">共同借款人</h4>
		    </div>
		    <div class="modal-body">
	              <div class="row">
		              <div class="col-md-12" style="padding: 0">
				        <div class="jqGrid_wrapper">
				         <table id="public_table_list"></table>
				         <div id="public_pager_list"></div>
				        </div>
				      </div>
			     </div>
		    </div>
		    <div class="modal-footer">
		        <button type="button" class="btn btn-info" onclick="selectPublic()">选择</button>
	            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        </div>
		   </div>
		  </div>
		 </div>
		 <!-- 共同借款人弹出结束 -->
		 
	  </div>
     </div>
    </div>
   </div>
 </div>
 </div>
 <!-- 需要延迟加载的资源 -->
  <script src="${ctx }js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>