package com.xlkfinance.bms.web.api.partnerapi.hnbx.constant;


/**
 * 华安保险常量类
 * @author chenzhuzhen
 * @date 2017年7月5日 下午7:27:03
 */
public class HNBXConstant {
	
	
	
	/**
	 * 婚姻状态
	 */
	public enum MarryStatus{
		/**
		 * 未婚
		 */
		C_10("10", "未婚"),
		/**
		 * 已婚无子女
		 */
		C_20("20", "已婚无子女"), 	
		/**
		 * 已婚且有子女
		 */
		C_21("21", "已婚且有子女"),
		/**
		 *离异
		 */
		C_22("22", "离异"),
		/**
		 * 复婚
		 */
		C_23("23", "复婚"),
		/**
		 * 丧偶
		 */
		C_30("30", "丧偶"),
		/**
		 * 未说明的婚姻状况
		 */
		C_90("90", "未说明的婚姻状况"); 
		
		private String code;
		private String msg;
		private MarryStatus(String code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(MarryStatus index:MarryStatus.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	/**
	 * 贷款品种
	 */
	public enum LoanType{
		/**
		 * 现金赎楼业务
		 */
		C_1("05240303", "现金赎楼业务"),
		/**
		 * 担保赎楼业务
		 */
		C_2("05320301", "担保赎楼业务"); 		 
		
		private String code;
		private String msg;
		private LoanType(String code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(LoanType index:LoanType.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}

	
	/**
	 * 赎楼类型
	 */
	public enum RansomType{

		/**
		 * 交易现金赎楼
		 */
		C_1("00", "交易现金赎楼"),
		/**
		 * 非交易现金赎楼
		 */
		C_2("01", "非交易现金赎楼"), 	
		/**
		 * 交易担保赎楼
		 */
		C_3("02", "交易担保赎楼"),
		/**
		 * 非交易担保赎楼
		 */
		C_4("03", "非交易担保赎楼"); 
		
		private String code;
		private String msg;
		private RansomType(String code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(RansomType index:RansomType.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	/**
	 * 4）期限类型
	 */
	public enum TermType{
		/**
		 * 天
		 */
		C_1("01", "天"),
		/**
		 * 月
		 */
		C_20("02", "月"); 	
 
		
		private String code;
		private String msg;
		private TermType(String code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(TermType index:TermType.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	/**
	 * 还款方式
	 */
	public enum RepaymentMode{
		/**
		 * 其他还款
		 */
		C_1("1", "其他还款"),
		/**
		 * 等额本金还款
		 */
		C_2("2", "等额本金还款"),
		/**
		 * 按月等额本息
		 */
		C_3("3", "按月等额本息"),
		/**
		 * 按季还息到期还本
		 */
		C_4("4", "按季还息到期还本"),
		/**
		 * 按月付息按季还本
		 */
		C_5("5", "按月付息按季还本"),
		/**
		 * 按月付息到期还本
		 */
		C_6("6", "按月付息到期还本"),
		/**
		 * 到期一次还款
		 */
		C_7("7", "到期一次还款"),
		/**
		 * 按月付息+部分本，到期还本
		 */
		C_8("8", "按月付息+部分本，到期还本"),
		/**
		 * 按月等本等息
		 */
		C_9("9", "按月等本等息"); 	
 
		
		private String code;
		private String msg;
		private RepaymentMode(String code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(RepaymentMode index:RepaymentMode.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	
	
	
	
	
	
	
	/**
	 * 担保方式
	 */
	public enum AssureMeans{
		/**
		 * 信用
		 */
		C_00("00", "信用"),
		/**
		 * 信用
		 */
		C_10("10", "信用"),
		/**
		 * 质押
		 */
		C_20("20", "质押"),
		/**
		 * 保证
		 */
		C_30("30", "保证"); 	
 
		
		private String code;
		private String msg;
		private AssureMeans(String code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(AssureMeans index:AssureMeans.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	
	
	
	/**
	 * 房屋用途
	 */
	public enum HousePurpose{
		/**
		 * 住宅
		 */
		C_1("1", "住宅"),
		/**
		 * 商住两用
		 */
		C_2("2", "商住两用"),
		/**
		 * 商用房
		 */
		C_3("3", "商用房"); 	
 
		
		private String code;
		private String msg;
		private HousePurpose(String code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(HousePurpose index:HousePurpose.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	
	/**
	 * 客户类型
	 */
	public enum CusType{
		/**
		 * 一般自然人
		 */
		C_110("110", "一般自然人"),
		/**
		 * 个人经营户
		 */
		C_120("120", "个人经营户"); 	
		
		private String code;
		private String msg;
		private CusType(String code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(CusType index:CusType.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	/**
	 * 证件类型
	 */
	public enum CertType{
		/**
		 * 居民身份证
		 */
		C_10("10", "居民身份证"),
		/**
		 * 组织机构代码
		 */
		C_20("20", "组织机构代码"),
		/**
		 * 社会信用代码
		 */
		C_30("30", "社会信用代码"),
		/**
		 * 机构信用代码
		 */
		C_40("40", "机构信用代码"); 	
		
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(CertType index:CertType.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	/**
	 * 证件类型
	 */
	public enum IndivRsdStatus{
		/**
		 * 自建
		 */
		C_0("0", "自建"),
		/**
		 * 自置
		 */
		C_1("1", "自置"),
		/**
		 * 按揭
		 */
		C_2("2", "按揭"),
		/**
		 * 亲属楼宇
		 */
		C_3("3", "亲属楼宇"),
		/**
		 * 集体宿舍
		 */
		C_4("4", "集体宿舍"),
		/**
		 * 租房
		 */
		C_5("5", "租房"),
		/**
		 * 共有住宅
		 */
		C_6("6", "共有住宅"),
		/**
		 * 其他
		 */
		C_7("7", "其他"); 	
		
		private String code;
		private String msg;
		private IndivRsdStatus(String code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(IndivRsdStatus index:IndivRsdStatus.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	/**
	 * 文件类型
	 */
	public enum FileType{
		/**
		 * 买方身份证正反面
		 */
		C_A1("A1", "买方身份证正反面"),
		/**
		 * 卖方身份证正反面
		 */
		C_A2("A2", "卖方身份证正反面"),
		/**
		 * 借款人身份证正反面
		 */
		C_B1("B1", "借款人身份证正反面"),
		/**
		 * 配偶身份证正反面
		 */
		C_B2("B2", "配偶身份证正反面"),
		/**
		 * 借款人征信授权书
		 */
		C_C("C", "借款人征信授权书"),
		/**
		 * 原借款人还款清单/贷款余额表
		 */
		C_D("D", "原借款人还款清单/贷款余额表"),
		/**
		 * 个人贷款履约保证保险投保单
		 */
		C_E("E", "个人贷款履约保证保险投保单"),
		/**
		 * 面签照片
		 */
		C_F("F", "面签照片"),
		/**
		 * 回款银行卡信息
		 */
		C_G("G", "回款银行卡信息"),
		
		/**
		 * 房屋买卖合同
		 */
		C_H("H", "房屋买卖合同"),
		
		/**
		 * 房地产查册书/抵押回执
		 */
		C_I("I", "房地产查册书/抵押回执"),
		/**
		 * 公证委托书
		 */
		C_J("J", "公证委托书"),
		/**
		 * 房产证
		 */
		C_K("K", "房产证"),
		/**
		 * 银行批复
		 */
		C_L("L", "银行批复"),
		/**
		 * 银行批复
		 */
		C_M("M", "银行批复"),
		/**
		 * 赎楼平台审批意见单
		 */
		C_N("N", "赎楼平台审批意见单"),
		/**
		 * 赎楼平台连带担保书
		 */
		C_O("O", "赎楼平台连带担保书"),
		/**
		 * 资金方借款合同
		 */
		C_P("P", "资金方借款合同"); 	
		
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(FileType index:FileType.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
	
	/**
	 * 返回码
	 */
	public enum RspCod{
		/**
		 * 居民身份证
		 */
		SUCCESS("00000", "成功"),
		/**
		 * 组织机构代码
		 */
		C_20("00001", "字段错误"),
		/**
		 * 社会信用代码
		 */
		C_30("00002", "其他错误"),
		/**
		 * 机构信用代码
		 */
		ERROR("00003", "系统异常"); 	
		
		private String code;
		private String msg;
		private RspCod(String code, String msg) {
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
		
		/**
		 * 获取对应值
		 * @param key
		 * @return
		 */
	    public static String getValue(String key){
	        for(RspCod index:RspCod.values()){
	            if(index.getCode() == key){
	                return index.getMsg();
	            }
	        }
	        return null;
	    }
	}
	
}
