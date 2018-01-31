<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>

<div id="main_body" style="margin: 5px;">
<div class="reductionDialog" style="height:120px;">
<table id="detailGrid" title="" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="		
	    rownumbers: true,
	    fitColumns:true,
	    showFooter:true,
	    ctrlSelect:true,
		striped:true,
		checkOnSelect:false">
	<!-- 表头 -->
	<thead><tr>
	    <th data-options="field:'receiveDt',width:160"  >金额类型</th>
	    <c:forEach items="${titls}" var="var">
	        <th data-options="field:'${var.dataStr}',width:200,align:'center'"  >${var.title}</th>
	    </c:forEach>
	</tr>
	</thead>
	<tbody>
	   <tr>
	      <td>逾期利息</td>
	      <c:forEach items="${overdueL}" var="var">
	         <td>
	             <c:if test="${var>0}">
	               <input type="checkbox" onclick="onClickBox(${var},this);" />&nbsp;
	             </c:if>
	             ${var}
	             </td>
	       </c:forEach>
	   <td>
	   <tr>
	      <td>逾期罚息</td>
	       <c:forEach items="${overdueF}" var="var">
	         <td>
	             <c:if test="${var>0}">
	               <input type="checkbox" onclick="onClickBox(${var},this);" />&nbsp;
	             </c:if>
	             ${var}
	          </td>
	       </c:forEach>
	   <td>
	</tbody>
</table>
</div>  
        <div style="text-align: left;margin-left: 200px;margin-top: 10px;">
	        <div>减免金额:<input type="text"  class="text_style" id="theReductionAmt" name=""/>
	        </div>
	        <div>减免备注:<textarea rows="3" cols="50" id="remark"></textarea></div>
        </div>
  	
    <div style="text-align: center;margin-top: margin-top: 10px;">
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="onClickBution();">确定</a>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" onclick="colseTheDialog();">取消</a>
  	</div>
</table>
<script>
function onClickBution()
{
	// 减免的金额
	var theReductionAmt = Number($("#theReductionAmt").val());
	var maxAmt = Number("${maxAmt}");
	// 减免超过最大值	
	if(theReductionAmt>maxAmt)
	{
		$.messager.alert("提示","减免金额最多不能超过"+maxAmt,"info");
		$("#theReductionAmt").val(maxAmt);
	}
	else
	{
		window.closeReductionDialog();
	}	
}

function colseTheDialog()
{
	$("#theReductionAmt").val(0);
	window.closeReductionDialog();
}

</script>
  
</div>	

<script type="text/javascript">
$(function(){
	$('#theReductionAmt').val(0.0);
	$("#remark").val("");
});
function submitReconciliation(){
}
 function onClickBox(amt,checkBox)
 {
	 var reductionAmt = Number($("#theReductionAmt").val());
	 if(checkBox.checked)
	 {
		 reductionAmt = reductionAmt + Number(amt);
	 }
	 else
     {
		 reductionAmt = reductionAmt - Number(amt);
	 }		 
  		 
	 $("#theReductionAmt").val(reductionAmt);
	 $("#theReductionAmt").attr('value',reductionAmt);
 }
</script>