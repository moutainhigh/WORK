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
					data:{templateName:'ZJYWTJL'},
					dataType:'json',
					success:function(data){
						if(data==1){
							debugger;
							var fromDate =$("#fromDate").textbox('getValue');
							var endDate =$("#endDate").textbox('getValue');
							var mothOrWekNum = $('input[name="chooseMonthOrWeek"]:checked').val();
							if((mothOrWekNum =='' || mothOrWekNum ==null) && mothOrWekNum != 2){
								mothOrWekNum = 0
							}
							
							window.location.href ="${basePath}reportController/foreclosureCapExportList.action?fromDate="+fromDate+"&endDate="+endDate+"&chooseMonthOrWeek="+mothOrWekNum+"&page=-1";
						}else{
							alert('资金方业务统计的导出模板不存在，请上传模板后重试');
						}
					}
				})
	}
	
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

	//formatMoney
	function formatMoy(value,row,index){
		if(value){
			return accounting.formatMoney(value/10000, "", 2, ",", ".");
		}else{
			return "-";
		}
	}
	function formatMoney(value,row,index){
		if(value != '--'){
			value = Number(value);
		}
		if(value){
			return accounting.formatMoney(value/10000, "", 2, ",", ".");
		}else{
			return "-";
		}
	}
	// 截止日期赋值
	 function onSelect(date) {
		  	var endDayStr = $("#endDate").datebox("getValue");
		  	if(""!= endDayStr && null != input_show){
		  		$("#input_show").val(endDayStr);
		  	}
		  }
	
	//新增笔数详情
	function searchNewCount(value, row, index) {
	  var mothOrWekNum = $('input[name="chooseMonthOrWeek"]:checked').val();
		if((mothOrWekNum =='' || mothOrWekNum ==null) && mothOrWekNum !=2){
			mothOrWekNum = 0
		}
		if(row.reMonth == "合计" || row.reMonth == "总计"){
			var va=row.newCount;
		}else{
			var va="<a href='javascript:void(0)' onclick = \"formatterCountOverView.disposeClick('"+row.reMonth+"',"+1+","+mothOrWekNum+",'"+row.orgName+"')\"> <font color='blue'>"+row.newCount+"</font></a>"
		}																													
		return va;
	}
	 //结清笔数详情
	function searchSquareCount(value, row, index) {
		var mothOrWekNum = $('input[name="chooseMonthOrWeek"]:checked').val();
		if((mothOrWekNum =='' || mothOrWekNum ==null) && mothOrWekNum !=2){
			mothOrWekNum = 0
		}
		if(row.reMonth == "合计" || row.reMonth == "总计"){
			var va=row.squareCount;
		}else{
			var va="<a href='javascript:void(0)' onclick = \"formatterCountOverView.disposeClick('"+row.reMonth+"',"+2+","+mothOrWekNum+",'"+row.orgName+"')\"> <font color='blue'>"+row.squareCount+"</font></a>"
		}
		return va;
	}
	 //在途笔数详情
	function searchIngCount(value, row, index) {
		var mothOrWekNum = $('input[name="chooseMonthOrWeek"]:checked').val();
		if((mothOrWekNum =='' || mothOrWekNum ==null) && mothOrWekNum !=2){
			mothOrWekNum = 0
		}
		if(row.reMonth == "合计" || row.reMonth == "总计"){
			var va=row.ingCount;
		}else{
			var va="<a href='javascript:void(0)' onclick = \"formatterCountOverView.disposeClick('"+row.reMonth+"',"+3+","+mothOrWekNum+",'"+row.orgName+"')\"> <font color='blue'>"+row.ingCount+"</font></a>"
		}
		return va;
	}
	 
	var formatterCountOverView={
			// 新增or结清or在途or累计详情
			disposeClick:function (reMonth,type,mothOrWekNum,orgName) {
				if(type == 1){
					url = BASE_PATH+'otherReportController/getCapDetailsView.action?reMonth='+reMonth+'&orgName='+orgName+'&mothOrWekNum='+mothOrWekNum+'&type=1'
					parent.openNewTab(url,"资金方新增笔数详情",true);
				}else if(type == 2) {
					url = BASE_PATH+'otherReportController/getCapDetailsView.action?reMonth='+reMonth+'&orgName='+orgName+'&mothOrWekNum='+mothOrWekNum+'&type=2'
					parent.openNewTab(url,"资金方结清笔数详情",true);
				}else{
					url = BASE_PATH+'otherReportController/getCapDetailsView.action?reMonth='+reMonth+'&orgName='+orgName+'&mothOrWekNum='+mothOrWekNum+'&type=3'
					parent.openNewTab(url,"资金方在途笔数详情",true);
				}
			}
		}
	
</script>
<title>赎楼贷业务统计</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>reportController/foreclosureOrganizationReportList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
		 <table>
		   <tr>
		    <td width="80" align="right" height="28">请选择：</td>
		    <td>
			    <input type="radio" value="0" id = "radioMonthBtn" name="chooseMonthOrWeek"  onclick="changeToMonth(this)" checked = "checked"/>按月
				<input type="radio" value="1" id = "radioWeekBtn" name="chooseMonthOrWeek"  onclick="changeToWeek(this)"/>按周
         		<input type="radio" value="2" id = "radioDayBtn" name="chooseMonthOrWeek"  onclick="changeToDay(this)"/>按日
				
				<input id="fromDate" name="fromDate" editable="false" class="easyui-datebox"/>
				<span id="radioDaySpanBtn">至</span>
				<input id="endDate" name="endDate" editable="false" class="easyui-datebox" data-options="onSelect:onSelect"/>
			</td>
			<td width="80" align="right">资金方：</td>
			<td>
				<input class="easyui-textbox" name="orgName" id="orgName" />
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
    <shiro:hasAnyRoles name="EXPORT_FORECLOSURE_CAPITAL,ALL_BUSINESS">
    	 <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportForeclosure()">导出</a>
     </shiro:hasAnyRoles>
        <span style="font-size:13px;font-weight:bold;  float: right; padding-right:300px;" >截止至<input style="font-size:13px;font-weight:bold;" size="10" id='input_show' type="text" value="输入截止日期；" readonly="readonly"/>&nbsp单位：万元</span>
    </div>
   </div>
   <div class="dksqListDiv" style="height: 100%;">

    <table id="grid" title="资金业务量统计列表"  class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>reportController/foreclosureCapitalReportList.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: false,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'rePaymentMonthId',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
 		<tr>
	       <th rowspan="2" align="center" style="display: none;" data-options="field:'reMonth',sortable:true" halign="center">统计周期</th>
	       <th rowspan="2" align="center" data-options="field:'orgName',sortable:true" halign="center">资金方</th>
	       <th colspan="2" align="center" data-options="field:'',sortable:true" halign="center">已放款</th>
	       <th colspan="2" align="center" data-options="field:'',sortable:true" halign="center">已还款</th>
	       <th colspan="2" align="center" data-options="field:'',sortable:true" halign="center">未还款（有史以来）</th>
	       <th colspan="2" align="center" data-options="field:'',sortable:true" halign="center">累计（有史以来）</th>
      	</tr>
      
	      <tr>
	        <th align="center" data-options="field:'newCount',formatter:searchNewCount,sortable:true" halign="center">笔数</th>
	        <th align="center" data-options="field:'newMoney',formatter:formatMoy,sortable:true" halign="center">金额</th>
	        <th align="center" data-options="field:'squareCount',formatter:searchSquareCount,tsortable:true" halign="center">笔数</th>
	        <th align="center" data-options="field:'squareMoney',formatter:formatMoy,sortable:true" halign="center">金额</th>
	        <th align="center" data-options="field:'ingCount',formatter:searchIngCount,sortable:true" halign="center">笔数</th>
	        <th align="center" data-options="field:'ingMoney',formatter:formatMoy,sortable:true" halign="center">余额</th>
	        <th data-options="field:'totalCount'" align="center" halign="center">笔数</th>
       		<th data-options="field:'totalMoney',formatter:formatMoney" align="center" halign="center">金额</th>
	      </tr>
     </thead> 
    </table>
   </div>
  </div>
 </div>

</body>
</html>
