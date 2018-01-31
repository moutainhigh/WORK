//add by yql 项目名称打开详情页
/*function disposeClick(pid,projectName) {
		url = BASE_PATH+"beforeLoanController/addNavigation.action?projectId="+pid+"&param='refId':'"+pid+"','projectName':'"+projectName+"'";
		parent.openNewTab(url,"贷款申请详情",true);
} */
function projectRepay(value, row, index) {
	if(row.pid>0){
		return '<a href="javascript:void(0)" onclick="disposeClick(\''+row.pid+'\',\''+row.projectName+'\')"><font color="blue">'+row.projectName+'</font></a>';
	}
	
}
//add by yql 打开客户的详情页
function custRepayClick(acctId,cusType,comId) {
	if(cusType==1){
		parent.openNewTab(BASE_PATH+'customerController/editPerBase.action?acctId='+acctId+'&flag=1',"个人客户",true);
	}else if (cusType==2){
		parent.openNewTab(BASE_PATH+'customerController/editComBases.action?acctId='+acctId+"&comId="+comId+'&flag=1',"企业客户",true);
	}
		
} 
function custRepay(value, row, index) {
	if(row.pid>0){
		return '<a href="javascript:void(0)" onclick="custRepayClick(\''+row.acctId+'\',\''+row.cusType+'\',\''+row.comId+'\')"><font color="blue">'+row.cusName+'</font></a>';
	}
}
//add by yql 打开合同预览页面
function contractdeite(value, row, index) {
	if(row.contractNo!=null && row.pid>0){
		return '<a class="download" href="javascript:void(0)" onclick="contractReview(\''+row.contractUrl+'\')"><font color="blue">'+row.contractNo+'</font></a>';
	}else if(row.pid==0){
		return'<B>总计(元)</B>';
	}
	

}
/**
 * 查看项目对账信息的页面
 */
function lookupRecord(projectId,loanId){
	  //获取期数，获取及时发送表的主键
	  var recCycleNums="";
      var rows = $('#loanCaculateGrid').datagrid('getSelections');
      var realtimeIds = "";
      if(rows.length==0){
    	  $.messager.alert("提示","请选择查看的数据","error");
    	  return ;
      }
		for(var i=0;i<rows.length;i++){
			if(rows[i].dataType==1 || rows[i].dataType==3){
				if(i==0){
		  			realtimeIds+=rows[i].pid+",";
		  		}else{
		  			realtimeIds+=rows[i].pid+",";
		  		}
			}else if(rows[i].dataType==2 || rows[i].dataType==4){
				if(i==0){
					recCycleNums+=rows[i].planCycleNum+",";
		  		}else{
		  			recCycleNums+=rows[i].planCycleNum+",";
		  		}
				
			}
	  		
	  	}
		
		//查看对账信息
		$('<div id="projectReconciliationListDiv"/>').dialog({
			href : BASE_PATH+'badDebtController/getProjectReconciliation.action?projectId='+projectId+'&loanId='+loanId+'&realtimeIds='+realtimeIds+'&recCycleNums='+recCycleNums,
			width : 800,
			height : 350,
			modal : true,
			title : '查看对账信息',
			onLoad : function() {
			}
		});
}

//控制经济行业类别的选择框显示
$(document).ready(function(){
	$("#ecoTradeId1").hide();
	$("#ecoTradeId2").hide();
	
	$('#cusType').combobox({
		onSelect: function(row){
			var opts = $(this).combobox('options');
			if(row[opts.textField]=='企业')
			{
				$("#ecoTradeId1").show();
				$("#ecoTradeId2").show();
			}else{
				$("#ecoTradeId1").hide();
				$("#ecoTradeId2").hide();
			}
 
		}
	});
})
//重置按钮
function majaxReset(form){
	$("#ecoTradeId1").hide();
	$("#ecoTradeId2").hide();
	$(form).form('reset')
}
