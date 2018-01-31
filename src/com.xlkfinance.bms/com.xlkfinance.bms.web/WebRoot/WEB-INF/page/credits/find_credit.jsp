<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">

</script>
<div  style="width:780px;float:left">
<table class="cus_table2">
	<input type="hidden" id="usableLimit" value="${credit.usableLimit }" />
	<input type="hidden" id="freezeLimit" value="${credit.freezeLimit }" />
	<tr style="height:25px;">
		<th colspan="3">授信项目信息</th>
	</tr>
  <tr>
    <td class="align_right">项目名称：</td>
	<td>${credit.projectName }</td>
	<td rowspan="2" style="width:150px;"><input type="button" class="cus_save_btn"  value="查看授信信息" onclick="creditDetail();"/></td>
  </tr>
  <tr>
    <td class="align_right" >项目编号：</td>
	<td>
		${credit.projectId }
	</td>
  </tr>
  <tr>
    <td colspan="3" style="padding:5px;" class="find_credit">
    	<table class="cus_table2" style="width:750px;min-width:750px;">
		  <tr>
	            <td width="20%" align="center">额度种类</td>
	            <td width="20%" align="center">总额度</td>
	            <td width="10%" align="center">已用额度</td>
	            <td width="10%" align="center">冻结额度</td>
	            <td width="10%" align="center">可用额度</td>
	            <td width="15%" align="center">授信开始时间</td>
	            <td width="15%" align="center">授信结束时间</td>
		  </tr>
		  <tr>
		    <td align="center">${credit.isHoopVal }</td>
            <td align="center">${credit.creditAmt }</td>
            <td align="center">${credit.useLimit }</td>
            <td align="center">${credit.freezeLimit }</td>
            <td align="center">${credit.usableLimit }</td>
            <td align="center">${credit.startDt }</td>
            <td align="center">${credit.endDt }</td>
		  </tr>
		</table>
    </td>
  </tr>
</table>

 </form>
 </div>	    
