<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<body class="easyui-layout" style="overflow: hidden;">
<div data-options="region:'center',border:false">
<!-- 项目基本信息  --> 
<div class=" easyui-panel" title="项目基本信息" data-options="collapsible:true">	
<table  class="cus_table"  style="width:700px;min-width:720px;margin-bottom: 0px;border:none;">
    <tr>
     <input type="hidden" name="pid" value=""/>
	 <td class="label_right" style="width: 150px;">项目名称：
</td>
	 <td><a href="javascript:void(0)" onclick="formatterProjectName.disposeClick(${bean.projectId},'${bean.projectNumber}')"><font color="blue">${bean.projectName}</font></a>
	 </td>
	 <td class="label_right">项目编号：
</td>
	 <td>${bean.projectNumber}</td>
  </tr>
   <tr >	
	 <td class="label_right">业务类别:</td>
	 <td>${bean.businessCatelog}
		 
	</td>
	 <td class="label_right">业务品种:</td>
	 <td>${bean.businessType}  </td>
	 
  </tr>
  <tr >	
	 <td class="label_right">流程类别:</td>
	 <td>${bean.flowCatelog}</td>
	 <td class="label_right">项目经理:</td>
	 <td>${bean.realName}</td>
	 
  </tr>
  
</table>
</div>
<!-- 还款计划表  --> 
<div class="pt10"></div>
<div class=" easyui-panel" title="还款计划表" data-options="collapsible:true" style="width: 1400px;">	
<%@ include file="../repayment/list_loanCalculate.jsp"%>
</div>
</div>	
</body>