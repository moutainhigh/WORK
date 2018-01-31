<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>
<script type="text/javascript">

function doSave()
{
	var loanId = 0;//$("#tloanId").value();
	var projectNo = $('#tprojectNo').textbox('getValue');
	var month = $('#tmonth').textbox('getValue')
	var interest = $('#tinterest').numberbox('getValue');
	var mangCost = $('#tmangCost').numberbox('getValue');
	var otherCost = $('#totherCost').numberbox('getValue');
	var theRestCost = $('#ttheRestCost').numberbox('getValue');
	
	var param={'projectNo':projectNo,'loanId':loanId,'month':month,'interest':interest,'mangCost':mangCost,'otherCost':otherCost,'theRestCost':theRestCost}
	
	$.ajax({
		url:BASE_PATH+'financeController/saveUpdateMonthlyReportRecord.action',
		data:param,
		dataType:'json',
		 success: function(data){
		    	if(data.header.success)
		    	{
		    		$.messager.alert("提示","保存成功","success");
		    		$("#updateMonthlyReportRecord").dialog('close');
		    		$("#acctTotalGrid").datagrid('reload');
		    	}
		    	// 保存失败
		    	else
	    		{
		    		$.messager.alert("提示",data.header.msg,"error");
	    		}
			},error : function(result){
				$.messager.alert("提示","保存失败","error");
			}
	});
}



</script>


<form id="baseInfo" action="financeController/addFinanceAcctManager.action" method="POST">
<table class="cus_table" margin-top: 20px;border:none;">
  <tr>
      <input type="hidden"  id="tloanId" value="0" />
	 <td style="width: 80px;"><font color="red">*</font>项目编号：</td>
	 <td><input type="text" class="easyui-textbox" id="tprojectNo"  required="true" missingMessage="清输入项目编号!"  /></td>
	 </td>
  </tr>
   <tr>
	 <td><font color="red">*</font>修正月份：</td>
	 <td><input type="text" class="easyui-textbox" id="tmonth"  required="true" missingMessage="清输入修正月份!"  /> <font color="red">格式如：201501</font></td>
	 </td>
  </tr>
   <tr>
	 <td>贷款利息：</td>
	 <td><input type="text" class="easyui-numberbox" id="tinterest"  data-options="min:0,precision:2,groupSeparator:','" /></td>
	 </td>
  </tr>
   <tr>
	 <td>管理费：</td>
	 <td><input type="text" class="easyui-numberbox" id="tmangCost"  data-options="min:0,precision:2,groupSeparator:','" /></td>
	 </td>
  </tr>
   <tr>
	 <td>其他费用：</td>
	 <td><input type="text" class="easyui-numberbox" id="totherCost"  data-options="min:0,precision:2,groupSeparator:','" /></td>
	 </td>
  </tr>
   <tr>
	 <td>其他收入：</td>
	 <td><input type="text" class="easyui-numberbox" id="ttheRestCost"  data-options="min:0,precision:2,groupSeparator:','" /></td>
	 </td>
  </tr>
</table>
 </form>	    
</div>

