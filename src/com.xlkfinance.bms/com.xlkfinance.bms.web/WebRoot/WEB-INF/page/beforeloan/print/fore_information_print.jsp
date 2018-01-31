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
<title>打印赎楼资料交接单</title>

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
   <div title="赎楼资料交接单" style="padding: 10px;">
    <form action="#" id="printForm" name="printForm" method="post">
    <div id="myPrintArea">
    <style type="text/css">
		.print{width: 100%; text-align:center; padding:5px 0 15px 0;}
		.print_id{width: 100%;font-size: 14px;height:30px;  clear:both;line-height:30px;padding-bottom:10px;}
		.print_id b{width: 50%; display: inline-block; }
		.print_id span{ display: inline-block; width: 50%;text-align:right;}
		.table_css th{ background:#eee;padding: 7px 5px;font-size: 12px;border-bottom: 1px #ddd solid;border-right: 1px #ddd solid;}
		.table_css tr{ background:#fff;}
		
		.table_css td {padding: 7px 5px;font-size: 12px;border-bottom: 1px #ddd solid;border-right: 1px #ddd solid;
		}
	</style>
    <div class="print bt"><b style="font-size: 18px; font-family: 'Microsoft YaHei', '微软雅黑';">赎楼资料交接单</b></div>
	<div class="print_id"><b>一、业务基本信息表</b><span>项目名称：${project.projectName}</span></div>
	<table border="0" cellpadding="0" cellspacing="0" class="table_css" style="width: 100%; clear:both; border-top: 1px #ddd solid; border-left: 1px #ddd solid;">
      <tr>
       <td width="100" align="center"><b>经办人、部门</b></td>
       <td>${project.managerName}(${project.orgName})</td>
       <td width="100" align="center"><b>经办人电话（短号）</b></td>
       <td>${project.managerPhone}</td>
      </tr>
      <tr>
       <td align="center"><b>业主姓名</b></td>
       <td>${project.sellerName}</td>
       <td align="center"><b>业主电话</b></td>
       <td>${project.sellerPhone}</td>
      </tr>
      
      <tr>
       <td align="center"><b>物业名称：</b></td>
       <td colspan="3">${project.houseName}</td>
      </tr>
      <tr>
       <td align="center"><b>放款金额：</b></td>
       <td>${project.loanMoneyStr}</td>
       <td align="center"><b>赎楼银行：</b></td>
       <td>${project.foreBank}</td>
      </tr>
       <tr>
       <td align="center"><b>申请还款日期：</b></td>
       <td>&nbsp;</td>
       <td align="center"><b>注销人：</b></td>
       <td>&nbsp;</td>
      </tr>
      
      <tr>
       <td align="center"><b>是否预存，期限</b></td>
       <td>&nbsp;</td>
       <td align="center"><b>是否罚息，期限</b></td>
       <td>&nbsp;</td>
      </tr>
      <tr>
       <td align="center"><b>银行联系人</b></td>
       <td>${project.bankUser}</td>
       <td align="center"><b>银行联系电话</b></td>
       <td>${project.bankPhone}</td>
      </tr>
     </table>
     
     <div class="print_id" style="margin-top:25px;"><b>二、赎楼资料清单</b></div>
<table border="0" cellpadding="0" cellspacing="0" class="table_css" style="width: 100%; border-top: 1px #ddd solid; border-left: 1px #ddd solid;">
      <tr>
       <td width="30" rowspan="2" align="center"><b>序号</b></td>
       <td rowspan="2" align="center"><b>资料名称</b></td>
       <td colspan="2" align="center"><b>赎楼前（选项处填入“√”）</b></td>
       <td width="170" rowspan="2" align="center"><b>备注</b></td>
      </tr>
      <tr>
        <td align="center" width="85"><b>原件</b></td>
        <td align="center" width="85"><b>复印件</b></td>
      </tr>
      <c:forEach items="${foreInformations}" var="item" varStatus="status">
      	<tr>
       <td align="center">${status.index+1 }</td>
       <td align="center">${item.foreInformationName}</td>
       <td align="center">${item.originalNumber}</td>
       <td align="center">${item.copyNumber}</td>
       <td>${item.remark}</td>
      </tr>
      </c:forEach>
     </table>
     
     <table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px; margin-top:15px;">
	  <tr>
	    <td width="30" rowspan="2" valign="top" style="padding-top:8px">注：</td>
	    <td style="padding:0 10px 10px 10px
	    ; line-height:30px;" valign="top">1、如若身份证有变更的，还需提供户籍管理机构开出的变更证明原件及复印件，台湾、香港同胞则还需提供回乡证复印件。香港人需同时提供身份证及回乡证，台湾的提供台胞证</td>
	  </tr>
	  <tr>
	    <td style="padding:0 10px"> 2、同行赎楼：资监协议、资监到帐原件、介绍函原件。</td>
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
