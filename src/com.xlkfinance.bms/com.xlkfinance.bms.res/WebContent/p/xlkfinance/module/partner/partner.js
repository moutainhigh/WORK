
/**
 * 资金机构管理js
 */

$(document).ready(function() {
	
	 
});

/**资金合作机构全局变量*/
var partnerGlobal ={
		/**合作机构*/
		partnerJson : [ {
			"pid" : "",
			"lookupDesc" : "请选择"
		}, {
			"pid" : "xiaoying",
			"lookupDesc" : "小赢"
		}, {
			"pid" : "tlxt",
			"lookupDesc" : "统联信托"
		}, {
			"pid" : "dianrongwang",
			"lookupDesc" : "点荣网"
		}, {
			"pid" : "nyyh",
			"lookupDesc" : "南粤银行"
		} , {
			"pid" : "hnbx",
			"lookupDesc" : "华安保险"
		}],

		/**文件类型*/
		accessoryTypeJson : [{ "pid": "", "lookupDesc": "请选择" },
		                     		{ "pid": "10", "lookupDesc": "南粤银行材料" },{ "pid": "11", "lookupDesc": "华安保险材料" },
		                     		{ "pid": "1", "lookupDesc": "客户基本信息" },
			                         { "pid": "2", "lookupDesc": "交易方基本信息" },{ "pid": "3", "lookupDesc": "房产信息" },
			                         { "pid": "4", "lookupDesc": "交易信息" },{ "pid": "5", "lookupDesc": "还款来源信息" },
			                         { "pid": "6", "lookupDesc": "征信信息" },{ "pid": "7", "lookupDesc": "合同协议信息" },
			                         { "pid": "9", "lookupDesc": "居间服务协议" },{ "pid": "8", "lookupDesc": "其他材料" }
			                         ],
		/**客户基本信息*/
		accessoryChildTypeJson_1 : [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "F00", "lookupDesc": "卖房人身份证" },
		                          { "pid": "F00_0", "lookupDesc": "卖房人身份证(正)" },{ "pid": "F00_1", "lookupDesc": "卖房人身份证(反)" },
									{ "pid": "F01", "lookupDesc": "卖房人户口本" },{ "pid": "F02", "lookupDesc": "卖房人婚姻证明" }],
		/**交易方基本信息*/
		accessoryChildTypeJson_2 : [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "F03", "lookupDesc": "买房人身份证" },
									{ "pid": "F04", "lookupDesc": "买房人户口本" },{ "pid": "F05", "lookupDesc": "买房人婚姻证明" }],
		/**房产信息*/
		accessoryChildTypeJson_3 : [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "F06", "lookupDesc": "房产证" },
									{ "pid": "F07", "lookupDesc": "房产查档单" }],
		/**交易信息*/
		accessoryChildTypeJson_4 : [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "F08", "lookupDesc": "交易合同" },
									{ "pid": "F09", "lookupDesc": "资金监管/托管协议" },{ "pid": "F10", "lookupDesc": "定金收据/转账凭证" },
									{ "pid": "F11", "lookupDesc": "首付款收据/转账凭证" },
									{ "pid": "F50", "lookupDesc": "放款凭证" },{ "pid": "F51", "lookupDesc": "收款凭证" }],
		/**还款来源信息*/
		accessoryChildTypeJson_5 : [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "F12", "lookupDesc": "银行贷款审批通过凭证" },
									{ "pid": "F13", "lookupDesc": "买家按揭贷款核实单原件" },{ "pid": "F14", "lookupDesc": "买房人征信报告" },
									{ "pid": "F15", "lookupDesc": "银行还款流水/收入证明" },
									{ "pid": "F28", "lookupDesc": "本金还款凭证" },{ "pid": "F29", "lookupDesc": "利息还款凭证" }],
		/**征信信息*/
		accessoryChildTypeJson_6 : [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "F16", "lookupDesc": "卖方人征信报告" },
									{ "pid": "F17", "lookupDesc": "法院诉讼查询结果截屏" }],
		/**合同协议信息*/
		accessoryChildTypeJson_7 : [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "F18", "lookupDesc": "借款合同" },
									{ "pid": "F19", "lookupDesc": "卖房人原按揭" },{ "pid": "F20", "lookupDesc": "抵押合同及还贷纪录" }],
		/**其他材料*/
		accessoryChildTypeJson_8 : [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "F21", "lookupDesc": "合作方申请表" },
									{ "pid": "F22", "lookupDesc": "项目申请审批表" },{ "pid": "F23", "lookupDesc": "推荐确认书" },
									{ "pid": "F24", "lookupDesc": "合作方服务协议" },{ "pid": "F25", "lookupDesc": "众安投保单" }],
		/**居间服务协议*/
		accessoryChildTypeJson_9 : [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "F26", "lookupDesc": "居间服务协议" },
									{ "pid": "F27", "lookupDesc": "公证处授权委托书" }],
		/**南粤银行材料*/
		accessoryChildTypeJson_10 : [{ "pid": "", "lookupDesc": "请选择" },
                                 	{ "pid": "05010", "lookupDesc": "1、其他资料，公证书、负债情况、小贷情况等" },
                                 	{ "pid": "05001", "lookupDesc": "2、贷款申请表" },
         							{ "pid": "05002", "lookupDesc": "3、身份类资料/手持身份证拍照/户口本/全国法院被执行人查询信息/全国法院失信被执行人查询材料" },
         						//	{ "pid": "05003", "lookupDesc": "4、信用报告" },
         						//	{ "pid": "05004", "lookupDesc": "5、居住类证明" },
         							{ "pid": "05005", "lookupDesc": "4、实地征信报告" },
         						//	{ "pid": "05006", "lookupDesc": "7、保单" },
         						//	{ "pid": "05007", "lookupDesc": "8、收入类资料" },
         						//	{ "pid": "05008", "lookupDesc": "9、经营类资料" },
         						//	{ "pid": "05009", "lookupDesc": "10、资产类资料" },
         							{ "pid": "05011", "lookupDesc": "5、借款合同" },
         						//	{ "pid": "05012", "lookupDesc": "12、抵押类材料" },
         						//	{ "pid": "05014", "lookupDesc": "13、婚姻类资料" },
         							{ "pid": "05017", "lookupDesc": "6、签约照片/签约视频/面签面谈材料" },
         						//	{ "pid": "05031", "lookupDesc": "15、婚姻类资料" },
         						//	{ "pid": "05032", "lookupDesc": "16、行驶证/车辆注册登记簿/车辆照片/交强险资料/抵押办理材料" },
         						//	{ "pid": "05034", "lookupDesc": "17、代收确认书" },
         							{ "pid": "05036", "lookupDesc": "7、委托扣款授权书" },
         							{ "pid": "05037", "lookupDesc": "8、银行卡复印件/第三方收款人身份证，第三方收款卡，第三方收款人（公司）查询资料" },
         						//	{ "pid": "05039", "lookupDesc": "20、商业保险单" },
         							{ "pid": "05044", "lookupDesc": "9、信用卡流水/借记卡流水/还款清单" },
         						//	{ "pid": "05045", "lookupDesc": "22、车辆购买协议/首付款证明/资方电子账户截图" },
         						//	{ "pid": "05100", "lookupDesc": "23、批量还款文件" },
         						//	{ "pid": "05046", "lookupDesc": "24、手持借款合同照片" },
         						//	{ "pid": "05047", "lookupDesc": "25、工作证明复印件" },
         						//	{ "pid": "05048", "lookupDesc": "26、额度试算表" },
         						//	{ "pid": "05049", "lookupDesc": "27、电核信息截图" },
         						//	{ "pid": "05050", "lookupDesc": "28、资方贷款申请表" },
         						//	{ "pid": "05051", "lookupDesc": "29、资方贷款借款合同（三方）" },
         						//	{ "pid": "05052", "lookupDesc": "30、资方贷款借款合同（融资人版）" },
         						//	{ "pid": "05053", "lookupDesc": "31、面谈面签声明表" },
         						//	{ "pid": "05054", "lookupDesc": "32、联系人信息收集表" },
         							{ "pid": "05055", "lookupDesc": "10、房产证明/评估资料/查档资料/原贷款合同、原贷款卡" },
         						//	{ "pid": "05056", "lookupDesc": "34、公积金证明" },
         							{ "pid": "05057", "lookupDesc": "11、买卖合同、定金收据、首期款监管协议" },
         							{ "pid": "05058", "lookupDesc": "12、新贷款银行审批批复(同贷书)、新贷款银行支付委托书" }
         						],
         						
	 /**华安保险材料*/
	 accessoryChildTypeJson_11 : [{ "pid": "", "lookupDesc": "请选择" },
	                              { "pid": "A1", "lookupDesc": "1、买方身份证正反面" },
	                              { "pid": "A2", "lookupDesc": "2、卖方身份证正反面" },
	                              { "pid": "B1", "lookupDesc": "3、借款人身份证正反面" },
	                              { "pid": "B2", "lookupDesc": "4、配偶身份证正反面" },
	                              { "pid": "C", "lookupDesc": "5、借款人征信授权书" },
	                              { "pid": "D", "lookupDesc": "6、原借款人还款清单/贷款余额表" },
	                              { "pid": "E", "lookupDesc": "7、个人贷款履约保证保险投保单" },
	                              { "pid": "F", "lookupDesc": "8、面签照片" },
	                              { "pid": "G", "lookupDesc": "9、回款银行卡信息" },
	                              { "pid": "H", "lookupDesc": "10、房屋买卖合同" },
	                              { "pid": "I", "lookupDesc": "11、房地产查册书/抵押回执" },
	                              { "pid": "J", "lookupDesc": "12、公证委托书" },
	                              { "pid": "K", "lookupDesc": "13、房产证" },
	                              { "pid": "L", "lookupDesc": "14、银行批复" },
	                              { "pid": "M", "lookupDesc": "15、资金监管协议或回单" },
	                              { "pid": "N", "lookupDesc": "16、赎楼平台审批意见单" },
	                              { "pid": "O", "lookupDesc": "17、赎楼平台连带担保书" },
	                              { "pid": "P", "lookupDesc": "18、资金方借款合同" }],
								
     //还款回购类型
     repaymentTypeJson : [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "1", "lookupDesc": "还款" },
                         { "pid": "2", "lookupDesc": "回购" }],
                         
     //机构推送帐号                  
     partnerPushAccountJson : [{ "pid": "", "lookupDesc": "请选择" },{ "pid": "2", "lookupDesc": "小科" },
	                      { "pid": "1", "lookupDesc": "万通" }],

	//================================以下为方法
	/**格式化机构*/
	partnerFormat: function (val,row) {
		if(val == 'xiaoying'){
			return "小赢";
		}else if(val == 'tlxt'){
			return "统联信托";
		}else if(val == 'dianrongwang'){
			return "点荣网";
		}else if(val == 'nyyh'){
			return "南粤银行";
		}else if(val == 'hnbx'){
			return "华安保险";
		}
	},
	/**格式化多个机构*/
	partnerMoreFormat: function (val,row) {
		var nameList = "";
		if(val!= null && val!= ''){
			var valList = val.split(";");
			for(var i = 0 ; i < valList.length; i ++){
				var tempVal = valList[i].split("##")[0];
				if(tempVal == 'xiaoying'){
					nameList += "小赢"
				}else if(tempVal == 'tlxt'){
					nameList +=  "统联信托";
				}else if(tempVal == 'dianrongwang'){
					nameList +=  "点荣网";
				}else if(tempVal == 'nyyh'){
					nameList +=  "南粤银行";
				}else if(tempVal == 'hnbx'){
					nameList +=  "华安保险";
				}else{
					nameList +=  tempVal;
				}
				nameList += ",";
			}
			nameList = nameList.substring(0,nameList.length-1); 
		}
		return nameList;
	},
	/**格式化项目来源*/
	formatProjectSource: function (val,row){
		if(val == 1){
			return "万通";
		}else if(val == 2){
			return "小科";
		}else if(val == 3){
			return "其他机构";
		} 
	},
	//格式化申请按钮
	formatPartnerNoRequestBtn:function(value, rowData, index){
		var partnerNo = rowData.partnerNo;
		var	tdDom = "<a projectId='"+rowData.projectId+"' partnerNo='"+partnerNo+"' id='partnerNoRequestBtn"+(index+1)+"' href='javascript:void(0)'   onClick='showPartnerNoTipsInfo("+(index+1)+");' onMouseOut='hidePartnerNoTipsInfo(1)' ><font color='blue'>申请</font></a>";
		return tdDom;
	},
	/**格式化附件类型*/
	accessoryTypeFormat:function (val,row){
		if(val == 1){
			return "客户基本信息";
		}else if(val == 2){
			return "交易方基本信息";
		}else if(val == 3){
			return "房产信息";
		}else if(val == 4){
			return "交易信息";
		}else if(val == 5){
			return "还款来源信息";
		}else if(val == 6){
			return "征信信息";
		}else if(val == 7){
			return "合同协议信息";
		}else if(val == 8){
			return "其他材料";
		}else if(val == 9){
			return "居间服务协议";
		}else if(val == 10){
			return "南粤银行材料";
		}else if(val == 11){
			return "华安保险材料";
		}
	},
	/**格式化附件子类型*/
	accessoryChildTypeFormat: function(val,row){
		if(row.accessoryType == '10'){
			//南粤银行材料
	   	    for(var o in partnerGlobal.accessoryChildTypeJson_10){  
	   	    	if(val == partnerGlobal.accessoryChildTypeJson_10[o].pid){
	   	    		return partnerGlobal.accessoryChildTypeJson_10[o].lookupDesc;
	   	    		break;
	   	    	}
	   		}  
		}else if(row.accessoryType == '11'){
			//华安保险材料
	   	    for(var o in partnerGlobal.accessoryChildTypeJson_11){  
	   	    	if(val == partnerGlobal.accessoryChildTypeJson_11[o].pid){
	   	    		return partnerGlobal.accessoryChildTypeJson_11[o].lookupDesc;
	   	    		break;
	   	    	}
	   		}  
		}else if(val == '00'){
			return "身份证";
		}else if(val == '01'){
			return "护照";
		}else if(val == '02'){
			return "军官证";
		}else if(val == '03'){
			return "士兵证";
		}else if(val == '04'){
			return "港澳居民来往内地通行证";
		}else if(val == '05'){
			return "户口本";
		}else if(val == '06'){
			return "外国护照";
		}else if(val == '07'){
			return "其他";
		}else if(val == '08'){
			return "文职证";
		}else if(val == '09'){
			return "警官证";
		}else if(val == '10'){
			return "台胞证";
		}else if(val == 'F00'){
			return "卖房人身份证";
		}else if(val == 'F00_0'){
			return "卖房人身份证(正)";
		}else if(val == 'F00_1'){
			return "卖房人身份证(反)";
		}else if(val == 'F01'){
			return "卖房人户口本";
		}else if(val == 'F02'){
			return "卖房人婚姻证明";
		}else if(val == 'F03'){
			return "买房人身份证";
		}else if(val == 'F04'){
			return "买房人户口本";
		}else if(val == 'F05'){
			return "买房人婚姻证明";
		}else if(val == 'F06'){
			return "房产证";
		}else if(val == 'F07'){
			return "房产查档单";
		}else if(val == 'F08'){
			return "交易合同";
		}else if(val == 'F09'){
			return "资金监管/托管协议";
		}else if(val == 'F10'){
			return "定金收据/转账凭证";
		}else if(val == 'F11'){
			return "首付款收据/转账凭证";
		}else if(val == 'F12'){
			return "银行贷款审批通过凭证";
		}else if(val == 'F13'){
			return "买家按揭贷款核实单原件";
		}else if(val == 'F14'){
			return "买房人征信报告";
		}else if(val == 'F15'){
			return "银行还款流水/收入证明";
		}else if(val == 'F16'){
			return "卖方人征信报告";
		}else if(val == 'F17'){
			return "法院诉讼查询结果截屏";
		}else if(val == 'F18'){
			return "借款合同";
		}else if(val == 'F19'){
			return "卖房人原按揭";
		}else if(val == 'F20'){
			return "抵押合同及还贷纪录";
		}else if(val == 'F21'){
			return "合作方申请表";
		}else if(val == 'F22'){
			return "项目申请审批表";
		}else if(val == 'F23'){
			return "推荐确认书";
		}else if(val == 'F24'){
			return "合作方服务协议";
		}else if(val == 'F25'){
			return "众安投保单";
		}else if(val == 'F26'){
			return "居间服务协议";
		}else if(val == 'F27'){
			return "公证处授权委托书";
		}else if(val == 'F28'){
			return "本金还款凭证";
		}else if(val == 'F29'){
			return "利息还款凭证";
		}else if(val == 'F50'){
			return "放款凭证";
		}else if(val == 'F51'){
			return "收款凭证";
		}
	},
	/**格式化是否上传第三方平台*/
	thirdFileUrlFormat:function (val,row){
		if(val == null || val == ""){
			return "否";
		}else{
			return "是";
		}
	},
	/**格式化项目状态*/
	formatterState:function (val,row){
		if(val == 1){
			return "待客户经理提交";
		}else if(val == 2){
			return "待部门经理审批";
		}else if(val == 3){
			return "待业务总监审批";
		}else if(val == 4){
			return "待审查员审批";
		}else if(val == 5){
			return "待风控初审";
		}else if(val == 6){
			return "待风控复审";
		}else if(val == 7){
			return "待风控终审";
		}else if(val == 8){
			return "待风控总监审批";
		}else if(val == 9){
			return "待总经理审批";
		}else if(val == 10){
			return "已审批";
		}else if(val == 11){
			return "已放款";
		}else if(val == 12){
			return "业务办理已完成";
		}else if(val == 13){
			return "已归档";
		}else if(val == 15){
			return "待合规复审";
		}
	},
	//申请状态
	formatterRequestStatus: function (val,row){
		if(val == 1){
			return "未申请";
		}else if(val == 2){
			return "申请成功";
		}else if(val == 3){
			return "申请中";
		}else if(val == 4){
			return "申请拒绝";
		}
	},
	//审核状态
	formatterStatus:function (val,row){
		if(val == 1){
			return "审核中";
		}else if(val == 2){
			return "通过";
		}else if(val == 3){
			return "未通过";
		}else if(val == 4){
			return "补充材料";
		}
	},
	//格式化通知类型
	formatterNotifyType:function (val,row){
		if(val == 1){
			return "申请 ";
		}else if(val == 2){
			return "复议";
		}else if(val == 3){
			return "确认要款";
		}else if(val == 4){
			return "复议确认要款";
		}
	},
	//格式确认要款状态
	formatConfirmStatus:function (val,row){
		if(val == 1 || val == 0){
			return "未发送 ";
		}else if(val == 2){
			return "审核中";
		}else if(val == 3){
			return "审核通过";
		}else if(val == 4){
			return "审核不通过";
		}
	},
	//格式化放款状态
	formatLoanState: function (val,row){
		if(val == 1 || val == 0){
			return "未申请";
		}else if(val == 2){
			return "申请中";
		}else if(val == 3){
			return "放款中";
		}else if(val == 4){
			return "放款成功";
		}else if(val == 5){
			return "放款失败";
		}else if(val == 6){
			return "放款驳回";
		}
	},
	//关闭状态
	formatterCloseState:function (val,row){
		if(val == 1){
			return "否";
		}else if(val == 2){
			return "是";
		}
	},
	//还款回购状态
	formatterRepaymentStatus:function (val,row){
		if(val == 1 || val == 0){
			return "未申请";
		}else if(val == 2){
			return "已申请";
		}else if(val == 3){
			return "确认收到";
		}else if(val == 4){
			return "未收到";
		}else if(val == 5){
			return "确认中";
		}else if(val == 6){
			return "己逾期";
		}else if(val == 7){
			return "坏帐";
		}else if(val == 8){
			return "正常";
		}else if(val == 9){
			return "宽限期内";
		}else{
			return val;
		}
	},
	//还款回购类型
	formatterRepaymentType:function (val,row){
		if(val == 1){
			return "还款";
		}else if(val == 2){
			return "回购";
		}else{
			return "";
		}
	},
	//格式化显示
	formatTooltip:function(value, row, index){
		if (value==null) {
			return "";
		}
        var abValue = value;
        if (value.length>=12) {
           abValue = value.substring(0,10) + "...";
        }
		var va = '<span  title="' + value + '" class="note">' + abValue + '</span>';
        return va;
	}

}





 
 

 

















 


 
 


 
 