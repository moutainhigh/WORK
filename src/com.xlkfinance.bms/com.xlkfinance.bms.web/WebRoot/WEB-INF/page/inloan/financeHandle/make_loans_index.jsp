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
.table_css{ border:1px #95B8E7 solid;}
.table_css td{ padding:4px 10px; font-size:12px;border-bottom:1px #ddd solid;  border-right:1px #ddd solid;}
.table_css td:last-child{  border-right:0;}
.bb td span{ float:left; width:100px; text-align: right; padding-right: 10px;}
.bt{ background-color: #E0ECFF;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#EFF5FF,endColorstr=#E0ECFF,GradientType=0);
font-size: 12px;
font-weight: bold;
color: #0E2D5F;}
.bt td{padding:4px 10px;border-bottom:1px #95B8E7 solid;}
.srk1{ height:60px;width:70%;}
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript">
var userSource=${userSource};//当前查询用户来源：万通用户=1，小科用户=2
	//重置按钮
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset');
		$('#productId').combobox('setValue',"-1");
	}

	function formatterStatus(val, row) {
		if (val == 1) {
			return "未收费";
		} else if (val == 2) {
			return "已收费";
		} else if (val == 5) {
			return "放款申请中";
		}  else if (val == 3) {
			return "已放款";
		}  else if (val == 4) {
			return "放款未完结";
		} else {
			return "未知";
		}
	}
	
		//根据项目Id
		function getApplayStatusByProjectId(projectId,recPro){
			var value=0;
			$.ajax({
				url : BASE_PATH+"financeHandleController/getApplayStatusByProjectId.action?projectId="+projectId+"&recPro="+recPro,
				cache : true,
				type : "POST",
				async : false,
				success : function(data, status) {
					value=data
				}
			});
			return value;
		}
	   var makeLoansList = {
		    	formatOperate:function(value, row, index){
		    		var va = "";
			    	if(row.foreclosureStatus==3){
			    		va=va+"<a href='javascript:void(0)' onclick = 'openForeclosureTurnDownRemarkDialog()'> <font color='blue'>查看驳回原因</font></a>";
			    	}
		    	    return va;
				},
				// 项目名称format
				formatProjectName:function(value, row, index){
					var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
					return va;
				}
			}
	   //查看驳回备注
   function openForeclosureTurnDownRemarkDialog(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var foreclosureTurnDownRemark=row[0].foreclosureTurnDownRemark;
		    $("#show_foreclosureTurnDownRemark").val(foreclosureTurnDownRemark);
			$('#foreclosureTurnDownRemark-dialog').dialog('open').dialog('setTitle', "驳回备注--"+row[0].projectName);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能查看一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//打开放款窗口
   function openMakeLoans(chooseType){
	   var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
		   var financeHandleId=row[0].pid;//财务办理id
		   var projectId=row[0].projectId;//项目id
		   var isNeedFinancial=row[0].isNeedFinancial;//是否需要小科资金 1不需要2需要（万通推单新增字段）
		   var reCheckStatus=row[0].reCheckStatus;//查档复核状态：未复核=1，重新复核=2，同意=3，不同意=4
		   var isNeedHandle=row[0].isNeedHandle;//是否需要贷中办理：需要=1，不需要=2
		   var productNumber=row[0].productNumber;//产品编号
		   var projectName=row[0].projectName;//
		   var makeApplyFinanceIdTwo = row[0].makeApplyFinanceIdTwo;//第二次放款
		   var makeLoansApplyFinanceId = row[0].makeLoansApplyFinanceId;//第1次放款
		   var validateApproveResult;
		   var projectSource = row[0].projectSource;//业务来源
		   var projectType = row[0].projectType;//项目类型
		   var makeLoansApplyFinanceApplyStatus=row[0].makeLoansApplyFinanceApplyStatus;//申请财务受理信息的第一次放款的申请状态
		   var url ="";
		   var status=row[0].status;//收费状态：未收费=1，已收费=2，已放款=3，放款未完结=4，放款申请中=5
		   var collectFeeType=row[0].collectFeeType;//(赎楼贷)收费方式:贷前收费=1，贷后收费=2。（房抵贷）收费节点：下户前收费=1，放款前收费=2，任意节点收费=3
		   var collectFeeStatus=row[0].collectFeeStatus;//收费状态：未收费=1，已收费=2
		   var mortgageStatus=row[0].mortgageStatus;//抵押状态：已抵押=1，未抵押=2
		   //判断房抵贷不可跳转
		   if (projectType==8) {
			   $.messager.alert("操作提示", "请选择赎楼贷或提放贷", "error");
			   return;
		   }
		   //假如不是交易/非交易有赎楼提放贷点击放款按钮假如保存过则走显示审批流页面
		   var fapplyStatus =0;
		   var sapplyStatus =0;
		  if( "FJYYSLTFD" != productNumber && "JYYSLTFD" != productNumber){
			//根据项目Id查询BIZ_LOAN_APPLY_FINANCE_HANDLE
			    var applyStatus2 = getApplayStatusByProjectId(projectId,3);
			    //applyStatus2 =1 为未申请 chooseType=0为点击放款按钮 =3为第一次放款 =9为第二次放款
			    if(applyStatus2 > 1){
			    	chooseType =3;
			    }
		  	}else{
		  		//第一次放款状态
		  		fapplyStatus = getApplayStatusByProjectId(projectId,3);
		  	    //第二次放款状态
		  		sapplyStatus = getApplayStatusByProjectId(projectId,9);
		  	}
		  //=1为万通的单
		   if(projectSource != 1 && fapplyStatus !=3 && sapplyStatus !=3 ){
			   if(chooseType == 0){
				  if( "FJYYSLTFD" ==productNumber || "JYYSLTFD" ==productNumber){
					  //交易/非交易有赎楼提放贷如果第二次已经申请则过，跳转审批明细页面
					  if(fapplyStatus == 5 && (sapplyStatus == 4 || sapplyStatus == 5)){
						  
					  }else{
							//校验放款条件
							if(projectId >0){
					        		$.ajax({
					            		type: "POST",
					                    data:{"projectId":projectId,"financeHandleId":financeHandleId,"chooseType":chooseType},
					                	url : '${basePath}financeHandleController/validateApprove.action',
					            		dataType: "json",
					            		async: false,
					            	    success: function(result){
					            	    	  validateApproveResult = result.header;
					            	    	}
					            	    })
						    }
							if (!validateApproveResult.success) {
								$.messager.alert("操作提示", validateApproveResult.msg, "error");
								return;
							}
					  }
					  //这种情况只有一次放款
				  } else{
					  //放款状态
					  var applyStatusSt = getApplayStatusByProjectId(projectId,3);
					  if(applyStatusSt == 1){
						//校验放款条件
							if(projectId >0){
					        		$.ajax({
					            		type: "POST",
					                    data:{"projectId":projectId,"financeHandleId":financeHandleId,"chooseType":chooseType},
					                	url : '${basePath}financeHandleController/validateApprove.action',
					            		dataType: "json",
					            		async: false,
					            	    success: function(result){
					            	    	  validateApproveResult = result.header;
					            	    	}
					            	    })
						    }
							if (!validateApproveResult.success) {
								$.messager.alert("操作提示", validateApproveResult.msg, "error");
								return;
							}
					  }
					  
				  }
			   }
		   }
		   
		   if (userSource==1&&isNeedFinancial==2) {//如果是万通用户，则不能操作需要小科资金的放款
			   $.messager.alert("操作提示", "该笔业务是由小科放款，您不能操作", "error");
			   return;
		   }
		   if (reCheckStatus!=3&&isNeedHandle==1) {
			   $.messager.alert("操作提示", "查档复核未通过，不能放款", "error");
			   return;
		   } 
		   if (financeHandleId<=0) {
			   $.messager.alert("操作提示", "财务办理id不存在", "error");
			   return;
		   }
		    //根据项目Id查询BIZ_LOAN_APPLY_FINANCE_HANDLE申请财务受理信息9=第二次放款
		    var applyStatus = getApplayStatusByProjectId(projectId,9);
		    var applyStatus1 = getApplayStatusByProjectId(projectId,3);
		    //0意思是没有打开查看放款记录页面
			if(chooseType == 0){
		   		if (status==3||row[0].makeLoansApplyFinanceApplyStatus>=3) {
		   			//判断是不是交易/非交易有赎楼提放贷
		   			if("FJYYSLTFD"==productNumber || "JYYSLTFD"==productNumber){
		   				//假如目前在审批中则显示审批流程
		   				if(applyStatus1 == 4 || applyStatus1 == 3){
		   					url = "${basePath}financeHandleController/toMakeLoans.action?projectId="+row[0].projectId+"&chooseType="+3+"&param='refId':'"+row[0].makeLoansApplyFinanceId+"','projectName':'"+row[0].projectName+"','projectId':'"+row[0].projectId+"'";
		   				}
		   				//判断二次放款是不是通过 走有审批流你的页面
		   				else if(applyStatus >= 3){
		   				  url = "${basePath}financeHandleController/toMakeLoans.action?projectId="+row[0].projectId+"&chooseType="+chooseType+"&param='refId':'"+row[0].makeApplyFinanceIdTwo+"','projectName':'"+row[0].projectName+"','projectId':'"+row[0].projectId+"'";
		   				}else{
		   				 url = "${basePath}financeHandleController/toMakeLoans.action?projectId="+row[0].projectId+"&chooseType="+chooseType;
		   				}
		   			}else{
		   				url = "${basePath}financeHandleController/toMakeLoans.action?projectId="+row[0].projectId+"&chooseType="+chooseType+"&param='refId':'"+row[0].makeLoansApplyFinanceId+"','projectName':'"+row[0].projectName+"','projectId':'"+row[0].projectId+"'";
		   			}
				}else{
					//走非交易/交易有赎楼提放贷以外的审批通过不通过页面
					url = "${basePath}financeHandleController/toMakeLoans.action?projectId="+row[0].projectId+"&chooseType="+chooseType;
				}
			}else{
				//第一次放款记录
				if(chooseType == 3){
					applyStatus = getApplayStatusByProjectId(projectId,3);
					if(applyStatus == 1){
		   				$.messager.alert("操作提示", "第一批未提交放款审批流程，不能查看审批记录！", "error");
		   				return;
		   			}
			   		if (status==3||row[0].makeLoansApplyFinanceApplyStatus>=3) {
			   			//判断是不是交易/非交易有赎楼提放贷
			   			url = "${basePath}financeHandleController/toMakeLoans.action?projectId="+row[0].projectId+"&chooseType="+chooseType+"&param='refId':'"+row[0].makeLoansApplyFinanceId+"','projectName':'"+row[0].projectName+"','projectId':'"+row[0].projectId+"'";
					}else{
						//走非交易/交易有赎楼提放贷以外的审批通过不通过页面
						url = "${basePath}financeHandleController/toMakeLoans.action?projectId="+row[0].projectId+"&chooseType="+chooseType;
					}
				}else{
					applyStatus = getApplayStatusByProjectId(projectId,9);
					if(applyStatus == 1){
		   				$.messager.alert("操作提示", "第二批未提交审批流程，不能查看审批记录！", "error");
		   				return;
		   			}
			   		if (status==3||row[0].makeLoansApplyFinanceApplyStatus>=3) {
			   			//判断是不是交易/非交易有赎楼提放贷
			   			url = "${basePath}financeHandleController/toMakeLoans.action?projectId="+row[0].projectId+"&chooseType="+chooseType+"&param='refId':'"+row[0].makeApplyFinanceIdTwo+"','projectName':'"+row[0].projectName+"','projectId':'"+row[0].projectId+"'";
					}else{
						//走非交易/交易有赎楼提放贷以外的审批通过不通过页面
						url = "${basePath}financeHandleController/toMakeLoans.action?projectId="+row[0].projectId+"&chooseType="+chooseType;
					}
				}
			}
		   parent.openNewTab(url, "放款", true);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//打开房抵贷放款窗口
   function openDydMakeLoans(){
	   var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
		   var financeHandleId=row[0].pid;//财务办理id
		   var projectId=row[0].projectId;//项目id
		   var productNumber=row[0].productNumber;//产品编号
		   var projectName=row[0].projectName;//
		   var makeApplyFinanceIdTwo = row[0].makeApplyFinanceIdTwo;//第二次放款
		   var makeLoansApplyFinanceId = row[0].makeLoansApplyFinanceId;//第1次放款
		   var projectSource = row[0].projectSource;//业务来源
		   var projectType = row[0].projectType;//项目类型
		   var makeLoansApplyFinanceApplyStatus=row[0].makeLoansApplyFinanceApplyStatus;//申请财务受理信息的第一次放款的申请状态
		   var url ="";
		   var status=row[0].status;//收费状态：未收费=1，已收费=2，已放款=3，放款未完结=4，放款申请中=5
		   var collectFeeType=row[0].collectFeeType;//(赎楼贷)收费方式:贷前收费=1，贷后收费=2。（房抵贷）收费节点：下户前收费=1，放款前收费=2，任意节点收费=3
		   var collectFeeStatus=row[0].collectFeeStatus;//收费状态：未收费=1，已收费=2
		   var mortgageStatus=row[0].mortgageStatus;//抵押状态：已抵押=1，未抵押=2
		   //房抵贷页面跳转
		   if (projectType==8) {
			   if (collectFeeType==2&&collectFeeStatus==1) {
				   $.messager.alert("操作提示", "请先收费再放款", "error");
					return;
			   }
			   if (mortgageStatus==2) {
				   $.messager.alert("操作提示", "请先抵押再放款", "error");
					return;
			   }
			   if (status==3||makeLoansApplyFinanceApplyStatus>=3) {
		   			url = "${basePath}financeHandleController/toFddMakeLoans.action?projectId="+row[0].projectId+"&param='refId':'"+makeLoansApplyFinanceId+"','projectName':'"+projectName+"','projectId':'"+projectId+"'";
				}else{
					url="${basePath}financeHandleController/toFddMakeLoans.action?projectId="+projectId+"&makeLoansApplyFinanceId"+makeLoansApplyFinanceId;
				}
			   parent.openNewTab(url, "放款", true);
			   return;
		   }else{
			   $.messager.alert("操作提示", "请选择抵押贷", "error");
		   }
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}

	//赎楼驳回
	function openForeclosureTurnDown() {
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var productNumber=row[0].productNumber;
			if(productNumber== 'FJYWSLTFD' || productNumber== 'JYWSLTFD' ){
				$.messager.alert("操作提示", "无赎楼节点，不能驳回!", "error");
				return;
			}
			if(row[0].projectType==8){
				$.messager.alert("操作提示", "房抵贷项目不能驳回", "error");
				return;
			}
			if(row[0].projectType==4){
				$.messager.alert("操作提示", "展期项目不能驳回", "error");
				return;
			}
			if(row[0].isNeedHandle==2){
				$.messager.alert("操作提示", "此订单不需要贷中办理，无法赎楼驳回!", "error");
				return;
			}
			if(row[0].status!=3){
				$.messager.alert("操作提示", "该项目未放款，不能驳回", "error");
				return;
			}
			$('#foreclosureTurnDown-dialog').dialog('open').dialog('setTitle', "驳回--"+row[0].projectName);
			$("#foreclosureTurnDownRemark").val('');
			$("#foreclosureTurnDownHandleId").val(row[0].bizHandleId);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//提交赎楼驳回
	function subForeclosureTurnDown() {
			$("#foreclosureTurnDownForm").form('submit', {
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(result) {
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						$.messager.alert('操作提示', ret.header["msg"], 'info');
						//关闭窗口
						$("#foreclosureTurnDown-dialog").dialog('close')
		                 // 重新加载列表
		                 $("#grid").datagrid("clearChecked");
		                 $("#grid").datagrid('reload'); 
					} else {
						$.messager.alert('操作提示', ret.header["msg"], 'error');
					}
				}
			});
	}
	$(document).ready(function() {
		$(".loan_list").hide();
		   $('#grid').datagrid({  
		       rowStyler:function(index,row){    
		           if (row.isChechan==1){    
		               return 'background-color:#FFAF4C;';    
		           }    
		       } 
		   }) ;
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
	//打印
	function print(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	 if(row.length > 1){
			$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
		}else if(row.length == 0){
			$.messager.alert("操作提示","请选择数据","error");
		}else{
			if(row[0].projectType==8){
				$.messager.alert("操作提示", "房抵贷项目不能打印", "error");
				return;
			}
			var url = "${basePath}beforeLoanController/print.action?projectId="+row[0].projectId;
	 		parent.openNewTab(url,"出账单打印",true);
		}
	}
	
	//是否万通推送
	function formatNeedFinancial(val, row){
		if (val == 1) {
			return "否";
		} else if (val == 2) {
			return "是";
		}
	}
	//第几次放款
	function formatterRecPro(val,row){
		if (val == 9) {
			return "第二次放款";
		} else  {
			return "第一次放款";
		}
	}
	
	//打开放款记录弹框
	function selectPersonal(){
		var row = $('#grid').datagrid('getSelections');
		if(row.lenth == 0){
			$.messager.alert("操作提示","请选择数据","error");
			return;
		}
		var projectId = row[0].projectId;
		var url = "<%=basePath%>financeHandleController/getLoanHisByProjectId.action?projectId="+projectId;
		$('#personal_grid').datagrid('options').url = url;
		$('#personal_grid').datagrid('reload');
	  	$('#dlg_personal').dialog('open');
	}
	
	// 选取哪一款
	function savePersonal(){
		// 获取选中的行
		var row = $('#personal_grid').datagrid('getSelected');	
		// 保证必须选取客户数据
		if(row.lenth == 0){
			$.messager.alert("操作提示","请选择第几次放款!","error");
			return false;
		}
		var chooseType = row.recPro;
		$("#chooseType").numberbox('setValue',chooseType);
		$('#grid').datagrid('reload');
		openMakeLoans(chooseType);
		$('#dlg_personal').dialog('close');
	}
	
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
  <input type="hidden" name ="chooseType"  id="chooseType" value = '0' class="easyui-numberbox" />
<!--   0=放款记录   1=打印 -->
<!--   <input type="hidden" name ="printOrLook"  id="printOrLook" value = '0' class="easyui-numberbox" /> -->
<!--    <input type="hidden"  id="chooseType"> -->
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>financeHandleController/makeLoansIndexList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td align="right"  width="80" height="28">项目编号：</td>
        <td><input class="easyui-textbox" name="projectNumber" id="projectNumber" /></td>
        <td align="right"  width="80" height="28">项目名称：</td>
        <td><input class="easyui-textbox" name="projectName" id="projectName" /></td>
       <td align="right"   width="80" height="28">放款状态：</td>
       <td >
        <select class="easyui-combobox" id="status"  name="status" style="width:124px" panelHeight="auto" editable="false" >
         <option value=-1  selected="selected" >--请选择--</option>
         <option value=2 >已收费</option>
         <option value=5 >放款申请中</option>
         <option value=3 >已放款</option>
         <option value=4 >放款未完结</option>
        </select>
       </td>
       </tr>
       <tr>
        <td align="right" height="28">物业名称：</td>
        <td><input class="easyui-textbox" name="houseName" /></td>
        <td align="right" height="28">买方姓名：</td>
        <td><input class="easyui-textbox" name="buyerName" /></td>
        <td align="right" height="28">卖方姓名：</td>
        <td><input class="easyui-textbox" name="sellerName" /></td>
       </tr>
        <tr>
         <td width="100" align="right"  height="28">产品名称：</td>
		  <td colsapn="2">
			 <input class="easyui-combobox" editable="false" style="width:129px;" name="productId" id="productId"/>						
		  </td>
		<td align="right"   width="80" height="28">收费状态：</td>
		<td >
        <select class="easyui-combobox" name="collectFeeStatus" style="width:124px" panelHeight="auto" editable="false" >
         <option value=-1  selected="selected" >--请选择--</option>
         <option value=1 >未收费</option>
         <option value=2 >已收费</option>
        </select>
       </td>
       </tr>
       <tr>
        <td colspan="6"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>
      </table>
     </div>
    </form>

    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
      <shiro:hasAnyRoles name="FINANCE_MAKE_LOANS,ALL_BUSINESS">
      <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openMakeLoans(0)">放款</a>
      </shiro:hasAnyRoles>
      <shiro:hasAnyRoles name="FINANCE_FDD_MAKE_LOANS,ALL_BUSINESS">
      <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openDydMakeLoans()">放款(房抵贷)</a>
      </shiro:hasAnyRoles>
      <shiro:hasAnyRoles name="FORECLOSURE_TURN_DOWN,ALL_BUSINESS">
      <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openForeclosureTurnDown()">赎楼驳回</a>
      </shiro:hasAnyRoles>
      <shiro:hasAnyRoles name="MAKE_LOANS_PRINT,ALL_BUSINESS">
   	  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="print()">出账单打印</a>
      </shiro:hasAnyRoles>
       <shiro:hasAnyRoles name="FINANCE_MAKE_LOANS,ALL_BUSINESS">
   	  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="selectPersonal()">查看放款记录</a>
      </shiro:hasAnyRoles>
    </div>

   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="财务放款列表" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>financeHandleController/makeLoansIndexList.action',
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
    
    <thead data-options="frozen:true">
		<tr>
			<th data-options="field:'pid',checkbox:true"></th>
       		<th data-options="field:'projectId',hidden:true">项目id</th>
       		<th data-options="field:'cz',formatter:makeLoansList.formatOperate" align="center" halign="center"  >操作</th>
       		<th data-options="field:'projectName',formatter:makeLoansList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		</tr>
	 </thead>
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'productName',sortable:true" align="center" halign="center">产品名称</th>
       <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th>
       <th data-options="field:'houseName',sortable:true" align="center" halign="center">物业名称</th>
       <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方姓名</th>
       <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方姓名</th>
       <th data-options="field:'loanMoney',sortable:true,formatter:formatMoney" align="center" halign="center">借款金额</th>
       <th data-options="field:'projectStatus',sortable:true,formatter:formatterProjectStatus" align="center" halign="center">审批状态</th>
       <th data-options="field:'isChechan',sortable:true,formatter:formatterIsChechan" align="center" halign="center">是否撤单</th>
       <th data-options="field:'realLoan',sortable:true,formatter:formatMoney" align="center" halign="center">放款金额</th>
       <th data-options="field:'status',sortable:true,formatter:formatterStatus" align="center" halign="center">放款状态</th>
       <th data-options="field:'collectFeeStatus',sortable:true,formatter:formatterCollectFeeStatus" align="center" halign="center">收费状态</th>
       <c:if test="${userSource==2}">
       <th data-options="field:'orgLoanDate',sortable:true,formatter:convertDate" align="center" halign="center">平台放款时间</th>
       <th data-options="field:'isNeedFinancial',formatter:formatNeedFinancial,sortable:true" align="center" halign="center">是否万通推送</th>
       </c:if>
       <th data-options="field:'checkLitigationApprovalStatus',sortable:true,formatter:formatterCheckLitigationApprovalStatus" align="center" halign="center">查诉讼审批状态</th>
       <th data-options="field:'checkDocumentApprovalStatus',sortable:true,formatter:formatterCheckDocumentApprovalStatus" align="center" halign="center">查档审批状态</th>
       <th data-options="field:'reCheckStatus',sortable:true,formatter:formatterReCheckStatus" align="center" halign="center">查档复核状态</th>
       <th data-options="field:'foreclosureStatus',sortable:true,formatter:formatterBizForeclosureStatus" align="center" halign="center">赎楼状态</th>
       <th data-options="field:'mortgageStatus',sortable:true,formatter:formatterInterviewMortgageStatus" align="center" halign="center">面签抵押状态</th>
       <th data-options="field:'houseClerkId',hidden:true">赎楼员id</th>
       <th data-options="field:'houseClerkName',sortable:true" align="center" halign="center">赎楼员</th>
       <th data-options="field:'createrDate',sortable:true,formatter:convertDate" align="center" halign="center">创建时间</th>
      </tr>
     </thead>
    </table>
   </div>
   <!-- 驳回 -->
   <div id="foreclosureTurnDown-dialog" class="easyui-dialog" buttons="#foreclosureTurnDownDiv" style="width: 353px; height: 150px; padding: 10px 20px;"
    closed="true"
   >
    <form id="foreclosureTurnDownForm" name="foreclosureTurnDownForm" method="post" action="${basePath}bizHandleController/foreclosureTurnDown.action">
     <table>
      <tr>
       <td>原因:</td>
       <td><textarea rows="2" id="foreclosureTurnDownRemark" name="foreclosureTurnDownRemark" maxlength="500" style="width: 250px;"></textarea></td>
      </tr>
     </table>
     <input type="hidden" name="pid" id="foreclosureTurnDownHandleId">
    </form>
   </div>
   <div id="foreclosureTurnDownDiv">
    <a id="saveFile" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subForeclosureTurnDown()">提交</a> <a
     href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#foreclosureTurnDown-dialog').dialog('close')"
    >取消</a>
   </div>
    <div id="foreclosureTurnDownRemark-dialog" class="easyui-dialog"  closed="true" title="赎楼驳回原因" data-options="iconCls:'icon-save'" style="width: 353px; height: 120px; padding: 10px 20px;">
       <textarea rows="2" id="show_foreclosureTurnDownRemark" name="foreclosureTurnDownRemark" maxlength="100" disabled="disabled" style="width: 300px;"></textarea>
    </div>
    
    
    <%-- 放款记录选取 --%>
	<div id="dlg-buttons_personal">		
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="savePersonal()">选择</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_personal').dialog('close')">关闭</a>
	</div>
	<div id="dlg_personal" class="easyui-dialog" fitColumns="true"  title="放款记录"
	     style="width:350px;top:200px; 0px 0px" buttons="#dlg-buttons_personal" closed="true" >
	    <table id="personal_grid" class="easyui-datagrid"  
	    	style="height: 200px;width: 500px;"
		 	data-options="
		    url: '',
		    method: 'post',
		    rownumbers: true,
		    singleSelect: true,
	    	pagination: true,
		    toolbar: '#toolbar_personal',
		    idField: 'pid'
		    ">
			<thead>
				<tr>
					<th data-options="field:'pid',checkbox:true" ></th>
					<th data-options="field:'recPro',formatter:formatterRecPro" >第几次放款</th>
					<th data-options="field:'recMoney'" >放款金额放款</th>
					<th data-options="field:'updateDate'" >放款时间</th>
					
				</tr>
			</thead>
	   	</table>
		<div id="toolbar_personal">
	        <form method="post" id="searchFromPersonal" name="searchFromPersonal"  style="padding: 0px 0px;">
	     		<div style="margin:5px">
<!-- 				 	<input name="projectId" id="projectId" type="hidden" /> -->
				 	<table class="beforeloanTable">
				 		<tr class = "loan_list">
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
	<!-- 放款记录选取结束 -->
    
  </div>
 </div>
</body>
</html>
