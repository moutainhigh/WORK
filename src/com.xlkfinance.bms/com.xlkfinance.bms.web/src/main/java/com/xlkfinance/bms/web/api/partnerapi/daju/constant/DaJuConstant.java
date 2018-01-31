package com.xlkfinance.bms.web.api.partnerapi.daju.constant;

/**
 * 大桔（南粤银行）常量类
 * @author chenzhuzhen
 * @date 2017年2月9日 下午2:27:41
 */
public class DaJuConstant {
	
	/**
	 * 接口业务代码
	 */
	public enum ServiceCode{
		NYBCREDIT_QUERY("NybCreditQuery", "南粤征信查询"),
		OPEN_ACCOUNT("OpenAccount", "用户开户绑卡"),
		LEND_APPLY("LendApply", "资金申请"),
		QUERY_LEND_APPLY("QueryLendApply", "资金申请状态查询"),
		ACCOUNT_NOTIFY("AccountNotify", "出账通知"),
		PREPAYMENT_ADVANCE("PrepaymentAdvance", "提前还款试算"),
		PREPAYMENT("Prepayment", "提前还款申请"),
		QUERY_PREPAYMENT("QueryPrepayment", "提前还款状态查询"),
		FILE_UPLOAD("FileUpload", "文件上传"),
		FILE_DOWNLOAN("FileDownloan", "文件下载"),
		BIZ_BATCH_FILE("BizBatchFile", "批量执行业务文件"),
		GetOtp("GetOtp", "获取验证码");
		private String code;
		private String msg;
		private ServiceCode(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	/**
	 * 接口业务返回码
	 */
	public enum ReturnCode{
		SUCCESS("0000", "成功"),
		SUCCESS_CREDIT("00000", "成功"),		//查询征信
		SUCCESS_FILE("000000", "成功");		//上传文件
		//‘非0000’ 为失败
		private String code;
		private String msg;
		private ReturnCode(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	/**
	 * 接口状态返回码
	 */
	public enum MessageCode{
		SUCCESS("000000", "成功"),
		C_100000("100000", "场景编号为空"),
		C_100001("100001", "场景密码为空"),
		C_100002("100002", "秘钥为空"),
		C_100003("100003", "接入代码为空"),
		C_100004("100004", "请求号为空"),
		C_100005("100005", "场景用户为空"),
		C_100008("100008", "密码无效"),
		C_200000("200000", "场景不可使用"),
		C_200001("200001", "场景不存在"),
		C_200002("200002", "接入配置不存在"),
		C_200004("200004", "时间限制内！不允许访问！"),
		C_300000("300000", "头信息验证失败"),
		C_300001("300001", "主体报文验证失败！"),
		C_300003("300003", "主体信息为空！"),
		C_300004("300004", "报文体不是json串！"),
		C_300010("300010", "AES解密失败！"),
		C_300011("300011", "RSA解密失败！"),
		C_300012("300012", "RSA解密失败！"),
		C_300013("300013", "RSA加密失败！"),
		C_300014("300014", "AES加密失败！"),
		C_900000("900000", "ip地址未授权"),
		C_999998("999998", "查询失败"),
		C_999999("999999", "其它异常，请联系管理员处理");
		private String code;
		private String msg;
		private MessageCode(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 4.3.提前还款类型
	 */
	public enum PrepayType{
		C_01000("01000", "全部提前还款"),
		C_01001("01001", "部分提前还款");
		private String code;
		private String msg;
		private PrepayType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	
	/**
	 * 4.4.02-提前还款金额类型
	 */
	public enum PrepayAmtType{
		C_02000("02000", "本金方式"),
		C_02001("02001", "利息方式");
		private String code;
		private String msg;
		private PrepayAmtType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	/**
	 * 4.5.03-资金申请状态
	 */
	public enum ApplyStat{
		APPLY_ING("03002", "申请处理中(大桔反馈接受场景方进件，并存库，基本校验完成)"),
		APPLY_PASS("03006", "申请成功(大桔反馈开户成功且影像校验成功，资金申请还未发送给银行)"),
		APPLY_NO_PASS("03001", "申请拒绝(大桔反馈业务数据校验失败或开户失败或影响校验不通过)"),
		APPROVAL_PASS("03000", "审批通过(银行方反馈审批成功)"),
		ERROR("03003", "系统错误(系统处理发生异常，需要人工干预排查)"),
		APPROVAL_ING("03004", "审批处理中(银行方接受资金申请，正在处理中)"),
		APPROVAL_NO_PASS("03005", "审批拒绝(银行方反馈审批拒绝)");
		
		private String code;
		private String msg;
		private ApplyStat(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 4.6.04-提前还款申请状态
	 */
	public enum PaymentStat{
		SUCCESS("04000", "还款成功"),
		FAIL("04001", "还款失败"),
		DEALING("04002", "处理中");
		private String code;
		private String msg;
		private PaymentStat(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 4.7.05-文件类型
	 */
	public enum FileType{
		C_05001("05001", "贷款申请表"),
		C_05002("05002", "身份类资料/手持身份证拍照/户口本/全国法院被执行人查询信息（所有借款人及其配偶、收款人）/全国法院失信被执行人查询材料（所有借款人及其配偶、担保人、收款人）"),
		C_05003("05003", "信用报告"),
		C_05004("05004", "居住类证明"),
		C_05005("05005", "实地征信报告"),
		C_05006("05006", "保单"),
		C_05007("05007", "收入类资料"),
		C_05008("05008", "经营类资料"),
		C_05009("05009", "资产类资料"),
		C_05010("05010", "其他资料，公证书、负责情况、小贷情况等"),
		C_05011("05011", "借款合同"),
		C_05012("05012", "抵押类材料"),
		C_05013("05013", "其他资料"),
		C_05014("05014", "婚姻类资料"),
		C_05017("05017", "签约照片/签约视频/面签面谈材料"),
		C_05031("05031", "婚姻类资料"),
		C_05032("05032", "行驶证/车辆注册登记簿/车辆照片/交强险资料/抵押办理材料"),
		C_05034("05034", "代收确认书"),
		C_05036("05036", "委托扣款授权书"),
		C_05037("05037", "银行卡复印件/第三方收款人身份证,第三方收款卡，第三方收款人（公司）查询资料"),
		C_05039("05039", "商业保险单"),
		C_05044("05044", "信用卡流水、借记卡流水/还款清单"),
		C_05045("05045", "车辆购买协议/首付款证明"),
		C_05100("05100", "批量还款文件"),
		//C_05045("05045", "资方电子账户截图"),
		C_05046("05046", "手持借款合同照片"),
		C_05047("05047", "工作证明复印件"),
		C_05048("05048", "额度试算表"),
		C_05049("05049", "电核信息截图"),
		C_05050("05050", "资方贷款申请表"),
		C_05051("05051", "资方贷款借款合同（三方）"),
		C_05052("05052", "资方贷款借款合同（融资人版）"),
		C_05053("05053", "面谈面签声明表"),
		C_05054("05054", "联系人信息收集表"),
		C_05055("05055", "房产证明/评估资料/查档资料/原贷款合同、原贷款卡"),
		C_05056("05056", "公积金证明"),
		C_05057("05057", "买卖合同、定金收据、首期款监管协议"),
		C_05058("05058", "新贷款银行审批批复(同贷书)、新贷款银行支付委托书");
		
		private String code;
		private String msg;
		private FileType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	/**
	 * 4.8.06-证件类型
	 */
	public enum CertType{
		C_06000("06000", "身份证"),
		C_06001("06001", "护照"),
		C_06002("06002", "驾驶证");
		private String code;
		private String msg;
		private CertType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 4.9. 07-性别
	 */
	public enum Gender{
		C_FEMALE("07000", "女"),
		C_MALE("07001", "男"),
		C_UNKNOW("07001", "未知");
		private String code;
		private String msg;
		private Gender(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 4.10.08-婚姻状况
	 */
	public enum Marriage{
		C_08000("08000", "未婚"),
		C_08001("08001", "已婚"),
		C_08002("08002", "离异"),
		C_08003("08003", "其他");
		private String code;
		private String msg;
		private Marriage(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	/**
	 * 4.11.09-教育状况
	 */
	public enum Education{
		C_09000("09000", "博士及以上"),
		C_09001("09001", "硕士"),
		C_09002("09002", "本科"),
		C_09003("09003", "大专"),
		C_09004("09004", "高中专"),
		C_09005("09005", "初中及以下");
		private String code;
		private String msg;
		private Education(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	/**
	 * 4.13.11-单位性质
	 */
	public enum CorpNature{
		C_11000("11000", "国有"),
		C_11001("11001", "私营"),
		C_11002("11002", "个体"),
		C_11003("11003", "股份"),
		C_11004("11004", "事业机关"),
		C_11005("11005", "军警"),
		C_11006("11006", "其他");
		private String code;
		private String msg;
		private CorpNature(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	/**
	 * 4.13.11-单位性质
	 */
	public enum CorpIndustry{
		C_12000("12000", "农林牧渔业"),
		C_12001("12001", "采矿业"),
		C_12002("12002", "制造业"),
		C_12003("12003", "电力、热力、燃气及水生产和供应业"),
		C_12004("12004", "建筑业"),
		C_12005("12005", "批发和零售业"),
		C_12006("12006", "交通运输、仓储和邮政业"),
		C_12007("12007", "住宿和餐饮业"),
		C_12008("12008", "信息传输、软件和信息技术服务业"),
		C_12009("12009", "金融业"),
		C_12010("12010", "房地产业"),
		C_12011("12011", "租赁和商务服务业"),
		C_12012("12012", "科学研究和技术服务业"),
		C_12013("12013", "水利、环境和公共设施管理业"),
		C_12014("12014", "居民服务、修理和其他服务业"),
		C_12015("12015", "教育"),
		C_12016("12016", "卫生和社会工作"),
		C_12017("12017", "文化、体育和娱乐业"),
		C_12018("12018", "公共管理、社会保障和社会组织"),
		C_12019("12019", "国际组织");
		private String code;
		private String msg;
		private CorpIndustry(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	/**
	 * 4.15.13-职业类型
	 */
	public enum OccupType{
		C_13000("13000", "国有机关、党群组织、企业、事业单位负责人"),
		C_13001("13001", "专业技术人员"),
		C_13002("13002", "办事人员和有关人员"),
		C_13003("13003", "商业、服务业人员"),
		C_13004("13004", "农林渔牧水利业生产人员"),
		C_13005("13005", "生产、运输设备操作人员及有关人员"),
		C_13006("13006", "军人"),
		C_13007("13007", "不便分类的其他从业人员"),
		C_13008("13008", "未知");
		private String code;
		private String msg;
		private OccupType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 4.16.14-行政类别
	 */
	public enum AdminType{
		C_14000("14000", "高级领导"),
		C_14001("14001", "中级领导"),
		C_14002("14002", "一般员工"),
		C_14003("14003", "其他"); 
		private String code;
		private String msg;
		private AdminType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	/**
	 * 4.17.15-职务类型
	 */
	public enum JobType{
		C_15000("15000", "单位负责人或局级及以上"),
		C_15001("15001", "部门负责人或处级"),
		C_15002("15002", "科室负责人或科级"),
		C_15003("15003", "普通员工或科员"),
		C_15004("15004", "临时工或办事员"),
		C_15005("15005", "无"); 
		private String code;
		private String msg;
		private JobType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	/**
	 * 4.39.39-工作性质
	 */
	public enum JobNature{
		C_39000("39000", "公务员"),
		C_39001("39001", "公益型事业单位人员"),
		C_39002("39002", "社会公用事业单位人员"),
		C_39003("39003", "金融行业人员"),
		C_39004("39004", "其他上市公司及其分支机构的正式工作人员"),
		C_39005("39005", "国有独资垄断性行业或行业龙头企业的正式工作人员"),
		C_39006("39006", "个体户"),
		C_39007("39007", "独资企业"),
		C_39008("39008", "合伙企业"),
		C_39009("39009", "其他");
		private String code;
		private String msg;
		private JobNature(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	/**
	 * 4.18.16-雇佣类型
	 */
	public enum EmployeeType{
		C_16000("16000", "标准受薪客户"),
		C_16001("16001", "优良职业客户"),
		C_16002("16002", "标准受薪客户(以自雇人士定价)"),
		C_16003("16003", "优良职业客户(以自雇人士定价)"),
		C_16004("16004", "自雇人士客户"),
		C_16005("16005", "优良职业客户（合作单位）"),
		C_16006("16006", "标准受薪客户（合作单位）"),
		C_16007("16007", "学生客群");
		private String code;
		private String msg;
		private EmployeeType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	/**
	 * 4.19.17-行业风险类别
	 */
	public enum EmployeeRisk{
		C_17000("17000", "极高风险行业"),
		C_17001("17001", "高风险行业"),
		C_17002("17002", "优良职业"),
		C_17003("17003", "一般风险行业");
 
		private String code;
		private String msg;
		private EmployeeRisk(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	
	
	/**
	 * 4.20.18-居住状况
	 */
	public enum ResidentType{
		C_18000("18000", "自置"),
		C_18001("18001", "按揭"),
		C_18002("18002", "亲属楼宇"),
		C_18003("18003", "集体宿舍"),
		C_18004("18004", "租房"),
		C_18005("18005", "共有住宅"),
		C_18006("18006", "其他");
		private String code;
		private String msg;
		private ResidentType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 	4.21.19-贷款用途
	 */
	public enum PurposeType{
		C_19016("19016", "赎楼");
		private String code;
		private String msg;
		private PurposeType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	/**
	 * 	4.22.20-贷款详细用途
	 */
	public enum PurposeDetail{
		C_20025("20025", "赎楼");
		private String code;
		private String msg;
		private PurposeDetail(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	/**
	 * 	4.23.21-支付方式
	 */
	public enum PayType{
		C_21000("21000", "自主支付"),
		C_21001("21001", "他行受托支付"),
		C_21002("21002", "本行受托支付");
		private String code;
		private String msg;
		private PayType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	/**
	 * 	4.24.22-期限类型
	 */
	public enum PeriodType{
		C_22000("22000", "年"),
		C_22001("22001", "月"),
		C_22002("22002", "日");
		private String code;
		private String msg;
		private PeriodType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	/**
	 * 	4.27.25-联系人关系类型
	 */
	public enum Relation{
		C_25000("25000", "配偶"),
		C_25001("25001", "亲密朋友"),
		C_25002("25002", "父母"),
		C_25003("25003", "子女"),
		C_25004("25004", "其他"),
		C_25005("25005", "兄弟"),
		C_25006("25006", "姐妹"),
		C_25007("25007", "同学"),
		C_25008("25008", "同事"),
		C_25009("25009", "朋友"),
		C_25010("25010", "商务合作伙伴");
		private String code;
		private String msg;
		private Relation(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
 
	
	/**
	 * 	4.28.26-Header状态返回码
	 */
	public enum Header{
		SUCCESS("000000", "成功"),
		C_100000("100000", "场景编号为空"),
		C_100001("100001", "场景密码为空"),
		C_100002("100002", "秘钥为空"),
		C_100003("100003", "接入代码为空"),
		C_100004("100004", "请求号为空"),
		C_100005("100005", "场景用户为空"),
		C_100008("100008", "密码无效"),
		C_200000("200000", "场景不可使用"),
		C_200001("200001", "场景不存在"),
		C_200002("200002", "接入配置不存在"),
		C_200004("200004", "时间限制内！不允许访问！"),
		C_300000("300000", "头信息验证失败！"),
		C_300001("300001", "主体报文验证失败！"),
		C_300003("300003", "主体信息为空！"),
		C_300004("300004", "报文体不是json串！"),
		C_300010("300010", "AES解密失败！"),
		C_300011("300011", "RSA解密失败！"),
		C_300012("300012", "RSA解密失败！"),
		C_300013("300013", "RSA加密失败！"),
		C_300014("300014", "AES加密失败！"),
		C_900000("900000", "ip地址未授权"),
		C_999998("999998", "查询失败"),
		ERROR("999999", "其它异常，请联系管理员处理");
		private String code;
		private String msg;
		private Header(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 4.30.28-单位工商查询结果
	 */
	public enum CorpStat{
		C_28000("28000", "正常"),
		C_28001("28001", "吊销"),
		C_28002("28002", "注销");
		private String code;
		private String msg;
		private CorpStat(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	/**
	 * 4.31.29-当地社保
	 */
	public enum SocialIns{
		C_29000("29000", "有"),
		C_29001("29001", "无");
		private String code;
		private String msg;
		private SocialIns(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	/**
	 * 4.32.30-社保记录状态
	 */
	public enum SocialInsStat{
		C_30000("30000", "当前有"),
		C_30001("30001", "近六个月有"),
		C_30002("30002", "无"),
		C_30003("30003", "已停缴");
		private String code;
		private String msg;
		private SocialInsStat(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	/**
	 * 4.33.31-公积金记录状态
	 */
	public enum ProvidentFund{
		C_31000("31000", "当前有"),
		C_31001("31001", "近六个月有"),
		C_31002("31002", "无"),
		C_31003("31003", "已停缴");
		private String code;
		private String msg;
		private ProvidentFund(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	
	
	
	
	/**
	 * 4.35.33-家庭本地房产
	 */
	public enum LocalHouse{
		C_33000("33000", "有"),
		C_33001("33001", "无");
		private String code;
		private String msg;
		private LocalHouse(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 	4.36.34-户口所在地
	 */
	public enum RegionCode{
		C_33000("34000", "本地"),
		C_33001("34001", "非本地");
		private String code;
		private String msg;
		private RegionCode(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	
	/**
	 * 	4.37.35-征信查询原因
	 */
	public enum QueryReason{
		C_35000("35000", "贷款审批"),
		C_38001("38001", "查询异常");
		private String code;
		private String msg;
		private QueryReason(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	
	
	/**
	 * 	4.40.45-南粤银行卡
	 */
	public enum AccountNybFlag{
		YES("45000", "客户的收款账号是南粤银行卡"),
		NO("45001", "客户的收款账号不是南粤银行卡");
		private String code;
		private String msg;
		private AccountNybFlag(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	
	/**
	 * 	4.40.45-南粤银行卡
	 */
	public enum NybRemarkFlag{
		C_46000("46000", "房产证，房产地址，备注"),
		C_46001("46001", "新贷款收款人，新贷款金额，新贷款收款账户，新贷款收款银行");
		private String code;
		private String msg;
		private NybRemarkFlag(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	/**
	 * 4.42.47-出账标志
	 */
	public enum PayStatus{
		C_47000("47000", "放款"),
		C_47001("47001", "撤销未出账合同");
		private String code;
		private String msg;
		private PayStatus(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	/**
	 * 4.43.48-身份核查标志
	 */
	public enum IdCheck{
		C_48000("48000", "通过"),
		C_48001("48001", "不通过");
		private String code;
		private String msg;
		private IdCheck(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 4.44.49-开户标志
	 */
	public enum AccountOpen{
		C_49000("49000", "成功"),
		C_49001("49001", "失败");
		private String code;
		private String msg;
		private AccountOpen(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 4.45.50-绑卡标志
	 */
	public enum CardBinding{
		C_50000("50000", "成功"),
		C_50001("50001", "失败");
		private String code;
		private String msg;
		private CardBinding(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	/**
	 * 	4.46.51-贷款品种
	 */
	public enum BusinessType{
		C_51000("51000", "交易类赎楼"),
		C_51001("51001", "非交易类赎楼");
		private String code;
		private String msg;
		private BusinessType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	

}
