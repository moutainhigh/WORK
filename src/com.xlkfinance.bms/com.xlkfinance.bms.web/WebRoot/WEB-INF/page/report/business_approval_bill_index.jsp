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
	
	function cxportForeclosure() {
			$.ajax({
					url:BASE_PATH+'templateFileController/checkFileUrl.action',
					data:{templateName:'SLDYWTJ'},
					dataType:'json',
					success:function(data){
						if(data==1){
							var mothOrWekNum = $('input[name="chooseMonthOrWeek"]:checked').val();
							if((mothOrWekNum =='' || mothOrWekNum ==null) && mothOrWekNum !=2){
								mothOrWekNum = 0
							}
							var fromDate =$("#fromDate").textbox('getValue');
							var endDate =$("#endDate").textbox('getValue');
							window.location.href ="${basePath}reportController/businessApprovalBillExportList.action?chooseMonthOrWeek="+mothOrWekNum+"&fromDate="+fromDate+"&endDate="+endDate+"&page=-1";
						}else{
							alert('赎楼贷业务统计的导出模板不存在，请上传模板后重试');
						}
					}
				})
	}

	//给后台选择日，周，月赋值
	function assignMents(val)
	{
		document.getElementsByName('chooseMonthOrWeek').value=val;
	}
	changeToDay = function(key){
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset');
		document.getElementById('radioMonthBtn').checked=false;
		document.getElementById('radioWeekBtn').checked=false;
		document.getElementById('radioDayBtn').checked=true;
		var value = document.getElementById("radioDayBtn").value;
		assignMents(value);
	}
	
	changeToWeek = function(key){
//		debugger;
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset');
		document.getElementById('radioMonthBtn').checked=false;
		document.getElementById('radioDayBtn').checked=false;
		document.getElementById('radioWeekBtn').checked=true;
		var value = document.getElementById("radioWeekBtn").value;
		assignMents(value);
	}
	
	changeToMonth = function(key){
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset');
		document.getElementById('radioWeekBtn').checked=false;
		document.getElementById('radioDayBtn').checked=false;
		document.getElementById('radioMonthBtn').checked=true;
		
		var value = document.getElementById("radioMonthBtn").value;
		assignMents(value);
	}

  function onSelect(date) {
	  var endDayStr = $("#endDay").datebox("getValue");
    	if(""!= endDayStr && null != input_show){
    		$("#input_show").val(endDayStr);
    	}else{
  		 var now = new Date();
  	        var year = now.getYear();
  	        var month = now.getMonth();
  	        var day = now.getDate();
  	        var time_str = "2" + year + "-" + month + "-" + day;
  	        document.getElementById('input_show').value=time_str;
//   	        //一秒刷新一次显示时间
//   	        var timeID = setTimeout(showLeftTime, 5000);
  	}
  }
	 //增加连接
	function searchBillCount(value, row, index) {
		var rows = $('#grid').datagrid("getRows");
		var mothOrWekNum = $('input[name="chooseMonthOrWeek"]:checked').val();
		
		if((mothOrWekNum =='' || mothOrWekNum ==null) && mothOrWekNum !=2){
			mothOrWekNum = 0
		}
			var va="<a href='javascript:void(0)' onclick = 'formatterBillOverView.disposeClick(\""+row.dateId+"\","+mothOrWekNum+",\""+row.approvalPerson+"\",\""+row.approvalStep+"\")'> <font color='blue'>"+row.totalCount+"</font></a>"
			return va;
	}
	
	var formatterBillOverView={
			// 业务笔详情
			disposeClick:function (dateId,mothOrWekNum,approvalPerson,approvalStep) {
				var url = "";
				url = BASE_PATH+'reportController/getBillOverView.action?dateId='+dateId+'&mothOrWekNum='+mothOrWekNum+'&approvalPerson='+approvalPerson+'&approvalStep='+approvalStep;
				parent.openNewTab(url,"业务审批台账业务笔数详情",true);
			}
		}
</script>
<title>业务审批台账统计</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/businessApprovalBillList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
		 <table>
		   <tr>
		    <td width="80" align="right" height="28">请选择：</td>
		   		    <td>
			    <input type="radio" value="0" id = "radioMonthBtn" name="chooseMonthOrWeek"  onclick="changeToMonth(this)" checked = "checked"/>按月
				<input type="radio" value="1" id = "radioWeekBtn" name="chooseMonthOrWeek"  onclick="changeToWeek(this)"/>按周
         		<input type="radio" value="2" id = "radioDayBtn" name="chooseMonthOrWeek"  onclick="changeToDay(this)"/>按日
				
				<input id="fromDate" name="fromDate" editable="false" class="easyui-datebox" />
				<span id="radioDaySpanBtn">至</span>
				<input id="endDate" name="endDate" editable="false" class="easyui-datebox"  data-options="onSelect:onSelect"/>
			</td>
		    <td colspan="8">
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-search"  onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> 
		    	<a href="#" onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;" >重置</a>
		    </td>
		  </tr>
      	</table>
     </div>
    </form>
    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
    <shiro:hasAnyRoles name="EXPORT_BUSINESS_APPROVAL_BILL,ALL_BUSINESS">
     <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportForeclosure()">导出</a>
     </shiro:hasAnyRoles>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">

    <table id="grid" title="业务审批台账" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>reportController/businessApprovalBillList.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: false,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'dateId',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'dateId',sortable:true" align="center" halign="center">统计周期</th>
       <th data-options="field:'department',sortable:true" align="center" halign="center">部门</th>
       <th data-options="field:'approvalPerson',sortable:true" align="center" halign="center">操作人</th>
       <th data-options="field:'approvalStep',sortable:true" align="center" halign="center">操作节点</th>
       <th data-options="field:'totalCount',sortable:true,formatter:searchBillCount" align="center" halign="center">业务笔数</th>
       <th data-options="field:'totalMoney',sortable:true,formatter:formatMoney" align="center" halign="center">业务金额</th>
      </tr>
      </thead>	
    </table>
   </div>
  </div>
 </div>
</body>
</html>
