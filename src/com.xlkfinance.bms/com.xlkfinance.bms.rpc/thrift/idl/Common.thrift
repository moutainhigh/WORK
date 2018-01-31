/**
 * @Title: Common.thrift
 * @Package com.xlkfinance.bms.rpc.common
 * @Description: 公共模块IDL
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月12日 下午1:36:23
 * @version V1.0
 */
namespace java com.xlkfinance.bms.rpc.common

exception ThriftServiceException {
  1: string code,
  2: string message
}

/*列表封装类*/
struct GridViewDTO{
	1: string pid;
	2: string value1; // 项目名称
	3: string value2; // 项目编号
	4: string value3; // 客户类型
	5: string value4; // 贷款申请时间
	6: string value5; // 联系电话
	7: string value6; // 贷款金额
	8: string value7; // 计划监管人
	9: string value8; // 计划监管时间
	10: string value9;  // 到期未收金额
	11: string value10;
	12: string value11;
	13: string value12;
	14: string value13;
	15: string value14;
	16: string value15;
	17: string value16;
	18: string value17;
	19: string value18;
	20: string value19;
	21: string value20;
	22: string value21;
	23: string value22;
	24: string value23;
	25: string value24;
	26: string value25;
	27: string value26;
	28: string value27;
	29: string value28;
	30: string value29;
	31: string value30;
	32: string value31;
	33: string value32;
	34: string value33;
}

/**
 * 邮件通知属性Vo
 * @author Daijingyu
 */
struct EmailVo{
	1: string senderEmail;  			//发送人邮件
	2: list<string> recipientEmails;	//为接收邮件信息的邮箱账号如有多个接受人以英文逗号隔开
	3: string subject;  				// 邮件标题
	4: string htmlText; 				//邮件内容  例子:(<h3>您好：<br/>邮箱信息发送测试</h3>)
}

/**
 * 短信通知传参Vo
 * @author Daijingyu
 */
 struct SmsVo {
	1: string mobileStr; //电话号吗列表 多个以英文逗号隔开
	2: string content;   //【短信模版】 信息内容（经过处理，“【”会替换成“[”，“】”会替换成“]”)
	3: string sign; 	 //信息开头附加签名编号例如(1：[信捷网]2：[信利康小贷]) 具体看接口文档
}
