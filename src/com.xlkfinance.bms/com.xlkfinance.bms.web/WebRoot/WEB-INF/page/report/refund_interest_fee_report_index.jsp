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
<script type="text/javascript">

//重置按钮
function majaxReset(){
	$(".ecoTradeId1").hide();
	$('#searchFrom').form('reset')
}


//业务办理状态格式化
function formatterBizApplyHandleStatus(val, row) {
	if (val == 1) {
		return "未申请";
	} else if (val == 2) {
		return "已申请";
	} else if (val == 3) {
		return "已完成";
	} else if (val == 4) {
		return "已归档";
	} else {
		return "未知";
	}
}
// 放款到账状态格式化
function formatterRecStatus(val, row) {
	if (val == 1) {
		return "未到账";
	} else if (val == 2) {
		return "已到账";
	} else {
		return "未知";
	}
}
//退款申请状态格式化
function formatterBackFeeApplyHandleStatus(val, row) {
	if (val == 1) {
		return "未申请";
	} else if (val == 2) {
		return "申请中";
	} else if (val == 3) {
		return "驳回";
	} else if (val == 4) {
		return "审核中";
	} else if (val == 5) {
		return "审核通过";
	} else if (val == 6) {
		return "审核不通过";
	} else if (val == 7) {
		return "已归档";
	} else {
		return "未知";
	}
}


//业务来源
function formatterBusinessSource(val,row){
	if(val == 13779){
		return "银行";
	} else if(val == 13780){
		return "中介";
	} else if(val == 13781){
		return "朋友";
	} else if(val == 13782){
		return "合作机构";
	} else {
		return "其他";
	}
}
function formatterInnerOrOut(val, row) {
	if (val == 1) {
		return "内单";
	} else if (val == 2) {
		return "外单";
	}else {
		return "未知";
	}
}


var refundFeeList = {
    	formatProjectName:function(value, row, index){
    		var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>";
			return va;
		},
       	formatOperate : function(value, row, index) {
         	var va=""
           	if (row.isConfirm==1&&row.backFeeApplyHandleStatus==5) {
           		var refundfeeConfirmButton=$("#refundfeeConfirmButton").val();
           		if (refundfeeConfirmButton!=null&&refundfeeConfirmButton!='') {
      			     va = refundfeeConfirmButton;
    			}
    		}
  			return va;
		}
	}

$(document).ready(function() {
    $('#grid').datagrid({  
		  rowStyler:function(index,row){    
		      if (row.isChechan==1){    
		          return 'background-color:#FFAF4C;';    
		      }    
		  } 
    }) ;
    
    var orgId = $("#orgId").combobox('getValue');
	$('#orgId').combobox({
		url:'${basePath}elementLendController/getLoginUserOrgList.action',    
	    valueField:'id',
	    textField:'name',
	    onLoadSuccess: function(rec){
	    	if(orgId >0){
	    		$('#orgId').combobox('setValue',projectId);
	    	}else{
	    		$('#orgId').combobox('setValue',-1);
	    	}
        } 
	});
	
	var pmUserId = $("#pmUserId").combobox('getValue');
	$('#pmUserId').combobox({
		url:'${basePath}beforeLoanController/findAcctManagerList.action',    
	    valueField:'pid',
	    textField:'realName',
	    onLoadSuccess: function(rec){
	    	if(pmUserId >0){
	    		$('#pmUserId').combobox('setValue',pmUserId);
	    	}else{
	    		$('#pmUserId').combobox('setValue',-1);
	    	}
        } 
	});
	
	var url = BASE_PATH+ "beforeLoanController/checkUserOrgInfo.action";
	
	$.ajax({
		url : url,
		async : false,
		success : function(data) {
			isWantong = data;
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

function cxportDifferWarn() {
	var rows = $('#grid').datagrid('getSelections');
	var projectIds = "";
	for (var i = 0; i < rows.length; i++) {
		if (i == 0) {
			projectIds += rows[i].pid;
		} else {
			projectIds += "," + rows[i].pid;
		}
	}
	$
			.ajax({
				url : BASE_PATH
						+ 'templateFileController/checkFileUrl.action',
				data : {
					templateName : 'REFUND_INTEREST'
				},
				dataType : 'json',
				success : function(data) {
					if (data == 1) {
						window.location.href = "${basePath}reportController/refundFeeExcelExportList.action?projectIds="
								+ projectIds + "&templateName=REFUND_INTEREST";
					} else {
						alert('退利息费报表的导出模板不存在，请上传模板后重试');
					}
				}
			})
}

</script>
<title>统计分析</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/reportList.action?type=2" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding:5px">
				<table>
                 <tr>
                 	 <td width="80"  align="right" height="28">部门：</td>
						<td ><select id="orgId" class="easyui-combobox" name="orgId" editable="false" style="width:150px;" /></td>
						<td width="80" align="right" height="28">客户经理：</td>
						<td>
							<select id="pmUserId" class="easyui-combobox" name="pmUserId" editable="false" style="width:150px;" />
						</td>
						<td width="80" align="right" height="28">产品：</td>
						<td>
							<select id="productId" class="easyui-combobox" name="productId" editable="false" style="width:150px;" />
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
                  <td  align="right" height="28">申请状态：</td>
                  <td><select class="easyui-combobox" name="backFeeApplyHandleStatus" style="width: 125px" panelHeight="auto" editable="false">
                    <option value=-1 selected="selected">--请选择--</option>
                    <option value=1>未申请</option>
                    <option value=2>申请中</option>
                    <option value=3>驳回</option>
                    <option value=4>审核中</option>
                    <option value=5>审核通过</option>
                    <option value=6>审核不通过</option>
                    <option value=7>已归档</option>
                  </select></td>
                  <td width="100" align="right" >出款日期：</td>
					<td colsapn="2">
						<input name="startDate" id="startDate" class="easyui-datebox" editable="false"/> <span>~</span> 
						<input name="endDate" id="endDate" class="easyui-datebox" editable="false"/>
					</td>
                  <td colspan="3"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
                   onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
                  >重置</a></td>
                 </tr>
                </table>
			</div>
    </form>

     <!-- 操作按钮 -->
	    <div style="padding-bottom: 5px">
	    	<shiro:hasAnyRoles name="REFUND_INTEREST,ALL_BUSINESS">
	    	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportDifferWarn()">导出</a>
	    	</shiro:hasAnyRoles>
	    </div>

   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="退利息统计" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>reportController/reportList.action?type=2',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: false,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead><tr>
		    <th data-options="field:'pid',checkbox:true" ></th>
		    <th data-options="field:'projectId',sortable:true" align="center" halign="center"  >ID编号</th>
		    <th data-options="field:'projectName',sortable:true,formatter:refundFeeList.formatProjectName" align="center" halign="center"  >项目名称</th>
		    <th data-options="field:'projectNumber',sortable:true" align="center" halign="center"  >项目编号</th>
		    <th data-options="field:'businessSourceStr',sortable:true" align="center" halign="center"  >业务来源</th>
		    <th data-options="field:'innerOrOut',sortable:true,formatter:formatterInnerOrOut" align="center" halign="center">单据类型</th>
		    <th data-options="field:'orgName',sortable:true" align="center" halign="center"  >业务部门</th>
		    <th data-options="field:'pmName',sortable:true" align="center" halign="center"  >客户经理</th>
		    <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方</th>  
		    <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方</th>
		    <th data-options="field:'bizApplyHandleStatus',sortable:true,formatter:formatterBizApplyHandleStatus" align="center" halign="center">业务状态</th>
		    <th data-options="field:'recAccountName',sortable:true" align="center" halign="center">收费账户</th>
		    <th data-options="field:'confirmMoney',sortable:true,formatter:formatMoney" align="center" halign="center">退利息</th>
		    <th data-options="field:'backFeeApplyHandleStatus',sortable:true,formatter:formatterBackFeeApplyHandleStatus" align="center" halign="center">退费状态</th>
		    <th data-options="field:'loanMoney',sortable:true,formatter:formatMoney" align="center" halign="center">担保金额</th>
		    <th data-options="field:'foreclosureFloorMoney',sortable:true,formatter:formatMoney" align="center" halign="center">赎楼金额</th>
		    <th data-options="field:'finInterest',sortable:true,formatter:formatMoney" align="center" halign="center">实收利息</th>
		    <th data-options="field:'bankName',sortable:true" align="center" halign="center"  >开户行</th>
		    <th data-options="field:'confirmDate',sortable:true,formatter:convertDate" align="center" halign="center"  >出款日期</th>
            </tr></thead>
    </table>
   </div>

  </div>
 </div>
</body>
</html>
