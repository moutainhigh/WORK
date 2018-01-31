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
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset')
	}

	function formatterInnerOrOut(val, row) {
		if (val == 1) {
			return "内单";
		} else if (val == 2) {
			return "外单";
		}else {
			return "外单";
		}
	}
	function formatterPaymentType(val, row) {
		if (val == 1) {
			return "按揭";
		} else if (val == 2) {
			return "组合贷";
		}else if (val == 3) {
			return "公积金贷";
		}else if (val == 4) {
			return "一次性付款";
		}else if (val == 5) {
			return "经营贷";
		}else if (val == 6) {
			return "消费贷";
		}
	}
	function cxportDifferWarn() {
		
		$.ajax({
					url : BASE_PATH
							+ 'templateFileController/checkFileUrl.action',
					data : {
						templateName : 'CHECHAN'
					},
					dataType : 'json',
					success : function(data) {
						if (data == 1) {
						  	var projectName =$("#projectName").textbox('getValue');
					        var applyName =$("#applyName").textbox('getValue');
					        var deptName =$("#deptName").textbox('getValue');
					        var pmUserName =$("#pmUserName").textbox('getValue');
					        var sellerName =$("#sellerName").textbox('getValue');//5
					        
					        var buyerName =$("#buyerName").textbox('getValue');
							var newLoanBank =$("#newLoanBank").combobox('getValue');
							var loanAmt =$("#loanAmt").numberbox('getValue'); 
					        var loanAmtMax =$("#loanAmtMax").numberbox('getValue');//10
							window.location.href ="${basePath}reportController/chechanExcelExportList.action?projectName="+projectName+"&applyName="+applyName+"&deptName="+deptName+"&pmUserName="+pmUserName+"&sellerName="+sellerName+
									"&buyerName="+buyerName+"&newLoanBank="+newLoanBank+"&loanAmt="+loanAmt+"&loanAmtMax="+loanAmtMax+"&page=-1";
							
						} else {
							alert('撤单报表的导出模板不存在，请上传模板后重试');
						}
					}
				})
	}
	//var newBank = "${project.projectForeclosure.newLoanBank}";
	$(document).ready(function(){
		setBankCombobox("newLoanBank",0,"-1");
		$('#grid').datagrid({  
        	//当列表数据加载完毕，则初始化办理动态条数
            onLoadSuccess:function(dynamicName){  
            	//备注提示
            	$(".note").tooltip({
                     onShow: function(){
                            $(this).tooltip('tip').css({ 
                                width:'300',
                                boxShadow: '1px 1px 3px #292929'                        
                            });
                      }
                 })
            }
        }) ;
	});

	var chechanReportList = {
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
				return va;
			},
			formatTooltip:function(value, row, index){
				if (value==null) {
					return "";
				}
		        var abValue = value;
	            if (value.length>=12) {
	               abValue = value.substring(0,10) + "...";
	            }
				var va = '<a href="#" title="' + value + '" class="note">' + abValue + '</a>';
	            return va;
			}
		}
	//业务回撤
	function retreatProjectInfo(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
	 		var projectId = row[0].projectId;
	 		var flag = true;
	 		//判断项目是否已回撤
			$.ajax({
				type: "POST",
		    	url : BASE_PATH+"beforeLoanController/checkRetreatProject.action?projectId="+projectId,
				async:false, 
			    success: function(result){
			    	if(result == 1){
			    		bool = true;
			    	}else{
			    		bool = false;
			    	}
				}
			});
			if(!bool){
				$.messager.confirm("操作提示","确认回撤选择的数据?",
						function(r) {
				 			if(r){
								$.post("<%=basePath%>beforeLoanController/retreatsProject.action",{projectId : projectId}, 
									function(result) {
										//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
										if(result && result.header["success"]){
											$.messager.alert('操作提示',"回撤成功!");	
											// 重新加载列表信息
						 					$("#grid").datagrid('load');
						 					// 清空所选择的行数据
						 					clearSelectRows("#grid");
										}else{
											$.messager.alert('操作提示',ret.header["msg"],'error');	
										}
									},'json');
				 			}
						});
			}else{
				$.messager.alert('操作提示',"该业务申请已回撤，请勿重复操作!",'error');
			}
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选择一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
</script>
<title>撤单统计报表</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/chechanReportList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td width="80" align="right" height="28">项目名称：</td>
        
        <td><input class="easyui-textbox" name="projectName" id="projectName"/></td>
        <td width="80" align="right" height="28">申请人：</td>
        <td><input class="easyui-textbox" name="applyName" id="applyName"/></td>
       
       </td>
        <td width="100" align="right" height="28">业务部门：</td>
        <td><input class="easyui-textbox" name="deptName" id="deptName"/></td>
     
       </tr>
       <tr>
        <td width="100" align="right" height="28">业务经理：</td>
        <td><input class="easyui-textbox" name="pmUserName"  id="pmUserName"/></td>
        <td width="100" align="right" height="28">卖方：</td>
        <td colsapn="2"><input class="easyui-textbox" name="sellerName" id="sellerName"/></td>
        <td width="100" align="right" height="28">买方：</td>
        <td colsapn="2"><input class="easyui-textbox" name="buyerName" id="buyerName" /></td>
         
       </tr>
       <tr>
       <td width="100" align="right" height="28"></font>新贷款银行：</td>
		<td>
			<select id="newLoanBank" class="easyui-combobox"   id="newLoanBank" name="newLoanBank" editable="false" style="width:129px;" />

		</td>
        <td width="80" align="right" height="28">贷款金额：</td>
        <td colspan="3"><input class="easyui-numberbox" name="loanAmt" id="loanAmt"/>
       
        至 <input class="easyui-numberbox" name="loanAmtMax" id="loanAmtMax"/></td>
       
       </tr>
       <tr>
        <td colspan="14"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>

      </table>
     </div>
    </form>
    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
    <shiro:hasAnyRoles name="CHE_CHAN_REPORT,ALL_BUSINESS">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportDifferWarn()">导出</a>
     </shiro:hasAnyRoles>
     
     <shiro:hasAnyRoles name="RETREAT_PROJECT_INFO,ALL_BUSINESS">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-setting" plain="true" onclick="retreatProjectInfo()">启用</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="撤单统计列表"  style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>reportController/chechanReportList.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: false,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'projectId',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead data-options="frozen:true">
		<tr>
		 	<th data-options="field:'projectId',checkbox:true"></th>
       		<th data-options="field:'projectName',formatter:chechanReportList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <thead>
      <tr>
       <!-- <th data-options="field:'projectId',checkbox:true"></th>
       <th data-options="field:'projectName',formatter:chechanReportList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th> -->
      <!--  <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th> -->
       <th data-options="field:'businessSourceStr',sortable:true" align="center" halign="center">业务来源</th>
       <th data-options="field:'innerOrOut',formatter:formatterInnerOrOut,sortable:true" align="center" halign="center">单据类型</th>
       <th data-options="field:'productName',sortable:true" align="center" halign="center">产品名称</th>
       <th data-options="field:'chechanUser',sortable:true" align="center" halign="center">撤单用户</th>
       <th data-options="field:'chechanDate',sortable:false" align="center" halign="center">撤单日期</th>
       <th data-options="field:'chechanCause',formatter:chechanReportList.formatTooltip,sortable:true,width:100" align="center" halign="center">撤单原因</th>
       <th data-options="field:'loanAmt',sortable:true,formatter:formatMoney" align="center" halign="center">贷款金额</th>
       <th data-options="field:'deptName',sortable:true" align="center" halign="center">业务部门</th>
       <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">业务经理</th>
       <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方</th>
       <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方</th>
       <th data-options="field:'applyName',sortable:true" align="center" halign="center">申请人</th>
       <th data-options="field:'interest',sortable:true,formatter:formatMoney" align="center" halign="center">咨询费</th>
       <th data-options="field:'recInterestDate',sortable:false" align="center" halign="center">收费日期</th>
       <th data-options="field:'paymentType',sortable:true,formatter:formatterPaymentType" align="center" halign="center">贷款方式</th>
       <th data-options="field:'oldLoanBank',sortable:true" align="center" halign="center">原按揭银行</th>
       <th data-options="field:'newLoanBank',sortable:true" align="center" halign="center">新贷款银行</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
