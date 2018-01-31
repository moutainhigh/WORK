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
td{ padding:5px;}

</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript">
/* $(document).ready(function(){
//  初始化加载datagrid
ajaxSearchPage('#grid','#searchFrom');
}) */

//重置按钮
function majaxReset() {
	$('#searchFrom').form('reset')
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
	} else if (val == 5) {
		return "放款申请中";
	} else {
		return "未知";
	}
}

  var collectFeeList = {
	    	formatOperate:function(value, row, index){
	    		var va ="";
	    		var projectType=row.projectType;
	    		if(projectType==2||projectType==6||projectType==8){
	    		   va = "<a href='javascript:void(0)' onclick = 'openCollectFee()'> <font color='blue'>收费</font></a>"
	    		}else if(projectType==4){
	    		   va = "<a href='javascript:void(0)' onclick = 'openExtensionFee()'> <font color='blue'> 展期收费</font></a>"
	    		}
	    	    return va;
			},
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
				return va;
			}
		}
function openCollectFee(){
    var row = $('#grid').datagrid('getSelections');
	if (row.length == 1) {
	var financeHandleId=row[0].pid;
	var projectId=row[0].projectId;
	var projectName=row[0].projectName;
	var projectType=row[0].projectType;//项目类型（贷款=2、贷款展期=4 ,6机构提交业务 8=抵押贷）
	if (projectType==2||projectType==6) {
	   $("#collectFeeForm").show();
	   $("#fddCollectFeeForm").hide();
	   $.ajax({
			url : "getApplyFinanceHandle.action",
			cache : true,
			type : "POST",
			data : {'projectId' : projectId,'financeHandleId' : financeHandleId,'recPros' : "1,2,7"},
			async : false,
			success : function(data, status) {
				var resultMap = eval("("+data+")");
				initForeclosureCollectFeeForm(resultMap,row);
	            $('#edit_dialog').dialog('open').dialog('setTitle', "财务收费--"+projectName);
	            loadcollectFeeHisGrid(projectId);
			}
		});
     }else if(projectType==8){
    	var poundage=row[0].poundage;//手续费
    	$("#fddInterest").numberbox('setValue',poundage);
    	setCombobox3("fddRecPro","FDD_COLLECT_FEE_PRO","-1");//房抵贷收费项目
    	$("#fddRecDate").datebox('setValue',formatterDate(new Date()));//收款日期
    	setCombobox3("fddRecAccount","COLLECT_FEE_BANK",'41033100040019324');
    	$("#fddFinanceHandleId").val(financeHandleId);
    	$("#fddOperator").textbox('setValue','${currUser.realName}');
		$("#collectFeeForm").hide();
		$("#fddCollectFeeForm").show();
	    $('#fddCollectFee_dialog').dialog('open').dialog('setTitle', "财务收费--"+projectName);
	    loadFddcollectFeeHisGrid(projectId)
	 }
	} else if (row.length > 1) {
		$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");       
	} else {
		$.messager.alert("操作提示", "请选择数据", "error");
	}
}
  //初始化赎楼贷收费表单数据
  function initForeclosureCollectFeeForm(resultMap,row){
	  $("#hisTotal").numberbox('setValue',0);
		var financeHandleId=row[0].pid;
		var projectId=row[0].projectId;
		var collectFeeStatus=row[0].status;//收费状态
	  var resultList = resultMap.list;
		var orgName = resultMap.orgName;
		var interest=0;
		var poundage=0;
		var brokerage=0;
		for (var i = 0; i < resultList.length; i++) {
		 if (resultList[i].recPro==1) {
			 interest=resultList[i].recMoney;
		}else if(resultList[i].recPro==2) {
			poundage=resultList[i].recMoney;
		}else if(resultList[i].recPro==7) {
			brokerage=resultList[i].recMoney;
		}
	    }
		var recDate=resultList[0].recDate;
		var resource=resultList[0].resource;
		var operator=resultList[0].operator;
		var recAccount=resultList[0].recAccount;
		var feeRate=resultList[0].feeRate;
		
		$("#feeRate").numberbox('setValue',feeRate);//费率
		if(collectFeeStatus==1){
			$("#interest").numberbox('setValue',interest);//咨询费
			$("#poundage").numberbox('setValue',poundage);//手续费
			$("#brokerage").numberbox('setValue',brokerage);//佣金
			$("#totalMoney").numberbox('setValue',interest+poundage+brokerage);
		}else{
			$("#interest").numberbox('setValue',0);//咨询费
			$("#poundage").numberbox('setValue',0);//手续费
			$("#brokerage").numberbox('setValue',0);//佣金
			$("#totalMoney").numberbox('setValue',0);
		}
		$("#recDate").datebox('setValue',formatterDate(new Date()));//收款日期
		var resourceKey=null;
		if(row[0].projectSource==1){//万通的费用来源
			resourceKey="WT_LOAN_SOURCES_OF_FUNDS";
		}else{//小科的费用来源
			resourceKey="LOAN_SOURCES_OF_FUNDS";
		}
		if (orgName!=null&&orgName!='') {
			//当贷前选择的业务来源合作机构时，收费页面的收费来源默认选中合作机构
		     setComboboxByText("resource",resourceKey,orgName);//费用来源
		     //如果没有匹配到值，默认选择自有资金
		     var resourceTemp=$("#resource").combobox('getValue');
		     if (resourceTemp==null||resourceTemp=="") {
		    	 $("#resource").combobox('select',1);
			 }
		}else{
		     setCombobox3("resource",resourceKey,'1');//费用来源
		}
		if(row[0].projectSource==1){
			setCombobox3("recAccount","WT_COLLECT_FEE_BANK","-1");
		}else{
		   setCombobox3("recAccount","COLLECT_FEE_BANK",'41033100040019324');
		}
		$("#operator").textbox('setValue','${currUser.realName}');
		$("#financeHandleId").val(resultList[0].financeHandleId);
  }
  function subForm() {
	    var row = $('#grid').datagrid('getSelections');
	    var projectType=row[0].projectType;//项目类型（贷款=2、贷款展期=4 ,6机构提交业务 8=抵押贷）
	    var collectFeeForm="collectFeeForm";
	    var dialog="edit_dialog";
	    if (projectType==8) {
	    	collectFeeForm="fddCollectFeeForm";
	    	dialog="fddCollectFee_dialog";
		}
		// 提交表单
		$("#"+collectFeeForm).form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.confirm('操作提示','保存成功!',function(r){
						$('#'+dialog).dialog('close');
						// 必须重置 datagr 选中的行 
						$("#grid").datagrid("clearChecked");
						$("#grid").datagrid('reload');
						$('#'+collectFeeForm).form('reset');
					});	
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
  //打开收取展期费窗口
function openExtensionFee(){
  var row = $('#grid').datagrid('getSelections');
	if (row.length == 1) {
	var projectId=row[0].projectId
	$.ajax({
		type: "POST",
        data:{"projectId":projectId},
        async:false, 
    	url : BASE_PATH+'foreLoanExtensionController/getProjectExtension.action',
		dataType: "json",
	    success: function(resultData){
	    	$("#extensionAmt").numberbox('setValue',resultData.extensionAmt);
	    	$("#extensionRate").numberbox('setValue',resultData.extensionRate);
	    	$("#extensionFee").numberbox('setValue',resultData.extensionFee);
	    	$("#extensionDate").datebox('setValue',resultData.extensionDate);
	    	
			$("#extensionFee_recDate").datebox('setValue',formatterDate(new Date()));//收款日期
			var resourceKey=null;
			if(row[0].projectSource==1){//万通的费用来源
				resourceKey="WT_LOAN_SOURCES_OF_FUNDS";
			}else{//小科的费用来源
				resourceKey="LOAN_SOURCES_OF_FUNDS";
			}
			if (resource!=null&&resource>0) {
				   setCombobox3("extensionFee_resource",resourceKey,resource);//费用来源
			} else {
				   setCombobox3("extensionFee_resource",resourceKey,'1');//费用来源
			}
			if(row[0].projectSource==1){
				setCombobox3("extensionFee_recAccount","WT_COLLECT_FEE_BANK","-1");
			}else{
			   setCombobox3("extensionFee_recAccount","COLLECT_FEE_BANK",'41033100040019324');
			}
	    	$("#extensionFee_operator").textbox('setValue','${currUser.realName}');
			$("#extensionFee_financeHandleId").val(row[0].pid);
	    	$('#extensionFee_dialog').dialog('open').dialog('setTitle', "展期收费--"+row[0].projectName);
		}
	});
	} else if (row.length > 1) {
		$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
	} else {
		$.messager.alert("操作提示", "请选择数据", "error");
	}
}
  //提交展期费表单
function subExtensionFeeForm() {
	// 提交表单
	$("#extensionFeeForm").form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				$.messager.confirm('操作提示','保存成功!',function(r){
 				  $('#edit_dialog').dialog('close');
 				  // 必须重置 datagr 选中的行 
 				  $("#grid").datagrid("clearChecked");
 				  $("#grid").datagrid('reload');
 				  $('#extensionFee_dialog').dialog('close');
 				  $('#extensionFeeForm').form('reset')
				});	
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}
$(document).ready(function() {
   $('#interest,#poundage,#brokerage').numberbox({  
       onChange:function(){  
   		var interest=0;//
   		if ($("#collectFeeForm #interest").val()>0) {
   			interest=parseFloat($("#collectFeeForm #interest").val());
			}
   		var poundage=0;//
   		if ($("#collectFeeForm #poundage").val()>0) {
   			poundage=parseFloat($("#collectFeeForm #poundage").val());
			}
   		var brokerage=0;//
   		if ($("#collectFeeForm #brokerage").val()>0) {
   			brokerage=parseFloat($("#collectFeeForm #brokerage").val());
			}
   		$("#totalMoney").numberbox('setValue',interest+poundage+brokerage);//合计
       }  
   });  
   $('#grid').datagrid({  
       rowStyler:function(index,row){    
           if (row.isChechan==1){    
               return 'background-color:#FFAF4C;';    
           }    
       } 
   }) ;
// debugger;
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
//加载财务收费历史列表（赎楼贷）
function loadcollectFeeHisGrid(projectId) {
	$('#collectFeeHis_grid').datagrid({  
		url:'collectFeeHisList.action',  
		queryParams:{  
		    	projectId : projectId
		},//当列表数据加载完毕
        onLoadSuccess:function(data){  
        	if (data.rows.length > 0) {  
        		var total=0;
        		var consultingFee=0;
        		var poundage=0;
        		var brokerage=0;
                for (var i = 0; i < data.rows.length; i++) {  
                    total+=data.rows[i].consultingFee+data.rows[i].poundage+data.rows[i].brokerage;
                }  
                $("#hisTotal").numberbox('setValue',total);//合计
            } 
        }
    }); 
}
//加载财务收费历史列表（房抵贷）
function loadFddcollectFeeHisGrid(projectId) {
	$('#fddCollectFeeHis_grid').datagrid({  
		url:'collectFeeHisList.action',  
		queryParams:{  
		    	projectId : projectId
		},//当列表数据加载完毕
        onLoadSuccess:function(data){  
        }
    }); 
}


</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>financeHandleController/collectFeeIndexList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td align="right" height="28">项目编号：</td>
        <td><input class="easyui-textbox" name="projectNumber" id="projectNumber" /></td>
        <td align="right" height="28">项目名称：</td>
        <td><input class="easyui-textbox" name="projectName" id="projectName" /></td>
        <td align="right" height="28">放款状态：</td>
       <td >
        <select class="easyui-combobox" id="status"  name="status" style="width:124px" panelHeight="auto" editable="false" >
         <option value=-1  selected="selected" >--请选择--</option>
         <option value=1 >未收费</option>
         <option value=2 >已收费</option>
         <option value=5 >放款申请中</option>
         <option value=3 >已放款</option>
         <option value=4 >放款未完结</option>
        </select>
       </td>
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
      <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openCollectFee()">收费</a>
    </div>

   </div>
   <div class="dksqListDiv" style="height: 100%;"> 
    <table id="grid" title="财务收费列表" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>financeHandleController/collectFeeIndexList.action',
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
       		<th data-options="field:'cz',formatter:collectFeeList.formatOperate" align="center" halign="center"  >操作</th>
       		<th data-options="field:'projectName',formatter:collectFeeList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		</tr>
	</thead>
     <!-- 表头 -->
     <thead>
      <tr>
       <!-- <th data-options="field:'pid',checkbox:true"></th>
       <th data-options="field:'projectId',hidden:true">项目id</th>
       <th data-options="field:'cz',formatter:collectFeeList.formatOperate" align="center" halign="center"  >操作</th>
       <th data-options="field:'projectName',formatter:collectFeeList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th> -->
       <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th>
       <th data-options="field:'houseName',sortable:true" align="center" halign="center">物业名称</th>
       <th data-options="field:'productName',sortable:true" align="center" halign="center">产品名称</th>
       <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方姓名</th>
       <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方姓名</th>       
       <th data-options="field:'loanMoney',sortable:true,formatter:formatMoney" align="center" halign="center">借款金额</th>
       <th data-options="field:'projectStatus',sortable:true,formatter:formatterProjectStatus" align="center" halign="center">审批状态</th>
       <th data-options="field:'status',sortable:true,formatter:formatterStatus" align="center" halign="center">放款状态</th>
       <th data-options="field:'collectFeeStatus',sortable:true,formatter:formatterCollectFeeStatus" align="center" halign="center">收费状态</th>
       <th data-options="field:'isChechan',sortable:true,formatter:formatterIsChechan" align="center" halign="center">是否撤单</th>
       
       <th data-options="field:'accountManager',sortable:true" align="center" halign="center">客户经理</th>
       <th data-options="field:'accountDepartment',sortable:true" align="center" halign="center">所属部门</th>
       
       <th data-options="field:'createrDate',sortable:true,formatter:convertDate" align="center" halign="center">创建时间</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="edit_dialog" class="easyui-dialog" data-options="modal:true" buttons="#submitDiv" style="width: 600px; height: 510px; padding:10px;" closed="true">
    <form id="collectFeeForm" name="collectFeeForm" action="${basePath}financeHandleController/collectFee.action" method="post">
    <table border="0" cellpadding="0" cellspacing="0" >
    <tr>
        <td class="label_right1">咨询费:</td>
		<td>
		<input name="interest" id="interest" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','">
		</td>
        <td class="label_right1">手续费:</td>
        <td>
		<input name="poundage" id="poundage" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','">
		</td>
    <tr>
    <tr>
        <td class="label_right1">佣金:</td>
        <td>
    	<input name="brokerage" id="brokerage" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','">
    	</td>
    	<td class="label_right1">合计:</td>
    	<td>
    	<input name="totalMoney" id="totalMoney" class="easyui-numberbox" readonly="readonly" data-options="min:0,max:9999999999,precision:2,groupSeparator:','">
  		</td>
    </tr>
    <tr>
     	<td class="label_right1">费率:</td>
        <td>
    	<input name="feeRate" id="feeRate" class="easyui-numberbox" data-options="required:false,min:0,max:9999999999,precision:2,groupSeparator:','" readonly="readonly">
    	</td>
     	<td class="label_right1">历史累计:</td>
        <td>
    	<input name="hisTotal" id="hisTotal" class="easyui-numberbox" data-options="required:false,min:0,max:9999999999,precision:2,groupSeparator:','" readonly="readonly">
    	</td>
    </tr>
    <tr>
    	<td class="label_right1">收款日期:</td>
    	<td>
    	<input name="recDate" id="recDate" class="easyui-datebox" data-options="required:true" editable="false">
    	</td>
    	<td class="label_right1">收款账号:</td>
    	<td>
        <input name="recAccount" id="recAccount" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" required="true" style="width: 250px;"/>
       </td>
      </tr>
      <tr>
    	<td class="label_right1">费用来源:</td>
    	<td>
         <input name="resource" id="resource" style="width: 150px;" data-options="validType:'selrequired'" class="easyui-combobox" required="true"/>
       </td>
    	<td class="label_right1">操作员:</td>
    	<td>
    	<input name="operator" id="operator" class="easyui-textbox" readonly="readonly">
       <input name="financeHandleId" id="financeHandleId" type="hidden">
       </td>
       </tr>
       </table>
    </form>
    <!-- 财务收费历史列表(分页查询) -->
    <table id="collectFeeHis_grid"  title="财务收费历史列表" class="easyui-datagrid" style="height: 270px; width: auto;"
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
       <th data-options="field:'consultingFee',formatter:formatMoney" align="center" halign="center">咨询费</th>
       <th data-options="field:'poundage',formatter:formatMoney" align="center" halign="center">手续费</th>
       <th data-options="field:'brokerage',formatter:formatMoney" align="center" halign="center">佣金</th>
       <th data-options="field:'recDate',formatter:convertDate" align="center" halign="center">收款日期</th>
       <th data-options="field:'recAccount'" align="center" halign="center">收款账号</th>
       <th data-options="field:'proResource'" align="center" halign="center">款项来源</th>
       <th data-options="field:'createrName'" align="center" halign="center">操作人</th>
       <th data-options="field:'createrDate',formatter:convertDateTime" align="center" halign="center">操作时间</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="submitDiv">
    <a id="saveFile" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#edit_dialog').dialog('close')"
    >取消</a>
   </div>
    <div id="extensionFee_dialog" class="easyui-dialog" data-options="modal:true" buttons="#subExtensionFeeDiv" style="width: 600px; height: 310px; padding:10px;" closed="true">
    <form id="extensionFeeForm" name="extensionFeeForm" action="${basePath}financeHandleController/collectExtensionFee.action" method="post">
    <table border="0" cellpadding="0" cellspacing="0" >
       <tr>
        <td class="label_right1">展期金额:</td>
		<td colspan="3">
		<input name="extensionAmt" id="extensionAmt" disabled="disabled" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','">
		</td>
    <tr>
    <tr>
        <td class="label_right1">展期费率:</td>
        <td colspan="3">
		<input name="extensionRate" id="extensionRate" disabled="disabled" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','">
		</td>
    </tr>
    <tr>
        <td class="label_right1">展期费用:</td>
        <td colspan="3">
    	<input name="extensionFee" id="extensionFee" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','">
    	</td>
    </tr>
    <tr>
        <td class="label_right1">展期结束时间:</td>
        <td colspan="3">
    	<input name="extensionDate" id="extensionDate" disabled="disabled" class="easyui-datebox" data-options="required:true" editable="false">
    	</td>
    </tr>
    <tr>
    	<td class="label_right1">收款日期:</td>
    	<td>
    	<input name="recDate" id="extensionFee_recDate" class="easyui-datebox" data-options="required:true" editable="false">
    	</td>
    	<td class="label_right1">收款账号:</td>
    	<td>
        <input name="recAccount" id="extensionFee_recAccount" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true" style="width: 250px;"/>
       </td>
      </tr>
      <tr>
    	<td class="label_right1">费用来源:</td>
    	<td>
         <input name="resource" id="extensionFee_resource" style="width: 100px;" data-options="validType:'selrequired'" class="easyui-combobox" required="true"/>
       </td>
    	<td class="label_right1">操作员:</td>
    	<td>
    	<input name="operator" id="extensionFee_operator" class="easyui-textbox" disabled="disabled">
       <input name="financeHandleId" id="extensionFee_financeHandleId" type="hidden">
       </td>
       </tr>
     </table>
    </form>
   </div>
   <div id="subExtensionFeeDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subExtensionFeeForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#extensionFee_dialog').dialog('close')"
    >取消</a>
   </div>
    <div id="fddCollectFee_dialog" class="easyui-dialog" data-options="modal:true" buttons="#subFddCollectFeeDiv" style="width: 600px; height: 450px; padding:10px;" closed="true">
     <!-- 房抵贷收费表单 -->
    <form id="fddCollectFeeForm" name="fddCollectFeeForm" action="${basePath}financeHandleController/collectfddFee.action" method="post">
    <table border="0" cellpadding="0" cellspacing="0" >
    <tr>
        <td class="label_right1">收费项目:</td>
		<td>
		<input name="recPro" id="fddRecPro" data-options="validType:'selrequired'" class="easyui-combobox">
		</td>
        <td class="label_right1">金额:</td>
        <td>
		<input name="interest" id="fddInterest" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','">
		</td>
    <tr>
    <tr>
    	<td class="label_right1">收款日期:</td>
    	<td>
    	<input name="recDate" id="fddRecDate" class="easyui-datebox" data-options="required:true" editable="false">
    	</td>
    	<td class="label_right1">收款账号:</td>
    	<td>
        <input name="recAccount" id="fddRecAccount" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" required="true" style="width: 250px;"/>
       </td>
      </tr>
      <tr>
    	<td class="label_right1">操作员:</td>
    	<td>
    	<input name="operator" id="fddOperator" class="easyui-textbox" readonly="readonly">
       <input name="financeHandleId" id="fddFinanceHandleId" type="hidden">
       </td>
       </tr>
       </table>
    </form>
     <!-- 财务收费历史列表(分页查询) -->
    <table id="fddCollectFeeHis_grid"  title="财务收费历史列表" class="easyui-datagrid" style="height: 270px; width: auto;"
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
       <th data-options="field:'consultingFee',formatter:formatMoney" align="center" halign="center">金额</th>
       <th data-options="field:'recProStr'" align="center" halign="center">收款项目</th>
       <th data-options="field:'recDate',formatter:convertDate" align="center" halign="center">收款日期</th>
       <th data-options="field:'recAccount'" align="center" halign="center">收款账号</th>
       <th data-options="field:'createrName'" align="center" halign="center">操作人</th>
       <th data-options="field:'createrDate',formatter:convertDateTime" align="center" halign="center">操作时间</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="subFddCollectFeeDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#fddCollectFee_dialog').dialog('close')"
    >取消</a>
   </div>
  </div>
 </div>
</body>
</html>
