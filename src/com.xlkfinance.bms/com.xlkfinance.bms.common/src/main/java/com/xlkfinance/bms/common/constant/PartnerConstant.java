package com.xlkfinance.bms.common.constant;


/**
 * 资金机构常量类
 * @author chenzhuzhen
 * @date 2016年11月11日 上午11:07:39
 */
public class PartnerConstant {
	
	/*
	 * ==============资金机构合作 key 必须与 CAPITAL_ORG_INFO > ORG_CODE  保持一致================
	 */
	/** 小赢 */
	public static final String PARTNER_XY = "xiaoying";
	/** 统联信托 */
	public static final String PARTNER_TL = "tlxt";
	/** 点融-点荣网 */
	public static final String PARTNER_DR = "dianrongwang";
	/** 南粤银行 */
	public static final String PARTNER_NYYH = "nyyh";
	/** 华安保险 */
	public static final String PARTNER_HNBX = "hnbx";
	
	/**交易类*/
	public static final int BUSINESS_TYPE_1 = 1 ;
	/**非交易类*/
	public static final int BUSINESS_TYPE_2 = 2 ;
	
	
	/**
	 * 资方同步状态类型
	 * @author Administrator
	 */
	public enum ThirdSyncStatusType{
		APPROVAL_STATUS(1, "审批状态"), 		 
		PAYMENT_STATUS(2, "还款状态");
		private int code;
		private String msg;
		private ThirdSyncStatusType(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
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
	 *合作项目申请状态,  BIZ_PROJECT_PARTNER.REQUEST_STATUS 1--未申请 2--申请成功/已申请  3：申请中   4：申请拒绝
	 */
	public enum RequestStatus{
		NO_APPLY(1, "未申请"), 		 
		APPLY_PASS(2, "申请成功"),
		APPLY_ING(3, "申请中"),
		APPLY_NO_PASS(4, "申请拒绝 ");
		private int code;
		private String msg;
		private RequestStatus(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
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
	 * 审批状态（BIZ_PROJECT_PARTNER.APPROVAL_STATUS）
	 */
	public enum ApprovalStatus{
		APPROVAL_ING(1, "审批中"), 	 
		APPROVAL_PASS(2, "审核通过"),
		APPROVAL_NO_PASS(3, "审核未通过"),
		ADD(4, "驳回补件 ");
		
		private int code;
		private String msg;
		private ApprovalStatus(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
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
	 * 放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败 6：驳回）（BIZ_PROJECT_PARTNER.LOAN_STATUS）
	 */
	public enum LoanStatus{
		NO_APPLY(1, "未申请"), 	 
		APPLY_ING(2, "申请中"),
		LOAN_ING(3, "放款中"),
		LOAN_SUCCESS(4, "放款成功"),
		LOAN_FAIL(5, "放款失败"),
		BACK(6, "驳回");
		
		private int code;
		private String msg;
		private LoanStatus(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
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
	 * 还款、回购状态（BIZ_PROJECT_PARTNER.REPAYMENT_REPURCHASE_STATUS）
	 * 		 1:未申请2:已申请3:确认收到；4：未收到; 5：确认中; 6：己逾期  ; 7：坏帐; 8：正常; 
	 * 	 	 9：宽限期内
	 * 
	 */
	public enum RepaymentRepurchaseStatus{
		NO_APPLY(1, "未申请"), 		 
		ALREADY_APPLY(2, "己申请"),
		SUCCESS(3, "确认收到"),
		FAIL(4, "未收到 "),
		CONFIRM_ING(5, "确认中 "),
		OVERDUE(6, "己逾期 "),
		BAD_LOAN(7, "坏帐 "),
		NORMAL(8, "正常 "),
		IN_GRACE_PERIOD(9, "宽限期内 ");
		private int code;
		private String msg;
		private RepaymentRepurchaseStatus(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
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
	 * 是否关闭
	 */
	public enum IsClosed{
		NO_CLOSED(1, "未关闭"), 		 
		HAS_CLOSED(2, "已关闭");
		private int code;
		private String msg;
		private IsClosed(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
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
	 * 还款回购类型
	 */
	public enum RepaymentRepurchaseType{
		C_1(1, "还款"), 		 
		C_2(2, "回购");
		private int code;
		private String msg;
		private RepaymentRepurchaseType(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
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
	 * 机构推送帐号
	 */
	public enum PartnerPushAccount{
		C_1(1, "万通"), 		 
		C_2(2, "小科");
		private int code;
		private String msg;
		private PartnerPushAccount(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
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
