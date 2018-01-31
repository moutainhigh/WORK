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
.bt{ background-color: #E0ECFF;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#EFF5FF,endColorstr=#E0ECFF,GradientType=0);
font-size: 12px;
font-weight: bold;
color: #0E2D5F;}
.bt td{padding:4px 10px;border-bottom:1px #95B8E7 solid;}
.srk1{ height:60px;width:99%;}
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript">
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
		} else if (val == 3) {
			return "已放款";
		}  else if (val == 4) {
			return "放款未完结";
		} else {
			return "未知";
		}
	}
	function formatBalanceConfirm(val, row) {
		if (val == 1) {
			return "未确认";
		} else if (val == 2) {
			return "已确认";
		}  else {
			return "未知";
		}
	}
	var foreclosureList = {
		    	formatOperate:function(value, row, index){
		    		var va = ""
		    		if(row.foreclosureStatus==<%=com.xlkfinance.bms.common.constant.Constants.IS_FORECLOSURE%>&&row.balanceConfirm==<%=com.xlkfinance.bms.common.constant.Constants.NO_BALANCE_CONFIRM%>){
		    			va=va+"<a href='javascript:void(0)' onclick = 'openBalanceConfirmDialog()'> <font color='blue'>余款确认</font></a>";
		    		}
		    		if(row.foreclosureStatus==3){
		    			va=va+"  <a href='javascript:void(0)' onclick = 'openForeclosureTurnDownRemarkDialog()'> <font color='blue'>查看驳回原因</font></a>";
		    		}
		    	    return va;
				},
				// 项目名称format
				formatProjectName:function(value, row, index){
					var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
					return va;
				}
	}
	function openBalanceConfirmDialog(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
		var bizHandleId=row[0].bizHandleId;
		 $.ajax({
				url : "getHouseBalance.action",
				cache : true,
				type : "POST",
				data : {'handleId' : bizHandleId},
				async : false,
				success : function(data, status) {
					var result = eval("("+data+")");
					var resultList =result['houseBalanceList'];
					for (var i = 0; i < resultList.length; i++) {
						var balance=resultList[i].balance;
						var backAccount=resultList[i].backAccount;
						var count=resultList[i].count;
					 	$("#notRecoveredBalance"+count).numberbox('setValue',balance);
						$("#notRecoveredBackAccount"+count).textbox('setValue',backAccount); 
					}
					$("#notRecoveredBalanceHandleId").val(resultList[0].handleId); 
		            $('#balance_confirm_dialog').dialog('open').dialog('setTitle', "余款确认--"+row[0].projectName);
				}
			});
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//查看驳回备注
	function openForeclosureTurnDownRemarkDialog(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
		var foreclosureTurnDownRemark=row[0].foreclosureTurnDownRemark;
		$("#show_foreclosureTurnDownRemark").val(foreclosureTurnDownRemark);
		$('#foreclosureTurnDownRemark-dialog').dialog('open')
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能查看一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//余款未收确认
	function subBalanceConfirmForm() {
			$("#balanceForm").form('submit', {
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(result) {
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						$.messager.alert('操作提示', ret.header["msg"], 'info');
						//关闭窗口
						$("#balance_confirm_dialog").dialog('close')
		                 // 重新加载列表
		                 $("#grid").datagrid("clearChecked");
		                 $("#grid").datagrid('reload'); 
		                 //$("#balanceForm").form('reset');
					} else {
						$.messager.alert('操作提示', ret.header["msg"], 'error');
					}
				}
			});
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
	//
	function openForeclosure() {
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if (row[0].foreclosureStatus==3) {
				$.messager.alert("操作提示", "赎楼状态为已驳回，不可赎楼", "error");
				return;
			}
			$('#foreclosureForm_1').form('reset');
			$('#foreclosureForm_2').form('reset');
			$('#remark1').val("");
			$('#remark2').val("");
			 $.ajax({
					url : "getHouseBalance.action",
					cache : true,
					type : "POST",
					data : {'handleId' : row[0].bizHandleId},
					async : false,
					success : function(data, status) {
						var result = eval("("+data+")");
						var resultList =result['houseBalanceList'];
						var handleUserSet =result['handleUserSet'];
						var principal ='';
						var payDate ='';
						for (var i = 0; i < resultList.length; i++) {
							var pid=resultList[i].pid;
							 principal=resultList[i].principal;
							 payDate=resultList[i].payDate;
							//赎楼日期为空取第一次放款金额
							if(principal ==null || principal ==''){
								principal=resultList[i].loanAmount;
							}//赎楼日期为空取第一次放款日期
							if(payDate ==null || payDate ==''){
								 payDate=resultList[i].loanTime;
							}
							var houseClerkId=resultList[i].houseClerkId;
							var houseClerk=resultList[i].houseClerk;
							var handleUserId=resultList[i].handleUserId;
							var handleUserName=resultList[i].handleUserName;
							var interest=resultList[i].interest;
							var defaultInterest=resultList[i].defaultInterest;
							var balance=resultList[i].balance;
							var backAccount=resultList[i].backAccount;
							var count=resultList[i].count;
						    var handleId=resultList[i].handleId;
							$("#principal"+count).numberbox('setValue',principal);
							$("#payDate"+count).datebox('setValue',payDate);
							if(houseClerk==null||houseClerk==""){
							    $("#houseClerk"+count).textbox('setValue','${currUser.realName}');
							}else{
							    $("#houseClerk"+count).textbox('setValue',houseClerk);
							}
							if(handleUserSet!=null){
								$("#handleUserId"+count).combobox("loadData", handleUserSet);
							}
							if(handleUserName!=null&&handleUserName!=""){
								$("#handleUserId"+count).combobox("setValue", handleUserName);
							}
							$("#interest"+count).numberbox('setValue',interest);
							$("#defaultInterest"+count).numberbox('setValue',defaultInterest);
						 	$("#balance"+count).numberbox('setValue',balance);
							//有保存过，则选择保存的值
							if(row[0].projectSource==1){
 			                if(backAccount==null||backAccount==""){
 		                       //还没有保存过，默认第一个
 		                       setCombobox3("backAccount"+count,"WT_MAKE_LOANS_BANK",'--请选择--');
 		                    }else{
 			                   setCombobox3("backAccount"+count,"WT_MAKE_LOANS_BANK",backAccount);
 		                    }
							}
 			                else{
 	 			                if(backAccount==null||backAccount==""){
 	 		                       //还没有保存过，默认第一个
 	 		                       setCombobox3("backAccount"+count,"MAKE_LOANS_BANK",'--请选择--');
 	 		                    }else{
 	 			                   setCombobox3("backAccount"+count,"MAKE_LOANS_BANK",backAccount);
 	 		                    }
 			                }
							$("#handleId"+count).val(handleId); 
							$("#count"+count).val(count); 
							$("#pid"+count).val(pid); 
							$("#totalMoney"+count).numberbox('setValue',principal+interest+defaultInterest); 
						}
						var remark=resultList[0].remark;
						if (remark!=null&&remark!='') {
					       $("#remark").val(remark);  
						}
				        $('#foreclosure_form_dialog').dialog('open').dialog('setTitle', "赎楼--"+row[0].projectName);
					}
				});
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
			 $('#foreclosureTurnDown-dialog').dialog('open').dialog('setTitle', "驳回--"+row[0].projectName);
			 $("#foreclosureTurnDownRemark").val('');
			 $("#foreclosureTurnDownHandleId").val(row[0].bizHandleId);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
    //提交赎楼信息
	function subHouseBalanceForm() {
		var row = $('#grid').datagrid('getSelections');
		  var foreclosureStatus = row[0].foreclosureStatus;
		 if (foreclosureStatus == 2) {
			 $.messager.alert("操作提示", "此项目已赎楼，不能多次赎楼！", "error", function(){
				 $('#foreclosure_form_dialog').dialog('close');
			 });
			 return;
		 }
		for (var i = 1; i <= 1; i++) {
			var formName = "foreclosureForm_" + i;
			$("#" + formName).form('submit', {
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(result) {
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						$.messager.alert('操作提示', ret.header["msg"], 'info', function(){
							$('#foreclosure_form_dialog').dialog('close');
						});
		                 // 重新加载列表
		                 $("#grid").datagrid("clearChecked");
		                 $("#grid").datagrid('reload');
		                 $("#"+formName+" input[type=reset]").trigger("click");
					} else {
						$.messager.alert('操作提示', ret.header["msg"], 'error');
					}
				}
			});
		}
	}
	//同步备注内容
	function changeRemark(remark){
		$("#remark1").val(remark);
		$("#remark2").val(remark);
	}
	//打开赎楼资料文件上传窗口
    function upFile(handleDynamicId,handleFlowName,handleId){
    	var row = $('#grid').datagrid('getSelections');
    	var bizHandleId=row[0].bizHandleId;
    	$('#saveFile').attr("onclick","submitFileForm('"+bizHandleId+"')");
    	$('#foreclosureFile-dialog').dialog('open').dialog('setTitle', "上传资料");
    }
    //提交赎楼资料文件
	function submitFileForm(bizHandleId){
		if($("#offlineMeetingFiles").textbox('getValue')!=''){
			$('#foreclosureFileForm').attr('enctype','multipart/form-data');
			$('#foreclosureFileForm').form('submit',{
				url:'${basePath}bizHandleController/uploadForeclosureFile.action?bizHandleId='+bizHandleId,
				success:function(data) {
					var ret = eval("(" + data + ")");
					if (ret && ret.header["success"]) {
						$.messager.alert("操作提示", ret.header["msg"]);
						$("#foreclosureFile-dialog").dialog("close");
					}else{
						$.messager.alert('操作提示', ret.header["msg"], 'error');
					}
				   $("#offlineMeetingFiles").textbox('setValue','');
				},
				
			});
		}else{
			$.messager.alert('操作','请选择文件!','info');
		} 
	}
	//打开放款挂起窗口
	function openLoanSuspend() {
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var projectId=row[0].projectId;
			$.ajax({
					url : "${basePath}financeHandleController/getLoanSuspendInfo.action",
					cache : true,
					type : "POST",
					data : {'projectId' : projectId},
					async : false,
					success : function(data, status) {
						var result = eval("("+data+")");
						var loanSuspend =result['loanSuspend'];
						if (loanSuspend!=null) {
							$("#suspendDay").numberbox('setValue',loanSuspend.suspendDay);
							$("#startDate").datebox('setValue',loanSuspend.startDate);
							$("#endDate").datebox('setValue',loanSuspend.endDate);
							$("#loanSuspendRemark").val(loanSuspend.remark);
							$('#subLoanSuspendBtn').linkbutton('disable');//提交按钮置灰
						}else{
							$("#suspendDay").numberbox('setValue',0);
							$("#startDate").datebox('setValue','');
							$("#endDate").datebox('setValue','');
							$("#endDate").datebox('setValue','');
							$("#loanSuspendRemark").val("");
							$('#subLoanSuspendBtn').linkbutton('enable');//提交按钮取消置灰
						}
					}
			 });
			 $("#loanSuspendProjectId").val(projectId);
			 $('#loanSuspend-dialog').dialog('open').dialog('setTitle', "期限顺延--"+row[0].projectName);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
    //提交放款挂起
	function subLoanSuspendForm(bizHandleId){
		$("#loanSuspendForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', ret.header["msg"], 'info');
					$("#loanSuspend-dialog").dialog("close");
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
    //初始化
	$(document).ready(function() {
		 $('#principal1,#interest1,#defaultInterest1,#principal2,#interest2,#defaultInterest2').numberbox({  
		        onChange:function(){  
		        	var foreclosureForm_1=document.getElementById("foreclosureForm_1");
		    		var foreclosureForm_2=document.getElementById("foreclosureForm_2");
		    		var principal1=0;//
		    		if (foreclosureForm_1.principal.value.length>0) {
		    			principal1=parseFloat(foreclosureForm_1.principal.value);
					}
		    		var interest1=0;//
		    		if (foreclosureForm_1.interest.value.length>0) {
		    			interest1=parseFloat(foreclosureForm_1.interest.value);
					}
		    		var defaultInterest1=0;//
		    		if (foreclosureForm_1.defaultInterest.value.length>0) {
		    			defaultInterest1=parseFloat(foreclosureForm_1.defaultInterest.value);
					}
		    		var principal2=0;//
		    		if (foreclosureForm_2.principal.value.length>0) {
		    			principal2=parseFloat(foreclosureForm_2.principal.value);
					}
		    		var interest2=0;//
		    		if (foreclosureForm_2.interest.value.length>0) {
		    			interest2=parseFloat(foreclosureForm_2.interest.value);
					}
		    		var defaultInterest2=0;//
		    		if (foreclosureForm_2.defaultInterest.value.length>0) {
		    			defaultInterest2=parseFloat(foreclosureForm_2.defaultInterest.value);
					}
		    		var totalMoney1=principal1+interest1+defaultInterest1;//第一次赎楼小计
		    		var totalMoney2=principal2+interest2+defaultInterest2;//第二次赎楼小计
		    		$("#totalMoney1").numberbox('setValue',totalMoney1);//第一次赎楼小计
		    		$("#totalMoney2").numberbox('setValue',totalMoney2);//第二次赎楼小计
		    		
		    		var row = $('#grid').datagrid('getSelections');
		    		var oneRealLoan=row[0].oneRealLoan;
		    		var twoRealLoan=row[0].twoRealLoan;
		    		$("#balance1").numberbox('setValue',oneRealLoan-totalMoney1);//一次赎楼余额转回
		    		//第二次赎楼小计>0，才显示二次赎楼余额转回
		    		if(totalMoney2>0){
		    		   $("#balance2").numberbox('setValue',twoRealLoan-totalMoney2);//二次赎楼余额转回
		            }
		        }  
		    }); 
		 //放款挂起的挂起日期计算
		 $('#startDate,#endDate').datebox({
		        onChange:function(){  
		        	var startDate=$("#startDate").datebox('getValue');
		        	var endDate=$("#endDate").datebox('getValue');
		        	if (startDate !="" && endDate!="") {
		        	  $("#suspendDay").numberbox('setValue',dateDiffIncludeToday(startDate, endDate));
					}
		        }  
		    }); 
		 
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
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>bizHandleController/foreclosureIndexList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td width="80" height="28" align="right" >项目编号：</td>
        <td><input class="easyui-textbox" name="projectNumber" id="projectNumber" /></td>
        <td width="80" height="28" align="right" >项目名称：</td>
        <td><input class="easyui-textbox" name="projectName" id="searchProjectName" /></td>
       <td width="80" height="28" align="right" >收费状态：</td>
        <td><select class="easyui-combobox" id="recFeeStatus" name="recFeeStatus" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=2>已收费</option>
          <option value=5>放款申请中</option>
          <option value=3>已放款</option>
          <option value=4>放款未完结</option>
        </select></td>
        <td width="80" height="28" align="right">赎楼状态：</td>
        <td><select class="easyui-combobox" name="foreclosureStatus" style="width: 124px" panelHeight="auto" editable="false">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>未赎楼</option>
          <option value=2>已赎楼</option>
          <option value=3>已驳回</option>
        </select></td>
       </tr>
       <tr>
        <td align="right" height="28">物业名称：</td>
        <td><input class="easyui-textbox" name="houseName"/></td>
        <td align="right" height="28">买方姓名：</td>
        <td><input class="easyui-textbox" name="buyerName"/></td>
        <td align="right" height="28">卖方姓名：</td>
        <td><input class="easyui-textbox" name="sellerName"/></td>
       </tr>
       <tr>
        <td align="right" height="28">办理人：</td>
        <td><input class="easyui-textbox" name="handleUserName"/></td>
        
        <td align="right" height="28">赎楼日期：</td>
        <td> 
	        <input id="fromPayDate" name="fromPayDate" editable="false" class="easyui-datebox"/>至
	        <input id="endPayDate" name="endPayDate" editable="false" class="easyui-datebox"/>
        </td>
        
         <td width="100" align="right"  height="28">产品名称：</td>
		  <td colsapn="2">
			 <input class="easyui-combobox" editable="false" style="width:129px;" name="productId" id="productId"/>						
		  </td>
       </tr>
       <tr>
        <td colspan="3"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>
      </table>
     </div>
    </form>

    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
      <shiro:hasAnyRoles name="FORECLOSURE,ALL_BUSINESS">
      <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openForeclosure()">赎楼</a>
      </shiro:hasAnyRoles>
      <shiro:hasAnyRoles name="FORECLOSURE_TURN_DOWN,ALL_BUSINESS">
      <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openForeclosureTurnDown()">赎楼驳回</a>
      </shiro:hasAnyRoles>
      <shiro:hasAnyRoles name="LOAN_SUSPEND_BTN,ALL_BUSINESS">
      <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openLoanSuspend()">期限顺延</a>
      </shiro:hasAnyRoles>
    </div>

   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="赎楼列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options=" 
		    url: '<%=basePath%>bizHandleController/foreclosureIndexList.action',
		    method: 'POST',
		    rownumbers: true,
		    queryParams:{'projectName':'${projectName}'},<!-- //此处，如果是从任务列表跳转过来，则projectName是有值的，此时定位该任务的项目数据 -->
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
	       <th data-options="field:'cz',formatter:foreclosureList.formatOperate" align="center" halign="center"  >操作</th>
	       <th data-options="field:'projectId',hidden:true">项目id</th>
	       <th data-options="field:'projectName',formatter:foreclosureList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		</tr>
	</thead>
     <!-- 表头 -->
     <thead>
      <tr>
       <!-- <th data-options="field:'pid',checkbox:true"></th>
       <th data-options="field:'cz',formatter:foreclosureList.formatOperate" align="center" halign="center"  >操作</th>
       <th data-options="field:'projectId',hidden:true">项目id</th>
       <th data-options="field:'projectName',formatter:foreclosureList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th> -->
       <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th>
       <th data-options="field:'productName',sortable:true" align="center" halign="center">产品名称</th>
       <th data-options="field:'houseName',sortable:true" align="center" halign="center">物业名称</th>
       <th data-options="field:'handleUserName',sortable:true" align="center" halign="center">办理人</th>
       <th data-options="field:'payDate',sortable:true" align="center" halign="center">赎楼日期</th>
       <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方姓名</th>
       <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方姓名</th>       
       <th data-options="field:'oneRealLoan',sortable:true,formatter:formatMoney" align="center" halign="center">一次放款金额</th>
       <th data-options="field:'twoRealLoan',sortable:true,formatter:formatMoney" align="center" halign="center">二次放款金额</th>
       <th data-options="field:'realLoan',sortable:true,formatter:formatMoney" align="center" halign="center">总计</th>
       <th data-options="field:'recFeeStatus',sortable:true,formatter:formatterStatus" align="center" halign="center">收费状态</th>
       <th data-options="field:'oneForeclosureMoney',sortable:true,formatter:formatMoney" align="center" halign="center">一次赎楼金额</th>
<!--        <th data-options="field:'twoForeclosureMoney',sortable:true,formatter:formatMoney" align="center" halign="center">二次赎楼金额</th> -->
       <th data-options="field:'foreclosureMoney',sortable:true,formatter:formatMoney" align="center" halign="center">合计</th>
       <th data-options="field:'foreclosureStatus',sortable:true,formatter:formatterBizForeclosureStatus" align="center" halign="center">赎楼状态</th>
       <th data-options="field:'houseClerkId',hidden:true">赎楼员id</th>
       <th data-options="field:'bizHandleId',hidden:true">业务办理id</th>
       <th data-options="field:'oneBalance',sortable:true,formatter:formatMoney">一次未收余款</th>
<!--        <th data-options="field:'twoBalance',sortable:true,formatter:formatMoney">二次未收余款</th> -->
       <th data-options="field:'balance',sortable:true,formatter:formatMoney">总计</th>
       <th data-options="field:'balanceConfirm',sortable:true,formatter:formatBalanceConfirm" align="center" halign="center" >未收余款确认</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="foreclosure_form_dialog" class="easyui-dialog" data-options="modal:true" buttons="#foreclosureOperateDiv" style="width: 750px; height: 440px;" closed="true">
      <form action="${basePath}bizHandleController/foreclosure.action" id="foreclosureForm_1" name="foreclosureForm_1"  method="post">
        <table class="creditsTable" style="padding-left:35px;width: 100%;">
         <tr>
          <td class="label_right1"><b>赎楼项目&nbsp;&nbsp;</b></td>
          <td class="label_center1"><b>赎楼金额</b></td>
          <td class="label_center1"><b>赎楼日期</b></td>
          <td class="label_center1"><b>赎楼员</b></td>
          <td class="label_center1"><b>办理人</b></td>
         </tr>
         <tr>
          <td class="label_right1"><font color="red">*</font>第一次赎楼本金：</td>
          <td class="label_center1"><input name="principal" id="principal1" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" /></td>
          <td class="label_center1"><font color="red">*</font><input name="payDate" id="payDate1" class="easyui-datebox" editable="false"/></td>
          <td class="label_center1"><font color="red">*</font>
		  <!--赎楼员 -->
          <input name="houseClerk" id="houseClerk1"   disabled="disabled" data-options="validType:'length[0,20]'" class="easyui-textbox" /></td>
          <td class="label_center1"><font color="red">*</font>
		  <!--办理人-->
          <input class="easyui-combobox" id="handleUserId1" name="handleUserId" style="width:100px" data-options="valueField:'id', textField:'realName', panelHeight:'auto',required:true" editable="false">
          </td>
         </tr>
         <tr>
          <td class="label_right1">利息：</td>
          <td colspan="3" class="label_center1"><input name="interest" id="interest1" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
         </tr>
         <tr>
          <td class="label_right1">罚息：</td>
          <td colspan="3" class="label_center1"><input name="defaultInterest" id="defaultInterest1" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
         </tr>
         <tr>
          <td class="label_right1">第一次赎楼小计：</td>
          <td colspan="3" class="label_center1"><input name="totalMoney1" id="totalMoney1"  class="easyui-numberbox" disabled="disabled" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
         </tr>
          <tr> 
          <td class="label_right1" style="padding-top: 10px;">一次赎楼余额转回：</td>
          <td class="label_center2" style="padding-top: 10px;"><input name="balance" id="balance1" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
          <td class="label_center1" style="padding-top: 10px;" colspan="3">转回帐号：<input name="backAccount" id="backAccount1" class="easyui-combobox" editable="false" panelHeight="auto" style="width: 255px;"/>
          </td>
         </tr>
        </table>
         <input type="hidden" name="pid" id="pid1">
         <input type="hidden" name="handleId" id="handleId1">
         <input type="hidden" name="count" id="count1">
         <input type="hidden" name="remark" id="remark1">
        </form>
        <form action="${basePath}bizHandleController/foreclosure.action" id="foreclosureForm_2" name="foreclosureForm_2"  method="post">
        <table class="creditsTable" style="padding-left:35px;width: 100%;margin-top:30px;display: none;">
         <tr> 
          <td class="label_right1">第二次赎楼本金：</td>
          <td class="label_center1"><input name="principal" id="principal2" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
          <td class="label_center1"><input name="payDate" id="payDate2" class="easyui-datebox" editable="false"/></td>
          <td class="label_center1">
          <input name="houseClerk" id="houseClerk2" disabled="disabled" class="easyui-textbox" data-options="validType:'length[0,20]'" /></td>
          <td class="label_center1">
          <input class="easyui-combobox" id="handleUserId2" name="handleUserId" style="width:100px" data-options="valueField:'id', textField:'realName', panelHeight:'auto',required:true" editable="false">
          </td>
         </tr>
         <tr>
          <td class="label_right1">利息：</td>
          <td colspan="3" class="label_center"><input name="interest" id="interest2" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
         </tr>
         <tr>
          <td class="label_right1">罚息：</td>
          <td colspan="3" class="label_center"><input name="defaultInterest" id="defaultInterest2" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
         </tr>
         <tr>
          <td class="label_right1">第二次赎楼小计：</td>
          <td colspan="3" class="label_center"><input name="totalMoney2" id="totalMoney2" disabled="disabled" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
         </tr>
        </table>
        <table class="creditsTable" style="padding-left:35px;width:100%;padding-top: 10px;">
         <tr style="display: none;">
          <td class="label_right1">二次赎楼余额转回：</td>
          <td class="label_center2"><input name="balance" id="balance2" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
          <td class="label_center1" colspan="2">转回帐号： <input name="backAccount" id="backAccount2" class="easyui-combobox" editable="false" panelHeight="auto" style="width: 255px;"/>
         </td>
         </tr>
         <tr style="height:50px;">
          <td class="label_right1"></td>
          <td colspan="3" class="label_center"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="upFile('${handleDynamicMap[item.pid+''].pid }','${item.name}','${handleDynamicMap[item.pid+''].handleId }')">资料上传</a></td>
         </tr>
         <tr>
          <td class="label_right1">备注：</td>
          <td colspan="3"><textarea rows="3" id="remark" maxlength="500" onchange="changeRemark(this.value)" style="width: 95%;"></textarea></td>
         </tr>
        </table>
         <input type="hidden" name="pid" id="pid2">
         <input type="hidden" name="handleId" id="handleId2">
         <input type="hidden" name="count" id="count2">
         <input type="hidden" name="remark" id="remark2">
        </form>
        
   </div>
   <div id="foreclosureOperateDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subHouseBalanceForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#foreclosure_form_dialog').dialog('close')"
    >取消</a>
   </div>
    <div id="balance_confirm_dialog" class="easyui-dialog" data-options="modal:true" buttons="#balanceOperateDiv" style="width: 750px; height: 120px;" closed="true">
      <form action="${basePath}bizHandleController/balanceConfirm.action" id="balanceForm" name="balanceForm"  method="post">
      <table class="creditsTable" style="padding-left:35px;width: 100%;">
          <tr>
          <td class="label_right1">一次赎楼余额转回：</td>
          <td class="label_center2"><input name="firstBalance" id="notRecoveredBalance1" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
          <td class="label_right">转回帐号：</td>
          <td  class="label_center2"><input name="firstBackAccount" id="notRecoveredBackAccount1" class="easyui-textbox" data-options="validType:'length[0,50]'"/></td>
         </tr>
         <tr style="display: none;">
          <td class="label_right1">二次赎楼余额转回：</td>
          <td class="label_center2"><input name="secondBalance" id="notRecoveredBalance2" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/></td>
          <td class="label_right">转回帐号：</td>
          <td class="label_center2"><input name="secondBackAccount" id="notRecoveredBackAccount2" class="easyui-textbox" data-options="validType:'length[0,50]'"/></td>
         </tr>
        </table>
         <input type="hidden" name="handleId" id="notRecoveredBalanceHandleId">
        </form>
   </div>
    <div id="balanceOperateDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subBalanceConfirmForm()">确认已收</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#balance_confirm_dialog').dialog('close')"
    >取消</a>
   </div>
       <div id="foreclosureFile-dialog" class="easyui-dialog" data-options="modal:true" buttons="#upFileinfo" style="width: 650px; height: 150px; padding: 10px 20px;" closed="true">
      <form id="foreclosureFileForm" name="foreclosureFileForm" method="post">
       <table>
        <tr>
         <td><span class="cus_red">*</span>资料文件：</td>
         <td><input class="easyui-filebox offlineMeetingInput" name="offlineMeetingFile" id="offlineMeetingFiles" style="width: 425px"></td>
        </tr>
       </table>
      </form>
     </div>
      <div id="upFileinfo">
      <a id="saveFile" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitFileForm()">保存</a> <a href="javascript:void(0)"
       class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#foreclosureFile-dialog').dialog('close')"
      >取消</a>
     </div>
     
       <div id="foreclosureTurnDown-dialog" class="easyui-dialog" data-options="modal:true" buttons="#foreclosureTurnDownDiv" style="width: 353px; height: 150px; padding: 10px 20px;" closed="true">
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
      <a id="saveFile" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subForeclosureTurnDown()">提交</a> <a href="javascript:void(0)"
       class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#foreclosureTurnDown-dialog').dialog('close')"
      >取消</a>
     </div>
      <div id="foreclosureTurnDownRemark-dialog" class="easyui-dialog" data-options="modal:true"  closed="true" title="赎楼驳回原因" data-options="iconCls:'icon-save'" style="width: 353px; height: 120px; padding: 10px 20px;">
       <textarea rows="2" id="show_foreclosureTurnDownRemark" name="foreclosureTurnDownRemark" maxlength="100" disabled="disabled" style="width: 300px;"></textarea>
      </div>
      
      <!-- 放款挂起 -->
      <div id="loanSuspend-dialog" class="easyui-dialog" buttons="#loanSuspendBtn" data-options="modal:true" style="width: 600px; height: 200px; padding: 10px 20px;" closed="true">
      <form id="loanSuspendForm" name="loanSuspendForm" method="post" action="${basePath}/financeHandleController/loanSuspendInfo.action">
      <input type="hidden" name="projectId" id="loanSuspendProjectId">
      <table style="width: 100%;">
          <tr>
          <td>顺延时间：</td>
          <td>
          <input class="easyui-datebox" editable="false" data-options="required:true" name="startDate" id="startDate"/>
        至 <input class="easyui-datebox" validType="checkDateEqualRange['#startDate']" editable="false" data-options="required:true" name="endDate" id="endDate" />
          </td>
          <td>顺延天数：</td>
          <td><input name="suspendDay" id="suspendDay" class="easyui-numberbox" editable="false"/></td>
         </tr>
         <tr>
          <td>备注：</td>
          <td colspan="3"><textarea rows="2" id="loanSuspendRemark" name="remark" maxlength="500" style="width: 400px;"></textarea></td>
         </tr>
        </table>
      </form>
     </div>
      <div id="loanSuspendBtn">
      <a id="subLoanSuspendBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subLoanSuspendForm()">提交</a> <a href="javascript:void(0)"
       class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#loanSuspend-dialog').dialog('close')"
      >取消</a>
     </div>
  </div>
 </div>
</body>
</html>
