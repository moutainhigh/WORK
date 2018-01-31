/**
 * 还款计划表导出 
 */
var exportRepay = {
		init:function(){
			// 只有贷前 , 额度 , 展期 才展示导出
			if(WORKFLOW_ID != 'extensionRequestProcess' 
				&& WORKFLOW_ID != 'loanRequestProcess' 
					&& WORKFLOW_ID != 'creditWithdrawalRequestProcess'){
				$("#exportDiv").hide();
			}
		},//导出还款计划表 tableType 1 非展期 2 展期
	 	exprotRepayment:function(templateName,loanId,projectId,isEdit){
	 		if(loanId == -1){
	 			$.messager.alert('操作提示','请先保存贷款基本信息','warning');
	 			return ;
	 		}
	 		
	 		// 验证模版是否存在
	 		$.ajax({
				url:BASE_PATH+'templateFileController/checkFileUrl.action',
				data:{templateName:templateName},
				dataType:'json',
				success:function(data){
					if(data==1){
						// 获取table中所有行的还款日期,并且组装好
						var dataRows = $('#repaymentPlanGrid'+isEdit).datagrid('getRows'); 
						var planRepayDts = "";
						if(dataRows && dataRows.length>0){
							for(var i =0 ; i < dataRows.length ; i++){
								if(i == (dataRows.length -1)){
									// 最后一行是合计,跳过
									continue;
								}
								if(i == (dataRows.length -2)){
									planRepayDts = planRepayDts +dataRows[i].planRepayDt;
								}else{
									planRepayDts = planRepayDts +dataRows[i].planRepayDt + ",";
								}
							}
						}
						window.location.href =BASE_PATH+"loanExtensionController/exportRepayment.action?templateName="+templateName+"&loanId="+loanId+"&projectId="+projectId+"&planRepayDts="+planRepayDts;
					}else{
						$.messager.alert('操作提示','还款计划表的导出模板不存在，请上传模板后重试','warning');
					}
				}
			})
	 	}
}


$(function(){
	exportRepay.init();
});