package com.xlkfinance.bms.common.constant;

/**
 * 客户征信模块常量
 * @author chenzhuzhen
 * @date 2017年6月9日 上午11:11:04
 */
public class CusCreditConstant {

	
	////////////////////////////////征信模块///////////////////////////
	/**
	 * 信息来源
	 */
	public enum DataSource{
		C_1(1, "鹏元征信"),
		C_2(2, "优分数据")	, 
		C_3(3, "FICO大数据评分");
		private int code;
		private String msg;
		private DataSource(int code, String msg) {
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
	 * 报告类型
	 */
	public enum ReportType{
		C_1(1, "个人信用报告"), 		 
		C_2(2, "个人风险汇总"), 		 
		C_3(3, "个人反欺诈分析"),
		C_4(4, "刑事案底核查"),
		C_5(5, "FICO大数据信用分");
		private int code;
		private String msg;
		private ReportType(int code, String msg) {
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
	 * 报告类型对应接口方法,code 对应 ReportType code
	 */
	public enum ReportTypeServiceMethod{
		/**
		 * 个人信用报告
		 */
		C_1(1, "pyzx_h_grxy_96002"),
		/**
		 * 个人风险汇总
		 */
		C_2(2, "pyzx_w_grfxhz_14200"), 		 
		/**
		 * 个人反欺诈分析
		 */
		C_3(3, "pyzx_w_grfqz_96040"),
		/**
		 * 刑事案底核查
		 */
		C_4(4, "yfsj_w_xsadhc_10000"),
		/**
		 * 大数据信用评分
		 */
		C_5(5, "fico_w_bds_10000");
		
		private int code;
		private String msg;
		private ReportTypeServiceMethod(int code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(int key){
	        for(ReportTypeServiceMethod index:ReportTypeServiceMethod.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	
	/**
	 * 报告类型对应收费子报告,子报告可多个，用英文逗号隔开
	 */
	public enum ReportTypeSubreportIDs{
		/**
		 * 个人信用报告
		 */
		C_1(1, "96002"),
		/**
		 * 个人风险汇总
		 */
		C_2(2, "14200"), 		 
		/**
		 * 个人反欺诈分析
		 */
		C_3(3, "96040");
		
		private int code;
		private String msg;
		private ReportTypeSubreportIDs(int code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(int key){
	        for(ReportTypeSubreportIDs index:ReportTypeSubreportIDs.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	/**
	 * 报告类型对应查询类型
	 */
	public enum ReportTypeQueryType{
		/**
		 * 个人信用报告
		 */
		C_1(1, "25136"),	//测试环境(互联网环境)
//		C_1(1, "33107"),	//生产环境
		/**
		 * 个人风险汇总
		 */
		C_2(2, "25160"), 		 
		/**
		 * 个人反欺诈分析
		 */
		C_3(3, "25212"),
		/**
		 * FICO大数据评分
		 */
		C_5(5, "101");
		
		private int code;
		private String msg;
		private ReportTypeQueryType(int code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(int key){
	        for(ReportTypeQueryType index:ReportTypeQueryType.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	
	
	
	
	/**
	 * 查询原因
	 */
	public enum QueryResonId{
		C_101("101", "贷款审批"), 		 
		C_102("102", "贷款贷后管理"), 		 
		C_103("103", "贷款催收"),
		C_104("104", "审核担保人信用"), 
		C_105("105", "担保/融资审批"), 
		
		C_201("201", "信用卡审批"), 
		C_202("202", "信用卡贷后管理"), 
		C_203("203", "信用卡催收"), 
		C_301("301", "加强税源基础管理"), 
		C_302("302", "追缴欠税"), 
		C_303("303", "商户信用"), 
		
		C_304("304", "申报创新人才奖"), 
		C_305("305", "失业人员小额贷款担保审批"), 
		C_306("306", "深圳市外来务工人员积分入户申请"), 
		
		C_401("401", "车贷保证保险审批"), 
		C_402("402", "审核车贷保证保险担保人信用"), 
		
		C_501("501", "求职"), 
		C_502("502", "招聘"), 
		C_503("503", "异议处理"), 
		C_901("901", "了解个人信用"), 
		C_999("999", "其他");
		
		private String code;
		private String msg;
		private QueryResonId(String code, String msg) {
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
	 * 是否有PBOC报告 （人行征信报告）
	 */
	public enum PbocStatus{
		C_YES("true", "有"), 		 
		C_NO("false", "无"), 		 
		C_UND("und", "不确定");
		
		private String code;
		private String msg;
		private PbocStatus(String code, String msg) {
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
	
	
	public enum ErrorCode{

		SUCCESS("0000", "操作成功"),
		// ======================常规错误定义 ====================//
		/** 参数不全 */
		ERROR_4001("4001", "参数不全"),
		/** 参数格式错误 */
		ERROR_4002("4002", "参数格式错误"),
		/** 签名验证失败 */
		ERROR_4003("4003", "签名验证失败"),
		/** 资源不存在 */
		ERROR_4004("4004", "资源不存在"),
		/** 模块错误 */
		ERROR_4005("4005", "模块错误"),
		/** 方法错误 */
		ERROR_4006("4006", "方法错误"),
		/** 操作不可执行*/
		ERROR_4007("4007", "操作不可执行"),
		/** 资源重复*/
		ERROR_4008("4008", "资源重复"),
		ERROR_9000("9000", "操作失败"),
		
		ERROR("9999", "系统异常");
		
		
		private String code;
		private String msg;
		private ErrorCode(String code, String msg) {
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
	
	////////////////////////////////征信模块///////////////////////////
	
}
