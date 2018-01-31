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
	function cxportDifferWarn() {
			$.ajax({
					url : BASE_PATH + 'templateFileController/checkFileUrl.action',
					data : {templateName :'TZXFTJ'},
					dataType : 'json',
					success : function(data) {
						if (data == 1) {
							var projectName =$("#projectName").textbox('getValue');
							var businessSourceStr =$("#businessSourceStr").textbox('getValue');
							var pmUserName =$("#pmUserName").textbox('getValue');
							window.location.href = "${basePath}reportController/refundFeesExportList.action?projectName="+projectName+"&businessSourceStr="+businessSourceStr+"&pmUserName="+pmUserName+"&page=-1";
						} else {
							alert('收退咨询费汇总报表的导出模板不存在，请上传模板后重试');
						}
					}
				})
	}
	var refundFeesReportList = {
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
				return va;
			}
		}
	
	 function formatRealLoanDays(val,row) {
    	var startDate= row.loanDate;
    	var endDate= row.recLoanDate;
    	if (startDate !="" && endDate!=""&& startDate !=null && endDate!=null) {
    		val = dateDiffIncludeToday(startDate, endDate);
			}else{
				val = "";
			}
    	return val;
	} 
	
	//初始化
	$(document).ready(function() {
		$("#grid").datagrid('hideColumn','isChechan');
		$('#grid').datagrid({  
	    	//当列表数据加载完毕
	        onLoadSuccess:function(data){  
	        	//备注提示
	        	$(".note").tooltip({
	                 onShow: function(){
	                        $(this).tooltip('tip').css({ 
	                            width:'300',
	                            boxShadow: '1px 1px 3px #292929'                        
	                        });
	                  }
	             })
	        },
	        rowStyler:function(index,row){    
	            if (row.isChechan==1){    
	                return 'background-color:#FFAF4C;';    
	            }    
	        } 
	    }) ;
	});
</script>
<title>收退咨询费报表</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/refundFeesIndexReportList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->								
     <div style="padding: 5px">
      <table>
       <tr>
      <td width="80" align="right">项目名称：</td>
       <td>
        <input class="easyui-textbox"   name="projectName" id="projectName"/>
       </td>
        <td width="80" align="right">业务来源：</td>
        <td>
        <input class="easyui-textbox" name="businessSourceStr" id="businessSourceStr" />
       </td>
       <td width="80" align="right">经办人：</td>
        <td>
        <input class="easyui-textbox" name="pmUserName" id="pmUserName" />
       </td>
       </tr>
<!--        <tr> -->
<!-- 	       	<td align="right" height="28" width="120">收咨询费时间：</td> -->
<!-- 	        <td colspan="3"> -->
<!-- 		        <input class="easyui-datebox" editable="false" id="collectFeesDate" name="collectFeesDate" /> -->
<!-- 		        		 至  -->
<!-- 		        <input class="easyui-datebox" editable="false" id="collectFeesEndDate" name="collectFeesEndDate"/> -->
<!-- 	        </td> -->
<!--         	<td align="right" height="28" width="120">退咨询费时间：</td> -->
<!-- 	        <td colspan="3"> -->
<!-- 		        <input class="easyui-datebox" editable="false" name="confirmDate" id="confirmDate"/> -->
<!-- 		         			至 -->
<!-- 		        <input class="easyui-datebox" editable="false" name="confirmEndDate" id="confirmEndDate"/> -->
<!-- 	        </td> -->
<!--        </tr> -->
       <tr>
        <td colspan="10"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>

      </table>
     </div>
    </form>
    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
    <shiro:hasAnyRoles name="EXPORT_REFUND_FEES_INDEX,ALL_BUSINESS">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportDifferWarn()">导出</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="收退咨询费列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>reportController/refundFeesIndexReportList.action',
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
      		<th data-options="field:'projectName',formatter:refundFeesReportList.formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
		 </tr>
	 </thead>
     <thead>
      <tr>
       <th data-options="field:'isChechan',sortable:true" align="center" halign="center">是否撤单</th>
       <th data-options="field:'projectNumber',sortable:true" align="center" halign="center">项目编号</th> 
       <th data-options="field:'businessSourceStr',sortable:true" align="center" halign="center">业务来源</th>
       <th data-options="field:'pmUserName',sortable:true" align="center" halign="center">经办人</th>
       <th data-options="field:'chinaName',sortable:true" align="center" halign="center">借款人名称</th>
       <th data-options="field:'loanAmount',sortable:true,formatter:formatMoney" align="center" halign="center">借款金额</th>
       <th data-options="field:'loanDate',sortable:true,formatter:convertDate" align="center" halign="center">放款日期</th>
       <th data-options="field:'loanDays',sortable:true" align="center" halign="center">借款期限</th>
       <th data-options="field:'recLoanDate',sortable:true,formatter:convertDate" align="center" halign="center">客户回款日期</th>
       <th data-options="field:'recMoney',sortable:true,formatter:formatMoney" align="center" halign="center">客户回款金额</th>
       
       
       <th data-options="field:'realLoanDays',sortable:true,formatter:formatRealLoanDays" align="center" halign="center">实际用款天数</th>
       <th data-options="field:'poundage',sortable:true,formatter:formatMoney" align="center" halign="center">预收手续费</th>
       <th data-options="field:'planConsultingMoney',sortable:true,formatter:formatMoney" align="center" halign="center">应收咨询费费</th>
       <th data-options="field:'returnFee',sortable:true,formatter:formatMoney" align="center" halign="center">收咨询费</th>
       <th data-options="field:'collectFeesDate',sortable:true,formatter:convertDate" align="center" halign="center">收咨询费日期</th>
       <th data-options="field:'confirmMoney',sortable:true,formatter:formatMoney" align="center" halign="center">退咨询费</th>
       
       <th data-options="field:'confirmDate',sortable:true,formatter:convertDate" align="center" halign="center">退咨询费日期</th>
       <th data-options="field:'consultingfeeRate',sortable:true,formatter:formatMoney" align="center" halign="center">月咨询费率</th>
       <th data-options="field:'extensionFee',sortable:true,formatter:formatMoney" align="center" halign="center">应收展期费</th>
       <th data-options="field:'acExtensionFee',sortable:true,formatter:formatMoney" align="center" halign="center">收展期费</th>
       <th data-options="field:'recInterestDate',sortable:true,formatter:convertDate" align="center" halign="center">收展期费日期</th>
       <th data-options="field:'extensionDays',sortable:true" align="center" halign="center">展期天数</th>
       <th data-options="field:'extensionFeeRate',sortable:true,formatter:formatMoney" align="center" halign="center">展期费率</th>
      </tr>
     </thead>
    </table>
   </div>
  </div>
 </div>
</body>
</html>
