/**
 * @Title: FinanceBankMapper.java
 * @Package com.xlkfinance.bms.server.finance.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Heng.Wang
 * @date: 2015年1月26日 下午3:47:53
 * @version V1.0
 */
package com.xlkfinance.bms.server.finance.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.finance.AcctProjectBalanceDTO;
import com.xlkfinance.bms.rpc.finance.AcctProjectBalanceView;
import com.xlkfinance.bms.rpc.finance.BadDebtDataBean;
import com.xlkfinance.bms.rpc.finance.BatchRepaymentView;
import com.xlkfinance.bms.rpc.finance.CostReduction;
import com.xlkfinance.bms.rpc.finance.CustArrearsView;
import com.xlkfinance.bms.rpc.finance.FinanceAcctTotalCondition;
import com.xlkfinance.bms.rpc.finance.FinanceAcctTotalDetailView;
import com.xlkfinance.bms.rpc.finance.FinanceAcctTotalView;
import com.xlkfinance.bms.rpc.finance.FinanceBank;
import com.xlkfinance.bms.rpc.finance.FinanceBusinessCondition;
import com.xlkfinance.bms.rpc.finance.FinanceBusinessView;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesCondition;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesDTO;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesView;
import com.xlkfinance.bms.rpc.finance.FinanceTransactionDTO;
import com.xlkfinance.bms.rpc.finance.LoanBankAccountBean;
import com.xlkfinance.bms.rpc.finance.LoanCycleNumView;
import com.xlkfinance.bms.rpc.finance.LoanFeew;
import com.xlkfinance.bms.rpc.finance.LoanRealTimeDTO;
import com.xlkfinance.bms.rpc.finance.LoanReconciliationDtlView;
import com.xlkfinance.bms.rpc.finance.MonthlyReportBasePlan;
import com.xlkfinance.bms.rpc.finance.MonthlyReportBasePlanIm;
import com.xlkfinance.bms.rpc.finance.MonthlyReportRecord;
import com.xlkfinance.bms.rpc.finance.MonthlyReportRecordCalculateDetail;
import com.xlkfinance.bms.rpc.finance.MonthlyReportRecordCondition;
import com.xlkfinance.bms.rpc.finance.OverdueDataCondition;
import com.xlkfinance.bms.rpc.finance.ProjectTotalDetailView;
import com.xlkfinance.bms.rpc.finance.QueryOverdueReceivablesBean;
import com.xlkfinance.bms.rpc.finance.ReconciliationItem;
import com.xlkfinance.bms.rpc.finance.ReconciliationOptionsView;
import com.xlkfinance.bms.rpc.finance.RepaymentReconciliationDTO;
import com.xlkfinance.bms.rpc.finance.RepaymentReconciliationDetailDTO;
import com.xlkfinance.bms.rpc.finance.RepaymentReconciliationView;
import com.xlkfinance.bms.rpc.finance.UnReconciliationCondition;
import com.xlkfinance.bms.rpc.finance.UnReconciliationView;

@MapperScan("financeBankMapper")
public interface FinanceBankMapper<T, PK> extends BaseMapper<T, PK>{
	public List<FinanceBank> getFinanceAcctManager(FinanceBank financeBank);
	public FinanceBank getFinanceAcctManagerById(int pid);
	public int updateFinanceAcctManager(FinanceBank financeBank);
	public int deleteFinanceAcctManager(List pid);
	/**add by yql*/
	/**
	 * 财务-客户业务查询
	 * 
	 * @return
	 */
	public List<FinanceBusinessView> getFinanceCusBusiness(FinanceBusinessCondition financeBusinessCondition);
	/**
	 * 
	  * @Description: TODO统计客户业务查询数据总数
	  * @param financeBusinessCondition
	  * @return
	  * @author: yql
	  * @date: 2015年3月14日 下午3:05:54
	 */
	public int countFinanceCusBusiness(FinanceBusinessCondition financeBusinessCondition);
	
	/**
	 * 
	  * @Description: TODO账户管理的总数
	  * @param financeBusinessCondition
	  * @return
	  * @author: yql
	  * @date: 2015年3月14日 下午3:45:38
	 */
	public int countFinanceAcctManager(FinanceBank financeBank);
	
	
	
	/**
	 * 
	  * @Description: TODO批量对账查询
	  * @param financeBusinessCondition
	  * @return
	  * @author: yql
	  * @date: 2015年3月15日 下午3:05:54
	 */
	public List<BatchRepaymentView>  getAcctBatchRepayment(FinanceBusinessCondition financeBusinessCondition);
	
	/**
	 * 
	  * @Description: TODO 统计批量对账查询数据总数
	  * @param financeBusinessCondition
	  * @return
	  * @author: yql
	  * @date: 2015年3月15日 下午3:45:38
	 */
	public int countAcctBatchRepayment(FinanceBusinessCondition financeBusinessCondition);
	
	public List<CustArrearsView> getCustArrearsView(FinanceBusinessCondition financeBusinessCondition);	
	  //统计
	public int countCustArrearsView(FinanceBusinessCondition financeBusinessCondition);
	
	public List<FinanceAcctTotalView> getFinanceAcctTotalView(FinanceAcctTotalCondition financeAcctTotalCondition);	
	  //统计
	public int countFinanceAcctTotal(FinanceAcctTotalCondition financeAcctTotalCondition);
	
	/**
	 * 
	  * @Description: TODO  财务明细列表
	  * @param financeAcctTotalCondition
	  * @return
	  * @author: yql
	  * @date: 2015年3月23日 下午3:18:40
	 */
	public List<FinanceAcctTotalDetailView> getFinanceAcctTotalDetailView(FinanceAcctTotalCondition financeAcctTotalCondition);
	// 获取用于页面对账表（还款计划表）中的一期详情
	public ReconciliationItem getProGetPlanProject(Map<String,Object> param);
	public List<ProjectTotalDetailView> getProjectTotalDetailList(FinanceBusinessCondition financeBusinessCondition);
	//查看对账信息查询
	public List<LoanReconciliationDtlView> getLoanReconciliationDtl(int receId);
	
	// 获取用于欠款列表的金额数据
	public ReconciliationItem getProGetCustProjectDetail(Map<String,Object> param);
	//多付金额转入客户余额处理  
	public int addAcctProjectBalance(AcctProjectBalanceDTO acctProjectBalanceDTO);
	 //根据客户id查询客户的余额，客户名称
	public AcctProjectBalanceView getAcctProjectBalanceByLoand(int loanId);
	 //根据客户id查询客户可转让的余额
    public AcctProjectBalanceView getBalanceByReceId(int receId);
    //未对账项目查询
    public List<UnReconciliationView> getListUnReconciliation(UnReconciliationCondition unReconciliationCondition);
    //未对账项目统计总数
    public int countUnReconciliation(UnReconciliationCondition unReconciliationCondition);
    //根据收款id查询收款信息
    public UnReconciliationView findUnReconciliationInfo(int inputId);
    //财务总账查询金额方法
    public FinanceAcctTotalView getFinanceTotalDatePro (Map<String,Object> param);

	/**add by yql*/
	// add by qcxian
    public int deleteLoanInputDate(int pid);
    
	/** 获取符合条件的财务收款数据集合*/
	public List<FinanceReceivablesDTO> getFinanceReceivablesDTOList(FinanceReceivablesCondition condition);
	
	// 获取新增的财务收款页面需要的数据对象
	public FinanceReceivablesView getFinanceReceivablesView(int loanId);
	// 保存财务收款
	void insertFinanceReceivables(FinanceReceivablesDTO dto);
	// 修改财务收款的使用余额（使用未对完款的余额，继续使用客户余额）
	int updateFinanceReceivablesUseBalance(FinanceReceivablesDTO dto);
	// 获取可以对账的选项集合
	List<ReconciliationOptionsView> getReconciliationOptions(int loanId);
	// 获取用于页面对账表（还款计划表）中的一期详情
	ReconciliationItem getRepaymentPlanReconciliationItem(Map param);
	// 获取用于页面对账表（即时发生还款计划表）中的每一项详情
	ReconciliationItem getRealtimePlanReconciliationItem(Map param);
	// 获取费用减免情况
	LoanFeew getRepaymentPlanLoanFeew(Map param);
	
	// 获取还款计划表中的对应的逾期利息
	double getOverdueRepaymentFine(OverdueDataCondition condition);
	// 获取即时还款计划表中的对应的逾期罚息
	double getOverdueRealtimeFine(OverdueDataCondition condition);
	// 新增对账记录
	void insertRepaymentReconciliation(RepaymentReconciliationDTO dto);
	// 新增对账细节记录
	void insertRepaymentReconciliationDetail(RepaymentReconciliationDetailDTO dto);
	// 新增财务交易记录
	void insertFinanceTransaction(FinanceTransactionDTO dto);
	/**
	 *  更新还款计划表中的完成状态
	  * @param paramMap key: pid 主键   key : reconciliationType  完成类型
	  * @author: qiancong.Xian
	  * @date: 2015年3月17日 下午4:07:31
	 */
	void updateRepaymentPlanReconciliation(Map<String,Integer> paramMap);
	/**
	 * 更新即时发生还款计划表中的完成状态
	  * @param paramMap key: pid 主键   key : reconciliationType  完成类型
	  * @author: qiancong.Xian
	  * @date: 2015年3月17日 下午4:07:31
	 */
	void updateRealtimePlanReconciliation(Map<String,Integer> paramMap);
	/**
	 *  更新财务收款表记录的状态
	  * @param paramMap  对应的key值： receivablesId  主键
	  * receivablesVersion  数据版本
	  * availableReconciliationAmount 剩余可用    
	  * receivablesReconciliation 是否已经完成
	  * @return 更新的行数
	  * @author: qiancong.Xian
	  * @date: 2015年3月24日 上午9:39:45
	 */
	int updateReceivablesData(Map<String,Object> paramMap);
	
	/**
	 *  获取批量还款的一个贷款下所有待还的项
	  * @param paramMap
	  * @author: qiancong.Xian
	  * @date: 2015年3月28日 下午5:37:52
	 */
	void getBatchRepaymentLoanItem(Map<String,Object> paramMap);
	
	
	LoanBankAccountBean getLoanBankAccountBean(int loanId);
	/**
	  * @Description: 获取项目结清办理中贷款信息
	  * @param paramMap
	  * @author: qiancong.Xian
	  * @date: 2015年4月3日 上午11:40:19
	 */
	void getLoanBaseDataBean(Map<String,Object> paramMap);
	
	/**
	 *  获取财务收款对象
	  * @param financeReceivablesId
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年4月3日 下午3:46:15
	 */
	FinanceReceivablesDTO getFinanceReceivables(int financeReceivablesId);
	/**
	 * 
	  * @ 获取呆账坏账后计算的数据
	  * @param loanId 贷款ID
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年4月10日 下午4:19:41
	 */
	List<BadDebtDataBean> getBadDebtDataBean(int loanId);
	
	/**
	 *  激活还款计划的最大版本（用于呆账坏账的完成）
	  * @param loanId
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年4月14日 下午2:57:05
	 */
	int activateRepaymentPlan(int loanId);
	/**
	 *  激活即时计划的最大版本（用于呆账坏账的完成）
	  * @param loanId
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年4月14日 下午2:57:05
	 */
	int activateRealtimePlan(int loanId);
	
	/**
	 *  获取某一天的逾期利息（还款对账功能中的费用减免使用）
	  * @param params
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年4月22日 下午7:13:05
	 */
	double getPlanOverdueByDate(Map<String,String> params);

	/**
	 *  获取某一天的逾期罚息（还款对账功能中的费用减免使用）
	  * @param params
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年4月22日 下午7:13:05
	 */
	double getIMMOverdueByDate(Map<String,String> params);
	
	/**
	 * 收款的时候，顺便更新一下无金额的计划为已对账
	  * @param loanId
	  * @author: qiancong.Xian
	  * @date: 2015年7月3日 下午2:38:46
	 */
	void updateNullValuePlan(int loanId);
	/**
	 * 
	  * 收款的时候，顺便更新一下无金额的即时计划为已对账
	  * @param loanId
	  * @author: qiancong.Xian
	  * @date: 2015年7月3日 下午2:39:25
	 */
	void updateNullValueIMPlan(int loanId);
	
	
	/**
	 *  获取月报表中的还款计划表基本数据
	  * @param limtDate
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月4日 上午11:18:44
	 */
	List<MonthlyReportBasePlan> getMonthlyReportBasePlan(String limtDate);
	/**
	 * 获取月报表中的即时发生表基本数据
	  * @param limtDate
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月4日 上午11:19:09
	 */
	List<MonthlyReportBasePlanIm> getMonthlyReportBasePlanIm(String limtDate);
	/**
	 *  月报表中，查询期限日期的逾期
	  * @param bean
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月7日 下午4:24:17
	 */
	QueryOverdueReceivablesBean queryOverdueReceivablesBean(QueryOverdueReceivablesBean bean);
	
	/**
	 * 新增报表记录
	  * @param monthlyReportRecord
	  * @author: qiancong.Xian
	  * @date: 2015年7月8日 上午10:51:04
	 */
	void addMonthlyReportRecord(MonthlyReportRecord monthlyReportRecord);
	/**
	 * 
	  * @Description: 获取当前周期前的所有记录之和
	  * @param monthlyReportRecord
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月8日 下午2:24:55
	 */
	List<MonthlyReportRecord> getMonthlyReportRecords(MonthlyReportRecord monthlyReportRecord);
	/**
	  * 计算当前月份数据的时候，要删除以前未锁定的数据 
	  * @param monthlyReportRecord
	  * @author: qiancong.Xian
	  * @date: 2014年9月30日 下午5:03:10
	 */
	void deleteMonthlyReportRecords(MonthlyReportRecord monthlyReportRecord);
	/**
	 * 列表展现月报表数据
	  * @param month
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2014年10月1日 下午2:30:07
	 */
	List<MonthlyReportRecord> listMonthlyReportRecords(@Param("map")Map map);
	// add by qcxian
	
	int listMonthlyReportRecordsTotal(@Param("map")Map map);
	
	/**
	 * 
	  * @Description: 更新锁定状态 1 锁定 0 未锁定
	  * @param condition
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年7月11日 上午10:34:38
	 */
	int updateStatusLock(String [] pid);
	
	/**
	 * 
	  * @Description: 更新锁定状态 1 锁定 0 未锁定
	  * @param condition
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年7月11日 上午10:34:38
	 */
	int updateStatusNoLock(String [] pid);
	
	/**
	 * 新增月报表记录计算细节
	  * @param detail
	  * @author: qiancong.Xian
	  * @date: 2014年10月3日 下午5:05:42
	 */
	void addMonthlyReportRecordCalculateDetail(MonthlyReportRecordCalculateDetail detail);
	/**
	  * @Description: 计算当前月份数据的时候，要删除以前未锁定的数据对于的计算细节 
	  * @param monthlyReportRecord
	  * @author: qiancong.Xian
	  * @date: 2014年10月3日 下午5:12:51
	 */
	void deleteMonthlyReportRecordsDetail(MonthlyReportRecord monthlyReportRecord);
	
	/**
	  * @Description: 查询月报表中需要扣除的费用减免
	  * @param loanId
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月14日 上午10:50:34
	 */
	List<CostReduction> getCostReductions(int loanId);
	/**
	 * 手动调整的时候，需要根据项目No来获取loanId
	  * @param projectNo
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月14日 下午8:20:50
	 */
	int checkLoanIdByProjectNo(String projectNo);
	
	/**
	  * @Description: 应收月报表数据删除
	  * @param ids
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年7月30日 下午4:49:15
	 */
	int deleteMonthlyReportRecordsById(String [] ids);
	
	/**
	 * @Description: 应收月报表详情数据删除
	 * @param ids
	 * @return
	 * @author: Zhangyu.Hoo
	 * @date: 2015年7月30日 下午4:49:15
	 */
	int deleteMonthlyReportRecordsDetailByMonthId(String [] ids);
	
	/**
	 * 
	  * @Description: 获取生效的利率变更申请贷款ID
	  * @param map
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年8月7日 下午3:26:48
	 */
	List<MonthlyReportBasePlanIm> getChangeIntLoanId(@Param("map")Map<String,Object> map);
	/**
	 * 
	  * @Description: 获取项目的到期未收金额
	  * @param map
	  * @return
	  * @author: yequnli
	  * @date: 2015年9月15日 下午3:26:48
	 */
	double getLoanOverdueAmt(Map<String,Object> map);
	/**
	 * 
	  * @Description: TODO 查询部分对账的最大期数,获取项目的到期未收金额
	  * @param loanId
	  * @return
	  * @author: yequnli
	  * @date: 2015年9月15日 下午1:34:49
	 */
	LoanCycleNumView getLoanCycleNumAndAmt(@Param("map")Map<String,Object> map);
	 /**
	  * 
	   * @Description: TODO 根据贷款id查询有余额的收款信息
	   * @param loanId
	   * @return
	   * @author: yequnli
	   * @date: 2015年9月18日 下午1:56:40
	  */
    public List<AcctProjectBalanceView> getBalanceByLoanId(int loanId);
    
    /**
     * 
      * @Description: TODO 把剩余金额转入收入后修改收款表的数据
      * @param dto
      * @return
      * @author: yequnli
      * @date: 2015年9月18日 下午2:52:55
     */
    public int updateFinanceBalance(FinanceReceivablesDTO dto);
     /**
      * 
       * @Description: TODO 修改收款表删除状态，反核销使用
       * @param map
       * @return
       * @author: yequnli
       * @date: 2015年10月20日 下午5:29:23
      */
    public int  updateLoanInputStatus(@Param("map")Map<String,Object> map);
    /**
     * 
      * @Description: TODO 修改财务交易记录表删除状态，反核销使用
      * @param map
      * @return
      * @author: yequnli
      * @date: 2015年10月20日 下午5:29:23
     */
   public int  updateLoanFtStatus(@Param("map")Map<String,Object> map);
   /**
    * 
     * @Description: TODO 修改对账表记录表删除状态，反核销使用
     * @param map
     * @return
     * @author: yequnli
     * @date: 2015年10月21日 上午9:14:19
    */
   public int  updateReconciliationStatus(@Param("map")Map<String,Object> map);
   /**
    * 
     * @Description: TODO 查询被删除的 对账表id 用于反核销
     * @param map
     * @return
     * @author: yequnli
     * @date: 2015年10月21日 上午9:24:48
    */
   public List<RepaymentReconciliationView> getReconciliationId(@Param("map")Map<String,Object> map);
   /**
    * 
     * @Description: TODO  查询被删除的 对账表的本金金额 用于反核销
     * @param map
     * @return
     * @author: yequnli
     * @date: 2015年10月21日 下午4:23:02
    */
   public List<RepaymentReconciliationView> getReconciliationDtl(@Param("map")Map<String,Object> map);
   /**
    * 
     * @Description: TODO 修改对账明细表状态 用于反核销
     * @param map
     * @return
     * @author: yequnli
     * @date: 2015年10月21日 上午9:30:04
    */
   public int  updateReconciliationDtl(@Param("map")Map<String,Object> map);
   /**
    * 
     * @Description: TODO 修改及时发生表对账状态  用于反核销
     * @param map
     * @return
     * @author: yequnli
     * @date: 2015年10月21日 上午9:53:05
    */
   public int updateLoanRealTimeStatus(@Param("map")Map<String,Object> map);
   /**
    * 
     * @Description: TODO 即使发生表数据 用于反核销
     * @param map
     * @return
     * @author: yequnli
     * @date: 2015年10月21日 上午10:16:31
    */
   public LoanRealTimeDTO getLoanRealTime(int pid);
   /**
    * 
     * @Description: TODO 修改还款计划表对账状态  用于反核销
     * @param map
     * @return
     * @author: yequnli
     * @date: 2015年10月21日 上午10:50:57
    */
   public int updateRepaymentPlanStatus(@Param("map")Map<String,Object> map);
   /**
    * 
     * @Description: TODO 查询还款计划表 用于反核销
     * @param map
     * @return
     * @author: yequnli
     * @date: 2015年10月21日 上午10:16:31
    */
   public LoanRealTimeDTO getRepaymentPlan(@Param("map")Map<String,Object> map);
   
   
}
