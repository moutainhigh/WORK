<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<%@ include file="/common.jsp"%>
<title>打印审批意见表</title>
<style>/*打印*/
	.print{width: 100%; text-align:center; padding:5px 0 15px 0;}
	.print_id{font-size: 14px; padding-bottom:10px;}
	.table_css th{ background:#eee;padding: 7px 5px;font-size: 12px;border-bottom: 1px #ddd solid;border-right: 1px #ddd solid;}
	.table_css tr{ background:#fff;}
	
	.table_css td {padding: 7px 5px;font-size: 12px;border-bottom: 1px #ddd solid;border-right: 1px #ddd solid;
	}
</style>
</head>
<script type="text/javascript">
/**
 * 关闭窗口
 */
function closeWindow() {
	var tab = parent.$('#layout_center_tabs').tabs('getSelected');
	var index = parent.$('#layout_center_tabs').tabs('getTabIndex', tab);
	parent.$('#layout_center_tabs').tabs('close', index);
}

function print() {
	$("#myPrintArea").printArea();
}

</script>
<head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="审批意见表" style="padding: 10px;">
    <form action="#" id="printForm" name="printForm" method="post">
    <div id="myPrintArea">
    <style>/*打印*/
	.print{width: 100%; text-align:center; padding:5px 0 15px 0;}
	.print_id{font-size: 14px; padding-bottom:10px;}
	.table_css th{ background:#eee;padding: 7px 5px;font-size: 12px;border-bottom: 1px #ddd solid;border-right: 1px #ddd solid;}
	.table_css tr{ background:#fff;}
	
	.table_css td {padding: 7px 5px;font-size: 12px;border-bottom: 1px #ddd solid;border-right: 1px #ddd solid;
	}
	</style>
    	<div class="print bt"><b style="font-size: 18px; font-family: 'Microsoft YaHei', '微软雅黑';">审批意见表</b></div>
		<div class="print_id">项目名称：${project.projectName }</div>
	<table cellpadding="0" cellspacing="0" class="table_css" style="width: 100%; border-top: 1px #ddd solid; border-left: 1px #ddd solid;">
      <tr>
       <td width="100" rowspan="3" align="center"><b>业务类型：</b><br />${project.businessTypeText }</td>
       <td colspan="3" style="border-bottom:none;border-right:none">卖方：${project.projectProperty.sellerName }    </td>
       <td style="border-bottom:none;">买方：${project.projectProperty.buyerName }   </td>
      </tr>
      <tr>
        <td colspan="4" style="border-bottom:none;">物业：${project.projectProperty.houseName }</td>
      </tr>
      <tr>
        <td style="border-right:none">借款金额：${project.projectGuarantee.loanMoney }元</td>
        <td colspan="2" style="border-right:none">天数：${project.projectForeclosure.loanDays }天</td>
        <td>费率: ${project.projectGuarantee.feeRate }%</td>
      </tr>
      <tr>
       <td align="center" height="370"><b>初审意见：</b></td>
       <td colspan="4">${project.auditorOpinion}<span style="border-bottom:none;border-right:none"></span></td>
      </tr>
      
      <tr>
       <td align="center" height="180"><b>复审意见：</b></td>
       <td colspan="4">&nbsp;</td>
      </tr>
      <tr>
       <td align="center" height="170"><b>终审意见：</b></td>
       <td colspan="4">&nbsp;</td>
      </tr>
     </table>
    </div>
    </form>
    <p class="jl">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWindow()">关闭</a>
     <a href="javascript:void(0)" class="easyui-linkbutton" id="biuuu_button" iconCls="icon-print" onclick="print()">打印</a>
    </p>
   </div>
  </div>
 </div>

</body>
</html>
