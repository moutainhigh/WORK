
/**
 * 客户征信报告管理js
 */



/**客户征信报告全局变量*/
var cusCreditGlobal ={
		
	//================================变量===============================
	/**信息来源*/
	dataSourceJson : [ {
		"pid" : "0",
		"lookupDesc" : "--请选择--"
	}, {
		"pid" : "1",
		"lookupDesc" : "鹏元征信"
	},{
		"pid" : "2",
		"lookupDesc" : "优分数据"
	} ,{
		"pid" : "3",
		"lookupDesc" : "FICO大数据评分"
	}],
		
	/**报告类型*/
	reportTypeJson : [ {
		"pid" : "0",
		"lookupDesc" : "--请选择--"
	}, {
		"pid" : "1",
		"lookupDesc" : "个人信用报告"
	}, {
		"pid" : "2",
		"lookupDesc" : "个人风险汇总"
	}, {
		"pid" : "3",
		"lookupDesc" : "个人反欺诈分析"
	}, {
		"pid" : "4",
		"lookupDesc" : "刑事案底核查"
	}, {
		"pid" : "5",
		"lookupDesc" : "FICO大数据信用评分"
	} ],
	

	
	/**查询原因*/
	queryResonIdJson : [ {
		"pid" : "0",
		"lookupDesc" : "--请选择--"
	}, {
		"pid" : "101",
		"lookupDesc" : "贷款审批"
	}, {
		"pid" : "102",
		"lookupDesc" : "贷款贷后管理"
	}
	/*, {
		"pid" : "103",
		"lookupDesc" : "贷款催收"
	}, {
		"pid" : "105",
		"lookupDesc" : "担保/融资审批"
	}, {
		"pid" : "901",
		"lookupDesc" : "贷款催收"
	}, {
		"pid" : "999",
		"lookupDesc" : "其他"
	}*/ ],
	
	
	
	/**有无人行征信*/
	queryPbocStatusJson : [ {
		"pid" : "0",
		"lookupDesc" : "--请选择--"
	}, {
		"pid" : "true",
		"lookupDesc" : "有"
	}, {
		"pid" : "false",
		"lookupDesc" : "无"
	}, {
		"pid" : "und",
		"lookupDesc" : "不确定"
	}],
	
	//================================方法===============================
	
	/**格式化信息来源*/
	formatDataSource: function (val,row) {
   	    for(var o in cusCreditGlobal.dataSourceJson){  
   	    	if(val == cusCreditGlobal.dataSourceJson[o].pid){
   	    		return cusCreditGlobal.dataSourceJson[o].lookupDesc;
   	    		break;
   	    	}
   		}  
	},

	/**格式化报告类型*/
	formatReportType: function (val,row) {
		
   	    for(var o in cusCreditGlobal.reportTypeJson){  
   	    	if(val == cusCreditGlobal.reportTypeJson[o].pid){
   	    		return cusCreditGlobal.reportTypeJson[o].lookupDesc;
   	    		break;
   	    	}
   		}  
	},
	
	/**格式化查询原因*/
	formatQueryResonId: function (val,row) {
   	    for(var o in cusCreditGlobal.queryResonIdJson){  
   	    	if(val == cusCreditGlobal.queryResonIdJson[o].pid){
   	    		return cusCreditGlobal.queryResonIdJson[o].lookupDesc;
   	    		break;
   	    	}
   		}  
	},
	 
	/**格式化查询状态*/
	formatQueryStatus:function (val,row){
		if(val == 1){
			return "成功";
		}else if(val == 2){
			return "<font color='red'>失败</font>";
		}else{
			return "";
		}
	},
	
	/**格式化有无人行征信*/
	formatQueryPbocStatus:function (val,row){
   	    for(var o in cusCreditGlobal.queryPbocStatusJson){  
   	    	if(val == cusCreditGlobal.queryPbocStatusJson[o].pid){
   	    		return cusCreditGlobal.queryPbocStatusJson[o].lookupDesc;
   	    		break;
   	    	}
   		}  
	},
	
	/**格式化重复查询-收费状态-状态*/
	formatIsRepeat:function (val,row){
		if(val == 1){
			return "否";
		}else if(val == 2){
			return "<font color='red'>是</font>";
		}else{
			return "--";
		}
	}

}





 
 

 

















 


 
 


 
 